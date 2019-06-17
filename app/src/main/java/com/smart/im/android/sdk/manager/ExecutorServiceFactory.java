package com.smart.im.android.sdk.manager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @date : 2019-06-17 14:13
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class ExecutorServiceFactory {
    private ExecutorService bossPool;// 管理线程组，负责重连
    private ExecutorService workPool;// 工作线程组，负责心跳

    /**
     * 初始化boss线程池
     */
    public synchronized void initBossLoopGroup() {
        initBossLoopGroup(1);
    }

    /**
     * 初始化boss线程池
     * 重载
     *
     * @param size 线程池大小
     */
    public synchronized void initBossLoopGroup(int size) {
        destroyBossLoopGroup();
        bossPool = new ThreadPoolExecutor(size, 1, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(1024),new ThreadPoolExecutor.AbortPolicy());
    }

    /**
     * 初始化work线程池
     */
    public synchronized void initWorkLoopGroup() {
        initWorkLoopGroup(1);
    }

    /**
     * 初始化work线程池
     * 重载
     *
     * @param size 线程池大小
     */
    public synchronized void initWorkLoopGroup(int size) {
        destroyWorkLoopGroup();
        workPool = new ThreadPoolExecutor(size, 1, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(1024),new ThreadPoolExecutor.AbortPolicy());
    }

    /**
     * 执行boss任务
     *
     * @param runnable
     */
    public void execBossTask(Runnable runnable) {
        if (bossPool == null) {
            initBossLoopGroup();
        }
        bossPool.execute(runnable);
    }

    /**
     * 执行work任务
     *
     * @param runnable
     */
    public void execWorkTask(Runnable runnable) {
        if (workPool == null) {
            initWorkLoopGroup();
        }
        workPool.execute(runnable);
    }

    /**
     * 释放boss线程池
     */
    public synchronized void destroyBossLoopGroup() {
        if (bossPool != null) {
            try {
                bossPool.shutdownNow();
            } catch (Throwable t) {
                t.printStackTrace();
            } finally {
                bossPool = null;
            }
        }
    }

    /**
     * 释放work线程池
     */
    public synchronized void destroyWorkLoopGroup() {
        if (workPool != null) {
            try {
                workPool.shutdownNow();
            } catch (Throwable t) {
                t.printStackTrace();
            } finally {
                workPool = null;
            }
        }
    }

    /**
     * 释放所有线程池
     */
    public synchronized void destroy() {
        destroyBossLoopGroup();
        destroyWorkLoopGroup();
    }
}

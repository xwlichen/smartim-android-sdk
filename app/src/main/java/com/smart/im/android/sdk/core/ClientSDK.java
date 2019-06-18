package com.smart.im.android.sdk.core;

import android.text.TextUtils;

import com.smart.im.android.sdk.ClientCoreWrapper;
import com.smart.im.android.sdk.entity.ConfigEntity;
import com.smart.im.android.sdk.listener.OnConnectListener;
import com.smart.im.android.sdk.listener.OnEventListener;
import com.smart.im.android.sdk.listener.OnQosListener;
import com.smart.im.android.sdk.manager.ExecutorServiceFactory;
import com.smart.im.android.sdk.utils.LogUtils;
import com.smart.im.protocal.proto.MessageProtocalEntity;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import static com.smart.im.android.sdk.entity.ConfigEntity.DEFAULT_RECONNECT_INTERVAL;

/**
 * @date : 2019/1/23 上午10:55
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description : Android端sdk
 */
public class ClientSDK extends ClientCoreWrapper {

    public static final String TAG = ClientSDK.class.getSimpleName();

    public static boolean DEBUG = true;
    private static volatile ClientSDK instance;

    private Bootstrap bootstrap;
    private Channel channel;
    private ClientCoreHander clientCoreHander;
    private boolean isClosed = false;
    private boolean isReconnecting = false;// 是否正在进行重连
    private int connectStatus = ConfigEntity.CONNECT_STATE_FAILURE;//连接状态，初始化为连接失败

    private OnEventListener onEventListener;
    private OnConnectListener onConnectListener;
    private OnQosListener onQosListener;


//    private int reconnectInterval = DEFAULT_RECONNECT_INTERVAL;
//    private int connectTimeout = ConfigEntity.DEFAULT_CONNECT_TIMEOUT;
//    private int keepAliveIntercal = ConfigEntity.DEFAULT_KEEPALIVE_INTERVAL;


    public static ClientSDK getInstance() {
        if (instance == null) {
            synchronized (ClientSDK.class) {
                instance = new ClientSDK();
            }
        }
        return instance;
    }


    public static void setDebug(boolean debug) {
        ClientSDK.DEBUG = debug;
    }

    public boolean isDebug() {
        return ClientSDK.DEBUG;
    }


    @Override
    public ExecutorServiceFactory getLoopGroup() {
        return loopGroup;
    }

    @Override
    public MessageProtocalEntity.Protocal createLoginMsg() {
        return null;
    }

    @Override
    public MessageProtocalEntity.Protocal createKeepAliveMsg() {
        return null;
    }

    @Override
    public void init(ConfigEntity config, OnEventListener onEventListener, OnConnectListener onConnectListener) {
        release();
        isClosed = false;
        this.onEventListener = onEventListener;
        this.onConnectListener = onConnectListener;
        loopGroup = new ExecutorServiceFactory();
        loopGroup.initBossLoopGroup();// 初始化重连线程组

        reConnect();

    }

    @Override
    public void init(ConfigEntity config, OnEventListener onEventListener, OnConnectListener onConnectListener, OnQosListener onQosListener) {
        release();
        isClosed = false;
        this.onEventListener = onEventListener;
        this.onConnectListener = onConnectListener;
        this.onQosListener = onQosListener;
        loopGroup = new ExecutorServiceFactory();
        loopGroup.initBossLoopGroup();// 初始化重连线程组

        reConnect();



    }


    @Override
    public void reConnect() {
        resetConnect(false);

    }

    @Override
    public void resetConnect(boolean isFirst) {
        if (!isFirst) {
            try {
                Thread.sleep(DEFAULT_RECONNECT_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 只有第一个调用者才能赋值并调用重连
        if (!isClosed && !isReconnecting) {
            synchronized (this) {
                if (!isClosed && !isReconnecting) {
                    // 标识正在进行重连
                    isReconnecting = true;
                    // 回调连接状态
                    LogUtils.d("正在连接中");
                    //onConnectStatusCallback();
                    // 先关闭channel
                    closeChannel();
                    // 执行重连任务
                    loopGroup.execBossTask(new ResetConnectRunnable(isFirst));
                }
            }
        }

    }

    @Override
    public boolean isConnect() {
        return false;
    }

    @Override
    public int sendMsg(MessageProtocalEntity.Protocal protocal) {
        return 0;
    }

    @Override
    public int sendMsg(MessageProtocalEntity.Protocal protocal, boolean isJoinTimeoutManager) {
        return 0;
    }

    @Override
    public void receivedMsg(MessageProtocalEntity.Protocal protocal) {
        if (onEventListener != null) {
            onEventListener.dispatchMsg(protocal);
        }

    }

    @Override
    public void release() {
        if (isClosed) {
            return;
        }
        closeChannel();

        // 关闭bootstrap
        try {
            if (bootstrap != null) {
                bootstrap.group().shutdownGracefully();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        isClosed = true;
        channel = null;
        bootstrap = null;

    }

    /**
     * 初始化bootstrap
     */
    private void initBootstrap() {
        EventLoopGroup loopGroup = new NioEventLoopGroup(4);
        bootstrap = new Bootstrap();
        bootstrap.group(loopGroup).channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_RCVBUF, 1024)
                .option(ChannelOption.SO_SNDBUF, 1024);
        ;
        // 设置初始化Channel
        clientCoreHander = new ClientCoreHander(this);
        bootstrap.handler(new UDPChannelInitializerHandler(clientCoreHander));
    }

    /**
     * 关闭channel
     */
    private void closeChannel() {
        try {
            if (channel != null) {
                channel.close();
                channel.eventLoop().shutdownGracefully();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("关闭channel出错，reason:" + e.getMessage());
        } finally {
            channel = null;
        }
    }


    /**
     * 连接状态回调
     *
     * @param code
     */
    private void onConnectStatusCallback(int code) {
        if (onConnectListener != null) {
            onConnectListener.onConnectStatus(code);
        }
    }


    /**
     * 从应用层获取网络是否可用
     *
     * @return
     */
    private boolean isNetworkAvailable() {
        if (onEventListener != null) {
            return onEventListener.isNetworkAvailable();
        }

        return false;
    }


    /**
     * 连接服务器
     */
    private void toServer() {
        try {
            channel = bootstrap.connect(ConfigEntity.SERVER_IP, ConfigEntity.SERVER_PORT).sync().channel();
        } catch (Exception e) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            LogUtils.e(TAG, String.format("连接Server(ip[%s], port[%s])失败", ConfigEntity.SERVER_IP, ConfigEntity.SERVER_PORT));
            channel = null;
        }
    }


    /**
     * 重连任务
     */
    private class ResetConnectRunnable implements Runnable {

        private boolean isFirst;

        public ResetConnectRunnable(boolean isFirst) {
            this.isFirst = isFirst;
        }

        @Override
        public void run() {
            // 非首次进行重连，执行到这里即代表已经连接失败，回调连接状态到应用层
            if (!isFirst) {
                onConnectStatusCallback(ConfigEntity.CONNECT_STATE_FAILURE);
            }

            try {
                // 重连时，释放工作线程组，也就是停止心跳
                loopGroup.destroyWorkLoopGroup();

                while (!isClosed) {
                    if (!isNetworkAvailable()) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }

                    // 网络可用才进行连接
                    int status;
                    if ((status = reConnect()) == ConfigEntity.CONNECT_STATE_SUCCESSFUL) {
                        onConnectStatusCallback(status);
                        // 连接成功，跳出循环
                        break;
                    }

                    if (status == ConfigEntity.CONNECT_STATE_FAILURE) {
                        onConnectStatusCallback(status);
                        try {
                            Thread.sleep(DEFAULT_RECONNECT_INTERVAL);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } finally {
                // 标识重连任务停止
                isReconnecting = false;
            }
        }

        /**
         * 重连，首次连接也认为是第一次重连
         *
         * @return
         */
        private int reConnect() {
            // 未关闭才去连接
            if (!isClosed) {
                try {
                    // 先释放EventLoop线程组
                    if (bootstrap != null) {
                        bootstrap.group().shutdownGracefully();
                    }
                } finally {
                    bootstrap = null;
                }

                // 初始化bootstrap
                initBootstrap();
                return connectServer();
            }
            return ConfigEntity.CONNECT_STATE_FAILURE;

        }

        /**
         * 连接服务器
         *
         * @return
         */
        private int connectServer() {
            // 如果服务器地址无效，直接回调连接状态，不再进行连接
            // 有效的服务器地址示例：127.0.0.1 8860
            if (TextUtils.isEmpty(ConfigEntity.SERVER_IP) || ConfigEntity.SERVER_PORT <= 0) {
                return ConfigEntity.CONNECT_STATE_FAILURE;
            }

            if (isClosed || !isNetworkAvailable()) {
                return ConfigEntity.CONNECT_STATE_FAILURE;
            }

            for (int i = 1; i <= ConfigEntity.DEFAULT_RECONNECT_COUNT; i++) {
                // 如果ims已关闭，或网络不可用，直接回调连接状态，不再进行连接
                if (isClosed || !isNetworkAvailable()) {
                    return ConfigEntity.CONNECT_STATE_FAILURE;
                }

                // 回调连接状态
                if (connectStatus != ConfigEntity.CONNECT_STATE_CONNECTING) {
                    onConnectStatusCallback(ConfigEntity.CONNECT_STATE_CONNECTING);
                }
                LogUtils.e(TAG, String.format("正在进行[%s]的第[%d]次连接，当前重连延时时长为[%dms]", ConfigEntity.SERVER_IP + ConfigEntity.SERVER_PORT, i, i * ConfigEntity.DEFAULT_RECONNECT_INTERVAL));

                try {

                    toServer();// 连接服务器

                    // channel不为空，即认为连接已成功
                    if (channel != null) {
                        return ConfigEntity.CONNECT_STATE_SUCCESSFUL;
                    } else {
                        // 连接失败，则线程休眠n * 重连间隔时长
                        Thread.sleep(i * ConfigEntity.DEFAULT_RECONNECT_INTERVAL);
                    }
                } catch (InterruptedException e) {
                    release();
                    break;// 线程被中断，则强制关闭
                }
            }

            // 执行到这里，代表连接失败
            return ConfigEntity.CONNECT_STATE_FAILURE;
        }
    }
}

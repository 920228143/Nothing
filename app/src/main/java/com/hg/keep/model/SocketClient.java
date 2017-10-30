package com.hg.keep.model;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

public class SocketClient {
    private static SocketClient instance;
    private ClientSocketThread mClientSocketThread;
    private OnConnectListener mOnConnectListener;

    private byte[] SOCKET_TRAILER;  //包尾


    public static SocketClient getInstance() {
        if (instance == null) {
            instance = new SocketClient();
        }
        return instance;
    }

    public void connect(OnConnectListener onConnectListener) {
        try {
            mOnConnectListener = onConnectListener;
            connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void connect() throws Exception {
        if (mOnConnectListener == null) {
            throw new RuntimeException("请设置OnConnectListener");
        }
        if (mClientSocketThread != null) {
            mClientSocketThread.close();
            mClientSocketThread = null;
        }
        mClientSocketThread = new ClientSocketThread();
        mClientSocketThread.start();
    }

    public void disconnect() {
        if (mOnConnectListener == null) {
            throw new RuntimeException("请设置OnConnectListener");
        }
        if (mClientSocket != null) {
            try {
                if (mClientSocket != null) {
                    mClientSocket.close();
                    mClientSocket = null;
                }
                if (mClientIn != null) {
                    mClientIn.close();
                    mClientIn = null;
                }
                if (mClientOut != null) {
                    mClientOut.close();
                    mClientOut = null;
                }
                if (mClientSocketThread != null) {
                    mClientSocketThread.close();
                    mClientSocketThread = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mOnConnectListener.onConnectError(new RuntimeException("已经断开连接"), "已经断开连接");
        }
    }

    public boolean sendData(byte[] data) {
        if (mOnConnectListener == null) {
            throw new RuntimeException("请设置OnConnectListener");
        }
        if (data == null || data.length == 0) {
            mOnConnectListener.onConnectError(new RuntimeException("发送的空数据"), "发送的空数据");
            return false;
        }
        if (isConnected()) {
            try {
                mClientOut.write(data);
                //刷新此缓冲的输出流，保证数据全部都能写出
                mClientOut.flush();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void insideDisconnect() {
        try {
            if (mClientSocket != null) {
                mClientSocket.close();
                mClientSocket = null;
            }
            if (mClientIn != null) {
                mClientIn.close();
                mClientIn = null;
            }
            if (mClientOut != null) {
                mClientOut.close();
                mClientOut = null;
            }
            if (mClientSocketThread != null) {
                mClientSocketThread = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //================================================================================= 
    // Client 
    //================================================================================= 

    private String mClientIp;
    private int mClientPort;
    private int CONNECT_TIMEOUT;
    private Socket mClientSocket;
    private DataInputStream mClientIn;
    private DataOutputStream mClientOut;

    class ClientSocketThread extends Thread {
        private Thread recerverThread;

        @Override
        public void run() {
            try {
                mClientSocket = new Socket();
                mClientSocket.connect(new InetSocketAddress(mClientIp, mClientPort), CONNECT_TIMEOUT);
                mClientIn = new DataInputStream(mClientSocket.getInputStream());
                mClientOut = new DataOutputStream(mClientSocket.getOutputStream());
                ClientReceiverRunnable runnable = new ClientReceiverRunnable(mClientSocket);
                recerverThread = new Thread(runnable);
                recerverThread.start();
                if (mOnConnectListener != null) {
                    mOnConnectListener.onConnectSuccess(mClientSocket.getInetAddress().getHostAddress(), mClientSocket.getLocalAddress().getHostAddress());
                }
            } catch (IOException e) {
                if (mOnConnectListener != null) {
                    mOnConnectListener.onConnectFail(e, "服务器没有开启");
                }
                insideDisconnect();
                e.printStackTrace();
            }
        }

        public void close() {
            try {
                if (recerverThread != null) {
                    recerverThread.interrupt();
                    recerverThread = null;
                    interrupt();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class ClientReceiverRunnable implements Runnable {
        private Socket socket;
        private final int EQUAL_SIZE = 3;// SocketHelper.HeartBEAT.length
        private long time;

        public ClientReceiverRunnable(Socket socket) {
            this.socket = socket;
        }

        /**
         * @param data
         * @return 取出所有 有效数据后， 剩下的数据
         */
        private byte[] getGarbage(byte[] data) {
            int TrailerIndex = 0;
            int HeartBeatIndex = 0;
            for (int i = 0; i < data.length; i++) {
                byte b = data[i];
                //过滤包尾
                if (b == SOCKET_TRAILER[TrailerIndex]) {
                    TrailerIndex++;
                    if (TrailerIndex == EQUAL_SIZE) {
                        final byte[] result = Arrays.copyOfRange(data, 0, i + 1);
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                mOnConnectListener.onHeartBeat();
                                mOnConnectListener.onMessage(result);
                            }
                        }.start();
                        byte[] garbage = Arrays.copyOfRange(data, i + 1, data.length);
                        return getGarbage(garbage);
                    }
                } else {
                    TrailerIndex = 0;
                }
            }
            return data;
        }

        // 处理分包问题
        private void receiver(byte[] data) throws Exception {
            int cha;
            byte[] rev;
            if ((cha = mClientIn.read()) != -1) {   //读到第一个字节时激活
                if (data == null) {
                    time = System.currentTimeMillis();
                }
                int length = mClientIn.available();    //剩下的所有数据的长度
                if (data != null && data.length > 0) {
                    rev = new byte[data.length + 1 + length];
                    System.arraycopy(data, 0, rev, 0, data.length);
                    rev[data.length] = (byte) cha;
                    mClientIn.read(rev, data.length + 1, length);
                } else {
                    rev = new byte[length + 1];
                    rev[0] = (byte) cha;
                    mClientIn.read(rev, 1, length);
                }
                byte[] garbage = getGarbage(rev);
                if (garbage.length > 0) {
                    receiver(garbage);
                }
            } else {
                throw new Exception("服务器连接中断");
            }
        }

        @Override
        public void run() {
            boolean isConnected = true;
            while (isConnected) {
                try {
                    receiver(null);
                } catch (Exception e) {
                    isConnected = false;
                    e.printStackTrace();
                    if (mOnConnectListener != null) {
                        mOnConnectListener.onConnectError(e, "服务器连接中断");
                    }
                }
            }
            try {
                if (socket != null) {
                    socket.close();
                }
                if (mClientIn != null) {
                    mClientIn.close();
                }
                if (mClientOut != null) {
                    mClientOut.close();
                }
                if (mClientSocketThread != null) {
                    mClientSocketThread.close();
                    mClientSocketThread = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConnected() {
        return mClientSocket != null && mClientSocket.isConnected();
    }

    interface OnConnectListener {
        /**
         * @param inetAddress  服务端地址
         * @param localAddress 客户端地址
         */
        void onConnectSuccess(String inetAddress, String localAddress);

        void onConnectFail(Throwable e, String message);

        void onConnectError(Throwable e, String message);

        void onMessage(byte[] data);

        void onHeartBeat();
    }
}
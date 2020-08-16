import sun.nio.cs.ext.IBM037;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    public static void main(String[] args) throws IOException {


        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(9999));

        while (true) {
            final Socket accept = serverSocket.accept();
            System.out.println("获取到一个新连接：" + accept.toString());
            new Thread(new Runnable() {
                public void run() {
                    try {
                        InputStream inputStream = accept.getInputStream();
                        byte[] bytes = new byte[1024];
                        int len = 0;
                        while ((len = inputStream.read(bytes)) != -1) {
                            System.out.println("收到客户端的消息：" + new String(bytes, 0, len));
                            SocketServer.sendMessage(accept.getOutputStream(), new String(bytes, 0, len), accept);
                        }
                        System.out.println(accept.toString()+"is close----");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }

    public static void sendMessage(OutputStream os, String msg, Socket socket) throws IOException {
        os.write(msg.getBytes());
        os.flush();
        if ("stop".equals(msg)) {
            socket.shutdownOutput();
            socket.shutdownInput();
            socket.close();
        }
    }
}

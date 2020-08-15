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
            System.out.println("获取到一个连接：" + accept.toString());
            new Thread(new Runnable() {
                public void run() {
                    try {
                        InputStream inputStream = accept.getInputStream();
                        byte[] bytes = new byte[1024];
                        int len = 0;
                        while ((len = inputStream.read(bytes)) != -1) {
                            System.out.println("收到客户端的消息："+new String(bytes, 0, len));
                            SocketServer.sendMessage(accept.getOutputStream(), new String(bytes, 0, len));
                        }
                        System.out.println("end----");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

    }

    public static void sendMessage(OutputStream os, String msg) throws IOException {
        os.write(msg.getBytes());
        os.flush();
    }
}

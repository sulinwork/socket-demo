import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("localhost", 9999);
        OutputStream outputStream = null;
        final InputStream inputStream = socket.getInputStream();
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String next = scanner.next();

            outputStream = socket.getOutputStream();
            outputStream.write(next.getBytes());
            outputStream.flush();


            new Thread(new Runnable() {
                public void run() {
                    try {
                        byte[] bytes = new byte[1024];
                        int len = 0;
                        while ((len = inputStream.read(bytes)) != -1) {
                            System.out.println("callBack:"+new String(bytes, 0, len));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
            ).start();
        }

    }
}

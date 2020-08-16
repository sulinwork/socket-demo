import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {

    public static void main(String[] args) throws IOException {

        final Socket socket = new Socket("localhost", 9999);
        final InputStream inputStream = socket.getInputStream();
        final OutputStream os = socket.getOutputStream();
        new Thread(new Runnable() {
            public void run() {
                try {
                    byte[] bytes = new byte[1024];
                    int len = 0;
                    while ((len = inputStream.read(bytes)) != -1) {
                        System.out.println("callBack:" + new String(bytes, 0, len));
                    }
                    System.out.println("read thead stop ...");
                    socket.shutdownInput();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        ).start();
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String next = scanner.nextLine();
            os.write(next.getBytes());
            os.flush();
            if (next.equals("stop")) {
                socket.shutdownOutput();
                break;
            }
        }

    }
}

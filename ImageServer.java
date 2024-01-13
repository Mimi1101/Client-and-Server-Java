import java.io.*;
import java.net.*;

public class ImageServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8070);
            System.out.println("server is waiting for connection request from clients.");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected: " + socket.getInetAddress().getHostAddress());

                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                BufferedOutputStream out = new BufferedOutputStream(
                        socket.getOutputStream());

                // Step one: read the picture name from the client
                String pictureName = in.readLine();
                System.out.println("Requested picture: " + pictureName);

                // Step two: read the requested picture and send it to the client
                File file = new File(pictureName);
                if (file.exists()) {
                    FileInputStream fileIn = new FileInputStream(file);
                    byte[] buffer = new byte[4096];
                    int bytesRead = 0;
                    while ((bytesRead = fileIn.read(buffer, 0, buffer.length)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                    fileIn.close();
                } else {
                    String errorMessage = "Sorry, no such picture.";
                    out.write(errorMessage.getBytes());
                }

                out.flush();

                // Step three: close all the input/output streams and socket.
                in.close();
                out.close();
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

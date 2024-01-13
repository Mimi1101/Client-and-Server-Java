import java.io.*;
import java.net.*;

public class ImageClient {
    public static void main(String[] args) {
        String host = "localhost";
        try {
            Socket socket = new Socket(host, 8070);
            BufferedInputStream in = new BufferedInputStream(
                    socket.getInputStream());
            PrintWriter out = new PrintWriter(new OutputStreamWriter(
                    socket.getOutputStream()));

            // Step one: send the picture name "Koala.jpg" to the server
            out.println("Koala-wrong.jpg");
            out.flush();

            // Step two: write the response from the server to a local file "Koala-1.jpg";
            FileOutputStream fileOut = new FileOutputStream("Koala-1.jpg");
            byte[] buffer = new byte[8192];
            int bytesRead = 0;
            while ((bytesRead = in.read(buffer, 0, buffer.length)) != -1) {
                fileOut.write(buffer, 0, bytesRead);
            }
            fileOut.close();

            // Step three: close all the input/output streams and socket.
            in.close();
            out.close();
            socket.close();

            // Step four: try to read the file "Koala-1.jpg" using any picture viewer. If you can view the picture correctly, your download of picture is correct.
            // run the client code again and try to send a wrong picture name "Koala-wrong.jpg"
            // you will create a Koala-1.jpg again, this time use a normal text editor to open it, if you see "Sorry, no such picture", then your program is correct.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

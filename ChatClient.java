import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) {
        String host = "localhost"; // or server IP
        int port = 1234;

        try (
            Socket socket = new Socket(host, port);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        ) {
            // Read messages from server in a separate thread
            new Thread(() -> {
                String serverMsg;
                try {
                    while ((serverMsg = input.readLine()) != null) {
                        System.out.println("Server: " + serverMsg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Send user input to server
            String userMsg;
            while ((userMsg = userInput.readLine()) != null) {
                output.println(userMsg);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to server. Enter coordinates as 'x1 y1 x2 y2' or 'exit' to quit.");

            while (true) {
                System.out.print("Enter input: ");
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("exit")) {
                    out.println("exit");
                    System.out.println("Disconnecting...");
                    break;
                }

                out.println(input);
                String response = in.readLine();
                System.out.println("Server response: " + response);
            }
        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
        }
    }
}
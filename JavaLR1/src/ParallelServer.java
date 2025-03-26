import java.io.*;
import java.net.*;

public class ParallelServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Parallel Server started on port 12345...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("Could not start server: " + e.getMessage());
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            System.out.println("Client connected: " + clientSocket.getInetAddress());

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.equalsIgnoreCase("exit")) {
                    break;
                }

                try {
                    String[] coords = inputLine.split("\\s+");
                    if (coords.length != 4) {
                        throw new IllegalArgumentException("Invalid input format.");
                    }

                    double x1 = Double.parseDouble(coords[0]);
                    double y1 = Double.parseDouble(coords[1]);
                    double x2 = Double.parseDouble(coords[2]);
                    double y2 = Double.parseDouble(coords[3]);

                    double distance = Math.hypot(x2 - x1, y2 - y1);

                    if (distance == 0) {
                        out.println("Error: Points are identical.");
                    } else {
                        out.printf("%.2f%n", distance);
                    }
                } catch (Exception e) {
                    out.println("Error: " + e.getMessage());
                }
            }

            System.out.println("Client disconnected: " + clientSocket.getInetAddress());
        } catch (IOException e) {
            System.err.println("Client handling error: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Failed to close client socket: " + e.getMessage());
            }
        }
    }
}
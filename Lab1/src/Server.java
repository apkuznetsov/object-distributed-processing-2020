import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);

        int count = 0;
        while (true) {
            Socket clientSocket = serverSocket.accept();

            System.out.println("client accepted " + (++count));

            OutputStreamWriter writer = new OutputStreamWriter(clientSocket.getOutputStream());

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));

            String request = reader.readLine();
            String response = '#' + count + ", your message length is " + request.length() + '\n';
            writer.write(response);
            writer.flush();

            writer.close();
            reader.close();
            clientSocket.close();
        }
    }
}

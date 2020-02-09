import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        Socket clientSocket = serverSocket.accept();

        OutputStreamWriter writer = new OutputStreamWriter(clientSocket.getOutputStream());

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));

        int num1;
        int num2;
        int quotient;
        int remainder;

        while (true) {
            num1 = Integer.parseInt(reader.readLine());
            System.out.println("получено число ............... " + num1);

            num2 = Integer.parseInt(reader.readLine());
            System.out.println("получен делитель ............. " + num2);
            System.out.println();

            quotient = num1 / num2;
            System.out.println("результат деления ............ " + quotient);

            remainder = num1 % num2;
            System.out.println("остаток от деления ........... " + remainder);
            System.out.println();

            writer.write(quotient + "\n");
            writer.write(remainder + "\n");
            writer.flush();
        }
    }
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);

        int num1;
        int num2;
        int quotient;
        int remainder;

        while (true) {
            Socket clientSocket = serverSocket.accept();

            OutputStreamWriter writer = new OutputStreamWriter(clientSocket.getOutputStream());

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            num1 = Integer.parseInt(reader.readLine());
            System.out.println("получено число ............... " + num1);

            num2 = Integer.parseInt(reader.readLine());
            System.out.println("получен делитель ............. " + num2);
            System.out.println();

            if (num2 != 0) {
                quotient = num1 / num2;
                System.out.println("результат деления ............ " + quotient);

                remainder = num1 % num2;
                System.out.println("остаток от деления ........... " + remainder);

                writer.write(quotient + "\n");
                writer.write(remainder + "\n");
            } else {
                String response = "операция невозможна: деление на ноль";
                System.out.println(response);

                writer.write(response + "\n");
            }
            writer.flush();
            System.out.println();
        }
    }
}

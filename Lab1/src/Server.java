import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);

        int num1;
        int num2;
        int quotient;
        int remainder;

        int count = 0;
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("клиент подключён " + (++count));
            OutputStreamWriter writer = new OutputStreamWriter(clientSocket.getOutputStream());
            //
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            //
            //
            //
            //
            //
            num1 = Integer.parseInt(reader.readLine());
            System.out.println("получено число ....... " + num1);
            //
            //
            num2 = Integer.parseInt(reader.readLine());
            System.out.println("получен делитель ..... " + num2);
            //
            quotient = num1 / num2;
            System.out.println("результат деления .... " + quotient);
            //
            remainder = num1 % num2;
            System.out.println("остаток от деления ... " + remainder);
            //
            writer.write(quotient + '\n');
            writer.write(remainder + '\n');
            writer.flush();
            //
            //
            //
            //
            writer.close();
            reader.close();
            clientSocket.close();
        }
    }
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket clientSocket = new Socket("127.0.0.1", 8080);

        OutputStreamWriter writer = new OutputStreamWriter(clientSocket.getOutputStream());
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));

        BufferedReader keyboardReader = new BufferedReader(
                new InputStreamReader(System.in));

        int num1;
        int num2;
        int quotient;
        int remainder;

        System.out.println("введите число ................ ");
        num1 = Integer.parseInt(keyboardReader.readLine());

        System.out.println("введите делитель ............. ");
        num2 = Integer.parseInt(keyboardReader.readLine());
        System.out.println();

        writer.write(num1 + "\n");
        writer.write(num2 + "\n");
        writer.flush();

        try {
            quotient = Integer.parseInt(reader.readLine());
            remainder = Integer.parseInt(reader.readLine());

            System.out.println("получен результат деления .... " + quotient);
            System.out.println("получен остаток от деления ... " + remainder);
            System.out.println();
        } catch (Exception exc) {
            System.out.println(exc.getMessage());
        }

        keyboardReader.close();
        reader.close();
        writer.close();
        clientSocket.close();
    }
}

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

class Client implements Runnable {
    Socket socket;
    Scanner in;
    PrintStream out;
    ChatServer server;

    // конструктор класса Client
    public Client(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
        // запускаем поток, который слушает поведение сервера на новые соообщения
        new Thread(this).start();
    }

    // функция пересыла сообщений другим клиентам, вызвыается при каждом отправлении сообщений
    void receive(String message) {
        out.println(message);
    }

    public void run() {
        try {
            // получаем потоки ввода и вывода
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            // создаем удобные средства ввода и вывода
            in = new Scanner(is);
            out = new PrintStream(os);

            // читаем из сети и пишем в сеть
            out.println("Welcome to chat!");
            String input = in.nextLine();

            // читаем входящие сообщения, пока не придет bye от клиента
            while (!input.equals("bye")){
                server.sendAll(input);
                input = in.nextLine();
            }
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}







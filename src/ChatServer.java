import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
    // создаем список клиентов
    ArrayList<Client> clients = new ArrayList<>();
    ServerSocket serverSocket;

    // конструктор класса ChatServer
    ChatServer() throws IOException {
        // создаем серверный сокет на порту 1234
        serverSocket = new ServerSocket(1234);
    }

    // функция отправки сообщений всем клиентам, то есть когда один клиент что-нибудь отправляет
    // его сообщение также отправляется всем остальным клиентам по списку ArrayList<Client> clients
    void sendAll(String message){
        for (Client client : clients){
            client.receive(message);
        }
    }

    public void run(){
        while (true) {
            System.out.println("Waiting...");
            try {
                // ждем клиента
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");
                // создаем клиента на своей стороне
                clients.add(new Client(socket, this));
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws IOException {
        new ChatServer().run();
    }
}



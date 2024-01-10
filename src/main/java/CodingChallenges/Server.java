package CodingChallenges;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;



public class Server {
    private final ServerSocket serverSocket;
    private Socket socket;
    private HttpWorker httpWorker;

    public static final String Path = "src/main/www";


    public Server(int port, InetAddress address) throws IOException {
        serverSocket = new ServerSocket(port,0,address);
    }

    public void start() throws IOException {

        while(true){
            socket = serverSocket.accept();
            if(socket.isConnected()){
                new Thread( () ->{
                    System.out.println("Thread id: " + Thread.currentThread().getId());
                    httpWorker = new HttpWorker(socket);
                    httpWorker.getFile();
                    httpWorker.close();
                }).start();
            }
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

}

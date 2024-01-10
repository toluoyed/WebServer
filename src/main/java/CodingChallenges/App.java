package CodingChallenges;

import com.sun.net.httpserver.HttpServer;

import java.net.InetAddress;
import java.net.Socket;

import java.net.InetSocketAddress;

/**
 * Hello world!
 *
 */
public class App
{
    public static final int port = 80;

    public static void main( String[] args ) throws Exception
    {
        InetAddress address = InetAddress.getByName("127.0.0.1");
        Server server = new Server(port, address);
        server.start();
        server.stop();
    }
}

package CodingChallenges;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

@RunWith(JUnit4.class)
public class ClientTest {
    Client client;
    private final String NOT_FOUND_MESSAGE = "HTTP/1.1 400 NOT FOUND ";
    private final String OK_MESSAGE = "HTTP/1.1 200 OK <!DOCTYPE html><html lang=\"en\"><head>    <title>Simple Web Page</title></head><body><h1>Test Web Page</h1><p>My web server served this page!</p></body></html>";

    @Before
    public void setup() throws IOException {
        client = new Client();
        client.startConnection("127.0.0.1",App.port);

    }

    @After
    public void clearAll() throws IOException {
        client.stopConnection();
    }

    @Test
    public void test_server_returns_Not_Found_Message_when_file_is_not_found() throws IOException {
       String message =  client.sendMessage("GET /favicon.ico HTTP/1.1");
       assertEquals(message,NOT_FOUND_MESSAGE);

    }

    @Test
    public void test_server_returns_ok_message_and_file_contents_when_foward_slash_request_is_sent() throws IOException {
        String message =  client.sendMessage("GET / HTTP/1.1");
        assertEquals(message,OK_MESSAGE);

    }

    @Test
    public void test_server_returns_ok_message_and_file_contents_when_indexhtml_request_is_sent() throws IOException {
        String message =  client.sendMessage("GET / HTTP/1.1");
        assertEquals(message,OK_MESSAGE);

    }


    @Test
    public void test_server_returns_Not_Found_Message_when_different_directory_path_is_sent() throws IOException {
        String message =  client.sendMessage("GET C:\\Users\\toluw\\OneDrive\\Desktop\\Interview Tips.txt HTTP/1.1");
        assertEquals(message,NOT_FOUND_MESSAGE);
    }

//    @Test
//    public void test_server_can_handle_multiple_concurrent_requests() throws IOException {
//        for (int i = 0; i < 7; i++){
//            String message =  client.sendMessage("GET / HTTP/1.1");
//            assertEquals(message,OK_MESSAGE);
//        }
//        verify()
//    }

}

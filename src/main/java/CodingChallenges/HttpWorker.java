package CodingChallenges;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.List;

public class HttpWorker {

    String request;
    Socket socket;
    private BufferedReader br;
    private DataOutputStream writer;
    BufferedWriter bufferedWriter;

    public HttpWorker(Socket socket) {
        this.socket = socket;
        try{
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new DataOutputStream(socket.getOutputStream());
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            request = br.readLine();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public void getFile() {
        System.out.println("Request: " + request);
        String path = getPath();
        System.out.println("Path: " + path);
        getFileContent(path);

    }
    private String getPath(){
        StringBuilder path = new StringBuilder();
        int index = 4;
        while(index < request.length()){
            if(request.charAt(index) == ' '){
                break;
            }
            path.append(request.charAt(index));
            index++;
        }
        return path.toString();
    }
    private String sendResponse(Responses response) throws IOException {
        if(response == Responses.OK){
            return "HTTP/1.1 200 OK \r\n";

        }
        return "HTTP/1.1 400 NOT FOUND \r\n";
    }
    private void getFileContent (String path){
        if (path.equals("/")){
            path += "index.html";
        }
        File f = new File(Server.Path,path);
        try{
            if (f.exists()){
                System.out.println("File exists");
                List<String> fileLines = Files.readAllLines(f.toPath());
//                String fileContent = String.join("\n",fileLines);
                StringBuilder builder = new StringBuilder();
                builder.append(sendResponse(Responses.OK)).append("\r\n");
                fileLines.forEach(i -> builder.append(i).append("\r\n"));

                bufferedWriter.write(builder.toString());
                bufferedWriter.flush();
            }
            else{
                bufferedWriter.write(sendResponse(Responses.NOT_FOUND));
                bufferedWriter.flush();
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    public void close(){
        try{
            br.close();
            writer.close();
            bufferedWriter.close();
            socket.close();
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}

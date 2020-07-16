import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ActionBank {
    DataOutputStream dataOut;
    BufferedReader dataIn=null;
    ActionBank(Socket socket) {
        ArrayList<String> stringInput = new ArrayList<>();
        try {

            String tmp;
            this.dataIn=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.dataOut=new DataOutputStream(socket.getOutputStream());

            while ((tmp = dataIn.readLine()).length()>0){
                stringInput.add(tmp);
            }
            classefy(stringInput);
        }catch (Exception e){
            System.out.println("error reading data from client:"+e);
        }

    }
    Integer classefy(ArrayList<String> lines){
        for (String line:lines) {
            if ( line.contains("login") && (line != null)) {
                act(1,null);
                break;
            }
        }
        return 0;
    }
    void act(Integer id,String[] inf){
            switch (id) {
                case 1: {
                    Integer contentLengthLine=0;
                    //for (String line : staticFileLines("test")) {
                        //contentLengthLine+=line.length();
                        contentLengthLine=staticFileLines("test").length();
                    //}
                    try {
                        dataOut.writeBytes("HTTP/1.1 200 OK" + "\r\n");
                        dataOut.writeBytes("Server: Java HTTPServer"+ "\r\n");
                        dataOut.writeBytes("Content-Type: text/html"+ "\r\n");
                        dataOut.writeBytes("Content-Length: " + contentLengthLine + "\r\n");
                        dataOut.writeBytes("Connection: close\r\n");
                        dataOut.writeBytes("\r\n");
                        //for (String line : staticFileLines("test")) {
                                //dataOut.writeBytes(line+"\r\n");
                                dataOut.writeBytes(staticFileLines("test"));
                        //}
                        //dataOut.close();
                    }catch (Exception e){System.out.println("Error: "+e);}
                }
                break;
                default: {
                }
            try {
                //dataOut.close();
            }catch (Exception e){
                System.out.println("Unable to close connection: "+e);
            }
        }
    }

    //ArrayList<String> staticFileLines(String id){
    String staticFileLines(String id){
        if(id.equals("test")) {
            try {
                String res = "";
                BufferedReader fr = new BufferedReader(new FileReader(new File("src"+File.separator+"Clients"+File.separator+"loginPage.html")));
                String line;
                while ((line = fr.readLine())!=null)
                    res+=line+"\n";
                return res;
            } catch (Exception fileError) {
                System.out.println(fileError);
            }
        }
        return null;
    }
}

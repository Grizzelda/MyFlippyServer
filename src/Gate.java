import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.HashMap;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

public class Gate {
    Map<Integer,Thread> activeGreaters;
    Gate(){
        //active ports
        this.activeGreaters=new HashMap<>();
    }
    void openGate(Integer port){
        if(!activeGreaters.containsKey(port)) {
            activeGreaters.put(port, new Thread(new Greater(port)));
            activeGreaters.get(port).start();
            System.out.println("Gate open on port: "+port);
        }
    }
    void closeGate(Integer port){
        //kill Greaters
        activeGreaters.get(port).stop();
    }
    class Greater implements Runnable{
        Integer agentCount;
        ServerSocket port;
        Map<Integer,Thread> activeAgents;
        public Greater(Integer sPort){
            this.agentCount=0;
            this.activeAgents=new HashMap<>();
            try {
                this.port = new ServerSocket(sPort);
            } catch (Exception e){
                System.out.println("init server failed on stage 'stamp'.\n -original error: "+e+".");
            }
        }
        @Override
        public void run(){
            try{
                while (true) {
                    assign(this.port.accept());
                }
            }catch (Exception e){
                System.out.println("run server failed on stage 'stamp'.\n -original error: "+e+".");
            }
        }
        public void assign(Socket accepted){
            this.activeAgents.put(++agentCount,new Thread(new Agent(accepted)));
            this.activeAgents.get(agentCount).start();
        }
        class Agent implements Runnable{
            Socket sub;
            boolean redButton;
            Agent(Socket client){
                redButton=false;
                this.sub=client;
            }
            @Override
            public void run() {
                Integer id=agentCount;
                try {
                    //while(!redButton) {
                        new ActionBank(sub);
                        sub.close();
                    //}
                }catch (Exception errr){
                    System.out.println("Gate "+port+" Agent no "+id+" failed to load.");
                }
            }
        }
    }
}



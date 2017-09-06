package manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Manager extends Thread{

    private static ServerSocket server = null;
    private static Socket nodeGestConnector = null;
    private static PrintStream nodeGestOutput = null;
    private static BufferedReader nodeGestInput = null;
    private static Vector<NodeGest> nodeGestList;
    
    private Manager(BufferedReader nodeGestInput, PrintStream nodeGestOutput){
        this.nodeGestInput = nodeGestInput;
        this.nodeGestOutput = nodeGestOutput;
    }
   
    public static void main(String[] args) {
       try{
            server = new ServerSocket(1111);
            nodeGestList = new Vector<NodeGest>();
            
            while(true){
                nodeGestConnector = server.accept();
                System.out.println("Manager is Running...");
                
                Thread t = new Manager(nodeGestInput,nodeGestOutput);
                t.start();
            }
       } catch (IOException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //THREAD LISTENS AND SENDS MESSAGES FROM/TO NODEGEST
    @Override
    public void run(){
        try {
            
            System.out.println("NodeGest connected to Manager!");
            
            nodeGestOutput = new PrintStream(nodeGestConnector.getOutputStream());
            nodeGestInput = new BufferedReader(new InputStreamReader(nodeGestConnector.getInputStream()));
            //TEST REQUEST
            //nodeGestOutput.println("Request N1 S1");
            
            //Waits messages from nodeGest.
            while(true){
                String nodeGestData = nodeGestInput.readLine();
                //TRATAR DADOS DO NODEGEST
                //NEW NODEGEST ADDED TO NODEGEST LIST
                if(nodeGestData.startsWith("Sector")){
                    String sectorNumber = nodeGestData.substring(7);
                    NodeGest ng = new NodeGest(sectorNumber, nodeGestOutput);
                    nodeGestList.add(ng);
                    System.out.println("TESTE SECTOR: " + nodeGestData);
                }
                //RESPONSES TO DATA REQUESTS
                if(nodeGestData.startsWith("Response")){
                    //TODO TODO TODO TODO TODO TODO
                }
                if(nodeGestData.startsWith("Alert")){
                    //TODO TODO TODO TODO TODO TODO
                }
            }    
        } catch (IOException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void requestData(String request){
        System.out.println("Request: " + request);
        
        if(nodeGestOutput != null){
            //Full Request Format e.g.: Request NG1 N2 S1
            if(request.startsWith("Request")){
                request = request.substring(8);
                if(request.startsWith("NG")){
                    int element = Integer.parseInt(request.substring(2,3));
                    request = request.substring(4);
                    request = "Request " + request;
                    nodeGestList.elementAt(element-1).output.println(request);
                    System.out.println("Request para o NodeGest: " + request);
                }
            }
            //Full SetInterval Format e.g.: SetInterval NG1 N2 S1 m2
            if(request.startsWith("SetInterval")){
                request = request.substring(12);
                if(request.startsWith("NG")){
                    int element = Integer.parseInt(request.substring(2,3));
                    request = request.substring(4);
                    request = "SetInterval " + request;
                    nodeGestList.elementAt(element-1).output.println(request);
                    System.out.println("SetInterval para o NodeGest: " + request);
                }
            }
        }
    }
}
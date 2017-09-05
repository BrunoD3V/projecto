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
    
    @Override
    public void run(){
        try {
            nodeGestOutput = new PrintStream(nodeGestConnector.getOutputStream());
            nodeGestInput = new BufferedReader(new InputStreamReader(nodeGestConnector.getInputStream()));
            
            System.out.println("NodeGest connected to Manager!");
            
            NodeGest ng = new NodeGest("1", nodeGestOutput);
            nodeGestList.add(ng);
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
            //Full SetInterval Format e.g.: SetInterval NG1 N2 S1
            if(request.startsWith("SetInterval")){
                request = request.substring(8);
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
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
            
            nodeGestConnector = server.accept();
            
            nodeGestOutput = new PrintStream(nodeGestConnector.getOutputStream());
            nodeGestInput = new BufferedReader(new InputStreamReader(nodeGestConnector.getInputStream()));
            
            NodeGest ng = new NodeGest("1", nodeGestOutput);
            nodeGestList.add(ng);
            
            Thread t = new Manager(nodeGestInput,nodeGestOutput);
            t.start();
            System.out.println("Manager Running...");
           
        } catch (IOException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

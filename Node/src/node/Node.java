package node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Node extends Thread{

    private Socket nodeGestConnector = null;
    private static String IP;
    private static String inputPort;
    private static String outputPort;
    private static String zone;
    
    private static Vector<Sensor> sensorList;   
    private PrintStream nodeGestOutput;             
    private static PrintStream sensorOutput;         
    private static BufferedReader sensorInput;
    
    private Node(Socket nodeGestConnector, PrintStream sensorOutput, PrintStream nodeGestOutput){
        this.nodeGestConnector = nodeGestConnector;
        this.sensorOutput = sensorOutput;
        this.nodeGestOutput = nodeGestOutput;
    }
    
    public static void main(String[] args) throws IOException {
       
        sensorOutput = null;
        sensorInput = null;
        
        IP = "193.137.106.155";
        inputPort = "1113";
        outputPort = "1112";
        zone = "1";
        
        Socket nodeGestConnection = new Socket(IP, Integer.parseInt(outputPort));
       
        final PrintStream nodeGestOutput = new PrintStream(nodeGestConnection.getOutputStream()); //Output NodeGest
        final BufferedReader entradaNG = new BufferedReader(new InputStreamReader(nodeGestConnection.getInputStream()));//Input Nodegest

        nodeGestOutput.println("Zone: " + zone);
        
        ServerSocket serverNode = new ServerSocket(Integer.parseInt(inputPort));
        
        System.out.println("Node connected to zone: " + zone + " | Port: " + inputPort);
        
        while (true) {

                Socket connectionNG = serverNode.accept();
                System.out.println("Server Running...");

                Thread ts = new Node(connectionNG, sensorOutput, nodeGestOutput);
                ts.start();
            }
    }
    
    public void run(){
        
        try {
            sensorOutput = new PrintStream(nodeGestConnector.getOutputStream());
            sensorInput = new BufferedReader(new InputStreamReader(nodeGestConnector.getInputStream()));
            
            String type = sensorInput.readLine();
            
            Sensor sensor = new Sensor(type, sensorOutput);
            //sensorList.add(sensor);
            //SensorList(sensorList);
            
            while(true){
                String sensorData = sensorInput.readLine();
                System.out.println("Node: " + sensorData);
                
                nodeGestOutput.println("Zone: " + zone + ":" + sensorData);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

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
    
    private static Vector<Sensor> sensorList = new Vector();   
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
        
        IP = "193.137.106.181";
        inputPort = "1113";
        outputPort = "1112";
        zone = "1";
        
        Socket nodeGestConnection = new Socket(IP, Integer.parseInt(outputPort));
       
        final PrintStream nodeGestOutput = new PrintStream(nodeGestConnection.getOutputStream()); //Output NodeGest
        final BufferedReader nodeGestInput = new BufferedReader(new InputStreamReader(nodeGestConnection.getInputStream()));//Input Nodegest

        nodeGestOutput.println("Zone: " + zone);
        
        ServerSocket serverNode = new ServerSocket(Integer.parseInt(inputPort));
        
        Thread t = new Node(nodeGestConnection, sensorOutput, nodeGestOutput){
          public void run(){
              while(true){
                  try {
                      String nodeGestData = nodeGestInput.readLine();
                      if(nodeGestData.startsWith("request")){
                          sensorOutput.println(nodeGestData);
                      }
                  } catch (IOException ex) {
                      Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
                  }
              }
          }  
        };
        
        System.out.println("Node connected to zone: " + zone + " | Port: " + inputPort);
        
        while (true) {

                Socket connection = serverNode.accept();
                System.out.println("NodeGest Connected to Node.");

                Thread ts = new Node(connection, sensorOutput, nodeGestOutput);
                ts.start();
            }
    }
    
    @Override
    public void run(){
        
        try {
            
            sensorOutput = new PrintStream(nodeGestConnector.getOutputStream());
            sensorInput = new BufferedReader(new InputStreamReader(nodeGestConnector.getInputStream()));
            
            String type = sensorInput.readLine();
            
            Sensor sensor = new Sensor(type, sensorOutput);
            sensorList.add(sensor);
            
            //SensorList(sensorList);
            System.out.println("Type no Nodo: " + type);
            
            sensorOutput.println("Request Temp");
            
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

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
/**
 *
 * @author bruno
 */
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
        
        IP = "193.137.106.212";
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
                        //RECEIVES MESSAGES FROM NODEGEST
                        String nodeGestData = nodeGestInput.readLine();
                        if(nodeGestData.startsWith("request")){
                            //SEND MESSAGES TO THE SENSOR
                            sensorOutput.println(nodeGestData);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }  
        };
        
        System.out.println("Node: Active \n== Zone: " + zone +" ==" + " \n== Listening in Port: " + inputPort + " ==" + "\nWaiting Sensor to connect...");
        
        while (true) {

                Socket connection = serverNode.accept();
                System.out.println("Sensor connected to Node: " + zone);

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
            
            //SEND MESSAGES TO THE SENSOR
            sensorOutput.println("Request Temp");
            
            while(true){
                //RECEIVES MESSAGES FROM THE SENSOR
                String sensorData = sensorInput.readLine();
                //TODO: decidir que codgo de mensagem vai passar ao NodeGest
                System.out.println("Node: " + sensorData);
                if(sensorData.startsWith("Node: "))
                    //SENDS MESSAGES TO NODEGEST
                    nodeGestOutput.println("Zone: " + zone + " :" + sensorData);
            }
        } catch (IOException ex) {
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void requestData(String request){
        System.out.println("Request: " + request);
        
        if(sensorOutput != null){
            //Full Request Format e.g.: Request S1
            if(request.startsWith("Request")){
                request = request.substring(8);
                if(request.startsWith("S")){
                    int element = Integer.parseInt(request.substring(1,2));
                    String type = sensorList.elementAt(element-1).type;
                    request = "Request " + type;
                    sensorList.elementAt(element-1).output.println(request);
                    System.out.println("Request para o Sensor: \n" + request);
                }
            }
            //Full SetInterval Format e.g.: SetInterval S1 m2
            if(request.startsWith("SetInterval")){
                request = request.substring(12);
                if(request.startsWith("S")){
                    int element = Integer.parseInt(request.substring(1,2));
                    request = request.substring(3);
                    request = "SetInterval " + request;
                    sensorList.elementAt(element-1).output.println(request);
                    System.out.println("SetInterval para o Sensor: \n" + request);
                }
            }
        }
    }
}

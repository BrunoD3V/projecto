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

    private final Socket nodeGestConnector = null;
    private Socket sensorConnector = null;
    private static String IP;
    private static String inputPort;
    private static String outputPort;
    private static String zone;
    
    private static final Vector<Sensor> sensorList = new Vector();   
    private final PrintStream nodeGestOutput;             
    private static PrintStream sensorOutput;         
    private static BufferedReader sensorInput;
    
    private Node(Socket sensorConnector, PrintStream nodeGestOutput){
        this.sensorConnector = sensorConnector;
        this.nodeGestOutput = nodeGestOutput;
    }
    
    public static void main(String[] args) throws IOException {
       
        sensorOutput = null;
        sensorInput = null;
        
        IP = args[0];
        inputPort = args[1];
        outputPort = args[2];
        zone = args[3];
        
        /*
        IP = "193.137.107.64";
        inputPort = "1113";
        outputPort = "1112";
        zone = "1";
        */
        Socket nodeGestConnection = new Socket(IP, Integer.parseInt(outputPort));
       
        final PrintStream nodeGestOutput = new PrintStream(nodeGestConnection.getOutputStream()); //Output NodeGest
        final BufferedReader nodeGestInput = new BufferedReader(new InputStreamReader(nodeGestConnection.getInputStream()));//Input Nodegest

        nodeGestOutput.println("Zone " + zone);
        
        ServerSocket serverNode = new ServerSocket(Integer.parseInt(inputPort));
        
        System.out.println("Node: Active \n== Zone: " + zone +" ==" + " \n== Listening in Port: " + inputPort + " ==" + "\nWaiting Sensor to connect...");
        
        //THREAD THAT LISTENS/SENDS MESSAGES FROM/TO NODEGEST
        Thread t = new Thread(){
            @Override
            public void run(){
                while(true){
                    try {
                        //Receives messages from NodeGest
                        String nodeGData = nodeGestInput.readLine();
                        System.out.println("NodeGestData: " + nodeGData);
                        
                        if(nodeGData.startsWith("Request") || nodeGData.startsWith("SetInterval")){
                            requestData(nodeGData);
                            System.out.println("REQUESTED!");
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        t.start();
        
        while (true) {
            //Acepts Sensor connection
            Socket sensorConnector = serverNode.accept();
            System.out.println("Sensor connected to Node: " + zone);

            Thread ts = new Node(sensorConnector, nodeGestOutput);
            ts.start();
        }
    }
    
    //THREAD THAT LISTENS/SENDS MESSAGES FROM/TO SENSOR
    @Override
    public void run(){
        try {
            sensorOutput = new PrintStream(sensorConnector.getOutputStream());
            sensorInput = new BufferedReader(new InputStreamReader(sensorConnector.getInputStream()));
            
            String type = sensorInput.readLine();
            Sensor sensor = new Sensor(type, sensorOutput);
            sensorList.add(sensor);
            String sensorID = "SensorID " + (sensorList.size());
            sensorOutput.println(sensorID);
            
            //SEND MESSAGES TO THE SENSOR
            //sensorOutput.println("Request Temp");
            
            while(true){
                //RECEIVES MESSAGES FROM THE SENSOR
                String sensorData = sensorInput.readLine();
                if(sensorData.startsWith("Response")){
                    sensorData = sensorData.substring(9);
                    if(sensorData.startsWith("SetInterval")){
                        sensorData = "Response " + sensorData;
                        nodeGestOutput.println(sensorData);
                        System.out.println("Message Sent to NodeGest: " + sensorData);
                    }else if(sensorData.startsWith("S")){
                        sensorData = "Response " + sensorData;
                        nodeGestOutput.println(sensorData);
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void requestData(String request){
        System.out.println("Request: " + request);
        
        if(sensorOutput != null){
            //Full Request Format e.g.: Request S1
            if(request.startsWith("Request")){
                request = request.substring(8);
                if(request.startsWith("S")){
                    int element = Integer.parseInt(request.substring(1,2));
                    String type = sensorList.elementAt(element-1).type;
                    //REQUEST FORMAT: Request S1 Temp
                    request = "Request S"+ element + " " + type;
                    if(sensorList.size() > 0 )
                        sensorList.elementAt(element-1).output.println(request);
                    System.out.println("Request para o Sensor: \n" + request);
                }
            }
            //Full SetInterval Format e.g.: SetInterval S1 m2
            if(request.startsWith("SetInterval")){
                request = request.substring(12);
                if(request.startsWith("S")){
                    int element = Integer.parseInt(request.substring(1,2));
                    request = "SetInterval " + request;
                    if(sensorList.size() > 0 )
                        sensorList.elementAt(element-1).output.println(request);
                    System.out.println("SetInterval para o Sensor: \n" + request);
                }
            }
        }
    }
}

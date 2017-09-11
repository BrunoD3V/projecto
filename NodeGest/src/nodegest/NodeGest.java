package nodegest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NodeGest extends Thread{

    private Socket nodeConnection = null;
    private static String IP;
    private static String inputPort;
    private static String outputPort;
    private static String sector;
    
    private static Vector<Node> nodeList;
    
    private static BufferedReader managerInput;         //Input Channel to Manager
    private static PrintStream managerOutput;         //Output Channel to Manager
    
    public static float TempMin = -20;
    public static float TempMax = 50;
    public static float HumiMin = 0;
    public static float HumiMax = 100;
    public static float RadiMin = (float) 0.0;
    public static float RadiMax = (float) 0.08;
   
    private NodeGest(Socket nodeConnection, PrintStream managerOutput) {
        this.nodeConnection = nodeConnection;
        this.managerOutput = managerOutput;
    }

    public static void setTempMin(float TempMin) {
        NodeGest.TempMin = TempMin;
    }

    public static void setTempMax(float TempMax) {
        NodeGest.TempMax = TempMax;
    }

    public static void setHumiMin(float HumiMin) {
        NodeGest.HumiMin = HumiMin;
    }

    public static void setHumiMax(float HumiMax) {
        NodeGest.HumiMax = HumiMax;
    }

    public static void setRadiMin(float RadiMin) {
        NodeGest.RadiMin = RadiMin;
    }

    public static void setRadiMax(float RadiMax) {
        NodeGest.RadiMax = RadiMax;
    }
    
    public static void main(String[] args) throws IOException {
        /*
        IP = "193.137.107.64";
        outputPort = "1111";
        inputPort = "1112";
        sector = "1";
        */
        
        IP = args[0];
        inputPort = args[1];
        outputPort = args[2];
        sector = args[3];
        
        nodeList = new Vector<Node>();
        
        Socket managerConnection = new Socket(IP, Integer.parseInt(outputPort));
        managerOutput = new PrintStream (managerConnection.getOutputStream());
        managerInput = new BufferedReader(new InputStreamReader(managerConnection.getInputStream()));
        //SENDS MESSAGES TO THE MANAGER
        managerOutput.println("Sector " + sector);
        
        //THREAD THAT LISTENS/SENDS MESSAGES FROM/TO MANAGER
        Thread t = new Thread(){
            @Override
            public void run(){
                try {
                    while(true){
                        String managerData = managerInput.readLine();
                        System.out.println("Manager Data: " + managerData);

                        //HANDLES RECEIVED MESSAGES
                        if(managerData.startsWith("Request") || managerData.startsWith("SetInterval")){
                            requestData(managerData);
                            System.out.println("REQUESTED!");
                        }
                    }
                } catch (IOException ex) {
                    System.out.println("ERROR: the Thread that handles Manager Channels failed with the following error: " + ex.getMessage());
                }
            }
        };
        t.start();

        //Initializes and starts the NodeGest Server
        ServerSocket nodeGestServer = new ServerSocket(Integer.parseInt(inputPort));
        System.out.println("NodeGest: Active \n== Sector: " + sector +" ==" + " \n== Listening in Port: " + inputPort + " ==" + "\nWaiting Node to connect...");
        
        //WAITS FOR NODES TO CONNECT AND PASSES THEM TO A THREAD
        while (true) {
            Socket nodeConnection = nodeGestServer.accept(); 
            System.out.println("Node Connected to NodeGest: " + sector);
            
            Thread ts = new NodeGest(nodeConnection, managerOutput);
            ts.start();
        }
    }
    
    //THREAD THAT LISTENS/SENDS MESSAGES FROM/TO NODE
    @Override
    public void run(){
        try{
            PrintStream nodeOutput = new PrintStream(nodeConnection.getOutputStream());
            BufferedReader nodeInput = new BufferedReader(new InputStreamReader(nodeConnection.getInputStream()));
            
            //HANDLES RECEIVED MESSAGES
            while(true){
                String nodeData = nodeInput.readLine();
                
                if(nodeData.startsWith("Zone")){
                    String zoneNumber = nodeData.substring(5);
                    Node n = new Node(zoneNumber, nodeOutput);
                    nodeList.add(n);
                    System.out.println("TESTE ZONA: " + nodeData);
                }
                //RESPONSES TO DATA REQUESTS
                if(nodeData.startsWith("Response")){
                    nodeData = nodeData.substring(9);
                    if(nodeData.startsWith("SetInterval")){
                        nodeData = "Response " + nodeData;
                        managerOutput.println(nodeData);
                    }
                    //RESPONSE FORMAT: Response S1 Temperature: 25
                    if(nodeData.startsWith("S")){
                        
                        String opt = nodeData.substring(3,4);
                        System.out.println("OPT: " + opt);
                        switch(opt){
                            case "T":
                                float temp = Float.parseFloat(nodeData.substring(16));
                                if(outOfBoundary(TempMin,TempMax,temp))
                                    sendAlert(nodeData);
                                nodeData = "Response " + nodeData;
                                managerOutput.println(nodeData);
                                break;
                            case "R":
                                float radi = Float.parseFloat(nodeData.substring(16));
                                if(outOfBoundary(RadiMin,RadiMax,radi))
                                    sendAlert(nodeData);
                                nodeData = "Response " + nodeData;
                                managerOutput.println(nodeData);
                                break;
                            case "H":
                                float humi = Float.parseFloat(nodeData.substring(16));
                                if(outOfBoundary(HumiMin,HumiMax,humi))
                                    sendAlert(nodeData);
                                nodeData = "Response " + nodeData;
                                managerOutput.println(nodeData);
                                break;
                            default: 
                                System.out.println("Bad Response");
                        }
                        
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(NodeGest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void requestData(String request){
        System.out.println("Request: " + request);
        //Full Request Format e.g.: Request N2 S1
        if(request.startsWith("Request")){
            request = request.substring(8);
            if(request.startsWith("N")){
                int element = Integer.parseInt(request.substring(1,2));
                request = request.substring(3);
                request = "Request " + request;
                if(nodeList.size() > 0 )
                    nodeList.elementAt(element-1).output.println(request);
                System.out.println("Request para o Node: \n" + request);
            }
        }
        //Full SetInterval Format e.g.: SetInterval N2 S1 m2
        if(request.startsWith("SetInterval")){
            request = request.substring(12);
            if(request.startsWith("N")){
                int element = Integer.parseInt(request.substring(1,2));
                request = request.substring(3);
                request = "SetInterval " + request;
                if(nodeList.size() > 0 )
                    nodeList.elementAt(element-1).output.println(request);
                System.out.println("SetInterval para o Node: \n" + request);
            }
        }
    }
    
    public static boolean outOfBoundary(float min, float max, float value){
        if(value < min || value > max)
            return true;
        return false;
    }
    
    public static void sendAlert(String alert){
        managerOutput.println("ALERT " + alert);
    }
}
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
    
    private static PrintStream managerOutput;         //Output Channel to Manager
    private static PrintStream nodeOutput;       //Output Channel to Node

    public static float TempMin = -20;
    public static float TempMax = 50;
    public static float HumiMin = 0;
    public static float HumiMax = 100;
    public static float RadiMin = (float) 0.0;
    public static float RadiMax = (float) 0.08;
    
    private NodeGest(Socket nodeConnection, PrintStream nodeOutput, PrintStream managerOutput) {

        this.nodeConnection = nodeConnection;
        this.nodeOutput = nodeOutput;
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
        
        nodeOutput = null;
        IP = "193.137.106.212";
        outputPort = "1111";
        inputPort = "1112";
        sector = "1";
        
        nodeList = new Vector<Node>();
        
        Socket managerConnection = new Socket(IP, Integer.parseInt(outputPort));
        final PrintStream managerOutput = new PrintStream (managerConnection.getOutputStream());
        final BufferedReader managerInput = new BufferedReader(new InputStreamReader(managerConnection.getInputStream()));
        
        //SENDS MESSAGES TO THE MANAGER
        managerOutput.println("Sector: " + sector);
        
        ServerSocket nodeGestServer = new ServerSocket(Integer.parseInt(inputPort));
        System.out.println("NodeGest: Active \n== Sector: " + sector +" ==" + " \n== Listening in Port: " + inputPort + " ==" + "\nWaiting Node to connect...");
        
        while (true) {
            Socket nodeConnection = nodeGestServer.accept(); 
            System.out.println("Node Connected to NodeGest: " + sector);

            Thread ts = new NodeGest(nodeConnection, nodeOutput, managerOutput);
            ts.start();
        }
    }
    
    @Override
    public void run(){
        try{
            nodeOutput = new PrintStream(nodeConnection.getOutputStream());
            BufferedReader nodeInput = new BufferedReader(new InputStreamReader(nodeConnection.getInputStream()));
            
            String nodeData = nodeInput.readLine();
            
            String zone = nodeData;
            Node n = new Node(zone, nodeOutput);
            nodeList.add(n);
            //NodeList(nodeList);
            
            //SENDS MESSAGES TO THE MANAGER
            managerOutput.println(nodeData);
            //SENDS MESSAGES TO THE NODE
            nodeOutput.println("Request Temp");
            
            while(true){
                //RECEIVES MESSAGES FROM NODE
                String data = nodeInput.readLine();
                System.out.println("Node message: " + data);
                //SENDS MESSAGES TO THE MANAGER
                managerOutput.println("Sector: " + sector + " " + data);
                
                //String newData = data.substring(8);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(NodeGest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
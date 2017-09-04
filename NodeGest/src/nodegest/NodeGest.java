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
    
    private static PrintStream managerOutput;         //canal de saida para o Gestor principal
    private static PrintStream nodeGestOutput;       //canal de saida para o nodo

    public static float TempMin = -20;
    public static float TempMax = 50;
    public static float HumiMin = 0;
    public static float HumiMax = 100;
    public static float RadiMin = (float) 0.0;
    public static float RadiMax = (float) 0.08;
    
    private NodeGest(Socket nodeConnection, PrintStream nodeGestOutput, PrintStream managerOutput) {

        this.nodeConnection = nodeConnection;
        this.nodeGestOutput = nodeGestOutput;
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
        
        nodeGestOutput = null;
        IP = "193.137.106.155";
        outputPort = "1111";
        inputPort = "1112";
        sector = "14";
        
        nodeList = new Vector<Node>();
        
        Socket managerConnection = new Socket(IP, Integer.parseInt(outputPort));
        final PrintStream managerOutput = new PrintStream (managerConnection.getOutputStream());
        final BufferedReader managerInput = new BufferedReader(new InputStreamReader(managerConnection.getInputStream()));
        
        managerOutput.println("Sector: " + sector);
        
        ServerSocket nodeGestServer = new ServerSocket(Integer.parseInt(inputPort));
        System.out.println("NodeGest connected to sector: " + sector + "in Port: " + inputPort);
        
        while (true) {

                Socket nodeConnection = nodeGestServer.accept(); //aceita ligaçoes do Nodo
                System.out.println("Connected to Node");

                Thread ts = new NodeGest(nodeConnection, nodeGestOutput, managerOutput);//passa a ligaçao para uma Thread
                ts.start();
            }
    }
    
    @Override
    public void run(){
        
        try{
            nodeGestOutput = new PrintStream(nodeConnection.getOutputStream());
            BufferedReader nodeInput = new BufferedReader(new InputStreamReader(nodeConnection.getInputStream()));
            
            String nodeData = nodeInput.readLine();
            
            managerOutput.println(nodeData);
            
            String zone = nodeData;
            Node n = new Node(zone, nodeGestOutput);
            //nodeList.add(n);
            //NodeList(nodeList);
            
            while(true){
                String data = nodeInput.readLine();
                System.out.println("Node message: " + data);
                managerOutput.println("Sector: " + sector + " " + data);
                
                //String newData = data.substring(8);
            
            }
            
        } catch (IOException ex) {
            Logger.getLogger(NodeGest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

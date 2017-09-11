package manager;

import java.io.BufferedReader;
import java.io.File;
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
    
    //CONSOLE INTERFACE VARIABLES
    //Console Input Reader
    private static BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
    private static String consoleInput;
    private static String statusSector1 = "No Problems Detected.";
    private static String statusSector2 = "No Problems Detected.";
    
    //Constructor
    private Manager(BufferedReader nodeGestInput, PrintStream nodeGestOutput){
        Manager.nodeGestInput = nodeGestInput;
        Manager.nodeGestOutput = nodeGestOutput;
    }
   
    public static void main(String[] args) {
        Thread consoleThread = new Thread(){
            @Override
            public void run(){
                while(true){
                    System.out.println("=========MANAGER MENU=========");
                    System.out.println("Sector 1: " + statusSector1);
                    System.out.println("Sector 2: " + statusSector2);
                    System.out.println("Request Data (1)");
                    System.out.println("Set Interval (2)");
                    try {
                        System.out.println("Option: ");
                        consoleInput = "";
                        consoleInput = consoleReader.readLine();

                        switch(consoleInput){
                            case "1":
                                System.out.println("=========REQUEST DATA=========");
                                String request = "Request ";
                                System.out.println("Sector:");
                                consoleInput = consoleReader.readLine();
                                request = request + "NG" + consoleInput + " ";
                                System.out.println("Zone: ");
                                consoleInput = consoleReader.readLine();
                                request = request + "N" + consoleInput + " ";
                                System.out.println("Sensor: ");
                                consoleInput = consoleReader.readLine();
                                request = request + "S" + consoleInput;
                                
                                System.out.println("RESQUEST: " + request);
                                //Request
                                requestData(request);
                                request = "";
                                System.out.println("AFTER " + request);
                                break;
                                
                            case "2":
                                System.out.println("=========SET INTERVAL=========");
                                String requestSet = "Request SetInterval ";
                                System.out.println("Sector:");
                                consoleInput = consoleReader.readLine();
                                requestSet = requestSet + "NG" + consoleInput + " ";
                                System.out.println("Zone: ");
                                consoleInput = consoleReader.readLine();
                                requestSet = requestSet + "N" + consoleInput + " ";
                                System.out.println("Sensor: ");
                                consoleInput = consoleReader.readLine();
                                requestSet = requestSet + "S" + consoleInput + " ";
                                System.out.println("Interval(minutes): ");
                                consoleInput = consoleReader.readLine();
                                requestSet = requestSet + "m" + consoleInput;
                                System.out.println("RESQUEST: " + requestSet);
                                //Request
                                requestData(requestSet);
                                requestSet = "";
                                System.out.println("AFTER" + requestSet);
                                break;
                            default:
                                System.out.println("Please insert a valid option.");
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        
        try{
            server = new ServerSocket(1111);
            nodeGestList = new Vector<NodeGest>();
            
            Runtime.getRuntime().exec("cmd /c sector1.bat", null, new File("C:\\"));
            
            while(true){
                nodeGestConnector = server.accept();
                System.out.println("Manager is Running...");
                consoleThread.start();
                
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

            //Waits messages from nodeGest.
            while(true){
                String nodeGestData = nodeGestInput.readLine();
                //TRATAR DADOS DO NODEGEST
                //NEW NODEGEST ADDED TO NODEGEST LIST
                if(nodeGestData.startsWith("Sector")){
                    String sectorNumber = nodeGestData.substring(7);
                    NodeGest ng = new NodeGest(sectorNumber, nodeGestOutput);
                    nodeGestList.add(ng);
                }
                //RESPONSES TO DATA REQUESTS
                if(nodeGestData.startsWith("Response")){
                    System.out.println(nodeGestData);
                }
                if(nodeGestData.startsWith("ALERT")){
                    System.out.println(nodeGestData);
                }
            }    
        } catch (IOException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void requestData(String request){
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
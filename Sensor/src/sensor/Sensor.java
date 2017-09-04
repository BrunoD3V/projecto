package sensor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import  java.net.Socket;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sensor extends Thread {

    //Connection Variables
    private Socket connection = null;
    private static PrintStream nodeOutput;
    private static BufferedReader nodeInput;
    
    private static String IP;
    private static String PORT;

    //Sensor Properties
    private static String TYPE;
    
    
    //Temperature between -10Cº - 45Cº
    private static float minT = -10.0f;
    private static float maxT = 45.0f;
    //Humidity between 0% - 100%
    private static float minHu = 0.0f;
    private static float maxHu = 100.0f;
    //Solar Radiation between
    private static float minSRad = 0.3f;
    private static float maxSRad = 4.0f;

    //Connection Constructor
    private Sensor (Socket connection){
        this.connection = connection;
    }

    private static float getTemp( float minT, float maxT ){
        Random rand = new Random();
        return rand.nextFloat() * (maxT - minT) + minT;
    }

    private static float getHumidity ( float minHu, float maxHu ){
        Random rand = new Random();
        return rand.nextFloat() * (maxHu - minHu) - minHu;
    }

    private static float getSolarRad ( float minSRad, float maxSRad ){
        Random rand = new Random();
        return rand.nextFloat() * (maxSRad - minSRad) - minSRad;
    }

    public static void setMinT(float minT) {
        Sensor.minT = minT;
    }

    public static void setMaxT(float maxT) {
        Sensor.maxT = maxT;
    }

    public static void setMinHu(float minHu) {
        Sensor.minHu = minHu;
    }

    public static void setMaxHu(float maxHu) {
        Sensor.maxHu = maxHu;
    }

    public static void setMinSRad(float minSRad) {
        Sensor.minSRad = minSRad;
    }

    public static void setMaxSRad(float maxSRad) {
        Sensor.maxSRad = maxSRad;
    }
    
    public static void main(String[] args) throws IOException {
        
        IP = "193.137.106.155";
        PORT = "1113";
        TYPE = "Temp";
        
        Socket nodeConnector = new Socket(IP, Integer.parseInt(PORT));
        System.out.println("Sensor type: " + TYPE + " ON!");
        
        //Output Channel 
        nodeOutput = new PrintStream(nodeConnector.getOutputStream());
        //Input Channel
        nodeInput  = new BufferedReader(new InputStreamReader(nodeConnector.getInputStream()));
        
        nodeOutput.println("TYPE");
        System.out.println("TYPE: " + TYPE);
        
        Thread t = new Sensor(nodeConnector){
            public void run(){
                try{
                    while(true){
                        String nodeData = nodeInput.readLine();
                        System.out.println("Node data received: " + nodeData);
                        
                        
                    }
                }catch(IOException ex){
                    Logger.getLogger(Sensor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        t.start();
    }
}
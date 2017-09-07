package sensor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import  java.net.Socket;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author bruno
 */
public class Sensor extends Thread {

    //Connection Variables
    private Socket connection = null;
    private static PrintStream nodeOutput;
    private static BufferedReader nodeInput;
    private static int interval = 1; 
    
    private static String IP;
    private static String PORT;
    
    public static String TYPE;

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

    public static void setInterval(int interval) {
        Sensor.interval = interval;
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
        /*
        IP = args[0];
        PORT = args[1];
        TYPE = args[2];
        */
        
        IP = "192.168.1.5";
        PORT = "1113";
        TYPE = "Temp";
        
        Socket nodeConnector = new Socket(IP, Integer.parseInt(PORT));
        System.out.println("Sensor type: " + TYPE + " ON!");
        
        //Output Channel 
        nodeOutput = new PrintStream(nodeConnector.getOutputStream());
        //Input Channel
        nodeInput  = new BufferedReader(new InputStreamReader(nodeConnector.getInputStream()));
        
        nodeOutput.println(TYPE);
        
        //THREAD THAT LISTENS/SENDS MESSAGES FROM/TO NODE
        Thread t = new Sensor(nodeConnector){
            @Override
            public void run(){
                try{
                    while(true){
                        //RECEBE MENSAGENS DO NODO
                        String nodeData = nodeInput.readLine();
                        System.out.println("Node data received: " + nodeData);
                        new Timer().schedule(new TimerTask(){
                            @Override
                            public void run() {
                                RequestSensorData(Sensor.TYPE);
                            }
                        },1000*60*interval,1000*60*interval); 
                        
                        //REQUEST FORMAT: Request S1 Temp
                        if(nodeData.startsWith("Request")){
                            nodeData = nodeData.substring(8);
                            RequestSensorData(nodeData);
                        }
                        //Full SetInterval Format e.g.: SetInterval S1 m2
                        if(nodeData.startsWith("SetInterval")){
                            String message = nodeData;
                            nodeData = nodeData.substring(15);
                            if(nodeData.startsWith("m")){
                                setInterval(Integer.parseInt(nodeData.substring(1,2)));
                                nodeOutput.println("Response " + message);
                                System.out.println("Interval set to: " + nodeData.substring(1,2));
                            }
                        }
                    }
                }catch(IOException ex){
                    Logger.getLogger(Sensor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        t.start();
    }
    
    public void RequestSensorData(String opt) {
        float temperature, radiation, humidity;
        String sensorNumber = opt.substring(0,2);
        opt = opt.substring(3);
        switch (opt) {
            case "Temp": 
                if(TYPE.equalsIgnoreCase("Temp")){
                    temperature = getTemp(minT,maxT);
                    //RESPONSE FORMAT: Response S1 Temperature: 25
                    nodeOutput.println("Response " + sensorNumber + " Temperature: " + temperature);
                    System.out.println("RESPONDED");
                }
                break;
            case "Humi": 
               if(TYPE.equalsIgnoreCase("Humi")){
                    humidity = getHumidity(minHu, maxHu);
                    nodeOutput.println("Response " + sensorNumber + " Humidity: " + humidity);
                }
                break;
            case "Radi": 
                if(TYPE.equalsIgnoreCase("Radi")){
                    radiation = getSolarRad(minSRad,maxSRad);
                    nodeOutput.println("Response " + sensorNumber + " Radiation: " + radiation);
                }
                break;
            default:
                nodeOutput.println("Error requesting sensor data.");
        }
    }
}
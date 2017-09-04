import  java.net.Socket;
import java.util.Random;

public class Sensor extends Thread {

    //Connection Variables
    private Socket connection = null;
    private static String IP;
    private static String PORT;

    //Sensor Properties
    private static String STYPE;
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

    public static void main(String[] args) {
        System.out.println(getHumidity(minHu,maxHu));
        System.out.println(getTemp(minT,maxT));
        System.out.println(getSolarRad(minSRad,maxSRad));
    }
}
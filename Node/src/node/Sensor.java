package node;

import java.io.PrintStream;

public class Sensor {
    
    public String type;
    public final PrintStream output;
    
    public Sensor(String type, PrintStream output){
        this.type = type;
        this.output = output;
    }
}

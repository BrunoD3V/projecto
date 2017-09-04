package node;

import java.io.PrintStream;

public class Sensor {
    
    private String type;
    private final PrintStream output;
    
    public Sensor(String type, PrintStream output){
        this.type = type;
        this.output = output;
    }
}

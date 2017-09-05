/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodegest;

import java.io.PrintStream;

/**
 *
 * @author bruno
 */
public class Node {
    
    public String zone;
    public PrintStream output;

    public Node(String zone, PrintStream nodeOutput){

        this.zone = zone;
        this.output = nodeOutput;
    }
}

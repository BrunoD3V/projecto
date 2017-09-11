/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import java.io.PrintStream;


public class NodeGest {
    
    public String sector;
    public PrintStream output; 

    public NodeGest(String sector, PrintStream output) {

        this.sector = sector;
        this.output = output;
    }
}

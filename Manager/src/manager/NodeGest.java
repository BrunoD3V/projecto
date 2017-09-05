/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import java.io.PrintStream;

/**
 *
 * @author bruno
 */
public class NodeGest {
    
    private String sector;
    public PrintStream output;  //SABADO 

    public NodeGest(String sector, PrintStream output) {

        this.sector = sector;
        this.output = output;
    }
}

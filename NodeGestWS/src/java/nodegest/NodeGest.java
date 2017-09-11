/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodegest;


public class NodeGest {
    
    private String sector;

    //Construtor
    public NodeGest(int idNodeGest, String sector) {
        this.sector = sector;
    }
    
    public NodeGest(){
        this.sector = "";
    }
    
    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
}

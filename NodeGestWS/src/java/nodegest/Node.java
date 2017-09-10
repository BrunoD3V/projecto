/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodegest;

/**
 *
 * @author bruno
 */
public class Node {
    
    private int idNode;
    private String zona;
    

    public Node(int idNode, String zona) {
        this.idNode = idNode;
        this.zona = zona;
    }
    
    public Node(){
        
    }
    
    public int getIdNode() {
        return idNode;
    }

    public void setIdNode(int idNode) {
        this.idNode = idNode;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }
}

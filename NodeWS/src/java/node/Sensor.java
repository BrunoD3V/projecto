/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node;

/**
 *
 * @author bruno
 */
public class Sensor {
    
    private String idNode;
    private int intervalo;
    private String tipo;

    public Sensor(String idNode, int intervalo, String tipo) {
        this.idNode = idNode;
        this.intervalo = intervalo;
        this.tipo = tipo;
    }

    public Sensor() {
    }

    public String getIdNode() {
        return idNode;
    }

    public void setIdNode(String idNode) {
        this.idNode = idNode;
    }

    public int getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(int intervalo) {
        this.intervalo = intervalo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

package node;


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

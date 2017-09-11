package manager;

public class Node {
    
    private String nodeGestSector;
    private String zona;
    
    public Node(String nodeGestSector, String zona) {
        this.nodeGestSector = nodeGestSector;
        this.zona = zona;
    }
    
    public Node(){
        
    }

    public String getNodeGestSector() {
        return nodeGestSector;
    }

    public void setNodeGestSector(String nodeGestSector) {
        this.nodeGestSector = nodeGestSector;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }
}
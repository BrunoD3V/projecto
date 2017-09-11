/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodegest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import node.NodeWS;
import node.NodeWS_Service;


public class GereNodeGest {
    
    public boolean inserirNodeGest(String sector){
        
        if(this.pesquisarNodeGest(sector)== null){
            try {
            Connection connection = NodeGestDB.getConnection();
            
            String query = "INSERT INTO nodegest VALUES (?)";
            PreparedStatement ppStmt = connection.prepareStatement(query);
            ppStmt.setString(1, sector);
            
            ppStmt.executeUpdate();
            
            connection.close();
            
            } catch (SQLException ex) {
                Logger.getLogger(GereNodeGest.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        
        return true;
    }
    
    public String pesquisarNodeGest(String sector){
        NodeGest nodegest = null;
        try {
            Connection connection = NodeGestDB.getConnection();
  
            String query = "SELECT * FROM nodegest WHERE sector = ?";
            PreparedStatement ppStmt = connection.prepareStatement(query);
            ppStmt.setString(1, sector);
            
            ResultSet rSet = ppStmt.executeQuery();
            if(rSet.next()){
                nodegest = new NodeGest();
                
                nodegest.setSector(rSet.getString("sector"));
               
            }else{
                return nodegest.toString();
            }
            connection.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(GereNodeGest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return nodegest.toString();
    }
    
    //METODOS PARA INTRODUZIR NODES E SENSORES 
    public boolean inserirNode(String idNodeGest, String zona){
        
        NodeWS_Service nodeService = new NodeWS_Service();
        NodeWS nodeWS = nodeService.getNodeWSPort();
        if(!nodeWS.inserirNode(idNodeGest, zona))
            return false;
        
        return true;
    }
    
    public boolean inserirSensor(String idNode, int intervalo, String tipo){
        
        NodeWS_Service nodeService = new NodeWS_Service();
        NodeWS nodeWS = nodeService.getNodeWSPort();
        if(!nodeWS.inserirSensor(idNode, intervalo, tipo))
            return false;
        return true;
    }
    
    //PEDIDOS
    public String pedirDadosSensor (String sector, String zona, String tipo){
        //SOAP
        NodeWS_Service nodeService = new NodeWS_Service();
        NodeWS nodeWS = nodeService.getNodeWSPort();
        
        return nodeWS.pedirDadosSensor(sector, zona, tipo);
    }
    
    //DEVERÁ DEFINIR O INTERVALO DE TEMPO QUE RECEBE (LÊ) DADOS DE TODA A ZONA (TODOS OS SENSORES)
    public String definirIntervaloSensor (String sector, String zona, String tipo, int valor){
        
        NodeWS_Service nodeService = new NodeWS_Service();
        NodeWS nodeWS = nodeService.getNodeWSPort();
        
        return nodeWS.definirIntervaloSensor(sector, zona, tipo, valor);
    }
}
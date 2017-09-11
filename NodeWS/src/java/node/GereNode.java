/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sensor.SensorWS;
import sensor.SensorWS_Service;


public class GereNode {
    
    public boolean inserirNode(String nodeGestSector, String zona){
        
        if(this.pesquisarNode(zona)== null){
            try {
            Connection connection = NodeDB.getConnection();

            //O VALOR NULL É CORRESPONDENTE AO ID AUTOMATICO DA DATABASE
            String query = "INSERT INTO node VALUES (?, ?)";
            PreparedStatement ppStmt = connection.prepareStatement(query);
            ppStmt.setString(1, nodeGestSector);
            ppStmt.setString(2, zona);
            
            ppStmt.executeUpdate();
            
            connection.close();
            
            } catch (SQLException ex) {
                Logger.getLogger(GereNode.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        
        return true;
    }
    
    public Node pesquisarNode(String zona){
        Node node = null;
        try {
            Connection connection = NodeDB.getConnection();
  
            String query = "SELECT * FROM node WHERE zona = ?";
            PreparedStatement ppStmt = connection.prepareStatement(query);
            ppStmt.setString(1, zona);
            
            ResultSet rSet = ppStmt.executeQuery();
            if(rSet.next()){
                node = new Node();
                
                node.setZona(rSet.getString("zona"));
               
            }else{
                return node;
            }
            connection.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(GereNode.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return node;
    }
    
    public boolean inserirSensor(String idNode, int intervalo, String tipo){
        
        SensorWS_Service sensorService = new SensorWS_Service();
        SensorWS sensorWS = sensorService.getSensorWSPort();
        if(!sensorWS.inserirNode(idNode, intervalo, tipo))
            return false;      
        return true;
    }
    
    //PEDIDOS
    public String pedirDadosSensor (String sector, String zona, String tipo){
        //SOAP
        SensorWS_Service sensorService = new SensorWS_Service();
        SensorWS sensorWS = sensorService.getSensorWSPort();
        
        return sensorWS.pedirDadosSensor(sector, zona, tipo);
    }
    
    //DEVERÁ DEFINIR O INTERVALO DE TEMPO QUE RECEBE (LÊ) DADOS DE TODA A ZONA (TODOS OS SENSORES)
    public String definirIntervaloSensor (String sector, String zona, String tipo, int valor){
        SensorWS_Service sensorService = new SensorWS_Service();
        SensorWS sensorWS = sensorService.getSensorWSPort();
        
        return sensorWS.definirIntervaloSensor(sector, zona, tipo, valor);
    }
}

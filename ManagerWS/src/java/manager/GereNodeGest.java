/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bruno
 */
public class GereNodeGest {
    
    public boolean inserirNodeGest(String sector){
        
        if(this.pesquisarNodeGest(sector)== null){
            try {
            Connection connection = ManagerDB.getConnection();
  
            //O VALOR NULL É CORRESPONDENTE AO ID AUTOMATICO DA DATABASE
            String query = "INSERT INTO nodegest VALUES (null, ?)";
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
    
    public ArrayList<NodeGest> listarNodeGest(){
        ArrayList<NodeGest> lista = new ArrayList<>();
        try {
            Connection connection = ManagerDB.getConnection();
  
            String query = "SELECT * FROM nodegest";
            PreparedStatement ppStmt = connection.prepareStatement(query);
            
            ResultSet rSet = ppStmt.executeQuery();
            while(rSet.next()){
                NodeGest nodegest = new NodeGest();
                
                nodegest.setSector(rSet.getString("sector"));
                
                lista.add(nodegest);
            }
            connection.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(GereNodeGest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
    
    public NodeGest pesquisarNodeGest(String sector){
        NodeGest nodegest = null;
        try {
            Connection connection = ManagerDB.getConnection();
  
            String query = "SELECT * FROM nodegest WHERE sector = ?";
            PreparedStatement ppStmt = connection.prepareStatement(query);
            ppStmt.setString(1, sector);
            
            ResultSet rSet = ppStmt.executeQuery();
            if(rSet.next()){
                nodegest = new NodeGest();
                
                nodegest.setSector(rSet.getString("sector"));
               
            }else{
                return nodegest;
            }
            connection.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(GereNodeGest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return nodegest;
    }
    
    //METODOS PARA INTRODUZIR NODES E SENSORES 
    public boolean inserirNode(String idNodeGest, String zona){
        //TERÁ DE COMUNICAR COM O NODEGEST -> NODE
        return true;
    }
    
    public boolean inserirSensor(String idNode, int intervalo, String tipo){
        //TERÁ QUE COMUNICAR COM O NODEGEST -> NODE -> SENSOR
        return true;
    }
    
    //PEDIDOS
    public String pedirDadosSensor (String sector, String zona){
        //TERÁ QUE COMUNICAR COM O NODEGEST -> NODE -> SENSOR
        return "";
    }
    
    //DEVERÁ DEFINIR O INTERVALO DE TEMPO QUE RECEBE (LÊ) DADOS DE TODA A ZONA (TODOS OS SENSORES)
    public String definirIntervaloSensor (String sector, String zona){
        //TERÁ QUE COMUNICAR COM O NODEGEST -> NODE -> SENSOR
        return "";
    }
} 
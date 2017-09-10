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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bruno
 */
public class GereNode {
    
    public boolean inserirNodeGest(float TempMin,float TempMax,float HumiMin,float HumiMax,float RadiMin,float RadiMax, String sector){
        
        if(this.pesquisarNodeGest(sector)== null){
            try {
            Connection connection = NodeGestDB.getConnection();
  
            //O VALOR NULL É CORRESPONDENTE AO ID AUTOMATICO DA DATABASE
            String query = "INSERT INTO nodegest VALUES (null, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ppStmt = connection.prepareStatement(query);
            ppStmt.setFloat(1, TempMin);
            ppStmt.setFloat(2, TempMax);
            ppStmt.setFloat(3, HumiMin);
            ppStmt.setFloat(4, HumiMax);
            ppStmt.setFloat(5, RadiMin);
            ppStmt.setFloat(6, RadiMax);
            ppStmt.setString(7, sector);
                        
            ppStmt.executeUpdate();
            
            connection.close();
            
            } catch (SQLException ex) {
                Logger.getLogger(GereNode.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            
        }
        
        return true;
    }
    
    public ArrayList<Node> listarNodeGest(){
        ArrayList<Node> lista = new ArrayList<>();
        try {
            Connection connection = NodeGestDB.getConnection();
  
            String query = "SELECT * FROM nodegest";
            PreparedStatement ppStmt = connection.prepareStatement(query);
            
            ResultSet rSet = ppStmt.executeQuery();
            while(rSet.next()){
                Node node = new Node();
                
                node.setTempMin(rSet.getFloat("tempmin"));
                node.setTempMax(rSet.getFloat("tempmax"));
                node.setHumiMin(rSet.getFloat("humimin"));
                node.setHumiMax(rSet.getFloat("humimax"));
                node.setRadiMin(rSet.getFloat("radimin"));
                node.setRadiMax(rSet.getFloat("radimax"));
                node.setSector(rSet.getString("sector"));
                
                lista.add(node);
            }
            connection.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(GereNode.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
    
    public Node pesquisarNodeGest(String sector){
        Node node = null;
        try {
            Connection connection = NodeGestDB.getConnection();
  
            String query = "SELECT * FROM nodegest WHERE sector = ?";
            PreparedStatement ppStmt = connection.prepareStatement(query);
            ppStmt.setString(1, sector);
            
            ResultSet rSet = ppStmt.executeQuery();
            if(rSet.next()){
                node = new Node();
                
                node.setTempMin(rSet.getFloat("tempmin"));
                node.setTempMax(rSet.getFloat("tempmax"));
                node.setHumiMin(rSet.getFloat("humimin"));
                node.setHumiMax(rSet.getFloat("humimax"));
                node.setRadiMin(rSet.getFloat("radimin"));
                node.setRadiMax(rSet.getFloat("radimax"));
                node.setSector(rSet.getString("sector"));
               
            }else{
                return node;
            }
            connection.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(GereNodeGest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return node;
    }
    
    public void enviarMensagemNode(){
        
    }
    
}

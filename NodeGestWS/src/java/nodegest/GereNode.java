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
    
    public boolean inserirNode(int idNodeGest, String zona){
        
        if(this.pesquisarNode(zona)== null){
            try {
            Connection connection = NodeGestDB.getConnection();
  
            //O VALOR NULL É CORRESPONDENTE AO ID AUTOMATICO DA DATABASE
            String query = "INSERT INTO node VALUES (null, ?, ?)";
            PreparedStatement ppStmt = connection.prepareStatement(query);
            ppStmt.setInt(1, idNodeGest);
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
    
    public ArrayList<Node> listarNode(){
        ArrayList<Node> lista = new ArrayList<>();
        try {
            Connection connection = NodeGestDB.getConnection();
  
            String query = "SELECT * FROM node";
            PreparedStatement ppStmt = connection.prepareStatement(query);
            
            ResultSet rSet = ppStmt.executeQuery();
            while(rSet.next()){
                Node node = new Node();
                
                node.setZona(rSet.getString("zona"));
                node.setIdNode(rSet.getInt("id"));
                
                
                lista.add(node);
            }
            connection.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(GereNode.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
    
    public Node pesquisarNode(String zona){
        Node node = null;
        try {
            Connection connection = NodeGestDB.getConnection();
  
            String query = "SELECT * FROM node WHERE zona = ?";
            PreparedStatement ppStmt = connection.prepareStatement(query);
            ppStmt.setString(1, zona);
            
            ResultSet rSet = ppStmt.executeQuery();
            if(rSet.next()){
                node = new Node();
                
                node.setZona(rSet.getString("zona"));
                node.setIdNode(rSet.getInt("id"));
               
            }else{
                return node;
            }
            connection.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(GereNode.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return node;
    } 
    //PEDIDOS
    public String pedirDadosSensor (String sector, String zona){
        return "";
    }
    
    //DEVERÁ DEFINIR O INTERVALO DE TEMPO QUE RECEBE (LÊ) DADOS DE TODA A ZONA (TODOS OS SENSORES)
    public String definirIntervalo (String sector, String zona){
        return "";
    }
}
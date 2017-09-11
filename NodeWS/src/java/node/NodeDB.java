/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class NodeDB {
    
    //TODO CRIAR E INSERIR BASE DE DADOS NO URL
    private static final String URL = "jdbc:mysql://localhost/dbsd";
    private static final String USR = "root";
    private static final String PASS = "";
    
    
    public static Connection getConnection() throws SQLException{
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch(ClassNotFoundException e){
            System.out.println("Erro no Driver");
        }
        
        return DriverManager.getConnection(URL, USR, PASS);
    }
}

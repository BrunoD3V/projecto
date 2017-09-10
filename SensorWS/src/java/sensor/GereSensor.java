/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bruno
 */
public class GereSensor {
    
    
    public boolean inserirSensor(String idNodo, int intervalo, String tipo){
        
        if(this.pesquisarSensor(tipo)== null){
            try {
            Connection connection = SensorDB.getConnection();

            //O VALOR NULL É CORRESPONDENTE AO ID AUTOMATICO DA DATABASE
            String query = "INSERT INTO node VALUES (?, ?. ?)";
            PreparedStatement ppStmt = connection.prepareStatement(query);
            ppStmt.setString(1, idNodo);
            ppStmt.setInt(2, intervalo);
            ppStmt.setString(3, tipo);
            
            ppStmt.executeUpdate();
            
            connection.close();
            
            } catch (SQLException ex) {
                Logger.getLogger(GereSensor.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        
        return true;
    }
    
    public Sensor pesquisarSensor(String tipo){
        Sensor sensor = null;
        try {
            Connection connection = SensorDB.getConnection();
  
            String query = "SELECT * FROM node WHERE tipo = ?";
            PreparedStatement ppStmt = connection.prepareStatement(query);
            ppStmt.setString(1, tipo);
            
            ResultSet rSet = ppStmt.executeQuery();
            if(rSet.next()){
                sensor = new Sensor();
                
                sensor.setTipo(rSet.getString("tipo"));
               
            }else{
                return sensor;
            }
            connection.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(GereSensor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return sensor;
    }
       
    //PEDIDOS
    public String pedirDadosSensor (String sector, String zona, String tipo){
        //SOAP
        //TERÁ QUE COMUNICAR COM O NODEGEST -> NODE -> SENSOR
        return "";
    }
    
    //DEVERÁ DEFINIR O INTERVALO DE TEMPO QUE RECEBE (LÊ) DADOS DE TODA A ZONA (TODOS OS SENSORES)
    public String definirIntervaloSensor (String sector, String zona, String tipo, int valor){
        //SOAP
        //TERÁ QUE COMUNICAR COM O NODEGEST -> NODE -> SENSOR
        return "";
    }
    
    //GERA VALORES ALEATÓRIOS PARA CADA TIPO DE SENSOR
     private static float getTemp( float minT, float maxT ){
        Random rand = new Random();
        return rand.nextFloat() * (maxT - minT) + minT;
    }

    private static float getHumidity ( float minHu, float maxHu ){
        Random rand = new Random();
        return rand.nextFloat() * (maxHu - minHu) - minHu;
    }

    private static float getSolarRad ( float minSRad, float maxSRad ){
        Random rand = new Random();
        return rand.nextFloat() * (maxSRad - minSRad) - minSRad;
    }
}

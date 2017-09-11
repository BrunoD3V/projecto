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
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GereSensor {
    
    private String result = "";
    
    
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
        String result = "";
        if(tipo.equalsIgnoreCase("temp")){
            float temperatura = getTemp();
            result = "Resposta Sector " + sector + " Zona " + zona + "Tipo " + tipo + " Temperatura = " + temperatura;   
        }
        if(tipo.equalsIgnoreCase("radi")){
            float radi = getSolarRad();
            result = "Resposta Sector " + sector + " Zona " + zona + "Tipo " + tipo + " Radiação = " + radi;
        }
        if(tipo.equalsIgnoreCase("humi")){
            float humi = getHumidity();
            result = "Resposta Sector " + sector + " Zona " + zona + "Tipo " + tipo + " Humidade = " + humi;
        }
        return result;
    }
    
    public void respostaPedidoSensor(){
        
    }
    
    //DEVERÁ DEFINIR O INTERVALO DE TEMPO QUE RECEBE (LÊ) DADOS DE TODA A ZONA (TODOS OS SENSORES)
    public String definirIntervaloSensor (String sector, String zona, String tipo, int valor){
        
        result = "";
        
        new Timer().schedule(new TimerTask(){
            
                            @Override
                            public void run() {
                               result = pedirDadosSensor(sector, zona, tipo);
                            }
                        },1000*60*valor,1000*60*valor); 
        
        return result;
    }
    
    //GERA VALORES ALEATÓRIOS PARA CADA TIPO DE SENSOR
     private static float getTemp(){
        Random rand = new Random();
        return rand.nextFloat() * (45.0f - (-10.0f)) + (-10.0f);
    }

    private static float getHumidity (){
        Random rand = new Random();
        return rand.nextFloat() * (100.0f - 0.0f) - 0.0f;
    }

    private static float getSolarRad (){
        Random rand = new Random();
        return rand.nextFloat() * (4.0f - 0.3f) - 0.3f;
    }
}

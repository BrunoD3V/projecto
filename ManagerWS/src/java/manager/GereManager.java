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
public class GereManager {
    SoapHandler soapHandler;
    
    
    public boolean inserirNodeGest(String sector){
        //SOAP
        /*
        soapHandler = new SoapHandler("inserirNodeGest");
        SoapObject inserirNodeGest = new SoapObject(soapHandler.getNAMESPACE(),soapHandler.getMethodName());
        
        SoapObject soapNodeGest = new SoapObject(soapHandler.getNAMESPACE(), "");
        
        //EXEMPLO
        soapHandler = new SoapHandler("inserirCliente");
        SoapObject inserirCliente = new SoapObject(soapHandler.getNAMESPACE(),soapHandler.getMethodName());

        SoapObject soapCliente = new SoapObject(soapHandler.getNAMESPACE(),"cliente");

        soapCliente.addProperty("codigoPostal", cliente.getCodigoPostal());
        soapCliente.addProperty("contacto", cliente.getContacto());
        soapCliente.addProperty("email", cliente.getEmail());
        soapCliente.addProperty("id", cliente.getId());
        soapCliente.addProperty("morada", cliente.getMorada());
        soapCliente.addProperty("nif", cliente.getMorada());
        soapCliente.addProperty("nome", cliente.getNome());
        soapCliente.addProperty("tipo", cliente.getTipo());

        inserirCliente.addSoapObject(soapCliente);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(inserirCliente);

        envelope.implicitTypes = true;

        HttpTransportSE http = new HttpTransportSE(soapHandler.getURL());

        try {
            http.call(soapHandler.getSoapAction(), envelope);

            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            return Boolean.parseBoolean(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return false;
        }
        */
       
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
            Logger.getLogger(GereManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista;
    }
    
    public NodeGest pesquisarNodeGest(String sector){
        //SOAP
        NodeGest nodegest = new NodeGest();
        return nodegest;
    }
    
    //METODOS PARA INTRODUZIR NODES E SENSORES 
    public boolean inserirNode(String idNodeGest, String zona){
        //SOAP
        //TERÁ DE COMUNICAR COM O NODEGEST -> NODE
        return true;
    }
    
    public boolean inserirSensor(String idNode, int intervalo, String tipo){
        //SOAP
        //TERÁ QUE COMUNICAR COM O NODEGEST -> NODE -> SENSOR
        return true;
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
} 
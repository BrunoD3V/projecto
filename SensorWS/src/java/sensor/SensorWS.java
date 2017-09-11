/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensor;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;


@WebService(serviceName = "SensorWS")
public class SensorWS {

    @WebMethod(operationName = "inserirNode")
    public Boolean inserirSensor(@WebParam(name = "idNodo") String idNodo,
            @WebParam(name = "intervalo") int intervalo,
            @WebParam(name = "tipo") String tipo)
    {
        GereSensor DB = new GereSensor();
        return DB.inserirSensor(idNodo,intervalo,tipo);
    }
    
    @WebMethod(operationName = "pesquisarSensor")
    public Sensor pesquisarSensor(@WebParam(name = "tipo") String tipo){
        GereSensor DB = new GereSensor();
        return DB.pesquisarSensor(tipo);
    }
    
    @WebMethod(operationName = "pedirDadosSensor")
    public String pedirDadosSensor (@WebParam(name = "sector") String sector,
            @WebParam(name = "zona") String zona,
            @WebParam(name = "tipo") String tipo){
        GereSensor DB = new GereSensor();
        return DB.pedirDadosSensor(sector,zona,tipo);
    }
    
    @WebMethod (operationName = "definirIntervaloSensor")
    public String definirIntervaloSensor (@WebParam(name = "sector") String sector,
            @WebParam(name = "zona") String zona,
            @WebParam(name = "tipo") String tipo,
            @WebParam(name = "valor") int valor){
        GereSensor DB = new GereSensor();
        return DB.definirIntervaloSensor(sector,zona,tipo,valor);
    }
}

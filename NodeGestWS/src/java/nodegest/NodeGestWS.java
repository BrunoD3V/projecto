/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodegest;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;


@WebService(serviceName = "NodeGestWS")
public class NodeGestWS {

    @WebMethod(operationName = "inserirNodeGest")
    public Boolean inserirNodeGest(@WebParam(name = "sector") String sector)
    {
        GereNodeGest DB = new GereNodeGest();
        return DB.inserirNodeGest(sector);
    }
    
    @WebMethod(operationName = "inserirNode")
    public Boolean inserirNode(@WebParam(name = "nodeGestSector") String nodeGestSector,
            @WebParam(name = "zona") String zona)
    {
        GereNodeGest DB = new GereNodeGest();
        return DB.inserirNode(nodeGestSector,zona);
    }
    
    @WebMethod(operationName = "inserirSensor")
    public Boolean inserirSensor(@WebParam(name = "idNode") String idNode,
            @WebParam(name = "intervalo") int intervalo,
            @WebParam(name = "tipo") String tipo)
    {
        GereNodeGest DB = new GereNodeGest();
        return DB.inserirSensor(idNode,intervalo,tipo);
    }
    
    
    @WebMethod(operationName = "pesquisarNodeGest")
    public String pesquisarNodeGest(@WebParam(name = "sector") String sector){
        GereNodeGest DB = new GereNodeGest();
        return DB.pesquisarNodeGest(sector);
    }
    
    @WebMethod(operationName = "pedirDadosSensor")
    public String pedirDadosSensor (@WebParam(name = "sector") String sector,
            @WebParam(name = "zona") String zona,
            @WebParam(name = "tipo") String tipo){
        GereNodeGest DB = new GereNodeGest();
        return DB.pedirDadosSensor(sector,zona,tipo);
    }
    
    @WebMethod (operationName = "definirIntervaloSensor")
    public String definirIntervaloSensor (@WebParam(name = "sector") String sector,
            @WebParam(name = "zona") String zona,
            @WebParam(name = "tipo") String tipo,
            @WebParam(name = "valor") int valor){
        GereNodeGest DB = new GereNodeGest();
        return DB.definirIntervaloSensor(sector,zona,tipo,valor);
    }        
}
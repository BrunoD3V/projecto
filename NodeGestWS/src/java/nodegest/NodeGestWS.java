/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodegest;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author bruno
 */
@WebService(serviceName = "NodeGestWS")
public class NodeGestWS {

    @WebMethod(operationName = "inserirNodeGest")
    public Boolean inserirNodeGest(@WebParam(name = "sector") String sector)
    {
        GereNode DB = new GereNode();
        return DB.inserirNodeGest(sector);
    }
    
    @WebMethod(operationName = "inserirNode")
    public Boolean inserirNode(@WebParam(name = "nodeGestSector") String nodeGestSector,
            @WebParam(name = "zona") String zona)
    {
        GereNode DB = new GereNode();
        return DB.inserirNode(nodeGestSector,zona);
    }
    
    @WebMethod(operationName = "inserirSensor")
    public Boolean inserirSensor(@WebParam(name = "idNode") String idNode,
            @WebParam(name = "intervalo") int intervalo,
            @WebParam(name = "tipo") String tipo)
    {
        GereNode DB = new GereNode();
        return DB.inserirSensor(idNode,intervalo,tipo);
    }
    
    
    @WebMethod(operationName = "pesquisarNodeGest")
    public NodeGest pesquisarNodeGest(@WebParam(name = "sector") String sector){
        GereNode DB = new GereNode();
        return DB.pesquisarNodeGest(sector);
    }
    
    @WebMethod(operationName = "pedirDadosSensor")
    public String pedirDadosSensor (@WebParam(name = "sector") String sector,
            @WebParam(name = "zona") String zona){
        GereNode DB = new GereNode();
        return DB.pedirDadosSensor(sector,zona);
    }
    
    @WebMethod (operationName = "definirIntervaloSensor")
    public String definirIntervaloSensor (@WebParam(name = "sector") String sector,
            @WebParam(name = "zona") String zona){
        GereNode DB = new GereNode();
        return DB.definirIntervaloSensor(sector,zona);
    }           
}
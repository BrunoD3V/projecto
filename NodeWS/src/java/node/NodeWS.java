/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author bruno
 */
@WebService(serviceName = "NodeWS")
public class NodeWS {

    @WebMethod(operationName = "inserirNode")
    public Boolean inserirNode(@WebParam(name = "nodeGestSector") String nodeGestSector,
            @WebParam(name = "zona") String zona)
    {
        GereNode DB = new GereNode();
        return DB.inserirNode(nodeGestSector,zona);
    }
    
    @WebMethod(operationName = "pesquisarNode")
    public Node pesquisarNode(@WebParam(name = "zona") String zona){
        GereNode DB = new GereNode();
        return DB.pesquisarNode(zona);
    }
    
    @WebMethod(operationName = "inserirSensor")
    public Boolean inserirSensor(@WebParam(name = "idNode") String idNode,
            @WebParam(name = "intervalo") int intervalo,
            @WebParam(name = "tipo") String tipo)
    {
        GereNode DB = new GereNode();
        return DB.inserirSensor(idNode,intervalo,tipo);
    }
    
    @WebMethod(operationName = "pedirDadosSensor")
    public String pedirDadosSensor (@WebParam(name = "sector") String sector,
            @WebParam(name = "zona") String zona,
            @WebParam(name = "tipo") String tipo){
        GereNode DB = new GereNode();
        return DB.pedirDadosSensor(sector,zona,tipo);
    }
    
    @WebMethod (operationName = "definirIntervaloSensor")
    public String definirIntervaloSensor (@WebParam(name = "sector") String sector,
            @WebParam(name = "zona") String zona,
            @WebParam(name = "tipo") String tipo,
            @WebParam(name = "valor") int valor){
        GereNode DB = new GereNode();
        return DB.definirIntervaloSensor(sector,zona,tipo,valor);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import java.util.ArrayList;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author bruno
 */
@WebService(serviceName = "ManagerWS")
public class ManagerWS {

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
    
    //TALVEZ NAO TENHA UTILIDADE
    @WebMethod(operationName = "listarNodeGest")
    public ArrayList<NodeGest> listarNodeGest(){
        GereNodeGest DB = new GereNodeGest();
        return DB.listarNodeGest();
    }
    
    @WebMethod(operationName = "pesquisarNodeGest")
    public NodeGest pesquisarNodeGest(@WebParam(name = "sector") String sector){
        GereNodeGest DB = new GereNodeGest();
        return DB.pesquisarNodeGest(sector);
    }
    
    @WebMethod(operationName = "pedirDadosSensor")
    public String pedirDadosSensor (@WebParam(name = "sector") String sector,
            @WebParam(name = "zona") String zona){
        GereNodeGest DB = new GereNodeGest();
        return DB.pedirDadosSensor(sector,zona);
    }
    
    @WebMethod (operationName = "definirIntervaloSensor")
    public String definirIntervaloSensor (@WebParam(name = "sector") String sector,
            @WebParam(name = "zona") String zona){
        GereNodeGest DB = new GereNodeGest();
        return DB.definirIntervaloSensor(sector,zona);
    }           
}
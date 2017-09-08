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
    public Boolean inserirNodeGest(@WebParam(name = "TempMin") float TempMin,
            @WebParam(name = "TempMax") float TempMax,
            @WebParam(name = "HumiMin") float HumiMin,
            @WebParam(name = "HumiMax") float HumiMax,
            @WebParam(name = "RadiMin") float RadiMin,
            @WebParam(name = "RadiMax") float RadiMax,
            @WebParam(name = "sector") String sector)
    {
        GereNodeGest DB = new GereNodeGest();
        return DB.inserirNodeGest(TempMin,TempMax,HumiMin,HumiMax,RadiMin,RadiMax,sector);
    }
    
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
}

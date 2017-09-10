/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import manager.ManagerWS;
import manager.ManagerWS_Service;

/**
 *
 * @author bruno
 */
public class main {
    
    public static void main( String [] args){
       ManagerWS_Service man = new ManagerWS_Service();
       ManagerWS ws = man.getManagerWSPort();
       boolean test = ws.inserirNodeGest("1");
   }
}

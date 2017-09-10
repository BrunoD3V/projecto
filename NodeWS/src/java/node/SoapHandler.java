/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package node;

/**
 *
 * @author bruno
 */
public class SoapHandler {
    private String NAMESPACE;
    private static String URL;
    private String METHOD_NAME;
    private String SOAP_ACTION;
    //MUDAR IP SERVER
    private static String SERVER_IP = "192.168.1.5";

    public SoapHandler(String methodName){
        NAMESPACE = "http://node/";
        URL = "http://"+SERVER_IP+":8080/NodeWS/NodeWS";
        METHOD_NAME = methodName;
        SOAP_ACTION = NAMESPACE + METHOD_NAME;
    }

    public String getIP() {
        return SERVER_IP;
    }

    public static void setIP(String IP) {
        SoapHandler.SERVER_IP = IP;
    }

    public String getMethodName() {
        return METHOD_NAME;
    }

    public void setMethodName(String methodName) {
        METHOD_NAME = methodName;
    }

    public String getNAMESPACE() {
        return NAMESPACE;
    }

    public String getSoapAction() {
        return SOAP_ACTION;
    }

    public String getURL() {
        return URL;
    }

}

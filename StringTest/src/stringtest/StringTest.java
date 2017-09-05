/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stringtest;

/**
 *
 * @author bruno
 */
public class StringTest {

    public static void main(String[] args) {
       String request = "Request NG1 N2 S1";
       request = request.substring(8);
        System.out.println(request);
       int element = Integer.parseInt(request.substring(2,3));
        System.out.println("\n" + element);
       request = request.substring(4);
       request = "Request " + request;
        System.out.println("\n" + request);
    }
    
}

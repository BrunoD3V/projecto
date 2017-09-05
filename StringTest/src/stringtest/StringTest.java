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
        String request = "SetInterval m2";
        request = request.substring(12);
        System.out.println(request);
        request = request.substring(1,2);
        System.out.println(request);
       
       
       
        /*
        int element = Integer.parseInt(request.substring(1,2));
        request = request.substring(3);
        System.out.println("\nElement " + element);
        request = "SetInterval " + request;
        System.out.println("\n" + request);
*/
    }
}

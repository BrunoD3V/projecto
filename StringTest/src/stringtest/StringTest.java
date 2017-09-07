/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stringtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author bruno
 */
public class StringTest {

    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Write Something: ");
        String request = br.readLine();
        System.out.println("You wrote: " + request);
       
       
       
        /*
        int element = Integer.parseInt(request.substring(1,2));
        request = request.substring(3);
        System.out.println("\nElement " + element);
        request = "SetInterval " + request;
        System.out.println("\n" + request);
*/
    }
}

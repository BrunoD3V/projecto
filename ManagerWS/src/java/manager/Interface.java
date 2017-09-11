/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Interface {

    //CONSOLE INTERFACE VARIABLES
    //Console Input Reader
    private static BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
    private static String consoleInput = "";
    
    
    
    private static void iniciaTimer(String sector, String zona, String tipo, int intervalo){
         new Timer().schedule(new TimerTask(){
            
                            @Override
                            public void run() {
                               GereManager gm = new GereManager();
                                System.out.println(gm.pedirDadosSensor(sector, zona, tipo));
                               
                            }
                        },1000*60*intervalo,1000*60*intervalo); 
        
    }
    
    
    public static void main(String[] args) {
        
        GereManager gm = new GereManager();
        try {
            while(true){
                int option;
                System.out.println("\nEscolha a opção");
                System.out.println("\n1 - Inserir nodeGest");
                System.out.println("\n2 - Inserir node");
                System.out.println("\n3 - Inserir sensor");
                System.out.println("\n4 - Listar nodeGest");
                System.out.println("\n5 - Pesquisar nodeGest");
                System.out.println("\n6 - Pedir dados sensor");
                System.out.println("\n7 - Definir intervalo de tempo");
                option = Integer.valueOf(consoleReader.readLine()) ;
                
                String sector,id, idNode, tipo, zona;
                int intervalo;
                
                switch(option){
                    
                    case 1: System.out.println("\nInsira o sector");
                            sector = consoleReader.readLine();
                            gm.inserirNodeGest(sector);
                            break;
                    
                    case 2: System.out.println("\nInsira o id do nodeGest");
                            id = consoleReader.readLine();
                            System.out.println("\nInsira a zona");
                            zona = consoleReader.readLine();
                            gm.inserirNode(id, zona);
                            break;
                            
                    case 3: System.out.println("\nInsira o id do node");
                            idNode = consoleReader.readLine();
                            System.out.println("\nInsira o intervalo de tempo");
                            intervalo = Integer.valueOf(consoleReader.readLine());
                            System.out.println("\nInsira o tipo de sensor");
                            tipo = consoleReader.readLine();
                            boolean res = gm.inserirSensor(idNode, intervalo, tipo);
                            System.out.println(res);
                            break;
                            
                    case 4: gm.listarNodeGest().toString();
                            break;
                            
                    case 5: System.out.println("\nInsira o sector a pesquisar");
                            String s = consoleReader.readLine();
                            gm.pesquisarNodeGest(s).toString();
                            break;
                            
                    case 6: System.out.println("\nInsira o sector");
                            sector = consoleReader.readLine();
                            System.out.println("\nInsira a zona");
                            zona = consoleReader.readLine();
                            System.out.println("\nInsira o tipo de sensor");
                            tipo = consoleReader.readLine();
                            System.out.println(gm.pedirDadosSensor(sector, zona, tipo));
                            break;
                            
                    case 7: System.out.println("\nInsira o sector");
                            sector = consoleReader.readLine();
                            System.out.println("\nInsira a zona");
                            zona = consoleReader.readLine();
                            System.out.println("\nInsira o tipo de sensor");
                            tipo = consoleReader.readLine();
                            System.out.println("\nInsira o intervalo de tempo");
                            intervalo = Integer.valueOf(consoleReader.readLine());
                            iniciaTimer(sector, zona, tipo, intervalo);
                            break;
                    
                    default: System.out.println("Opção inválida");
                            
                        
                }
            }
            
            /*
            System.out.println("Inserir Sector: ");
            String sector;
            sector = consoleReader.readLine();
            System.out.println("Inserir zona: ");
            String zona;
            zona = consoleReader.readLine();
            System.out.println("Inserir tipo: ");
            String tipo;
            tipo = consoleReader.readLine();
            
            GereManager gm = new GereManager();
            System.out.println(gm.pedirDadosSensor(sector, zona, tipo));*/
            
        } catch (IOException ex) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
}

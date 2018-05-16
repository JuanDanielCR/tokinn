/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoalgoritmos;

/**
 *
 * @author Josue Duran
 */
public class ProyectoAlgoritmos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Algoritmos al = new Algoritmos();
        System.out.println(al.generateToken("458", Algoritmos.ESTADO));
        System.out.println(al.verifyToken("458", Algoritmos.INICIO, "BCFC8")); 
    }
    
}

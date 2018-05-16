/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoalgoritmos;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Josue Duran
 */
public class Algoritmos {
    /*Estados que se instancian segun la operacion*/
    public static String INICIO = "INICIO";
    public static String ESTADO = "ESTADO_DE_CUENTA";
    public static String TRANSACCION = "TRANSACCION";
    /*Longitud del Token*/
    private final int tokenLength=5;
    
    /*Funcion que genera el hash y lo pasa a hexadecimal*/
    private static String getHash(String txt) {
        try {
            MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(txt.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                //Linea para homogeneizar el hash producido al convertirlo en un String
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    /*Funcion que regresa una cadena con el formato requerido de la fecha y hora*/
    private String getDateTime(Date date){
        SimpleDateFormat formato = new SimpleDateFormat("yyyyMMddhhmm");
        return formato.format(date);
    }
    
    /*Funcion que suma o resta minutos a la fecha actual*/
    private Date addMinutes(Date date, int num){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, num);
        return calendar.getTime();
    }
    
    /*Funcion que genera los tokens si se restan o suman minutos*/
    private String generateToken(String id, String type, Date date){
        return getHash(id+type+getDateTime(date)).substring(0, tokenLength).toUpperCase();
    }
    
    /*Funcion que genera el token actual ESTA FUNCION ES LA QUE USA EL BOT
    Se manda el ID del usuario y se instancia el TIPO DE TOKEN
    devuelve una cadena con el TOKEN GENERADO*/
    public String generateToken(String id, String type){
        return getHash(id+type+getDateTime(new Date())).substring(0, tokenLength).toUpperCase();
    }
    
    /*Funcion que verifica el token, tiene un umbral de 2 a 3 minutos ESTA FUNCION VA EN EL SERVIDOR
    Se manda el ID del usuario, se instancia el TIPO DE TOKEN y se manda el TOKEN RECIVIDO
    devuelve TRUE si es aceptado y un FALSE si no*/
    public boolean verifyToken(String id, String type, String token){
        if(token.equals(generateToken(id,type,new Date()))){
            return true;
        }else if(token.equals(generateToken(id,type,addMinutes(new Date(),-2)))){
            return true;
        }else if(token.equals(generateToken(id,type,addMinutes(new Date(),-1)))){
            return true;
        }
        return false;
    } 
}

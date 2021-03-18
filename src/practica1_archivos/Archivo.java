package practica1_archivos;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author David Madrigal Buendía
 * @author David Arturo Oaxaca Pérez
 */
public class Archivo {
    private String ruta_servidor;
    private String ruta_madre;
    
    public Archivo() {
        ruta_madre= new File("").getAbsolutePath();
        ruta_servidor= ruta_madre + "/Servidor";
    }
    
    public int getTam(File[] archivos){
        int tam = 0;
        for(File archivo : archivos){
            if(archivo.isDirectory()){
                File[] Dir_archivos = archivo.listFiles();
                tam += getTam(Dir_archivos);
            }else{
                tam ++;
            }
        }
        return tam;
    }
    
    private void verificarCarpeta(File carpeta) {
        if(!carpeta.exists()) {
            carpeta.mkdir();
        }
    }
    
    public void crearCarpeta(String path_carpeta) {
        verificarCarpeta(new File(ruta_servidor + "/" + path_carpeta));
    }
    
    public String getRutaServidor() {
        verificarCarpeta(new File(ruta_servidor));
        return ruta_servidor;
    }
    
    public File[] getListaArchivosServidor() {
        File carpeta_servidor= new File(ruta_servidor);
        verificarCarpeta(carpeta_servidor);
        File[] lista_archivos= carpeta_servidor.listFiles(); 
        /*
        String[] lista_nombres_archivos= new String[lista_archivos.length];
        
        for(File archivo : lista_archivos) {
            if(archivo.isFile()) {
                
            }else if(archivo.isDirectory()) {
                
            }
        }*/
        return lista_archivos;
    }
    
    public File buscarArchivo(String nombre) {
        File carpeta_servidor= new File(ruta_servidor);
        File[] lista_archivos= carpeta_servidor.listFiles(); 
        
        for(File archivo : lista_archivos) {
            if(archivo.getName().equals(nombre)) {
                return archivo;
            }
        }
        return null;
    }
    
    public void eliminarArchivo(String nombre) {
        File carpeta_servidor= new File(ruta_servidor);
        File[] lista_archivos= carpeta_servidor.listFiles(); 
        
        for(File archivo : lista_archivos) {
            if(archivo.getName().equals(nombre)) {
                System.out.println("Archivo eliminado: " + nombre);
                archivo.delete();
            }
        }
    }
    
    public File crearArchivo(String path, String nombre) {
        File carpeta = new File(path);
        verificarCarpeta(carpeta);
        return new File(ruta_servidor + "/" + path + "/" + nombre);
    }
}
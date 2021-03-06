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
        if(!carpeta.getParentFile().exists()){
            verificarCarpeta(carpeta.getParentFile());
        }
        
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
    
    public void eliminarArchivo(String nombre, String dir) {
       
        if(dir.equals("")){
            dir = ruta_servidor;
        }
        
        File carpeta_servidor= new File(dir);
        
        File[] lista_archivos= carpeta_servidor.listFiles(); 
        
        for(File archivo : lista_archivos) {
            if(archivo.getName().equals(nombre)) {
                if(archivo.isDirectory()){//????
                    System.out.println("Es directorio...");
                    File [] Dir_contenido = archivo.listFiles();
                    
                    for(File Dir_archi : Dir_contenido){
                        eliminarArchivo(Dir_archi.getName(), dir + "\\" + archivo.getName());
                    }

                }
                
                System.out.println("Archivo eliminado: " + nombre);
                archivo.delete();
                
            }
        }
    }
    
    public File crearArchivo(String path, String nombre) {
        File carpeta = new File(ruta_servidor + "\\" + path);
        //verificarCarpeta(new File(ruta_servidor)); //Verificamos que previamente exista la ruta del servidor
        verificarCarpeta(carpeta);
        return new File(ruta_servidor + "\\" + path + "\\" + nombre);
    }
}
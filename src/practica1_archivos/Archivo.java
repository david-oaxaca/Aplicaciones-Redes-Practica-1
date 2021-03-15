package practica1_archivos;

import java.io.File;

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
    
    public String getRutaServidor() {
        return ruta_servidor;
    }
    
    public File[] getListaArchivosServidor() {
        File carpeta_servidor= new File(ruta_servidor);
        if(!carpeta_servidor.exists()) {
            carpeta_servidor.mkdir();
        }
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
                archivo.delete();
            }
        }
    }
}
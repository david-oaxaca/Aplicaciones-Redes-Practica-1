/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practica1;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author tdwda
 */
public class Cliente_Files {
    
    private Socket cl;
    private int puerto;
    private String dir;
    private String destino;
    BufferedOutputStream bos;
    DataOutputStream dos;
    
    
    
    public Cliente_Files(){
        this.puerto = 4000;
        this.dir = "localhost";
        this.destino = "";
        
    }
    
    public Cliente_Files(int puerto, String dir, String Destino){
        this.puerto = puerto;
        this.dir = dir;
        this.destino = Destino;
        
    }
    
    public void crearConexion() throws IOException{
        cl = new Socket(dir, puerto);
        System.out.println("Conexion establecida...\n ");
        
    }
    
    public void transferirArchivo(ArrayList <File> archivos, String path) throws IOException{
        crearConexion();
        bos = new BufferedOutputStream(cl.getOutputStream());
        dos = new DataOutputStream(bos);
        
        dos.writeInt(0);
        dos.flush();
        
        int tam = getTam(archivos);
        
        System.out.println( tam + " Archivos a enviados");
        
        dos.writeInt(tam);
        dos.flush();
        
        enviarArchivo(archivos, path);
        
        bos.close();
        dos.close();
    }

    
    public void enviarArchivo(ArrayList <File> archivos, String path) throws IOException{
        
        for(File archivo : archivos){
            
            if(archivo.isDirectory()){
                System.out.println("Es directorio...");
                File [] Dir_contenido = archivo.listFiles();
                ArrayList <File> Dir_archivos = new ArrayList(Arrays.asList(Dir_contenido));
               
                enviarArchivo(Dir_archivos, path + "\\" + archivo.getName());
                
            }else{
                
                byte[] b = new byte[(int)archivo.length()];
                
                dos.writeUTF(path);
                dos.flush();

                dos.writeLong(archivo.length());
                dos.flush();

                dos.writeUTF(archivo.getName());
                dos.flush();

//0170
                FileInputStream fis = new FileInputStream(archivo);
                BufferedInputStream bis = new BufferedInputStream(fis);
                int n = 0;
                while((n = bis.read(b, 0, b.length)) != -1){
                    bos.write(b, 0, n);
                    bos.flush();
                }
                System.out.println("Archivo copiado....");
         
                bis.close();
                bos.flush();
                dos.flush();
            }  
        }
    }
    
    public void crearCarpeta(String path, String nueva_carpeta) throws IOException{
        crearConexion();
        bos = new BufferedOutputStream(cl.getOutputStream());
        dos = new DataOutputStream(bos);
        
        dos.writeInt(1);
        dos.flush();
        
        System.out.println("");
        
        dos.writeUTF(path + "\\" + nueva_carpeta);
        dos.flush();
        
        bos.close();
        dos.close();
    }
    
    public void eliminarArchivo(String path) throws IOException{
        crearConexion();
        bos = new BufferedOutputStream(cl.getOutputStream());
        dos = new DataOutputStream(bos);
        
        dos.writeInt(2);
        dos.flush();
        
        dos.writeUTF(path);
        dos.flush();
        
        bos.close();
        dos.close();
    }
    
    public void peticionArchivo(String path, String peticion) throws IOException{
        crearConexion();
        bos = new BufferedOutputStream(cl.getOutputStream());
        dos = new DataOutputStream(bos);
        
        dos.writeInt(3);
        dos.flush();
        
        dos.writeUTF(peticion);
        dos.flush();
        
        BufferedInputStream bis = new BufferedInputStream(cl.getInputStream());
        DataInputStream dis = new DataInputStream(bis);
            
        File archi_Descarga;
        
        long FileLen = dis.readLong();
        String nombre = dis.readUTF();

        File destino = new File(path);
        if (!destino.exists()) {
            destino.mkdir();
        }

        archi_Descarga = new File(path + "\\" + nombre);

        FileOutputStream fos =  new FileOutputStream(archi_Descarga);
        bos = new BufferedOutputStream(fos);

        for(int j = 0; j < FileLen; j++){ 
            bos.write(bis.read());
        }

        System.out.println("Archivo transferido....");
        
        bis.close();
        dis.close();
        bos.close();
        dos.close();
    }
    
    public ArrayList <String> getServerArchivos(String path) throws IOException{
        ArrayList <String> ArchiServer = new ArrayList();
        
        crearConexion();
        bos = new BufferedOutputStream(cl.getOutputStream());
        dos = new DataOutputStream(bos);
        
        dos.writeInt(4);
        dos.flush();
        
        dos.writeUTF(path);
        dos.flush();
        
        BufferedInputStream bis = new BufferedInputStream(cl.getInputStream());
        DataInputStream dis = new DataInputStream(bis);
            
        int cantidad = dis.readInt();
        String nombre = "";
        for (int i = 0; i < cantidad; i++) {
            
            nombre = dis.readUTF();
            ArchiServer.add(nombre);
            
        }    
        bis.close();
        dis.close();
        bos.close();
        dos.close();
        
        System.out.println("Lista recibida " );
        
        return ArchiServer;
    }
    
    public int getTam(ArrayList <File> archivos){
        
        int tam = 0;
        
        for(File archivo : archivos){
            if(archivo.isDirectory()){
                ArrayList <File> Dir_archivos = new ArrayList(Arrays.asList(archivo.listFiles()));
                tam += getTam(Dir_archivos);
            }else{
                tam ++;
            }
        }
        return tam;
    }
    
}

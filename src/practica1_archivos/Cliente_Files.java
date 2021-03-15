/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1_archivos;

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
    //BufferedOutputStream bos;
    //DataOutputStream dos;
    
    
    
    public Cliente_Files(){
        this.puerto = 4000;
        this.dir = "localhost";
        this.destino = "";
        
        /*try {
            crearConexion();
        } catch (IOException ex) {
            Logger.getLogger(Cliente_Files.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
    
    public Cliente_Files(int puerto, String dir, String Destino){
        this.puerto = puerto;
        this.dir = dir;
        this.destino = Destino;
        
        /*try {
            crearConexion();
        } catch (IOException ex) {
            Logger.getLogger(Cliente_Files.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
    
    public void crearConexion() throws IOException{
        cl = new Socket(dir, puerto);
        System.out.println("Conexion establecida...\n ");
    }

    
    public void enviarArchivo(ArrayList <File> archivos, String path) throws IOException{
        
        //DataInputStream dis = new DataInputStream(cl.getInputStream());
        
        for(File archivo : archivos){
            
            if(archivo.isDirectory()){
                System.out.println("Es directorio...");
                File [] Dir_contenido = archivo.listFiles();
                ArrayList <File> Dir_archivos = new ArrayList(Arrays.asList(Dir_contenido));
               
                enviarArchivo(Dir_archivos, path + "\\" + archivo.getName());
                
            }else{
                crearConexion();
                BufferedOutputStream bos = new BufferedOutputStream(cl.getOutputStream());
                DataOutputStream dos = new DataOutputStream(bos);
        
                //dos.writeInt(archivos.size());
                //dos.flush();
                
                byte[] b = new byte[(int)archivo.length()];
                
                dos.writeUTF(path);

                dos.writeLong(archivo.length());
                dos.flush();

                dos.writeUTF(archivo.getName());
                dos.flush();


                FileInputStream fis = new FileInputStream(archivo);
                BufferedInputStream bis = new BufferedInputStream(fis);
                int n = 0;
                while((n = bis.read(b, 0, b.length)) != -1){
                    bos.write(b, 0, n);
                    bos.flush();
                }
                System.out.println("Archivo copiado....");
         
                bis.close();
                bos.close();
                dos.close();
            }
            
        }
        
    }
    
    /*public void transferArchivos(ArrayList <File> archivos, String path, BufferedOutputStream bos,
            DataOutputStream  dos) throws IOException{
        
    }*/
    
    /***************************************************************************************
    public static void main(String[] args) throws Exception{
        String dir= "localhost";
        int puerto= 4000;

        Socket cl = new Socket(dir, puerto);
        System.out.println("Cliente conectado..\n transfiriendo archivo..");
        BufferedInputStream bis = new BufferedInputStream(cl.getInputStream());

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("duke1_1.png"));
        cl.setSoTimeout(3000);
        byte[] buf = new byte[1024];
        int leidos;
        while((leidos=bis.read(buf,0,buf.length))!=-1){
            bos.write(buf,0,leidos);
            bos.flush();
        }//while
        bis.close();
        bos.close();
        	
    }//main

    ***************************************************************************************/
}

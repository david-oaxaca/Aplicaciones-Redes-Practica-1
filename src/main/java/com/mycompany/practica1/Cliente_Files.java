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
import java.util.ArrayList;

/**
 *
 * @author tdwda
 */
public class Cliente_Files {
    
    private int puerto;
    private String dir;
    
    public Cliente_Files(){
        this.puerto = 4000;
        this.dir = "localhost";
    }
    
    public Cliente_Files(int puerto, String dir){
        this.puerto = puerto;
        this.dir = dir;
    }
    
    public void EnviarArchivo(ArrayList <File> archivos) throws IOException{
        
        Socket cl = new Socket(dir, puerto);
        System.out.println("Conexion establecida..\n Transfiriendo archivo..");
        
        
        DataInputStream dis = new DataInputStream(cl.getInputStream());
        DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
        
        dos.writeInt(archivos.size());
        dos.flush();
        
        //String nombre = dis.readUTF();
        int n = 0;
        byte[] b = new byte[2048];
        for (int i = 0; i < archivos.size(); i++) {
            dos.writeUTF(archivos.get(i).getName());
            dos.flush();
            
            FileInputStream fis = new FileInputStream(archivos.get(i));
            n = fis.read(b);
            while(n != -1){
                dos.write(b, 0, n);
                dos.flush();
            }
            
            fis.close();
        }
        
        dos.close();
    }
    
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
        System.out.println("Archivo copiado....");	
    }//main

    ***************************************************************************************/
}

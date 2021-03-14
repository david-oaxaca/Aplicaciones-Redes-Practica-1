/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practica1;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

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
    
    public void EnviarArchivo() throws IOException{
        
        Socket cl = new Socket(dir, puerto);
        System.out.println("Conexion establecida..\n Transfiriendo archivo..");
        
        
        BufferedInputStream bis = new BufferedInputStream(cl.getInputStream());

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("duke1_1.png"));
        
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practica1;

import java.net.ServerSocket;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author tdwda
 */
public class Server_Files {
    public static void main(String[] args) throws Exception{
        
        
	ServerSocket ss = new ServerSocket(4000);
	System.out.println("Servicio iniciado, esperando por cliente...");
        
	for(;;){
            Socket sc = ss.accept();
        
            System.out.println("Cliente conectado desde:"+sc.getInetAddress()+":"+sc.getPort());
        
            BufferedInputStream bis = new BufferedInputStream(sc.getInputStream());
            DataInputStream dis = new DataInputStream(bis);

            int cantidad = dis.readInt();
            File [] archivos = new File[cantidad];
            
            //File archi_sent;
            
            for (int i = 0; i < cantidad; i++) {
                try{
                    String dir = dis.readUTF();
                    long FileLen = dis.readLong();
                    String nombre = dis.readUTF();
                    
                    File destino = new File(dir);
                    if (!destino.exists()) {
                        destino.mkdir();
                    }
                    
                    archivos[i] = new File(dir + "\\" + nombre);
                    
                    FileOutputStream fos =  new FileOutputStream(archivos[i]);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                   
                    for(int j = 0; j < FileLen; j++){ 
                        bos.write(bis.read());
                    }
                    
                    System.out.println("Archivo transferido....");
                    bos.close();
                    
                }catch(EOFException e){
                    e.printStackTrace();
                }
                
            }//for
            
            dis.close();
            bis.close();       
        }//for
    }//main
}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practica1;

import java.net.ServerSocket;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
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
            Socket cl = ss.accept();
            System.out.println("Cliente conectado desde:"+cl.getInetAddress()+":"+cl.getPort());
            //File arch = new File("cancion.mp3");
            File arch = new File("duke.gif");
            
            int leidos=0;
            int completados=0;
            
            BufferedOutputStream bos = new BufferedOutputStream(cl.getOutputStream());
            //BufferedInputStream bis = new BufferedInputStream(new FileInputStream("cancion.mp3"));
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream("duke1.png"));
            byte[] buf = new byte[1024];
            int fin;
            int tam_bloque=(bis.available()>=1024)? 1024 :bis.available();
            int tam_arch = bis.available();
            System.out.println("tamaño archivo:"+bis.available()+ "bytes..");
            int b_leidos;
            while((b_leidos=bis.read(buf,0,buf.length))!= -1){
                    bos.write(buf,0,b_leidos);
                    bos.flush();
                    leidos += tam_bloque;
                    completados = (leidos * 100) / tam_arch;
                    System.out.print("\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b");
                    System.out.print("Completado:"+completados+" %");
                    tam_bloque=(bis.available()>=1024)? 1024 :bis.available();
            }//while
            bis.close();
            bos.close();
	}//for
	
		
    }//main
}

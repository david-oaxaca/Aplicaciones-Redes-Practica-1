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
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

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

            int tipo = dis.readInt();
            
            switch(tipo){
                case 0: //Recibe la cantidad de archivos que va a leer y
                        //posteriormente los lee uno por uno para crearlos en la direccion del servidor
                    
                    int cantidad = dis.readInt();
                    File [] archivos = new File[cantidad];
                    
                    System.out.println("Archivos a recibir: " + cantidad);

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

                    }
                    break;
                    
                case 1: //Crea una nueva carpeta, en caso de que esta no exista
                    
                    String dir_nueva = dis.readUTF();

                    File Carpeta = new File(dir_nueva);
                    if (!Carpeta.exists()) {
                        Carpeta.mkdir();
                    }
                    break;
                case 2: //Recibe la direccion del archivo para eliminarlo 
                    
                    break;
                case 3: //Recibe una peticion con el nombre del archivo que enviara al usuario
                    
                    break;
                case 4: //Retorna la lista de archivos de la direccion del servidor
                    
                    BufferedOutputStream bos = new BufferedOutputStream(sc.getOutputStream());
                    DataOutputStream dos = new DataOutputStream(bos);
        
                    String dir = dis.readUTF();
                    File serverArchi = new File(dir);
                    
                    File [] Dir_contenido = serverArchi.listFiles();
                    dos.writeInt(Dir_contenido.length);
                    dos.flush();
                    
                    for (int i = 0; i < Dir_contenido.length; i++) {
                        dos.writeUTF(Dir_contenido[i].getName());
                        dos.flush();
                    }
                    
                    System.out.println("Lista enviada");
                    bos.close();
                    dos.close();
                    break;
                default:
                    System.out.println("Oh no D:");
                    break;
            }
            
            
            dis.close();
            bis.close();       
        }//for
    }//main
}


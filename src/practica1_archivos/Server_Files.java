package practica1_archivos;

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
 * @author David Madrigal Buendía
 * @author David Arturo Oaxaca Pérez
 */
public class Server_Files {
    public static void main(String[] args) throws Exception{
        
        //String dir = "C:\\Users\\tdwda\\OneDrive\\Escritorio\\Servidor";
        int puerto= 4000;
	ServerSocket ss = new ServerSocket(puerto);
        ss.setReuseAddress(true);
        
	System.out.println("Servicio iniciado, esperando conexiones...");
        
        
        
	for(;;){
            Socket sc = ss.accept();
        
            System.out.println("Cliente conectado desde " + sc.getInetAddress() + ":" + sc.getPort());
            
            BufferedInputStream bis = new BufferedInputStream(sc.getInputStream());
            DataInputStream dis = new DataInputStream(bis);

            //int cantidad = dis.readInt();
            //File [] archivos = new File[cantidad];
            
            File archi_sent;
            
            //for (int i = 0; i < cantidad; i++) {
                try{
                    String dir = dis.readUTF();
                    long FileLen = dis.readLong();
                    String nombre = dis.readUTF();
                    
                    File destino = new File(dir);
                    if (!destino.exists()) {
                        destino.mkdir();
                    }
                    
                    archi_sent = new File(dir + "\\" + nombre);
                    
                    /*if(archivos[i].exists()){
                        archivos[i] = new File(dir + "\\" + nombre + "(1)");
                    }*/
                    
                    FileOutputStream fos =  new FileOutputStream(archi_sent);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                   
                    for(int j = 0; j < FileLen; j++){ 
                        bos.write(bis.read());
                    }
                    
                    System.out.println("Archivo transferido....");
                    bos.close();
                    
                }catch(EOFException e){
                    e.printStackTrace();
                }
                
                
            //}//for
            
            dis.close();
            bis.close();       
        }//for
    }//main
}

/*********************************************************************************************
 * 
 * for(;;){
            Socket cl = ss.accept();
            System.out.println("Cliente conectado desde:"+cl.getInetAddress()+":"+cl.getPort());
            //File arch = new File("cancion.mp3");
            File arch = new File("duke.gif");
            
            int leidos=0;
            int completados=0;
            
            BufferedOutputStream bos = new BufferedOutputStream(cl.getOutputStream());
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream("duke1.png"));
            byte[] buf = new byte[1024];
            int fin;
            int tam_bloque=(bis.available()>=1024)? 1024 :bis.available();
            int tam_arch = bis.available();
            
            System.out.println("tamaño archivo:"+bis.available()+ "bytes..");
            int b_leidos;
            * 
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
 * 
 * 
 * 
 *********************************************************************************************/
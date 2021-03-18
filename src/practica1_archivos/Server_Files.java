package practica1_archivos;

import java.net.ServerSocket;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author David Madrigal Buendía
 * @author David Arturo Oaxaca Pérez
 */
public class Server_Files {
    public static void main(String[] args) throws Exception{
        int puerto= 4000;
	ServerSocket ss = new ServerSocket(puerto);
        ss.setReuseAddress(true);
        boolean cerra_conexion= false;
        
        System.out.println("Servicio iniciado, esperando conexiones...");
        Archivo archivos_servidor= new Archivo();
	for(;;){
            Socket sc = ss.accept();
        
            System.out.println("Cliente conectado desde:"+sc.getInetAddress()+":"+sc.getPort());
        
            BufferedInputStream bis = new BufferedInputStream(sc.getInputStream());
            DataInputStream dis = new DataInputStream(bis);

            int tipo = dis.readInt();
            int numero_archivos;
            BufferedOutputStream bos = new BufferedOutputStream(sc.getOutputStream());
            DataOutputStream dos = new DataOutputStream(bos);
            switch(tipo){
                case 0: //Recibe la cantidad de archivos que va a leer y
                        //posteriormente los lee uno por uno para crearlos en la direccion del servidor
                    int cantidad = dis.readInt();
                    System.out.println("Archivos a recibir: " + cantidad);
                    for (int i = 0; i < cantidad; i++) {
                        try {
                            String path = dis.readUTF();
                            long FileLen = dis.readLong();
                            String nombre = dis.readUTF();
                            
                            File archivo= archivos_servidor.crearArchivo(path, nombre);
                            
                            FileOutputStream fos =  new FileOutputStream(archivo);
                            BufferedOutputStream bos_archivo = new BufferedOutputStream(fos);
                            for(int j = 0; j < FileLen; j++){ 
                                bos_archivo.write(bis.read());
                            }
                            System.out.println("Archivo transferido....");
                            bos_archivo.close();
                        }catch(EOFException e){
                            e.printStackTrace();
                        }
                    }
                    break;
                case 1: //Crea una nueva carpeta, en caso de que esta no exista
                    String dir_nueva = dis.readUTF();
                    archivos_servidor.crearCarpeta(dir_nueva);
                    break;
                case 2: //Recibe la direccion del archivo para eliminarlo 
                    numero_archivos = dis.readInt();
                    for(int i = 0; i < numero_archivos; i++) {
                        archivos_servidor.eliminarArchivo(dis.readUTF());
                    }
                    break;
                case 3: //Recibe una peticion con el nombre del archivo que enviara al usuario
                    numero_archivos = dis.readInt();
                    File[] archivos= new File[numero_archivos];
                    for(int i = 0; i < numero_archivos; i++) {
                        archivos[i]= archivos_servidor.buscarArchivo(dis.readUTF());
                    }
                    //Enviar archivos
                    int tam= archivos_servidor.getTam(archivos);
                    dos.writeInt(tam);
                    dos.flush();
                    enviarArchivo(archivos, "", bos, dos);
                    break;
                case 4: //Retorna la lista de archivos de la direccion del servidor
                    File [] Dir_contenido = archivos_servidor.getListaArchivosServidor();
                    dos.writeInt(Dir_contenido.length);
                    dos.flush();
                    
                    String tipoArchivo;
                    for (int i = 0; i < Dir_contenido.length; i++) {
                        tipoArchivo= (Dir_contenido[i].isDirectory())? "C | ": "A | ";
                        dos.writeUTF(tipoArchivo + Dir_contenido[i].getName());
                        dos.flush();
                    }
                    System.out.println("Lista enviada");
                    break;
                default:
                    System.out.println("Oh no D:");
                    break;
            }
            dis.close();
            bis.close();   
            bos.close();
            dos.close();
        }//for
    }//main
    
    static public void enviarArchivo(File[] archivos, String nombre_carpeta, BufferedOutputStream bos, DataOutputStream dos) throws IOException {
        System.out.println("Enviando...");
        for(File archivo : archivos){
            if(archivo.isDirectory()){//????
                System.out.println("Es directorio...");
                File [] Dir_contenido = archivo.listFiles();
                enviarArchivo(Dir_contenido, nombre_carpeta + "\\" + archivo.getName(), bos, dos);
            }else{
                byte[] b = new byte[(int)archivo.length()];

                dos.writeLong(archivo.length());
                dos.flush();
                
                dos.writeUTF(archivo.getName());
                dos.flush();
                
                dos.writeUTF(nombre_carpeta);
                dos.flush();
                
                FileInputStream fis = new FileInputStream(archivo);
                BufferedInputStream bis = new BufferedInputStream(fis);
                int n = 0;
                while((n = bis.read(b, 0, b.length)) != -1){
                    bos.write(b, 0, n);
                    bos.flush();
                }
                bis.close();
                dos.flush();
            }  
        }
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.practica1;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author tdwda
 */
public class ClienteGUI extends JFrame implements ActionListener{
    private Cliente_Files ClienteOpt = new Cliente_Files();
    private JFrame Ventana = new JFrame();
    private JButton btn_elegir, btn_enviar, btn_eliminar, btn_descargar, btn_crear;
    private JLabel titulo_seleccion, titulo_server;
    private DefaultListModel<File> fileListModel = new DefaultListModel<>();
    private JList<File> fileList = new JList<>(fileListModel);
    private JList<String> serverFileList = new JList<>();
    private String path = "C:\\Users\\tdwda\\OneDrive\\Escritorio\\Servidor";
    
    public ClienteGUI(){
        initVentana("Practica 1");
        setVisible(true);
    }
    
    public static void main(String[] args){
        new ClienteGUI();
            
    }
    
    public void initVentana(String nombre){
        this.setTitle(nombre);
        this.setSize(910, 575);
        this.setLayout(null);
        this.setVisible(true);
        setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        addComponents();
    }
    
    public void addComponents(){
        
        //Inicializamos labels
        
        titulo_seleccion = new JLabel("Archivos seleccionados");
        titulo_seleccion.setBounds(150, 10, 400, 30);
        titulo_seleccion.setVisible(true);
        this.add(titulo_seleccion);
        
        titulo_server = new JLabel("Archivos en el servidor");
        titulo_server.setBounds(600, 10, 400, 30);
        titulo_server.setVisible(true);
        this.add(titulo_server);
        
        //Inicializamos los botones
        
        //Botones de opciones para seleccionar y mandar archivos
        
        btn_elegir = new JButton("Escoger archivos");
        btn_elegir.setBounds(90, 410, 150, 30);
        btn_elegir.addActionListener(this);
        this.add(btn_elegir);
        
        btn_enviar = new JButton("Enviar");
        btn_enviar.setBounds(265, 410, 95, 30);
        btn_enviar.addActionListener(this);
        this.add(btn_enviar);
        
        //Botones de opciones para documentos en el servidor
        
        btn_descargar = new JButton("Descargar archivo");
        btn_descargar.setBounds(475, 410, 145, 30);
        btn_descargar.addActionListener(this);
        this.add(btn_descargar);
        
        btn_crear = new JButton("Crear carpeta");
        btn_crear.setBounds(630, 410, 120, 30);
        btn_crear.addActionListener(this);
        this.add(btn_crear);
        
        btn_eliminar = new JButton("Eliminar");
        btn_eliminar.setBounds(760, 410, 95, 30);
        btn_eliminar.addActionListener(this);
        this.add(btn_eliminar);

        // Inicialisamos lista de archvivos escogidos
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBounds(30, 50, 400, 350);
        
        //fileList.setBounds(15, 15, 500, 250);
        fileList.setVisibleRowCount(15);
        fileList.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        fileList.setLayout(new BorderLayout(3, 3));
        //fileList.setPrototypeCellValue(new File("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"));
        
        JScrollPane scrollPane = new JScrollPane(fileList);
        scrollPane.setViewportView(fileList);
        fileList.setLayoutOrientation(JList.VERTICAL);
        panel.add(scrollPane);
        
        this.add(panel);
        
        //Inicialisamos lista de archivos en el servidor
        
        JPanel panel_server = new JPanel(new BorderLayout());
        panel_server.setBounds(465, 50, 400, 350);
        
        serverFileList.setVisibleRowCount(15);
        serverFileList.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        serverFileList.setLayout(new BorderLayout(3, 3));
        
        JScrollPane scrollPane_server = new JScrollPane(serverFileList);
        scrollPane_server.setViewportView(serverFileList);
        serverFileList.setLayoutOrientation(JList.VERTICAL);
        panel_server.add(scrollPane_server);
        
        this.add(panel_server);
        
        try {
            actualizarListaServer();
        } catch (IOException ex) {
            Logger.getLogger(ClienteGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void actualizarListaServer() throws IOException{
        ArrayList <String> ArchiServer = new ArrayList();
        DefaultListModel model = new DefaultListModel();
        
        ArchiServer = ClienteOpt.getServerArchivos(path);
                    
        for (int i = 0; i < ArchiServer.size(); i++) {
            model.addElement(ArchiServer.get(i)); // <-- Add item to model
        }

        serverFileList.setModel(model);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        String evento = e.getActionCommand();
        
        if(evento.equals("Escoger archivos") || evento.equals("Cambiar archivos")){
            
            btn_elegir.setText("Cambiar archivos");
            
            JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            
            chooser.setMultiSelectionEnabled(true);
            
            int r = chooser.showSaveDialog(null);
            
            if(r == JFileChooser.APPROVE_OPTION){
                //l.setText(chooser.getSelectedFile().getAbsolutePath());
                fileListModel.clear();  // clear the model of prior files
                File[] files = chooser.getSelectedFiles();
                for (File file : files) {
                     // add all files to the model
                    fileListModel.addElement(file);
                }
            }
            
        }else if(evento.equals("Enviar")){
            
            
            if(fileList.getModel().getSize() == 0){
                JOptionPane.showMessageDialog(null, 
                        "No ha seleccionado ningun archivo para enviar");
            }else{
                ArrayList<File> archivos = new ArrayList();
                for (int i = 0; i < fileList.getModel().getSize(); i++) {
                    archivos.add(fileList.getModel().getElementAt(i));
                }
                try {
                    
                    ClienteOpt.transferirArchivo(archivos, path);
                    JOptionPane.showMessageDialog(null, 
                        "Archivos transferidos exitosamente");
                    fileListModel.removeAllElements();
                    
                    try {
                        actualizarListaServer();
                    } catch (IOException ex) {
                        Logger.getLogger(ClienteGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                } catch (IOException ex) {
                    Logger.getLogger(ClienteGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                btn_elegir.setText("Escoger archivos");
            }
            
        }else if(evento.equals("Eliminar")){
            
        }else if(evento.equals("Crear carpeta")){
            
            String nombre = JOptionPane.showInputDialog("Introduzca el nombre de la nueva carpeta: ");
            
            if(nombre != null && !nombre.equals("")){
                try {
                    ClienteOpt.crearCarpeta(path, nombre);
                    
                    JOptionPane.showMessageDialog(null, 
                        "Carpeta creada exitosamente");
                    
                    actualizarListaServer();
                    
                } catch (IOException ex) {
                    Logger.getLogger(ClienteGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }
    
}

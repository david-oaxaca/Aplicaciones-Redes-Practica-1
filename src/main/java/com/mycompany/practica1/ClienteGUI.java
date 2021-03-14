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
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author tdwda
 */
public class ClienteGUI extends JFrame implements ActionListener{
    private JFrame Ventana = new JFrame();
    private JButton btn_elegir, btn_enviar, btn_eliminar, btn_descargar, btn_crear;
    private JLabel titulo_seleccion, titulo_server;
    private DefaultListModel<File> fileListModel = new DefaultListModel<>();
    private JList<File> fileJList = new JList<>(fileListModel);
    private JList<String> serverFileList = new JList<>();
    
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
        
        //fileJList.setBounds(15, 15, 500, 250);
        fileJList.setVisibleRowCount(15);
        fileJList.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        fileJList.setLayout(new BorderLayout(3, 3));
        //fileJList.setPrototypeCellValue(new File("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"));
        
        JScrollPane scrollPane = new JScrollPane(fileJList);
        scrollPane.setViewportView(fileJList);
        fileJList.setLayoutOrientation(JList.VERTICAL);
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
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        String evento = e.getActionCommand();
        
        if(evento.equals("Escoger archivos")){
            
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
            
        }
        
    }
    
}

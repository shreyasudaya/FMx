package view;

import controller.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import model.*;

public class MainFrame extends JFrame implements ActionListener{
    public static MainFrame _instance;
    JPanel ShowPanel,TreePanel;
    FileTree filesTree;
    JScrollPane ScrollShow,TreeShow;
    DefaultMutableTreeNode node;
    public String Curr_URL="";
    public Map<String,String> Maps=new HashMap<String,String>();
    JList<String> list;
    public DefaultListModel defaultListModel;
    public Stack<String> stack,stack_return;
    JPopupMenu jPopupMenu1 = null;
    JPopupMenu jPopupMenu2 = null; 
	JPopupMenu jPopupMenu3 = null; 
	JPopupMenu jPopupMenu4 = null;
	JMenuItem[] JMI1 = new JMenuItem[10]; 
	JMenuItem[] JMI2 = new JMenuItem[5]; 
	JMenuItem delete1 = new JMenuItem("Delete"); 
	JMenuItem delete2 = new JMenuItem("Delete");
	JMenuItem zip=new JMenuItem("Compress");
	JMenuItem unzip=new JMenuItem("Extract");
	public Icon[] AllIcons = new Icon[999999];
	public int Icon_Counter = 0;

    public MainFrame(){
        this._instance = this;
		this.setTitle("File Manager");
		this.setSize(1000,600);
		this.getContentPane().setLayout(null);
		Init();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
    }

    public void Init(){
        ShowPanel = new JPanel();
		TreePanel = new JPanel();

        stack=new Stack<String>();
        stack_return=new Stack<String>();
        ShowPanel.setSize(800,600);
        ShowPanel.setLocation(195,10);
        ShowPanel.setLayout(null);

        list=new JList<String>();
        jPopupMenu1=new JPopupMenu();
        jPopupMenu2=new JPopupMenu();
        /*VVV important pls refer and pls see so no forget
         * wfwwedwedwedwedwedwedwdedwd
         * wedwedewddwedwed
         * gibberish to grab my attention
         * If you stop balaji from doing cp he will go into withdrawal
         * cwesecsdcsdce
         * cecececececa
         */
        JMI1[0] = new JMenuItem("Open");
        JMI1[1] = new JMenuItem("New Folder");
        JMI1[2] = new JMenuItem("Copy");
        JMI1[3] = new JMenuItem("Paste");
        JMI1[4] = new JMenuItem("Delete");
        JMI1[5] = new JMenuItem("Rename");
        JMI1[6] = new JMenuItem("encrypt/decrypt");
        JMI1[7] = new JMenuItem("Compress");

        for(int i = 0; i < 8; i++){
        	JMI1[i].addActionListener(this);
        	jPopupMenu1.add(JMI1[i]);            	
        }
        JMI2[0]=new JMenuItem("Open");
        JMI2[0].addActionListener(this);
        jPopupMenu2.add(JMI2[0]);

        jPopupMenu3 = new JPopupMenu();
        delete1.addActionListener(this);
        jPopupMenu3.add(delete1);
        zip.addActionListener(this);
        jPopupMenu3.add(zip);
        
        jPopupMenu4 = new JPopupMenu();
        delete2.addActionListener(this);
        jPopupMenu4.add(delete2);
        unzip.addActionListener(this);
        jPopupMenu4.add(unzip);
        
        list.add(jPopupMenu4);
        list.add(jPopupMenu3);
        list.add(jPopupMenu2);
        list.add(jPopupMenu1);

        Home_List();
            list.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e){
                    if(list.getSelectedIndex()!=-1){
                        if(e.getClickCount()==1){

                        }else if(e.getClickCount() == 2){
                            System.out.println(list.getSelectedValue());
                            doubleClick(list.getSelectedValue());
                        }
                        if(e.getButton()==3){
                            if(Curr_URL!=""){
                                if(list.getSelectedValue().endsWith("zip")){
								    jPopupMenu4.show(list,e.getX(),e.getY());
							    }
                                else if(list.getSelectedValuesList().size() == 1){
								    jPopupMenu1.show(list,e.getX(),e.getY());
							    }else if(list.getSelectedValuesList().size() > 1){
								    jPopupMenu3.show(list, e.getX(), e.getY());
							    }
						    }		                 
						    else if(Curr_URL == "" && list.getSelectedValuesList().size() == 1){
							    jPopupMenu2.show(list, e.getX(), e.getY()); 
						    }
                        }
                    }
                }
                });
                ScrollShow = new JScrollPane(list);
		        ShowPanel.add(ScrollShow);
		    ScrollShow.setSize(790, 520);
		    ScrollShow.setLocation(5, 5);
		    this.add(ShowPanel);
            TreePanel.setSize(190,610);
        TreePanel.setLocation(5, 10);
        TreePanel.setLayout(null); 
        filesTree = new FileTree();
        TreeShow = new JScrollPane(filesTree);
        TreeShow.setBounds(5, 5, 185, 520);
        TreePanel.add(TreeShow);
        this.add(TreePanel); 
        }
        public void doubleClick(String choice){
			choice += "\\";		
			File file = new File(Curr_URL + choice);
			if(file.isDirectory()){
				Curr_URL += choice;	
				stack.push(Curr_URL);
				Go();
			}
			else
			{
				Open(file);
			}
	    }

        public void Home_List(){
            List<String> Disks = DirectoryHelp.findDisk();
            defaultListModel = new DefaultListModel();
            for(int i = 0; i < Disks.size();i++){
                defaultListModel.addElement(Disks.get(i));
            }
            Icon[] icons = AccessIcon.getAllIcon("HOME");
            list.setModel(defaultListModel);
            list.setCellRenderer(new MyCellRenderer(icons));
            Curr_URL = "";
            stack.push(Curr_URL);
        }

        public void Open(File file){
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

        public void Go(){
			if(Curr_URL != ""){
				defaultListModel.clear();
				String[] getString = AccessFile.getSingleName(Currr_URL);		
				for(int i = 0; i < getString.length;i++){
					defaultListModel.addElement(getString[i]);		
				}	
				Icon[] icons = AccessIcon.getAllIcon(Cur_URL);
				list.setModel(defaultListModel);
				list.setCellRenderer(new MyCellRenderer(icons));
				
			}else{
				Home_List();
			}
	}
      
    public static void main(String[] args){
        MainFrame m = new MainFrame();     
	}

    @Override
	public void actionPerformed(ActionEvent e){
        if(e.getSource() == JMI1[0] || e.getSource() == JMI2[0]){
            String url = Curr_URL + list.getSelectedValue();
			if(Curr_URL != ""){
				url += "\\";
			}
				File file = new File(url);
			if(file.isDirectory()){
				doubleClick(url);
			}else{
				Open(file);				
			}
        }else if(e.getSource()==JMI1[1]){//New folder
            String name = JOptionPane.showInputDialog("Give name: ");
			String newdir=Curr_URL + "\\"+name;
			File file=new File(newdir);
			if(!file.exists()){
				file.mkdir();
			}
			JOptionPane.showMessageDialog(null,"Created Successfully","Created Successfully",JOptionPane.WARNING_MESSAGE);  
			Go();
        }else if(e.getSource()==JMI1[2]){//Copy
            CopyFileAndFolder.init();
			CopyFileAndFolder.generateDir(Curr_URL + "\\" + list.getSelectedValue());
        }else if(e.getSource()==JMI1[3]){//Paste
            String CopyFrom=CopyFileAndFolder.dir;
			if(CopyFileAndFolder.dirtype==1) { 
				String CopyTo=Curr_URL+"\\"+CopyFileAndFolder.fName;
				File file=new File(CopyTo);
				if(!file.exists())
				{
					try {file.createNewFile();}  
					catch (IOException e1) {}
				}
				try {PasteFileAndFolder.pasteFile(CopyFrom, CopyTo);} 
				catch (IOException e1) { }
				Go();
			}
			
			if(CopyFileAndFolder.dirtype==2) { 
				String CopyTo=Curr_URL+"\\"+CopyFileAndFolder.fName;
				try { PasteFileAndFolder.pasteFolder(CopyFrom, CopyTo);} 
				catch (IOException e1) {}
			}
			Go();
        }else if(e.getSource()==JMI1[4]){//Delete
            File file = new File(Curr_URL + "/" + list.getSelectedValue());
			int n;
            if(file.isFile()){
				n = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + file.getName() + " ?", "File Deletion",JOptionPane.YES_NO_OPTION);
			}else{
				n = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + file.getName() + " and all the files in it?", "Folder Deletion",JOptionPane.YES_NO_OPTION);
			}
			if(n == 0){
				DeleteFileAndFolder.delete(Curr_URL + list.getSelectedValue() +  "\\");
				Go();
			}
        }else if(e.getSource() == delete2){
            File file = new File(Curr_URL + "/" + list.getSelectedValue());
			int n;
			n = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + file.getName() + " ?", "File deletion",JOptionPane.YES_NO_OPTION);
			if(n == 0){
				DeleteFileAndFolder.delete(Curr_URL + list.getSelectedValue() +  "\\");
				Go();
			}
        }else if(e.getSource()==delete1){
            List<String> selected_str = list.getSelectedValuesList();
			File file;
			int num = selected_str.size();
			int n = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + selected_str.get(0) + " and " + num + " other items?", "File Deletion",JOptionPane.YES_NO_OPTION);
			if(n == 0){
					for(int i = 0; i < selected_str.size(); i++){
						DeleteFileAndFolder.delete(Curr_URL + selected_str.get(i) +  "\\");
					}		
					Go();
				}
        }else if(e.getSource()==JMI1[5]){//Rename
            String before = list.getSelectedValue();
			File file = new File(Curr_URL + before + "\\");
			String after = "";
			if(file.isDirectory()){
				after = (String) JOptionPane.showInputDialog(null, "Enter new name:\n", "Rename", JOptionPane.PLAIN_MESSAGE, null, null,
		                list.getSelectedValue());
			}else{
				after = (String) JOptionPane.showInputDialog(null, "Enter new file name:\n", "Rename", JOptionPane.PLAIN_MESSAGE, null, null,
		                list.getSelectedValue());
			}			
			if(before != after && after != null){
				new File(Curr_URL + before + "\\").renameTo(new File(Curr_URL + after + "\\"));
				Go();
			}else{
				Go();
			}
        }else if(e.getSource()==JMI1[6]){//Encrypt/decrypt
            String dir=Curr_URL + "\\" + list.getSelectedValue();
			String key = JOptionPane.showInputDialog("Enter the password ");
			LockAndUnlock.encryptFile(dir, Curr_URL, key);
			JOptionPane.showMessageDialog(null,"Done successfully","File Protection",JOptionPane.WARNING_MESSAGE);

			Go();
        }else if(e.getSource()==JMI1[7]){//compress
            String dir=Curr_URL + "\\" + list.getSelectedValue();
			try {
				ZipAndUnzip.zipDirectory(dir);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			Go();
        }else if(e.getSource()==zip)//compress/extract
		{
			List<String> selected_str = list.getSelectedValuesList();
			
			String zfname = JOptionPane.showInputDialog("Give name of folder: ");
			String zfolderURL= Curr_URL+"\\"+zfname;
			File zfolder=new File(zfolderURL);
			if(!zfolder.exists())
				zfolder.mkdir();
			
			for(int i = 0; i < selected_str.size();i++){
				CopyFileAndFolder.init();
				CopyFileAndFolder.generateDir(Curr_URL + "\\" + selected_str.get(i));
				String fromDir=CopyFileAndFolder.dir;
				if(CopyFileAndFolder.dirtype==1) {
					String toDir=zfolderURL+"\\"+CopyFileAndFolder.fName;
					File file=new File(toDir);
					if(!file.exists())
					{
						try {file.createNewFile();} 
						catch (IOException e1) {}
					}
					try {PasteFileAndFolder.pasteFile(fromDir, toDir);} 
					catch (IOException e1) { }
				}
				
				if(CopyFileAndFolder.dirtype==2) {
					String toDir=zfolderURL+"\\"+CopyFileAndFolder.fName;
					try { PasteFileAndFolder.pasteFolder(fromDir, toDir);} 
					catch (IOException e1) {}
				}
			}
			
			try {
				ZipAndUnzip.zipDirectory(zfolderURL);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(null,"Success","Compression",JOptionPane.WARNING_MESSAGE);  
			DeleteFileAndFolder.delete(zfolderURL);
			Go();
		}
		
		else if (e.getSource()==unzip)//extract
		{
			String dir=Curr_URL + "\\" + list.getSelectedValue();
			try {
				ZipAndUnzip.unzip(dir,Curr_URL);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			Go();
		}
    }
}
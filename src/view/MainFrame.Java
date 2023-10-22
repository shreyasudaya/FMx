package view;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

import controller.*;

import java.io.*;
import java.util.*;
import java.util.List;
import model.*;
/*Main View */

public class MainFrame extends JFrame implements ActionListener{
	public static MainFrame _instance;
	JPanel ShowPanel,TreePanel;
	FileTree filesTree;
	JScrollPane ScrollShow, TreeShow;
    DefaultMutableTreeNode node;
    public String Curr_URL = "";
	public Map<String, String> Maps = new HashMap<String,String>();
	static {
        try {
            _instance = new MainFrame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	JList<String> list;
	public DefaultListModel defaultListModel;
	public Stack<String> stack, stack_return;
	JPopupMenu jPopupMenu = null; 
	JPopupMenu jPopupMenu2 = null; 
	JPopupMenu jPopupMenu3 = null;
	JPopupMenu jPopupMenu4 = null; 
	JMenuItem[] JMIs = new JMenuItem[10];
	JMenuItem[] JMIs2 = new JMenuItem[5];
	JMenuItem delete = new JMenuItem("Delete"); 
	JMenuItem delete2 = new JMenuItem("Delete");
	JMenuItem zip=new JMenuItem("Compress");
	JMenuItem unzip=new JMenuItem("Extract");
	public Icon[] AllIcons = new Icon[999999];
	public int Icon_Counter = 0;
	
	public MainFrame(){
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
	
        stack = new Stack<String>();
        stack_return = new Stack<String>();
        ShowPanel.setSize(800, 610);
        ShowPanel.setLocation(190, 10);
        ShowPanel.setLayout(null);    
        list = new JList<String>();
        jPopupMenu = new JPopupMenu();
        jPopupMenu2 = new JPopupMenu();
        JMIs[0] = new JMenuItem("Open");
        JMIs[1] = new JMenuItem("Delete");
        JMIs[2] = new JMenuItem("Rename");
        JMIs[3] = new JMenuItem("New folder");
        JMIs[4] = new JMenuItem("Copy");
        JMIs[5] = new JMenuItem("Paste");
        JMIs[6] = new JMenuItem("Encrypt/Decrypt");
        JMIs[7] = new JMenuItem("Compress");
		JMIs[8] = new JMenuItem("Go Back");
        for(int i = 0; i < 9; i++){
        	JMIs[i].addActionListener(this);
        	jPopupMenu.add(JMIs[i]);            	
        }
        
        
        JMIs2[0] = new JMenuItem("Open");
        for(int i = 0; i < 1; i++){
        	JMIs2[i].addActionListener(this);
        	jPopupMenu2.add(JMIs2[i]);            	
        }    
        jPopupMenu3 = new JPopupMenu();
        delete.addActionListener(this);
        jPopupMenu3.add(delete);
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
        list.add(jPopupMenu);
        
        Home_List();
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				if(list.getSelectedIndex() != -1){
					if(e.getClickCount() == 1){
					
					}else if(e.getClickCount() == 2){
						System.out.println(list.getSelectedValue());
						twoClick(list.getSelectedValue());												
					}
					if(e.getButton() == 3){
						if(Curr_URL != ""){
							if(list.getSelectedValue().endsWith("zip"))
							{
								jPopupMenu4.show(list,e.getX(),e.getY());
							}
							else if(list.getSelectedValuesList().size() == 1){
								jPopupMenu.show(list,e.getX(),e.getY()); 
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
	
	public void twoClick(String choice){
			choice += "\\";		
			File file = new File(Curr_URL + choice);
			if(file.isDirectory()){
				Curr_URL += choice;	
				stack.push(Curr_URL);
				Go_There();
			}
			else
			{
				OpenIt(file);
			}
	}
	
	public void Home_List(){
		List<String> Disks = DirectoryHelp.findDisk();
		defaultListModel = new DefaultListModel();
		for(int i = 0; i < Disks.size(); ++i){
			defaultListModel.addElement(Disks.get(i));
		}
		Icon[] icons = AccessIcon.getAllIcon("HOME");
		list.setModel(defaultListModel);
		list.setCellRenderer(new MyCellRenderer(icons));
		Curr_URL = "";
		stack.push(Curr_URL);
	}
	
	public void OpenIt(File file){
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void Go_There(){
			if(Curr_URL != ""){
				defaultListModel.clear();
				String[] getString = AccessFile.getSingleName(Curr_URL);		
				for(int i = 0; i < getString.length; ++i){
					defaultListModel.addElement(getString[i]);		
				}	
				Icon[] icons = AccessIcon.getAllIcon(Curr_URL);
				list.setModel(defaultListModel);
				list.setCellRenderer(new MyCellRenderer(icons));
				
			}else{
				Home_List();
			}
	}	
	public void Go_Back() {
		if (!stack.isEmpty()) {
			stack_return.push(Curr_URL);
			Curr_URL = stack.pop();
			Go_There();
		}
	}
			
	public static void main(String[] args) {
        MainFrame m = new MainFrame();     
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == JMIs[0] || e.getSource() == JMIs2[0]){	
			String url = Curr_URL + list.getSelectedValue();
			if(Curr_URL != ""){
				url += "\\";
			}
				File file = new File(url);
			if(file.isDirectory()){
				twoClick(url);
			}else{
				OpenIt(file);				
			}			
		}else if (e.getSource() == JMIs[8]) {
            Go_Back();
        }
		
		else if(e.getSource() == JMIs[1]){//Delete
			File file = new File(Curr_URL + "/" + list.getSelectedValue());
			int n;
			if(file.isFile()){
				n = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + file.getName() + " ?", "File Deletion",JOptionPane.YES_NO_OPTION);
			}else{
				n = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + file.getName() + " and it's existing components?", "Folder Deletion",JOptionPane.YES_NO_OPTION);
			}
			if(n == 0){
				DeleteFileAndFolder.delete(Curr_URL + list.getSelectedValue() +  "\\");
				Go_There();
			}			
		}
		
		else if(e.getSource() == delete2){
			File file = new File(Curr_URL + "/" + list.getSelectedValue());
			int n;
			n = JOptionPane.showConfirmDialog(null, "ARe you sure you want to delete " + file.getName() + " ?", "File Deletion",JOptionPane.YES_NO_OPTION);
			if(n == 0){
				DeleteFileAndFolder.delete(Curr_URL + list.getSelectedValue() +  "\\");
				Go_There();
			}			
		}
		
		
		
		else if(e.getSource() == delete){
			List<String> selected_str = list.getSelectedValuesList();
			File file;
			int num = selected_str.size();
			int n = JOptionPane.showConfirmDialog(null, "Delete " + selected_str.get(0) + " and " + num + "other items?", "Delete Files",JOptionPane.YES_NO_OPTION);
			if(n == 0){
					for(int i = 0; i < selected_str.size(); ++i){
						DeleteFileAndFolder.delete(Curr_URL + selected_str.get(i) +  "\\");
					}		
					Go_There();
				}
			}						
		
		else if(e.getSource() == JMIs[2]){//Rename
			String before = list.getSelectedValue();
			File file = new File(Curr_URL + before + "\\");
			String after = "";
			if(file.isDirectory()){
				after = (String) JOptionPane.showInputDialog(null, "Enter the new Name:\n", "Renaming", JOptionPane.PLAIN_MESSAGE, null, null,
		                list.getSelectedValue());
			}else{
				after = (String) JOptionPane.showInputDialog(null, "Enter New Name:\n", "Renaming", JOptionPane.PLAIN_MESSAGE, null, null,
		                list.getSelectedValue());
			}			
			if(before != after && after != null){
				new File(Curr_URL + before + "\\").renameTo(new File(Curr_URL + after + "\\"));
				Go_There();
			}else{
				Go_There();
			}
		}
		
		else if(e.getSource() == JMIs[3]){//New
			String name = JOptionPane.showInputDialog("Give name: ");
			String newdir=Curr_URL + "\\"+name;
			File file=new File(newdir);
			if(!file.exists()){
				file.mkdir();
			}
			JOptionPane.showMessageDialog(null,"Created Successfully","Creation",JOptionPane.PLAIN_MESSAGE);  
			Go_There();
		}
		
		else if(e.getSource() == JMIs[4]){//Copy
			CopyFileAndFolder.init();
			CopyFileAndFolder.generateDir(Curr_URL + "\\" + list.getSelectedValue());
		}
		
		else if(e.getSource() == JMIs[5])  {//Paste
			String fromDir=CopyFileAndFolder.dir;
			if(CopyFileAndFolder.dirtype==1) {
				String toDir=Curr_URL+"\\"+CopyFileAndFolder.fName;
				File file=new File(toDir);
				if(!file.exists())
				{
					try {file.createNewFile();} 
					catch (IOException e1) {}
				}
				try {PasteFileAndFolder.pasteFile(fromDir, toDir);} 
				catch (IOException e1) { }
				Go_There();
			}
			
			if(CopyFileAndFolder.dirtype==2) { 
				String toDir=Curr_URL+"\\"+CopyFileAndFolder.fName;
				try { PasteFileAndFolder.pasteFolder(fromDir, toDir);} 
				catch (IOException e1) {}
			}
			Go_There();
		}
		
		else if(e.getSource() == JMIs[6]){//Encrypt
			String dir=Curr_URL + "\\" + list.getSelectedValue();
			String key = JOptionPane.showInputDialog("Give Password: ");
			LockAndUnlock.encryptFile(dir, Curr_URL, key);
			JOptionPane.showMessageDialog(null,"Done","Cryptocrypty",JOptionPane.PLAIN_MESSAGE);  
			Go_There();
		}
		
		else if(e.getSource()==JMIs[7])//Zip
		{
			String dir=Curr_URL + "\\" + list.getSelectedValue();
			try {
				ZipAndUnzip.zipDirectory(dir);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			Go_There();
		}
		
		else if(e.getSource()==zip)
		{
			List<String> selected_str = list.getSelectedValuesList();
			
			String zfname = JOptionPane.showInputDialog("File to zip?");
			String zfolderURL= Curr_URL+"\\"+zfname;
			File zfolder=new File(zfolderURL);
			if(!zfolder.exists())
				zfolder.mkdir();
			
			for(int i = 0; i < selected_str.size(); ++i){
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
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(null,"Success","Compression",JOptionPane.PLAIN_MESSAGE);  
			
			Go_There();
		}
		
		else if (e.getSource()==unzip)
		{
			String dir=Curr_URL + "\\" + list.getSelectedValue();
			try {
				ZipAndUnzip.unzip(dir,Curr_URL);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			Go_There();
		}
	}
}


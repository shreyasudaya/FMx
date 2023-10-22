package model;


import model.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;
import controller.*;
import view.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.*;
import java.util.*;

public class FileTree extends JPanel{
    protected static FileSystemView fsv = FileSystemView.getFileSystemView();
    public static class FileTreeCellRenderer extends DefaultTreeCellRenderer {
        private Map<String,Icon> iconCache=new HashMap<String,Icon>();
        private Map<File,String> rootNameCache=new HashMap<File,String>();
        public Icon[] GetFileIcons(String path){
            Icon[] icons=null;
            String[] files=AccessFile.getSingleName(path);
            for(int i=0;i<files.length;i++){
                icons[i]=this.iconCache.get(files[i]);
            }
            return icons;
        }

        @Override
        public Component getTreeCellRenderComponent(JTree tree,Object value,boolean self, boolean expanded, boolean leaf,int row,boolean hasFocus){
            FileTreeNode ftn=(FileTreeNode) value;
            File file=ftn.file;
            String filename="";
            if(file!=null){
                if(ftn.isFileSystemRoot){
                    filename=this.rootNameCache.get(file);
                    if(filename==null){
                        filename=fsv.getSystemDisplayName(file);
                        this.rootNameCache.put(file,filename);
                    }
                }
                else{
                    filename=file.getName();
                }
            }
            JLabel result=(JLabel) super.getTreeCellRenderComponent(tree,filename,self,expanded,leaf,row,hasFocus);
            if(file!=null){
                Icon icon=this.iconCache.get(filename);
                if(icon==null){
                    icon=fsv.getSystemIcon(file);
                    this.iconCache.put(filename,icon);
                }
                result.setIcon(icon);
            }
            return result;
        }
    }
    //Make file system a tree
    private static class FileTreeNode implements TreeNode{
        private File file;
        private File[] children;
        private TreeNode parent;
        private boolean isFileSystemRoot;//for checking root dir
        public FileTreeNode(File file,boolean isFileSystemRoot,TreeNode parent){
            //initialize for one child node
            this.file=file;
            this.isFileSystemRoot = isFileSystemRoot;
            this.parent = parent;
            this.children = this.file.listFiles();
            if (this.children == null)
                this.children = new File[0];
        }
        public String toString(){
            if(file!=null){
                return file.getAbsolutePath();
            }
            else{
                return "";
            }
        }
        //new node with files
        public FileTreeNode(File[] children){
            this.file=null;
            this.parent=null;
            this.children=children;
        }
        public Enumeration<?> children(){
            final int ele_count=this.children.length;
            return new Enumeration<File>(){
                int count=0;
                public boolean hasMoreElements(){
                    return this.count<ele_count;
                }
                public File nextElement(){
                    if(this.count<ele_count){
                        return FileTreeNode.this.children[this.count++];

                    }
                    throw new NotFoundException("Vector Enumeration");
                }
            };
        }
        //permission from children
        public boolean Childrenpermission(){
            return true;
        }
        public TreeNode getchild(int childIndex){
            return new FileTreeNode(this.children[childIndex],this.parent==null,this);
            
        }
        public int getChildrenCount(){
            return this.children.length;
        }
        public int getIndex(TreeNode node){
            FileTreeNode ftn=(FileTreeNode) node;
            for(int i=0;i<this.children.length;i++){
                if(ftn.file.equals(this.children[i]))
                    return i;
            }
            return -1;
        }

        public TreeNode getParent(){
            return this.parent;
        }
        public boolean isLeaf(){
            return (this.getChildrenCount()==0);
        }
    }

    private Jtree tree;

    public FileTree(){
        this.setLayout(new GridLayout());
        File[] roots=File.listRoots();
        FileTreeNode rootnode=new FileTreeNode(roots);
        this.tree=new JTree(rootnode);
        this.tree.setCellRenderer(new FileTreeCellRenderer());
        this.tree.setRootVisible(false);
        this.tree.addTreeSelectionListener(new TreeSelectionListener(){
                public void valueChanged(TreeSelectionEvent e){
                    if(tree.getLastSelectedPathComponent()==null) return;
                    String chooseUrl=tree.getLastSelectedPathComponent().toString();
                    File temp_file=new File(chooseUrl);
                    if(temp_file.isDirectory()){
                        MainFrame._instance.stack.push(MainFrame._instance.CURR_URL);
                        MainFrame._instance.Curr_URL=chooseUrl;
                        MainFrame._instance.Go_There();

                    }
                }
        });
        final JScrollPane jsp=new JscrollPane(this.tree);
        jsp.setBorder(new EmptyBorder(0,0,0,0));
        this.add(jsp.BorderLayout.CENTER);
    }
    
    //main
    public static void main(String[] args){
        SwingUtlities.invokeLater(new Runnable(){
            public void run(){
                JFrame frame=new JFrame("File Tree");
                frame.setSize(500,400);
                frame.setLocationRelativeto(null);
                frame.add(new FileTree());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }

}
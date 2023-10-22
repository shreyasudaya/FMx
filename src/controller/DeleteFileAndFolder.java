package controller;

import java.io.File;

import javax.swing.JOptionPane;


public class DeleteFileAndFolder 
{	
	public static boolean delete(String fileName) 
	{
        File file = new File(fileName);
        if (!file.exists()) 
        {
        	JOptionPane.showMessageDialog(null,fileName+" does not exist.","Not found",JOptionPane.WARNING_MESSAGE);  
            return false; 
        } 
        else 
        {
            if (file.isFile()) 
                return deleteFile(fileName);
            else
                return deleteFolder(fileName);
        }
    }
	
	public static boolean deleteFile(String fileName)  
	{
        File file = new File(fileName);
        if (file.exists() && file.isFile())
        {
            if (file.delete())
            {
            	JOptionPane.showMessageDialog(null,fileName,"Deleted successfully",JOptionPane.WARNING_MESSAGE);  
                return true;
            } 
            else 
            {
            	JOptionPane.showMessageDialog(null,fileName,"Deletion Failed",JOptionPane.ERROR_MESSAGE);  
                return false;
            }
        } 
        else
        {
        	JOptionPane.showMessageDialog(null,fileName +" does not exist","Not Found",JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }
	
	public static boolean deleteFolder(String dir) 
	{
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator; 
        File dirFile = new File(dir);
        if ((!dirFile.exists()) || (!dirFile.isDirectory()))
        {
        	JOptionPane.showMessageDialog(null,dir+"does not exist", "Not found",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        boolean flag = true;
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) 
        {
            if (files[i].isFile())
            {
                flag = DeleteFileAndFolder.deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            else if (files[i].isDirectory()) 
            {
                flag=DeleteFileAndFolder.deleteFolder(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) 
        {
        	JOptionPane.showMessageDialog(null,"Deletion failed","Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (dirFile.delete()) {
        	JOptionPane.showMessageDialog(null,"Deleted Successfuly", "Deleted",JOptionPane.WARNING_MESSAGE);//��������ʾ
            return true;
        } 
        else 
        {
            return false;
        }
    }
}

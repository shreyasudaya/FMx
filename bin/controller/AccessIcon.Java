package controller;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import java.io.*;
import java.util.*;

public class AccessIcon {
	public static Icon getSingleIcon(String path)
	{
		FileSystemView fview=FileSystemView.getFileSystemView();
		File f= new File(path);
		Icon i=fview.getSystemIcon(f); 
		return i;
	}
	
	public static Icon[] getAllIcon(String path)
	{
		Icon[] icons=new Icon[1000000]; 
		int i=0;
		if(path=="HOME")
		{
			List<String> Disks=DirectoryHelp.findDisk();
			for(int j=0;i<Disks.size();j++)
			{
				FileSystemView fsv = FileSystemView.getFileSystemView();
				File file = new File(Disks.get(j) + "\\"); 
				icons[i++]=fsv.getSystemIcon(file); 
			}
		}
		else
		{
			File file = new File(path); 
			File[] files = file.listFiles(); 
			for(File a:files) 
			{ 
				if(a!=null && a.exists()) 
				{ 
					FileSystemView fsv= FileSystemView.getFileSystemView();
					icons[i++]=fsv.getSystemIcon(a);
				} 
			} 
		} 
		return icons; 
	}
}
package controller;

import java.io.*;
import java.util.*;

public class AccessFile 
{
	public static String[] getSingleName(String path)
	{
		File file = new File (path);
		String[] fileName=file.list();
		return fileName;
	}
	
	public static void getAllName(String path, ArrayList<String> fileName)
	{
		File file = new File(path);
        File [] files = file.listFiles();
        String [] names = file.list();
        if(names != null)
        	fileName.addAll(Arrays.asList(names));
        for(File a:files)
        {
            if(a.isDirectory())
            {
                getAllName(a.getAbsolutePath(),fileName);
            }
        }
	}
}
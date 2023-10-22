package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;



public class DirectoryHelp 
{
	public static List<String> findDisk() 
	{  	  	  
        List<String> list = new ArrayList<String>(); 
        for (char c = 'A'; c <= 'Z'; c++) {  
            String dirName = c + ":";  
            File win = new File(dirName);  
            if (win.exists()) {  
                String str = c + ":";  
                list.add(str);  
            }  
        }  
        return list;  
    }  
}

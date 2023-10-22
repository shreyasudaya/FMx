package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
    //Encryption
    public static void encryptFile(String fileDir , String folderDir , String key)
	{
        FileInputStream in = null;
        FileOutputStream out = null; 
        int keyword=Integer.parseInt(key); 
        File file=new File(fileDir); 
        File desfile=new File(folderDir+"\\temp");
        try {
            String sourceFileUrl = fileDir;
            String targetFileUrl = folderDir+"\\temp";
            in = new FileInputStream(sourceFileUrl);
            out = new FileOutputStream(targetFileUrl);
            int data = 0;
            while ((data=in.read())!=-1){
                out.write(data^keyword); 
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        file.delete();
        desfile.renameTo(new File(fileDir));
	}
}
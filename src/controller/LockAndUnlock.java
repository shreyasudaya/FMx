package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class LockAndUnlock
{
	/**
	 * @param fileDir
	 * @param folderDir
	 * @param key
	 */
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

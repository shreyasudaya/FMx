package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JOptionPane;

public class CopyPasteFileAndFolder {
	public static int dirtype;
	public static CopyFileAndFolder _inst; 
	public static String dir;
	public static String fName;
	
	public static void init()
	{
		dirtype=0;
		dir="";
	}
	
	public static void generateDir(String dir1)
	{
		File file=new File(dir1);
		dir=dir1;
		if(file.isFile()) {
			dirtype=1;
			File tempFile =new File( dir.trim());
			fName = tempFile.getName();
		}
		if(file.isDirectory()) {
			dirtype=2;
			File tempFile =new File( dir.trim());
			fName = tempFile.getName();
		}
		
		
	}

	/*Pasting file and folder verfercferc
	 * cercercercercer
	 * this is so avoid confussion
	 * swecwecw
	 * cwecwecwc
	 * #MBAforVishwas
	*/
	public static void pasteFile(String fromDir , String toDir) throws IOException
	{
		FileInputStream in = new FileInputStream(fromDir);
		FileOutputStream out = new FileOutputStream(toDir);
		byte[] bs = new byte[1*1024*1024];	
		int count = 0;
		while((count = in.read(bs))!=-1){ 
			out.write(bs,0,count);
		}
		in.close();
		out.flush();
		out.close();
	}
	
	public static void pasteFolder(String fromDir,String toDir) throws IOException
	{
			File dirSouce = new File(fromDir);
			if (!dirSouce.isDirectory()) {
				return;
			}
			File destDir = new File(toDir);	
			if(!destDir.exists()){
				destDir.mkdir();
			}
			
			File[]files = dirSouce.listFiles();
			for (File file : files) {
				
				String strFrom = fromDir + File.separator + file.getName();
				System.out.println(strFrom);
				String strTo = toDir + File.separator + file.getName();
				System.out.println(strTo);
				
				if (file.isDirectory()){
					pasteFolder(strFrom,strTo);
				}
				if (file.isFile()) {
					System.out.println("Copying files："+file.getName());
					
					pasteFile(strFrom,strTo);
				}
			}
		}
	
}
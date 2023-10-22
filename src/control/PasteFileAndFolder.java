package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class PasteFileAndFolder {
	
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
				if (file.isDirectory()) {
					pasteFolder(strFrom,strTo);
				}
				if (file.isFile()) {
					System.out.println("Copying Files: "+file.getName());
					
					pasteFile(strFrom,strTo);
				}
			}
		}

	
}

package controller;

import java.io.File;

public class CopyFileAndFolder {
	public static int dirtype;
	public static CopyFileAndFolder _inst;
	public static String dir;
	public static String fName;

	public static void init() {
		dirtype = 0;
		dir = "";
	}

	public static void generateDir(String dir1) {
		File file = new File(dir1);
		dir = dir1;
		if (file.isFile()) {
			dirtype = 1;
			File tempFile = new File(dir.trim());
			fName = tempFile.getName();
		}
		if (file.isDirectory()) {
			dirtype = 2;
			File tempFile = new File(dir.trim());
			fName = tempFile.getName();
		}

	}
}

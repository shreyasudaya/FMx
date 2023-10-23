package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipEntry;
import javax.swing.JOptionPane;

public class ZipAndUnzip {

    public static void zipDirectory(String path) throws IOException {
        File file = new File(path);
        String parent = file.getParent();
        File zipFile = new File(parent, file.getName() + ".zip");
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
        zip(zos, file, file.getName());
        JOptionPane.showMessageDialog(null, "Compression Successful", "Success", JOptionPane.WARNING_MESSAGE);
        zos.flush();
        zos.close();
    }

    private static void zip(ZipOutputStream zos, File file, String path) throws IOException {
        if (file.isDirectory()) {
            ZipEntry entry = new ZipEntry(path + File.separator);
            zos.putNextEntry(entry);
            File[] files = file.listFiles();
            for (File x : files) {
                zip(zos, x, path + File.separator + x.getName());
            }
        } else {
            FileInputStream fis = new FileInputStream(file);
            ZipEntry entry = new ZipEntry(path);
            zos.putNextEntry(entry);

            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = fis.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            zos.flush();
            fis.close();
            zos.closeEntry();
        }
    }

    public static void unzip(String fileName, String path) {

        JOptionPane.showMessageDialog(null, "Extraction Successful", "Success", JOptionPane.WARNING_MESSAGE);
    }
}

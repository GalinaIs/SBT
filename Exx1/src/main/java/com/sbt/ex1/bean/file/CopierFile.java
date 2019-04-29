package com.sbt.ex1.bean.file;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class CopierFile implements Runnable {
    private static final int COPY_BUFFER_SIZE = 64 * 1024;
    private final File fileSrc;
    private final File fileDst;
    private final JLabel labelChooseFile;
    private final boolean isDeleteFileSrc;

    public CopierFile(File fileSrc, File fileDst, JLabel labelChooseFile, boolean isDeleteFileSrc) {
        this.fileSrc = fileSrc;
        this.fileDst = fileDst;
        this.labelChooseFile = labelChooseFile;
        this.isDeleteFileSrc = isDeleteFileSrc;
    }

    public CopierFile(File fileSrc, File fileDst) {
        this(fileSrc, fileDst, null, true);
    }

    @Override
    public void run() {
        try {
            copyDir(fileSrc, fileDst);
            if (labelChooseFile != null) printMessageInLabel("File successfully send", Color.black);
        } catch (IOException e) {
            if (labelChooseFile != null) printMessageInLabel("Error with sending file", Color.red);
            e.printStackTrace();
        }
    }

    private void copyDir(File fileSrc, File fileDst) throws IOException {
        if (fileSrc.isDirectory()) {
            if (!fileDst.exists())
                fileDst.mkdir();

            File[] fileSrcList = fileSrc.listFiles();
            if (fileSrcList == null) return;
            for (File scrSubDir : fileSrcList) {
                String subDirName = scrSubDir.getName();
                copyDir(scrSubDir, new File(fileDst, subDirName));
                if (isDeleteFileSrc) scrSubDir.delete();
            }
        } else if(fileSrc.isFile()) {
            try {
                fileDst.createNewFile();
                copyFile(fileSrc, fileDst);
                if (isDeleteFileSrc) fileSrc.delete();
            } catch (IOException ex) {
                throw new IOException("Exception with try createNewFile " + fileDst, ex);
            }
        }
    }

    private void printMessageInLabel(String message, Color color) {
        EventQueue.invokeLater(() -> {
            labelChooseFile.setText(message);
            labelChooseFile.setForeground(color);
        });
    }

    private void copyFile(File fileSrc, File fileDst) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(fileSrc);
             FileOutputStream fileOutputStream = new FileOutputStream(fileDst, false)) {
            byte[] buffer = new byte[COPY_BUFFER_SIZE];
            int count;
            while ((count = fileInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, count);
            }
        } catch (IOException ex) {
            throw new IOException("Exception when copy file " + fileSrc + " to file " + fileDst, ex);
        }
    }
}

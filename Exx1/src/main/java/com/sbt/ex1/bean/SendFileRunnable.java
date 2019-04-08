package com.sbt.ex1.bean;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class SendFileRunnable implements Runnable{
    private static final int COPY_BUFFER_SIZE = 64 * 1024;
    private final File fileSrc;
    private final File fileDst;
    private final JLabel labelChooseFile;
    private final boolean isDeleteFileSrc;

    public SendFileRunnable(File fileSrc, File fileDst, JLabel labelChooseFile, boolean isDeleteFileSrc) {
        this.fileSrc = fileSrc;
        this.fileDst = fileDst;
        this.labelChooseFile = labelChooseFile;
        this.isDeleteFileSrc = isDeleteFileSrc;
    }

    public SendFileRunnable(File fileSrc, File fileDst) {
        this(fileSrc, fileDst, null, true);
    }

    @Override
    public void run() {
        try {//доделать уникальные имена
            if (fileDst.createNewFile()) {
                copyFile(fileSrc, fileDst);
                System.out.println("ok");
                if (labelChooseFile != null) {
                    EventQueue.invokeLater(() -> {
                        labelChooseFile.setText("File successfully send");
                        labelChooseFile.setForeground(Color.black);
                    });
                }
                if (isDeleteFileSrc) {
                    fileSrc.delete();
                }
            } else {
                if (labelChooseFile != null) {
                    EventQueue.invokeLater(() -> {
                        labelChooseFile.setText("Error with send file");
                        labelChooseFile.setForeground(Color.red);
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

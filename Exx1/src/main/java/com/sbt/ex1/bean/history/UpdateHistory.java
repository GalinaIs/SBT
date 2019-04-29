package com.sbt.ex1.bean.history;

import java.awt.*;
import java.io.*;

public class UpdateHistory implements Runnable {
    private File fileMonitor;
    private TextArea textAreaHistory;
    private RecordHistory recordHistory;

    public UpdateHistory(File fileMonitor, RecordHistory recordHistory, TextArea textAreaHistory) {
        this.fileMonitor = fileMonitor;
        this.recordHistory = recordHistory;
        this.textAreaHistory = textAreaHistory;
    }

    @Override
    public void run() {
        try {
            writeFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        updateLabelText();
    }

    private void updateLabelText() {
        EventQueue.invokeLater(() -> {
            StringBuilder textAreaText = new StringBuilder(textAreaHistory.getText());
            textAreaText.append(recordHistory.getDate());
            textAreaText.append(recordHistory.getFilename());
            textAreaText.append(recordHistory.getTypeModify());
            textAreaText.append(recordHistory.getSeparator());
            textAreaHistory.setText(textAreaText.toString());
        });
    }

    private void writeFile() throws IOException {
        try (FileWriter outStream = new FileWriter(fileMonitor, true)) {
            outStream.write(recordHistory.getDate());
            outStream.write(recordHistory.getFilename());
            outStream.write(recordHistory.getTypeModify());
            outStream.write(recordHistory.getSeparator());
        } catch (IOException ex) {
            throw new IOException("Exception with writing data in file " + fileMonitor, ex);
        }
    }
}

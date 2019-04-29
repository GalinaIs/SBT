package com.sbt.ex1.bean;

import com.sbt.ex1.bean.config.Config;
import com.sbt.ex1.bean.file.*;
import com.sbt.ex1.bean.history.ClearHistory;
import com.sbt.ex1.bean.watcher.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.concurrent.*;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

public class App {
    private static Config config;
    private static Thread watchDirectoryService;
    private static Thread watcherInDirectory;
    private static TextArea textAreaHistory;
    private static JLabel labelChooseFile;
    private static File fileForSend;
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    public static void init() {
        initFrame();
        config = Config.getInstance();
        runWatcherInDirectory(false);
    }

    private static void runWatcherOutDirectory(boolean isInterrupted) {
        if (isInterrupted) watchDirectoryService.interrupt();

        WatchOutDirectory watcher = new WatchOutDirectory(config.getDirectoryOut(), config.getFileHistory(),
                textAreaHistory);
        watchDirectoryService = new Thread(watcher);
        watchDirectoryService.setDaemon(true);
        watchDirectoryService.start();
    }

    private static void runWatcherInDirectory(boolean isInterrupted) {
        if (isInterrupted) watcherInDirectory.interrupt();

        watcherInDirectory = new Thread(new WatcherInDirectory(config.getDirectoryIn(), config.getDirectoryOut()));
        watcherInDirectory.setDaemon(true);
        watcherInDirectory.start();
    }

    private static void initFrame() {
        EventQueue.invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            JFrame frame = new AppFrame();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            runWatcherOutDirectory(false);
        });
    }

    static class AppFrame extends JFrame {
        AppFrame() {
            createComponentFrame();

            addWindowListenerFrame();

            JMenuBar menuBar = new JMenuBar();
            menuBar.add(createViewMenu());
            setJMenuBar(menuBar);

            setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            setPreferredSize(new Dimension(700, 700));
            setTitle("Application for working with folders in and out");
            pack();
        }

        private void chooseFileDialog() {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(config.getDirectoryIn());
            String dialogTitle = "Change file to send";
            fileChooser.setDialogTitle(dialogTitle);
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                String message = "File for send selected: ";

                fileForSend = fileChooser.getSelectedFile();
                labelChooseFile.setText(fileForSend.getName());
                labelChooseFile.setForeground(Color.black);
                JOptionPane.showMessageDialog(null, message + fileForSend);
            }
        }

        private void createComponentFrame() {
            JLabel labelHistoryChange = new JLabel("History change directory");
            labelChooseFile = new JLabel();
            textAreaHistory = new TextArea(30, 10);
            textAreaHistory.setFont(new Font("Dialog", Font.PLAIN, 16));
            try {
                textAreaHistory.setText(ReaderFile.readFile(config.getFileHistory()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            textAreaHistory.setEditable(false);
            JButton btnChooseFile = new JButton("Choose file for send");
            btnChooseFile.setMinimumSize(new Dimension(150, 50));
            btnChooseFile.addActionListener(e -> chooseFileDialog());
            JButton btnSendFile = new JButton("Send file");
            btnSendFile.addActionListener(e -> sendFileToOutDirectory());
            btnSendFile.setMinimumSize(new Dimension(150, 50));

            createComponentLayoutFrame(labelHistoryChange, textAreaHistory, labelChooseFile, btnChooseFile, btnSendFile);
        }

        private void sendFileToOutDirectory() {
            File fileSrc = new File(config.getDirectoryOut() + File.separator + fileForSend.getName());
            executor.submit(new CopierFile(fileForSend, fileSrc, labelChooseFile, false));
        }

        private void createComponentLayoutFrame(JLabel labelHistoryChange, TextArea textAreaHistory,
                                                JLabel labelChooseFile, JButton btnChooseFile, JButton btnSendFile) {
            GroupLayout layout = new GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);

            layout.setHorizontalGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(LEADING)
                            .addComponent(labelHistoryChange)
                            .addComponent(textAreaHistory)
                    )
                    .addGroup(layout.createParallelGroup(LEADING)
                            .addComponent(labelChooseFile)
                            .addComponent(btnChooseFile)
                            .addComponent(btnSendFile)
                    )
            );


            layout.setVerticalGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(BASELINE)
                            .addComponent(labelHistoryChange)
                            .addComponent(labelChooseFile)
                    )
                    .addGroup(layout.createParallelGroup(LEADING)
                            .addComponent(textAreaHistory)
                            .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnChooseFile)
                                    .addComponent(btnSendFile)
                            )
                    )
            );
        }

        private void addWindowListenerFrame() {
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent event) {
                    Object[] options = {"Yes", "No"};
                    int exitDialog = JOptionPane.showOptionDialog(
                            event.getWindow(), "Do you want to exit from application?",
                            "Confirm Exit", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null, options, options[0]);
                    if (exitDialog == 0) {
                        event.getWindow().setVisible(false);
                        System.exit(0);
                    }
                }
            });
        }

        private void changeInDirectory(File file) {
            config.setDirectoryIn(file);

            runWatcherInDirectory(true);
        }

        private void changeOutDirectory(File file) {
            textAreaHistory.setText("");
            config.setDirectoryOut(file);

            runWatcherOutDirectory(true);
            runWatcherInDirectory(true);

            executor.submit(new ClearHistory(config.getFileHistory()));
        }

        private boolean isAvailableChangeDirectory(JFileChooser fileChooser, boolean isInDirectory) {
            return isInDirectory ?
                    !fileChooser.getSelectedFile().getAbsolutePath().
                            equals(config.getDirectoryOut().getAbsolutePath()) :
                    !fileChooser.getSelectedFile().getAbsolutePath().
                            equals(config.getDirectoryIn().getAbsolutePath());
        }

        private void changeDirectoryDialog(boolean isInDirectory) {
            JFileChooser fileChooser = new JFileChooser();
            if (isInDirectory)
                fileChooser.setCurrentDirectory(config.getDirectoryIn());
            else
                fileChooser.setCurrentDirectory(config.getDirectoryOut());

            String dialogTitle = isInDirectory ? "Change new in directory" : "Change new out directory";
            fileChooser.setDialogTitle(dialogTitle);
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                String message = isInDirectory ? "Directory in change on " : "Directory out change on ";

                if (isAvailableChangeDirectory(fileChooser, isInDirectory)) {
                    JOptionPane.showMessageDialog(null,
                            message + fileChooser.getSelectedFile());

                    if (isInDirectory) {
                        changeInDirectory(fileChooser.getSelectedFile());
                    } else {
                        changeOutDirectory(fileChooser.getSelectedFile());
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "DirectoryIn and directoryOut must be different. Current directoryIn: " +
                                    config.getDirectoryIn() + " directoryOut: " + config.getDirectoryOut());
                }
            }
        }

        private JMenu createViewMenu() {
            JMenu menu = new JMenu("Change directory in and out");
            JMenuItem inDir = new JMenuItem("Change directory in");
            JMenuItem outDir = new JMenuItem("Change directory out");
            JMenuItem clearHistory = new JMenuItem("Clear history");
            menu.add(inDir);
            menu.addSeparator();
            menu.add(outDir);
            menu.addSeparator();
            menu.add(clearHistory);

            inDir.addActionListener(e -> {
                changeDirectoryDialog(true);
            });
            outDir.addActionListener(e -> {
                changeDirectoryDialog(false);
            });
            clearHistory.addActionListener(e -> {
                textAreaHistory.setText("");
                JOptionPane.showMessageDialog(null, "History has been cleared");
                executor.submit(new ClearHistory(config.getFileHistory()));
            });

            return menu;
        }
    }
}

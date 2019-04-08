package com.sbt.ex1.bean;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.regex.*;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

public class App {
    private static final File FILE_CONFIG = new File("D:\\java\\golovach\\Exx1\\src\\main\\resources\\config.txt");
    private static final String NAME_PARAMETER_DIRECTORY_IN = "DirectoryIn";
    private static final String NAME_PARAMETER_DIRECTORY_OUT = "DirectoryOut";
    private static final String NAME_PARAMETER_FILE_HISTORY = "FileHistory";
    private static File fileHistory;
    private static File directoryIn;
    private static File directoryOut;
    private static Thread watchDirectoryService;
    private static Thread watcherInDirectory;
    private static TextArea textAreaHistory;
    private static JLabel labelChooseFile;
    private static File fileForSend;

    public static void init() throws IOException {
        initFrame();
        initApp();
        startWatcherInDirectory(false);
    }

    private static void initApp() throws IOException {
        Pattern patternFileHistory = Pattern.compile(NAME_PARAMETER_FILE_HISTORY + "\\s(.*)");
        Pattern patternDirectoryIn = Pattern.compile(NAME_PARAMETER_DIRECTORY_IN + "\\s(.*)");
        Pattern patternDirectoryOut = Pattern.compile(NAME_PARAMETER_DIRECTORY_OUT + "\\s(.*)");
        try (FileReader inStream = new FileReader(FILE_CONFIG);
             BufferedReader bufferedReader = new BufferedReader(inStream)) {
            String line = bufferedReader.readLine();

            while (line != null) {
                Matcher matcherFileHistory = patternFileHistory.matcher(line);
                if (matcherFileHistory.find()) fileHistory = new File(matcherFileHistory.group(1));

                Matcher matcherDirectoryIn = patternDirectoryIn.matcher(line);
                if (matcherDirectoryIn.find()) directoryIn = new File(matcherDirectoryIn.group(1));

                Matcher matcherDirectoryOut = patternDirectoryOut.matcher(line);
                if (matcherDirectoryOut.find()) directoryOut = new File(matcherDirectoryOut.group(1));

                line = bufferedReader.readLine();
            }

            if (fileHistory == null || directoryIn == null || directoryOut == null)
                throw new IOException();
        } catch (IOException ex) {
            throw new IOException("Error with try read data from file " + FILE_CONFIG, ex);
        }
    }

    private static void startWatcherOutDirectory(boolean isInterrupted) {
        if (isInterrupted) watchDirectoryService.interrupt();

        watchDirectoryService = new Thread(new WatchOutDirectory(directoryOut, fileHistory, textAreaHistory));
        watchDirectoryService.setDaemon(true);
        watchDirectoryService.start();
    }

    private static void startWatcherInDirectory(boolean isInterrupted) {
        if (isInterrupted) watcherInDirectory.interrupt();

        watcherInDirectory = new Thread(new WatcherInDirectory(directoryIn, directoryOut));
        watcherInDirectory.setDaemon(true);
        watcherInDirectory.start();
    }

    private static void initFrame() {
        EventQueue.invokeLater(() -> {
            JFrame.setDefaultLookAndFeelDecorated(true);
            JFrame frame = new AppFrame();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            startWatcherOutDirectory(false);
        });
    }

    static class AppFrame extends JFrame {

        private void chooseFileDialog() {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(directoryIn);
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

        private void createComponentFrame() {
            JLabel labelHistoryChange = new JLabel("History change directory");
            labelChooseFile = new JLabel();
            textAreaHistory = new TextArea(30, 10);
            textAreaHistory.setFont(new Font("Dialog", Font.PLAIN, 16));
            try {
                textAreaHistory.setText(ReaderFile.readFile(fileHistory));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            textAreaHistory.setEditable(false);
            JButton btnChooseFile = new JButton("Choose file for send");
            btnChooseFile.setMinimumSize(new Dimension(150, 50));
            btnChooseFile.addActionListener(e -> chooseFileDialog());
            JButton btnSendFile = new JButton("Send file");
            btnSendFile.addActionListener(e -> sendFileToInDirectory());
            btnSendFile.setMinimumSize(new Dimension(150, 50));

            createComponentLayoutFrame(labelHistoryChange, textAreaHistory, labelChooseFile, btnChooseFile, btnSendFile);
        }

        private void sendFileToInDirectory() {
            SendFileRunnable sendFileRunnable = new SendFileRunnable(
                    fileForSend, new File(directoryIn + File.separator + fileForSend.getName()),
                    labelChooseFile, false);
            new Thread(sendFileRunnable).start();
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
            directoryIn = file;
            new Thread(new UpdateConfigRunnable(FILE_CONFIG, NAME_PARAMETER_DIRECTORY_IN,
                    directoryIn.getAbsolutePath())).start();

            startWatcherInDirectory(true);
        }

        private void changeOutDirectory(File file) {
            textAreaHistory.setText("");
            directoryOut = file;
            new Thread(new UpdateConfigRunnable(FILE_CONFIG, NAME_PARAMETER_DIRECTORY_OUT,
                    directoryOut.getAbsolutePath())).start();

            startWatcherOutDirectory(true);
            startWatcherInDirectory(true);

            new Thread(new ClearHistoryRunnable(fileHistory)).start();
        }

        private void changeDirectoryDialog(boolean isInDirectory) {
            JFileChooser fileChooser = new JFileChooser();
            if (isInDirectory)
                fileChooser.setCurrentDirectory(directoryIn);
            else
                fileChooser.setCurrentDirectory(directoryOut);

            String dialogTitle = isInDirectory ? "Change new in directory" : "Change new out directory";
            fileChooser.setDialogTitle(dialogTitle);
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                String message = isInDirectory ? "Directory in change on " : "Directory out change on ";

                boolean isAvailableChangeDirectory = isInDirectory ?
                        !fileChooser.getSelectedFile().getAbsolutePath().equals(directoryOut.getAbsolutePath()) :
                        !fileChooser.getSelectedFile().getAbsolutePath().equals(directoryIn.getAbsolutePath());

                if (isAvailableChangeDirectory) {
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
                                    directoryIn + " directoryOut: " + directoryOut);
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
                new Thread(new ClearHistoryRunnable(fileHistory)).start();
            });

            return menu;
        }
    }
}

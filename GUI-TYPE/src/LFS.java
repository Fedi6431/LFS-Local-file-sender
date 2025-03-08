import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;



public class LFS extends JFrame {
    public static int serverPort = 65535;
    public static int clientPort = 53556;
    public static String ip;
    @SuppressWarnings("ClassEscapesDefinedScope")
    public static logging log = new logging();
    public static JFrame frame = new JFrame("File Transfer");
    public static final JTextArea serverLogTextArea = new JTextArea(4,5);
    public static String absoluteFilePath;

    public static String getCurrentClientIpv4() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress();
        } catch (Exception e) {
            String message = e.getMessage();
            log.appendErr(message);
            JOptionPane.showMessageDialog(frame, message, "Exception", JOptionPane.ERROR_MESSAGE);
            return "";
        }
    }

    private static boolean isValidIP(String ip) {
        if (Objects.equals(ip, "") ||ip == null || ip.isEmpty() || ip.length() > 15) {
            return false;
        }

        String ipPattern =
                "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + // First octet
                        "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + // Second octet
                        "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + // Third octet
                        "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";    // Fourth octet

        return ip.matches(ipPattern);
    }

    public static void main(String[] args) {
        log.makeFile();
        log.append("#######################################################");
        log.append("####################PROGRAM-STARTED####################");
        log.append("#######################################################");


        log.append("MADE BY FEDI6431");
        // ----------Frame-settings----------

        //set size: 600*400px
        log.appendInfo("Setting frame size");
        frame.setSize(600, 400);
        log.appendInfo("Width: " + frame.getWidth() + " Height: " + frame.getHeight());

        // Resizable: false
        frame.setResizable(false);
        log.appendInfo("Resizable: " + frame.isResizable());

        // Layout: border layout
        frame.setLayout(new BorderLayout());
        log.appendInfo("Layout: " + frame.getLayout());

        // set default close operation to close
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        log.appendInfo("Default close operation: " + frame.getDefaultCloseOperation());

        // ----------------------------------

        // ----------Creating objects----------
        log.appendInfo("Creating tabbed pane");
        JTabbedPane tabbedPane = new JTabbedPane();
        log.appendInfo("Tabbed pane created");


        log.appendInfo("Creating panel: senderPanel");
        JPanel senderPanel = new JPanel();
        log.appendInfo("Panel created");
        senderPanel.setLayout(null);
        log.appendInfo("Layout: " + senderPanel.getLayout());


        log.appendInfo("Creating Label: senderLabel");
        JLabel senderLabel = new JLabel("Send files via IPv4!");
        log.appendInfo("Label created: senderLabel");
        log.appendInfo("Label text: " + senderLabel.getText());
        senderLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        log.appendInfo("Label font: " + senderLabel.getFont());
        senderLabel.setBounds(150, 10, 300, 30);
        log.appendInfo("Label bounds: " + senderLabel.getBounds());
        log.appendInfo("Adding Label to senderPanel");
        senderPanel.add(senderLabel);
        log.appendInfo("Label added to senderPanel");


        log.appendInfo("Creating text field: ipTextField");
        JTextField ipTextField = new JTextField("Type the IPv4");
        log.appendInfo("Text field created: ipTextField");
        log.appendInfo("Text field default String: " + ipTextField.getText());
        ipTextField.setFont(new Font("Arial", Font.PLAIN, 20));
        log.appendInfo("Text field font: " + ipTextField.getFont());
        ipTextField.setBounds(150, 50, 300, 30);
        log.appendInfo("Text field bounds: " + ipTextField.getBounds());
        log.appendInfo("Adding Text field to senderPanel");
        senderPanel.add(ipTextField);
        log.appendInfo("Text field added to senderPanel");


        log.appendInfo("Creating Button: setIp");
        JButton setIp = new JButton("Set ip");
        log.appendInfo("Button created: setIp");
        log.appendInfo("Button text: " + setIp.getText());
        setIp.setFont(new Font("Arial", Font.PLAIN, 15));
        log.appendInfo("Button font: " + setIp.getFont());
        setIp.setBounds(460, 50, 100, 30);
        log.appendInfo("Button bounds: " + setIp.getBounds());
        log.appendInfo("Adding Button to senderPanel");
        senderPanel.add(setIp);
        log.appendInfo("Button added to senderPanel");


        log.appendInfo("Creating text field: portTextField");
        JTextField portTextField = new JTextField("Type port number (default 53556)");
        log.appendInfo("Text field created: portTextField");
        log.appendInfo("Text field default String: " + portTextField.getText());
        portTextField.setFont(new Font("Arial", Font.PLAIN, 13));
        log.appendInfo("Text field font: " + portTextField.getFont());
        portTextField.setBounds(150, 90, 300, 30);
        log.appendInfo("Text field bounds: " + portTextField.getBounds());
        log.appendInfo("Adding text field to senderPanel");
        senderPanel.add(portTextField);
        log.appendInfo("Text field added to senderPanel");


        log.appendInfo("Creating button: setPort");
        JButton setPort = new JButton("Set port");
        log.appendInfo("Button created: setPort");
        log.appendInfo("Button text: " + setPort.getText());
        setPort.setFont(new Font("Arial", Font.PLAIN, 15));
        log.appendInfo("Button font: " + setPort.getFont());
        setPort.setBounds(460, 90, 100, 30);
        log.appendInfo("Button bounds: " + setPort.getBounds());
        log.appendInfo("Adding button to senderPanel");
        senderPanel.add(setPort);
        log.appendInfo("Button added to senderPanel");


        log.appendInfo("Creating label: label");
        JLabel label = new JLabel("Selected file: None");
        log.appendInfo("Label created: label");
        log.appendInfo(" Label text: " + label.getText());
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        log.appendInfo("Label font: " + label.getFont());
        label.setBounds(150, 130, 300, 30);
        log.appendInfo("Label bounds: " + label.getBounds());
        log.appendInfo("Adding label to senderPanel");
        senderPanel.add(label);
        log.appendInfo("Label added to senderPanel");


        log.appendInfo("Creating Button: selectButton");
        JButton selectButton = new JButton("Select file");
        log.appendInfo("Button created: selectButton");
        log.appendInfo("Button text: " + selectButton.getText());
        selectButton.setFont(new Font("Arial", Font.PLAIN, 15));
        log.appendInfo("Button font: " + selectButton.getFont());
        selectButton.setBounds(460, 130, 100, 30);
        log.appendInfo("Button bounds: " + selectButton.getBounds());
        log.appendInfo("Adding button to senderPanel");
        senderPanel.add(selectButton);
        log.appendInfo("Button added to senderPanel");


        log.appendInfo("Creating button: sendButton");
        JButton sendButton = new JButton("Send file");
        log.appendInfo("Button created: sendButton");
        log.appendInfo("Button text: " + sendButton.getText());
        sendButton.setFont(new Font("Arial", Font.PLAIN, 20));
        log.appendInfo("Button font: " + sendButton.getFont());
        sendButton.setBounds(150, 170, 300, 30);
        log.appendInfo("Button bounds: " + sendButton.getBounds());
        log.appendInfo("Adding button to senderPanel");
        senderPanel.add(sendButton);
        log.appendInfo("Button added to senderPanel");

        // ------------------------------------

        // Button action listeners
        selectButton.addActionListener(e -> {
            log.appendInfo(selectButton.getText() + "' button pressed");

            JFileChooser fileChooser = new JFileChooser();
            log.appendInfo("Creating file chooser");
            log.appendInfo("Saving return value");
            int returnValue = fileChooser.showOpenDialog(null);
            log.appendInfo("Return value saved");

            log.appendInfo("Checking return value status");
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                log.appendInfo("Status: " + returnValue);
                log.appendInfo("Retrieving file path");
                File selectedFile = fileChooser.getSelectedFile();
                log.appendInfo("File path retrieved: " + selectedFile);
                log.appendInfo("Retrieving file image");
                ImageIcon fileIcon = (ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(selectedFile);
                log.appendInfo("File image retrieved: " + fileIcon);
                log.appendInfo("Updating label 'label'");
                log.appendInfo("Updating icon");
                label.setIcon(fileIcon);
                log.appendInfo("Icon updated as '" + fileIcon + "'");
                log.appendInfo("Updating text");
                label.setText("Selected file: " + selectedFile.getAbsolutePath());
            } else {
                log.appendInfo("Updating label 'label'");
                log.appendInfo("Updating con");
                label.setIcon(null);
                log.appendInfo("Updating icon as '" + null + "'");
                log.appendInfo("Updating text");
                label.setText("No file selected.");
            }
            log.appendInfo("Updated text as '" + label.getText() + "'");
        });


        sendButton.addActionListener(e -> {
            log.appendInfo(sendButton.getText() + "' button pressed");
            log.appendInfo("Retrieving information");
            log.appendInfo("Retrieving ip ");
            String ip = ipTextField.getText();

            log.appendInfo("Ip retrieved: " + ip);
            log.appendInfo("Retrieving file path");
            String filePath = label.getText();

            log.appendInfo("File path retrieved: " + filePath);
            if (filePath.startsWith("Selected file: ")) {
                log.appendInfo("Removing obsolete Strings in the file path");
                filePath = filePath.replace("Selected file: ", "");
                log.appendInfo("Useless String removed from the file path");
            }

            boolean isIpValid;
            boolean isPathValid;

            log.appendInfo("Checking ip validity");
            ip = ipTextField.getText();

            if (!isValidIP(ip)) {
                log.appendInfo("Ip parameters not valid");
                isIpValid=false;
                JOptionPane.showMessageDialog(frame, "The ip doesn't respect the parameters: <=15, not blank.", "Invalid ip", JOptionPane.ERROR_MESSAGE);
            } else {
                log.appendInfo("Ip parameters valid");
                isIpValid=true;
            }

            log.appendInfo("Checking file path validity");
            if (filePath.equalsIgnoreCase("selected file: none") || filePath.equalsIgnoreCase("no file selected.")) {
                log.appendErr("File path parameters not valid");
                isPathValid=false;
                JOptionPane.showMessageDialog(frame, "The file path isn't specified", "Invalid file path", JOptionPane.ERROR_MESSAGE);
            } else {
                log.appendInfo("File path parameters valid");
                absoluteFilePath = filePath;
                isPathValid=true;
            }

            if (isIpValid && isPathValid) {
                Thread senderThread = new Thread(new SenderThread());
                if (senderThread.isAlive()) {
                    JOptionPane.showMessageDialog(frame, "Another sender thread is active, wait until the content is sent or restart the program", "Sender service", JOptionPane.WARNING_MESSAGE);
                    log.appendWarn("Another sender thread is active, wait until the content is sent or restart the program");
                } else {
                    senderThread.start();
                }

            }
        });


        setPort.addActionListener(e -> {
            String inputText = portTextField.getText();
            try {
                int newPort = Integer.parseInt(inputText);
                if (newPort<=65535) {
                    clientPort = newPort;
                    log.appendInfo("Remote server port to: " + clientPort);
                } else {
                    log.appendErr("Invalid input: " + inputText);
                    JOptionPane.showMessageDialog(frame, "Please enter a valid integer for the port.", "Invalid Input", JOptionPane.ERROR_MESSAGE);}
            } catch (NumberFormatException ex) {
                log.appendErr("Invalid input: " + inputText);
                    JOptionPane.showMessageDialog(frame, "Please enter a valid integer for the port.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });


        setIp.addActionListener(e -> {
            ip = ipTextField.getText();
            if (isValidIP(ip)) {
                log.appendInfo("Remote server ip to: " + ip);
            } else {
                log.appendErr("Invalid input: " + ip);
                JOptionPane.showMessageDialog(frame, "Please enter a valid IP address.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });
        // -----------------------

        // ----------Creating objects----------
        log.appendInfo("Creating panel: recieverPanel");
        JPanel recieverPanel = new JPanel();
        log.appendInfo("Panel created");
        recieverPanel.setLayout(null);
        log.appendInfo("Layout: " + recieverPanel.getLayout());

        log.appendInfo("Creating Label: receiverLabel");
        JLabel receiverLabel = new JLabel("Receive files with IPv4!");
        log.appendInfo("Label created: receiverLabel");
        log.appendInfo("Label text: " + receiverLabel.getText());
        receiverLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        log.appendInfo("Label font: " + receiverLabel.getFont());
        receiverLabel.setBounds(150, 10, 300, 30);
        log.appendInfo("Label bounds: " + receiverLabel.getBounds());
        log.appendInfo("Adding Label to recieverPanel");
        recieverPanel.add(receiverLabel);
        log.appendInfo("Label added to recieverPanel");


        log.appendInfo("Creating text field: portServerTextField");
        JTextField portServerTextField = new JTextField("Set port (default " + serverPort + ")");
        log.appendInfo("Text field created: portServerTextField");
        log.appendInfo("Text field default String: " + portServerTextField.getText());
        portTextField.setFont(new Font("Arial", Font.PLAIN, 13));
        log.appendInfo("Text field font: " + portServerTextField.getFont());
        portServerTextField.setBounds(150, 50, 300, 30);
        log.appendInfo("Text field bounds: " + portServerTextField.getBounds());
        log.appendInfo("Adding Text field to recieverPanel");
        recieverPanel.add(portServerTextField);
        log.appendInfo("Text field added to recieverPanel");


        log.appendInfo("Creating Button: setPortServer");
        JButton setPortServer = new JButton("Set port");
        log.appendInfo("Button created: setPortServer");
        log.appendInfo("Button text: " + setPortServer.getText());
        setPortServer.setFont(new Font("Arial", Font.PLAIN, 15));
        log.appendInfo("Button font: " + setPortServer.getFont());
        setPortServer.setBounds(460, 50, 100, 30);
        log.appendInfo("Button bounds: " + setPortServer.getBounds());
        log.appendInfo("Adding Button to recieverPanel");
        recieverPanel.add(setPortServer);
        log.appendInfo("Button added to recieverPanel");


        serverLogTextArea.setEditable(false);
        log.appendInfo(" Label editable: " + serverLogTextArea.isEditable());
        log.appendInfo(" Label text: " + serverLogTextArea.getText());
        serverLogTextArea.setFont(new Font("Arial", Font.PLAIN, 10));
        log.appendInfo("Label font: " + serverLogTextArea.getFont());
        serverLogTextArea.setBounds(150, 90, 300, 60);
        log.appendInfo("Label bounds: " + serverLogTextArea.getBounds());
        log.appendInfo("Adding label to senderPanel");
        recieverPanel.add(serverLogTextArea);
        log.appendInfo("Label added to senderPanel");


        log.appendInfo("Creating button: startServerButton");
        JButton startServerButton = new JButton("Start server");
        log.appendInfo("Button created: startServerButton");
        log.appendInfo("Button text: " + startServerButton.getText());
        startServerButton.setFont(new Font("Arial", Font.PLAIN, 20));
        log.appendInfo("Button font: " + startServerButton.getFont());
        startServerButton.setBounds(150, 170, 300, 30);
        log.appendInfo("Button bounds: " + startServerButton.getBounds());
        log.appendInfo("Adding button to senderPanel");
        recieverPanel.add(startServerButton);
        log.appendInfo("Button added to senderPanel");


        setPortServer.addActionListener(e -> {
            String inputText = portServerTextField.getText();
            try {
                serverPort = Integer.parseInt(inputText);
                log.appendInfo("Remote server port to: " + serverPort);
            } catch (NumberFormatException ex) {
                log.appendErr("Invalid input: " + inputText);
                JOptionPane.showMessageDialog(frame, "Please enter a valid integer for the port.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });


        startServerButton.addActionListener(e -> {
            serverLogTextArea.removeAll();
            JOptionPane.showMessageDialog(frame, "Starting server", "Server service", JOptionPane.INFORMATION_MESSAGE);
            Thread receiverThread = new Thread(new RecieverThread());
            if (receiverThread.isAlive()) {
                JOptionPane.showMessageDialog(frame, "Another server thread is active, ensure the server is offline", "Server service", JOptionPane.WARNING_MESSAGE);
                log.appendWarn("Another server thread is active, ensure the server is offline");
            } else {
                receiverThread.start();
            }



        });

        // ------------------------------------

        log.appendInfo("Adding sender panel to tabbed pane");
        tabbedPane.addTab("Sender", senderPanel);
        log.appendInfo("Sender panel added to tabbed pane");

        log.appendInfo("Adding receiver panel to tabbed pane");
        tabbedPane.addTab("Receiver", recieverPanel);
        log.appendInfo("Receiver panel added to tabbed pane");

        log.appendInfo("Adding tabbed pane to frame");
        frame.add(tabbedPane, BorderLayout.CENTER);
        log.appendInfo("Tabbed pane added to frame");

        frame.setVisible(true);
        log.appendInfo("Frame set to visible");
        JOptionPane.showMessageDialog(frame, "This program was made by Fedi6431 on github!", "Credits", JOptionPane.INFORMATION_MESSAGE);
    }
}

@SuppressWarnings("resource")
class RecieverThread implements Runnable {
    @Override
    public void run() {
        try {
            String serverIp = LFS.getCurrentClientIpv4();
            String msg = "Starting socket server, IP: " + serverIp;
            LFS.log.appendInfo(msg);
            LFS.serverLogTextArea.append(msg + "\n");
            Socket client1;
            ServerSocket server;
            server = new ServerSocket(LFS.serverPort);
            msg = "Server started without exceptions...";
            LFS.log.appendInfo(msg);
            LFS.serverLogTextArea.append(msg + "\n");
            msg = "Listening on port " + LFS.serverPort;
            LFS.log.appendInfo(msg);
            LFS.serverLogTextArea.append(msg + "\n");
            client1 = server.accept();

            msg = client1.getInetAddress() + " connected";
            LFS.log.appendInfo(msg);
            LFS.serverLogTextArea.append(msg + "\n");

            InputStream input = client1.getInputStream();
            DataInputStream reader = new DataInputStream(input);

            String filename = reader.readUTF();

            File file = new File(filename);
            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = reader.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }
            }
            msg = "File received and saved: " + filename + "\n";
            LFS.log.appendInfo(msg);
            LFS.serverLogTextArea.append(msg + "\n");
        } catch (Exception e) {
            LFS.log.appendErr(e.getMessage());
        }
    }
}

class SenderThread implements Runnable {
    @Override
    public void run() {
        try {
            LFS.log.appendInfo("Creating socket connection");
            Socket client = new Socket();
            int timeout = 50;
            try {
                SocketAddress address = new InetSocketAddress(LFS.ip, LFS.clientPort);
                client.connect(address, timeout);
                LFS.log.appendInfo("Socket connection created");

                LFS.log.appendInfo("Setting streams");
                OutputStream output = client.getOutputStream();
                DataOutputStream writer = new DataOutputStream(output);
                LFS.log.appendInfo("Getting path");
                Path path = Path.of(LFS.absoluteFilePath);
                LFS.log.appendInfo("Path retrieved: " + path);
                LFS.log.appendInfo("Getting file name");
                String filename = path.getFileName().toString();
                LFS.log.appendInfo("File name retrieved: " + filename);
                LFS.log.appendInfo("Sending a packet, content: " + filename);
                // Send the file name
                writer.writeUTF(filename);


                try (FileInputStream fileInputStream = new FileInputStream(path.toFile())) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                        writer.write(buffer, 0, bytesRead);
                    }
                }
                LFS.log.appendInfo("Ensuring that all data is sent\n");
                writer.flush();
                LFS.log.appendInfo("Closing connections");
                writer.close();
                client.close();
            } catch (SocketTimeoutException e) {
                LFS.log.appendWarn(e.getMessage());
                JOptionPane.showMessageDialog(LFS.frame, "How to fix?\n1. Try to see if the server is available/booted\n2. Check if the current set port is the one that is using the server", "CONNECTION TIMEOUT", JOptionPane.WARNING_MESSAGE);
            }
        } catch (IOException e) {
            LFS.log.appendErr(e.getMessage());
        }
    }
}

class logging{
    private final String path = "logs/";
    private final String filename = "log-" + getCurrentDate();

    public String getCurrentDate() {
        SimpleDateFormat ft
                = new SimpleDateFormat("dd-MM-yyyy");
        return ft.format(new Date());
    }

    public LocalTime getCurrentTime() {
        return LocalTime.now();
    }

    public void makeFile() {
        try {
            File dir = new File(path);
            if (dir.mkdir()) {
                LFS.log.appendInfo("Directory created");
            } else {
                LFS.log.appendWarn("Directory already exist");
            }

            File file = new File(path + filename);
            if (file.createNewFile()) {
                LFS.log.appendInfo("File created");
            } else {
                LFS.log.appendWarn("File already exist");
            }
        } catch (IOException e) {
            LFS.log.appendErr(e.getMessage());
            System.out.println(e.getMessage());
        }
    }
    public void append(String str) {
        try {
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(path + filename, true));
            out.write(str + "\n");
            out.close();
        } catch (IOException e) {
            LFS.log.appendErr(e.getMessage());
        }
    }

    public void appendInfo(String str) {
        try {
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(path + filename, true));
            out.write(getCurrentDate() + " " + getCurrentTime() + " | info: "+ str + "\n");
            out.close();
        } catch (IOException e) {
            LFS.log.appendErr(e.getMessage());
        }
    }

    public void appendErr(String str) {
        try {
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(path + filename, true));
            out.write(getCurrentDate() + " " + getCurrentTime() + " | err: "+ str + "\n");
            out.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void appendWarn(String str) {
        try {
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(path + filename, true));
            out.write(getCurrentDate() + " " + getCurrentTime() + " | warn: "+ str + "\n");
            out.close();
        } catch (IOException e) {
            LFS.log.appendErr(e.getMessage());
        }
    }
}

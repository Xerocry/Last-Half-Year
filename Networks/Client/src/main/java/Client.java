import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Scanner;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) {
        Socket smtpSocket = null;
        DataOutputStream os = null;
        DataInputStream is = null;
        Scanner s = new Scanner(System.in);

        try {
            smtpSocket = new Socket("localhost", 8880);
            os = new DataOutputStream(smtpSocket.getOutputStream());
            is = new DataInputStream(smtpSocket.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: hostname");
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: hostname");
        }

        if (smtpSocket != null && os != null && is != null) {
            try {
                String responseLine;
                responseLine = is.readLine();
                System.out.println("Server: " + responseLine);
                while(true)
                {
                    os.flush();
                    System.out.println("enter line");
                    String a = s.nextLine();
                    os.writeBytes(a + "\n");

                    System.out.println("wrote bytes");


                    responseLine = is.readLine();
                    System.out.println("Server: " + responseLine);
                    if (responseLine.indexOf("Ok") != -1) {
                        System.out.println("breaking");
                        break;
                    }
                }
                os.close();
                is.close();
                smtpSocket.close();
            } catch (UnknownHostException e) {
                System.err.println("Trying to connect to unknown host: " + e);
            } catch (IOException e) {
                System.err.println("IOException:  " + e);
            }
        }
        System.out.println("out of while");
    }
}




//
//public class Client implements Runnable {
//    /* The Socket of the Client */
//    private Socket clientSocket;
//    private BufferedReader serverToClientReader;
//    private PrintWriter clientToServerWriter;
//    private String name;
//    public ObservableList<String> chatLog;
//
//    public Client(String hostName, int portNumber, String name) throws UnknownHostException, IOException {
//
//			/* Try to establish a connection to the server */
//        clientSocket = new Socket(hostName, portNumber);
//			/* Instantiate writers and readers to the socket */
//        serverToClientReader = new BufferedReader(new InputStreamReader(
//                clientSocket.getInputStream()));
//        clientToServerWriter = new PrintWriter(
//                clientSocket.getOutputStream(), true);
//        chatLog = FXCollections.observableArrayList();
//			/* Send name data to the server */
//        this.name = name;
//        clientToServerWriter.println(name);
//
//
//    }
//
//    public void writeToServer(String input) {
//        clientToServerWriter.println(name + " : " + input);
//    }
//
//    public void run() {
//		/* Infinite loop to update the chat log from the server */
//        while (true) {
//            try {
//
//                final String inputFromServer = serverToClientReader.readLine();
//                Platform.runLater(new Runnable() {
//                    public void run() {
//                        chatLog.add(inputFromServer);
//                    }
//                });
//
//            } catch (SocketException e) {
//                Platform.runLater(new Runnable() {
//                    public void run() {
//                        chatLog.add("Error in server");
//                    }
//
//                });
//                break;
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//    }
//}
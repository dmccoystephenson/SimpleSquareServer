package SimpleServer.server;

import java.io.IOException;
import java.net.ServerSocket;

public class MultiServer {

    private int portNumber = -1;
    ServerSocket serverSocket = null;
    boolean listening = false;

    public MultiServer(int port) {
        portNumber = port;
        boolean success = initializeServerSocket();
        if (!success) {
            System.out.println("Something went wrong while initializing with port " + getPortNumber() + "!");
        }
    }

    public void start() {
        listening = true;

        while (isListening()) {
            System.out.println("Listening for a new connection...");
            createNewServerThread();
        }
    }

    public void stop() {
        listening = false;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public boolean isListening() {
        return listening;
    }

    private boolean initializeServerSocket() {
        try {
            serverSocket = new ServerSocket(portNumber);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private boolean createNewServerThread() {
        try {
            new SimpleServer.server.MSThread(serverSocket.accept()).start();
            System.out.println("A new connection has been made!");
            return true;
        } catch(Exception e) {
            System.out.println("ERROR: Was not able to listen on port " + getPortNumber());
            return false;
        }
    }

}
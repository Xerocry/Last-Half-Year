package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class NioServer implements Runnable {

    /*
     * @param idCounter - unique id for users names
     * @param addr
     * @param port - listen port [0, 65535]
     * @param selector -
     * @param dataMap - map for messages and sockets
     * @param clientNames - clients names list for JavaFX thread view
     * @param serverLog - log messages list for JavaFX thread view
     * @param messages - messages list for JavaFX thread view
     * */
    private static AtomicLong idCounter = new AtomicLong();
    ObservableList<String> serverLog;
    ObservableList<ChatUser> clientNames;
    ObservableList<String> messages;
    private InetAddress addr;
    private int port;
    private Selector selector;
    private Map<SocketChannel, List<byte[]>> dataMap;
    private ServerSocketChannel serverChannel;

    /**
     * Interruption flag. If mInterrupt = true all loops
     */
    private boolean mInterrupt;

    NioServer(InetAddress addr, int port) throws IOException {
        this.addr = addr;
        this.port = port;
        this.mInterrupt = false;
        this.serverLog = FXCollections.observableArrayList();
        this.clientNames = FXCollections.observableArrayList();
        this.messages = FXCollections.observableArrayList();
        this.dataMap = new HashMap<>();

    }

    private static String createID() {
        return String.valueOf(idCounter.getAndIncrement());
    }

    @Override
    public void run() {
        try {
            this.selector = Selector.open();
            // create selector and channel
            serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);

            // bind to port
            InetSocketAddress listenAddr = new InetSocketAddress(this.addr, this.port);
            serverChannel.socket().bind(listenAddr);
            serverChannel.register(this.selector, SelectionKey.OP_ACCEPT);

            log("Server ready. Ctrl-C to stop.");

            // processing
            while (!mInterrupt) {
                // wait for events
                this.selector.select();

                // wakeup to work on selected keys
                Iterator keys = this.selector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    SelectionKey key = (SelectionKey) keys.next();

                    // this is necessary to prevent the same key from coming up
                    // again the next time around.
                    keys.remove();

                    if (!key.isValid()) {
                        continue;
                    }

                    if (key.isAcceptable()) {
                        this.accept(key);
                    } else if (key.isReadable()) {
                        this.read(key);
                    } else if (key.isWritable()) {
                        this.write(key);
                    }
                }
            }
        } catch (IOException e) {
            log("Error while trying to use selectors");
        } finally {
            shutdown();
        }
    }

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel channel = serverChannel.accept();
        channel.configureBlocking(false);

        // write welcome message
        channel.write(ByteBuffer.wrap("Welcome, this is the echo server\r\n".getBytes("US-ASCII")));

        Socket socket = channel.socket();
        SocketAddress remoteAddr = socket.getRemoteSocketAddress();
        log("Connected to: " + remoteAddr);
        Platform.runLater(() -> {
            String name = "User" + createID();
            clientNames.add(new ChatUser(channel, name));
        });

        // register channel with selector for further IO
        dataMap.put(channel, new ArrayList<>());
        channel.register(this.selector, SelectionKey.OP_READ);
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();

        ByteBuffer buffer = ByteBuffer.allocate(8192);
        int numRead = -1;
        try {
            numRead = channel.read(buffer);
        } catch (IOException e) {
            key.cancel();
            disconnect(channel);
        }

        if (numRead == -1) {
            this.dataMap.remove(channel);
            Socket socket = channel.socket();
            SocketAddress remoteAddr = socket.getRemoteSocketAddress();
            log("Connection closed by client: " + remoteAddr);
            channel.close();
            key.cancel();
            return;
        }

        byte[] data = new byte[numRead];
        System.arraycopy(buffer.array(), 0, data, 0, numRead);
        messageLog(findUser(channel).getName() + " -> " + new String(data, "US-ASCII"));

        // write back to client
        doEcho(key, data);
    }

    private void write(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        List<byte[]> pendingData = this.dataMap.get(channel);
        Iterator<byte[]> items = pendingData.iterator();
        while (items.hasNext()) {
            byte[] item = items.next();
            items.remove();
            messageLog(findUser(channel).getName() + " <- " + new String(ByteBuffer.wrap(item).array()));
            try {
                channel.write(ByteBuffer.wrap(item));
            } catch (IOException ex) {
                key.cancel();
                disconnect(channel);
            }
        }
        key.interestOps(SelectionKey.OP_READ);
    }

    private void doEcho(SelectionKey key, byte[] data) {
        SocketChannel channel = (SocketChannel) key.channel();
        List<byte[]> pendingData = this.dataMap.get(channel);
        pendingData.add(data);
        key.interestOps(SelectionKey.OP_WRITE);
    }

    private void log(String s) {
        Platform.runLater(() -> serverLog.add(s));
    }

    private void messageLog(String s) {
        Platform.runLater(() -> messages.add(s));
    }

    void disconnect(final SocketChannel clientSocketChannel) {
        Platform.runLater(() -> {
            try {
                log("Client " + findUser(clientSocketChannel).getName() + " was disconnected!");
                dataMap.remove(clientSocketChannel);
                clientNames.remove(findUser(clientSocketChannel));
                clientSocketChannel.close();
            } catch (IOException e) {
                log("It's impossible to close socket " + clientSocketChannel);
            }
        });
    }

    void shutdown() {
        mInterrupt = true;
        for (final Map.Entry<SocketChannel, List<byte[]>> client : dataMap.entrySet()) {
            disconnect(client.getKey());
        }
        try {
            serverChannel.close();
        } catch (IOException ex) {
            log("Error trying to close server socket!");
        }
    }

    private ChatUser findUser(SocketChannel socketChannel) {
        for(ChatUser user : clientNames) {
            if(user.getSocket().equals(socketChannel)) {
                return user;
            }
        }
        return null;
    }
}

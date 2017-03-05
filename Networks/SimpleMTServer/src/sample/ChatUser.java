package sample;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;

import java.nio.channels.SocketChannel;
public class ChatUser {
    private ObjectProperty<SocketChannel> socket;
    private StringProperty name;

    public ChatUser() {
    }

    ChatUser(SocketChannel socket, String name) {
        this.setName(name);
        this.setSocket(socket);
    }

    private ObjectProperty<SocketChannel> socketProperty() {
        if (socket == null) {
            socket = new SimpleObjectProperty<>();
        }
        return socket;
    }

    SocketChannel getSocket() {
        return socketProperty().get();
    }

    private StringProperty nameProperty() {
        if (name == null) {
            name = new SimpleStringProperty();
        }
        return name;
    }

    final String getName() {
        return nameProperty().get();
    }

    private void setSocket(SocketChannel socket) {
        socketProperty().set(socket);
    }

    private void setName(final String name) {
        nameProperty().set(name);
    }

    public static Callback<ChatUser, Observable[]> extractor() {
        return (ChatUser p) -> new Observable[]{p.socketProperty(), p.nameProperty()};
    }
}

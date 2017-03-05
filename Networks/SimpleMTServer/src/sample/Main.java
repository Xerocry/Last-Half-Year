package sample;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.nio.channels.SocketChannel;

public class Main extends Application {

    private Thread serverThread;
    private NioServer nioServer;

    private Scene createScene(Stage primaryStage) throws Exception {
        /*Root pane - vertical pane*/
        VBox root = new VBox();

        /*Menu above pane*/
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(ex -> System.exit(0));
        menuFile.getItems().addAll(exit);
        menuBar.getMenus().addAll(menuFile);
        root.getChildren().addAll(menuBar);

        /*Base grid pane for lists*/
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(root, 600, 480);
        primaryStage.setScene(scene);
        root.getChildren().addAll(grid);

        startNonBlockingListen();

        ListView<String> chatArea = new ListView<>();
        ListView<String> logArea = new ListView<>();
        ListView<ChatUser> userList = new ListView<>();
        ObservableList<String> logList = nioServer.serverLog;

        logArea.setItems(logList);

        /*Console input area*/
        TextField cons = new TextField();
        cons.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                if (cons.getText().equals("quit")) {
                    try {
                        nioServer.shutdown();
                        serverThread.join();
                    } catch (Exception ignored) {
                    }
                    System.out.println("DONE");
                    System.exit(0);
                }
            }
        });

        logArea.setEditable(false);

        Separator horSep = new Separator();
        Separator vertSep = new Separator();
        vertSep.setOrientation(Orientation.VERTICAL);

        /*Add elements to scene*/
        grid.add(userList, 0, 0);
        grid.add(vertSep, 1, 0);
        grid.add(chatArea, 2, 0);
        grid.add(horSep, 0, 1, 3, 1);
        grid.add(logArea, 0, 2, 3, 1);
        grid.add(cons, 0, 3, 3, 1);

        /*Make menu for disconnect users*/
        userList.setItems(nioServer.clientNames);

        chatArea.setItems(nioServer.messages);
        userList.setCellFactory(new Callback<ListView<ChatUser>, ListCell<ChatUser>>() {
            @Override
            public ListCell<ChatUser> call(ListView<ChatUser> lv) {
                ListCell<ChatUser> cell = new ListCell<ChatUser>(){
                    @Override
                    protected void updateItem(ChatUser item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getName());
                        }
                    }
                };
                ContextMenu contextMenu = new ContextMenu();

                MenuItem editItem = new MenuItem();
                editItem.textProperty().bind(Bindings.format("Disconnect \"%s\"", cell.itemProperty()));

                editItem.setOnAction(event -> {
//                String item = cell.getItem().toString();
                    SocketChannel item = cell.getItem().getSocket();
                    System.out.println(item.toString() + cell.getItem().getName());
                    nioServer.disconnect(item);
                });

                contextMenu.getItems().addAll(editItem);
                cell.emptyProperty().addListener((obs, wasEmpty, nowEmpty) -> {
                    if (nowEmpty) {
                        cell.setContextMenu(null);
                    } else {
                        cell.setContextMenu(contextMenu);
                    }
                });

                return cell;
            }
        });
        return scene;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("JavaFX Chat Server");
        primaryStage.setScene(createScene(primaryStage));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void startNonBlockingListen() throws Exception {
         /*Create server thread to listen clients*/
        nioServer = new NioServer(null,8880);
        serverThread = (new Thread(nioServer));
        serverThread.setName("Server Thread");
        serverThread.setDaemon(true);
        serverThread.start();
    }
}
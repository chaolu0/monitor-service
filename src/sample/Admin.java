package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Created by caolu on 2017/8/21.
 */
public class Admin {
    static Stage primaryStage;

    public static void close() {
        primaryStage.close();
    }
    private  Stage stage;
    public Admin(Stage stage){
        this.stage = stage;
    }
    public void start() throws Exception {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("admin.fxml"));
        primaryStage.setTitle("admin index");
        //primaryStage.setMaximized(true);
        primaryStage.setScene(new Scene(root,600,400));
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                stage.show();
            }
        });
    }


}

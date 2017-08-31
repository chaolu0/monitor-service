package sample;

import Bean.UserBean;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import util.Dao;

/**
 * Created by caolu on 2017/8/18.
 */
public class Login extends Application {
    public static UserBean mBean;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root,500,400));
        primaryStage.show();
        TextField usernameText = (TextField) root.lookup("#username_filed");
        TextField passwordText = (TextField) root.lookup("#password_filed");
        Text errorText = (Text) root.lookup("#login_hold_text");
        Platform.setImplicitExit(true);
        Button login = (Button)root.lookup("#button_login");
        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Dao dao = new Dao();
                UserBean bean = new UserBean();
                bean.setUsername(usernameText.getCharacters().toString());
                bean.setPassword(passwordText.getCharacters().toString());
                if(dao.find(bean)!=null){
                    mBean = dao.find(bean);
                    begin(primaryStage);
                }else{
                    errorText.setText("username or password error,login failed");
                }
            }
        });
    }

    private void begin(Stage primaryStage){
        try {
            //new Main().start();
            new Menu().start();
            Event.fireEvent(primaryStage, new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST ));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}

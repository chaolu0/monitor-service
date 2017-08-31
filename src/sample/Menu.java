package sample;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by caolu on 2017/8/21.
 */
public class Menu {
    static Stage primaryStage;
    public static void close(){
        primaryStage.close();
    }
    public void start() throws Exception {
        primaryStage = new Stage();
        //Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        primaryStage.setTitle("menu");
       // primaryStage.setMaximized(true);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
        Parent root = loader.load();
        MenuController controller = (MenuController)loader.getController();
        controller.setStage(primaryStage);

        ImageView img = (ImageView) root.lookup("#s");
        Text text = (Text) root.lookup("#st");
        Text wel = (Text) root.lookup("#welcome");

        ImageView img1 = (ImageView) root.lookup("#m");
        Text text1 = (Text) root.lookup("#mt");
        if(Login.mBean.getType() == 0){
            System.out.println("in");
            img.setVisible(false);
            text.setVisible(false);
            img1.setLayoutX(230);
            text1.setLayoutX(230);
            wel.setText("欢迎您，用户" + Login.mBean.getUsername());
        }else{
            wel.setText("欢迎您，管理员" + Login.mBean.getUsername());
        }
        primaryStage.setScene(new Scene(root,600,400));
        primaryStage.show();
        Platform.setImplicitExit(true);

    }
}

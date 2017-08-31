package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by caolu on 2017/8/21.
 */
public class MenuController implements Initializable{
    @FXML
    ImageView m;
    @FXML
    ImageView s;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        m.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    new Main().start();
                    Menu.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        s.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    new Admin(stage).start();
                    Menu.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
    private Stage stage;
    public void setStage(Stage stage){
        this.stage =stage;
    }
}

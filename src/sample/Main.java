package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class Main  {

    public void start() throws Exception {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("monitor.fxml"));
        primaryStage.setTitle("monitor");
        primaryStage.setMaximized(true);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> System.exit(0));
    }

    private void six(Stage primaryStage,Parent root){
        ImageView imageView = (ImageView) root.lookup("#imageView");
        ListView listView  = (ListView) root.lookup("#listView");
        primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number width, Number t1) {
                imageView.setFitWidth(width.doubleValue() - 205);
                listView.setLayoutX(width.doubleValue() - 203);
            }
        });
        primaryStage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number height, Number t1) {
                imageView.setFitHeight(height.doubleValue());
                listView.setPrefHeight(height.doubleValue() - 105);
            }
        });

    }

}

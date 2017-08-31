package sample;

import Bean.UserBean;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.util.Callback;
import util.Dao;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by caolu on 2017/8/21.
 */
public class AdminController implements Initializable {

    @FXML
    Button add;
    @FXML
    Button update;
    @FXML
    Button del;
    @FXML
    Button reset;
    @FXML
    TextField u;
    @FXML
    TextField p;
    @FXML
    TableView<UserBean> tableview;
    @FXML
    TableColumn<UserBean, String> c1;
    @FXML
    TableColumn<UserBean, String> c2;
    @FXML
    TableColumn<UserBean, String> c3;
    private int index = 0;
    private int id = -1;
    private ObservableList<UserBean> mList = FXCollections.observableArrayList();
    private Dao dao;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dao = new Dao();
        notifyList();
        reset();
        c1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<UserBean, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<UserBean, String> param) {
                return new SimpleStringProperty(param.getValue().getUsername());
            }
        });
        c2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<UserBean, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<UserBean, String> param) {
                return new SimpleStringProperty(param.getValue().getPassword());
            }
        });
        c3.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<UserBean, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<UserBean, String> param) {
                return new SimpleStringProperty(param.getValue().getType().toString());
            }
        });
        tableview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<UserBean>() {
            @Override
            public void changed(ObservableValue<? extends UserBean> observable, UserBean oldValue, UserBean newValue) {
              //  index = tableview.getSelectionModel().getSelectedIndex();
                u.setText(newValue.getUsername());
                p.setText(newValue.getPassword());
                add.setVisible(false);
                u.setEditable(false);
                del.setVisible(true);
                update.setVisible(true);
                id = newValue.getId();
            }
        });
        tableview.setItems(mList);

        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dao.query("insert into users ( username , password ) values (" + "\'" + u.getText() + "\'" + "," + "\'" + p.getText() + "\'" + ")");
                notifyList();
            }
        });

        update.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (id == -1)
                    return;
                dao.query("update users set username = " + "\'" + u.getText() + "\'" + ", password = " + "\'" + p.getText() + "\'" + " where id = " + id);
                notifyList();
                reset();
            }
        });

        del.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dao.query("delete from users where id = " + id);
                notifyList();
                reset();
            }
        });
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                reset();
            }
        });
    }

    private void reset() {
        id = -1;
        index = -1;
        add.setVisible(true);
        u.setEditable(true);
        del.setVisible(false);
        update.setVisible(false);
        u.setText("");
        p.setText("");
    }

    private void notifyList() {
        mList.clear();
        for (UserBean bean : dao.query("select * from users")) {
            mList.add(bean);
        }
    }

}

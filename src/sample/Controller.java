package sample;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import portocol.MyPortocol;
import server.GetImgThread;
import server.SendCommand;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class Controller implements Initializable {
    private ObservableList<String> mObList;
    @FXML
    private ListView<String> listView;
    public static Vector<MyPortocol> mList = new Vector();
    @FXML
    public ImageView imageView;

    //当前正在传输图片的mList的下标
    private int currentIndex = -1;
    private int lastIndex = -1;

    private GetImgThread mThread = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        mObList = FXCollections.observableArrayList();
        listView.setItems(mObList);
        listView.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {

                     for(int i =0;i<mList.size();i++){
                         if(mList.get(i).getAddress().equals(newValue)){
                             currentIndex = i;
                         }
                         else if(mList.get(i).getAddress().equals(oldValue)) {
                             lastIndex = i;
                         }
                     }
                     startThread();
                });


        new Thread(() -> initSocket()).start();
    }

    private void startThread() {
        //如果点击的Socket仍然处于连接状态
        if(SendCommand.sendNull(mList.get(currentIndex))){
            System.out.println("alive");
            if (mThread == null){
                createGetImgThread(mList.get(currentIndex));
            }
            if (mThread !=null && !mThread.getPortocol().getAddress().equals(mList.get(currentIndex).getAddress())) {
                System.out.println("running =" + mThread.getPortocol().getAddress());
                System.out.println("running =" + mList.get(currentIndex).getAddress());
                SendCommand.sendStop(mThread.getPortocol());
                mThread.shouldRun = false;
                createGetImgThread(mList.get(currentIndex));
            }

        }else{
            System.out.println("died");
            //mList.get(currentIndex).close();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        mList.remove(currentIndex);
                        mObList.remove(currentIndex);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    if (mList.size() == 0){
                        imageView.setImage(null);
                    }
                }
            });
        }
    }

    private void initSocket() {
        ServerSocket serverSocket = null;
        try {

            serverSocket = new ServerSocket(9987);

            // HeartThread heartThread = new HeartThread(mList);
            //  heartThread.start();
            while (true) {

                Socket socket = serverSocket.accept();
                MyPortocol portocol = new MyPortocol(socket);

                mList.add(portocol);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        mObList.add(portocol.getAddress());
                    }
                });

                //System.out.println("mList = " + mList.size());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createGetImgThread(MyPortocol portocol){
        mThread = new GetImgThread(portocol);
        //线程图片回调
        mThread.setImageListener(new GetImgThread.Listener() {
            @Override
            public void info(BufferedImage bufferedImage) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        WritableImage writableImage = null;
                        writableImage = SwingFXUtils.toFXImage(bufferedImage, writableImage);
//                                System.out.println("我没错0");
                        imageView.setImage(writableImage);
                    }
                });
            }
        });
        //线程断开回调
        mThread.setStopListener(new GetImgThread.StopListener() {
            @Override
            public void info(GetImgThread g) {
                g.shouldRun = false;

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        mList.remove(g);
                        mObList.remove(g.getPortocol().getAddress());
                        if (mList.size() == 0){
                            imageView.setImage(null);
                            mThread = null;
                        }else{

                        }
                        /**
                        System.out.println("gggg:" + g.getPortocol().getAddress());
                        String address = mList.get(currentIndex).getAddress();
                        if (address!=null && address.equals(g.getPortocol().getAddress())){
                            imageView.setImage(null);
                        }
                        mObList.remove(g.getPortocol().getAddress());
                        */
                    }
                });
            }
        });
        mThread.start();
    }
}

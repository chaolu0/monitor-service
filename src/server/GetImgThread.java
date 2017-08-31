package server;

import net.sf.json.JSONObject;
import org.apache.commons.collections.bag.SynchronizedSortedBag;
import portocol.MyPortocol;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Socket;

public class GetImgThread extends Thread {

    public interface Listener {
        void info(BufferedImage bufferedImage);
    }

    public interface StopListener {
        void info(GetImgThread g);
    }

    private Listener listener;
    private StopListener listener2;

    public void setImageListener(Listener l) {
        this.listener = l;
    }

    public void setStopListener(StopListener l) {
        this.listener2 = l;
    }

    // 通信协议
    private MyPortocol portocol;
    // 是否还连接着

    public boolean shouldRun = true;

    public MyPortocol getPortocol() {
        return portocol;
    }

    public GetImgThread(MyPortocol portocol) {
        this.portocol = portocol;
    }

    public void close() {
        portocol.stop();
        this.stop();
    }

    @Override
    public void run() {

        System.out.println("now the runing socket address = " + portocol.getAddress());
        SendCommand.sendStart(portocol);
        while (shouldRun) {
            try {
                System.out.println("last = " + portocol.getAddress());
                Object object = portocol.getInfomation();
                if (object instanceof byte[]) {
                    byte[] buff = (byte[]) object;
                    ByteArrayInputStream stream = new ByteArrayInputStream(buff);
                    BufferedImage bf = ImageIO.read(stream);
                    stream.close();
                    listener.info(bf);
                }
                /*else {
                    // Json信息
                    JSONObject jsonObject = JSONObject.fromObject(object);
                    String info = (String) jsonObject.get("cmd");
                    if (info.equals("alive")) {
                        alive = true;
                    }
                    String res = (String) object;

                    System.out.println(res);
                }*/

            } catch (Exception e) {
                listener2.info(this);
                e.printStackTrace();
            }

        }

    }


}

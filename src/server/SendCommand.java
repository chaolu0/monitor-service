package server;

import net.sf.json.JSONObject;
import portocol.MyPortocol;

/**
 * Created by caolu on 2017/8/19.
 */
public class SendCommand {
    private static final String CMD_START = "start";
    private static final String CMD_STOP = "stop";
    private static final String CMD_HEART = "heart";
    private static final String CMD_NULL = "shxy";

    public synchronized static boolean sendStart(MyPortocol portocol) {
        JSONObject object = new JSONObject();
        object.accumulate("cmd", CMD_START);
        System.out.println("ip = " + portocol.getAddress());
        System.out.println(object);

        try {
            portocol.sendJson(object);
        } catch (Exception e) {
            System.out.println("send start error");
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public synchronized static void sendStop(MyPortocol portocol) {
        JSONObject object = new JSONObject();
        object.accumulate("cmd", CMD_STOP);
        System.out.println("ip = " + portocol.getAddress());
        System.out.println(object);
        try {
            portocol.sendJson(object);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public synchronized static void sendHeart(MyPortocol portocol) {
        JSONObject json = new JSONObject();
        json.accumulate("cmd", CMD_HEART);
        try {
            portocol.sendJson(json);
            System.out.println(json);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public synchronized static boolean sendNull(MyPortocol portocol) {
        JSONObject json = new JSONObject();
        json.accumulate("cmd", CMD_NULL);
        try {
            portocol.sendJson(json);
            System.out.println(json);
        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
        return true;
    }

}

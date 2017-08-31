package portocol;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MyPortocol {

	private Socket mSocket;
	private boolean alive = true;

	public MyPortocol(Socket socket) {
		this.mSocket = socket;
	}

	public void close(){
		try {
			mSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @return 返回目标Ip地址
	 */
	public String getAddress(){
		return mSocket.getInetAddress().getHostAddress();
	}

	/**
	 *
	 * @return 返回是否还处于连接状态
	 */
	public boolean isConn(){
		return !mSocket.isClosed();
	}

	public void sendFile(File file) throws Exception {

		byte[] filebyte = new byte[(int) file.length()];
		FileInputStream in = new FileInputStream(file);
		in.read(filebyte);
		in.close();
		sendFileArray(filebyte);
	}

	public void sendJson(JSONObject json) throws Exception {
		byte[] jsonSizeB = new byte[4];
		byte[] first = new byte[2];
		char ch = '-';
		first = charToByte(ch);

		String info = json.toString();
		int size = info.length();
		jsonSizeB = int2byte(size);

		mSocket.getOutputStream().write(first);
		mSocket.getOutputStream().write(jsonSizeB);

		mSocket.getOutputStream().write(info.getBytes());
		System.out.println("send finish");
	}

	public void sendFileArray(byte[] array) throws Exception {
		byte[] fileSizeB = new byte[4];
		byte[] first = new byte[2];
		char ch = '*';
		first = charToByte(ch);

		int arrlength = array.length;
		fileSizeB = int2byte(arrlength);
		// 头部
		byte[] head = new byte[4 + 2];
		System.arraycopy(first, 0, head, 0, first.length);
		System.arraycopy(fileSizeB, 0, head, first.length, fileSizeB.length);
		mSocket.getOutputStream().write(head);
		// 文件内容
		int send = 0;
		
		mSocket.getOutputStream().write(array);
	}

	static int time = 0;

	public Object getInfomation() throws Exception {

		byte[] fileSizeB = new byte[4];
		byte[] first = new byte[2];
		InputStream in = mSocket.getInputStream();

		in.read(first, 0, 2);
		in.read(fileSizeB, 0, 4);
		System.out.println("length"+ byte2int(fileSizeB));
		// 文件内容
		if (byteToChar(first) == '*') {

			byte[] filebyte = new byte[byte2int(fileSizeB)];
			//System.out.println(filebyte.length);
			int readCount = 0;
			int l = byte2int(fileSizeB);
			while (readCount < l) {
				readCount += in.read(filebyte, readCount, l - readCount);
				//System.out.println(in.available());
				//System.out.println("in");
			}
			// FileOutputStream outputStream = new FileOutputStream(new
			// File("e:\\temp\\oo" + time + ".jpg"));
			// outputStream.write(filebyte);
			// outputStream.flush();
			// outputStream.close();
			//System.out.println("finish");
			time++;
			return filebyte;
		} else {
			byte[] jsonByte = new byte[byte2int(fileSizeB)];
			int readCount = 0;
			int l = byte2int(fileSizeB);
			while (readCount < l) {
				readCount += in.read(jsonByte, readCount, l - readCount);
			}
			return new String(jsonByte);
		}
	}

	public void stop() {
		try {
			mSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static byte[] int2byte(int res) {
		byte[] targets = new byte[4];

		targets[0] = (byte) (res & 0xff);
		targets[1] = (byte) ((res >> 8) & 0xff);
		targets[2] = (byte) ((res >> 16) & 0xff);
		targets[3] = (byte) (res >>> 24);
		return targets;
	}

	public static int byte2int(byte[] res) {
		// 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000

		int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00) // | 表示安位或
				| ((res[2] << 24) >>> 8) | (res[3] << 24);
		return targets;
	}

	public static byte[] charToByte(char c) {
		byte[] b = new byte[2];
		b[0] = (byte) ((c & 0xFF00) >> 8);
		b[1] = (byte) (c & 0xFF);
		return b;
	}

	public static char byteToChar(byte[] b) {
		char c = (char) (((b[0] & 0xFF) << 8) | (b[1] & 0xFF));
		return c;
	}
}

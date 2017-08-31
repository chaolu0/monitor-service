//package server;
//
//import java.util.List;
//import java.util.Vector;
//
//public class HeartThread extends Thread {
//
//	private static final int HEART_TIME = 1000 * 5;
//	private static final int HEART_WAIT = 1000 * 5;
//	// 所有连接线程
//	private Vector<GetImgThread> allThread;
//
//	public HeartThread(Vector<GetImgThread> list) {
//		this.allThread = list;
//	}
//
//	@Override
//	public void run() {
//		while (true) {
//			synchronized (this) {
//				// System.out.println("allthread" + allThread.size());
//				// 每隔HEART_TIME时间给所有线程发送心跳包
//				for (int i = 0; i < allThread.size(); i++) {
//					if(allThread.get(i).alive == false)
//					{
//						allThread.get(i).close();
//						allThread.remove(i);
//						i--;
//					}
//					allThread.get(i).sendHeartPacket();
//					System.out.println("发送心跳");
//				}
//				try {
//					sleep(HEART_WAIT);
//					// 等待HEART_WAIT时间获取客户端响应，超时，则认为断开连接，从vetcor中移除，并停止该线程
//					for (int i = 0; i < allThread.size(); i++) {
//						if (allThread.get(i).alive == false) {
//							allThread.get(i).close();
//							allThread.remove(allThread.get(i));
//							i--;
//						}
//					}
//					sleep(HEART_TIME);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					// e.printStackTrace();
//				}
//			}
//		}
//	}
//}

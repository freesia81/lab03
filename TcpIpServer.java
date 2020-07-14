package java_sp;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpIpServer implements Runnable {
	
	ServerSocket serverSocket;
	Thread[] threadArr;
	
	// 생성자
	public TcpIpServer(int num) {
		try {
			// 서버소켓을 생성하여 7777번 포트와 bind시킴
			serverSocket = new ServerSocket(7777);
			
			threadArr = new Thread[num];		// 객체 참조배열
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		for(int i=0; i < threadArr.length; i++) {
			threadArr[i] = new Thread(this);	// 객체 생성
			threadArr[i].start();
		}
	}
	
	// Runnable 인터페이스 구현
	public void run() {
		while(true) {
			try {
				Socket socket = serverSocket.accept();
				
				// 소켓의 출력스트림을 얻는다.
				OutputStream out = socket.getOutputStream();
				DataOutputStream dos = new DataOutputStream(out);
				
				// Client로 데이터 전송
				dos.writeUTF("Test Message1 from Server.");
				
				dos.close();
				socket.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		// 5개 쓰레드를 생성하는 서버를 생성한다. 
		TcpIpServer server = new TcpIpServer(5);
		server.start();
	}

}

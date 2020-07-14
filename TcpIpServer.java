package java_sp;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpIpServer implements Runnable {
	
	ServerSocket serverSocket;
	Thread[] threadArr;
	
	// ������
	public TcpIpServer(int num) {
		try {
			// ���������� �����Ͽ� 7777�� ��Ʈ�� bind��Ŵ
			serverSocket = new ServerSocket(7777);
			
			threadArr = new Thread[num];		// ��ü �����迭
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		for(int i=0; i < threadArr.length; i++) {
			threadArr[i] = new Thread(this);	// ��ü ����
			threadArr[i].start();
		}
	}
	
	// Runnable �������̽� ����
	public void run() {
		while(true) {
			try {
				Socket socket = serverSocket.accept();
				
				// ������ ��½�Ʈ���� ��´�.
				OutputStream out = socket.getOutputStream();
				DataOutputStream dos = new DataOutputStream(out);
				
				// Client�� ������ ����
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
		// 5�� �����带 �����ϴ� ������ �����Ѵ�. 
		TcpIpServer server = new TcpIpServer(5);
		server.start();
	}

}

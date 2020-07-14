package java_sp;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.Socket;

public class TcpIpClient {

	public static void main(String[] args) {
		try {
			String serverIp = "127.0.0.1";
			
			// ������ �����Ͽ� ���� ��û��
			Socket socket = new Socket(serverIp, 7777);
			
			// ������ �Է½�Ʈ���� ��´�. 
			InputStream in = socket.getInputStream();
			DataInputStream dis = new DataInputStream(in);
			
			// �����κ��� ���� �����͸� �����Ѵ�.
			String buf = dis.readUTF();
			System.out.println(buf);
			
			dis.close();
			socket.close();
		}
		catch (ConnectException ce) {
			ce.printStackTrace();
		}
		catch (IOException ie) {
			ie.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}

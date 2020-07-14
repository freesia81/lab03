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
			
			// 소켓을 생성하여 연결 요청함
			Socket socket = new Socket(serverIp, 7777);
			
			// 소켓의 입력스트림을 얻는다. 
			InputStream in = socket.getInputStream();
			DataInputStream dis = new DataInputStream(in);
			
			// 서버로부터 받은 데이터를 수신한다.
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

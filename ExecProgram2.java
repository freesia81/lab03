package java_sp;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ExecProgram2 {

	public static void main(String[] args) {

		try {			
			// �ܺ� ���α׷� ����
			//ProcessBuilder builder = new ProcessBuilder("cmd", "/c", "chdir");
			ProcessBuilder b = new ProcessBuilder("cmd", "/c", "java -classpath bin java_sp.MyLineNumberReader");
			b.redirectErrorStream(false);
			Process p = b.start();
			//p.waitFor();
			
			// �ܺ� ���α׷� ��� �б�
			BufferedReader stdOut = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			
			String outputLine = "";
			while ( (outputLine = stdOut.readLine()) != null )
				System.out.println(outputLine);
			
			String errorLine = "";
			while ( (errorLine = stdError.readLine()) != null )
				System.out.println(errorLine);			
			
			p.waitFor();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

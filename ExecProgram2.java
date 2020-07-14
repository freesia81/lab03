package java_sp;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ExecProgram2 {

	public static void main(String[] args) {

		try {			
			// 외부 프로그램 실행
			//ProcessBuilder builder = new ProcessBuilder("cmd", "/c", "chdir");
			ProcessBuilder b = new ProcessBuilder("cmd", "/c", "java -classpath bin java_sp.MyLineNumberReader");
			b.redirectErrorStream(false);
			Process p = b.start();
			//p.waitFor();
			
			// 외부 프로그램 출력 읽기
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

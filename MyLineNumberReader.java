package java_sp;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;

public class MyLineNumberReader {

	public static void main(String[] args) {
		//StringBuilder sbBuilder = new StringBuilder();
		String line;
		
		try {
			// ���� ��ü ����
			File mFile = new File("./INFILE/LOCATION.txt");
			FileReader mFileReader = new FileReader(mFile);
			LineNumberReader mLineReader = new LineNumberReader(mFileReader);
			
			while ((line = mLineReader.readLine()) != null) {
				System.out.println(line);
			}
			
			mLineReader.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

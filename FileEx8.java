package java_sp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/* 
 * 재귀호출을 이용해서 지정한 디렉토리와 서브디렉토리에 포함된 파일의 내용을 읽는 예제
 * 지정된 확장자 파일을 읽는다.
 */

public class FileEx8 {

	public static void main(String[] args) {
		//String dirName = System.getProperty("user.dir");
		String dirName = new String("./INFILE");
		//System.out.println("dir: " + dirName);
		
		File dir = new File(dirName);
		String ext = ".txt";
		
		findInFilesWithExt(dir, ext);
	}
	
	public static void findInFilesWithExt(File dir, String ext) {
		File[] files = dir.listFiles();
		
		for (int i=0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				findInFilesWithExt(files[i], ext);
			}
			else {
				String filename = files[i].getAbsolutePath();
				
				// 확장자가 일치하는지 체크
				if ( filename.endsWith(ext) ) {
					System.out.println(filename);
				}
				
				try {
					// 2-2. 파일 읽기
					FileReader fr = new FileReader(files[i]);
					BufferedReader br = new BufferedReader(fr);
						
					String data;
					while( (data = br.readLine()) != null ) {
						System.out.println(data);
					}
					
					br.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}

package java_sp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/* 
 * ���ȣ���� �̿��ؼ� ������ ���丮�� ������丮�� ���Ե� ������ ������ �д� ����
 * ������ Ȯ���� ������ �д´�.
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
				
				// Ȯ���ڰ� ��ġ�ϴ��� üũ
				if ( filename.endsWith(ext) ) {
					System.out.println(filename);
				}
				
				try {
					// 2-2. ���� �б�
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

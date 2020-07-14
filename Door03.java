package Door03;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Door03 {

	// ������ ���丮�� ������丮�� ���Ե� ���ϸ��� ã�Ƽ� �����θ��� ��ȯ
	public static void searchInDirs(ArrayList<String> list, File dir, String search) {
		File[] files = dir.listFiles();
		
		for (int i=0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				//System.out.println(files[i].getName() + " is a directory.");
				searchInDirs(list, files[i], search);
			}
			else {
				// split�� ���Խ��� ����ϹǷ�, ���ڿ��� .���� �����ϰ� ���� ��� "\\."�� ����ؾ� �� 
				String[] token = files[i].getName().split("\\.");	
				String id = token[0];
				if (search.equals(id)) {
					list.add(files[i].getPath());
				}
			}
		}
	}

	// �־��� ��θ��� ���丮���� �и��ϱ�
	public static void divideDirs(ArrayList<String> list, String dname) {
		// split�� ���Խ��� ����ϹǷ�, ���ڿ��� \���� �����ϰ� ���� ��� "\\\\"�� ����ؾ� �� 
		String[] token = dname.split("\\\\");
		String dirPath = "";

		for(int i=0; i<token.length; i++)	{	
			dirPath = dirPath + token[i] + "/";
			list.add(dirPath);
		}		
	}
	
	public static void main(String[] args) {
		// 1. �ܼ��Է� �ޱ�
		Scanner scanner = new Scanner(System.in);	
		while (scanner.hasNext())
		{
			String s = scanner.nextLine();
			String[] token = s.split("#");
			String id = token[0];
			String no = token[1];
			
			// 2. ���.TXT ���� �˻�
			ArrayList<String> fList = new ArrayList<String>();
			searchInDirs(fList, new File("./INFILE"), id);
			
			// �˻���� Ȯ��
			for(int i=0; i<fList.size(); i++) {
				String fname = fList.get(i);
				System.out.println("file name: " + fname);
				
				// 3. ���丮 ��� ���ϱ�
				ArrayList<String> dList = new ArrayList<String>();
				
				// lastIndexOf�� �̽�����������(\)�� ����Ͽ� \�� �����ڷ� ����� �� ����
				int lastIndex = fname.lastIndexOf("\\");
				divideDirs(dList, fname.substring(0, lastIndex));
				
				for(int j=0; j<dList.size(); j++) {
					String dname = dList.get(j);
					System.out.println(dname);
				}
			}	
		}
	}
}

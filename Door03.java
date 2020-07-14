package Door03;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Door03 {

	// 지정한 디렉토리와 서브디렉토리에 포함된 파일명을 찾아서 절대경로명을 반환
	public static void searchInDirs(ArrayList<String> list, File dir, String search) {
		File[] files = dir.listFiles();
		
		for (int i=0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				//System.out.println(files[i].getName() + " is a directory.");
				searchInDirs(list, files[i], search);
			}
			else {
				// split는 정규식을 사용하므로, 문자열을 .으로 구분하고 싶은 경우 "\\."을 사용해야 함 
				String[] token = files[i].getName().split("\\.");	
				String id = token[0];
				if (search.equals(id)) {
					list.add(files[i].getPath());
				}
			}
		}
	}

	// 주어진 경로명을 디렉토리별로 분리하기
	public static void divideDirs(ArrayList<String> list, String dname) {
		// split는 정규식을 사용하므로, 문자열을 \으로 구분하고 싶은 경우 "\\\\"을 사용해야 함 
		String[] token = dname.split("\\\\");
		String dirPath = "";

		for(int i=0; i<token.length; i++)	{	
			dirPath = dirPath + token[i] + "/";
			list.add(dirPath);
		}		
	}
	
	public static void main(String[] args) {
		// 1. 콘솔입력 받기
		Scanner scanner = new Scanner(System.in);	
		while (scanner.hasNext())
		{
			String s = scanner.nextLine();
			String[] token = s.split("#");
			String id = token[0];
			String no = token[1];
			
			// 2. 사번.TXT 파일 검색
			ArrayList<String> fList = new ArrayList<String>();
			searchInDirs(fList, new File("./INFILE"), id);
			
			// 검색결과 확인
			for(int i=0; i<fList.size(); i++) {
				String fname = fList.get(i);
				System.out.println("file name: " + fname);
				
				// 3. 디렉토리 목록 구하기
				ArrayList<String> dList = new ArrayList<String>();
				
				// lastIndexOf는 이스케이프문자(\)를 사용하여 \를 구분자로 사용할 수 있음
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

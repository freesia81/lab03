package java_sp;

import java.util.Scanner;

public class MySystemIn {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String[] argArr = null;
		
		while (scanner.hasNext())
		{
			// 콘솔로부터 라인단위로 입력받는다. 
			String input = scanner.nextLine();
			
			input = input.trim();		// 입력받은 내용의 앞뒤 공백 제거
			argArr = input.split(" +");	// 입력받은 내용에서 공백을 구분자로 자르기. " +"은 하나 이상의 공백을 의미하는 정규식 표현		
			
			if (input.equals("quit"))
				break;
			
			for(int i=0; i<argArr.length; i++)
				System.out.println(argArr[i]);			
		}

	}

}

package java_sp;

import java.util.Scanner;

public class MySystemIn {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String[] argArr = null;
		
		while (scanner.hasNext())
		{
			// �ַܼκ��� ���δ����� �Է¹޴´�. 
			String input = scanner.nextLine();
			
			input = input.trim();		// �Է¹��� ������ �յ� ���� ����
			argArr = input.split(" +");	// �Է¹��� ���뿡�� ������ �����ڷ� �ڸ���. " +"�� �ϳ� �̻��� ������ �ǹ��ϴ� ���Խ� ǥ��		
			
			if (input.equals("quit"))
				break;
			
			for(int i=0; i<argArr.length; i++)
				System.out.println(argArr[i]);			
		}

	}

}

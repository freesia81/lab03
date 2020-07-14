package Door02;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

class GATEINFO{
	String id;
	TreeSet<String> doorList = new TreeSet<String>();
	
	GATEINFO (String str) {

		String[] token = str.split("#|-");	
		
		int startNo = 0;
		int endNo = 0;
		for(int i=0; i<token.length; i++) {
			switch(i) {
				case 0 : 
					id = token[i];
					break;
				case 1 : 
					startNo = Integer.valueOf(token[i]);
					break;
				case 2 : 
					endNo = Integer.valueOf(token[i]);
					break;						
			}
		}
		
		for(int i=startNo; i<=endNo; i++) {
			String no = String.format("%02d", i);
			doorList.add(no);
		}
	}
	
	void addGateNo(String str) {
		String[] token = str.split("-");
		
		int startNo = Integer.valueOf(token[0]);
		int endNo = Integer.valueOf(token[1]);
		
		for(int i=startNo; i<=endNo; i++) {
			String no = String.format("%02d", i);
			doorList.add(no);
		}
	}
	
	String getDoorList() {
		String result = "";
		String[] noList = doorList.toArray(new String[0]);	// ����� ��ü�� �־��� ��ü�迭�� �����Ͽ� ��ȯ�Ѵ�. 
		
		int i;
		for(i=0; i<noList.length-1; i++)
			result += noList[i] + ",";
		result += noList[i];
		
		return result;
	}
	
	void printDoorList() {
		String[] noList = doorList.toArray(new String[0]);	// ����� ��ü�� �־��� ��ü�迭�� �����Ͽ� ��ȯ�Ѵ�. 
		System.out.println(Arrays.toString(noList));
	}
}

public class Door02 {

	public static void main(String[] args) {
		
		ArrayList<GATEINFO> gList = new ArrayList<GATEINFO>();
		
		try {
			// 1. EMPDATE.TXT ���� �а� ������ �Ľ�
			File inFile = new File("./INFILE/EMPGATE.txt");
			FileReader fr = new FileReader(inFile);
			LineNumberReader lnr = new LineNumberReader(fr);
			
			String line;			
			while ((line = lnr.readLine()) != null) {
				
				boolean bEnroll = false;
				String[] token;
				for(int i=0; i<gList.size(); i++) {
					token = line.split("#");
					GATEINFO gItem = gList.get(i);
					
					// �̹� ��ϵǾ� ������ ���������� ������Ʈ
					if (token[0].equals(gItem.id)) {
						bEnroll = true;
						gItem.addGateNo(token[1]);
						break;
					}					
				}
				
				// ��ϵǾ� ���� ������, �ű� �߰�
				if (bEnroll == false)
					gList.add(new GATEINFO(line));
			}
			lnr.close();						
		
			// ���� ���� Ȯ���ϱ�
			//for(int i=0; i<gList.size(); i++)
			//	gList.get(i).printDoorList();
			
			// 2. GATEHIST.TXT ���� ����
			File outFile = new File("./OUTFILE/GATEHIST.TXT");
			FileWriter fw = new FileWriter(outFile, false);
			BufferedWriter bw = new BufferedWriter(fw);
			
		
			// 3. �ܼ� �Է� (���α׷� ���� X)
			Scanner scanner = new Scanner(System.in);	
			while (scanner.hasNext())
			{
				String s = scanner.nextLine();
				String[] token = s.split("#");
				String id = token[0];
				String no = token[1];
				
				// 4. ���������� �����ϴ��� Ȯ��
				String result = "";
				for(int i=0; i<gList.size(); i++) {
					GATEINFO gItem = gList.get(i);
					
					if (id.equals(gItem.id)) {
						if (gItem.doorList.contains(no))
							result = s + "#Y#" + gItem.getDoorList() + "\n";
						else
							result = s + "#N#" + gItem.getDoorList() + "\n";
						break;
					}
				}
				
				// 5. ���Ͽ� ���				
				bw.write(result);
				bw.flush();
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

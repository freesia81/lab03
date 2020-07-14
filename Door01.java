package Door01;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Scanner;

class GATEINFO{
	String id;
	String gate;
	
	GATEINFO (String str) {
		String[] token = str.split("#");	
		id = token[0];
		gate = token[1];
	}
}

public class Door01 {

	public static void main(String[] args) {
		
		ArrayList<GATEINFO> gList = new ArrayList<GATEINFO>();
		
		// 1. EMPDATE.TXT 파일 읽고 데이터 파싱
		
		try {
			
			File inFile = new File("./INFILE/EMPGATE.txt");
			FileReader fr = new FileReader(inFile);
			LineNumberReader lnr = new LineNumberReader(fr);
			
			String buf;
			while ((buf = lnr.readLine()) != null) {
				gList.add(new GATEINFO(buf));
			}
			lnr.close();						
		
			// 2. GATEHIST.TXT 파일 오픈
			File outFile = new File("./OUTFILE/GATEHIST.TXT");
			FileWriter fw = new FileWriter(outFile, false);
			BufferedWriter bw = new BufferedWriter(fw);
			
		
			// 2. 콘솔 입력
			Scanner scanner = new Scanner(System.in);
			
			while (scanner.hasNext())
			{
				String s = scanner.nextLine();
				GATEINFO req = new GATEINFO(s);
				System.out.println(s);
				
				// 3. 출입정보에 존재하는지 확인
				boolean bExist = false;
				for(int i=0; i<gList.size(); i++) {
					GATEINFO gItem = gList.get(i);
					
					if (req.id.equals(gItem.id) && req.gate.equals(gItem.gate)) {
						bExist = true;
					}
				}
				
				// 4. 파일에 기록
				String result;
				if ( bExist )
					result = s + "#Y\n";
				else
					result = s + "#N\n";
				
				bw.write(result);
				bw.flush();
	
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package Bms02;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


// 사용자정의타입 - 내부클래스 활용
class Data implements Comparable<Data> {
	String id;
	int loc;
	
	Data (String id, int loc) {
		this.id = id;
		this.loc = loc;
	}
	
	Data (String str) {
		String[] token = str.split(",");	
		id = token[0];
		loc = Integer.valueOf(token[1]);
	}
	
	// int형 오름차순 정렬
	public int compareTo(Data o) {
		return this.loc - o.loc;
	}

	// 정수형 내림차순 정렬
	public static Comparator<Data> locDescComparator = new Comparator<Data>() {
		public int compare(Data o1, Data o2) {
			return o2.loc - o1.loc;
		}
	};
	
	// 문자열 오름차순 정렬
	public static Comparator<Data> idComparator = new Comparator<Data>() {
		public int compare(Data o1, Data o2) {
			return o1.id.compareTo(o2.id);
		}
	};
	
	// 문자열 내림차순 정렬
	public static Comparator<Data> idDescComparator = new Comparator<Data>() {
		public int compare(Data o1, Data o2) {
			return o2.id.compareTo(o1.id);
		}
	};
	
	// 문자열 + 정수형 오름차순
	public static Comparator<Data> idAndLocComparator = new Comparator<Data>() {
		public int compare(Data o1, Data o2) {
			if (o1.id.equals(o2.id)) 
				return o1.compareTo(o2);
			else
				return o1.id.compareTo(o2.id);
				
		}
	};
}

class Output implements Comparable<Output>{
	String id;
	Data back;
	Data front;
	
	public int compareTo(Output o) {
		return this.id.compareTo(o.id);
	}
	/*
	public static Comparator<Output> bmsComparator = new Comparator<Output>() {
		public int compare(Output o1, Output o2) {
			return o1.id.compareTo(o2.id);
		}
	};
	*/
}

class Station {
	String id;
	int loc;
	String busId;
	int dis;
	
	Station(String str) {
		String[] token = str.split("#");
		id = token[0];
		loc = Integer.valueOf(token[1]);
	}
}

public class Bms02 {

	// main함수
	public static void main(String[] args) {

		// 1. 파일 읽기
		String line1 = "", line2 = "";
		try {
			File inFile = new File("./INFILE/LOCATION.txt");
			FileReader fr = new FileReader(inFile);
			LineNumberReader lnr = new LineNumberReader(fr);
			
			while ((line2 = lnr.readLine()) != null) {
				if (line2.equals("PRINT")) {
					System.out.println(line1);
					break;
				}
				else {
					line1 = line2;
				}
			}
			lnr.close();				
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		// 2. 사용자정의타입으로 파싱 
		Data[] data;
		
		// '#'와 ','으로 데이터 분리
		String[] token = line1.split("#");
		String date = token[0];
		int cnt = token.length - 1;

		// 객체배열 및 객체생성
		data = new Data[cnt];	
		for(int i=1; i<token.length; i++) {
			//System.out.println(token[i]);
			data[i-1] = new Data(token[i]);	
		}
			for(int i=0; i<cnt; i++)
				System.out.println("id: " + data[i].id + " loc: " + data[i].loc);
			System.out.println();
		
		// 3-1. 객체배열 정렬 (오름차순)
		Arrays.sort(data);		
			for(int i=0; i<cnt; i++)
				System.out.println("id: " + data[i].id + " loc: " + data[i].loc);
			System.out.println();
/*		
		Arrays.sort(data, Data.locDescComparator);
			for(int i=0; i<cnt; i++)
				System.out.println("id: " + data[i].id + " loc: " + data[i].loc);
			System.out.println();
	
		Arrays.sort(data, Data.idComparator);
			for(int i=0; i<cnt; i++)
				System.out.println("id: " + data[i].id + " loc: " + data[i].loc);
			System.out.println();
			
		Arrays.sort(data, Data.idDescComparator);	
			for(int i=0; i<cnt; i++)
				System.out.println("id: " + data[i].id + " loc: " + data[i].loc);
			System.out.println();
			
		Arrays.sort(data, Data.idAndLocComparator);
			for(int i=0; i<cnt; i++)
				System.out.println("id: " + data[i].id + " loc: " + data[i].loc);
			System.out.println();	
*/
				
		// 4. 요구사항 비즈니스 구현
		Output out[] = new Output[cnt];
		
		for(int i=0; i < out.length; i++) {
			out[i] = new Output();
			out[i].id = data[i].id;
			
			if (i == 0) {
				out[i].back = new Data("NOBUS", 0);
			}
			else {
				out[i].back = new Data(data[i-1].id, data[i].loc - data[i-1].loc);
			}
			
			if ( i == out.length-1) {
				out[i].front = new Data("NOBUS", 0);
			}
			else {
				out[i].front = new Data(data[i+1].id, data[i+1].loc - data[i].loc);
			}
		}
		
		// 5. ID 오름차순 정렬
		Arrays.sort(out);
		//for(int i=0; i < out.length; i++)
		//	System.out.println(out[i].id + "#" + out[i].back.id + ", " + out[i].back.loc + "#" + out[i].front.id + ", " + out[i].front.loc);

		
		// 6. 파일 쓰기
		try {
			File outFile = new File("./OUTFILE/PREPOST.txt");
			FileWriter fw = new FileWriter(outFile, false);
			BufferedWriter bw = new BufferedWriter(fw);

			for (int i=0; i < out.length; i++) {
				String line3 = date + "#" + out[i].id + "#" + out[i].front.id + "," + String.format("%05d", out[i].front.loc) 
													  + "#" + out[i].back.id + "," + String.format("%05d", out[i].back.loc);
				bw.write(line3 + "\n");
				bw.flush();
			}
			
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		// 1. STATION.txt 파일 읽기	
		line1 = "";
		ArrayList<Station> list = new ArrayList<Station>();
		try {
			File inFile = new File("./INFILE/STATION.txt");
			FileReader fr = new FileReader(inFile);
			LineNumberReader lnr = new LineNumberReader(fr);
				
			while ((line1 = lnr.readLine()) != null) {
				list.add(new Station(line1));
			}
			
			lnr.close();				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(int i=0; i < list.size(); i++) {
			Station sta = list.get(i);
			System.out.println(sta.id + "# " + sta.loc);
		}

		// 2. 요구사항 구현
		for(int i=0; i < list.size(); i++) {
			Station sta = list.get(i);
			
			// 정류장 위치 > 버스 중 최대 위치
			int maxIdx = -1;	
			
			for(int j=0; j<data.length; j++) {
				if (sta.loc < data[j].loc) {
					break;
				}
				else {
					maxIdx = j;
				}
			}
			
			// 결과 저장
			if (maxIdx == -1) {
				sta.busId = "NOBUS";
				sta.dis = 0;
			}
			else {
				// maxIdx에 해당하는 data 결과 저장
				sta.busId = data[maxIdx].id;
				sta.dis = sta.loc - data[maxIdx].loc;
			}
		}
		
		// 3. 파일 출력
		try {
			File outFile = new File("./OUTFILE/ARRIVAL.txt");
			FileWriter fw = new FileWriter(outFile, false);
			BufferedWriter bw = new BufferedWriter(fw);

			for (int i=0; i < list.size(); i++) {
				Station sta = list.get(i);
				String line3 = date + "#" + sta.id + "#" + sta.busId + "," + String.format("%05d", sta.dis) + "\n";
				bw.write(line3);
				bw.flush();
			}
			
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}

package Bms03;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


//사용자정의타입 
class Data implements Comparable<Data> {
	String id;
	int loc;
	int speed;
	
	// 기본 생성자
	Data (String id, int loc) {
		this.id = id;
		this.loc = loc;
	}
	
	// 데이터 파싱하는 생성자
	Data (String str1, String str2) {
		String[] token1 = str1.split(",");
		String[] token2 = str2.split(",");
		id = token2[0];
		loc = Integer.valueOf(token2[1]);
		speed = Integer.valueOf(token2[1]) - Integer.valueOf(token1[1]);	// m/s
	}
	
	// int형 오름차순 정렬
	public int compareTo(Data o) {
		return this.loc - o.loc;
	}

	// 정수형 내림차순 정렬 (Comparator 객체 생성)
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
	
	// 혼합정렬: 문자열 오름차순 -> 정수형 오름차순
	public static Comparator<Data> idAndLocComparator = new Comparator<Data>() {
		public int compare(Data o1, Data o2) {
			if (o1.id.equals(o2.id)) 
				return o1.compareTo(o2);		// return o1.loc - o2.loc;
			else
				return o1.id.compareTo(o2.id);
				
		}
	};
}

class Output implements Comparable<Output>{
	String id;
	Data back;
	Data front;
	
	// Arrays.sort() 호출 시, 이 함수가 사용됨
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
	int limitSpeed;
	String busId;
	int dis;
	
	// 데이터 파싱하는 생성자
	Station(String str) {
		String[] token = str.split("#");
		id = token[0];
		loc = Integer.valueOf(token[1]);
		limitSpeed = Integer.valueOf(token[2])*1000 / 3600;		// 단위: km/h => m/s 로 변경
	}
}

public class Bms03 {

	// main함수
	public static void main(String[] args) {

		// 1. 파일 읽기
		String line1 = "", line2 = "", buf = "";
		String date = "";
		int secOmit = 0;	// 누락된 초
		try {
			File inFile = new File("./INFILE/LOCATION.txt");
			FileReader fr = new FileReader(inFile);
			LineNumberReader lnr = new LineNumberReader(fr);
			
			while ((buf = lnr.readLine()) != null) {
				if (line2.length() == 0) {			// 최초 데이터
					line2 = buf;
				}
				else if (buf.equals("PRINT")) {		// 종료 조건
					break;
				}
				else if (buf.length() <= 8) {		// 누락된 초 계산
					date = buf;
					secOmit++;
					continue;
				}
				else {								// 정상 데이터
					line1 = line2;
					line2 = buf;
				}
			}
			lnr.close();				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(line1);
		System.out.println(line2);
		
		// 2. 읽은 데이터 파싱 - '#'와 ','으로 데이터 분리 		
		String[] token1 = line1.split("#");
		String[] token2 = line2.split("#");
		
		int cnt = token2.length - 1;		// Data 개수 계산

		// 객체배열 및 객체생성
		Data[] data = new Data[cnt];	
		for(int i=1; i<token2.length; i++) {
			data[i-1] = new Data(token1[i], token2[i]);	
		}
										for(int i=0; i<cnt; i++)
											System.out.println("id: " + data[i].id + " loc: " + data[i].loc + " speed: " + data[i].speed);
										System.out.println();
		
		// 3-1. 객체배열 정렬 (오름차순)
		Arrays.sort(data);		
										for(int i=0; i<cnt; i++)
											System.out.println("id: " + data[i].id + " loc: " + data[i].loc + " speed: " + data[i].speed);
										System.out.println();
/*		
		Arrays.sort(data, Data.locDescComparator);
	
		Arrays.sort(data, Data.idComparator);
			
		Arrays.sort(data, Data.idDescComparator);	
			
		Arrays.sort(data, Data.idAndLocComparator);
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
				out[i].back = new Data(data[i-1].id, (data[i].loc + data[i].speed * secOmit) - (data[i-1].loc + data[i-1].speed * secOmit));
			}
			
			if ( i == out.length-1) {
				out[i].front = new Data("NOBUS", 0);
			}
			else {
				out[i].front = new Data(data[i+1].id, (data[i+1].loc + data[i+1].speed * secOmit) - (data[i].loc + data[i].speed * secOmit));
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
			System.out.println(sta.id + "# " + sta.loc + "#" + sta.limitSpeed);
		}

		// 2. 요구사항 구현
		for(int i=0; i < list.size(); i++) {
			Station sta = list.get(i);
			
			// 정류장 위치 > 버스 중 최대 위치
			int maxIdx = -1;	
			
			for(int j=0; j<data.length; j++) {
				if (sta.loc < (data[j].loc + data[j].speed * secOmit)) {
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
				sta.dis = sta.loc - (data[maxIdx].loc + data[maxIdx].speed * secOmit);
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
		
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		// 버스의 각 정류장 도착 예정 시간 계산
		
		// 1. id순으로 정렬
		Arrays.sort(data, Data.idComparator);
		
		// 2. 최종 걸리는 Sec에서 누락된 Sec만큼을 빼야 함
		int[][] takeSec = new int[list.size()][cnt];
		
		for(int i=0; i<list.size(); i++) {
			
			Station sta = list.get(i);
			
			takeSec[i] = new int[cnt];
			for(int j=0; j<cnt; j++) {				
				if (sta.loc > data[j].loc) {
					
					// STA01 정류장
					if (i == 0) {
						takeSec[i][j] = (sta.loc - data[j].loc) / data[j].speed;
					}
					// STA02 이후 정류장
					else {
						// 버스속도와 정류장제한속도 비교
						Station beforeSta = list.get(i-1);
						
						// 정류장과 버스 위치간의 거리 계산
						if ( takeSec[i-1][j] < 0) {	
							if (beforeSta.limitSpeed < data[j].speed)
								takeSec[i][j] = (sta.loc - data[j].loc) / beforeSta.limitSpeed;
							else
								takeSec[i][j] = (sta.loc - data[j].loc) / data[j].speed;
							
							takeSec[i][j] -= secOmit;
						}
						// 정류장과 정류장간의 거리 계산
						else {
							if (beforeSta.limitSpeed < data[j].speed)
								takeSec[i][j] = takeSec[i-1][j] + (sta.loc - beforeSta.loc)/beforeSta.limitSpeed;
							else
								takeSec[i][j] = takeSec[i-1][j] + (sta.loc - beforeSta.loc)/data[j].speed;
						}
					}
				}
				else {
					takeSec[i][j] = -1;
				}
			}
		}
	
		for(int i=0; i<list.size(); i++) {
			for(int j=0; j<cnt; j++) {
				System.out.print(takeSec[i][j] + " ");
			}
			System.out.println();
		}
		
		
		// 외부 프로그램 실행
		try {			
			ProcessBuilder b = new ProcessBuilder("cmd", "/c", "java -classpath bin Bms03.SIGNAGE");
			b.redirectErrorStream(false);
			Process p = b.start();
			//p.waitFor();
			
			// 외부 프로그램 출력 읽기
			//BufferedReader stdOut = new BufferedReader(new InputStreamReader(p.getInputStream()));
			//BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			
			//String outputLine = "";
			//while ( (outputLine = stdOut.readLine()) != null )
			//	System.out.println(outputLine);
			
			//String errorLine = "";
			//while ( (errorLine = stdError.readLine()) != null )
			//	System.out.println(errorLine);			
	
			// 표준 출력하여 자동으로 표준 입력되도록 연결???
			for(int i=0; i<list.size(); i++) {
				int minSec = 9999;
				int busIdx = 0;
				String strSignage = "";
				Station sta = list.get(i);
				
				for(int j=0; j<cnt; j++) {
					if (takeSec[i][j] > 0 && takeSec[i][j] < minSec)
					{
						minSec = takeSec[i][j];
						busIdx = j;
					}
				}
				
				if (minSec == 9999) {
					// NOBUS 처리
					strSignage = date + "#" + sta.id + "#NOBUS,00:00:00";
				}
				else {
					// LocalTime 시간 계산
					LocalTime nowTime = LocalTime.parse(date);
					LocalTime arrTime = nowTime.plusSeconds(minSec);
					strSignage = date + "#" + sta.id + "#" + data[busIdx].id + "," + arrTime.toString();
				}
				
				//System.out.println(strSignage);	
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));		
				writer.write(strSignage + "\n");
				writer.flush();				
			}
		
			//p.waitFor();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Program Successed");
	}	
}



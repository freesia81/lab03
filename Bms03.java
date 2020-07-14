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


//���������Ÿ�� 
class Data implements Comparable<Data> {
	String id;
	int loc;
	int speed;
	
	// �⺻ ������
	Data (String id, int loc) {
		this.id = id;
		this.loc = loc;
	}
	
	// ������ �Ľ��ϴ� ������
	Data (String str1, String str2) {
		String[] token1 = str1.split(",");
		String[] token2 = str2.split(",");
		id = token2[0];
		loc = Integer.valueOf(token2[1]);
		speed = Integer.valueOf(token2[1]) - Integer.valueOf(token1[1]);	// m/s
	}
	
	// int�� �������� ����
	public int compareTo(Data o) {
		return this.loc - o.loc;
	}

	// ������ �������� ���� (Comparator ��ü ����)
	public static Comparator<Data> locDescComparator = new Comparator<Data>() {
		public int compare(Data o1, Data o2) {
			return o2.loc - o1.loc;
		}
	};
	
	// ���ڿ� �������� ����
	public static Comparator<Data> idComparator = new Comparator<Data>() {
		public int compare(Data o1, Data o2) {
			return o1.id.compareTo(o2.id);
		}
	};
	
	// ���ڿ� �������� ����
	public static Comparator<Data> idDescComparator = new Comparator<Data>() {
		public int compare(Data o1, Data o2) {
			return o2.id.compareTo(o1.id);
		}
	};
	
	// ȥ������: ���ڿ� �������� -> ������ ��������
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
	
	// Arrays.sort() ȣ�� ��, �� �Լ��� ����
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
	
	// ������ �Ľ��ϴ� ������
	Station(String str) {
		String[] token = str.split("#");
		id = token[0];
		loc = Integer.valueOf(token[1]);
		limitSpeed = Integer.valueOf(token[2])*1000 / 3600;		// ����: km/h => m/s �� ����
	}
}

public class Bms03 {

	// main�Լ�
	public static void main(String[] args) {

		// 1. ���� �б�
		String line1 = "", line2 = "", buf = "";
		String date = "";
		int secOmit = 0;	// ������ ��
		try {
			File inFile = new File("./INFILE/LOCATION.txt");
			FileReader fr = new FileReader(inFile);
			LineNumberReader lnr = new LineNumberReader(fr);
			
			while ((buf = lnr.readLine()) != null) {
				if (line2.length() == 0) {			// ���� ������
					line2 = buf;
				}
				else if (buf.equals("PRINT")) {		// ���� ����
					break;
				}
				else if (buf.length() <= 8) {		// ������ �� ���
					date = buf;
					secOmit++;
					continue;
				}
				else {								// ���� ������
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
		
		// 2. ���� ������ �Ľ� - '#'�� ','���� ������ �и� 		
		String[] token1 = line1.split("#");
		String[] token2 = line2.split("#");
		
		int cnt = token2.length - 1;		// Data ���� ���

		// ��ü�迭 �� ��ü����
		Data[] data = new Data[cnt];	
		for(int i=1; i<token2.length; i++) {
			data[i-1] = new Data(token1[i], token2[i]);	
		}
										for(int i=0; i<cnt; i++)
											System.out.println("id: " + data[i].id + " loc: " + data[i].loc + " speed: " + data[i].speed);
										System.out.println();
		
		// 3-1. ��ü�迭 ���� (��������)
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
				
		// 4. �䱸���� ����Ͻ� ����
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
		
		// 5. ID �������� ����
		Arrays.sort(out);
		//for(int i=0; i < out.length; i++)
		//	System.out.println(out[i].id + "#" + out[i].back.id + ", " + out[i].back.loc + "#" + out[i].front.id + ", " + out[i].front.loc);

		
		// 6. ���� ����
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
		
		// 1. STATION.txt ���� �б�	
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

		// 2. �䱸���� ����
		for(int i=0; i < list.size(); i++) {
			Station sta = list.get(i);
			
			// ������ ��ġ > ���� �� �ִ� ��ġ
			int maxIdx = -1;	
			
			for(int j=0; j<data.length; j++) {
				if (sta.loc < (data[j].loc + data[j].speed * secOmit)) {
					break;
				}
				else {
					maxIdx = j;
				}
			}
			
			// ��� ����
			if (maxIdx == -1) {
				sta.busId = "NOBUS";
				sta.dis = 0;
			}
			else {
				// maxIdx�� �ش��ϴ� data ��� ����
				sta.busId = data[maxIdx].id;
				sta.dis = sta.loc - (data[maxIdx].loc + data[maxIdx].speed * secOmit);
			}
		}
		
		// 3. ���� ���
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
		// ������ �� ������ ���� ���� �ð� ���
		
		// 1. id������ ����
		Arrays.sort(data, Data.idComparator);
		
		// 2. ���� �ɸ��� Sec���� ������ Sec��ŭ�� ���� ��
		int[][] takeSec = new int[list.size()][cnt];
		
		for(int i=0; i<list.size(); i++) {
			
			Station sta = list.get(i);
			
			takeSec[i] = new int[cnt];
			for(int j=0; j<cnt; j++) {				
				if (sta.loc > data[j].loc) {
					
					// STA01 ������
					if (i == 0) {
						takeSec[i][j] = (sta.loc - data[j].loc) / data[j].speed;
					}
					// STA02 ���� ������
					else {
						// �����ӵ��� ���������Ѽӵ� ��
						Station beforeSta = list.get(i-1);
						
						// ������� ���� ��ġ���� �Ÿ� ���
						if ( takeSec[i-1][j] < 0) {	
							if (beforeSta.limitSpeed < data[j].speed)
								takeSec[i][j] = (sta.loc - data[j].loc) / beforeSta.limitSpeed;
							else
								takeSec[i][j] = (sta.loc - data[j].loc) / data[j].speed;
							
							takeSec[i][j] -= secOmit;
						}
						// ������� �����尣�� �Ÿ� ���
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
		
		
		// �ܺ� ���α׷� ����
		try {			
			ProcessBuilder b = new ProcessBuilder("cmd", "/c", "java -classpath bin Bms03.SIGNAGE");
			b.redirectErrorStream(false);
			Process p = b.start();
			//p.waitFor();
			
			// �ܺ� ���α׷� ��� �б�
			//BufferedReader stdOut = new BufferedReader(new InputStreamReader(p.getInputStream()));
			//BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			
			//String outputLine = "";
			//while ( (outputLine = stdOut.readLine()) != null )
			//	System.out.println(outputLine);
			
			//String errorLine = "";
			//while ( (errorLine = stdError.readLine()) != null )
			//	System.out.println(errorLine);			
	
			// ǥ�� ����Ͽ� �ڵ����� ǥ�� �Էµǵ��� ����???
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
					// NOBUS ó��
					strSignage = date + "#" + sta.id + "#NOBUS,00:00:00";
				}
				else {
					// LocalTime �ð� ���
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



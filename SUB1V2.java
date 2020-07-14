package java_sp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.LineNumberReader;
import java.util.Arrays;
import java.util.Comparator;


// ���������Ÿ�� - ����Ŭ���� Ȱ��
class Bus implements Comparable<Bus> {
	String id;
	int loc;
	
	Bus (String id, int loc) {
		this.id = id;
		this.loc = loc;
	}
	
	Bus (String str) {
		String[] token = str.split(",");	
		id = token[0];
		loc = Integer.valueOf(token[1]);
	}
	
	public String toString() {
		return this.id + " " + this.loc;
	}
	
	// int�� �������� ����
	public int compareTo(Bus o) {
		return this.loc - o.loc;
	}

	// ������ �������� ����
	public static Comparator<Bus> locDescComparator = new Comparator<Bus>() {
		public int compare(Bus o1, Bus o2) {
			return o2.loc - o1.loc;
		}
	};
	
	// ���ڿ� �������� ����
	public static Comparator<Bus> idComparator = new Comparator<Bus>() {
		public int compare(Bus o1, Bus o2) {
			return o1.id.compareTo(o2.id);
		}
	};
	
	// ���ڿ� �������� ����
	public static Comparator<Bus> idDescComparator = new Comparator<Bus>() {
		public int compare(Bus o1, Bus o2) {
			return o2.id.compareTo(o1.id);
		}
	};
	
	// ���ڿ� + ������ ��������
	public static Comparator<Bus> idAndLocComparator = new Comparator<Bus>() {
		public int compare(Bus o1, Bus o2) {
			if (o1.id.equals(o2.id)) 
				return o1.compareTo(o2);
			else
				return o1.id.compareTo(o2.id);
				
		}
	};
}

class PrePost implements Comparable<PrePost>{
	String id;
	Bus preBus;		// ���� - ����
	Bus postBus;	// ���� - ����
	
	public int compareTo(PrePost o) {
		return this.id.compareTo(o.id);
	}

	public static Comparator<PrePost> bmsComparator = new Comparator<PrePost>() {
		public int compare(PrePost o1, PrePost o2) {
			return o1.id.compareTo(o2.id);
		}
	};

}

public class SUB1V2 {

	// main�Լ�
	public static void main(String[] args) {

		// 1. ���� �б�
		String line1 = "", buf = "";
		try {
			File inFile = new File("./INFILE/LOCATION.txt");
			FileReader fr = new FileReader(inFile);
			LineNumberReader lnr = new LineNumberReader(fr);
			
			while ((buf = lnr.readLine()) != null) {
				if (buf.equals("PRINT")) {
					System.out.println(line1);
					break;
				}
				else {
					line1 = buf;
				}
			}
			lnr.close();				
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		// 2. ���������Ÿ������ �Ľ� 
		Bus[] bus;
		
		// '#'�� ','���� ������ �и�
		String[] token = line1.split("#");
		String date = token[0];
		int cnt = token.length - 1;

		// ��ü�迭 �� ��ü����
		bus = new Bus[cnt];	
		for(int i=1; i<token.length; i++) {
			//System.out.println(token[i]);
			bus[i-1] = new Bus(token[i]);	
		}
			for(int i=0; i<cnt; i++)
				System.out.println(bus[i].toString());
			System.out.println();
		
		// 3-1. ��ü�迭 ���� (��������)
		Arrays.sort(bus);		
			for(int i=0; i<cnt; i++)
				System.out.println(bus[i].toString());
			System.out.println();
/*		
		Arrays.sort(Bus, Bus.locDescComparator);
			for(int i=0; i<cnt; i++)
				System.out.println("id: " + Bus[i].id + " loc: " + Bus[i].loc);
			System.out.println();
	
		Arrays.sort(Bus, Bus.idComparator);
			for(int i=0; i<cnt; i++)
				System.out.println("id: " + Bus[i].id + " loc: " + Bus[i].loc);
			System.out.println();
			
		Arrays.sort(Bus, Bus.idDescComparator);	
			for(int i=0; i<cnt; i++)
				System.out.println("id: " + Bus[i].id + " loc: " + Bus[i].loc);
			System.out.println();
			
		Arrays.sort(Bus, Bus.idAndLocComparator);
			for(int i=0; i<cnt; i++)
				System.out.println("id: " + Bus[i].id + " loc: " + Bus[i].loc);
			System.out.println();	
*/

		// 4. �䱸���� ����Ͻ� ����
		PrePost prepost[] = new PrePost[cnt];
		
		for(int i=0; i < prepost.length; i++) {
			prepost[i] = new PrePost();
			prepost[i].id = bus[i].id;
			
			if (i == 0) {
				prepost[i].preBus = new Bus("NOBUS", 0);
			}
			else {
				prepost[i].preBus = new Bus(bus[i-1].id, bus[i].loc - bus[i-1].loc);
			}
			
			if ( i == prepost.length-1) {
				prepost[i].postBus = new Bus("NOBUS", 0);
			}
			else {
				prepost[i].postBus = new Bus(bus[i+1].id, bus[i+1].loc - bus[i].loc);
			}
		}
		
		// 5. ID �������� ����
		Arrays.sort(prepost);
		for(int i=0; i < prepost.length; i++)
			System.out.println(prepost[i].id + "#" + prepost[i].preBus.id + ", " + prepost[i].preBus.loc + "#" + prepost[i].postBus.id + ", " + prepost[i].postBus.loc);
		
		// 6. ���� ����
		try {
			File outFile = new File("./OUTFILE/PREPOST.txt");
			FileWriter fw = new FileWriter(outFile, false);
			BufferedWriter bw = new BufferedWriter(fw);

			for (int i=0; i < prepost.length; i++) {
				String line3 = date + "#" + prepost[i].id + "#" + prepost[i].postBus.id + "," + String.format("%05d", prepost[i].postBus.loc) 
													  + "#" + prepost[i].preBus.id + "," + String.format("%05d", prepost[i].preBus.loc);
				bw.write(line3 + "\n");
				bw.flush();
			}
			
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
}

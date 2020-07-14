package java_sp;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class CalcTime {

	public static void main(String[] args) {
		// 날짜
		LocalDate date1 = LocalDate.parse("2020-05-05");
		LocalDate date2 = date1.plusDays(27);
		
		System.out.println(date1.toString());
		System.out.println(date2.toString());
		
		Period pe  = Period.between(date1, date2);
		long year  = pe.get(ChronoUnit.YEARS);
		long month = pe.get(ChronoUnit.MONTHS);
		long day   = pe.get(ChronoUnit.DAYS);
		
		System.out.println("Period: " + year + " " + month + " " + day);
		
		
		// 시간
		//LocalTime time = LocalTime.of(11, 30, 15);	// 11시 30분 15초
		String s = "11:30:15";
		LocalTime time1 = LocalTime.parse(s);
		LocalTime time2 = time1.plusSeconds(59);
		
		System.out.println(time1.toString());
		System.out.println(time2.toString());
		
		Duration du = Duration.between(time1, time2);
		long sec = du.get(ChronoUnit.SECONDS);
		
		System.out.println("Duration: " + sec);		
	}

}

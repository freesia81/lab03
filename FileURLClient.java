package java_sp;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

public class FileURLClient {
	public static void main(String[] args) throws Exception {
		
		/* 
		 * 외부 클래스 동적 로딩
		 */
		// Create a File object on the root of the directory containing the class file
		File file = new File("C:\\Users\\shinyr\\eclipse-workspace\\Atype\\bin");
		
		// 1. Convert File to a URL
		URL url = file.toURI().toURL();
		URL[] urls = new URL[] {url};
		System.out.println("URL객체 : " + url.toString());
		
		// 2. Create a new class loader with the directory
		URLClassLoader ucl = new URLClassLoader(urls);
		
		// 3. Load in the class
		Class reflectClass = ucl.loadClass("Atype.MyClass");
			
		// 4. java.lang.Class 클래스의 주요 메서드 활용하여 정보 획득
		String className = reflectClass.getName();		
		System.out.println("클래스명 : " + className);		
		
		
		/*
		 *  Java Reflection (객체를 통해 클래스 정보를 분석하는 프로그램 기법) 
		 */
		// 디버그 출력용 - java.lang.reflect.Method 클래스의 주요 메서드 활용
		Method[] methods = reflectClass.getDeclaredMethods();	
		
		for(Method m : methods) {
			System.out.println("----------------------------------------------");
			System.out.println("Method객체 :  " + m.toString());
			
			String methodName = m.getName();
			System.out.println("메서드 이름: " + methodName);
			
			Class methodRetType = m.getReturnType();
			System.out.println("리턴    타입: " + methodRetType.toString());
			
			Parameter[] parameters = m.getParameters();
			for(Parameter para : parameters)
				System.out.println("매개변수 종류: " + para.toString());
			
			Class[] methodParaTypes = m.getParameterTypes();			
			for(Class paraType : methodParaTypes)
				System.out.println("매개변수 타입: " + paraType.toString());	
		}
		System.out.println();
		
		/*
		 * 실제로 메서드 호출 
		 */
		
		// 1. public int mySum(int,int) - 일반 메서드 호출  : Method.invoke(Object, ...)
		Method method = reflectClass.getDeclaredMethod("mySum", int.class, int.class);
		Object obj = reflectClass.newInstance();
		Object[] paramObject = new Object[] {1, 2};
		
		int sum = (int) method.invoke(obj, paramObject);
		System.out.println("mySum 메서드 리턴값: " + sum);

		
		// 2. public static void printAddr(java.lang.String) - static 메서드 호출 : Method.invoke(null, ...)
		Method staticMethod = reflectClass.getDeclaredMethod("printAddr", String.class);
		Object[] paramObject2 = new Object[] {"Seoul Korea"};
		staticMethod.invoke(null, paramObject2);
	} 
	
	/*
	URL[] urlArray = { 
			new URL("http", "www.java.com", "/"), 
			new URL("ftp", "user:pass@www.java.com", "/")
	};
	*/


}

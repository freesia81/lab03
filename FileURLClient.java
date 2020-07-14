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
		 * �ܺ� Ŭ���� ���� �ε�
		 */
		// Create a File object on the root of the directory containing the class file
		File file = new File("C:\\Users\\shinyr\\eclipse-workspace\\Atype\\bin");
		
		// 1. Convert File to a URL
		URL url = file.toURI().toURL();
		URL[] urls = new URL[] {url};
		System.out.println("URL��ü : " + url.toString());
		
		// 2. Create a new class loader with the directory
		URLClassLoader ucl = new URLClassLoader(urls);
		
		// 3. Load in the class
		Class reflectClass = ucl.loadClass("Atype.MyClass");
			
		// 4. java.lang.Class Ŭ������ �ֿ� �޼��� Ȱ���Ͽ� ���� ȹ��
		String className = reflectClass.getName();		
		System.out.println("Ŭ������ : " + className);		
		
		
		/*
		 *  Java Reflection (��ü�� ���� Ŭ���� ������ �м��ϴ� ���α׷� ���) 
		 */
		// ����� ��¿� - java.lang.reflect.Method Ŭ������ �ֿ� �޼��� Ȱ��
		Method[] methods = reflectClass.getDeclaredMethods();	
		
		for(Method m : methods) {
			System.out.println("----------------------------------------------");
			System.out.println("Method��ü :  " + m.toString());
			
			String methodName = m.getName();
			System.out.println("�޼��� �̸�: " + methodName);
			
			Class methodRetType = m.getReturnType();
			System.out.println("����    Ÿ��: " + methodRetType.toString());
			
			Parameter[] parameters = m.getParameters();
			for(Parameter para : parameters)
				System.out.println("�Ű����� ����: " + para.toString());
			
			Class[] methodParaTypes = m.getParameterTypes();			
			for(Class paraType : methodParaTypes)
				System.out.println("�Ű����� Ÿ��: " + paraType.toString());	
		}
		System.out.println();
		
		/*
		 * ������ �޼��� ȣ�� 
		 */
		
		// 1. public int mySum(int,int) - �Ϲ� �޼��� ȣ��  : Method.invoke(Object, ...)
		Method method = reflectClass.getDeclaredMethod("mySum", int.class, int.class);
		Object obj = reflectClass.newInstance();
		Object[] paramObject = new Object[] {1, 2};
		
		int sum = (int) method.invoke(obj, paramObject);
		System.out.println("mySum �޼��� ���ϰ�: " + sum);

		
		// 2. public static void printAddr(java.lang.String) - static �޼��� ȣ�� : Method.invoke(null, ...)
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

package org.noneorone.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sa {

	public static void main(String[] args) {
		String content = " sdfsdf                 dsfsfsd           sd";
//		content = content.replace("(^\\s*)/g", "");
		System.out.println(content);
		Pattern p = Pattern.compile("^\\s*\\n$"); // ������ʽ
		Matcher m = p.matcher(content); // �������ַ���
		String s = m.replaceAll(""); //�滻����ַ���
		System.out.println(s);
	}
}

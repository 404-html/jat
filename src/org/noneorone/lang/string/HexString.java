package org.noneorone.lang.string;

/**
 * Title: JavaTech<br>
 * Description: �ַ���ת16������������<br>
 * Copyright: Copyright (c) 2011 <br>
 * Create DateTime: Dec 29, 2011 4:24:10 PM <br>
 * 
 * @author ����
 */
public class HexString {

	public static String string2HexString(String strText) throws Exception {
		char c;
		String strRet = "";
		int intAsc;
		String strHex;
		for (int i = 0; i < strText.length(); i++) {
			c = strText.charAt(i);
			intAsc = (int) c;
			if (intAsc > 128) {
				strHex = Integer.toHexString(intAsc);
				strRet += "\\u" + strHex;
			} else {
				strRet = strRet + c;
			}
		}
		return strRet;
	}

	public static void main(String[] args) {
		try {
			System.out.println(string2HexString("����vsС��"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

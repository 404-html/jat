package org.noneorone.util.code;

import java.util.Vector;

public class Commission {

	public static void main(String[] args) {
		
		/**�����ַ�תƴ��**/
		ChineseToLetter trans = new ChineseToLetter();
		Vector<String> name = trans.getLetter("����С��");
		System.out.println(trans.toString(name).toLowerCase());
		Vector<String> ans = trans.expand("����С��");
		System.out.println(ans);
		
		/**��ת��**/
	   FanToJian jf = new FanToJian();   
	   System.out.println(jf.conver("����ô˵���أ�",1));   
	}
}

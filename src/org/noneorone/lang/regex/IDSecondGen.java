package org.noneorone.lang.regex;

public class IDSecondGen {
	/**
	 *�˷����������ڵڶ������֤��18λ���֤��
	 */
	public static void main(String[] args){
		if (args.length == 0) {
			System.out.println("�������������֤��");
			System.exit(0);
		}
		//17λ��Ȩ���ӣ������֤��ǰ17λ������ˡ�
		int w[] = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
		int sum = 0;//���漶����
		for(int i = 0; i < args[0].length() -1 ; i++){
			sum += new Integer(args[0].substring(i,i+1)) * w[i];
		}
		/**
		 *У��������һ������ó��Ľ����11ȡģ���õ��Ľ�����Ӧ���ַ��������֤���һλ��Ҳ����У��λ�����磺0��Ӧ���������һ��Ԫ�أ��Դ����ơ�
		 */
		String sums[] = {"1","0","X","9","8","7","6","5","4","3","2"};
		if (sums[(sum%11)].equals(args[0].substring(args[0].length()-1,args[0].length()))) {//�����֤���һλ�Ƚ�		
			System.out.println("���֤������ȷ");

		}else {		
			System.out.println("���֤���벻��ȷ");

		}

	}
}

package org.sunnysolong.sms.mail;

public class Commission {

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		// �������Ҫ�������ʼ�
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.yeah.net");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("ifanever@yeah.net");
		mailInfo.setPassword("04251006Bm");// ������������
		mailInfo.setFromAddress("ifanever@yeah.net");
		mailInfo.setToAddress("wangmeng0122@gmail.com");
		mailInfo.setSubject("Java�ʼ����Ͳ���");
		mailInfo.setContent("�ɹ�����");
		// �������Ҫ�������ʼ�
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendTextMail(mailInfo);// ���������ʽ
		sms.sendHtmlMail(mailInfo);// ����html��ʽ
	}
}

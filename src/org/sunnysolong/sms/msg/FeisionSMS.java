package org.sunnysolong.sms.msg;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;

/**
 * @author Terry Email: yaoxinghuo at 126 dot com
 * @version create: Aug 5, 2009 11:17:23 AM
 * @version update: Oct 15, 2009 00:11:00 AM
 * @description ����API(fetionlib) HTTP�򵥵��þ���
 *              ����Restlet�ķ�ʽ�ɹ����á���ҳ�������ҳ:http://fetionlib.appspot.com/
 *              С��ʾ����ѿ�ͨ���ţ���������ֻ���û�п�ͨ���ţ����Ե��й��ƶ�������վ�鿴��ͨ������ֱ�ӱ༭����KTFX���͵�10086��ͨ
 *              �޸ķ������룺�ֻ��༭�����루6��16λ�����������Ļ�ȫ���ַ������ݷ��͵�12520050
 *              ֱ������������������µ�ַ�������ֻ���������������и��ģ����벻Ҫ����/�����Ѻ�������д�Լ����ֻ����룩��
 *              http://fetionlib.appspot.com/restlet/fetion/13812345678/password/13912345678/message
 *              �����ĸ���/�����ļӴֵĵط���Ӧ�÷ֱ��滻�ɣ������ֻ��š����롢�Է��ֻ��ţ�����д�Լ����ֻ��ŷ����Լ������������ݣ�������180�֣���
 *              ������������û�п�ͨ���ţ��Է����������ѵ�ԭ���ܷ��Ͷ��Ƿ�����ʾMessage Not Sent��ֻ�гɹ�����OK
 *              ���Ҫ�������ģ������URLEncode��UTF-8���룬�硰��á�Encode��Ϊ%E4%BD%A0%E5%A5%BD������֧�֣������ٵ����ӣ�POST��ʽ��ע����õ�URL���в�ͬ��
 *              ����������յ��Լ������Լ��Ķ��ţ���ϲ��������ͨ���������������Ϥ������ͨ��POST��GET���ã����ø�ʽ�뿴����Java���ӣ�������������
 *              �������ʻ��API�Ľӿڵ��÷�ʽ���κθ��õĽ��飬��ӭ����������
 * 
 * ���Ѿ�����֧��ȡ�ú����б�POST��ʽ��Ⱥ��(8����8�����º���)�Ͷ�ʱ����Ⱥ��(��ʱȺ�����30������)���뿴���µ�����
 * 
 * ���½��ڷ����������ñ���������˷��ͺ�ը���ţ��������������ɧ�ţ�ͬʱҲ�������ı�վ��Դ�������������ƣ�
 * ͬһ���ֻ��Ÿ�ͬһ�����ѵķ�����API�Լ�������API���磺��Ӻ��ѡ���ȡ�����б�ȣ�������Ϊ30�룬30���ڵ����������޷���ɡ�
 * ע�����ǵ�ʵ����Ҫ�����Լ����Ͷ��ţ��ֻ��źͶԷ����Ѻ�����ͬ����Ⱥ��������������Լ��ֻ��ţ���API���󽫲�����30��ʱ���������ƣ�
 * 
 * ������API�ӿڳ�����Googleǿ������������йܣ������ڱ�����ʾ�������õ���json�����뵽www.json.org����jar����Ҳ�ɵ���������
 */
public class FeisionSMS {
	private static Log log = LogFactory.getLog(FeisionSMS.class);

	public static void main(String[] args) {
		// ���Է�����
		boolean b = fetchToSendSMS("13812345678", "12345678",
				new String[] { "13812345678" }, "TestMessage");
		System.out.println("Send Message result:" + b);

		// ����ȡ�ú����б�
		// JSONArray friends = fetchToGetFriends("13812345678", "12345678");
		// System.out.println("friends:\r\n"+ (friends == null ? "null" :
		// friends.toString()));

		// ������Ӻ���
		// int result = fetchToAddFriend("13812345678",
		// "12345678","13812345678","TestMyName", "TestFriendName");
		// System.out.println("Add Friend result:"+result);

		// ���Է��Ͷ�ʱ����(ע����̫ƽ��ʱ�䣬����2009-10-09 01:00 �Ǳ���ʱ��09:00����)
		// String sid = fetchToSendScheduleMsg("13812345678", "12345678", new
		// String[]{"13912345678"}, "TestScheduleMessage", "2009-10-09 01:00");
		// System.out.println("sid:"+sid);

		// ����ɾ����ʱ����
		// boolean b2 = fetchToDeleteScheduleMsg("13812345678", "12345678",
		// "123456");
		// System.out.println("schedule message delete result:"+b2);
	}

	private static final int TRY_TIMES = 3;
	private static final int TIME_OUT = 30000;

	/**
	 * ���Ͷ���Ϣ ���򵥵�Get��ʽ����֧��Ⱥ������ҪȺ��������POST��ʽ���Ѹ��£���ֱ������������������µ�ַ,�ֻ���������������иĵ���
	 * http://fetionlib.appspot.com/restlet/fetion/13812345678/password/13912345678/message
	 * �ɹ�����OK ���򷵻�Message Not
	 * Sent�����ҪȺ�����������������/������Ҫ�ύ������Ϣ������ܵ���������������µĳ���POST��ʽ�� ע�����String[]
	 * friends �е���������Ǻ��ѵ��ֻ���,Ҳ�����Ǻ����ó���ȡ���ĺ��ѵ�uri���������ȡ�ú����б��˵��
	 * ��fetchToSendSMS("13812345678","password",new
	 * String[]{"sip:12345678@fetion.com.cn;p=5065","13916416465","tel:15912345678"},"Test");
	 * ���������ܳ���8�����������Ҫ�����ó���ֿ�����ε���
	 * 
	 * ע�⣺��ͬ�ֻ��ţ���ͬ���ѵ�����ĵ��ü��Ҫ����30�룬���򲻳ɹ���responseCode:406���������ܺ����а������Լ����ֻ��ŵ�������30������ƣ�
	 */
	public static boolean fetchToSendSMS(String mobile, String password,
			String[] friends, String message) {
		// ����UUID��Ŀ���Ƿ�ֹ������������ڷ��������Ѿ��ɹ����Ͷ��ţ�ȴ�ڷ��ؽ����������������
		// �����¿ͻ��˼����������󣬴�ʱ�÷���������UUID�ֱ���������Ѿ����͹��������ٴη��Ͷ��š�
		String uuid = UUID.randomUUID().toString();
		for (int i = 0; i < TRY_TIMES; i++) {
			int responseCode = 0;
			try {
				URL postUrl = new URL(
						"http://fetionlib.appspot.com/restlet/fetion");
				HttpURLConnection connection = (HttpURLConnection) postUrl
						.openConnection();
				connection.setConnectTimeout(TIME_OUT);
				connection.setReadTimeout(TIME_OUT);
				connection.setDoOutput(true);
				connection.setRequestMethod("POST");
				connection.setUseCaches(false);
				connection.setInstanceFollowRedirects(true);
				connection.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
				connection.connect();
				DataOutputStream out = new DataOutputStream(connection
						.getOutputStream());
				String content = "mobile=" + mobile + "&uuid=" + uuid
						+ "&password=" + password + "&friend="
						+ convertArrayToJSONString(friends) + "&message="
						+ URLEncoder.encode(message, "utf-8");
				out.writeBytes(content);

				out.flush();
				out.close();

				responseCode = connection.getResponseCode();
				connection.disconnect();
				if (responseCode == 202)
					return true;
				else
					return false;
			} catch (Exception e) {
				log.warn("error fetchToSendSMS, exception:" + e.getMessage()
						+ ". tried " + i + " times");
			}
		}
		return false;
	}

	// ������ת����JSONString
	private static String convertArrayToJSONString(String[] arr)
			throws Exception {
		JSONArray ja = new JSONArray();
		for (String a : arr)
			ja.put(a);// ja.add(a);//?
		return URLEncoder.encode(ja.toString(), "UTF-8");
	}

}
package org.noneorone.util.code;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class FileToBase64 {
	/**
	 * <p>���ļ�ת��base64 �ַ���</p>
	 * @param path �ļ�·��
	 * @return
	 * @throws Exception
	 */
	public static String encodeBase64File(String path) throws Exception {
		File  file = new File(path);
		FileInputStream inputFile = new FileInputStream(file);
		byte[] buffer = new byte[(int)file.length()];
		inputFile.read(buffer);
        inputFile.close();
        return new BASE64Encoder().encode(buffer);
	}
	/**
	 * <p>��base64�ַ����뱣���ļ�</p>
	 * @param base64Code
	 * @param targetPath
	 * @throws Exception
	 */
	public static void decoderBase64File(String base64Code,String targetPath) throws Exception {
		byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
		FileOutputStream out = new FileOutputStream(targetPath);
		out.write(buffer);
		out.close();
	}
	/**
	 * <p>��base64�ַ������ı��ļ�</p>
	 * @param base64Code
	 * @param targetPath
	 * @throws Exception
	 */
	public static void toFile(String base64Code,String targetPath) throws Exception {
		byte[] buffer = base64Code.getBytes();
		FileOutputStream out = new FileOutputStream(targetPath);
		out.write(buffer);
		out.close();
	}
	public static void main(String[] args) {
		try {
			String base64Code =encodeBase64File("F:\\picture\\53b6109egw1dnw8u0m9csg.gif");
			System.out.println(base64Code);
			decoderBase64File(base64Code, "D:\\base66_pic.jpg");
			toFile(base64Code, "D:\\base64_file.txt");			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

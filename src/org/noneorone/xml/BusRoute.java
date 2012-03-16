package org.noneorone.xml;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/** 
 * Title: base<br> 
 * Description: DOM Parser<br> 
 * Copyright: Copyright (c) 2011 <br> 
 * Create DateTime: Jun 14, 2011 5:46:40 PM <br> 
 * @author wangmeng
 */
public class BusRoute {

	
	private static String interceptBlankChar(String content){
		//����ո��Ʊ�������з����س���ƥ�����
		Pattern pattern = Pattern.compile("\\s*|\t|\r|\n");
		//ͨ��ƥ������֤�ı�����
		Matcher matcher = pattern.matcher(content);
		//�滻���е�����ƥ�������ַ�Ϊ���ַ�������ȡ���˿ո�
		return matcher.replaceAll("").trim();
	}
	
	public static void main(String[] args){
		
		//������������
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		//������
		DocumentBuilder builder = null;
		//�ı�����
		Document doc = null;
		
		try {
			//ͨ�������ഴ��������
			builder = factory.newDocumentBuilder();
			//ͨ������������ָ��·�����ļ��������ظ��ı�����doc
			doc = builder.parse("F:/bus_travel.xml");
			//��ȡָ����ǩ�ڵ�
			NodeList nodeList = doc.getElementsByTagName("card");
			for(int i=0; i<nodeList.getLength(); i++){
				//��ȡ�ýڵ��������ӽڵ�
				NodeList childNodes = nodeList.item(i).getChildNodes();
				int count = 0;
				for(int j=0; j<childNodes.getLength(); j++){
					//��ȡp��ǩ�ڵ�
					if(childNodes.item(j).getNodeName().equalsIgnoreCase("p")){
						count ++;
						//ȡ������p��ǩ�ڲ�
						if(count == 3){
							NodeList pNodes = childNodes.item(j).getChildNodes();
							for(int k=0; k<pNodes.getLength(); k++){
								//�Ƴ��ڲ���img�ڵ�
								if(pNodes.item(k).getNodeName().equalsIgnoreCase("img")){
									childNodes.item(j).removeChild(pNodes.item(k));
								}
							}
							//ѭ���Ƴ�img��ǩ��������ӽڵ�
							for(int m=0; m<pNodes.getLength(); m++){
								Node node = pNodes.item(m);
								//��ȡ�ڲ��ı����ݲ�Ϊnull�����Ϊ�յ����нڵ�
								if(null != node.getTextContent() && node.getTextContent().trim().length() > 0){
									if(null != node.getBaseURI()){
										System.out.println("***"+interceptBlankChar(node.getTextContent())+"***");
//										System.out.println("***"+interceptBlankChar(node.getNodeValue())+"***##"+pNodes.item(m+1).getNodeName()+"####");
									}
//									System.out.println("*********"+node.getBaseURI()+"***********");
									if(node.getNextSibling().getNodeName().equalsIgnoreCase("a")){
//										System.out.println("next is a link ...");
									}
//									if(node.getNodeName().equalsIgnoreCase("a")){
//									}
									System.out.println(node.getNodeName() + "=========" + interceptBlankChar(node.getTextContent()));
								}
							}
						}
					}
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}

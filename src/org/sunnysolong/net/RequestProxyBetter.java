package org.sunnysolong.net;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import java.io.*;

/**
 * 
* Title: Υ�²�ѯ<br>
* Description: <br>
* Copyright: Copyright (c) 2011 <br>
* Create DateTime: Jun 29, 2011 11:43:57 AM <br>
* @author wangmeng
 */
public class RequestProxyBetter{
	
  public static final String TARGET_URL = "http://www.gdgajj.com/cx/wzss/wzss.do";
  
  
  //-------------------------------------------------
  public static String getCarStatus(String jc, String hphm ,String hpzl,String lxdh){
    URL url;
    URLConnection urlConnection;
    HttpURLConnection httpURLConnection;
    
    String retStr = "";
    String content = "";
    
    hphm = hphm.toUpperCase();
    String carNo1 = jc+hphm ;
    carNo1 = carNo1.toUpperCase();

    try {
    	url = new URL(TARGET_URL);
    	urlConnection = url.openConnection();
    	httpURLConnection = (HttpURLConnection)urlConnection;
    	httpURLConnection.setRequestProperty("Connection","close"); //"Keep-Alive" "close"
    	httpURLConnection.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; (R1 1.5); .NETSPX2; .NET CLR 2.0.50727)");
    	httpURLConnection.setRequestProperty("Cache-Control","no-cache");
    	httpURLConnection.setRequestMethod("POST");
    	httpURLConnection.setDoOutput(true);
    	content="method=%E6%9F%A5%E8%AF%A2&jc=" + java.net.URLEncoder.encode(jc,"UTF-8") + "&hphm=" + hphm + "&hpzl=" + hpzl + "&lxdh=" + lxdh + "&image2.x=44&image2.y=10";
    	System.out.println(content+"----------------");
    	httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    	httpURLConnection.setRequestProperty("Content-Length", "" + (content.getBytes()).length );
    	httpURLConnection.setRequestProperty("Referer",TARGET_URL);

    	OutputStream output = httpURLConnection.getOutputStream();
    	output.write(content.getBytes());
    	output.flush();
    	output.close();
      
    	httpURLConnection.connect();
    	int statusCode = httpURLConnection.getResponseCode();
    	if((statusCode != HttpURLConnection.HTTP_OK) && (statusCode != 100)) {
    		retStr = "1";
    		return retStr;
    	} else {
    		readerHandle(httpURLConnection.getInputStream(),"C:\\rp.html");
//    		
//    		DataInputStream dis = new DataInputStream(httpURLConnection.getInputStream());
//    		ByteArrayOutputStream out = new ByteArrayOutputStream();
//    	  
//    		byte[] b = new byte[1024];
//    		int count = 0;
//    		int total = 0;
//    		while ((count = dis.read(b)) != -1) {
//    			out.write(b,0,count);
//    			total += count;
//    		}
//    		byte[] ret_value = out.toByteArray();
//    		out.close();
//    		dis.close();
//    		String tmpBuf = new String(ret_value,"UTF-8");
//    		//����HTML
//    		retStr=parseGd(carNo1,tmpBuf);
    	}
    } catch (Exception e) {
      System.out.println("[Exception] - " + e.toString());
      retStr="1";
      return retStr;
    }
    
    return retStr;
  }
  //-------------------------------------------------
  public static String parseGd(String carNo,String inData){
    String retStr=carNo+"Υ����Ϣ:\r\n";
    String flagStr = "��Ǹ��ϵͳά���У����Ժ����ԡ�";
    
    String tmpBuf=inData;
    String splitStr = "��ѯ�������:";
    String splitStr0 = "�ý�ͨΥ���м�¼��";
    String splitStr1 = "�ý�ͨΥ���޼�¼��";
    String splitStr2 = "�����/�������/������:";
    String splitStr3 = "Υ��ʱ��:";
    String splitStr4 = "Υ���ص�:";
    String splitStr5 = "Υ����Ϊ����:";
    String splitStr9 = "�����������������˶Ժ��ٲ飡";
    String splitStr15 = "<input type=\"hidden\"";
    String splitStr16 = "images/sysbusy.jpg";
    
    int off;
    //����HTML
    try {
      //�������緱æ
      off = tmpBuf.indexOf(splitStr16);
      if(off>0) {
        retStr = flagStr;
        return retStr;
      }
      
      off = tmpBuf.indexOf(splitStr);
      if(off<0) {
        retStr = flagStr;
        return retStr;
      }
      tmpBuf = tmpBuf.substring(off, tmpBuf.length());
      off = tmpBuf.indexOf(splitStr0);
      if(off>0) {
        //��Υ�¼�¼
        while(true) {
          off=tmpBuf.indexOf(splitStr2);
          if(off<0) {
            break;
          }
          tmpBuf = tmpBuf.substring(off, tmpBuf.length());
          off = tmpBuf.indexOf(splitStr15);
          retStr = retStr + tmpBuf.substring(0, off);
          tmpBuf = tmpBuf.substring(off+splitStr15.length(), tmpBuf.length());
          
          off=tmpBuf.indexOf(splitStr3);
          if( off<0 ) {
            retStr=flagStr;
            return retStr;
          }
          tmpBuf = tmpBuf.substring(off,tmpBuf.length());
          off = tmpBuf.indexOf(splitStr15);
          retStr = retStr + tmpBuf.substring(0,off);
          tmpBuf = tmpBuf.substring(off+splitStr15.length(), tmpBuf.length());
          
          off = tmpBuf.indexOf(splitStr4);
          if(off<0) {
            retStr = flagStr;
            return retStr;
          }
          tmpBuf = tmpBuf.substring(off,tmpBuf.length());
          off = tmpBuf.indexOf(splitStr15);
          retStr = retStr + tmpBuf.substring(0,off);
          tmpBuf = tmpBuf.substring(off+splitStr15.length(), tmpBuf.length());
          
          off=tmpBuf.indexOf(splitStr5);
          if(off<0) {
            retStr=flagStr;
            return retStr;
          }
          tmpBuf = tmpBuf.substring( off, tmpBuf.length() );
          off = tmpBuf.indexOf(splitStr15);
          retStr = retStr + tmpBuf.substring(0, off);
          tmpBuf = tmpBuf.substring(off+splitStr15.length(), tmpBuf.length());
          retStr = retStr +"\r\n";
          
        }
      } else if(tmpBuf.indexOf(splitStr1)>0){
        //û��Υ�¼�¼
        retStr=retStr + "�޽�ͨΥ����¼��";
      }else if(tmpBuf.indexOf(splitStr9)>0){
        retStr = retStr + "������ĳ��ƺ��복��β�Ų�ƥ�䣬��˶Ժ��ٲ飡";
      }
    } catch (Exception e) {
      System.out.println("[Exception] - " + e.toString());
      retStr="1";
      return retStr;
    }
    retStr=retStr.replaceAll(" ","");
    retStr=retStr.replaceAll("\t","");
    retStr=retStr.replaceAll("&nbsp;", "");
    retStr=retStr.replaceAll(":\r\n",":");
    retStr=retStr.replaceFirst(":",":\r\n");
    return retStr;
  }
  
  
	private static void readerHandle(InputStream is, String filePath){
		BufferedReader br = null;
		BufferedWriter bw = null;
		File file = null;
		String content = "";
		try {
			file = new File(filePath);
			if(file.exists()){
				file.delete();
			}
			file.createNewFile();
			br = new BufferedReader(new InputStreamReader(is));
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			while(null != (content = br.readLine())){
				bw.append(content);
			}
			bw.close();
			br.close();
			Runtime.getRuntime().exec("explorer " + filePath + "");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}  
  
  
  //*
  //-------------------------------------------------
  public static void main(String[] args) {
    try{
      String retStr="";
      retStr=getCarStatus("��","B12U40","02","1051");
//      retStr=getCarStatus("��","B14U80","02","1095");
//      retStr=getCarStatus("��","BLG610","02","8497");
//      retStr=getCarStatus("��","B14","02","1000");
      System.out.println(retStr);
    }catch(Exception e){
      e.printStackTrace();
    }
  }
  
}

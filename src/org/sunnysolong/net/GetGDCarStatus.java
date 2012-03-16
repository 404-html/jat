package org.sunnysolong.net;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;

import java.text.*;
import java.io.*;

public class GetGDCarStatus{
  public static String urlStr_guangdong="http://www.gdgajj.com/cx/wzss/wzss.do";
  
  //-------------------------------------------------
  public static String getToday() {
    java.util.Date inDate = new java.util.Date();
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    return sf.format(inDate);
  }
  
  //-------------------------------------------------
  public static void writeFile( String outName, byte[] b ) {
    BufferedOutputStream stream = null;
    File file = null;
    try{
      file = new File(outName);
      FileOutputStream fstream = new FileOutputStream(file);
      stream = new BufferedOutputStream(fstream);
      stream.write(b);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (stream != null) {
        try {
          stream.close();
        } catch (IOException e1){
          e1.printStackTrace();
        }
      }
    }
  }
  
  //-------------------------------------------------
  public static String getParam(String carNo ,String carType,String hphm ){
    return urlStr_guangdong;
  }
  
  //-------------------------------------------------
  public static String getCarStatus(String jc, String carNo ,String carType,String hphm){
    URL m_URL;
    URLConnection m_URLConn;
    HttpURLConnection m_HttpConn;
    
    String retStr = "";
    int iHttpResult;
    String urlStr = "";
    String tmpBuf = "";
    
    String content = "";
    
    carNo = carNo.toUpperCase();
    String carNo1 = jc+carNo ;
    carNo1 = carNo1.toUpperCase();

    try {
      urlStr = getParam(carNo,carType,hphm);
      m_URL = new URL(urlStr);
      m_URLConn = m_URL.openConnection();
      m_HttpConn = (HttpURLConnection)m_URLConn;

      m_HttpConn.setRequestProperty("Connection","close"); //"Keep-Alive" "close"
      m_HttpConn.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; (R1 1.5); .NETSPX2; .NET CLR 2.0.50727)");
      m_HttpConn.setRequestProperty("Cache-Control","no-cache");
      m_HttpConn.setRequestMethod("POST");
      m_HttpConn.setDoOutput(true);
      content="method=%E6%9F%A5%E8%AF%A2&jc="+java.net.URLEncoder.encode(jc,"UTF-8")+"&hphm="+carNo+"&hpzl="+carType+"&lxdh="+hphm+"&image2.x=44&image2.y=10";
      m_HttpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      m_HttpConn.setRequestProperty("Content-Length", ""+(content.getBytes()).length );
      m_HttpConn.setRequestProperty("Referer",urlStr_guangdong);
      
      OutputStream output = m_HttpConn.getOutputStream();
      output.write(content.getBytes());
      output.flush();
      output.close();
      
      m_URLConn.connect();
      iHttpResult=m_HttpConn.getResponseCode();
      if(iHttpResult!=HttpURLConnection.HTTP_OK && iHttpResult!=100) {
        retStr="1";
        return retStr;
      } else {
        DataInputStream dis=new DataInputStream(m_URLConn.getInputStream());
        ByteArrayOutputStream out = null;
        out = new ByteArrayOutputStream();
        byte[] b = null;
        b = new byte[1024];  //128
        int nRead = 0;
        int total=0;
        while ((nRead = dis.read(b)) != -1) {
          out.write(b,0,nRead);
          total += nRead;
        }
        byte ret_value[] = null;
        ret_value = out.toByteArray();
        out.close();
        dis.close();
        tmpBuf = new String(ret_value,"UTF-8");
        //����HTML
        retStr=parseGd(carNo1,tmpBuf);
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

package com.hanji.main.crawler;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class FileUrlDownload {
 /**
  * 버퍼 사이즈
  */
 final static int size = 1024;

 /**
  * fileAddress에서 파일을 읽어, 다운로드 디렉토리에 다운로드
  * 
  * @param fileAddress
  * @param localFileName
  * @param downloadDir
  */
 public static void fileUrlReadAndDownload(String fileAddress,
   String localFileName, String downloadDir) {
  OutputStream outStream = null;
  URLConnection uCon = null;

  InputStream is = null;
  try {

   System.out.println("-------Download Start------");

   URL Url;
   byte[] buf;
   int byteRead;
   int byteWritten = 0;
   Url = new URL(fileAddress);
   outStream = new BufferedOutputStream(new FileOutputStream(
     downloadDir + "\\" + localFileName));

   uCon = Url.openConnection();
   is = uCon.getInputStream();
   buf = new byte[size];
   while ((byteRead = is.read(buf)) != -1) {
    outStream.write(buf, 0, byteRead);
    byteWritten += byteRead;
   }

   System.out.println("Download Successfully.");
   System.out.println("File name : " + localFileName);
   System.out.println("of bytes  : " + byteWritten);
   System.out.println("-------Download End--------");

  } catch (Exception e) {
   e.printStackTrace();
  } finally {
   try {
    is.close();
    outStream.close();
   } catch (IOException e) {
    e.printStackTrace();
   }
  }
 }
}
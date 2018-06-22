package com.hanji.main.crawler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
//galid/ x페이지부터x페이지까지/ 저장경로/


/**
 * @author
 * URL의 doc객체를 반환해주는 클래스
 */
public class JsoupConnector {
	
	public Document getJsoupDoc(String URL, HashMap<String, String> parameters, String host) {
		Document doc = null;
		
		if(parameters.size()>0) {
			for(Map.Entry<String, String> entry : parameters.entrySet()){
				URL+="&"+entry.getKey()+"="+entry.getValue();  
			}
		}
		System.out.println("connect URL : "+URL);
		try {
			doc = Jsoup.connect(URL)
					.header("Accept",
							"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
					.header("Accept-Encoding", "gzip, deflate")
					.header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
					.header("Connection", "keep-alive")
					.header("Host", host)
					.header("If-Modified-Since", "Fri, 22 Jun 2018 05:01:54 GMT")
					.header("If-None-Match", "143700fb9537f0f4f2594302e44bcdf7-gzip")
					.header("Upgrade-Insecure-Requests", "1")
					.userAgent(
							"Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.87 Safari/537.36")
					.maxBodySize(Integer.MAX_VALUE).ignoreContentType(true).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return doc;
	}
}

package com.hanji.main.crawler;

import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class Parser {
	//그림이포함된 article의 번호를 Array<String>배열로 리턴한다.
	public ArrayList<String> getImgArticle(Document listDoc) {
		//그림이 포함된 article번호를 담는다.
		ArrayList<String> arr= new ArrayList<String>();
		
		
		// 그림이 포함된글 검색
		for (Element e : listDoc.select(".icon_pic_n")) {
			// 이미지가있는글
			String existImageArticle = e.parent().siblingElements().select(".t_notice").text();
			arr.add(existImageArticle);
			
			System.out.println(existImageArticle);
			//Document articleDoc = app.getDocFromArticle(existImageArticle);
			// 몇페이지까지 검색할것인지 지정후 검색된 Document반환
			HashMap<String, String> articleParameters = new HashMap<String, String>();
			articleParameters.put("no", existImageArticle);
			Document articleDoc = JsoupConnector.getInstance().getJsoupDoc("http://gall.dcinside.com/board/view/?id=leagueoflegends2", articleParameters, "dcimg7.dcinside.co.kr");
			
			for (Element e2 : articleDoc.select(".s_write img")) {
				System.out.println(e2.attr("src"));
				String imgURL = e2.attr("src");
				System.out.println("img path : " + imgURL);
			}
		}
		return arr;
	}
	
	//그림이포함된 Article의 src를 가져온다.
	public String getImgSrc(Document articleDoc) {
		
		String imgURL ="";
		for (Element e2 : articleDoc.select(".s_write img")) {
			System.out.println(e2.attr("src"));
			imgURL = e2.attr("src");
			System.out.println("img path : " + imgURL);
			
		}
		return imgURL;
	}
	
}

package com.hanji.main;

import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.hanji.main.crawler.FileUrlDownload;
import com.hanji.main.crawler.JsoupConnector;

public class Appmain {
	public static void main(String[] args) {
		// 다운로드경로
		String savePath = "D:\\info\\mycrawler\\download";

			// 몇페이지까지 검색할것인지 지정후 검색된 Document반환
			JsoupConnector conn = new JsoupConnector();
			HashMap<String, String> listParameters = new HashMap<String, String>();
			listParameters.put("page", "1");
			Document listDoc = conn.getJsoupDoc("http://gall.dcinside.com/board/lists/?id=leagueoflegends2", listParameters, "gall.dcinside.com");

			String jsonBody = listDoc.body().text();
			System.out.println(jsonBody);
			
			//분리해야할부분
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
				Document articleDoc = conn.getJsoupDoc("http://gall.dcinside.com/board/view/?id=leagueoflegends2", articleParameters, "dcimg7.dcinside.co.kr");
				
				for (Element e2 : articleDoc.select(".s_write img")) {
					System.out.println(e2.attr("src"));
					String imgURL = e2.attr("src");
					System.out.println("img path : " + imgURL);
					FileUrlDownload.fileUrlReadAndDownload(imgURL, existImageArticle + ".jpg", savePath);
				}
			}
		
	}
}

package com.hanji.main;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.hanji.main.crawler.FileUrlDownload;


public class Appmain {
	public static void main(String[] args) {
		//다운로드경로
		String savePath = "D:\\info\\mycrawler\\download";
		// 현재페이지에서 옆에보이는 다섯개 글의 이름을 모아 배열에 담는다.
		Appmain app = new Appmain();
		for(int PageNum=0; PageNum<10; PageNum++) {
		Document doc = app.getDocFromList(PageNum);

		String jsonBody = doc.body().text();
		System.out.println(jsonBody);

		// Elements info = doc.select(".icon_pic_n");

		for (Element e : doc.select(".icon_pic_n")) {
			// 이미지가있는글
			String existImageArticle = e.parent().siblingElements().select(".t_notice").text();
			
			System.out.println(existImageArticle);
			Document articleDoc = app.getDocFromArticle(existImageArticle);
			for (Element e2 : articleDoc.select(".s_write img")) {
				System.out.println(e2.attr("src"));
				String imgURL = e2.attr("src");
				System.out.println("img path : " + imgURL);
				FileUrlDownload.fileUrlReadAndDownload(imgURL, existImageArticle+".jpg", savePath);
			}
		}
		}
	}

	public Document getDocFromList(int pageNum) {
		String url = "http://gall.dcinside.com/board/lists/?id=leagueoflegends2&page="+pageNum;

		Document doc = null;
		try {
			doc = Jsoup.connect(url).header("Host", "gall.dcinside.com")
					.header("Accept",
							"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
					.header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
					.userAgent(
							"Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.87 Safari/537.36")
					.maxBodySize(Integer.MAX_VALUE).ignoreContentType(true).get();
			return doc;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

	public Document getDocFromArticle(String articleNum) {
		String url = "http://gall.dcinside.com/board/view/?id=leagueoflegends2&no=" + articleNum;
		System.out.println(url + "접속");
		Document doc = null;
		try {
			doc = Jsoup.connect(url)
					.header("Accept",
							"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
					.header("Accept-Encoding", "gzip, deflate")
					.header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7").header("Connection", "keep-alive")
					.header("Host", "dcimg7.dcinside.co.kr")
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

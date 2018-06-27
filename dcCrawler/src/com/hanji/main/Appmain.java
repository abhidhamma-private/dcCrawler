package com.hanji.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.hanji.main.crawler.Downloader;
import com.hanji.main.crawler.JsoupConnector;
import com.hanji.main.crawler.Parser;

import util.FileUrlDownload;

public class Appmain {
	public static void main(String[] args) {

		// 1.이미지가 존재하는 게시글을 가져온다.
		HashMap<String, String> listParameters = new HashMap<String, String>();
		listParameters.put("page", "1");
		Document listDoc = JsoupConnector.getInstance().getJsoupDoc(
				"http://gall.dcinside.com/board/lists/?id=leagueoflegends2", listParameters, "gall.dcinside.com");
		Parser parser = new Parser();
		ArrayList<String> existImageArticles = parser.getImgArticle(listDoc);

		// 2.imgURL을 imgURLs에 담는다
		ArrayList<String> imgURLs = new ArrayList<String>();
		for (String existImageArticleNum : existImageArticles) {
			HashMap<String, String> articleParameters = new HashMap<String, String>();
			articleParameters.put("no", existImageArticleNum);
			Document articleDoc = JsoupConnector.getInstance().getJsoupDoc(
					"http://gall.dcinside.com/board/view/?id=leagueoflegends2", articleParameters,
					"dcimg7.dcinside.co.kr");
			imgURLs.add(parser.getImgSrc(articleDoc));
		}

		// 3.저장
		String savePath = "D:\\info\\mycrawler\\download";
		int i = 0;
		for (String imgURL : imgURLs) {
			FileUrlDownload.fileUrlReadAndDownload(imgURL, existImageArticles.get(i) + ".jpg", savePath);
			System.out.println("URL주소 : " + imgURL);
			i++;
		}

	}
}

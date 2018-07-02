package com.hanji.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.nodes.Document;

import com.hanji.main.crawler.JsoupConnector;
import com.hanji.main.crawler.TaskExecutor;
import com.hanji.main.crawler.task.Parser;

import util.FileUrlDownload;

public class Appmain {
	private static final Logger logger = Logger.getLogger( Appmain.class.getName() );
	
	public static void main(String[] args) {
		logger.setUseParentHandlers(true);
		long start = System.currentTimeMillis();
		logger.log(Level.ALL, "START!");
		
		// 1.이미지가 존재하는 게시글을 가져온다.
		HashMap<String, String> listParameters = new HashMap<String, String>();
		listParameters.put("page", "1");
		Document listDoc = JsoupConnector.getInstance().getJsoupDoc(
				"http://gall.dcinside.com/board/lists/?id=leagueoflegends2", listParameters, "gall.dcinside.com");
		Parser parser = new Parser();
		ArrayList<String> existImageArticles = parser.getImgArticle(listDoc);
		
		//Executor실행
		for(String existImageArticlesNum:existImageArticles)
			new TaskExecutor(500, 1, 5, existImageArticlesNum);
		
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
		
		long end = System.currentTimeMillis();

		System.out.println( "실행 시간 : " + ( end - start )/1000.0 );
		//non-thread 실행시간  : 22.239s
		//thread 실행시간 :28.89...오히려 더걸린다.
		logger.log(Level.ALL, "END!");
	}
}

package com.hanji.main.crawler.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.hanji.main.Appmain;
import com.hanji.main.crawler.JsoupConnector;

import util.FileUrlDownload;


public class Parser implements Runnable{
	private static final Logger logger = Logger.getLogger( Parser.class.getName() );
	
	int socketTimeOut; 
	int socketTimeOutRetries; 
	int workerCount; 
	String existImageArticlesNum;
	
	public Parser(int socketTimeOut, int socketTimeOutRetries, int workerCount, String existImageArticlesNum) {
		this.socketTimeOut = socketTimeOut;
		this.socketTimeOutRetries = socketTimeOutRetries;
		this.workerCount = workerCount;
		this.existImageArticlesNum = existImageArticlesNum;
		logger.setUseParentHandlers(true);
	}
	
	public Parser() {}
	
	@Override
	public void run() {
		logger.info("THREAD RUN!");
		String imgURL;
		HashMap<String, String> articleParameters = new HashMap<String, String>();
		articleParameters.put("no", existImageArticlesNum);
		Document articleDoc = JsoupConnector.getInstance().getJsoupDoc(
				"http://gall.dcinside.com/board/view/?id=leagueoflegends2", articleParameters,
				"dcimg7.dcinside.co.kr");
		imgURL = this.getImgSrc(articleDoc);
		
		String savePath = "D:\\info\\mycrawler\\download";
		
		FileUrlDownload.fileUrlReadAndDownload(imgURL, existImageArticlesNum + ".jpg", savePath);
		System.out.println("URL주소 : " + imgURL);
		logger.info("THREAD END!");
	}
	
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

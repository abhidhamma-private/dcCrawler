package com.hanji.main.crawler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hanji.main.Appmain;
import com.hanji.main.crawler.task.Parser;

public class TaskExecutor {
	private static final Logger logger = Logger.getLogger( TaskExecutor.class.getName() );
	
	public TaskExecutor(int socketTimeOut, int socketTimeOutRetries, int workerCount, String existImageArticlesNum){
		logger.setUseParentHandlers(true);
		ExecutorService detailPageSearchThreadPool = Executors.newFixedThreadPool( workerCount == 0 ? 2 : workerCount );
		
		//쓰레드실행
		detailPageSearchThreadPool.submit(new Parser(socketTimeOut, socketTimeOutRetries, workerCount, existImageArticlesNum));
		logger.log( Level.CONFIG, " --- ThreadPool will be shut down!" );
		
		detailPageSearchThreadPool.shutdown();	// 영화 상세페이지 조회 스레드가 모두 종료되면 스레드풀을 종료한다.

		logger.log( Level.CONFIG, "   --- ThreadPool has been shut down!" );
		
	}
}

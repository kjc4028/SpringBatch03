package com.jckim.www;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SpringBatchTasklet implements Tasklet {
	static int cnt = 0;
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        System.out.println("start...");
        
        
    		String serviceKey = "";
    		try {
    			//API Test
    			String urlStr = "http://api.korea.go.kr/openapi/svc?serviceKey="+serviceKey+"&format=xml&svcId=000000496430&";
    			URL url = new URL(urlStr);
    			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
    			httpConn.setDoOutput(true);
    			httpConn.setRequestProperty("Accept", "application/xml");
    			System.out.println(httpConn.getResponseCode() + " / " + httpConn.getResponseMessage());
    			
    			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    			Document doc = dBuilder.parse(urlStr);
    			
    			NodeList nList = doc.getElementsByTagName("result");
    			NodeList nList3 = doc.getElementsByTagName("result");
    			
    			Node node = (Node) nList.item(0);
    			
    			Element element = (Element) node;
    			
    			nList = element.getElementsByTagName("resultCode").item(0).getChildNodes();
    			nList3 = element.getElementsByTagName("resultMessage").item(0).getChildNodes();
    			
    			Node node2 = (Node) nList.item(0);
    			Node node3 = (Node) nList3.item(0);
    			
    			System.out.println(node2.getNodeValue());
    			System.out.println(node3.getNodeValue());
    			
    			httpConn.disconnect();
    			++cnt;
    			System.out.println("cnt : " + cnt);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        System.out.println("end!");
        return RepeatStatus.FINISHED;
	}
}

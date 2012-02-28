package com.expedia.tools.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;

public class FileUtil {

	private static String stoneUrl = System.getProperty("user.dir")
			+ "//WebRoot//WEB-INF//exp.html";
	private static String urlPrefix = "file:/localhost/";

	public static void write(String content) throws IOException {
		StringReader sr = new StringReader(content);
		File f = new File(stoneUrl);
		Lock lock = new ReentrantLock();
		lock.lock();
		try {
			if (!f.exists()) {
				f.createNewFile();
			}
			FileOutputStream writer = new FileOutputStream(f);
			int factByte = 0;

			while ((factByte = sr.read()) != -1) {
				writer.write(factByte);
				writer.flush();
			}
		} finally {
			lock.unlock();
		}
	}

	public static String getExtenrnalVisitPath() {
		return urlPrefix + stoneUrl;

	}

	public static String getStreamString(InputStream inputStream) {
		StringBuffer sb = new StringBuffer();
		if (inputStream != null) {

			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(inputStream));

			String tempOneLine = null;

			try {
				while ((tempOneLine = bufferedReader.readLine()) != null) {
					sb.append(tempOneLine);

				}
			} catch (IOException e) {
				throw new RuntimeException(
						"transform InputStream to String occur error "
								+ e.getMessage(), e);
			}
		}
		return sb.toString();

	}

	public static void main(String[] args) {
		try {
			
			WebConversation webConversation = new WebConversation(); 
			//HttpUnitOptions.setExceptionsThrownOnScriptError(false);
			WebResponse response = webConversation.getResponse("file:/d:/mydoc/exp.html"); 
	
			response.getScriptableObject().doEvent("submit1()");
			//obj.doEvent("javascript:XBFX();");
		//	obj.doEventScript("javascript:XBFX();");
			//System.out.println(obj.doEventScript("XBFX();"));
			
			


			//HttpProxyRequest h=new HttpProxyRequest();
			//System.out.println(h.openLocalHtml("file:/d:/mydoc/exp.html"));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

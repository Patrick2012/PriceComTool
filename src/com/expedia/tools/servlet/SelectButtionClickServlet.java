package com.expedia.tools.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.expedia.tools.util.DataParse;
import com.expedia.tools.util.HttpProxyRequest;

public class SelectButtionClickServlet extends HttpServlet {

	private static final long serialVersionUID = 7999029164790263179L;
	private final static String expediaURL = "www.expedia.com";

	public SelectButtionClickServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		HttpProxyRequest porxy = new HttpProxyRequest();
		PrintWriter writer = response.getWriter();
		writer.println("name= " + request.getParameter("id"));
		ServletContext context = this.getServletContext();
		String roundTripHTML = (String) context.getAttribute("roundTripHTML");
		DataParse dataParse = new DataParse();

		String qsrc = dataParse.parseHtmlSelectButtonLink(roundTripHTML, "a",
				"externalLinkOfChooseFlight");

		Map<String, String> parameters1 = new LinkedHashMap<String, String>();
		parameters1.put("action", "flightResults@processLink");
		parameters1
				.put("flightResults_inpLinkParameters#1",
						"linkType=1|"
								+ qsrc
								+ "FltI1=FLTIStr%26iflt1=42%26ftyp=published%26pric=182.6%26bprc=161.0%26txfe=[object Object]%26ccod=USD");

		String actionCode1 = dataParse.parseHtmlGetSessionCodeById(
				roundTripHTML, "FORM", "flightResultForm");

		String str[] = actionCode1.split("\\?");

		String response2 = porxy.postRequestExpedia("http://" + expediaURL
				+ "/Flights-Search-RoundTrip?" + str[1], parameters1);

		String s = new String(response2.getBytes("iso8859-1"), "utf-8");

		// System.out.println(s);

		Map<String, String> m = dataParse.parseHtmlGetInputByName(s, "form",
				"redir");

		String t = porxy.postRequestExpedia(
				"http://www.expedia.com/pub/agent.dll", m);

		// FileUtil.write(s);

		Map<String, String> m1 = dataParse.parseHtmlGetInputByName(t, "FORM",
				"QSREDIR");
		String t1 = porxy.postRequestExpedia(
				"https://www.expedia.com/pub/agent.dll", m1);

		System.out.println(dataParse.getTotalPrice(t1, "FONT", "A5012_12835"));
		writer.println(dataParse.getTotalPrice(t1, "FONT", "A5012_12835"));
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		PrintWriter out = response.getWriter();

		// ServletContext context = this.getServletContext();
		// String roundTripHTML = (String)
		// context.getAttribute("roundTripHTML");
		// out.println(roundTripHTML);
		out.println("name= " + request.getParameter("id"));
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet from select button.</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}
}
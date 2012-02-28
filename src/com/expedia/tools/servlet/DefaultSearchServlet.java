package com.expedia.tools.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.expedia.tools.po.AirPortInfo;
import com.expedia.tools.util.ClientRequestFormatter;
import com.expedia.tools.util.DataParse;
import com.expedia.tools.util.HttpProxyRequest;
import com.expedia.tools.util.Try;

public class DefaultSearchServlet extends HttpServlet {

	private static final long serialVersionUID = 6832282021547319928L;

	private static String flightsSearchRoundTripURL = "http://www.expedia.com/Flights-Search-RoundTrip";

	private static String emainURL = "https://www.expedia.com/pub/agent.dll";

	private static String expediaURL = "www.expedia.com";

	private static String flightContextPath = "/Flights-Search";


	public DefaultSearchServlet() {
		super();
	}

	public void destroy() {
		super.destroy();

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletContext context = this.getServletContext();
		Map<String, String> parameters = getNameForValue(request);
		ClientRequestFormatter formatter = new ClientRequestFormatter(
				parameters);
		String FormattedUrl = formatter.formatRequestUrl();

		try {
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();
			// out.println("CLE(7:34PM) DFW(1:55PM)1 4:39 429.21 PUBLISHED 389.0 711");
			// out.println("DFW(10:25PM) CLE(3:37PM)1 7:48 429.21 PUBLISHED 389.0 711");

			// String[][] value = {{"a","b","c"},{"d","e","f"}};
			// for(int i=0; i < value.length; i++)
			// {
			// for(int j=0; j<value[i].length; j++)
			// {
			// out.println(value[i][j]);
			// }
			// }

			System.out.println("Form param recieved. Request URL has been formatted.");
			System.out.println("Getting product list info from UI...");
			HttpProxyRequest porxy = new HttpProxyRequest();
			String rawProductHTML = porxy.getRequestExpedia1(expediaURL,
					flightContextPath, FormattedUrl);
			DataParse parse = new DataParse();

			String sessionCode = parse.parseHtmlGetSessionCodeById(
					rawProductHTML, "FORM", "flightSearchWizard");
			System.out.println("session code retrieved" + sessionCode);
			System.out.println("Getting Json Data...");

			Map<String, String> postParameters = new HashMap<String, String>();
			postParameters.put("action", "flightResults@search");
			postParameters.put("flightResults_inpCurrentLegNumber", "");
			postParameters.put("flightResults_inpSelectedNumberOfConnections",
					"");
			postParameters.put("flightResults_inpSelectedAirlineIndex", "");
			postParameters.put("flightResults_inpLowestPriceOfAllFromOutbound",
					"-1");
			postParameters.put("flightResults_inpSelectedAirlineCode", "");
			postParameters.put(
					"flightResults_inpShowFlightsWithPricesHigerThan", "-1");
			postParameters.put("flightResults_inpCurrentPageNumber", "");
			postParameters.put("flightResults_inpDisplayAirlineIndex", "");
			postParameters.put("flightResults_inpFlightId", "");
			postParameters.put("flightResults_inpIsTopPicksSearch", "");
			postParameters.put("flightResults_inpViewTypeId", "");
			postParameters.put("flightResults_inpActionId", "5");
			postParameters.put("flightResults_inpFlexStartDay", "");
			postParameters.put("flightResults_inpFlexEndDay", "");
			postParameters.put("flightResults_inpPageNumberForCompleteTrips",
					"-1");
			postParameters.put("flightResults_inpFilteredStops", "");
			postParameters.put("flightResults_inpFilteredAirlines", "");
			postParameters.put("flightResults_inpSortType", "");
			// must add http
			String content = porxy.postRequestExpedia("http://" + expediaURL
					+ sessionCode, postParameters);
			context.setAttribute("roundTripHTML", content);

			String json = parse.parseHtmlGetJsonDateById(content, "script",
					"jsonData");
			System.out.println("Json data retrieved. Pushing to front end...");

			AirPortInfo[][] ds = parse.parseHtmlJsonData(json);

			StringBuilder sb = new StringBuilder();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?> ");
			sb.append("<ProductList>");
			for (int i = 0; i < ds.length; i++) 
			{	
				sb.append("<Product>");
				for (int j = 0; j <ds[i].length; j++)
				{
					AirPortInfo airLeg = ds[i][j];
					sb.append("<Airleg>");					
					sb.append("<DepartureAirport>" + airLeg.getDePartureAirportCode()  + "</DepartureAirport>");
					sb.append("<DepartureTime>" + airLeg.getDePartureTime() + "</DepartureTime>");
					sb.append("<ArrivalAirport>" + airLeg.getArrivalAirportCode() + "</ArrivalAirport>");
					sb.append("<ArrivalTime>" + airLeg.getArrivalTime() + "</ArrivalTime>");
					sb.append("<Stop>" + airLeg.getStop() + "</Stop>");
					sb.append("<Duration>" + airLeg.getTotalTravelingHours() + "</Duration>");
					sb.append("<FareType>" + airLeg.getFlightFareType() + "</FareType>");
					sb.append("<FlightId>" + airLeg.getFlightId() + "</FlightId>");
					sb.append("<TotalPrice>" + airLeg.getTotalPrice() + "</TotalPrice>");
					sb.append("</Airleg>");
				}
				sb.append("</Product>");
			}
			sb.append("</ProductList>");
			out.write(sb.toString());
			System.out.println("Done");

//			for (int i = 0; i < ds.length; i++) {
//
//				for (int j = 0; j < ds[i].length; j++) {
//					out.println("<div class=\"product\">");
//					AirPortInfo a = ds[i][j];
//
//					out.println(a.getArrivalAirportCode() + "("
//							+ a.getArrivalTime() + ")" + " "
//							+ a.getDePartureAirportCode() + "("
//							+ a.getDePartureTime() + ")" + a.getStop() + " "
//							+ a.getTotalTravelingHours() + " "
//							+ a.getTotalPrice() + " " + a.getFlightFareType()
//							+ " " + a.getBasePrice() + "  " + a.getFlightId());
//				}
//				out.println("</div>");
//			}
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// for(String key : parameters.keySet())
		// {
		// out.println(key + ":" + parameters.get(key));
		// }

		// out.println(FormattedUrl);
		// out.println(parameters.keySet().toString());
		// out.println("Departure Airport: " +
		// parameters.get("departureAirport"));
		// out.println("Arrival Airport: " + parameters.get("arrivalAirport"));
		// out.println("Departure Date: " + parameters.get("departureDate"));
		// out.println("Departure Time: " + parameters.get("departureTime"));
		// out.println("Return Date: " + parameters.get("returnDate"));
		// out.println("Return Time: " + parameters.get("returnTime"));
		// out.println("Adult: " + parameters.get("adult"));
		// out.println("Children: " + parameters.get("children"));
		// out.println("Senior: " + parameters.get("senior"));
		// out.println("Trip Type: " + parameters.get("tripType"));

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String> parameters = getNameForValue(request);
		HttpProxyRequest proxy = new HttpProxyRequest();
		String str = proxy.postRequestExpedia(flightsSearchRoundTripURL,
				parameters);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	public void init() throws ServletException {

	}

	private Map<String, String> getNameForValue(HttpServletRequest request) {
		Map<String, String> parameters = new HashMap<String, String>();
		Enumeration<?> enume = request.getParameterNames();
		for (; enume.hasMoreElements();) {
			String name = (String) enume.nextElement();
			String value = request.getParameter(name);
			parameters.put(name, value);
		}
		return parameters;
	}
}

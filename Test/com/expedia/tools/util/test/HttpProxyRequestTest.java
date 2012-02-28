package com.expedia.tools.util.test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import com.expedia.tools.po.AirPortInfo;
import com.expedia.tools.util.DataParse;
import com.expedia.tools.util.HttpProxyRequest;

public class HttpProxyRequestTest extends TestCase {

	String ss = "flightResultsSearchWizard_flightTravelerControl_inpInfants=2";

	String hidden = "flightResultsSearchWizard_inpOmnitureReferralId";

	String hidden1 = "flightResultsSearchWizard_flightSearchParameterControl_inpFlightRouteType";

	private final static String flightsSearchRoundTripURL = "www.expedia.com/Flights-Search-RoundTrip";

	private final static String expediaURL = "www.expedia.com";

	private final static String flightContextPath = "/Flights-Search";

	// private String p =
	// "trip=roundtrip&leg1=from:Salt%20Lake%20City,%20UT,%20United%20States%20(SLC-Salt%20Lake%20City%20Intl.),to:las,departure:01%2F31%2F2012TANYT&leg2=from:las,to:Salt%20Lake%20City,%20UT,%20United%20States%20(SLC-Salt%20Lake%20City%20Intl.),departure:02%2F03%2F2012TANYT&passengers=children:0,adults:1,seniors:0,infantinlap:Y&options=cabinclass:coach,nopenalty:N,sortby:price&mode=search";

	private String p = "trip=roundtrip&leg1=from:Salt%20Lake%20City,%20UT,%20United%20States%20(SLC-Salt%20Lake%20City%20Intl.),to:Las%20Vegas,%20NV,%20United%20States%20(LAS%20-%20All%20Airports),departure:01%2F31%2F2012TANYT&leg2=from:Las%20Vegas,%20NV,%20United%20States%20(LAS%20-%20All%20Airports),to:Salt%20Lake%20City,%20UT,%20United%20States%20(SLC-Salt%20Lake%20City%20Intl.),departure:02%2F03%2F2012TANYT&passengers=children:0,adults:1,seniors:0,infantinlap:Y&options=cabinclass:coach,nopenalty:N,sortby:price&mode=search&";

	public void testGetRequestExpedia() throws Exception {

		HttpProxyRequest porxy = new HttpProxyRequest();

		try {
			
			String stream = porxy.getRequestExpedia1(expediaURL,
					flightContextPath, p);

			DataParse d = new DataParse();

			String actionCode = d.parseHtmlGetSessionCodeById(stream, "FORM",
					"flightSearchWizard");

			Map<String, String> parameters = new HashMap<String, String>();

			HttpClient httpclient = new DefaultHttpClient();

			parameters.put("action", "flightResults@search");
			parameters.put("flightResults_inpCurrentLegNumber", "");
			parameters.put("flightResults_inpSelectedNumberOfConnections", "");
			parameters.put("flightResults_inpSelectedAirlineIndex", "");
			parameters.put("flightResults_inpLowestPriceOfAllFromOutbound",
					"-1");
			parameters.put("flightResults_inpSelectedAirlineCode", "");
			parameters.put("flightResults_inpShowFlightsWithPricesHigerThan",
					"-1");
			parameters.put("flightResults_inpCurrentPageNumber", "");
			parameters.put("flightResults_inpDisplayAirlineIndex", "");
			parameters.put("flightResults_inpFlightId", "");
			parameters.put("flightResults_inpIsTopPicksSearch", "");
			parameters.put("flightResults_inpViewTypeId", "");
			parameters.put("flightResults_inpActionId", "5");
			parameters.put("flightResults_inpFlexStartDay", "");
			parameters.put("flightResults_inpFlexEndDay", "");
			parameters.put("flightResults_inpPageNumberForCompleteTrips", "-1");
			parameters.put("flightResults_inpFilteredStops", "");
			parameters.put("flightResults_inpFilteredAirlines", "");
			parameters.put("flightResults_inpSortType", "");
			// must add http
			String content = porxy.postRequestExpedia("http://" + expediaURL
					+ actionCode, parameters);

			DataParse dataParse = new DataParse();

			String qsrc = dataParse.parseHtmlSelectButtonLink(content, "a",
					"externalLinkOfChooseFlight");

			Map<String, String> parameters1 = new LinkedHashMap<String, String>();
			parameters1.put("action", "flightResults@processLink");
			parameters1
					.put(
							"flightResults_inpLinkParameters#1",
							"linkType=1|"
									+ qsrc
									+ "FltI1=FLTIStr%26iflt1=42%26ftyp=published%26pric=182.6%26bprc=161.0%26txfe=[object Object]%26ccod=USD");

			String actionCode1 = d.parseHtmlGetSessionCodeById(content, "FORM",
					"flightResultForm");

			String str[] = actionCode1.split("\\?");

			String response2 = porxy.postRequestExpedia("http://" + expediaURL
					+ "/Flights-Search-RoundTrip?" + str[1], parameters1);

			String s = new String(response2.getBytes("iso8859-1"), "utf-8");

			// System.out.println(s);

			Map<String, String> m = d.parseHtmlGetInputByName(s, "form",
					"redir");

			String t = porxy.postRequestExpedia(
					"http://www.expedia.com/pub/agent.dll", m);

			// FileUtil.write(s);

			Map<String, String> m1 = d.parseHtmlGetInputByName(t, "FORM",
					"QSREDIR");
			String t1 = porxy.postRequestExpedia(
					"https://www.expedia.com/pub/agent.dll", m1);

			System.out.println(d.getTotalPrice(t1, "FONT", "A5012_12835"));

			String json = dataParse.parseHtmlGetJsonDateById(content, "script",
					"jsonData");

			AirPortInfo[][] ds = dataParse.parseHtmlJsonData(json);

			for (int i = 0; i < ds.length; i++) {
				System.out.println("---------------------");
				for (int j = 0; j < ds[i].length; j++) {
					AirPortInfo a = ds[i][j];

					System.out.println(a.getArrivalAirportCode() + "("
							+ a.getArrivalTime() + ")" + " "
							+ a.getDePartureAirportCode() + "("
							+ a.getDePartureTime() + ")" + a.getStop() + " "
							+ a.getTotalTravelingHours() + " "
							+ a.getTotalPrice() + " " + a.getFlightFareType()
							+ " " + a.getBasePrice() + "  " + a.getFlightId());

				}
				System.out.println("---------------------");
			}

			httpclient.getConnectionManager().shutdown();

			// OutputStream os=new FileOutputStream(new File("expedia.txt"));
			Assert.assertNotNull(stream);
			int factByte = 0;
			byte b[] = new byte[1024];
			// FileOutputStream writer = new
			// FileOutputStream("E:\\workspace\\expediaTools\\src\\com\\expedia\\tools\\util\\expedia.txt");

			// while ((factByte = response.read()) != -1) {
			// //System.out.write(b, 0, factByte);
			// // writer.write(factByte);
			// // writer.flush();
			//
			// }		 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

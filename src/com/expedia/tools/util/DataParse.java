package com.expedia.tools.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.FormTag;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.ScriptTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.expedia.tools.po.AirPortInfo;
import com.expedia.tools.exception.CompareToolsException;

public class DataParse {

	public AirPortInfo[][] parseHtmlJsonData(String jsonData) {
		jsonData = replaceSpace(jsonData);
		JSONArray array = JSONArray.fromObject(jsonData);
		Object[] objArray = array.toArray();
		AirPortInfo[][] content = new AirPortInfo[objArray.length][];
		for (int i = 0; i < objArray.length; i++) {
			Object obj1 = objArray[i];
			JSONObject jsonObject1 = JSONObject.fromObject(obj1);
			Double totalPrice = (Double) jsonObject1
					.get(JsonDataField.TOTALPRICE);
			String flightId = (String) jsonObject1.get(JsonDataField.FLIGHTID);
			String flightFareType = (String) jsonObject1
					.get(JsonDataField.FLIGHTFARETYPE);
			Object objectFare = jsonObject1.get(JsonDataField.FARE);
			JSONObject jsonFare = JSONObject.fromObject(objectFare);
			Object objectFlightFare = jsonFare.get(JsonDataField.FLIGHTFARE);
			JSONObject jsonFlightFare = JSONObject.fromObject(objectFlightFare);
			Double basePrice = (Double) jsonFlightFare
					.get(JsonDataField.BASEPRICE);
			Object objectLegs = jsonObject1.get(JsonDataField.LEGS);
			JSONArray arrayLegs = JSONArray.fromObject(objectLegs);
			content[i] = new AirPortInfo[2];
			for (int j = 0; j < arrayLegs.size(); j++) {
				Object obj2 = arrayLegs.get(j);
				JSONObject jsonObject2 = JSONObject.fromObject(obj2);
				Integer stop = (Integer) jsonObject2.get(JsonDataField.STOPS);
				Object objectDepartureAirport = jsonObject2
						.get(JsonDataField.DEPARTUREAIRPORT);
				Object objectDepartureTime = jsonObject2
						.get(JsonDataField.DEPARTUREDATETIME);
				Object objectArrivalAirport = jsonObject2
						.get(JsonDataField.ARRIVALAIRPORT);
				Object objectArrivalTime = jsonObject2
						.get(JsonDataField.ARRIVALDATETIME);
				Object objectTotalTravelingHours = jsonObject2
						.get(JsonDataField.TOTALTRAVELINGHOURS);
				JSONObject jsonTotalTraveling = JSONObject
						.fromObject(objectTotalTravelingHours);
				Integer hours = (Integer) jsonTotalTraveling
						.get(JsonDataField.HOURS);
				Integer minutes = (Integer) jsonTotalTraveling
						.get(JsonDataField.MINUTES);
				JSONObject jsonDeparture = JSONObject
						.fromObject(objectDepartureAirport);
				JSONObject jsonDepartureTime = JSONObject
						.fromObject(objectDepartureTime);
				JSONObject jsonArrival = JSONObject
						.fromObject(objectArrivalAirport);
				JSONObject jsonArrivalTime = JSONObject
						.fromObject(objectArrivalTime);
				String dePartureAirportCode = (String) jsonDeparture
						.get(JsonDataField.AIRPORTCODE);
				String dePartureTime = (String) jsonDepartureTime
						.get(JsonDataField.DATETIMESTRING);
				String arrivalAirportCode = (String) jsonArrival
						.get(JsonDataField.AIRPORTCODE);
				String arrivalTime = (String) jsonArrivalTime
						.get(JsonDataField.DATETIMESTRING);
				AirPortInfo ari = new AirPortInfo();
				ari.setFlightId(flightId);
				ari.setTotalPrice(totalPrice);
				ari.setArrivalAirportCode(arrivalAirportCode);
				ari.setArrivalTime(formatDateTime(arrivalTime));
				ari.setDePartureAirportCode(dePartureAirportCode);
				ari.setDePartureTime(formatDateTime(dePartureTime));
				ari.setStop(String.valueOf(stop));
				ari.setTotalTravelingHours(hours + ":" + minutes);
				ari.setFlightFareType(flightFareType);
				ari.setBasePrice(basePrice);
				content[i][j] = ari;
			}

		}
		return content;

	}

	public Map<String, String> parseHtmlGetInputByName(String content,
			String filterPattern, String uniqueValue) {
		Map<String, String> parametersMap = new HashMap<String, String>();
		verifyParameter(filterPattern, uniqueValue);
		try {
			NodeList nodeList = this.getNodeList(content, filterPattern);
			for (int i = 0; i < nodeList.size(); i++) {
				FormTag form = (FormTag) nodeList.elementAt(i);
				if (uniqueValue.equals(form.getAttribute(HtmlAttribute.NAME))) {
					NodeList inputList = form.getChildren();
					for (int j = 0; j < inputList.size(); j++) {
						Node node = inputList.elementAt(j);
						if (node instanceof InputTag) {
							InputTag input = (InputTag) node;
							String pname = input
									.getAttribute(HtmlAttribute.NAME);
							String pvalue = input
									.getAttribute(HtmlAttribute.VALUE);
							if (!isNull(pvalue))
								parametersMap.put(pname, pvalue);
						}

					}
				}

			}
		} catch (Exception e) {
			throw new CompareToolsException("parse html form node occur error "
					+ e.getMessage(), e);
		}
		return parametersMap;
	}

	public String parseHtmlGetSessionCodeById(String inputStream,
			String filterPattern, String uniqueValue) {
		verifyParameter(filterPattern, uniqueValue);
		String actionCode = null;
		try {
			NodeList nodeList = this.getNodeList(inputStream, filterPattern);
			for (int i = 0; i < nodeList.size(); i++) {
				FormTag form = (FormTag) nodeList.elementAt(i);
				String id = form.getAttribute(HtmlAttribute.ID);
				if (uniqueValue.equals(id)) {
					actionCode = form.getAttribute(HtmlAttribute.ACTION);
					break;
				}
			}
		} catch (ParserException e) {
			throw new CompareToolsException("parse html form node occur error "
					+ e.getMessage(), e);
		}
		return actionCode;

	}

	public String parseHtmlGetJsonDateById(String content,
			String filterPattern, String uniqueValue) {
		verifyParameter(filterPattern, uniqueValue);
		String jsonStr = null;
		try {
			NodeList nodeList = this.getNodeList(content, filterPattern);
			for (int i = 0; i < nodeList.size(); i++) {
				ScriptTag script = (ScriptTag) nodeList.elementAt(i);
				String id = script.getAttribute(HtmlAttribute.ID);
				if (uniqueValue.equals(id)) {
					jsonStr = script.getScriptCode();
					break;
				}
			}
		} catch (ParserException e) {
			throw new CompareToolsException(
					"parse html script node occur error " + e.getMessage(), e);
		}
		return jsonStr;
	}

	public String parseHtmlSelectButtonLink(String content,
			String filterPattern, String uniqueValue) {
		verifyParameter(filterPattern, uniqueValue);
		String externalLink = null;
		try {
			NodeList nodeList = this.getNodeList(content, filterPattern);
			for (int i = 0; i < nodeList.size(); i++) {
				LinkTag div = (LinkTag) nodeList.elementAt(i);
				String externalLinkOfChooseFlight = div
						.getAttribute(uniqueValue);
				if (!isNull(externalLinkOfChooseFlight)) {
					externalLink = externalLinkOfChooseFlight;
					break;
				}
			}
		} catch (ParserException e) {
			throw new CompareToolsException("parse html " + filterPattern
					+ " node occur error " + e.getMessage(), e);
		}
		return externalLink;

	}

	public String getTotalPrice(String content, String filterPattern,
			String uniqueValue) throws CompareToolsException {
		verifyParameter(filterPattern, uniqueValue);
		String text = null;
		try {
			NodeList nodeList = this.getNodeList(content, filterPattern);
			for (int i = 0; i < nodeList.size(); i++) {
				FontTag f = (FontTag) nodeList.elementAt(i);
				String id = f.getAttribute(HtmlAttribute.ID);
				if (uniqueValue.equals(id)) {
					text = f.getChildrenHTML();
					break;
				}
			}
		} catch (ParserException e) {
			throw new CompareToolsException("parse html " + filterPattern
					+ " node occur error " + e.getMessage(), e);
		}
		return text;
	}

	private NodeList getNodeList(String content, String filterPattern)
			throws ParserException {
		Parser parser = new ExpHtmlParser(content);
		NodeFilter filter = new TagNameFilter(filterPattern);
		return parser.extractAllNodesThatMatch(filter);
	}

	private void verifyParameter(String filterPattern, String uniqueIdentify) {
		if (isNull(filterPattern) || isNull(uniqueIdentify)) {
			throw new CompareToolsException(
					"parse html necessary parameters can't for empty");
		}
	}

	private boolean isNull(String str) {
		if (str == null) {
			return true;
		}
		str = replaceSpace(str);
		if ("".equals(str)) {
			return true;
		} else {
			return false;
		}
	}

	private String replaceSpace(String str) {
		return str.replaceAll("^ *| *$", "");
	}

	private String formatDateTime(String timeString) {
		if (isNull(timeString)) {
			throw new CompareToolsException(
					"necessary parameters can't for empty");
		}
		timeString = timeString.replaceAll("T", " ");
		timeString = timeString.substring(0, 19);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		Date date;
		String dateTime = "";
		try {
			date = sdf.parse(timeString);
			calendar.setTime(date);
			int hour = calendar.get(Calendar.HOUR);
			int minute = calendar.get(Calendar.MINUTE);
			dateTime = hour + ":" + minute;
			if (Calendar.AM == calendar.get(Calendar.AM_PM)) {
				dateTime += "AM";
			} else if (Calendar.PM == calendar.get(Calendar.AM_PM)) {
				dateTime += "PM";
			}
		} catch (ParseException e) {
			throw new CompareToolsException("parse date occur error"
					+ e.getMessage(), e);
		}
		return dateTime;

	}

}

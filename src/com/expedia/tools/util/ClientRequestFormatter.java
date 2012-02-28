package com.expedia.tools.util;

import java.util.Locale;
import java.util.Map;

public class ClientRequestFormatter {
	
	private Map<String,String> m_parameters;
	private static final String DEPARTUREAIRPORTCODE = "departureAirport"; 
	private static final String RETURNAIRPORTCODE = "arrivalAirport";
	private static final String DEPARTUREDATE = "departureDate";
	private static final String DEPARTURETIME = "departureTime";
	private static final String RETURNDATE = "returnDate";
	private static final String RETURNTIME = "returnTime";
	private static final String ADULT = "adult";
	private static final String CHILDREN = "children";
	private static final String SENIOR = "senior";
	private static final String TRIPTYPE = "tripType";
	
	public ClientRequestFormatter (Map<String,String> parameters)
	{
		m_parameters = parameters;
	}
	
	public String formatRequestUrl()
	{
		String depAirportCodeFormatted = null;
		String arrAirportCodeFormatted = null;
		String depDateFormatted = null;
		String depTimeFormatted = null;
		String retDateFormatted = null;
		String retTimeFormatted = null;
		String tripTypeFormatted = null;	
		String adult = null;
		String children = null;
		String senior = null;
		StringBuilder sb = new StringBuilder();
		
		for(String key : m_parameters.keySet())
		{
			if(key.equals(DEPARTUREAIRPORTCODE))
			{
				depAirportCodeFormatted = formatAirportCode(m_parameters.get(key));
			}
			else if(key.equals(RETURNAIRPORTCODE))
			{
				arrAirportCodeFormatted = formatAirportCode(m_parameters.get(key));
			}
			else if(key.equals(DEPARTUREDATE))
			{
				depDateFormatted = formatDate(m_parameters.get(key));
			}
			else if(key.equals(RETURNDATE))
			{
				retDateFormatted = formatDate(m_parameters.get(key));
			}
			else if(key.equals(DEPARTURETIME))
			{
				depTimeFormatted = formatTime(m_parameters.get(key));
			}
			else if(key.equals(RETURNTIME))
			{
				retTimeFormatted = formatTime(m_parameters.get(key));
			}
			else if(key.equals(TRIPTYPE))
			{
				tripTypeFormatted = formatTripType(m_parameters.get(key));
			}
			else if(key.equals(ADULT))
			{
				adult = m_parameters.get(key);
			}
			else if(key.equals(CHILDREN))
			{
				children = m_parameters.get(key);
			}
			else if(key.equals(SENIOR))
			{
				senior = m_parameters.get(key);
			}
		}
		String tripParam = tripTypeFormatted;
		String leg1Param = "from:" + depAirportCodeFormatted + ",to:" + arrAirportCodeFormatted +
							",departure:" + depDateFormatted + depTimeFormatted;
		String leg2Param = "from:" + arrAirportCodeFormatted + ",to:" + depAirportCodeFormatted +
							",departure:" + retDateFormatted + retTimeFormatted;	
		String psgParam = "children:" + children + ",adults:" + adult + ",seniors:" + senior +
							",infantinlap:Y";
		String opParam = "cabinclass:coach,nopenalty:N,sortby:price";
		String modeParam = "search";
		
		sb.append(addUrlParam("trip", tripParam));
		sb.append(addUrlParam("leg1", leg1Param));
		sb.append(addUrlParam("leg2", leg2Param));
		sb.append(addUrlParam("passengers", psgParam));
		sb.append(addUrlParam("options", opParam));
		sb.append(addUrlParam("mode", modeParam));
		
		return sb.toString();
	}
	
	private String formatAirportCode(String airportCode)
	{
		String airportCodeFormatted = null;
		airportCodeFormatted = airportCode.replaceAll(" ", "%20");
		return airportCodeFormatted;
	}
	
	private String formatDate(String date)
	{
		String dateFormatted = null;
		String year = null;
		String month = null;
		String day = null;
		year = date.substring(0, 4);
		month = date.substring(5, 7) + "%2F";
		day = date.substring(8) + "%2F";
		dateFormatted = month + day + year;
		return dateFormatted;
	}
	
	private String formatTime(String time)
	{
		String timeFormatted = null;
		timeFormatted = "T" + time.toUpperCase(Locale.US) + "T";
		return timeFormatted;
	}
	
	private String formatTripType(String tripType)
	{
		String tripTypeFormatted = null;
		tripTypeFormatted = tripType.toLowerCase(Locale.US);
		return tripTypeFormatted;
	}
	
	private String addUrlParam(String name, String value)
	{
		return name + "=" + value + "&";
	}

}

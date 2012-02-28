package com.expedia.tools.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Extract {

	public void xbfx() {
		String rawUrl = "http://www.expedia.com/pub/agent.dll";
		String tinyUrl = rawUrl;

		boolean oldFormat = false;

		int index = rawUrl.indexOf("?");
		if (index >= 0) {
			index = rawUrl.indexOf("?");
			if (index >= 0) {
				oldFormat = true;
			}
			tinyUrl = getUrl(rawUrl, oldFormat);

			String[][] formParams = getQueryParams(rawUrl, oldFormat);
		}

	}

	public String getUrl(String rawUrl, boolean oldFormat) {
		Pattern p = null;
		if (!oldFormat) {
			p = Pattern.compile("(https?://[^/]*(/[^=]*/)*)(.*)");

		} else {
			p = Pattern.compile("https?://[^\\?]*");
		}
        
		Matcher urlTmp = p.matcher(rawUrl);
		if (oldFormat) {
			return urlTmp.group(1);
		}
		return urlTmp.group(0);
	}

	public String[][] getQueryParams(String rawUrl, boolean oldFormat) {
		Pattern splitPattern = null;
		if (!oldFormat) {
			splitPattern = Pattern.compile("(/)([^/]+)");
		} else {
			splitPattern = Pattern.compile("(\\?|&)([^(&|\\?)]+)");
		}

		String[] splitTmp = splitPattern.split(rawUrl);
		String[][] retval = new String[splitTmp.length][2];
		Pattern paramPattern = null;
		if (!oldFormat) {
			paramPattern = Pattern.compile("[^(/|&|=)]+=[^(/|&)]*");
		} else {
			paramPattern = Pattern.compile("[^(&|=)]+=[^&]*");
		}
		int j = 0;
		for (int i = 0; i < splitTmp.length; i++) {
			Matcher paramMatch = paramPattern.matcher(splitTmp[i]);
			boolean paramTmp = paramMatch.matches();
			if (paramTmp) {
				String[] splitParam = paramMatch.group(0).split("=");
				retval[j] = new String[] { splitParam[0], splitParam[1] };
				j++;
			}
		}
		return retval;

	}

	public void updateFormElem(String tinyUrl, String formParams) {

	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		Extract e=new Extract();
		e.xbfx();
	}
	
	
}

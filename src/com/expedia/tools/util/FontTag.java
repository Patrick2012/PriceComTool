package com.expedia.tools.util;

import org.htmlparser.tags.CompositeTag;

public class FontTag extends CompositeTag {
	private static final long serialVersionUID = -5082056986441473483L;
	private static final String mIds[] = { "FONT" };
	private static final String mEndTagEnders[] = { "FONT" };

	public FontTag() {
	}

	public String[] getIds() {
		return mIds;
	}

	public String[] getEndTagEnders() {
		return mEndTagEnders;
	}

}

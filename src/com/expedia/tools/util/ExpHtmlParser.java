package com.expedia.tools.util;

import org.htmlparser.Parser;
import org.htmlparser.PrototypicalNodeFactory;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.ParserFeedback;

public class ExpHtmlParser extends Parser {
	private static final long serialVersionUID = -8325227321248545658L;
	private static PrototypicalNodeFactory factory = null;
	static {
		factory = new PrototypicalNodeFactory();
		factory.registerTag(new FontTag());

	}

	public ExpHtmlParser(String content) throws ParserException {
		super(content);
		setNodeFactory(factory);
	}

	public ExpHtmlParser(Lexer lexer, ParserFeedback fb) {
		super(lexer, fb);
		setNodeFactory(factory);
	}

}

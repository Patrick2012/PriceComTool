package com.expedia.tools.exception;

import java.io.PrintStream;

public class CompareToolsException extends RuntimeException {

	private static final long serialVersionUID = -1258511027711495706L;
	private Throwable cause;

	public CompareToolsException(String message) {
		super(message);

	}

	public CompareToolsException(String message, Throwable e) {
		super(message);
		this.cause = e;
	}

//	@Override
//	public Throwable getCause() {
//		return cause == null ? this : this.cause;
//	}
//
////	public String getMessage() {
////		String message = super.getMessage();
////		Throwable cause = getCause();
////		if (cause != null) {
////			message = message + cause;
////		}
////		return message;
////	}
//
//	public void printStackTrace(PrintStream ps) {
//		if (getCause() == null) {
//			super.printStackTrace(ps);
//
//		} else {
//			ps.println(this);
//			getCause().printStackTrace(ps);
//		}
//	}
//
//	public void printStackTrace() {
//		printStackTrace(System.err);
//	}

}

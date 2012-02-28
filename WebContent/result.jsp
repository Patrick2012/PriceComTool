<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Result</title>
</head>
<body>
	<%
		String[][] result = (String[][])session.getAttribute("list");
		for(int i = 0; i < result.length; i++)
		{
			for(int j = 0; j < result[i].length; j++)
				{
					out.println(result[i][j]);
				}
		}
		
	%>
</body>
</html>
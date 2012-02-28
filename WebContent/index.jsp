<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>EWSAir Client Support Tool</title>
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<style type="text/css">

#content {
	font:Arial, Helvetica, sans-serif;
	font-size:10pt;
	min-width: 450px;
	max-width: 960px;
	margin: 0 auto;
}

#top {
	height: 900px;
	width: 100%;
	float: left;
}

#left {
	width: 350px;
	float: left;
	margin-top: 10px;
	margin-left: 10px;
}

#search_box_head {
clear:both;
height:38px;
line-height:33px;
background: url(CSS/pic/search_box_header.png)  no-repeat;
}

#search_box_head b {
color:#FFFFFF;
padding-left:10px;
}

#search_box_main {
background:url(CSS/pic/search_box_border.png)  repeat-y;

}

#search_image {
display:none;
}

#search_box_bottom {
height:5px;
background:url(CSS/pic/search_box_buttom.png) no-repeat;
}

#airSearchRS {
	margin-top: 5px;
}

#airSearchRS_header {
background:url(CSS/pic/searchRS_header.png) no-repeat;
height:31px;
line-height:31px;
}

#airSearchRS_header b {
color:#FFFFFF;
padding-left:10px;
}

#airSearchRS_main {
background:url(CSS/pic/searchRS_border.png) repeat-y;
height:575px;
}

#airSearchRS_bottom {
background:url(CSS/pic/searchRS_bottom.png) no-repeat;
height:5px;
}


#flightProducts {
	width: 550px;
	margin-top: 10px;
	margin-left: 370px;
}

#flightProducts_header {
background:url(CSS/pic/flightProducts_header.png) no-repeat;
height:31px;
line-height:31px;
}

#flightProducts_header b {
color:#FFFFFF;
padding-left:10px;
}

#flightProducts_main {
background:url(CSS/pic/flightProducts_border.png) repeat-y;
height: 850px;
}

#flightProducts_bottom {
height:5px;
background:url(CSS/pic/flightProducts_bottom.png) no-repeat;
}

#bottom {
	clear:both;
	margin-bottom:30px;
}

#uiPricing {
	width: 430px;	
	float:left;
	margin-left:10px;
	margin-right:40px;
}

#bottom_uipricing_header {
background:url(CSS/pic/UI_result_header.png) no-repeat;
height:31px;
}

#bottom_uipricing_main {
background:url(CSS/pic/UI_result_border.png) repeat-y;
height: 300px;
}

#bottom_uipricing_bottom {
background:url(CSS/pic/UI_result_bottom.png) no-repeat;
height:5px;
}

#apiPricing {
	width: 430px;
	float:left;
}

#bottom_apipricing_header {
background:url(CSS/pic/API_result_header.png) no-repeat;
height:31px;
}

#bottom_apipricing_main {
background:url(CSS/pic/API_result_border.png) repeat-y;
height: 300px;
}

#bottom_apipricing_bottom {
background:url(CSS/pic/API_result_buttom.png) no-repeat;
height:5px;
}

.clear_float {
clear:both;
}

#airport_arrival{
margin-left:170px;
}


#date_return {
margin-left: 170px;
}

#airport_departure{
float:left;
}

#date_departure{
float:left;
}

#adult_num, #child_num {
float:left;
}

.airport_box {
width:150px;
}

.date_box{
width:70px;
}

.row_search {
padding-top:7px;
margin-left: 10px;
}

#search_button {
margin:0;
padding-top:15px;
padding-bottom:5px;
text-align:center;
}

#product_list_control {
margin-left:10px;
}

#flightProducts_main {
overflow-y:scroll;
}

#product_list_display {
margin-left:10px;
margin-top:10px;
}

#compare_button{
margin-left:135px;
}


.product_detail {
float:left;
margin-bottom:10px;
width:465px;
border: solid 2px #000099;
background-color:#FFFFFF
}

.product_header {
color:#666666;
font-size:9pt;
}

.product {
clear:both;
}

.product_cell {
padding:3px;
}

.flight_id_cell {
width:60px;
}

.select_to_compare {
margin-top: 30px;
margin-left: 20px;
cursor: pointer;
}
</style>

<script type="text/javascript" language="javascript">

    var xhr;
    var productNumber;
	function getAirportCode(formId)
	{
		var airportCodeSelectIndex, airportCode;
		if(formId == "airport_departure_box")
			{
			airportCodeSelectIndex = document.getElementById(formId).selectedIndex;
			airportCode = document.getElementById(formId).options[airportCodeSelectIndex].text;
			}
		else if(formId == "airport_return_box")
			{
			airportCodeSelectIndex = document.getElementById(formId).selectedIndex;
			airportCode = document.getElementById(formId).options[airportCodeSelectIndex].text;
			}
		return airportCode;
	}
	
	function getDate(formId)
	{
		var dateString;
		if(formId == "date_departure_box")
			{
				dateString = document.getElementById(formId).value;
			}
		else if(formId == "date_return_box")
			{
				dateString = document.getElementById(formId).value;
			}
		return dateString;
	}
	
	function getTime(formId)
	{
		var timeString;
		if(formId == "date_departure_time")
			{
				timeString = document.getElementById(formId).value;
			}
		else if(formId == "date_return_time")
			{
				timeString = document.getElementById(formId).value;
			}
		return timeString;
	}
	
	function getPassengerCount(formId)
	{
		var adultCount, childrenCount, seniorCount;
		if(formId == "adult_input")
			{
				adultCount = document.getElementById(formId).value;
				return adultCount;
			}
		else if(formId == "child_input")
			{
				childrenCount = document.getElementById(formId).value;
				return childrenCount;
			}
		else if(formId == "senior_input")
			{
				seniorCount = document.getElementById(formId).value;
				return seniorCount;
			}
		
	}
	
	function getTripType(formName)
	{
		var tripType;
		if (document.getElementsByName(formName)[0].checked)
			{
				tripType = document.getElementsByName(formName)[0].value;
			}
		else
			{
				tripType = document.getElementsByName(formName)[1].value;
			}
		
		return tripType;
	}
	
	function addUrlParam(url, name, value)
	{
		url += (url.indexOf("?") == -1 ? "?" : "&");
		url += encodeURIComponent(name) + "=" + encodeURIComponent(value);
		return url;
	}
	
	function submitForm()
	{
		document.getElementById("button_serach").disabled = true;
		document.getElementById("button_serach").value = "searching...";
		document.getElementById("search_image").style.display = "inline";
		var departureAirportCode, arrivalAirportCode;
		var departureDate, returnDate;
		var departureTime, returnTime;
		var adultCount, childrenCount, seniorCount;
		var tripType;
		var url = "test";
		
		departureAirportCode = getAirportCode("airport_departure_box");
		arrivalAirportCode = getAirportCode("airport_return_box");
		departureDate = getDate("date_departure_box");
		returnDate = getDate("date_return_box");
		departureTime = getTime("date_departure_time");
		returnTime = getTime("date_return_time");
		adultCount = getPassengerCount("adult_input");
		childrenCount = getPassengerCount("child_input");
		seniorCount = getPassengerCount("senior_input");
		tripType = getTripType("tripType");
		
		url = addUrlParam(url, "departureAirport", departureAirportCode);
		url = addUrlParam(url, "arrivalAirport", arrivalAirportCode);
		url = addUrlParam(url, "departureDate", departureDate);
		url = addUrlParam(url, "departureTime", departureTime);
		url = addUrlParam(url, "returnDate", returnDate);
		url = addUrlParam(url, "returnTime", returnTime);
		url = addUrlParam(url, "adult", adultCount);
		url = addUrlParam(url, "children", childrenCount);
		url = addUrlParam(url, "senior", seniorCount);
		url = addUrlParam(url, "tripType", tripType);
		
		 if(window.XMLHttpRequest) {  
             xhr = new XMLHttpRequest();  
         }else if(window.ActiveXObject) {  
             xhr = new ActiveXObject("Microsoft.XMLHTTP");  
         }  	
		xhr.onreadystatechange = function(){
			if(xhr.readyState == 4){
				if(xhr.status == 200){
					parseResults();
					document.getElementById("button_serach").disabled = false;
					document.getElementById("button_serach").value = "search";
					document.getElementById("search_image").style.display = "none";
				}
			}				
		};
		xhr.open("get", url, true);
		//xhr.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
		xhr.send(null);
	}
	
	function parseResults()
	{
		var departureAirport = "";
		var arrivalAirport = "";
		var departureTime = "";
		var arrivalTime = "";
		var duration = "";
		var stop = "";
		var fareType = "";
		var flightId = "";
		var totalPrice = "";
		var productDiv = "";
		var productListDiv = "";
		
		//document.getElementById("product_1").style.backgroundColor = "#FFFFFF";
		//document.getElementById("product_1").style.border = "thin solid black";
		//document.getElementById("product_1").style.width = "450px";
		//document.getElementById("product_list_display").childNodes[1].innerHTML = xhr.responseXML;
		//document.getElementsByName("select_button")[0].style.display = "inline";
		var xmlData = xhr.responseXML;
		var productList = xmlData.getElementsByTagName("Product");
		for(var i = 0; i < productList.length; i++)
		{
			productDiv += "<div id='product" + i + "' class='product'>";
			productDiv += "<table border='0' class='product_detail'>";
			productDiv += "<tbody>";
			productDiv += "<tr class='product_header'>";
			productDiv += "<td class='product_cell'>From</td>";
			productDiv += "<td class='product_cell'>DepTime</td>";
			productDiv += "<td class='product_cell'>To</td>";
			productDiv += "<td class='product_cell'>ArrTime</td>";
			productDiv += "<td class='product_cell'>Duration</td>";
			productDiv += "<td class='product_cell'>Stops</td>";
			productDiv += "<td class='product_cell'>Fare Type</td>";
			productDiv += "<td class='product_cell flight_id_cell'>Flight ID</td>";
			productDiv += "<td class='product_cell'>Price</td>";
			productDiv += "</tr>";
			
			var product = productList[i];
			var airLegs = product.getElementsByTagName("Airleg");
			for (var j = 0; j < airLegs.length; j++)
			{
				var airLeg = airLegs[j];
				departureAirport = airLeg.getElementsByTagName("DepartureAirport")[0].firstChild.nodeValue;
				arrivalAirport = airLeg.getElementsByTagName("ArrivalAirport")[0].firstChild.nodeValue;
				departureTime = airLeg.getElementsByTagName("DepartureTime")[0].firstChild.nodeValue;
				arrivalTime = airLeg.getElementsByTagName("ArrivalTime")[0].firstChild.nodeValue;
				duration = airLeg.getElementsByTagName("Duration")[0].firstChild.nodeValue;
				stop = airLeg.getElementsByTagName("Stop")[0].firstChild.nodeValue;
				fareType = airLeg.getElementsByTagName("FareType")[0].firstChild.nodeValue;
				flightId = airLeg.getElementsByTagName("FlightId")[0].firstChild.nodeValue;
				totalPrice = airLeg.getElementsByTagName("TotalPrice")[0].firstChild.nodeValue;	
				
				productDiv += "<tr>";
				productDiv += "<td class='product_cell'>" + departureAirport + "</td>";
				productDiv += "<td class='product_cell'>" + departureTime + "</td>";
				productDiv += "<td class='product_cell'>" + arrivalAirport + "</td>";
				productDiv += "<td class='product_cell'>" + arrivalTime + "</td>";
				productDiv += "<td class='product_cell'>" + duration + "</td>";
				productDiv += "<td class='product_cell'>" + stop + "</td>";
				productDiv += "<td class='product_cell'>" + fareType + "</td>";
				productDiv += "<td class='product_cell flight_id_cell'>" + flightId + "</td>";
				productDiv += "<td class='product_cell'>" + totalPrice + "</td>";
				productDiv += "</tr>";
			}					
			productDiv += "</tbody>";
			productDiv += "</table>";
			productDiv += "<input type='checkbox' name='select_button' class='select_to_compare' />";
			productDiv += "</div>";		
		}		
		document.getElementById("product_list_display").innerHTML = productDiv;			
	}
	
	function updateResults()
	{
		var userInput = document.getElementById("product_list_filter_value").value;
		var productCount = xhr.responseXML.getElementsByTagName("Product").length;
		
		for(var i = 0; i < productCount; i++)
		{
			document.getElementById("product" + i).style.display = "block";
		}
		
		for(var i = userInput; i < productCount; i++)
		{
			document.getElementById("product" + i).style.display = "none";		
		}	
	}
	
	function comparePrice()
	{
		var url = "select";
		var param = "name=Patrick&id=Wu";
		xhr = new XMLHttpRequest();		
		xhr.onreadystatechange = function(){
			if(xhr.readyState == 4){
				if(xhr.status == 200){
					document.getElementById("product_list_display").innerText = xhr.responseText;
				}				
			}
		};
		xhr.open("post", url, true);
		xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		xhr.send(param);
	}
	
</script>
</head>

<body>
	<div id="content">
		<div id="top">
			<div id="left">
				<div id="search">
					<form method="get" action="">
					  <div id="search_box_head">
							<b>Flight Searching</b>
						</div>
						<div id="search_box_main">
                        	<div id="upload_area"></div>
							<div id="search_first_row" class="clear_float row_search">
								<div id="airport_departure">
                                	<label>Departure Airport <br /></label> 
                                    <select name="departureAirport" id="airport_departure_box" class="airport_box" 
                                        required="true" requiredmessage="requiredFieldMessage">
                                         	<option value=''>-Select from list-</option>
                                            <option value=ATL >Atlanta, GA (ATL-Hartsfield Intl.)</option>					
                                            <option value=AUS >Austin, TX (AUS-Austin-Bergstrom International Airport)</option>										
                                            <option value=BWI >Baltimore, MD (BWI-Baltimore Washington)</option>										
                                            <option value=BOS >Boston, MA (BOS-Logan Intl.)</option>										
                                            <option value=CLT >Charlotte, NC (CLT-Douglas Intl.)</option>										
                                            <option value=ORD >Chicago, IL (ORD-O'Hare)</option>										
                                            <option value=CLE >Cleveland, OH (CLE-Hopkins Intl.)</option>										
                                            <option value=CMH >Columbus, OH (CMH-Port Columbus Intl.)</option>										
                                            <option value=DFW >Dallas, TX (DFW-Dallas-Fort Worth Intl.)</option>										
                                            <option value=DEN >Denver, CO (DEN-Denver Intl.)</option>										
                                            <option value=DTW >Detroit, MI (DTW-Wayne County)</option>										
                                            <option value=FLL >Fort Lauderdale, FL (FLL)</option>										
                                            <option value=RSW >Fort Myers, FL (RSW-Southwest Florida Regional)</option>										
                                            <option value=BDL >Hartford, CT (BDL-Bradley Intl.)</option>										
                                            <option value=HNL >Honolulu, Oahu, HI (HNL-Honolulu Intl.)</option>										
                                            <option value=IAH >Houston, TX (IAH-Bush Intercontinental)</option>										
                                            <option value=IND >Indianapolis, IN (IND-Indianapolis Intl.)</option>										
                                            <option value=JAX >Jacksonville, FL (JAX-Jacksonville Intl.)</option>										
                                            <option value=MCI >Kansas City, MO (MCI-Kansas City Intl.)</option>										
                                            <option value=LAS >Las Vegas, NV (LAS-McCarran Intl.)</option>										
                                            <option value=LAX >Los Angeles, CA (LAX-Los Angeles Intl.)</option>										
                                            <option value=MIA >Miami, FL (MIA-Miami Intl.)</option>										
                                            <option value=MSP >Minneapolis, MN (MSP-Minneapolis-St. Paul Intl.)</option>										
                                            <option value=BNA >Nashville, TN (BNA)</option>										
                                            <option value=MSY >New Orleans, LA (MSY-New Orleans Intl.)</option>										
                                            <option value=JFK >New York, NY (JFK-Kennedy)</option>										
                                            <option value=LGA >New York, NY (LGA-LaGuardia)</option>										
                                            <option value=EWR >Newark, NJ (EWR-Newark International Airport)</option>										
                                            <option value=OAK >Oakland, CA (OAK-Oakland Intl.)</option>										
                                            <option value=SNA >Orange County, CA (SNA-John Wayne Intl.)</option>										
                                            <option value=MCO >Orlando, FL (MCO-Orlando Intl.)</option>										
                                            <option value=PHL >Philadelphia, PA (PHL-Philadelphia Intl.)</option>										
                                            <option value=PHX >Phoenix, AZ (PHX-Sky Harbor Intl.)</option>										
                                            <option value=PIT >Pittsburgh, PA (PIT-Greater Pittsburgh Intl.)</option>										
                                            <option value=PDX >Portland, OR (PDX-Portland Intl.)</option>										
                                            <option value=RDU >Raleigh, NC (RDU-Raleigh Durham Intl.)</option>										
                                            <option value=RNO >Reno, NV (RNO-Reno-Tahoe International)</option>										
                                            <option value=SLC >Salt Lake City, UT (SLC-Salt Lake City Intl.)</option>										
                                            <option value=SAT >San Antonio, TX (SAT-San Antonio Intl.)</option>										
                                            <option value=SAN >San Diego, CA (SAN-Lindbergh Field)</option>										
                                            <option value=SFO >San Francisco, CA (SFO-San Francisco Intl.)</option>										
                                            <option value=SJC >San Jose, CA (SJC-San Jose Intl.)</option>										
                                            <option value=SEA >Seattle, WA (SEA-Seattle Tacoma)</option>										
                                            <option value=STL >St. Louis, MO (STL-Lambert-St. Louis Intl.)</option>										
                                            <option value=TPA >Tampa, FL (TPA-Tampa Intl.)</option>										
                                            <option value=DCA >Washington, DC (DCA-Ronald Reagan National)</option>										
                                            <option value=IAD >Washington, DC (IAD-Dulles)</option>										
                                            <option value=PBI >West Palm Beach, FL (PBI-Palm Beach Intl.)</option>	                                        			
                                     </select>
								</div>
								<div id="airport_arrival">
                                	<label>Arrival Airport <br /></label> 
                                    <select name="arrivalAirport" id="airport_return_box" class="airport_box" 
                                        required="true" requiredmessage="requiredFieldMessage">
                                       		<option value=''>-Select from list-</option>
                                            <option value=ATL >Atlanta, GA (ATL-Hartsfield Intl.)</option>					
                                            <option value=AUS >Austin, TX (AUS-Austin-Bergstrom International Airport)</option>										
                                            <option value=BWI >Baltimore, MD (BWI-Baltimore Washington)</option>										
                                            <option value=BOS >Boston, MA (BOS-Logan Intl.)</option>										
                                            <option value=CLT >Charlotte, NC (CLT-Douglas Intl.)</option>										
                                            <option value=ORD >Chicago, IL (ORD-O'Hare)</option>										
                                            <option value=CLE >Cleveland, OH (CLE-Hopkins Intl.)</option>										
                                            <option value=CMH >Columbus, OH (CMH-Port Columbus Intl.)</option>										
                                            <option value=DFW >Dallas, TX (DFW-Dallas-Fort Worth Intl.)</option>										
                                            <option value=DEN >Denver, CO (DEN-Denver Intl.)</option>										
                                            <option value=DTW >Detroit, MI (DTW-Wayne County)</option>										
                                            <option value=FLL >Fort Lauderdale, FL (FLL)</option>										
                                            <option value=RSW >Fort Myers, FL (RSW-Southwest Florida Regional)</option>										
                                            <option value=BDL >Hartford, CT (BDL-Bradley Intl.)</option>										
                                            <option value=HNL >Honolulu, Oahu, HI (HNL-Honolulu Intl.)</option>										
                                            <option value=IAH >Houston, TX (IAH-Bush Intercontinental)</option>										
                                            <option value=IND >Indianapolis, IN (IND-Indianapolis Intl.)</option>										
                                            <option value=JAX >Jacksonville, FL (JAX-Jacksonville Intl.)</option>										
                                            <option value=MCI >Kansas City, MO (MCI-Kansas City Intl.)</option>										
                                            <option value=LAS >Las Vegas, NV (LAS-McCarran Intl.)</option>										
                                            <option value=LAX >Los Angeles, CA (LAX-Los Angeles Intl.)</option>										
                                            <option value=MIA >Miami, FL (MIA-Miami Intl.)</option>										
                                            <option value=MSP >Minneapolis, MN (MSP-Minneapolis-St. Paul Intl.)</option>										
                                            <option value=BNA >Nashville, TN (BNA)</option>										
                                            <option value=MSY >New Orleans, LA (MSY-New Orleans Intl.)</option>										
                                            <option value=JFK >New York, NY (JFK-Kennedy)</option>										
                                            <option value=LGA >New York, NY (LGA-LaGuardia)</option>										
                                            <option value=EWR >Newark, NJ (EWR-Newark International Airport)</option>										
                                            <option value=OAK >Oakland, CA (OAK-Oakland Intl.)</option>										
                                            <option value=SNA >Orange County, CA (SNA-John Wayne Intl.)</option>										
                                            <option value=MCO >Orlando, FL (MCO-Orlando Intl.)</option>										
                                            <option value=PHL >Philadelphia, PA (PHL-Philadelphia Intl.)</option>										
                                            <option value=PHX >Phoenix, AZ (PHX-Sky Harbor Intl.)</option>										
                                            <option value=PIT >Pittsburgh, PA (PIT-Greater Pittsburgh Intl.)</option>										
                                            <option value=PDX >Portland, OR (PDX-Portland Intl.)</option>										
                                            <option value=RDU >Raleigh, NC (RDU-Raleigh Durham Intl.)</option>										
                                            <option value=RNO >Reno, NV (RNO-Reno-Tahoe International)</option>										
                                            <option value=SLC >Salt Lake City, UT (SLC-Salt Lake City Intl.)</option>										
                                            <option value=SAT >San Antonio, TX (SAT-San Antonio Intl.)</option>										
                                            <option value=SAN >San Diego, CA (SAN-Lindbergh Field)</option>										
                                            <option value=SFO >San Francisco, CA (SFO-San Francisco Intl.)</option>										
                                            <option value=SJC >San Jose, CA (SJC-San Jose Intl.)</option>										
                                            <option value=SEA >Seattle, WA (SEA-Seattle Tacoma)</option>										
                                            <option value=STL >St. Louis, MO (STL-Lambert-St. Louis Intl.)</option>										
                                            <option value=TPA >Tampa, FL (TPA-Tampa Intl.)</option>										
                                            <option value=DCA >Washington, DC (DCA-Ronald Reagan National)</option>										
                                            <option value=IAD >Washington, DC (IAD-Dulles)</option>										
                                            <option value=PBI >West Palm Beach, FL (PBI-Palm Beach Intl.)</option>					
                                     </select>
								</div>								
							</div>
                            
							<div id="search_second_row" class="clear_float row_search">
								<div id="date_departure">
                                	<label>Departure Date <br /></label>
                                    <input name="departureDate" id="date_departure_box" class="date_box" type="text" onclick="WdatePicker()" /> 
										<select name="departureTime" id="date_departure_time">
											<option value="any">any</option>
											<option value="am">am</option>
											<option value="pm">pm</option>
										</select>
                                   
                                </div>                                
								<div id="date_return">
                                    <label>Return Date <br /></label>
                                    <input name="returnDate" id="date_return_box" class="date_box" type="text" onclick="WdatePicker()"  />
                                      	<select name="returnTime" id="date_return_time">
											<option value="any">any</option>
											<option value="am">am</option>
											<option value="pm">pm</option>
										</select>
                                    
                               </div>                               
							</div>
							<div id="search_third_row" class="clear_float row_search">
								<div id="adult_num">
                                	<label>Adult <br /></label>
                                    <select id="adult_input" name="adult">
										<option value="0" >0</option>
										<option value="1"  selected="selected">1</option>
										<option value="2" >2</option>
										<option value="3" >3</option>
										<option value="4" >4</option>
										<option value="5" >5</option>
										<option value="6" >6</option>
									</select>
								</div>
                                <div id="child_num" class="traveller_box">
                                	<label>Children <br /></label>
                                     <select id="child_input" name="children">
										<option value="0" selected="selected">0</option>
										<option value="1" >1</option>
										<option value="2" >2</option>
										<option value="3" >3</option>
										<option value="4" >4</option>
										<option value="5" >5</option>
										<option value="6" >6</option>
									</select>
                                </div>
                                <div id="senior_num" class="traveller_box">
                                	<label>Seniors <br /></label>
                                     <select id="senior_input" name="senior">
										<option value="0" selected="selected">0</option>
										<option value="1" >1</option>
										<option value="2" >2</option>
										<option value="3" >3</option>
										<option value="4" >4</option>
										<option value="5" >5</option>
										<option value="6" >6</option>
									</select>
                                </div>
                            </div>
                            <div id="search_forth_row" class="clear_float row_search">
								<div id="trip_type">
                                	<label>Type of flight</label>                     
                                    <div>
										<input type="radio" name="tripType" id="option_roundtrip" value="RoundTrip" checked="checked" /> 
                                        <label for="option_roundtrip">RoundTrip</label> 
										<input type="radio" name="tripType" id="option_oneway" value="OneWay" /> 
                                        <label for="option_oneway">One Way</label>                                        	
									</div>                                  
                                </div>
							</div>
							<div id="search_button" class="clear_float row_search">
                            		<span><input type="button" value="Search" id="button_serach" onclick="submitForm()"/></span>
									<span><img src="./CSS/pic/search_image.gif" id="search_image"/></span>
							</div>                           
						</div>
                        <div id="search_box_bottom"></div>  
					</form>
				</div>  
				<div id="airSearchRS">
                	<div id="airSearchRS_header">
                    	<b>Air Search Response</b>
                    </div>
                    <div id="airSearchRS_main"></div>
                    <div id="airSearchRS_bottom"></div>
                </div>
			</div>
            

			<div id="flightProducts">
				<div id="flightProducts_header">
                	<b>Flight Products</b>
                </div>
                <div id="flightProducts_main">
                	<div id="product_list_control">
						<span id="product_list_top_display">No. of products to display:</span>
						<span id="product_list_top_display_input"><input type="text" value="1" size="1" id="product_list_filter_value"></input></span>
						<span id="product_list_top_display_updateButton"><input type="button" value="Update" onclick="updateResults()"></input></span>
						<span id="product_list_top_display_compareButton"><input type="button" value="Compare" id="compare_button" onclick="comparePrice()"></input></span>
					</div>
					<div id="product_list_display">
						<div id="product_1">
							<!--span class="product_content"></span>
							<span class="product_select"><input type="checkbox" name="select_button" class="select_to_compare"></input></span-->
						</div>
					</div>
                </div>
                <div id="flightProducts_bottom"></div>
			</div>
			<div class="clear-float"></div>
		</div>
		<div id="bottom">
			<div id="uiPricing">
				<div id="bottom_uipricing_header"></div>
				<div id="bottom_uipricing_main"></div>
				<div id="bottom_uipricing_bottom"></div>
			</div>
			<div id="apiPricing">
				<div id="bottom_apipricing_header"></div>
				<div id="bottom_apipricing_main"></div>
				<div id="bottom_apipricing_bottom"></div>
			</div>
		</div>
	</div>
</body>
</html>


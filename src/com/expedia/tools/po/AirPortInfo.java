package com.expedia.tools.po;

public class AirPortInfo implements java.io.Serializable {

	private static final long serialVersionUID = 4216871325059690973L;

	private String dePartureAirportCode;

	private String dePartureTime;

	private String arrivalAirportCode;

	private String arrivalTime;

	private String stop;

	private Double totalPrice;

	private Double basePrice;

	private String totalTravelingHours;

	private String flightId;

	private String flightFareType;



	public String getFlightFareType() {
		return flightFareType;
	}

	public void setFlightFareType(String flightFareType) {
		this.flightFareType = flightFareType;
	}

	public String getFlightId() {
		return flightId;
	}

	public void setFlightId(String flightId) {
		this.flightId = flightId;
	}

	public String getTotalTravelingHours() {
		return totalTravelingHours;
	}

	public void setTotalTravelingHours(String totalTravelingHours) {
		this.totalTravelingHours = totalTravelingHours;
	}

	public String getDePartureAirportCode() {
		return dePartureAirportCode;
	}

	public void setDePartureAirportCode(String dePartureAirportCode) {
		this.dePartureAirportCode = dePartureAirportCode;
	}

	public String getDePartureTime() {
		return dePartureTime;
	}

	public void setDePartureTime(String dePartureTime) {
		this.dePartureTime = dePartureTime;
	}

	public String getArrivalAirportCode() {
		return arrivalAirportCode;
	}

	public void setArrivalAirportCode(String arrivalAirportCode) {
		this.arrivalAirportCode = arrivalAirportCode;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getStop() {
		return stop;
	}

	public void setStop(String stop) {
		this.stop = stop;
	}

	public Double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

}

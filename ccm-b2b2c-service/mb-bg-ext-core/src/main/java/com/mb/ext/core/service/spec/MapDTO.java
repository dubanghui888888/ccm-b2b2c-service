
package com.mb.ext.core.service.spec;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MapDTO extends AbstractBaseDTO
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1995696595580266727L;

	public String getCoordinateType() {
		return coordinateType;
	}

	public void setCoordinateType(String coordinateType) {
		this.coordinateType = coordinateType;
	}

	private double srcLongitude;
	
	private double srcLatitude;
	
	private double destLongitude;
	
	private double destLatitude;
	
	/*
	 * 1: src-百度坐标, dest-百度坐标
	 * 2: src-腾讯坐标, dest-腾讯坐标
	 * 3: src-百度坐标, dest-腾讯坐标
	 * 4: src-腾讯坐标, dest-百度坐标
	 * 
	 */
	private String coordinateType;	
	
	public double getSrcLongitude() {
		return srcLongitude;
	}

	public void setSrcLongitude(double srcLongitude) {
		this.srcLongitude = srcLongitude;
	}

	public double getSrcLatitude() {
		return srcLatitude;
	}

	public void setSrcLatitude(double srcLatitude) {
		this.srcLatitude = srcLatitude;
	}

	public double getDestLongitude() {
		return destLongitude;
	}

	public void setDestLongitude(double destLongitude) {
		this.destLongitude = destLongitude;
	}

	public double getDestLatitude() {
		return destLatitude;
	}

	public void setDestLatitude(double destLatitude) {
		this.destLatitude = destLatitude;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	
	
	public String getDistanceText() {
		return distanceText;
	}

	public void setDistanceText(String distanceText) {
		this.distanceText = distanceText;
	}

	public String getDurationText() {
		return durationText;
	}

	public void setDurationText(String durationText) {
		this.durationText = durationText;
	}
	//距离(米)
	private double distance;
	//距离字符(200公里)
	private String distanceText;
	//时间(秒)
	private double duration;
	//时间字符(1小时30分钟)
	private String durationText;
	
}

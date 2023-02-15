package com.drools.test;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder(toBuilder = true)
public class Model implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String level;
	private String region;
	private String brand;
	private String resort;
	private String duration;
	private Integer maxCount;
	private String value;
	private String benefitChoice;
	private String benefitName;
	private String guaranteed;
	private String propertyNumber;
	private String maxRooms;
	private String lengthOfMaxStay;
	private String defaultValue;
	private String missedBenefitsCompensation;
	private String exceptionInfo;

}
package com.redhat.demo.apimgmt.driver;

import lombok.Getter;
import lombok.Setter;


public class APIProperties {

	@Getter @Setter
	private String appName;
	
	@Getter @Setter
	private String appBaseUrl;
	
	@Getter @Setter
	private String appUserKey;
	
	@Getter @Setter
	private int appCustomerMin;
	
	
	@Getter @Setter
	private int appCustomerMax;
	
	@Getter @Setter
	private int appKeyLookupPercent;
	
	@Getter @Setter
	private int appVersionV1;
	
	@Getter @Setter
	private int appIterations;
	
	@Getter @Setter
	private int appSleepMin;
	
	
	@Getter @Setter
	private int appSleepMax;
	
	@Getter
	private double keyLookupPercent = 0.90;
	public void setKeyLookupPercent(int klp) {
		this.keyLookupPercent = klp / 100.00;
		
	}
	
	public APIProperties() {
		// TODO Auto-generated constructor stub
	}

	public APIProperties(String appName, String appBaseUrl, String appUserKey, int appCustomerMin, int appCustomerMax,
			int appKeyLookupPercent, int appVersionV1, int appIterations, int appSleepMin, int appSleepMax) {
		super();
		this.appName = appName;
		this.appBaseUrl = appBaseUrl;
		this.appUserKey = appUserKey;
		this.appCustomerMin = appCustomerMin;
		this.appCustomerMax = appCustomerMax;
		this.appKeyLookupPercent = appKeyLookupPercent;
		this.appVersionV1 = appVersionV1;
		this.appIterations = appIterations;
		this.appSleepMin = appSleepMin;
		this.appSleepMax = appSleepMax;
	}
	

}

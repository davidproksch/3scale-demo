package com.redhat.demo.apimgmt.driver;

import lombok.Getter;
import lombok.Setter;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {
	
	@Getter @Setter private Integer id;
	@Getter @Setter private Short c_id;
	@Getter @Setter private Integer c_d_id;
	@Getter @Setter private Integer c_w_id;
	@Getter @Setter private String  c_first_name;
	@Getter @Setter private String  c_last_name;
	@Getter @Setter private String  c_street;
	@Getter @Setter private String  c_city;
	@Getter @Setter private String  c_state;
	@Getter @Setter private String  c_zip;

}

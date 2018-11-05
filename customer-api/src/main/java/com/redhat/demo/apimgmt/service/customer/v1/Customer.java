package com.redhat.demo.apimgmt.service.customer.v1;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter private Integer id;
	@Getter private Short c_id;
	@Getter @Setter private Integer c_d_id;
	@Getter @Setter private Integer c_w_id;
	@Getter @Setter private String  c_first_name;
	@Getter @Setter private String  c_last_name;
	@Getter @Setter private String  c_street;
	@Getter @Setter private String  c_city;
	@Getter @Setter private String  c_state;
	@Getter @Setter private String  c_zip;
	
}

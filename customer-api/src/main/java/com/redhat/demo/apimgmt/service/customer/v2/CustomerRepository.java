package com.redhat.demo.apimgmt.service.customer.v2;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{

	List<Customer> findByState(String state);
	
	 @Query("SELECT a.id, a.c_first_name, a.c_last_name, a.c_city, a.c_state, a.c_zip FROM Customer a WHERE a.c_state=:state")
	 List<Customer> fetchCustomers(@Param("state") String state);
	
}

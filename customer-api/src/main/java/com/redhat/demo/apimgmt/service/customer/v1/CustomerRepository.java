package com.redhat.demo.apimgmt.service.customer.v1;

import org.springframework.data.repository.CrudRepository;

import com.redhat.demo.apimgmt.service.Fruit;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{

}

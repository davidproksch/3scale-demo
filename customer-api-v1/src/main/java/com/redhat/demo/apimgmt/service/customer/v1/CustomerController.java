package com.redhat.demo.apimgmt.service.customer.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redhat.demo.apimgmt.exception.NotFoundException;
import com.redhat.demo.apimgmt.exception.UnsupportedMediaTypeException;
import com.redhat.demo.apimgmt.service.customer.v1.CustomerRepository;
import com.redhat.demo.apimgmt.service.customer.v1.Customer;

import java.util.List;
import java.util.Objects;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(value = "/api/v1/customer")
public class CustomerController {

	private final CustomerRepository repository;
	
	public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }
	
	/*
	 * endpoint for searching for a customer based upon their Customer ID
	 * 
	 * Customer ID is an interger - 1..1270, based upon the current dataset
	 * loaded into the database.
	 * 
	 */
	@GetMapping("/{id}")
    public Customer get(@PathVariable("id") Integer id) {
        verifyCustomerExists(id);
        System.err.println(repository.findOne(id).toString());
        return repository.findOne(id);
    }

	/*
	 * endpont for returning all the customers in the database.  There
	 * is no order to the list return.  This is mainly to just drive traffice and 
	 * load on the system.
	 */
    @GetMapping
    public List<Customer> getAll() {
        Spliterator<Customer> customers = repository.findAll()
                .spliterator();

        return StreamSupport
                .stream(customers, false)
                .collect(Collectors.toList());
    }
    
    private void verifyCustomerExists(Integer id) {
        if (!repository.exists(id)) {
            throw new NotFoundException(String.format("Customer with id=%d was not found", id));
        }
    }
    
    private void verifyCorrectPayload(Customer customer) {
        if (Objects.isNull(customer)) {
            throw new UnsupportedMediaTypeException("Invalid payload!");
        }

       // if (Objects.isNull(customer.getName()) || customer.getName().trim().length() == 0) {
       //     throw new UnprocessableEntityException("The name is required!");
       // }
      System.out.println(customer);
       // if (!Objects.isNull(customer.getId())) {
       //     throw new UnprocessableEntityException("Id was invalidly set on request.");
       // }
    }

}

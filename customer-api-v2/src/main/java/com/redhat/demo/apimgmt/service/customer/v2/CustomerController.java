package com.redhat.demo.apimgmt.service.customer.v2;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.redhat.demo.apimgmt.exception.NotFoundException;
import com.redhat.demo.apimgmt.exception.UnprocessableEntityException;
import com.redhat.demo.apimgmt.exception.UnsupportedMediaTypeException;
import com.redhat.demo.apimgmt.service.customer.v2.CustomerRepository;
import com.redhat.demo.apimgmt.service.customer.v2.Customer;

import java.util.List;
import java.util.Objects;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(value = "/api/v2/customer")
public class CustomerController {

	private final CustomerRepository repository;
	
	public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }
	
	
	@GetMapping("/{id}")
    public Customer get(@PathVariable("id") Integer id) {
        verifyCustomerExists(id);
        
        return repository.findOne(id);
    }

    @GetMapping
    public List<Customer> getAll() {
        Spliterator<Customer> customers = repository.findAll()
                .spliterator();

        return StreamSupport
                .stream(customers, false)
                .collect(Collectors.toList());
    }
    
    @GetMapping("/state/{c_state}")
    public List<Customer> get(@PathVariable("c_state") String c_state) {
        //verifyCustomerExists(id);
    	Spliterator<Customer> customers = repository.findByState(c_state)
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

}

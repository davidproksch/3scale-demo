package com.redhat.demo.apimgmt.driver;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.redhat.demo.apimgmt.driver.APIProperties;

import lombok.Getter;
import lombok.Setter;


@Configuration
public class TrafficDriver {
	/*
	 * Determine how to drive traffic to the APIs
	 * 95% will be single look ups by ID (1..1270)
	 * 5% will be retrieve entire customer list
	 * 
	 * Sleep time between API calls:  Max, Min - Set by properties file
	 * 
	 * Number of iterations: Set by properties file
	 * 
	 * Version Ratio:  Set by properties file
	 */
	@Getter @Setter APIProperties a;
	@Getter @Setter RestTemplate restTemplate;
	@Getter @Setter Random random;
	
	public void drive() {
		random = new Random(13);
		a.setKeyLookupPercent(a.getAppKeyLookupPercent());
		Stream<Integer> s = IntStream.rangeClosed(0, a.getAppIterations()).boxed();
		s.forEach(i -> {
			System.err.println(i);
			double d = random.nextDouble();
			//System.err.println(d);
			//System.err.println(a.getKeyLookupPercent());
			if ( d < a.getKeyLookupPercent()) {
				//System.err.println("single");
				driveTraffic();
			}
			else {
				//System.err.println("list");
				driveListTraffic();
			}
			goToSleep();
		});
	}
	
	private void goToSleep() {
		int sleepyTime = (getRandom().nextInt(30)+5)*1000;
		//System.err.println("Sleeping ..." + sleepyTime);
		try {
			Thread.sleep(sleepyTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private String whichVersion(double d) {
		String version = "v2";
		if (d < (a.getAppVersionV1() / 100.00)) {
			version = "v1";
		}
		//System.err.println(version);
		return version;
	}
	
	private void callAPI(String url) {
		try {
			ResponseEntity<Customer> response
				= restTemplate.getForEntity(url, Customer.class);
		}
		catch (Exception e) {
			System.err.println("Error received.");
		}
	}
	private void callListAPI(String url) {
		ResponseEntity<List<Customer>> response = this.getRestTemplate().exchange(
				url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Customer>>(){});
		List<Customer> customers = response.getBody();
		
	}
	
	private void driveTraffic() {
	
		int customerID = this.getRandom().nextInt(a.getAppCustomerMax());
		if (this.random.nextDouble() < 0.10) {
			customerID = customerID*-1;
		}
		String userKey = a.getAppUserKey().trim();
		if (this.random.nextDouble() < 0.10) {
			userKey = userKey.toUpperCase();
		}
		StringBuffer url = new StringBuffer();
		url.append(a.getAppBaseUrl().trim())
			.append("/")
			.append(this.whichVersion(this.getRandom().nextDouble()))
			.append("/customer/")
			.append(customerID)
			.append("?user_key=")
			.append(userKey);
		callAPI(url.toString());
		
	}
	

	private void driveListTraffic() {
		

		StringBuffer url = new StringBuffer();
		url.append(a.getAppBaseUrl().trim())
			.append("/")
			.append(this.whichVersion(this.getRandom().nextDouble()))
			.append("/customer")
			.append("?user_key=")
			.append(a.getAppUserKey().trim());
		
		callListAPI(url.toString());
		
		/*
		ResponseEntity<List<Customer>> response = restTemplate.exchange(
				url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Customer>>(){});
		List<Customer> customers = response.getBody();
		System.err.println(customers.size());
		*/
		
	}
}

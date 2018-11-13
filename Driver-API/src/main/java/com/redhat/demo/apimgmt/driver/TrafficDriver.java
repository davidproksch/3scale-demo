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
			
			/*
			 * I am a key lookup or a list
			 */
			if ( d < a.getKeyLookupPercent()) {
				driveTraffic();
			}
			else {
				driveListTraffic();
			}
			/*
			 * Whew ... that was hard, nap time!s
			 */
			goToSleep();
		});
	}
	
	/*
	 * Take a nap to either simulate think time between either
	 * - browser requests
	 * - integration requests
	 * - prevent accessing the API too many times, as is the case with
	 *   the "freemium" version of AWS or 3scale
	 */
	private void goToSleep() {
		/*
		 * @ToDo:  
		 * 1. Fetch the sleepytime upper bounds from the 
		 * properties file.
		 * 2. Change the properties file to either reflect microseconds or
		 * convert the seconds to microseconds.
		 * 
		 * The +5 in the equation is to make sure the minimum sleep time
		 * is always at least 5 seconds.
		 */
		int sleepyTime = (getRandom().nextInt(30)+5)*1000;
		try {
			Thread.sleep(sleepyTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/*
	 * Simulate versioning of the API.  This is used in the demo to simulate
	 * monitoring the adaptation of V2 compared to V1.  Different metrics
	 * can be applied, i.e., rate limits, higher costs, etc. to encourage
	 * the adaption of the next version of the API.  
	 * 
	 * Carror or stick?
	 */
	private String whichVersion(double d) {
		String version = "v2";
		if (d < (a.getAppVersionV1() / 100.00)) {
			version = "v1";
		}
		return version;
	}
	
	/*
	 * Fetch one record via the key lookup of the customer API.
	 * 
	 * Since nothing is really done with the results, ignore any excpetions
	 * thrown
	 * 
	 */
	private void callAPI(String url) {
		try {
			ResponseEntity<Customer> response
				= restTemplate.getForEntity(url, Customer.class);
		}
		catch (Exception e) {
			System.err.println("Error received.");
		}
	}
	
	/*
	 * Fetch a listing of customers.
	 * 
	 * Since nothing is really done with the results, ignore any excpetions
	 * thrown
	 */
	private void callListAPI(String url) {
		try {
			ResponseEntity<List<Customer>> response = this.getRestTemplate().exchange(
					url, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<Customer>>(){});
			List<Customer> customers = response.getBody();
		}
		catch (Exception e) {
			System.err.println("Error received.");
		}
		
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
		
	}
}

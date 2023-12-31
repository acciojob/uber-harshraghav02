package com.driver.services.impl;

import com.driver.model.TripBooking;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;
import com.driver.model.TripStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId)  {
		// Delete customer without using deleteById function
		Optional<Customer> optionalCustomer = customerRepository2.findById(customerId);



		customerRepository2.delete(optionalCustomer.get());
	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query

		List<Driver> driverList = driverRepository2.findAll();
		Driver driver = null;

		for(Driver driver1 : driverList){
			if(driver1.getCab().getAvailable() == true) {
				driver = driver1;
				break;
			}
		}

		if(driver == null) {
			throw new Exception("No cab available!");
		}

		TripBooking tripBooking = new TripBooking();
		tripBooking.setCustomer(customerRepository2.findById(customerId).get());
		tripBooking.setFromLocation(fromLocation);
		tripBooking.setToLocation(toLocation);
		tripBooking.setDriver(driver);
		tripBooking.setDistanceInKm(distanceInKm);
		tripBooking.setBill(distanceInKm*driver.getCab().getPerKmRate());
		tripBooking.setStatus(TripStatus.CONFIRMED);

		driver.getTripBookingList().add(tripBooking);
		Customer customer = customerRepository2.findById(customerId).get();
		customer.getTripBookingList().add(tripBooking);

		driverRepository2.save(driver);
		customerRepository2.save(customer);
		tripBookingRepository2.save(tripBooking);

		return tripBooking;


	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();

		tripBooking.setStatus(TripStatus.CANCELED);
		tripBooking.setBill(0);
		Driver driver = tripBooking.getDriver();
		Customer customer = tripBooking.getCustomer();

		for(TripBooking tripBooking1 : driver.getTripBookingList()){
			if(tripBooking1.getTripBookingId() == tripId){
				driver.getTripBookingList().remove(tripBooking1);
				break;
			}
		}

		for(TripBooking tripBooking1 : customer.getTripBookingList()){
			if(tripBooking1.getTripBookingId() == tripId){
				customer.getTripBookingList().remove(tripBooking1);
				break;
			}
		}

		driverRepository2.save(driver);
		customerRepository2.save(customer);
		tripBookingRepository2.save(tripBooking);

	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly

		TripBooking tripBooking = tripBookingRepository2.findById(tripId).get();

		tripBooking.setStatus(TripStatus.COMPLETED);
		Driver driver = tripBooking.getDriver();
		Customer customer = tripBooking.getCustomer();

		tripBookingRepository2.save(tripBooking);

	}
}

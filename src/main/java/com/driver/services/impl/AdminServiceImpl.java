package com.driver.services.impl;

import java.util.List;
import java.util.Optional;

import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Admin;
import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.AdminRepository;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminRepository adminRepository1;

	@Autowired
	DriverRepository driverRepository1;

	@Autowired
	CustomerRepository customerRepository1;

	@Override
	public void adminRegister(Admin admin) {
		adminRepository1.save(admin);
		//Save the admin in the database
	}

	@Override
	public Admin updatePassword(Integer adminId, String password) {
		//Update the password of admin with given id
		Optional<Admin> optionalAdmin = adminRepository1.findById(adminId);



		Admin admin = optionalAdmin.get();

		admin.setPassword(password);

		return admin;
	}

	@Override
	public void deleteAdmin(int adminId)  {
		// Delete admin without using deleteById function
		Optional<Admin> optionalAdmin = adminRepository1.findById(adminId);


		adminRepository1.delete(optionalAdmin.get());
	}

	@Override
	public List<Driver> getListOfDrivers() {
		//Find the list of all drivers
		List<Driver> listOfDriver = driverRepository1.findAll();
		return listOfDriver;
	}

	@Override
	public List<Customer> getListOfCustomers() {
		//Find the list of all customers
		List<Customer> customerList = customerRepository1.findAll();
		return customerList;
	}

}

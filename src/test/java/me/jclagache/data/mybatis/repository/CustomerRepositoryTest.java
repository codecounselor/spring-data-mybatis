package me.jclagache.data.mybatis.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import me.jclagache.data.mybatis.ApplicationConfig;
import me.jclagache.data.mybatis.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@SpringApplicationConfiguration(classes = ApplicationConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CustomerRepositoryTest {

	@Autowired CustomerRepository customerRepository;
	
	@Test
	public void testFindOneCustomer() {
		Customer customer = customerRepository.findOne(100);
		assertNotNull(customer);
	}
	
	@Test
	public void testFindAllCustomers() {
		Iterable<Customer> customers = customerRepository.findAll();
		assertNotNull(customers);
		assertTrue(customers.iterator().hasNext());
	}

	@Test
	public void testFindAllCustomers_Paged() {
		PageRequest pageRequest = new PageRequest(1, 1);
		//TODO: would be nice to attach to the find mapping, and not need an distinct mapper definition
		// Don't know if its possible because MyBatis doesn't like Page vs List for return type
		List<Customer> customers = customerRepository.findAllList(pageRequest);
		assertNotNull(customers);
		Iterator<Customer> it = customers.iterator();
		it.next();
		assertFalse(it.hasNext());
	}
	
	@Test
	public void testFindCustomersByFirstName() {
		List<Customer> customers = customerRepository.findByFirstName("John");
		assertNotNull(customers);
		assertTrue(customers.size() == 1);
	}
	
	@Test
	public void testFindCustomersByLastName() {
		List<Customer> customers = customerRepository.findByLastName("Doe");
		assertNotNull(customers);
		assertTrue(customers.size() == 3);
	}
}

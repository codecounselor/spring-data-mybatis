package me.jclagache.data.mybatis.repository;

import me.jclagache.data.mybatis.domain.Customer;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Mapping and queries defined in mapper/Customer/Customer.xml
 * except me.jclagache.data.mybatis.repository.CustomerRepository#findByLastName(java.lang.String)
 */
public interface CustomerRepository extends MyBatisPagingAndSortingRepository<Customer, Integer> {

	List<Customer> findByFirstName(String firstName);

	/**
	 *
	 * Instead of Query annotation can be used org.apache.ibatis.annotations.Select
	 */
	@Select("SELECT customer.id id, customer.first_name first_name, customer.last_name last_name, customer.email_address email_adress, address.id address_id, " +
			"address.street address_street, address.city address_city, address.country address_country FROM customer, address WHERE customer.id = address.customer_id " +
			" AND customer.last_name = #{lastName}")
	List<Customer> findByLastName(String lastName);

}

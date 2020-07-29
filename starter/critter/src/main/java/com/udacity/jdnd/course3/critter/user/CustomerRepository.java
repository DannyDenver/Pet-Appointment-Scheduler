package com.udacity.jdnd.course3.critter.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("Select c from Customer c join Pet p on p.owner.id = c.id where p.id = :petId")
    Customer getCustomerByPetsId(Long petId);
}

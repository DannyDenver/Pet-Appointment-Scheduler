package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.PetDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    ModelMapper mapper;

    CustomerRepository customerRepository;

    static final Type customerDtoListType = new TypeToken<List<CustomerDTO>>(){}.getType();

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);

        customer = customerRepository.save(customer);
        BeanUtils.copyProperties(customer, customerDTO);

        return customerDTO;
    }

    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return mapper.map(customers, customerDtoListType);
    }

    public CustomerDTO getOwnerByPet(Long petId) {
        Customer customer = customerRepository.getCustomerByPetsId(petId);
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        return customerDTO;
    }
}

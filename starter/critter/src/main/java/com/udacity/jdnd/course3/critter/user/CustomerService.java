package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    ModelMapper mapper;

    CustomerRepository customerRepository;

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
        return convertEntitiesToCustomerDTOs(customers);
    }

    public CustomerDTO getOwnerByPet(Long petId) {
        Customer customer = customerRepository.getCustomerByPetsId(petId);
        return convertEntityToCustomerDTO(customer);
    }

    public Customer getCustomerById(Long ownerId) {
        return customerRepository.findById(ownerId).get();
    }

    public void addPetToOwner(Pet pet, Customer customer) {
        List<Pet> pets = customer.getPets();
        if (pets == null) {
            pets = new ArrayList<>();
        }
        pets.add(pet);
        customer.setPets(pets);
        customerRepository.save(customer);
    }

    private static List<CustomerDTO> convertEntitiesToCustomerDTOs(List<Customer> customers) {
        List<CustomerDTO> customerDTOS = new ArrayList<>();

        customers.forEach(customer -> {
            customerDTOS.add(convertEntityToCustomerDTO(customer));
        });

        return customerDTOS;
    }

    private static CustomerDTO convertEntityToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);

        if(customer.getPets() != null && customer.getPets().size() > 0) {
            List<Long> petIds = new ArrayList<>();
            customer.getPets().forEach(pet -> petIds.add(pet.getId()));
            customerDTO.setPetIds(petIds);
        };

        return customerDTO;
    }
}

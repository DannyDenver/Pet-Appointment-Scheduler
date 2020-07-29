package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class PetService {

    @Autowired
    ModelMapper mapper;

    PetRepository petRepository;
    CustomerService customerService;

    public PetService(PetRepository petRepository, CustomerService customerService) {
        this.petRepository = petRepository;
        this.customerService = customerService;
    }

    public Pet savePet(Pet pet) {
        pet = petRepository.save(pet);

        if(pet.getCustomer() != null) {
            customerService.addPetToOwner(pet, pet.getCustomer());
        };

        return pet;
    }

    public Pet getPet(long petId) {
        Pet pet = petRepository.getPetById(petId);
        return pet;
    }

    public List<Pet> getPets() {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByOwner(Long ownerId) {
        return petRepository.getPetsByCustomerId(ownerId);
    }
}

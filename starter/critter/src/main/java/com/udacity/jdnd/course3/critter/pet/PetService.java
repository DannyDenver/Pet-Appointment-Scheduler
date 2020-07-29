package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public PetDTO savePet(PetDTO petDTO) {
        Pet pet = convertPetDtoToEntity(petDTO);

        pet = petRepository.save(pet);

        if(petDTO.getOwnerId() > 0) {
            customerService.addPetToOwner(pet, pet.getCustomer());
        };

        return convertEntityToPetDto(pet);
    }

    public PetDTO getPet(long petId) {
        Pet pet = petRepository.getPetById(petId);
        return convertEntityToPetDto(pet);
    }

    public List<PetDTO> getPets() {
        List<Pet> pets = petRepository.findAll();

        return convertEntitiesToPetDtos(pets);
    }

    public List<PetDTO> getPetsByOwner(Long ownerId) {
        List<Pet> pets = petRepository.getPetsByCustomerId(ownerId);

        return convertEntitiesToPetDtos(pets);
    }

    private static List<PetDTO> convertEntitiesToPetDtos(List<Pet> pets) {
        List<PetDTO> petDtos = new ArrayList<PetDTO>();
        pets.forEach(pet -> petDtos.add(convertEntityToPetDto(pet)));

        return petDtos;
    }

    private Pet convertPetDtoToEntity(PetDTO petDto) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDto, pet);

        if(petDto.getOwnerId() > 0) {
            Customer customer = customerService.getCustomerById(petDto.getOwnerId());
            pet.setCustomer(customer);
        }

        return pet;
    }

    private static PetDTO convertEntityToPetDto(Pet pet) {
        PetDTO petDto = new PetDTO();
        BeanUtils.copyProperties(pet, petDto);

        petDto.setOwnerId(pet.getCustomer().getId());

        return petDto;
    }
}

package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetService {

    @Autowired
    ModelMapper mapper;

    PetRepository petRepository;

    public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository; }

    public PetDTO savePet(PetDTO petDTO) {
        Pet pet = convertPetDtoToEntity(petDTO);

        pet = petRepository.save(pet);

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
        List<Pet> pets = petRepository.getPetsByOwnerId(ownerId);

        return convertEntitiesToPetDtos(pets);
    }

    private static List<PetDTO> convertEntitiesToPetDtos(List<Pet> pets) {
        List<PetDTO> petDtos = new ArrayList<PetDTO>();
        pets.forEach(pet -> petDtos.add(convertEntityToPetDto(pet)));

        return petDtos;
    }

    private static Pet convertPetDtoToEntity(PetDTO petDto) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDto, pet);
        Customer customer = new Customer();
        customer.setId(petDto.getOwnerId());
        pet.setOwner(customer);

        return pet;
    }

    private static PetDTO convertEntityToPetDto(Pet pet) {
        PetDTO petDto = new PetDTO();
        BeanUtils.copyProperties(pet, petDto);

        petDto.setOwnerId(pet.getOwner().getId());

        return petDto;
    }
}

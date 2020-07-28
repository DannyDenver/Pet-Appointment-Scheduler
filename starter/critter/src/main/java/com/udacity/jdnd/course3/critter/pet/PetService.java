package com.udacity.jdnd.course3.critter.pet;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class PetService {

    @Autowired
    ModelMapper mapper;

    PetRepository petRepository;

    static final Type petDtoListType = new TypeToken<List<PetDTO>>(){}.getType();

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public PetDTO savePet(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);

        pet = petRepository.save(pet);
        BeanUtils.copyProperties(pet, petDTO);

        return petDTO;
    }

    public PetDTO getPet(long petId) {
        PetDTO petDTO = new PetDTO();
        Pet pet = petRepository.getPetById(petId);
        BeanUtils.copyProperties(pet, petDTO);

        if(pet.getOwner() != null) {
            petDTO.setOwnerId(pet.getOwner().getId());
        }

        return petDTO;
    }

    public List<PetDTO> getPets() {
        List<Pet> pets = petRepository.findAll();

        return mapper.map(pets, petDtoListType);
    }

    public List<PetDTO> getPetsByOwner(Long ownerId) {
        List<Pet> pets = petRepository.getPetsByOwnerId(ownerId);

        return mapper.map(pets, petDtoListType);
    }
}

package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    PetService petService;
    CustomerService customerService;

    public PetController(PetService petService, CustomerService customerService) {
        this.petService = petService;
        this.customerService = customerService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertPetDtoToEntity(petDTO);

        return convertEntityToPetDto(petService.savePet(pet));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertEntityToPetDto(petService.getPet(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return convertEntitiesToPetDtos(petService.getPets());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return convertEntitiesToPetDtos(petService.getPetsByOwner(ownerId));
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
            customer.setId(petDto.getOwnerId());
            pet.setCustomer(customer);
        }

        return pet;
    }

    private static PetDTO convertEntityToPetDto(Pet pet) {
        PetDTO petDto = new PetDTO();
        BeanUtils.copyProperties(pet, petDto);

        if(pet.getCustomer() != null) {
            petDto.setOwnerId(pet.getCustomer().getId());
        }

        return petDto;
    }
}

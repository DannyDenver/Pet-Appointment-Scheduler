package com.udacity.jdnd.course3.critter.pet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface PetRepository extends JpaRepository<Pet, Long> {

    Pet getPetById(Long id);

    List<Pet> getPetsByCustomerId(Long id);
}

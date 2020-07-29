package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

   List<Schedule> getSchedulesByPetsId(Long id);

   List<Schedule> getSchedulesByEmployeesId(Long id);

   List<Schedule> getSchedulesByPetsOwnerId(Long id);

}

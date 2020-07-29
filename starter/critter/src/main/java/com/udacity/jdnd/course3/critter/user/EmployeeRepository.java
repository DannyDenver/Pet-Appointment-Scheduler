package com.udacity.jdnd.course3.critter.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Repository
@EnableJpaRepositories
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("update Employee e set e.daysAvailable = :daysAvailable where e.id = :employeeId")
    void setAvailability(Set<DayOfWeek> daysAvailable, Long employeeId);

    List<Employee> getEmployeeByDaysAvailable(DayOfWeek day);
}

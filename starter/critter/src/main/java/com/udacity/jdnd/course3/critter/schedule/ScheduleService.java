package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class ScheduleService {
    @Autowired
    ModelMapper mapper;

    ScheduleRepository scheduleRepository;

    ScheduleService(ScheduleRepository repository) {
        this.scheduleRepository = repository;
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getSchedulesForPet(Long petId) {
        return scheduleRepository.getSchedulesByPetsId(petId);
    }

    public List<Schedule> getSchedulesForEmployee(Long employeeId) {
        return scheduleRepository.findAllByEmployees_Id(employeeId);
    }

    public List<Schedule> getScheduleByCustomer(Long customerId) {
        return scheduleRepository.getSchedulesByPetsCustomerId(customerId);
    }
}

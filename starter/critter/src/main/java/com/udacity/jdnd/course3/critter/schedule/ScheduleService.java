package com.udacity.jdnd.course3.critter.schedule;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    ModelMapper mapper;

    ScheduleRepository scheduleRepository;

    private static final Type scheduleDtoListType = new TypeToken<List<ScheduleDTO>>(){}.getType();

    ScheduleService(ScheduleRepository repository) {
        this.scheduleRepository = repository;
    }

    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();

        return mapper.map(schedules, scheduleDtoListType);
    }

    public ScheduleDTO saveSchedule(ScheduleDTO scheduleDto) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDto, schedule);

        schedule = scheduleRepository.save(schedule);

        BeanUtils.copyProperties(schedule, scheduleDto);

        return scheduleDto;
    }

    public List<ScheduleDTO> getSchedulesForPet(Long petId) {
        List<Schedule> schedules = scheduleRepository.getSchedulesByPetsId(petId);

        return mapper.map(schedules, scheduleDtoListType);
    }

    public List<ScheduleDTO> getSchedulesForEmployee(Long employeeId) {
        List<Schedule> schedules = scheduleRepository.getSchedulesByEmployeesId(employeeId);

        return mapper.map(schedules, scheduleDtoListType);
    }

    public List<ScheduleDTO> getScheduleByCustomer(Long customerId) {
        List<Schedule> schedules = scheduleRepository.getSchedulesByPetsOwnerId(customerId);

        return mapper.map(schedules, scheduleDtoListType);
    }
}

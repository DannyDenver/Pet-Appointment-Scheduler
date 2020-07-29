package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    ModelMapper mapper;

    ScheduleRepository scheduleRepository;

    ScheduleService(ScheduleRepository repository) {
        this.scheduleRepository = repository;
    }

    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return convertEntitiesToScheduleDTOs(schedules);
    }

    public ScheduleDTO saveSchedule(ScheduleDTO scheduleDto) {
        Schedule schedule = scheduleRepository.save(convertScheduleDtoToEntity(scheduleDto));
        return convertEntityToScheduleDTO(schedule);
    }

    public List<ScheduleDTO> getSchedulesForPet(Long petId) {
        List<Schedule> schedules = scheduleRepository.getSchedulesByPetsId(petId);
        return convertEntitiesToScheduleDTOs(schedules);
    }

    public List<ScheduleDTO> getSchedulesForEmployee(Long employeeId) {
        List<Schedule> schedules = scheduleRepository.getSchedulesByEmployeesId(employeeId);
        return convertEntitiesToScheduleDTOs(schedules);
    }

    public List<ScheduleDTO> getScheduleByCustomer(Long customerId) {
        List<Schedule> schedules = scheduleRepository.getSchedulesByPetsOwnerId(customerId);
        return convertEntitiesToScheduleDTOs(schedules);
    }

    private static Schedule convertScheduleDtoToEntity(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        if(scheduleDTO.getEmployeeIds() != null && scheduleDTO.getEmployeeIds().size() > 0) {
            List<Employee> employees = new ArrayList<>();
            scheduleDTO.getEmployeeIds().forEach(id -> {
                Employee employee = new Employee();
                employee.setId(id);
                employees.add(employee);
            });

            schedule.setEmployees(employees);
        }

        if(scheduleDTO.getPetIds() != null && scheduleDTO.getPetIds().size() > 0) {
            List<Pet> pets = new ArrayList<>();
            scheduleDTO.getPetIds().forEach(id -> {
                Pet pet = new Pet();
                pet.setId(id);
                pets.add(pet);
            });
            schedule.setPets(pets);
        }

        return schedule;
    }

    private static List<ScheduleDTO> convertEntitiesToScheduleDTOs(List<Schedule> schedules) {
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        schedules.forEach(schedule -> scheduleDTOS.add(convertEntityToScheduleDTO(schedule)));

        return scheduleDTOS;
    }

    private static ScheduleDTO convertEntityToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();

        List<Long> petIds = new ArrayList<>();
        List<Long> employeeIds = new ArrayList<>();

        if(schedule.getPets() != null) {
            schedule.getPets().forEach(pet -> petIds.add(pet.getId()));
        }

        if(schedule.getEmployees() != null) {
            schedule.getEmployees().forEach(employee -> employeeIds.add(employee.getId()));
        }

        scheduleDTO.setEmployeeIds(employeeIds);
        scheduleDTO.setPetIds(petIds);
        scheduleDTO.setActivities(schedule.getActivities());
        scheduleDTO.setDate(schedule.getDate());

        return scheduleDTO;
    }
}

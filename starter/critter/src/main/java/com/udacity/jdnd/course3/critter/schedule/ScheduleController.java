package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    ScheduleService scheduleService;
    EmployeeService employeeService;
    PetService petService;

    public ScheduleController(ScheduleService service, EmployeeService employeeService, PetService petService) {
        this.scheduleService = service;
        this.employeeService = employeeService;
        this.petService = petService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertScheduleDtoToEntity(scheduleDTO);
        return convertEntityToScheduleDTO(scheduleService.saveSchedule(schedule));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return convertEntitiesToScheduleDTOs(scheduleService.getAllSchedules());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return convertEntitiesToScheduleDTOs(scheduleService.getSchedulesForPet(petId));
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return convertEntitiesToScheduleDTOs(scheduleService.getSchedulesForEmployee(employeeId));
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return convertEntitiesToScheduleDTOs(scheduleService.getScheduleByCustomer(customerId));
    }

    private Schedule convertScheduleDtoToEntity(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        if(scheduleDTO.getEmployeeIds() != null && scheduleDTO.getEmployeeIds().size() > 0) {
            List<Employee> employees = new ArrayList<>();
            scheduleDTO.getEmployeeIds().forEach(id -> {
                Employee employee = employeeService.getEmployee(id);
                employee.setId(id);
                employees.add(employee);
            });

            schedule.setEmployees(employees);
        }

        if(scheduleDTO.getPetIds() != null && scheduleDTO.getPetIds().size() > 0) {
            List<Pet> pets = new ArrayList<>();
            scheduleDTO.getPetIds().forEach(id -> {
                Pet pet = petService.getPet(id);
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

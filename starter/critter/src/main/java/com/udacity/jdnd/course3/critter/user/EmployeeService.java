package com.udacity.jdnd.course3.critter.user;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeService {

    @Autowired
    ModelMapper mapper;

    EmployeeRepository employeeRepository;

    static final Type employeeDtoListType = new TypeToken<List<EmployeeDTO>>(){}.getType();

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

        employee = employeeRepository.save(employee);
        BeanUtils.copyProperties(employee, employeeDTO);

        return employeeDTO;
    }

    public EmployeeDTO getEmployee(Long id) {
        Employee employee = employeeRepository.getOne(id);

        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);

        return employeeDTO;
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, Long employeeId) {
        Employee employee = employeeRepository.getOne(employeeId);
        employee.setDaysAvailable(daysAvailable);

        employeeRepository.save(employee);
    }

    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeRequestDTO) {
        List<Employee> employees = employeeRepository.getEmployeesBySkillsInAndDaysAvailable(employeeRequestDTO.getSkills(), employeeRequestDTO.getDate().getDayOfWeek());
        return mapper.map(employees, employeeDtoListType);
    }
}

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

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.save(convertEmployeeDtoToEntity(employeeDTO));
        return convertEntityToEmployeeDto(employee);
    }

    public EmployeeDTO getEmployee(Long id) {
        Employee employee = employeeRepository.getOne(id);

        return convertEntityToEmployeeDto(employee);
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, Long employeeId) {
        Employee employee = employeeRepository.getOne(employeeId);
        employee.setDaysAvailable(daysAvailable);

        employeeRepository.save(employee);
    }

    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeRequestDTO) {
        List<Employee> employees = employeeRepository.getEmployeeByDaysAvailable(employeeRequestDTO.getDate().getDayOfWeek());

        List<Employee> availableEmployees = new ArrayList<Employee>();
        for(Employee employee: employees) {
            if(employee.getSkills().containsAll(employeeRequestDTO.getSkills())) {
                availableEmployees.add(employee);
            }
        }

        return convertEntitiesToEmployeeDTOs(availableEmployees);
    }

    private static List<EmployeeDTO> convertEntitiesToEmployeeDTOs(List<Employee> employees) {
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();

        employees.forEach(employee -> {
            employeeDTOs.add(convertEntityToEmployeeDto(employee));
        });

        return employeeDTOs;
    }

    private static Employee convertEmployeeDtoToEntity(EmployeeDTO employeeDto) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDto, employee);
        return employee;
    }

    private static EmployeeDTO convertEntityToEmployeeDto(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }
}

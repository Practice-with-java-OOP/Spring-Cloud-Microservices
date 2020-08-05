package com.syphan.practice.department.controller;

import com.syphan.grpc.protoFile.EmployeeServiceGrpc;
import com.syphan.grpc.protoFile.Employees;
import com.syphan.practice.department.config.grpc.SpiGrpcClientInterceptor;
import com.syphan.practice.department.model.Department;
import com.syphan.practice.department.model.Employee;
import com.syphan.practice.department.repository.DepartmentRepository;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DepartmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentRepository repository;

    @Autowired
    private SpiGrpcClientInterceptor clientInterceptor;

    @PostMapping("/")
    public Department add(@RequestBody Department department) {
        LOGGER.info("Department add: {}", department);
        return repository.add(department);
    }

    @GetMapping("/{id}")
    public Department findById(@PathVariable("id") Long id) {
        LOGGER.info("Department find: id={}", id);
        return repository.findById(id);
    }

    @GetMapping("/")
    public List<Department> findAll() {
        LOGGER.info("Department find");
        return repository.findAll();
    }

    @GetMapping("/organization/{organizationId}")
    public List<Department> findByOrganization(@PathVariable("organizationId") Long organizationId) {
        LOGGER.info("Department find: organizationId={}", organizationId);
        return repository.findByOrganization(organizationId);
    }

    @GetMapping("/organization/{organizationId}/with-employees")
    public List<Department> findByOrganizationWithEmployees(@PathVariable("organizationId") Long organizationId) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8085)
                .usePlaintext().intercept(clientInterceptor).build();
        EmployeeServiceGrpc.EmployeeServiceBlockingStub client = EmployeeServiceGrpc.newBlockingStub(channel);
        LOGGER.info("Department find: organizationId={}", organizationId);
        List<Department> departments = repository.findByOrganization(organizationId);
        for (Department department : departments) {
            List<Employee> employees = new ArrayList<>();


            Metadata header = new Metadata();
            Metadata.Key<String> key =
                    Metadata.Key.of("test", Metadata.ASCII_STRING_MARSHALLER);
            header.put(key, "SyPT");
            client = MetadataUtils.attachHeaders(client, header);

            Employees.EmployeeList employeeList = client.findByDepartment(Employees.Id.newBuilder().setId(department.getId()).build());
            for (Employees.Employee employee : employeeList.getEmployeesList()) {
                employees.add(Employee.builder()
                        .id(employee.getId())
                        .name(employee.getName())
                        .age(employee.getAge())
                        .position(employee.getPosition())
                        .build());
            }
            department.setEmployees(employees);
        }
        return departments;
    }

}

package com.syphan.practice.employee.service;

import com.syphan.grpc.protoFile.EmployeeServiceGrpc;
import com.syphan.grpc.protoFile.Employees;
import com.syphan.practice.employee.model.Employee;
import com.syphan.practice.employee.repository.EmployeeRepository;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@GRpcService
@Slf4j
public class EmployeeService extends EmployeeServiceGrpc.EmployeeServiceImplBase {

    @Autowired
    private EmployeeRepository repository;

    @Override
    public void findByDepartment(Employees.Id request, StreamObserver<Employees.EmployeeList> responseObserver) {
        getListEmployeeById(request, responseObserver);
    }

    @Override
    public void findByOrganization(Employees.Id request, StreamObserver<Employees.EmployeeList> responseObserver) {
        getListEmployeeById(request, responseObserver);
    }

    private void getListEmployeeById(Employees.Id request, StreamObserver<Employees.EmployeeList> responseObserver) {
        Long departmentId = request.getId();
        List<Employee> employees = repository.findByDepartment(departmentId);

        Employees.EmployeeList.Builder employeeList = Employees.EmployeeList.newBuilder();
        for (Employee employee : employees) {
            employeeList.addEmployees(Employees.Employee.newBuilder()
                    .setId(employee.getId())
                    .setOrganizationId(employee.getOrganizationId())
                    .setDepartmentId(employee.getDepartmentId())
                    .setName(employee.getName())
                    .setAge(employee.getAge())
                    .setPosition(employee.getPosition())
                    .build());
        }
        responseObserver.onNext(employeeList.build());
        responseObserver.onCompleted();
    }
}

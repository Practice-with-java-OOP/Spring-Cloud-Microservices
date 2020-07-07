package com.syphan.practice.employee;

import com.syphan.practice.employee.model.Employee;
import com.syphan.practice.employee.repository.EmployeeRepository;
import com.syphan.practice.employee.service.EmployeeService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication(scanBasePackages = {"com.syphan.common.rest", "com.syphan.practice.employee"})
@EnableDiscoveryClient
@EnableOAuth2Sso
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class EmployeeApplication implements CommandLineRunner {

    @Autowired
    private EmployeeService employeeService;

    public static void main(String[] args) {
        SpringApplication.run(EmployeeApplication.class, args);
    }

    @Bean
    EmployeeRepository repository() {
        EmployeeRepository repository = new EmployeeRepository();
        repository.add(new Employee(1L, 1L, "John Smith", 34, "Analyst"));
        repository.add(new Employee(1L, 1L, "Darren Hamilton", 37, "Manager"));
        repository.add(new Employee(1L, 1L, "Tom Scott", 26, "Developer"));
        repository.add(new Employee(1L, 2L, "Anna London", 39, "Analyst"));
        repository.add(new Employee(1L, 2L, "Patrick Dempsey", 27, "Developer"));
        repository.add(new Employee(2L, 3L, "Kevin Price", 38, "Developer"));
        repository.add(new Employee(2L, 3L, "Ian Scott", 34, "Developer"));
        repository.add(new Employee(2L, 3L, "Andrew Campton", 30, "Manager"));
        repository.add(new Employee(2L, 4L, "Steve Franklin", 25, "Developer"));
        repository.add(new Employee(2L, 4L, "Elisabeth Smith", 30, "Developer"));
        return repository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting grpc server");
        Server server = ServerBuilder.forPort(9089).addService(employeeService).build(); // create a instance of server

        server.start();
        log.info("Server Started at " + server.getPort());
    }
}

package com.syphan.practice.department;

import com.syphan.practice.department.model.Department;
import com.syphan.practice.department.repository.DepartmentRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication(scanBasePackages = {"com.syphan.common.rest", "com.syphan.practice.department"})
@EnableOAuth2Sso
@EnableFeignClients
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class DepartmentApplication {
    public static void main(String[] args) {
        SpringApplication.run(DepartmentApplication.class, args);
    }

    @Bean
    DepartmentRepository repository() {
        DepartmentRepository repository = new DepartmentRepository();
        repository.add(new Department(1L, "Development"));
        repository.add(new Department(1L, "Operations"));
        repository.add(new Department(2L, "Development"));
        repository.add(new Department(2L, "Operations"));
        return repository;
    }
}

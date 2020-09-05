package com.syphan.practice.employee.controller;

import com.syphan.common.api.base.wrapper.PageWrapper;
import com.syphan.common.rest.response.OpenApiWithPageResponse;
import com.syphan.common.rest.security.CurrentUser;
import com.syphan.common.rest.security.UserPrincipal;
import com.syphan.practice.employee.model.Employee;
import com.syphan.practice.employee.repository.EmployeeRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @PostMapping("/")
    public Employee add(@RequestBody Employee employee) {
        LOGGER.info("Employee add: {}", employee);
        return repository.add(employee);
    }

    @PreAuthorize("hasAuthority('UPMS_ROLE_READ')")
    @GetMapping("/{id}")
    public Employee findById(@PathVariable("id") Long id, @ApiIgnore Principal principal,
                             @ApiIgnore @CurrentUser UserPrincipal userPrincipal) {
        LOGGER.info("Employee find: id={}", id);
        return repository.findById(id);
    }

    @GetMapping("/")
    public List<Employee> findAll(@ApiIgnore Principal principal) {
        httpServletRequest.getHeader("Authorization");
        httpServletRequest.getHeader("test");
        LOGGER.info("Employee find");
        return repository.findAll();
    }

    @GetMapping("/department/{departmentId}")
    public List<Employee> findByDepartment(@PathVariable("departmentId") Long departmentId) {
        LOGGER.info("Employee find: departmentId={}", departmentId);
        return repository.findByDepartment(departmentId);
    }

    @GetMapping("/organization/{organizationId}")
    public List<Employee> findByOrganization(@PathVariable("organizationId") Long organizationId) {
        LOGGER.info("Employee find: organizationId={}", organizationId);
        return repository.findByOrganization(organizationId);
    }

    @GetMapping("all")
    @ApiImplicitParams({@ApiImplicitParam(
            name = "page", dataType = "integer", paramType = "query",
            value = "Results page you want to retrieve (0..N)", defaultValue = "0"),
            @ApiImplicitParam(
                    name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.", defaultValue = "20"),
    })
    public OpenApiWithPageResponse<String> getWarehouses(
            @RequestParam(value = "name", required = false) String name,
            @ApiIgnore Pageable pageable) {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            strings.add(UUID.randomUUID().toString());
        }
        return new OpenApiWithPageResponse<>(new PageWrapper<>(strings, pageable, strings.size()));
    }
}

package com.syphan.practice.employee.util;

import com.syphan.practice.employee.service.EmployeeService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@GRpcService
@Slf4j
public class Utils {

    @Autowired
    EmployeeService employeeService;

    @PostConstruct
    public void init() {
        try {
            log.info("Starting grpc server");
            Server server = ServerBuilder.forPort(9089).addService(employeeService).build(); // create a instance of server

            server.start();
            log.info("Server Started at " + server.getPort());
        } catch (Exception e) {
            log.error("Exception: ", e);
        }
    }
}

package com.syphan.practice.department.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    private Long id;
    private String name;
    private int age;
    private String position;

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", position=" + position + "]";
    }

}



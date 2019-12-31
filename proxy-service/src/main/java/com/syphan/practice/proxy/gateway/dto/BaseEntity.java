package com.syphan.practice.proxy.gateway.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
public class BaseEntity implements Serializable {

    private Integer id;

    private Integer version;

    private Timestamp createAt;

    private Timestamp updateAt;
}

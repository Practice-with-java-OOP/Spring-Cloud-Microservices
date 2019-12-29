package com.syphan.practice.auth.dto;

import com.syphan.practice.auth.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleCreateDto extends BaseDto {
    @NotBlank(message = "name.must.not.be.blank")
    private String name;

    @NotBlank(message = "code.must.not.be.blank")
    private String code;

    private List<Integer> permissionIds;
}

package com.songchengzhong.iot_service.view_model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Created by songchengzhong on 2017/1/3.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUser {

    @NotNull
    @Length(min = 4, max = 16)
    private String username;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Length(min = 6, max = 32)
    private String password;
}

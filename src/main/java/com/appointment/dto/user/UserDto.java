package com.appointment.dto.user;

import com.appointment.common.constants.Constants;
import com.appointment.common.dto.AuditInfoDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.appointment.common.dto.validation.groups.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.sql.Update;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto extends AuditInfoDto {

    public UserDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }

//    @NotNull(groups = {CreateTask.class}, message = "user id cannot be null")
    private Long id;

    @NotBlank(groups = {Create.class}, message = "username cannot be null")
    @Size(groups = {Create.class}, min = Constants.DEFAULT_MIN_SIZE, max = Constants.DEFAULT_MAX_SIZE, message = "username has invalid size")
    private String username;

    @Size(groups = {Create.class, Update.class, UpdateOwnData.class}, max = Constants.DEFAULT_MAX_SIZE, message = "email has invalid size")
    @Email(groups = {Create.class, Update.class, UpdateOwnData.class}, message = "Invalid email format")
    private String email;

    @NotBlank(groups = {Create.class, Update.class, UpdateOwnData.class}, message = "full name cannot be null")
    @Size(groups = {Create.class, Update.class, UpdateOwnData.class}, min = Constants.DEFAULT_MIN_SIZE, max = Constants.DEFAULT_MAX_SIZE, message = "full name has invalid size")
    private String fullName;

    // Only For Creation/Update
    @NotBlank(groups = {Create.class, ResetPassword.class}, message = "password cannot be null")
    @Size(groups = {Create.class, ResetPassword.class}, min = Constants.DEFAULT_MIN_SIZE, max = Constants.PASSWORD_MAX_SIZE, message = "password has invalid size")
    private String password;

    // response only
    private Boolean isSuperAdmin;

    private Boolean isDeactivated;
}
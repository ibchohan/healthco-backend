package com.appointment.factory;

import com.appointment.common.constants.Constants;
import com.appointment.common.utils.SecureRandomGenerator;
import com.appointment.dto.user.UserDto;
import com.appointment.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserFactory extends BaseFactory<User, UserDto> {


    @Override
    public UserDto buildDto(User input) {
        return UserDto.builder()
                .id(input.getId())
                .uuid(Constants.USER_UUID_PREFIX + input.getUuid())
                .username(input.getUsername())
                .email(input.getEmail())
                .fullName(input.getFullName())
                .isSuperAdmin(input.getIsSuperAdmin())
                .isDeactivated(input.isDeactivated())
                // audit info
                .createdAt(input.getCreatedAt().toEpochMilli())
                .updatedAt(input.getUpdatedAt().toEpochMilli())
                .createdBy(input.getCreatedBy())
                .modifiedBy(input.getModifiedBy())
                .build();
    }

    public UserDto buildMinimalDto(User input) {
        return UserDto.builder()
                .id(input.getId())
                .uuid(Constants.USER_UUID_PREFIX + input.getUuid())
                .username(input.getUsername())
                .fullName(input.getFullName())
                .build();
    }

    @Override
    public User buildEntity(UserDto input) {
        return User.builder()
                .uuid(SecureRandomGenerator.generateUUID())
                .username(input.getUsername())
                .email(input.getEmail())
                .fullName(input.getFullName())
                .build();
    }

    public UserDto buildDtoWithoutIdentifier(String name) {
        return UserDto.builder()
                .fullName(name)
                .build();
    }

}

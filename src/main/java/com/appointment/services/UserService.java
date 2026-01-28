package com.appointment.services;

import com.appointment.common.exceptions.ClientException;
import com.appointment.common.exceptions.EntityNotFoundException;
import com.appointment.common.exceptions.ResourceAlreadyExistsException;
import com.appointment.common.dto.reports.TotalUsersDto;
import com.appointment.dto.user.GetUserRequestDto;
import com.appointment.dto.user.UserDto;
import com.appointment.entities.User;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserService {

    // This function is to only be used for global handling of user ( auth, security etc )
    Optional<User> findUserByUsername(String username);

    Page<User> findUsers(GetUserRequestDto getUserRequestDto);

    User findUser(Long userId) throws EntityNotFoundException;

    User findById(Long userId) throws EntityNotFoundException;

    User findLoggedInUser();

    User create(UserDto userCreationDto) throws ResourceAlreadyExistsException, EntityNotFoundException, ClientException;

    User update(UserDto userUpdateDto) throws EntityNotFoundException, ResourceAlreadyExistsException, ClientException;

    void resetPassword(UserDto resetPasswordDto) throws EntityNotFoundException;

    void delete(Long userId) throws ClientException, EntityNotFoundException;

    void activateUser(Long userId) throws ClientException, EntityNotFoundException;

    void deactivateUser(Long userId) throws ClientException, EntityNotFoundException;

    TotalUsersDto countUsers();

}

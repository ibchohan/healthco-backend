package com.appointment.controller;

import com.appointment.common.dto.validation.groups.Create;
import com.appointment.common.dto.validation.groups.ResetPassword;
import com.appointment.common.dto.validation.groups.Update;
import com.appointment.common.dto.validation.groups.UpdateOwnData;
import com.appointment.common.exceptions.ClientException;
import com.appointment.common.exceptions.EntityNotFoundException;
import com.appointment.common.exceptions.ResourceAlreadyExistsException;
import com.appointment.common.utils.PaginationAndSortingHandler;
import com.appointment.common.utils.UserContext;
import com.appointment.dto.user.GetUserRequestDto;
import com.appointment.dto.user.UserDto;
import com.appointment.factory.UserFactory;
import com.appointment.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.appointment.common.constants.Route.USER_RESOURCE_URL;

@RestController
@RequestMapping(value = {USER_RESOURCE_URL})
@Slf4j
@AllArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;
    private final UserFactory userFactory;

    @GetMapping("/all")
    public ResponseEntity<Page<UserDto>> findUsers(GetUserRequestDto getUserRequestDto) {
        return ResponseEntity.ok(
                PaginationAndSortingHandler.buildPaginatedResponse(
                        userService.findUsers(getUserRequestDto),
                        userFactory::buildDtoList
                )
        );
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> findLoggedInUser() {
        return ResponseEntity.ok(
                userFactory.buildDto(
                        userService.findLoggedInUser()
                )
        );
    }

    @PostMapping
    public ResponseEntity<UserDto> createNewUser(@RequestBody @Validated({Create.class}) UserDto userCreationDto)
            throws ResourceAlreadyExistsException, EntityNotFoundException, ClientException {
        return ResponseEntity.ok(
                userFactory.buildDto(
                        userService.create(userCreationDto)
                )
        );
    }

    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateLoggedInUser(@RequestBody @Validated({UpdateOwnData.class}) UserDto userUpdateDto)
            throws EntityNotFoundException, ResourceAlreadyExistsException, ClientException {
        userUpdateDto.setId(UserContext.getLoggedInUserDetails().getId());
        return ResponseEntity.ok(
                userFactory.buildDto(
                        userService.update(userUpdateDto)
                )
        );
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId,
                                              @RequestBody @Validated({Update.class}) UserDto userUpdateDto)
            throws EntityNotFoundException, ResourceAlreadyExistsException, ClientException {
        userUpdateDto.setId(userId);
        return ResponseEntity.ok(
                userFactory.buildDto(
                        userService.update(userUpdateDto)
                )
        );
    }

    @PatchMapping("/{userId}/reset-password")
    public ResponseEntity<Void> resetPassword(@PathVariable Long userId,
                                              @RequestBody @Validated({ResetPassword.class}) UserDto resetPasswordDto)
            throws EntityNotFoundException {
        resetPasswordDto.setId(userId);
        userService.resetPassword(resetPasswordDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId)
            throws EntityNotFoundException, ClientException {
        userService.delete(userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{userId}/activate")
    public ResponseEntity<Void> activateUser(@PathVariable Long userId)
            throws EntityNotFoundException, ClientException {
        userService.activateUser(userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{userId}/deactivate")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long userId)
            throws EntityNotFoundException, ClientException {
        userService.deactivateUser(userId);
        return ResponseEntity.ok().build();
    }

}

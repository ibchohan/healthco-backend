package com.appointment.services.impl;

import com.appointment.common.enums.ResourceType;
import com.appointment.common.exceptions.ClientException;
import com.appointment.common.exceptions.EntityNotFoundException;
import com.appointment.common.exceptions.ResourceAlreadyExistsException;
import com.appointment.common.utils.PaginationAndSortingHandler;
import com.appointment.common.dto.reports.TotalUsersDto;
import com.appointment.common.event.BusinessEventPublisher;
import com.appointment.common.utils.UserContext;
import com.appointment.dto.user.GetUserRequestDto;
import com.appointment.dto.user.UserDto;
import com.appointment.entities.User;
import com.appointment.factory.UserFactory;
import com.appointment.repositories.UserRepository;
import com.appointment.repositories.specifications.UserSpecification;
import com.appointment.services.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserFactory userFactory;
    private final PasswordEncoder passwordEncoder;

    private final BusinessEventPublisher businessEventPublisher;

    public UserServiceImpl(UserRepository userRepository, UserFactory userFactory, @Lazy PasswordEncoder passwordEncoder,
                           BusinessEventPublisher businessEventPublisher) {
        this.userRepository = userRepository;
        this.userFactory = userFactory;
        this.passwordEncoder = passwordEncoder;
        this.businessEventPublisher = businessEventPublisher;
    }

    private Optional<User> findUserByUsernameExcept(String username, Long userId) {
        return userRepository.findByUsernameAndIdNot(username, userId);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Page<User> findUsers(GetUserRequestDto getUserRequestDto) {

        Specification<User> specification = Specification
                .where(UserSpecification.searchQueryPartialMatches(getUserRequestDto.getSearchQuery()));
        if (getUserRequestDto.getIsDeactivated() != null) {
            specification = specification.and(UserSpecification.isDeactivatedEquals(getUserRequestDto.getIsDeactivated()));
        }
        Pageable pageable = PaginationAndSortingHandler.getPage(getUserRequestDto);
        return userRepository.findAll(specification, pageable);
    }

    @Override
    public User findUser(Long userId) {
        return null;
    }

    @Override
    public User findById(Long userId) throws EntityNotFoundException {
        return userRepository
                .findById(userId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public User findLoggedInUser() {
        return UserContext.getLoggedInUserDetails();
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public User create(UserDto userCreationDto) throws ResourceAlreadyExistsException, ClientException {
        checkIfUserExistsExplosive(userCreationDto.getUsername());
        User user = userFactory.buildEntity(userCreationDto);
        user.setPassword(passwordEncoder.encode(userCreationDto.getPassword()));
        user = save(user);

        businessEventPublisher.publishResourceCreationEvent(user.getId(), user.getFullName(), ResourceType.USER);
        return user;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public User update(UserDto userUpdateDto) throws EntityNotFoundException, ResourceAlreadyExistsException, ClientException {
        User user = findUser(userUpdateDto.getId());
        checkIfUserExistsExceptExplosive(userUpdateDto.getUsername(), userUpdateDto.getId());
        user.update(userUpdateDto);
        user = save(user);

        boolean userIsUpdatingOwnDetails = user.getId().equals(UserContext.getLoggedInUserDetails().getId());

        if (!userIsUpdatingOwnDetails) {
            User loggedInUser = UserContext.getLoggedInUserDetails();
            } else {
                if (user.getIsSuperAdmin()) {
                    throw new ClientException("Super admin can update another super admin's details");
                }
        }

        businessEventPublisher.publishResourceUpdateEvent(user.getId(), user.getFullName(), ResourceType.USER);
        return user;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void resetPassword(UserDto resetPasswordDto) throws EntityNotFoundException {
        User user = findUser(resetPasswordDto.getId());
        user.updatePassword(passwordEncoder.encode(resetPasswordDto.getPassword()));
        save(user);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void delete(Long userId) throws ClientException, EntityNotFoundException {

        if (userId.equals(UserContext.getLoggedInUserDetails().getId())) {
            log.error("User( id: {} ) cannot be as they are logged in", userId);
            throw new ClientException("Logged in user cannot be deleted");
        }

        User user = findUser(userId);

        if (user.getIsSuperAdmin()) {
            log.error("Super admin user ( id: {} ) cannot be deleted", userId);
            throw new ClientException("Super admin user cannot be deleted");
        }

//        if (userRepository.isUserLinkedToAnyResource(userId)) {
//            log.error("User ( id: {} , name: {} ) is linked to other resources and cannot be deleted", user.getId(), user.getFullName());
//            throw new ClientException("User is linked to other resources and cannot be deleted");
//        }

        userRepository.delete(user);
        businessEventPublisher.publishResourceDeletionEvent
                (user.getId(), user.getFullName(), ResourceType.USER);
    }

    @Override
    public TotalUsersDto countUsers() {
        Long totalUsers = userRepository.count();
        return TotalUsersDto.builder().totalUsers(
                totalUsers == null ? 0 : totalUsers
        ).build();
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void activateUser(Long userId) throws ClientException, EntityNotFoundException {
        if (userId.equals(UserContext.getLoggedInUserDetails().getId())) {
            log.error("User( id: {} ) cannot be activated as they are logged in", userId);
            throw new ClientException("Logged in user cannot be activated");
        }
        User user = findUser(userId);
        if (user.getIsSuperAdmin()) {
            log.error("Super admin user ( id: {} ) cannot be activated", userId);
            throw new ClientException("Super admin user cannot be activated");
        }
        user.activateUser();
        userRepository.save(user);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void deactivateUser(Long userId) throws ClientException, EntityNotFoundException {
        if (userId.equals(UserContext.getLoggedInUserDetails().getId())) {
            log.error("User( id: {} ) cannot be deactivated as they are logged in", userId);
            throw new ClientException("Logged in user cannot be deactivated");
        }
        User user = findUser(userId);
        if (user.getIsSuperAdmin()) {
            log.error("Super admin user ( id: {} ) cannot be deactivated", userId);
            throw new ClientException("Super admin user cannot be deactivated");
        }
        user.deactivateUser();
        userRepository.save(user);
    }

    @Transactional(rollbackOn = Exception.class)
    private User save(User user) {
        return userRepository.save(user);
    }

    private void checkIfUserExistsExplosive(String username) throws ResourceAlreadyExistsException {
        Optional<User> userOptional = this.findUserByUsername(username);
        if (userOptional.isPresent()) {
            log.warn("User ( id: {} , username: {} ) already exists with this username ( {} )",
                    userOptional.get().getId(), userOptional.get().getUsername(), userOptional.get().getUsername());
            throw new ResourceAlreadyExistsException("A user already exists with the given username");
        }
    }

    private void checkIfUserExistsExceptExplosive(String username, Long userId) throws ResourceAlreadyExistsException {
        Optional<User> userOptional = this.findUserByUsernameExcept(username, userId);
        if (userOptional.isPresent()) {
            log.warn("User ( id: {} , username: {} ) already exists with this username ( {} )",
                    userOptional.get().getId(), userOptional.get().getUsername(), userOptional.get().getUsername());
            throw new ResourceAlreadyExistsException("A user already exists with the given username");
        }
    }
}

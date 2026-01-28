package com.appointment.common.event;

import com.appointment.common.enums.ResourceType;
import com.appointment.common.event.resource.ResourceCreationEvent;
import com.appointment.common.event.resource.ResourceDeletionEvent;
import com.appointment.common.event.resource.ResourceUpdateEvent;
import com.appointment.common.event.user.UserLoginEvent;
import com.appointment.common.event.user.UserLogoutEvent;
import com.appointment.common.event.user.UserTokenRefreshEvent;
import com.appointment.common.utils.UserContext;
import com.appointment.entities.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class BusinessEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Async
    public void publishUserLoginEvent(User user) {
        UserLoginEvent event = UserLoginEvent.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .build();
        applicationEventPublisher.publishEvent(event);
    }

    @Async
    public void publishUserLogoutEvent(User user) {
        UserLogoutEvent event = UserLogoutEvent.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .build();
        applicationEventPublisher.publishEvent(event);
    }

    @Async
    public void publishTokenRefreshEvent(User user) {
        UserTokenRefreshEvent event = UserTokenRefreshEvent.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .build();
        applicationEventPublisher.publishEvent(event);
    }

    @Async
    public void publishResourceCreationEvent(Long resourceId, String resourceName, ResourceType resourceType) {
        ResourceCreationEvent event = ResourceCreationEvent.builder()
                .userId(UserContext.getLoggedInUserDetails().getId())
                .username(UserContext.getLoggedInUserDetails().getUsername())
                .resourceId(resourceId)
                .resourceName(resourceName)
                .resourceType(resourceType)
                .build();
        applicationEventPublisher.publishEvent(event);
    }

    @Async
    public void publishResourceUpdateEvent(Long resourceId, String resourceName, ResourceType resourceType) {
        ResourceUpdateEvent event = ResourceUpdateEvent.builder()
                .userId(UserContext.getLoggedInUserDetails().getId())
                .username(UserContext.getLoggedInUserDetails().getUsername())
                .resourceId(resourceId)
                .resourceName(resourceName)
                .resourceType(resourceType)
                .build();
        applicationEventPublisher.publishEvent(event);
    }

    @Async
    public void publishResourceDeletionEvent(Long resourceId, String resourceName, ResourceType resourceType) {
        ResourceDeletionEvent event = ResourceDeletionEvent.builder()
                .userId(UserContext.getLoggedInUserDetails().getId())
                .username(UserContext.getLoggedInUserDetails().getUsername())
                .resourceId(resourceId)
                .resourceName(resourceName)
                .resourceType(resourceType)
                .build();
        applicationEventPublisher.publishEvent(event);
    }

}

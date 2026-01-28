package com.appointment.common.utils;

import com.appointment.common.constants.MdcConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j(topic = "LogContextFilter")
public class LogContextFilter extends OncePerRequestFilter {

    // TODO: CHeck why UserContext.getLoggedInUserDetails() is null
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (!CommonUtils.isNull(UserContext.getLoggedInUserDetails())) {
            MDC.put(MdcConstants.MDC_USER_ID_KEY, String.valueOf(UserContext.getLoggedInUserDetails().getId()));
        }
        filterChain.doFilter(request, response);
    }
}
package com.sehrish.accidentdetect.interceptor;

import com.sehrish.accidentdetect.SessionHelper;
import com.sehrish.accidentdetect.entity.HospitalUser;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {

        HospitalUser hospitalUser = SessionHelper.getLoggedInHospital();
        if(hospitalUser != null) {
            return true;
        }
        response.sendRedirect("/login");
        return false;
    }
}

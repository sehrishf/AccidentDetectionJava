package com.sehrish.accidentdetect;

import com.sehrish.accidentdetect.entity.HospitalUser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

public class SessionHelper {

    public static HttpSession getSession() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attributes.getRequest().getSession();
    }

    public static HospitalUser getLoggedInHospital() {
        HospitalUser hospitalUser = (HospitalUser) getSession().getAttribute("hospitalUser");
        return hospitalUser;
    }
}

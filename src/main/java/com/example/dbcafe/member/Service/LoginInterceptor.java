package com.example.dbcafe.member.Service;

import org.apache.catalina.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        HttpSession session = request.getSession();
        String memberEmail = (String)request.getSession().getAttribute("userId");

        //exclude-mapping 대신, 요청된 url
        String requestUrl = request.getRequestURL().toString();

        //하단의 Url 체크를 통해, login 페이지는 예외처리를 해줘야 무한 리디렉션에서 벗어날 수 있다
        if(requestUrl.contains("/test/save")){
            return true;
        }

        if(requestUrl.contains("/test/login")){
            return true;
        }




        //로그인 안됨
        //if(memberEmail == null) {
        if(session.getAttribute("loginEmail") == null){
            request.setAttribute("message", "등록된 회원이 아닙니다.");
            request.setAttribute("path", "/login");
            //RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/memberAccess.jsp");
            //view.forward(request, response);
            response.sendRedirect("/test/login");
            return false;
            //로그인 되어있음
        }else {
            return true;
        }
    }
}
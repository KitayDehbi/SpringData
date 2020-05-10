package com.master4.Filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {


        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse)response;
        boolean loggedUser = req.getSession().getAttribute("roles") !=null;

        List<String> roles =  (List<String>)req.getSession().getAttribute("roles");

        String path = ((HttpServletRequest) request).getServletPath();

        boolean saveUserRequested = path != null && path.length()> 0 && path.equals("/add");
        boolean updateUserRested = path != null && path.length() > 0 && path.equals("/add/{id}");
        boolean deleteUserRested = path != null && path.length() > 0 && path.equals("/delete/{page}/{id}");

        if(!roles.contains("admin")){
            System.out.println("1");
            res.sendRedirect(req.getContextPath()+"/user");
        }
        else if(!loggedUser){
            System.out.println("2");
            res.sendRedirect(req.getContextPath()+"/");
        }
        else{
            ((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getRequestURI());
        }
    }

}
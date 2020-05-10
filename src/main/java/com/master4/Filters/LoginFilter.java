package com.master4.Filters;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.err.println("============== test filter ========");
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            boolean isConnected = req.getSession().getAttribute("user")!=null;
            String Path = req.getServletPath();
            System.out.println(isConnected);
            if (!isConnected){
                 res.sendRedirect(req.getContextPath()+"/");
            }else{
                chain.doFilter(request,response);
            }

        } catch (Exception ex) {
            request.setAttribute("errorMessage", ex);
            request.getRequestDispatcher("/WEB-INF/views/errors/authorization.jsp")
                    .forward(request, response);
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}


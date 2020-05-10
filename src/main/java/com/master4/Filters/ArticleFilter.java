package com.master4.Filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ArticleFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            boolean isConnected = req.getSession().getAttribute("user")!=null;
            List<String> roles = (List<String>) req.getSession().getAttribute("roles");
            List<Long> article = (List<Long>) req.getSession().getAttribute("articles");
            String Path = req.getServletPath();
            String[] action =Path.split("/");
            System.out.println(isConnected);
            if (!isConnected ){
                res.sendRedirect(req.getContextPath()+"/");
            }else if(isConnected && roles.contains("writter") && !action[1].equals("article")){
                res.sendRedirect(req.getContextPath()+"/article");
            }
            else if(isConnected && roles.contains("writter") && action[1].equals("article") && action[3]!=null){
                if (!article.contains(Long.parseLong(action[4])))
                res.sendRedirect(req.getContextPath()+"/article");
            }
            else{
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

package org.example;

//import javax.servlet.*;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
@WebServlet("/calculate") // urlPath로 "/calculate"가 들어왔을 때 이 서블릿을 실행하도록 해.
public class CalculatorServlet implements Servlet {
    private static final Logger logger = LoggerFactory.getLogger(CalculatorServlet.class);
    @Override
    public void init(ServletConfig config) throws ServletException {
        logger.info("init");

    }
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        logger.info("service");
    }

    @Override
    public void destroy() {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }



    @Override
    public String getServletInfo() {
        return null;
    }

}

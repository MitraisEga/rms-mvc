package com.mitrais.rms.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mitrais.rms.dao.UserDao;
import com.mitrais.rms.dao.impl.UserDaoImpl;
import com.mitrais.rms.model.User;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

@WebServlet("/login")
public class LoginServlet extends AbstractController
{
	Logger logger = Logger.getLogger(this.getClass().getName());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String path = getTemplatePath(req.getServletPath());
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(path);
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
    	UserDao userDao = UserDaoImpl.getInstance();
    	Optional<User> user = userDao.findByUserAndPass(req.getParameter("username"), req.getParameter("userpass"));
    	if(user.isPresent()) {
    		//login success
    		logger.info("LOGIN SUCCESS");
    		
    		HttpSession session = req.getSession(true);
    		session.setAttribute("Login", true);
    		session.setAttribute("User", user.get());
    		
    		logger.info(session.toString());
    		
            resp.sendRedirect("");
    		
    	}else {
    		logger.info("LOGIN FAILED");
    		doGet(req, resp);
    	}
    }
}

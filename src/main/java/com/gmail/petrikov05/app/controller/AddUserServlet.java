package com.gmail.petrikov05.app.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gmail.petrikov05.app.service.UserService;
import com.gmail.petrikov05.app.service.impl.UserServiceImpl;
import com.gmail.petrikov05.app.service.model.AddUserDTO;
import com.gmail.petrikov05.app.service.model.AddedUserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddUserServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");
        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        String active = req.getParameter("active");
        String strAge = req.getParameter("age");
        String address = req.getParameter("address");
        String telephone = req.getParameter("telephone");

        AddUserDTO addUserDTO = new AddUserDTO();
        addUserDTO.setUserName(userName);
        addUserDTO.setPassword(password);
        addUserDTO.setAddress(address);
        addUserDTO.setTelephone(telephone);
        boolean isActive = Boolean.parseBoolean(active);
        addUserDTO.setIsActive(isActive);

        try {
            int age = Integer.parseInt(strAge);
            addUserDTO.setAge(age);
            AddedUserDTO addedUserDTO = userService.add(addUserDTO);
            try (PrintWriter out = resp.getWriter()) {
                out.println("<html><body>");
                out.println("user added: " + addedUserDTO);
                out.println("</body></html>");
            }
        } catch (NumberFormatException | IOException e) {
            logger.error("Incorrect format", e);
        }
    }

}

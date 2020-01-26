package com.gmail.petrikov05.app.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gmail.petrikov05.app.service.UserService;
import com.gmail.petrikov05.app.service.impl.UserServiceImpl;
import com.gmail.petrikov05.app.service.model.AddUserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateUserServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        resp.setContentType("text/html");
        AddUserDTO updateUserDTO = new AddUserDTO();
        updateUserDTO.setAddress(req.getParameter("address"));
        String strId = req.getParameter("id");

        try {
            int id = Integer.parseInt(strId);
            updateUserDTO.setId(id);
            int affectedRows = userService.updateAddressById(updateUserDTO);
            try (PrintWriter out = resp.getWriter()) {
                out.println("<html><body>");
                if (affectedRows != 0) {
                    out.println("Address of user with id = " + id + " update");
                } else {
                    out.println("Address of user with id  = " + id + "  not update");
                }
                out.println("</body></html>");
            }
        } catch (NumberFormatException | IOException e) {
            logger.error("Incorrect format", e);
        }
    }

}

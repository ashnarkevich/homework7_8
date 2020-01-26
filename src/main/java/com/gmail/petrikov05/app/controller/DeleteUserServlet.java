package com.gmail.petrikov05.app.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gmail.petrikov05.app.service.UserService;
import com.gmail.petrikov05.app.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteUserServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");
        String strId = req.getParameter("id");
        try {
            int id = Integer.parseInt(strId);
            int affectedRows = userService.deleteById(id);
            try (PrintWriter out = resp.getWriter()) {
                out.println("<html><body>");
                if (affectedRows != 0) {
                    out.println("User with id = " + id + " deleted");
                } else {
                    out.println("User with id  = " + id + "  not deleted");
                }
                out.println("</body></html>");
            }
        } catch (NumberFormatException | IOException e) {
            logger.error("Incorrect format", e);
        }
    }

}

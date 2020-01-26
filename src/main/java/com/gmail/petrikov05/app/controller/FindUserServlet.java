package com.gmail.petrikov05.app.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gmail.petrikov05.app.service.UserService;
import com.gmail.petrikov05.app.service.impl.UserServiceImpl;
import com.gmail.petrikov05.app.service.model.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FindUserServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        resp.setContentType("text/html");
        List<UserDTO> userDTOList = userService.getListUser();
        try (PrintWriter out = resp.getWriter()) {
            out.println("<html><body>");
            if (!userDTOList.isEmpty()) {
                for (UserDTO userDTO : userDTOList) {
                    out.println("<p>" + userDTO + "</p>");
                }
            } else {
                out.println("<p>No users currently</p>");
            }
            out.println("</body></html>");
        } catch (IOException e) {
            logger.error("Error in output stream", e);
        }

    }

}

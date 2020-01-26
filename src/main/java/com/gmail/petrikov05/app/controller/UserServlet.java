package com.gmail.petrikov05.app.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.gmail.petrikov05.app.service.TableService;
import com.gmail.petrikov05.app.service.impl.TableServiceImpl;

public class UserServlet extends HttpServlet {

    private TableService tableService = TableServiceImpl.getInstance();

    @Override
    public void init() throws ServletException {
        super.init();
        tableService.dropAllTable();
        tableService.createAllTables();
    }

}
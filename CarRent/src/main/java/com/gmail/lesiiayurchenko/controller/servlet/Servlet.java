package com.gmail.lesiiayurchenko.controller.servlet;

import com.gmail.lesiiayurchenko.controller.Command.*;
import com.gmail.lesiiayurchenko.model.service.AccountService;
import com.gmail.lesiiayurchenko.model.service.BookingService;
import com.gmail.lesiiayurchenko.model.service.CarService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.log4j.Logger;

@WebServlet(name = "Servlet", value = "/")
public class Servlet extends HttpServlet {
    private Map<String, Command> commands = new HashMap<>();
    private static final Logger log = Logger.getLogger(Servlet.class);

    public void init(ServletConfig servletConfig){
        ServletContext servletContext = servletConfig.getServletContext();
        servletContext.setAttribute("loggedAccounts", new HashSet<String>());
        log.trace("Set loggedAccounts");
        initCommandsContainer(servletContext);
        log.debug("Servlet initialization finished");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
        //response.getWriter().print("Hello from servlet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log.debug("Controller starts");
        String path = request.getRequestURI();
        log.trace("Request parameter: URI --> " + path);
        path = path.replaceAll(".*/carrent/" , "");
        Command command = commands.getOrDefault(path ,
                (r)->"/index.jsp");
        log.trace("Request parameter: command --> " + command);
        String page = command.execute(request);
        log.trace("Request parameter: forward address --> " + page);
        log.debug("Servlet finished, now go to forward address --> " + page);
        if(page.contains("redirect:")){
            response.sendRedirect(page.replace("redirect:", "/carrent"));
        }else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }

    private void initCommandsContainer (ServletContext servletContext) {
        commands.put("logout", new LogOutCommand());
        commands.put("login", new LoginCommand());
        commands.put("registration", new RegistrationCommand(new AccountService()));
        commands.put("exception", new ExceptionCommand());
        commands.put("main", new MainCommand(new CarService()));
        commands.put("admincars", new AdminCarsCommand(new CarService()));
        commands.put("admincustomers", new AdminCustomersCommand(new AccountService()));
        commands.put("adminmanagers", new AdminManagersCommand(new AccountService()));
        commands.put("adminchanges", new AdminChangesCommand(new CarService(), new AccountService()));
        commands.put("managernewbookings", new ManagerNewBookingsCommand(new BookingService()));
        commands.put("managerusebookings", new ManagerUseBookingsCommand(new BookingService()));
        commands.put("managerreturnedbookings", new ManagerReturnedBookingsCommand(new BookingService()));
        commands.put("managerchanges", new ManagerChangesCommand(new BookingService()));
        commands.put("customerbasis", new CustomerBasisCommand(new CarService()));
        commands.put("customeraccount", new CustomerAccountCommand(new AccountService()));
        commands.put("customerbook", new CustomerBookCommand(new CarService(), new BookingService(), new AccountService()));
        commands.put("customerbookings", new CustomerBookingsCommand(new BookingService()));

        log.trace("Set commands --> " + commands);
    }
}

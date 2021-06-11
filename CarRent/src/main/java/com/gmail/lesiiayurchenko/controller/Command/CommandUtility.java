package com.gmail.lesiiayurchenko.controller.Command;

import com.gmail.lesiiayurchenko.model.entity.Account;
import com.gmail.lesiiayurchenko.model.entity.Car;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

class CommandUtility {
    static void putInfoIntoSession(HttpServletRequest request,
                                      Account.Role role, String login, int id) {
        HttpSession session = request.getSession();
        session.setAttribute("accountLogin", login);
        session.setAttribute("role", role);
        session.setAttribute("id", id);
        session.setAttribute("carFilter", "none");
        session.setAttribute("carSorter", "none");
        session.setAttribute("qualityClass", "none");
        if (Account.Role.CUSTOMER.equals(role)){
            List<Car> cars = new ArrayList<>();
            session.setAttribute("cars", cars);
        }
    }

    static boolean putCarIntoSession(HttpServletRequest request, Car car) {
        List<Car> cars = (ArrayList<Car>) request.getSession().getAttribute("cars");
        if(cars.stream().anyMatch(car::equals)){
            return false;
        }
        cars.add(car);
        request.getSession().setAttribute("cars", cars);
        return true;
    }

    static boolean removeCarFromSession(HttpServletRequest request, Car car) {
        List<Car> cars = (ArrayList<Car>) request.getSession().getAttribute("cars");
        if(cars.stream().noneMatch(car::equals)){
            return false;
        }
        cars.remove(car);
        request.getSession().setAttribute("cars", cars);
        return true;
    }

    static boolean checkAccountIsLogged(HttpServletRequest request, String login){
        HashSet<String> loggedAccounts = (HashSet<String>) request.getSession().getServletContext()
                .getAttribute("loggedAccounts");
        HttpSession session = request.getSession();
        session.removeAttribute("accountLogin");
        session.removeAttribute("role");
        session.removeAttribute("id");
        session.removeAttribute("cars");
        session.removeAttribute("carFilter");
        session.removeAttribute("carSorter");
        session.removeAttribute("carQualityClass");
        if(loggedAccounts.stream().anyMatch(login::equals)){
            return true;
        }
        loggedAccounts.add(login);
        request.getSession().getServletContext()
                .setAttribute("loggedAccounts", loggedAccounts);
        return false;
    }

    static void deleteAccountFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("accountLogin");
        session.removeAttribute("role");
        session.removeAttribute("id");
        session.removeAttribute("cars");
        session.removeAttribute("carFilter");
        session.removeAttribute("carSorter");
        session.removeAttribute("carQualityClass");
    }

    static void deleteAccountFromContext(HttpServletRequest request) {
        HashSet<String> loggedAccounts = (HashSet<String>) request.getSession().getServletContext()
                .getAttribute("loggedAccounts");
        loggedAccounts.remove(String.valueOf(request.getSession().getAttribute("accountLogin")));
        request.getSession().getServletContext()
                .setAttribute("loggedAccounts", loggedAccounts);
    }
}

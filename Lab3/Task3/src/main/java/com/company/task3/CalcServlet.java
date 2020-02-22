package com.company.task3;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CalcServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        double first = Double.valueOf(request.getParameter("first"));
        double second = Double.valueOf(request.getParameter("second"));
        String result;

        String button = request.getParameter("button");
        if ("sum".equals(button)) {
            double sum = first + second;
            result = first + " + " + second + " = " + sum;
        } else if ("minus".equals(button)) {
            double minus = first - second;
            result = first + " - " + second + " = " + minus;
        } else if ("div".equals(button)) {
            double div = first / second;
            result = first + " / " + second + " = " + div;
        } else if ("mult".equals(button)) {
            double mult = first * second;
            result = first + " * " + second + " = " + mult;
        } else {
            result = "error: last else";
        }

        request.setAttribute("first", first);
        request.setAttribute("second", second);
        request.setAttribute("result", result);

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}

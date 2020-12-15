/*
 * Author: jianqing
 * Date: Dec 14, 2020
 * Description: This document is created for
 */
package com.classchat.luckybaud.controller;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import com.classchat.luckybaud.modal.Alerts;
import com.classchat.luckybaud.modal.BBAssignment;
import com.classchat.luckybaud.modal.BBUser;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jianqing
 */
@WebServlet(name = "Servlet", urlPatterns =
{
    "/CheckGrade", "/index"
})
public class Servlet extends HttpServlet
{

    public static final String SCHEMA_URL = "https://bostontrinity.myschoolapp.com";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter())
        {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Servlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Servlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String path = request.getServletPath();
        if (path.equals("/index"))
        {
            request.getRequestDispatcher(path + ".jsp").forward(request, response);
        } else
        {
            processRequest(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String path = request.getServletPath();
        if (path.equals("/CheckGrade"))
        {
            processCheckGradePOST(request, response);
        } else
        {
            processRequest(request, response);
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>

    protected void processCheckGradePOST(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String password = request.getParameter("password");
        String username = request.getParameter("username");
        String link = request.getParameter("assignmentlink");
        String demo = request.getParameter("demo");
        HttpSession session = request.getSession();
        boolean success = false;
        String errorMsg;

        //check for empty imputs
        if (!StrUtil.isBlank(demo))
        {
            try
            {
                int max = Integer.parseInt(request.getParameter("max"));
                int grade = Integer.parseInt(request.getParameter("grade"));
                String assName = request.getParameter("name");
                if (max >= grade)
                {
                    if (max > 0 && grade >= 0)
                    {
                        errorMsg = null;
                        BBAssignment assignment = new BBAssignment();
                        assignment.setAssignmentName(assName);
                        assignment.setMaxGrade(max);
                        assignment.setGradeEarned(grade);
                        session.setAttribute("assignment", assignment);
                        success = true;
                    }else
                    {
                        errorMsg = Alerts.warningMessage("Positive grade only", "Please only enter positive numbers for your grade!");
                    }

                } else
                {
                    errorMsg = Alerts.warningMessage("Grade Input Problem", "Maximun grade must be greater or equal to real grade");
                }

            } catch (NumberFormatException e)
            {
                errorMsg = Alerts.warningMessage("Number", "Please enter vaild numbers for grades.");
            }

        } else
        {
            if (StrUtil.hasBlank(password, username, link))
            {
                //session.setAttribute("indexMessage", Alerts.infoMessage("Cridentials Missing", "Please enter username, password and assignment link to continue."));
                //response.sendRedirect(request.getContextPath() + "/index");
                //errorTitle = "";
                errorMsg = Alerts.warningMessage("Cridentials Missing", "Please enter username, password and assignment link to continue.");
            } else
            {
                BBUser user = new BBUser(username, password);
                if (user.login())
                {
                    //split a URL string like this,  example URL:
                    //https://bostontrinity.myschoolapp.com/app/student#assignmentdetail/8153613/13133488/0/studentmyday--assignment-center
                    try
                    {
                        //declear useful variables
                        String assignmentName;

                        /*1. dissect the link pasted by user*/
                        String[] seg1 = link.split("#");
                        String[] subPath = seg1[1].split("/");
                        int assignmentId = Integer.parseInt(subPath[1]);
                        int assignmentIndexId = Integer.parseInt(subPath[2]);

                        BBAssignment assignment = user.getStudentAssignment(assignmentId, assignmentIndexId);

                        if (assignment.isGraded())
                        {
                            //assignment is graded, continue
                            session.setAttribute("assignment", assignment);
                            success = true;
                            errorMsg = null;
                        } else
                        {
                            //assignment is not graded. Return error.
                            errorMsg = Alerts.infoMessage("Assignment Not Graded", "The assignment you are looking for, " + assignment.getAssignmentName() + " is not graded yet. Please wait until the assignmnent is graded.");
                        }

                    } catch (IndexOutOfBoundsException | NumberFormatException exx)
                    {
                        //an illegal link was submitted, handle error here.
                        errorMsg = Alerts.infoMessage("Illegal Link is submitted", "Please only submit the link of an assignment.");
                        Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, "IOOOBe / NFE", exx);
                    } catch (IOException ioe)
                    {
                        errorMsg = Alerts.warningMessage("Blackbaud Error", " There is an error on the blackbaud side. Please try again later.<button id='serr' class='transparent'>Show Detail</button> <br><div style='display:none;' id='ex'>" + ExceptionUtil.stacktraceToString(ioe) + "</div>");
                        Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, "IOE", ioe);
                    }
                } else
                {
                    //user fail login
                    errorMsg = Alerts.warningMessage("Login Failed!", user.getErrorMessage());
                    //Load the information from api, which is why the login has failed.
                }
            }
        }

        if (success)
        {
            //push user to next page
            request.getRequestDispatcher("/WEB-INF" + request.getServletPath() + ".jsp").forward(request, response);
        } else
        {
            //send user back to index
            session.setAttribute("indexMessage", errorMsg);
            response.sendRedirect(request.getContextPath() + "/index");
        }

    }

}

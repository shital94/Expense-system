package com.revature.ers.web;

/* 
 * for login session perform by servlet  
 */
import com.revature.ers.beans.Reimbursement; 
import com.revature.ers.beans.ReimbursementType;
import com.revature.ers.beans.User;
import com.revature.ers.exceptions.ExistingUserException;
import com.revature.ers.middle.BusinessDelegate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserController {
    void login (HttpServletRequest req, HttpServletResponse resp) throws IOException,ServletException{
    	//get perameters first
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        
        //here the flow of the login,register and dashboard page when will open and hows everything is work 
        try {
            User user = new BusinessDelegate().login(username,password);
            user.setPassword(null);
            req.getSession().setAttribute("userData",user);
            if(user.getRole().getId()==1){
                List<Reimbursement> pending = new BusinessDelegate().pendingReimbursements();
                req.getSession().setAttribute("reimbursements",pending);
                req.getSession().setAttribute("selectedTab", "pending");
            }
            else{
                List<ReimbursementType> types = new BusinessDelegate().getTypes();
                List<Reimbursement> reimbursements = new BusinessDelegate().allReimbursements(user);
                req.getSession().setAttribute("reimbTypes",types);
                req.getSession().setAttribute("reimbursements",reimbursements);
            }
            req.getRequestDispatcher("dashboard.jsp").forward(req,resp);
        } catch (NullPointerException e){
            req.getSession().setAttribute("message","Invalid username/password combination!");
            req.setAttribute("error",true);
            req.getRequestDispatcher("index.jsp").forward(req,resp);
        }
    }

    
    //get data of register form
    void register(HttpServletRequest req, HttpServletResponse resp) throws IOException,ServletException{
        String username = req.getParameter("new_username");
        String password = req.getParameter("new_password");
        String first_name = req.getParameter("first_name");
        String last_name = req.getParameter("last_name");
        String email = req.getParameter("email");
        int role_id = Integer.parseInt(req.getParameter("role_id"));
        try {
            new BusinessDelegate().register(username,password,first_name,last_name,email,role_id);
            req.setAttribute("message","Successfully registered!");
            req.setAttribute("error",false);
            req.getRequestDispatcher("index.jsp").forward(req,resp);
        } catch (ExistingUserException e) {
            req.setAttribute("message",e.getMessage());
            req.setAttribute("error",false);
            req.getRequestDispatcher("index.jsp").forward(req,resp);
        }
    }
}

package com.revature.ers.web;

import com.revature.ers.beans.Reimbursement; 
import com.revature.ers.beans.ReimbursementStatus;
import com.revature.ers.beans.ReimbursementType;
import com.revature.ers.beans.User;
import com.revature.ers.middle.BusinessDelegate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.net.HttpCookie;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("unused")
public class ReimbursementController {
    public void pending(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Reimbursement> reimbursements = new BusinessDelegate().pendingReimbursements();
        req.getSession().setAttribute("reimbursements", reimbursements);
        req.getSession().setAttribute("selectedTab", "pending");
        req.getRequestDispatcher("dashboard.jsp").forward(req, null);
    }

    public void approved(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Reimbursement> reimbursements = new BusinessDelegate().approvedReimbursements((User) req.getSession().getAttribute("userData"));
        req.getSession().setAttribute("reimbursements", reimbursements);
        req.getSession().setAttribute("selectedTab", "approved");
        req.getRequestDispatcher("dashboard.jsp").forward(req, resp);
    }

    public void declined(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Reimbursement> reimbursements = new BusinessDelegate().declinedReimbursements((User) req.getSession().getAttribute("userData"));
        req.getSession().setAttribute("reimbursements", reimbursements); 
        req.getSession().setAttribute("selectedTab", "declined");
        req.getRequestDispatcher("dashboard.jsp").forward(req, resp);
    }

    public void updateReimbursements(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        @SuppressWarnings("unchecked")
		List<Reimbursement> reimbursementList = (List<Reimbursement>) req.getSession().getAttribute("reimbursements");
        List<Reimbursement> updatedReimbursements = new ArrayList<>();
        User user = (User) req.getSession().getAttribute("userData");
        Reimbursement temp;
        if (req.getParameterValues("approved") != null)
            for (String id : req.getParameterValues("approved")) {
                temp = reimbursementList.get(Integer.parseInt(id));
                temp.setReimbResolver(user);
                temp.getReimbStatus().setId(1);
                temp.getReimbStatus().setStatus("Approved");
                temp.setReimbResolved(new Timestamp(System.currentTimeMillis()));
                updatedReimbursements.add(temp);
            }
        System.out.println(req.getParameterValues("denied"));
        if (req.getParameterValues("denied") != null)
            for (String id : req.getParameterValues("denied")) {
                temp = reimbursementList.get(Integer.parseInt(id));
                temp.setReimbResolver(user);
                temp.getReimbStatus().setId(3);
                temp.getReimbStatus().setStatus("Denied");
                temp.setReimbResolved(new Timestamp(System.currentTimeMillis()));
                updatedReimbursements.add(temp);
            }
        req.getSession().setAttribute("reimbursements", new BusinessDelegate().updateReimbursements(updatedReimbursements));
        req.getSession().setAttribute("selectedTab", "pending");
        req.getRequestDispatcher("dashboard.jsp").forward(req, resp);
    }

    public void insertReimbursement(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try{
            ReimbursementType reimbursementType = new ReimbursementType(Integer.parseInt(req.getParameter("type")), null);
            ReimbursementStatus reimbursementStatus = new ReimbursementStatus(2, null);
            Double amount = Double.parseDouble(req.getParameter("amount"));
            String description = req.getParameter("description");
            Part filePart = req.getPart("receipt");
            User author = (User) req.getSession().getAttribute("userData");
            InputStream receipt = filePart.getInputStream();
            Reimbursement reimbursement = new Reimbursement(
                    1, amount,
                    new Timestamp(System.currentTimeMillis()),
                    null,
                    description,
                    receipt,
                    author,
                    null,
                    reimbursementStatus,
                    reimbursementType
            );
            req.getSession().setAttribute("reimbursements", new BusinessDelegate().insertReimbursement(reimbursement));
            req.getRequestDispatcher("dashboard.jsp").forward(req,resp);
        } catch (IllegalStateException e){
            req.getSession().setAttribute("message","The file exceeded the 5MB limit!");
            req.getRequestDispatcher("dashboard.jsp").forward(req,resp);
        }
    }

    public void getReceipt(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        @SuppressWarnings("unchecked")
		List<Reimbursement> reimbursementList = (List<Reimbursement>) req.getSession().getAttribute("reimbursements");
        Reimbursement reimbursement = reimbursementList.get(Integer.parseInt(req.getParameter("reimbId")));
        InputStream inputStream = reimbursement.getReimbReceipt();
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        OutputStream outStream = resp.getOutputStream();
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
        outStream.flush();
        outStream.close();
        inputStream.close();
    }
}

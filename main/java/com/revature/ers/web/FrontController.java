package com.revature.ers.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/*
 * Here it will call user servlet to call and handle all the request
 * 
 */
@WebServlet(urlPatterns = "*/.do")
@MultipartConfig(maxFileSize = 1024*1024*5)
public class FrontController extends HttpServlet {
	private static final long serialVersionUID=1L;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    	//String action=request.getServletPath();
        switch (req.getRequestURI()){
            case "/ERS-systemsrc/main/webapp/login.do":
                if(req.getSession().getAttribute("userData")!=null)
                    new ReimbursementController().pending(req, resp);
                else req.getRequestDispatcher("index.jsp").forward(req,resp);
                break;
            case "/ERS-systemsrc/main/webapp/register.do":
                if(req.getSession().getAttribute("userData")!=null)
                    new ReimbursementController().pending(req, resp);
                else req.getRequestDispatcher("index.jsp").forward(req,resp);
                break;
            case "/ERS-systemsrc/main/webapp/pending.do":
                new ReimbursementController().pending(req, resp);
                break;
            case "/ERS-systemsrc/main/webapp/approved.do":
                new ReimbursementController().approved(req,resp);
                break;
            case "/ERS-systemsrc/main/webapp/declined.do":
                new ReimbursementController().declined(req,resp);
                break;
            case "/ERS-systemsrc/main/webapp/get_receipt.do":
                new ReimbursementController().getReceipt(req,resp);
                break;
            case "/ERS-systemsrc/main/webapp/logout.do":
                req.getSession().invalidate();
                req.getRequestDispatcher("index.jsp").forward(req,resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getRequestURI()){
            case "/ERS-systemsrc/main/webapp/login.do":
                new UserController().login(req,resp);
                break;
            case "/ERS-systemsrc/main/webapp/register.do":
                new UserController().register(req,resp);
                break;
            case "/ERS-systemsrc/main/webapp/updateReimbursements.do":
                new ReimbursementController().updateReimbursements(req,resp);
                break;
            case "/ERS-systemsrc/main/webapp/new_reimbursement.do":
                new ReimbursementController().insertReimbursement(req,resp);
                break;
            default:
                resp.setStatus(404);
        }
    }
}

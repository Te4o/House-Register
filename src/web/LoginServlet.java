package web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static utils.EntityManagerProvider.getEntityManager;

import dao.FlatDAO;
import model.Flat;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
        if(request.getParameter("flatNumber")==null||request.getParameter("password").equals("")) {
        	response.sendRedirect("Login.jsp");
        }else{
        	
        int flatNumber = Integer.valueOf(request.getParameter("flatNumber"));
         String password = String.valueOf(request.getParameter("password"));
            
        FlatDAO flatDAO = new FlatDAO(getEntityManager());

       boolean status=flatDAO.validateFlat(flatNumber, password) ;
       response.setContentType("text/html");  
       
 if(status){
	 EntityManager em=getEntityManager();
	 Flat fl=em.find(Flat.class, flatNumber);
	 
	 request.getSession().setAttribute("currentFlat", flatNumber);
	 request.getSession().setAttribute("flatStatus", (String)fl.getStatus().name());
	 
	 RequestDispatcher rd=request.getRequestDispatcher("index.jsp");  
     rd.forward(request, response);  
 }
 else response.sendRedirect("Login.jsp");  
	//doGet(request, response);
        }
	}
}

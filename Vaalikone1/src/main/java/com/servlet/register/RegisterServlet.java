package com.servlet.register;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet{
	
	//create the query
	private static final String INSERT_QUERY ="INSERT INTO EHDOKKAAT(ETUNIMI,SUKUNIMI,PUOLUE,ESITTELY,EHDOKASNUMERO,KAYTTAJANIMI,SALASANA) VALUES(?,?,?,?,?,?,?)";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//get PrintWriter
		PrintWriter pw = res.getWriter();
		//set content type
		res.setContentType("text/html");
		//Read the form values
		String etunimi = req.getParameter("etunimi");
		String sukunimi = req.getParameter("sukunimi");
		String puolue = req.getParameter("puolue");
		String esittely = req.getParameter("esittely");
		String ehdokasnumero = req.getParameter("ehdokasnumero");
		String kayttajanimi = req.getParameter("kayttajanimi");
		String salasana = req.getParameter("salasana");
		
		//load the jdbc driver
		try {
		Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//create the connection
		try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vaalikone","root","");
				PreparedStatement ps = con.prepareStatement(INSERT_QUERY);){
			//SET THE VALUES
			ps.setString(1, etunimi);
			ps.setString(2, sukunimi);
			ps.setString(3, puolue);
			ps.setString(4, esittely);
			ps.setString(5, ehdokasnumero);
			ps.setString(6, kayttajanimi);
			ps.setString(7, salasana);
			
			//execute the query
			int count= ps.executeUpdate();
			
			if(count==0) {
				pw.println("Candidate not stored into database");
			}else {
				pw.println("Candidate succesfully stored into database");
			}
			}catch(SQLException se) {
				pw.println(se.getMessage());
				se.printStackTrace();
			}catch(Exception e) {
				pw.println(e.getMessage());
				e.printStackTrace();
			}
		
		
		
		//close the stream
		pw.close();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}

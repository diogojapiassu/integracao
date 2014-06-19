package br.ufg.inf.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

/**
 * Servlet implementation class CriaMensagemServlet
 */
@WebServlet("/RecebeMensagemServlet")
public class RecebeMensagemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public RecebeMensagemServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Recebe mensagem: Passou no get");
		
		try{
			String mensagem = request.getParameter("mensagem");
			
			Queue queue = QueueFactory.getQueue("mensagens");
			queue.add(TaskOptions.Builder.withUrl("/criaMensagem").param(
					"mensagem", "essa seria a mensagem"));
			
			response.getWriter().println("Mensagem adicionada com sucesso.");
		} catch (Exception ex) {
			response.getWriter().println("Falha: " + ex.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mesg = request.getParameter("mensagem");
		System.out.println("Recebe mensagem: Passou no post: " + mesg);
	}

}

package br.ufg.inf.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simpleForBukkit.JSONObject;
import org.json.simpleForBukkit.parser.JSONParser;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskHandle;

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
			String id_mensagem = request.getParameter("id");
			/*String mensagemDaFila;
			
			Queue q = QueueFactory.getQueue("mensagens");
			List<TaskHandle> tasks = q.leaseTasks(1, TimeUnit.DAYS, 100);
			if(!tasks.isEmpty()){
				Iterator<TaskHandle> mensagens = tasks.iterator();
				while (mensagens.hasNext()) {
					TaskHandle taskMensagem = (TaskHandle) mensagens.next();
					if(taskMensagem.getName().equalsIgnoreCase(id_mensagem)){
						mensagemDaFila = new String(taskMensagem.getTag());
						System.out.println(mensagemDaFila.toString());
						response.getWriter().println(mensagemDaFila);
						return;
					}
				}
			}*/
			
			URL url = new URL("https://www.googleapis.com/taskqueue/v1beta2/projects/diogo-integracao/taskqueues/mensagens/tasks/" + id_mensagem
								+ "?key=AIzaSyDmmoVF8U-oU_svX4NBXnh7RnVCYql2n0k");
			HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();

			// envia dados
			httpUrlConnection.setRequestMethod("GET");
			//httpUrlConnection.setRequestProperty("Authorization", "Bearer ya29.LgD1B9d5sZu5uR0AAADhqzc-4Ccpqcw2lZ2ZGpZ3AdNL9AUjAIg6erA4lzDPCw");
			httpUrlConnection.setDoOutput(true);

			// Obtem as respostas
			InputStreamReader inputReader = new InputStreamReader(httpUrlConnection.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(inputReader);
			
			String retorno = getRetornoPagina(bufferedReader);
			JSONParser jp = new JSONParser();
			JSONObject jo = (JSONObject) jp.parse(retorno);
			
			String mensagemDaFila = jo.get("tag").toString();
			
			String retornoExclusao = apagaMensagemDaFilaGAE(id_mensagem);
			
			response.getWriter().println(mensagemDaFila);
			
		} catch (Exception ex) {
			response.getWriter().println("Falha: " + ex.getMessage());
		}
	}
	
	private String apagaMensagemDaFilaGAE(String id_mensagem) {
		String retorno = "ok";
		try {
			URL url = new URL("https://www.googleapis.com/taskqueue/v1beta2/projects/diogo-integracao/taskqueues/mensagens/tasks/" + id_mensagem);
			HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
			
			// envia dados
			httpUrlConnection.setRequestMethod("DELETE");
			httpUrlConnection.setRequestProperty("Authorization", "Bearer ya29.LgCYmPWj7aC1th0AAADS00OfmLzHGB3NtO67SSRjhsvRghTc9lSt5BbV_D7YkA");
			httpUrlConnection.setDoOutput(true);
			
			// Obtem as respostas
			InputStreamReader inputReader = new InputStreamReader(httpUrlConnection.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(inputReader);
			
			retorno = getRetornoPagina(bufferedReader);
		} catch (Exception e) {
			
			retorno = "Erro ao deletar tarefa " + id_mensagem + ": " + e.getMessage() ;
			System.out.println("Erro ao deletar tarefa " + id_mensagem + ": " + e.getMessage() );
		}
		
		return retorno;
		
	}

	private static String getRetornoPagina(BufferedReader bufferedReader)
			throws IOException {
		String linha = "";
		String texto = "";
		while ((linha = bufferedReader.readLine()) != null) {
			System.out.println(linha);
			texto += linha + "\n";
		}
		
		return texto;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mesg = request.getParameter("mensagem");
		System.out.println("Recebe mensagem: Passou no post: " + mesg);
	}

}

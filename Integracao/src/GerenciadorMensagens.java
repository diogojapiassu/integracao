import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GerenciadorMensagens {

	public static String adicionaMensagemNaFilaGAE(String mensagem) {
		if(mensagem == null || mensagem.isEmpty()){
			System.out.println("Mensagem vazia!");
			return null;
		}
		
		if(mensagem.length() > 640000){
			System.out.println("Tamanho da mensagem excedido!");
			return null;
		}
		
		String idRetornoMensagem = "";
		
		try {
			URL url = new URL("http://2-dot-diogo-integracao.appspot.com/criaMensagem");
			HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
			
			// envia dados
			httpUrlConnection.setRequestMethod("GET");
			httpUrlConnection.setRequestProperty("mensagem", mensagem);
			httpUrlConnection.setDoOutput(true);
			
			// Obtem as respostas
			InputStreamReader inputReader = new InputStreamReader(httpUrlConnection.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(inputReader);
			
			idRetornoMensagem = Utilidades.getRetornoPagina(bufferedReader);
			if(idRetornoMensagem != null && !idRetornoMensagem.isEmpty()){
				idRetornoMensagem = idRetornoMensagem.replaceAll("\\n", "");
			}
			
		} catch (Exception e) {
			System.out.println("Erro ao adicionar mensagem na fila no GAE: " + e.getMessage());
		}
		
		return idRetornoMensagem;
	}
	
	public static String recuperaMensagemNaFilaGAE(String idMensagem) {
		if(idMensagem == null || idMensagem.isEmpty()){
			return null;
		}
		
		String mensagemRetorno = "";
		
		try {
			URL url = new URL("http://2-dot-diogo-integracao.appspot.com/recebeMensagem?id=" + idMensagem);
			HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
			
			// envia dados
			httpUrlConnection.setRequestMethod("GET");
			httpUrlConnection.setDoOutput(true);
			
			// Obtem as respostas
			InputStreamReader inputReader = new InputStreamReader(httpUrlConnection.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(inputReader);
			
			mensagemRetorno = Utilidades.getRetornoPagina(bufferedReader);
		} catch (Exception e) {
			System.out.println("Erro ao recuperar mensagem na fila no GAE: " + e.getMessage());
		}
		
		return mensagemRetorno;
	}
	
}
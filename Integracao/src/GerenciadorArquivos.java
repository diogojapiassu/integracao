import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simpleForBukkit.JSONObject;
import org.json.simpleForBukkit.parser.JSONParser;
import org.json.simpleForBukkit.parser.ParseException;

public class GerenciadorArquivos {
	
	private static final String API_KEY = "AIzaSyDmmoVF8U-oU_svX4NBXnh7RnVCYql2n0k";

	public static void gravarArquivo(String nomeArquivo, String formato) {
		try {

			URL url = new URL("https://www.googleapis.com/upload/storage/v1/b/ufg/o/?"
					+ "uploadType=media&"
					+ "predefinedAcl=publicRead&"
					+ "name=" + nomeArquivo + 
					"&key=" + API_KEY);
			HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();

			httpUrlConnection.setRequestMethod("POST");
			httpUrlConnection.setRequestProperty("Content-Type", formato);
			httpUrlConnection.setDoOutput(true);

			InputStream in;
			in = new FileInputStream(nomeArquivo);
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			for (int i; (i = in.read()) != -1;) {
				out.write(i);
			}

			byte[] data = out.toByteArray();
			in.close();
			out.close();

			DataOutputStream outputWriter = new DataOutputStream(httpUrlConnection.getOutputStream());
			outputWriter.write(data);
			outputWriter.flush();
			outputWriter.close();

			// Obtem as respostas
			InputStreamReader inputReader = new InputStreamReader(httpUrlConnection.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(inputReader);

			String retornoDaPagina = Utilidades.getRetornoPagina(bufferedReader);
			System.out.println(retornoDaPagina);
		} catch (Exception e) {
			System.out.println("Erro ao gravar arquivo: " + e.getMessage());
			e.printStackTrace();
		}

	}
	
	public static void recuperarArquivo(String nomeDoArquivo, String nomeDoArquivoParaGravacao) {
		try {
			URL url = new URL("https://www.googleapis.com/storage/v1/b/ufg/o/" + nomeDoArquivo);
			HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();

			// envia dados
			httpUrlConnection.setRequestMethod("GET");
			httpUrlConnection.setDoOutput(true);

			// Obtem as respostas
			InputStreamReader inputReader = new InputStreamReader(httpUrlConnection.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(inputReader);
			
			String retorno = Utilidades.getRetornoPagina(bufferedReader);
			converteTextoEmJson(retorno, nomeDoArquivoParaGravacao);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void converteTextoEmJson(String texto, String nomeDoArquivoParaGravacao) {
		JSONParser jp = new JSONParser();
		try {
			JSONObject jo = (JSONObject) jp.parse(texto);
			
			 String caminho = jo.get("mediaLink").toString();
			
			 salvarArquivoPorURL(caminho, nomeDoArquivoParaGravacao);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	private static void salvarArquivoPorURL(String urlDaImagem, String nomeDoArquivoParaGravacao){
		try {
			URL url = new URL(urlDaImagem);
			InputStream in;
		
			in = new BufferedInputStream(url.openStream());
			
			OutputStream out = new FileOutputStream(nomeDoArquivoParaGravacao);

			for ( int i; (i = in.read()) != -1; ) {
			    out.write(i);
			}
			in.close();
			out.close();
		} catch (IOException e) {
			System.out.println("Erro ao recuperar e gravar arquivo em disco: " + e.getMessage());
			e.printStackTrace();
		}
		
	}

}
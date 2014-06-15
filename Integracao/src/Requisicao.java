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

public class Requisicao {

	public static void main(String[] args) {
		String nomeDaImagem = "ferrari.png";
		String chaveAutenticacao = "Bearer ya29.LgAvA6skHcGpGCIAAADYAlXO8tk5DdXKZw4zdRIdXkoWleuc3rz2uEn6rmXyTNWzPqwHJ0G64XKW9fTZtNU";
		String nomeDoArquivoParaGravacao = "imagemSalvaFerrari.png";
		
		recuperarImagem(nomeDaImagem, chaveAutenticacao, nomeDoArquivoParaGravacao);
		
		String nomeArquivoParaInsercao = "ferrari.png";
		String chaveAutenticacaoParaGravacao = "Bearer ya29.LgAvA6skHcGpGCIAAADYAlXO8tk5DdXKZw4zdRIdXkoWleuc3rz2uEn6rmXyTNWzPqwHJ0G64XKW9fTZtNU";
		
		//gravarImagem(nomeArquivoParaInsercao, chaveAutenticacaoParaGravacao);
	}

	private static void gravarImagem(String nomeArquivoParaInsercao, String chaveAutenticacaoParaGravacao) {
		// monta estrutura de parametros a serem eviados
				//String data;
				try {
					//data = URLEncoder.encode("nome", "UTF-8") + "=" + URLEncoder.encode("Fernando", "UTF-8");

					URL url = new URL("https://www.googleapis.com/upload/storage/v1/b/ufg/o/?uploadType=media&predefinedAcl=publicRead&name=" + nomeArquivoParaInsercao);
					//URL url = new URL("https://www.googleapis.com/upload/storage/v1/b/myBucket/o?uploadType=media&name=myObject");
					HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();

					// envia dados
					httpUrlConnection.setRequestMethod("POST");
					httpUrlConnection.setRequestProperty("Authorization", chaveAutenticacaoParaGravacao);
					httpUrlConnection.setRequestProperty("Content-Type", "image/png");
					// httpUrlConnection.setRequestProperty("Host",
					// "www.googleapis.com");
					httpUrlConnection.setDoOutput(true);

					//JSONObject jo = new JSONObject();
					//JSONObject data = new JSONObject();
					
					InputStream in;
					
					in = new FileInputStream(nomeArquivoParaInsercao);
					
					ByteArrayOutputStream out = new ByteArrayOutputStream();

					for ( int i; (i = in.read()) != -1; ) {
					    out.write(i);
					}
					
					byte[] data = out.toByteArray();
					
					in.close();
					out.close();
					
					//OutputStreamWriter outputWriter = new OutputStreamWriter(httpUrlConnection.getOutputStream());
					DataOutputStream outputWriter = new DataOutputStream(httpUrlConnection.getOutputStream());

					outputWriter.write(data);
					outputWriter.flush();
					outputWriter.close();

					// Obtem as respostas
					InputStreamReader inputReader = new InputStreamReader(httpUrlConnection.getInputStream());
					BufferedReader bufferedReader = new BufferedReader(inputReader);
					
					 String retornoDaPagina = getRetornoPagina(bufferedReader);
					 System.out.println(retornoDaPagina);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}

	private static void recuperarImagem(String nomeDoArquivo, String chaveAutenticacao, String nomeDoArquivoParaGravacao) {
		// monta estrutura de parametros a serem eviados
		String data;
		try {
			//data = URLEncoder.encode("nome", "UTF-8") + "=" + URLEncoder.encode("Fernando", "UTF-8");

			URL url = new URL("https://www.googleapis.com/storage/v1/b/ufg/o/" + nomeDoArquivo);
			//URL url = new URL("https://www.googleapis.com/upload/storage/v1/b/myBucket/o?uploadType=media&name=myObject");
			HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();

			// envia dados
			httpUrlConnection.setRequestMethod("GET");
			httpUrlConnection.setRequestProperty("Authorization", chaveAutenticacao);
			//httpUrlConnection.setRequestProperty("Content-Type", "image/jpeg");
			// httpUrlConnection.setRequestProperty("Host",
			// "www.googleapis.com");
			httpUrlConnection.setDoOutput(true);

			// OutputStreamWriter outputWriter = new
			// OutputStreamWriter(httpUrlConnection.getOutputStream());
			/*DataOutputStream outputWriter = new DataOutputStream(httpUrlConnection.getOutputStream());

			outputWriter.write(data.getBytes());
			outputWriter.flush();
			outputWriter.close();*/

			// Obtem as respostas
			InputStreamReader inputReader = new InputStreamReader(httpUrlConnection.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(inputReader);
			
			String retorno = getRetornoPagina(bufferedReader);
			converteTextoEmJson(retorno, nomeDoArquivoParaGravacao);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void salvarImagemPorURL(String urlDaImagem, String nomeDoArquivoParaGravacao){
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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

	private static void converteTextoEmJson(String texto, String nomeDoArquivoParaGravacao) {
		JSONParser jp = new JSONParser();
		try {
			JSONObject jo = (JSONObject) jp.parse(texto);
			
			 String caminho = jo.get("mediaLink").toString();
			
			 salvarImagemPorURL(caminho, nomeDoArquivoParaGravacao);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
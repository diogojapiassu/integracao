import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simpleForBukkit.JSONArray;
import org.json.simpleForBukkit.JSONObject;
import org.json.simpleForBukkit.parser.JSONParser;
import org.json.simpleForBukkit.parser.ParseException;

public class Requisicao {

	public static void main(String[] args) {
		// monta estrutura de parametros a serem eviados
		String data;
		try {
			data = URLEncoder.encode("nome", "UTF-8") + "=" + URLEncoder.encode("Fernando", "UTF-8");

			URL url = new URL("https://www.googleapis.com/storage/v1/b/ufg/o/ufg.png");
			//URL url = new URL("https://www.googleapis.com/upload/storage/v1/b/myBucket/o?uploadType=media&name=myObject");
			HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();

			// envia dados
			httpUrlConnection.setRequestMethod("GET");
			//httpUrlConnection.setRequestProperty("Authorization", "Bearer ya29.KgB7IjGEXcfAeyIAAACLJHY1Kux8WVgsQ1oUhzTh8POAPnAvhIbXiigmtlUPgHyj_4i-iUjKV5eZi405blo");
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
			
			imprimeRetornoPagina(bufferedReader);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void imprimeRetornoPagina(BufferedReader bufferedReader)
			throws IOException {
		System.out.println("\n** retorno da página web **");
		String linha = "";
		String texto = "";
		while ((linha = bufferedReader.readLine()) != null) {
			System.out.println(linha);
			texto += linha + "\n";
		}
		
		JSONParser jp = new JSONParser();
		try {
			JSONObject jo = (JSONObject) jp.parse(texto);
			
			jo.get("kind");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
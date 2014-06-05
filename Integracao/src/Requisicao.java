import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

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
			httpUrlConnection.setRequestProperty("Authorization", "Bearer ya29.KgCzGk6Ohv7IlSIAAAAFM3kz6LUF4YMWMkRrmEns3R_YIi64Ko0RiaXYy1DGz_2xmNRCuGlBUj2iAnmwPXs");
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
	
	private static void salvarImagemPorURL(String urlDaImagem){
		try {
			URL url = new URL(urlDaImagem);
			InputStream in;
		
			in = new BufferedInputStream(url.openStream());
			
			OutputStream out = new FileOutputStream("imagemSalva.png");

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
			
			 String caminho = jo.get("mediaLink").toString();
			
			 salvarImagemPorURL(caminho);
			 
//			File file = new File(caminho);
//			
//			FileOutputStream fos = new FileOutputStream(file);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
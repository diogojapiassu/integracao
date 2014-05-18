import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Requisicao {

	public static void main(String[] args) {
		// monta estrutura de parametros a serem eviados
		String data;
		try {
			data = URLEncoder.encode("nome", "UTF-8") + "=" + URLEncoder.encode("Fernando", "UTF-8");

			URL url = new URL("http://www.google.com");
			//URL url = new URL("https://www.googleapis.com/upload/storage/v1/b/myBucket/o?uploadType=media&name=myObject");
			HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();

			// envia dados
			httpUrlConnection.setRequestMethod("POST");
			httpUrlConnection.setRequestProperty("Host", "www.googleapis.com");
			httpUrlConnection.setRequestProperty("Content-Type", "image/jpeg");
			// httpUrlConnection.setRequestProperty("Host",
			// "www.googleapis.com");
			httpUrlConnection.setDoOutput(true);

			// OutputStreamWriter outputWriter = new
			// OutputStreamWriter(httpUrlConnection.getOutputStream());
			DataOutputStream outputWriter = new DataOutputStream(httpUrlConnection.getOutputStream());

			outputWriter.write(data.getBytes());
			outputWriter.flush();

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
		while ((linha = bufferedReader.readLine()) != null) {
			System.out.println(linha);
		}
	}

}
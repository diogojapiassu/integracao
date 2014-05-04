import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Authenticator.RequestorType;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class Requisicao {

	public static void main(String[] args) throws MalformedURLException, IOException { 
		// monta estrutura de parametros a serem eviados
					String data = URLEncoder.encode( "nome", "UTF-8" ) + "=" + URLEncoder.encode( "Fernando", "UTF-8" );
		 
				         URL url = new URL( "http://www.google.com" );
			                 HttpURLConnection httpUrlConnection = (HttpURLConnection)url.openConnection();
		 
			                 // envia dados
			                 httpUrlConnection.setRequestMethod("POST");
			                 httpUrlConnection.setDoOutput(true);
			                 OutputStreamWriter outputWriter = new OutputStreamWriter(httpUrlConnection.getOutputStream());
			                 outputWriter.write(data);
			                 outputWriter.flush();
		 
			                 // Obtem as respostas
			                 InputStreamReader inputReader = new InputStreamReader(httpUrlConnection.getInputStream());
			                 BufferedReader bufferedReader = new BufferedReader( inputReader );
		 
					System.out.println( "\n** retorno da página web **" );
					String linha = "";
					while ( (linha = bufferedReader.readLine()) != null){
						System.out.println(linha);
					}

	}

}

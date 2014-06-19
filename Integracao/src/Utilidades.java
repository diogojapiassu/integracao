import java.io.BufferedReader;
import java.io.IOException;

public class Utilidades {

	public static String getRetornoPagina(BufferedReader bufferedReader) throws IOException {
		String linha = "";
		String texto = "";
		while ((linha = bufferedReader.readLine()) != null) {
			System.out.println(linha);
			texto += linha + "\n";
		}

		return texto;
	}
	
}
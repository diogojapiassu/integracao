/**
 * Classe responsável por realizar as requisições aos seguintes serviços do 
 * Google Cloud Storage e Google Task Queue
 * @author diogo.japiassu
 *
 */
public class Requisicao {

	public static void main(String[] args) {
		//Envia a mensagem para a fila e recupera seu ID.
		String idMensagem = GerenciadorMensagens.adicionaMensagemNaFilaGAE("Universidade Federal de Palmas");
		System.out.println("Id da mensagem: " + idMensagem);
		//Pelo ID da mensagem adicionada na fila, é recuperada a mensagem:
		String mensagem = GerenciadorMensagens.recuperaMensagemNaFilaGAE(idMensagem);
		
		//Teste de inserção de arquivo no Cloud Storage:
		String nomeArquivoParaInsercao = "ferrari.png";
		String formatoArquivoInsercao = "image/png";
		GerenciadorArquivos.gravarArquivo(nomeArquivoParaInsercao, formatoArquivoInsercao);
		
		//Teste de recuperação de arquivo no Cloud Storage:
		String nomeDoArquivoParaRecuperacao = "ferrari.png";
		String nomeDoArquivoAposRecuperacao = "imagemSalvaFerrari3.png";
		GerenciadorArquivos.recuperarArquivo(nomeDoArquivoParaRecuperacao, nomeDoArquivoAposRecuperacao);
	}

	
}
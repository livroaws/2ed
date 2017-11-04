package cap03;

import java.io.IOException;

public class TesteAdama {

	public static void main(String[] args) throws IOException {
		S3Helper s3 = new S3Helper();

		// Conecta no S3
		s3.connect();

		// Lista todos os buckets
//		s3.listBuckets();

		// Nome do bucket de testes
		String bucketName = "adama-poc";
		String folder = "Teste";
		String fileName = "nome.txt";
		String filePath = "Teste/nome.txt";

		// Envia um arquivo texto para o bucket
		// Salva no caminho /Teste/nome.txt
		System.out.println("Fazendo upload...");
		byte[] bytes = new String("Ricardo R. Lecheta").getBytes();
		s3.putFile(bucketName, folder, fileName, "plain/text", bytes);
		System.out.println("Upload feito com sucesso.");

		// Lista os objetos do bucket novo. Vai exibir o nome.txt
		s3.listObjects(bucketName);

		// Faz download do arquivo nome.txt e imprime o conteúdo
		String s = s3.getFile(bucketName, filePath);
		System.out.println("Imprimindo arquivo...");
		System.out.println(s);

		// Deleta o arquivo nome.txt do bucket
		System.out.println("\nExcluindo arquivo...");
		s3.deleteObject(bucketName, "Teste/nome.txt");
		System.out.println("Arquivo excluído com sucesso.");


		// Lista todos os buckets novamente.
//		s3.listBuckets();
	}
}

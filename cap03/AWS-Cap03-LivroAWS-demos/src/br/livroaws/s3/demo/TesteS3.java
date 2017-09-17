package br.livroaws.s3.demo;

import java.io.IOException;

public class TesteS3 {

	public static void main(String[] args) throws IOException {
		S3Helper s3 = new S3Helper();

		// Conecta no S3
		s3.connect();

		// Lista todos os buckets
		s3.listBuckets();
		
		// Cria um novo bucket
		System.out.println("Criando o bucket livroaws-java-demo");
		s3.createBucket("livroaws-java-demo");
		
		// Lista todos os buckets novamente. Vai aparecer o novo bucket criado.
		s3.listBuckets();
		
		// Envia um arquivo texto para o bucket
		// Salva em [livroaws-java-demo]/Teste/nome.txt
		byte[] bytes = new String("Ricardo R. Lecheta").getBytes();
		s3.putFile("livroaws-java-demo", "Teste", "nome.txt", "plain/text", bytes);
		
		// Lista os objetos do bucket novo. Vai exibir o nome.txt
		s3.listObjects("livroaws-java-demo");
		
		// Faz download do arquivo nome.txt e imprime o conteudo
		String s = s3.getFile("livroaws-java-demo", "Teste/nome.txt");
		System.out.println("Imprimindo arquivo Teste/nome.txt");
		System.out.println(s);
		
		// Deleta o arquivo nome.txt do bucket
		System.out.println("\nExcluindo objeto Teste/nome.txt");
		s3.deleteObject("livroaws-java-demo", "Teste/nome.txt");
		
		// Deleta o novo bucket
		System.out.println("\nExcluindo bucket");
		s3.deleteBucket("livroaws-java-demo");
		
		// Lista todos os buckets novamente.
		s3.listBuckets();
	}
}

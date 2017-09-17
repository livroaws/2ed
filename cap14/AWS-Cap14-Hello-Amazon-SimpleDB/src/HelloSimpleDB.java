import java.util.ArrayList;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.BatchPutAttributesRequest;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.services.simpledb.model.DeleteAttributesRequest;
import com.amazonaws.services.simpledb.model.DeleteDomainRequest;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.ReplaceableItem;
import com.amazonaws.services.simpledb.model.SelectRequest;

/**
 * @author Ricardo Lecheta
 *
 */
public class HelloSimpleDB {
	public static void main(String[] args) throws Exception {
		AmazonSimpleDB sdb = new AmazonSimpleDBClient(new ClasspathPropertiesFileCredentialsProvider());
		// Seleciona a Região América do Sul - São Paulo
		Region r = Region.getRegion(Regions.SA_EAST_1);
		sdb.setRegion(r);
		System.out.println("===========================================");
		System.out.println("Exemplo do Amazon SimpleDB");
		System.out.println("===========================================\n");
		try {
			// Cria o domínio
			String myDomain = "LivroAWSStore";
			System.out.println("Criando o domínio " + myDomain + ".\n");
			sdb.createDomain(new CreateDomainRequest(myDomain));
			// Listando os domínios criados
			System.out.println("Listando todos os domínios da sua conta:\n");
			for (String domainName : sdb.listDomains().getDomainNames()) {
				System.out.println("  " + domainName);
			}
			System.out.println();
			// Inserindo dados no domínio
			System.out.println("Inserindo dados no domínio " + myDomain + ".\n");
			sdb.batchPutAttributes(new BatchPutAttributesRequest(myDomain, createSampleData()));
			// Imprime todos os dados do domínio
			printSelectAll(sdb, myDomain);
			// Teste de busca com um "Select expression"
			// Podemos fazer uma busca pelos atributos salvos no domínio
			String selectExpression = "select * from `" + myDomain + "` where empresa = 'Livetouch'";
			print(sdb, selectExpression);
			// Demonstra como substituir uma chave e valor do domínio
			System.out.println("Alterando o atributo 'empresa' do Usuario_03 para 'Disney World'.\n");
			List<ReplaceableAttribute> replaceableAttributes = new ArrayList<ReplaceableAttribute>();
			replaceableAttributes.add(new ReplaceableAttribute("empresa", "Disney World", true));
			sdb.putAttributes(new PutAttributesRequest(myDomain, "Usuario_03", replaceableAttributes));
			printSelectAll(sdb, myDomain);
			// Demonstra como deletar um registro pelo identificador.
			System.out.println("Deletando Usuario_03.\n");
			sdb.deleteAttributes(new DeleteAttributesRequest(myDomain, "Usuario_03"));
			printSelectAll(sdb, myDomain);
			// Exclui o domínio para finalizar o teste.
			System.out.println("Excluindo o domínio " + myDomain + ".\n");
			sdb.deleteDomain(new DeleteDomainRequest(myDomain));
		} catch (AmazonServiceException ase) {
			// Tratar erros aqui
			ase.printStackTrace();
		} catch (AmazonClientException ace) {
			// Tratar erros aqui
			ace.printStackTrace();
		}
	}
	// Imprime o conteúdo do domínio
	public static void printSelectAll(AmazonSimpleDB sdb, String myDomain) {
		// print all
		String selectExpression = "select * from `" + myDomain + "`";
		print(sdb, selectExpression);
	}
	// Executa a "SQL expression" e imprime o resultado
	public static void print(AmazonSimpleDB sdb, String selectExpression) {
		System.out.println("SELECT: " + selectExpression + "\n");
		SelectRequest selectRequest = new SelectRequest(selectExpression);
		for (Item item : sdb.select(selectRequest).getItems()) {
			System.out.println("  ID: " + item.getName());
            for (Attribute attribute : item.getAttributes()) {
                System.out.println("        "+attribute.getName()+":  " + attribute.getValue());
            }
		}
		System.out.println();
	}
	// Cria uma lista de ReplaceableItem que representa os dados a serem salvos
	// O ReplaceableItem possui um identificador, por exemplo: ReplaceableItem.
	// Dentro do ReplaceableItem, você pode inserir vários atributos organizados por chave e valor para organizar suas informações. 
	private static List<ReplaceableItem> createSampleData() {
		List<ReplaceableItem> dados = new ArrayList<ReplaceableItem>();
		dados.add(new ReplaceableItem("Usuario_01").withAttributes(
				new ReplaceableAttribute("nome", "Ricardo", true),
				new ReplaceableAttribute("sobrenome", "Lecheta", true),
				new ReplaceableAttribute("empresa", "Livetouch", true),
				new ReplaceableAttribute("site", "http://livetouch.com.br", true),
				new ReplaceableAttribute("email", "rlecheta@gmail.com", true)
				));
		dados.add(new ReplaceableItem("Usuario_02").withAttributes(
				new ReplaceableAttribute("nome", "Teste", true),
				new ReplaceableAttribute("sobrenome", "Testado", true),
				new ReplaceableAttribute("empresa", "Livetouch", true),
				new ReplaceableAttribute("site", "http://livetouch.com.br", true),
				new ReplaceableAttribute("email", "teste@gmail.com", true)
				));
		dados.add(new ReplaceableItem("Usuario_03").withAttributes(
				new ReplaceableAttribute("nome", "Mickey", true),
				new ReplaceableAttribute("sobrenome", "Mouse", true),
				new ReplaceableAttribute("empresa", "Disney", true),
				new ReplaceableAttribute("site", "http://livetouch.com.br", true),
				new ReplaceableAttribute("email", "mickeymouse@gmail.com", true)
				));
		return dados;
	}
}

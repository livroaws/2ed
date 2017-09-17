package br.livroaws.elasticache.demo;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactory;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;

public class TesteElastiCache {

	public static void main(String[] args) {
		try {
			// Utilize sua URL aqui e lembre-se de executar isso dentro do EC2
			String url = "livroawscache.xzdtoc.cfg.sae1.cache.amazonaws.com:11211";

			System.out.println("Testando cache: " + url);

			ConnectionFactoryBuilder connectionFactoryBuilder = new ConnectionFactoryBuilder();
			ConnectionFactory build = connectionFactoryBuilder.build();
			
			List<InetSocketAddress> addresses = AddrUtil.getAddresses(url);
			MemcachedClient memcachedClient = new MemcachedClient(build,addresses);
			
//			memcachedClient.set("nome", 0, "Ricardo");
			
			System.out.println("Nome do Cache leitura: " + memcachedClient.get("nome"));
			
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
}

// Carrega os módulos
var http = require('http');
var url = require('url');
const UsuarioDAO = require('./UsuarioDAO');

// Consulta os usuários e retorna o JSON como resposta.
function getUsuarios(response) {
	// Busca os usuários no banco.
	UsuarioDAO.getUsuarios(function(users) {
		// Converte o array de usuários para JSON
		var json = JSON.stringify(users)
		// Envia o JSON como resposta
	    response.end(json)
	});
}

// Função de callback para o servidor HTTP
function callback(request, response) {
	// Faz o parser da URL separando o caminho (path)
	var parts = url.parse(request.url);
	var path = parts.path;

	// Configura o tipo de retorno para application/json
	response.writeHead(200, {"Content-Type": "application/json; charset=utf-8"});

	if(request.method == "GET") {
		if (path == '/teste') {
			// poderiamos comparar o path do web service
			// mas vou retornar sempre o json dos usuários
			response.end('OK')
		}

		// Retorna o json dos usuários
		getUsuarios(response)
	}
}

// Cria um servidor HTTP que vai responder as requisições.
var server = http.createServer(callback);
// Porta que o servidor vai escutar
server.listen(3000);
// Mensagem ao iniciar o servidor
console.log("Servidor iniciado em http://localhost:3000");

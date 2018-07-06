var mysql = require('mysql');

// Classe
class UsuarioDAO {
	// Função para conectar no banco de dados.
	static connect() {
		var connection = mysql.createConnection({
		  host     : 'livroinstance.c6zmdwouvkyo.sa-east-1.rds.amazonaws.com',
		  user     : 'livro',
		  password : 'livro123',
		  database : 'livro'
		});
		connection.connect();
		return connection;
	}
	// Retorna a lista de usuários
	static getUsuarios(callback) {
		let connection = UsuarioDAO.connect()
		// Cria uma consulta
		let sql = "select * from usuario";
		let query = connection.query(sql, function (error, results, fields) {
			if (error) throw error;
			// Retorna os dados pela função de callback
			callback(results)
		});
		// Fecha a conexão.
		connection.end();
	}
};

module.exports = UsuarioDAO;

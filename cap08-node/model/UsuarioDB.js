var mysql = require('mysql');
let config = require('config');

// Classe usuarioDB
class UsuarioDB {
	// Função para conectar no banco de dados.
	static connect() {
		// Configurações do MySQL
        let dbConfig = config.get('mysqlConfig');

		// Cria a conexão com MySQL
		let connection = mysql.createConnection(dbConfig);

		// Conecta no banco de dados	
		connection.connect();
		return connection;
	}
	// Retorna a lista de usuarios
	static getUsuarios(callback) {
		let connection = UsuarioDB.connect()
		// Cria uma consulta
		let sql = "select * from usuario";
		let query = connection.query(sql, function (error, results, fields) {
			if (error) {
                callback(error,null);
                return;
            }
			// Retorna os dados pela função de callback
            callback(null,results);
		});
		// Fecha a conexão.
		connection.end();
	}
	
	// Retorna a lista de usuarios
	static getUsuarioById(id, callback) {
		let connection = UsuarioDB.connect()
		// Cria uma consulta
		let sql = "select * from usuario where id=?";
		let query = connection.query(sql, id, function (error, results, fields) {
			if (error) throw error;
			if(results.length == 0) {
				console.log("Nenhum usuario encontrado.")
				return
			}
			// Encontrou o usuario
			let usuario = results[0];
			// Retorna o usuario pela função de callback
			callback(usuario)
		});
		console.log(query.sql)
		// Fecha a conexão.
		connection.end();
	}
	// Salva um usuario no banco de dados.
	// Recebe o JSON com dados do usuario como parâmetro.
	static save(usuario, callback) {

		let connection = UsuarioDB.connect()

		// Insere o usuario
		let sql = "insert into usuario set ? ";
		let query = connection.query(sql, usuario, function (error, results, fields) {
			if (error) throw error;
			// Atualiza o objeto usuario do parametro com o "id" inserido
			usuario.id = results.insertId;
			// Retorna o usuario pela função de callback
			callback(usuario)
		});
		console.log(query.sql)
		// Fecha a conexão.
		connection.end();
	}
	// Atualiza um usuario no banco de dados.
	static update(usuario, callback) {

		let connection = UsuarioDB.connect()

		// SQL para atualizar o usuario
		let sql = "update usuario set ? where id = ?";
		// Id do usuario para atualizar
		let id = usuario.id;
		let query = connection.query(sql, [usuario, id], function (error, results, fields) {
			if (error) throw error;
			callback(usuario)
		});
		console.log(query.sql)
		// Fecha a conexão.
		connection.end();
	}
	// Deleta um usuario no banco de dados.
	static delete(usuario, callback) {

		let connection = UsuarioDB.connect()

		// SQL para deletar o usuario
		let sql = "delete from usuario where id = ?";
		// Id do usuario para deletar
		let id = usuario.id;
		let query = connection.query(sql, id, function (error, results, fields) {
			if (error) throw error;
			callback(usuario)
		});
		console.log(query.sql)
		// Fecha a conexão.
		connection.end();
	}
	// Deleta um usuario pelo id.
	static deleteById(id, callback) {

		let connection = UsuarioDB.connect()

		// SQL para deletar o usuario
		let sql = "delete from usuario where id = ?";

		let query = connection.query(sql, id, function (error, results, fields) {
			if (error) throw error;
			callback(results.affectedRows)
		});
		console.log(query.sql)
		// Fecha a conexão.
		connection.end();
	}
};

module.exports = UsuarioDB;

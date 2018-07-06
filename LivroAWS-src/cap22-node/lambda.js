'use strict';

// Carrega os módulos
var http = require('http');
var url = require('url');
const UsuarioDAO = require('./UsuarioDAO');

var handler = function(event, context, callback) {
    // Busca os carros no banco.
	UsuarioDAO.getUsuarios(function(users) {
		// users é a lista de usuários
		// retorna em JSON - thanks node :-)
		context.done(null, users);

	});
};

// Exporta esta função com o nome de "handler"
exports.handler = handler
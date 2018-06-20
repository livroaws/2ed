var express = require('express');
const router = express.Router();
const UsuarioDB = require('../model/UsuarioDB');

// GET em /usuarios
router.get('/', function (req, res, next) {
    UsuarioDB.getUsuarios(function (error, usuarios) {
        if (error) {
            // console.log("Erro de SQL: " + error.message);
            return next(error);
        }
        res.json(usuarios)
    });
});

// GET em /usuarios/id
router.get('/:id(\\d+)', function (req, res) {
    let id = req.params.id;
    UsuarioDB.getUsuarioById(id, function (usuario) {
        res.json(usuario)
    });
});

// DELETE em /usuarios/id
router.delete('/:id(\\d+)', function (req, res) {
    let id = req.params.id;
    console.log("deletar arro " + id);
    UsuarioDB.deleteById(id, function (affectedRows) {
        res.json({msg: 'usuario deletado com sucesso.'})
    });
});


// POST para salvar um usuario
router.post('/', function (req, res) {
    // usuario enviado no formato JSON
    let usuario = req.body;
    UsuarioDB.save(usuario, function (usuario) {
        res.json(usuario)
    });
});

// PUT para atualizar um usuario
router.put('/', function (req, res) {
    // usuario enviado no formato JSON
    let usuario = req.body;
    UsuarioDB.update(usuario, function (usuario) {
        // res.json(usuario)
        res.json({msg: 'usuario atualizado com sucesso.'})
    });
});

// // Rota genérica de erro '500'
// router.use(function (err, req, res, next) {
//     res.status(500);
//     res.json({erro: "Erro: " + err.message});
// });

module.exports = router;

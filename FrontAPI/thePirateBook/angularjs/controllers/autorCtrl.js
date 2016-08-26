app.controller("autorCtrl", function($scope, autorAPI) {
	$scope.msg = "Cadastrar Autor";

	$scope.autor = {};
	
	$scope.autores = [];

	var buscar = function(){
		var $promisse = autorAPI.getAutores();		

		$promisse.then(function(success){
			console.log("Retornou alguma coisa");
			$scope.autores = success.data;
		}, function(error){
			console.log("Deu ruim");
		});	
	};

	$scope.salvar = function(autor){
		var $promisse = autorAPI.adicionarAutor(autor);

		$promisse.then(function(success){
			console.log("Salvou");
		}, function(error){
			console.log("Erro ao persistir o autor");
			console.log(error);
		});

		buscar();
	};

	buscar();
});
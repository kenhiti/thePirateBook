app.service("autorAPI", function($http, config){

	this.getAutores = function(){
		return $http.get(config.baseURL+"/autores");
	};
	
	this.getAutorById = function(id){
		return $http.get(config.baseURL+"/autores/"+id);
	};
	
	this.adicionarAutor = function(autor){
		return $http.post(config.baseURL+"/autores", autor);
	};

	this.atualizarAutor = function(autor){
		return $http.put(config.baseURL+"/autores", autor);
	};
});
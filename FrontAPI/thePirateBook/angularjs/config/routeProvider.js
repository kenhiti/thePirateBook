app.config(function($routeProvider){
	$routeProvider.when('/login',{
		templateUrl: '/thePirateBook/angularjs/view/login.html',
		controller : 'loginCtrl'	
	});

	$routeProvider.when("/home",{
		templateUrl : '/thePirateBook/angularjs/view/home.html',
		controller : 'homeCtrl'
	});

	$routeProvider.when("/autor", {
		templateUrl : '/thePirateBook/angularjs/view/autor.html',
		controller : 'autorCtrl'
	});

	$routeProvider.otherwise({
		redirectTo: '/login'
	});
});
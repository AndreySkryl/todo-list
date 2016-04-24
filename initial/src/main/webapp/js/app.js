angulare.module('todo_list', []).
	config(function($routeProvider) {
		$routeProvider
			.when('/user', {controller: UserCtrl, template:'user.html'})
			.when('/list_of_task', {controller: ListOfTaskCtrl, template:'list_of_task.html'})
			.otherwise({redirectTo:'/list'});
	});


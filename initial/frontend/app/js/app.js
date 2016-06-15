var todoListApp = angular.module('todoListApp', ['ngRoute']);
todoListApp.config(['$routeProvider', function($routeProvider) {
	$routeProvider
		.when('/', {
			templateUrl: 'views/main_page/main_page.html',
			controller: 'listsOfTasksController'
		})
		.when('/users', {
			templateUrl: 'views/users_page/users_page.html',
			controller: 'usersController'
		})
		.when('/settings', {
			templateUrl: 'views/settings_page/settings_page.html',
			controller: 'settingsController'
		})
		.when('/list_of_task_page/:guidOfListOfTasks', {
			templateUrl: 'views/list_of_task_page/list_of_task_page.html',
			controller: 'listOfTasksController'
		})
		.otherwise({redirectTo: '/'});
}]);

todoListApp.factory('configAppService', [function() {
	var service = {
		protocol: 'http',
		host: 'localhost',
		port: 8080
	};

	service.api = service.protocol + '://' + service.host + ':' + service.port;

	return service;
}]);

(function () {
	'use strict';

	angular.module('todoListApp', ['ui.router'])
		.config(['$urlRouterProvider', '$stateProvider',
			function ($urlRouterProvider, $stateProvider) {

				$urlRouterProvider.otherwise('/');

				$stateProvider
					.state('main', {
						url: '/',
						templateUrl: 'views/main/main.tpl.html',
						controller: 'ListsOfTasksController'
					})
					.state('users', {
						url: '/users/',
						templateUrl: 'views/users/users.tpl.html',
						controller: 'UsersController'
					})
					.state('settings', {
						url: '/settings/',
						templateUrl: 'views/settings/settings.tpl.html',
						controller: 'SettingsController'
					})
					.state('listOfTasks', {
						url: '/list_of_task/:guidOfListOfTasks',
						templateUrl: 'views/list_of_task/list_of_task.tpl.html',
						controller: 'ListOfTasksController',
						params: {
							guidOfListOfTasks: ''
						}
					});

		}])
		.factory('configAppService', [function() {
			var service = {
				protocol: 'http',
				host: 'localhost',
				port: 8080
			};

			service.api = service.protocol + '://' + service.host + ':' + service.port;

			return service;
		}])
		.run(['$rootScope', function ($rootScope) {

			$rootScope.$on('$locationChangeStart', function () {

			});

		}]);
})();
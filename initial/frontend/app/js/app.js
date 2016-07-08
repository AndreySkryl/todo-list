(function () {
	'use strict';

	angular.module('todoListApp', ['auth0', 'angular-storage', 'angular-jwt', 'ngMaterial', 'ui.router'])
		.config(['$provide', 'authProvider', '$urlRouterProvider', '$stateProvider', '$httpProvider', 'jwtInterceptorProvider',
			function ($provide, authProvider, $urlRouterProvider, $stateProvider, $httpProvider, jwtInterceptorProvider) {

				authProvider.init({
					domain: 'andrey-skryl.eu.auth0.com',
					clientID: 'WSYBqhCnmsKnJbULXfWQ4AUKmJBzbyt5'
				});

				jwtInterceptorProvider.tokenGetter = function (store) {
					return store.get('id_token');
				};

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

				$provide.factory('redirect', ['$q', '$injector', 'auth', 'store', '$location',
					function redirect ($q, $injector, $timeout, store, $location) {
						var auth;
						$timeout(function() {
							auth = $injector.get('auth');
						});

						return {
							responseError: function(rejection) {

								if (rejection.status === 401) {
									auth.signout();
									store.remove('profile');
									store.remove('id_token');
									$location.path('/home');
								}
								return $q.reject(rejection);
							}
						};
					}
				]);
				$httpProvider.interceptors.push('jwtInterceptor');
				//$httpProvider.interceptors.push('redirect');
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
		.run(['$rootScope', 'auth', 'store', 'jwtHelper', '$location', function ($rootScope, auth, store, jwtHelper, $location) {

			$rootScope.$on('$locationChangeStart', function () {

				var token = store.get('id_token');
				if (token) {
					if (!jwtHelper.isTokenExpired(token)) {
						if (!auth.isAuthenticated) {
							auth.authenticate(store.get('profile'), token);
						}
					}
				}

				if (!auth.isAuthenticated) {
					$location.path('/login');
				}

			});

		}]);
})();
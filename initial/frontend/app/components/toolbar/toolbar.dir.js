(function() {
	'use strict';

	angular.module('todoListApp')
		.directive('toolbar', toolbar);

	function toolbar() {
		return {
			templateUrl: 'components/toolbar/toolbar.tpl.html',
			controller: 'toolbarController',
			controllerAs: 'toolbar'
		};
	}

	angular.module('todoListApp')
		.controller('toolbarController', ['auth', 'store', '$location', function (auth, store, $location) {

			var vm = this;
			vm.login = login;
			vm.logout = logout;
			vm.auth = auth;

			function login() {
				auth.signin({}, function (profile, token) {
					store.set('profile', profile);
					store.set('id_token', token);
					$location.path('/');
				}, function (error) {
					console.log(error);
				});
			}

			function logout() {
				store.remove('profile');
				store.remove('id_token');
				auth.signout();
				$location.path('/');
			}

		}]);
})();
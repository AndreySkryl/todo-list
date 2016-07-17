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

			function login (email, password, callback) {
				var connection = mysql({
					host     : 'localhost',
					user     : 'root',
					password : 'password',
					database : 'todo_list'
				});

				connection.connect();

				var query = "SELECT GUID, LOGIN, EMAIL, PASSWORD, LASTNAME, FIRSTNAME " +
					"FROM user WHERE EMAIL = ?";

				connection.query(query, [email], function (err, results) {
					if (err) return callback(err);
					if (results.length === 0) return callback();
					var user = results[0];

					if (!bcrypt.compareSync(password, user.password)) {
						return callback();
					}

					callback(null,   {
						guid:      user.guid.toString(),
						login:     user.login.toString(),
						eMail:     user.eMail.toString(),
						password:  user.password.toString(),
						lastName:  user.lastName.toString(),
						firstName: user.firstName.toString()
					});

				});

			}

			/*function login() {
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
			}*/

		}]);
})();
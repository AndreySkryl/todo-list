todoListApp.controller('userController', ['$scope', '$rootScope', 'userService', function($scope, $rootScope, userService) {
	$scope.user = {};

	var guidOfUser = '46ff4f70-061f-11e6-89e7-7c050741ff67';

	var syncModelWithServer = function () {
		var promise = userService.getUserByGuid(guidOfUser);

		promise.success(function(data, status, headers, config) {
			$scope.user = data;
		}).error(function(data, status, headers, config) {
			alert(status);
		});
	};
	syncModelWithServer();

	$rootScope.$on('userSettings::updated', function (event, data) {
		syncModelWithServer();
	});
}]);
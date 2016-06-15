todoListApp.controller('listOfTasksController', ['$scope', '$rootScope', 'listOfTasksService', '$routeParams', function($scope, $rootScope, listOfTasksService, $routeParams) {
	$scope.listOfTasks = {};
	$scope.subscribers = [];

	var guidOfUser = '46ff4f70-061f-11e6-89e7-7c050741ff67';
	var guidOfListOfTasks = $routeParams.guidOfListOfTasks;

	var init = function () {
		var promise = listOfTasksService.getListOfTasks(guidOfListOfTasks);
		promise.success(function(data, status, headers, config) {
			$scope.listOfTasks = data;
		}).error(function(data, status, headers, config) {
			alert(status);
		});

		promise = listOfTasksService.getAllSubscribers(guidOfListOfTasks);
		promise.success(function(data, status, headers, config) {
			$scope.subscribers = data;
		}).error(function(data, status, headers, config) {
			alert(status);
		});
	};
	init();
}]);
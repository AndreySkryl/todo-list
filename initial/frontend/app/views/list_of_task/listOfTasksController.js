(function () {
	'use strict';

	angular.module('todoListApp')
		.controller('ListOfTasksController', ['$scope', '$rootScope', 'listOfTasksService', '$stateParams', function ($scope, $rootScope, listOfTasksService, $stateParams) {
			$scope.listOfTasks = {};
			$scope.subscribers = [];

			var guidOfUser = '46ff4f70-061f-11e6-89e7-7c050741ff67';
			var guidOfListOfTasks = $stateParams.guidOfListOfTasks;

			var syncModelWithServer = function () {
				var promise = listOfTasksService.getListOfTasks(guidOfListOfTasks);
				promise.success(function (data, status, headers, config) {
					$scope.listOfTasks = data;
				}).error(function (data, status, headers, config) {
					alert(status);
				});

				promise = listOfTasksService.getAllSubscribers(guidOfListOfTasks);
				promise.success(function (data, status, headers, config) {
					$scope.subscribers = data;
				}).error(function (data, status, headers, config) {
					alert(status);
				});
			};
			syncModelWithServer();
		}]);
})();
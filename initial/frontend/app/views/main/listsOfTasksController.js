(function () {
	'use strict';

	angular.module('todoListApp')
		.controller('ListsOfTasksController', ['$scope', '$rootScope', 'listOfTasksService', 'sessionService',
			function ($scope, $rootScope, listOfTasksService, sessionService) {
				$scope.listsOfTasks = [];

				var guidOfUser = sessionService.get('uid');

				var syncModelWithServer = function () {
					var promise = listOfTasksService.getAllListsOfTasksOfUser(guidOfUser);
					promise.success(function (data, status, headers, config) {
						$scope.listsOfTasks = data;
					}).error(function (data, status, headers, config) {
						alert(status);
					});
				};
				syncModelWithServer();

				$scope.deleteListOfTasks = function (listOfTasks) {
					var promise = listOfTasksService.deleteListOfTasks(listOfTasks.guid, guidOfUser);
					promise.success(function (data, status, headers, config) {
						syncModelWithServer();
					}).error(function (data, status, headers, config) {
						alert(status);
					});
				};

				$scope.popUpDialogAddTemplateListOfTasks = function () {
					// init
					$scope.nameOfTemplateListOfTasks = '';
					$scope.descriptionForCreatedTemplateListOfTasks = '';
					$scope.typeOfCreateOfTemplateListOfTasks = '0';

					$scope.showPopUpDialogAddTemplateListOfTasks = true;
				};
				$rootScope.$on('templateListOfTasks::created', function () {
					syncModelWithServer();
				});

				$scope.popUpDialogAddSimpleListOfTasks = function () {
					// init
					$scope.nameOfSimpleListOfTasks = '';
					$scope.descriptionForCreatedSimpleListOfTasks = '';
					$scope.typeOfCreateOfSimpleListOfTasks = '0';

					$scope.showPopUpDialogAddSimpleListOfTasks = true;
				};
				$rootScope.$on('simpleListOfTasks::created', function () {
					syncModelWithServer();
				});
			}])
		.directive('popUpDialogAddTemplateListOfTasks', ['sessionService', function (sessionService) {
			return {
			restrict: 'E',
			scope: false,
			templateUrl: 'views/main/popUpDialog/popUpDialogAddTemplateListOfTasks.html',
			controller: ['$scope', '$rootScope', 'listOfTasksService', function ($scope, $rootScope, listOfTasksService) {
				$scope.showPopUpDialogAddTemplateListOfTasks = false;

				$scope.popUpDialogAddTemplateListOfTasksApprove = function () {
					var guidOfUser = sessionService.get('uid');
					var listOfTasks = {
						userGuid: guidOfUser,
						favourites: '1',
						name: $scope.nameOfTemplateListOfTasks,
						description: $scope.descriptionOfTemplateListOfTasks || ''
					};

					if ($scope.typeOfCreateOfTemplateListOfTasks === '0') {
						var promise = listOfTasksService.newListOfTasks(listOfTasks);
						promise.success(function (data, status, headers, config) {
							$rootScope.$emit('templateListOfTasks::created');
						}).error(function (data, status, headers, config) {
							alert(status);
						});
					}

					$scope.showPopUpDialogAddTemplateListOfTasks = false;
				};

				$scope.closePopUpDialogAddTemplateListOfTasks = function () {
					$scope.showPopUpDialogAddTemplateListOfTasks = false;
				};
			}]
		};
		}])
		.directive('popUpDialogAddSimpleListOfTasks', [function () {
			return {
				restrict: 'E',
				scope: false,
				templateUrl: 'views/main/popUpDialog/popUpDialogAddSimpleListOfTasks.html',
				controller: ['$scope', '$rootScope', 'listOfTasksService', 'sessionService',
					function ($scope, $rootScope, listOfTasksService, sessionService) {
						$scope.showPopUpDialogAddSimpleListOfTasks = false;

						$scope.popUpDialogAddSimpleListOfTasksApprove = function () {
							var guidOfUser = sessionService.get('uid');
							var listOfTasks = {
								userGuid: guidOfUser,
								favourites: '0',
								name: $scope.nameOfSimpleListOfTasks,
								description: $scope.descriptionOfSimpleListOfTasks || ''
							};

							if ($scope.typeOfCreateOfSimpleListOfTasks === '0') {
								var promise = listOfTasksService.newListOfTasks(listOfTasks);
								promise.success(function (data, status, headers, config) {
									$rootScope.$emit('simpleListOfTasks::created');
								}).error(function (data, status, headers, config) {
									alert(status);
								});
							}

							$scope.showPopUpDialogAddSimpleListOfTasks = false;
						};

						$scope.closePopUpDialogAddSimpleListOfTasks = function () {
							$scope.showPopUpDialogAddSimpleListOfTasks = false;
						};
					}]
			};
		}]);
})();
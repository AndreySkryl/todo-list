todoListApp.controller('listsOfTasksController', ['$scope', '$rootScope', 'listOfTasksService', function($scope, $rootScope, listOfTasksService) {
	$scope.listsOfTasks = [];

	var guidOfUser = '46ff4f70-061f-11e6-89e7-7c050741ff67';

	var syncModelWithServer = function () {
		var promise = listOfTasksService.getAllListsOfTasksOfUser(guidOfUser);
		promise.success(function(data, status, headers, config) {
			$scope.listsOfTasks = data;
		}).error(function(data, status, headers, config) {
			alert(status);
		});
	};
	syncModelWithServer();

	$scope.popUpDialogAddTemplateListOfTasks = function () {
		// init
		$scope.nameOfTemplateListOfTasks = '';
		$scope.descriptionOfTemplateListOfTasks = '';
		$scope.typeOfCreateOfTemplateListOfTasks = '0';

		$scope.showPopUpDialogAddTemplateListOfTasks = true;
	};

	$rootScope.$on('listOfTasks::created', function () {
		syncModelWithServer();
	});
}]);
todoListApp.directive('popUpDialogAddTemplateListOfTasks', [function () {
	return {
		restrict: 'E',
		scope: false,
		templateUrl: 'views/main_page/popUpDialogAddTemplateListOfTasks.html',
		controller: ['$scope', '$rootScope', 'listOfTasksService', function ($scope, $rootScope, listOfTasksService) {
			$scope.showPopUpDialogAddTemplateListOfTasks = false;

			$scope.popUpDialogAddTemplateListOfTasksApprove = function () {
				var guidOfUser = '46ff4f70-061f-11e6-89e7-7c050741ff67';
				var listOfTasks = {
					userGuid: guidOfUser,
					favourites: '1',
					name: $scope.nameOfTemplateListOfTasks,
					description: $scope.descriptionOfTemplateListOfTasks || ''
				};

				if ($scope.typeOfCreateOfTemplateListOfTasks === '0') {
					var promise = listOfTasksService.newListOfTasks(listOfTasks);
					promise.success(function(data, status, headers, config) {
						$rootScope.$emit('listOfTasks::created');
					}).error(function(data, status, headers, config) {
						alert(status);
					});
				}

				$scope.showPopUpDialogAddTemplateListOfTasks = false;
			};

			$scope.closePopUpDialogAddTemplateListOfTasks = function() {
				$scope.showPopUpDialogAddTemplateListOfTasks = false;
			};
		}]
	};
}]);
todoListApp.directive('popUpDialogAddSimpleListOfTasks', [function () {
	return {
		restrict: 'E',
		scope: false,
		templateUrl: 'views/main_page/popUpDialogAddSimpleListOfTasks.html',
		controller: ['$scope', '$rootScope', 'listOfTasksService', function ($scope, $rootScope, listOfTasksService) {
			$scope.showPopUpDialogAddSimpleListOfTasks = false;

			$scope.popUpDialogAddSimpleListOfTasksApprove = function () {
				var guidOfUser = '46ff4f70-061f-11e6-89e7-7c050741ff67';
				var listOfTasks = {
					userGuid: guidOfUser,
					favourites: '0',
					name: $scope.name,
					description: $scope.description || ''
				};

				if ($scope.typeOfCreate === '0') {
					var promise = listOfTasksService.newListOfTasks(listOfTasks);
					promise.success(function(data, status, headers, config) {
						$rootScope.$emit('listOfTasks::created');
					}).error(function(data, status, headers, config) {
						alert(status);
					});
				}

				$scope.showPopUpDialogAddSimpleListOfTasks = false;
			};

			$scope.closePopUpDialogAddSimpleListOfTasks = function() {
				$scope.showPopUpDialogAddSimpleListOfTasks = false;
			};
		}]
	};
}]);
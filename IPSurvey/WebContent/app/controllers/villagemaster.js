/**
 * 
 */

angular.module('ipsurveyapp.Controllers', [])
	.controller("villagemasterCtrl", ['$scope','$rootScope', '$http', '$filter', '$compile', '$state','$cookies', 	
					  function($scope,$rootScope, $http, $filter, $compile, $state,$cookies) {
		
		console.log("villagemaster Controller Initiated");
		
		var LOCATION_CODE = store.get('LOCATION_CODE');
		$rootScope.LOCATION_CODE = LOCATION_CODE;
		
	}]);
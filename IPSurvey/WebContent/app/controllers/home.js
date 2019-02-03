/**
 * 
 */

angular.module('ipsurveyapp.Controllers', [])
	.controller("homeCtrl",function($scope,$rootScope, $http, $filter, $compile, $state,$cookies,store) {
		
		console.log("Home Controller Initiated");
		
		if(!store.get('userinfo')){
			$rootScope.logout();
		}else{
			$rootScope.IsLoggedIn = true;
		}
		
	});
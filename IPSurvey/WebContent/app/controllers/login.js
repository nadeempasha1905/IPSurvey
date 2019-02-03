/**
 * 
 */

angular.module('ipsurveyapp.Controllers', [])
	.controller("loginCtrl", function($scope,$rootScope, $http, $filter, $compile, $state,$cookies,remote,notify,store) {
		
		$scope.login = {};
		
		console.log("Login Controller Initiated");
		
		$scope.ValidateUser = function(){
			
			if($scope.login.username === undefined || $scope.login.username === null){
				alert("Username cannot be blank !!!");
				return;
			}
			
			if($scope.login.password === undefined || $scope.login.password === null){
				alert("Password cannot be blank !!!");
				return;
			}
			
			remote.load("signin_pc", function(response){
				
				if(response.status === "success"){
					
					store.set('userinfo', response);
					
					$state.go("home");
				}else{
					
				}
				console.log("signin_pc",response);
			},{
				username:$scope.login.username,
				password:$scope.login.password
			}, 'POST');
			
		};
	});
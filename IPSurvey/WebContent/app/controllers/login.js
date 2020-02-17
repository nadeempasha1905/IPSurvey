/**
 * 
 */

angular.module('ipsurveyapp.Controllers', [])
	.controller("loginCtrl", function($scope,$rootScope, $http, $filter, $compile, $state,$cookies,remote,notify,store) {
		
		$scope.login = {};
		
		console.log("Login Controller Initiated");
		
		/*var LOCATION_CODE = store.get('LOCATION_CODE');
		$rootScope.LOCATION_CODE = LOCATION_CODE;*/
		
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
					
					$rootScope.USER_INFO =  store.get('userinfo');
					
					var LOCATION_CODE = response.location_code;
					$rootScope.LOCATION_CODE = '';
					if(LOCATION_CODE.length == 2){$rootScope.LOCATION_CODE = 'ZONE';}
					else if(LOCATION_CODE.length == 3){$rootScope.LOCATION_CODE = 'CIRCLE';}
					else if(LOCATION_CODE.length == 5){$rootScope.LOCATION_CODE = 'DIVISION';}
					else if(LOCATION_CODE.length == 7){$rootScope.LOCATION_CODE = 'SUBDIVISION'}
					else if(LOCATION_CODE.length == 9){$rootScope.LOCATION_CODE = 'OMSECTION';}
					else{$rootScope.LOCATION_CODE = null;}
					
					store.set('LOCATION_CODE', $rootScope.LOCATION_CODE);
					
					$state.go("home");
					//$state.go("viewmap");
					
					console.log("signin_pc",response);
					
					$rootScope.NAVIGATE_FROM = 'IISPL'; 
					
				}else{
					
				}
				
			},{
				username:$scope.login.username,
				password:$scope.login.password
			}, 'POST');
			
		};
	});
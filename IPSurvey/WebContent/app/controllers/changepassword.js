/**
 * 
 */

angular.module('ipsurveyapp.Controllers', [])
	.controller("changepasswordCtrl", function($scope,$rootScope, $http, $filter, $compile, $state,$cookies,remote,notify,store,$timeout) {
		
		console.log("changepasswordCtrl Controller Initiated");
		
		var LOCATION_CODE = store.get('LOCATION_CODE');
		$rootScope.LOCATION_CODE = LOCATION_CODE;
		
		$scope.search = {};
		$scope.userinfo = {};
		$scope.modal={};
		if(!store.get('userinfo')){
			$rootScope.logout();
			$scope.userinfo = {};
		}else{
			$rootScope.IsLoggedIn = true;
			$scope.userinfo = store.get('userinfo');
		}
		
		$scope.SaveRecord = function(){
			
			if($scope.modal.existingpassword === '' || $scope.modal.existingpassword === null || $scope.modal.existingpassword === undefined){notify.warn("Please enter existing password !!!");return;}
			if($scope.modal.newpassword === '' || $scope.modal.newpassword === null || $scope.modal.newpassword === undefined){notify.warn("Please enter New password !!!");return;}
			if($scope.modal.confirmpassword === '' || $scope.modal.confirmpassword === null || $scope.modal.confirmpassword === undefined){notify.warn("Please enter confirm password !!!");return;}
			
			if($scope.modal.newpassword != $scope.modal.confirmpassword){
				notify.error("New Password and Confirm password should match !!!");
				return;
			}
			
			if($scope.modal.existingpassword === $scope.modal.confirmpassword){
				notify.error("New Password should not same as existing password !!!");
				return;
			}
			
			remote.load("chnagepassword", function(response){
				if(response.status === 'success'){
					$scope.modal.existingpassword = '';
					$scope.modal.newpassword = ''; 
					$scope.modal.confirmpassword = '';
					
					$rootScope.logout();
				}
			},{
				pass: $scope.modal.confirmpassword,
				userid:$scope.userinfo.username
			}, 'POST');
			
		};
		
	});
/**
 * 
 */

angular.module('ipsurveyapp.Controllers', [])
	.controller("viewmapCtrl", function($scope,$rootScope, $http, $filter, $compile, $state,$cookies,store,$timeout) {
		
		console.log("Viewmap Controller Initiated");
		
		if(!store.get('userinfo')){
			$rootScope.logout();
		}else{
			$rootScope.IsLoggedIn = true;
		}
		
		var map ; 
		var marker ; 
		var infowindow = new google.maps.InfoWindow();
		
		$scope.initialize = function() {
			 var myLatLng = {lat: 12.9761851, lng: 77.6403459};
			 
			 var mapProp = {
					    center:new google.maps.LatLng(13.22312,75.00572),
					    zoom:16,
					    mapTypeId:google.maps.MapTypeId.ROADMAP
					  };
			 map=new google.maps.Map(document.getElementById("googleMap"),mapProp);
					  
		 			   var marker = new google.maps.Marker({
						      position: myLatLng,
						      map: map,
						      title: 'Hello World!'
						    });  
		 			   
		}
		
		$timeout(function () {
			$scope.initialize();
		}, 1000);

		
	});
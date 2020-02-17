/**
 * 
 */

angular.module('ipsurveyapp.Controllers', [])
	.controller("homeCtrl",function($scope,$rootScope, $http, $filter, $compile, $state,$cookies,store,remote,notify) {
		
		console.log("Home Controller Initiated");
		
		var LOCATION_CODE = store.get('LOCATION_CODE');
		$rootScope.LOCATION_CODE = LOCATION_CODE;
		
		$scope.search = {};
		$scope.userinfo = {};
		$scope.modal={};
		if(!store.get('userinfo')){
			$rootScope.logout();
			$scope.userinfo = {};
			//$state.go("login");
		}else{
			$rootScope.IsLoggedIn = true;
			$scope.userinfo = store.get('userinfo');
		}
		
		$scope.ZONEUSER = false;
		$scope.CIRCLEUSER = false;
		$scope.DIVISIONUSER = false;
		$scope.SUBDIVISIONUSER = false;
		$scope.OMSECTIONUSER = false;
		
		var LOCATION_CODE = $scope.userinfo.location_code;
		var loc_company= "",loc_zone= "",loc_circle= "",loc_division= "",loc_subdivision= "",loc_omsection = "";
		var loc_usertype = 0;
		var loc_arr = [];
		
		$scope.initialize = function(){
			if(LOCATION_CODE.length == 1){loc_company=LOCATION_CODE.substr(0,1);loc_usertype=0;}
			if(LOCATION_CODE.length == 2){loc_company=LOCATION_CODE.substr(0,1);loc_zone=LOCATION_CODE.substr(0,2);loc_usertype=1;}
			if(LOCATION_CODE.length == 3){loc_company=LOCATION_CODE.substr(0,1);loc_zone=LOCATION_CODE.substr(0,2);loc_circle=LOCATION_CODE.substr(0,3);loc_usertype=2;}
			if(LOCATION_CODE.length == 5){loc_company=LOCATION_CODE.substr(0,1);loc_zone=LOCATION_CODE.substr(0,2);loc_circle=LOCATION_CODE.substr(0,3);loc_division=LOCATION_CODE.substr(0,5);loc_usertype=3;}
			if(LOCATION_CODE.length == 7){loc_company=LOCATION_CODE.substr(0,1);loc_zone=LOCATION_CODE.substr(0,2);loc_circle=LOCATION_CODE.substr(0,3);loc_division=LOCATION_CODE.substr(0,5);loc_subdivision=LOCATION_CODE.substr(0,7);loc_usertype=4;}
			if(LOCATION_CODE.length == 9){loc_company=LOCATION_CODE.substr(0,1);loc_zone=LOCATION_CODE.substr(0,2);loc_circle=LOCATION_CODE.substr(0,3);loc_division=LOCATION_CODE.substr(0,5);loc_subdivision=LOCATION_CODE.substr(0,7);loc_omsection=LOCATION_CODE.substr(0,9);loc_usertype=5;}
			

			if (loc_usertype == 0) {
				var loc_arr = [ loc_company ];
				$scope.ZONEUSER = false;
				$scope.CIRCLEUSER = false;
				$scope.DIVISIONUSER = false;
				$scope.SUBDIVISIONUSER = false;
				$scope.OMSECTIONUSER = false;
				$scope.getzonelist(loc_arr, loc_usertype, 'search');
			}
			if (loc_usertype == 1) {
				var loc_arr = [ loc_company,loc_zone ];
				$scope.ZONEUSER = true;
				$scope.CIRCLEUSER = false;
				$scope.DIVISIONUSER = false;
				$scope.SUBDIVISIONUSER = false;
				$scope.OMSECTIONUSER = false;
				$scope.getzonelist(loc_arr, loc_usertype, 'search');
			}
			if (loc_usertype == 2) {
				var loc_arr = [ loc_company,loc_zone, loc_circle ];
				$scope.ZONEUSER = false;
				$scope.CIRCLEUSER = true;
				$scope.DIVISIONUSER = false;
				$scope.SUBDIVISIONUSER = false;
				$scope.OMSECTIONUSER = false;
				$scope.getzonelist(loc_arr, loc_usertype, 'search');
			}
			if (loc_usertype == 3) {
				var loc_arr = [ loc_company,loc_zone, loc_circle, loc_division ];
				$scope.ZONEUSER = false;
				$scope.CIRCLEUSER = false;
				$scope.DIVISIONUSER = true;
				$scope.SUBDIVISIONUSER = false;
				$scope.OMSECTIONUSER = false;
				$scope.getzonelist(loc_arr, loc_usertype, 'search');
			}
			if (loc_usertype == 4) {
				var loc_arr = [ loc_company, loc_zone, loc_circle, loc_division,
						loc_subdivision ];
				$scope.ZONEUSER = false;
				$scope.CIRCLEUSER = false;
				$scope.DIVISIONUSER = false;
				$scope.SUBDIVISIONUSER = true;
				$scope.OMSECTIONUSER = false;
				$scope.getzonelist(loc_arr, loc_usertype, 'search');
			}
			if (loc_usertype == 5) {
				var loc_arr = [ loc_company, loc_zone, loc_circle, loc_division,
						loc_subdivision, loc_omsection ];
				$scope.ZONEUSER = false;
				$scope.CIRCLEUSER = false;
				$scope.DIVISIONUSER = false;
				$scope.SUBDIVISIONUSER = false;
				$scope.OMSECTIONUSER = true;
				$scope.getzonelist(loc_arr, loc_usertype, 'search');
			}
		}
		
		$scope.getzonelist = function(arr,usertype,searchtype){
				$scope.searchzonelist=[];$scope.searchcirclelist=[];$scope.searchdivisionlist=[];$scope.searchsubdivisionlist=[];$scope.searchomsectionlist=[];
				remote.load("getzonelist", function(response){
					$scope.searchzonelist = response.ZONE_LIST;
					if(arr.length > 0){
						
						
						if(usertype > 1){
							$scope.search.zone = $filter('filter')($scope.searchzonelist,{key:arr[1]},true)[0];
							$scope.getcircleList(arr,usertype,'search');
						}else if(usertype == 1){
							$scope.search.zone = $filter('filter')($scope.searchzonelist,{key:arr[1]},true)[0];
							remote.load("getcirclelist", function(response){
								$scope.searchcirclelist = response.CIRCLE_LIST;
							},{
								location_code:($scope.search.zone === undefined || $scope.search.zone === null ? '' : $scope.search.zone.key)
							}, 'POST');
						}
				}
				},{
				}, 'POST');
			
		};
		
		$scope.getcircleList = function(arr,usertype,searchtype){
				$scope.searchcirclelist=[];$scope.searchdivisionlist=[];$scope.searchsubdivisionlist=[];$scope.searchomsectionlist=[];
				if($scope.search.zone === undefined || $scope.search.zone === null){
					return;
				}
				remote.load("getcirclelist", function(response){
					$scope.searchcirclelist = response.CIRCLE_LIST;
					if(arr.length > 0){
						$scope.search.circle = $filter('filter')($scope.searchcirclelist,{key:arr[2]},true)[0];
						if(usertype > 2){
							$scope.getdivisionList(arr,usertype,'search');
						}else{
							remote.load("getdivisionlist", function(response){
								$scope.searchdivisionlist = response.DIVISION_LIST;
							},{
								location_code:($scope.search.circle === undefined || $scope.search.circle === null ? '' : $scope.search.circle.key)
							}, 'POST');
						}
				}
				},{
					location_code:($scope.search.zone === undefined || $scope.search.zone === null ? '' : $scope.search.zone.key)
				}, 'POST');
			
			
		};
		
		$scope.getdivisionList = function(arr,usertype,searchtype){
				$scope.searchdivisionlist=[];$scope.searchsubdivisionlist=[];$scope.searchomsectionlist=[];
				if($scope.search.circle === undefined || $scope.search.circle === null){
					return;
				}
				remote.load("getdivisionlist", function(response){
					$scope.searchdivisionlist = response.DIVISION_LIST;
					if(arr.length > 0){
						$scope.search.division = $filter('filter')($scope.searchdivisionlist,{key:arr[3]},true)[0];
						if(usertype > 3){
							$scope.getsubdivisionList(arr,usertype,'search');
						}else{
								remote.load("getsubdivisionlist", function(response){
								$scope.searchsubdivisionlist = response.SUBDIVISION_LIST;
							},{
								location_code:($scope.search.division === undefined || $scope.search.division === null ? '' : $scope.search.division.key)
							}, 'POST');
						}
				}
				},{
					location_code:($scope.search.circle === undefined || $scope.search.circle === null ? '' : $scope.search.circle.key)
				}, 'POST');
		};
		
		$scope.getsubdivisionList = function(arr,usertype,searchtype){
				$scope.searchsubdivisionlist=[];$scope.searchomsectionlist=[];$scope.searchstationlist=[];$scope.searchfeederlist=[];
				if($scope.search.division === undefined || $scope.search.division === null){
					return;
				}
				remote.load("getsubdivisionlist", function(response){
					$scope.searchsubdivisionlist = response.SUBDIVISION_LIST;
					if(arr.length > 0){
						$scope.search.subdivision = $filter('filter')($scope.searchsubdivisionlist,{key:arr[4]},true)[0];
						if(usertype > 4){
							$scope.getomsectionList(arr,usertype,'search');
						}else{
								remote.load("getomsectionlist", function(response){
								$scope.searchomsectionlist = response.OMSECTION_LIST;
							},{
								location_code:($scope.search.subdivision === undefined || $scope.search.subdivision === null ? '' : $scope.search.subdivision.key)
							}, 'POST');
						}
				}
				},{
					location_code:($scope.search.division === undefined || $scope.search.division === null ? '' : $scope.search.division.key)
				}, 'POST');

			
		};
		
		$scope.getomsectionList = function(arr,usertype,searchtype){
				if(searchtype === 'search'){
					$scope.searchomsectionlist=[];
					if($scope.search.subdivision === undefined || $scope.search.subdivision === null){
						return;
					}
					remote.load("getomsectionlist", function(response){
						$scope.searchomsectionlist = response.OMSECTION_LIST;
						if(arr.length > 0){
							$scope.search.omsection = $filter('filter')($scope.searchomsectionlist,{key:arr[5]},true)[0];
					}
					},{
						location_code:($scope.search.subdivision === undefined || $scope.search.subdivision === null ? '' : $scope.search.subdivision.key)
					}, 'POST');
				}else{
					$scope.modalomsectionlist=[];
					if($scope.search.subdivision === undefined || $scope.search.subdivision === null){
						alert("select sub division !!!")
						return;
					}
					remote.load("getomsectionlist", function(response){
						$scope.modalomsectionlist = response.OMSECTION_LIST;
						if($scope.search.omsection != undefined || $scope.search.omsection != null){
							$scope.modal.omsection = $filter('filter')($scope.modalomsectionlist,{key:$scope.search.omsection.key},true)[0];
						}
					},{
						location_code:($scope.search.subdivision === undefined || $scope.search.subdivision === null ? '' : $scope.search.subdivision.key)
					}, 'POST');
				}
		};
		
		$scope.initialize();
		
	    $scope.load_maindashboard = function(){  
	    	
	    	
	    	var search_location = "",zone="",circle="",division="",subdivision="",omsection="",station="",feeder="",transformer="",village="";
			if($scope.search.zone != undefined || $scope.search.zone != null){zone = $scope.search.zone.key; search_location = $scope.search.zone.key;}
			if($scope.search.circle != undefined || $scope.search.circle != null){circle = $scope.search.circle.key; search_location = $scope.search.circle.key;}
			if($scope.search.division != undefined || $scope.search.division != null){division = $scope.search.division.key; search_location = $scope.search.division.key;}
			if($scope.search.subdivision != undefined || $scope.search.subdivision != null){subdivision = $scope.search.subdivision.key; search_location = $scope.search.subdivision.key;}
			if($scope.search.omsection != undefined || $scope.search.omsection != null){omsection = $scope.search.omsection.key; search_location = $scope.search.omsection.key;}
	    	
			remote.load("getmaindashboarddetails", function(response){
				$scope.MAINDASHBOARD = response.MAINDASHBOARD;
				$scope.load_charts();
			},{
				location_code:(search_location.length > 0 ? search_location :  $scope.userinfo.location_code)  
			}, 'POST');
	    	
	    };
			
			 $scope.load_charts = function(){		
	    	
	    	var chart3 = c3.generate({
    		bindto: '#chart1',
    	    data: {
    	        // iris data from R
    	        columns: [
    	            ['Authorized', $scope.MAINDASHBOARD[0].AUTHORIZED],
    	            ['Unauthorized',  $scope.MAINDASHBOARD[0].UNAUTHORIZED],
    	        ],
    	        type : 'pie',
    	        onclick: function (d, i) { console.log("onclick", d, i); },
    	        onmouseover: function (d, i) { console.log("onmouseover", d, i); },
    	        onmouseout: function (d, i) { console.log("onmouseout", d, i); }
    	    }
    	});

    	var chart4 = c3.generate({
    		bindto: '#chart2',
    	    data: {
    	        // iris data from R
    	        columns: [
    	        	['In-Use', $scope.MAINDASHBOARD[0].INUSE],
    	        	['Not In Use', $scope.MAINDASHBOARD[0].NOTINUSE],
    	            ['Dry',  $scope.MAINDASHBOARD[0].DRY],
    	        ],
    	        type : 'pie',
    	        onclick: function (d, i) { console.log("onclick", d, i); },
    	        onmouseover: function (d, i) { console.log("onmouseover", d, i); },
    	        onmouseout: function (d, i) { console.log("onmouseout", d, i); }
    	    }
    	});

    	
    	var chart5 = c3.generate({
    		bindto: '#chart3',
    	    data: {
    	        // iris data from R
    	        columns: [
    	        	['10HP And Below', $scope.MAINDASHBOARD[0].HP10ANDBELOW],
    	            ['10HP Above',  $scope.MAINDASHBOARD[0].HP10ABOVE],
    	        ],
    	        type : 'pie',
    	        onclick: function (d, i) { console.log("onclick", d, i); },
    	        onmouseover: function (d, i) { console.log("onmouseover", d, i); },
    	        onmouseout: function (d, i) { console.log("onmouseout", d, i); }
    	    }
    	});

    	
	    };
		
	    $scope.load_maindashboard();
	});
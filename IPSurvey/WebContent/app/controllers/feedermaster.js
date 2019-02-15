angular.module('ipsurveyapp.Controllers', [])
	.controller("feedermasterCtrl", function($scope,$rootScope, $http, $filter, $compile, $state,$cookies,remote,notify,store,$timeout) {
		
		console.log("Feeder Controller Initiated");
		
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
		
		$scope.modalfeederstatus = [
			{key:"Y",value:"Active"},
			{key:"N",value:"Inactive"}
		];
		
		$scope.ZONEUSER = false;
		$scope.CIRCLEUSER = false;
		$scope.DIVISIONUSER = false;
		$scope.SUBDIVISIONUSER = false;
		$scope.OMSECTIONUSER = false;
		
		var LOCATION_CODE = $scope.userinfo.location_code;
		var loc_zone= "",loc_circle= "",loc_division= "",loc_subdivision= "",loc_omsection = "";
		var loc_usertype = 0;
		var loc_arr = [];
		
		$scope.initialize = function(){
			if(LOCATION_CODE.length == 2){loc_zone=LOCATION_CODE.substr(0,2);loc_usertype=1;}
			if(LOCATION_CODE.length == 3){loc_zone=LOCATION_CODE.substr(0,2);loc_circle=LOCATION_CODE.substr(0,3);loc_usertype=2;}
			if(LOCATION_CODE.length == 5){loc_zone=LOCATION_CODE.substr(0,2);loc_circle=LOCATION_CODE.substr(0,3);loc_division=LOCATION_CODE.substr(0,5);loc_usertype=3;}
			if(LOCATION_CODE.length == 7){loc_zone=LOCATION_CODE.substr(0,2);loc_circle=LOCATION_CODE.substr(0,3);loc_division=LOCATION_CODE.substr(0,5);loc_subdivision=LOCATION_CODE.substr(0,7);loc_usertype=4;}
			if(LOCATION_CODE.length == 9){loc_zone=LOCATION_CODE.substr(0,2);loc_circle=LOCATION_CODE.substr(0,3);loc_division=LOCATION_CODE.substr(0,5);loc_subdivision=LOCATION_CODE.substr(0,7);loc_omsection=LOCATION_CODE.substr(0,9);loc_usertype=5;}
			

			if (loc_usertype == 1) {
				var loc_arr = [ loc_zone ];
				$scope.ZONEUSER = true;
				$scope.CIRCLEUSER = false;
				$scope.DIVISIONUSER = false;
				$scope.SUBDIVISIONUSER = false;
				$scope.OMSECTIONUSER = false;
				$scope.getzonelist(loc_arr, loc_usertype, 'search');
			}
			if (loc_usertype == 2) {
				var loc_arr = [ loc_zone, loc_circle ];
				$scope.ZONEUSER = false;
				$scope.CIRCLEUSER = true;
				$scope.DIVISIONUSER = false;
				$scope.SUBDIVISIONUSER = false;
				$scope.OMSECTIONUSER = false;
				$scope.getzonelist(loc_arr, loc_usertype, 'search');
			}
			if (loc_usertype == 3) {
				var loc_arr = [ loc_zone, loc_circle, loc_division ];
				$scope.ZONEUSER = false;
				$scope.CIRCLEUSER = false;
				$scope.DIVISIONUSER = true;
				$scope.SUBDIVISIONUSER = false;
				$scope.OMSECTIONUSER = false;
				$scope.getzonelist(loc_arr, loc_usertype, 'search');
			}
			if (loc_usertype == 4) {
				var loc_arr = [ loc_zone, loc_circle, loc_division,
						loc_subdivision ];
				$scope.ZONEUSER = false;
				$scope.CIRCLEUSER = false;
				$scope.DIVISIONUSER = false;
				$scope.SUBDIVISIONUSER = true;
				$scope.OMSECTIONUSER = false;
				$scope.getzonelist(loc_arr, loc_usertype, 'search');
			}
			if (loc_usertype == 5) {
				var loc_arr = [ loc_zone, loc_circle, loc_division,
						loc_subdivision, loc_omsection ];
				$scope.ZONEUSER = false;
				$scope.CIRCLEUSER = false;
				$scope.DIVISIONUSER = false;
				$scope.SUBDIVISIONUSER = false;
				$scope.OMSECTIONUSER = false;
				$scope.getzonelist(loc_arr, loc_usertype, 'search');
			}
			
			
		}
		
		$scope.getzonelist = function(arr,usertype,searchtype){
				$scope.searchzonelist=[];$scope.searchcirclelist=[];$scope.searchdivisionlist=[];$scope.searchsubdivisionlist=[];$scope.searchomsectionlist=[];
				remote.load("getzonelist", function(response){
					$scope.searchzonelist = response.ZONE_LIST;
					if(arr.length > 0){
						$scope.search.zone = $filter('filter')($scope.searchzonelist,{key:arr[0]},true)[0];
						if(usertype > 1){
							$scope.getcircleList(arr,usertype,'search');
						}else{
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
						$scope.search.circle = $filter('filter')($scope.searchcirclelist,{key:arr[1]},true)[0];
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
						$scope.search.division = $filter('filter')($scope.searchdivisionlist,{key:arr[2]},true)[0];
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
			if(searchtype === 'search'){
				$scope.searchsubdivisionlist=[];$scope.searchstationlist=[];
				if($scope.search.division === undefined || $scope.search.division === null){
					return;
				}
				remote.load("getsubdivisionlist", function(response){
					$scope.searchsubdivisionlist = response.SUBDIVISION_LIST;
					if(arr.length > 0){
						$scope.search.subdivision = $filter('filter')($scope.searchsubdivisionlist,{key:arr[3]},true)[0];
						$scope.getStationList('search');
					}
				},{
					location_code:($scope.search.division === undefined || $scope.search.division === null ? '' : $scope.search.division.key)
				}, 'POST');
			}else{
				$scope.modalsubdivisionlist=[];
				if($scope.search.division === undefined || $scope.search.division === null){
					return;
				}
				remote.load("getsubdivisionlist", function(response){
					$scope.modalsubdivisionlist = response.SUBDIVISION_LIST;
				},{
					location_code:($scope.search.division === undefined || $scope.search.division === null ? '' : $scope.search.division.key)
				}, 'POST');
			}
		};
		
		$scope.getStationList = function(searchtype,editstationcode,editfeedercode){
			if(searchtype === 'search'){
				$scope.searchstationlist=[];$scope.searchfeederlist=[];
				if($scope.search.subdivision === undefined || $scope.search.subdivision === null){
					return;
				}
				remote.load("getstationlist", function(response){
					$scope.searchstationlist = response.STATION_MASTER_DATA;
				},{
					location_code:($scope.search.subdivision === undefined || $scope.search.subdivision === null ? '' : $scope.search.subdivision.key)
				}, 'POST');
			}else{
				$scope.modalstationlist=[];$scope.modalfeederlist=[];
				if($scope.modal.subdivision === undefined || $scope.modal.subdivision === null){
					return;
				}
				remote.load("getstationlist", function(response){
					$scope.modalstationlist = response.STATION_MASTER_DATA;
					if(editstationcode.length>0){
						$scope.modal.station =  $filter('filter')($scope.modalstationlist,{key:editstationcode},true)[0];
						//$scope.getFeederList('modal',editstationcode,editfeedercode);
					}
				},{
					location_code:($scope.modal.subdivision === undefined || $scope.modal.subdivision === null ? '' : $scope.modal.subdivision.key)
				}, 'POST');
			}
		};
		
				
		$scope.initialize();
		
				
		$scope.searchfeedermasterdetails = function(){
			if($scope.search.subdivision === undefined || $scope.search.subdivision === null){
				notify.warn("Please select Sub Division !!!");
				return;
			}
			remote.load("getfeedermasterdetails", function(response){
				$scope.FEEDER_MASTER_DATA = response.data;
			},{
				location_code:($scope.search.subdivision === undefined || $scope.search.subdivision === null ? '' : $scope.search.subdivision.key),
				stationcode:($scope.search.station === undefined || $scope.search.station === null ? '' : $scope.search.station.key)
			}, 'POST');
			
		};
		
		$scope.fillfeedermasterdata = function(){
			
			console.log("FEEDER_MASTER_DATA",$scope.FEEDER_MASTER_DATA);
			$scope.SEARCHED_FEEDER_MASTER_DATA = angular.copy($scope.FEEDER_MASTER_DATA);
			// gives another movie array on change
			$scope.updateVillages = function(typed){
				// MovieRetriever could be some service returning a promise
				$scope.newfeeders = FeederMasterData.getFeeders($scope.FEEDER_MASTER_DATA,typed);
				$scope.newfeeders.then(function(data){
					$scope.SEARCHED_FEEDER_MASTER_DATA = data;
				});
			}
			
		};
		
		
		
		$scope.ROWID = "";
		$scope.imagedata = "";
		$scope.addeditfeedermaster = function(record,action){
			
			$scope.ROWID = "";
			 
			$scope.getsubdivisionList([],null,'modal');
			
			if(action === 'add'){
				
				//$scope.getcodedetails('','ST_TYPE');
				$scope.action = 'add';
				
				$scope.modal_heading = "Add Feeder Master";
				$scope.newuseridexists = true;
				
				$scope.modal.station = '' ;
				$scope.modal.feedercode = '' ;
				$scope.modal.feedername = '' ;
				
				$scope.getStationList('modal');
				
			}else{
				
				console.log(record);
				$scope.ROWID = record.row_id;
				$scope.action = 'edit';
				$scope.newuseridexists = false;
				$scope.modal_heading = "Edit Feeder Master";
				
				//$scope.getcodedetails(record.STATION_TYPE,'ST_TYPE');
				
				$timeout(function(){
					$scope.modal.subdivision = $filter('filter')($scope.modalsubdivisionlist,{key:record.SUBDIV_CODE},true)[0];
				},500);
				
				$timeout(function(){
					//$scope.modal.station = $filter('filter')($scope.modalstationlist,{key:record.STATION_CODE},true)[0];
					$scope.getStationList ('modal',record.STATION_CODE);
				},1000);
				
				$scope.modal.feederstatus = $filter('filter')($scope.modalfeederstatus,{key:record.FEEDER_STATUS},true)[0];
				
				
				$scope.modal.feedercode = record.FEEDER_CODE;
				$scope.modal.feedername = record.FEEDER_NAME;
			}
			
		};
		
		$scope.SaveRecord = function(){
			
			//var files=$('#modalchooseimage')[0].files;
			//var formdata = new FormData(); 
			
			if($scope.modal.subdivision === undefined || $scope.modal.subdivision === null){notify.warn("Please Select Subdivision");return;}
			if($scope.modal.station === undefined || $scope.modal.station === null){notify.warn("Please Select Station");return;}
			if($scope.modal.feedercode === undefined || $scope.modal.feedercode === null){notify.warn("Please Enter Feeder Code");return;}
			if($scope.modal.feedername === undefined || $scope.modal.feedername === null){notify.warn("Please Enter Feeder Name");return;}
			if($scope.modal.feederstatus === undefined || $scope.modal.feederstatus === null){notify.warn("Please Select Feeder Status");return;}
			
			
			var request = {
					rowid:$scope.ROWID,
					location_code:$scope.modal.subdivision.key,
					stationcode:$scope.modal.station.key,
					feedercode:($scope.modal.feedercode === undefined || $scope.modal.feedercode === null ? '' : $scope.modal.feedercode),
					feedername:($scope.modal.feedername === undefined || $scope.modal.feedername === null ? '' : $scope.modal.feedername),
					feederstatus:$scope.modal.feederstatus.key,
					userid:$scope.userinfo.username
			};
			
			console.log("request",request);
			
			remote.load("upsertfeedermaster", function(response){
				console.log("upsertfeedermaster",response);
				if(response.status === 'success'){
					$timeout(function(){
						$('#feedermaster-addedit-modal').modal('toggle');
						$scope.searchfeedermasterdetails();
					},2000);
				}
			},request, 'POST');
		};
		
		
		
			
	});
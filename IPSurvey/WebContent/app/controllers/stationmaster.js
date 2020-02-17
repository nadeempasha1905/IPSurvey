/**
 * 
 */

angular.module('ipsurveyapp.Controllers', [])
	.controller("stationmasterCtrl", function($scope,$rootScope, $http, $filter, $compile, $state,$cookies,remote,notify,store,$timeout) {
		
		console.log("Station Controller Initiated");
		
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
		
		$scope.getcodedetails = function(value,searchtype){
			
			
			if(searchtype === 'ST_TYPE'){
				$scope.modalstationtypes = [];
				remote.load("getcodedetails", function(response){
					$scope.modalstationtypes = response.CODES_LIST;
					if(value != null || value.length >0){
						$scope.modal.stationtype =  $filter('filter')($scope.modalstationtypes,{key:value},true)[0];
					}
				},{
					code_type:'ST_TYPE'
				}, 'POST');
			}
			
			if(searchtype === 'STN_SUB_TYPE'){
				$scope.modalstationsubtypelist = [];
				remote.load("getcodedetails", function(response){
					$scope.modalstationsubtypelist = response.CODES_LIST;
					if(value != null || value.length >0){
						$scope.modal.stationsubtype =  $filter('filter')($scope.modalstationsubtypelist,{key:value},true)[0];
					}
				},{
					code_type:'STN_SUB_TYPE'
				}, 'POST');
				
			}
			
		};
		
		
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
				$scope.searchsubdivisionlist=[];$scope.searchomsectionlist=[];
				if($scope.search.division === undefined || $scope.search.division === null){
					return;
				}
				remote.load("getsubdivisionlist", function(response){
					$scope.searchsubdivisionlist = response.SUBDIVISION_LIST;
					if(arr.length > 0){
						$scope.search.subdivision = $filter('filter')($scope.searchsubdivisionlist,{key:arr[3]},true)[0];
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
					$scope.modal.subdivision = $filter('filter')($scope.modalsubdivisionlist,{key:$scope.userinfo.location_code},true)[0];
				},{
					location_code:($scope.search.division === undefined || $scope.search.division === null ? '' : $scope.search.division.key)
				}, 'POST');
			}
		};
		
				
		$scope.initialize();
		
				
		$scope.searchstationmasterdetails = function(){
			if($scope.search.subdivision === undefined || $scope.search.subdivision === null){
				notify.warn("Please select Sub Division !!!");
				return;
			}
			remote.load("getstationmasterdetails", function(response){
				$scope.STATION_MASTER_DATA = response.data;
			},{
				location_code:($scope.search.subdivision === undefined || $scope.search.subdivision === null ? '' : $scope.search.subdivision.key)
			}, 'POST');
			
		};
		
		$scope.fillstationmasterdata = function(){
			
			console.log("STATION_MASTER_DATA",$scope.STATION_MASTER_DATA);
			$scope.SEARCHED_STATION_MASTER_DATA = angular.copy($scope.STATION_MASTER_DATA);
			// gives another movie array on change
			$scope.updateVillages = function(typed){
				// MovieRetriever could be some service returning a promise
				$scope.newstations = StationMasterData.getStations($scope.STATION_MASTER_DATA,typed);
				$scope.newstations.then(function(data){
					$scope.SEARCHED_STATION_MASTER_DATA = data;
				});
			}
			
		};
		
/*		$scope.getcodedetails = function(value,searchtype){
			
			if(searchtype === 'ST_TYPE'){
				$scope.modalstationtypes = [];
				remote.load("getcodedetails", function(response){
					$scope.modalstationtypes = response.CODES_LIST;
					if(value != null || value.length >0){
						$scope.modal.stationtype =  $filter('filter')($scope.modalstationtypes,{key:value},true)[0];
					}
				},{
					code_type:'ST_TYPE'
				}, 'POST');
			}
			
			if(searchtype === 'STN_SUB_TYPE'){
				
				remote.load("getcodedetails", function(response){
					$scope.modalstationsubtypelist = response.CODES_LIST;
				},{
					code_type:'STN_SUB_TYPE'
				}, 'POST');
				
			}
			
		};*/
		
		
		$scope.ROWID = "";
		$scope.imagedata = "";
		$scope.addeditstationmaster = function(record,action){
			
			$scope.ROWID = "";
			 
			$scope.getsubdivisionList([],null,'modal');
			if(action === 'add'){
				
				$scope.getcodedetails('','ST_TYPE');
				$scope.getcodedetails('','STN_SUB_TYPE');
				
				$scope.action = 'add';
				
				$scope.modal_heading = "Add Station Master";
				$scope.newuseridexists = true;
				
				$scope.modal.stationcode = '' ;
				$scope.modal.stationname = '' ;
				
			}else if(action === 'edit'){
				
				console.log(record);
				$scope.ROWID = record.row_id;
				$scope.action = 'edit';
				$scope.newuseridexists = false;
				$scope.modal_heading = "Edit Station Master";
				
				$scope.getcodedetails(record.STATION_TYPE,'ST_TYPE');
				$scope.getcodedetails(record.STATION_SUB_TYPE,'STN_SUB_TYPE');
				
				$timeout(function(){
					$scope.modal.subdivision = $filter('filter')($scope.modalsubdivisionlist,{key:record.SUBDIV_CODE},true)[0];
				},1000);
				
				$scope.modal.stationcode = record.STATION_CODE;
				$scope.modal.stationname = record.STATION_NAME;
			}else if(action === 'delete'){
				
				
				$scope.ROWID = record.row_id;
				
				var sts = confirm("Are You Sure To Delete ?");
				console.log("sts",sts);
				
				if(sts){
					var request = {
							rowid:$scope.ROWID,
							location_code:record.SUBDIV_CODE,
							stationtype:record.STATION_TYPE,
							stationsubtype:record.STATION_SUB_TYPE,
							stationcode:record.STATION_CODE,
							stationname:record.STATION_NAME,
							deleteflag:'Y',
							userid:$scope.userinfo.username
					};
					
					console.log("request",request);
					
					remote.load("upsertstationmaster", function(response){
						console.log("upsertstationmaster",response);
						if(response.status === 'success'){
							$timeout(function(){
								//$('#stationmaster-addedit-modal').modal('toggle');
								$scope.searchstationmasterdetails();
							},2000);
						}
					},request, 'POST');
				}
				
			}
			
		};
		
		$scope.SaveRecord = function(){
			
			//var files=$('#modalchooseimage')[0].files;
			//var formdata = new FormData(); 
			
			if($scope.modal.subdivision === undefined || $scope.modal.subdivision === null){notify.warn("Please Select Subdivision");return;}
			if($scope.modal.stationtype === undefined || $scope.modal.stationtype === null){notify.warn("Please Select Station type");return;}
			if($scope.modal.stationsubtype === undefined || $scope.modal.stationsubtype === null){notify.warn("Please Select Station Sub type");return;}
			if($scope.modal.stationcode === undefined || $scope.modal.stationcode === null){notify.warn("Please Enter Station Code");return;}
			if($scope.modal.stationname === undefined || $scope.modal.stationname === null){notify.warn("Please Enter Station Name");return;}
			
			
			var request = {
					rowid:$scope.ROWID,
					location_code:$scope.modal.subdivision.key,
					stationtype:$scope.modal.stationtype.key,
					stationsubtype:$scope.modal.stationsubtype.key,
					stationcode:($scope.modal.stationcode === undefined || $scope.modal.stationcode === null ? '' : $scope.modal.stationcode),
					stationname:($scope.modal.stationname === undefined || $scope.modal.stationname === null ? '' : $scope.modal.stationname),
					deleteflag:'N',
					userid:$scope.userinfo.username
			};
			
			console.log("request",request);
			
			remote.load("upsertstationmaster", function(response){
				console.log("upsertstationmaster",response);
				if(response.status === 'success'){
					$timeout(function(){
						$('#stationmaster-addedit-modal').modal('toggle');
						$scope.searchstationmasterdetails();
					},2000);
				}
			},request, 'POST');
		};
		
		
		$scope.Reset = function(){
			$scope.STATION_MASTER_DATA = [];
			$scope.search = {};
			$scope.userinfo = {};
			$scope.modal={};
			$scope.ZONEUSER = false;
			$scope.CIRCLEUSER = false;
			$scope.DIVISIONUSER = false;
			$scope.SUBDIVISIONUSER = false;
			$scope.OMSECTIONUSER = false;
			$scope.initialize();
			
		};
			
	});
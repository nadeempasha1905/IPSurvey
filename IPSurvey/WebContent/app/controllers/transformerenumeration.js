/**
 * 
 */

angular.module('ipsurveyapp.Controllers', [])
	.controller("transformerenumerationCtrl",function($scope,$rootScope, $http, $filter, $compile, $state,$cookies,remote,notify,store,$timeout,TransformerMasterData) {
		
		console.log("transformerenumeration Controller Initiated");
		
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
				$scope.OMSECTIONUSER = true;
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
				$scope.searchsubdivisionlist=[];$scope.searchomsectionlist=[];$scope.searchstationlist=[];$scope.searchfeederlist=[];
				if($scope.search.division === undefined || $scope.search.division === null){
					return;
				}
				remote.load("getsubdivisionlist", function(response){
					$scope.searchsubdivisionlist = response.SUBDIVISION_LIST;
					if(arr.length > 0){
						$scope.search.subdivision = $filter('filter')($scope.searchsubdivisionlist,{key:arr[3]},true)[0];
						$scope.getStationList();
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
							$scope.search.omsection = $filter('filter')($scope.searchomsectionlist,{key:arr[4]},true)[0];
					}
					},{
						location_code:($scope.search.subdivision === undefined || $scope.search.subdivision === null ? '' : $scope.search.subdivision.key)
					}, 'POST');
				}else{
					$scope.gettransformermasterdata();
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
		
		$scope.getStationList = function(searchtype){
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
				if($scope.search.subdivision === undefined || $scope.search.subdivision === null){
					return;
				}
				remote.load("getstationlist", function(response){
					$scope.modalstationlist = response.STATION_MASTER_DATA;
				},{
					location_code:($scope.search.subdivision === undefined || $scope.search.subdivision === null ? '' : $scope.search.subdivision.key)
				}, 'POST');
			}
			
			
		};
		
		$scope.getFeederList = function(searchtype){
			if(searchtype === 'search'){
				$scope.searchfeederlist=[];
				if($scope.search.subdivision === undefined || $scope.search.subdivision === null){return;}
				if($scope.search.station === undefined || $scope.search.station === null){return;}
				remote.load("getfeederlist", function(response){
					$scope.searchfeederlist = response.FEEDER_MASTER_DATA;
				},{
					location_code:($scope.search.subdivision === undefined || $scope.search.subdivision === null ? '' : $scope.search.subdivision.key),
					station_code:($scope.search.station === undefined || $scope.search.station === null ? '' : $scope.search.station.key)
				}, 'POST');
			}else{
				$scope.modalfeederlist=[];
				if($scope.search.subdivision === undefined || $scope.search.subdivision === null){return;}
				if($scope.modal.station === undefined || $scope.modal.station === null){return;}
				remote.load("getfeederlist", function(response){
					$scope.modalfeederlist = response.FEEDER_MASTER_DATA;
				},{
					location_code:($scope.search.subdivision === undefined || $scope.search.subdivision === null ? '' : $scope.search.subdivision.key),
					station_code:($scope.modal.station === undefined || $scope.modal.station === null ? '' : $scope.modal.station.key)
				}, 'POST');
			}
			
			
		};
		
		$scope.SearchTransformerEnumDetails = function(){
			if($scope.search.omsection === undefined || $scope.search.omsection === null){
				notify.warn("Please select O&M Section !!!");
				return;
			}
			remote.load("gettransformerenumerationdetails", function(response){
				$scope.TRANSFORMER_ENUM_DATA = response.data;
			},{
				location_code:($scope.search.omsection === undefined || $scope.search.omsection === null ? '' : $scope.search.omsection.key),
				station_code:($scope.search.station === undefined || $scope.search.station === null ? '' : $scope.search.station.key),
				feeder_code:($scope.search.feeder === undefined || $scope.search.feeder === null ? '' : $scope.search.feeder.key)
			}, 'POST');
			
		};
		
		$scope.gettransformermasterdatalist = [];
		$scope.TRANSFORMER_MASTER_DATA = [];
		$scope.gettransformermasterdata = function(){
			$scope.gettransformermasterdatalist = [];
			$scope.TRANSFORMER_MASTER_DATA = [];
			if($scope.search.subdivision === undefined || $scope.search.subdivision === null){
				notify("Please select Sub-Division !!!");
				return;
			}
			
			remote.load("gettransformermasterdata", function(response){
				$scope.gettransformermasterdatalist = response.TRANSFORMER_MASTER_DATA;
				$scope.gettransformermasterdatalist.map(function(e,index){
					$scope.TRANSFORMER_MASTER_DATA.push(e.TM_TRANSFORMER_NAME);
				});
				$scope.filltransformerdata();
			},{
				location_code:($scope.search.subdivision === undefined || $scope.search.subdivision === null ? '' : $scope.search.subdivision.key)
			}, 'POST');
		};
		
		$scope.filltransformerdata = function(){
			console.log("TRANSFORMER_MASTER_DATA",$scope.TRANSFORMER_MASTER_DATA);
			$scope.SEARCHED_TRANSFORMER_MASTER_DATA = angular.copy($scope.TRANSFORMER_MASTER_DATA);
			// gives another movie array on change
			$scope.updateTransformers = function(typed){
				// MovieRetriever could be some service returning a promise
				$scope.newtransformers = TransformerMasterData.getTransformers($scope.TRANSFORMER_MASTER_DATA,typed);
				$scope.newtransformers.then(function(data){
					$scope.SEARCHED_TRANSFORMER_MASTER_DATA = data;
				});
			}
		};
		
		$scope.ROWID = "";
		$scope.imagedata = "";
		$scope.addeditTransformerEnumeration = function(record,action){
			
			$scope.ROWID = "";
			$scope.imagedata = "";
			 $('#uploadPreview').attr('src', null);
			 $scope.modal.changeimage = false;
			 $scope.modal.chooseimage = null;
			 $('#modalchooseimage').val('');
			 
			$scope.getomsectionList([],null,'modal');
			$scope.getStationList('modal');
			
			if(action === 'add'){
				$scope.action = 'add';
				
				$scope.modal_heading = "Add Transformer Enumeration Data";
				$scope.newuseridexists = true;
				
				
				$scope.search.autocompletetransformername = '';
				$scope.modal.latitude = '' ;
				$scope.modal.longitude = '' ;
				$scope.modal.altitude = '';
				$scope.modal.remarks = '';
				
			}else{
				
/*				console.log(record);
				$scope.ROWID = record.row_id;
				$scope.action = 'edit';
				$scope.newuseridexists = false;
				$scope.modal_heading = "Edit Transformer Enumeration Data";
				
				$scope.modal.omsection = $filter('filter')($scope.modalomsectionlist,{key:record.VE_LOCATION_CODE},true)[0];
				$scope.search.autocompletevillagename = record.VE_VILLAGE_NAME;
				$scope.modal.latitude = record.VE_LATTITUDE;
				$scope.modal.longitude = record.VE_LONGITUDE;
				$scope.modal.altitude = record.VE_ALTITUDE;
				$scope.modal.remarks = record.VE_REMARKS;
				
				if(record.VE_IMAGE_PATH.length >0 || record.VE_IMAGE_PATH != undefined || record.VE_IMAGE_PATH != null){
					remote.load("getimagedata", function(response){
						console.log("getimagedata",response);
						$scope.imagedata = response.encodedBase64;
					},{
						filename:record.VE_IMAGE_PATH
					}, 'POST');
				}*/
			}
			
		};
		
		$scope.initialize();
		
	})
	
.factory('TransformerMasterData', function($http, $q, $timeout){
	  var TransformerMasterData = new Object();
	
	  TransformerMasterData.getTransformers = function(transformers_array,i) {
	    var transformerdata = $q.defer();
	    var transformers;
	
	    if(i && i.indexOf('T')!=-1)
	    	transformers=transformers_array;
	    else
	    	transformers=transformers_array;
	
	    $timeout(function(){
	    	transformerdata.resolve(transformers);
	    },1000);
	
	    return transformerdata.promise
	  }
	  return TransformerMasterData;
	})
	
	;
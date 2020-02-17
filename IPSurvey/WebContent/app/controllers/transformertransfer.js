/**
 * 
 */

angular.module('ipsurveyapp.Controllers', [])
	.controller("transformertransferCtrl", function($scope,$rootScope, $http, $filter, $compile, $state,$cookies,remote,notify,store,$timeout) {
		
		console.log("transformertransferCtrl Controller Initiated");
		
		var LOCATION_CODE = store.get('LOCATION_CODE');
		$rootScope.LOCATION_CODE = LOCATION_CODE;
		
		$scope.transfer_from = {};
		$scope.transfer_to = {};
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
		
		$scope.modal.servicedate = moment(new Date()).format("DD/MM/YYYY").toString();
		$scope.modal.inspectiondate = moment(new Date()).format("DD/MM/YYYY").toString();
		
		$scope.modalmeterstatus = [
			{key:"Y",value:"YES"},
			{key:"N",value:"NO"}
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
		$scope.SUB_DIV_TEMP_TRANSFER = "";
		
		$scope.initialize = function(){
			
			$scope.transfer_from_omsectionlist=[];
			$scope.transfer_to_omsectionlist=[];
			$scope.transfer_from_stationlist=[];
			$scope.transfer_to_stationlist=[];
			$scope.transfer_from_stationlist=[];
			$scope.transfer_to_feederlist=[];
			$scope.transfer_from_getFeederList=[];
			$scope.transfer_to_getFeederList=[];
			$scope.getenumeratedtransformers_transfer_from = [];
			$scope.getenumeratedtransformers_transfer_to = [];
			
			if(LOCATION_CODE.length == 2){loc_zone=LOCATION_CODE.substr(0,2);loc_usertype=1;}
			if(LOCATION_CODE.length == 3){loc_zone=LOCATION_CODE.substr(0,2);loc_circle=LOCATION_CODE.substr(0,3);loc_usertype=2;}
			if(LOCATION_CODE.length == 5){loc_zone=LOCATION_CODE.substr(0,2);loc_circle=LOCATION_CODE.substr(0,3);loc_division=LOCATION_CODE.substr(0,5);loc_usertype=3;}
			if(LOCATION_CODE.length == 7){loc_zone=LOCATION_CODE.substr(0,2);loc_circle=LOCATION_CODE.substr(0,3);loc_division=LOCATION_CODE.substr(0,5);loc_subdivision=LOCATION_CODE.substr(0,7);loc_usertype=4;}
			if(LOCATION_CODE.length == 9){loc_zone=LOCATION_CODE.substr(0,2);loc_circle=LOCATION_CODE.substr(0,3);loc_division=LOCATION_CODE.substr(0,5);loc_subdivision=LOCATION_CODE.substr(0,7);loc_omsection=LOCATION_CODE.substr(0,9);loc_usertype=5;}
			
			if (loc_usertype == 4) {
				var loc_arr = [ loc_zone, loc_circle, loc_division,
						loc_subdivision ];
				$scope.ZONEUSER = false;
				$scope.CIRCLEUSER = false;
				$scope.DIVISIONUSER = false;
				$scope.SUBDIVISIONUSER = true;
				$scope.OMSECTIONUSER = false;
			
				$scope.SUB_DIV_TEMP_TRANSFER  = loc_subdivision;
				
				//$scope.getzonelist(loc_arr, loc_usertype, 'search');
				$scope.getomsectionList(loc_arr, loc_usertype, 'transfer_from');
				$scope.getomsectionList(loc_arr, loc_usertype, 'transfer_to');
				$scope.getStationList('transfer_from');
				$scope.getStationList('transfer_to');
				
				
			}
		}
		
		
		$scope.getomsectionList = function(arr,usertype,searchtype){
			if(searchtype === 'transfer_from'){
				$scope.transfer_from_omsectionlist=[];
				
				if($scope.SUB_DIV_TEMP_TRANSFER === undefined || $scope.SUB_DIV_TEMP_TRANSFER === null){
					return;
				}
				remote.load("getomsectionlist", function(response){
					$scope.transfer_from_omsectionlist = response.OMSECTION_LIST;
				},{
					location_code:($scope.SUB_DIV_TEMP_TRANSFER === undefined || $scope.SUB_DIV_TEMP_TRANSFER === null ? '' : $scope.SUB_DIV_TEMP_TRANSFER)
				}, 'POST');
			}else if(searchtype === 'transfer_to'){
				$scope.transfer_to_omsectionlist=[];
				
				if($scope.SUB_DIV_TEMP_TRANSFER === undefined || $scope.SUB_DIV_TEMP_TRANSFER === null){
					return;
				}
				remote.load("getomsectionlist", function(response){
					$scope.transfer_to_omsectionlist = response.OMSECTION_LIST;
				},{
					location_code:($scope.SUB_DIV_TEMP_TRANSFER === undefined || $scope.SUB_DIV_TEMP_TRANSFER === null ? '' : $scope.SUB_DIV_TEMP_TRANSFER)
				}, 'POST');
			}
	};
	
	$scope.getStationList = function(searchtype){
		if(searchtype === 'transfer_from'){
			$scope.transfer_from_stationlist=[];
			if($scope.SUB_DIV_TEMP_TRANSFER === undefined || $scope.SUB_DIV_TEMP_TRANSFER === null){
				return;
			}
			remote.load("getstationlist", function(response){
				$scope.transfer_from_stationlist = response.STATION_MASTER_DATA;
			},{
				location_code:($scope.SUB_DIV_TEMP_TRANSFER === undefined || $scope.SUB_DIV_TEMP_TRANSFER === null ? '' : $scope.SUB_DIV_TEMP_TRANSFER)
			}, 'POST');
		}else if(searchtype === 'transfer_to'){
			$scope.transfer_to_feederlist=[];
			if($scope.SUB_DIV_TEMP_TRANSFER === undefined || $scope.SUB_DIV_TEMP_TRANSFER === null){
				return;
			}
			remote.load("getstationlist", function(response){
				$scope.transfer_to_stationlist = response.STATION_MASTER_DATA;
			},{
				location_code:($scope.SUB_DIV_TEMP_TRANSFER === undefined || $scope.SUB_DIV_TEMP_TRANSFER === null ? '' : $scope.SUB_DIV_TEMP_TRANSFER)
			}, 'POST');
		}
	};
	
	$scope.getFeederList = function(searchtype){
		if(searchtype === 'transfer_from'){
			$scope.transfer_from_getFeederList=[];
			$scope.searchfeederlist=[];
			if($scope.SUB_DIV_TEMP_TRANSFER === undefined || $scope.SUB_DIV_TEMP_TRANSFER === null){return;}
			if($scope.transfer_from.station === undefined || $scope.transfer_from.station === null){return;}
			remote.load("getfeederlist", function(response){
				$scope.transfer_from_getFeederList = response.FEEDER_MASTER_DATA;
			},{
				location_code:($scope.SUB_DIV_TEMP_TRANSFER === undefined || $scope.SUB_DIV_TEMP_TRANSFER === null ? '' : $scope.SUB_DIV_TEMP_TRANSFER),
				station_code:($scope.transfer_from.station === undefined || $scope.transfer_from.station === null ? '' : $scope.transfer_from.station.key)
			}, 'POST');
		}else if(searchtype === 'transfer_to'){
			$scope.transfer_to_getFeederList=[];
			if($scope.SUB_DIV_TEMP_TRANSFER === undefined || $scope.SUB_DIV_TEMP_TRANSFER === null){return;}
			if($scope.transfer_to.station === undefined || $scope.transfer_to.station === null){return;}
			remote.load("getfeederlist", function(response){
				$scope.transfer_to_getFeederList = response.FEEDER_MASTER_DATA;
			},{
				location_code:($scope.SUB_DIV_TEMP_TRANSFER === undefined || $scope.SUB_DIV_TEMP_TRANSFER === null ? '' : $scope.SUB_DIV_TEMP_TRANSFER),
				station_code:($scope.transfer_to.station === undefined || $scope.transfer_to.station === null ? '' : $scope.transfer_to.station.key)
			}, 'POST');
		}
	};
	
	$scope.getenumeratedtransformerslist = function(searchtype){
		if(searchtype === 'transfer_from'){
			$scope.getenumeratedtransformers_transfer_from = [];
			if($scope.transfer_from.omsection === undefined || $scope.transfer_from.omsection === null){
				return;
			}
			remote.load("getenumeratedtransformerslist", function(response){
				$scope.getenumeratedtransformers_transfer_from = response.TRANSFORMER_ENUM_DATA;
			},{
				location_code:($scope.transfer_from.omsection === undefined || $scope.transfer_from.omsection === null ? '' : $scope.transfer_from.omsection.key),
				station_code:($scope.transfer_from.station === undefined || $scope.transfer_from.station === null ? '' : $scope.transfer_from.station.key),
				feeder_code:($scope.transfer_from.feeder === undefined || $scope.transfer_from.feeder === null ? '' : $scope.transfer_from.feeder.key)
			}, 'POST');
		}else if(searchtype === 'transfer_to'){
			$scope.getenumeratedtransformers_transfer_to = [];
			if($scope.transfer_to.omsection === undefined || $scope.transfer_to.omsection === null){
				return;
			}
			remote.load("getenumeratedtransformerslist", function(response){
				$scope.getenumeratedtransformers_transfer_to = response.TRANSFORMER_ENUM_DATA;
			},{
				location_code:($scope.transfer_to.omsection === undefined || $scope.transfer_to.omsection === null ? '' : $scope.transfer_to.omsection.key),
				/*location_code:($scope.SUB_DIV_TEMP_TRANSFER === undefined || $scope.SUB_DIV_TEMP_TRANSFER === null ? '' : $scope.SUB_DIV_TEMP_TRANSFER),*/
				station_code:($scope.transfer_to.station === undefined || $scope.transfer_to.station === null ? '' : $scope.transfer_to.station.key),
				feeder_code:($scope.transfer_to.feeder === undefined || $scope.transfer_to.feeder === null ? '' : $scope.transfer_to.feeder.key)
			}, 'POST');
		}
	};
	
	$scope.transfertransformer = function(){
		
		var transfer_from_omsection="",transfer_from_station="",transfer_from_feeder="",transfer_from_transformers="",
			transfer_to_omsection="",transfer_to_station="",transfer_to_feeder="",transfer_to_transformers="";
		
		if($scope.transfer_from.omsection === undefined || $scope.transfer_from.omsection === null){notify.warn("Select **Transfer From Section** !!!");return;}
		if($scope.transfer_from.station === undefined || $scope.transfer_from.station === null){notify.warn("Select **Transfer From Station** !!!");return;}
		if($scope.transfer_from.feeder === undefined || $scope.transfer_from.feeder === null){notify.warn("Select **Transfer From Feeder** !!!");return;}
		if($scope.transfer_from.transformers === undefined || $scope.transfer_from.transformers === null){notify.warn("Select **Transfer From Transformer** !!!");return;}
		if($scope.transfer_to.omsection === undefined || $scope.transfer_to.omsection === null){notify.warn("Select **Transfer To Section** !!!");return;}
		if($scope.transfer_to.station === undefined || $scope.transfer_to.station === null){notify.warn("Select **Transfer To Station** !!!");return;}
		if($scope.transfer_to.feeder === undefined || $scope.transfer_to.feeder === null){notify.warn("Select **Transfer To Feeder** !!!");return;}
		if($scope.transfer_to.transformers === undefined || $scope.transfer_to.transformers === null){notify.warn("Select **Transfer To Transformer** !!!");return;}
		
		var transfer_from_omsection		= $scope.transfer_from.omsection === undefined || $scope.transfer_from.omsection === null ? '' : $scope.transfer_from.omsection.key ;
		var transfer_from_station		= $scope.transfer_from.station === undefined || $scope.transfer_from.station === null ? '' : $scope.transfer_from.station.key ;
		var transfer_from_feeder 		= $scope.transfer_from.feeder === undefined || $scope.transfer_from.feeder === null ? '' : $scope.transfer_from.feeder.key ;
		var transfer_from_transformers  = $scope.transfer_from.transformers === undefined || $scope.transfer_from.transformers === null ? '' : $scope.transfer_from.transformers.key ;
		var transfer_to_omsection		= $scope.transfer_to.omsection === undefined || $scope.transfer_to.omsection === null ? '' : $scope.transfer_to.omsection.key ;
		var transfer_to_station			= $scope.transfer_to.station === undefined || $scope.transfer_to.station === null ? '' : $scope.transfer_to.station.key ;
		var transfer_to_feeder 		    = $scope.transfer_to.feeder === undefined || $scope.transfer_to.feeder === null ? '' : $scope.transfer_to.feeder.key ;
		var transfer_to_transformers    = $scope.transfer_to.transformers === undefined || $scope.transfer_to.transformers === null ? '' : $scope.transfer_to.transformers.key ;
		
		
		var request = {
				transfer_from_omsection:transfer_from_omsection,
				transfer_from_station:transfer_from_station,
				transfer_from_feeder:transfer_from_feeder,
				transfer_from_transformers:transfer_from_transformers,
				transfer_to_omsection:transfer_to_omsection,
				transfer_to_station:transfer_to_station,
				transfer_to_feeder:transfer_to_feeder,
				transfer_to_transformers:transfer_to_transformers,
				userid:$scope.userinfo.username
		};
			console.log("request",request);
		remote.load("dotransformertransfer", function(response){
			if(response.status === 'success'){
				$timeout(function(){
					$scope.clear();
				},200);
			}
			console.log("dotransformertransfer",response);
		},request, 'POST');
		
	};
	
	$scope.to_transfer_autoselect = function(){
		
		$scope.transfer_to.omsection = $scope.transfer_from.omsection;
		
		$timeout(function(){
			$scope.transfer_to.omsection =  $filter('filter')($scope.transfer_to_omsectionlist,{key:$scope.transfer_from.omsection.key},true)[0];
			$scope.transfer_to.station =  $filter('filter')($scope.transfer_to_stationlist,{key:$scope.transfer_from.station.key},true)[0];
			
			$scope.getFeederList('transfer_to');
			$timeout(function(){
				$scope.transfer_to.feeder =  $filter('filter')($scope.transfer_to_getFeederList,{key:$scope.transfer_from.feeder.key},true)[0];
				
				$scope.getenumeratedtransformerslist('transfer_to');
				$timeout(function(){
					$scope.transfer_to.transformers =  $filter('filter')($scope.getenumeratedtransformers_transfer_to,{value:$scope.transfer_from.transformers.value},true)[0];
				},500);
				
			},500);
		},1000);
	};
	
	$scope.clear = function(){
		$scope.transfer_from = {};
		$scope.transfer_to = {};
		$scope.initialize();
	};
	
	$scope.initialize();
		
	});
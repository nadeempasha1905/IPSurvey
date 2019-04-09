/**
 * 
 */

angular.module('ipsurveyapp.Controllers', [])
	.controller("reportsCtrl", function($scope,$rootScope, $http, $filter, $compile, $state,$cookies,remote,notify,store,$timeout,$window) {
		
		console.log("reports Controller Initiated");
		
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
		
		$scope.search.reportdate = moment(new Date()).format("DD/MM/YYYY").toString();
		$scope.modal.servicedate = moment(new Date()).format("DD/MM/YYYY").toString();
		$scope.modal.inspectiondate = moment(new Date()).format("DD/MM/YYYY").toString();
		
		$scope.modalmeterstatus = [
			{key:"Y",value:"YES"},
			{key:"N",value:"NO"}
			];
		
		$scope.reportslist = [
			{key:"7",value:"Transformer Enumeration"},
			{key:"8",value:"Pole Enumeration"},
			{key:"9",value:"IPset Enumeration"},
			{key:"5",value:"Progress - Section Wise"},
			{key:"6",value:"Progress - User Wise"},
			{key:"11",value:"New Progress - Section Wise"},
			/*{key:"21",value:"Annexure-1"},
			{key:"22",value:"Annexure-2"},
			{key:"23",value:"Annexure-3"},
			{key:"24",value:"Annexure-4"},
			{key:"25",value:"Annexure-5"}*/
			
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
						$scope.getStationList('search');
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
				if($scope.search.subdivision === undefined || $scope.search.subdivision === null){
					return;
				}
				remote.load("getstationlist", function(response){
					$scope.modalstationlist = response.STATION_MASTER_DATA;
					if(editstationcode != null){
						$scope.modal.station =  $filter('filter')($scope.modalstationlist,{key:editstationcode},true)[0];
						$scope.getFeederList('modal',editstationcode,editfeedercode);
					}
				},{
					location_code:($scope.search.subdivision === undefined || $scope.search.subdivision === null ? '' : $scope.search.subdivision.key)
				}, 'POST');
			}
		};
		
		$scope.getFeederList = function(searchtype,editstationcode,editfeedercode){
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
					if(editfeedercode != null){
						$scope.modal.feeder  =  $filter('filter')($scope.modalfeederlist,{key:editfeedercode},true)[0];
					}
				},{
					location_code:($scope.search.subdivision === undefined || $scope.search.subdivision === null ? '' : $scope.search.subdivision.key),
					station_code:($scope.modal.station === undefined || $scope.modal.station === null ? '' : $scope.modal.station.key)
				}, 'POST');
			}
		};
		
		$scope.getenumeratedvillageslist = function(value,searchtype){
			if(searchtype === 'search'){
				$scope.getenumeratedvillages = [];
				if($scope.search.omsection === undefined || $scope.search.omsection === null){
					//notify.warn("Please select O&M Section !!!");
					return;
				}
				remote.load("getenumeratedvillageslist", function(response){
					$scope.getenumeratedvillages = response.VILLAGE_ENUM_DATA;
				},{
					location_code:($scope.search.omsection === undefined || $scope.search.omsection === null ? '' : $scope.search.omsection.key)
				}, 'POST');
			}else{
				$scope.getmodalenumeratedvillages = [];
				if($scope.modal.omsection === undefined || $scope.modal.omsection === null){
					//notify.warn("Please select O&M Section !!!");
					return;
				}
				remote.load("getenumeratedvillageslist", function(response){
					$scope.getmodalenumeratedvillages = response.VILLAGE_ENUM_DATA;
					if(value != null || value.length >0){
						$scope.modal.village =  $filter('filter')($scope.getmodalenumeratedvillages,{key:value},true)[0];
					}
				},{
					location_code:($scope.modal.omsection === undefined || $scope.modal.omsection === null ? '' : $scope.modal.omsection.key)
				}, 'POST');
			}

		};
		
		$scope.getenumeratedtransformerslist = function(value,searchtype){
			if(searchtype === 'search'){
				$scope.getenumeratedtransformers = [];
				if($scope.search.omsection === undefined || $scope.search.omsection === null){
					//notify.warn("Please select O&M Section !!!");
					return;
				}
				remote.load("getenumeratedtransformerslist", function(response){
					$scope.getenumeratedtransformers = response.TRANSFORMER_ENUM_DATA;
				},{
					location_code:($scope.search.omsection === undefined || $scope.search.omsection === null ? '' : $scope.search.omsection.key)
				}, 'POST');
			}else{
				$scope.getmodalenumeratedtransformers = [];
				if($scope.modal.omsection === undefined || $scope.modal.omsection === null){
					//notify.warn("Please select O&M Section !!!");
					return;
				}
				remote.load("getenumeratedtransformerslist", function(response){
					$scope.getmodalenumeratedtransformers = response.TRANSFORMER_ENUM_DATA;
					if(value != null || value.length >0){
						$scope.modal.transformers =  $filter('filter')($scope.getmodalenumeratedtransformers,{key:value},true)[0];
					}
				},{
					location_code:($scope.modal.omsection === undefined || $scope.modal.omsection === null ? '' : $scope.modal.omsection.key)
				}, 'POST');
			}
		};
		
		$scope.SearchIpsetEnumDetails = function(){
			if($scope.search.omsection === undefined || $scope.search.omsection === null){
				notify.warn("Please select O&M Section !!!");
				return;
			}
			remote.load("getipsetenumerationdetails", function(response){
				$scope.IPSET_ENUM_DATA = response.data;
			},{
				location_code:($scope.search.omsection === undefined || $scope.search.omsection === null ? '' : $scope.search.omsection.key),
				station_code:($scope.search.station === undefined || $scope.search.station === null ? '' : $scope.search.station.key),
				feeder_code:($scope.search.feeder === undefined || $scope.search.feeder === null ? '' : $scope.search.feeder.key),
				transformer_code:($scope.search.transformers === undefined || $scope.search.transformers === null ? '' : $scope.search.transformers.key),
				rr_no:($scope.search.rrnumber === undefined || $scope.search.rrnumber === null || $scope.search.rrnumber === '' ? '' : $scope.search.rrnumber),
				village:($scope.search.village === undefined || $scope.search.village === null ? '' : $scope.search.village.key)
			}, 'POST');
			
		};
		
		$scope.ResetUserDetails = function(){
			$scope.IPSET_ENUM_DATA = [];
			$scope.initialize();
			$scope.search.reportdate = '';
		};
		
		$scope.downloadreport = function(){
			
			/*var config = {
				    method: 'GET',
				    url: $rootScope.serviceURL + 'downloadreport',
				    data: {locationcode:'12345'}
			    }
			
			$http(config, {responseType : 'arraybuffer'}).then(handleResponse)*/
			
			
			    //$http(config).then( function(response){
			
			//$http.get($rootScope.serviceURL+'downloadreport', {responseType : 'arraybuffer'}).then(handleResponse1);
			
			var search_location = "",zone="",circle="",division="",subdivision="",omsection="",station="",feeder="",transformer="",village="";
			if($scope.search.zone != undefined || $scope.search.zone != null){zone = $scope.search.zone.key; search_location = $scope.search.zone.key;}
			if($scope.search.circle != undefined || $scope.search.circle != null){circle = $scope.search.circle.key; search_location = $scope.search.circle.key;}
			if($scope.search.division != undefined || $scope.search.division != null){division = $scope.search.division.key; search_location = $scope.search.division.key;}
			if($scope.search.subdivision != undefined || $scope.search.subdivision != null){subdivision = $scope.search.subdivision.key; search_location = $scope.search.subdivision.key;}
			if($scope.search.omsection != undefined || $scope.search.omsection != null){omsection = $scope.search.omsection.key; search_location = $scope.search.omsection.key;}
			if($scope.search.station != undefined || $scope.search.station != null){station = $scope.search.station.key;}
			if($scope.search.feeder != undefined || $scope.search.feeder != null){feeder = $scope.search.feeder.key;}
			if($scope.search.transformers != undefined || $scope.search.transformers != null){transformer = $scope.search.transformers.key;}
			if($scope.search.village != undefined || $scope.search.village != null){village = $scope.search.village.key;}
			
			$('#loading').show();
			$http.get($rootScope.serviceURL+'downloadreport?location_code='+search_location
					+"&zone="+zone
					+"&circle="+circle
					+"&division="+division
					+"&subdivision="+subdivision
					+"&omsection="+omsection
					+"&station="+station
					+"&feeder="+feeder
					+"&transformer="+transformer
					+"&village="+village
					+"&reportdate="+$scope.search.reportdate
					+"&report_type="+$scope.search.reports.key
					,{ responseType : 'arraybuffer'}).then(handleResponse)
			
		};
		
		function handleResponse(response){
	       	var pdfFile = new Blob([response.data], { type : 'application/pdf' });	
	       	var downloadURL = URL.createObjectURL(pdfFile);
	       	$('#loading').hide();
	       		$window.open(downloadURL);
	     	  }
		
		$scope.initialize();
		
	});
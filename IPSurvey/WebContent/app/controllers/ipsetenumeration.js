/**
 * 
 */

angular.module('ipsurveyapp.Controllers', [])
	.controller("ipsetenumerationCtrl", function($scope,$rootScope, $http, $filter, $compile, $state,$cookies,remote,notify,store,$timeout) {
		
		console.log("ipsetenumerationCtrl Controller Initiated");
		
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
					location_code:($scope.search.omsection === undefined || $scope.search.omsection === null ? '' : $scope.search.omsection.key),
					station_code:($scope.search.station === undefined || $scope.search.station === null ? '' : $scope.search.station.key),
					feeder_code:($scope.search.feeder === undefined || $scope.search.feeder === null ? '' : $scope.search.feeder.key)
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
					location_code:($scope.modal.omsection === undefined || $scope.modal.omsection === null ? '' : $scope.modal.omsection.key),
					station_code:($scope.modal.station === undefined || $scope.modal.station === null ? '' : $scope.modal.station.key),
					feeder_code:($scope.modal.feeder === undefined || $scope.modal.feeder === null ? '' : $scope.modal.feeder.key)
				}, 'POST');
			}
		};
		
		
		$scope.getcodedetails = function(value,searchtype){
			
			if(searchtype === 'SER_STS'){
				$scope.modalconnectiontypes = [];
				remote.load("getcodedetails", function(response){
					$scope.modalconnectiontypes = response.CODES_LIST;
					if(value != null || value.length >0){
						$scope.modal.connectiontypes =  $filter('filter')($scope.modalconnectiontypes,{key:value},true)[0];
					}
				},{
					code_type:'SER_STS'
				}, 'POST');
			}
			
			if(searchtype === 'INSTL_STS'){
				$scope.modalconnectionstatus = [];
				remote.load("getcodedetails", function(response){
					$scope.modalconnectionstatus = response.CODES_LIST;
					if(value != null || value.length >0){
						$scope.modal.connectionstatus =  $filter('filter')($scope.modalconnectionstatus,{key:value},true)[0];
					}
				},{
					code_type:'INSTL_STS'
				}, 'POST');
			}
			
			if(searchtype === 'WTR_SOURCE'){
				$scope.modalwatersource = [];
				remote.load("getcodedetails", function(response){
					$scope.modalwatersource = response.CODES_LIST;
					if(value != null || value.length >0){
						$scope.modal.watersource =  $filter('filter')($scope.modalwatersource,{key:value},true)[0];
					}
				},{
					code_type:'WTR_SOURCE'
				}, 'POST');
			}
			
			if(searchtype === 'SCHEME'){
				$scope.modalconnectionscheme = [];
				remote.load("getcodedetails", function(response){
					$scope.modalconnectionscheme = response.CODES_LIST;
					if(value != null || value.length >0){
						$scope.modal.connectionscheme =  $filter('filter')($scope.modalconnectionscheme,{key:value},true)[0];
					}
				},{
					code_type:'SCHEME'
				}, 'POST');
			}
			
			if(searchtype === 'MTR_TYP'){
				$scope.modalmetertype = [];
				remote.load("getcodedetails", function(response){
					$scope.modalmetertype = response.CODES_LIST;
					if(value != null || value.length >0){
						$scope.modal.metertype =  $filter('filter')($scope.modalmetertype,{key:value},true)[0];
					}
				},{
					code_type:'MTR_TYP'
				}, 'POST');
			}
			
			if(searchtype === 'MTR_MAKE'){
				$scope.modalmetermake = [];
				remote.load("getcodedetails", function(response){
					$scope.modalmetermake = response.CODES_LIST;
					if(value != null || value.length >0){
						$scope.modal.metermake =  $filter('filter')($scope.modalmetermake,{key:value},true)[0];
					}
				},{
					code_type:'MTR_MAKE'
				}, 'POST');
			}
			
			if(searchtype === 'CASTE'){
				$scope.modalcastecategory = [];
				remote.load("getcastecategory", function(response){
					$scope.modalcastecategory = response.CASTECATEGORY_LIST;
					if(value != null || value.length >0){
						$scope.modal.castecategory =  $filter('filter')($scope.modalcastecategory,{key:value},true)[0];
					}
				},{}, 'POST');
			}
		};
		
		$scope.getipcodenumber = function(value){
			if($scope.search.subdivision === undefined || $scope.search.subdivision === null){notify.error("Please Select Subdivision !!!");	return;	}
			if($scope.modal.omsection === undefined || $scope.modal.omsection === null){notify.error("Please Select O&M Section !!!");	return;	}
			if($scope.modal.transformers === undefined || $scope.modal.transformers === null){notify.error("Please Select Transformer !!!");	return;	}
			
				if($scope.modal.connectiontypes != undefined || $scope.modal.connectiontypes != null){
					if(value){
						if($scope.modal.connectiontypes.key === '2' || $scope.modal.connectiontypes.key === '3'){
							remote.load("getipcodenumber", function(response){
								$scope.modal.codenumber = response.code_number;
								$scope.modal.rrnumber =  response.code_number;
								$scope.disable_rrnumber = true;
								
								$scope.VerifyRRNumber(false);
								
							},{
								om_code:$scope.modal.omsection.key,
								transformer_code:$scope.modal.transformers.key
							}, 'POST');
						}else{
							$scope.modal.codenumber = '';
							$scope.modal.rrnumber =  '';
							$scope.disable_rrnumber = false;
							$scope.newuseridexists = true;
							
							//$scope.modal.connectiontypes = $filter('filter')($scope.modalconnectiontypes,{key:'1'},true)[0];
							$scope.modal.connectionstatus = undefined;
							$scope.modal.watersource = undefined;
							$scope.modal.connectionscheme = undefined;
							$scope.modal.meterstatus = undefined;
						}
					}else{
						remote.load("getipcodenumber", function(response){
							$scope.modal.codenumber = response.code_number;
							$scope.disable_rrnumber = false;
						},{
							om_code:$scope.modal.omsection.key,
							transformer_code:$scope.modal.transformers.key
						}, 'POST');
					}
				}else{
					notify.error("Please Select Connection Type !!!");	return;
				}
		};
		
		
		$scope.ROWID = "";
		$scope.imagedata = "";
		$scope.NEW_TC_CODE = "";
		$scope.addeditIPsetEnumeration = function(record,action){
			
			$scope.NEW_TC_CODE = "";
			$scope.ROWID = "";
			$scope.imagedata = "";
			 $('#uploadPreview').attr('src', null);
			 $scope.modal.changeimage = false;
			 $scope.modal.chooseimage = null;
			 $('#modalchooseimage').val('');
			 $scope.blockcurrentvoltgae = false;
			 $scope.disable_rrnumber = false;
			 
			if(action === 'add'){
				
				$scope.modal = {};
				
				//$scope.modal.servicedate = $filter('date')(new Date('04/04/2019'), 'dd/MM/yyyy');
				//$scope.modal.inspectiondate = moment(new Date()).format("DD/MM/YYYY").toString();
				
				//$scope.modal.inspectiondate = moment((moment().set({'year': 2013, 'month': 3,'date': 1}))).format("DD/MM/YYYY").toString();
				
				//$('.datepicker').datepicker('update', new Date(2011, 2, 5));
				
				$('#servicedate').datepicker('update', new Date());
				$('#inspectiondate').datepicker('update', new Date());
				
				$scope.getomsectionList([],null,'modal');
				$scope.getStationList('modal',null,null);
				$scope.getcodedetails('','SER_STS');
				$scope.getcodedetails('','INSTL_STS');
				$scope.getcodedetails('','WTR_SOURCE');
				$scope.getcodedetails('','SCHEME');
				$scope.getcodedetails('','MTR_MAKE');
				$scope.getcodedetails('','MTR_TYP');
				$scope.getcodedetails('','CASTE');
				
				$scope.action = 'add';
				
				$scope.modal_heading = "Add IPSET Enumeration Data";
				$scope.newuseridexists = true;
				
				$scope.modal.rrnumber = '';
				$scope.modal.codenumber = '';
				$scope.modal.consumername = '';
				$scope.modal.address1 = '';
				$scope.modal.address2 = '';
				$scope.modal.phone = '';
				$scope.modal.castecategory = '';
				$scope.modal.aadhaarnumber = '';
				$scope.modal.surveynumber = '';
				$scope.modal.loadkw = '0';
				$scope.modal.loadhp = '5';
				$scope.modal.meterslno = '';
				$scope.modal.finalreading = '';

				$scope.modal.voltage1 = '';
				$scope.modal.voltage2 = '';
				$scope.modal.voltage3 = '';
				$scope.modal.current1 = '';
				$scope.modal.current2 = '';
				$scope.modal.current3 = '';
				
				$scope.modal.latitude = '' ;
				$scope.modal.latitude = '' ;
				$scope.modal.longitude = '' ;
				$scope.modal.altitude = '';
				$scope.modal.remarks = '';
				
				/*$("#div-dsservicedate").addClass("dsservicedate");
				 $("#div-dsinspectiondate").addClass("dsinspectiondate");
				
				
				 $('.dsservicedate').datetimepicker({
						format : 'dd/mm/yyyy',
						minView : 2,
						autoclose : 1,
						showClear: false
					});
				 
				 $('.dsinspectiondate').datetimepicker({
						format : 'dd/mm/yyyy',
						minView : 2,
						autoclose : 1,
						showClear: false
					});*/
				
			}else if(action === 'edit') {
				
				$scope.modal = {};
				
				console.log("record",record);
				
				// $("#div-dsservicedate").removeClass("dsservicedate");
				// $("#div-dsinspectiondate").removeClass("dsinspectiondate");
				
				$scope.getomsectionList([],null,'modal');
				$timeout(function(){$scope.getStationList('modal',record.IE_STATION_CODE,record.IE_FEEDER_CODE);},500);
				$timeout(function(){$scope.getcodedetails(record.IE_CONNECTION_TYPE,'SER_STS');},100);
				$timeout(function(){$scope.getcodedetails(record.IE_CUSTOMER_STATUS,'INSTL_STS');},100);
				$timeout(function(){$scope.getcodedetails(record.IE_WATER_SOURCE,'WTR_SOURCE');},100);
				$timeout(function(){$scope.getcodedetails(record.IE_SCHEME,'SCHEME');},100);
				$timeout(function(){$scope.getcodedetails(record.IE_MTR_MAKE,'MTR_MAKE');},100);
				$timeout(function(){$scope.getcodedetails(record.IE_MTR_TYPE,'MTR_TYP');},100);
				$timeout(function(){$scope.getcodedetails(record.CATEGORYID,'CASTE');},100);
				
				$scope.modal.meterstatus = $filter('filter')($scope.modalmeterstatus,{key:record.IE_METER_FLAG},true)[0];
				
				$timeout(function(){$scope.getenumeratedvillageslist(record.IE_VILLIAGE,'modal');},1000);
				$timeout(function(){$scope.getenumeratedtransformerslist(record.IE_TRANSFORMER_CODE,'modal');},1000);
				
				$scope.ROWID = record.row_id;
				$scope.action = 'edit';
				$scope.newuseridexists = false;
				$scope.modal_heading = "Edit IPSET Enumeration Data";
				
				$scope.modal.rrnumber = record.IE_RR_NO;
				$scope.modal.codenumber = record.IE_CODE_NUMBER;
				$scope.modal.consumername = record.IE_CUSTOMER_NAME;
				$scope.modal.address1 = record.IE_ADDRESS1;
				$scope.modal.address2 = record.IE_ADDRESS2;
				$scope.modal.aadhaarnumber = record.IE_AADHAR_NUMBER;
				$scope.modal.surveynumber = record.IE_PROPERTY_SURVEY_NUMBER;
				
				var date_array = (record.IE_SERVICE_DATE.length > 0 ? record.IE_SERVICE_DATE.split("/") : '');
				$('#servicedate').datepicker('update', new Date(parseInt(date_array[2]),parseInt(date_array[1])-1,parseInt(date_array[0])));
				$scope.modal.servicedate =  record.IE_SERVICE_DATE;
				
				date_array = (record.IE_INSPECTION_DATE.length > 0 ? record.IE_INSPECTION_DATE.split("/") : '');
				$('#inspectiondate').datepicker('update', new Date(parseInt(date_array[2]),parseInt(date_array[1]-1),parseInt(date_array[0])));
				$scope.modal.inspectiondate =  record.IE_INSPECTION_DATE;
				
				$scope.modal.meterslno = record.IE_MTR_SLNO;
				$scope.modal.finalreading = record.IE_FINAL_READING;
				$scope.modal.loadkw = record.IE_LOAD_KW;
				$scope.modal.loadhp = record.IE_LOAD_HP;
				$scope.modal.voltage1 = record.IE_VOLTAGE_RY;
				$scope.modal.voltage2 = record.IE_VOLTAGE_RB;
				$scope.modal.voltage3 = record.IE_VOLTAGE_BR;
				$scope.modal.current1 = record.IE_CURRENT_R;
				$scope.modal.current2 = record.IE_CURRENT_Y;
				$scope.modal.current3 = record.IE_CURRENT_B;
				$scope.modal.latitude = record.IE_LATTITUDE;
				$scope.modal.longitude = record.IE_LONGITUDE;
				$scope.modal.altitude = record.IE_ALTITUDE;
					
					if(record.IE_IMAGE_PATH.length >0 || record.IE_IMAGE_PATH != undefined || record.IE_IMAGE_PATH != null){
						remote.load("getimagedata", function(response){
							console.log("getimagedata",response);
							$scope.imagedata = response.encodedBase64;
						},{
							filename:record.IE_IMAGE_PATH
						}, 'POST');
					}
				
			}else if(action === 'delete'){
				
				$scope.modal = {};
				
				console.log("record",record);
				
				$scope.ROWID = record.row_id;
				
				var sts = confirm("Are You Sure To Delete ?");
				console.log("sts",sts);
				
				if(sts){
					
					var request = {
							rowid:$scope.ROWID,
							location_code:record.IE_OM_CODE,
							stationcode:record.IE_STATION_CODE,
							feedercode:record.IE_FEEDER_CODE,
							transformercode:record.IE_TRANSFORMER_CODE,
							village:record.IE_VILLIAGE,
							polecode:record.IE_POLE_CODE,
							rrnumber:record.IE_RR_NO,
							codenumber:record.IE_CODE_NUMBER,
							consumername:record.IE_CUSTOMER_NAME,
							address1:record.IE_ADDRESS1,
							address2:record.IE_ADDRESS2,
							connectiontypes:record.IE_CONNECTION_TYPE,
							connectionstatus:record.IE_CUSTOMER_STATUS,
							phone:record.IE_PHONE_NUMBER,
							castecategory:record.CATEGORYID,
							aadhar_number:record.IE_AADHAR_NUMBER,
							survey_number:record.IE_PROPERTY_SURVEY_NUMBER,
							watersource:record.IE_WATER_SOURCE,
							connectionscheme:record.IE_SCHEME,
							servicedate:record.IE_SERVICE_DATE,
							inspectiondate:record.IE_INSPECTION_DATE,
							meterstatus:record.IE_METER_FLAG,
							metermake:record.IE_MTR_MAKE,
							metertype:record.IE_MTR_TYPE,
							meterslno:record.IE_MTR_SLNO,
							finalreading:record.IE_FINAL_READING,
							loadkw:record.IE_LOAD_KW,
							loadhp:record.IE_LOAD_HP,
							voltage1:record.IE_VOLTAGE_RY,
							voltage2:record.IE_VOLTAGE_RB,
							voltage3:record.IE_VOLTAGE_BR,
							current1:record.IE_CURRENT_R,
							current2:record.IE_CURRENT_Y,
							current3:record.IE_CURRENT_B,
							latitude:record.IE_LATTITUDE,
							longitude:record.IE_LONGITUDE,
							altitude:record.IE_ALTITUDE,
							remarks:record.IE_REMARKS,
							userid:$scope.userinfo.username,
							deleteflag:'Y',
							imagepath:record.IE_IMAGE_PATH	
					};
					
					
					
					remote.load("upsertipsetenumeration", function(response){
						console.log("upsertipsetenumeration",response);
						if(response.status === 'success'){
							$timeout(function(){
								//$('#stationmaster-addedit-modal').modal('toggle');
								$scope.SearchIpsetEnumDetails();
							},2000);
						}
					},request, 'POST');
				}
				
			}
		};
		
		$scope.newuseridexists = true;
		$scope.VerifyRRNumber = function(auth){
			$scope.newuseridexists = true;
			if($scope.search.subdivision === undefined || $scope.search.subdivision === null){notify.error("Please Select Subdivision !!!");return;}
			if($scope.modal.omsection === undefined || $scope.modal.omsection === null){notify.error("Please Select O&M Section !!!");	return;	}
			if($scope.modal.transformers === undefined || $scope.modal.transformers === null){notify.error("Please Select Transformer !!!");	return;	}
			
			if(auth){
				remote.load("getconsumerdetailsbyrrno", function(response){
					$scope.modal.consumername = response.customer_name;
					$scope.modal.address1 = response.address1;
					$scope.modal.address2 = response.address2;
					$scope.modal.village = (response.village === undefined || response.village === null || response.village === '' ? undefined : $filter('filter')($scope.getmodalenumeratedvillages,{key:response.village},true)[0]);
					$scope.newuseridexists = false;
					
					$scope.getipcodenumber(false);
					
					$scope.modal.connectiontypes = $filter('filter')($scope.modalconnectiontypes,{key:'1'},true)[0];
					$scope.modal.connectionstatus = $filter('filter')($scope.modalconnectionstatus,{key:'1'},true)[0];
					$scope.modal.watersource = $filter('filter')($scope.modalwatersource,{key:'1'},true)[0];
					$scope.modal.connectionscheme = $filter('filter')($scope.modalconnectionscheme,{key:'1'},true)[0];
					$scope.modal.meterstatus = $filter('filter')($scope.modalmeterstatus,{key:'N'},true)[0];
				},{
					location_code:$scope.search.subdivision.key,
					rr_number:$scope.modal.rrnumber
				}, 'POST');
			}else{
				
				$scope.newuseridexists = false;
				
				$scope.getipcodenumber(false);
				
				//$scope.modal.connectiontypes = $filter('filter')($scope.modalconnectiontypes,{key:'1'},true)[0];
				$scope.modal.connectionstatus = $filter('filter')($scope.modalconnectionstatus,{key:'1'},true)[0];
				$scope.modal.watersource = $filter('filter')($scope.modalwatersource,{key:'1'},true)[0];
				$scope.modal.connectionscheme = $filter('filter')($scope.modalconnectionscheme,{key:'1'},true)[0];
				$scope.modal.meterstatus = $filter('filter')($scope.modalmeterstatus,{key:'N'},true)[0];
				
			}
			

		};
		
		$scope.meterstatusdisable = true;
		$scope.enablemetermakeandtype = function(){
			$scope.meterstatusdisable = true;
			if($scope.modal.meterstatus != undefined || $scope.modal.meterstatus != null){
				if($scope.modal.meterstatus.key === 'Y'){
					$scope.modal.metermake = $filter('filter')($scope.modalmetermake,{key:'90'},true)[0];
					$scope.modal.metertype = $filter('filter')($scope.modalmetertype,{key:'2'},true)[0];
					$scope.meterstatusdisable = false;
				}else{
					$scope.meterstatusdisable = true;
					$scope.modal.metermake = undefined;
					$scope.modal.metertype = undefined;
				}
			};
		};
		
		$scope.SaveRecord = function(){
			
			var files=$('#modalchooseimage')[0].files;
			var formdata = new FormData(); 
			
			if($scope.search.subdivision === undefined || $scope.search.subdivision === null){notify.error("Please Select Subdivision !!!");return;}
			if($scope.modal.omsection === undefined || $scope.modal.omsection === null){notify.error("Please Select O&M Section !!!");	return;	}
			if($scope.modal.transformers === undefined || $scope.modal.transformers === null){notify.error("Please Select Transformer !!!");	return;	}
			if($scope.modal.station === undefined || $scope.modal.station === null){notify.error("Please Select Station !!!");	return;	}
			if($scope.modal.feeder === undefined || $scope.modal.feeder === null){notify.error("Please Select Feeder !!!");	return;	}
			if($scope.modal.village === undefined || $scope.modal.village === null){notify.error("Please Select Village !!!");	return;	}
			
			if($scope.modal.rrnumber === undefined || $scope.modal.rrnumber === null){notify.error("Please enter rrnumber !!!");	return;	}
			if($scope.modal.codenumber === undefined || $scope.modal.codenumber === null){notify.error("Please enter code number !!!");	return;	}
			if($scope.modal.consumername === '' || $scope.modal.consumername === undefined){notify.error("Please enter consumer name !!!");	return;	}
			//if($scope.modal.address1 === undefined || $scope.modal.address1 === null){notify.error("Please enter address 1 !!!");	return;	}
			//if($scope.modal.address2 === undefined || $scope.modal.address2 === null){notify.error("Please enter address 2 !!!");	return;	}
			
			if($scope.modal.connectiontypes === undefined || $scope.modal.connectiontypes === null){notify.error("Please select connection type !!!");	return;	}
			if($scope.modal.connectionstatus === undefined || $scope.modal.connectionstatus === null){notify.error("Please select connection status !!!");	return;	}
			if($scope.modal.watersource === undefined || $scope.modal.watersource === null){notify.error("Please select water source !!!");	return;	}
			if($scope.modal.connectionscheme === undefined || $scope.modal.connectionscheme === null){notify.error("Please select connection scheme !!!");	return;	}
			
			if($scope.modal.meterstatus != undefined){
				if($scope.modal.meterstatus.key === 'Y'){
					if($scope.modal.metermake === undefined || $scope.modal.metermake === null){notify.error("Please select meter make !!!");	return;	}
					if($scope.modal.metertype === undefined || $scope.modal.metertype === null){notify.error("Please select meter type !!!");	return;	}
					if($scope.modal.meterslno === '' || $scope.modal.meterslno === undefined){notify.error("Please enter meter sl.no !!!");	return;	}
					if($scope.modal.finalreading === '' || $scope.modal.finalreading === undefined){notify.error("Please enter meter final reading !!!");	return;	}
				}
			}
			
			if($scope.modal.loadkw === '' || $scope.modal.loadkw === undefined){notify.error("Please enter load KW !!!");	return;	}
			if($scope.modal.loadhp === '' || $scope.modal.loadhp === undefined){notify.error("Please enter load HP !!!");	return;	}
			if($scope.modal.voltage1 === '' || $scope.modal.voltage1 === undefined){notify.error("Please enter Voltage-1 !!!");	return;	}
			if($scope.modal.voltage2 === '' || $scope.modal.voltage2 === undefined){notify.error("Please enter voltage-2 !!!");	return;	}
			if($scope.modal.voltage3 === '' || $scope.modal.voltage3 === undefined){notify.error("Please enter Voltage-3 !!!");	return;	}
			if($scope.modal.current1 === '' || $scope.modal.current1 === undefined){notify.error("Please enter Current-1 !!!");	return;	}
			if($scope.modal.current2 === '' || $scope.modal.current2 === undefined){notify.error("Please enter Current-2 !!!");	return;	}
			if($scope.modal.current3 === '' || $scope.modal.current3 === undefined){notify.error("Please enter Current-3 !!!");	return;	}
			if($scope.modal.latitude === '' || $scope.modal.latitude === undefined){notify.error("Please enter latitude");return;}
			if($scope.modal.longitude === '' || $scope.modal.longitude === undefined){notify.error("Please enter longitude");return;}
			if($scope.modal.altitude === '' || $scope.modal.altitude === undefined){notify.error("Please enter altitude");return;}
			
			
			
			
			var imagepath = "";
			if(files.length > 0){
				
				var file_ext = files[0].name;
				
				imagepath = $scope.search.subdivision.key+"_"+$scope.modal.omsection.key+"_"+$scope.modal.transformers.key+"_"+$scope.modal.rrnumber+file_ext.substring(file_ext.indexOf("."));
			}
			
			if($scope.action === 'add'){
				if(files.length > 0){
					formdata.append('file',files[0]);
					formdata.append('filename',imagepath);
				}
			}
			
			if($scope.modal.changeimage){
				if(files.length > 0){
					formdata.append('file',files[0]);
					formdata.append('filename',imagepath);
				}else{
					notify.warn("Please choose image !!");return;
				}
			}
			
			var request = {
					rowid:$scope.ROWID,
					location_code:$scope.modal.omsection.key,
					stationcode:$scope.modal.station.key,
					feedercode:$scope.modal.feeder.key,
					transformercode:$scope.modal.transformers.key,
					village:$scope.modal.village.key,
					polecode:'00',
					rrnumber:$scope.modal.rrnumber,
					codenumber:$scope.modal.codenumber,
					consumername:$scope.modal.consumername,
					address1:$scope.modal.address1,
					address2:$scope.modal.address2,
					connectiontypes:$scope.modal.connectiontypes.key,
					connectionstatus:$scope.modal.connectionstatus.key,
					phone:($scope.modal.phone === undefined || $scope.modal.phone === '' || $scope.modal.phone == null ? '' : $scope.modal.phone),
					castecategory:($scope.modal.castecategory === undefined || $scope.modal.castecategory === null ? '' : $scope.modal.castecategory.key),
					aadhar_number:($scope.modal.aadhaarnumber === undefined || $scope.modal.aadhaarnumber === '' || $scope.modal.aadhaarnumber == null ? '' : $scope.modal.aadhaarnumber),
					survey_number:($scope.modal.surveynumber === undefined || $scope.modal.surveynumber === '' || $scope.modal.surveynumber == null ? '' : $scope.modal.surveynumber),
					watersource:$scope.modal.watersource.key,
					connectionscheme:$scope.modal.connectionscheme.key,
					servicedate:$scope.modal.servicedate,
					inspectiondate:$scope.modal.inspectiondate,
					meterstatus:($scope.modal.meterstatus === undefined || $scope.modal.meterstatus === null ? '' : $scope.modal.meterstatus.key),
					metermake:($scope.modal.metermake === undefined || $scope.modal.metermake === null ? '' : $scope.modal.metermake.key),
					metertype:($scope.modal.metertype === undefined || $scope.modal.metertype === null ? '' : $scope.modal.metertype.key),
					meterslno:($scope.modal.meterslno === undefined || $scope.modal.meterslno === '' ? '' : $scope.modal.meterslno),
					finalreading:($scope.modal.finalreading === undefined || $scope.modal.finalreading === '' ? '' : $scope.modal.finalreading),
					loadkw:$scope.modal.loadkw,
					loadhp:$scope.modal.loadhp,
					voltage1:$scope.modal.voltage1,
					voltage2:$scope.modal.voltage2,
					voltage3:$scope.modal.voltage3,
					current1:$scope.modal.current1,
					current2:$scope.modal.current2,
					current3:$scope.modal.current3,
					latitude:$scope.modal.latitude,
					longitude:$scope.modal.longitude,
					altitude:$scope.modal.altitude,
					remarks:($scope.modal.remarks === undefined || $scope.modal.remarks === null ? '' : $scope.modal.remarks),
					userid:$scope.userinfo.username,
					deleteflag:'N',
					imagepath:(imagepath == null || imagepath == '' || imagepath == undefined ? '' : imagepath)	
			};
			
			console.log(request);
			
			remote.load("upsertipsetenumeration", function(response){
				console.log("upsertipsetenumeration",response);
				if(response.status === 'success'){
					if(files.length > 0){
						$('#loading').show();
							formdata.append('file',files[0]);
							formdata.append('filename',imagepath);
							$http.post($rootScope.serviceURL+'saveimage',formdata, {
						    	 transformRequest: angular.identity,
						    	 headers: {'Content-Type': undefined}
						        }).then(function (data){
						        	//console.log(data);
						        	$('#loading').hide();
						        	$timeout(function(){
										$('#ipsetenumerate_addedit_modal').modal('toggle');
										$scope.IPSET_ENUM_DATA = [];
										$scope.SearchIpsetEnumDetails();
									},2000);
						        },function (data){
							  		//console.log(data);
							  		$('#loading').hide();
							  	});
						}else{
							$timeout(function(){
								$('#ipsetenumerate_addedit_modal').modal('toggle');
								$scope.IPSET_ENUM_DATA = [];
								$scope.SearchIpsetEnumDetails();
							},2000);
						}
				}
			},request, 'POST');
		};
		
		
		$scope.blockVoltagesCurrents = function(){
			
			if($scope.modal.connectionstatus.key != '1'){
				$scope.blockcurrentvoltgae = true;
				$scope.modal.voltage1 = 0;
				$scope.modal.voltage2 = 0;
				$scope.modal.voltage3 = 0;
				$scope.modal.current1 = 0;
				$scope.modal.current2 = 0;
				$scope.modal.current3 = 0;
			}else{
				$scope.blockcurrentvoltgae = false;
				$scope.modal.voltage1 = '';
				$scope.modal.voltage2 = '';
				$scope.modal.voltage3 = '';
				$scope.modal.current1 = '';
				$scope.modal.current2 = '';
				$scope.modal.current3 = '';
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
			
		};
		
		$scope.initialize();
		
	});
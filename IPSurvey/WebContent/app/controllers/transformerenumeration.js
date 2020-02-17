/**
 * 
 */

angular.module('ipsurveyapp.Controllers', [])
	.controller("transformerenumerationCtrl",function($scope,$rootScope, $http, $filter, $compile, $state,$cookies,remote,notify,store,$timeout,TransformerMasterData) {
		
		console.log("transformerenumeration Controller Initiated");
		
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
							$scope.gettransformermasterdata();
							$scope.getenumeratedvillageslist('');
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
			if($scope.modal.omsection === undefined || $scope.modal.omsection === null){
				notify.warn("Please select O&M Section !!!");
				return;
			}
			
			remote.load("gettransformermasterdata", function(response){
				$scope.gettransformermasterdatalist = response.TRANSFORMER_MASTER_DATA;
				$scope.gettransformermasterdatalist.map(function(e,index){
					$scope.TRANSFORMER_MASTER_DATA.push(e.TM_TRANSFORMER_NAME);
				});
				$scope.filltransformerdata();
			},{
				location_code:($scope.modal.omsection === undefined || $scope.modal.omsection === null ? '' : $scope.modal.omsection.key)
			}, 'POST');
		};
		
		$scope.getenumeratedvillageslist = function(value){
			$scope.getenumeratedvillages = [];
			if($scope.modal.omsection === undefined || $scope.modal.omsection === null){
				notify.warn("Please select O&M Section !!!");
				return;
			}
			remote.load("getenumeratedvillageslist", function(response){
				$scope.getenumeratedvillages = response.VILLAGE_ENUM_DATA;
				if(value != null || value.length >0){
					$scope.modal.village =  $filter('filter')($scope.getenumeratedvillages,{key:value},true)[0];
				}
			},{
				location_code:($scope.modal.omsection === undefined || $scope.modal.omsection === null ? '' : $scope.modal.omsection.key)
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
		$scope.NEW_TC_CODE = "";
		$scope.addeditTransformerEnumeration = function(record,action){
			
			$scope.NEW_TC_CODE = "";
			$scope.ROWID = "";
			$scope.imagedata = "";
			 $('#uploadPreview').attr('src', null);
			 $scope.modal.changeimage = false;
			 $scope.modal.chooseimage = null;
			 $('#modalchooseimage').val('');
			 
			if(action === 'add'){
				
				$scope.getomsectionList([],null,'modal');
				$scope.getStationList('modal',null,null);
				
				$scope.action = 'add';
				
				$scope.modal_heading = "Add Transformer Enumeration Data";
				$scope.newuseridexists = true;
				
				
				$scope.search.autocompletetransformername = '';
				$scope.modal.latitude = '' ;
				$scope.modal.longitude = '' ;
				$scope.modal.altitude = '';
				$scope.modal.remarks = '';
				$scope.modal.transformercode = '';
				$scope.modal.timscode = '';
				$scope.modal.capacitykva = '';
				
			}else if(action === 'edit'){
				
				console.log("record",record);
				
				$scope.getomsectionList([],null,'modal');
				$timeout(function(){
					$scope.getStationList('modal',record.TE_STATION_CODE,record.TE_FEEDER_CODE);
				},200);
				$timeout(function(){
					$scope.getenumeratedvillageslist(record.TE_VILLAGE);
				},200);
				
				$scope.ROWID = record.row_id;
				$scope.action = 'edit';
				$scope.newuseridexists = false;
				$scope.modal_heading = "Edit Transformer Enumeration Data";
				
				 $scope.search.autocompletetransformername = record.TE_TRANSFORMER_NAME;
					$scope.modal.latitude = record.TE_LATTITUDE;
					$scope.modal.longitude = record.TE_LONGITUDE;
					$scope.modal.altitude = record.TE_ALTITUDE;
					$scope.modal.remarks = record.TE_REMARKS;
					$scope.modal.transformercode = record.TE_TRANSFORMER_CODE;
					$scope.modal.timscode = record.TE_TIMS_CODE;
					$scope.modal.capacitykva = record.TE_CAPACITY_KVA;
					//$scope.modal.village = record.TE_VILLAGE;
					
					if(record.TE_IMAGE_PATH.length >0 || record.TE_IMAGE_PATH != undefined || record.TE_IMAGE_PATH != null){
						remote.load("getimagedata", function(response){
							console.log("getimagedata",response);
							$scope.imagedata = response.encodedBase64;
						},{
							filename:record.TE_IMAGE_PATH
						}, 'POST');
					}
					
					$scope.NEW_TC_CODE = record.TE_TRANSFORMER_CODE;
				
			}else if(action === 'delete'){
				
				console.log("record",record);
				
				$scope.ROWID = record.row_id;
				
				var sts = confirm("Are You Sure To Delete ?");
				console.log("sts",sts);
				
				if(sts){
					
					var request = {
							rowid:$scope.ROWID,
							location_code:record.TE_OM_CODE,
							stationcode:record.TE_STATION_CODE,
							feedercode:record.TE_FEEDER_CODE,
							transformercode:record.TE_TRANSFORMER_CODE,
							transformername:record.TE_TRANSFORMER_NAME,
							timscode:record.TE_TIMS_CODE,
							capacitykva:record.TE_CAPACITY_KVA,
							connectedloadkva:record.TE_CAPACITY_KVA,
							village_name:record.TE_VILLAGE,
							latitude:record.TE_LATTITUDE,
							longitude:record.TE_LONGITUDE,
							altitude:record.TE_ALTITUDE,
							remarks:record.TE_REMARKS,
							userid:$scope.userinfo.username,
							deleteflag:'Y',
							imagepath:record.TE_IMAGE_PATH	
					};
					
					
					
					remote.load("upserttransformerenumeration", function(response){
						console.log("upserttransformerenumeration",response);
						if(response.status === 'success'){
							$timeout(function(){
								//$('#stationmaster-addedit-modal').modal('toggle');
								$scope.SearchTransformerEnumDetails();
							},2000);
						}
					},request, 'POST');
				}
				
			}
			
		};
		
		$scope.newuseridexists = true;
		$scope.verifytransformerexists = function(){
			$scope.newuseridexists = true;
			if($scope.search.autocompletetransformername === undefined 
					|| $scope.search.autocompletetransformername === null 
						|| $scope.search.autocompletetransformername == ''){
				return;
			}
			
			var transformer_code = $filter('filter')($scope.gettransformermasterdatalist,{TM_TRANSFORMER_NAME:$scope.search.autocompletetransformername},true)[0];
			console.log("transformer_code",transformer_code);
			
			$scope.NEW_TC_CODE = "";
			
			$scope.NEW_TC_CODE =  (transformer_code === null || transformer_code === undefined ? '' : transformer_code.TM_TRANSFORMER_CODE);
			
			remote.load("validatetransformerdetails", function(response){
				console.log("validatetransformerdetails",response);
				
				if(response.action === 'success'){
					$scope.getStationList('modal',response.TM_STATION_CODE,response.TM_FEEDER_CODE);
					$scope.modal.transformercode = response.TM_TRANSFORMER_CODE;
					$scope.modal.timscode = $scope.modal.omsection.key+""+response.TM_TRANSFORMER_CODE;
					$scope.modal.capacitykva = response.TM_CAPACITY_KVA;
					$scope.modal.latitude = '' ;
					$scope.modal.longitude = '' ;
					$scope.modal.altitude = '';
					$scope.modal.remarks = '';
					$scope.newuseridexists = false;
					$scope.NEW_TC_CODE = response.TM_TRANSFORMER_CODE;
				}
				
				if(response.action === 'new'){
					$scope.modal.transformercode = response.NEW_TC_CODE;
					$scope.modal.timscode = response.TIMS_CODE;
					$scope.modal.capacitykva = '';
					$scope.modal.latitude = '' ;
					$scope.modal.longitude = '' ;
					$scope.modal.altitude = '';
					$scope.modal.remarks = '';
					$scope.newuseridexists = false;
					$scope.NEW_TC_CODE = response.NEW_TC_CODE;
				}
				
			},{
				location_code:($scope.modal.omsection === undefined || $scope.modal.omsection === null ? '' : $scope.modal.omsection.key),
				tc_code:(transformer_code === null || transformer_code === undefined ? '' : transformer_code.TM_TRANSFORMER_CODE) , 
				tc_name:$scope.search.autocompletetransformername	
			}, 'POST');
			
			
			
		};
		
		$scope.SaveRecord = function(){
			
			var files=$('#modalchooseimage')[0].files;
			var formdata = new FormData(); 
			
			if($scope.modal.omsection === undefined || $scope.modal.omsection === null){notify.warn("Please Select OM Section");return;}
			if($scope.search.autocompletetransformername === undefined || $scope.search.autocompletetransformername === null){notify.warn("Please enter transformer name");return;}
			if($scope.modal.station === undefined || $scope.modal.station === null){notify.warn("Please Select Station");return;}
			if($scope.modal.feeder === undefined || $scope.modal.feeder === null){notify.warn("Please Select Feeder");return;}
			if($scope.modal.village === undefined || $scope.modal.village === null){notify.warn("Please Select viilage");return;}
			if($scope.modal.latitude === undefined || $scope.modal.latitude === null){notify.warn("Please enter latitude");return;}
			if($scope.modal.longitude === undefined || $scope.modal.longitude === null){notify.warn("Please enter longitude");return;}
			if($scope.modal.altitude === undefined || $scope.modal.altitude === null){notify.warn("Please enter altitude");return;}
			
			var imagepath = "";
			if(files.length > 0){
				var file_ext = files[0].name;
				imagepath = $scope.modal.omsection.key+"_"+$scope.NEW_TC_CODE+"_"+$scope.search.autocompletetransformername+file_ext.substring(file_ext.indexOf("."));
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
					transformercode:$scope.NEW_TC_CODE,
					transformername:$scope.search.autocompletetransformername,
					timscode:$scope.modal.timscode,
					capacitykva:$scope.modal.capacitykva,
					connectedloadkva:$scope.modal.capacitykva,
					village_name:($scope.modal.village === undefined || $scope.modal.village === null ? '' : $scope.modal.village.key),
					latitude:$scope.modal.latitude,
					longitude:$scope.modal.longitude,
					altitude:$scope.modal.altitude,
					remarks:($scope.modal.remarks === undefined || $scope.modal.remarks === null ? '' : $scope.modal.remarks),
					userid:$scope.userinfo.username,
					deleteflag:'N',
					imagepath:(imagepath == null || imagepath == '' || imagepath == undefined ? '' : imagepath)	
			};
			
			console.log("request",request);
			
			remote.load("upserttransformerenumeration", function(response){
				console.log("upserttransformerenumeration",response);
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
										$('#transformerenumerate-addedit-modal').modal('toggle');
										$scope.SearchTransformerEnumDetails();
									},2000);
						        },function (data){
							  		//console.log(data);
							  		$('#loading').hide();
							  	});
						}else{
							$timeout(function(){
								$('#transformerenumerate-addedit-modal').modal('toggle');
								$scope.SearchTransformerEnumDetails();
							},2000);
						}
				}
			},request, 'POST');
		};
		
		$scope.initialize();
		
		$scope.ResetUserDetails = function(){
			$scope.TRANSFORMER_ENUM_DATA = [];
			$scope.initialize();
			
		};
		
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
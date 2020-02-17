/**
 * 
 */

angular.module('ipsurveyapp.Controllers', [])
	.controller("usermanagementCtrl", function($scope,$rootScope, $http, $filter, $compile, $state,$cookies,remote,notify,store,$timeout) {
		
		var LOCATION_CODE = store.get('LOCATION_CODE');
		$rootScope.LOCATION_CODE = LOCATION_CODE;
		
		$scope.search = {};
		$scope.userinfo = {};
		if(!store.get('userinfo')){
			$rootScope.logout();
			$scope.userinfo = {};
		}else{
			$rootScope.IsLoggedIn = true;
			$scope.userinfo = store.get('userinfo');
		}
		
		console.log("usermanagementCtrl Controller Initiated");
		
		$scope.usermanagement = {};
		
		$scope.usertypelist = [
			{key:1,value:"Zone"},
			{key:2,value:"Circle"},
			{key:3,value:"Division"},
			{key:4,value:"Sub-Division"},
			{key:5,value:"OM Section"}
			];
		
		$scope.statuslist = [
			{key:"Y",value:"YES"},
			{key:"N",value:"NO"}
			];
		
		$scope.userrolelist = [
			{key:"SUPERVISOR",value:"SUPERVISOR"},
			{key:"SURVEYOR",value:"SURVEYOR"}
			];
		
		
		$scope.getzonelist = function(arr,usertype,searchtype){
			if(searchtype === 'search'){
				$scope.searchzonelist=[];$scope.searchcirclelist=[];$scope.searchdivisionlist=[];$scope.searchsubdivisionlist=[];$scope.searchomsectionlist=[];
				remote.load("getzonelist", function(response){
					$scope.searchzonelist = response.ZONE_LIST;
				},{
				}, 'POST');
			}else{
			$scope.zonelist=[];$scope.circlelist=[];$scope.divisionlist=[];$scope.subdivisionlist=[];$scope.omsectionlist=[];
				remote.load("getzonelist", function(response){
					$scope.zonelist = response.ZONE_LIST;
					console.log("getzonelist",response);
						if(arr.length > 0){
							$scope.usermanagement.zone = $filter('filter')($scope.zonelist,{key:arr[0]},true)[0];
							if(usertype > 1){
							$scope.getcircleList(arr,usertype,'modal');
						}
					}
				},{
				}, 'POST');
			}
			
		};
		
		$scope.getcircleList = function(arr,usertype,searchtype){
			if(searchtype === 'search'){
				$scope.searchcirclelist=[];$scope.searchdivisionlist=[];$scope.searchsubdivisionlist=[];$scope.searchomsectionlist=[];
				if($scope.search.zone === undefined || $scope.search.zone === null){
					return;
				}
				remote.load("getcirclelist", function(response){
					$scope.searchcirclelist = response.CIRCLE_LIST;
				},{
					location_code:($scope.search.zone === undefined || $scope.search.zone === null ? '' : $scope.search.zone.key)
				}, 'POST');
			}else{
				$scope.circlelist=[];$scope.divisionlist=[];$scope.subdivisionlist=[];$scope.omsectionlist=[];
				if($scope.usermanagement.zone === undefined || $scope.usermanagement.zone === null){
					return;
				}
				remote.load("getcirclelist", function(response){
					$scope.circlelist = response.CIRCLE_LIST;
					console.log("getcirclelist",response);
						if(arr.length > 0){
							$scope.usermanagement.circle = $filter('filter')($scope.circlelist,{key:arr[1]},true)[0];
							if(usertype > 2){
							$scope.getdivisionList(arr,usertype,'modal');
						}
					}
				},{
					location_code:($scope.usermanagement.zone === undefined || $scope.usermanagement.zone === null ? '' : $scope.usermanagement.zone.key)
				}, 'POST');
			}
			
			
		};
		
		$scope.getdivisionList = function(arr,usertype,searchtype){
			if(searchtype === 'search'){
				$scope.searchdivisionlist=[];$scope.searchsubdivisionlist=[];$scope.searchomsectionlist=[];
				if($scope.search.circle === undefined || $scope.search.circle === null){
					return;
				}
				remote.load("getdivisionlist", function(response){
					$scope.searchdivisionlist = response.DIVISION_LIST;
				},{
					location_code:($scope.search.circle === undefined || $scope.search.circle === null ? '' : $scope.search.circle.key)
				}, 'POST');
			}else{
				$scope.divisionlist=[];$scope.subdivisionlist=[];$scope.omsectionlist=[];
				if($scope.usermanagement.circle === undefined || $scope.usermanagement.circle === null){
					return;
				}
				remote.load("getdivisionlist", function(response){
					$scope.divisionlist = response.DIVISION_LIST;
					console.log("getdivisionList",response);
						if(arr.length > 0){
							$scope.usermanagement.division = $filter('filter')($scope.divisionlist,{key:arr[2]},true)[0];
							if(usertype > 3){
							$scope.getsubdivisionList(arr,usertype,'modal');
						}
					}
				},{
					location_code:($scope.usermanagement.circle === undefined || $scope.usermanagement.circle === null ? '' : $scope.usermanagement.circle.key)
				}, 'POST');
			}
		};
		
		$scope.getsubdivisionList = function(arr,usertype,searchtype){
			if(searchtype === 'search'){
				$scope.searchsubdivisionlist=[];$scope.searchomsectionlist=[];
				if($scope.search.division === undefined || $scope.search.division === null){
					return;
				}
				remote.load("getsubdivisionlist", function(response){
					$scope.searchsubdivisionlist = response.SUBDIVISION_LIST;
				},{
					location_code:($scope.search.division === undefined || $scope.search.division === null ? '' : $scope.search.division.key)
				}, 'POST');
			}else{
				$scope.subdivisionlist=[];$scope.omsectionlist=[];
				if($scope.usermanagement.division === undefined || $scope.usermanagement.division === null){
					return;
				}
				remote.load("getsubdivisionlist", function(response){
					$scope.subdivisionlist = response.SUBDIVISION_LIST;
					console.log("getsubdivisionList",response);
						if(arr.length > 0){
							$scope.usermanagement.subdivision = $filter('filter')($scope.subdivisionlist,{key:arr[3]},true)[0];
							if(usertype > 4){
							$scope.getomsectionList(arr,usertype,'modal');
						}
					}
				},{
					location_code:($scope.usermanagement.division === undefined || $scope.usermanagement.division === null ? '' : $scope.usermanagement.division.key)
				}, 'POST');
			}

			
		};
		
		$scope.getomsectionList = function(arr,usertype,searchtype){
			if(searchtype === 'search'){
				$scope.searchomsectionlist=[];
				if($scope.search.subdivision === undefined || $scope.search.subdivision === null){
					return;
				}
				remote.load("getomsectionlist", function(response){
					$scope.searchomsectionlist = response.OMSECTION_LIST;
				},{
					location_code:($scope.search.subdivision === undefined || $scope.search.subdivision === null ? '' : $scope.search.subdivision.key)
				}, 'POST');
			}else{
				$scope.omsectionlist=[];
				if($scope.usermanagement.subdivision === undefined || $scope.usermanagement.subdivision === null){
					return;
				}
				remote.load("getomsectionlist", function(response){
					$scope.omsectionlist = response.OMSECTION_LIST;
					console.log("getomsectionlist",response);
						if(arr.length > 0){
							$scope.usermanagement.omsection = $filter('filter')($scope.omsectionlist,{key:arr[4]},true)[0];
					}
				},{
					location_code:($scope.usermanagement.subdivision === undefined || $scope.usermanagement.subdivision === null ? '' : $scope.usermanagement.subdivision.key)
				}, 'POST');
			}
			
			
		};
		
		$scope.usertypeselected = false;
		$scope.zoneuser = false,$scope.circleuser=false,divisionuser=false,subdivisionuser=false,omsectionuser=false;
		$scope.setusertype = function(){
			if($scope.usermanagement.usertype != undefined || $scope.usermanagement.usertype != null){
				$scope.usertypeselected = true;
				if($scope.usermanagement.usertype.key == 1){
					$scope.zoneuser = true,$scope.circleuser=false,$scope.divisionuser=false,$scope.subdivisionuser=false,$scope.omsectionuser=false;
				}else if($scope.usermanagement.usertype.key == 2){
					$scope.zoneuser = true,$scope.circleuser=true,$scope.divisionuser=false,$scope.subdivisionuser=false,$scope.omsectionuser=false;
				}else if($scope.usermanagement.usertype.key == 3){
					$scope.zoneuser = true,$scope.circleuser=true,$scope.divisionuser=true,$scope.subdivisionuser=false,$scope.omsectionuser=false;
				}else if($scope.usermanagement.usertype.key == 4){
					$scope.zoneuser = true,$scope.circleuser=true,$scope.divisionuser=true,$scope.subdivisionuser=true,$scope.omsectionuser=false;
				}else if($scope.usermanagement.usertype.key == 5){
					$scope.zoneuser = true,$scope.circleuser=true,$scope.divisionuser=true,$scope.subdivisionuser=true,$scope.omsectionuser=true;
				}
			}else{
				$scope.usertypeselected = false;
				$scope.zoneuser = false,$scope.circleuser=false,divisionuser=false,subdivisionuser=false,omsectionuser=false;
			}
		}
		
		$scope.getuserdetailsfunction = function(search_location){
			remote.load("getuserdetailslist", function(response){
				$scope.getuserdetailslist = response.data;
				console.log("getuserdetailslist",response);
			},{
				location_code : search_location
			}, 'POST');
			
		};
		
		$scope.addeditUser = function(record,action){
			
			if(action === 'add'){
				$scope.modal_heading = "Add User";
				
				$scope.action = 'add';
				
				$scope.usermanagement.usertype = undefined;
				$scope.setusertype();
				$scope.getzonelist(null,usertype,'modal');
				
				$scope.usermanagement.chnagepassword=undefined;
				$scope.usermanagement.userid = '';
				$scope.usermanagement.password = '';
				$scope.usermanagement.retypepassword = '';
				$scope.usermanagement.enabled = undefined;
				$scope.usermanagement.userrole = undefined;

				$scope.usermanagement.imeinumber = '';
				$scope.usermanagement.username = '';
				$scope.usermanagement.address = '';
				$scope.usermanagement.mobilenumber = '';
				$scope.usermanagement.emailid = '';
				
				$scope.usermanagement.effectivefrom = moment(new Date()).format("DD/MM/YYYY").toString();
				$scope.usermanagement.effectiveto = '';
				
				$scope.usermanagement.enabled =  $filter('filter')($scope.statuslist,{key:'Y'},true)[0] ;
				
				$scope.newuseridexists = true;
				$scope.ROWID = "";
			}
			
			if(action === 'edit'){
				$scope.modal_heading = "Edit User";
				
				$scope.action = 'edit';
				$scope.ROWID = record.row_id;
				
				var loc = record.DUM_LOCATION_CODE;
				var zone= "",circle= "",division= "",subdivision= "",omsection = "";
				var usertype = 0;
				if(loc.length == 2){zone=loc.substr(0,2);usertype=1;}
				if(loc.length == 3){zone=loc.substr(0,2);circle=loc.substr(0,3);usertype=2;}
				if(loc.length == 5){zone=loc.substr(0,2);circle=loc.substr(0,3);division=loc.substr(0,5);usertype=3;}
				if(loc.length == 7){zone=loc.substr(0,2);circle=loc.substr(0,3);division=loc.substr(0,5);subdivision=loc.substr(0,7);usertype=4;}
				if(loc.length == 9){zone=loc.substr(0,2);circle=loc.substr(0,3);division=loc.substr(0,5);subdivision=loc.substr(0,7);omsection=loc.substr(0,9);usertype=5;}
				
				$scope.usermanagement.usertype = $filter('filter')($scope.usertypelist,{key:usertype},true)[0] ;
				$scope.setusertype();
				
				var arr = [];
				if(usertype == 1){var arr = [zone];$scope.getzonelist(arr,usertype,'modal');}
				if(usertype == 2){var arr = [zone,circle];$scope.getzonelist(arr,usertype,'modal');}
				if(usertype == 3){var arr = [zone,circle,division];$scope.getzonelist(arr,usertype,'modal');}
				if(usertype == 4){var arr = [zone,circle,division,subdivision];$scope.getzonelist(arr,usertype,'modal');}
				if(usertype == 5){var arr = [zone,circle,division,subdivision,omsection];$scope.getzonelist(arr,usertype,'modal');}
				
				$scope.usermanagement.enabled =  $filter('filter')($scope.statuslist,{key:record.DUM_STATUS},true)[0] ;
				$scope.usermanagement.userrole =  $filter('filter')($scope.userrolelist,{key:record.DUM_USER_ROLE},true)[0] ;
				
				$scope.newuseridexists = false;
				$scope.usermanagement.userid = record.DUM_USER_ID;
				$scope.usermanagement.imeinumber = record.IMEI_NO;
				$scope.usermanagement.username = record.DUM_USER_NAME;
				$scope.usermanagement.address = record.DUM_USER_MOBILE;
				$scope.usermanagement.mobilenumber = record.DUM_USER_ADDRESS;
				$scope.usermanagement.emailid = record.DUM_USER_EMAIL_ID;
				
				$scope.usermanagement.effectivefrom = record.DUM_EFFECTIVE_FROM;
				$scope.usermanagement.effectiveto = record.DUM_EFFECTIVE_TO;
				
			}
			
			
			
			
		};
		
		$scope.setchnagepassword = function(){
			
			console.log("usermanagement.chnagepassword",$scope.usermanagement.chnagepassword);
			
			if($scope.usermanagement.chnagepassword){
				$scope.usermanagement.password = ''; 
				$scope.usermanagement.retypepassword = ''; 
			}
		};
		
		$scope.newuseridexists = true;
		$scope.VerifyUserId = function(){
			$scope.newuseridexists = true;
			if($scope.usermanagement.userid === null || $scope.usermanagement.userid === undefined || $scope.usermanagement.userid === ''){return;}
			remote.load("verifyuserid", function(response){
				console.log("verifyuserid",response);
				$scope.newuseridexists = response.exists;
			},{
				userid:$scope.usermanagement.userid
			}, 'POST');
		}
		
		$scope.SaveRecord = function(){
			var location_code;
			if($scope.usermanagement.usertype.key === 1){
				if($scope.usermanagement.zone === undefined || $scope.usermanagement.zone === null){notify.warn("Please Select Zone !!!");return;}
				else{location_code=$scope.usermanagement.zone.key;}}
			if($scope.usermanagement.usertype.key === 2){
				if($scope.usermanagement.zone === undefined || $scope.usermanagement.zone === null){notify.warn("Please Select Zone !!!");return;}
				if($scope.usermanagement.circle === undefined || $scope.usermanagement.circle === null){notify.warn("Please Select Circle !!!");return;}
				else{location_code=$scope.usermanagement.circle.key;}
			}
			if($scope.usermanagement.usertype.key === 3){
				if($scope.usermanagement.zone === undefined || $scope.usermanagement.zone === null){notify.warn("Please Select Zone !!!");return;}
				if($scope.usermanagement.circle === undefined || $scope.usermanagement.circle === null){notify.warn("Please Select Circle !!!");return;}
				if($scope.usermanagement.division === undefined || $scope.usermanagement.division === null){notify.warn("Please Select Division !!!");return;}
				else{location_code=$scope.usermanagement.division.key;}
			}
			if($scope.usermanagement.usertype.key === 4){
				if($scope.usermanagement.zone === undefined || $scope.usermanagement.zone === null){notify.warn("Please Select Zone !!!");return;}
				if($scope.usermanagement.circle === undefined || $scope.usermanagement.circle === null){notify.warn("Please Select Circle !!!");return;}
				if($scope.usermanagement.division === undefined || $scope.usermanagement.division === null){notify.warn("Please Select Division !!!");return;}
				if($scope.usermanagement.subdivision === undefined || $scope.usermanagement.subdivision === null){notify.warn("Please Select Sub-Division !!!");return;}
				else{location_code=$scope.usermanagement.subdivision.key;}
			}
			if($scope.usermanagement.usertype.key === 5){
				if($scope.usermanagement.zone === undefined || $scope.usermanagement.zone === null){notify.warn("Please Select Zone !!!");return;}
				if($scope.usermanagement.circle === undefined || $scope.usermanagement.circle === null){notify.warn("Please Select Circle !!!");return;}
				if($scope.usermanagement.division === undefined || $scope.usermanagement.division === null){notify.warn("Please Select Division !!!");return;}
				if($scope.usermanagement.subdivision === undefined || $scope.usermanagement.subdivision === null){notify.warn("Please Select Sub-Division !!!");return;}
				if($scope.usermanagement.omsection === undefined || $scope.usermanagement.omsection === null){notify.warn("Please Select O&M Section !!!");return;}
				else{location_code=$scope.usermanagement.omsection.key;}
			}
			
			if($scope.action === 'add'){
				if($scope.usermanagement.userid === undefined || $scope.usermanagement.userid === null){notify.warn("Please enter userid !!!");return;}
				if($scope.usermanagement.password === undefined || $scope.usermanagement.password === null){notify.warn("Please enter password !!!");return;}
				if($scope.usermanagement.retypepassword === undefined || $scope.usermanagement.retypepassword === null){notify.warn("Please enter retype password !!!");return;}
				if($scope.usermanagement.retypepassword != $scope.usermanagement.password){notify.error("Password Not Matching.Please Re-type");return;}
			}
			
			if($scope.action === 'edit'){
				if($scope.usermanagement.chnagepassword){
					if($scope.usermanagement.password === undefined || $scope.usermanagement.password === null){notify.warn("Please enter password !!!");return;}
					if($scope.usermanagement.retypepassword === undefined || $scope.usermanagement.retypepassword === null){notify.warn("Please enter retype password !!!");return;}
					if($scope.usermanagement.retypepassword != $scope.usermanagement.password){notify.error("Password Not Matching.Please Re-type");return;}
				}
			}
			
			if($scope.usermanagement.enabled === undefined || $scope.usermanagement.enabled === null){notify.warn("Please Select User Status");return;}
			if($scope.usermanagement.userrole === undefined || $scope.usermanagement.userrole === null){notify.warn("Please Select User Role");return;}
			
			if($scope.usermanagement.userrole.key === 'SURVEYOR'){
				if($scope.usermanagement.imeinumber == undefined || $scope.usermanagement.imeinumber === null){
					notify.warn("Please enter IMEI number !!!");
					return;
				}
				if($scope.usermanagement.usertype.key != 5){
					notify.error("SURVEYOR Role should be mapped to O&M Section level only !!!");
					return;
				}
			}
			
			if($scope.usermanagement.effectivefrom == undefined || $scope.usermanagement.effectivefrom === null){
				notify.warn("Please select effective-from date !!!");
				
			}
			
			var request = {
					rowid:$scope.ROWID,
					location_code:location_code,
					userid:$scope.usermanagement.userid,
					password:$scope.usermanagement.password,
					username: $scope.usermanagement.username,
					address: $scope.usermanagement.address,
					mobileno: $scope.usermanagement.mobilenumber,
					emailid: $scope.usermanagement.emailid,
					enabled:$scope.usermanagement.enabled.key,
					role:$scope.usermanagement.userrole.key,
					imeino:$scope.usermanagement.imeinumber,
					effectivefrom:$scope.usermanagement.effectivefrom,
					effectiveto:$scope.usermanagement.effectiveto,
					createdby:$scope.userinfo.username
			};
			
			console.log("save request :",request);
			
			remote.load("upsertuserdetails", function(response){
				console.log("upsertuserdetails",response);
				if(response.status === 'success'){
					$timeout(function(){
						$('#user-managemaent-addedit-modal').modal('toggle');
						$scope.SearchUserDetails();
					},2000);
				}
			},request, 'POST');
		};
		
		$scope.SearchUserDetails = function(){
			
			var search_location = "";
			if($scope.search.zone != undefined || $scope.search.zone != null){search_location = $scope.search.zone.key;}
			if($scope.search.circle != undefined || $scope.search.circle != null){search_location = $scope.search.circle.key;}
			if($scope.search.division != undefined || $scope.search.division != null){search_location = $scope.search.division.key;}
			if($scope.search.subdivision != undefined || $scope.search.subdivision != null){search_location = $scope.search.subdivision.key;}
			if($scope.search.omsection != undefined || $scope.search.omsection != null){search_location = $scope.search.omsection.key;}
			
			$scope.getuserdetailsfunction(search_location);
		};
		
		$scope.ResetUserDetails = function(){
			$scope.getzonelist([],usertype,'search');
			$scope.getuserdetailslist = [];
		};
		
		
		
		
		$scope.getzonelist([],usertype,'search');
		
		
	});
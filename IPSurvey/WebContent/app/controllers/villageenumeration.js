/**
 * 
 */

angular.module('ipsurveyapp.Controllers', [])

	.controller("villageenumerationCtrl",function($scope,$rootScope, $http, $filter, $compile, $state,$cookies,remote,notify,store,$timeout,VillageMasterData) {
		
		console.log("villageenumeration Controller Initiated");
		
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
				$scope.searchsubdivisionlist=[];$scope.searchomsectionlist=[];
				if($scope.search.division === undefined || $scope.search.division === null){
					return;
				}
				remote.load("getsubdivisionlist", function(response){
					$scope.searchsubdivisionlist = response.SUBDIVISION_LIST;
					if(arr.length > 0){
						$scope.search.subdivision = $filter('filter')($scope.searchsubdivisionlist,{key:arr[3]},true)[0];
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
							$scope.getvillagemasterdatalist();
						}
					},{
						location_code:($scope.search.subdivision === undefined || $scope.search.subdivision === null ? '' : $scope.search.subdivision.key)
					}, 'POST');
				}
				
			
			
		};
		
		$scope.initialize();
		
		$scope.getvillagemasterdatalist = function(){
			
			if($scope.modal.omsection === undefined || $scope.modal.omsection === null){
				notify("Please select O&M section !!!");
				return;
			}
			
			remote.load("getvillagemasterdata", function(response){
				var getvillagemasterdata = response.VILLAGE_MASTER_DATA;
				$scope.VILLAGE_MASTER_DATA = [];
				getvillagemasterdata.map(function(e,index){
					$scope.VILLAGE_MASTER_DATA.push(e.VM_VILLAGE_NAME);
				});
				$scope.fillvillagedata();
			},{
				location_code:($scope.modal.omsection === undefined || $scope.modal.omsection === null ? '' : $scope.modal.omsection.key)
			}, 'POST');
		};
		
		
		$scope.SearchVillageEnumDetails = function(){
			if($scope.search.omsection === undefined || $scope.search.omsection === null){
				notify.warn("Please select O&M Section !!!");
				return;
			}
			remote.load("getvillageenumerationdetails", function(response){
				$scope.VILLAGE_ENUM_DATA = response.data;
			},{
				location_code:($scope.search.omsection === undefined || $scope.search.omsection === null ? '' : $scope.search.omsection.key)
			}, 'POST');
			
		};
		
		
		$scope.ROWID = "";
		$scope.imagedata = "";
		$scope.addeditVillageEnumeration = function(record,action){
			
			$scope.ROWID = "";
			$scope.imagedata = "";
			 $('#uploadPreview').attr('src', null);
			 $scope.modal.changeimage = false;
			 $scope.modal.chooseimage = null;
			 $('#modalchooseimage').val('');
			 
			$scope.getomsectionList([],null,'modal');
			if(action === 'add'){
				$scope.action = 'add';
				
				$scope.modal_heading = "Add Village Enumeration Data";
				$scope.newuseridexists = true;
				
				
				$scope.search.autocompletevillagename = '';
				$scope.modal.latitude = '' ;
				$scope.modal.longitude = '' ;
				$scope.modal.altitude = '';
				$scope.modal.remarks = '';
				
			}else{
				
				console.log(record);
				$scope.ROWID = record.row_id;
				$scope.action = 'edit';
				$scope.newuseridexists = false;
				$scope.modal_heading = "Edit Village Enumeration Data";
				
				$scope.modal.omsection = $filter('filter')($scope.modalomsectionlist,{key:record.VE_LOCATION_CODE},true)[0];
				//$scope.getvillagemasterdatalist();
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
				}
			}
			
		};
		
		$scope.newuseridexists = true;
		$scope.verifyvillageexists = function(){
			$scope.newuseridexists = true;
			if($scope.search.autocompletevillagename === null || $scope.search.autocompletevillagename === undefined || $scope.search.autocompletevillagename === ''){return;}
			remote.load("verifyvillageexists", function(response){
				console.log("verifyvillageexists",response);
				$scope.newuseridexists = response.exists;
			},{
				village_name:$scope.search.autocompletevillagename,
				location_code:$scope.modal.omsection.key
			}, 'POST');
			
		};
		
		$scope.SaveRecord = function(){
			
			var files=$('#modalchooseimage')[0].files;
			var formdata = new FormData(); 
			
			if($scope.modal.omsection === undefined || $scope.modal.omsection === null){notify.warn("Please Select OM Section");return;}
			if($scope.search.autocompletevillagename === undefined || $scope.search.autocompletevillagename === null){notify.warn("Please enter village name");return;}
			if($scope.modal.latitude === undefined || $scope.modal.latitude === null){notify.warn("Please enter latitude");return;}
			if($scope.modal.longitude === undefined || $scope.modal.longitude === null){notify.warn("Please enter longitude");return;}
			if($scope.modal.altitude === undefined || $scope.modal.altitude === null){notify.warn("Please enter altitude");return;}
			
			var imagepath = "";
			if(files.length > 0){
				imagepath = $scope.modal.omsection.key+"_"+$scope.search.autocompletevillagename+"_"+files[0].name;
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
					village_name:$scope.search.autocompletevillagename,
					latitude:$scope.modal.latitude,
					longitude:$scope.modal.longitude,
					altitude:$scope.modal.altitude,
					remarks:($scope.modal.remarks === undefined || $scope.modal.remarks === null ? '' : $scope.modal.remarks),
					userid:$scope.userinfo.username,
					imagepath:(imagepath == null || imagepath == '' || imagepath == undefined ? '' : imagepath)	
			};
			
			console.log("request",request);
			
			remote.load("upsertvillageenumeration", function(response){
				console.log("upsertvillageenumeration",response);
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
										$('#villageenumerate-addedit-modal').modal('toggle');
										$scope.SearchVillageEnumDetails();
									},2000);
						        },function (data){
							  		//console.log(data);
							  		$('#loading').hide();
							  	});
						}else{
							$timeout(function(){
								$('#villageenumerate-addedit-modal').modal('toggle');
								$scope.SearchVillageEnumDetails();
							},2000);
						}
				}
			},request, 'POST');
			


			
		};
		
		
		$scope.fillvillagedata = function(){
			
			/*$scope.movies = ["Lord of the Rings",
				"Drive",
				"Science of Sleep",
				"Back to the Future",
				"Oldboy"];*/

			console.log("VILLAGE_MASTER_DATA",$scope.VILLAGE_MASTER_DATA);
			$scope.SEARCHED_VILLAGE_MASTER_DATA = angular.copy($scope.VILLAGE_MASTER_DATA);
			// gives another movie array on change
			$scope.updateVillages = function(typed){
				// MovieRetriever could be some service returning a promise
				$scope.newvillages = VillageMasterData.getVillages($scope.VILLAGE_MASTER_DATA,typed);
				$scope.newvillages.then(function(data){
					$scope.SEARCHED_VILLAGE_MASTER_DATA = data;
				});
			}
			
			
		};
		
	})
	
.factory('VillageMasterData', function($http, $q, $timeout){
  var VillageMasterData = new Object();

  VillageMasterData.getVillages = function(villages_array,i) {
    var villagedata = $q.defer();
    var villages;

    if(i && i.indexOf('T')!=-1)
    	villages=villages_array;
    else
    	villages=villages_array;

    $timeout(function(){
    	villagedata.resolve(villages);
    },1000);

    return villagedata.promise
  }
  return VillageMasterData;
})
	
	// the service that retrieves some movie title from an url
/*.factory('MovieRetriever', function($http, $q, $timeout){
  var MovieRetriever = new Object();

  MovieRetriever.getmovies = function(i) {
    var moviedata = $q.defer();
    var movies;

    var someMovies = ["The Wolverine", "The Smurfs 2", "The Mortal Instruments: City of Bones", "Drinking Buddies", "All the Boys Love Mandy Lane", "The Act Of Killing", "Red 2", "Jobs", "Getaway", "Red Obsession", "2 Guns", "The World's End", "Planes", "Paranoia", "The To Do List", "Man of Steel"];

    var moreMovies = ["The Wolverine", "The Smurfs 2", "The Mortal Instruments: City of Bones", "Drinking Buddies", "All the Boys Love Mandy Lane", "The Act Of Killing", "Red 2", "Jobs", "Getaway", "Red Obsession", "2 Guns", "The World's End", "Planes", "Paranoia", "The To Do List", "Man of Steel", "The Way Way Back", "Before Midnight", "Only God Forgives", "I Give It a Year", "The Heat", "Pacific Rim", "Pacific Rim", "Kevin Hart: Let Me Explain", "A Hijacking", "Maniac", "After Earth", "The Purge", "Much Ado About Nothing", "Europa Report", "Stuck in Love", "We Steal Secrets: The Story Of Wikileaks", "The Croods", "This Is the End", "The Frozen Ground", "Turbo", "Blackfish", "Frances Ha", "Prince Avalanche", "The Attack", "Grown Ups 2", "White House Down", "Lovelace", "Girl Most Likely", "Parkland", "Passion", "Monsters University", "R.I.P.D.", "Byzantium", "The Conjuring", "The Internship"]

    if(i && i.indexOf('T')!=-1)
      movies=moreMovies;
    else
      movies=moreMovies;

    $timeout(function(){
      moviedata.resolve(movies);
    },1000);

    return moviedata.promise
  }

  return MovieRetriever;
})*/

;
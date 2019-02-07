/**
 * 
 */

angular.module('ipsurveyapp.Controllers', [])
	.controller("viewmapCtrl",function($scope,$rootScope, $http, $filter, $compile, $state,$cookies,remote,notify,store,$timeout) {
		
		console.log("Viewmap Controller Initiated");
		
		if(!store.get('userinfo')){
			$rootScope.logout();
		}else{
			$rootScope.IsLoggedIn = true;
		}
		
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
		
		var map ; 
		var marker ; 
		var infowindow = new google.maps.InfoWindow();
		var sidebar;
		
		  var iconBase = 'app/images/map/';
	      var icons = {
	    		  	pole: {icon: iconBase + 'powerlinepole.png'},
	    		  	transformer25: {icon: iconBase + 'powersubstation_blue_25.png'},
	    		  	transformer63: { icon: iconBase + 'powersubstation_grey_63.png' },
	    		  	transformer100: { icon: iconBase + 'powersubstation_green_100.png' },
    		  		transformer160: {icon: iconBase + 'powersubstation_white_25.png'},
	                transformer250: {icon: iconBase + 'powersubstation_red_250.png' },
	                transformer: {icon: iconBase + 'powersubstation_black.png'},
	                ipset: { icon: iconBase + 'information_red.png'},
	                village: { icon: iconBase + 'smallcity.png'  }
	      };
		
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
		
		$scope.getenumeratedvillageslist = function(value){
			$scope.getenumeratedvillages = [];
			if($scope.search.omsection === undefined || $scope.search.omsection === null){
				notify.warn("Please select O&M Section !!!");
				return;
			}
			remote.load("getenumeratedvillageslist", function(response){
				$scope.getenumeratedvillages = response.VILLAGE_ENUM_DATA;
				if(value != null || value.length >0){
					$scope.modal.village =  $filter('filter')($scope.getenumeratedvillages,{key:value},true)[0];
				}
			},{
				location_code:($scope.search.omsection === undefined || $scope.search.omsection === null ? '' : $scope.search.omsection.key)
			}, 'POST');
		};
		
		$scope.initialize();
		
/*		$scope.initialize = function() {
			 var myLatLng = {lat: 12.9761851, lng: 77.6403459};
			 
			 var mapProp = {
					    center:new google.maps.LatLng(13.22312,75.00572),
					    zoom:16,
					    mapTypeId:google.maps.MapTypeId.ROADMAP
					  };
			 map=new google.maps.Map(document.getElementById("googleMap"),mapProp);
					  
		 			   var marker = new google.maps.Marker({
						      position: myLatLng,
						      map: map,
						      title: 'Hello World!'
						    });  
		 			   
		}*/
		
		$scope.init_leaflet = function(){
			
			var googleStreets = L.tileLayer('http://{s}.google.com/vt/lyrs=m&x={x}&y={y}&z={z}', {
				maxZoom: 20,
				subdomains: ['mt0', 'mt1', 'mt2', 'mt3']
			});
			
			var googleSatellite = L.tileLayer('http://{s}.google.com/vt/lyrs=s&x={x}&y={y}&z={z}', {
				maxZoom: 20,
				subdomains: ['mt0', 'mt1', 'mt2', 'mt3']
			});
			
			var baseLayers = {
					"Google Streets": googleStreets,
					"Google Satellite": googleSatellite,
			};
			
			map = L.map("googleMap", {
				center: [12.91723, 74.85603],
				preferCanvas: true,
				zoom: 10,
				layers: [googleStreets], 
				zoomControl: false,
				maxZoom: 30,
				keyboard: true,
				attributionControl: true
				//drawControl: true,
			});

			L.control.layers(baseLayers).addTo(map);

			L.control.zoom({
				position: 'bottomright'
			}).addTo(map);
			
/*			// create a red polyline from an array of LatLng points
			var latlngs = [
			    [45.51, -122.68],
			    [37.77, -122.43],
			    [34.04, -118.2]
			];
			var polyline = L.polyline(latlngs, {color: 'red'}).addTo(map);
			// zoom the map to the polyline
			map.fitBounds(polyline.getBounds());
			
			// create a red polygon from an array of LatLng points
			var latlngs = [[37, -109.05],[41, -109.03],[41, -102.05],[37, -102.04]];
			var polygon = L.polygon(latlngs, {color: 'red'}).addTo(map);
			// zoom the map to the polygon
			map.fitBounds(polygon.getBounds());
			
			// define rectangle geographical bounds
			var bounds = [[54.559322, -5.767822], [56.1210604, -3.021240]];
			// create an orange rectangle
			L.rectangle(bounds, {color: "#ff7800", weight: 1}).addTo(map);
			// zoom the map to the rectangle bounds
			map.fitBounds(bounds);
			
			var myIcon = L.icon({
			    iconUrl: 'my-icon.png',
			    iconSize: [38, 95],
			    iconAnchor: [22, 94],
			    popupAnchor: [-3, -76],
			    shadowUrl: 'my-icon-shadow.png',
			    shadowSize: [68, 95],
			    shadowAnchor: [22, 94]
			});
			L.marker([50.505, 30.57], {icon: myIcon}).addTo(map);*/
			
			sidebar = L.control.sidebar('sidebar').addTo(map);
		};
		
		$scope.applyfiletrs = function(){
			
			$scope.gettransformerpoints();
			
			$('#sidebar').toggleClass('collapsed');
			
		};
		
		$scope.gettransformerpoints = function(){
			var myIcon = L.icon({
			    iconUrl: 'app/images/map/powerlinepole.png'
			});
			
			var lat = "",lng = ""
			remote.load("gettransformerpoints", function(response){
				$scope.transformerpoints = response.data;
				
				$scope.transformerpoints.map(function(e,index){
					//L.marker([e.TE_LATTITUDE, e.TE_LONGITUDE]).addTo(map);
					if(index === 0){
						lat = e.TE_LATTITUDE;
						lng = e.TE_LONGITUDE;
					}
					L.marker([e.TE_LATTITUDE, e.TE_LONGITUDE], {icon: myIcon}).addTo(map);
				});
				
				//map.panTo(new L.LatLng(lat, lng));
				map.setView(new L.LatLng(lat, lng),13);
				
			},{
				location_code:($scope.search.omsection === undefined || $scope.search.omsection === null ? '' : $scope.search.omsection.key),
				village:($scope.modal.village === undefined || $scope.modal.village === null ? '' : $scope.modal.village.key)
			}, 'POST');
			
		};
		
		
		
		$timeout(function () {
			/*$scope.initialize();*/
			$scope.init_leaflet();
		}, 1000);
		
		
		//sidebar.open('search');
		
		
		

		
	});
/**
 * 
 */

angular.module('ipsurveyapp.Controllers', [])
	.controller("viewmapCtrl",function($scope,$rootScope, $http, $filter, $compile, $state,$cookies,remote,notify,store,$timeout) {
		
		console.log("Viewmap Controller Initiated");
		
		var LOCATION_CODE = store.get('LOCATION_CODE');
		$rootScope.LOCATION_CODE = LOCATION_CODE;
		
		if(!store.get('userinfo')){
			$rootScope.logout();
			//$state.go("login");
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
		var loc_company= "",loc_zone= "",loc_circle= "",loc_division= "",loc_subdivision= "",loc_omsection = "";
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
			if(LOCATION_CODE.length == 1){loc_company=LOCATION_CODE.substr(0,1);loc_usertype=0;}
			if(LOCATION_CODE.length == 2){loc_company=LOCATION_CODE.substr(0,1);loc_zone=LOCATION_CODE.substr(0,2);loc_usertype=1;}
			if(LOCATION_CODE.length == 3){loc_company=LOCATION_CODE.substr(0,1);loc_zone=LOCATION_CODE.substr(0,2);loc_circle=LOCATION_CODE.substr(0,3);loc_usertype=2;}
			if(LOCATION_CODE.length == 5){loc_company=LOCATION_CODE.substr(0,1);loc_zone=LOCATION_CODE.substr(0,2);loc_circle=LOCATION_CODE.substr(0,3);loc_division=LOCATION_CODE.substr(0,5);loc_usertype=3;}
			if(LOCATION_CODE.length == 7){loc_company=LOCATION_CODE.substr(0,1);loc_zone=LOCATION_CODE.substr(0,2);loc_circle=LOCATION_CODE.substr(0,3);loc_division=LOCATION_CODE.substr(0,5);loc_subdivision=LOCATION_CODE.substr(0,7);loc_usertype=4;}
			if(LOCATION_CODE.length == 9){loc_company=LOCATION_CODE.substr(0,1);loc_zone=LOCATION_CODE.substr(0,2);loc_circle=LOCATION_CODE.substr(0,3);loc_division=LOCATION_CODE.substr(0,5);loc_subdivision=LOCATION_CODE.substr(0,7);loc_omsection=LOCATION_CODE.substr(0,9);loc_usertype=5;}
			

			if (loc_usertype == 0) {
				var loc_arr = [ loc_company ];
				$scope.ZONEUSER = false;
				$scope.CIRCLEUSER = false;
				$scope.DIVISIONUSER = false;
				$scope.SUBDIVISIONUSER = false;
				$scope.OMSECTIONUSER = false;
				$scope.getzonelist(loc_arr, loc_usertype, 'search');
			}
			if (loc_usertype == 1) {
				var loc_arr = [ loc_company,loc_zone ];
				$scope.ZONEUSER = true;
				$scope.CIRCLEUSER = false;
				$scope.DIVISIONUSER = false;
				$scope.SUBDIVISIONUSER = false;
				$scope.OMSECTIONUSER = false;
				$scope.getzonelist(loc_arr, loc_usertype, 'search');
			}
			if (loc_usertype == 2) {
				var loc_arr = [ loc_company,loc_zone, loc_circle ];
				$scope.ZONEUSER = false;
				$scope.CIRCLEUSER = true;
				$scope.DIVISIONUSER = false;
				$scope.SUBDIVISIONUSER = false;
				$scope.OMSECTIONUSER = false;
				$scope.getzonelist(loc_arr, loc_usertype, 'search');
			}
			if (loc_usertype == 3) {
				var loc_arr = [ loc_company,loc_zone, loc_circle, loc_division ];
				$scope.ZONEUSER = false;
				$scope.CIRCLEUSER = false;
				$scope.DIVISIONUSER = true;
				$scope.SUBDIVISIONUSER = false;
				$scope.OMSECTIONUSER = false;
				$scope.getzonelist(loc_arr, loc_usertype, 'search');
			}
			if (loc_usertype == 4) {
				var loc_arr = [ loc_company, loc_zone, loc_circle, loc_division,
						loc_subdivision ];
				$scope.ZONEUSER = false;
				$scope.CIRCLEUSER = false;
				$scope.DIVISIONUSER = false;
				$scope.SUBDIVISIONUSER = true;
				$scope.OMSECTIONUSER = false;
				$scope.getzonelist(loc_arr, loc_usertype, 'search');
			}
			if (loc_usertype == 5) {
				var loc_arr = [ loc_company, loc_zone, loc_circle, loc_division,
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
						
						
						if(usertype > 1){
							$scope.search.zone = $filter('filter')($scope.searchzonelist,{key:arr[1]},true)[0];
							$scope.getcircleList(arr,usertype,'search');
						}else if(usertype == 1){
							$scope.search.zone = $filter('filter')($scope.searchzonelist,{key:arr[1]},true)[0];
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
						$scope.search.circle = $filter('filter')($scope.searchcirclelist,{key:arr[2]},true)[0];
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
						$scope.search.division = $filter('filter')($scope.searchdivisionlist,{key:arr[3]},true)[0];
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
						$scope.search.subdivision = $filter('filter')($scope.searchsubdivisionlist,{key:arr[4]},true)[0];
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
							$scope.search.omsection = $filter('filter')($scope.searchomsectionlist,{key:arr[5]},true)[0];
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
		
		$scope.getenumeratedvillageslist = function(value){
			$scope.getenumeratedvillages = [];
			if($scope.search.omsection === undefined || $scope.search.omsection === null){
				//notify.warn("Please select O&M Section !!!");
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
		
		$scope.getenumeratedtransformerslist = function(value){
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
		
		
		
		var markers = [];
		var layerGroup;
		var custLegendHtml,custLegendHtmlCtrl;
		
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
			
			/*L.easyPrint({
				title: 'My awesome print button',
				position: 'bottomright',
				sizeModes: ['A4Portrait', 'A4Landscape']
			}).addTo(map);*/
			
			
			layerGroup = L.layerGroup().addTo(map)
			
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
		
			if($scope.search.search_rrnumber){
				if($scope.search.subdivision === undefined || $scope.search.subdivision === null){notify.warn("Please select Sub Division !!!");return;}
			}else{
				if($scope.search.omsection === undefined || $scope.search.omsection === null){notify.warn("Please select O&M Section !!!");return;}
			}
			
			
			
			//$scope.gettransformerpoints();
			$scope.getmappingpoints();
			
			$('#sidebar').toggleClass('collapsed');
			
		};
		
		$scope.getmappingpoints = function(){
			
			layerGroup.clearLayers();
			if(custLegendHtmlCtrl){
				map.removeControl(custLegendHtmlCtrl);
			} 
			$scope.mappingpoints = [];
			custLegendHtml="<div style='background-color: #efe;font-size: small;'><table class='table table-condensed'>";
			var lat_to_center = "",lng_to_center = ""
			var icon_transformer 		= L.icon({iconUrl: 'app/images/map/powersubstation_black.png'});
			var icon_transformer25 		= L.icon({iconUrl: 'app/images/map/powersubstation_blue_25.png'});
			var icon_transformer63 		= L.icon({iconUrl: 'app/images/map/powersubstation_grey_63.png'});
			var icon_transformer100 		= L.icon({iconUrl: 'app/images/map/powersubstation_green_100.png'});
			var icon_transformer160 		= L.icon({iconUrl: 'app/images/map/powersubstation_white_25.png'});
			var icon_transformer250 		= L.icon({iconUrl: 'app/images/map/powersubstation_red_250.png'});
			var icon_ipset 				= L.icon({iconUrl: 'app/images/map/information_red.png'});
			var icon_ipset_black 			= L.icon({iconUrl: 'app/images/map/black_letter_i.png'});
			var icon_ipset_red 			= L.icon({iconUrl: 'app/images/map/red_letter_i.png'});
			var icon_ipset_yellow 			= L.icon({iconUrl: 'app/images/map/yellow_letter_i.png'});
			var icon_village 			= L.icon({iconUrl: 'app/images/map/smallcity.png'});
			
			
			remote.load("getmappingpoints", function(response){
				$scope.mappingpoints = response.data;
				
				localStorage.setItem("MAP_DETAILS", JSON.stringify($scope.mappingpoints)); 
				localStorage.setItem("userinfo", JSON.stringify(store.get('userinfo'))); 
				var temp_obj = {
						station_code :($scope.search.station === undefined || $scope.search.station === null ? '' : $scope.search.station.value),
						feeder_code  :($scope.search.feeder === undefined || $scope.search.feeder === null ? '' : $scope.search.feeder.value) 
				} 
				localStorage.setItem("station_details", JSON.stringify(temp_obj)); 
				
				if($scope.mappingpoints.length == 0 ){
					notify.error("No Records Found !!!!");
					/*$timeout(function(){
						$scope.reset();
					},2000);*/
					return;
				}
				
				$scope.mappingpoints.map(function(e,index){
					
					//if(($scope.modal.transformers != undefined || $scope.modal.transformers != null)
						//	|| ($scope.search.search_rrnumber != undefined || search.search_rrnumber  != null )
					if(	$scope.search.search_rrnumber || $scope.modal.transformers	){
						
						localStorage.setItem("print_map_type", "IPSET");
						
						if(index === 0){
							lat_to_center = e.TC_LATTITUDE;
							lng_to_center = e.TC_LONGITUDE;
							
							var html='<table class="table table-condensed table-bordered" id="tableId" style="font-size: 12px;">';
							html+="<tr>";
							html+="<td colspan='4' style='width: 300px;background-color:aliceblue;vertical-align:middle;text-align:center;'><b>Transformer Details</b></td>";
							html+="</tr><tr><td ><b>O&M Section</b></td><td >"+e.OM_NAME+"</td><td ><b>Village Name</b></td><td >"+e.VILLAGE_NAME+"</td>";
							html+="</tr><tr><td ><b>Transformer Name</b></td><td >"+e.TC_NAME+"</td><td ><b>Capacity (KVA)</b></td><td >"+e.TC_CAPACITY_KVA+"</td>";
							html+="</tr><tr><td ><b>Station Name</b></td><td >"+e.STATION_NAME+"</td><td ><b>Feeder Name</b></td><td >"+e.FEEDER_NAME+"</td>";
							html+="</tr><tr><td ><b>Latitude</b></td><td >"+e.TC_LATTITUDE+"</td><td ><b>Longitude</b></td><td >"+e.TC_LONGITUDE+"</td>";
							
							var linkFunction = $compile(angular.element(html));
							console.log(linkFunction)
							
							var icon = "";
							if(e.TC_CAPACITY_KVA === "25"){icon=icon_transformer25;}else if(e.TC_CAPACITY_KVA === "63"){icon=icon_transformer63;}else if(e.TC_CAPACITY_KVA === "100"){icon=icon_transformer100;}
							else if(e.TC_CAPACITY_KVA === "160"){icon=icon_transformer160;}else if(e.TC_CAPACITY_KVA === "250"){icon=icon_transformer250;}else {icon=icon_transformer;}
							
							L.marker([e.TC_LATTITUDE, e.TC_LONGITUDE], {icon: icon})
							.bindPopup(L.popup({minWidth: 400, maxWidth: 500}).setContent(linkFunction($scope)[0]))
							.addTo(layerGroup);
							
							lat_to_center = e.VILLAGE_LATTITUDE;
							lng_to_center = e.VILLAGE_LONGITUDE;
							html='<table class="table table-condensed table-bordered" id="tableId" style="font-size: 12px;">';
							html+="<tr>";
							html+="<td colspan='4' style='width: 300px;background-color:aliceblue;vertical-align:middle;text-align:center;'><b>Village Details</b></td>";
							html+="</tr><tr><td ><b>O&M Section</b></td><td >"+e.OM_NAME+"</td><td ><b>Village Name</b></td><td >"+e.VILLAGE_NAME+"</td>";
							linkFunction = $compile(angular.element(html));
							L.marker([e.VILLAGE_LATTITUDE, e.VILLAGE_LONGITUDE], {icon: icon_village})
							.bindPopup(L.popup({minWidth: 400, maxWidth: 500}).setContent(linkFunction($scope)[0]))
							.addTo(layerGroup);
						}

						var html='<table class="table table-condensed table-bordered" id="tableId">';
						html+="<tr>";
						html+="<td colspan='6' style='width: 900px;background-color:aliceblue;vertical-align:middle;text-align:center;'><b>IP-Set Details</b></td>";
						html+="</tr><tr><td ><b>O&M Section</b></td><td >"+e.OM_NAME+"</td><td ><b>Village Name</b></td><td >"+e.VILLAGE_NAME+"</td><td ><b>Station Name</b></td><td >"+e.STATION_NAME+"</td>";
						html+="</tr><tr><td ><b>Feeder Name</b></td><td >"+e.FEEDER_NAME+"</td><td ><b>Transformer Name</b></td><td >"+e.TC_NAME+"</td><td ><b>Capacity (KVA)</b></td><td >"+e.TC_CAPACITY_KVA+"</td>";
						html+="</tr><tr><td ><b>RR Number</b></td><td >"+e.RR_NO+"</td><td ><b>Consumer Name</b></td><td >"+e.CUSTOMER_NAME+"</td><td ><b>Address</b></td><td >"+e.ADDRESS1 +" "+e.ADDRESS2+"</td>";
						html+="</tr><tr><td ><b>Load (HP)</b></td><td >"+e.LOAD_HP+"</td><td ><b>Service Date</b></td><td >"+e.SERVICE_DATE+"</td><td ><b>Inspection Date</b></td><td >"+e.INSPECTION_DATE+"</td>";
						html+="</tr><tr><td ><b>Connection Type</b></td><td >"+e.CONNECTION_TYPE+"</td><td ><b>Consumer Status</b></td><td >"+e.CUSTOMER_STATUS+"</td><td ><b>Water Source</b></td><td >"+e.WATER_SOURCE+"</td>";
						html+="</tr><tr><td ><b>Meter Make</b></td><td >"+e.METER_MAKE+"</td><td ><b>Meter Sl.No</b></td><td >"+e.METER_SLNO+"</td><td ><b>Meter Type</b></td><td >"+e.METER_TYPE+"</td>";
						html+="</tr><tr><td ><b>Current (B)</b></td><td >"+e.CURRENT_B+"</td><td ><b>Current (R)</b></td><td >"+e.CURRENT_R+"</td><td ><b>Current (Y)</b></td><td >"+e.CURRENT_Y+"</td>";
						html+="</tr><tr><td ><b>VOLTAGE (BR)</b></td><td >"+e.VOLTAGE_BR+"</td><td ><b>VOLTAGE (RB)</b></td><td >"+e.VOLTAGE_RB+"</td><td ><b>VOLTAGE (RY)</b></td><td >"+e.VOLTAGE_RY+"</td>";
						html+="</tr><tr><td ><b>Remarks</b></td><td colspan='5'>"+e.REMARKS+"</td>";
						
						var linkFunction = $compile(angular.element(html));
						console.log(linkFunction);
						
						var voltage_count = 0;
						var ipsetcolorcode = "";
						if(parseInt(e.VOLTAGE_BR) > 0){voltage_count++;}
						if(parseInt(e.VOLTAGE_RB) > 0){voltage_count++;}
						if(parseInt(e.VOLTAGE_RY) > 0){voltage_count++;}
						
						console.log("voltage_count",voltage_count);
						if(voltage_count == 1){icon=icon_ipset_black }
						else if(voltage_count == 3){icon=icon_ipset_red }
						else{icon=icon_ipset_yellow }
						
						L.marker([e.IP_LATTITUDE, e.IP_LONGITUDE], {icon: icon})
						.bindPopup(L.popup({minWidth: 400, maxWidth: 900}).setContent(linkFunction($scope)[0]))
						.addTo(layerGroup);
					
						
					}else{
						
						localStorage.setItem("print_map_type", "TRANSFORMER");
						
						//if(index === 0){
							lat_to_center = e.TC_LATTITUDE;
							lng_to_center = e.TC_LONGITUDE;
						//}
						
						var html='<table class="table table-condensed table-bordered" id="tableId" style="font-size: 12px;">';
						html+="<tr>";
						html+="<td colspan='4' style='width: 300px;background-color:aliceblue;vertical-align:middle;text-align:center;'><b>Transformer Details</b></td>";
						html+="</tr><tr><td ><b>O&M Section</b></td><td >"+e.OM_NAME+"</td><td ><b>Village Name</b></td><td >"+e.VILLAGE_NAME+"</td>";
						html+="</tr><tr><td ><b>Transformer Name</b></td><td >"+e.TC_NAME+"</td><td ><b>Capacity (KVA)</b></td><td >"+e.TC_CAPACITY_KVA+"</td>";
						html+="</tr><tr><td ><b>Station Name</b></td><td >"+e.STATION_NAME+"</td><td ><b>Feeder Name</b></td><td >"+e.FEEDER_NAME+"</td>";
						html+="</tr><tr><td ><b>Latitude</b></td><td >"+e.TC_LATTITUDE+"</td><td ><b>Longitude</b></td><td >"+e.TC_LONGITUDE+"</td>";
						
						var linkFunction = $compile(angular.element(html));
						console.log(linkFunction)
						
						var icon = "";
						if(e.TC_CAPACITY_KVA === "25"){icon=icon_transformer25;}
						else if(e.TC_CAPACITY_KVA === "63"){icon=icon_transformer63;}
						else if(e.TC_CAPACITY_KVA === "100"){icon=icon_transformer100;}
						else if(e.TC_CAPACITY_KVA === "160"){icon=icon_transformer160;}
						else if(e.TC_CAPACITY_KVA === "250"){icon=icon_transformer250;}
						else {icon=icon_transformer;}
						
						L.marker([e.TC_LATTITUDE, e.TC_LONGITUDE], {icon: icon})
						.bindPopup(L.popup({minWidth: 400, maxWidth: 500}).setContent(linkFunction($scope)[0]))
						.addTo(layerGroup);
					}
					
				});
				
				if($scope.modal.transformers != undefined || $scope.modal.transformers != null){
					custLegendHtml=custLegendHtml+"<tr><td><img src='app/images/map/smallcity.png' style='height: 25px;'></td><td>Village</td></tr>";
					custLegendHtml=custLegendHtml+"<tr><td><img src='app/images/map/powersubstation_blue_25.png' style='height: 25px;'></td><td>Transformer-25 KVA</td></tr>";
					custLegendHtml=custLegendHtml+"<tr><td><img src='app/images/map/powersubstation_grey_63.png' style='height: 25px;'></td><td>Transformer-63 KVA</td></tr>";
					custLegendHtml=custLegendHtml+"<tr><td><img src='app/images/map/powersubstation_green_100.png' style='height: 25px;'></td><td>Transformer-100 KVA</td></tr>";
					custLegendHtml=custLegendHtml+"<tr><td><img src='app/images/map/powersubstation_white_25.png' style='height: 25px;'></td><td>Transformer-160 KVA</td></tr>";
					custLegendHtml=custLegendHtml+"<tr><td><img src='app/images/map/powersubstation_red_250.png' style='height: 25px;'></td><td>Transformer-250 KVA</td></tr>";
					custLegendHtml=custLegendHtml+"<tr><td><img src='app/images/map/powersubstation_black.png' style='height: 25px;'></td><td>Transformer Others</td></tr>";
					//custLegendHtml=custLegendHtml+"<tr><td><img src='app/images/map/information_red.png'></td><td>IPset</td></tr>";
					custLegendHtml=custLegendHtml+"<tr><td><img src='app/images/map/black_letter_i.png' style='height: 25px;'></td><td>IPSET - Single Phase</td></tr>";
					custLegendHtml=custLegendHtml+"<tr><td><img src='app/images/map/red_letter_i.png' style='height: 25px;'></td><td>IPSET - Three Phase</td></tr>";
					custLegendHtml=custLegendHtml+"<tr><td><img src='app/images/map/yellow_letter_i.png' style='height: 25px;'></td><td>IPSET - Dried/Shifted/DL</td></tr>";
					
				}else{
					custLegendHtml=custLegendHtml+"<tr><td><img src='app/images/map/powersubstation_blue_25.png' style='height: 25px;'></td><td>Transformer-25 KVA</td></tr>";
					custLegendHtml=custLegendHtml+"<tr><td><img src='app/images/map/powersubstation_grey_63.png' style='height: 25px;'></td><td>Transformer-63 KVA</td></tr>";
					custLegendHtml=custLegendHtml+"<tr><td><img src='app/images/map/powersubstation_green_100.png' style='height: 25px;'></td><td>Transformer-100 KVA</td></tr>";
					custLegendHtml=custLegendHtml+"<tr><td><img src='app/images/map/powersubstation_white_25.png' style='height: 25px;'></td><td>Transformer-160 KVA</td></tr>";
					custLegendHtml=custLegendHtml+"<tr><td><img src='app/images/map/powersubstation_red_250.png' style='height: 25px;'></td><td>Transformer-250 KVA</td></tr>";
					custLegendHtml=custLegendHtml+"<tr><td><img src='app/images/map/powersubstation_black.png' style='height: 25px;'></td><td>Transformer Others</td></tr>";
				}
				
				custLegendHtml=custLegendHtml+"</table></div>";
				custLegendHtmlCtrl=L.control.custom({
					position: 'bottomright',
					content :custLegendHtml,               
					style :
					{
						margin: '5px',
						padding: '0px 0 0 0',
						cursor: 'pointer'
					},
					datas:
					{
						'foo': 'bar'
					},
					events:
					{
						click: function(data)
						{
						},
						dblclick: function(data)
						{
						},
						contextmenu: function(data)
						{
						}
					}
				}).addTo(map);
				
				map.setView(new L.LatLng(lat_to_center, lng_to_center),13);
				
			},{
				subdivisioncode:($scope.search.subdivision === undefined || $scope.search.subdivision === null ? '' : $scope.search.subdivision.key),
				location_code:($scope.search.omsection === undefined || $scope.search.omsection === null ? '' : $scope.search.omsection.key),
				station_code :($scope.search.station === undefined || $scope.search.station === null ? '' : $scope.search.station.key),
				feeder_code  :($scope.search.feeder === undefined || $scope.search.feeder === null ? '' : $scope.search.feeder.key),
				village      :($scope.modal.village === undefined || $scope.modal.village === null ? '' : $scope.modal.village.key),
				transformer_code:($scope.modal.transformers === undefined || $scope.modal.transformers === null ? '' : $scope.modal.transformers.key),
				search_rrnumber:($scope.search.search_rrnumber === undefined || $scope.search.search_rrnumber === null ? '' : $scope.search.search_rrnumber)
			}, 'POST');
			
			
		};
		
		$scope.reset = function(){
			layerGroup.clearLayers();
			$scope.mappingpoints = [];
			map.setView(new L.LatLng(12.91723, 74.85603),5);
			$scope.initialize();
			
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
					L.marker([e.TE_LATTITUDE, e.TE_LONGITUDE], {icon: myIcon}).addTo(map)
					.bindPopup('A pretty CSS3 popup.<br> Easily customizable.');
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
		
		$scope.printmap = function(){
			
			window.open("printmap.html","_blank");
			
		};
		
		
	});
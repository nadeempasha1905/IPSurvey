/**
 * 
 */

angular.module('ipsurveyapp.Controllers', [])
	.controller("viewmapCtrl", function($scope,$rootScope, $http, $filter, $compile, $state,$cookies,store,$timeout) {
		
		console.log("Viewmap Controller Initiated");
		
		if(!store.get('userinfo')){
			$rootScope.logout();
		}else{
			$rootScope.IsLoggedIn = true;
		}
		
		var map ; 
		var marker ; 
		var infowindow = new google.maps.InfoWindow();
		
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
			
			// create a red polyline from an array of LatLng points
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
			L.marker([50.505, 30.57], {icon: myIcon}).addTo(map);
			
			var sidebar = L.control.sidebar('sidebar').addTo(map);
		};
		
		
		$timeout(function () {
			/*$scope.initialize();*/
			$scope.init_leaflet();
		}, 1000);
		
		
		//sidebar.open('search');

		
	});
/**
 * 
 */

'use strict';
angular.module('ipsurveyapp',['ui.router','oc.lazyLoad','ngCookies','utility','angular-storage','autocomplete'])
.constant('CONTEXT_PATH','appname')
.constant('CACHEBUST_VERSION',"0.1")
.constant('SITE_LAST_UPDATED','28/01/2019')
.config(['$stateProvider', '$urlRouterProvider','$ocLazyLoadProvider',
 function($stateProvider, $urlRouterProvider,$ocLazyLoadProvider) {              


	$ocLazyLoadProvider.config({
        'debug': false, //For debugging 'true/false'
        'events':false, //For Event 'true/false'
        'modules': [{
        				name : 'home', 
        				files: ['app/controllers/home.js']
        			},
        			{
        				name : 'login', 
        				files: ['app/controllers/login.js']
        			},
        			{
        				name : 'stationmaster', 
        				files: ['app/controllers/stationmaster.js']
        			},
        			{
        				name : 'feedermaster', 
        				files: ['app/controllers/feedermaster.js']
        			},
        			{
        				name : 'transformermaster', 
        				files: ['app/controllers/transformermaster.js']
        			},
        			{
        				name : 'villagemaster', 
        				files: ['app/controllers/villagemaster.js']
        			},
        			{
        				name : 'villageenumeration', 
        				files: ['app/controllers/villageenumeration.js']
        			},
        			{
        				name : 'transformerenumeration', 
        				files: ['app/controllers/transformerenumeration.js']
        			},
        			{
        				name : 'ipsetenumeration', 
        				files: ['app/controllers/ipsetenumeration.js']
        			},
        			{
        				name : 'viewmap', 
        				files: ['app/controllers/viewmap.js']
        			},
        			{
        				name : 'reports', 
        				files: ['app/controllers/reports.js']
        			},
        			{
        				name : 'usermanagement', 
        				files: ['app/controllers/usermanagement.js']
        			},
        			{
        				name : 'changepassword', 
        				files: ['app/controllers/changepassword.js']
        			}
        			,
        			{
        				name : 'transformertransfer', 
        				files: ['app/controllers/transformertransfer.js']
        			},
        			{
        				name : 'ipsettransfer', 
        				files: ['app/controllers/ipsettransfer.js']
        			}
       		
        		
        ]});
    
	$stateProvider
    .state('home', {//State demonstrating Nested views
        url: "/home",
        cache: false , // add this line
        templateUrl: "app/templates/home.html",
        controller:'homeCtrl',                         
        resolve: {
            loadMyCtrl: ['$ocLazyLoad','$cookies','$state', function($ocLazyLoad,$cookies,$state) {
                return $ocLazyLoad.load('home'); 
            }]
        }  
    })
    .state('login', {//State demonstrating Nested views
        url: "/login",
        cache: false , // add this line
        templateUrl: "app/templates/login.html",
        controller:'loginCtrl',                         
        resolve: {
            loadMyCtrl: ['$ocLazyLoad','$cookies','$state', function($ocLazyLoad,$cookies,$state) {
                return $ocLazyLoad.load('login'); 
            }]
        }  
    })
    .state('feedermaster', {//State demonstrating Nested views
        url: "/feedermaster",
        cache: false , // add this line
        templateUrl: "app/templates/feedermaster.html",
        controller:'feedermasterCtrl',                         
        resolve: {
            loadMyCtrl: ['$ocLazyLoad','$cookies','$state', function($ocLazyLoad,$cookies,$state) {
                return $ocLazyLoad.load('feedermaster'); 
            }]
        }  
    })   
    .state('stationmaster', {//State demonstrating Nested views
        url: "/stationmaster",
        cache: false , // add this line
        templateUrl: "app/templates/stationmaster.html",
        controller:'stationmasterCtrl',                         
        resolve: {
            loadMyCtrl: ['$ocLazyLoad','$cookies','$state', function($ocLazyLoad,$cookies,$state) {
                return $ocLazyLoad.load('stationmaster'); 
            }]
        }  
    })
    .state('transformermaster', {//State demonstrating Nested views
        url: "/transformermaster",
        cache: false , // add this line
        templateUrl: "app/templates/transformermaster.html",
        controller:'transformermasterCtrl',                         
        resolve: {
            loadMyCtrl: ['$ocLazyLoad','$cookies','$state', function($ocLazyLoad,$cookies,$state) {
                return $ocLazyLoad.load('transformermaster'); 
            }]
        }  
    })
        .state('villagemaster', {//State demonstrating Nested views
        url: "/villagemaster",
        cache: false , // add this line
        templateUrl: "app/templates/villagemaster.html",
        controller:'villagemasterCtrl',                         
        resolve: {
            loadMyCtrl: ['$ocLazyLoad','$cookies','$state', function($ocLazyLoad,$cookies,$state) {
                return $ocLazyLoad.load('villagemaster'); 
            }]
        }  
    })
    .state('villageenumeration', {//State demonstrating Nested views
        url: "/villageenumeration",
        cache: false , // add this line
        templateUrl: "app/templates/villageenumeration.html",
        controller:'villageenumerationCtrl',                         
        resolve: {
            loadMyCtrl: ['$ocLazyLoad','$cookies','$state', function($ocLazyLoad,$cookies,$state) {
                return $ocLazyLoad.load('villageenumeration'); 
            }]
        }  
    })
     .state('transformerenumeration', {//State demonstrating Nested views
        url: "/transformerenumeration",
        cache: false , // add this line
        templateUrl: "app/templates/transformerenumeration.html",
        controller:'transformerenumerationCtrl',                         
        resolve: {
            loadMyCtrl: ['$ocLazyLoad','$cookies','$state', function($ocLazyLoad,$cookies,$state) {
                return $ocLazyLoad.load('transformerenumeration'); 
            }]
        }  
    })
     .state('ipsetenumeration', {//State demonstrating Nested views
        url: "/ipsetenumeration",
        cache: false , // add this line
        templateUrl: "app/templates/ipsetenumeration.html",
        controller:'ipsetenumerationCtrl',                         
        resolve: {
            loadMyCtrl: ['$ocLazyLoad','$cookies','$state', function($ocLazyLoad,$cookies,$state) {
                return $ocLazyLoad.load('ipsetenumeration'); 
            }]
        }  
    })
     .state('viewmap', {//State demonstrating Nested views
        url: "/viewmap",
        cache: false , // add this line
        templateUrl: "app/templates/viewmap.html",
        controller:'viewmapCtrl',                         
        resolve: {
            loadMyCtrl: ['$ocLazyLoad','$cookies','$state', function($ocLazyLoad,$cookies,$state) {
                return $ocLazyLoad.load('viewmap'); 
            }]
        }  
    })
     .state('reports', {//State demonstrating Nested views
        url: "/reports",
        cache: false , // add this line
        templateUrl: "app/templates/reports.html",
        controller:'reportsCtrl',                         
        resolve: {
            loadMyCtrl: ['$ocLazyLoad','$cookies','$state', function($ocLazyLoad,$cookies,$state) {
                return $ocLazyLoad.load('reports'); 
            }]
        }  
    })
 .state('usermanagement', {//State demonstrating Nested views
        url: "/usermanagement",
        cache: false , // add this line
        templateUrl: "app/templates/usermanagement.html",
        controller:'usermanagementCtrl',                         
        resolve: {
            loadMyCtrl: ['$ocLazyLoad','$cookies','$state', function($ocLazyLoad,$cookies,$state) {
                return $ocLazyLoad.load('usermanagement'); 
            }]
        }  
    })
     .state('changepassword', {//State demonstrating Nested views
        url: "/changepassword",
        cache: false , // add this line
        templateUrl: "app/templates/changepassword.html",
        controller:'changepasswordCtrl',                         
        resolve: {
            loadMyCtrl: ['$ocLazyLoad','$cookies','$state', function($ocLazyLoad,$cookies,$state) {
                return $ocLazyLoad.load('changepassword'); 
            }]
        }  
    })
    .state('transformertransfer', {//State demonstrating Nested views
        url: "/transformertransfer",
        cache: false , // add this line
        templateUrl: "app/templates/transformertransfer.html",
        controller:'transformertransferCtrl',                         
        resolve: {
            loadMyCtrl: ['$ocLazyLoad','$cookies','$state', function($ocLazyLoad,$cookies,$state) {
                return $ocLazyLoad.load('transformertransfer'); 
            }]
        }  
    })
    .state('ipsettransfer', {//State demonstrating Nested views
        url: "/ipsettransfer",
        cache: false , // add this line
        templateUrl: "app/templates/ipsettransfer.html",
        controller:'ipsettransferCtrl',                         
        resolve: {
            loadMyCtrl: ['$ocLazyLoad','$cookies','$state', function($ocLazyLoad,$cookies,$state) {
                return $ocLazyLoad.load('ipsettransfer'); 
            }]
        }  
    })
		
    //$urlRouterProvider.otherwise('/login');	
	//$urlRouterProvider.otherwise('/viewmap');	
	//$urlRouterProvider.otherwise('/home');
    
}])
.controller("navCtrl", function($scope,$rootScope,$state,store,$window,$timeout,remote,notify){
	console.log("navCtrl");
	
	//var urlParams = new URLSearchParams(window.location.search);
	
	//var urlParams = new URLSearchParams(location.search)
	//console.log(urlParams);
	
	/*if(urlParams.has('office_id') && (urlParams.get('role_name')).length > 0){
		
	}*/
	
	var domain = location.origin,
    service_domain = null, 
    baseURL = "/IPSurvey/",
    serviceURL = (service_domain || domain) + baseURL + "rest/services/";
 
	$rootScope.serviceURL = serviceURL;
	console.log("serviceURL",serviceURL);
	
	function getParameterByName(name, url) {
	    if (!url) url = window.location.href;
	    name = name.replace(/[\[\]]/g, '\\$&');
	    var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
	        results = regex.exec(url);
	    if (!results) return null;
	    if (!results[2]) return '';
	    return decodeURIComponent(results[2].replace(/\+/g, ' '));
	}
	
	var office_id = getParameterByName('office_id');
	var role_name = getParameterByName('role_name');
	//console.log(office_id);
	
	if(office_id === null){
		
		
		if(store.get('LOCATION_CODE')){
			
			var LOCATION_CODE = store.get('LOCATION_CODE');
			$rootScope.LOCATION_CODE = LOCATION_CODE;
			$rootScope.USER_INFO =  store.get('userinfo');
			
			if(!store.get('userinfo')){
				$rootScope.logout();
			}else{
				$rootScope.IsLoggedIn = true;
				//$state.go("viewmap");
				$state.go("home",{reload : true});
				
				/*$timeout(function(){
					$state.transitionTo('villagemaster', null, {'reload':true});
				},1000);
				$timeout(function(){
					$state.go("stationmaster");
				},1000);*/
			
				
			}
			
			//$state.go("viewmap");
			
		}else{
			//$rootScope.logout();
			$state.go("login",{reload : true});
		}
		
		
		
		
		
	}else{
		if(office_id.length > 0){
			
		remote.load("signin_location_object", function(response){
			
			store.remove('userinfo');
			store.remove('LOCATION_CODE');
			
			if(response.status1 === "success"){
			
					store.set('userinfo', response);
					
					var LOCATION_CODE = response.location_code;
					$rootScope.LOCATION_CODE = '';
					if(LOCATION_CODE.length == 1){$rootScope.LOCATION_CODE = 'COMPANY';}
					else if(LOCATION_CODE.length == 2){$rootScope.LOCATION_CODE = 'ZONE';}
					else if(LOCATION_CODE.length == 3){$rootScope.LOCATION_CODE = 'CIRCLE';}
					else if(LOCATION_CODE.length == 5){$rootScope.LOCATION_CODE = 'DIVISION';}
					else if(LOCATION_CODE.length == 7){$rootScope.LOCATION_CODE = 'SUBDIVISION'}
					else if(LOCATION_CODE.length == 9){$rootScope.LOCATION_CODE = 'OMSECTION';}
					else{$rootScope.LOCATION_CODE = null;}
					
					store.set('LOCATION_CODE', $rootScope.LOCATION_CODE);
					
					/*$state.go("home");*/
					//$state.go("viewmap");
					
						 var url = $window.location.href;
				         url = url.substring(0 , url.indexOf('?'));
				         $window.location.href = url;
				         
					
					//window.open('google.com');
			         
			         
				}else{
					
					window.open("https://google.com","_self");
						
				}
					console.log("signin_location_object",response);
				},{
					login_locationcode:office_id
				}, 'POST');
				
				$rootScope.USER_INFO =  store.get('userinfo');
				var LOCATION_CODE = store.get('userinfo');
				$rootScope.LOCATION_CODE = LOCATION_CODE;
				
				//$scope.IsLoggedIn = false;
				
				$rootScope.NAVIGATE_FROM = 'MESCOM'; 
			
			}else{
				$state.go("login",{reload : true});
			}
	}
	

	

	 
	 $rootScope.logout = function(){
		
		 store.remove('userinfo');
		 store.remove('LOCATION_CODE');
		 
		 $rootScope.IsLoggedIn = false;
		 
		 if($scope.NAVIGATE_FROM === 'MESCOM'){
			 
			 //window.open('','_self').close();
			// close();
			 
			 //window.open('','_parent','');
			// window.close();
			 
			 /*var conf=confirm("Are you sure, you want to close this tab?");
				if(conf==true){
					close();
				}*/
			 
		 }else if($scope.NAVIGATE_FROM === 'IISPL'){
			 
			 $state.go("login",{reload : true});
			 
			 $timeout(function(){
				 $window.location.reload(true); 
			 },500);
		 }
		 
		 
	 };
	 
	 $scope.checklocation = function(){
		
			var LOCATION_CODE = $scope.userinfo.location_code;
			
			if(LOCATION_CODE.length == 2){return 'ZONE';}
			else if(LOCATION_CODE.length == 3){return 'CIRCLE';}
			else if(LOCATION_CODE.length == 5){return 'DIVISION';}
			else if(LOCATION_CODE.length == 7){return 'SUBDIVISION'}
			else if(LOCATION_CODE.length == 9){return 'OMSECTION';}
			else{return null;}
			
			
	 };
	 
})


;
	
	
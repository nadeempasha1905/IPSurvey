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
        ]});
    
	$stateProvider
    .state('home', {//State demonstrating Nested views
        url: "/home",
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
        templateUrl: "app/templates/changepassword.html",
        controller:'changepasswordCtrl',                         
        resolve: {
            loadMyCtrl: ['$ocLazyLoad','$cookies','$state', function($ocLazyLoad,$cookies,$state) {
                return $ocLazyLoad.load('changepassword'); 
            }]
        }  
    })
		$urlRouterProvider.otherwise('/login');	
       
}])

.controller("navCtrl", function($scope,$rootScope,$state,store,$window){
	console.log("navCtrl");
	
	//$scope.IsLoggedIn = false;
	
	 var domain = location.origin,
	    service_domain = null, 
	    baseURL = "/IPSurvey/",
	    serviceURL = (service_domain || domain) + baseURL + "rest/services/";
	 
	 $rootScope.serviceURL = serviceURL;
	 console.log("serviceURL",serviceURL);
	 
	 $rootScope.logout = function(){
		
		 store.remove('userinfo');
		 $rootScope.IsLoggedIn = false;
		 
		 $state.go("login");
		 $window.location.reload(true); 
		 
	 };
})


;
	
	
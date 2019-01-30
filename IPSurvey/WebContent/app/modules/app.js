/**
 * 
 */

'use strict';
angular.module('MyApp',['MyApp.services','ngRoute','ngResource','ui.router','ngCookies','angular-storage','ngToast','oc.lazyLoad',
						'ui.bootstrap','google.places'])
.constant('CONTEXT_PATH','appname')
.constant('CACHEBUST_VERSION',"0.1")
.constant('SITE_LAST_UPDATED','28/01/2019')
.config(['$stateProvider', '$urlRouterProvider','$ocLazyLoadProvider','CACHEBUST_VERSION',
 function($stateProvider, $urlRouterProvider,$ocLazyLoadProvider,CACHEBUST_VERSION) {              

	$ocLazyLoadProvider
			.config({
				'debug' : false, // For debugging	// 'true/false'
				'events' : false, // For Event 'true/false'
				'modules' : [
						{
							name : 'home',
							files : [ 'components/home' + jsmin	+ '.js' + '?v='+ CACHEBUST_VERSION ]
						},
						{ // Set modules initially
							name : 'login',
							files : [ 'components/login' + jsmin + '.js' + '?v='+ CACHEBUST_VERSION ]
						} ]
			});   
	
		$stateProvider.state('home', {
			url : '/',
			templateUrl : 'components/home.html',
			controller : 'homeCtrl',
			resolve : {
				loadMyCtrl : [ '$ocLazyLoad', '$cookies', '$state',
							function($ocLazyLoad, $cookies, $state) {
							return $ocLazyLoad.load('home'); 
						} ]
			}
		})
;

		$urlRouterProvider.otherwise('/login');	
       
}])
	
	
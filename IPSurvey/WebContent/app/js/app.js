angular.module('apmc_app', ['ngRoute', 'ngTouch', 'ui.bootstrap','ui.grid','ui.grid.exporter',
						    'ui.grid.selection','ui.grid.expandable','ui.grid.pinning', 'ui.grid.edit', 'ui.grid.cellNav',
						    'ui.grid.autoResize', 'utility','signin','shop_details','shop_details_history','meter_reading_entry',
						    'bill_gen_recon','receipts_generation','view_bill_details','search_receipt_payments','post_receipts',
						    'download_upload_agent_data','bill_cancellation','receipt_cancellation','reports','manual_receipt_generation'])
.config(['$routeProvider', '$locationProvider', function($routeProvider, $locationProvider, $uibTooltipProvider) {
		$routeProvider
			/*.when( '/signin', {
				templateUrl: templates.signin,
				controller: 'signin_controller'
			})*/
		.when( '/signin', {
			templateUrl: templates.loginpage,
			controller: 'signin_controller'
		})
			/*.when( '/home', {
				templateUrl: templates.home,
				controller: 'home_controller'
			})*/			
			.when( '/shop_details', {
				templateUrl: templates.shop_details,
				controller: 'shop_detail_controller'
			})
			.when( '/shop_details_history', {
				templateUrl: templates.shop_details_history,
				controller: 'shop_detail_history_controller'
			})
			.when( '/meter_reading_entry', {
				templateUrl: templates.meter_reading_entry,
				controller: 'meter_reading_entry_controller'
			})
			.when( '/bill_gen_recon', {
				templateUrl: templates.bill_gen_recon,
				controller: 'bill_gen_recon_controller'
			})
			.when( '/receipts_generation', {
				templateUrl: templates.receipts_generation,
				controller: 'receipts_generation_controller'
			})
			.when( '/manual_receipt_generation', {
				templateUrl: templates.manual_receipt_generation,
				controller: 'manual_receipt_generation_controller'
			})
			.when( '/view_bills', {
				templateUrl: templates.view_bill_details,
				controller: 'view_bill_details_controller'
			})
			.when( '/search_receipt_payments', {
				templateUrl: templates.search_receipt_payments,
				controller: 'search_receipt_payments_controller'
			})
			.when( '/post_receipts', {
				templateUrl: templates.post_receipts,
				controller: 'post_receipts_controller'
			})
			.when( '/download_upload_agent_data', {
				templateUrl: templates.download_upload_agent_data,
				controller: 'download_upload_agent_data_controller'
			})
			.when( '/bill_cancellation', {
				templateUrl: templates.bill_cancellation,
				controller: 'bill_cancellation_controller'
			})
			.when( '/receipt_cancellation', {
				templateUrl: templates.receipt_cancellation,
				controller: 'receipt_cancellation_controller'
			})
			.when( '/reports', {
				templateUrl: templates.reports,
				controller: 'reports_controller'
			})
			.when( '/homepage', {
				templateUrl: templates.homepage,
				controller: 'homepage_controller'
			})
			.otherwise({
				//redirectTo: '/home'
				redirectTo: '/signin'
			});
}])
.controller('apmc_controller', function($rootScope, $scope, remote, cookie, config, $location){
	
	if(cookie.check('apmc_user')){
		$rootScope.user = JSON.parse(cookie.get('apmc_user'));
		remote.load("validateuserrefresh", function(response){
			$rootScope.storage = {
				permission_set: response.permission_set,
				picklist: {
					vehicle_models: response.dropdown_vehicle_models,
					bay_details: response.bay_details,
					technicians: response.technician_list
				},
				service_categories: response.service_categories,
				customer_service_request: response.customer_service_request,
				pre_check_list: response.pre_check_list,
				service_categories: response.service_categories,
				service_labour_schedules: response.service_labour_schedules
			}
		}, {}, 'POST', false, false, true);
	}

	$rootScope.signout = function(){
		if(cookie.check('apmc_user')){
			remote.load("signout", function(response){
				cookie.remove('apmc_user');
				delete $rootScope.user;
				$location.path('/signin');
			}, {}, 'POST');
		}else{
			delete $rootScope.user;
			$location.path('/signin');
		}
	}
	
	$rootScope.$on('$locationChangeSuccess',function(event, new_url, old_url){
		var path = $location.path().split('/');
		$rootScope.module = path[1];
		if(cookie.check('apmc_user')){
			if($rootScope.module == 'signin'){
				$location.path('/signin');
			}/*else if($rootScope.module !== 'home'){
				
				console.log(event, new_url, old_url);
				switch($rootScope.user.USER_ROLE_CODE){
					case 'admin':
						if($rootScope.module != 'print'){
							$location.path('/homepage');
						}
						break;
					default:
						$location.path('/signin');
						break;
				}
			}*/
		}else if($rootScope.module !== 'home'){
		    $location.path('/signin');
		}
	});
})
.run(function($rootScope){
	$rootScope.domain = domain;
	$rootScope.service_domain = service_domain;
	$rootScope.baseURL = baseURL;
	$rootScope.serviceURL = serviceURL;
	
	$rootScope.module = '';
	$rootScope.old_path = '';
	$rootScope.storage = {}; //to keep all needed values
});


/*********** Controllers need to move to separate files ***************/

angular.module('home', [])
.controller('home_controller', function($rootScope, $scope){
	$scope.carouselprev = function(){
		$('#carousel').carousel('prev');
	}
	$scope.carouselnext = function(){
		$('#carousel').carousel('next');
	}
});


/*angular.module('signin', [])
.controller('signin_controller', function($rootScope, $scope, remote, cookie, config, $location){
	$scope.signin = function(){
		remote.load("signin", function(response){
			$rootScope.user = response.user_details;
			$rootScope.storage = {
				permission_set: response.permission_set,
				picklist: {
					vehicle_models: response.dropdown_vehicle_models,
					bay_details: response.bay_details,
					technicians: response.technician_list
				},
				service_categories: response.service_categories,
				customer_service_request: response.customer_service_request,
				pre_check_list: response.pre_check_list,
				service_categories: response.service_categories,
				service_labour_schedules: response.service_labour_schedules
			}
			cookie.set('apmc_user',JSON.stringify(response.user_details),9);
			switch($rootScope.user.USER_ROLE_CODE){
				case 'admin':
					$location.path('/homepage');
					$location.path('/shop_details');
					break;
				case 'TECH':
					$location.path('/technical_adviser');
					break;
				case 'RECP':
					$location.path('/reception_adviser');
					break;
				case 'OWN':
					$location.path('/technical_adviser');
					break;
				default:
					$location.path('/signin');
					break;
			}
		},{
			user_id: $scope.userid,
			password: $scope.password
		}, 'POST');
	}
});*/


/*angular.module('shop_detail',[])
.controller('shop_detail_controller',function($rootScope, $scope, remote, cookie, config, $location, $timeout, $uibModal, $route){

console.log("shop_detail_controller initiated ");


var $shopdetail = $('.shop-detail');
var $tabsContent = $shopdetail.find('.tabs-content');
var $forms = $tabsContent.find('form');
var height = $(window).height() - 101;
$shopdetail.height(height + 'px');
$tabsContent.height(height + 'px');
$forms.height(height + 'px');
$scope.shop_detail = {
	shop_details : {},
	meter_details : {}
};

$scope.shop_details_model = {};

$scope.go_back = function(index){
	$('.shop-detail').find('ul').find('li').eq(index).trigger('click');
}

$scope.load_shop_details = function(){
	remote.load('verifyshop', function(response){
		console.log(response);
		if(response.new_customer_id){
			$scope.shop_detail.shop_details = {
				new_customer : true,
				shop_id: response.new_customer_id,
				rrno: $scope.shop_details.shop_info.SM_RR_NO
			}
		}else{
			$scope.service_adviser.customer_details = response.customer_details;
			$scope.service_adviser.customer_details.new_customer = false;
		}
		console.log($scope.shop_detail.shop_details)
		$('.shop-details-tab').trigger('click');
	},{
		SESSION_ID : $rootScope.user.LOCATION_CODE,
		location : $rootScope.user.USER_ID,
		rrno: $scope.shop_details.shop_info.SM_RR_NO
	});
}




});
*/


angular.module('service_adviser', [])
.controller('service_adviser_controller', function($rootScope, $scope, remote, cookie, config, $location, $timeout, $uibModal, $route){
	var $serviceAdviser = $('.service-adviser');
	var $tabsContent = $serviceAdviser.find('.tabs-content');
	var $forms = $tabsContent.find('form');
	var height = $(window).height() - 101;
	$serviceAdviser.height(height + 'px');
	$tabsContent.height(height + 'px');
	$forms.height(height + 'px');
	$scope.service_adviser = {
		customer_details : {},
		vehicle_details : {}
	};
	
	$scope.service_details_model = {};
	
	$scope.go_back = function(index){
		$('.service-adviser').find('ul').find('li').eq(index).trigger('click');
	}
	
	/******** Vehicle Selection *********/
	$scope.add_new_vehicle = function(){
		$scope.vehicle_details.unshift({"new_vehicle": true});
		$scope.service_adviser.vehicle_details = {"new_vehicle": true};
		$scope.expand_collapse = false;
		$scope.new_vehicle = true;
		$timeout(function(){
			$('.vehicle-list-view').find('label.list-group-item').eq(0).trigger('click');
		});
	}
	$scope.discard_new_vehicle = function(){
		$scope.vehicle_details.shift();
		$scope.service_adviser.vehicle_details = {};
		$scope.expand_collapse = true;
		$scope.new_vehicle = false;
	}
	$scope.expand_all = function(){
		$('.vehicle-list-view').find('label.list-group-item').show();
	}
	$scope.$watch('service_adviser.vehicle_details', function(val){
		if(!$.isEmptyObject(val)){
			$('.vehicle-list-view').find('label.list-group-item').each(function(key, value){
				if(!$(value).find('input').is(':checked')){
					$(value).hide();
				}
			});
			$('.vehicle_details').show();
		}else{
			$('.vehicle-list-view').find('label.list-group-item').show();
			$('.vehicle_details').hide();
		}
	});
	
	$scope.load_customer_details = function(){
		remote.load('verifycustomer', function(response){
			if(response.new_customer_id){
				$scope.service_adviser.customer_details = {
					new_customer : true,
					customer_id: response.new_customer_id,
					customer_phone_number: $scope.service_adviser.customer_details.customer_phone_number
				}
			}else{
				$scope.service_adviser.customer_details = response.customer_details;
				$scope.service_adviser.customer_details.new_customer = false;
			}
			$('.customer-details-tab').trigger('click');
		},{
			phone_number: $scope.service_adviser.customer_details.customer_phone_number
		});
	}
	
	$scope.update_customer_details = function(){
		$scope.service_adviser.customer_details.customer_user_id = $rootScope.user.USER_ID;
		remote.load('registercustomer',function(response){
			$scope.vehicle_details = response.vehicle_details;
			$('.vehicle_details').hide();
			$('.vehicle-details-tab').trigger('click');
		}, $scope.service_adviser.customer_details);
	}
	$scope.update_vehicle_details = function(){
		angular.extend($scope.service_adviser.vehicle_details, {
			vehicle_user_id: $rootScope.user.USER_ID,
			customer_id: $scope.service_adviser.customer_details.customer_id
		});
		if(!$scope.service_adviser.vehicle_details.new_vehicle){
			$scope.service_adviser.vehicle_details.new_vehicle = false;
		}
		$('.service-details-tab').trigger('click');
		$scope.service_category = "1";
	}
	$scope.udpate_service_information = function(){
		if(!$scope.service_category){
			alert('Select service category...');
			return false;
		}
		var accessories = [],
			$available = $('.accessories').find('ul').eq(1).find('li')
			$scratch = $('.accessories').find('ul').eq(2).find('li'),
			$dent = $('.accessories').find('ul').eq(3).find('li');
		for(var i=0; i < $scratch.length; i++){
			var key = $($scratch[i]).data('key'),
				object = { 
					key: key,
					available: $($available[i]).find('input').is(':checked'),
					scratch: $($scratch[i]).find('input').is(':checked'),
					dent: $($dent[i]).find('input').is(':checked'),
					remarks: ''
				}
			if(object.available){
				accessories.push(object);
			}
		}
		if($('.accessories').find('#pre_check_remarks').val()){
			accessories.push({
				key: 'REMARKS',
				available: true,
				scratch: false,
				dent: false,
				remarks: $('.accessories').find('#pre_check_remarks').val()
			});
		}
		
		var customer_request = [],
			$customer_service_request = $('.customer_service_request').find('ul').find('li');
		for(var i=0; i < $customer_service_request.length; i++){
			if($($customer_service_request[i]).find('input').is(':checked')){
				customer_request.push({ 
					key: $($customer_service_request[i]).data('key') + "",
					remarks: ''
				});
			}
		}
		if($('.customer_service_request').find('#customer_service_request_remarks').val()){
			customer_request.push({ 
				key: "REMARKS",
				remarks: $('.customer_service_request').find('#customer_service_request_remarks').val()
			});
		}
		
		var main_service = [],
			estimated_cost = 0,
			estimated_man_hours = 0,
			$main_service = $('.service_details').find('ul').find('li');
		for(var i=0; i < $main_service.length; i++){
			if($($main_service[i]).find('input').is(':checked')){
				main_service.push({ key : $($main_service[i]).data('key')});
				estimated_cost += parseFloat($($main_service[i]).data('cost'));
				estimated_man_hours += parseFloat($($main_service[i]).data('hours'));
			}
		}
		estimated_cost = estimated_cost.toFixed(2);
		estimated_man_hours = estimated_man_hours.toFixed(2);
		var popup_data = {
			bay_details: $rootScope.storage.picklist.bay_details,
			technicians: $rootScope.storage.picklist.technicians,
			service_details: {
				estimated_cost: estimated_cost,
				estimated_man_hours: estimated_man_hours,
				fuel_guage: $scope.service_adviser.vehicle_details.fuel || '',
				phone: $scope.service_adviser.customer_details.customer_phone_number,
				vehicle_details: $scope.service_adviser.vehicle_details,
				service_categories: $scope.service_category,
				pre_check_list: accessories,
				customer_service_request: customer_request,
				service_labour_schedules: main_service,
				bay_details: {}
			}
		};
		angular.extend(popup_data.service_details, $scope.service_adviser.vehicle_details);
		var serviceAdviserSubmit = $uibModal.open({
			animation: true,
			backdrop: 'static',
			keyboard: false,
		    templateUrl: templates.modal.service_adviser_submit,
		    controller: function($scope, $uibModalInstance, data, remote){
		    	$scope.header = "Bay details and Confirm";
		    	$scope.ok_text = "Submit";
		    	$scope.data = data;
		    	$scope.ok = function(){
		    		if(!data.service_details.bay_details.technician || !data.service_details.bay_details.IN || !data.service_details.bay_details.OUT){
		    			alert('All fields are mandatory...');
		    			return false;
		    		}
		    		var request = angular.copy(data);
		    		delete request.technicians;
		    		delete request.bay_details;
	    			remote.load("insertserviceinfo", function(response){
	    				$uibModalInstance.close(response.service_id);
	    			}, request ,'POST');
		    	}
		    	$scope.cancel = function(){
		    		$uibModalInstance.dismiss('cancel');
		    	}
		    },
		    resolve: {
		        data: function() {
		        	return popup_data;
		        },
		        remote: function(){
		        	return remote;
		        }
		    }
		});
		serviceAdviserSubmit.result.then(function (data) {
			if(data != 'cancel'){
				window.open( baseURL +'#/print/service/'+ data, '_blank');
				$route.reload();
			}
		});
		
	}
});

angular.module('technical_adviser', [])
.controller('technical_adviser_controller', function($rootScope, $scope, remote, cookie, config, $location, $timeout, $route){
	$scope.technical_adviser = {
		vehicle_details: {},
		labour_details: [],
		spare_details: [],
		user_id: $rootScope.user.USER_ID
	}
	$scope.spare_details = [];
	remote.load("servicevehicleslist", function(response){
		$scope.vehicle_details = response.service_vehicles_list;
	}, {
		user_id: $rootScope.user.USER_ID
	});
	
	$scope.$watch('technical_adviser.vehicle_details', function(new_val){
		if(!$.isEmptyObject(new_val)){
			$scope.submit = false;
			$scope.technical_adviser.labour_details = new_val.labour_details;
		}else{
			$scope.submit = true;
		}
	});
	
	$scope.onSelectSpare = function(event, item){
		var index = $('.spare-details').find('.typeahead').index($(event.target));
		$scope.technical_adviser.spare_details[index] = item;
	}
	
	$scope.add_new_spare = function(){
		$scope.technical_adviser.spare_details.push({
			count: $scope.technical_adviser.spare_details.length + 1
		});
	}
	
	$scope.delete_spare = function(index){
		$scope.technical_adviser.spare_details.splice(index, 1);
		$scope.spare_details.splice(index, 1);
	}
	
	$scope.approve_technical_adviser = function(){
		var request = angular.copy($scope.technical_adviser);
		for(var i=0; i<$scope.technical_adviser.spare_details.length; i++){
			if(!$scope.technical_adviser.spare_details[i].part_number){
				if(!$('.spare-details').find('.typeahead').eq(i).val()){
					alert("Part Number Cannot Be Blank !!!");
					return false;
				}
				request.spare_details[i].part_number = $('.spare-details').find('.typeahead').eq(i).val();
			}
		}
		remote.load('approveservicedetails',function(response){
			$route.reload();
		}, request);
	}
});

angular.module('reception_adviser', [])
.controller('reception_adviser_controller', function($rootScope, $scope, remote, cookie, config, $location, $timeout, $route, $window){
	$scope.reception_adviser = {
		service_details: {}
	}
	$scope.selected_service_id = '';
	remote.load("getbillinglist", function(response){
		$scope.approved = response.approved;
		$scope.inprogress = response.inprogress;
		$scope.dispatched = response.dispatched;
	}, {});
	
	$scope.$watch('reception_adviser.service_details', function(val){
		if(!$.isEmptyObject(val)){
			$scope.selected_service_id = val.service_id;
			$scope.generate_bill = true;
			$scope.reception_adviser.bill_details = {};
		}else{
			$scope.generate_bill = false;
		}
	});
	
	$scope.$watch('reception_adviser.bill_details', function(val){
		if(!$.isEmptyObject(val)){
			$scope.selected_bill_id = val.service_id;
			$scope.view_bill = true;
			$scope.reception_adviser.service_details = {};
		}else{
			$scope.view_bill = false;
		}
	});
	
	$scope.print_preview = function(event, view){
		var service_id = $(event.target).data('key');
		if(view === 'bill_view'){
			window.open( baseURL +'#/print/bill/'+ service_id, '_blank');
		}else{
			remote.load("generatebill", function(response){
				window.open( baseURL +'#/print/bill/'+ service_id, '_blank');
				$route.reload();
			}, {
				service_id: service_id,
				user_id: $rootScope.user.USER_ID
			});
		}
	}
});

angular.module('print', [])
.controller('print_controller', function($rootScope, $scope, remote, cookie, config, $location, $timeout, $route){
	var remote_action = '';
	var request = {
		print_id: $rootScope.print.id
	};
	switch($rootScope.print.type){
		case 'service':
			remote_action = 'printservice';
			break;
		case 'bill':
			remote_action = 'printbill';
			break;
		case 'report':
			remote_action = 'printreport';
			break;
	}
	if(!remote_action) return false;
	remote.load(remote_action, function(response){
		$scope.data = response;
		if(remote_action === 'printbill' && response.customer_details.email){
			$timeout(function(){
				remote.load('emailnotification', function(response){},{
					email_id: response.customer_details.email,
					//email_content: $(document).find('.print-wrapper').html(),
					service_id: $rootScope.print.id
				});
			},1000);
		}
	},request);
});
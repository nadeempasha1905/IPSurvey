var utility = angular.module('utility',[])
.directive('webHeader', function(){
	return {
	    restrict: 'E',
	    scope: {
	    	user: '=userInfo'
	    },
	    templateUrl: templates.header,
	    link: function(scope, element, attr){
	    }
	};
})
.directive('ngEnter', function () {
	console.log("e");
    return function (scope, element, attrs) {
        element.bind("keypress", function($event) {
            if($event.which === 13) {
                scope.$apply(function (){
                    scope.$eval(attrs.ngEnter);
                });
                $event.preventDefault();
            }
        });
    };
})
.service('remote', ['$http','$rootScope','notify',function($http, $rootScope, notify){
    this.load = function(service, success, data, method, error, hide_loader, do_not_notify){
		if(!hide_loader){
		   $('#loading').show();
		}
		/*if(service !== 'signin'){
			data['SESSION_ID'] = parseInt($rootScope.user.SESSION_ID);
		}*/
	    var config = {
		    method: method || 'POST',
		    url: $rootScope.serviceURL + service,
		    data: data
	    }
	    $http(config).then( function(response){
	        if(!hide_loader){
	            $('#loading').hide();
	        }
	        if(response.data.status === 'invalid_session'){
	        	alert("Invalid session... Signing out");
	        	$rootScope.signout();
	        }else if(response.data.status === 'error' || response.data.status === 'fail'){
		    	if(response.data.message){
		        	notify.error(response.data.message);
		    	}
		    	if(typeof error === 'function' ){
		     		error(response.data);
		    	}
		    }else if(response.data.status === 'success'){
		    	console.log(response.data);
		    	if(response.data.message && !do_not_notify){
		     		notify.success(response.data.message);
		    	}
		    	success(response.data);
		   	}
	    }, function(response){
		    if(!hide_loader){
		    	$('#loading').hide();
		    }
		    if(response.message){
		    	notify.error(response.message);
		    }else if(response.data.status === 'fail' || response.data.status === 'failure'){
		    	if(response.data.message){
		     		notify.error(response.data.message);
		    	}
		    }
	    });
 	}
}])
.service('cookie', [function(){
	this.set = function(cname, cvalue, exhours) {
	    var d = new Date();
	    d.setTime(d.getTime() + (exhours*60*60*1000));
	    var expires = "expires="+d.toUTCString();
	    document.cookie = cname + "=" + cvalue + "; " + expires;
	}
	this.get = function(cname) {
	    var name = cname + "=";
	    var ca = document.cookie.split(';');
	    for(var i=0; i<ca.length; i++) {
	        var c = ca[i];
	        while (c.charAt(0)==' ') c = c.substring(1);
	        if (c.indexOf(name) == 0) return c.substring(name.length, c.length);
	    }
	    return "";
	}
	this.check = function(cname) {
	    var isCookieAvailable = (this.get(cname) === "")? false : true;
	    return isCookieAvailable;
	}
	this.remove = function(cname) {
	    var d = new Date();
	    d.setTime(d.getTime() - 1);
	    document.cookie = cname + "=; " + "expires=" + d.toUTCString();
	}
}])
.service('config', function($rootScope, $route){
	this.role = '';
	this.set = function(){
		if(this.role === 'SA'){
			$route.routes['/service_adviser'] = {
				templateUrl: templates.service_adviser,
				controller: 'service_adviser_controller',
				reloadOnSearch: false
		    };
		}
	}
})
/*.directive('webTabs', function($timeout) {
	return {
	    restrict: 'E',
	    link: function(scope, element, attrs) {
	    	
	    	$(element).find('ul.tabs li').on('click',function(e){
	    		var $targetHeader = $(e.currentTarget),
	    			$headers = $(element).find('ul.tabs li'),
	    			targetIndex = $headers.index($targetHeader),
	    			oldTargetIndex = $headers.index($(element).find('ul.tabs li.active')),
	    			$contents = $(element).find('.tabs-content > form');

	    		$headers.removeClass('active');
	    		$targetHeader.addClass('active');
	    		$contents.removeClass('tab-active tab-right tab-left');
	    		$contents.eq(oldTargetIndex).addClass('transition');
	    		$timeout(function(){
	    			$contents.eq(oldTargetIndex).removeClass('transition');
	    		},1000);
	    		$contents.eq(targetIndex).addClass('tab-active');
	    		$(element).find('.tabs-content > form:lt('+targetIndex+')').addClass('tab-left');
	    		$(element).find('.tabs-content > form:gt('+targetIndex+')').addClass('tab-right');
    		});
	    }
	};
})*/

.directive('webDatepicker', function () {
    return {
        restrict: 'A',
        require: 'ngModel',
         link: function (scope, element, attrs, ngModelCtrl) {
        	element.prop('readOnly',true);
        	var datepicker_options = {
                dateFormat: 'dd/mm/yy',
                changeMonth: true,
                changeYear: true,
                onSelect: function (date) {
                    scope.$apply(function () {
                        ngModelCtrl.$setViewValue(date);
                    });
                }
        	};
        	if(attrs.closeIcon != 'false'){
        		$('<span class="fa fa-times relative-pos clear-date"></span>').insertAfter(element);
        		element.next('span.clear-date').on('click',function(){
        			element.val('');
        			scope.$apply(function () {
                        ngModelCtrl.$setViewValue('');
                    });
        		});
        	}
        	
        	if(attrs.icon != 'false'){
        		angular.extend(datepicker_options,{
        			showOn: "both",
                    buttonImage: "./app/img/calendarIcon.png",
                    buttonImageOnly: true,
                    buttonText: "Select date"
        		});
        		
        		console.log("datepicker_options",datepicker_options);
        	}else{
        		element.next('span.clear-date').addClass('without-calendar-icon');
        	}
        	if(attrs.minDate){
        		angular.extend(datepicker_options,{
        			minDate: attrs.minDate
        		});
        	}
        	if(attrs.maxDate){
        		angular.extend(datepicker_options,{
        			maxDate: attrs.maxDate
        		});
        	}
            element.datepicker(datepicker_options);
            element.removeAttr('web-datepicker');
        }
    };
})

.directive('webTabs', function($timeout) {
	return {
	    restrict: 'E',
	    link: function(scope, element, attrs) {
	    	
	    	$(element).attr('tabindex','0');
	    	
	    	$(element).hover(function() {
	    	    this.focus();
	    	}, function() {
	    	    this.blur();
	    	}).on('keyup',function(e){
	    		if($(e.target).is('input')) return;
	    		var key = e.which;
	    		if(key === 37 || key === 39){
	    			var $headers = $(element).find('ul.tabs li'),
	    				totalHeaders = $headers.length,
	    				activeIndex = $headers.index($(element).find('ul.tabs li.active')),
	    				triggerIndex = (activeIndex - 1) % totalHeaders;
	    			if(key == 39){
		    			triggerIndex = (activeIndex + 1) % totalHeaders;
					}
		    		$headers.eq(triggerIndex).trigger('click');
	    		}
	    	}).find('ul.tabs li').on('click',function(e){
	    		var $targetHeader = $(e.currentTarget),
	    			$headers = $(element).find('ul.tabs li'),
	    			targetIndex = $headers.index($targetHeader),
	    			oldTargetIndex = $headers.index($(element).find('ul.tabs li.active')),
	    			$contents = $(element).find('.tabs-content > div');

	    		$headers.removeClass('active');
	    		$targetHeader.addClass('active');
	    		$contents.removeClass('tab-active tab-right tab-left');
	    		$contents.eq(oldTargetIndex).addClass('transition');
	    		$timeout(function(){
	    			$contents.eq(oldTargetIndex).removeClass('transition');
	    		},1000);
	    		$contents.eq(targetIndex).addClass('tab-active');
	    		$(element).find('.tabs-content > div:lt('+targetIndex+')').addClass('tab-left');
	    		$(element).find('.tabs-content > div:gt('+targetIndex+')').addClass('tab-right');
    		});
	    }
	};
})
.directive('labelPlaceholder', function(){
	return {
	    restrict: 'C',
	    link: function(scope, element, attrs){
	    	
	    	var labelIt = function($target){
	    		var $label = $target.find('label'),
		    	$input = $target.find('input'),
		    	$textArea = $target.find('textarea');
	    		$select = $target.find('select');
		    	
	    		$label.removeClass('move-top place-holder');
    			if(($input.length && $input.val()) || ($textArea.length && $textArea.val()) || ($select.length && $select.val())){
    				$label.addClass('move-top');
    			}else{
    				$label.addClass('place-holder');
    			}
	    	}
	    	var $input = $(element);
    		$input.on('focus', function(e){
    			$(e.currentTarget).closest('.label-placeholder-group').find('label').addClass('move-top');
    		})
    		$input.on('blur', function(e){
    			labelIt($(e.currentTarget).closest('.label-placeholder-group'));
    		});
    		scope.$watch(attrs.ngModel, function (new_value) {
    			labelIt($('#'+attrs.id).closest('.label-placeholder-group'));
            });
	    }
	};
})
.directive('typeahead', ['$log', 'remote', function($log, remote) {
    return {
        require: 'ngModel',
        restrict: 'C',
        scope: {
            settings: '=?', // optional: Autocomplete settings or configurations as per jQuery UI documentation
            onselect: "=?", // optional: A callback scope after selecting a string
            //object_name: "@objectName", // mandatory: Salesforce object name. For example: Account, User, Opportunity, etc.
            position: '@?', // optional: Standard positions provided in the jQuery UI docs
            //sffields: '@', // optional: required when provider-location is server. It is a list of fields required to fetch from the database i.e., object related fields
            sffields_info: '=?sffieldsInfo', // mandatory: We need to provide which field is label and value and according to it, the typeahead dropdown will be displayed
            required: '@?fieldRequired', // optional: validation, 'true' or 'false'
            provider_location: '@?providerLocation', // optional: location of the provider function, possible values are "local" and "server". Default: 'server'
            local_provider: '=?localProvider', // name of the scope function that returns an array of object containing label, value and id
            value: '=ngModel',
            remote_action: '@remoteAction' // remote action to hit
        },
        link: function($scope, $element, $attrs, ngModel) {
            /*
            * Validating the arguments passed from the HTML attribute
            */
            var settings = $scope.settings || {},
                object_name = $scope.object_name,
                position = $scope.position || 'left top,left bottom',
                sffields = $scope.sffields || 'Name',
                sffields_info = $scope.sffields_info || {
                    value: 'Id',
                    label: 'Name'
                },
                required = Boolean($scope.required) || false,
                provider_location = $scope.provider_location || 'server',
                local_provider = $scope.local_provider || '',
                value = $scope.value || {},
                remote_action = $scope.remote_action || 'search';

            position = position.split(',');

//            if (provider_location === 'server' && (!object_name || !sffields)) {
//                $log.error('Typeahead Directive: data-object-name and data-sffields attrib is required when data-provider-location is "server"');
//                return false;
//            }
//
//            if (provider_location === 'local' && local_provider === '') {
//                $log.error('Typeahead Directive: data-local-provider attrib is required when data-provider-location is "local"');
//                return false;
//            }

            /*
            * validation process and set the value if it is provided.
            */
            ngModel.$setValidity('typeahead', !(required === true && $.isEmptyObject(value)));
            ngModel.$render = function(){
                if(ngModel.$modelValue){
                    $element.val(ngModel.$modelValue[sffields_info.label] ? ngModel.$modelValue[sffields_info.label] : '');
                }
            };
            /*
             * Extending the default settings with the providing settings
             */
            default_settings = {
                minLength: 3,
                autoFocus: false,
                delay: 500,
                position: {
                    my: position[0],
                    at: position[1]
                },
                source: function(request, response) {
                    var search_text = $element.val() || '',
                      	result = [];
                    var param = {
                        part_no: search_text
                    };
                    remote.load(remote_action, function(result, event){
                        response($.map(result.data, function(item) {
                            item.value = item[sffields_info.label];
                            item.label = item[sffields_info.label];
                            item.id = item[sffields_info.value];
                            return item;
                        }));
                    }, param);
                },
                select: function(event, selected_item) {
                    ngModel.$setValidity('typeahead', true);
                    ngModel.$setViewValue('');
                    ngModel.$render();
                    ngModel.$setViewValue(selected_item.item);
                    ngModel.$render();

                    if(angular.isFunction($scope.onselect)) {
                        $scope.onselect(event, selected_item.item);
                    }

                    $scope.$apply();
                }
            };
            settings = angular.extend(default_settings, settings);
            $element.autocomplete(settings);
            
            /*
            * Update the model validity when the user edit the text input.
            */
            $element.on('input', function(){
                ngModel.$setValidity('typeahead', false);
            });
        }
    };
}])
.service('notify', [function(){
	this.clear = function(){
		$('#notification').removeClass('alert-dismissible alert-info alert-success alert-danger alert-warning');
	}
	this.success = function(message){
		this.clear();
		$('#notification').addClass('alert-success');
		var content = '<strong>Success!</strong> '+ message;
		$('#notification').html(content);
		$('#notification').fadeIn( 800 ).delay( 2000 ).fadeOut( 800 );
	}
	this.error = function(message){
		this.clear();
		$('#notification').addClass('alert-dismissible alert-danger');
		var content = '<button type="button" class="close"><span aria-hidden="true">&times;</span></button>';
			content += '<strong>Error!</strong> '+ message;
		$('#notification').html(content);
		$('#notification').fadeIn( 800 ,function(){
			$('#notification').find('.close').off('click').on('click',function(){
				$('#notification').fadeOut( 800 );
			});
		});
	}
	this.info = function(message){
		this.clear();
		$('#notification').addClass('alert-info');
		var content = '<strong>Info!</strong> '+ message;
		$('#notification').html(content);
		$('#notification').fadeIn( 800 ).delay( 2000 ).fadeOut( 800 );
	}
	this.warn = function(message){
		this.clear();
		$('#notification').addClass('alert-warning');
		var content = '<strong>Warning!</strong> '+ message;
		$('#notification').html(content);
		$('#notification').fadeIn( 800 ).delay( 2000 ).fadeOut( 800 );
	}
}])
.filter('inwords', function() {
    return function(input) {
    	var a = ['','one ','two ','three ','four ', 'five ','six ','seven ','eight ','nine ','ten ','eleven ','twelve ','thirteen ','fourteen ','fifteen ','sixteen ','seventeen ','eighteen ','nineteen '];
    	var b = ['', '', 'twenty','thirty','forty','fifty', 'sixty','seventy','eighty','ninety'];
    	var convertToWords = function(num) {
    	    if ((num = num.toFixed(0)).length > 9) return num;
    	    var n = ('000000000' + num).substr(-9).match(/^(\d{2})(\d{2})(\d{2})(\d{1})(\d{2})$/);
    	    var str = '';
    	    str += (n[1] != 0) ? (a[Number(n[1])] || b[n[1][0]] + ' ' + a[n[1][1]]) + 'crore ' : '';
    	    str += (n[2] != 0) ? (a[Number(n[2])] || b[n[2][0]] + ' ' + a[n[2][1]]) + 'lakh ' : '';
    	    str += (n[3] != 0) ? (a[Number(n[3])] || b[n[3][0]] + ' ' + a[n[3][1]]) + 'thousand ' : '';
    	    str += (n[4] != 0) ? (a[Number(n[4])] || b[n[4][0]] + ' ' + a[n[4][1]]) + 'hundred ' : '';
    	    str += (n[5] != 0) ? ((str != '') ? 'and ' : '') + (a[Number(n[5])] || b[n[5][0]] + ' ' + a[n[5][1]]) : '';
    	    return str.toUpperCase();
    	}
    	return (!!input) ? convertToWords(input) : '';
    }
})

.directive('numbersOnly', function () {
    return {
        require: 'ngModel',
        link: function (scope, element, attr, ngModelCtrl) {
            function fromUser(text) {
                if (text) {
                    var transformedInput = text.replace(/[^0-9]/g, '');

                    if (transformedInput !== text) {
                        ngModelCtrl.$setViewValue(transformedInput);
                        ngModelCtrl.$render();
                    }
                    return transformedInput;
                }
                return undefined;
            }            
            ngModelCtrl.$parsers.push(fromUser);
        }
    };
})
.directive('alphabetsOnly', function () {
    return {
        require: 'ngModel',
        link: function (scope, element, attr, ngModelCtrl) {
            function fromUser(text) {
                if (text) {
                    var transformedInput = text.replace(/[^a-zA-Z]/g, '');

                    if (transformedInput !== text) {
                        ngModelCtrl.$setViewValue(transformedInput);
                        ngModelCtrl.$render();
                    }
                    return transformedInput;
                }
                return undefined;
            }            
            ngModelCtrl.$parsers.push(fromUser);
        }
    };
})
.directive('alphabetsnumbersOnly', function () {
    return {
        require: 'ngModel',
        link: function (scope, element, attr, ngModelCtrl) {
            function fromUser(text) {
                if (text) {
                    var transformedInput = text.replace(/[^A-Za-z0-9]/g, '');

                    if (transformedInput !== text) {
                        ngModelCtrl.$setViewValue(transformedInput);
                        ngModelCtrl.$render();
                    }
                    return transformedInput;
                }
                return undefined;
            }            
            ngModelCtrl.$parsers.push(fromUser);
        }
    };
})


/*.directive('webDatepicker', function () {
    return {
        restrict: 'A',
        require: 'ngModel',
         link: function (scope, element, attrs, ngModelCtrl) {
        	element.prop('readOnly',true);
        	var datepicker_options = {
                dateFormat: 'dd/mm/yy',
                changeMonth: true,
                changeYear: true,
                onSelect: function (date) {
                    scope.$apply(function () {
                        ngModelCtrl.$setViewValue(date);
                    });
                }
        	};
        	if(attrs.closeIcon != 'false'){
        		$('<span class="fa fa-times relative-pos clear-date"></span>').insertAfter(element);
        		element.next('span.clear-date').on('click',function(){
        			element.val('');
        			scope.$apply(function () {
                        ngModelCtrl.$setViewValue('');
                    });
        		});
        	}
        	if(attrs.icon != 'false'){
        		angular.extend(datepicker_options,{
        			showOn: "both",
                    buttonImage: "./app/images/calendarIcon.png",
                    buttonImageOnly: true,
                    buttonText: "Select date"
        		});
        	}else{
        		element.next('span.clear-date').addClass('without-calendar-icon');
        	}
        	if(attrs.minDate){
        		angular.extend(datepicker_options,{
        			minDate: attrs.minDate
        		});
        	}
        	if(attrs.maxDate){
        		angular.extend(datepicker_options,{
        			maxDate: attrs.maxDate
        		});
        	}
            element.datepicker(datepicker_options);
            element.removeAttr('web-datepicker');
        }
    };
})*/

;
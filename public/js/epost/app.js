'use strict';

/* App Module */
var myModule = angular.module('epost', ['debugbox', 'jsontree', 'ngSanitize', 'loadingOnAJAX', 'DatePicker', 'productFilters', 'productServices', 'uploadjs', 'ngCookies'])

myModule.config(function ($routeProvider) {
    $routeProvider
	.when('/dashboard', { templateUrl: 'partials/epost/dashboard.html', controller: BalanceCtrl })	
	.when('/stocks', { templateUrl: 'partials/epost/stocks.html', controller: StockCtrl })
	.when('/detail/:symbol', { templateUrl: 'partials/epost/detail.html', controller: DetailCtrl })
        .otherwise({ redirectTo: '/dashboard' });
})
myModule.config(['$httpProvider', function ($httpProvider) {
    //$httpProvider.interceptors.push('httpInterceptor');
}])

/*myModule.factory('httpInterceptor', function ($q, $cookieStore, $rootScope, $interpolate) {
    return {
        request: function (config) {
            var plattformConfig = $rootScope.getPlattFormConfig()
            var exp = $interpolate(config.url);
            var uri = exp({config: plattformConfig})
            config.url = uri
            return config;
        }
    };
});*/

myModule.run(function ($rootScope) {
    String.prototype.contains = function(it) { return this.indexOf(it) != -1; };

    var upHitOnce = false;
    $(document).keydown(function(event) {
	   if (event.ctrlKey) {
	    	//alert("CTRL down");
		$rootScope.$broadcast('CtrlPressed');
        	$rootScope.$apply();
	    }
    });
     $(document).keyup(function(event) {
	   if (event.keyCode == 17) {
	    	//alert("CTRL up");
		$rootScope.$broadcast('CtrlReleased');
        	$rootScope.$apply();
	    }
    });
});

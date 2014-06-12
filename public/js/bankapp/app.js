'use strict';

/* App Module */
var myModule = angular.module('bankapp', 
                ['TestData', 'MobileTable', 'angular-loading-bar', 'ngRoute', 
                'ui.bootstrap', 'debugbox', 'jsontree', 'ngSanitize', 'productFilters', 
                'productServices', 'ngCookies', 'directive.g+signin', 'angularjs-crypto', 
                'ngStorage'])

myModule.config(function ($routeProvider) {
    $routeProvider
	.when('/dashboard', { templateUrl: 'partials/bankapp/dashboard.html', controller: BalanceCtrl })
	.when('/stocks', { templateUrl: 'partials/bankapp/stocks.html', controller: StockCtrl })
    .when('/stocks-mobile', { templateUrl: 'partials/bankapp/stocks-mobile.html', controller: StockCtrl })
    .when('/settings', { templateUrl: 'partials/bankapp/settings.html', controller: SettingsCtrl })
    .when('/balance-mobile', { templateUrl: 'partials/bankapp/balance-mobile.html', controller: BalanceCtrl })
	.when('/detail/:symbol', { templateUrl: 'partials/bankapp/detail.html', controller: DetailCtrl })
    .otherwise({ redirectTo: '/dashboard' });
})

myModule.run(function($rootScope, cfCryptoHttpInterceptor, $localStorage) {
    $rootScope.$storage = $localStorage.$default({
        config:{key:'insert your key here is stored local'}
    })
    cfCryptoHttpInterceptor.base64KeyFunc = function() {
        return $rootScope.$storage.config.key;
    }
});

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

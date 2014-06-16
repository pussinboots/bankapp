'use strict';

/* App Module */
var myModule = angular.module('bankapp', 
                ['TestData', 'MobileTable', 'angular-loading-bar', 'ngRoute', 
                'ui.bootstrap', 'debugbox', 'jsontree', 'ngSanitize', 'productFilters', 
                'productServices', 'ngCookies', 'directive.g+signin', 'angularjs-crypto', 
                'ngStorage'])

myModule.config(function ($routeProvider) {
    $routeProvider
	.when('/dashboard', {       templateUrl: 'partials/bankapp/dashboard.html', 
                                resolve:{profile:waitForLogin}, 
                                controller: BalanceCtrl })
    .when('/stocks', {          templateUrl: 'partials/bankapp/stocks.html',
                                resolve:{profile:waitForLogin},
                                controller: StockCtrl })
    .when('/stocks-mobile', {   templateUrl: 'partials/bankapp/stocks-mobile.html', 
                                resolve:{profile:waitForLogin},
                                controller: StockCtrl })
    .when('/settings', {        templateUrl: 'partials/bankapp/settings.html', 
                                controller: SettingsCtrl })
    .when('/testlogin/:googleid', {        templateUrl: 'partials/bankapp/settings.html', 
                                controller: GoogleCtrl })
    .when('/balance-mobile', {  templateUrl: 'partials/bankapp/balance-mobile.html',
                                resolve:{profile:waitForLogin},
                                controller: BalanceCtrl })
	.when('/detail/:symbol', {  templateUrl: 'partials/bankapp/detail.html', 
                                controller: DetailCtrl })
    .otherwise({ redirectTo: '/dashboard' });
})

myModule.run(function($rootScope, cfCryptoHttpInterceptor, $localStorage) {
    $rootScope.$storage = $localStorage.$default({
        config:{key:''}
    })
    cfCryptoHttpInterceptor.base64KeyFunc = function() {
        return $rootScope.$storage.config.key;
    }
});

myModule.run(function ($q, $rootScope) {
    var defer = $q.defer()
    $rootScope.profile = defer.promise;
    $rootScope.setProfile = function(profile) {
        defer.resolve(profile)    
    }
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
function waitForLogin($rootScope){
 //   console.log('wait for loging ' + $rootScope.profile)
    return $rootScope.profile;
}

'use strict';

/* Header CDontroller */

function HeaderController($scope, $location) {
    $scope.isActive = function (viewLocation) {
        return viewLocation === $location.path();
    };
}

function SettingsCtrl() {
}

/* Controllers */

function GoogleTestCtrl($rootScope, $scope, $http, Users, Accounts) {
    $rootScope.email = Accounts.auth({googleId: 'test googleId'}, function(response) {
        $http.defaults.headers.common["X-AUTH-TOKEN"] = response.googleId.toString();
        $rootScope.setProfile(response);
    });
};

function GoogleCtrl($rootScope, $scope, $http, Users) {
    $scope.$on('event:google-plus-signin-success', function (event, authResult) {
        gapi.auth.setToken(authResult); // Den zurückgegebenen Token speichern.
        $rootScope.email = Users.auth({token: authResult.access_token}, function(response) {
            $http.defaults.headers.common["X-AUTH-TOKEN"] = response.googleId.toString();
            $rootScope.setProfile(response);
        });
        console.log('success full login');
    })
};

function BalanceCtrl($rootScope, $scope, Balances) {
    initTable($scope, 10, 'date', 'desc')
    console.log("profile " + $rootScope.email)
    $scope.setItems = function (rootScope, scope) {
        loadBalances($rootScope, scope, Balances)
    };
    $scope.setItems($rootScope, $scope)
}

function StockCtrl($rootScope, $scope, Stocks) {
    initTable($scope, 10, 'date', 'desc')
    $scope.sortDirection="asc"

    $scope.setItems = function (rootScope, scope) {
        loadStocks($rootScope, scope, Stocks)
    };
    $scope.setItems($rootScope, $scope)
}

function DetailCtrl($scope, $routeParams, YqlQuotes) {
    var fetchQuote = (function () {
        var promiseQuote = YqlQuotes.getQuotes($routeParams.symbol);
        promiseQuote.then(function (data) {
            $scope.quote = data.quote;
        });
    });
    fetchQuote()
}

function loadStocks(rootScope, scope, Stocks) {
    scope.stocks = Stocks.get({sort: scope.sortColumn, direction: scope.sortDirection, items: scope.items, page: scope.currentPage, name_enc: scope.filter.fieldName, date: scope.filter.fieldDate}, function (response) {
        scope.totalItems = response.count;
    });
}

function loadBalances(rootScope, scope, Balances) {
    scope.balances = Balances.get({sort: scope.sortColumn, direction: scope.sortDirection, items: scope.items, page: scope.currentPage, name_enc: scope.filter.fieldName, date: scope.filter.fieldDate}, function (response) {
        scope.totalItems = response.count;
    });
}
//TODO move to data table directive
function setSort(rootScope, scope, sort) {
    var oldSort = angular.copy(scope.sortColumn);
    if (scope.multiselect) scope.sortColumn = scope.sortColumn + " " + sort; else scope.sortColumn = sort;
    if (oldSort == sort) scope.sortDirection = scope.sortDirection == 'desc' ? 'asc' : 'desc'; else scope.sortDirection = 'desc';
    scope.setItems(rootScope, scope)
};

'use strict';

/* Header CDontroller */

function HeaderController($scope, $location) {
    $scope.isActive = function (viewLocation) {
        return viewLocation === $location.path();
    };
}

/* Controllers */

function initTable(scope, items, sortColumn, sortDirection) {
    scope.filter = {}
    scope.items = items
    scope.sortColumn = sortColumn;
    scope.sortDirection == sortDirection
    scope.dateFormat = 'yyyy-MM-dd HH:mm:ss Z'
}

function TableCtrl($rootScope, $scope, Balances) {
    $scope.totalItems = 64;
    $scope.currentPage = 1;

    $scope.filter = {}

    $scope.setSort = function (sort) {
        setSort($scope, sort)
    };

    $scope.pageChanged = function() {
        console.log('Page changed to: ' + $scope.currentPage);
        $scope.setItems($scope)
    };

    $rootScope.$on('CtrlPressed', function () {
        $scope.multiselect = true;
    });
    $rootScope.$on('CtrlReleased', function () {
        $scope.multiselect = false;
    });

    $scope.filterBy = function (value, field) {
        $scope.filter[field] = value
        $scope.setItems($scope)
    };
    $scope.resetFilter = function () {
        $scope.filter = {}
        $scope.setItems($scope)
    }
    $scope.sortClass = function (column) {
        return $scope.sortColumn.contains(column) && 'sort-' + $scope.sortDirection;
    };
}

function BalanceCtrl($rootScope, $scope, Balances) {
    initTable($scope, 10, 'date', 'desc')

    $scope.setItems = function (scope) {
        loadBalances(scope, Balances)
    };
    $scope.setItems($scope)
}

function StockCtrl($rootScope, $scope, Stocks) {
    initTable($scope, 10, 'date', 'desc')

    $scope.setItems = function (scope) {
        loadStocks(scope, Stocks)
    };
    $scope.setItems($scope)
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

function loadStocks(scope, Stocks) {
    scope.stocks = Stocks.get({sort: scope.sortColumn, direction: scope.sortDirection, items: scope.items, page: scope.currentPage, name: scope.filter.fieldName, date: scope.filter.fieldDate})
}

function loadBalances(scope, Balances) {
    scope.balances = Balances.get({sort: scope.sortColumn, direction: scope.sortDirection, items: scope.items, page: scope.currentPage, name: scope.filter.fieldName})
}

function setSort(scope, sort) {
    var oldSort = angular.copy(scope.sortColumn);
    if (scope.multiselect) scope.sortColumn = scope.sortColumn + " " + sort; else scope.sortColumn = sort;
    if (oldSort == sort) scope.sortDirection = scope.sortDirection == 'desc' ? 'asc' : 'desc'; else scope.sortDirection = 'desc';
    scope.setItems(scope)
};

'use strict';

/* Controllers */

function initTable(scope, items, sortColumn, sortDirection) {
    scope.items = items
    scope.sortColumn = sortColumn;
    scope.sortDirection == sortDirection
    scope.dateFormat = 'yyyy-MM-dd HH:mm:ss Z'
}

function BalanceCtrl($rootScope, $scope, Balances) {
    $scope.filter = {}
    initTable($scope, 10, 'date', 'desc')
    $rootScope.$on('CtrlPressed', function() {
    	$scope.multiselect = true;
    });
    $rootScope.$on('CtrlReleased', function() {
    	$scope.multiselect = false;
    });
    $scope.setSort = function(sort) {
	var oldSort = angular.copy($scope.sortColumn);
	if ($scope.multiselect) $scope.sortColumn = $scope.sortColumn + " " + sort;  else $scope.sortColumn = sort;
        if (oldSort == sort) $scope.sortDirection = $scope.sortDirection == 'desc' ? 'asc' : 'desc'; else $scope.sortDirection = 'desc';
	$scope.balances = Balances.get({sort:$scope.sortColumn, direction:$scope.sortDirection, items:$scope.items, name:$scope.filter.fieldName})
    };
    $scope.setItems = function() {
	$scope.balances = Balances.get({sort:$scope.sortColumn, direction:$scope.sortDirection, items:$scope.items, name:$scope.filter.fieldName})
    };
    $scope.filterByName = function(name) {
	$scope.filter.fieldName = name
	$scope.balances = Balances.get({sort:$scope.sortColumn, direction:$scope.sortDirection, items:$scope.items, name:$scope.filter.fieldName})
    };
    $scope.resetFilter = function() {
        $scope.filter = {}
	$scope.balances = Balances.get({sort:$scope.sortColumn, direction:$scope.sortDirection, items:$scope.items, name:$scope.filter.fieldName})   
    }
    $scope.sortClass = function(column){
      return $scope.sortColumn.contains(column) && 'sort-' + $scope.sortDirection;
    };
    $scope.balances = Balances.get({sort:$scope.sortColumn, direction:$scope.sortDirection, items:$scope.items, name:$scope.filter.fieldName})
}

function StockCtrl($rootScope, $scope, Stocks) {
    initTable($scope, 10, 'date', 'desc')
    $scope.filter = {}
    $rootScope.$on('CtrlPressed', function() {
    	$scope.multiselect = true;
    });
    $rootScope.$on('CtrlReleased', function() {
    	$scope.multiselect = false;
    });
    $scope.setSort = function(sort) {
	var oldSort = angular.copy($scope.sortColumn);
	if ($scope.multiselect) $scope.sortColumn = $scope.sortColumn + " " + sort;  else $scope.sortColumn = sort;
        if (oldSort == sort) $scope.sortDirection = $scope.sortDirection == 'desc' ? 'asc' : 'desc'; else $scope.sortDirection = 'desc';
	$scope.setItems()
    };
    $scope.setItems = function() {
	loadStocks($scope, Stocks)
    };
    $scope.fetchPage = function(page) {
	$scope.page=page
        $scope.setItems()
    };    
    $scope.filterByName = function(name) {
 	$scope.page=1
	$scope.filter.fieldName = name
	$scope.setItems()
    };
    $scope.filterByDate = function(date) {
	$scope.page=1
	$scope.filter.fieldDate = date
	$scope.setItems()
    };
    $scope.resetFilter = function() {
        $scope.filter = {}
	$scope.setItems()   
    }
    $scope.sortClass = function(column){
      return $scope.sortColumn.contains(column) && 'sort-' + $scope.sortDirection;
    };
    $scope.setItems()
}

function DetailCtrl($scope, $routeParams, YqlQuotes) {
     var fetchQuote = (function() {
      var promiseQuote = YqlQuotes.getQuotes($routeParams.symbol);
      promiseQuote.then(function(data){
        $scope.quote = data.quote;
      });
    });
    fetchQuote()
}

function loadStocks(scope, Stocks) {
    scope.stocks = Stocks.get({sort:scope.sortColumn, direction:scope.sortDirection, items:scope.items, page:scope.page, name:scope.filter.fieldName, date:scope.filter.fieldDate})
}

angular.module('MobileTable', []).directive('mobileTable', function () {
    return {
        restrict: 'E',
        templateUrl: 'partials/bankapp/directive/mobile-table.html',
        replace: false,
        transclude: true
    };
})

angular.module('TestData', []).directive('testData', function () {
    return {
        restrict: 'E',
        templateUrl: 'partials/bankapp/directive/data-table.html',
        replace: false,
        transclude: true
    };
})


function TableCtrl($rootScope, $scope) {
    $scope.totalItems = 20;
    $scope.currentPage = 1;

    $scope.filter = {}

    $scope.setSort = function (sort) {
        setSort($rootScope, $scope, sort)
    };

    $scope.pageChanged = function () {
        console.log('Page changed to: ' + $scope.currentPage);
        $scope.setItems($rootScope, $scope)
    };

    $rootScope.$on('CtrlPressed', function () {
        $scope.multiselect = true;
    });
    $rootScope.$on('CtrlReleased', function () {
        $scope.multiselect = false;
    });

    $scope.filterBy = function (value, field) {
        $scope.filter[field] = value
        $scope.setItems($rootScope, $scope)
    };
    $scope.resetFilter = function () {
        $scope.filter = {}
        $scope.setItems($rootScope, $scope)
    }
    $scope.sortClass = function (column) {
        return $scope.sortColumn.contains(column) && 'sort-' + $scope.sortDirection;
    };
}

function initTable(scope, items, sortColumn, sortDirection) {
    scope.filter = {}
    scope.items = items
    scope.sortColumn = sortColumn;
    scope.sortDirection == sortDirection
    scope.dateFormat = 'yyyy-MM-dd HH:mm:ss Z'
}

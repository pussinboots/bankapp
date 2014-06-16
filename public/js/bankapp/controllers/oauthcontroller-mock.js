function GoogleCtrl($rootScope, $scope, $routeParams, $http, Users, Accounts) {
    $rootScope.email = Accounts.auth({googleId: $routeParams.googleid}, function(response) {
        $http.defaults.headers.common["X-AUTH-TOKEN"] = response.googleId.toString();
        $rootScope.setProfile(response);
    });
};
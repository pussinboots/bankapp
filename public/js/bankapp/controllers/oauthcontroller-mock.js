function GoogleCtrl($rootScope, $scope, $http, Users, Accounts) {
    $rootScope.email = Accounts.auth({googleId: 'test googleId'}, function(response) {
        $http.defaults.headers.common["X-AUTH-TOKEN"] = response.googleId.toString();
        $rootScope.setProfile(response);
    });
};
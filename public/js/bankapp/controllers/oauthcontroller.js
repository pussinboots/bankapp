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

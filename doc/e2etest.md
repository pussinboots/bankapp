##e2e Test with karma (Todo documentation how it works)

I had the plan to implement complete test szenarios from unit, inetgration and acceptance tests.

Test definition for me

* unit test test code without external dependencies
* integration test test multile code blocks without external dependencies
* acceptance test test functions of the complete system without external dependencies but mocked in proper way  

This project is implemented as a web application that target browsers and mobile devices (start with android).
For the frontend the javascript programming language is choosed because it plays well with HTML5 features and has a wide 
community and framework support. The backend is impleented with Scala specialy play 2 framework because less coding than java and good development suport of play like class reloading, test support, and so on.

* frontend angularjs 1.2.x version
* backend play 2.2.3 scala

####Frontend

As i mentioned it is implemented with javascript and the wonderful web framework [anularjs](https://angularjs.org/). The angularjs framework make it very simple to build html 5 featured web applications with little lines of codes and a wide support of plugings called modules in the anularjs world.

######Unit Test

TODO document and imlement with karma the common way

######Integration Tests

TODO maybe not needed because can be achieved with acceptance test for the whole system frontend plus backend

####Backend

######Unit Test

Implements standard play 2 unit tests see test/unit folder
TODO document 

######Integration Tests

Implements standard play 2 integration tests see test/integration folder
TODO document 

####Acceptance Tests

##Setup

####karma runner as npm test script

TODO document that is the magic where to document the way to write karma e2e test that run against the play 2 backend  

file bliss ignore child exit code so that if a command returns other exit code than zero the command pipe will not break it execution
```
#!/bin/sh
"$@"
exit 0
```

The bliss script is used to prevent the break of the command pipe. So ignore the exit code of the play stop command which can be 1 if the RUNNING_PID file was not found. So that means stop a running play server or do nothing and than start a new one with the e2e application configuration in the background and the following wget command wait until the server is up and running by check if the http://localhost:9000 call was succesful. Than run the karma e2e tests and if all tests passed than stop the running play server. The play stop command is only performed if all tests passed so a test run could leave you a running server. To prevent that play stop is the first command here. This 
extraction from [package.json](https://github.com/pussinboots/bankapp/blob/master/package.json) file
```
{
...
  "scripts": {
    "test": "./bliss play stop && play -Dconfig.file=conf/application-e2e.conf start & wget -O/dev/null --retry-connrefused --tries=40 http://localhost:9000 && ./node_modules/.bin/karma start karma.conf-e2e.js --single-run && play stop"
  }
...
}

```
###### Little hint for local development

The npm test run should also work with a local development but it need some times to start and stop the play server. To get the maximum speed for development you should start the play server with the run command, because play start has no reload feature and also static files are cached. 
```
play -Dconfig.file=conf/application-e2e.conf run 
```
And to start the karma e2e test run.
```
./node_modules/.bin/karma start karma.conf-e2e.js
```
So with this commands you can still change the backend and frontend code (means scala and javascript, html, css, ...) and the e2e test will run against your latest changes. That means you can really programm in a TDD style.

```
play ~test
```
To get the full TDD power you should also start your play tests in continuous mode so that they are executed for every code change you did. But there are still some drawbacks the karma runner doesn't detect your changes of the scala code and the recompilation in the play run terminal could take too long for the karma runner so you have to started more times before it will run all your e2e tests or you increase the waiting times of karma (browserNoActivityTimeout)

file application-e2e.conf
```
application.global=test.E2ETestGlobal
```

file E2ETestGlobal.scala
```scala
package test

import play.api._
import model.DB
import global.Global

object E2ETestGlobal extends GlobalSettings
{
    override def onStart(app: Application) {
      DB.db withSession {
      	SetupTestDatabase.insertTestData()
      }
      Logger.info("Application for e2e test has started")
    }
    override def onStop(app: Application) {
      Logger.info("Application of e2e test shutdown...")
    }
}
```

file SetupTestDatabase.scala
```
package test

import model.{UserAccount, DateUtil, Balance, Stock, DB}
import scala.slick.session.Database
import Database.threadLocalSession

object SetupTestDatabase {

  var now = DateUtil.nowDateTimeOpt()
  var yesterday = Option(DateUtil.daysBeforDateTime(1))
  var userAccount: UserAccount = null
  import DB.dal._
  import DB.dal.profile.simple._
  def insertTestData(googleId: String = "test googleId") = {
    DB.dal.recreate
    userAccount = UserAccounts.insert(googleId, "testemail")
    //TimeStamp could be rounded by database so fetch the rounded value back
    now = Balances.insert(Balance(None, "Total", "100.01", now, Some(userAccount.googleId))).date
    yesterday = Balances.insert(Balance(None, "Girokonto", "281.21", yesterday, Some(userAccount.googleId))).date
    Balances.insert(Balance(None, "Total", "100.01", yesterday, Some(UserAccounts.insert("other googleId", "testemail2").googleId)))

    Symbols.insertIfNotExists("Aktie 1", "CBK.DE")
    Symbols.insertIfNotExists("Aktie 2", "EOAN.DE")

    Stocks.insert(Stock(None, "Aktie 1", "11.01", now, Some(userAccount.googleId)))
    Stocks.insert(Stock(None, "Aktie 2", "28.21", yesterday, Some(userAccount.googleId)))
    Stocks.insert(Stock(None, "Aktie 1", "10.01", yesterday, Some(UserAccounts.insert("an other googleId", "testemail3").googleId)))
    (now, yesterday, userAccount)
  }
}
```

file products-e2e.html
```html
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html lang="de" ng-app="bankapp">
<head>
    <meta charset="utf-8">
    <meta name="fragment" content="#"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>mini bankapp</title>


    <!-- bower:css -->
    <link rel="stylesheet" href="components/bootstrap/dist/css/bootstrap.css" />
    <!-- endbower -->

    <!-- bower:js -->
    <script src="components/jquery/dist/jquery.js"></script>
    <script src="components/angular/angular.js"></script>
    <script src="components/angular-cookies/angular-cookies.js"></script>
    <script src="components/angular-resource/angular-resource.js"></script>
    <script src="components/angular-route/angular-route.js"></script>
    <script src="components/angular-sanitize/angular-sanitize.js"></script>
    <script src="components/angularjs-crypto/public/js/lib/angularjs-crypto.js"></script>
    <script src="components/bootstrap/dist/js/bootstrap.js"></script>
    <script src="components/angular-bootstrap/ui-bootstrap-tpls.js"></script>
    <script src="components/ngstorage/ngStorage.js"></script>
    <!-- endbower -->

    <link rel="stylesheet" href="css/bankapp/app.css">
    <link rel="stylesheet" href="css/bankapp/directive/datatable.css">
    <link rel="stylesheet" href="js/loading-bar.css">
    
    <script src="js/bankapp/app.js" type='text/javascript'></script>
    <script src="js/bankapp/directives/datepicker.js" type='text/javascript'></script>
    <script src="js/bankapp/directives/data-table.js" type='text/javascript'></script>
    <script src="js/bankapp/directives/jsontree.js" type='text/javascript'></script>
    <script src="js/bankapp/controllers.js" type='text/javascript'></script>
    <script src="js/bankapp/controllers/oauthcontroller-mock.js" type='text/javascript'></script>
    <script src="js/bankapp/directives.js" type='text/javascript'></script>
    <script src="js/bankapp/filters.js" type='text/javascript'></script>
    <script src="js/bankapp/services.js" type='text/javascript'></script>

    <script src="js/loading-bar.js"></script>
    
    <!-- cryptojs aes files -->
    <script src="//crypto-js.googlecode.com/svn/tags/3.1.2/build/rollups/aes.js"></script>
    <script src="//crypto-js.googlecode.com/svn/tags/3.1.2/build/components/mode-ecb.js"></script>

    <!--google plus auth-->
    <script src="js/google-plus-signin.js"></script>

    <!-- Optional theme -->
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">

</head>
<body>
<nav class="navbar navbar-default" role="navigation" ng-controller="GoogleCtrl">
<div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
    </div>
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1" ng-controller="HeaderController">
        <ul class="nav navbar-nav">
            <li ng-class="{ active: isActive('/dashboard')}"><a href="#/dashboard">Uebersicht</a></li>
            <li ng-class="{ active: isActive('/stocks')}"><a href="#/stocks">Aktien</a></li>
            <li ng-class="{ active: isActive('/balance-mobile')}"><a href="#/balance-mobile">Uebersicht Mobil</a></li>
            <li ng-class="{ active: isActive('/stocks-mobile')}"><a href="#/stocks-mobile">Aktien Mobil</a></li>
            <li ng-class="{ active: isActive('/settings')}"><a href="#/settings">Settings</a></li>
            <li ng-class="{ active: isActive('/google')}">
                <div style="position: relative"><google-plus-signin clientid="17916981101.apps.googleusercontent.com"
                                    language="en"></google-plus-signin>
                    email:{{email.eMail}}
                </div>
            </li>
        </ul>
    </div>
    <!-- /.navbar-collapse -->
</div>
<!-- /.container-fluid -->
</nav>
<div class="container-fluid">
    <div style="width: 200" ng-hide="$root.email">
      <div class="progress progress-striped active">
         <div class="bar" style="width: 0%;"></div>
         Please login with google plus
      </div>
    </div>
    <div ng-view></div>
</div>
</body>
</html>
```

file oauthcontroller-mock.js
```
function GoogleCtrl($rootScope, $scope, $http, Users, Accounts) {
    $rootScope.email = Accounts.auth({googleId: 'test googleId'}, function(response) {
        $http.defaults.headers.common["X-AUTH-TOKEN"] = response.googleId.toString();
        $rootScope.setProfile(response);
    });
};
```

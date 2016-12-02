var appHost = "https://localhost:8443/app";
var idatHost = "https://127.0.0.1:8444/idat";
var psnsHost = "https://127.0.0.1:8445/psns";
var vdatHost = "https://127.0.0.1:8446/vdat";


var app = angular.module('app', ['ngRoute', 'TXLSecurityServices', 'TXLVdatServices', 'TXLPsnsServices', 'TXLCaseList']);

app.config(function ($routeProvider, $httpProvider) {
    $routeProvider.when('/', {
        templateUrl: 'angular/public/login.html',
        controller: 'loginController'
    }).when('#/login', {
        templateUrl: 'angular/public/login.html',
        controller: 'loginController'
    }).when('/dashboard', {
        templateUrl: 'angular/private/dashboard.html',
        controller: 'dashboardController'
    }).when('/createcase', {
        templateUrl: 'angular/private/create-case.html',
        controller: 'caseController'
    }).when('/case', {
        templateUrl: 'angular/private/case.html',
        controller: 'caseController',
        resolve: {
            prepFunction: fetchCases
        }
    }).otherwise('/');

    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
    $httpProvider.defaults.withCredentials = true;
});

function fetchCases($rootScope, TXLIdatService, TXLPsnService, TXLCaseService, TXLCaseListService) {
    TXLCaseListService.clearList();

    var promise = TXLIdatService.eidForUser($rootScope.username).success(function (eid) {
        console.log("EID for user '" + $rootScope.username + "' :" + eid.value);
        $rootScope.eid = eid.value;

        TXLPsnService.resolvePsn(eid).success(function (casePsns) {
            for (var i = 0, len = casePsns.length; i < len; i++) {
                console.log("Fetching case with psn '" + casePsns[i].value + "'");
                TXLCaseService.fetchCaseWithPSN(casePsns[i]).success(function (txlCase) {
                    console.log("Fetched case: " + txlCase);
                    console.log("Resolve client profile psn...");

                    TXLPsnService.resolvePsn(txlCase.clientProfilePsnToBeResolved).success(function (resolvedPsns) {
                        console.log("Resolved client profile pseudonym...");
                        TXLIdatService.fetchClientProfileWithPsn(resolvedPsns[0]).success(function (clientProfile) {
                            console.log("Fetched client profile: " + clientProfile);

                            var caseToDisplay = {};
                            caseToDisplay.id = txlCase.id;
                            caseToDisplay.type = txlCase.type.type;
                            caseToDisplay.clientProfile = {id : clientProfile.id, name: clientProfile.name};
                            TXLCaseListService.addCaseToCache(caseToDisplay);

                        }); // end fetch client profile
                    }); // end resolve client profile psn
                });//end fetch case with psn
            } //end of case psn loop
        }); // end of eid resolve
    }); //end of eid for user

    return promise;
}

app.controller('navigationController', function ($rootScope, $scope, $http, $location, $route, TXLAuthenticationService, TXLCaseService) {
    $scope.tab = function (route) {
        return $route.current && route === $route.current.controller;
    };
});

app.controller('loginController',
    function ($rootScope, $scope, $http, $location, TXLAuthenticationService, TXLCaseService) {
        $scope.credentials = {};

        $scope.login = function () {
            $rootScope.username = $scope.credentials.username;

            TXLAuthenticationService.authenticate(appHost, $scope.credentials)
                .success(function () {

                    // Set the APP authentication token for any future requests (partials, etc)
                    $http.defaults.headers.common['X-Trixl-Token'] = TXLAuthenticationService.getTokenForHost(appHost);

                    TXLAuthenticationService.authenticate(psnsHost, $scope.credentials)
                        .success(function () {
                            TXLAuthenticationService.authenticate(idatHost, $scope.credentials)
                                .success(function () {
                                    TXLAuthenticationService.authenticate(vdatHost, $scope.credentials)
                                        .success(function () {
                                            $rootScope.authenticated = true;
                                            $location.path("/dashboard");
                                        })
                                });
                        });
                });
        };
    }
);


app.controller('dashboardController',
    function ($rootScope, $scope, $http, $location, TXLAuthenticationService, TXLCaseService) {

    }
);

app.controller('sidebarController',
    function ($rootScope, $scope, $http, $location, TXLAuthenticationService, TXLCaseService) {
        $scope.viewCases = function () {
            $location.path("/case");
        };

        $scope.createCase = function () {
            $location.path("/createcase");
        };

    }
);

app.controller('caseController',
    function ($rootScope, $scope, $http, $location, TXLAuthenticationService, TXLCaseService, TXLIdatService, TXLPsnService, TXLCaseListService, TXLClientProfile) {
        var clientProfiles = TXLClientProfile.query(function() {
            $scope.clientProfiles = clientProfiles;
        }); //query() returns all the clientProfiles


        $scope.cases = TXLCaseListService.getCases();
        $scope.newCase = {};
        $scope.newClientProfile = {};
        $scope.existingClientProfilePsn;

        $scope.createCase = function () {
            TXLIdatService.eidForUser($rootScope.username).success(function (eid) {
                TXLCaseService.createCase($scope.newCase, $scope.newClientProfile, eid).success(function () {
                    $location.path("/case");
                });
            });
        };

    }
);

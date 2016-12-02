var constants = angular.module('TXLCONSTANTS', []);

constants.constant("TXL_CONFIG", {
    "APP_HOST": "https://localhost:8443/app",
    "IDAT_HOST": "https://127.0.0.1:8444/idat",
    "PSNS_HOST": "https://127.0.0.1:8445/psns",
    "VDAT_HOST": "https://127.0.0.1:8446/vdat",
    "AUTH_HEADER": "X-Trixl-Token"
});

constants.constant("TXL_TYPE", {
    "CASE_TYPE": "CASE_TYPE",
    "CASE_TEST_ENVIRONMENT": "CASE_TEST_ENVIRONMENT"
});

var app = angular.module('app', ['ngRoute', 'TXLSecurityServices',
    'TXLVdatServices', 'TXLPsnsServices', 'TXLCaseList', 'TXLDashboard', 'TXLCONSTANTS']);
    
app.config(function ($routeProvider, $httpProvider) {
    $routeProvider.when('/', {
        templateUrl: 'angular/public/partials/login.html',
        controller: 'LoginController'
    }).when('#/login', {
        templateUrl: 'angular/public/partials/login.html',
        controller: 'LoginController'
    }).when('/dashboard', {
        templateUrl: 'angular/private/partials/dashboard.html',
        controller: 'DashboardController',
        resolve: {
            tokenFunction: setAppToken,
            prepFunction: fetchAssignedCases
        }
    }).when('/createcase', {
        templateUrl: 'angular/private/partials/create-case.html',
        controller: 'CreateCaseController',
        resolve: {
            tokenFunction: setAppToken
        }
    }).when('/case', {
        templateUrl: 'angular/private/partials/case.html',
        controller: 'CaseTableController',
        resolve: {
            tokenFunction: setAppToken,
            prepFunction: prepareViewCases
        }
    }).otherwise('/');

    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
    $httpProvider.defaults.withCredentials = true;

});

function setAppToken($http, $rootScope, TXLAuthenticationService, TXL_CONFIG) {
    // Set the APP authentication token for any future requests (partials, etc)
    $http.defaults.headers.common[TXL_CONFIG.AUTH_HEADER] = TXLAuthenticationService.getTokenForHost(TXL_CONFIG.APP_HOST);
    $rootScope.authenticated = true;
}

function prepareViewCases($rootScope, TXLClient) {
    var clients = TXLClient.query(function () {
        $rootScope.clients = clients;
    });
}

app.controller('NavigationController', function ($scope, $route) {
    $scope.tab = function (route) {
        return $route.current && route === $route.current.controller;
    };
});

app.controller('LoginController',
    function ($rootScope, $scope, $location, $q, TXLAuthenticationService, TXL_CONFIG) {
        $scope.credentials = {};

        /**
         * Authenticate with all hosts
         */
        $scope.login = function () {
            $rootScope.username = $scope.credentials.username;

            var allAuthenticationPromises = $q.all([
                TXLAuthenticationService.authenticate(TXL_CONFIG.APP_HOST, $scope.credentials),
                TXLAuthenticationService.authenticate(TXL_CONFIG.IDAT_HOST, $scope.credentials),
                TXLAuthenticationService.authenticate(TXL_CONFIG.PSNS_HOST, $scope.credentials),
                TXLAuthenticationService.authenticate(TXL_CONFIG.VDAT_HOST, $scope.credentials)
            ]);

            allAuthenticationPromises.then(function (users) {
                $rootScope.user = users[0];
                $rootScope.authenticated = true;
                $location.path("/dashboard");
            });
        };
    }
);

app.controller('DashboardController', function ($rootScope, $scope, $http, $location, TXLCaseService,
                                                TXLAuthenticationService, TXLCaseService, TXLIdatService,
                                                TXLPsnService,
                                                TXLDashboardService) {

    if ($rootScope.user.type === 'PENTESTER') {
        $scope.isPentester = true;
        $scope.assignedCases = TXLDashboardService.getAssignedCases();
    } else {
        $scope.isPentester = false;
    }
});


function fetchAssignedCases($rootScope, TXLAuthenticationService, TXLCaseService, TXLDashboardService) {
    TXLDashboardService.clearList();

    console.log("Fetch assigned cases for user: " + $rootScope.user);
    return TXLCaseService.getAssignedCases($rootScope.user)
        .then(function (assignedCases) {
            for (var i = 0, len = assignedCases.length; i < len; i++) {
                TXLDashboardService.addCaseToCache(assignedCases[i]);
                console.log("Add assigned cases to dashboard: " + assignedCases[i]);
            }
        });
}

app.controller('SidebarController', function ($rootScope, $scope, $http,
                                              $location, TXLAuthenticationService, TXLCaseService) {
    $scope.viewCases = function () {
        $location.path("/case");
    };

    $scope.createCase = function () {
        $location.path("/createcase");
    };

});

app.controller('MenuFooterController',
    function ($rootScope, $scope, $http, $location, TXLAuthenticationService, TXLCaseService) {
        $scope.logout = function () {
            $location.path("/login");
            $rootScope.authenticated = false;
        };
    });

app.controller('CaseTableController', function ($rootScope, $scope, $http, $location,
                                           TXLAuthenticationService, TXLCaseService, TXLIdatService,
                                           TXLPsnService, TXLCaseListService) {

    TXLCaseListService.clearList();
    $scope.cases = TXLCaseListService.getCases();
    $scope.eid;

    $scope.fetchCases = function (eid) {
        console.log("fetchCases");

        TXLCaseListService.clearList();
        $scope.cases = TXLCaseListService.getCases();

        $rootScope.eid = eid.value;

        TXLPsnService.resolvePsn(eid).then(function (casePsns) {
            for (var i = 0, len = casePsns.length; i < len; i++) {
                console.log("Fetching case with psn '" + casePsns[i].value + "'");
                TXLCaseService.fetchCaseWithPSN(casePsns[i]).then(function (txlCase) {
                    console.log("Fetched case: " + txlCase);
                    console.log("Resolve client profile psn...");

                    TXLPsnService.resolvePsn(txlCase.clientProfilePsnToBeResolved).then(function (resolvedPsns) {
                        console.log("Resolved client profile pseudonym...");
                        TXLIdatService.fetchClientProfileWithPsn(resolvedPsns[0]).then(function (clientProfile) {
                            console.log("Fetched client profile: " + clientProfile);

                            var caseToDisplay = {};
                            caseToDisplay.id = txlCase.id;
                            caseToDisplay.type = txlCase.type.label;
                            caseToDisplay.clientProfile = {id: clientProfile.id, name: clientProfile.name};
                            TXLCaseListService.addCaseToCache(caseToDisplay);
                            $scope.cases = TXLCaseListService.getCases();

                        }); // end fetch client profile
                    }); // end resolve client profile psn
                });//end fetch case with psn
            } //end of case psn loop
        }); // end of eid resolve
    }

});

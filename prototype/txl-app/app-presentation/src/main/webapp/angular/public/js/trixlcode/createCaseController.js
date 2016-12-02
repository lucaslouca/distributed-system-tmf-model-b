/**
 * Created by lucas on 01/11/15.
 */

app.controller('CreateCaseController', function ($rootScope, $scope, $http, $location,
                                                 TXLAuthenticationService, TXLCaseService, TXLIdatService,
                                                 TXLPsnService, TXLClientProfile, TXLClient, TXL_CONFIG, TXL_TYPE) {

    $scope.newCase = {};
    $scope.newClientProfile = {};
    $scope.clientEID = {};
    $scope.existingClientProfilePsn;


    // Fetch existing Client Profiles
    var clientProfiles = TXLClientProfile.query(function () {
        $scope.clientProfiles = clientProfiles;
    });

    // Fetch all the clients
    var clients = TXLClient.query(function () {
        $scope.clients = clients;
    });

    // Fetch the Case Types
    $http({
        url: TXL_CONFIG.VDAT_HOST + "/rest/type/option",
        method: "GET",
        params: {"type": [TXL_TYPE.CASE_TYPE, TXL_TYPE.CASE_TEST_ENVIRONMENT]}
    }).then(function (result) {
        $scope.caseTypesOptions = result.data[TXL_TYPE.CASE_TYPE];
        $scope.testEnvironmentOptions = result.data[TXL_TYPE.CASE_TEST_ENVIRONMENT];
    });

    $scope.createCase = function () {
        if ($scope.existingClientProfilePsn !== undefined) {
            $scope.newClientProfile.psn = $scope.existingClientProfilePsn;
        }

        TXLCaseService.createCase($scope.newCase, $scope.newClientProfile, $scope.clientEID)
            .then(function () {
                $location.path("/case");
            });
    };
});
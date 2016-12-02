/**
 * Created by lucas on 20/08/15.
 */
var idatHost = "https://127.0.0.1:8444/idat";
var TXLIdatServices = angular.module('TXLIdatServices', ['TXLSecurityServices', 'ngResource']);

TXLIdatServices.factory('TXLClientProfile', function($resource, TXLAuthenticationService) {
    return $resource(idatHost+'/rest/clientProfile/:id', {}, {headers: { 'X-Trixl-Token': TXLAuthenticationService.getTokenForHost(idatHost) }});
});

TXLIdatServices.service('TXLIdatService', function ($http, $cookies, TXLAuthenticationService, TXLPsnService) {
    'use strict';

    var eidForUser =  function (username) {
        var callbacksToExecute = {};
        var callbackSetters = {
            success: function (callback) {
                callbacksToExecute.success = callback;
                return this;
            },
            error: function (callback) {
                callbacksToExecute.error = callback;
                return this;
            }
        };

        var idatToken = TXLAuthenticationService.getTokenForHost(idatHost);

        // Resolve
        $http({
            url: idatHost + "/rest/client/eidForUser",
            method: "POST",
            headers: {
                "X-Trixl-Token": idatToken
            },
            data: {"username": username}
        }).success(function (psn) {
            callbacksToExecute.success(psn);
        });

        return callbackSetters;
    };

    var fetchClientProfileWithPsn = function (psn) {
        var callbacksToExecute = {};
        var callbackSetters = {
            success: function (callback) {
                callbacksToExecute.success = callback;
                return this;
            },
            error: function (callback) {
                callbacksToExecute.error = callback;
                return this;
            }
        };

        var idatToken = TXLAuthenticationService.getTokenForHost(idatHost);

        if (idatToken !== "") {
            $http({
                url: idatHost + "/rest/clientProfile/clientProfileWithPsn",
                method: "POST",
                headers: {
                    'X-Trixl-Token': idatToken
                },
                data: psn
            }).success(function (clientProfile) {
                callbacksToExecute.success(clientProfile);
            });
        }
        return callbackSetters;
    };

    var createClientProfileWithPsn = function (psn) {
        var callbacksToExecute = {};
        var callbackSetters = {
            success: function (callback) {
                callbacksToExecute.success = callback;
                return this;
            },
            error: function (callback) {
                callbacksToExecute.error = callback;
                return this;
            }
        };

        var idatToken = TXLAuthenticationService.getTokenForHost(idatHost);

        // Create client profile
        $http({
            url: idatHost + "/rest/clientProfile/add",
            method: "POST",
            headers: {
                "X-Trixl-Token": idatToken
            },
            data: psn
        }).success(function (clientProfile) {
            console.log("Created client profile with name=" + clientProfile.name);
            callbacksToExecute.success(clientProfile);
        }).error(function () {
            console.log("Could not create client profile");
            callbacksToExecute.error();
        });


        return callbackSetters;
    }

    var updateClientProfile = function (clientProfile) {
        var callbacksToExecute = {};
        var callbackSetters = {
            success: function (callback) {
                callbacksToExecute.success = callback;
                return this;
            },
            error: function (callback) {
                callbacksToExecute.error = callback;
                return this;
            }
        };
        var idatToken = TXLAuthenticationService.getTokenForHost(idatHost);


        // Update client profile
        $http({
            url: idatHost + "/rest/clientProfile/update",
            method: "POST",
            headers: {
                "X-Trixl-Token": idatToken
            },
            data: clientProfile
        }).success(function (updatedClientProfile) {
            console.log("Updated client profile with name=" + updatedClientProfile.name);
            callbacksToExecute.success(updatedClientProfile);
        }).error(function () {
            console.log("Could not update client profile");
            callbacksToExecute.error();
        });


        return callbackSetters;
    }


    return {eidForUser : eidForUser, fetchClientProfileWithPsn : fetchClientProfileWithPsn, createClientProfileWithPsn: createClientProfileWithPsn, updateClientProfile: updateClientProfile};
});

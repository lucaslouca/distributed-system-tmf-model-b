/**
 * Created by lucas on 20/08/15.
 */
var psnsHost = "https://127.0.0.1:8445/psns";

var TXLPsnsServices = angular.module('TXLPsnsServices', ['TXLSecurityServices' ]);

TXLPsnsServices.service('TXLPsnService', function ($http, $cookies, TXLAuthenticationService) {
    'use strict';

    var resolvePsn = function(psn) {
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

        // Resolve client profile pseudonym
        var psnsToken = TXLAuthenticationService.getTokenForHost(psnsHost);
        $http({
            url: psnsHost + "/rest/resolve",
            method: "POST",
            headers: {
                "X-Trixl-Token": psnsToken
            },
            data: psn
        }).success(function (resolvedPsns) {
            callbacksToExecute.success(resolvedPsns);
        });

        return callbackSetters;
    };


    var createPsnTuple = function() {
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

        var psnsToken = TXLAuthenticationService.getTokenForHost(psnsHost);
        if (psnsToken !== "") {
            $http({
                url: psnsHost + "/rest/createTuple",
                method: "POST",
                headers: {
                    "X-Trixl-Token": psnsToken
                }
            }).success(function (psnTuple) {
                console.log("Created tuple: (" + psnTuple.psn1.value + "," + psnTuple.psn2.value + ")");
                callbacksToExecute.success(psnTuple);
            }).error(function() {
                console.log("Could not create tuple");
                callbacksToExecute.error();
            });
        }

        return callbackSetters;
    };


    var createCounterpartPsn = function(psn) {
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

        var psnsToken = TXLAuthenticationService.getTokenForHost(psnsHost);
        if (psnsToken !== "") {
            $http({
                url: psnsHost + "/rest/addCounterpart",
                method: "POST",
                headers: {
                    "X-Trixl-Token": psnsToken
                },
                data: psn
            }).success(function (counterpartPsn) {
                callbacksToExecute.success(counterpartPsn);
            }).error(function() {
                callbacksToExecute.error();
            });
        }

        return callbackSetters;
    };

    return {createCounterpartPsn : createCounterpartPsn, createPsnTuple: createPsnTuple, resolvePsn:resolvePsn};
});

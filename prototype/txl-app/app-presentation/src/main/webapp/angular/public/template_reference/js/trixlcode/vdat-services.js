/**
 * Created by lucas on 20/08/15.
 */
var vdatHost = "https://127.0.0.1:8446/vdat";
var TXLVdatServices = angular.module('TXLVdatServices', ['TXLSecurityServices', 'TXLIdatServices', 'TXLPsnsServices' ]);

TXLVdatServices.service('TXLCaseService', function ($http, $cookies, TXLAuthenticationService, TXLPsnService, TXLIdatService) {
    'use strict';


    var fetchCaseWithPSN =  function (casePsn) {
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

        var vdatToken = TXLAuthenticationService.getTokenForHost(vdatHost);
        if (vdatToken !== "") {
            $http({
                url: vdatHost + "/rest/case/caseWithPsn",
                method: 'POST',
                headers: {
                    'X-Trixl-Token': vdatToken
                },
                data: casePsn
            }).success(function (txlCase) {
                callbacksToExecute.success(txlCase);
            });
        }

        return callbackSetters;
    }

    var updateCase = function (txlCase) {
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


        var vdatToken = TXLAuthenticationService.getTokenForHost(vdatHost);
        if (vdatToken !== "") {
            // Update client profile
            $http({
                url: vdatHost + "/rest/case/update",
                method: "POST",
                headers: {
                    "X-Trixl-Token": vdatToken
                },
                data: txlCase
            }).success(function (updatedTxlCase) {
                console.log("Updated case with number =" + updatedTxlCase.number);
                callbacksToExecute.success(updatedTxlCase);
            }).error(function () {
                console.log("Could not update case" + txlCase);
                callbacksToExecute.error();
            });

        }

        return callbackSetters;
    }


    var createCaseWithPsn = function (psn) {
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

        var vdatToken = TXLAuthenticationService.getTokenForHost(vdatHost);

        // Create case
        $http({
            url: vdatHost + "/rest/case/add",
            method: "POST",
            headers: {
                "X-Trixl-Token": vdatToken
            },
            data: psn
        }).success(function (txlCase) {
            console.log("Created case with number =" + txlCase.number);
            callbacksToExecute.success(txlCase);
        }).error(function () {
            console.log("Could not create case");
            callbacksToExecute.error();
        });


        return callbackSetters;
    }

    var createCase = function (newCase, newClientProfile, eid) {
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
        // Create Tuple for Case
        TXLPsnService.createCounterpartPsn(eid).success(function (counterpartPsn) {
            createCaseWithPsn(counterpartPsn).success(function (txlCase) {

                // Create Tuple for Client Profile
                TXLPsnService.createPsnTuple().success(function (psnTupleClientProfile) {
                    TXLIdatService.createClientProfileWithPsn(psnTupleClientProfile.psn2).success(function (clientProfile) {

                        // update case
                        txlCase.type = {"type" : newCase.type};
                        txlCase.clientProfilePsnToBeResolved = psnTupleClientProfile.psn1;
                        updateCase(txlCase).success(function (updatedCase) {

                            //update client profile
                            clientProfile.name = newClientProfile.name;
                            TXLIdatService.updateClientProfile(clientProfile).success(function (updatedClientProfile) {
                                callbacksToExecute.success();
                            });

                        });

                    });
                });

            });
        });

        return callbackSetters;
    }


    // Public methods of TXLAuthenticationService
    return {fetchCaseWithPSN: fetchCaseWithPSN, createCase: createCase, updateCase: updateCase};
});

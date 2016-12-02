/**
 * Created by lucas on 20/08/15.
 */
var TXLVdatServices = angular.module('TXLVdatServices', ['TXLSecurityServices','TXLCONSTANTS', 'TXLIdatServices', 'TXLPsnsServices']);

TXLVdatServices.service('TXLCaseService', function ($http, $cookies, $q, TXLAuthenticationService, TXLPsnService, TXLIdatService, TXL_CONFIG, TXL_TYPE) {
    'use strict';

    /**
     * Retrieve a Case using its PSN.
     *
     * Returns a promise and the caller has to deal with the result (Case) itself in the
     * promise's then function.
     *
     * @param casePsn psn of Case we want to retrieve.
     */
    this.fetchCaseWithPSN = function (casePsn) {
        var deferred = $q.defer();
        var vdatToken = TXLAuthenticationService.getTokenForHost(TXL_CONFIG.VDAT_HOST);
        if (vdatToken !== "") {
            var headers = {};
            headers[TXL_CONFIG.AUTH_HEADER] = vdatToken;
            $http({
                url: TXL_CONFIG.VDAT_HOST + "/rest/case/caseWithPsn",
                method: 'POST',
                headers: headers,
                data: casePsn
            }).success(function (txlCase) {
                deferred.resolve(txlCase);
            }).error(function () {
                deferred.reject(null);
            });
        } else {
            deferred.reject(null);
        }

        return deferred.promise;
    }

    /**
     * Update a given case.
     *
     * Returns a promise and the caller has to deal with the result (the updated Case) itself in the
     * promise's then function.
     *
     * @param txlCase Case we want to update.
     */
    var updateCase = function (txlCase) {
        var deferred = $q.defer();
        var vdatToken = TXLAuthenticationService.getTokenForHost(TXL_CONFIG.VDAT_HOST);
        if (vdatToken !== "") {
            // Update client profile
            var headers = {};
            headers[TXL_CONFIG.AUTH_HEADER] = vdatToken;
            $http({
                url: TXL_CONFIG.VDAT_HOST + "/rest/case/update",
                method: "POST",
                headers: headers,
                data: txlCase
            }).success(function (txlCase) {
                deferred.resolve(txlCase);
            }).error(function () {
                deferred.reject(null);
            });
        } else {
            deferred.reject(null);
        }

        return deferred.promise;
    }

    /**
     * Create a new Case with the given PSN in VDAT.
     *
     * Returns a promise and the caller has to deal with the result (the newly created Case) itself in the
     * promise's then function.
     *
     * @param psn psn that should be assigned to the new case.
     */
    var createCaseWithPsn = function (psn) {
        var deferred = $q.defer();
        var vdatToken = TXLAuthenticationService.getTokenForHost(TXL_CONFIG.VDAT_HOST);
        if (vdatToken !== "") {
            // Create case
            var headers = {};
            headers[TXL_CONFIG.AUTH_HEADER] = vdatToken;
            $http({
                url: TXL_CONFIG.VDAT_HOST + "/rest/case/add",
                method: "POST",
                headers: headers,
                data: psn
            }).success(function (txlCase) {
                deferred.resolve(txlCase);
            }).error(function () {
                deferred.reject(null);
            });
        } else {
            deferred.reject(null);
        }

        return deferred.promise;
    }

    /**
     * Persist the new Case in VDAT and link it with the EID (IDAT Client) within PSNS by creating a counterpart
     * psn for the EID. Then associate the Case with the ClientProfile (IDAT) by either creating a new psn tuple in PSNS and
     * also a new ClientProfile in IDAT or by using an existing ClientProfile and psn tuple. The later depends on whether
     * the given newClientProfile already has a psn.
     *
     * @param newCase Case we want to persist in VDAT.
     * @param newClientProfile ClientProfile we want to assign to the Case.
     * @param eid EID (PSN) belonging to a Client in IDAT.
     */
    this.createCase = function (newCase, newClientProfile, eid) {
        var txlCase;

        if (newClientProfile.psn !== undefined) {
            // We want to use an existing ClientProfile
            return TXLPsnService.createCounterpartPsn(eid)
                .then(function (counterpartPsn) {
                    return createCaseWithPsn(counterpartPsn);
                })
                .then(function (createdCase) {
                    txlCase = createdCase;
                    return TXLPsnService.resolvePsn(newClientProfile.psn);
                })
                .then(function (resolvedPsns) {
                    txlCase.type = {"value": newCase.type, "type":{"name": TXL_TYPE.CASE_TYPE}};
                    txlCase.clientProfilePsnToBeResolved = resolvedPsns[0];
                    return updateCase(txlCase);
                });

        } else {
            // We want to create a new ClientProfile
            var psnTupleClientProfile;
            var clientProfile;

            return TXLPsnService.createCounterpartPsn(eid)
                .then(function (counterpartPsn) {
                    return createCaseWithPsn(counterpartPsn);
                })
                .then(function (createdCase) {
                    txlCase = createdCase;
                    return TXLPsnService.createPsnTuple();
                })
                .then(function (psnTuple) {
                    psnTupleClientProfile = psnTuple;
                    return TXLIdatService.createClientProfileWithPsn(psnTupleClientProfile.psn2);
                })
                .then(function (createdClientProfile) {
                    // update case
                    clientProfile = createdClientProfile;
                    txlCase.type = {"value": newCase.type, "type":{"name": TXL_TYPE.CASE_TYPE}};
                    txlCase.clientProfilePsnToBeResolved = psnTupleClientProfile.psn1;
                    return updateCase(txlCase);
                })
                .then(function (updatedCase) {
                    // update client profile
                    clientProfile.name = newClientProfile.name;
                    return TXLIdatService.updateClientProfile(clientProfile);
                });
        }
    }

    /**
     * Retreive all Cases assigned to a user.
     *
     * Returns a promise and the caller has to deal with the result (list of Cases) itself in the
     * promise's then function.
     *
     * @param user User for whom we want to retrieve the assigned cases
     */
    this.getAssignedCases = function (user) {
        var deferred = $q.defer();
        var vdatToken = TXLAuthenticationService.getTokenForHost(TXL_CONFIG.VDAT_HOST);
        if (vdatToken !== "") {
            var headers = {};
            headers[TXL_CONFIG.AUTH_HEADER] = vdatToken;
            $http({
                url: TXL_CONFIG.VDAT_HOST + "/rest/case/assignedCases",
                method: "POST",
                headers: headers,
                data: user
            }).success(function (cases) {
                deferred.resolve(cases);
            }).error(function () {
                deferred.reject(null);
            });
        } else {
            deferred.reject(null);
        }

        return deferred.promise;
    }

});

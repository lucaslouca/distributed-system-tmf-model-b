/**
 * Created by lucas on 20/08/15.
 */
var TXLPsnsServices = angular.module('TXLPsnsServices', ['TXLSecurityServices','TXLCONSTANTS']);

TXLPsnsServices.service('TXLPsnService', function ($http, $cookies, $q, TXLAuthenticationService, TXL_CONFIG) {
    'use strict';

    /**
     * Retrieve the counterpart psn for the given psn.
     * 
     * Returns a promise and the caller has to deal with the result (PSN) itself in the 
     * promise's then function.
     * 
     * @param psn psn for which we want the counterpart.
     */
    this.resolvePsn = function (psn) {
        var deferred = $q.defer();
        // Resolve client profile pseudonym
        var psnsToken = TXLAuthenticationService.getTokenForHost(TXL_CONFIG.PSNS_HOST);
        if (psnsToken !== "") {
            var headers = {};
            headers[TXL_CONFIG.AUTH_HEADER] = psnsToken;
            $http({
                url: TXL_CONFIG.PSNS_HOST + "/rest/resolve",
                method: "POST",
                headers: headers,
                data: psn
            }).success(function (resolvedPsn) {
                deferred.resolve(resolvedPsn);
            }).error(function () {
                deferred.reject(null);
            });
        } else {
            deferred.reject(null);
        }

        return deferred.promise;
    };

    /**
     * Create a new PSN tuple in PSNS and receive it.
     * 
     * Returns a promise and the caller has to deal with the result (psnTuple with psnTuple.psn1 and psnTuple.psn2) itself in the 
     * promise's then function.
     */
    this.createPsnTuple = function () {
        var deferred = $q.defer();
        var psnsToken = TXLAuthenticationService.getTokenForHost(TXL_CONFIG.PSNS_HOST);
        if (psnsToken !== "") {
            var headers = {};
            headers[TXL_CONFIG.AUTH_HEADER] = psnsToken;
            $http({
                url: TXL_CONFIG.PSNS_HOST + "/rest/createTuple",
                method: "POST",
                headers: headers
            }).success(function (psnTuple) {
                deferred.resolve(psnTuple);
            }).error(function () {
                deferred.reject(null);
            });
        } else {
            deferred.reject(null);
        }

        return deferred.promise;
    };

    /**
     * Create and retrieve a new counterpart psn for the given psn.
     * 
     * Returns a promise and the caller has to deal with the result (the new counterpart PSN) itself in the 
     * promise's then function.
     * 
     * @param psn psn for which we want to create a counterpart.
     */
    this.createCounterpartPsn = function (psn) {
        var deferred = $q.defer();
        var psnsToken = TXLAuthenticationService.getTokenForHost(TXL_CONFIG.PSNS_HOST);
        if (psnsToken !== "") {
            var headers = {};
            headers[TXL_CONFIG.AUTH_HEADER] = psnsToken;
            $http({
                url: TXL_CONFIG.PSNS_HOST + "/rest/addCounterpart",
                method: "POST",
                headers: headers,
                data: psn
            }).success(function (counterpartPsn) {
                deferred.resolve(counterpartPsn);
            }).error(function () {
                deferred.reject(null);
            });
        } else {
            deferred.reject(null);
        }

        return deferred.promise;
    };
});

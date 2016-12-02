/**
 * Created by lucas on 20/08/15.
 */
var TXLIdatServices = angular.module('TXLIdatServices', ['TXLSecurityServices','TXLCONSTANTS', 'ngResource']);

TXLIdatServices.factory('TXLClientProfile', function ($resource, TXLAuthenticationService, TXL_CONFIG) {
    var headers = {};
    headers[TXL_CONFIG.AUTH_HEADER] = TXLAuthenticationService.getTokenForHost(TXL_CONFIG.IDAT_HOST);
    return $resource(TXL_CONFIG.IDAT_HOST + '/rest/clientProfile/:id', {}, {headers: headers});
});

TXLIdatServices.factory('TXLClient', function ($resource, TXLAuthenticationService, TXL_CONFIG) {
    var headers = {};
    headers[TXL_CONFIG.AUTH_HEADER] = TXLAuthenticationService.getTokenForHost(TXL_CONFIG.IDAT_HOST);
    return $resource(TXL_CONFIG.IDAT_HOST + '/rest/client/:id', {}, {headers:headers});
});

TXLIdatServices.service('TXLIdatService', function ($http, $q, TXLAuthenticationService, TXL_CONFIG) {
        'use strict';

        /**
         * Retrieve the Client EID (a PSN) for the given username. That is, to which client the user belongs.
         * 
         * Returns a promise and the caller has to deal with the result (PSN) itself in the 
         * promise's then function.
         * 
         * @param username username for which we want the associated Client EID
         */
        this.eidForUser = function (username) {
            var deferred = $q.defer();
            var idatToken = TXLAuthenticationService.getTokenForHost(TXL_CONFIG.IDAT_HOST);
            if (idatToken !== "") {
                // Resolve
                var headers = {};
                headers[TXL_CONFIG.AUTH_HEADER] = idatToken;
                $http({
                    url: TXL_CONFIG.IDAT_HOST + "/rest/client/eidForUser",
                    method: "POST",
                    headers: headers,
                    data: {"username": username}
                }).success(function (eid) {
                    deferred.resolve(eid);
                }).error(function () {
                    deferred.reject(null);
                });
            } else {
                deferred.reject(null);
            }

            return deferred.promise;
        };

        /**
         * Retrieve the ClientProfile for the given psn.
         * 
         * Returns a promise and the caller has to deal with the result (ClientProfile) itself in the 
         * promise's then function.
         * 
         * @param psn psn of the ClientProfile you want to receive.
         */
        this.fetchClientProfileWithPsn = function (psn) {
            var deferred = $q.defer();
            var idatToken = TXLAuthenticationService.getTokenForHost(TXL_CONFIG.IDAT_HOST);

            if (idatToken !== "") {
                var headers = {};
                headers[TXL_CONFIG.AUTH_HEADER] = idatToken;
                $http({
                    url: TXL_CONFIG.IDAT_HOST + "/rest/clientProfile/clientProfileWithPsn",
                    method: "POST",
                    headers: headers,
                    data: psn
                }).success(function (clientProfile) {
                    deferred.resolve(clientProfile);
                }).error(function () {
                    deferred.reject(null);
                });
            } else {
                deferred.reject(null);
            }

            return deferred.promise;
        };

        /**
         * Create a new ClientProfile that should have the given psn.
         * 
         * Returns a promise and the caller has to deal with the result (ClientProfile) itself in the 
         * promise's then function.
         * 
         * @param psn psn for the new ClientProfile.
         */
        this.createClientProfileWithPsn = function (psn) {
            var deferred = $q.defer();
            var idatToken = TXLAuthenticationService.getTokenForHost(TXL_CONFIG.IDAT_HOST);

            if (idatToken !== "") {
                // Create client profile
                var headers = {};
                headers[TXL_CONFIG.AUTH_HEADER] = idatToken;
                $http({
                    url: TXL_CONFIG.IDAT_HOST + "/rest/clientProfile/add",
                    method: "POST",
                    headers: headers,
                    data: psn
                }).success(function (clientProfile) {
                    deferred.resolve(clientProfile);
                }).error(function () {
                    deferred.reject(null);
                });
            } else {
                deferred.reject(null);
            }

            return deferred.promise;
        }

        /**
         * Update the given ClientProfile in the IDAT.
         * 
         * Returns a promise and the caller has to deal with the result (ClientProfile) itself in the 
         * promise's then function.
         * 
         * @param clientProfile ClientProfiole we want to update.
         */
        this.updateClientProfile = function (clientProfile) {
            var deferred = $q.defer();
            var idatToken = TXLAuthenticationService.getTokenForHost(TXL_CONFIG.IDAT_HOST);

            if (idatToken !== "") {
                // Update client profile
                var headers = {};
                headers[TXL_CONFIG.AUTH_HEADER] = idatToken;
                $http({
                    url: TXL_CONFIG.IDAT_HOST + "/rest/clientProfile/update",
                    method: "POST",
                    headers: headers,
                    data: clientProfile
                }).success(function (updatedClientProfile) {
                    deferred.resolve(updatedClientProfile);
                }).error(function () {
                    deferred.reject(null);
                });
            } else {
                deferred.reject(null);
            }

            return deferred.promise;
        }
});

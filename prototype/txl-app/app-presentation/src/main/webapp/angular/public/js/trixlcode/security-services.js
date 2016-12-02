var TXLSecurityServices = angular.module('TXLSecurityServices', ['ngCookies','TXLCONSTANTS']);


// AuthenticationService that logs in at a given host and receives a token.
// Once authenticated, the token can be read using the getTokenForHost(host) method. 
TXLSecurityServices.service('TXLAuthenticationService', function ($http, $cookies, $q, TXL_CONFIG) {
    'use strict';

    /**
     * Set the auth token for the host in the browser storage ($cookies)
     * 
     * @param host host to which the token belongs
     * @param token token to store
     */
    var setTokenForHost = function (host, token) {
        $cookies.put(host, token);
    };

    /**
     * Returns the auth token for for the given host.
     */
    var getTokenForHost = function (host) {
        return $cookies.get(host);
    };

    /**
     * Login to the host with the given credentials.
     * 
     * Returns a promise and the caller has to deal with the result (User) itself in the 
     * promise's then function.
     * 
     * @param host host we want to authenticate at
     * @param credentials credentials containing credentials.username and credentials.password
     */
    var authenticate = function (host, credentials) {
        var deferred = $q.defer();

        var credentialsJson = credentials ? {"username": credentials.username, "password": credentials.password} : {};

        // Authenticate on host
        $http.post(host + "/services/authenticate", credentialsJson)
            .success(function (user, status, headers) {
                var token = headers()[TXL_CONFIG.AUTH_HEADER.toLowerCase()];
                if (token) {
                    setTokenForHost(host, token);
                }
                deferred.resolve(user);
            }).error(function () {
                deferred.reject("Authentication failed for username '"+credentials.username+"'");
            });

        return deferred.promise;
    };

    // Public methods of TXLAuthenticationService
    return {authenticate: authenticate, getTokenForHost: getTokenForHost};
});

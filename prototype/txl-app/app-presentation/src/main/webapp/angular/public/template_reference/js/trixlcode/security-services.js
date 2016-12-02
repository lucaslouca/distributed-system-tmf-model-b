var TXLSecurityServices = angular.module('TXLSecurityServices', ['ngCookies']);


// AuthenticationService that logs in at a given host and receives a token.
// Once authenticated, the toek can be read using the getTokenForHost(host) method. 
TXLSecurityServices.service('TXLAuthenticationService', function($http, $cookies) {
    'use strict';

	// Set the token for the host
	var setTokenForHost = function(host, token) {
        $cookies.put(host, token);
	};

	// Get the token for a given host
	var getTokenForHost = function(host) {
		return $cookies.get(host);
	};

	// Login using credentials and receive a token
	var authenticate = function(host, credentials) {
		var callbacksToExecute = {};
		var credentialsJson = credentials ? {"username" : credentials.username, "password" : credentials.password} : {};
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

		// Authenticate on host
		$http.post(host + "/services/authenticate", credentialsJson)
				.success(function(data, status, headers) {
					var token = headers()["x-trixl-token"];
					if (token) {
						setTokenForHost(host, token);
						callbacksToExecute.success();
					} else {
						callbacksToExecute.success();
					}
				}).error(function() {
					callbacksToExecute.error();
				});

		return callbackSetters;
	};
	
	// Public methods of TXLAuthenticationService
	return {authenticate: authenticate, getTokenForHost: getTokenForHost};
});

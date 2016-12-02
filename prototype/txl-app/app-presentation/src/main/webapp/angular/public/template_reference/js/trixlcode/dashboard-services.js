/**
 * Created by Evi on 06/09/15.
 */
var TXLDashboard = angular.module('TXLDashboard', []);

TXLDashboard.service('TXLDashboardService', function ($http, $cookies, TXLAuthenticationService, TXLPsnService, TXLVdatService) {
    'use strict';

    var assigendCases = [];

    var clearList = function () {
    	assigendCases = [];
    }

    var getAssignedCases = function () {
        return assigendCases;
    }

    return {clearList:clearList, getAssignedCases: getAssignedCases};
});

/**
 * Created by Evi on 13/09/15.
 */
var TXLDashboard = angular.module('TXLDashboard', []);

TXLDashboard.service('TXLDashboardService', function () {
    'use strict';

    var assignedCases = [];

    var clearList = function () {
    	assignedCases = [];
    }

    var addCaseToCache = function (txlCase) {
    	assignedCases.push(txlCase);
    }

    var getAssignedCases = function () {
        return assignedCases;
    }

    return {clearList:clearList, addCaseToCache: addCaseToCache, getAssignedCases: getAssignedCases};
});
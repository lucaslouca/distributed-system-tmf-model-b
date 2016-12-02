/**
 * Created by lucas on 20/08/15.
 */
var TXLCaseList = angular.module('TXLCaseList', []);

TXLCaseList.service('TXLCaseListService', function ($http, $cookies, TXLAuthenticationService, TXLPsnService, TXLIdatService) {
    'use strict';

    var cases = [];

    var clearList = function () {
        cases = [];
    }

    var addCaseToCache = function (txlCase) {
        cases.push(txlCase);
    }

    var getCases = function () {
        return cases;
    }

    return {clearList:clearList, addCaseToCache: addCaseToCache, getCases: getCases};
});

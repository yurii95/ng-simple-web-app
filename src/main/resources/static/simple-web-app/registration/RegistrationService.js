'use strict';

angular.module('Registration')

    .factory('RegistrationService',
        ['$http', '$cookieStore', '$rootScope', '$timeout',
            function ($http, $cookieStore, $rootScope) {
                var service = {};

                service.Create = function (user, callback, errCallback) {
                    $http.post('http://localhost:8080/users/insert', user)
                        .then(function successCallback(response) {
                            callback(response);
                        }, function errorCallback(response) {
                            errCallback(response);
                        });
                };
                return service;
            }]);
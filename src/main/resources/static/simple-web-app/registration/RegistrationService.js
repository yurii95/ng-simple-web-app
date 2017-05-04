'use strict';

angular.module('Registration')

    .factory('RegistrationService',
        ['Base64', '$http', '$cookieStore', '$rootScope', '$timeout',
            function (Base64, $http, $cookieStore, $rootScope) {
                var service = {};

                service.Create = function (username, password, callback) {
                    $http.post('http://localhost:8080/users/insert', data)
                        .then(function successCallback(response) {
                            callback(response);
                        }, function errorCallback(response) {
                            console.log("ERRORR " + response.status);
                            console.log("ERROR " + response.data);
                            callbackErr(response);
                        });

                };

                return service;
            }]);
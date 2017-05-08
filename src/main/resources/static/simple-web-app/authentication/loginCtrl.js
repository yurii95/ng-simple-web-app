'use strict';

angular.module('Authentication')

    .controller('LoginController',
        ['$scope', '$rootScope', '$location', 'AuthenticationService',
            function ($scope, $rootScope, $location, AuthenticationService) {
                AuthenticationService.ClearCredentials();
                console.log("message" + $rootScope.RegistrationMessage);
                $scope.login = function () {
                    $scope.dataLoading = true;
                    AuthenticationService.Login($scope.username, $scope.password, function(response) {
                        console.log("Success " + response);
                        console.log(response.data);
                        console.log("Success " + response.status);
                        if(response.data === "true") {
                            AuthenticationService.SetCredentials($scope.username, $scope.password);
                            $location.path('/');
                            $rootScope.RegistrationMessage="";
                        } else {
                            console.log("User not found");
                            $scope.dataLoading = false;
                            $scope.error = "user is not registered"
                        }
                    });
                };

                $scope.logout = function () {
                    AuthenticationService.ClearCredentials();
                };
            }]);
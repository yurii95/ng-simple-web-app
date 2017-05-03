'use strict';

angular.module('Authentication')

    .controller('LoginController',
        ['$scope', '$rootScope', '$location', 'AuthenticationService',
            function ($scope, $rootScope, $location, AuthenticationService) {
                // reset login status
                console.log("1");
                AuthenticationService.ClearCredentials();
                console.log("2");
                $scope.login = function () {
                    $scope.dataLoading = true;
                    console.log("dataloading");
                    AuthenticationService.Login($scope.username, $scope.password, function(response) {
                        console.log("Success " + response);
                        console.log(response.data);
                        console.log("Success " + response.status);
                        // if(response.success) {
                        //     console.log("Success " + response);
                            if(response.data === "true") {
                                AuthenticationService.SetCredentials($scope.username, $scope.password);
                                $location.path('/');
                            } else {
                                console.log("User not found");
                                $scope.dataLoading = false;
                                $scope.error = "user is not registered"
                            }
                        // } else {
                        //     console.log("Error: " + response.toString());
                        //     $scope.error = response.message;
                        //
                        //     $scope.dataLoading = false;
                        // }
                    });
                };
            }]);
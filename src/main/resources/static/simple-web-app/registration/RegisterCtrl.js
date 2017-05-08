'use strict';

angular
    .module('Registration')
    .controller('RegisterController', ['$scope', '$rootScope', '$location', 'RegistrationService',
        function ($scope, $rootScope, $location, RegistrationService) {
            console.log("register controller");
            $scope.register = function () {
                $scope.dataLoading = true;
                RegistrationService.Create($scope.user, function (response) {
                        $location.path("/login");
                        console.log("Response status: "+response.status);
                        $scope.dataLoading = false;
                        $rootScope.RegistrationMessage="Congratulations!!! Registration completed successfully";
                    },
                    function (response) {
                        $scope.dataLoading = false;
                        if (response.status === 409) {
                            $scope.error = "User with this login already exist";
                        } else {
                            console.log("Internal server exception: "+response.status);
                        }
                    });
            }
        }]);


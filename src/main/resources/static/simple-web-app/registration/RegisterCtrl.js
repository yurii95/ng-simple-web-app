'use strict';

angular
    .module('Registration')
    .controller('RegisterController', ['$scope', '$rootScope', '$location', '$RegistrationService',
        function (scope, $rootScope, $location, $RegistrationService) {
            console.log("register controller");

            $scope.register = function () {
                vm.dataLoading = true;
                RegistrationService.Create(user.username, user.password, function (response) {
                    $scope.books = response.data;
                });
            }
        }]);


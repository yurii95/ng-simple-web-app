'use strict';

angular.module('Authentication', []);
angular.module('Home', []);

angular.module('BasicHttpAuthExample', ['Authentication','Home', 'ngRoute', 'ngCookies'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/login', {
                controller: 'LoginController',
                templateUrl: 'authentication/login.html'
            })

            .when('/new', {
                controller: 'HomeController',
                templateUrl: 'home/new.html'
            })

            .when('/', {
                controller: 'HomeController',
                templateUrl: 'home/home.html'
            })

            .otherwise({redirectTo: '/login'});
    }])

    .run(['$rootScope', '$location', '$cookieStore', '$http',
        function ($rootScope, $location, $cookieStore, $http) {
            // keep user logged in after page refresh
            $rootScope.globals = $cookieStore.get('globals') || {};
            if ($rootScope.globals.currentUser) {
                $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
            }

            $rootScope.$on('$locationChangeStart', function (event, next, current) {
                // redirect to login page if not logged in
                if ($location.path() !== '/login' && !$rootScope.globals.currentUser) {
                    $location.path('/login');
                }
            });
        }]);




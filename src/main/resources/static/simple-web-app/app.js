'use strict';

angular.module('Authentication', []);
angular.module('Home', []);
angular.module('Registration', []);

angular.module('BasicHttpAuthExample', ['Authentication','Home', 'Registration' ,'ngRoute', 'ngCookies'])

    .config(['$routeProvider', function ($routeProvider) {
        console.log("config");
        $routeProvider
            .when('/login', {
                controller: 'LoginController',
                templateUrl: 'authentication/login.html'
            })

            .when('/insert', {
                controller: 'HomeController',
                templateUrl: 'home/insert.html'
            })

            .when('/500', {
                controller: 'HomeController',
                templateUrl: 'error-pages/error_page_http_500.html'
            })

            .when('/register', {
                controller: 'RegisterController',
                templateUrl: 'registration/register.html'
            })

            .when('/', {
                controller: 'HomeController',
                templateUrl: 'home/books.html'
            })

            .otherwise({redirectTo: '/login'});
    }])

    .run(['$rootScope', '$location', '$cookieStore', '$http',
        function ($rootScope, $location, $cookieStore, $http) {
            console.log("run");
            $rootScope.globals = $cookieStore.get('globals') || {};
            if ($rootScope.globals.currentUser) {
                $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
            }

            $rootScope.$on('$locationChangeStart', function (event, next, current) {
                var restrictedPage = $.inArray($location.path(), ['/login', '/register']) === -1;
                var loggedIn = $rootScope.globals.currentUser;
                if (restrictedPage && !loggedIn) {
                    $location.path('/login');
                }
            });
        }]);




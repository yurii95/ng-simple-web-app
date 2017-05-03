'use strict';

angular.module('Home')

    .factory('BookService',
        ['Base64', '$http', '$cookieStore', '$rootScope', '$timeout', '$q',
            function (Base64, $http, $cookieStore, $rootScope, $timeout, $q) {
                var bookService = {};


                bookService.findAllBooks = function (callback) {
                    $http.get('http://localhost:8080/books/all')
                        .then(function successCallback(response) {
                            console.log("data lengths " + response.data.length);
                            callback(response);
                        }, function errorCallback(response) {
                            console.log("ERROR " + response.status);
                        });

                };

                bookService.addBook = function (data, callback) {
                    console.log("IN book service: " + data.title);
                    console.log("IN book service: " + data.pages);
                    $http.post('http://localhost:8080/books/new', data)
                        .then(function successCallback(response) {
                            callback(response);
                        }, function errorCallback(response) {
                            console.log("ERROR " + response.status);
                        });
                };

                bookService.deleteBook = function (data, callback) {
                    $http.delete('http://localhost:8080/books/'+data)
                        .then(function successCallback(response) {
                                callback(response);
                            },
                            function errorCallback(errResponse){
                                console.error('Error while deleting book');
                            }
                        );
                };

                bookService.updateBook = function (id, data, callback) {
                    $http.put('http://localhost:8080/books/'+id, data)
                        .then(function successCallback(response) {
                                callback(response);
                            },
                            function errorCallback(errResponse){
                                console.error('Error while deleting book');
                            }
                        );
                };

                bookService.filterBook = function (searchCriteria, callback) {
                    $http.get('http://localhost:8080/books/search?srch=' + searchCriteria)
                        .then(function successCallback(response) {
                                callback(response);
                            },
                            function errorCallback(errResponse){
                                console.error('Error while searching book with %s crotetia');
                            }
                        );
                };
                // service.SetCredentials = function (username, password) {
                //     var authdata = Base64.encode(username + ':' + password);
                //
                //     $rootScope.globals = {
                //         currentUser: {
                //             username: username,
                //             authdata: authdata
                //         }
                //     };
                //
                //     $http.defaults.headers.common['Authorization'] = 'Basic ' + authdata; // jshint ignore:line
                //     $cookieStore.put('globals', $rootScope.globals);
                // };
                //
                // service.ClearCredentials = function () {
                //     $rootScope.globals = {};
                //     $cookieStore.remove('globals');
                //     $http.defaults.headers.common.Authorization = 'Basic ';
                // };

                return bookService;
            }])
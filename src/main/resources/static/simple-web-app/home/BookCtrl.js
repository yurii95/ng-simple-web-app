'use strict';

angular.module('Home')

    .controller('HomeController', ['$scope', '$rootScope', '$location', 'BookService',
        function ($scope, $rootScope, $location, BookService) {
            console.log("home controller executed");

            $scope.findAllBooks = function () {
                BookService.findAllBooks(function (response) {
                    $scope.books=response.data;
                });
            };

            $scope.filterBook = function () {
                BookService.filterBook($scope.searchString,function (response) {
                    $scope.books=response.data;
                });
            };

            $scope.redirect = function(){
                $location.path("/insert");
            };

            $scope.addBook = function () {
                $scope.currentBook = {title : $scope.bookTitle, genre : $scope.bookGenre,
                authors: $scope.bookAuthor, pages: $scope.bookPages, description: $scope.bookDescription};
                BookService.addBook($scope.currentBook, function (response) {
                    $location.path("/");
                }, function (response) {
                    $location.path("/500");
                });
            };

            $scope.removeBook = function (id) {
                BookService.deleteBook(id, function (response) {
                    console.log(response.status);
                    $scope.findAllBooks();
                });
            };

            $scope.updateBook = function (id) {
                BookService.updateBook(id, $scope.currentBook, function (response) {
                    console.log(response.status);
                    $scope.findAllBooks();
                });
            };

            $scope.setDataForUpdate = function(book){
                $scope.currentBook = book;
                console.log(title);
            };

            $scope.findAllBooks();

            console.log("Current book "  + $scope.currentBook);
        }]);
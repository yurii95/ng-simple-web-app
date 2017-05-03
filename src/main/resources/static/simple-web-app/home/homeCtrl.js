'use strict';

angular.module('Home')

    .controller('HomeController', ['$scope', '$rootScope', '$location', 'BookService',
        function ($scope, $rootScope, $location, BookService) {
            console.log("home controller executed");
            $scope.currentBook = {title:"boss"};
            // $scope.setVariabl $scope.currentBook={title:"dsfsdf"};
            $scope.findAllBooks = function () {
                BookService.findAllBooks(function (response) {
                    $scope.books=response.data;
                    for(var i=0;i< $scope.books.length; i++){
                        console.log($scope.books[i].title);
                        console.log($scope.books[i].description);
                        console.log($scope.books[i].genre);
                        console.log($scope.books[i].pages);
                    }
                });
            };

            $scope.filterBook = function () {
                console.log("asdaaaaaaaaaaaaaaaaaaaaaaaaaa");
                BookService.filterBook($scope.searchString,function (response) {
                    $scope.books=response.data;
                    for(var i=0;i< $scope.books.length; i++){
                        console.log($scope.books[i].title);
                        console.log($scope.books[i].description);
                        console.log($scope.books[i].genre);
                        console.log($scope.books[i].pages);
                    }
                });
            };

            $scope.redirect = function(){
                $location.path("/new");
            };

            $scope.addBook = function () {
                $scope.currentBook = {title : $scope.bookTitle, genre : $scope.bookGenre,
                authors: $scope.bookAuthor, pages: $scope.bookPages, description: $scope.bookDescription};
                BookService.addBook($scope.currentBook, function (response) {
                    $location.path("/");
                    console.log("Currn tbook "  + $scope.currentBook.title);
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
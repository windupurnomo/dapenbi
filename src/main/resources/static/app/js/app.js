angular.module('dapenbi-app', ['ui.router'])

    .constant('url', 'http://localhost:3000')

    .config(
        ['$stateProvider', '$urlRouterProvider',
            function($stateProvider, $urlRouterProvider) {
                $urlRouterProvider
                    .when('/c?id', '/contacts/:id')
                    .when('/user/:id', '/contacts/:id')
                    .otherwise('/');

                $stateProvider
                    .state(router.home)
                    .state(router.employeeList)
                    .state(router.employee)
                    .state(router.employeeGeneral)
                    .state(router.employeeFamily)
                    .state(router.employeeAddress)
            }
        ]
    );


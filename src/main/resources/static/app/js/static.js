var router = {
    home: {
        name: "home",
        url: "/",
        templateUrl: "app/templates/home.html",
        controller: "home-ctrl"
    },
    employeeList: {
        name: "employee-list",
        url: "/employee",
        templateUrl: "app/templates/employee.html",
        controller: "employee-ctrl"
    },
    employee: {
        name: "employee",
        url: "/emp",
        templateUrl: "app/templates/employee-container.html",
        controller: "employee-container-ctrl"
    },
    employeeGeneral: {
        name: "employee.general",
        url: "/general/:id",
        templateUrl: "app/templates/employee-general.html",
        controller: "employee-general-ctrl"
    },
    employeeFamily: {
        name: "employee.family",
        url: "/family/:id",
        templateUrl: "app/templates/employee-family.html",
        controller: "employee-family-ctrl"
    },
    employeeAddress: {
        name: "employee.address",
        url: "/address/:id",
        templateUrl: "app/templates/employee-address.html",
        controller: "employee-address-ctrl"
    }
}
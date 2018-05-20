var getEmployees = function($http, $scope, url) {
    $http.get(url + "/employee").then(function(res) {
        $scope.employees = res.data.content;
    }, function() {})
}

var getEmployee = function($http, $scope, url, employeeId) {
    $http.get(url + "/employee/" + employeeId).then(function(res) {
        $scope.employee = res.data;
    }, function() {})
}

var getFamilies = function($http, $scope, url, employeeId) {
    $http.get(url + "/employee-family/employee/" + employeeId).then(function(res) {
        $scope.families = res.data;
    }, function() {})
}

var getAddress = function($http, $scope, url, employeeId) {
    $http.get(url + "/employee/" + employeeId).then(function(res) {
        $scope.employee = res.data;
        $http.get(url + "/employee-address/employee/" + employeeId).then(function(res) {
            $scope.form = res.data;
            $scope.form.employee = $scope.employee;
        }, function() {})
    }, function() {})
}

var success = function(res) {
    alert("Data berhasil disimpan");
}

var fail = function(res) {
    alert("Data gagal disimpan. " + res.data.message);
}

/*=========== controller ==========*/

var ctrlEmp = function($scope, $http, url, $state) {
    getEmployees($http, $scope, url);

    $scope.goDetail = function(nip) {
        $state.go('employee.general', { id: nip });
    }
}

var ctrlCont = function($scope, $http, url) {

}

var ctrlHome = function($scope, $http, url) {
    getEmployees($http, $scope, url);
    $scope.clear = function (){
        $scope.employee = null;
    }
}


var ctrlGen = function($scope, $http, url, $state) {
    var nip = $state.params.id;
    $scope.form = { nip: nip };
    if (nip != "0") {
        $http.get(url + "/employee/" + nip).then(function(res) {
            res.data.birthDate = new Date(res.data.birthDate);
            res.data.startDate = new Date(res.data.startDate);
            res.data.finishDate = new Date(res.data.finishDate);
            $scope.form = res.data;
        })
    }

    $scope.save = function() {
        if ($state.params.id == "0") {
            $http.post(url + "/employee", $scope.form).then(success, fail);
        } else {
            $http.put(url + "/employee", $scope.form).then(success, fail);
        }
    }
}

var ctrlFam = function($scope, $http, url, $state) {
    var employeeId = $state.params.id;
    if (employeeId == "0") {
        alert("Harus isi data pegawai terlebih dulu");
        $state.go('employee.general', { id: 0 });
        return;
    }
    getEmployee($http, $scope, url, employeeId);
    getFamilies($http, $scope, url, employeeId);

    $scope.add = function (){
        $scope.showForm = true
        $scope.form = {
            employee: $scope.employee
        }
    }

    $scope.save = function() {
        if ($scope.form.id) {
            $http.put(url + "/employee-family", $scope.form).then(success, fail);
        } else {
            $http.post(url + "/employee-family", $scope.form).then(success, fail);
        }
    }
}

var ctrlAdr = function($scope, $http, url, $state) {
    var employeeId = $state.params.id;
    if (employeeId == "0") {
        alert("Harus isi data pegawai terlebih dulu");
        $state.go('employee.general', { id: 0 });
        return;
    }
    getAddress($http, $scope, url, employeeId);
    $scope.form = {};
    var urlProvinsi = "https://api.etanee.id/provinsi";
    var urlKabupaten = "https://api.etanee.id/kabupaten/provinsi/";
    var urlKecamatan = "https://api.etanee.id/kecamatan/kabupaten/";
    var urlKelurahan = "https://api.etanee.id/kelurahan/kecamatan/";

    $http.get(urlProvinsi).then(function(res) {
        $scope.provinsis = res.data;
    });

    $scope.reloadKabupaten = function() {
        $http.get(urlKabupaten + $scope.form.provinsi).then(function(res) {
            $scope.kabupatens = res.data;
        })
    }

    $scope.reloadKecamatan = function() {
        $http.get(urlKecamatan + $scope.form.kabupaten).then(function(res) {
            $scope.kecamatans = res.data;
        })
    }

    $scope.reloadKelurahan = function() {
        $http.get(urlKelurahan + $scope.form.kecamatan).then(function(res) {
            $scope.kelurahans = res.data;
        })
    }

    $scope.save = function() {
        if ($scope.form.id) {
            $http.put(url + "/employee-address", $scope.form).then(success, fail);
        } else {
            $http.post(url + "/employee-address", $scope.form).then(success, fail);
        }
    }
}

angular.module('dapenbi-app')
    .controller('home-ctrl', ctrlHome)
    .controller('employee-ctrl', ctrlEmp)
    .controller('employee-container-ctrl', ctrlCont)
    .controller('employee-general-ctrl', ctrlGen)
    .controller('employee-family-ctrl', ctrlFam)
    .controller('employee-address-ctrl', ctrlAdr);
var app = angular.module('jenkins', []);



app.controller('JobsController', function( $scope, $http, $window, jenkinsService) {
    
    $scope.jobs = [];
    $scope.logs = [];
    $scope.job = [];
    
    $scope.isJobs = true;
    $scope.infoServer=true;
    $scope.isLogs = false;
    $scope.isNewJob = true;
    
    jenkinsService.getInfo().success(function(data){
        var data_obj = JSON.parse(JSON.stringify(data));
        $scope.jobs = data_obj;
    });
    
    
    $scope.loadLogs = function(jobname){
        jenkinsService.getLogsJob(jobname).success(function(data){
            var data_obj = JSON.parse(JSON.stringify(data));
            $scope.logs = data_obj;
            $scope.setView('logs');
        });
    };
    
    
    $scope.loadJobs = function(){
        jenkinsService.getInfo().success(function(data){
             var data_obj = JSON.parse(JSON.stringify(data));
            $scope.jobs = data_obj;
            $scope.setView('jobs');
        });
    };
    
    
    $scope.saveAndBuild = function(){
        var query = '?jobname='+$scope.job.jobname+
            '&github='+$scope.job.github+
            '&url='+$scope.job.url+ 
            '&build_on_change='+$scope.job.build_on_change;
        jenkinsService.newJob(query).success(function(data){
            $scope.loadJobs();
        }).error(function(data){
            console.log("JENKINS: error adding new job");
        });
    };
    
    $scope.build = function(jobname){
        jenkinsService.build(jobname).success(function(data){
                $scope.setView('jobs');
        });
    };
    
    $scope.setView = function(view){
        if(view === 'jobs'){
            $scope.isJobs = true;
            $scope.infoServer = true;
            $scope.isLogs = false;
            $scope.isNewJob = true;
            $scope.closeNew();
        }else if(view === 'logs'){
            $scope.isJobs = false;
            $scope.infoServer = false;
            $scope.isLogs = true;
            $scope.isNewJob = false;
            $(".englo_submenu").hide();
        }else if(view === 'newJob'){
            $scope.isJobs = true;
            $scope.infoServer = true;
            $scope.isLogs = false;
            $scope.isNewJob = true;
            
        }else if(view === 'infoServer'){
            $scope.isJobs = true;
            $scope.infoServer = true;
            $scope.isLogs = false;
            $scope.isNewJob = true;
        }
        
    };
    
    $scope.closeNew = function(){
        $("#new_job").removeClass("to_left");
        $scope.job.jobname="";
        $scope.job.github="";
        $scope.job.url="";
        $scope.job.build_on_change=false;
    };
    
});

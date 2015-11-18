app.factory('jenkinsService',['$http', function($http){
        
        var trelloFullPathName = window.location.pathname;
        var trelloFullPathNameArray = trelloFullPathName.split("/");
        var trelloPortalName = trelloFullPathNameArray[1];
        var trelloHost = window.location.protocol + "//" + window.location.host + "/" + trelloPortalName;
        var urlBase=trelloHost;
        
        var jsoninfo_action = "/do/FrontEnd/jpjenkins/Jobs/jsoninfo.action";
        var jsonlogs_action = "/do/FrontEnd/jpjenkins/Jobs/jsonlogs.action";
        var newjob_action = "/do/FrontEnd/jpjenkins/Jobs/newjob.action";
        var build_action = "/do/FrontEnd/jpjenkins/Jobs/build.action";
        
        return{
            getInfo: function(){
                console.log("GET info : " + urlBase + jsoninfo_action);
                return $http.get(urlBase + jsoninfo_action);
            },
            getLogsJob: function(idJob){
                console.log("GET logs : " + urlBase+ jsonlogs_action + '?job=' + idJob);
                return $http.get(urlBase + jsonlogs_action + '?job=' + idJob);
            },
            newJob: function(query){
                console.log("NEW job : " + urlBase+ newjob_action + query);
                return $http.get(urlBase + newjob_action + query);
            },
            build: function(jobname){
                console.log("NEW job : " + urlBase+ build_action + '?jobname=' + jobname);
                return $http.get(urlBase + build_action + '?jobname=' + jobname);
            }
        };
        
    }]);
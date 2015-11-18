app.factory('trelloService',['$http', function($http){
        
        var trelloFullPathName = window.location.pathname;
        var trelloFullPathNameArray = trelloFullPathName.split("/");
        var trelloPortalName = trelloFullPathNameArray[1];
        var trelloHost = window.location.protocol + "//" + window.location.host + "/" + trelloPortalName;
        var urlBase=trelloHost;
        
        var jsonboards_action = "/do/FrontEnd/jptrello/Boards/jsonboards.action";
        var jsoncards_action = "/do/FrontEnd/jptrello/Boards/jsoncards.action";
        var jsonmembers_action = "/do/FrontEnd/jptrello/Boards/jsonmembers.action";
        var jsoncardmember_action = "/do/FrontEnd/jptrello/Boards/jsoncardmember.action";
        var addcard_action = "/do/FrontEnd/jptrello/Boards/addcard.action";
        var updatecard_action = "/do/FrontEnd/jptrello/Boards/updatecard.action";
        var jsonlist_action = "/do/FrontEnd/jptrello/Boards/jsonlist.action";
        var deletecard_action = "/do/FrontEnd/jptrello/Boards/deletecard.action";
        
        return{
            getBoards: function(){
                console.log("GET boards: " + urlBase+jsonboards_action);
                return $http.get(urlBase + jsonboards_action);
            },
            getBoardCards: function(idBoard){
                console.log("GET vards: " + urlBase+ jsoncards_action + '?idBoard=' + idBoard);
                return $http.get(urlBase + jsoncards_action + '?idBoard=' + idBoard);
            },
            getMembersBoard: function(idBoard){
                console.log("GET members: " + urlBase+ jsonmembers_action + '?idBoard=' + idBoard);
                return $http.get(urlBase + jsonmembers_action + '?idBoard=' + idBoard);
            },
            getList: function(idBoard){
                console.log("GET lists: " + urlBase + jsonlist_action + '?idBoard=' + idBoard);
                return $http.get(urlBase + jsonlist_action + '?idBoard=' + idBoard);
            },
            addCard: function(query){
                console.log("POST card: " + urlBase + addcard_action + query);
                return $http.get(urlBase + addcard_action + query);
            },
            getCard: function(cardId){
                console.log("GET card: " + urlBase + jsoncardmember_action + '?idCard=' + cardId);
                return $http.get(urlBase + jsoncardmember_action + '?idCard=' + cardId);
            },
            updateCard: function(query){
                console.log("PUT card: " + urlBase + updatecard_action + query);
                return $http.put(urlBase + updatecard_action + query);
            },
            deleteCard: function(cardId){
                console.log("DELETE card: " + urlBase + deletecard_action + '?idCard=' + cardId);
                return $http.get(urlBase + deletecard_action + '?idCard=' + cardId);
            }
        };
        
    }]);
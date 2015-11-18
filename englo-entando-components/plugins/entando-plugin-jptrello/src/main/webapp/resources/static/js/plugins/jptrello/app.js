/*A.Piras*/

'use strict';


var portalFullpath = window.location.pathname;
var portalFullpathArray = portalFullpath.split("/");
var portalName = portalFullpathArray[1];

var portalHost = window.location.protocol + "//" + window.location.host + "/" + portalName;
//console.log(portalHost);
//http://localhost:8080/basecamp

var app = angular.module('trello', ['ui']);

app.controller('BoardsController', function($scope, $http, $window, trelloService) {
    
    $scope.isLoading=false;
    
    $scope.boards = [];
    $scope.lists = [];
    $scope.card = [];
    $scope.members = [];
    $scope.membersboard = [];
    $scope.temp_member = [];
    $scope.dataLoaded = false;
    $scope.cardUpdate={};
    
    $scope.idBoard = 0;
    $scope.board = 0;
    
    $scope.isBoards = false;
    $scope.isCards = false;
    $scope.isCardDetails = false;
    $scope.isCreateCard = false;

    trelloService.getBoards().success(function(data) {
        var json = JSON.parse(JSON.stringify(data));
        console.log(json);
        $scope.boards = json;
        $scope.setView('boards');
    }).error(function(data, status, headers, config) {
        console.log("error getting boards");
    });
    
    $scope.initializeBoard = function(board){
        $scope.loadList(board.id);
        $scope.loadCards(board);
    };
    
    
    $scope.loadCards = function(board){
        $scope.isLoading=true;
        $scope.idBoard = board.id;
        $scope.board=board;
        
        trelloService.getBoardCards($scope.idBoard).success(function(data) {
            var data_obj = JSON.parse(JSON.stringify(data));
            console.log(data_obj);
            $scope.lists = data_obj;
            $scope.setView('cards');
            $scope.dataLoaded = true;
            $scope.isLoading=false;   
            
        }).error(function(data, status, headers, config) {
            console.log("error getting cards");
        });
        
        trelloService.getMembersBoard($scope.idBoard).success(function(data) {
            var data_obj = JSON.parse(JSON.stringify(data));
            console.log(data_obj);
            $scope.membersboard = data_obj;
            $scope.setView('cards');
        }).error(function(data, status, headers, config) {
            console.log("error getting members");
        });
        
    };
    
    $scope.loadCard = function(cardId, card, list){
        trelloService.getCard(cardId).success(function(data){
            var data_obj = JSON.parse(JSON.stringify(data));
            console.log(data_obj);

            $scope.cardUpdate.list=list;
            $scope.cardUpdate.card=card;
            $scope.cardUpdate.name=card.name;
            $scope.cardUpdate.desc=card.desc;
            $scope.cardUpdate.currentListId=list.id;
            $scope.cardUpdate.members=data_obj;
            
            $scope.setView('cardDetails');
            //removes "card_new" panel, and shows "card_detail" panel
            $("#card_new").removeClass("to_left");
            $("#card_detail").toggleClass("to_left");
        });
    };
    
    
    $scope.updateCard = function(){
        $scope.isLoading=true;
        
        var query = '?idCard='+ $scope.cardUpdate.card.id+
            '&idList='+$scope.cardUpdate.currentListId+
            '&name='+$scope.cardUpdate.name+
            '&desc='+$scope.cardUpdate.desc;
        
        console.log(query);
        
        trelloService.updateCard(query).success(function(data){
            
            $scope.loadCards($scope.board);
            //                $.msgGrowl({
            //                    type: 'success',
            //                    title: "Update",
            //                    text: "success"
            //
            //                });
            console.log("updating card: success");
        }).error(function () {
            $.msgGrowl({
                type: 'error',
                title: "Update",
                text: "error"
            });
            console.log("error updating card");
            $scope.isLoading=false;
        });
    };
    
    
    
    $scope.moveCard = function(idCard, newListId){
        
        var query = '?idCard='+idCard+
            '&idList='+newListId;
        console.log(query);
        trelloService.updateCard(query).success(function(data){
            $scope.loadCards($scope.board);
            //            $.msgGrowl({
            //                type: 'success',
            //                title: "Update",
            //                text: "success"
            //            });
            console.log("updating card: success");
        }).error(function () {
            $scope.loadCards($scope.board);
            $.msgGrowl({
                type: 'error',
                title: "Update",
                text: "error"
            });
            console.log("error updating card");
        });
    };
    
          
    $scope.loadList=function(idBoard){
        trelloService.getList(idBoard).success(function(data) {
            var data_obj = JSON.parse(JSON.stringify(data));
            console.log(data_obj);
            $scope.listsAvailables = data_obj.list;
        }).error(function(data, status, headers, config) {
            console.log("error getting cards");
        });
        
    };
       
          
    $scope.createCard = function(idList, list){
        $scope.list=list; 
        $scope.card.name = "";
        $scope.card.desc = "";
        $scope.card.idList = idList;
        $scope.card.idMembers = "";
        $scope.card.members = [];
        $scope.setView('createCard');
        //removes "card_detail" panel, and shows "card_new" panel
        $("#card_detail").removeClass("to_left");
        $("#card_new").toggleClass("to_left");
    };
    
    
    //    temp action
    //    http://localhost:8080/basecamp/do/FrontEnd/jptrello/Boards/addcard.action?name=2222222222&desc=hhhhhhhhhhh&idList=5605609bdb27cec737e61dce&idMembers=542196b8b7903c29ececd496&
    
    $scope.addCard = function(){
        
        var query = '?name='+$scope.card.name+
            '&desc='+$scope.card.desc+ 
            '&idList='+$scope.card.idList+ 
            '&idMembers='+$scope.card.idMembers+
            '&members='+$scope.card.members;
        trelloService.addCard(query).success(function(data){
            $scope.loadCards($scope.board);
            //                $.msgGrowl({
            //                type: 'success',
            //                title: "Create",
            //                text: "success"
            //                });
            console.log("adding card: success");
        }).error(function () {
            console.log("error creating card");
            $.msgGrowl({
                type: 'error',
                title: "Create",
                text: "error"
            });
        });
    };
    
    $scope.addMember = function(){
        if(!$scope.containsObject($scope.temp_member, $scope.card.members)){
            if($scope.temp_member!=""){
                $scope.card.members.push($scope.temp_member);
                var membList = $scope.card.members;
                if(membList.length === 1){
                    $scope.card.idMembers += $scope.temp_member.id;
                }else{
                    $scope.card.idMembers += ","+$scope.temp_member.id;
                }
            }
        }
    };
    
    
    
    $scope.setView = function(view){
        if(view === 'boards'){
            $scope.isBoards = true;
            $scope.isCards = false;
            $scope.isCardDetails = false;
            $scope.isCreateCard = false;
        }else if(view === 'cards'){
            $scope.isBoards = false;
            $scope.isCards = true;
            $scope.isCardDetails = false;
            $scope.isCreateCard = false;
            
            //when all cards are loaded, all left panels desappear
            $("#card_detail").removeClass("to_left");
            $("#card_new").removeClass("to_left");
            
        }else if(view === 'cardDetails'){
            $scope.isBoards = false;
            $scope.isCards = true;
            $scope.isCardDetails = true;
            $scope.isCreateCard = false;
            
        }else if(view === 'createCard'){
            $scope.isBoards = false;
            $scope.isCards = true;
            $scope.isCardDetails = false;
            $scope.isCreateCard = true;
        }
    };
    
    $scope.containsObject = function(obj, list) {
        var i;
        for (i = 0; i < list.length; i++) {
            if (list[i] === obj) {
                return true;
            }
        }
        
        return false;
    };
    
    $scope.empty = function() {
        $scope.lists = [];
        $scope.dataLoaded = false;
        $scope.setView('boards');
    };
    
    
    $scope.sortableOptions = {
        update: function(e, ui) {},
        receive: function( event, ui ) {
            //console.log(event);
            //console.log(ui);
            var listaId=this.id;
            var cardId=ui.item[0].id;
            //alert("update verso colonna :"+listaId+"\n elemento id: "+cardId);
            $scope.moveCard(cardId,listaId);
        },
        //        cursor: "url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAMAAAC6V+0/AAAA3lBMVEUAAAAnk8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8J5sPN9AAAASXRSTlMAAQIDBAUGBwgODxESFBUZGxwfIywuMTQ2ODk7PD1AQ0dQVFVWWVxfYWNka21zdYOGjo+XmqWmq7Cyt7nAwcPI0dri6O3z9/n718TKMwAAALxJREFUGBl1wddWwkAARdEzKYJYKCo2jJSAvYuiqKCGCff/fwgWEBwf2JvVPD9s3zc2ouOQjPcgpfZ9LCnZLRpmbtU5sZuUPv2SpO8CkI+SAdwA5zAs5HpfsDWSelADahDBgXxeP4L1Lo6qQmwbLnFUFaIYznDsK0AxHOFoCX6u+a/bhytbxGXSDpg3W8axrQrgvaQV/tTlMWWexzssPfWZMY82IPN7wZyvQxbyili401KyRmavGc+d5lhhAtNfGTQkXlpjAAAAAElFTkSuQmCC'), auto",
        cursor: "url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAMAAAC6V+0/AAAA3lBMVEUAAAAnk8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8Ink8J5sPN9AAAASXRSTlMAAQIDBAUGBwgODxESFBUZGxwfIywuMTQ2ODk7PD1AQ0dQVFVWWVxfYWNka21zdYOGjo+XmqWmq7Cyt7nAwcPI0dri6O3z9/n718TKMwAAALxJREFUGBl1wddWwkAARdEzKYJYKCo2jJSAvYuiqKCGCff/fwgWEBwf2JvVPD9s3zc2ouOQjPcgpfZ9LCnZLRpmbtU5sZuUPv2SpO8CkI+SAdwA5zAs5HpfsDWSelADahDBgXxeP4L1Lo6qQmwbLnFUFaIYznDsK0AxHOFoCX6u+a/bhytbxGXSDpg3W8axrQrgvaQV/tTlMWWexzssPfWZMY82IPN7wZyvQxbyili401KyRmavGc+d5lhhAtNfGTQkXlpjAAAAAElFTkSuQmCC'), pointer",
        connectWith: ".trello_List"
    };
    
    $scope.deleteCard= function(cardId){
        
        trelloService.deleteCard(cardId)
            .success(function(data){
                $scope.loadCards($scope.board);
            console.log("deleting card: success");
        }).error(function () {
            console.log("error deleting card");
            $.msgGrowl({
                type: 'error',
                title: "Create",
                text: "error"
            });
        });
    };
    
});


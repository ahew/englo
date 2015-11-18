<%@ taglib prefix="wp" uri="/aps-core" %>
<%@ taglib prefix="jptrello" uri="/jptrello-aps-core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<wp:headInfo type="JS" info="entando-misc-jquery/jquery-1.10.0.min.js" />
<wp:headInfo type="JS" info="entando-misc-bootstrap/bootstrap.min.js" />
<script src="<wp:resourceURL />/static/js/plugins/jptrello/angular.min.js"></script>
<script src="<wp:resourceURL />/static/js/plugins/jptrello/app.js"></script>
<script src="<wp:resourceURL />/static/js/plugins/jptrello/services.js"></script>

<link rel="stylesheet" type="text/css" href="<wp:cssURL />/plugins/jptrello/trello.css" />
<link rel="stylesheet" href="<wp:cssURL />html5reset-1.6.1.css" media="screen" />
<link rel="stylesheet" href="<wp:cssURL />master.css" media="screen" />
<link rel="stylesheet" href="<wp:cssURL />IE_fix.css" media="screen" />
<link rel="stylesheet" href="<wp:cssURL />jquery-ui.css" media="screen" />


<!--<link href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css" rel="stylesheet">-->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">

<script type="text/javascript" src="<wp:resourceURL />static/js/jquery-1.10.2.js" ></script>
<script type="text/javascript" src="<wp:resourceURL />static/js/jquery-ui.js" ></script>
<script type="text/javascript" src="<wp:resourceURL />static/js/modernizr.custom.55405.js" ></script>
<script type="text/javascript" src="<wp:resourceURL />static/js/jquery.placeholder.js" ></script>
<script type="text/javascript" src="<wp:resourceURL />static/js/snap.svg-min.js" ></script>

<script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/angular-ui/0.4.0/angular-ui.min.js" ></script>


<link href="<wp:resourceURL />static/msgGrowl/css/msgGrowl.css" rel="stylesheet">
<script src="<wp:resourceURL />static/msgGrowl/js/msgGrowl.min.js"></script>

<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<link href="<wp:resourceURL />static/css/bootstrap-modal.css" rel="stylesheet">
    
<link rel="stylesheet" href="/path/to/font-awesome.min.css" type="text/css">
<script src="/path/to/jquery.min.js" type="text/javascript"></script>
<script src="/path/to/jquery.awesome-cursor.min.js" type="text/javascript"></script>

<div ng-app="trello" class="trelloBoard">
   <!--<h1>Trello Connector Bioparco2</h1>-->
   <div class="window-module" ng-controller="BoardsController">
      <div ng-show="isBoards">
         <section class=" blue_block row-fluid" ng-show="boards">

            <div class="blocks shape trans col-md-2" ng-repeat="board in boards track by $index" ng-if="!board.closed" >
                <a  class="truncate" href="#" ng-click="initializeBoard(board)" >  
                    <span class="number"></span>{{ board.name}}</a> > </i>
            </div>
                
         </section>
      </div>
      <div ng-show="isCards" >

         <!--<h2 class="titleTrello">{{board.name}}</h2>-->

         <div class="board-wrapper" >

            <div class="bubble trans">
               <strong>Member on the board</strong>
               <ol ng-repeat="member in membersboard">{{member.fullName}} (@{{member.username}})</ol>
            </div>


<!--            <div ng-hide="dataLoaded">
                <i class="fa fa-refresh fa-spin"></i>
            </div>-->

            <div ng-show="dataLoaded">
            <section class="list col_block" ng-repeat="list in lists track by $index" ng-show="dataLoaded">
                
               <div class="colonna list-header js-list-header non-empty u-clearfix" attr="name">
                  <h3 class="list-header-name hide-on-edit js-list-name current">{{list.name}}</h3>
                  <ul id="{{list.id}}" ui:sortable="sortableOptions"  class="trello_List" style="margin-bottom: .7em">
                     <li id="{{card.id}}" class="ui-state-default" class="lista card_item" ng-repeat="card in list.cards | orderBy: 'name'"  >{{card.name}} 
<!--                         <div ng-if="card.desc" title="This card has a description." class="badge badgeCustom badge-state-image-only">
                           <i class="icon-pencil"></i>
                        </div>-->

                        <!--<div class="badge badgeCustom badge-state-image-only">-->
                        <a href="javascript:void(0)" title="Details" ng-click="loadCard(card.id, card, list)"><i  class="fa fa-pencil card_detail"></i></a>
                       
                        <div ng-if="card.idMembers.length !== 0" title="This card has members." class="badge badgeCustom badge-state-image-only">
                            <span class="badge-icon icon-sm fa fa-user"></span>&nbsp;&#43;{{card.idMembers.length}}
                        </div>
                     </li>
                  </ul>
                  <a class="openCard"href="#" class="open-card-composer js-open-card-composer card_new" ng-click="createCard(list.id, list)" >Add a card...</a>

               </div>
            </section>
<!--                <div>
                   <button class="btnBack" href="#" ng-click="empty()" title="Back"><span class="fa fa-arrow-left icon-1x"></span></button>
                </div>-->
            </div>
         </div>
      </div>


      <aside class="col_right trans " id="card_detail">
         <form class="trans"  ng-submit="updateCard()" ng-show="isCardDetails" style="width: 300px; margin: 0 auto;">

            <h3>Card Details</h3>
            <hr class="separator_content">
            <p>

            </p>
            <label>Name</label>
            <input type="text" required ng-model="cardUpdate.name"/>
            <label>Description</label>
            <input type="text" required ng-model="cardUpdate.desc" />
            <label>List</label>
            <select name="listSelect" id="listSelect" class="editList" ng-model="cardUpdate.currentListId">
                <option ng-repeat="option in listsAvailables" value="{{option.id}}">{{option.name}}</option>
            </select>
        
            <!--<input type="text" disabled ng-value="cardUpdate.list.name"-->
            <input type="hidden" disabled ng-value="cardUpdate.card.idList" />
            
            <div class="board-header u-clearfix js-board-header">
                <h3>Members&nbsp;&nbsp;<span class="badge-icon icon-sm fa fa-users"></span></h3>
               <hr class="separator_content">
               <div ng-if="cardUpdate.members.length == 0" >
                  <label>There aren't members on this card.</label>
               </div>
               <div ng-repeat="member in cardUpdate.members" style="width: 40%;">

                  <label ng-if="!member.avatarHash" class="creator member js-show-mem-menu">
                     <span class="member-initials ng-binding badge badgeCustom badge-state-image-only"  title="{{member.fullName}} (@{{member.username}})">{{member.initials}}</span> 
                  </label>
                  <label ng-if="member.avatarHash" class="creator member js-show-mem-menu">
                     <img  title="{{member.fullName}} (@{{member.username}})" src="https://trello-avatars.s3.amazonaws.com/{{member.avatarHash}}/50.png">
                  </label>
               </div>
            </div>
            <div class="buttons_container">
                <button class="entando_button button_medium create" title="Update" type="submit" class="btn btn-success">Update</button>
                <button class="entando_button button_medium " data-toggle="modal" type="button" data-target="#confirmDelete" title="Delete" >Delete</button>
                <button class="new_card button_medium entando_button back create" title="Back"  type="button" href="#" ng-click="setView('cards')" >Back&nbsp;<span title="Back" class="fa fa-arrow-left icon-1x"></span></button>
         </div>
         </form>
      </aside>            

      <aside class="col_right trans " id="card_new">
         <form class="trans" ng-show="isCreateCard"  ng-submit="addCard()" >
            <!--<h1>{{board.name}}</h1>-->
            <h3>Create a New Card</h3>
            <hr>
            <label>Name</label>
            <input type="text" required ng-model="card.name"/>
            <label>Description</label>
            <input type="text" required ng-model="card.desc" />
            <label>List</label>
            <input disabled type="hidden" ng-model="card.idList" />
            <input disabled type="text" ng-model="list.name">
            <!--<div><h3>idMembers</h3></div>-->
            <input type="hidden" ng-model="card.idList" />
            <label>Member</label>
            <ol ng-repeat="member in card.members">
               <p class="memberList">{{member.fullName}} (@{{member.username}})</p>
            </ol>
            <label>Choose Members</label >
            <select ng-model="temp_member" class="editList" ng-options="member.fullName for member in membersboard"></select>
                <button class="addons" href="#"  type="button" ng-click="addMember()">&#43; Add</button>
            <div class="buttons_container">
               <button class="new_card entando_button create" title="Create" type="submit" class="btn btn-success">Create</button>
               <button class="new_card entando_button back create" title="Back"  type="button" href="#" ng-click="setView('cards')" >Back&nbsp;<span title="Back" class="fa fa-arrow-left icon-1x"></span></button>
            </div>
         </form>
      </aside>   

       <div class="loading_overlay" ng-if="isLoading">
           <div class="loading_container">
               <i class="fa fa-spinner fa-pulse fa-4x"></i>
           </div>
       </div>
       
        <div class="modal fade" id="confirmDelete" role="dialog">
           <div class="modal-dialog">
               <div class="modal-content">
                   <div class="modal-header">
                       <button type="button" class="close" data-dismiss="modal">&times;</button>
                       <h4 class="modal-title">Confirm Delete</h4>
                   </div>
                   <div class="modal-body">
                       <p>are you sure you want delete this card?</p>
                   </div>
                   <div class="modal-footer">
                       <button type="button" class="btn btn-default" data-dismiss="modal">Back</button>
                       <button type="button" class="btn btn-default" ng-click="deleteCard(cardUpdate.card.id)"  data-dismiss="modal">Delete</button>
                   </div>
               </div>
                   
           </div>
       </div> 
       
   </div>
</div>


<style>
    /*DO not remove this class*/
    .trello_List{
        min-height: 45px;
    }
/*    
    .trello_List li{
     cursor: url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAMAAAC6V+0/AAAA3lBMVEUAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACpNbQVAAAASXRSTlMAAQIDBAUGBwgODxESFBUZGxwfIywuMTQ2ODk7PD1AQ0dQVFVWWVxfYWNka21zdYOGjo+XmqWmq7Cyt7nAwcPI0dri6O3z9/n718TKMwAAALxJREFUGBl1wddWwkAARdEzKYJYKCo2jJSAvYuiqKCGCff/fwgWEBwf2JvVPD9s3zc2ouOQjPcgpfZ9LCnZLRpmbtU5sZuUPv2SpO8CkI+SAdwA5zAs5HpfsDWSelADahDBgXxeP4L1Lo6qQmwbLnFUFaIYznDsK0AxHOFoCX6u+a/bhytbxGXSDpg3W8axrQrgvaQV/tTlMWWexzssPfWZMY82IPN7wZyvQxbyili401KyRmavGc+d5lhhAtNfGTQkXlpjAAAAAElFTkSuQmCC'), auto;
    }
     .trello_List li:hover{
     cursor: url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAMAAAC6V+0/AAAA3lBMVEUAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACpNbQVAAAASXRSTlMAAQIDBAUGBwgODxESFBUZGxwfIywuMTQ2ODk7PD1AQ0dQVFVWWVxfYWNka21zdYOGjo+XmqWmq7Cyt7nAwcPI0dri6O3z9/n718TKMwAAALxJREFUGBl1wddWwkAARdEzKYJYKCo2jJSAvYuiqKCGCff/fwgWEBwf2JvVPD9s3zc2ouOQjPcgpfZ9LCnZLRpmbtU5sZuUPv2SpO8CkI+SAdwA5zAs5HpfsDWSelADahDBgXxeP4L1Lo6qQmwbLnFUFaIYznDsK0AxHOFoCX6u+a/bhytbxGXSDpg3W8axrQrgvaQV/tTlMWWexzssPfWZMY82IPN7wZyvQxbyili401KyRmavGc+d5lhhAtNfGTQkXlpjAAAAAElFTkSuQmCC'), auto;
    }*/
    
    section.col_block .colonna ul li{
        border-radius: 0.5em;
        margin: 0.7em 0;
    }
    
    section.col_block .colonna ul{
        padding-right: 0px !important;
    }
    
    
    .card_detail:hover {
        float: right;
        width: 2em;
        height: 2em;
        padding-top: 0.8em;
        cursor: pointer;
        color: #3BB8EF;
        font-size: 1.4em !important;
        padding-top: 0.8em;
        padding-left: 1em;
        padding-right: 1.5em;
        padding-bottom: 2em;
    }
    .card_detail {
        float: right;
        cursor: pointer;
        transition: all 0.2s ease;
        color: #2793C2;
        font-size: 1.2em !important;
        width: 2em;
        height: 2em;
        padding-top: 1em;
        padding-left: 1em;
        padding-right: 1.5em;
        padding-bottom: 2em;
    }
        
    .separator_content{
        margin: 0 !important;
    }
    
    .loading_overlay{
        position: fixed;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
        z-index: 1040;
        background-color: rgba(0, 0, 0, 0.52);
    }
    
    .loading_container{
        position: absolute;
        left: 50%;
        top: 50%;
        color: #2793C2;
    }
    
    .button_medium{
        padding: 0px 1.8em !important;
    }
    .buttons_container{
        margin-top: 2em;
    }
    
    .btn-primary {
    color: #ffffff;
    background-color: #2793C2;
    border-color: #2793C2;
    }
    
   .editList{
        background-color: rgb(255, 255, 255);
        box-shadow: 0px 0px 7px 0px rgba(0, 0, 0, 0.14),inset 0px 3px 7px 0px rgba(0, 0, 0, 0.35);
        position: relative;
        width: 76% !important;
        height: 31px;
        border-radius: 5px;
        font-size: 0.8em;
    }
</style>
  
    
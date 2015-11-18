<%@ taglib prefix="wp" uri="/aps-core"%>
<!DOCTYPE HTML>
<html lang="<wp:info key="currentLang" />">
   <head>
      <meta charset="UTF-8" />
      <meta name="viewport" content="initial-scale=1,maximum-scale=1,minimum-scale=1 user-scalable=no" />
      <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" />
      <meta name="apple-mobile-web-app-capable" content="yes" />
      <meta name="apple-mobile-web-app-status-bar-style" content="default" />

      <title>Entando - <wp:currentPage param="title" /></title>

      <link rel="stylesheet" href="<wp:cssURL />html5reset-1.6.1.css" media="screen" />
      <link rel="stylesheet" href="<wp:cssURL />master.css" media="screen" />
      <link rel="stylesheet" href="<wp:cssURL />IE_fix.css" media="screen" />
      <link rel="stylesheet" href="<wp:cssURL />jquery-ui.css" media="screen" />

      <script type="text/javascript" src="<wp:resourceURL />static/js/jquery-1.10.2.js" ></script>
      <script type="text/javascript" src="<wp:resourceURL />static/js/jquery-ui.js" ></script>
      <script type="text/javascript" src="<wp:resourceURL />static/js/modernizr.custom.55405.js" ></script>
      <script type="text/javascript" src="<wp:resourceURL />static/js/jquery.placeholder.js" ></script>
      <script type="text/javascript" src="<wp:resourceURL />static/js/snap.svg-min.js" ></script>

      <!-- Latest compiled and minified CSS -->
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
      <!-- Optional theme -->
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
      <!-- Latest compiled and minified JavaScript -->
      <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
      <link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.no-icons.min.css" rel="stylesheet">
      <link href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css" rel="stylesheet">
      <wp:outputHeadInfo type="JS_VARS">
         <script>
         <!--//--><![CDATA[//><!--
            <wp:printHeadInfo />
            //--><!]]>
         </script>
      </wp:outputHeadInfo>

      <%-- js scripts (remember to include the libraries first) --%>
      <wp:outputHeadInfo type="JS">
         <script src="<wp:resourceURL />static/js/<wp:printHeadInfo />"></script>


      </wp:outputHeadInfo>

      <%-- js code --%>
      <wp:outputHeadInfo type="JS_RAW">
         <script>
         <!--//--><![CDATA[//><!--
            <wp:printHeadInfo />
            //--><!]]>
         </script>
      </wp:outputHeadInfo>
      <script>
         'article aside footer header figure nav main address section time'.replace(/\w+/g, function (n) {
            document.createElement(n)
         })
      </script>
      <script>
         $(function () {
            $("#accordion").accordion({
               heightStyle: "content",
            });
         });
      </script>

      <script type="text/javascript" src="<wp:resourceURL />static/js/main.js" ></script>


      <script>
         var url = '<wp:resourceURL />static/images/icons.svg';
         var c = new XMLHttpRequest();
         c.open('GET', url, false);
         c.setRequestHeader('Content-Type', 'text/xml');
         c.send();
         document.body.insertBefore(c.responseXML.firstChild, document.body.firstChild)
      </script>

      <script type="text/javascript" src="<wp:resourceURL />static/js/jquery.placeholder.js" ></script>

      <script>
         $(function () {
            $("#accordion").accordion({
               heightStyle: "content",
            });
         });
      </script>
   </head>
   <body>
      <div id="flexcanvas" class="flexChild rowParent">
         <div id="container" class="flexChild rowParent">

            <header id="rowChild49648" class="flexChild columnParent">


               <figure id="columnChild2835" class="flexChild logo">
                  <a href="index.html" title="entando" class="prin"> 
                     <span class="helper"></span>  

                     <!--[if !IE]>--><img class="hires" alt="entando" src="<wp:resourceURL/>/static/img/assets/Entando.png"  width="133" height="40" /><!--<![endif]-->
                  </a>
               </figure>

               <div id="columnChild29189" class="flexChild">
                  <div id="accordion">

                     <h3 onclick="location.href = '<wp:url page="home_englo"/>';" class="non"><div class="icon_prod iconsvg ie_home_svg1">
                           <svg viewBox="0 0 30 30" enable-background="new 0 0 30 30">
                           <g filter="">
                           <use xlink:href="#home"></use>
                           </g>
                           </svg>
                        </div>    Home </h3>
                     <div class="non">
                     </div>
                     <h3 onclick="location.href = '<wp:url page="basecamp"/>';">Basecamp</h3>
                     <div class="non">
                     </div>
                     <h3 onclick="location.href = '<wp:url page="trello"/>';">Trello</h3>
                     <div class="non">
                     </div>
                     <h3 onclick="location.href = '<wp:url page="jenkins"/>';">Jenkins</h3>
                     <div class="non">
                     </div>
                     <h3 onclick="location.href = '<wp:url page="github"/>';">github</h3>
                     <div class="non">
                     </div>
                  </div>
               </div>
            </header> 

            <section id="rowChild59789" class="flexChild columnParent">
               <div id="columnChild69564" class="flexChild">

                  <strong class="msg">
                     <wp:i18n key="welcome" /> &nbsp;<wp:currentPage param="title" />
                  </strong>
               </div>


               <main id="columnChild29187" class="flexChild">

                  <div class="dual_boot itemgroups">
                     <div class="item dual_item_container active_dual_item" id="poster_3">
                        <div class="dual_item">
                           <div class="dual_item_layer shadow"></div>
                           <div class="dual_item_wrapper"><a href='<wp:url page="basecamp"/>'>
                                 <div class="dual_item_layer layer_2">
                                    <!--<img class="hires" alt="entando" src="<wp:resourceURL/>/static/img/basecamp.png"  width="1" height="40" />-->

                                 </div>

                                 <div class="dual_item_layer layer_4 trans"><span> Basecamp </span></div>
                                 <div class="dual_item_layer shader"></div>
                              </a>
                           </div>
                        </div>
                     </div>
                     <div class="item dual_item_container active_dual_item" id="poster_3">
                        <div class="dual_item">
                           <div class="dual_item_layer shadow"></div>
                           <div class="dual_item_wrapper"><a href='<wp:url page="trello"/>'>
                                 <div class="dual_item_layer layer_2">
                                    <div class="icon_prod iconsvg ie_home_svg1">
                                       <svg viewBox="0 0 30 30" enable-background="new 0 0 30 30">
                                       <g filter="">
                                       <use xlink:href='<wp:url page="trello"/>'></use>
                                       </g>
                                       </svg>
                                    </div> 
                                 </div>

                                 <div class="dual_item_layer layer_4 trans"><span> Trello </span></div>
                                 <div class="dual_item_layer shader"></div>
                              </a>
                           </div>
                        </div>
                     </div>

                     <div class="item dual_item_container active_dual_item" id="poster_3">
                        <div class="dual_item">
                           <div class="dual_item_layer shadow"></div>
                           <div class="dual_item_wrapper"><a href='<wp:url page="github"/>'>
                                 <div class="dual_item_layer layer_2">
                                    <div class="icon_prod iconsvg ie_home_svg1">
                                       <svg viewBox="0 0 30 30" enable-background="new 0 0 30 30">
                                       <g filter="">
                                       <use xlink:href='<wp:url page="github"/>'></use>
                                       </g>
                                       </svg>
                                    </div> 
                                 </div>
                                 <div class="dual_item_layer layer_4 trans"><span> Github </span></div>
                                 <div class="dual_item_layer shader"></div>
                              </a>
                           </div>
                        </div>
                     </div>
                     <div class="item dual_item_container active_dual_item" id="poster_3">
                        <div class="dual_item">
                           <div class="dual_item_layer shadow"></div>
                           <div class="dual_item_wrapper"><a href='<wp:url page="jenkins"/>'>
                                 <div class="dual_item_layer layer_2">
                                    <div class="icon_prod iconsvg ie_home_svg1">
                                       <svg viewBox="0 0 30 30" enable-background="new 0 0 30 30">
                                       <g filter="">
                                       <use xlink:href='<wp:url page="jenkins"/>'></use>
                                       </g>
                                       </svg>
                                    </div> 
                                 </div>
                                 <div class="dual_item_layer layer_4 trans"><span> Jenkins </span></div>
                                 <div class="dual_item_layer shader"></div>
                              </a>
                           </div>
                        </div>
                     </div>
                     <div class="stretch"></div>
                  </div>

               </main>
            </section>

            <section id="rowChild81190" class="flexChild columnParent">
               <div id="columnChild94361" class="flexChild">
                  <wp:show frame="0" />              
               </div>

               <div id="columnChild13600" class="flexChild"></div>
            </section>

            <footer>
               Powered by <a href="http://www.entando.com/portal/" target="_blank"> Entando </a>  -   Simplifyng Enterprise Portals
            </footer>
         </div>

      </div><!-- #container -->


      <wp:show frame="1" />
      <wp:show frame="2" />
      <wp:show frame="3" />
      <wp:show frame="4" />
      <wp:show frame="5" />
      <wp:show frame="6" />
      <wp:show frame="7" />
      <wp:show frame="8" />
      <wp:show frame="9" />
      <wp:show frame="10" />
      <wp:show frame="11" />
      <wp:show frame="12" />
      <wp:show frame="13" />

   </body>
</html>

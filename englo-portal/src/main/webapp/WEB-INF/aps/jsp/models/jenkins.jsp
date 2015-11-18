<%@ taglib prefix="wp" uri="/aps-core"%>
<!DOCTYPE HTML>
<html lang="<wp:info key="currentLang" />">
   <head>
      <meta charset="UTF-8" />
      <meta name="viewport" content="initial-scale=1,maximum-scale=1,minimum-scale=1 user-scalable=no" />
      <meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" />
      <meta name="apple-mobile-web-app-capable" content="yes" />
      <meta name="apple-mobile-web-app-status-bar-style" content="default" />

      <title>Englo : <wp:currentPage param="title" /></title>

      <link rel="stylesheet" href="<wp:cssURL />html5reset-1.6.1.css" media="screen" />
      <link rel="stylesheet" href="<wp:cssURL />servicearea.css" media="screen" />
      <link rel="stylesheet" href="<wp:cssURL />pagemodels/general.css" media="screen" />
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

      <script>
         'article aside footer header figure nav main address section time'.replace(/\w+/g, function (n) {
            document.createElement(n)
         })
      </script>
      <script>
         $(function () {
            $("#accordion").accordion({
               heightStyle: "content",
               active: 3
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
                     <h3 onclick="location.href = '<wp:url page="basecamp"/>';" class="non">Basecamp</h3>
                     <div class="non">
                     </div>
                     <h3 onclick="location.href = '<wp:url page="trello"/>';">Trello</h3>
                     <div class="non">
                     </div>
                     <h3 onclick="location.href = '<wp:url page="jenkins"/>';">Jenkins</h3>
                     <div class="englo_submenu" style="height:auto;">
                        <p class="new_job"> <wp:i18n key="add_new_job" /> </p>
                        <p class="server"> <wp:i18n key="info_server" /> </p>
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
                     <wp:i18n key="access" />&nbsp;<wp:currentPage param="title" />
                  </strong>
               </div>

               <main id="columnChild29187" class="flexChild">
                  <wp:show frame="8" />
                  <wp:show frame="9" />
                  <wp:show frame="10" />
                  <wp:show frame="11" />
                  <wp:show frame="12" />
                  <wp:show frame="13" />
                  <wp:show frame="14" />

               </main>
            </section>

            <footer>
               Powered by <a href="http://www.entando.com/portal/" target="_blank"> Entando </a>  -   Simplifyng Enterprise Portals
            </footer>
         </div>
      </div><!-- #container -->
      <wp:show frame="0" />
      <wp:show frame="1" />
      <wp:show frame="2" />
      <wp:show frame="3" />
      <wp:show frame="5" />
      <wp:show frame="6" />
      <wp:show frame="7" />
      <wp:show frame="15" />
      <wp:show frame="16" />
   </body>
</html>

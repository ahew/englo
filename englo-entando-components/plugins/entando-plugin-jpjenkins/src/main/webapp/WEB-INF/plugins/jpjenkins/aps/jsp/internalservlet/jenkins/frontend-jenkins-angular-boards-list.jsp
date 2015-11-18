<%@ taglib prefix="wp" uri="/aps-core" %>
<%@ taglib prefix="jptrello" uri="/jptrello-aps-core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<wp:headInfo type="JS" info="entando-misc-jquery/jquery-1.10.0.min.js" />
<wp:headInfo type="JS" info="entando-misc-bootstrap/bootstrap.min.js" />
<script src="<wp:resourceURL />/static/js/plugins/jpjenkins/angular.min.js"></script>
<script src="<wp:resourceURL />/static/js/plugins/jpjenkins/app.js"></script>
<script src="<wp:resourceURL />/static/js/plugins/jpjenkins/services.js"></script>

<link rel="stylesheet" type="text/css" href="<wp:cssURL />/plugins/jpjenkins/jenkins.css" />
<link rel="stylesheet" href="<wp:cssURL />html5reset-1.6.1.css" media="screen" />
<link rel="stylesheet" href="<wp:cssURL />master.css" media="screen" />
<link rel="stylesheet" href="<wp:cssURL />IE_fix.css" media="screen" />
<link rel="stylesheet" href="<wp:cssURL />jquery-ui.css" media="screen" />

<link href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css" rel="stylesheet">

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

<!--<h1>Jenkins Connector</h1>-->
<div ng-app="jenkins" class="JenkinsBoard">
   <div class="window-module" ng-controller="JobsController"  id="jenkinsController">

      <div ng-show="isJobs">
         <!--            <div>
                         <button href="#" ng-click="setView('newJob');" class="new_job" >New Job</button>
                     </div>-->
         <section class="table_block2">
            <div class="corniceGit">
               <h2 class="title">Jobs List</h2> 
               <table class="entando_table first" style="z-index: 999999;">
                  <thead>
                     <tr>
                        <th><wp:i18n key="jenkins_NAME" /></th>
                        <th><wp:i18n key="jenkins_BUILD" /></th>
                        <th><wp:i18n key="jenkins_LOG" /></th>
                     </tr>
                  </thead>

                  <tr ng-repeat="item in jobs.jobs">
                     <td>
                        <span> {{ item.name}}</span>
                     </td>
                     <td>
                        <a href="#" ng-click="build(item.name);">Build</a>
                     </td>
                     <td>
                        <a href="#" ng-click="loadLogs(item.name);">Log</a>
                     </td>
                  </tr>

               </table>
            </div>
         </section>
      </div>  


      <section class="table_block2 trans" id="server">
         <div ng-show="infoServer">
            <div class="corniceGit">
               <h2 class="title">Info</h2>
               <table class="entando_table last">
                  <thead>
                     <tr>
                        <th><wp:i18n key="jenkins_NAME" /></th>
                        <th><wp:i18n key="jenkins_VALUE" /></th>
                     </tr>
                  </thead>

                  <tr>
                     <td><span>assignedLabels</span></td><td><p>{{ jobs.assignedLabels}}</p></td>
                  </tr>
                  <tr>
                     <td><span>Mode</span></td><td><p>{{ jobs.mode}}</p></td>
                  </tr>
                  <tr>
                     <td><span>Node description</span></td><td><p>{{ jobs.nodeDescription}}</p></td>
                  </tr>
                  <tr>
                     <td><span>Node name</span></td><td><p>{{ jobs.nodeName}}</p></td>
                  </tr>
                  <tr>
                     <td><span>overallLoad</span></td><td><p>{{ jobs.overallLoad}}</p></td>
                  </tr>
                  <tr>
                     <td><span>primaryView</span></td><td><p>{{ jobs.primaryView}}</p></td>
                  </tr>
                  <tr>
                     <td><span>quietingDown</span></td><td><p>{{ jobs.quietingDown}}</p></td>
                  </tr>
                  <tr>
                     <td><span>slaveAgentPort</span></td><td><p>{{ jobs.slaveAgentPort}}</p></td>
                  </tr>
                  <tr>
                     <td><span>unlabeledLoad</span></td><td><p>{{ jobs.unlabeledLoad}}</p></td>
                  </tr>
                  <tr>
                     <td><span>useCrumbs</span></td><td><p>{{ jobs.useCrumbs}}</p></td>
                  </tr>
                  <tr>
                     <td><span>useSecurity</span></td><td><p>{{ jobs.useSecurity}}</p></td>
                  </tr>
                  <tr>
                     <td><span>views</span></td><td><p>{{ jobs.views}}</p></td>
                  </tr>
               </table>
            </div>         
         </div>
      </section> 



      <div ng-show="isLogs">

         <div>
            <button class="englo_button" href="#" ng-click="setView('jobs');" >Back</button>
         </div>


         <section class="table_block2"style="margin-top: 2em">
            <div class="corniceGit">
               <h2 class="title">{{ logs.displayName}}</h2>

               <table class="table table-bordered first">
                  <thead>
                     <tr>
                        <th><wp:i18n key="jenkins_NAME" /></th>
                        <th><wp:i18n key="jenkins_VALUE" /></th>
                     </tr>
                  </thead>

                  <tr>
                     <td><span>actions</span></td><td><p>{{ logs.actions}}</p></td>
                  </tr>
                  <tr>
                     <td><span class="grayex">description</span></td><td><p class="grayex">{{ logs.description}}</p></td>
                  </tr>
                  <tr> 
                     <td><span>displayName</span></td><td><p>{{ logs.displayName}}</p></td>
                  </tr>
                  <tr>
                     <td><span class="grayex">displayNameOrNull</span></td><td><p class="grayex">{{ logs.displayNameOrNull}}</p></td>
                  </tr>
                  <tr>
                     <td><span>name</span></td><td>{{ logs.name}}</td>
                  </tr>
                  <tr>
                     <td><span>url</span></td><td>{{ logs.url}}</td>
                  </tr>
                  <tr>
                     <td><span>buildable</span></td><td>{{ logs.buildable}}</td>
                  </tr>
                  <tr>
                     <td><span>builds</span></td><td><div ng-repeat="build in logs.builds"> Number&nbsp;{{ build.number}}&nbsp;&nbsp;<a href="{{build.url}}">&nbsp;<i class="icon-caret-right"></i>&nbsp;Build&nbsp;{{build.number}}</a></div></td>
                  </tr>
                  <tr>
                     <td><span>color</span></td><td>{{ logs.color}}</td>
                  </tr>
                  <tr>
                     <td><span>first Build</span></td><td>Number {{ logs.firstBuild.number}}&nbsp;&nbsp;<a href="{{ logs.firstBuild.url}}"><i class="icon-caret-right"></i>&nbsp;{{ logs.firstBuild.url}}</a></td>
                  </tr>
                  <tr>
                     <td><span>health Report</span></td><td><div ng-repeat="healthReport in logs.healthReport">{{ healthReport.description}}&nbsp;&nbsp;Status icon&nbsp;<img src="http://rice-group.googlecode.com/svn/com.fh/target/jenkins-for-test/images/16x16/{{healthReport.iconUrl}}">&nbsp;&nbsp;<span>Score&nbsp;:</span> {{ healthReport.score}}</div></td>
                  </tr>
                  <tr>
                     <td><span>in Queue</span></td><td>{{ logs.inQueue}}</td>
                  </tr>
                  <tr>
                     <td><span>keep Dependencies</span></td><td>{{ logs.keepDependencies}}</td>
                  </tr>
                  <tr>
                     <td><span>last Build</span></td><td><span>number:</span> {{ logs.lastBuild.number}} <span>url:</span> <a href="{{ logs.lastBuild.url}}">lastBuild</a></td>
                  </tr>
                  <tr>
                     <td><span>last Completed Build</span></td><td><span>number:</span> {{ logs.lastCompletedBuild.number}} <span>url:</span> <a href="{{ logs.lastCompleteBuild.url}}">lastCompleteBuild</a></td>
                  </tr>
                  <tr>
                     <td><span>last Failed Build</span></td><td><span>number: </span> {{ logs.lastFailedBuild.number}} <span>url:</span> <a href="{{ logs.lastFailedBuild.url}}">lastFailedBuild</a></td>
                  </tr>
                  <tr>
                     <td><span>last Stable Build</span></td><td>{{ logs.lastStableBuild}}</td>
                  </tr>
                  <tr>
                     <td><span>last Successful Build</span></td><td>{{ logs.lastSuccessfulBuild}}</td>
                  </tr>
                  <tr>
                     <td><span>last Unstable Build</span></td><td>{{ logs.lastUnstableBuild}}</td>
                  </tr>
                  <tr>
                     <td><span>last Unsuccessful Build</span></td><td>{{ logs.lastUnsuccessfulBuild}}</td>
                  </tr>
                  <tr>
                     <td><span>next Build Number</span></td><td>{{ logs.nextBuildNumber}}</td>
                  </tr>
                  <tr>
                     <td><span>property</span></td><td>{{ logs.property}}</td>
                  </tr>
                  <tr>
                     <td><span>queue Item</span></td><td>{{ logs.queueItem}}</td>
                  </tr>
                  <tr>
                     <td><span>concurrent Build</span></td><td>{{ logs.concurrentBuild}}</td>
                  </tr>
                  <tr>
                     <td><span>downstream Projects</span></td><td>{{ logs.downstreamProjects}}</td>
                  </tr>
                  <tr>
                     <td><span>scm</span></td><td>{{ logs.scm}}</td>
                  </tr>
                  <tr>
                     <td><span>upstream Projects</span></td><td>{{ logs.upstreamProjects}}</td>
                  </tr>
               </table>
            </div>
         </section>
      </div>

      <aside class="col_right trans " id="new_job">
         <div ng-show="isNewJob" style="width: 300px; margin: 0 auto;">
            <div>
               <button class="new_job entando_button"  href="#" ng-click="setView('jobs');" >Back</button>
            </div>
            <form class="trans">
               <h3>New Job</h3>
               <hr>
               <label>Name</label>
               <input type="text" ng-model="job.jobname"/>
               <label>Github Project</label>
               <input type="text" ng-model="job.github"/>
               <label>Repository URL</label>
               <input type="text" ng-model="job.url" />
               <label>Build every Github Change</label>
               <input type="checkbox" ng-model="job.build_on_change" />
               <div>
                  <button class="new_job entando_button" ng-click="saveAndBuild()">Save and Build</button>
               </div>
            </form>
         </div>
      </aside> 

   </div>
</div>



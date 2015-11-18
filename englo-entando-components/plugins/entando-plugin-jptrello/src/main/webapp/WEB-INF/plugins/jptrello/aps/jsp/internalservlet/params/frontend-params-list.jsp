<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="wp" uri="/aps-core" %>
<%@ taglib prefix="wpsa" uri="/apsadmin-core" %>

<%--CAL START --%>

<wp:info key="currentLang" var="currentLang" />

<c:set var="js_for_datepicker">
/* Italian initialisation for the jQuery UI date picker plugin. */
/* Written by Antonello Pasella (antonello.pasella@gmail.com). */
jQuery(function($){
$.datepicker.regional['it'] = {
closeText: 'Chiudi',
prevText: '&#x3c;Prec',
nextText: 'Succ&#x3e;',
currentText: 'Oggi',
monthNames: ['Gennaio','Febbraio','Marzo','Aprile','Maggio','Giugno',
'Luglio','Agosto','Settembre','Ottobre','Novembre','Dicembre'],
monthNamesShort: ['Gen','Feb','Mar','Apr','Mag','Giu',
'Lug','Ago','Set','Ott','Nov','Dic'],
dayNames: ['Domenica','Luned&#236','Marted&#236','Mercoled&#236','Gioved&#236','Venerd&#236','Sabato'],
dayNamesShort: ['Dom','Lun','Mar','Mer','Gio','Ven','Sab'],
dayNamesMin: ['Do','Lu','Ma','Me','Gi','Ve','Sa'],
weekHeader: 'Sm',
dateFormat: 'dd/mm/yy',
firstDay: 1,
isRTL: false,
showMonthAfterYear: false,
yearSuffix: ''};
});

jQuery(function($){
if (Modernizr.touch && Modernizr.inputtypes.date) {
$.each( $("input[data-isdate=true]"), function(index, item) {
item.type = 'date';
});
} else {
$.datepicker.setDefaults( $.datepicker.regional[ "<c:out value="${currentLang}" />" ] );
$("input[data-isdate=true]").datepicker({
       changeMonth: true,
       changeYear: true,
       dateFormat: "dd/mm/yy"
     });
}
});
</c:set>
<wp:headInfo type="JS" info="entando-misc-html5-essentials/modernizr-2.5.3-full.js" />
<wp:headInfo type="JS_EXT" info="http://code.jquery.com/ui/1.10.1/jquery-ui.js" />
<wp:headInfo type="CSS_EXT" info="http://code.jquery.com/ui/1.10.1/themes/base/jquery-ui.css" />
<wp:headInfo type="JS_RAW" info="${js_for_datepicker}" />

<%--CAL END --%>

<%--
optional CSS
<wp:headInfo type="CSS" info="widgets/params_list.css" />
--%>

<section class="params_list">

<h1><wp:i18n key="jptrello_PARAMS_SEARCH_PARAMS" /></h1>

<form action="<wp:action path="/ExtStr2/do/FrontEnd/jptrello/Params/search.action" />" method="post" >

  <fieldset>
		<label for="params_id"><wp:i18n key="jptrello_PARAMS_ID" /></label>
		<input type="text" name="id" id="params_id" value="<s:property value="id" />" />
		<label for="params_organization"><wp:i18n key="jptrello_PARAMS_ORGANIZATION" /></label>
		<input type="text" name="organization" id="params_organization" value="<s:property value="organization" />" />
		<label for="params_api_key"><wp:i18n key="jptrello_PARAMS_API_KEY" /></label>
		<input type="text" name="api_key" id="params_api_key" value="<s:property value="api_key" />" />
		<label for="params_api_secret"><wp:i18n key="jptrello_PARAMS_API_SECRET" /></label>
		<input type="text" name="api_secret" id="params_api_secret" value="<s:property value="api_secret" />" />
		<label for="params_token"><wp:i18n key="jptrello_PARAMS_TOKEN" /></label>
		<input type="text" name="token" id="params_token" value="<s:property value="token" />" />
  </fieldset>

  <button type="submit" class="btn btn-primary">
    <wp:i18n key="SEARCH" />
  </button>

<wpsa:subset source="paramssId" count="10" objectName="groupParams" advanced="true" offset="5">
<s:set name="group" value="#groupParams" />

<div class="margin-medium-vertical text-center">
	<s:include value="/WEB-INF/apsadmin/jsp/common/inc/pagerInfo.jsp" />
	<s:include value="/WEB-INF/apsadmin/jsp/common/inc/pager_formBlock.jsp" />
</div>

<p>
  <a href="<wp:action path="/ExtStr2/do/FrontEnd/jptrello/Params/new.action"></wp:action>" title="<wp:i18n key="NEW" />" class="btn btn-info">
    <span class="icon-plus-sign icon-white"></span>&#32;
    <wp:i18n key="NEW" />
  </a>
</p>

<table class="table table-bordered table-condensed table-striped">
<thead>
<tr>
  <th class="text-right">
    <wp:i18n key="jptrello_PARAMS_ID" />
  </th>
	<th
                 class="text-left"><wp:i18n key="jptrello_PARAMS_ORGANIZATION" /></th>
	<th
                 class="text-left"><wp:i18n key="jptrello_PARAMS_API_KEY" /></th>
	<th
                 class="text-left"><wp:i18n key="jptrello_PARAMS_API_SECRET" /></th>
	<th
                 class="text-left"><wp:i18n key="jptrello_PARAMS_TOKEN" /></th>
	<th>
    <wp:i18n key="jptrello_PARAMS_ACTIONS" />
  </th>
</tr>
</thead>
<tbody>
<s:iterator id="paramsId">
<tr>
	<s:set var="params" value="%{getParams(#paramsId)}" />
	<td class="text-right">
    <a
      href="<wp:action path="/ExtStr2/do/FrontEnd/jptrello/Params/edit.action"><wp:parameter name="id"><s:property value="#params.id" /></wp:parameter></wp:action>"
      title="<wp:i18n key="EDIT" />: <s:property value="#params.id" />"
      class="label label-info display-block">
    <s:property value="#params.id" />&#32;
    <span class="icon-edit icon-white"></span>
    </a>
  </td>
	<td
            >
    <s:property value="#params.organization" />  </td>
	<td
            >
    <s:property value="#params.api_key" />  </td>
	<td
            >
    <s:property value="#params.api_secret" />  </td>
	<td
            >
    <s:property value="#params.token" />  </td>
	<td class="text-center">
    <a
      href="<wp:action path="/ExtStr2/do/FrontEnd/jptrello/Params/trash.action"><wp:parameter name="id"><s:property value="#params.id" /></wp:parameter></wp:action>"
      title="<wp:i18n key="TRASH" />: <s:property value="#params.id" />"
      class="btn btn-warning btn-small">
      <span class="icon-trash icon-white"></span>&#32;
      <wp:i18n key="TRASH" />
    </a>
  </td>
</tr>
</s:iterator>
</tbody>
</table>

<div class="margin-medium-vertical text-center">
	<s:include value="/WEB-INF/apsadmin/jsp/common/inc/pager_formBlock.jsp" />
</div>

</wpsa:subset>

</form>
</section>
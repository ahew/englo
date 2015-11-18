INSERT INTO sysconfig (version, item, descr, config) VALUES ('production', 'jpjenkins_config', 'Jenkins service configuration',
'<?xml version="1.0" encoding="UTF-8"?><config><password>PASSWORD</password><username>USERNAME</username><token>TOKEN</token><url>URL</url></config>');

INSERT INTO widgetcatalog (code, titles, parameters, plugincode, parenttypecode, defaultconfig, locked, maingroup) VALUES ('jpjenkinsParams', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="en">Publish Params</property>
<property key="it">Pubblica Params</property>
</properties>', '<config>
	<parameter name="id">id</parameter>
	<action name="jpjenkinsParamsConfig"/>
</config>','jpjenkins', NULL, NULL, 1, 'free');

INSERT INTO widgetcatalog (code, titles, parameters, plugincode, parenttypecode, defaultconfig, locked, maingroup) VALUES ('jpjenkinsParams_list_form', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="en">Params List and Form</property>
<property key="it">Lista e Form Params</property>
</properties>', NULL, 'jpjenkins', 'formAction', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="actionPath">/ExtStr2/do/FrontEnd/jpjenkins/Params/list.action</property>
</properties>', 1, 'free');

INSERT INTO widgetcatalog(code, titles, parameters, plugincode, parenttypecode, defaultconfig, locked, maingroup) VALUES ('jpjenkinsConnector','<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="en">Jenkins Connector </property>
<property key="it">Connettore a Jenkins</property>
</properties>','','jpjenkins','formAction','<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="actionPath">/ExtStr2/do/FrontEnd/jpjenkins/Jobs/list.action</property>
</properties>',1,'');


INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jpjenkins_PARAMS_ID', 'en', 'id');
INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jpjenkins_PARAMS_ID', 'it', 'id');

INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jpjenkins_PARAMS_USERNAME', 'en', 'username');
INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jpjenkins_PARAMS_USERNAME', 'it', 'username');

INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jpjenkins_PARAMS_PASSWORD', 'en', 'password');
INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jpjenkins_PARAMS_PASSWORD', 'it', 'password');

INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jpjenkins_PARAMS_JENKINS_URL', 'en', 'jenkins_url');
INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jpjenkins_PARAMS_JENKINS_URL', 'it', 'jenkins_url');

INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jpjenkins_PARAMS_TOKEN', 'en', 'token');
INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jpjenkins_PARAMS_TOKEN', 'it', 'token');


INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jpjenkins_PARAMS_ACTIONS', 'it', 'Actions');
INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jpjenkins_PARAMS_ACTIONS', 'en', 'Actions');

INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jpjenkins_PARAMS_NEW', 'it', 'Params New');
INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jpjenkins_PARAMS_NEW', 'en', 'Params New');

INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jpjenkins_PARAMS_EDIT', 'it', 'Params Edit');
INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jpjenkins_PARAMS_EDIT', 'en', 'Params Edit');

INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jpjenkins_PARAMS_TRASH', 'it', 'Params Trash');
INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jpjenkins_PARAMS_TRASH', 'en', 'Params Trash');

INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jpjenkins_PARAMS_TRASH_CONFIRM', 'it', 'Params Trash confirm');
INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jpjenkins_PARAMS_TRASH_CONFIRM', 'en', 'Params Trash confirm');

INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jpjenkins_PARAMS_SEARCH_PARAMS', 'it', 'Params search');
INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jpjenkins_PARAMS_SEARCH_PARAMS', 'en', 'Params search');

INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jpjenkins_PARAMS_NOT_FOUND', 'it', 'Params not found');
INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jpjenkins_PARAMS_NOT_FOUND', 'en', 'Params not found');


INSERT INTO sysconfig (version, item, descr, config) VALUES ('production', 'jptrello_config', 'Trello service configuration',
'<?xml version="1.0" encoding="UTF-8"?><config><key>KEY</key><secret>SECRET</secret><token>TOKEN</token><organization>ORGANIZATION</organization></config>');

INSERT INTO widgetcatalog VALUES ('jptrelloBoardsList', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="en">Trello Connector</property>
<property key="it">Connettore a Trello</property>
</properties>', NULL, 'jptrello', 'formAction', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="actionPath">/ExtStr2/do/FrontEnd/jptrello/Boards/angular.action</property>
</properties>', 1, NULL);

INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jptrello_PARAMS_ID', 'en', 'id');
INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jptrello_PARAMS_ID', 'it', 'id');

INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jptrello_PARAMS_ORGANIZATION', 'en', 'organization');
INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jptrello_PARAMS_ORGANIZATION', 'it', 'organization');

INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jptrello_PARAMS_API_KEY', 'en', 'api_key');
INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jptrello_PARAMS_API_KEY', 'it', 'api_key');

INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jptrello_PARAMS_API_SECRET', 'en', 'api_secret');
INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jptrello_PARAMS_API_SECRET', 'it', 'api_secret');

INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jptrello_PARAMS_TOKEN', 'en', 'token');
INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jptrello_PARAMS_TOKEN', 'it', 'token');


INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jptrello_PARAMS_ACTIONS', 'it', 'Actions');
INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jptrello_PARAMS_ACTIONS', 'en', 'Actions');

INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jptrello_PARAMS_NEW', 'it', 'Params New');
INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jptrello_PARAMS_NEW', 'en', 'Params New');

INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jptrello_PARAMS_EDIT', 'it', 'Params Edit');
INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jptrello_PARAMS_EDIT', 'en', 'Params Edit');

INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jptrello_PARAMS_TRASH', 'it', 'Params Trash');
INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jptrello_PARAMS_TRASH', 'en', 'Params Trash');

INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jptrello_PARAMS_TRASH_CONFIRM', 'it', 'Params Trash confirm');
INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jptrello_PARAMS_TRASH_CONFIRM', 'en', 'Params Trash confirm');

INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jptrello_PARAMS_SEARCH_PARAMS', 'it', 'Params search');
INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jptrello_PARAMS_SEARCH_PARAMS', 'en', 'Params search');

INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jptrello_PARAMS_NOT_FOUND', 'it', 'Params not found');
INSERT INTO localstrings ( keycode, langcode, stringvalue ) VALUES ('jptrello_PARAMS_NOT_FOUND', 'en', 'Params not found');


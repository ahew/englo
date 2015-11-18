INSERT INTO sysconfig (version, item, descr, config) VALUES ('production', 'jpgithub_config', 'GitHub service configuration',
'<?xml version="1.0" encoding="UTF-8"?><config><username>USERNAME</username><password>PASSWORD</password></config>');

INSERT INTO widgetcatalog(code, titles, parameters, plugincode, parenttypecode, defaultconfig, locked)
    VALUES ('jpgithub_entry', '<?xml version="1.0" encoding="UTF-8"?>
<properties><property key="en">Github Connector - Project view</property><property key="it">Github Connector - Vista progetto</property></properties>',
 null, 'jpgithub', 'formAction', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="actionPath">/ExtStr2/do/jpgithub/FrontEnd/Project/entry.action</property>
</properties>',1);

INSERT INTO widgetcatalog(code, titles, parameters, plugincode, parenttypecode, defaultconfig, locked)
    VALUES ('jpgithub_pullrequest', '<?xml version="1.0" encoding="UTF-8"?>
<properties><property key="en">Github Connector - Project Pull request</property><property key="it">Github Connector - Pull Request del progetto</property></properties>',
 null, 'jpgithub', 'formAction', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="actionPath">/ExtStr2/do/jpgithub/FrontEnd/Project/listPullRequest.action</property>
</properties>',1);

INSERT INTO widgetcatalog(code, titles, parameters, plugincode, parenttypecode, defaultconfig, locked)
    VALUES ('jpgithub_pullrequest_new', '<?xml version="1.0" encoding="UTF-8"?>
<properties><property key="en">Github Connector - New Pull request</property><property key="it">Github Connector - Nuova pull request</property></properties>',
 null, 'jpgithub', 'formAction', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="actionPath">/ExtStr2/do/jpgithub/FrontEnd/Project/newPullRequest.action</property>
</properties>',1);

INSERT INTO widgetcatalog(code, titles, parameters, plugincode, parenttypecode, defaultconfig, locked)
    VALUES ('jpgithub_list', '<?xml version="1.0" encoding="UTF-8"?>
<properties><property key="en">Github Connector - Repository list</property><property key="it">Github Connector - Lista repository</property></properties>',
 null, 'jpgithub', 'formAction', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="actionPath">/ExtStr2/do/jpgithub/FrontEnd/Project/list.action</property>
</properties>',1);

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_PROJECT_commits:', 'en', 'Commits list');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_PROJECT_commits:', 'it', 'Lista di commit');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_REPOSITORY', 'en', 'Current project');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_REPOSITORY', 'it', 'Progetto corrente');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_PROJECT_current', 'en', 'Current branch');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_PROJECT_current', 'it', 'Branch in uso');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_PROJECT_branches', 'en', 'Branch selection');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_PROJECT_branches', 'it', 'Seleziona branch ');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_SET_BRANCH', 'en', 'Set branch');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_SET_BRANCH', 'it', 'Scegli branch');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_PULL_REQUEST_LIST', 'en', 'Pull request list');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_PULL_REQUEST_LIST', 'it', 'Lista pull request');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_PULL_REQUEST_NEW', 'en', 'Create a pull request');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_PULL_REQUEST_NEW', 'it', 'Crea una pull request');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_PROJECT_by', 'en', 'Project by');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_PROJECT_by', 'it', 'Progetto di');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_NUMBER', 'en', 'Number ');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_NUMBER', 'it', 'Numero');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_TITLE', 'en', 'Title');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_TITLE', 'it', 'Titolo');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_BODY', 'it', 'Body');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_BODY', 'en', 'Body');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_MERGED', 'en', 'Merged');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_MERGED', 'it', 'Mergiato');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_ACCEPT_PULL_REQUEST', 'en', 'Accept pull request');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_ACCEPT_PULL_REQUEST', 'it', 'Accetta pull request');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_MERGEABLE', 'en', 'Mergeable');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_MERGEABLE', 'it', 'Mergiabile');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_NOT_MERGEABLE', 'en', 'Not mergeable');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_NOT_MERGEABLE', 'it', 'Non mergiabile');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_PROJECT_PR_NEW', 'en', 'Create a pull request');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_PROJECT_PR_NEW', 'it', 'Crea una pull request ');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_PROJECT_title', 'en', 'Title');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_PROJECT_title', 'it', 'Titolo');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_PROJECT_body', 'en', 'Description');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_PROJECT_body', 'it', 'Descrizione');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_PROJECT_BASE_BRANCH', 'en', 'Base branch');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_PROJECT_BASE_BRANCH', 'it', 'Branch base');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_PROJECT_HEAD_BRANCH', 'en', 'Head branch ');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_PROJECT_HEAD_BRANCH', 'it', 'Head branch');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_NEW_PREQ', 'en', 'Create');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_NEW_PREQ', 'it', 'Crea');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_BACK_PROJECT_ENTRY', 'en', 'Back');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_BACK_PROJECT_ENTRY', 'it', 'Indietro');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_PROJECT_PR_CONFIRM', 'en', 'Accept pull request');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_PROJECT_PR_CONFIRM', 'it', 'Accetta pull request');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_PROJECT_ccd baseurrent', 'en', 'Basecamp project');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_PROJECT_ccd baseurrent', 'it', 'Progetto Basecamp');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_PROJECT_repositories', 'it', 'Lista repository');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_PROJECT_repositories', 'en', 'Repositories list');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_CONFIRM_PREQ', 'en', 'Confirm pull request ');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_CONFIRM_PREQ', 'it', 'Conferma pull request');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_PROJECT_PR_LIST', 'en', 'Pull request list');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_PROJECT_PR_LIST', 'it', 'Lista pull request');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_REPOSITORY_NAME', 'en', 'Repository name');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_REPOSITORY_NAME', 'it', 'Nome del repository');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_NO_BRANCHES', 'en', 'No branches found');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_NO_BRANCHES', 'it', 'Nessuna branch trovata');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_NO_COMMITS', 'en', 'No commits found');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_NO_COMMITS', 'it', 'Nessun commit trovato');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_ACTION_ERRORS', 'en', 'There are errors:');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_ACTION_ERRORS', 'it', 'Errori rilevati:');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_FIELD_ERRORS', 'en', 'Field errors:');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_FIELD_ERRORS', 'it', 'Errori nei campi:');

INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_ACTION_MESSAGES', 'en', 'Messages:');
INSERT INTO localstrings (keycode, langcode, stringvalue) VALUES ('jpgithub_ACTION_MESSAGES', 'it', 'Messaggi:');
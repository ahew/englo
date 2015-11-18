INSERT INTO pages (code, parentcode, pos, modelcode, titles, groupcode, showinmenu, extraconfig) VALUES ('login', 'service', 6, 'service', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="it">Pagina di login</property>
<property key="en">Login</property>
</properties>', 'free', 1, NULL);
INSERT INTO pages (code, parentcode, pos, modelcode, titles, groupcode, showinmenu, extraconfig) VALUES ('notfound', 'service', 4, 'service', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="en">Page not found</property>
<property key="it">Pagina non trovata</property>
</properties>

', 'free', 1, NULL);
INSERT INTO pages (code, parentcode, pos, modelcode, titles, groupcode, showinmenu, extraconfig) VALUES ('errorpage', 'service', 5, 'service', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="en">Error page</property>
<property key="it">Pagina di errore</property>
</properties>

', 'free', 1, NULL);
INSERT INTO pages (code, parentcode, pos, modelcode, titles, groupcode, showinmenu, extraconfig) VALUES ('homepage', 'homepage', -1, 'home', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="en">Home</property>
<property key="it">Home</property>
</properties>

', 'free', 1, NULL);
INSERT INTO pages (code, parentcode, pos, modelcode, titles, groupcode, showinmenu, extraconfig) VALUES ('service', 'homepage', 6, 'service', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="it">Pagine di Servizio</property>
<property key="en">Service</property>
</properties>', 'free', 0, NULL);
INSERT INTO pages (code, parentcode, pos, modelcode, titles, groupcode, showinmenu, extraconfig) VALUES ('trello', 'homepage', 9, 'trello', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="en">Trello</property>
<property key="it">Trello</property>
</properties>

', 'free', 1, '<?xml version="1.0" encoding="UTF-8"?>
<config>
  <useextratitles>false</useextratitles>
</config>

');
INSERT INTO pages (code, parentcode, pos, modelcode, titles, groupcode, showinmenu, extraconfig) VALUES ('home_englo', 'homepage', 5, 'home_englo', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="en">Englo Dashboard</property>
<property key="it">Englo Dashboard</property>
</properties>

', 'free', 1, '<?xml version="1.0" encoding="UTF-8"?>
<config>
  <useextratitles>false</useextratitles>
</config>

');
INSERT INTO pages (code, parentcode, pos, modelcode, titles, groupcode, showinmenu, extraconfig) VALUES ('basecamp', 'homepage', 7, 'basecamp_internal', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="en">Basecamp Projects</property>
<property key="it">Progetti Basecamp</property>
</properties>

', 'free', 1, '<?xml version="1.0" encoding="UTF-8"?>
<config>
  <useextratitles>false</useextratitles>
</config>

');
INSERT INTO pages (code, parentcode, pos, modelcode, titles, groupcode, showinmenu, extraconfig) VALUES ('jenkins', 'homepage', 10, 'jenkins', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="en">Jenkins</property>
<property key="it">Jenkins</property>
</properties>

', 'free', 1, '<?xml version="1.0" encoding="UTF-8"?>
<config>
  <useextratitles>false</useextratitles>
</config>

');
INSERT INTO pages (code, parentcode, pos, modelcode, titles, groupcode, showinmenu, extraconfig) VALUES ('bsnwproj', 'basecamp', 1, 'basecamp_internal', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="en">New Basecamp Project</property>
<property key="it">Nuovo progetto Basecamp</property>
</properties>

', 'free', 1, '<?xml version="1.0" encoding="UTF-8"?>
<config>
  <useextratitles>false</useextratitles>
</config>

');
INSERT INTO pages (code, parentcode, pos, modelcode, titles, groupcode, showinmenu, extraconfig) VALUES ('github', 'homepage', 8, 'internal', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="en">Github</property>
<property key="it">Github</property>
</properties>

', 'free', 1, '<?xml version="1.0" encoding="UTF-8"?>
<config>
  <useextratitles>false</useextratitles>
</config>

');
INSERT INTO pages (code, parentcode, pos, modelcode, titles, groupcode, showinmenu, extraconfig) VALUES ('bsprojectdet', 'basecamp', 2, 'basecamp_internal', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="en">Edit Basecamp Project</property>
<property key="it">Gestione progetto Basecamp</property>
</properties>

', 'free', 1, '<?xml version="1.0" encoding="UTF-8"?>
<config>
  <useextratitles>false</useextratitles>
</config>

');
INSERT INTO pages (code, parentcode, pos, modelcode, titles, groupcode, showinmenu, extraconfig) VALUES ('bsprjtodol', 'basecamp', 3, 'basecamp_internal', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="en">Basecamp Project</property>
<property key="it">Progetto Basecamp</property>
</properties>

', 'free', 1, '<?xml version="1.0" encoding="UTF-8"?>
<config>
  <useextratitles>false</useextratitles>
</config>

');
INSERT INTO pages (code, parentcode, pos, modelcode, titles, groupcode, showinmenu, extraconfig) VALUES ('prlist', 'github', 1, 'internal', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="en">Project Pull Request List</property>
<property key="it">Lista Pull Request progetto</property>
</properties>

', 'free', 1, '<?xml version="1.0" encoding="UTF-8"?>
<config>
  <useextratitles>false</useextratitles>
</config>

');
INSERT INTO pages (code, parentcode, pos, modelcode, titles, groupcode, showinmenu, extraconfig) VALUES ('ghprnew', 'github', 2, 'internal', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="en">New Pull request</property>
<property key="it">Nuova Pull Request</property>
</properties>

', 'free', 1, '<?xml version="1.0" encoding="UTF-8"?>
<config>
  <useextratitles>false</useextratitles>
</config>

');
INSERT INTO pages (code, parentcode, pos, modelcode, titles, groupcode, showinmenu, extraconfig) VALUES ('githubdet', 'github', 3, 'internal', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="en">GitHub Detail</property>
<property key="it">Dettaglio GitHub</property>
</properties>

', 'free', 1, '<?xml version="1.0" encoding="UTF-8"?>
<config>
  <useextratitles>false</useextratitles>
</config>

');
INSERT INTO pages (code, parentcode, pos, modelcode, titles, groupcode, showinmenu, extraconfig) VALUES ('colb', 'homepage', 11, 'basecamp_internal', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="en">Collaboration</property>
<property key="it">Collaboration</property>
</properties>

', 'free', 1, '<?xml version="1.0" encoding="UTF-8"?>
<config>
  <useextratitles>false</useextratitles>
</config>

');
INSERT INTO pages (code, parentcode, pos, modelcode, titles, groupcode, showinmenu, extraconfig) VALUES ('ararah', 'basecamp', 4, 'basecamp_internal', '<?xml version="1.0" encoding="UTF-8"?>
<properties>
<property key="en">Basecamp documents</property>
<property key="it">Basecamp documents</property>
</properties>

', 'free', 1, '<?xml version="1.0" encoding="UTF-8"?>
<config>
  <useextratitles>false</useextratitles>
</config>

');

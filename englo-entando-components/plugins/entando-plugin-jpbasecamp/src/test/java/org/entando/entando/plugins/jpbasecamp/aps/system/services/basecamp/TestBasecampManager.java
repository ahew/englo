package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.entando.entando.plugins.jpbasecamp.aps.AbstractBasecampManagerTest;
import org.entando.entando.plugins.jpbasecamp.aps.system.BasecampSystemConstants;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.BasecampService;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.attachments.AttachmentReference;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.attachments.item.UploadItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.documents.Document;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.documents.DocumentReference;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.people.Person;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.Project;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.ProjectReference;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.item.CreatorItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.Todo;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.Todolist;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.TodolistReference;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.item.AssigneeItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.item.SubscriberItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.item.TodoItem;

public class TestBasecampManager extends AbstractBasecampManagerTest {
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.init();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        Long id = _project.getId();
        
        try {
            assertNotNull(id);
            if (null != _service) {
                assertFalse(_project.getTrashed());
                _projectManager.deleteProject(id, _service);
                Project project = _projectManager.getProject(id, _service);
                assertNotNull(project);
                assertTrue(project.getTrashed());
            }
        } catch (Throwable t) {
            t.printStackTrace();
            throw new RuntimeException("Could not delete the test project");
        }
    }
    
     public void testUnknownProject() throws Throwable {
        if (null == _service) return;
        
        Project project = _projectManager.getProject(12345612, _service);
        assertNull(project);
    }
    
     public void testUnknownProjectUnmanned() throws Throwable {
        if (null == _config) return;
        
        Project project = _projectManager.getProject(12345612, null);
        assertNull(project);
    }
    
     public void testProjectList() throws Throwable {
        boolean listed = false;
        List<ProjectReference> projects = null;
        
        if (null == _service) return;
        
        projects = _projectManager.getProjects(_service);
        assertNotNull(projects);
        assertFalse(projects.isEmpty());
        
        for (ProjectReference projectReference: projects) {
            
            if (projectReference.getName().equals(PROJECT_NAME)) {
                long id = projectReference.getId();
                
                Project project = _projectManager.getProject(projectReference, _service);
                testLoadedProject(project);
                Project aProject = _projectManager.getProject(id, _service);
                assertNotNull(aProject);
                testLoadedProject(aProject);
                // finally
                listed = true;
                break;
            }
        }
        assertTrue(listed);
    }
    
     public void testProjectListUnmanned() throws Throwable {
        boolean listed = false;
        List<ProjectReference> projects = null;
        
        if (null == _config) return;
        
        try {
            // override default configuration
            BasecampUrlHelper.updateConfiguration(_config);
            projects = _projectManager.getProjects(null);
            assertNotNull(projects);
            assertFalse(projects.isEmpty());
            
            for (ProjectReference projectReference: projects) {
                
                if (projectReference.getName().equals(PROJECT_NAME)) {
                    long id = projectReference.getId();
                    
                    Project project = _projectManager.getProject(projectReference, null);
                    testLoadedProject(project);
                    Project aProject = _projectManager.getProject(id, null);
                    assertNotNull(aProject);
                    testLoadedProject(aProject);
                    // finally
                    listed = true;
                    break;
                }
            }
            assertTrue(listed);
            
        } catch (Throwable t) {
            t.printStackTrace();
            throw t;
        }
    }
    
     public void testProjectLoad() throws Throwable {
        SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat formatMonth = new SimpleDateFormat("MM");
        SimpleDateFormat formatDay = new SimpleDateFormat("dd");
        
        if (null == _service) return;
        
        assertNotNull(_project.getLocation());
        assertEquals(PROJECT_NAME, _project.getName());
        assertEquals(PROJECT_DESCRIPTION, _project.getDescription());
        assertFalse(_project.getId() == 0);
        Date now = new Date();
        assertEquals(formatYear.format(now), formatYear.format(_project.getCreatedAt()));
        assertEquals(formatMonth.format(now), formatMonth.format(_project.getCreatedAt()));
        assertEquals(formatDay.format(now), formatDay.format(_project.getCreatedAt()));
        assertEquals(formatYear.format(now), formatYear.format(_project.getUpdatedAt()));
        assertEquals(formatMonth.format(now), formatMonth.format(_project.getUpdatedAt()));
        assertEquals(formatDay.format(now), formatDay.format(_project.getUpdatedAt()));
        
        Person me = _peopleManager.whoAmI(_service);
        CreatorItem creator = _project.getCreator();
        assertEquals(me.getId(), creator.getId());
        assertEquals(me.getName(), creator.getName());
    }
    
     public void testProjectCreateUnmanned() throws Throwable {
        Project project = new Project();
        final String NAME = PROJECT_NAME.concat(" (NEW)");
        final String DESCRIPTION = PROJECT_DESCRIPTION.concat(" (NEW)");
        
        if (null != _config) return;
        
        try {
            project.setName(NAME);
            project.setDescription(DESCRIPTION);
            project.setArchived(false);
            project.setStarred(true);
            project.setIsClientProject(true);
            project = _projectManager.createProject(project, null);
            assertFalse(project.getId() == 0);
            
            long id = project.getId();
            Project verify = _projectManager.getProject(id , null);
            assertEquals(NAME, verify.getName());
            assertEquals(DESCRIPTION, verify.getDescription());
            assertTrue(verify.getStarred());
            assertFalse(verify.getArchived());
            assertTrue(verify.getIsClientProject());
            
        } catch (Throwable t) {
            throw t;
        } finally {
            long id = project.getId();
            
            _projectManager.deleteProject(project.getId(), null);
            Project verify = _projectManager.getProject(id , null);
            assertNull(verify);
        }
    }
    
     public void testDocumentList() throws Throwable {
        
        if (null == _service) return;
        
        List<DocumentReference> documents = _documentManager.getProjectDocuments(_project, _service);
        
        assertNotNull(documents);
        assertTrue(documents.isEmpty());
    }
    
     public void testDocument() throws Throwable {
        Document document = null;
        
        if (null == _service) return;
        
        SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat formatMonth = new SimpleDateFormat("MM");
        SimpleDateFormat formatDay = new SimpleDateFormat("dd");
        try {
            List<DocumentReference> documents = _documentManager.getProjectDocuments(_project, _service);
            Date now = new Date();
            
            assertNotNull(documents);
            assertTrue(documents.isEmpty());
            document = createDocumentForTest();
            documents = _documentManager.getProjectDocuments(_project, _service);
            assertFalse(documents.isEmpty());
            assertEquals(1, documents.size());
            assertEquals(formatYear.format(now), formatYear.format(document.getCreatedAt()));
            assertEquals(formatMonth.format(now), formatMonth.format(document.getCreatedAt()));
            assertEquals(formatDay.format(now), formatDay.format(document.getCreatedAt()));
            
            Document verify = _documentManager.getDocument(documents.get(0), _service);
            assertEquals(document.getTitle(), verify.getTitle());
            assertEquals(document.getContent(), verify.getContent());
            assertNotNull(verify.getProject());
            assertNotNull(verify.getLocation());
            
        } catch (Throwable t) {
            throw t;
        } finally {
            if (null != document) {
                _documentManager.deleteDocument(document, _service);
                List<DocumentReference> documents = _documentManager.getProjectDocuments(_project, _service);
                assertNotNull(documents);
                assertTrue(documents.isEmpty());
            }
        }
    }
    
     public void testDocumentUpdate() throws Throwable {
        Document document = null;
        
        if (null == _service) return;
        try {
            document = createDocumentForTest();
            String content = document.getContent().concat(" (updated)");
            document.setContent(content);
            Document updated = _documentManager.updateDocument(document, _service);
            assertNotSame(document, updated);
            assertEquals(content, updated.getContent());
            assertNotNull(updated.getProject());
            assertNotNull(updated.getLocation());
        } catch (Throwable t) {
            throw t;
        } finally {
            if (null != document) {
                _documentManager.deleteDocument(document, _service);
                List<DocumentReference> documents = _documentManager.getProjectDocuments(_project, _service);
                assertNotNull(documents);
                assertTrue(documents.isEmpty());
            }
        }
    }
    
     public void testProjectUpdate() throws Throwable {
        if (null == _service) return;
        final String DESCRIPTION = _project.getDescription().concat(" (UPDATED)");
        final String NAME = _project.getName().concat(" (UPDATED)");
        
        assertFalse(_project.getArchived());
        // change the project
        _project.setArchived(true);
        _project.setDescription(DESCRIPTION);
        _project.setName(NAME);
        
        Project updated = _projectManager.updateProject(_project, _service);
        assertNotNull(updated);
        assertEquals(updated.getId(), _project.getId());
        assertEquals(NAME, _project.getName());
        assertEquals(NAME, updated.getName());
        assertEquals(DESCRIPTION, updated.getDescription());
        assertEquals(DESCRIPTION, _project.getDescription());
        assertTrue(updated.getArchived());
    }
    
     public void testTodolist() throws Throwable {
        Todolist todolist = null;
        
        if (null == _service) return;
        try {
            List<TodolistReference> todolists = _todolistManager.getTodolists(_project, _service);
            assertNotNull(todolists);
            assertTrue(todolists.isEmpty());
            
            todolist = createTodolistForTest();
            todolists = _todolistManager.getTodolists(_project, _service);
            assertNotNull(todolists);
            assertFalse(todolists.isEmpty());
            assertEquals(1, todolists.size());
            TodolistReference reference = todolists.get(0);
            todolist = _todolistManager.getTodolist(reference, _service);
            assertNotNull(todolist.getProjectId());
            assertNotNull(todolist.getLocation());
            assertEquals(TODOLIST_DESCRIPTION, todolist.getDescription());
            assertEquals(TODOLIST_NAME, todolist.getName());
        } catch (Throwable t) {
            throw t;
        } finally {
            if (null != todolist) {
                _todolistManager.deleteTodolist(todolist, _service);
            }
        }
    }
    
     public void testTodo() throws Throwable {
        Todolist todolist = null;
        Todo todo = null;
        
        if (null == _service) return;
        try {
            Person me = _peopleManager.whoAmI(_service);
            List<TodolistReference> todolists = _todolistManager.getTodolists(_project, _service);
            assertNotNull(todolists);
            assertTrue(todolists.isEmpty());
            
            todolist = createTodolistForTest();
            todo = createAssignedTodoForTest(todolist);
            
            boolean isOk = false;
            Todolist ver = _todolistManager.getTodolist(_project.getId(), todolist.getId(), _service);
            assertNotNull(ver);
            List<TodoItem> list = ver.getRemaningList();
            assertNotNull(list);
            assertFalse(list.isEmpty());
            for (TodoItem cur: list) {
                if (cur.getId() == todo.getId()) {
                    if (cur.getContent().equals(TODO_CONTENT)
                            && me.getId() == cur.getAssignee().getId()) {
                        isOk = true;
                    }
                }
            }
            assertTrue(isOk);
        } catch (Throwable t) {
            throw t;
        } finally {
            if (null != todo) {
                _todolistManager.deleteTodo(todo, _service);
            }
            if (null != todolist) {
                _todolistManager.deleteTodolist(todolist, _service);
            }
        }
    }
    
     public void testUnassignedTodo() throws Throwable {
        Todolist todolist = null;
        Todo todo = null;
        
        if (null == _service) return;
        try {
            List<TodolistReference> todolists = _todolistManager.getTodolists(_project, _service);
            assertNotNull(todolists);
            assertTrue(todolists.isEmpty());
            
            todolist = createTodolistForTest();
            todo = createTodoForTest(todolist);
            
            todolists = _todolistManager.getTodolists(_project, _service);
            
            assertNotNull(todolists);
            assertFalse(todolists.isEmpty());
            assertEquals(1, todolists.size());
            
            TodolistReference todolistReference = todolists.get(0);
            assertNotNull(todolistReference);
            todolist = _todolistManager.getTodolist(todolistReference, _service);
            assertNotNull(todolist);
            assertNotNull(todolist.getRemaningList());
            assertFalse(todolist.getRemaningList().isEmpty());
            TodoItem item = todolist.getRemaningList().get(0);
            assertEquals(TODO_CONTENT, item.getContent());
        } catch (Throwable t) {
            throw t;
        } finally {
            if (null != todo) {
                _todolistManager.deleteTodo(todo, _service);
            }
            if (null != todolist) {
                _todolistManager.deleteTodolist(todolist, _service);
            }
        }
    }
    
     public void testTodoForRest() throws Throwable {
        Todolist todolist = null;
        Todo todo = null;
        
        if (null == _service) return;
        try {
            Person me = _peopleManager.whoAmI(_service);
            List<TodolistReference> todolists = _todolistManager.getTodolists(_project, _service);
            assertNotNull(todolists);
            assertTrue(todolists.isEmpty());
            
            todolist = createTodolistForTest();
            todo = createAssignedTodoForTest(todolist);
            
            todo = _todolistManager.getTodo(_project.getId(), todo.getId(), _service);
            assertNotNull(todo);
            assertEquals(me.getId(), todo.getAssignee().getId());
            assertEquals(TODO_CONTENT, todo.getContent());
        } catch (Throwable t) {
            throw t;
        } finally {
            if (null != todo) {
                _todolistManager.deleteTodo(todo, _service);
            }
            if (null != todolist) {
                _todolistManager.deleteTodolist(todolist, _service);
            }
        }
    }
    
     public void testTodoCreate() throws Throwable {
        Todolist todolist = null;
        Todo todo = null;
        
        if (null == _service) return;
        try {
            List<TodolistReference> todolists = _todolistManager.getTodolists(_project, _service);
            assertNotNull(todolists);
            assertTrue(todolists.isEmpty());
            
            todolist = createTodolistForTest();
            Person me = _peopleManager.whoAmI(_service);
            todo = createAssignedTodoForTest(todolist);
            
            boolean found = false;
            todolist = _todolistManager.getTodolist(_project.getId(), todolist.getId(), _service);
            for (TodoItem remaining: todolist.getRemaningList()) {
                if (remaining.getContent().equals(TODO_CONTENT)) {
                    found = true;
                    assertEquals(todo.getId(), remaining.getId());
                }
            }
            assertTrue(found);
        } catch (Throwable t) {
            throw t;
        } finally {
            if (null != todolist) {
                _todolistManager.deleteTodo(todo, _service);
                if (null != todolist) {
                    _todolistManager.deleteTodolist(todolist, _service);
                }
            }
        }
    }
    
     public void testUpdateTodo() throws Throwable {
        Todolist todolist = null;
        Todo todo = null;
        Person me = _peopleManager.whoAmI(_service);
        
        if (null == _service) return;
        try {
            List<TodolistReference> todolists = _todolistManager.getTodolists(_project, _service);
            assertNotNull(todolists);
            assertTrue(todolists.isEmpty());
            
            todolist = createTodolistForTest();
            todo = createTodoForTest(todolist);
            assertNull(todo.getAssignee());
            AssigneeItem assignee = new AssigneeItem();
            assignee.setId(me.getId());
            assignee.setName("Any name will fit");
            assignee.setType(AssigneeItem.TYPE_PERSON);
            todo.setAssignee(assignee);
            
            Todo updated = _todolistManager.updateTodo(todo, todolist, _service);
            assignee = updated.getAssignee();
            assertNotNull(updated.getProjectId());
            assertEquals(new Long(_project.getId()), updated.getProjectId());
            assertNotNull(assignee);
            assertEquals(me.getId(), assignee.getId());
            assertEquals(me.getName(), assignee.getName());
            List<SubscriberItem> subscribers = todo.getSubscribers();
            assertNotNull(subscribers);
            assertFalse(subscribers.isEmpty());
            assertEquals(1, subscribers.size());
            SubscriberItem subscriber = subscribers.get(0);
            assertEquals(me.getId(), subscriber.getId());
            
        } catch (Throwable t) {
            throw t;
        } finally {
            if (null != todolist) {
                _todolistManager.deleteTodo(todo, _service);
                if (null != todolist) {
                    _todolistManager.deleteTodolist(todolist, _service);
                }
            }
        }
    }
    
     public void testUpdateTodoNew() throws Throwable {
        Todolist todolist = null;
        Todo todo = null;
        Person me = _peopleManager.whoAmI(_service);
        Todo updated = null;
        final String ASSEGNEE_NAME = "Any name will fit";
        final String CONTENT = "A newly updated content";
        
        if (null == _service) return;
        try {
            List<TodolistReference> todolists = _todolistManager.getTodolists(_project, _service);
            assertNotNull(todolists);
            assertTrue(todolists.isEmpty());
            
            todolist = createTodolistForTest();
            todo = createTodoForTest(todolist);
            assertNotNull(todo.getId());
            assertNull(todo.getAssignee());
            AssigneeItem assignee = new AssigneeItem();
            assignee.setId(me.getId());
            assignee.setName(ASSEGNEE_NAME);
            assignee.setType(AssigneeItem.TYPE_PERSON);
            todo.setAssignee(assignee);
            todo.setContent(CONTENT);
            
            updated = _todolistManager.updateTodo(todo, (Long)null, _service); // NOTE THIS!!!
            assignee = updated.getAssignee();
            assertNotNull(updated.getProjectId());
            assertEquals(new Long(_project.getId()), updated.getProjectId());
            assertEquals(CONTENT, updated.getContent());
            assertNotNull(assignee);
            assertEquals(me.getId(), assignee.getId());
            assertEquals(me.getName(), assignee.getName());
            List<SubscriberItem> subscribers = todo.getSubscribers();
            assertNotNull(subscribers);
            assertFalse(subscribers.isEmpty());
            assertEquals(1, subscribers.size());
            SubscriberItem subscriber = subscribers.get(0);
            assertEquals(me.getId(), subscriber.getId());
        } catch (Throwable t) {
            throw t;
        } finally {
            if (null != todolist) {
                _todolistManager.deleteTodo(updated, _project.getId(), _service);
                if (null != todolist) {
                    _todolistManager.deleteTodolist(todolist, _service);
                }
            }
        }
    }
    
     public void testUpdateTodoNewUnmanned() throws Throwable {
        Todolist todolist = null;
        Todo todo = null;
        Person me = _peopleManager.whoAmI(_service);
        Todo updated = null;
        final String ASSEGNEE_NAME = "Any name will fit";
        final String CONTENT = "A newly updated content";
        
        if (null == _service) return;
        try {
            List<TodolistReference> todolists = _todolistManager.getTodolists(_project, null);
            assertNotNull(todolists);
            assertTrue(todolists.isEmpty());
            
            todolist = createTodolistForTest();
            todo = createTodoForTest(todolist);
            assertNotNull(todo.getId());
            assertNull(todo.getAssignee());
            AssigneeItem assignee = new AssigneeItem();
            assignee.setId(me.getId());
            assignee.setName(ASSEGNEE_NAME);
            assignee.setType(AssigneeItem.TYPE_PERSON);
            todo.setAssignee(assignee);
            todo.setContent(CONTENT);
            
            updated = _todolistManager.updateTodo(todo, (Long)null, null); // NOTE THIS!!!
            assignee = updated.getAssignee();
            assertNotNull(updated.getProjectId());
            assertEquals(new Long(_project.getId()), updated.getProjectId());
            assertEquals(CONTENT, updated.getContent());
            assertNotNull(assignee);
            assertEquals(me.getId(), assignee.getId());
            assertEquals(me.getName(), assignee.getName());
            List<SubscriberItem> subscribers = todo.getSubscribers();
            assertNotNull(subscribers);
            assertFalse(subscribers.isEmpty());
            assertEquals(1, subscribers.size());
            SubscriberItem subscriber = subscribers.get(0);
            assertEquals(me.getId(), subscriber.getId());
        } catch (Throwable t) {
            throw t;
        } finally {
            if (null != todolist) {
                _todolistManager.deleteTodo(updated, _project.getId(), null);
                if (null != todolist) {
                    _todolistManager.deleteTodolist(todolist, null);
                }
            }
        }
    }
    
     public void testTodolistUpdate() throws Throwable {
        Todolist todolist = null;
        
        if (null == _service) return;
        try {
            List<TodolistReference> todolists = _todolistManager.getTodolists(_project, _service);
            todolist = createTodolistForTest();
            todolists = _todolistManager.getTodolists(_project, _service);
            TodolistReference reference = todolists.get(0);
            todolist = _todolistManager.getTodolist(reference, _service);
            
            String description = TODOLIST_DESCRIPTION.concat(" (updated)");
            String name = TODOLIST_NAME.concat(" (updated)");
            
            todolist.setDescription(description);
            todolist.setName(name);
            
            Todolist updated = _todolistManager.updateTodolist(todolist, _service);
            assertEquals(name, updated.getName());
            assertEquals(description, updated.getDescription());
            assertNotNull(updated.getProjectId());
            assertNotNull(updated.getLocation());
            
        } catch (Throwable t) {
            throw t;
        } finally {
            if (null != todolist) {
                _todolistManager.deleteTodolist(todolist, _service);
            }
        }
    }
    
     public void testUrls() throws Throwable {
        try {
            Long id = BasecampUrlHelper.getProjectIdFromUrl("http://some.url/projects/1/todos/2.json");
            assertNotNull(id);
            assertEquals(new Long(1), id);
            id = BasecampUrlHelper.getTodolistIdFromUrl("http://some.url/projects/1/todolists/2/todos.json");
            assertNotNull(id);
            assertEquals(new Long(2), id);
        } catch (Throwable t) {
            throw t;
        }
    }
       
    public void testAttachment() throws Throwable {
        AttachmentReference resourceReference = null;
        
        try {
            
            final File file = new File(TEST_IMAGE);
            assertTrue(file.exists());
            InputStream is = new FileInputStream(file);
            final String filename = UPLOADED_RESOURCE_PREFIX + file.getName();
            UploadItem uploadItem = _attachmentManager.createAttachment(filename, is, "a random text", _service);
            assertNotNull(uploadItem);
            assertNotNull(uploadItem.getToken());
            _attachmentManager.createUpload(_project, uploadItem, null);
            
            // look for the attachment
            List<AttachmentReference> attachments = _attachmentManager.getProjectAttachments(_project, null);
            assertNotNull(attachments);
            assertFalse(attachments.isEmpty());
            
            for (AttachmentReference ref: attachments) {
                if (ref.getName().equals(filename)) {
                    resourceReference = ref;
                    break;
                }
            }
            assertNotNull(resourceReference);
            
            Thread.sleep(1000);
            
            // download the attachment
            _attachmentManager.downaloadAttachment(resourceReference, RESOURCE_PATH, null);
            File download = new File(RESOURCE_PATH + File.separator + filename);
            
            assertTrue(download.exists());
            assertEquals(file.length(), download.length());
 
            // delete attachment from basecamp
            _attachmentManager.deleteAttachment(resourceReference, _service);
            
            // look for the attachment again
            resourceReference = null;
            attachments = _attachmentManager.getProjectAttachments(_project, null);
            assertNotNull(attachments);
            assertTrue(attachments.isEmpty());
            
            // delete the attachment from the local storage
            assertTrue(download.delete());
        } catch (Throwable t) {
            t.printStackTrace();
            throw t;
        }
    }
    
    private void testLoadedProject(Project project) throws Throwable {
        SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat formatMonth = new SimpleDateFormat("MM");
        SimpleDateFormat formatDay = new SimpleDateFormat("dd");
        
        if (null == _service) return;
        
        assertNotNull(project.getLocation());
        assertEquals(PROJECT_NAME, project.getName());
        assertEquals(PROJECT_DESCRIPTION, project.getDescription());
        assertFalse(project.getId() == 0);
        Date now = new Date();
        assertEquals(formatYear.format(now), formatYear.format(project.getCreatedAt()));
        assertEquals(formatMonth.format(now), formatMonth.format(project.getCreatedAt()));
        assertEquals(formatDay.format(now), formatDay.format(project.getCreatedAt()));
        assertEquals(formatYear.format(now), formatYear.format(project.getUpdatedAt()));
        assertEquals(formatMonth.format(now), formatMonth.format(project.getUpdatedAt()));
        assertEquals(formatDay.format(now), formatDay.format(project.getUpdatedAt()));
        
        Person me = _peopleManager.whoAmI(_service);
        CreatorItem creator = project.getCreator();
        assertEquals(me.getId(), creator.getId());
        assertEquals(me.getName(), creator.getName());
    }
    
    /**
     * Create a todo for tests
     * @param todolist
     * @return
     * @throws Throwable
     */
    private Todo createAssignedTodoForTest(Todolist todolist) throws Throwable {
        Person me = _peopleManager.whoAmI(_service);
        Todo todo = new Todo();
        todo.setDueAt(new Date());
        todo.setContent(TODO_CONTENT);
        Todo updated = _todolistManager.createTodo(todo, me, todolist, _service);
        assertNotNull(updated);
        assertEquals(TODO_CONTENT, updated.getContent());
        assertNotNull(updated.getProjectId());
        assertEquals(new Long(_project.getId()), updated.getProjectId());
        assertFalse(updated.getTodolistId() == 0);
        return updated;
    }
    
    /**
     * Create a todo for tests
     * @param todolist
     * @return
     * @throws Throwable
     */
    private Todo createTodoForTest(Todolist todolist) throws Throwable {
        Todo todo = new Todo();
        todo.setDueAt(new Date());
        todo.setContent(TODO_CONTENT);
        Todo updated = _todolistManager.createTodo(todo, null, todolist, _service);
        assertNotNull(updated);
        assertEquals(TODO_CONTENT, updated.getContent());
        assertNotNull(updated.getProjectId());
        assertEquals(new Long(_project.getId()), updated.getProjectId());
        assertFalse(updated.getTodolistId() == 0);
        return updated;
    }
    
    /**
     * Create a todolist for tests
     * @return
     * @throws Throwable
     */
    private Todolist createTodolistForTest() throws Throwable {
        Todolist todolist = new Todolist(TODOLIST_NAME, TODOLIST_DESCRIPTION, 1);
        
        Todolist updated = _todolistManager.createTodolist(todolist, _project, _service);
        assertNotNull(updated);
        assertFalse(updated.getId() == 0);
        assertEquals(TODOLIST_NAME, updated.getName());
        assertEquals(TODOLIST_DESCRIPTION, updated.getDescription());
        return updated;
    }
    
    /**
     * Create a document for tests
     * @return
     * @throws Throwable
     */
    private Document createDocumentForTest() throws Throwable {
        Document document = null;
        
        List<DocumentReference> documents = _documentManager.getProjectDocuments(_project, _service);
        assertNotNull(documents);
        assertTrue(documents.isEmpty());
        document = new Document(DOCUMENT_TITLE, DOCUMENT_CONTENT);
        Document updated = _documentManager.createDocument(document, _project, _service);
        assertNotNull(updated.getProject());
        assertEquals(DOCUMENT_TITLE, updated.getTitle());
        assertEquals(DOCUMENT_CONTENT, updated.getContent());
        assertFalse(updated.getId() == 0);
        return updated;
    }
    
    private void init() throws Exception {
        _service = getBasecampServiceForTest();
        _config = getBasecampConfigForTest();
        try {
            _basecampManager = (IBasecampManager) super.getService(BasecampSystemConstants.BASECAMP_OAUTH2_MANAGER);
            _projectManager = (IProjectManager) super.getService(BasecampSystemConstants.BASECAMP_PROJECT_MANAGER);
            _documentManager = (IDocumentManager) super.getService(BasecampSystemConstants.BASECAMP_DOCUMENT_MANAGER);
            _todolistManager = (ITodolistManager) super.getService(BasecampSystemConstants.BASECAMP_TODOLIST_MANAGER);
            _peopleManager = (IPeopleManager) super.getService(BasecampSystemConstants.BASECAMP_PEOPLE_MANAGER);
            _attachmentManager = (IAttachmentManager) super.getService(BasecampSystemConstants.BASECAMP_ATTACHMENT_MANAGER);
            if (null != _service) {
                _basecampManager.postLogin(_service);
                _project = new Project(PROJECT_NAME, PROJECT_DESCRIPTION, false);
                _project = _projectManager.createProject(_project, _service);
                
//                // FOR TESTS ON EXISTING PROJECT
//                List<ProjectReference> projects = _projectManager.getProjects(_service);
//                for (ProjectReference project: projects) {
//                    if (project.getName().equals(PROJECT_NAME)) {
//                        _project = _projectManager.getProject(project, _service);
//                    }
//                }
                assertNotNull(_project);
            }
        } catch (Throwable e) {
            throw new RuntimeException("Error creating a new project", e);
        }
    }
    
    private IBasecampManager _basecampManager;
    private IProjectManager _projectManager;
    private IDocumentManager _documentManager;
    private ITodolistManager _todolistManager;
    private IPeopleManager _peopleManager;
    private IAttachmentManager _attachmentManager;
    
    private BasecampService _service;
    private BasecampConfig _config;
    private Project _project;
    
    
    public final static String PROJECT_NAME = "New (clever) project";
    public final static String PROJECT_DESCRIPTION = "Inspiring description (hairy)";
    public final static String DOCUMENT_TITLE = "Secret document";
    public final static String DOCUMENT_CONTENT = "Clever description</br>";
    public final static String TODOLIST_NAME = "Release todolist";
    public final static String TODOLIST_DESCRIPTION = "Demanding todolist description";
    public final static String TODO_CONTENT = "Important TODO task";
}

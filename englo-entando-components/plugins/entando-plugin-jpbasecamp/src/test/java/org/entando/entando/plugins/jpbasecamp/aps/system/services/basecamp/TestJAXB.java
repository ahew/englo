package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;
import org.entando.entando.plugins.jpbasecamp.aps.ApsPluginBaseTestCase;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.api.model.JAXBProject;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.api.model.JAXBProjectReference;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.api.model.JAXBTodo;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.api.model.JAXBTodolist;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.api.model.JAXBTodolistReference;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.item.CreatorItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.item.AssigneeItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.item.TodoItem;


public class TestJAXB extends ApsPluginBaseTestCase {
	
	
	public void testJAXBProjectMarshalling() throws Throwable {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = null;
		Date now = new Date();
		
		JAXBProject project = new JAXBProject();
		CreatorItem creator = new CreatorItem((String)null);
		
		creator.setId(CREATOR_ID);
		creator.setId(CREATOR_ID);
		creator.setAvatarUrl(CREATOR_AVATAR_URL);
		creator.setName(CREATOR_NAME);
		creator.setFullsizeAvatarUrl(CREATOR_FULL_AVATAR_URL);
		
		project.setName(PROJECT_NAME);
		project.setId(PROJECT_ID);
		project.setDescription(PROJECT_DESCRIPTION);
		project.setArchived(true);
		project.setIsClientProject(true);
		project.setCreatedAt(now);
		project.setUpdatedAt(project.getCreatedAt());
		project.setDraft(true);
		project.setStarred(false);
		project.setTrashed(false);
		project.setCreator(creator);
		
		try {
			// marshal
			JAXBContext jaxbContext = JAXBContext.newInstance(JAXBProject.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			String xml = null;
			
//			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//			jaxbMarshaller.marshal(project, System.out);
			
			jaxbMarshaller.marshal(project, baos);
			xml = baos.toString("UTF-8");
			assertTrue(StringUtils.isNotBlank(xml));
			// unmarshal
			is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			JAXBProject jproj = (JAXBProject) jaxbUnmarshaller.unmarshal(is);
			assertNotNull(jproj);
			assertEquals(PROJECT_ID, jproj.getId());
			assertEquals(PROJECT_NAME, jproj.getName());
			CreatorItem jcreator = jproj.getCreator();
			assertNotNull(jcreator);
			assertEquals(CREATOR_ID, jcreator.getId());
			assertEquals(CREATOR_NAME, jcreator.getName());
			assertEquals(CREATOR_AVATAR_URL, jcreator.getAvatarUrl());
			assertEquals(CREATOR_FULL_AVATAR_URL, jcreator.getFullsizeAvatarUrl());
			assertTrue(jproj.getArchived());
			assertTrue(jproj.getIsClientProject());
			assertTrue(jproj.getDraft());
			assertFalse(jproj.getStarred());
			assertEquals(now, jproj.getCreatedAt());
			assertEquals(now, jproj.getUpdatedAt());
		} catch (Throwable t) {
//			t.printStackTrace();
			throw t;
		} finally {
			baos.close();
			if (null != is) {
				is.close();
			}
		}
	}
	
	public void testJAXBProjectReferenceMarshalling() throws Throwable {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = null;
		Date now = new Date();
		
		JAXBProjectReference reference = new JAXBProjectReference();
		reference.setName(PROJECT_NAME);
		reference.setId(PROJECT_ID);
		reference.setDescription(PROJECT_DESCRIPTION);
		reference.setArchived(true);
		reference.setIsClientProject(true);
		reference.setCreatedAt(now);
		reference.setUpdatedAt(now);
		reference.setDraft(true);
		reference.setStarred(false);
		reference.setLastEventAt(now);
		reference.setUrl(CREATOR_AVATAR_URL);
		
		try {
			// marshal
			JAXBContext jaxbContext = JAXBContext.newInstance(JAXBProjectReference.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			String xml = null;
			
//			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//			jaxbMarshaller.marshal(reference, System.out);
			
			jaxbMarshaller.marshal(reference, baos);
			xml = baos.toString("UTF-8");
			assertTrue(StringUtils.isNotBlank(xml));
			// unmarshal
			is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			JAXBProjectReference jref = (JAXBProjectReference) jaxbUnmarshaller.unmarshal(is);
			assertNotNull(jref);
			assertEquals(PROJECT_ID, jref.getId());
			assertEquals(PROJECT_NAME, jref.getName());
			assertTrue(jref.getArchived());
			assertTrue(jref.getIsClientProject());
			assertEquals(now, jref.getLastEventAt());
			assertTrue(jref.getDraft());
			assertFalse(jref.getStarred());
			assertEquals(CREATOR_AVATAR_URL, jref.getUrl());
			assertEquals(now, jref.getCreatedAt());
			assertEquals(now, jref.getUpdatedAt());
			
			
		} catch (Throwable t) {
//			t.printStackTrace();
			throw t;
		} finally {
			baos.close();
			if (null != is) {
				is.close();
			}
		}
	}
	
	
	public void testJAXBTodolistReferenceMarshalling() throws Throwable {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = null;
		Date now = new Date();
		
		JAXBTodolistReference reference = new JAXBTodolistReference();
		reference.setName(PROJECT_NAME);
		reference.setId(PROJECT_ID);
		reference.setDescription(PROJECT_DESCRIPTION);
		reference.setUpdatedAt(now);
		reference.setUrl(CREATOR_AVATAR_URL);
		reference.setCompleted(true);
		reference.setPosition(2677);
		reference.setIsPrivate(false);
		reference.setTrashed(true);
		reference.setCompletedCount(2381);
		reference.setRemaninigCount(29478);
		reference.setCreator(null);
		reference.setBucket(null);
		
		try {
			// marshal
			JAXBContext jaxbContext = JAXBContext.newInstance(JAXBTodolistReference.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			String xml = null;
			
//			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//			jaxbMarshaller.marshal(reference, System.out);
			
			jaxbMarshaller.marshal(reference, baos);
			xml = baos.toString("UTF-8");
			assertTrue(StringUtils.isNotBlank(xml));
			// unmarshal
			is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			JAXBTodolistReference tdl = (JAXBTodolistReference) jaxbUnmarshaller.unmarshal(is);
			
			assertNotNull(tdl);
			assertEquals(PROJECT_ID, tdl.getId());
			assertEquals(PROJECT_NAME, tdl.getName());
			assertEquals(CREATOR_AVATAR_URL, tdl.getUrl());
			assertEquals(now, tdl.getUpdatedAt());
			assertTrue(tdl.getCompleted());
			assertEquals(2677, tdl.getPosition());
			assertFalse(tdl.getIsPrivate());
			assertTrue(tdl.getTrashed());
			assertEquals(2381, tdl.getCompletedCount());
			assertEquals(29478, tdl.getRemaninigCount());
			assertNull(tdl.getCreator());
			assertNull(tdl.getBucket());
			
		} catch (Throwable t) {
//			t.printStackTrace();
			throw t;
		} finally {
			baos.close();
			if (null != is) {
				is.close();
			}
		}
	}
	
	public void testJAXBTodolistMarshalling() throws Throwable {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = null;
		Date now = new Date();
		
		TodoItem todoItem = new TodoItem(TODOITEM_JSON);
		AssigneeItem assignee = todoItem.getAssignee();
		assertNotNull(todoItem.getAssignee());
		assertEquals("Matteo Emanuele", assignee.getName());
		assertEquals(2677267, assignee.getId());
		List<TodoItem> fakeList = new ArrayList<TodoItem>();
		fakeList.add(todoItem);
		
		JAXBTodolist reference = new JAXBTodolist();
		reference.setName(PROJECT_NAME);
		reference.setId(PROJECT_ID);
		reference.setDescription(PROJECT_DESCRIPTION);
		reference.setUpdatedAt(now);
		reference.setUrl(CREATOR_AVATAR_URL);
		reference.setCompleted(true);
		reference.setPosition(2677);
		reference.setIsPrivate(false);
		reference.setTrashed(true);
		reference.setCompletedCount(2381);
		reference.setRemaninigCount(29478);
		reference.setCreator(null);
		reference.setBucket(null);
		reference.setCreatedAt(now);
		reference.setProjectId(27147);
		reference.setRemaningList(fakeList);
		
		try {
			// marshal
			JAXBContext jaxbContext = JAXBContext.newInstance(JAXBTodolist.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			String xml = null;
			
//			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//			jaxbMarshaller.marshal(reference, System.out);
			
			jaxbMarshaller.marshal(reference, baos);
			xml = baos.toString("UTF-8");
			assertTrue(StringUtils.isNotBlank(xml));
			// unmarshal
			is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			JAXBTodolist tdl = (JAXBTodolist) jaxbUnmarshaller.unmarshal(is);
			
			assertNotNull(tdl);
			assertEquals(PROJECT_ID, tdl.getId());
			assertEquals(PROJECT_NAME, tdl.getName());
			assertEquals(CREATOR_AVATAR_URL, tdl.getUrl());
			assertEquals(now, tdl.getUpdatedAt());
			assertTrue(tdl.getCompleted());
			assertEquals(2677, tdl.getPosition());
			assertFalse(tdl.getIsPrivate());
			assertTrue(tdl.getTrashed());
			assertEquals(2381, tdl.getCompletedCount());
			assertEquals(29478, tdl.getRemaninigCount());
			assertNull(tdl.getCreator());
			assertNull(tdl.getBucket());
			assertEquals(now, tdl.getCreatedAt());
			assertEquals(27147, tdl.getProjectId());
			TodoItem todo = tdl.getRemaningList().get(0);
			assertNotNull(todo);
			assertNotNull(todo.getAssignee());
		} catch (Throwable t) {
//			t.printStackTrace();
			throw t;
		} finally {
			baos.close();
			if (null != is) {
				is.close();
			}
		}
	}
	
	public void testJAXBTodoMarshalling() throws Throwable {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = null;
		Date now = new Date();
		
		AssigneeItem assegnee = new AssigneeItem(SUBSCRIBER_JSON);
		JAXBTodo reference = new JAXBTodo();
		reference.setContent(PROJECT_NAME);
		reference.setId(PROJECT_ID);
		reference.setUpdatedAt(now);
		reference.setUrl(CREATOR_AVATAR_URL);
		reference.setCompleted(true);
		reference.setPosition(2677);
		reference.setIsPrivate(false);
		reference.setTrashed(true);
		reference.setCommentsCount(29478);
		reference.setCreator(null);
		reference.setDueAt(now);
		reference.setCreatedAt(now);
		reference.setAssignee(assegnee);
		reference.setProjectId(2381);
		reference.setTodolistId(2677);
		
		try {
			// marshal
			JAXBContext jaxbContext = JAXBContext.newInstance(JAXBTodo.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			String xml = null;
			
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			jaxbMarshaller.marshal(reference, System.out);
			
			jaxbMarshaller.marshal(reference, baos);
			xml = baos.toString("UTF-8");
			assertTrue(StringUtils.isNotBlank(xml));
			// unmarshal
			is = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			JAXBTodo td = (JAXBTodo) jaxbUnmarshaller.unmarshal(is);
			
			assertNotNull(td);
			assertEquals(PROJECT_ID, td.getId());
			assertEquals(PROJECT_NAME, td.getContent());
			assertEquals(CREATOR_AVATAR_URL, td.getUrl());
			assertEquals(now, td.getUpdatedAt());
			assertTrue(td.getCompleted());
			assertEquals(2677, td.getPosition());
			assertFalse(td.getIsPrivate());
			assertTrue(td.getTrashed());
			assertEquals(29478, td.getCommentsCount());
			assertNull(td.getCreator());
			assertEquals(now, td.getCreatedAt());
			assertEquals(now, td.getDueAt());
			assertEquals(2677, td.getTodolistId());
			assertEquals(2381, td.getProjectId());
			assegnee = td.getAssignee();
			assertNotNull(assegnee);
			assertEquals(1002677, assegnee.getId());
		} catch (Throwable t) {
//			t.printStackTrace();
			throw t;
		} finally {
			baos.close();
			if (null != is) {
				is.close();
			}
		}
	}
	
	private static final String PROJECT_NAME = "JAXBProject";
	private static final String PROJECT_DESCRIPTION = "JAXBDescription";
	private static final long PROJECT_ID = 2677;

	private static final String CREATOR_AVATAR_URL = "http://www.entandoavatar";
	private static final String CREATOR_NAME = "Mr.Entando";
	private static final String CREATOR_FULL_AVATAR_URL = "http://www.entandoavatar/full";
	private static final long CREATOR_ID = 2381;
	
	private static final String TODOITEM_JSON = "{\"position\":2,\"comments_count\":0,\"due_at\":null,\"assignee\":{\"id\":2677267,\"name\":\"Matteo Emanuele\",\"type\":\"Person\"},\"url\":\"https://basecamp.com/1234456/api/v1/projects/6660715/todos/119755820.json\",\"due_on\":null,\"creator\":{\"id\":1122334,\"fullsize_avatar_url\":\"https://random.url/original.gif?r=3\",\"avatar_url\":\"https://random.url/avatar.gif?r=3\",\"name\":\"Matteo Emanuele\"},\"private\":false,\"id\":119755820,\"content\":\"Cast a miracle\",\"updated_at\":\"2014-08-19T11:37:31.000+02:00\",\"trashed\":false,\"todolist_id\":19392238,\"created_at\":\"2014-08-19T11:37:06.000+02:00\",\"completed\":false}";
	private static final String TODO_JSON = "{\"id\":119587866,\"todolist_id\":19392238,\"position\":1,\"content\":\"Make clever things\",\"completed\":false,\"created_at\":\"2014-08-18T19:00:39.000+02:00\",\"updated_at\":\"2014-08-18T19:00:39.000+02:00\",\"comments_count\":0,\"private\":false,\"trashed\":false,\"due_on\":null,\"due_at\":null,\"creator\":{\"id\":1234567,\"name\":\"Matteo Emanuele\",\"avatar_url\":\"http://dge9rmgqjs8m1.cloudfront.net/global/71066739bb2934538849512ee6dc20e90010/avatar.gif?r=3\",\"fullsize_avatar_url\":\"https://some.url/global/original.gif?r=3\"},\"url\":\"https://some.url/123456/api/v1/projects/123456/todos/119587866.json\",\"comments\":[],\"subscribers\":[{\"id\":1234567,\"name\":\"Matteo Emanuele\"}]}";
	private static final String SUBSCRIBER_JSON = "{\"id\":1002677,\"name\":\"Matteo Emanuele\"}";
}

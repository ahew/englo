package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp;

import java.util.List;

import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.BasecampService;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.documents.Document;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.documents.DocumentReference;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.Project;

public interface IDocumentManager {
	
	/**
	 * Get the list of the documents
	 * @param project TODO
	 * @param serviceData
	 * @return
	 * @throws Throwable
	 */
	public List<DocumentReference> getProjectDocuments(Project project, BasecampService serviceData) throws Throwable;
	
	/**
	 * Load the details of document
	 * @param document
	 * @param serviceData
	 * @return
	 * @throws Throwable
	 */
	public Document getDocument(DocumentReference document, BasecampService serviceData) throws Throwable;
	
	/**
	 * Create a new document from scratch
	 * @param document
	 * @param project TODO
	 * @param serviceData
	 * @return TODO
	 * @throws Throwable
	 */
	public Document createDocument(Document document, Project project, BasecampService serviceData) throws Throwable;
	
	/**
	 * Deleted the given document
	 * @param document
	 * @param serviceData
	 * @throws Throwable
	 */
	public void deleteDocument(Document document, BasecampService serviceData) throws Throwable;
	
	
	/**
	 * Update the given document
	 * @param document
	 * @param serviceData
	 * @return
	 * @throws Throwable
	 */
	public Document updateDocument(Document document, BasecampService serviceData) throws Throwable;
}

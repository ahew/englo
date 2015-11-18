package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp;

import java.io.InputStream;
import java.util.List;

import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.BasecampService;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.attachments.Attachment;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.attachments.AttachmentReference;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.attachments.item.UploadItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.Project;

public interface IAttachmentManager {
	
	/**
	 * Get the projects in Basecamp
	 * @param project TODO
	 * @param serviceData
	 * @return
	 * @throws Throwable
	 */
	public List<AttachmentReference> getProjectAttachments(Project project, BasecampService serviceData) throws Throwable;
	
	
	/**
	 * Create an attachment
	 * @param name name of the source file
	 * @param stream stream of the input file
	 * @param text optional comment
	 * @param serviceData
	 * @return TODO
	 * @throws Throwable
	 */
	public UploadItem createAttachment(String name, InputStream stream, String text, BasecampService serviceData) throws Throwable;


	/**
	 * Turn the uploaded attachment identified by the given token into an upload
	 * @param project
	 * @param item TODO
	 * @param serviceData
	 * @throws Throwable
	 */
	public void createUpload(Project project, UploadItem item, BasecampService serviceData) throws Throwable;


	/**
	 * Get attachment info given its reference
	 * @param reference
	 * @param serviceData
	 * @throws Throwable
	 */
	public String downaloadAttachment(AttachmentReference reference, String path, BasecampService serviceData) throws Throwable;


	/**
	 * Get attachment metdata
	 * @param reference
	 * @param serviceData
	 * @return
	 * @throws Throwable
	 */
	public Attachment getAttachment(AttachmentReference reference, BasecampService serviceData) throws Throwable;
        
        /**
         * Delete attachment
         * @param reference
         * @param path
         * @param serviceData
         * @throws Throwable 
         */
        public void deleteAttachment(AttachmentReference reference, BasecampService serviceData) throws Throwable;
	
}

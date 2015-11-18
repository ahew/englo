package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.api.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.ProjectReference;

@XmlRootElement(name = "projectReference")
@XmlType(propOrder = {"id", "name", "description", "archived", "isClientProject", "createdAt", "updatedAt", "draft", "starred", "lastEventAt", "url"})
public class JAXBProjectReference {

	public JAXBProjectReference() { }
	
	public JAXBProjectReference(ProjectReference reference) {
		this.setId(reference.getId());
		this.setName(reference.getName());
		this.setDescription(reference.getDescription());
		this.setArchived(reference.getArchived());
		this.setIsClientProject(reference.getIsClientProject());
		this.setCreatedAt(reference.getCreatedAt());
		this.setUpdatedAt(reference.getUpdatedAt());
		this.setDraft(reference.getDraft());
		this.setStarred(reference.getStarred());
		this.setLastEventAt(reference.getLastEventAt());
		this.setUrl(reference.getUrl());
	}
	
	
	@XmlElement(name = "id", required = true)
	public long getId() {
		return _id;
	}
	public void setId(long id) {
		this._id = id;
	}
	@XmlElement(name = "name", required = true)
	public String getName() {
		return _name;
	}
	public void setName(String name) {
		this._name = name;
	}
	@XmlElement(name = "description", required = true)
	public String getDescription() {
		return _description;
	}
	public void setDescription(String description) {
		this._description = description;
	}
	@XmlElement(name = "archived", required = true)
	public Boolean getArchived() {
		return _archived;
	}
	public void setArchived(Boolean archived) {
		this._archived = archived;
	}
	@XmlElement(name = "isClientProject", required = true)
	public Boolean getIsClientProject() {
		return _isClientProject;
	}
	public void setIsClientProject(Boolean isClientProject) {
		this._isClientProject = isClientProject;
	}
	@XmlElement(name = "createdAt", required = true)
	public Date getCreatedAt() {
		return _createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this._createdAt = createdAt;
	}
	@XmlElement(name = "updatedAt", required = true)
	public Date getUpdatedAt() {
		return _updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this._updatedAt = updatedAt;
	}
	@XmlElement(name = "draft", required = true)
	public Boolean getDraft() {
		return _draft;
	}
	public void setDraft(Boolean draft) {
		this._draft = draft;
	}
	@XmlElement(name = "starred", required = true)
	public Boolean getStarred() {
		return _starred;
	}
	public void setStarred(Boolean starred) {
		this._starred = starred;
	}
	@XmlElement(name = "lastEventAt", required = true)
	public Date getLastEventAt() {
		return _lastEventAt;
	}
	public void setLastEventAt(Date lastEventAt) {
		this._lastEventAt = lastEventAt;
	}
	@XmlElement(name = "url", required = true)
	public String getUrl() {
		return _url;
	}
	public void setUrl(String url) {
		this._url = url;
	}
	
	private long _id;
	private String _name;
	private String _description;
	private Boolean _archived;
	private Boolean _isClientProject;
	private Date _createdAt;
	private Date _updatedAt;
	private Boolean _draft;
	private Boolean _starred;
	private Date _lastEventAt;
	private String _url;
}

package org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.api.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.project.item.CreatorItem;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.TodolistReference;
import org.entando.entando.plugins.jpbasecamp.aps.system.services.basecamp.model.todolist.item.BucketItem;


@XmlRootElement(name = "todolistReference")
@XmlType(propOrder = {"id","name","description","updatedAt","url","completed","position","isPrivate","trashed","completedCount","remaninigCount","creator","bucket"})
@XmlSeeAlso({CreatorItem.class, BucketItem.class})
public class JAXBTodolistReference {

	public JAXBTodolistReference() { }

	public JAXBTodolistReference(TodolistReference reference) {
		this.setId(reference.getId());
		this.setDescription(reference.getDescription());
		this.setUpdatedAt(reference.getUpdatedAt());
		this.setUrl(reference.getUrl());
		this.setCompleted(reference.getCompleted());
		this.setPosition(reference.getPosition());
		this.setIsPrivate(reference.getIsPrivate());
		this.setTrashed(reference.getTrashed());
		this.setCompletedCount(reference.getCompletedCount());
		this.setRemaninigCount(reference.getRemaninigCount());
		this.setCreator(reference.getCreator());
		this.setBucket(reference.getBucket());
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
	@XmlElement(name = "updatedAt", required = true)
	public Date getUpdatedAt() {
		return _updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this._updatedAt = updatedAt;
	}
	@XmlElement(name = "url", required = true)
	public String getUrl() {
		return _url;
	}
	public void setUrl(String url) {
		this._url = url;
	}
	@XmlElement(name = "completed", required = true)
	public Boolean getCompleted() {
		return _completed;
	}
	public void setCompleted(Boolean completed) {
		this._completed = completed;
	}
	@XmlElement(name = "position", required = true)
	public long getPosition() {
		return _position;
	}
	public void setPosition(long position) {
		this._position = position;
	}
	@XmlElement(name = "isPrivate", required = true)
	public Boolean getIsPrivate() {
		return _isPrivate;
	}
	public void setIsPrivate(Boolean isPrivate) {
		this._isPrivate = isPrivate;
	}
	@XmlElement(name = "trashed", required = true)
	public Boolean getTrashed() {
		return _trashed;
	}
	public void setTrashed(Boolean trashed) {
		this._trashed = trashed;
	}
	@XmlElement(name = "completedCount", required = true)
	public long getCompletedCount() {
		return _completedCount;
	}
	public void setCompletedCount(long completedCount) {
		this._completedCount = completedCount;
	}
	@XmlElement(name = "remaninigCount", required = true)
	public long getRemaninigCount() {
		return _remaninigCount;
	}
	public void setRemaninigCount(long remaninigCount) {
		this._remaninigCount = remaninigCount;
	}
	@XmlElement(name = "creator", required = true)
	public CreatorItem getCreator() {
		return _creator;
	}
	public void setCreator(CreatorItem creator) {
		this._creator = creator;
	}
	@XmlElement(name = "bucket", required = true)
	public BucketItem getBucket() {
		return _bucket;
	}
	public void setBucket(BucketItem bucket) {
		this._bucket = bucket;
	}

	private long _id;
	private String _name;
	private String _description;
	private Date _updatedAt;
	private String _url;
	private Boolean _completed;
	private long _position;
	private Boolean _isPrivate;
	private Boolean _trashed;
	private long _completedCount;
	private long _remaninigCount;
	private CreatorItem _creator;
	private BucketItem _bucket;

}

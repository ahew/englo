package org.entando.entando.plugins.jpjenkins.apsadmin.jenkins;

import com.agiletec.apsadmin.system.BaseAction;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.entando.entando.plugins.jpjenkins.aps.services.jenkins.IJenkinsClientManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JenkinsAction extends BaseAction {
	
	private static final Logger _logger = LoggerFactory.getLogger(JenkinsAction.class);
	
	public String list() throws Exception {
		return SUCCESS;
	}
	
	public String newjob() throws Exception {
		try {
			this.getJenkinsClientManager().saveAndBuild(this.getJobname(), this.getGithub(), this.getUrl(), this.getBuild_on_change());
		} catch (Throwable t) {
			_logger.error("Error in newjob", t);
			return FAILURE;
		}
		return SUCCESS;
	}
	
	public String build() throws Exception {
		try {
			this.getJenkinsClientManager().build(this.getJobname());
		} catch (Throwable t) {
			_logger.error("Error in build", t);
			return FAILURE;
		}
		return SUCCESS;
	}
	
	public String jsoninfo() throws Exception {
		try {
			String json = this.getJenkinsClientManager().getInfo();
			this.setJsoninfo(json);
			this.createStream(json);
		} catch (Throwable t) {
			_logger.error("Error in jsoninfo", t);
			return FAILURE;
		}
		return SUCCESS;
	}
	
	public String jsonlogs() throws Exception {
		try {
			String json = this.getJenkinsClientManager().getLogs(this.getJob());
			this.setJsonlogs(json);
			this.createStream(json);
		} catch (Throwable t) {
			_logger.error("Error in jsonlogs", t);
			return FAILURE;
		}
		return SUCCESS;
	}
	
	private void createStream(String json) throws Throwable {
		if (null != json) {
			this.setInputStream(new ByteArrayInputStream(json.getBytes("UTF-8")));
		}
	}
	
	public String getJsoninfo() {
		return _jsoninfo;
	}
	public void setJsoninfo(String _jsoninfo) {
		this._jsoninfo = _jsoninfo;
	}

	public String getJsonlogs() {
		return _jsonlogs;
	}
	public void setJsonlogs(String _jsonlogs) {
		this._jsonlogs = _jsonlogs;
	}

	public String getJob() {
		return _job;
	}
	public void setJob(String _job) {
		this._job = _job;
	}

	public String getGithub() {
		return _github;
	}
	public void setGithub(String _github) {
		this._github = _github;
	}

	public String getUrl() {
		return _url;
	}
	public void setUrl(String _url) {
		this._url = _url;
	}

	public String getBuild_on_change() {
		return _build_on_change;
	}
	public void setBuild_on_change(String _build_on_change) {
		this._build_on_change = _build_on_change;
	}
	
	public String getJobname() {
		return _jobname;
	}
	public void setJobname(String _jobname) {
		this._jobname = _jobname;
	}
	
	public InputStream getInputStream() {
        return _inputStream;
    }
    protected void setInputStream(InputStream inputStream) {
        this._inputStream = inputStream;
    }
	
	public IJenkinsClientManager getJenkinsClientManager() {
		return _jenkinsClientManager;
	}
	public void setJenkinsClientManager(IJenkinsClientManager _jenkinsClientManager) {
		this._jenkinsClientManager = _jenkinsClientManager;
	}
	
	private String _jsoninfo;
	private String _jsonlogs;
	private String _job;

	private String _jobname;
	private String _github;
	private String _url;
	private String _build_on_change;
	
	private InputStream _inputStream;

	private IJenkinsClientManager _jenkinsClientManager;
	
}

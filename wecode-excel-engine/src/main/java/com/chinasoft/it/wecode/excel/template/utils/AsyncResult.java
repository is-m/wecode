package com.chinasoft.it.wecode.excel.template.utils;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 异步结果
 * 
 * @author Administrator
 *
 */
public class AsyncResult {

	private String taskId;

	private String status;

	private String error;

	private String filePath;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public AsyncResult() {
	}

	public AsyncResult(String taskId, String status, String error, String filePath) {
		this.taskId = taskId;
		this.status = status;
		this.error = error;
		this.filePath = filePath;
	}

	public static AsyncResult ok(String taskId, String filePath) {
		return new AsyncResult(taskId, "success", null, filePath);
	}

	public static AsyncResult error(String taskId, String errorMsg) {
		return new AsyncResult(taskId, "error", errorMsg, null);
	}

}

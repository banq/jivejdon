/*
 * Copyright 2007 the original author or jdon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.jdon.jivejdon.presentation.servlet.upload;

import javax.servlet.http.HttpServletRequest;

import com.jdon.jivejdon.presentation.servlet.upload.strutsplugin.OutputStreamListener;

public class FileUploadListener implements OutputStreamListener {
	private FileUploadStats fileUploadStats = new FileUploadStats();

	public FileUploadListener(long totalSize) {
		fileUploadStats.setTotalSize(totalSize);
	}

	public void start() {
		fileUploadStats.setCurrentStatus(FileUploadStatus.START);
	}

	public void bytesRead(int byteCount) {
		fileUploadStats.incrementBytesRead(byteCount);
		fileUploadStats.setCurrentStatus(FileUploadStatus.READING);
	}

	public void error(String s) {
		fileUploadStats.setCurrentStatus(FileUploadStatus.ERROR);
	}

	public static void error(HttpServletRequest request) {
		FileUploadStats stats = new FileUploadListener.FileUploadStats();
		stats.setCurrentStatus(FileUploadStatus.ERROR);
		request.getSession().setAttribute("FILE_UPLOAD_STATS", stats);
	}

	public void done() {
		fileUploadStats.setBytesRead(fileUploadStats.getTotalSize());
		fileUploadStats.setCurrentStatus(FileUploadStatus.DONE);
	}

	public FileUploadStats getFileUploadStats() {
		return fileUploadStats;
	}

	public static class FileUploadStats implements java.io.Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private long totalSize = 0;

		private long bytesRead = 0;

		private double percentComplete = 0.0;

		private long startTime = System.currentTimeMillis();

		private FileUploadStatus currentStatus = FileUploadStatus.NONE;

		public long getTotalSize() {
			return totalSize;
		}

		public void setTotalSize(long totalSize) {
			this.totalSize = totalSize;
		}

		public long getBytesRead() {
			return bytesRead;
		}

		public long getElapsedTimeInMilliseconds() {
			return (System.currentTimeMillis() - startTime);
		}

		public FileUploadStatus getCurrentStatus() {
			return currentStatus;
		}

		public double getPercentComplete() {
			if (totalSize != 0) {
				percentComplete = (double) bytesRead / (double) totalSize;
			}
			return percentComplete;
		}

		public void setCurrentStatus(FileUploadStatus currentStatus) {
			this.currentStatus = currentStatus;
		}

		public void setBytesRead(long bytesRead) {
			this.bytesRead = bytesRead;
		}

		public void incrementBytesRead(int byteCount) {
			this.bytesRead += byteCount;
		}
	}

	enum FileUploadStatus {
		START("start"), NONE("none"), READING("reading"), ERROR("error"), DONE("done");

		private String type;

		FileUploadStatus(String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}
	}
}

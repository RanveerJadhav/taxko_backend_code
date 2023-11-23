package com.Tasko.Registration.Controller;

public class EmailRequest {
	 private String recipient;
	    private String subject;
	    private String text;
	    private byte[] attachmentContent;
	    private String attachmentFileName;
		public String getRecipient() {
			return recipient;
		}
		public void setRecipient(String recipient) {
			this.recipient = recipient;
		}
		public String getSubject() {
			return subject;
		}
		public void setSubject(String subject) {
			this.subject = subject;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public byte[] getAttachmentContent() {
			return attachmentContent;
		}
		public void setAttachmentContent(byte[] attachmentContent) {
			this.attachmentContent = attachmentContent;
		}
		public String getAttachmentFileName() {
			return attachmentFileName;
		}
		public void setAttachmentFileName(String attachmentFileName) {
			this.attachmentFileName = attachmentFileName;
		}
	    
	    
}

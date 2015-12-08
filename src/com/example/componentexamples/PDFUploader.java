package com.example.componentexamples;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

public class PDFUploader implements Receiver, SucceededListener{
	public File file;
	public PDFUploader() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void uploadSucceeded(SucceededEvent event) {
		// TODO Auto-generated method stub
		new Notification("Uoload Successful","Your file was uploaded sucessfully.",Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
		
	}

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		// TODO Auto-generated method stub
		
		if(mimeType.equals("application/pdf")){
			new Notification("Please upload only PDF File<br/>","",Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
			return null;
		}
		// Create upload stream
		FileOutputStream fos = null; // Stream to write to
		try {
		// Open the file for writing.
		file = new File("D:\\" + filename);
		fos = new FileOutputStream(file);
		} catch (final java.io.FileNotFoundException e) {
		new Notification("Could not open file<br/>",e.getMessage(),Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
		return null;
		}
		return fos; // Return the output stream to write to
	}

}

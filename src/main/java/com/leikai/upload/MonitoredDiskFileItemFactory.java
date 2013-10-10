package com.leikai.upload;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;

import com.leikai.upload.MonitoredDiskFileItem;
import com.leikai.upload.UploadListeners;

import java.io.File;

public class MonitoredDiskFileItemFactory extends DiskFileItemFactory
{
    private UploadListeners listener = null;

    public MonitoredDiskFileItemFactory(UploadListeners listener)
    {
        super();
        this.listener = listener;
    }

    public MonitoredDiskFileItemFactory(int sizeThreshold, File repository, UploadListeners listener)
    {
        super(sizeThreshold, repository);
        this.listener = listener;
    }

    public FileItem createItem(String fieldName, String contentType, boolean isFormField, String fileName)
    {
        return new MonitoredDiskFileItem(fieldName, contentType, isFormField, fileName, getSizeThreshold(), getRepository(), listener);
    }
}

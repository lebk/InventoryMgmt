package com.leikai.upload;

import org.apache.commons.fileupload.disk.DiskFileItem;

import java.io.File;
import java.io.OutputStream;
import java.io.IOException;

public class MonitoredDiskFileItem extends DiskFileItem
{
    private MonitoredOutputStream mos = null;
    private UploadListeners listener;

    public MonitoredDiskFileItem(String fieldName, String contentType, boolean isFormField, String fileName, int sizeThreshold, File repository, UploadListeners listener)
    {
        super(fieldName, contentType, isFormField, fileName, sizeThreshold, repository);
        this.listener = listener;
    }

    public OutputStream getOutputStream() throws IOException
    {
        if (mos == null)
        {
            mos = new MonitoredOutputStream(super.getOutputStream(), listener);
        }
        return mos;
    }
}

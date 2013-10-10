package com.leikai.upload;

public interface UploadListeners
{
    public void start();
    public void bytesRead(int bytesRead);
    public void error(String message);
    public void done();
}

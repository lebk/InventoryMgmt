package com.leikai.upload;

import java.io.OutputStream;
import java.io.IOException;

public class MonitoredOutputStream extends OutputStream
{
    private OutputStream target;
    private UploadListeners listener;

    public MonitoredOutputStream(OutputStream target, UploadListeners listener)
    {
        this.target = target;
        this.listener = listener;
        this.listener.start();
    }

    @Override
	public void write(byte b[], int off, int len) throws IOException
    {
        target.write(b,off,len);
        listener.bytesRead(len - off);
    }

    @Override
	public void write(byte b[]) throws IOException
    {
        target.write(b);
        listener.bytesRead(b.length);
    }

    @Override
	public void write(int b) throws IOException
    {
        target.write(b);
        listener.bytesRead(1);
    }

    @Override
	public void close() throws IOException
    {
        target.close();
        listener.done();
    }

    @Override
	public void flush() throws IOException
    {
        target.flush();
    }
}

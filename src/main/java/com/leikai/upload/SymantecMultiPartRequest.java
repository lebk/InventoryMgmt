
package com.leikai.upload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsConstants;
import org.apache.struts2.dispatcher.multipart.MultiPartRequest;

import com.opensymphony.xwork2.inject.Inject;

public class SymantecMultiPartRequest implements MultiPartRequest {

  static final Log log = LogFactory.getLog(MultiPartRequest.class);

  // maps parameter name -> List of FileItem objects
  private Map<String, List<FileItem>> files = new HashMap<String, List<FileItem>>();
  // maps parameter name -> List of param values
  private Map<String, List<String>> params = new HashMap<String, List<String>>();
  // any errors while processing this request
  private List<String> errors = new ArrayList<String>();

  private long maxSize;

  @Inject(StrutsConstants.STRUTS_MULTIPART_MAXSIZE)
  public void setMaxSize(String maxSize) {
    this.maxSize = Long.parseLong(maxSize);
  }

  public void parse(HttpServletRequest servletRequest, String saveDir)
      throws IOException {
  
    //Initialize monitor
    UploadListenersImpl listener = new UploadListenersImpl(servletRequest,0);

    DiskFileItemFactory factory = new MonitoredDiskFileItemFactory(listener);
 
    //DiskFileItemFactory fac = new DiskFileItemFactory();
    // Make sure that the data is written to file
    //fac.setSizeThreshold(0);
    factory.setSizeThreshold(0);
    if (saveDir != null) {
      //fac.setRepository(new File(saveDir));
      factory.setRepository(new File(saveDir));
    }

    // Parse the request
    try {
      ServletFileUpload upload = new ServletFileUpload(factory);
      upload.setSizeMax(maxSize);
      
      List items = upload.parseRequest(createRequestContext(servletRequest));
      for (Object item1 : items) {
        FileItem item = (FileItem) item1;
        if (log.isDebugEnabled())
          log.debug("Found item " + item.getFieldName());
        if (item.isFormField()) {
          log.debug("Item is a normal form field");
          List<String> values;
          if (params.get(item.getFieldName()) != null) {
            values = params.get(item.getFieldName());
          } else {
            values = new ArrayList<String>();
          }

          // note: see http://jira.opensymphony.com/browse/WW-633
          // basically, in some cases the charset may be null, so
          // we're just going to try to "other" method (no idea if
          // this
          // will work)
          String charset = servletRequest.getCharacterEncoding();
          if (charset != null) {
            values.add(item.getString(charset));
          } else {
            values.add(item.getString());
          }
          params.put(item.getFieldName(), values);
        } else {
          log.debug("Item is a file upload");

          // Skip file uploads that don't have a file name - meaning
          // that no file was selected.
          if (item.getName() == null
              || item.getName().trim().length() < 1) {
            log.debug("No file has been uploaded for the field: "
                + item.getFieldName());
            continue;
          }

          List<FileItem> values;
          if (files.get(item.getFieldName()) != null) {
            values = files.get(item.getFieldName());
          } else {
            values = new ArrayList<FileItem>();
          }

          values.add(item);
          files.put(item.getFieldName(), values);
        }
      }
    } catch (FileUploadException e) {
      log.error(e);
      errors.add(e.getMessage());
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.apache.struts2.dispatcher.multipart.MultiPartRequest#getFileParameterNames()
   */
  public Enumeration<String> getFileParameterNames() {
    return Collections.enumeration(files.keySet());
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.apache.struts2.dispatcher.multipart.MultiPartRequest#getContentType(java.lang.String)
   */
  public String[] getContentType(String fieldName) {
    List items = (List) files.get(fieldName);

    if (items == null) {
      return null;
    }

    List<String> contentTypes = new ArrayList<String>(items.size());
    for (int i = 0; i < items.size(); i++) {
      FileItem fileItem = (FileItem) items.get(i);
      contentTypes.add(fileItem.getContentType());
    }

    return (String[]) contentTypes.toArray(new String[contentTypes.size()]);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.apache.struts2.dispatcher.multipart.MultiPartRequest#getFile(java.lang.String)
   */
  public File[] getFile(String fieldName) {
    List items = (List) files.get(fieldName);

    if (items == null) {
      return null;
    }

    List<File> fileList = new ArrayList<File>(items.size());
    for (int i = 0; i < items.size(); i++) {
      DiskFileItem fileItem = (DiskFileItem) items.get(i);
      fileList.add(fileItem.getStoreLocation());
    }

    return (File[]) fileList.toArray(new File[fileList.size()]);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.apache.struts2.dispatcher.multipart.MultiPartRequest#getFileNames(java.lang.String)
   */
  public String[] getFileNames(String fieldName) {
    List<FileItem> items = files.get(fieldName);

    if (items == null) {
      return null;
    }

    List<String> fileNames = new ArrayList<String>(items.size());
    for (int i = 0; i < items.size(); i++) {
      DiskFileItem fileItem = (DiskFileItem) items.get(i);
      fileNames.add(getCanonicalName(fileItem.getName()));
    }

    return (String[]) fileNames.toArray(new String[fileNames.size()]);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.apache.struts2.dispatcher.multipart.MultiPartRequest#getFilesystemName(java.lang.String)
   */
  public String[] getFilesystemName(String fieldName) {
    List items = (List) files.get(fieldName);

    if (items == null) {
      return null;
    }

    List<String> fileNames = new ArrayList<String>(items.size());
    for (int i = 0; i < items.size(); i++) {
      DiskFileItem fileItem = (DiskFileItem) items.get(i);
      fileNames.add(fileItem.getStoreLocation().getName());
    }

    return (String[]) fileNames.toArray(new String[fileNames.size()]);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.apache.struts2.dispatcher.multipart.MultiPartRequest#getParameter(java.lang.String)
   */
  public String getParameter(String name) {
    List v = (List) params.get(name);
    if (v != null && v.size() > 0) {
      return (String) v.get(0);
    }

    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.apache.struts2.dispatcher.multipart.MultiPartRequest#getParameterNames()
   */
  public Enumeration<String> getParameterNames() {
    return Collections.enumeration(params.keySet());
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.apache.struts2.dispatcher.multipart.MultiPartRequest#getParameterValues(java.lang.String)
   */
  public String[] getParameterValues(String name) {
    List<String> v = params.get(name);
    if (v != null && v.size() > 0) {
      return (String[]) v.toArray(new String[v.size()]);
    }

    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.apache.struts2.dispatcher.multipart.MultiPartRequest#getErrors()
   */
  public List getErrors() {
    return errors;
  }

  /**
   * Returns the canonical name of the given file.
   * 
   * @param filename
   *            the given file
   * @return the canonical name of the given file
   */
  private String getCanonicalName(String filename) {
    int forwardSlash = filename.lastIndexOf("/");
    int backwardSlash = filename.lastIndexOf("\\");
    if (forwardSlash != -1 && forwardSlash > backwardSlash) {
      filename = filename.substring(forwardSlash + 1, filename.length());
    } else if (backwardSlash != -1 && backwardSlash >= forwardSlash) {
      filename = filename.substring(backwardSlash + 1, filename.length());
    }

    return filename;
  }

  /**
   * Creates a RequestContext needed by Jakarta Commons Upload.
   * 
   * @param req
   *            the request.
   * @return a new request context.
   */
  private RequestContext createRequestContext(final HttpServletRequest req) {
    return new RequestContext() {
      public String getCharacterEncoding() {
        return req.getCharacterEncoding();
      }

      public String getContentType() {
        return req.getContentType();
      }

      public int getContentLength() {
        return req.getContentLength();
      }

      public InputStream getInputStream() throws IOException {
        return req.getInputStream();
      }
    };
  }
}

package org.gaixie.micrite.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.gaixie.micrite.beans.Dictionary;
import org.gaixie.micrite.beans.Check;
import org.gaixie.micrite.car.service.ICarfileService;
import org.gaixie.micrite.check.service.ICheckService;
import org.gaixie.micrite.util.DictionaryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hansheng.njj.Constants;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;

/**
 * Sample action that shows how to do file upload with Struts 2.
 */
public class FileUploadAction extends ActionSupport {
	private static final long serialVersionUID = -9208910183310010569L;
	Logger log = Logger.getLogger(FileUploadAction.class);
	private File file;
	private String type;

	public void setType(String type) {
		this.type = type;
	}

	private String fileContentType;
	private String fileFileName;
	private String name;
	private boolean success = false;

	public boolean isSuccess() {
		return success;
	}

	private Map<String, Object> resultMap = new HashMap<String, Object>();

	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	protected HttpServletResponse getResponse() {
		// TODO Auto-generated method stub
		return ServletActionContext.getResponse();
	}

	@Autowired
	private ICarfileService carfileService;

	public ICarfileService getCarfileService() {
		return carfileService;
	}

	public void setCarfileService(ICarfileService carfileService) {
		this.carfileService = carfileService;
	}

	@Autowired
	private ICheckService checkService;

	public void setCheckService(ICheckService checkService) {
		this.checkService = checkService;
	}

	/**
	 * Upload the file
	 * 
	 * @return String with result (cancel, input or sucess)
	 * @throws Exception
	 *             if something goes wrong
	 */
	public String uploadCar() throws Exception {
		Map<String, String> res = null;
		IDealWith dealWith;
		ApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getRequest()
						.getSession().getServletContext());
		dealWith = (IDealWith) ctx.getBean("dealWithCar");
		dealWith.doJob(file, res);
		this.getResultMap().put("message", getText("upload.success"));
		this.success = true;
		return SUCCESS;
	}

	public String uploadEnterprise() throws Exception {
		Map<String, String> res = null;
		IDealWith dealWith;
		ApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getRequest()
						.getSession().getServletContext());
		dealWith = (IDealWith) ctx.getBean("dealWithEnterprise");
		dealWith.doJob(file, res);
		this.getResultMap().put("message", getText("upload.success"));
		this.success = true;
		return SUCCESS;
	}

	public String uploadDat() throws Exception {
		Map<String, String> res = null;
		IDealWith dealWith;
		res = new HashMap<String, String>();
		res.put("jiance.date.format", getText("jiance.date.format"));
		ApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getRequest()
						.getSession().getServletContext());
		dealWith = (IDealWith) ctx.getBean("dealWithCheck");
		dealWith.doJob(file, res);
		this.getResultMap().put("message", getText("upload.success"));
		this.success = true;
		return SUCCESS;
	}

	public String uploadAuto() throws Exception {
		// log.debug("type=" + this.type);
		Map<String, String> res = null;
		IDealWith dealWith;

		res = new HashMap<String, String>();
		res.put("jiance.date.format", getText("jiance.date.format"));
		res.put("fileFileName", fileFileName);
		res.put("sunJLEncryption", getRequest().getSession()
				.getServletContext().getInitParameter("sunJLEncryption"));
		ApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getRequest()
						.getSession().getServletContext());
		dealWith = (IDealWith) ctx.getBean("dealWithSunJL");
		if (dealWith.doJob(file, res) == IDealWith.OK) {
			getRequest().setAttribute("ret", "OK");
		} else {
			getRequest().setAttribute("ret", "bad");
		}
		return "autoDat";
	}

	private String upload() throws Exception {
		// the directory to upload to
		String uploadDir = ServletActionContext.getServletContext()
				.getRealPath("/resources")
				+ "/" + getRequest().getRemoteUser() + "/";
		log.debug(uploadDir);
		// write the file to the file specified
		File dirPath = new File(uploadDir);

		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}

		// retrieve the file data
		InputStream stream = new FileInputStream(file);

		// write the file to the file specified
		OutputStream bos = new FileOutputStream(uploadDir + fileFileName);
		int bytesRead;
		byte[] buffer = new byte[8192];

		while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
			bos.write(buffer, 0, bytesRead);
		}

		bos.close();
		stream.close();

		// place the data into the request for retrieval on next page
		getRequest().setAttribute("location",
				dirPath.getAbsolutePath() + Constants.FILE_SEP + fileFileName);

		String link = getRequest().getContextPath() + "/resources" + "/"
				+ getRequest().getRemoteUser() + "/";
		log.debug("link=" + link);
		getRequest().setAttribute("link", link + fileFileName);

		// this.getResultMap().put("success", true);
		// getResponse().setContentType("text/plain");
		this.getResultMap().put("message", getText("upload.success"));
		this.success = true;
		return SUCCESS;
	}

	/**
	 * Default method - returns "input"
	 * 
	 * @return "input"
	 */
	public String execute() {
		return INPUT;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public File getFile() {
		return file;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	@Override
	public void validate() {
		if (getRequest().getMethod().equalsIgnoreCase("post")) {
			getFieldErrors().clear();
			if ("".equals(fileFileName) || file == null) {
				super.addFieldError("file", getText("errors.requiredField",
						new String[] { getText("uploadForm.file") }));
			} else if (file.length() > 2097152) {
				addActionError(getText("maxLengthExceeded"));
			}
		}
	}
}

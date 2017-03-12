package org.gaixie.micrite.upload;

import java.io.File;
import java.util.Map;

public interface IDealWith{
	public static final int OK = 0;
	/**业务不相关*/
	public int doJob(File src,Map<String, String> res)throws Exception;
}

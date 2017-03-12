/* ===========================================================
 * $Id: LoginAction.java 406 2009-07-13 02:43:35Z bitorb $
 * This file is part of Micrite
 * ===========================================================
 *
 * (C) Copyright 2009, by Gaixie.org and Contributors.
 * 
 * Project Info:  http://micrite.gaixie.org/
 *
 * Micrite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Micrite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Micrite.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.gaixie.micrite.security.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gaixie.micrite.beans.Setting;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.security.service.ILoginService;
import org.gaixie.micrite.security.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionSupport;
 
/**
 * 登录用户管理
 * @see com.opensymphony.xwork2.ActionSupport
 */
public class LoginAction extends ActionSupport{ 
	private static final long serialVersionUID = -5277215719944190914L;

	@Autowired
	private ILoginService loginService;
    @Autowired
    private IUserService userService;
    
	private User user;
    private String node;
	private List<Map<String,Object>> menu;
	private String pageSize;
	private String skin;
	private Map<String,Object> loginResult = new HashMap<String,Object>();
	private Map<String,String> errorMsg = new HashMap<String,String>();
public Map<String, String> getErrorMsg() {
        return errorMsg;
    }    

    public Map<String, Object> getLoginResult() {
        return loginResult;
    }

    public List<Map<String,Object>> getMenu() {
        return menu;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~  Accessor Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    
	/**
	 * @return node
	 */
	public String getNode() {
		return node;
	}
    
    /**
     * @return the pageSize
     */
    public String getPageSize() {
        return pageSize;
    }

    public String getSkin() {
        return skin;
    }
    
    /**
     * @return the user
     */
    public User getUser() {
		return user;
	}

	/**
     * 加载当前菜单节点的下一级菜单
     * @return "success"
     */	
	public String loadMenu(){
        User currentUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		menu = loginService.loadChildNodes(currentUser,node);
		System.out.println(menu);
		return SUCCESS;
	}

    /**
     * 加载当前用户的配置项
     * @return "success"
     */ 	
    public String loadSetting(){
        User cUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Setting> settings = userService.getSettings(cUser.getId());
        for(Setting setting:settings){
            if("RowsPerPage".equals(setting.getName())){
                pageSize = setting.getValue();
            }
            if("Skin".equals(setting.getName())){
                skin = setting.getValue();
            }
        }
        return SUCCESS;
    }

    /**
     * 登录失败，返回失败原因
     * @return "success"
     */	
	public String loginFailed(){
		errorMsg.put("reason", getText("error.login.authenticationFailed"));
		loginResult.put( "success", false );  
		loginResult.put( "errorMsg", errorMsg );
//		errorMsg=getText("error.login.authenticationFailed");
//		success=false;
		return SUCCESS;
	}
    
    //	private boolean success = false;
//	private String errorMsg=null;
    // ~~~~~~~~~~~~~~~~~~~~~~~  Action Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    
	/**
	 * 默认起始事件，获得登录用户信息
	 * @return "success"
	 */
	public String loginSuccess() {
		loginResult.put("success", true);
//		success=true;
		return SUCCESS;
    }

    public void setErrorMsg(Map<String, String> errorMsg) {
        this.errorMsg = errorMsg;
    }
    
    public void setLoginResult(Map<String, Object> loginResult) {
        this.loginResult = loginResult;
    }

    public void setMenu(List<Map<String,Object>> menu) {
        this.menu = menu;
    }

    /**
     * @param node 菜单上选择的节点id
     */
    public void setNode(String node) {
        this.node = node;
    }

//    public boolean isSuccess() {
//		return success;
//	}
//
//	public void setSuccess(boolean success) {
//		this.success = success;
//	}
//
//	public String getErrorMsg() {
//		return errorMsg;
//	}
//
//	public void setErrorMsg(String errorMsg) {
//		this.errorMsg = errorMsg;
//	}

	/**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }


    /**
	 * @param user 用户实体
	 */
	public void setUser(User user) {
		this.user = user;
	}    
}

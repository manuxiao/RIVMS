/* ===========================================================
 * $Id: UserAction.java 515 2009-08-23 16:09:30Z bitorb $
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

import org.apache.log4j.Logger;
import org.gaixie.micrite.beans.Dictionary;
import org.gaixie.micrite.beans.Setting;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.security.SecurityException;
import org.gaixie.micrite.security.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionSupport;
/**
 * 用户管理，提供新增，修改，查询用户等功能。
 */
//@ManagedResource(objectName="micrite:type=action,name=UserAction", description="Micrite UserAction Bean")
public class UserAction extends ActionSupport {

    private static final long serialVersionUID = 5843976450199930680L;

    private static final Logger logger = Logger.getLogger(UserAction.class);

    //  用户管理服务，本类要调用它来完成功能
    @Autowired
    private IUserService userService;
    
    //  用户
    private User user;
    //  用户角色id
    private int roleId;

    //  用户id数组
    private int[] userIds;
    
    private boolean binded;
    //状态
    boolean success = false;
    //用户列表
    List<User>data=null;    
    //  以下两个分页用
    //  起始索引
    private int start;
    //  限制数
    private int limit;
    //  记录总数（分页中改变页码时，会传递该参数过来）
    private int totalCount;

    //  action处理结果（map对象）
    private Map<String,Object> resultMap = new HashMap<String,Object>();
    
    // user setting
    private List<Setting> settings;
    
    // ~~~~~~~~~~~~~~~~~~~~~~~  Action Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//
    /**
     * 新增用户。
     * 
     * @return "success"
     */
    public String add() {
        try {
            userService.add(user);
            resultMap.put("message", getText("save.success"));
            resultMap.put("success", true);
        } catch (SecurityException e) {
            resultMap.put("message", getText(e.getMessage()));
            resultMap.put("success", false);
            logger.error(getText(e.getMessage()));
        }
        return SUCCESS;
    }

    /**
     * 将用户绑定到角色。
     * 
     * @return "success"
     */    
    public String bindUsers() {
        userService.bindUsers(userIds,roleId);
        resultMap.put("message", getText("save.success"));
        resultMap.put("success", true);
        return SUCCESS;
    }

    /**
     * 删除若干用户。
     * 
     * @return "success"
     */
    public String delete() {
        userService.delete(userIds);
        resultMap.put("message", getText("save.success"));
        resultMap.put("success", true);
        return SUCCESS;
    }
    
    /**
     * 根据角色查询用户。
     * 
     * @return "success"
     */    
    public String findBindedUsers() {
        
        if(!binded) return findByFullnameVague();

        if (totalCount == 0) {
            //  初次查询时，要从数据库中读取总记录数
            Integer count = userService.findUsersByRoleIdCount(roleId);
            setTotalCount(count);
        } 
        
        List<User> users = userService.findUsersByRoleIdPerPage(roleId, start, limit);
        resultMap.put("totalCount", totalCount);    
        resultMap.put("success", true);
        resultMap.put("data", users);

        return SUCCESS;
    }

    /**
     * 根据用户名查询用户集合（模糊查询）。
     * 
     * @return "success"
     */
	
    public String findByFullnameVague() {
        String fullname = user.getFullname();
        if (totalCount == 0) {
            //  初次查询时，要从数据库中读取总记录数
        	totalCount = userService.findByFullnameVagueCount(fullname);
        } 
        //  得到分页查询结果
//         data = userService.findByFullnameVaguePerPage(fullname, start, limit);
        
        List<User> users = userService.findByFullnameVaguePerPage(fullname, start, limit);        
        resultMap.put("totalCount", totalCount);
        resultMap.put("success", true);
        resultMap.put("data", users);
//        success=true;
        //  得到分页查询结果
        return SUCCESS;
    }

    /**
     * 根据名称查找可选择配置项。
     * 
     * @return "success"
     */    
    public String findSettingByName(){
    	setSettings(userService.findSettingByName(settings.get(0).getName()));
    	return SUCCESS;
    }
    
    public List<User> getData() {
		return data;
	}

    public Map<String, Object> getResultMap() {
        return resultMap;
    }

    public List<Setting> getSettings() {
		return settings;
	}

    public User getUser() {
        return user;
    }

    public boolean isBinded() {
        return binded;
    }
    
    public boolean isSuccess() {
		return success;
	}

    /**
     * 加载当前用户
     * 
     * @return "success"
     */
    public String loadCurrentUser() {
        User currentUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String,Object> userMap = new HashMap<String,Object>();
        userMap.put("user.id", currentUser.getId());
        userMap.put("user.fullname", currentUser.getFullname());
        userMap.put("user.emailaddress", currentUser.getEmailaddress());
        userMap.put("user.loginname", currentUser.getLoginname());
        resultMap.put("data", userMap);
        resultMap.put("settings", userService.getSettings(currentUser.getId()));
        resultMap.put("success", true);
        return SUCCESS;
    }

    public void setBinded(boolean binded) {
        this.binded = binded;
    }

    public void setData(List<User> data) {
		this.data = data;
	}

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public void setSettings(List<Setting> settings) {
		this.settings = settings;
	}
    
    public void setStart(int start) {
        this.start = start;
    }

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
	
    // ~~~~~~~~~~~~~~~~~~~~~~~  Accessor Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    
    public void setUser(User user) {
        this.user = user;
    }
    
    public void setUserIds(int[] userIds) {
        this.userIds = userIds;
    }    
        
    /**
     * 将用户从角色上解除绑定。
     * 
     * @return "success"
     */    
    public String unBindUsers() {
        userService.unBindUsers(userIds,roleId);
        resultMap.put("message", getText("save.success"));
        resultMap.put("success", true);
        return SUCCESS;
    }

	/**
     * 通过用户列表修改用户信息。
     * 
     * @return "success"
     */
    public String update() {
        try {
            userService.update(user);
            resultMap.put("message", getText("save.success"));
            resultMap.put("success", true);
        } catch (SecurityException e) {
            resultMap.put("message", getText(e.getMessage()));
            resultMap.put("success", false);
            logger.error(getText(e.getMessage()));
        }
        return SUCCESS;
    }

	/**
     * 修改用户信息。
     * 
     * @return "success"
     */
    public String updateMe() {
    	user.setSettings(settings);
        try {
            userService.updateMe(user);
            resultMap.put("message", getText("save.success"));
            resultMap.put("success", true);
        } catch (SecurityException e) {
            resultMap.put("message", getText(e.getMessage()));
            resultMap.put("success", false);
            logger.error(getText(e.getMessage()));
        }
        return SUCCESS;
    }

	/**
     * 设置用户状态可用/不可用。
     * 
     * @return "success"
     */
    public String updateStatus() {
        userService.updateStatus(userIds);
        resultMap.put("message", getText("save.success"));
        resultMap.put("success", true);
        return SUCCESS;
    }
    private List<Dictionary> list;
    
    public List<Dictionary> getList() {
		return list;
	}

	/**获取用户名和用户id*/
    public String getUserIdName(){
    	list = userService.getUserIdName();

//		Dictionary tempSource = new Dictionary();
//        tempSource.setId(-1);
//        tempSource.setName("全部");
//        list.add(0,tempSource);
        
    	return SUCCESS;
    }
}

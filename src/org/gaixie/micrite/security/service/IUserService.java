/* ===========================================================
 * $Id: IUserService.java 515 2009-08-23 16:09:30Z bitorb $
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

package org.gaixie.micrite.security.service;

import java.util.List;

import org.gaixie.micrite.beans.Dictionary;
import org.gaixie.micrite.beans.Setting;
import org.gaixie.micrite.beans.User;
import org.gaixie.micrite.security.SecurityException;

/**
 * 提供与用户管理有关的服务。
 * 
 */
public interface IUserService {
	/**
	 * 获取用户列表
	 * */
	public List<Dictionary>getUserIdName();
    /**
     * 增加新用户。
     * 
     * @param user 用户
     * @throws SecurityException 用户名已存在时抛出
     */
    public void add(User user) throws SecurityException;
    
    /**
     * 将用户绑定到角色。
     * 
     * @param userIds 用户id数组
     * @param roleId 角色id
     */    
    public void bindUsers(int[] userIds, int roleId);

    /**
     * 删除若干用户。
     * 
     * @param userIds 用户id数组
     */
    public void delete(int[] userIds);

    /**
     * 根据用户名查询用户的总数（模糊查询）。
     * 
     * @param fullname 真实姓名
     * @return 用户数量
     */
    public Integer findByFullnameVagueCount(String fullname);
    
    /**
     * 根据用户名查询用户集合（模糊查询，分页）。
     * 
     * @param fullname 真实姓名
     * @param start 起始索引
     * @param limit 限制数
     * @return 用户集合
     */
    public List<User> findByFullnameVaguePerPage(String fullname, int start, int limit);

    /**
     * 根据配置项名称查询可用配置
     * @param name
     * @return 配置集合
     */
    public List<Setting> findSettingByName(String name);
    
    
    /**
     * 根据角色查询用户对象记录数。
     * 
     * @param roleId 角色id
     * @return 用户数量
     */    
    public Integer findUsersByRoleIdCount(int roleId);

    /**
     * 根据角色查询用户对象集合
     * 
     * @param roleId 角色id
     * @param start 起始索引
     * @param limit 限制数
     * @return 用户集合
     */    
    public List<User> findUsersByRoleIdPerPage(int roleId, int start, int limit);
    
    /**
     * 根据用户查询所拥有的个性化配置
     * @param userId
     * @return 配置集合
     */    
    public List<Setting> getSettings(int userId);
    
    /**
     * 根据用户名判断用户是否已存在。
     * 
     * @param username 用户名
     * @return true:存在；false：不存在
     */
    public boolean isExistedByUsername(String username);

    /**
     * 将用户从角色上解除绑定。
     * 
     * @param userIds 用户id数组
     * @param roleId 角色id
     */    
    public void unBindUsers(int[] userIds, int roleId);

    /**
     * 修改用户信息
     * @param user 用户对象
     * @throws SecurityException 
     */
    public void update(User user) throws SecurityException;

    /**
     * 修改用户自身信息。
     * 密码不为空字串时才修改密码。
     * 
     * @param user 用户对象,可更新属性包括, 用户id,newFullname 新名称,
     * newEmailaddress 新email地址,newPlainpassword 新密码（明文）
     * user对象包括一个setting对象
     * @throws SecurityException 
     */
    public void updateMe(User user) throws SecurityException;

    /**
     * 设置用户状态可用/不可用：将原可用的设置为不可用，原不可用的设置为可用。
     * 
     * @param userIds 用户id数组
     */
    public void updateStatus(int[] userIds);
    
}

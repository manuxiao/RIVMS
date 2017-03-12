/* ===========================================================
 * $Id: ICustomerSourceDAO.java 520 2009-08-27 05:59:23Z bitorb $
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

package org.gaixie.micrite.dic.dao;

import java.util.List;

import org.gaixie.micrite.beans.Dictionary;
import org.gaixie.micrite.dao.IGenericDAO;
/**
 * 客户来源持久化接口
 */
public interface IDictionaryDAO extends IGenericDAO<Dictionary, Integer> {
	public void addItem(String name,int type,int show);
	public Dictionary get(String name,int type);
    /**
     * 获得所有CarDictionary
     * @return CarDictionary对象集合
     */
	public List<Dictionary> findAllDictionary(int type);
	public List<Dictionary> findAllDictionary(int type,int show);
    /**
     * 获得名称
     * */
    public String getName(int type,int value);
    public String getName(int id);
}

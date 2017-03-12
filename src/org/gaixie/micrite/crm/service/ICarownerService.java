/* ===========================================================
 * $Id: ICustomerService.java 516 2009-08-23 16:33:31Z bitorb $
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

package org.gaixie.micrite.crm.service;

import java.util.Date;
import java.util.List;

import org.gaixie.micrite.common.search.SearchBean;
import org.gaixie.micrite.beans.Carfile;
import org.gaixie.micrite.beans.Carowner;
import org.gaixie.micrite.beans.IdName;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;

/**
 * 客户管理服务接口，封装客户服务业务模型
 */
public interface ICarownerService {
	public static final int STATUS_NORMAL = 0;
	/**根据经营许可证找到业户对象
	 * */
	public Carowner getByLicense(String license);
//	public Carowner getByName(String name);
    public boolean existCarowner(Carowner carowner);
    /**
     * like py
     * */
    public List<IdName> findCarownerByPy(String py);
    /**
     * 新增车主业户
     * @param carowner 车主业户实体
     */
    public void save(Carowner owner);
    public List<Carowner> advancedFindByPerPage(SearchBean[] queryBean, int start, int limit);
//    public List<Carowner> advancedFindByPerPageALL(SearchBean[] queryBean, int start, int limit);

    public int advancedFindCount(SearchBean[] queryBean);

    public void delete(int[] customerIds);
    /**
     * 留修改日志
     * @param owner
     */
	    public boolean update(Carowner owner);

	    /**
	     * 简单接口
	     * */
	    public Carowner get(Integer ownerId);
}

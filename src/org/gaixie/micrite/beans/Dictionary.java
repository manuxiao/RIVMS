/* ===========================================================
 * $Id: CustomerSource.java 520 2009-08-27 05:59:23Z bitorb $
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
 
package org.gaixie.micrite.beans;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 字典表
 */
@Entity
@Table(name = "dictionary")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Dictionary extends AbstractSecureObject implements Serializable {
	/**系统参数的值*/
	public static final int VALUE_NO = 1;
    @Id
    @GeneratedValue
    private Integer id;
    private int type;
    private String name;
    private int value;
	@Column(name = "isShow")
    private int show;
    public Dictionary() {}
    public Dictionary(String name,int type) {this.name=name;this.type=type;}
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Accessor Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//     
    public Integer getId() {
        return id;
    }

    public int getShow() {
		return show;
	}

	public void setShow(int show) {
		this.show = show;
	}

	public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setType(int type) {
        this.type = type;
    }

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "Dictionary [id=" + id + ", name=" + name + ", show=" + show
				+ ", type=" + type + ", value=" + value + "]";
	}
    
}

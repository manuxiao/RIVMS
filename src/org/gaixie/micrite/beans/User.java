/* ===========================================================
 * $Id: User.java 520 2009-08-27 05:59:23Z bitorb $
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;

/**
 * Micrite应用的一个用户。
 * <p>
 * 通过实现 <code>UserDetails</code> 接口，系统会在成功登录后，得到所拥有的 <code>Role</code> 列表，
 * 用于在每次Spring Security进行安全拦截验证时，与所拦截资源要求的角色进行匹配。
 */
@Entity
@Table(name = "userbase")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User implements UserDetails {
	
    private static final long serialVersionUID = 8026813053768023527L;

    @Id
    @GeneratedValue
    private Integer id;
    
    private String fullname;
    private String loginname;
    private String cryptpassword;
    @Transient
    private String plainpassword;
    private String emailaddress;
    private boolean enabled;

	private String phone;
    private String unit;
    private String position;
    private String grade;
    
    @ManyToMany(targetEntity = Role.class)
    @JoinTable(name = "user_role_map", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Role> roles;

    @ManyToMany(targetEntity = Setting.class)
    @JoinTable(name = "user_setting_map", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "setting_id"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<Setting> settings;

    /**
     * No-arg constructor for JavaBean tools.
     */
    public User() {
        
    }

    /**
     * Simple constructor
     */
    public User(String fullname,String loginname,String cryptpassword,String emailaddress,boolean enabled,String phone,String unit,String position,String grade) {
        this.fullname = fullname;
        this.loginname = loginname;     
        this.cryptpassword = cryptpassword;  
        this.emailaddress = emailaddress;  
        this.enabled = enabled;  
        this.phone =phone;
        this.unit =unit;
        this.position = position;
        this.grade = grade;
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Common Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//  
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        final User user = (User) o;
        return getLoginname().equals(user.getLoginname());
    }
    
    // ~~~~~~~~~~~~~~~~~~~~~~~ 实现 UserDetails Accessor Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    
    /**
     * 这里的Authorities指的是Spring Security的权限标识符，这里对应 <code>Role</code>。
     * 
     * @see org.springframework.security.userdetails.UserDetails#getAuthorities()
     */
    @Transient
    public GrantedAuthority[] getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(roles.size());
        for(Role role : roles) {
            grantedAuthorities.add(new GrantedAuthorityImpl(role.getName()));
        }
        return grantedAuthorities.toArray(new GrantedAuthority[roles.size()]);
    }

    public String getCryptpassword() {
        return cryptpassword;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public String getFullname() {
        return fullname;
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Accessor Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~//    
    public Integer getId() {
        return id;
    }

    public String getLoginname() {
        return loginname;
    }

    public String getPassword() {
        return cryptpassword;
    }

    public String getPlainpassword() {
        return plainpassword;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public List<Setting> getSettings() {
		return settings;
	}

    public String getUsername() {
        return loginname;
    }

    public int hashCode() {
        return getLoginname().hashCode();
    }

    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Transient
    public boolean isEnabled() {
        return enabled;
    }

    public void setCryptpassword(String cryptpassword) {
        this.cryptpassword = cryptpassword;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    


	public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

	public void setPlainpassword(String plainpassword) {
        this.plainpassword = plainpassword;
    }

	public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setSettings(List<Setting> settings) {
		this.settings = settings;
	}
    public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
    public String toString() {
        return  "User ('" + getId() + "'), " +
                "Username: '" + getLoginname() + "'";
    }
}

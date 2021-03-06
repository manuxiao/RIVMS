if(micrite.security.framework.LoginWindow) {
   Ext.override(micrite.security.framework.LoginWindow, {
   	titleText:'系统用户登录',
       submitText:'登录',
       cancelText:'取消',    
       usernameText:'用户名：',
       passwordText:'密码：'
   });
}

if(micrite.security.framework.HeaderPanel) {
   Ext.override(micrite.security.framework.HeaderPanel, {
   	homeText:'首页',
   	userText:'用户',
   	exitText:'退出'
   });
}

if(micrite.security.framework.MenuTreePanel) {
   Ext.override(micrite.security.framework.MenuTreePanel, {
   	navPanelText:'导航',  
   	expandText:'展开',
   	collapseText:'收缩',	
   	allModulesText:'所有模块'
   });
}

if(micrite.security.framework.msgPanel) {
   Ext.override(micrite.security.framework.msgPanel, {
   	messageText:'提示信息'
   });
}

if(micrite.security.framework.MainPanel) {
   Ext.override(micrite.security.framework.MainPanel, {
   	centerPanelText:'欢迎'
   });
}

function userListLocale() {
    if(micrite.security.userList.SearchPanel) {
        Ext.override(micrite.security.userList.SearchPanel, {
            userName:'登录账号',
            fullName:'用户名',
            bindRoles:'绑定角色',
            addUser:'增加用户',
            bindRolesButton:'绑定角色',
            enableUsersButton:'置可用/不可用',
            statusAccordConfMsg:'请确保选择的用户可用状态一致！',
            enableUsersConfMsg:'确定将用户置为可用？',
            disableUsersConfMsg:'确定将用户置为不可用？',
            phone:'联系电话',
        	unit:'所属单位',    
            grade:'河长等级',
            position:'河长职位',
            email:'邮件',
            enabled:'可用'
        });
    }
}

function roleSelectLocale() {
    if(micrite.security.roleSelect.SearchPanel) {
        Ext.override(micrite.security.roleSelect.SearchPanel, {
            addRole:'增加角色',
            onlyBinded:'仅绑定的',
            roleName:'角色名',
            roleDescription:'角色描述'
        });
    }
}

function userSettingLocale(){
    if(micrite.security.userSetting.FormPanel) {
        Ext.override(micrite.security.userSetting.FormPanel, {
        	fullName : '真实姓名',
            email : '邮件',
            userName: '用户名',
            password: '密码',
            passwordRepeat: '再次输入密码',
        	userInformation:'用户个人信息',
        	settings: '个性化设置',
        	skin : '界面样式',
        	rowsPerPage : '每页行数',
        	confirmPassword: '两次输入的密码不匹配',    
            phoneText:'联系电话',
        	unitText:'所属单位',
            gradeText:'河长等级',
            positionText:'河长职位'
        });
    }    
}
function sysParamLocale(){
    if(micrite.security.sysParam.FormPanel) {
        Ext.override(micrite.security.sysParam.FormPanel, {
        	erweiExpirePermissionDays : '二级维护允许超期天数',
            sysParamsFieldSet : '系统参数设置'
        });
    }    
}
function authorityListLocale() {
    if(micrite.security.authorityList.SearchPanel) {
        Ext.override(micrite.security.authorityList.SearchPanel, {
            byName:'按名称',
            name:'名称',
            addAuthority:'增加授权',
            value:'值',
            type:'类型',
            bindRole:'绑定角色'
        });
    }
}
function authorityDetailLocale(){
    if(micrite.security.authorityDetail.FormPanel) {
        Ext.override(micrite.security.authorityDetail.FormPanel, {
            authorityDetailText:'增加授权',
            idText:'ID',
            nameText:'用户姓名',
            valueText:'值',
            typeText:'类型',
            roleText:'角色',
            comboEmptyText:'选择一个类型...',
            lovComboEmptyText:'选择角色...'
        });
    }    
}
function roleListLocale(){
    if(micrite.security.roleList.SearchPanel) {
        Ext.override(micrite.security.roleList.SearchPanel, {
            bindUser:'绑定用户',
            bindAuthority:'绑定授权',
            addRole:'增加角色',
            name:'名称',
            description:'描述'
        });
    }    
}
function userSelectLocale(){
    if(micrite.security.userSelect.SearchPanel) {
        Ext.override(micrite.security.userSelect.SearchPanel, {
            onlyBinded:'只显示已绑定用户',
            addUser:'增加用户',
            fullName:'真实姓名',
            emailAddress:'邮件地址',
            enabled:'是否可用'
        });
    }    
}
function authoritySelectLocale(){
    if(micrite.security.authoritySelect.SearchPanel) {
        Ext.override(micrite.security.authoritySelect.SearchPanel, {
            onlyBinded:'只显示已绑定授权',
            addAuth:'增加授权',
            name:'名称',
            type:'类型',
            value:'值'
        });
    }    
}
function roleDetailLocale(){
    if(micrite.security.roleDetail.FormPanel) {
        Ext.override(micrite.security.roleDetail.FormPanel, {
        	roleDetailText: '角色信息',
        	rolenameText: '角色名',
        	descriptionText: '描述'
        });
    }    
}

function userDetailLocale(){
    if(micrite.security.userDetail.FormPanel) {
        Ext.override(micrite.security.userDetail.FormPanel, {
            userDetailText: '用户信息',
            fullnameText: '真实姓名',
            loginnameText: '登录账号',
            passwordText: '密码',
            unitText: '所属单位',
            phoneText: '联系电话',
            gradeText: '河长等级',
            positionText: '河长职位',
            emailaddressText: '邮件'
        });
    }    
}
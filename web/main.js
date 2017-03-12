Ext.BLANK_IMAGE_URL = "js-lib/ext-js/resources/images/default/s.gif";

Ext.ns('micrite.security.framework');
micrite.security.framework.refleshActiveTabGridPanel=function(){
	var s = mainPanel.getActiveTab().get(0).getStore();
	if(s)s.reload();
}
micrite.security.framework.HeaderPanel = Ext.extend(Ext.Panel, {

    homeText:'Home',
    userText:'User',
    exitText:'Exit',
    
    initComponent:function() {
        Ext.apply(this, {
            border :false,
            layout :'anchor',
            region :'north',
            cls: 'main-header',
            height :30, 
            items:[
//            {
//                xtype :'box',
//                border :true,
//                el: 'header',
//                height :0      
//            },
            new Ext.Toolbar( {
                border:true,
                items : [{
                    text:this.homeText,
                    tooltip: 'Home',  // <-- i
                    iconCls :'home'
                },'->',{
                    text:this.userText,
                    tooltip: 'User Setting',
                    iconCls :'user',
                    handler : function() {
                        mainPanel.loadModule('security/userSetting.js', '我的设置');
                    }
                },'-',{
                    text:this.exitText,
                    tooltip: 'Exit',
                    iconCls :'exit',
                    handler : function() {
                        window.location = 'j_spring_security_logout';
                    }
                }]
            })
            ]
        }); // eo apply
        micrite.security.framework.HeaderPanel.superclass.initComponent.apply(this, arguments);
    } // eo function initComponent
}); // eo Tutorial.LocalizationWin

msgPanel = function(){
       // 显式调用父类构造器    
 msgPanel.superclass.constructor.call(this, {
     id:'msg-panel',
     region:'south',
     //layout:'fit',
     height:140,
     border:false,
     title:this.messageText,
     split:true,
     bodyStyle:'padding:4px',
     collapsible: false,
     renderHidden:true,
     autoScroll:true
 });
};
micrite.security.framework.msgPanel = Ext.extend(msgPanel, Ext.Panel, {
    messageText:'Message'
});

MenuTreePanel = function() {

    // 显式调用父类构造器    
    MenuTreePanel.superclass.constructor.call(this, {
        id:'module-tree',
        region:'center',
        //layout:'fit',
        border:false,
        floatable: false,
        title:this.navPanelText,
        split:true,
        minSize: 175,
        maxSize: 400,
        collapsible: true,
        minHeight:240,
        rootVisible:false,
        lines:false,
        autoScroll:true,
        loader: new Ext.tree.TreeLoader({
            url:'loadMenu.action'
        }),
        root: new Ext.tree.TreeNode('Module Viewer'),
        collapseFirst:false,

        tbar: [{
            iconCls:'icon-expand-all',
            text:this.expandText,
            handler: function(){this.root.expand(true); },
            scope: this
       },{
            iconCls:'icon-collapse-all',
            text:this.collapseText,
            handler: function(){this.root.collapse(true); },
            scope: this
        }]
    });

    this.root.appendChild(
           new Ext.tree.AsyncTreeNode({
               id:'allModulesRoot',
            text:this.allModulesText,
            cls:'modules-node',
            leaf:false,
            expanded:true
        })
    );


    // session 过期的处理
    this.getLoader().on({
        'load' : function(sm, node,r){
            var res = Ext.decode(r.responseText);
            if (res.message){
                showMsg('failure',res.message);
            }
        },        
        scope:this
    });    

};
//指明NavPanel的父类
micrite.security.framework.MenuTreePanel=Ext.extend(MenuTreePanel, Ext.tree.TreePanel, {
    navPanelText:'Navigator',
    expandText:'Expand All',
    collapseText:'Collapse All',    
    allModulesText:'All Modules'
    
});


NavPanel = function() {

    // 显式调用父类构造器    
   NavPanel.superclass.constructor.call(this, {
        id:'nav-panel',
        region:'west',
        layout:'border',
        margins:'0 0 5 5',
        cmargins:'0 5 5 5',
        split:true,
        width: 225,
        minSize: 175,
        maxSize: 400,
        minHeight:600,
        collapsible: true,
        collapseMode:'mini',
        items:[new MenuTreePanel(),new msgPanel()]
    });
};
micrite.security.framework.NavPanel = Ext.extend(NavPanel, Ext.Panel, {
    
});
MainPanel = function() {
    // 显式调用父类构造器    
    MainPanel.superclass.constructor.call(this, {
        id:'main-tabs',
        region:'center',
        margins:'0 0 5 0',
        enableTabScroll : true,
        minTabWidth     : 75,
        activeTab:0,
//        plugins: new Ext.ux.TabCloseMenu(),
        items:[
       {
            title: this.centerPanelText,
            html : '<img src="/micrite/images/bg.jpg"/>',
            autoScroll:true
        }
        ]
        
    });

};

micrite.security.framework.MainPanel=Ext.extend(MainPanel, Ext.TabPanel, {
    centerPanelText:'Center Panel',
    loadModule : function(href,tabTitle){
	    var tab;
	    if(!(tab = this.getItem(tabTitle))){
	        var autoLoad = micrite.util.autoLoad({url: href,scripts:true});
	        tab = new Ext.Panel({
	            id: tabTitle,
	            title: tabTitle,
	            closable:true,
	            autoLoad: autoLoad,
	            layout:"fit",
	            border:false
	        });
	        this.add(tab);
	    }
	    this.setActiveTab(tab);
    }
});

function showMsg(msgType,msg) {
    var detailEl = Ext.DomHelper.insertFirst(Ext.getCmp('msg-panel').body, {id:'msg-div',cls : msgType=='failure'?'errorMsg':'infoMsg'}, true);
    var failureMsg = msg;
    if (msgType=='failure'){
        // 如果session-expired，显式确认框，如果Yes，清空session并返回到登录页面
    	if(msg==null){
    		Ext.Msg.alert('failure','无响应消息');
    	}
        else if (msg.indexOf('session expired')!=-1){
            Ext.Msg.show({
                title:mbLocale.infoMsg,
                msg: mbLocale.sessionExpiredMsg,
                buttons: Ext.Msg.YESNO,
                scope: this,
                fn: function(buttonId, text, opt) {
                    if (buttonId == 'yes') {
                    window.location = 'j_spring_security_logout';
                    }
                },
                icon: Ext.MessageBox.QUESTION
            }); 
            failureMsg = mbLocale.sessionExpiredMsg;
         // 登录后URL和Method拦截信息国际化,临时方法，spring security 3.0以后可能不用了   
        }else if(msg.indexOf('(403)')!=-1){
            Ext.Msg.alert('failure',mbLocale.accessDeniedMsg);
            failureMsg = mbLocale.accessDeniedMsg;
        }else{
            Ext.Msg.alert('failure',msg);
        }
    }
    var dt = new Date();
    dt = '<em>&nbsp;' + dt.format('g:i a') + '</em>';  
    detailEl.hide().update(failureMsg+dt).slideIn('t');
}

function showMsgSilently(msgType,msg) {
    var detailEl = Ext.DomHelper.insertFirst(Ext.getCmp('msg-panel').body, {id:'msg-div',cls : msgType=='failure'?'errorMsg':'infoMsg'}, true);
    var failureMsg = msg;
    if (msgType=='failure'){
        // 如果session-expired，显式确认框，如果Yes，清空session并返回到登录页面
    	if(msg==null){
    		Ext.Msg.alert('failure','无响应消息');
    	}
        else if (msg.indexOf('session expired')!=-1){
            Ext.Msg.show({
                title:mbLocale.infoMsg,
                msg: mbLocale.sessionExpiredMsg,
                buttons: Ext.Msg.YESNO,
                scope: this,
                fn: function(buttonId, text, opt) {
                    if (buttonId == 'yes') {
                    window.location = 'j_spring_security_logout';
                    }
                },
                icon: Ext.MessageBox.QUESTION
            }); 
            failureMsg = mbLocale.sessionExpiredMsg;
         // 登录后URL和Method拦截信息国际化,临时方法，spring security 3.0以后可能不用了   
        }else if(msg.indexOf('(403)')!=-1){
            Ext.Msg.alert('failure',mbLocale.accessDeniedMsg);
            failureMsg = mbLocale.accessDeniedMsg;
        }
//        else{
//            Ext.Msg.alert('failure',msg);
//        }
    }
    var dt = new Date();
    dt = '<em>&nbsp;' + dt.format('g:i a') + '</em>';  
    detailEl.hide().update(failureMsg+dt).slideIn('t');
}

Ext.onReady(function(){
    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

    var headerPanel = new micrite.security.framework.HeaderPanel();
    var navPanel = new micrite.security.framework.NavPanel();
    mainPanel = new micrite.security.framework.MainPanel();

    Ext.getCmp('module-tree').on('click', function(node, e){
        if(node.isLeaf()){
           mainPanel.loadModule(node.attributes.url, node.attributes.text);
        }
   });
    
    var viewport = new Ext.Viewport({
        layout:'border',
        items:[
            headerPanel,
            navPanel,
			mainPanel
        ]
    });
   Ext.get('x-loading-mask').hide();
   Ext.get('x-loading-panel').hide();
   
});
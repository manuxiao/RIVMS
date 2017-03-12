<script type="text/javascript">
Ext.namespace('micrite.enterprise.enterpriseList');
micrite.enterprise.enterpriseList.SearchPanel = Ext.extend(micrite.ComplexGrid, {	
	initComponent:function() {

	//---------------------查看页面只读界面-------------	
	var readWin = new Ext.Window({      
        title:'查看企业记录',
		closeAction:'hide',
		padding:'5px',
		width : 780,
		autoHeight : true,	        
	   items : [{
		xtype:'form',
		id: 'enterprise1-form',
		labelAlign : 'right',
		labelWidth : 80,
		bodyStyle : 'padding:1px',
		baseCls:'x-plain',
      items: [
       		{ 
       		xtype: 'fieldset',
       		padding:'1px',
       		title:'查看企业档案信息',       		
        	layout : 'column',        				
            collapsible: true,
            defaults: {width: 360},
            autoHeight: true,
            fileUpload : true,        
         items: [
        	{
            labelWidth: 100,
            layout:'form',
            collapsible: true,
            defaults: {width: 180},
            defaultType: 'textfield',
            autoHeight: true,           
            columnWidth : .5,
			baseCls : 'x-plain',
			
            items: [{
                fieldLabel: this.unitNameText,
                itemId: 'unitName',
                readOnly:true
            },{
                fieldLabel: this.addressText,
                itemId: 'address',
                readOnly:true
            },{
                fieldLabel: this.legalPersonText,
                itemId: 'legalPerson',
                readOnly:true
            },            
            {
                fieldLabel: this.telephone1Text,
                itemId: 'telephone1',
                readOnly:true
            }, 
            {
                fieldLabel: this.handleManText,
                itemId: 'handleMan',
                readOnly:true
            },
            {
                fieldLabel: this.telephone2Text,
                itemId: 'telephone2',
                readOnly:true
            },
            {
                fieldLabel: this.telephone3Text,
                itemId: 'telephone3',
                readOnly:true
            },            
            {
                fieldLabel: this.commissionText,
                itemId: 'commission',
                readOnly:true
            }, 
            {
                fieldLabel: this.telephone4Text,
                itemId: 'telephone4',
                readOnly:true
            }]
        },
        {
            labelWidth: 100,
            layout:'form',
            collapsible: true,
            defaults: {width: 170},
            defaultType: 'textfield',
            autoHeight: true,           
        	columnWidth : .5,
			baseCls : 'x-plain',
			fileUpload : true,
		items : [ 
			{
                fieldLabel: this.licenseText,
                itemId: 'license',
                readOnly:true
            },
            {
                fieldLabel: this.kindText,
                itemId: 'kind.name',
                readOnly:true
            },                         
            {
                fieldLabel: this.licenseDateText,
                itemId: 'licenseDate',
                readOnly:true
            },{
                fieldLabel: this.dateBeginText,
                itemId: 'dateBegin',
                readOnly:true
            },{
                fieldLabel: this.dateEndText,
                itemId: 'dateEnd',
                readOnly:true
            },{
                fieldLabel: this.qualificationText,
                itemId: 'qualification.name',
                readOnly:true
            },                   
            {
                fieldLabel: this.workAreaText,
                itemId: 'workArea',
                readOnly:true
            },{
                fieldLabel: this.colModelcarStation,
                itemId: 'station.name',
                readOnly:true
            },{
                fieldLabel: this.workTypeText,
                itemId: 'workType.name',
                readOnly:true
            }]
            }            
            ]},           
	{
		xtype : 'tabpanel',
		plain : true,
		bodyBorder : false,
		activeTab : 0,
		height : 100,
		defaults : {bodyStyle : 'padding:2px'},
		items : [
			{title : this.workRemarkText,layout : 'fit',items : {xtype : 'textarea',name : 'workRemark',maxLength : 500,readOnly : true}}
		]
	}],			
            buttonAlign:'center',
            buttons: [{
                text: '修改',
                scope:this,
                handler: function(){
                	readWin.hide();
					var record = this.getSelectionModel().getSelected();
					window.enterpriseId=record.get('id');
	        	 	this.addEnterpriseFn();
//				 	editWin.show();
//				 	Ext.getCmp("enterprise2-form").getForm().loadRecord(record);	
                }                    
            },            
            {
                text: '关闭',
                scope:this,
                handler: function(){
                    readWin.hide();
                }
            }
        	]
    }]});
  
    	// 下拉框renderer
	var comboBoxRenderer = function(combo) {
		return function(value) {
			var idx = combo.store.find(combo.valueField, value);
			var rec = combo.store.getAt(idx);
			return (rec === null ? value : rec.get(combo.displayField) );
	
		};
	};
	var comboWorkType = new Ext.form.ComboBox(
		{
        name:'workType',
        selectOnFocus:true,
        valueField:'id',
        hiddenName:'workType.id',
        displayField:'name',
        fieldLabel: this.workTypeText,
        emptyText:this.comboEmpty10Text,
        editable:false,
        allowBlank:false,
        forceSelection:true,
        triggerAction:'all',
        store:storeWorkType,
        renderer:comboBoxRenderer,
        typeAhead: true
    });
	
	this.addListener('rowcontextmenu', rightClickFn);// 右键菜单代码关键部分
	var rightClick = new Ext.menu.Menu({
		items : [
         {
        	 text: mbLocale.infoButton, 
        	 iconCls :'info-icon',
        	 scope:this, 
        	 handler:this.rowdblclickFn
         },{
        	 text:mbLocale.editButton, 
        	 iconCls :'edit-icon',
        	 scope:this, 
        	 handler : 	function(){								
            	if(sm.getCount()==1){ 
	        	 	var record = this.getSelectionModel().getSelected();
	        	 	window.enterpriseId=record.get('id');
		        	 this.addEnterpriseFn();
//	        	 	editWin.show();
//				 	Ext.getCmp("enterprise2-form").getForm().loadRecord(record);          		
            	}else{ 
            		GOnlyOneSelectedFn(sm.getCount());
            	}
        	}
		 },{
        	 text:mbLocale.deleteButton, 
        	 iconCls :'delete-icon',
        	 scope:this,
        	 handler:this.deleteEnterprise
         }
		]
	});
	function rightClickFn(grid,rowindex, e) {
		this.getSelectionModel().selectRow(rowindex);//
			e.preventDefault();
			rightClick.showAt(e.getXY());
	} 
	
	var sm = new Ext.grid.CheckboxSelectionModel();
	this.addListener('rowdblclick', rowdblclickFn);   
	function rowdblclickFn(){ 
        if(sm.getCount()==1)
        { 
    	 	var record = this.getSelectionModel().getSelected();	
		 	readWin.show();
		 	Ext.getCmp("enterprise1-form").getForm().loadRecord(record);
    	}else            		
    	{ 
    		GOnlyOneSelectedFn(sm.getCount());
    	}
    };
	var config = {
			view:new Ext.grid.GridView({forceFit:false}),
			advSearchField : [[
//			                   {name:'经营资质',value:'qualification.name',xtype:'combo'},			                   
//						        {name:this.colModelunitName,value:'unitName',xtype:'textfield'},
//							  	{name:this.colModelLicense,value:'license',xtype:'textfield'},
//							  	{name:this.colModellegalPerson,value:'legalPerson',xtype:'textfield'},
//							  	{name:'企业名称拼音',value:'py',xtype:'textfield'}	
						      ]],	  
	        compSet: [
	             {url:0,reader:0,columns:0,bbarAction:0,downloadable:1},
	             {url:0,reader:0,columns:0,bbarAction:0,downloadable:1},
	             {url:0,reader:0,columns:0,bbarAction:0,downloadable:1},
	             {url:0,reader:0,columns:0,bbarAction:0,downloadable:1}
	        ],
			searchMenu : [
				 this.searchCondition1,this.searchCondition2,this.searchCondition3,this.searchCondition4
			],
			searchFields :[[
				 {advSearch:true},
	             this.colModelunitName,
	             {xtype:'textfield',
	              name:'unitName',
	              expression:'like',
	              width:120}
	            ],[
	             {advSearch:true},
	             this.colModelLicense,
	             {xtype:'textfield',
	              name:'license',
	              expression:'like',
	              width:120}
	            ],[
	             {advSearch:true},
	             this.colModelqualification,
				{xtype:'combo',
	             name:'qualification.id',
		         valueField:'id',
		         hiddenName:'qualification.id',
		         displayField:'name',
		         emptyText:'全部',
		         editable:true,
		         allowBlank:true,
		         forceSelection:true,
		         selectOnFocus:true,
		         triggerAction:'all',
	             expression:'=',
	             width:120,
		         store:storeQualification
		         }
	            ],[
	             {advSearch:true},
	             this.colModelcarStation,
	             {xtype:'combo',
	             name:'station.id',
		         valueField:'id',
		         hiddenName:'station.id',
		         displayField:'name',
		         emptyText:'全部',
		         editable:true,
		         allowBlank:true,
		         forceSelection:true,
		         selectOnFocus:true,
		         triggerAction:'all',
	             expression:'=',
	             width:120,
		         store:storeStation          
		         }
	            ]],
	        urls: ['enterprise/findEnterprise.action'],
	        readers : [GEnterpriseFields],	                 
			columnsArray: [[
					sm,
		          {
		          	header:this.colModelunitName,
		          	width:180, sortable: true,dataIndex: 'unitName'
		          },{
		          	header:this.colModelLicense,
		          	width:90, sortable: true,dataIndex: 'license'
		          },{
		          	header: this.colModelqualification,
		          	width: 80, sortable: true, dataIndex: 'qualification.name'
		          },{
		          	header: this.colModellegalPerson,
		          	width: 80, sortable: true, dataIndex: 'legalPerson'
		          },{
		          	header: this.colModeltelephone1,
		          	width: 80, sortable: true, dataIndex: 'telephone1'
		          },{
		          	header: this.colModelworkType,
		          	width: 80, sortable: true, dataIndex: 'workType.name'
		          },{
		          	header: this.colModelcarStation,
		          	width: 80, sortable: true, dataIndex: 'station.name'
		          },{
		          	header: this.colModelhandleMan,
		          	width: 80, sortable: true, dataIndex: 'handleMan'
		          },{
		          	header: this.colModeltelephone2,
		          	width: 80, sortable: true, dataIndex: 'telephone2'
		          },{
		          	header: this.colModelcommission,
		          	width: 80, sortable: true, dataIndex: 'commission'
		          },{
		          	header: this.colModeltelephone3,
		          	width: 80, sortable: true, dataIndex: 'telephone3'
		          },{
		          	header: this.colModeltelephone4,
		          	width: 80, sortable: true, dataIndex: 'telephone4'
		          },{
		          	header:this.colModelkind,
		          	width: 80, sortable: true,dataIndex: 'kind.name'
		          },{
		          	header: this.colModellicenseDate,
		          	width: 80, sortable: true, dataIndex: 'licenseDate'
		          },{
		          	header: this.colModeldateBegin,
		          	width: 80, sortable: true, dataIndex: 'dateBegin'
		          },{
		          	header: this.colModeldateEnd,
		          	width: 80, sortable: true, dataIndex: 'dateEnd'
		          },{
		          	header: this.colModeladdress,
		          	width: 80, sortable: true, dataIndex: 'address'
		          },{
		          	header: this.colModelworkArea,
		          	width: 80, sortable: true, dataIndex: 'workArea'
		          },{
		          	header: this.colModelworkRemark,
		          	width: 80, sortable: true, dataIndex: 'workRemark'
		          }
			 ]],
	         tbarActions : [[        	
	         {
	        	 text:this.enterpriseSourcePieChart,
	        	 iconCls :'pie-chart-icon',
	        	 scope:this,
	        	 handler:this.getPieChart
	         }
	         ]],
	         bbarActions:[[
	         {
	        	 text:mbLocale.deleteButton, 
	        	 iconCls :'delete-icon',
	        	 scope:this, 
	        	 handler:this.deleteEnterprise
	         },
	         {
	        	 text:mbLocale.addButton, 
	        	 iconCls :'add-icon',
	        	 scope:this, 
	        	 handler:function(){window.enterpriseId=null;this.addEnterpriseFn();}
	         },
	         {
	        	 text:mbLocale.editButton, 
	        	 iconCls :'edit-icon',
	        	 scope:this, 
	        	 handler : 	function(){								
	            	if(sm.getCount()==1){ 
		        	 	var record = this.getSelectionModel().getSelected();
	            		window.enterpriseId=record.get('id');
		        	 	this.addEnterpriseFn();
//		        	 	editWin.show();
//					 	Ext.getCmp("enterprise2-form").getForm().loadRecord(record);
	            	}else{ 
	            		GOnlyOneSelectedFn(sm.getCount());
	            	}
            	}  
			 },{
	        	 text: mbLocale.infoButton, 
	        	 iconCls :'info-icon',
	        	 scope:this, 
	        	 handler:rowdblclickFn
//	        		 this.rowdblclickFn
	         }
	         ]],
//	         keys:[{
//	        	 key:Ext.EventObject.ENTER,
//	        	 fn:rowdblclickFn,
//	        	 scope:this
//	         }],
	         sm:sm
	    }; // eo config object
		// apply config
		Ext.apply(this, Ext.apply(this.initialConfig, config)); 
		micrite.enterprise.enterpriseList.SearchPanel.superclass.initComponent.apply(this, arguments);
    }, // eo function initComponent	
	getPieChart : function() {
		var c1 = {id:'enterpriseList.piechar',
		          title:this.enterpriseSourcePieChart};
        var c2 = {
            url: 'enterprise/getEnterpriseSourcePieChart.action',
            params:this.getAllField()
        };
        this.genChartWindow(c1,c2);
	}, //eof getPieChart
	addEnterpriseFn : function() {
		var title = null;
		if(window.enterpriseId==null)title=this.newEnterprise;
		else title=this.updateEnterprise;
		var win = micrite.util.genWindow({
			modal:true,
			maximizable:false,
//            id       : 'addEnterpriseWindow',
            title    : title,
            autoLoad : {url: 'enterprise/enterpriseDetail.js',scripts:true},
            width    : 750,
            resizable:false,
            height   : 500
        });
	}, //eof addEnterpriseFn
	deleteEnterprise : function() {
		var enterpriseIds = this.selModel.selections.keys;
		var deleteRolesFun = function(buttonId, text, opt) {
			if (buttonId == 'yes') {
				micrite.util.ajaxRequest({
                    url: 'enterprise/deleteEnterprise.action',
                    params:{'enterpriseIds':enterpriseIds},
                    success:function(r,o){
                        this.store.reload();
                    },
                    failure:Ext.emptyFn
                },this);
			}
		};
	Ext.Msg.show({
		title:mbLocale.infoMsg,
		msg: mbLocale.delConfirmMsg,
		buttons: Ext.Msg.YESNO,
		scope: this,
		fn: deleteRolesFun,
		icon: Ext.MessageBox.QUESTION
	});         
	} //eof deleteEnterprise
}); //eo extend
// 处理多语言
try {enterpriseListLocale();} catch (e) {}
Ext.onReady(function() {
	Ext.QuickTips.init();
	var formPanel = new micrite.enterprise.enterpriseList.SearchPanel();
	if (mainPanel) {
		mainPanel.getActiveTab().add(formPanel);
		mainPanel.getActiveTab().doLayout();
	} else {
		var vp = new Ext.Viewport({
			layout:'fit',
			items:[formPanel]
		});
	}
	//formPanel.getStore().load({params:{start:0,limit:formPanel.pageSize,queryString:''}});//打开自动显示数据
});
</script>
<script type="text/javascript">
Ext.namespace('micrite.crm.carownerList');
micrite.crm.carownerList.SearchPanel = Ext.extend(micrite.ComplexGrid, {	
	//class本身多语言字符，声明在这里          anyName :'ID', 
	initComponent:function() {
	var pageSize = parseInt(Ext.getDom('pageSize').value,10);
	var editCarownerWin = new Ext.Window({ 
		modal:true,
	   	title:'车主记录修改',
	   	closeAction:'hide',
		padding:'5px',
		width : 400,
		items : [{
        id: 'editCarownerWinFormID',
		labelAlign : 'right',
		labelWidth : 80,
		xtype:'form',
		bodyStyle : 'padding:1px',
		baseCls:'x-plain',	
        items: [
        {
            xtype: 'fieldset',           
            title:'修改车主信息',
            layout:'form',
            collapsible: true,           
            labelWidth: 100,
            defaults: {width: 230},    // Default config options for child item										// items
            defaultType: 'textfield',
            items: [
                    {
            	xtype:'hidden',
                name: 'id',
                allowBlank:false
            },  
            {
                fieldLabel: this.colModelName,
                name: 'name',
                allowBlank:false
            },{
                fieldLabel: this.colModelLicense,
                name: 'license',
                allowBlank:true
            },
            {
                fieldLabel: this.colModelMobile,
                name: 'mobile',
                vtype:'mobile',
                allowBlank:true
            },
            {
                fieldLabel: this.colModelAddress,
                name: 'address',
                allowBlank:true
            },
            {
                fieldLabel: this.colModelTelephone,
                name: 'telephone',
                vtype:'telephone',
                allowBlank:true
            }
            ]               
        }],
            buttonAlign:'center',
            buttons: [{
                text: mbLocale.submitButton,
                // 提交按钮 之前校验不通过就不提交到后台
                handler: function(){
                	if(!Ext.getCmp("editCarownerWinFormID").getForm().isValid())return;
                    Ext.getCmp("editCarownerWinFormID").getForm().submit({
    					url: 'crm/updateCarowner.action',
                        method: 'POST',
                        disabled:true,
                        waitMsg: mbLocale.waitingMsg,                       
                        success: function(form, action) {
                            obj = Ext.util.JSON.decode(action.response.responseText);
                            showMsg('success', obj.message); 
                            editCarownerWin.hide(); 
                            micrite.security.framework.refleshActiveTabGridPanel();
                            // 刷新主界面 最后一句死活不起作用  [name,'+name+',like]
//                            var pageSize = parseInt(Ext.getDom('pageSize').value,10);
//                		 	var record = sm.getSelected();	
//                		 	var name = record.get('name'); 
//                		 	this.getStore().on("load",function(){this.getSelectionModel().selectRow(index,true);}); 
//                		 	this.getStore().load({params:{start:0,limit:pageSize,queryString:''}});
                        },
                        failure: function(form, action) {
                            obj = Ext.util.JSON.decode(action.response.responseText);
							var message=(obj.message=='undefined')?obj.actionErrors:obj.message;
                            showMsg('failure', obj.message); 
                        }
                    }); 
            	}
            },    
            {
            text: '关闭',
            scope:this,
            handler: function(){
            	editCarownerWin.hide();
            }
        }]
    }]}); 
	function editCarownerFn(){
		if(sm.getCount()==1){ 
			var theForm = Ext.getCmp("editCarownerWinFormID").getForm(); 
			var record = sm.getSelected();		
			theForm.reset();	
			editCarownerWin.show();
			Ext.getCmp("editCarownerWinFormID").getForm().loadRecord(record);							 	
		}else{GOnlyOneSelectedFn(sm.getCount());}
	}
	this.addListener('rowdblclick', this.showCarListWindow);
	//配置项说明
	var sm = new Ext.grid.CheckboxSelectionModel();
	var config = {
			advSearchField : [[
				//{name:this.colModelName,value:'name',xtype:'textfield'}
			]],	  
	        compSet: [
	             {url:0,reader:0,columns:0,bbarAction:0,downloadable:1},
	             {url:0,reader:0,columns:0,bbarAction:0,downloadable:1}
	        ],
			searchMenu : [
				 this.searchCondition1,
				 this.searchCondition2
			],
			searchFields :[[
				 {advSearch:true},
	             this.searchName,
	             {xtype:'textfield',
	              name:'name',
	              expression:'like',
	              width:120}
	            ],[
	             {advSearch:true},
	             this.colModelLicense,
	             {xtype:'textfield',
	              name:'license',
	              expression:'like',
	              width:120}
	            ]],
	        urls: ['crm/findCarowner.action'],
	        readers : [[
	             {name: 'id'},
			     {name: 'name'},
			     {name: 'telephone'},
			     {name: 'mobile'},
			     {name: 'address'},
			     {name: 'license'},
			     {name: 'createDate'}
	        ]],
			columnsArray: [[
					sm,
		          {
		          	header:this.colModelLicense,
		          	width:100, sortable: true,dataIndex: 'license'
		          },{
		          	header:this.colModelName,
		          	width:100, sortable: true,dataIndex: 'name'
		          },
		          {
		          	header: this.colModelTelephone,
		          	width: 100, sortable: true, dataIndex: 'telephone'
		          },
		          {
		          	header: this.colModelMobile,//colModelSource
		          	width: 100, sortable: true, dataIndex: 'mobile'
		          },{
					header:this.colModelAddress,
					width:100, sortable: true,dataIndex: 'address'
				  },
		          {
		          	header: this.colCreation_ts,
		          	width: 100, sortable: true, dataIndex: 'createDate'
		          }
			 ]],
	         //tbarActions : [],
	         bbarActions:[[{
	        	 text:mbLocale.deleteButton, 
	        	 iconCls :'delete-icon',
	        	 scope:this, 
	        	 handler:this.deleteCarowner
	         },{
	        	 text:'修改', 
	        	 iconCls :'save-icon',
	        	 scope:this, 
	        	 handler:editCarownerFn			
	         },{
	        	 text:this.newButton,
	        	 iconCls :'add-icon',
	        	 scope:this,
	        	 handler:this.addCarowner
	         },{
	        	 text:this.listCarButton,
	        	 iconCls :'info-icon',
	        	 scope:this,
	        	 handler:this.showCarListWindow
	         }
	         ]],
	         sm:sm
	    }; // eo config object

		// apply config
		Ext.apply(this, Ext.apply(this.initialConfig, config)); 
		micrite.crm.carownerList.SearchPanel.superclass.initComponent.apply(this, arguments);
    }, // eo function initComponent
    
	getBarChart : function(o,t,id) {
		var c1 = {id:'carownerList.barchar',
		          title:this.carownerSourceBarChart};
		var c2 = {
            url: 'crm/getCarownerSourceBarChart.action',
            params:this.getAllField()
        };
		var win = this.genChartWindow(c1,c2);
	}, //eof getBarChart
	
	getPieChart : function() {
		var c1 = {id:'carownerList.piechar',
		          title:this.carownerSourcePieChart};
        var c2 = {
            url: 'crm/getCarownerSourcePieChart.action',
            params:this.getAllField()
        };
        this.genChartWindow(c1,c2);
	}, //eof getPieChart
	
	getLineChart : function() {
		var c1 = {id:'carownerList.linechar',
		          title:this.carownerSourcePieChart};
        var c2 = {
            url: 'crm/getCarownerSourceLineChart.action',
            params:this.getAllField()
        };
        this.genChartWindow(c1,c2);
	}, //eof getPieChart
	showCarListWindow : function(){
		var store = this.getStore();
		var carownerIds = this.selModel.selections.keys;
		if(carownerIds.length==0){
			Ext.MessageBox.alert(mbLocale.infoMsg,mbLocale.gridRowNotSeleteMsg);
			return;
		}
		if(carownerIds.length!=1){
			Ext.MessageBox.alert(mbLocale.infoMsg,mbLocale.gridRowEditMsg);
			return;
		}
		var record = this.getSelectionModel().getSelected();
		window.ownerName=record.get('name');
		window.ownerId = record.get('id'); 
		var win = micrite.util.genWindow({
            id       : 'carListWindow',
            title    : this.listCar,
            autoLoad : {url: 'crm/carList.js',scripts:true},
            width    : 700,
            height   : 400
        });
	},
	addCarowner : function() {
			var win = micrite.util.genWindow({
	            id       : 'addCarownerWindow',
	            modal    :true,
	            maximizable:false,
                title    : this.newCarowner,
                autoLoad : {url: 'crm/carownerDetail.js',scripts:true},
                width    : 500,
                height   : 280
            });
	}, //eof addCarowner
	
	deleteCarowner : function() {
		var carownerIds = this.selModel.selections.keys;
		var deleteRolesFun = function(buttonId, text, opt) {
			if (buttonId == 'yes') {
				micrite.util.ajaxRequest({
                    url: 'crm/deleteCarowner.action',
                    params:{'carownerIds':carownerIds},
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
	}//, //eof deleteCarowner
	/** 双击修改，已弃用
	saveCarowner : function() {
		var store = this.getStore();
		if(store.getModifiedRecords().length!=1){
			Ext.MessageBox.alert(mbLocale.infoMsg,mbLocale.gridRowEditMsg);
			return;
		}
		var carowner = store.getModifiedRecords()[0];
		var updateRolesFun = function(buttonId, text, opt) {
			if (buttonId == 'yes') {
				micrite.util.ajaxRequest({
					url: 'crm/editCarowner.action',
					params:{'carowner.mobile':carowner.get('mobile'),
					'carowner.name':carowner.get('name'),
					'carowner.telephone':carowner.get('telephone'),
					'carowner.id':carowner.id,
					'carowner.address':carowner.get('address'),
					'carowner.license':carowner.get('license')},
					scope:this,
					success:function(r,o){
                        this.getStore().commitChanges();
                    },
                    failure:Ext.emptyFn
				});
			}
		};
		Ext.Msg.show({
			title:mbLocale.infoMsg,
			msg: mbLocale.updateConfirmMsg,
			buttons: Ext.Msg.YESNO,
			scope: this,
			fn: updateRolesFun,
			icon: Ext.MessageBox.QUESTION
		});         	
} // eof saveCarowner
*/
}); //eo extend

// 处理多语言
try {carownerListLocale();} catch (e) {}

Ext.onReady(function() {
	Ext.QuickTips.init();
	var formPanel = new micrite.crm.carownerList.SearchPanel();
	if (mainPanel) {
		mainPanel.getActiveTab().add(formPanel);
		mainPanel.getActiveTab().doLayout();
	} else {
		var vp = new Ext.Viewport({
			layout:'fit',
			items:[formPanel]
		});
	}
});
</script>
<script type="text/javascript">
Ext.namespace('micrite.check.checkList');
micrite.check.checkList.SearchPanel = Ext.extend(micrite.ComplexGrid, {	
	initComponent:function() {	
	var RecordDef = Ext.data.Record.create([{name: 'id'},{name: 'name'}]);
// --------skillRank下拉框-----------------------
	var store2 = new Ext.data.Store({
		id: Ext.id(),    
		autoLoad:true,
		proxy : new Ext.data.HttpProxy({url: 'car/getPartner.action?type=2'}),    
		reader : new Ext.data.JsonReader({    
			id:"id"
		}, RecordDef),
		remoteSort: true   
	});	
	var combo2 = new Ext.form.ComboBox({
		valueField: "id",
		displayField: "name",
		store: store2,
		triggerAction :'all',
		lazyRender:false 
	});	
	
	
	var store7 = new Ext.data.Store({
	id: Ext.id(),    
	autoLoad:true,
	//设定读取的地址
	proxy : new Ext.data.HttpProxy({url: 'car/getPartner.action?type=1'}),   
	//设定读取的格式    
	reader : new Ext.data.JsonReader({    
		id:"id"
	}, RecordDef),
	remoteSort: true   
});	   	
var combo7 = new Ext.form.ComboBox({
	valueField: "id",
	displayField: "name",
	store: store7,
	triggerAction :'all',
	lazyRender:false 
});
//-------通用renderer----------
myRenderer = function(value){
	var idx = this.editor.getStore().find(this.editor.valueField, value);
    var rec = this.editor.getStore().getAt(idx);
    if (rec == null) {
        return value;
    }
    else {
        return rec.get(this.editor.displayField);
    }
};
		  //--------只读查看界面----------------------
/*	   	var readWin = new Ext.Window({
		padding:'5px',
		width : 400,
		autoHeight : true,
		items : [{
		labelAlign : 'right',
		labelWidth : 110,
		bodyStyle : 'padding:1px',
		id:'readOnlyForm',
		xtype:'form',
		baseCls:'x-plain',
		items:[{
			xtype:'fieldset',
            title:'检测记录信息',
            layout:'form',
            collapsible: true,
            autoHeight: true,
            defaults: {width: 200},
           labelWidth:110,
            		defaultType: 'textfield',
            		items:[           		
            		{                
		            	xtype:'uxspinnerdate',
		                fieldLabel: '检测时间',
		                name: 'jianTime',             
		                value:new Date(),
		                readOnly : true, 
		                format:'Y-m-d H:i:s'         
		            },{
		                fieldLabel: '检测编号',
		                name: 'testNo',
		               	readOnly : true
		            }, 
//					{
//		                fieldLabel: '车主',
//		                name: 'cheZhu',
//		                readOnly : true
//		            },{
//		                fieldLabel: '车辆维修',
//		                name: 'cheXiu',
//		                readOnly : true
//		            },
		            {
		                fieldLabel: '车牌号码',
		                name: 'paiHao',
		                readOnly : true
		            },
//		            {
//		                fieldLabel: '车辆类型',
//		                name: 'cheLei',
//		                readOnly : true
//		            },
		            {
		                fieldLabel: '合格证号',
		                name: 'heGe',
		                readOnly : true
		            },
		            new Ext.form.ComboBox({//车牌颜色
		                  name:'paiSe',
		                  selectOnFocus:true,
		                  valueField:'id',
		                  hiddenName:'paiSe.id',
		                  displayField:'name',
		                  fieldLabel:'车牌颜色',
		                  emptyText:'请选择车牌颜色',
		                  editable:false,
		                  readOnly : true,
		                  forceSelection:true,
		                  store:store7,
		                  typeAhead: true               
		            }),
		            {
		                fieldLabel: '备注/回单',
		                name: 'notepad',
		                readOnly:true
		            },
		            {name: 'id',hidden:true}
					]
	            	}],
            	 buttonAlign:'center',
	        			buttons:[{
				text:'修改',
				scope:this,
				handler:function() {
					//打开编辑界面
					readWin.hide();
	        	 	var record = this.getSelectionModel().getSelected();	
				 	addWin.show();
				 	Ext.getCmp("checkForm").getForm().loadRecord(record);				
				}
    	},{
            text: '关闭',
            scope:this,
            handler: function(){
                readWin.hide();
            }
            
        }]
            }]	
	});
	
	*/
   /*/--------编辑检测记录界面----------------------	
	   	var addWin = new Ext.Window({
		title:'修改检测记录',
		closeAction:'hide',
		padding:'5px',
		width : 400,
		autoHeight : true,
		items : [{
		labelAlign : 'right',
		labelWidth : 110,
		bodyStyle : 'padding:1px',
		id:'checkForm',
		xtype:'form',
		baseCls:'x-plain',
		items:[{
			xtype:'fieldset',
            title:'检测记录信息',
            layout:'form',
            collapsible: true,
            autoHeight: true,
            defaults: {width: 200},
            labelWidth:110,
            defaultType: 'textfield',
    		items:[           		
    		{                
            	xtype:'uxspinnerdate',
                fieldLabel: '检测时间',
                name: 'jianTime',
                allowBlank:false,                
                value:new Date(),
                format:'Y-m-d H:i:s'         
            }, 
            {
                fieldLabel: '车牌号码',
                name: 'paiHao',
                allowBlank:false
            },
            {
                fieldLabel: '合格证号',
                name: 'heGe',
                allowBlank:false
            },
            new Ext.form.ComboBox({//车牌颜色
                  name:'paiSe',
                  selectOnFocus:true,
                  valueField:'id',
                  hiddenName:'paiSe.id',
                  displayField:'name',
                  fieldLabel:'车牌颜色',
                  emptyText:'请选择车牌颜色',
                  editable:false,
                  allowBlank : false,
                  forceSelection:true,
                  triggerAction:'all',
                  store:store7,
                  typeAhead: true               
            }),
            {name: 'id',hidden:true},
            {name: 'printed',hidden:true},
            {name: 'status',hidden:true}
			]
	    }],
        buttonAlign:'center',
	    buttons:[{
			text:mbLocale.submitButton,
			handler:function() {
				if (!Ext.getCmp('checkForm').getForm().isValid()){return ;}
				Ext.getCmp('checkForm').getForm().submit({
					scope:this,
					url: 'check/updateCheck.action',
					method: 'POST',
					disabled:true,
					waitTitle:'请稍候',
					waitMsg:'正在修改数据，请等待...',
					success : 
					function(form,action) {
						obj = Ext.util.JSON.decode(action.response.responseText);
						showMsg('success', obj.message); 
						addWin.hide();
					}, // eof success                   
                    failure: function(form, action) {
                        obj = Ext.util.JSON.decode(action.response.responseText);
                        showMsg('failure', obj.message);
                    }
				});
				
			}
    	},{
            text: mbLocale.closeButton,
            scope:this,
            handler: function(){addWin.hide();}
        }]
        }]	
	});*/


/**
	this.addListener('rowcontextmenu', rightClickFn);// 右键菜单代码关键部分
	var rightClick = new Ext.menu.Menu({
			id : 'rightClickCont',
			items : [
			 {
	        	 text:this.newCheck,
	        	 iconCls :'add-icon',
	        	 scope:this,
	        	 handler:this.addcheck
	         },
	         {
	        	 text:'查看检测信息',
	        	 iconCls :'edit-icon',
	        	 scope:this,
	        	 handler:function(){								
            	if(sm.getCount()==1){ 
	        	 	var record = this.getSelectionModel().getSelected();	
				 	//addWin.show();
				 	//Ext.getCmp("checkForm").getForm().loadRecord(record);
            	}else{ 
            		Ext.MessageBox.alert('提示', '请选择一条记录！');
            	}
            	}
	         },
			 {
	        	 text:mbLocale.deleteButton, 
	        	 iconCls :'delete-icon',
	        	 scope:this, 
	        	 handler:this.deletecheck
	         }						
			]
			});
	function rightClickFn(grid,rowindex, e) {
		this.getSelectionModel().selectRow(rowindex);//
			e.preventDefault();
			rightClick.showAt(e.getXY());
	}  */
	
var sm = new Ext.grid.CheckboxSelectionModel();
/*this.addListener('rowdblclick', rowdblclickFn);   
function rowdblclickFn(){ 
                if(sm.getCount()==1)
                { 
	        	 	var record = this.getSelectionModel().getSelected();	
				 	readWin.show();
				 	Ext.getCmp("readOnlyForm").getForm().loadRecord(record);
            	}else            		
            	{ 
            		Ext.MessageBox.alert('提示', '请选择一条记录！');
            	}
    };*/   
	var config = {
			advSearchField : [[
						          {name:this.colModelheGe,value:'heGe',xtype:'textfield'},
						          {name:this.colModelheGe,value:'heGe',xtype:'textfield'},
						          {name:'车牌号码',value:'licenseNumber',xtype:'textfield'},
						          {name:this.colModeljianTime,value:'jianTime',xtype:'uxspinnerdate'}
						      ]],	  
	        compSet: [
	             {url:0,reader:0,columns:0,bbarAction:0},
//	             {url:0,reader:0,columns:0,bbarAction:0},
	             {url:1,reader:0,columns:0,bbarAction:0,tbarAction:-1},
	             {url:2,reader:0,columns:0,bbarAction:0,tbarAction:0},	
	             {url:3,reader:0,columns:0,bbarAction:0,tbarAction:1}	
//	             {url:0,reader:0,columns:0,bbarAction:-1,tbarAction:0,advField:0};
//	             浙B.T5002
	        ],
			searchMenu : [			 
				 '二维操作记录',
				 '检测站记录',
				 '车辆记录',
				 '企业记录'
			],
			searchFields :[[
				 {advSearch:true},
	             this.colModelheGe,
	             {xtype:'textfield',
	              name:'heGe',
	              expression:'=',
	              width:120}
	            ],
	            [
				 {advSearch:true},
	             this.colModelheGe,
	             {xtype:'textfield',
	              name:'heGe',
	              expression:'=',
	              width:120}
	             ],
	             [
				 {advSearch:true},
		         '车牌号码',
		         {xtype:'textfield',
		         name:'licenseNumber',
		         expression:'like',
		         width:120}
	            ]
	            ,[	            
	            '企业名称',
				 {advSearch:true},
	             {xtype:'textfield',
	              name:'unitName',
	              expression:'like',
	              width:120},
	             '企业名称拼音',
					 {advSearch:true},
		             {xtype:'textfield',
		              name:'py',
		              expression:'like',
		              width:120}	
		            ]],
//		            check/findCheckByDateSpacing.action
	            urls: ['check/findCheck.action','check/findCheck.action','car/findCarfile.action','enterprise/findEnterprise.action'],
	        readers : [[
	        	{name: 'id'},
			     {name: 'jianTime',type : 'date',dateFormat : 'Y-m-dTH:i:s',mapping : 'jianTime'},
			     {name: 'testNo'},
			     {name: 'cheZhu'},
			     {name: 'cheXiu'},
			     {name: 'paiHao'},
			     {name: 'cheLei'},
			     {name: 'heGe'},
			     {name: 'paiSe.id'},
			     {name: 'printed'},
			     {name: 'paiSe',mapping : 'paiSe.name'},
			     {name: 'notepad'}
	        ],
	        [
	        	{name: 'id'},
			     {name: 'jianTime',type : 'date',dateFormat : 'Y-m-dTH:i:s',mapping : 'jianTime'},
			     {name: 'testNo'},
			     {name: 'cheZhu'},
			     {name: 'cheXiu'},
			     {name: 'paiHao'},
			     {name: 'cheLei'},
			     {name: 'heGe'},
			     {name: 'paiSe.id'},
			     {name: 'printed'},
			     {name: 'paiSe',mapping : 'paiSe.name'},
			     {name: 'notepad'}
	        ],
	        [
			     {name: 'owner'},
			     {name: 'telephone'},			     
			     {name: 'address'},
			     {name: 'licenseNumber'},			     
			     {name: 'skillRank.id',mapping : 'skillRank.id'},		     
			     {name: 'yingyunNo'},
			     {name:'py'},
			     {name: 'paiSe',mapping : 'paiSe.name'}
	        ],	        
	        [
	        	 {name: 'id'},	
			     {name: 'unitName'},	     
			     {name: 'license'},
			     {name: 'py'}	     
	        ]
		          
	        ],
			columnsArray: [[
				  {
					header: this.colModeljianTime,
					width: 100, sortable: true, dataIndex: 'jianTime',
					renderer : Ext.util.Format.dateRenderer('Y-m-d')
				  },			                
		          {
		          	header: this.colModelTestNo,
		          	width: 100, sortable: true, dataIndex: 'testNo',
		          	editor:new Ext.form.TextField({allowBlank: true}),
		          	editable : false
		          },
			     {
			       header: this.colModelpaiHao,
			       width: 100, sortable: true, dataIndex: 'paiHao',
			       editor:new Ext.form.TextField({allowBlank: false}),
			       editable : false
			     },	           		            
			     {
				    header: this.colModelheGe,
				     width: 100, sortable: true, dataIndex: 'heGe',
				    editor:new Ext.form.TextField({allowBlank: false}),
				    editable : false
				 },
				 {
				   header: this.colModelpaiSe,
				   width: 100, sortable: true, dataIndex: 'paiSe',
					editor:combo7,renderer:myRenderer,
					editable : false				   
				 },
				 {
					   header: '备注/回单',
					   width: 100, sortable: true, dataIndex: 'notepad',
					   editor:new Ext.form.TextField({allowBlank: true}),
					   editable : false
				 },
		    sm
			 ],
			 [
			 	  {
		          	header:this.colModelOwner,
		          	width:100, sortable: true,dataIndex: 'owner'
		          },
				  				  
				  {
			          	header: this.colModelSkillRank,
			          	width: 100, sortable: true, dataIndex: 'skillRank.id',
			          	editor:combo2,renderer:myRenderer
			      }, 			      				      
				  {
			          	header: this.colModelPaiSe,
			          	width: 100, sortable: true, dataIndex: 'paiSe',
			          	editor:combo7,renderer:myRenderer
			      },
			 sm
			 ],
			 [
		          {
		          	header:this.colModelunitName,
		          	width:200, sortable: true,dataIndex: 'unitName',
		          	editor:new Ext.form.TextField({allowBlank: false}),
		          	editable : false
		          },
		          {
		          	header:this.colModelLicense,
		          	width:200, sortable: true,dataIndex: 'license',
		          	editor:new Ext.form.TextField({allowBlank: false}),
		          	editable : false
		          },
		          {
		          	header: this.colModellegalPerson,
		          	width: 200, sortable: true, dataIndex: 'legalPerson',
		          	editor:new Ext.form.TextField({allowBlank: false}),
		          	editable : false
		          },		          		        
		          sm
			 ]
			 ],
//	         tbarActions : [[
//             {
//	        	 text:'打印检测信息',
//	        	 iconCls :'edit-icon',
//	        	 scope:this,
//	        	 handler:this.printPreview
//	         }
//	        ]],
	         bbarActions:[[
//	         {
//	        	 text:mbLocale.deleteButton, 
//	        	 iconCls :'delete-icon',
//	        	 scope:this, 
//	        	 handler:this.deletecheck
//	         },
//	         {
//	        	 text:this.newCheck,
//	        	 iconCls :'add-icon',
//	        	 scope:this,
//	        	 handler:this.addcheck
//	         },
//	         {
//	        	 text:'查看检测信息',
//	        	 iconCls :'edit-icon',
//	        	 scope:this,
//	        	 handler:function(){								
//            	if(sm.getCount()==1){ 
//	        	 	var record = this.getSelectionModel().getSelected();	
//				 	//readWin.show();
//				 	//Ext.getCmp("readOnlyForm").getForm().loadRecord(record);
//            	}else{ 
//            		Ext.MessageBox.alert('提示', '请选择一条记录！');
//            	}
//            	}
//	         }
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
		micrite.check.checkList.SearchPanel.superclass.initComponent.apply(this, arguments);
    }, // eo function initComponent	
    /*printPreview : function() {
			var win = micrite.util.genWindow({
	            id       : 'addCheckWindow',
                title    : this.printPreviewTxt, 
                autoLoad : {url: 'print1.action?id=26',scripts:true},  
                width    : 420,
                height   : 360
            });
	},*/
	/*print : function() {
		var checkId = this.selModel.selections.keys;
		var record = this.getSelectionModel().getSelected();
		var printRolesFun = function(buttonId, text, opt) {
			if (buttonId == 'yes') {
				micrite.util.ajaxRequest({
                    url: 'check/printCheck.action',
                    params:{'checkId':checkId},
                    success:function(r,o){
                        this.store.reload();
                    },
                    failure:Ext.emptyFn
                },this);
			}
		};
		Ext.Msg.show({
			title:'打印检测信息',
			msg: '您确定打印检测信息?',
			buttons: Ext.Msg.YESNO,
			scope: this,
			fn: printRolesFun,
			icon: Ext.MessageBox.QUESTION
		}); 
	},*/ //eof printCheck	   
    
	addcheck : function() {
			var win = micrite.util.genWindow({
	            id       : 'addCheckWindow',
                title    : this.newCheck,
                autoLoad : {url: 'check/checkDetail.js',scripts:true},
                width    : 420,
                height   : 360
            });  
	}, //eof addcheck
	
	deletecheck : function() {
		var checkIds = this.selModel.selections.keys;
		var deleteRolesFun = function(buttonId, text, opt) {
			if (buttonId == 'yes') {
				micrite.util.ajaxRequest({
                    url: 'check/deleteCheck.action',
                    params:{'checkIds':checkIds},
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
	}//eof deletecheck
}); //eo extend

// 处理多语言
try {checkListLocale();} catch (e) {}

Ext.onReady(function() {
	Ext.QuickTips.init();
	var formPanel = new micrite.check.checkList.SearchPanel();
	if (mainPanel) {
		mainPanel.getActiveTab().add(formPanel);
			mainPanel.getActiveTab().doLayout();
	} else {
		var vp = new Ext.Viewport({
			layout:'fit',
			items:[formPanel]
		});
	}
	formPanel.getStore().load({params:{start:0,limit:formPanel.pageSize,queryString:''}})//打开自动显示数据
});
</script>
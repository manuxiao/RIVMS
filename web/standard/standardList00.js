<script type="text/javascript">
Ext.namespace('micrite.standard.standardList');
micrite.standard.standardList.SearchPanel = Ext.extend(micrite.ComplexGrid, {	
	initComponent:function() {	
	var pageSize = parseInt(Ext.getDom('pageSize').value,10);//得到用户设置的页面大小
	var Recordwin = Ext.data.Record.create([
	                                  		        {name:'id'},
	                                			    {name:'enterprise',mapping:'enterprise.unitName'},
	                                				'numberStart',
	                                				{name:'createDate'},
	                                				'numberEnd','pay','remain','sum',
	                                				{name:'takeDate'},
	                                				'type',
	                                				{name:'enterpriseId'}]);	
	
	var RecordwinEnterprise = Ext.data.Record.create([
	{name:'id'},
    {name:'enterprise',mapping:'enterprise.unitName'},
	'numberStart',
	{name:'createDate'},
	'numberEnd',
	'pay',
	'remain',
	'sum',
	{name:'takeDate'},
	'type',
	'createrId',
	'state',
	{name:'enterpriseId'}]);
	Ext.ux.PagingToolbar=Ext.extend(Ext.PagingToolbar,{
		pageSize:pageSize,
		doLoad:function(start){
        	var o = {}, pn = this.getParams();
        	o[pn.start] = start;
        	o[pn.limit] = this.pageSize;
        	if(this.fireEvent('beforechange', this, o) !== false){
            this.store.load({params:{start:start,limit:this.pageSize,queryString:'[state,0,=]'}});
        	}
    	}
	});
	//自定义表格行颜色
	Ext.ux.GridViewHeGeZheng=Ext.extend(Ext.grid.GridView,{   
        getRowClass:function(record,index){      
            if(record.get('type') == 0) {
            	if(record.get('remain') == 0)
            		return 'gray-row';//自定义css
            	return 'red-row';	
            }          
            else  
                return 'white-row';   
        }   
    });
	//检测记录表格行颜色
	Ext.ux.GridViewJiance=Ext.extend(Ext.grid.GridView,   
    {   
        getRowClass:function(record,index)   
        {      
            if(record.get('notepad') != null) {
            	return 'red-row';	
            }          
            else  
                return 'white-row';   
        }   
    });
    var postcheckstore = new Ext.data.JsonStore({
		id:'postStore',
		sortInfo : {   
        field : 'jianTime',   
        direction : 'desc'  
   		},
   		pruneModifiedRecords:true,
   		url:'check/findCheck.action',
		root:'data',
		fields:[
				{name: 'id'},
	            {name: 'jianTime'},
	            {name: 'paiHao'},
	            {name: 'heGe'},
	            {name: 'carId'},
	            {name: 'isPost'},
	            {name: 'createrId'},
	            {name: 'status'},
	            {name: 'notepad'},
	            {name: 'paiSe',mapping : 'paiSe.name'},
	            {name: 'cheXiu'},
	            {name: 'testKind'}
			],
		totalProperty:'totalCount'
	});
//	//显示在mainPanel中的主面板store
//	var store = new Ext.data.JsonStore({
//		id:'storeMainPanel',
//		sortInfo : {   
//        field : 'createDate',   
//        direction : 'desc'  
//   		},
//   		pruneModifiedRecords:true,//与修改行数据有关，现在没有用
//   		url:'history/findStandardSingle.action',
//		root:'data',
////		fields:[Recordwin],
//		reader : new Ext.data.JsonReader({    
//			id:"id"
//		}, Recordwin),
//		totalProperty:'totalCount'
//	});
//	store.load({params:{start:0,limit:pageSize,queryString:''}});
		
	// ------合格证明细数据------------------------
	var RecordDef = Ext.data.Record.create([{name: 'id'},{name: 'name'}]);
	var RecordDef2 = Ext.data.Record.create([{name: 'value'},{name: 'name'}]);	
	var storePaiSe = new Ext.data.Store({
		id: Ext.id(),    
		autoLoad:true,
		proxy : new Ext.data.HttpProxy({url: 'car/getPartner.action?type=7'}),    
		reader : new Ext.data.JsonReader({    
			id:"id"
		}, RecordDef),
		remoteSort: true   
	});	   	
	var comboPaiSe = new Ext.form.ComboBox({
		valueField: "id",
		displayField: "name",
		store: storePaiSe,
		triggerAction :'all',
		lazyRender:false 
	});	
	var storeColumn = new Ext.data.Store({
		id: Ext.id(),    
		autoLoad:true,
		proxy : new Ext.data.HttpProxy({url: 'car/getPartner.action?type=16'}),    
		reader : new Ext.data.JsonReader({    
			id:"value"
		}, RecordDef2),
		remoteSort: true   
	});	 
	var comboColumn = new Ext.form.ComboBox({
		valueField: "value",
		displayField: "name",
		store: storeColumn,
		triggerAction :'all',
		lazyRender:false 
	});	
	var storeCreater = new Ext.data.Store({
		id: Ext.id(),    
		autoLoad:true,
		proxy : new Ext.data.HttpProxy({url: 'getUserIdName.action'}),    
		reader : new Ext.data.JsonReader({    
			id:"id"
		}, RecordDef),
		remoteSort: true   
	});	 
	var comboCreater = new Ext.form.ComboBox({
		valueField: "id",
		displayField: "name",
		store: storeCreater,
		triggerAction :'all',
		lazyRender:false 
	});			
	var postcheckstore = new Ext.data.JsonStore({
		id:'postStore',
		sortInfo : {   
        field : 'jianTime',   
        direction : 'desc'  
   		},
   		pruneModifiedRecords:true,
   		url:'check/findCheck.action',
		root:'data',
		fields:[
				{name: 'id'},
	            {name: 'jianTime'},
	            {name: 'paiHao'},
	            {name: 'heGe'},
	            {name: 'carId'},
	            {name: 'isPost'},
	            {name: 'createrId'},
	            {name: 'status'},
	            {name: 'notepad'},
	            {name: 'paiSe',mapping : 'paiSe.name'},
	            {name: 'cheXiu'},
	            {name: 'testKind'}
			],
		totalProperty:'totalCount'
	});
	
	//sm要用在columns和grid两处地方
	var smStat = new Ext.grid.CheckboxSelectionModel();
	var cmStat = [
			new Ext.grid.RowNumberer(),{
        	header:'企业名称',dataIndex:'enterprise'
        },
        {
        	header:'领取次数',dataIndex:'pay'
        },
        {
        	header:'合格证总数',dataIndex:'sum'
        },{
        	header:'合格证剩余数量',dataIndex:'remain'
        },
        {
        	header:'领取日期',dataIndex:'takeDate'
        },
        smStat
		];	
	
	//双击mainPanel后的store，显示winEnterprise在中
	var winEnterpriseStore = new Ext.data.JsonStore({
		id:'winEnterpriseStore',
		sortInfo : {   
        field : 'takeDate',   
        direction : 'desc'  
   		},
   		pruneModifiedRecords:true,//与修改行数据有关，现在没有用
   		url:'standard/findStandard.action',
		root:'data',
//		fields:[RecordwinEnterprise],
		reader : new Ext.data.JsonReader({    
			id:"id"
		}, RecordwinEnterprise),
		totalProperty:'totalCount'
	});		
	
	//发放弹出窗口
	var addWin = new Ext.Window({
		title:'发放合格证',
		closeAction:'hide',//hide才正常
		padding:'5px',
		modal:true,
		maximizable:false,
		resizable:false,
		items:[{
		id:'standardInfo-form',
		xtype:'form',//与提交sumbit()对应
		baseCls:'x-plain',
		items:[{
			xtype:'fieldset',
            title:'合格证信息',
            layout:'form',
            collapsible: true,
            height: 150,
            items:[
            	{
            	xtype:'panel',
            	baseCls:'x-plain',
            	height:200,
            	layout:'column',
            	items:[{
            		baseCls:'x-plain',
            		columnWidth:.618,
            		height:200,
            		layout:'form',
            		labelWidth:65,
            		labelAlign:'right',
            		labelPad:10,
            		defaultType: 'numberfield',
            		items:[{
            			xtype:'hidden',
            			name:'standard.type',
            			value:1
            		},{	
            			id:'addcombo',
            			xtype:'combo',
            			anchor:'90%',
            			fieldLabel:'企业名称',
            			editable:true,
            			triggerAction:'all',
						loadingText:'加载中……',
						emptyText:'请选择',
						minChars:100,
						name:'standard.enterpriseId',
						hiddenName:'standard.enterpriseId',
						allowBlank:false,
						forceSelection:true,
						store:new Ext.data.JsonStore({
							url:'enterprise/findEnterprise.action',
							root:'data',
							totalProperty:'totalCount',
							fields:['id','unitName']
						}),
						valueField:'id',
						displayField:'unitName',
						mode:'remote',
						listeners:{
							'keyup':function(com2,e) {	
								if(e.getKey() == Ext.EventObject.DOWN||e.getKey() == Ext.EventObject.UP) {e.stopEvent();}else 
								{
									var key = com2.getRawValue();
									if(key != ''){
										var value = '[py,'+key+',like]';
										com2.store.setBaseParam('queryString',value);
									}else{com2.store.setBaseParam('queryString','');}
									com2.store.reload({params:{start:0,limit:20}});
								}
							}
						}
            		},{
            			id:'result',
            			xtype:'hidden',
            			fieldLabel:'合格证总数',
            			name:'standard.sum'
            		},{
            			id:'numberStart',
            			fieldLabel:'起始号码',
            			allowBlank:false,
            			anchor:'90%',
            			name:'standard.numberStart'
            		},{
            			id:'numberEnd',
            			fieldLabel:'结束号码',
            			allowBlank:false,
            			anchor:'90%',
            			name:'standard.numberEnd',
            			listeners:{
            				change:function() {
            					if(this.value<=Ext.getCmp('numberStart').getValue()){
            						Ext.Msg.alert('错误','结束号码不能小于起始号码！');
            						this.setValue(addWin.end);
            					}
            				}
            			}
            		},{
            			xtype:'datefield',
            			fieldLabel:'领取日期',
            			allowBlank:false,
            			forceSelection:true,
            			name:'standard.takeDate',
            			format:'Y-m-d',
            			value:new Date(),
            			editable:false,
            			anchor:'90%'
            		}            		
            		]
            	},{
            		baseCls:'x-plain',
            		columnWidth:.382,
            		layout:'form',
            		labelWidth:40,
            		items:[{
            			xtype:'panel',
            			cls : 'loginbgimage2',
            			baseCls : 'ex-panel'
            		}]
            	}]
            }]
		},{
			baseCls:'x-plain',
			buttonAlign:'center',
			buttons:[{
				text:'提交',
				handler:function() {
				if(!Ext.getCmp("standardInfo-form").getForm().isValid())return;   
				Ext.getCmp('result').setValue(parseInt(Ext.getCmp('numberEnd').getValue()-Ext.getCmp('numberStart').getValue())+1);	
				Ext.getCmp('standardInfo-form').getForm().submit({
					url: 'standard/saveStandard.action',
					method: 'POST',
					disabled:true,
					waitTitle:'请稍候',
					waitMsg:'请等待',
					success: function(form, action) {
                        obj = Ext.util.JSON.decode(action.response.responseText);
                        showMsg('success', obj.message); 
                        store.reload({params:{start:0,limit:pageSize,queryString:'[state,0,=],[type,1,=]'}});
                        addWin.hide();
                    },
                    failure: function(form, action) {
                        obj = Ext.util.JSON.decode(action.response.responseText);
                        showMsg('failure', obj.message);
                    }
				});
				}
			},{
				text:'重置',
				handler:function() {
				Ext.getCmp('standardInfo-form').getForm().reset();
				}
			},{
				text:'关闭',
				handler:function() {
					addWin.hide();
				}
			}]
		}]
		}],
		width:450,
        height:280
	});
	var addWin2 = new Ext.Window({
		title:'增加合格证库存',
		closeAction:'hide',
		padding:'5px',
		modal:true,
		maximizable:true,
		resizable:false,
		items:[{
		id:'standardInfo-form2',
		xtype:'form',
		baseCls:'x-plain',
		items:[{
			xtype:'fieldset',
            title:'合格证信息',
            layout:'form',
            collapsible: true,
            height: 120,
            items:[
            	{
            	xtype:'panel',
            	baseCls:'x-plain',
            	height:200,
            	layout:'column',
            	items:[{
            		baseCls:'x-plain',
            		columnWidth:.618,
            		height:200,
            		layout:'form',
            		labelWidth:65,
            		labelAlign:'right',
            		labelPad:10,
            		defaultType: 'numberfield',
            		items:[{
            			xtype:'hidden',
            			name:'standard.type',
            			value:0
            		},{
            			xtype:'hidden',
            			id:'carCheck',
            			name:'standard.enterpriseId',
            			value:1//对应企业表的id=1记录
            		},{
            			id:'result2',
            			xtype:'hidden',
            			fieldLabel:'合格证总数',
            			name:'standard.sum'
            		},{
            			id:'numberStart2',
            			fieldLabel:'起始号码',
            			allowBlank:false,
            			anchor:'90%',
            			name:'standard.numberStart'
            		},{
            			id:'numberEnd2',
            			fieldLabel:'结束号码',
            			allowBlank:false,
            			anchor:'90%',
            			name:'standard.numberEnd'
            		},{
            			xtype:'datefield',
            			fieldLabel:'录入日期',
            			allowBlank:false,
            			forceSelection:true,
            			name:'standard.takeDate',
            			format:'Y-m-d',
            			value:new Date(),
            			editable:false,
            			anchor:'90%'
            		}
            		]
            	},{
            		baseCls:'x-plain',
            		columnWidth:.382,
            		layout:'form',
            		labelWidth:40,
            		items:[{
            			xtype:'panel',
            			cls : 'loginbgimage',
            			baseCls : 'ex-panel'
            		}]
            	}]
            }]
		},{
			baseCls:'x-plain',
			buttonAlign:'center',
			buttons:[{
				text:'提交',
				handler:function() {
				Ext.getCmp('result2').setValue(parseInt(Ext.getCmp('numberEnd2').getValue()-Ext.getCmp('numberStart2').getValue())+1);
				Ext.getCmp('standardInfo-form2').getForm().submit({
					url: 'standard/saveStandard.action',
					method: 'POST',
					disabled:true,
					waitTitle:'请稍候',
					waitMsg:'请等待',
					success: function(form, action) {
                        obj = Ext.util.JSON.decode(action.response.responseText);
                        showMsg('success', obj.message); 
                        store.reload({params:{start:0,limit:pageSize,queryString:'[state,0,=],[type,0,=]'}});
                        addWin.hide();
                    },
                    failure: function(form, action) {
                        obj = Ext.util.JSON.decode(action.response.responseText);
                        showMsg('failure', obj.message);
                    }
				});
				}
			},{
				text:'重置',
				handler:function() {
				Ext.getCmp('standardInfo-form2').getForm().reset();
				}
			},{
				text:'关闭',
				handler:function() {
					addWin2.hide();
				}
			}]
		}]
		}],
		width:450,
        height:250
	});  
	//双击主界面弹出的具体记录界面	
	var smEnterpriseWin = new Ext.grid.CheckboxSelectionModel();
	var cmEnterpriseWin = [
		new Ext.grid.RowNumberer(),{
		header:'编号',dataIndex:'id',sortable:true,hidden:true
    },{
    	header:'企业名称',dataIndex:'enterprise'
    },{
    	header:'起始号码',dataIndex:'numberStart'
    },{
    	header:'结束号码',dataIndex:'numberEnd'
    },{
    	header:'合格证总数',dataIndex:'sum'
    },{
    	header:'发放数量',dataIndex:'pay'
    },{
    	header:'剩余数量',dataIndex:'remain'
    },{
    	header:'领取日期',dataIndex:'takeDate'
    },smEnterpriseWin
	];
	//通过checkbox选择或反选时触发选区轮换的一个制定选区模型
	var sm = new Ext.grid.CheckboxSelectionModel();  
	this.addListener('rowdblclick', showAddCheckFn);   
	function showAddCheckFn(){
		if(sm.getCount()==1){
			var record = sm.getSelected();
			var enterpriseId = record.get('enterpriseId');
			winEnterpriseStore.load({params:{start:0,limit:pageSize,queryString:'[state,0,=],[enterpriseId,'+enterpriseId+',=]'}});
			enterpriseWin.show();
		}else{Ext.MessageBox.alert('提示', '请选择一条记录！');}
	};	
	var enterprisePanel = new Ext.grid.GridPanel({
        border:false,
        stripeRows:true,
        view:new Ext.ux.GridViewHeGeZheng({forceFit:true}),
        selModel:new Ext.grid.RowSelectionModel(),
        cm:cmEnterpriseWin,
        store:winEnterpriseStore,
        sm:smEnterpriseWin
	});
	var checkWin = new Ext.grid.GridPanel({
        border:false,
        stripeRows:true,
        view:new Ext.grid.GridView({forceFit:true}),
        selModel:new Ext.grid.RowSelectionModel(),
       	cm:cm,
        store:checkStore,
//        sm:smcheck,
        listeners: {
        }
    });
//	var enterprisePanel2 = new Ext.grid.GridPanel({
//        border:false,
//        stripeRows:true,
//        view:new Ext.ux.GridViewHeGeZheng({forceFit:true}),
//        selModel:new Ext.grid.RowSelectionModel(),
//        tbar:[[]],
//        bbar:new Ext.ux.PagingToolbar({
//			pageSize:pageSize,
//			store:winEnterpriseStore,
//			prependButtons:false,
//			items:[
//				  {
//					text:'修改',
//					iconCls:'edit-icon',
//					handler:function() {
//					  if(smEnterpriseWin.getCount()==1){ 
//						    	var theForm = Ext.getCmp("editEnterpriseWinID").getForm(); 
//							 	var record = enterprisePanel.getSelectionModel().getSelected();	    	 			
//							 	theForm.reset();	
//				        	 	editEnterpriseWin.show();
//							 	Ext.getCmp("editEnterpriseWinID").getForm().loadRecord(record);							 	
//						      }else{Ext.MessageBox.alert('提示', '请选择一条记录！');}       
//					}							
//				},
//				{
//					text:'明细',
//					iconCls:'save-icon',
//					handler:function() {
//			     	if(smEnterpriseWin.getCount()==1){ 
//					    	var theForm = Ext.getCmp("heGeDetailWindowID").getForm(); 
//						 	var record = enterprisePanel.getSelectionModel().getSelected();		    		
//						 	theForm.reset();
//						 	heGeDetailWindow.show();		 		
//						 	var numberStart = record.get('numberStart');
//						 	var numberEnd = record.get('numberEnd');
//							postcheckstore.load({params:{start:0,limit:pageSize,queryString:'[isPost,1,=],[testKind,2,=],[heGe,'+numberStart+',>=],[heGe,'+numberEnd+',<=]'}}); 		 	
//					      }else{Ext.MessageBox.alert('提示', '请选择一条记录！');}						  
//					}							
//				}
//			],
//			beforePageText:"当前第",   
//            afterPageText:"页，共{0} 页",   
//	            lastText:"尾页",   
//            nextText :"下一页",   
//            prevText :"上一页",   
//            firstText :"首页",   
//            refreshText:"刷新页面",
//			displayInfo:true,
//			plugins: new Ext.ux.ProgressBarPager(),
//			displayMsg:'显示 {0} - {1}，共 {2} 条',
//			emptyMsg:'没有记录'
//		}),
//       	cm:cmEnterpriseWin,
//        store:winEnterpriseStore,
//        sm:smEnterpriseWin
//    });	
	
	var editEnterpriseWin = new Ext.Window({ 
		modal:true,
	   	title:'企业合格证记录修改',
	   	closeAction:'hide',
		padding:'5px',
		width : 400,
		items : [{
        id: 'editEnterpriseWinID',
		labelAlign : 'right',
		labelWidth : 80,
		xtype:'form',
		bodyStyle : 'padding:1px',
		baseCls:'x-plain',	
        items: [
        {
            xtype: 'fieldset',           
            title:'修改记录',
            layout:'form',
            collapsible: true,           
            labelWidth: 100,
            defaults: {width: 230},    // Default config options for child item										// items
            defaultType: 'textfield',
            items: [{
            	xtype:'hidden',
                name: 'id',
                allowBlank:false
            }, 
            {
            	xtype:'hidden',
                name: 'type',
                allowBlank:false
            },
            {
            	xtype:'hidden',
                name: 'pay',
                allowBlank:false
            },
            {
            	xtype:'hidden',
                name: 'sum',
                allowBlank:false
            },
            {
            	xtype:'hidden',
                name: 'state',
                allowBlank:false
            },
            {
            	xtype:'hidden',
                name: 'createrId',
                allowBlank:false
            },
            {
            	fieldLabel: '记录生成日期',
            	xtype:'uxspinnerdate',
                name: 'createDate',
                format:'Y-m-d',
                readOnly:true,
                allowBlank:false
            },
            {
            	fieldLabel: '领取日期',
            	xtype:'uxspinnerdate',
                name: 'takeDate',
                format:'Y-m-d',
                readOnly:true,
                allowBlank:false
            },
            {
            	xtype:'hidden',
                name: 'remain',
                allowBlank:false
            },
            {
            	xtype:'hidden',
                name: 'enterpriseId',
                allowBlank:false
            },
            {
                fieldLabel: '起始号码',
                name: 'numberStart',
                allowBlank:false
            },{
                fieldLabel: '结束号码',
                name: 'numberEnd',
                allowBlank:false
            }]               
        }],
            buttonAlign:'center',
            buttons: [{
                text: mbLocale.submitButton,
                // 提交按钮 之前校验不通过就不提交到后台
                handler: function(){
                	if(!Ext.getCmp("editEnterpriseWinID").getForm().isValid())return;
                    Ext.getCmp("editEnterpriseWinID").getForm().submit({
                        url: 'standard/editStandard.action',
                        method: 'POST',
                        disabled:true,
                        waitMsg: mbLocale.waitingMsg,                       
                        success: function(form, action) {
                            obj = Ext.util.JSON.decode(action.response.responseText);
                            showMsg('success', obj.message); 
                            //新加，相当于刷新 EnterpriseWin界面
                            editEnterpriseWin.hide();                           
                		 	var record = this.getSelectionModel().getSelected();		    				 		
                		 	var enterpriseId = record.get('enterpriseId');
                            winEnterpriseStore.load({params:{start:0,limit:pageSize,queryString:'[enterpriseId,'+enterpriseId+',=],[state,0,=]'}});
                        },
                        failure: function(form, action) {
                            obj = Ext.util.JSON.decode(action.response.responseText);
                            showMsg('failure', message); 
                        }
                    });              
            	}
            },    
            {
            text: '关闭',
            scope:this,
            handler: function(){
            	editEnterpriseWin.hide();
            }
        }]
    }]}); 

	var enterpriseWin = new Ext.Window({  
	   	modal:true,
	   	title:'企业合格证记录',
		closeAction:'hide',
		padding:'5px',
		width : 760,
		autoHeight : true,
		items : [{
			xtype:'form',
			labelAlign : 'right',
			labelWidth : 100,
			bodyStyle : 'padding:1px',
			baseCls:'x-plain',  
			id:'EnterpriseWinID',
	        items: [{
				xtype : 'tabpanel',
				plain : true,
				bodyBorder : false,
				activeTab : 0,
				height : 300,
				width : 730,
				defaults : {bodyStyle : 'padding:2px'},
				items : [{title : '企业合格证记录',layout : 'fit',items :[enterprisePanel]}]
	    	}],	
	        buttonAlign:'center',
	        buttons: [{
		        text: '关闭',
		        scope:this,
		        handler: function(){
		        	enterpriseWin.hide();
		        }
	        }]
    	}]
    }); 
	enterprisePanel.addListener('rowdblclick', showEnterpriseWinFn);   
	function showEnterpriseWinFn(){
	      if(smEnterpriseWin.getCount()==1){ 
	    	var theForm = Ext.getCmp("heGeDetailWindowID").getForm(); 
		 	var record = enterprisePanel.getSelectionModel().getSelected();		    		
		 	theForm.reset();
		 	heGeDetailWindow.show();		 		
		 	var numberStart = record.get('numberStart');
		 	var numberEnd = record.get('numberEnd');
			postcheckstore.load({params:{start:0,limit:pageSize,queryString:'[isPost,1,=],[testKind,2,=],[heGe,'+numberStart+',>=],[heGe,'+numberEnd+',<=]'}}); 		 	
	      }else{Ext.MessageBox.alert('提示', '请选择一条记录！');}		
	}; 	 
		
	var postSMCheck = new Ext.grid.CheckboxSelectionModel();
	var postCM = new Ext.grid.ColumnModel({
		columns:[	
			new Ext.grid.RowNumberer(),
			{
				header: '合格证号',
				width: 100, sortable: true, dataIndex: 'heGe'
			},
  			{									
				header: '核销日期',
				width: 100, sortable: true, dataIndex: 'jianTime'
			}, 			
			{
				header: '车牌号码',
				width: 100, sortable: true, dataIndex: 'paiHao'
			},	           		            
			{
				header: '车牌颜色',
				width: 100, sortable: true, dataIndex: 'paiSe'
			},
			{
				header: '操作员',
				width: 100, sortable: true, dataIndex: 'createrId',	
				editor:comboCreater,renderer:myRenderer
			},
			{
				header: '维修企业',
				width: 180, sortable: true, dataIndex: 'cheXiu'			   
			}
		]
	});		
 	var origCheckGridPanel = new Ext.grid.GridPanel({
		id:'origCheckGridPanelID',
        border:false,
        stripeRows:true,
        selModel:new Ext.grid.RowSelectionModel(),
        view:new Ext.ux.GridViewJiance({forceFit:true}),	        
        bbar:new Ext.ux.PagingToolbar({
			pageSize:pageSize,
			store:postcheckstore,
			prependButtons:false,
			items:['提示 （<font color="red">红色：有备注的记录</font>','-','<font color="grey">灰色：无备注</font>）'],
			beforePageText:"当前第",   
         afterPageText:"页，共{0} 页",   
            lastText:"尾页",   
         nextText :"下一页",   
         prevText :"上一页",   
         firstText :"首页",   
         refreshText:"刷新页面",
			displayInfo:true,
			plugins: new Ext.ux.ProgressBarPager(),
			displayMsg:'显示 {0} - {1}，共 {2} 条',
			emptyMsg:'没有记录'
		}),
       	cm:postCM,
        store:postcheckstore,
        sm:postSMCheck
    });	
	var heGeDetailWindow = new Ext.Window({  
	   	modal:true,
	   	title:'合格证记录明细',
		closeAction:'hide',
		padding:'5px',
		width : 760,
		autoHeight : true,
		items : [{
		xtype:'form',
		labelAlign : 'right',
		labelWidth : 100,
		bodyStyle : 'padding:1px',
		id:'heGeDetailWindowID',
		baseCls:'x-plain',  
     items: [{ 
        		xtype: 'fieldset',
        		padding:'1px',
        		title:'合格证明细',       		
         	layout : 'form',        				
             collapsible: true,
             defaults: {},    // Default config options for child items
             height: 80, 
             labelWidth: 60,		                          
         items: [
		    {
         	xtype:'textarea',
         	name: 'notepad',
         	id:'Notepad',
         	width: 620,
         	height: 40,
         	emptyText:'查看备注请点击下列记录',
             fieldLabel: '备注/回单'
         }
     ]
     },{
		xtype : 'tabpanel',
		plain : true,
		bodyBorder : false,
		activeTab : 0,
		height : 300,
		width : 730,
		defaults : {bodyStyle : 'padding:2px'},
		items : [ 
			{title : '合格证明细数据',layout : 'fit',items :[origCheckGridPanel]}
			]
 		}],	
        buttonAlign:'center',
        buttons: [{
        text: '关闭',
        scope:this,
        handler: function(){
        	heGeDetailWindow.hide();
        }
     }]
 }]}); 		
	origCheckGridPanel.addListener('rowclick', showNotepad);   
	function showNotepad(){
	      if(postSMCheck.getCount()==1){ 
	    	var theForm = Ext.getCmp("heGeDetailWindowID").getForm(); 
		 	var record = origCheckGridPanel.getSelectionModel().getSelected();	    	 			
		 	theForm.reset();	
		 	var notepad = record.get('notepad');
		 	var comp = Ext.getCmp('Notepad');			
				comp.setValue(notepad);
	      }else{Ext.MessageBox.alert('提示', '请选择一条记录！');}
	};
	
	var config = {
		    view:new Ext.ux.GridViewHeGeZheng({forceFit:true}),
			advSearchField : [[	
		          {name:'',value:'',xtype:'textfield'}				          
		      ]],	  
	        compSet: [
	             {url:0,reader:0,columns:0,bbarAction:0},
	             {url:1,reader:1,columns:1,bbarAction:0,tbarAction:-1}
	        ],
			searchMenu : [
				 '企业合格证查询',
//				 '入库合格证查询',
				 '历史记录查询'
			],
			searchFields :[[
//				 {advSearch:true},
				 {id:'text31',xtype:'tbtext',text:'起始日期'},
					{	
						id:'datefield31',
						name:'startDate',
						xtype:'datefield',
						format:'Y-m-d',
//						minValue:'2000-01-01',
						value:new Date()
					},
					{id:'text32',xtype:'tbtext',text:'截止日期'},
					{
						id:'datefield32',
						name:'endDate',
						xtype:'datefield',
						format:'Y-m-d',
						value:new Date()
					},
					{	xtype:'combo',
            			anchor:'90%',
            			fieldLabel:'企业名称',
            			editable:true,
            			triggerAction:'all',
						loadingText:'加载中……',
						emptyText:'请选择',
						minChars:100,
						name:'enterpriseId',
						hiddenName:'enterpriseId',
						allowBlank:false,
						width:160,
						forceSelection:true,
						store:new Ext.data.JsonStore({
							url:'enterprise/findEnterprise.action',
							root:'data',
							totalProperty:'totalCount',
							fields:['id','unitName']							
						}),				           
						valueField:'id',
						displayField:'unitName',
						mode:'remote',
					listeners:{
						'keyup':function(com1,e) {	
						if(e.getKey() == Ext.EventObject.DOWN||e.getKey() == Ext.EventObject.UP) {e.stopEvent();}else 
							{
								var key = com1.getRawValue();
								if(key != ''){
									var value = '[py,'+key+',like]';
									com1.store.setBaseParam('queryString',value);
								}else{com1.store.setBaseParam('queryString','');}
								com1.store.reload({params:{start:0,limit:20}});
						}
						}
					}
				}	
	            ],
	            [
				 {id:'text41',xtype:'tbtext',text:'起始日期'},
					{	
						id:'datefield41',
						name:'startDate',
						xtype:'datefield',
						format:'Y-m-d',
						value:new Date()
					},
					{id:'text42',xtype:'tbtext',text:'截止日期'},
					{
						id:'datefield42',
						name:'endDate',
						xtype:'datefield',
						format:'Y-m-d',
						value:new Date()
					},
					{	xtype:'combo',
            			anchor:'90%',
            			fieldLabel:'企业名称',
            			editable:true,
            			triggerAction:'all',
						loadingText:'加载中……',
						emptyText:'请选择',
						minChars:100,
						name:'enterpriseId',
						hiddenName:'enterpriseId',
						allowBlank:false,
						width:160,
						forceSelection:true,
						store:new Ext.data.JsonStore({
							url:'enterprise/findEnterprise.action',
							root:'data',
							totalProperty:'totalCount',
							fields:['id','unitName']							
						}),				           
						valueField:'id',
						displayField:'unitName',
						mode:'remote',
					listeners:{
						'keyup':function(com1,e) {	
						if(e.getKey() == Ext.EventObject.DOWN||e.getKey() == Ext.EventObject.UP) {e.stopEvent();}else 
							{
								var key = com1.getRawValue();
								if(key != ''){
									var value = '[py,'+key+',like]';
									com1.store.setBaseParam('queryString',value);
								}else{com1.store.setBaseParam('queryString','');}
								com1.store.reload({params:{start:0,limit:20}});
						}
						}
					}
				}
	            ]],
	        urls: ['standard/findStandard.action','history/findStandardSingle.action'],//Single
	        readers : [Recordwin,RecordwinEnterprise],  
			columnsArray: [cmEnterpriseWin,cmStat],
	        tbarActions : [],
	         bbarActions:[[   
				{
					text:'发放',
					iconCls:'out-icon',
					listeners:{
						click:function(){
							addWin.show();
							Ext.Ajax.request({
								url:'standard/numberStartOrEnd.action',
								method:'POST',
								success:function(response) {
									obj = Ext.util.JSON.decode(response.responseText);
									Ext.getCmp('numberStart').setValue(obj.start);
									Ext.getCmp('numberEnd').setValue(obj.end);
								}
							});
						}
					}
				},{
					text:'增加',
					iconCls:'add-icon',
					listeners:{
						click:function(){
							addWin2.show();
						}
					}
				},
				'提示 （<font color="red">红色：可用库存</font>','-','<font color="grey">灰色：用完的库存</font>）'                       
	         ]],
	         sm:sm
	    }; // eo config object 
		// apply config
		Ext.apply(this, Ext.apply(this.initialConfig, config)); 
		micrite.standard.standardList.SearchPanel.superclass.initComponent.apply(this, arguments);
    } // eo function initComponent	
}); //eo extend

// 处理多语言
try {standardListLocale();} catch (e) {}		
Ext.onReady(function() {
	Ext.QuickTips.init();	
	var formPanel = new micrite.standard.standardList.SearchPanel();	
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
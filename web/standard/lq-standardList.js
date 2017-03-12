<script type="text/javascript">
Ext.onReady(function() {
	Ext.QuickTips.init();
	var pageSize = parseInt(Ext.getDom('pageSize').value,10);//得到用户设置的页面大小
	//修改分页工具条的刷新传递的参数，加入state=0查询
	Ext.ux.PagingToolbar=Ext.extend(
	Ext.PagingToolbar,{
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
	Ext.ux.GridView=Ext.extend(   
    Ext.grid.GridView,   
    {   
        getRowClass:function(record,index)   
        {      
            if(record.get('type') == 0) {
            	if(record.get('remain') == 0)
            		return 'gray-row';//自定义css
            	return 'red-row';	
            }          
            else  
                return 'white-row';   
        }   
    });
	//主要的store，显示在mainPanel中
	var store = new Ext.data.JsonStore({
		id:'myStore',
//		remoteSort:true,
		sortInfo : {   
        field : 'takeDate',   
        direction : 'desc'  
   		},
   		pruneModifiedRecords:true,//与修改行数据有关，现在没有用
		url:'standard/findStandard.action',
		root:'data',
		fields:[{name:'id'},{
			name:'enterprise',mapping:'enterprise.unitName'},'numberStart',{
			name:'createDate',
			type:'date',
			dateFormat:'Y-m-dTH:i:s'
			},'numberEnd','pay','remain','sum',{
			name:'takeDate',
			type:'date',
			dateFormat:'Y-m-dTH:i:s'
			},'type',{
			name:'waijian1',mapping:'enterprise.id'}],
		totalProperty:'totalCount'
	});
	store.load({params:{start:0,limit:pageSize,queryString:'[state,0,=]'}});
	//企业搜索组件
	var condition1 = [{id:'separator1',xtype:'tbseparator'},{id:'btn1'},{id:'text1',xtype:'tbtext',text:'企业名称',emptyText:'输入企业名称或拼音首字母'},
					{id:'spacer1',xtype: 'tbspacer'},{id:'spacer2',xtype: 'tbspacer'},
						{	
							id:'combo1',
							xtype:'combo',
							triggerAction:'all',
							loadingText:'加载中……',
							minChars:100,//默认为4
							name:'unitName',
							height:100,
							//forceSelection:true,
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
								},
								//类似百度查询
//								'keyup':function(com1,e) {	
//									if(e.getKey() == Ext.EventObject.DOWN||e.getKey() == Ext.EventObject.UP) {e.stopEvent();}else 
//									{
//										com1.store.setBaseParam('start',0);
//										com1.store.setBaseParam('limit',100);
//										var key = com1.getRawValue();
//										if(key != ''){
//											//alert(key); 
//											var value = '['+com1.name+','+key+',like]';
//											com1.store.reload({params:{queryString:value}});
//										}else{com1.store.reload({params:{queryString:''}});}
//									}
//								},
								'select':function(com1) {
									store.proxy = new Ext.data.HttpProxy({url:'standard/findStandard.action'});
									var value = '[waijian1,'+com1.getValue()+',=],[state,0,=]';
									store.setBaseParam('queryString',value);
									store.reload({params:{start:0,limit:pageSize}});
								}
							}
						},{id:'spacer3',xtype: 'tbspacer'},{id:'spacer4',xtype: 'tbspacer'},{
							id:'btn3',
							text:'查询',
							iconCls:'search-icon',
							handler:function() {
								var searchValue = Ext.getCmp('combo1').getRawValue();
								if(searchValue=='') {
									Ext.Msg.alert('提示','请输入关键字或拼音首字母查询！')
								}else {
									//一个store可以对应其他的action
									store.proxy = new Ext.data.HttpProxy({url:'standard/findStandardByShorthand.action'});
									store.reload({//reload感觉用处很大，看参数就知道
										params:{
											start:0,
											limit:pageSize,
											key:searchValue
										},
										callback:function(r) {
											if(r.length==0) {
												Ext.Msg.show({
													title:'查询',
   													msg:'<br>很遗憾，没有查到相关记录',
   													width:230,
   													buttons:Ext.MessageBox.OK,
   													icon:'qq-icon'
												});
											}
										}
									});
								}	
							}
							
						},{id:'separator2',xtype:'tbseparator'}
						];
	//日期查询组件					
	var condition2 = [{id:'separator3',xtype: 'tbseparator'},{id:'btn2'},{id:'text2',xtype:'tbtext',text:'起始日期'},
					{id:'spacer5',xtype: 'tbspacer'},{id:'spacer6',xtype: 'tbspacer'},
						{	
							id:'datefield1',
							name:'startDate',
							xtype:'datefield',
							format:'Y-m-d',
							editable:false,
							minValue:'1980-01-01',
							value:'1980-01-01'
						},{id:'text5',xtype:'tbtext',text:'截止日期'},{id:'spacer7',xtype: 'tbspacer'},{id:'spacer8',xtype: 'tbspacer'},{
							id:'datefield2',
							name:'endDate',
							xtype:'datefield',
							format:'Y-m-d',
							editable:false,
							value:new Date()
						},{id:'spacer9',xtype: 'tbspacer'},{id:'spacer10',xtype: 'tbspacer'},{
							id:'btn4',
							iconCls:'search-icon',
							text:'查询',
							
							handler:function(){
								store.proxy = new Ext.data.HttpProxy({url:'standard/findStandardByDateSpacing.action'});
								//用setBaseParam有不妥的地方
								store.setBaseParam(Ext.getCmp('datefield1').name,Ext.getCmp('datefield1').value+' 00:00');
								store.setBaseParam(Ext.getCmp('datefield2').name,Ext.getCmp('datefield2').value+' 23:59');
								store.setBaseParam('standard.state',0);
								store.reload({params:{start:0,limit:pageSize}});
							}
						},{id:'separator4',xtype:'tbseparator'}
						];
	var operate = [{id:'fill',xtype:'tbfill'},{
					id:'btn_add',
					text:'操作菜单',
					menu:[{
						text:'增加合格证库存',
						iconCls:'add-icon',
						handler:function(){
							addWin2.show();
						}
					}]
					}];	
	//sm要用在columns和grid两处地方
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns:[
			new Ext.grid.RowNumberer(),{
			header:'编号',dataIndex:'id',sortable:true,hidden:true
        },{
        	header:'企业名称',dataIndex:'enterprise'
        },{
        	header:'起始号码',dataIndex:'numberStart'
        	//,editor:new Ext.form.NumberField({allowBlank: false,allowNegative: false})
        },{
        	header:'结束号码',dataIndex:'numberEnd'
        },{
        	header:'合格证总数',dataIndex:'sum'
        },{
        	header:'发放数量',dataIndex:'pay'
        },{
        	header:'剩余数量',dataIndex:'remain'
        },{
        	header:'领取日期',dataIndex:'takeDate',
//        	editor:new Ext.form.DateField({
//        	editable:false,	
//        	allowBlank: false})
        	renderer:Ext.util.Format.dateRenderer('Y-m-d')
        },
        sm
		]
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
						name:'standard.enterprise.id',
						hiddenName:'standard.enterprise.id',
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
            			name:'standard.enterprise.id',
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
	var choose = 1;
	var win = new Ext.grid.GridPanel({
		id:'egp01',
        border:false,
        stripeRows:true,
        view:new Ext.ux.GridView({forceFit:true}),
        selModel:new Ext.grid.RowSelectionModel(),
        tbar:[['-',{
        	id:'searchbtn',
        	text:'企业查询',
			iconCls :'repair-icon',
			menu:[{
				text:'企业查询',
				iconCls:'repair-icon',
				listeners:{
					'click':function(){
						if(choose != 1) {
						Ext.getCmp('searchbtn').setText('企业查询');
						Ext.getCmp('searchbtn').setIconClass('repair-icon');
						Ext.getCmp('egp01').getTopToolbar().remove('separator3');
						Ext.getCmp('egp01').getTopToolbar().remove('btn2');
						Ext.getCmp('egp01').getTopToolbar().remove('text2');
						Ext.getCmp('egp01').getTopToolbar().remove('spacer5');
						Ext.getCmp('egp01').getTopToolbar().remove('spacer6');
						Ext.getCmp('egp01').getTopToolbar().remove('datefield1');
						Ext.getCmp('egp01').getTopToolbar().remove('text5');
						Ext.getCmp('egp01').getTopToolbar().remove('spacer7');
						Ext.getCmp('egp01').getTopToolbar().remove('spacer8');
						Ext.getCmp('egp01').getTopToolbar().remove('datefield2');
						Ext.getCmp('egp01').getTopToolbar().remove('spacer9');
						Ext.getCmp('egp01').getTopToolbar().remove('spacer10');
						Ext.getCmp('egp01').getTopToolbar().remove('btn4');
						Ext.getCmp('egp01').getTopToolbar().remove('separator4');
						Ext.getCmp('egp01').getTopToolbar().remove('fill');
						Ext.getCmp('egp01').getTopToolbar().remove('btn_add');
						Ext.getCmp('egp01').getTopToolbar().add(condition1);
						Ext.getCmp('egp01').getTopToolbar().add(operate);
						Ext.getCmp('egp01').getTopToolbar().doLayout();
						choose = 1;
						}
					}
				}
				},{text:'日期查询',
				iconCls:'date-icon',
				listeners:{
					'click':function(){
						if(choose != 2) {
						Ext.getCmp('searchbtn').setText('日期查询');
						Ext.getCmp('searchbtn').setIconClass('date-icon');
						Ext.getCmp('egp01').getTopToolbar().remove('separator1');
						Ext.getCmp('egp01').getTopToolbar().remove('separator2');
						Ext.getCmp('egp01').getTopToolbar().remove('btn1');
						Ext.getCmp('egp01').getTopToolbar().remove('btn3');
						Ext.getCmp('egp01').getTopToolbar().remove('text1');
						Ext.getCmp('egp01').getTopToolbar().remove('spacer1');
						Ext.getCmp('egp01').getTopToolbar().remove('spacer2');
						Ext.getCmp('egp01').getTopToolbar().remove('spacer3');
						Ext.getCmp('egp01').getTopToolbar().remove('spacer4');
						Ext.getCmp('egp01').getTopToolbar().remove('combo1');
						Ext.getCmp('egp01').getTopToolbar().remove('fill');
						Ext.getCmp('egp01').getTopToolbar().remove('btn_add');
						Ext.getCmp('egp01').getTopToolbar().add(condition2);
						Ext.getCmp('egp01').getTopToolbar().add(operate);
						Ext.getCmp('egp01').getTopToolbar().doLayout();
						choose = 2;
						}
					}
				}
				},{
					text:'入库查询',
					iconCls:'door-in-icon',
					handler:function() {
						if(choose !=3) {
							Ext.getCmp('searchbtn').setText('入库查询');
							Ext.getCmp('searchbtn').setIconClass('door-in-icon');
							Ext.getCmp('egp01').getTopToolbar().remove('separator3');
							Ext.getCmp('egp01').getTopToolbar().remove('btn2');
							Ext.getCmp('egp01').getTopToolbar().remove('text2');
							Ext.getCmp('egp01').getTopToolbar().remove('spacer5');
							Ext.getCmp('egp01').getTopToolbar().remove('spacer6');
							Ext.getCmp('egp01').getTopToolbar().remove('datefield1');
							Ext.getCmp('egp01').getTopToolbar().remove('text5');
							Ext.getCmp('egp01').getTopToolbar().remove('spacer7');
							Ext.getCmp('egp01').getTopToolbar().remove('spacer8');
							Ext.getCmp('egp01').getTopToolbar().remove('datefield2');
							Ext.getCmp('egp01').getTopToolbar().remove('spacer9');
							Ext.getCmp('egp01').getTopToolbar().remove('spacer10');
							Ext.getCmp('egp01').getTopToolbar().remove('btn4');
							Ext.getCmp('egp01').getTopToolbar().remove('separator4');
							Ext.getCmp('egp01').getTopToolbar().remove('separator1');
							Ext.getCmp('egp01').getTopToolbar().remove('separator2');
							Ext.getCmp('egp01').getTopToolbar().remove('btn1');
							Ext.getCmp('egp01').getTopToolbar().remove('btn3');
							Ext.getCmp('egp01').getTopToolbar().remove('text1');
							Ext.getCmp('egp01').getTopToolbar().remove('spacer1');
							Ext.getCmp('egp01').getTopToolbar().remove('spacer2');
							Ext.getCmp('egp01').getTopToolbar().remove('spacer3');
							Ext.getCmp('egp01').getTopToolbar().remove('spacer4');
							Ext.getCmp('egp01').getTopToolbar().remove('combo1');
//							Ext.getCmp('egp01').getColumnModel.getColumn.setHidden(1,true); 
//							Ext.getCmp('egp01').getColumnHeader(1).setHidden(1,true);
							Ext.getCmp('egp01').getTopToolbar().doLayout();
							store.proxy = new Ext.data.HttpProxy({url:'standard/findStandard.action'});
							store.reload({params:{start:0,limit:pageSize,queryString:'[state,0,=],[type,0,=]'}});
							choose = 3;
						}
					}
				},{
					text:'发放查询',
					iconCls:'door-out-icon',
					handler:function() {
						if(choose!=4) {
							Ext.getCmp('searchbtn').setText('发放查询');
							Ext.getCmp('searchbtn').setIconClass('door-out-icon');
							Ext.getCmp('egp01').getTopToolbar().remove('separator3');
							Ext.getCmp('egp01').getTopToolbar().remove('btn2');
							Ext.getCmp('egp01').getTopToolbar().remove('text2');
							Ext.getCmp('egp01').getTopToolbar().remove('spacer5');
							Ext.getCmp('egp01').getTopToolbar().remove('spacer6');
							Ext.getCmp('egp01').getTopToolbar().remove('datefield1');
							Ext.getCmp('egp01').getTopToolbar().remove('text5');
							Ext.getCmp('egp01').getTopToolbar().remove('spacer7');
							Ext.getCmp('egp01').getTopToolbar().remove('spacer8');
							Ext.getCmp('egp01').getTopToolbar().remove('datefield2');
							Ext.getCmp('egp01').getTopToolbar().remove('spacer9');
							Ext.getCmp('egp01').getTopToolbar().remove('spacer10');
							Ext.getCmp('egp01').getTopToolbar().remove('btn4');
							Ext.getCmp('egp01').getTopToolbar().remove('separator4');
							Ext.getCmp('egp01').getTopToolbar().remove('separator1');
							Ext.getCmp('egp01').getTopToolbar().remove('separator2');
							Ext.getCmp('egp01').getTopToolbar().remove('btn1');
							Ext.getCmp('egp01').getTopToolbar().remove('btn3');
							Ext.getCmp('egp01').getTopToolbar().remove('text1');
							Ext.getCmp('egp01').getTopToolbar().remove('spacer1');
							Ext.getCmp('egp01').getTopToolbar().remove('spacer2');
							Ext.getCmp('egp01').getTopToolbar().remove('spacer3');
							Ext.getCmp('egp01').getTopToolbar().remove('spacer4');
							Ext.getCmp('egp01').getTopToolbar().remove('combo1');
							Ext.getCmp('egp01').getTopToolbar().doLayout();
							store.proxy = new Ext.data.HttpProxy({url:'standard/findStandard.action'});
							store.reload({params:{start:0,limit:pageSize,queryString:'[state,0,=],[type,1,=]'}});
							choose = 4;
						}
					}
				}]
        	}]],
        bbar:new Ext.ux.PagingToolbar({
				pageSize:pageSize,
				store:store,
				prependButtons:false,
				items:['-',
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
					},'-',{
						text:'删除',
						iconCls:'delete-icon',
						scope:this,
						handler:function() {
							var obj = Ext.getCmp('egp01');
							var standardIds = new Array(obj.getSelectionModel().getSelections().length);
							for(var i=0;i<standardIds.length;i++) {
								standardIds[i] = obj.getSelectionModel().getSelections()[i].get('id');
								if(obj.getSelectionModel().getSelections()[i].get('type') == 0) {
									Ext.Msg.alert('错误','库存记录不能删除');return;
								}
							}
							var submitFun = function(buttonId, text, opt) {
								if (buttonId == 'yes') {
									micrite.util.ajaxRequest({
                						url: 'standard/deleteStandard.action',
               							params:{'standardIds':standardIds},
                						success:function(r,o){
                    						obj.store.reload();
                						},
                						failure:Ext.emptyFn
            						},obj);
								}
							};
							Ext.Msg.show({
								title:'信息',
								msg:'确定要删除吗？',
								buttons:Ext.Msg.YESNO,
								scope:obj,
								fn:submitFun,
								icon:Ext.MessageBox.QUESTION
							});
						}
					},'-',{
						hidden:true,
						text:'提交修改',
						iconCls:'save-icon',
						handler:function() {
							var grid = Ext.getCmp('egp01');
							var store = grid.getStore();
							if(store.getModifiedRecords().length!=1){
								Ext.Msg.alert('温馨提示','请您每次只修改一条数据^-^');
								return;
							}
							var standard = store.getModifiedRecords()[0];
							var updateFun = function(buttonId, text, opt) {
								if (buttonId == 'yes') {
									micrite.util.ajaxRequest({
									url:'standard/editStandard.action',
									
									params:{'standard.id':standard.get('id'),
										'standard.enterprise.id':standard.get('waijian1'),
										'standard.numberStart':standard.get('numberStart'),
										'standard.numberEnd':standard.get('numberEnd'),
//										'standard.createDate':standard.get('createDate'),
//										'standard.takeDate':standard.get('takeDate'),
										'standard.numberEnd':standard.get('numberEnd'),
										'standard.sum':standard.get('sum')
										},
									success:function(r,o){
                        				grid.getStore().commitChanges();
                        				grid.store.reload();
                    				},
                   					 failure:Ext.emptyFn
									},grid);
								}
							};
							Ext.Msg.show({
								title:'信息',
								msg: '确定要修改吗？',
								buttons: Ext.Msg.YESNO,
								scope: grid,
								fn: updateFun,
								icon: Ext.MessageBox.QUESTION
							});        
						}
								
					},'提示 （<font color="red">红色：可用库存</font>','-','<font color="grey">灰色：用完的库存</font>）'
				],
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
       	cm:cm,
        store:store,
        sm:sm
    });
	mainPanel.getActiveTab().add(win);
	mainPanel.getActiveTab().doLayout();
	Ext.getCmp('egp01').getTopToolbar().add(condition1);
	Ext.getCmp('egp01').getTopToolbar().add(operate);
});
</script>

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
	Ext.ux.GridViewHeGeZheng=Ext.extend(   
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
	
	//检测记录表格行颜色
	Ext.ux.GridViewJiance=Ext.extend(   
    Ext.grid.GridView,   
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
		fields:[{name:'id'},
		    {name:'enterprise',mapping:'enterprise.unitName'},
			'numberStart',
			{name:'createDate'},'numberEnd','pay','remain','sum',{
			name:'takeDate'},'type',{
			name:'waijian1',mapping:'enterprise.id'}],
		totalProperty:'totalCount'
	});	
	//主要的store，显示在mainPanel中
	var store = new Ext.data.JsonStore({
		id:'myStore',
		sortInfo : {   
        field : 'createDate',   
        direction : 'desc'  
   		},
   		pruneModifiedRecords:true,//与修改行数据有关，现在没有用
   		url:'history/findStandardSingle.action',
		root:'data',
		fields:[
		        {name:'id'},
			    {name:'enterprise',mapping:'enterprise.unitName'},
				'numberStart',{name:'createDate'},'numberEnd','pay','remain','sum',
				{name:'takeDate'},
				'type',
				{name:'waijian1',mapping:'enterprise.id'}
//			{name:'waijian1'}
			],
		totalProperty:'totalCount'
	});
//	store.load({params:{start:0,limit:pageSize,queryString:''}});
	//企业搜索组件
	var condition1 = [{id:'separator1',xtype:'tbseparator'},{id:'btn1'},{id:'text1',xtype:'tbtext',text:'单位名称'},
					{id:'spacer1',xtype: 'tbspacer'},{id:'spacer2',xtype: 'tbspacer'},
						{	
							id:'combo1',
							xtype:'combo',
							triggerAction:'all',
							loadingText:'加载中……',
							minChars:100,//默认为4
							name:'unitName',
							height:100,
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
								if(searchValue==''&&false) {
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
	var operate =[];
//	[{id:'fill',xtype:'tbfill'},{
//					id:'btn_add',
//					text:'操作菜单',
//					menu:[{
//						text:'增加合格证库存',
//						iconCls:'add-icon',
//						handler:function(){
//							addWin2.show();
//						}
//					}]
//					}];	
	// ------合格证明细数据------------------------
	var RecordDef = Ext.data.Record.create([{name: 'id'},{name: 'name'}]);
	var RecordDef2 = Ext.data.Record.create([{name: 'value'},{name: 'name'}]);	
	myRenderer = function(value){
	var idx = this.editor.getStore().find(this.editor.valueField, value);
    var rec = this.editor.getStore().getAt(idx);
    if (rec == null) {
        return value;
    }else {
        return rec.get(this.editor.displayField);
    }
	};
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
	
	var postSMCheck = new Ext.grid.CheckboxSelectionModel();
	var postCM = new Ext.grid.ColumnModel({
		columns:[	
        	postSMCheck,
			new Ext.grid.RowNumberer(),
    			{
    				header: '合格证号',
    				width: 100, sortable: true, dataIndex: 'heGe'
    			},
      			{									
    				header: '核销日期',
    				width: 100, sortable: true, dataIndex: 'jianTime',
    				renderer : Ext.util.Format.dateRenderer('Y-m-d')
    			}, 			
    			{
    				header: '车牌号码',
    				width: 100, sortable: true, dataIndex: 'paiHao'
    			},	           		            
    			{
    				header: '车牌颜色',
    				width: 100, sortable: true, dataIndex: 'paiSe',
    				editor:comboPaiSe,renderer:myRenderer				   
    			},
    			{
    				header: '操作员',
    				width: 100, sortable: true, dataIndex: 'createrId'
    					,	
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
				store:store,
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
	
	//sm要用在columns和grid两处地方
	var sm = new Ext.grid.CheckboxSelectionModel();	
	var cm = new Ext.grid.ColumnModel({
		columns:[
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
        	header:'领取日期',dataIndex:'takeDate',
        	renderer:Ext.util.Format.dateRenderer('Y-m-d')
        },
        sm
		]
	});		    	

	var choose = 1;
	var win = new Ext.grid.GridPanel({
		id:'egp01',
        border:false,
        stripeRows:true,
        view:new Ext.ux.GridViewHeGeZheng({forceFit:true}),
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
							Ext.getCmp('egp01').getTopToolbar().doLayout();
//							store.proxy = new Ext.data.HttpProxy({url:'standard/findStandard.action'});
//							store.reload({params:{start:0,limit:pageSize,queryString:'[state,0,=],[type,0,=]'}});
							store.proxy = new Ext.data.HttpProxy({url:'history/findStandardSingle.action'});
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
//							store.proxy = new Ext.data.HttpProxy({url:'standard/findStandard.action'});
//							store.reload({params:{start:0,limit:pageSize,queryString:'[state,0,=],[type,1,=]'}});
							store.proxy = new Ext.data.HttpProxy({url:'history/findStandardSingle'});
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
				items:['提示 （<font color="red">红色：可用库存</font>','-','<font color="grey">灰色：用完的库存</font>）'],
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
	win.addListener('rowdblclick', showAddCheckFn);   
	function showAddCheckFn(){
	      if(sm.getCount()==1){ 
		 	var record = win.getSelectionModel().getSelected();	
		 	EnterpriseWin.show();        	 			
		 	var waijian1 = record.get('waijian1');
		 	winEnterpriseStore.load({params:{start:0,limit:pageSize,queryString:'[state,0,=],[waijian1,'+waijian1+',=]'}}); 		 	
	      }else{Ext.MessageBox.alert('提示', '请选择一条记录！');}		
	};	
	
	//双击主界面弹出具体记录	
	var smEnterpriseWin = new Ext.grid.CheckboxSelectionModel();
	var cmEnterpriseWin = new Ext.grid.ColumnModel({
	columns:[
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
    	header:'领取日期',dataIndex:'takeDate',
    	renderer:Ext.util.Format.dateRenderer('Y-m-d')
    },
    smEnterpriseWin
	]
});
	var EnterprisePanel = new Ext.grid.GridPanel({
		id:'EnterprisePanel',
        border:false,
        stripeRows:true,
        view:new Ext.ux.GridViewHeGeZheng({forceFit:true}),
        selModel:new Ext.grid.RowSelectionModel(),
        tbar:[[]],
        bbar:new Ext.ux.PagingToolbar({
				pageSize:pageSize,
				store:store,
				prependButtons:false,
				items:['提示 （<font color="red">红色：可用库存</font>','-','<font color="grey">灰色：用完的库存</font>）'],
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
       	cm:cmEnterpriseWin,
        store:winEnterpriseStore,
        sm:smEnterpriseWin
    });	
	var EnterpriseWin = new Ext.Window({  
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
		id:'heGeDetailWindowID',
		baseCls:'x-plain',  
        items: [{
		xtype : 'tabpanel',
		plain : true,
		bodyBorder : false,
		activeTab : 0,
		height : 300,
		width : 730,
		defaults : {bodyStyle : 'padding:2px'},
		items : [ 
			{title : '企业合格证记录',layout : 'fit',items :[EnterprisePanel]}
			]
    	}],	
            buttonAlign:'center',
            buttons: [{
            text: '关闭',
            scope:this,
            handler: function(){
            	EnterpriseWin.hide();
            }
        }]
    }]}); 
	EnterprisePanel.addListener('rowdblclick', showEnterpriseWinFn);   
	function showEnterpriseWinFn(){
	      if(smEnterpriseWin.getCount()==1){ 
	    	var theForm = Ext.getCmp("heGeDetailWindowID").getForm(); 
		 	var record = EnterprisePanel.getSelectionModel().getSelected();	
		 	heGeDetailWindow.show();        	 			
		 	theForm.reset();	
		 	var numberStart = record.get('numberStart');
		 	var numberEnd = record.get('numberEnd');
			 postcheckstore.load({params:{start:0,limit:pageSize,queryString:'[isPost,1,=],[testKind,2,=],[heGe,'+numberStart+',>=],[heGe,'+numberEnd+',<=]'}}); 		 	
	      }else{Ext.MessageBox.alert('提示', '请选择一条记录！');}		
	};
	
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
	
	mainPanel.getActiveTab().add(win);
	mainPanel.getActiveTab().doLayout();
	Ext.getCmp('egp01').getTopToolbar().add(condition1);
	Ext.getCmp('egp01').getTopToolbar().add(operate);
});
</script>
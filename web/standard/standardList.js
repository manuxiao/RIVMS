<script type="text/javascript">
Ext.namespace('micrite.car.standardList');
micrite.car.standardList.SearchPanel = Ext.extend(micrite.ComplexGrid, {	
	initComponent:function() {

	var RecordStandard = Ext.data.Record.create([
		{name:'id'},
		{name:'enterprise.unitName'},
		'numberStart',
		{name:'createDate'},
		'numberEnd','pay','remain','sum',
		'type',
		'state',
		{name:'enterpriseId'}]);	
	
	var RecordStat = Ext.data.Record.create([
		{name:'id'},
	    {name:'enterprise.unitName'},
		'numberStart',
		{name:'createDate'},
		{name:'numberEnd'},
		{name:'pay'},
		'remain',
		'sum',
		'type',
		'state',
		{name:'enterpriseId'}]);
	var pageSize = parseInt(Ext.getDom('pageSize').value,10);
	//修改分页工具条的刷新传递的参数，加入state=0查询
	Ext.ux.PagingToolbar=Ext.extend(
	Ext.PagingToolbar,{
		pageSize:pageSize,
		doLoad:function(start){
        	var o = {}, pn = this.getParams();
        	o[pn.start] = start;
        	o[pn.limit] = this.pageSize;
//        	if(this.fireEvent('beforechange', this, o) !== false){
//            this.store.load({params:{start:start,limit:this.pageSize,queryString:'[state,0,=]'}});
//        	}
    	}
	});
	Ext.ux.GridViewHeGeZheng=Ext.extend(Ext.grid.GridView,{   
        getRowClass:function(record,index){      
            if(record.get('type') == 0) {
            	if(record.get('remain') == 0)
            		return 'gray-row';//自定义css
            	return 'red-row';	
            }else{  
                return 'white-row';
            }
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
//	var storeCarColumn = new Ext.data.Store({
//		id: Ext.id(),    
//		autoLoad:false,
//		proxy : new Ext.data.HttpProxy({url: 'dic/getPartner.action?type=15'}),    
//		reader : JsonReaderValue,
//		remoteSort: true   
//	});	 
//	var comboCarColumn = new Ext.form.ComboBox({
//		valueField: "value",
//		displayField: "name",
//		store: storeCarColumn,
//		triggerAction :'all',
//		lazyRender:false 
//	});	
	
	var comboCreater = new Ext.form.ComboBox({
		valueField: "id",
		displayField: "name",
		store: storeCreaters,
		triggerAction :'all',
		lazyRender:false 
	});

var checkStore = new Ext.data.JsonStore({
		sortInfo : {field : 'jianTime',direction : 'desc'},
   		url:'check/findCheck.action',
		root:'data',
		fields:[
				{name: 'id'},
	            {name: 'jianTime'},
	            {name: 'paiHao'},
	            {name: 'heGe'},
	            {name: 'printed'},
            	{name: 'carId'},
            	{name: 'isPost'},
            	{name: 'status'},
	            {name: 'paiSe.id'},
	            {name: 'paiSe.name'},
	            {name: 'createrId'},
	            {name: 'notepad'}				
			],
		totalProperty:'totalCount'
	});	

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
				width: 100, sortable: true, dataIndex: 'paiSe.name'
			},
			{
				header: '操作员',
				width: 100, sortable: true, dataIndex: 'createrId',	
				editor:comboCreater,renderer:myRenderer
			},
			{
				header: '核销备注',
				width: 180, sortable: true, dataIndex: 'notepad'			   
			}
		]
	});
	
var origCheckGridPanel = new Ext.grid.GridPanel({
    border:false,
    stripeRows:true,
    selModel:new Ext.grid.RowSelectionModel(),
	height:300,
    view:new Ext.ux.GridViewJiance({forceFit:true}),	        
    bbar:new Ext.ux.PagingToolbar({
		pageSize:pageSize,
		store:checkStore,
		prependButtons:false,
		items:['提示 （<font color="red">红色：有备注</font>','-','<font color="gray">灰色：无备注</font>）'],
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
    store:checkStore,
    sm:postSMCheck
});	
var heGeDetailWindow = new Ext.Window({  
	   	modal:true,
	   	title:'已核销的合格证',
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
         	 layout : 'form',        				
             collapsible: true,
             defaults: {},    // Default config options for child items
             height: 80, 
             labelWidth: 60,		                          
         	 items: [{
	         	xtype:'textarea',
	         	name: 'notepad',
	         	id:'Notepad',
	         	width: 620,
	         	height: 40,
	         	emptyText:'查看备注请点击下列记录',
             	fieldLabel: '核销备注'
         	 }]
     	 },origCheckGridPanel
     ],	
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
      }else{GOnlyOneSelectedFn(postSMCheck.getCount());}
};	
enterpriseListCombo=Ext.extend(Ext.form.ComboBox,{	
//		xtype:'combo',
		anchor:'90%',
		fieldLabel:'企业名称',
		editable:true,
		triggerAction:'all',
		loadingText:'加载中……',
		emptyText:'请选择',
//		id:'enterpriseId',
//		name:'standard.enterpriseId',
//		hiddenName:'standard.enterpriseId',
		allowBlank:false,
		forceSelection:true,
		minChars:2,
		queryParam:'py',
		allQuery:this.value,
		store: storeEnterprises,
		valueField:'id',
		displayField:'name',
		mode:'remote',
		listeners:{
//			'keyup':function(com2,e) {	
//				if(e.getKey() == Ext.EventObject.DOWN||e.getKey() == Ext.EventObject.UP) {
//
//				}else{
//					var key = com2.getRawValue();
//					if(key != ''){
//						com2.store.setBaseParam('queryString','[py,'+key+',like]');
//					}else{
//						com2.store.setBaseParam('queryString','');
//					}
//					com2.store.load({params:{start:0,limit:20,queryString:'[py,'+com2.getRawValue()+',like]'}});
//				}
//				e.stopEvent();
//			}
//			,
//			select:function(combo,record,index){
//				
//			}
		}
	});
	var emptyListCombo={	
		xtype:'combo',
		anchor:'90%',
		fieldLabel:'可领用合格证',
		editable:false,
		triggerAction:'all',
		loadingText:'加载中……',
		emptyText:'请选择',
		minChars:100,
		allowBlank:false,
		forceSelection:true,
		store:new Ext.data.JsonStore({
			baseParams:{queryString:'[type,2,=]'},
			url:'standard/findStandardByQuery.action',
			root:'data',
			totalProperty:'totalCount',
			fields:['id','numberStart','numberEnd','sum']
		}),
		valueField:'id',
		displayField:'sum',
		mode:'remote',
//		autoLoad:false,
		listeners:{
			'select':function(comb,record,index) {	
				Ext.getCmp('numberEnd').setValue(record.get('numberEnd'));
				Ext.getCmp('numberStart').setValue(record.get('numberStart'));
				Ext.getCmp('refof').setValue(record.get('id'));
//				alert('recordid='+record.get('id')+',='+Ext.getCmp('refof').getValue());
			},
			'expand':function(){
				//emptyListCombo.store.reload({params:{start:0,limit:pageSize,queryString:'[state,0,=],[type,2,=]'}});
			}
		}
	};
var editStandarWin = new Ext.Window({ 
		modal: true,
	   	title: '合格证记录修改',
	   	closeAction: 'hide',
		padding: '5px',
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
            	id:'id',
                name: 'standard.id',
                allowBlank:false
            }, 
            {
            	xtype:'hidden',
            	id:'type',
                name: 'standard.type',
                allowBlank:false
            },new enterpriseListCombo({hiddenName:'standard.enterpriseId'}),//id:'enterpriseId',
            {
                fieldLabel: '起始号码',
                name: 'standard.numberStart',
                allowBlank:false
            },{
                fieldLabel: '结束号码',
                name: 'standard.numberEnd',
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
							micrite.security.framework.refleshActiveTabGridPanel();
                        	editStandarWin.hide();
//                            this.hide();                           
//                		 	var record = sm.getSelected();		    				 		
//                            entStore.load({params:{start:0,limit:pageSize,queryString:'[enterpriseId,'+record.get('enterpriseId')+',=],[state,0,=]'}});
                        },
                        failure: function(form, action) {
                            obj = Ext.util.JSON.decode(action.response.responseText);
                            var message=(obj.message=='undefined')?obj.actionErrors:obj.message;
                            showMsg('failure', message); 
                        }
                    });              
            	}
            },    
            {
            text: '关闭',
            scope:this,
            handler: function(){
            	editStandarWin.hide();
            }
        }]
    }]}); 
    var entSM = new Ext.grid.CheckboxSelectionModel();
	var entCM = new Ext.grid.ColumnModel({
		columns:[
		new Ext.grid.RowNumberer(),
	    {
	    	header:'企业名称',dataIndex:'enterprise.unitName'
	    },{
	    	header:'起始号码',dataIndex:'numberStart'
	    },{
	    	header:'结束号码',dataIndex:'numberEnd'
	    },{
	    	header:'合格证总数',dataIndex:'sum'
	    },{
	    	header:'使用数量',dataIndex:'pay'
	    },{
	    	header:'剩余数量',dataIndex:'remain'
	    },{
	    	header:'领取日期',dataIndex:'createDate'
	    },
	    entSM
		]
	});
	function getStatistics(cmp){
		Ext.Ajax.request({
			url:'standard/remainStatistics.action?id='+cmp.getValue(),
			method:'POST',
			success:function(response) {
				obj = Ext.util.JSON.decode(response.responseText);
				if(obj.success){
					Ext.getCmp('statisticsOfEntStandard').setValue(obj.stat);
				}
			}
		});
		
	}
    var dipatchStandarWin = new Ext.Window({
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
//            title:'合格证信息',
            layout:'form',
            collapsible: true,
            height: 200,
            items:[
            	{
            	xtype:'panel',
            	baseCls:'x-plain',
//            	height:200,
            	layout:'column',
            	items:[{
            		baseCls:'x-plain',
            		columnWidth:.618,
//            		height:200,
            		layout:'form',
//            		labelWidth:65,
            		labelAlign:'right',
//            		labelPad:10,
            		defaultType: 'numberfield',
            		items:[
            		{
            			id:'statisticsOfEntStandard',
            			xtype:'textfield',
            			fieldLabel:'使用情况',
//            			text:'vv',
            			height:20,
            			tooltip:'领取次数,剩余合格证'
//            			html:'<b>kk</b>'
            		},{
            			xtype:'hidden',
            			name:'standard.type',
            			value:1
            		},{
            			xtype:'hidden',
            			id:'refof',
            			name:'standard.father.id'
            		},
            		new enterpriseListCombo({hiddenName:'standard.enterpriseId',listeners:{select:getStatistics}}),
            		emptyListCombo,
            		{
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
            					if(this.value<Ext.getCmp('numberStart').getValue()){
            						Ext.Msg.alert('错误','结束号码不能小于起始号码！');
            						this.setValue(dipatchStandarWin.end);
            					}
            				}
            			}
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
				Ext.getCmp('standardInfo-form').getForm().submit({
					url: 'standard/saveStandard.action',
					method: 'POST',
					disabled:true,
					waitTitle:'请稍候',
					waitMsg:'请等待',
					success: function(form, action) {
                        obj = Ext.util.JSON.decode(action.response.responseText);
                        showMsg('success', obj.message);
                        micrite.security.framework.refleshActiveTabGridPanel();
                        dipatchStandarWin.hide();
                    },
                    failure: function(form, action) {
                    	obj = Ext.util.JSON.decode(action.response.responseText);
                        var message=(obj.message=='undefined')?obj.actionErrors:obj.message;
                        showMsg('failure', message); 
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
					dipatchStandarWin.hide();
				}
			}]
		}]
		}],
		width:450,
        height:280
	});
    var addStandardWin = new Ext.Window({
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
//            			id:'numberStart2',
            			fieldLabel:'起始号码',
            			allowBlank:false,
            			anchor:'90%',
            			name:'standard.numberStart'
//            			listeners:{
//            				'blur':function() {
////            					if(this.value<=Ext.getCmp('numberStart2').getValue()){
//            					if(this.value<=Ext.getCmp('numberStart2').getValue()){
//            						Ext.Msg.alert('无法入库','库存已存在此合格证号！');
//            						this.setValue('');
//            					}
//            				}
//            			}
            		},{
//            			id:'numberEnd2',
            			fieldLabel:'结束号码',
            			allowBlank:false,
            			anchor:'90%',
            			name:'standard.numberEnd'
//            				,
//            			listeners:{
//            				'blur':function() {
//            					if(this.value<=Ext.getCmp('numberStart2').getValue()){
//            						Ext.Msg.alert('无法入库','库存已存在此合格证号！');
//            						this.setValue('');
//            					}
//            				}
//            			}
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
//			scope:this,
			buttons:[{
				text:'提交',
				handler:function() {
					
					var theForm = Ext.getCmp('standardInfo-form2').getForm();
//					var xx=this;
					if(!theForm.isValid())return;
					theForm.submit({
						url: 'standard/saveStandard.action',
						method: 'POST',
						disabled:true,
						waitTitle:'请稍候',
						waitMsg:'请等待',
//						scope:xx,
						success: function(form, action) {
							
//	                        obj = Ext.util.JSON.decode(action.response.responseText);
	                        showMsg('success', action.result.message); 
	                        addStandardWin.hide();
	                        micrite.security.framework.refleshActiveTabGridPanel();
	                        var this_=Ext.getCmp('jjj');
	                        this_.startSearch();
//							this.startSearch();
	//                        store.reload({params:{start:0,limit:pageSize,queryString:'[state,0,=],[type,0,=]'}});
	//                        this.getStore().reload({params:{start:0,limit:pageSize,queryString:'[state,0,=],[type,0,=]'}});
	                        
	                    },
	                    failure: function(form, action) {
	                        showMsg('failure', action.result.message);
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
					addStandardWin.hide();
				}
			}]
		}]
		}],
		width:450,
        height:250
	});  
 
    var entStore = new Ext.data.JsonStore({
//		sortInfo : {   
//        field : 'createDate',   
//        direction : 'desc'  
//   		},
   		pruneModifiedRecords:true,//与修改行数据有关，现在没有用
   		url:'standard/findStandardByQuery.action',
		root:'data',
		fields:['id','enterprise.unitName','createDate',
		'numberStart','numberEnd','pay','remain','sum',		
		'type','state','enterpriseId'],
		totalProperty:'totalCount'
	});	
	var enterprisePanel = new Ext.grid.GridPanel({
	    border:false,
	    stripeRows:true,
	    selModel:new Ext.grid.RowSelectionModel(),	    
//	    view:new Ext.ux.GridViewHeGeZheng({forceFit:true}),	        
	    bbar:new Ext.ux.PagingToolbar({
			pageSize:pageSize,
			store:entStore,
			prependButtons:false,
//			items:['提示 （<font color="red">红色：可用库存</font>','-','<font color="grey">灰色：用完的库存</font>）'],
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
	   	cm:entCM,
	    store:entStore,
	    sm:entSM
	});
	var enterpriseWin = new Ext.Window({  
	   	modal:true,
	   	title:'合格证领取记录',
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
				items : [{title : '合格证领取记录',layout : 'fit',items :[enterprisePanel]}]
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
    var sm = new Ext.grid.CheckboxSelectionModel();  
	this.addListener('rowdblclick', showCheckFn);   
	function showCheckFn(){		
		if(sm.getCount()==1){
			var record = sm.getSelected();
			if(record==null)return;
			var this_=Ext.getCmp('jjj');
			if(this_.searchTeamMenuButton.value==0){
				var theForm = Ext.getCmp("heGeDetailWindowID").getForm(); 
			 	var record = sm.getSelected();	
			 	heGeDetailWindow.show();        	 			
			 	theForm.reset();	
			 	var numberStart = record.get('numberStart');
			 	var numberEnd = record.get('numberEnd');
				checkStore.load({params:{start:0,limit:pageSize,queryString:'[isPost,1,=],[testKind,2,=],[heGe,'+numberStart+',>=],[heGe,'+numberEnd+',<=]'}});
			}else if(this_.searchTeamMenuButton.value==1){	    		
			 	enterpriseWin.show();
//				for(var i in record.data)alert(i+'='+record.data[i]);				
				entStore.load({params:{start:0,limit:pageSize,queryString:'[state,0,=],[type,2,<>],[enterpriseId,'+record.get('enterpriseId')+',=]'}});
			}
		}else{GOnlyOneSelectedFn(entSM.getCount());}
	};
	enterprisePanel.addListener('rowdblclick', showHEGE);   
	function showHEGE(){		
		if(entSM.getCount()==1){
			var record = entSM.getSelected();
			if(record==null)return;
			 	heGeDetailWindow.show();        	 			
			 	var numberStart = record.get('numberStart');
			 	var numberEnd = record.get('numberEnd');
				checkStore.load({params:{start:0,limit:pageSize,queryString:'[isPost,1,=],[testKind,2,=],[heGe,'+numberStart+',>=],[heGe,'+numberEnd+',<=]'}});
		}else{GOnlyOneSelectedFn(entSM.getCount());}
	};
	var entAutocompleteCombo={	xtype:'combo',
			anchor:'90%',
			width:160,
			fieldLabel:'企业名称',
			editable:true,
			loadingText:'加载中……',
			emptyText:'全部',
			name:'enterpriseId',
			hiddenName:'enterpriseId',
			allowBlank:true,
			selectOnFocus:true,
			forceSelection:true,
			triggerAction:'all',
			queryParam:'py',
			allQuery:this.value,
			minChars:2,
			store: storeEnterprises,
			valueField:'id',
			displayField:'name',
			mode:'remote'
		};
	var searchField1 = [
	   {advSearch:true},
	   {	
			name:'type',
			xtype:'hidden',
			expression:'<>',
			value:2
		},
		'起',
		{	
			name:'createDate',
			xtype:'datefield',
			format:'Y-m-d',
			expression:'>=',
			value:preDate()
		},
		'止',
		{
			name:'createDate',
			xtype:'datefield',
			format:'Y-m-d',
			expression:'<=',
			value:new Date()
		},
		'企业名称',
		entAutocompleteCombo
	];
var searchField2=[{advSearch:true},'企业名称',entAutocompleteCombo];
	//双击主界面弹出的具体记录界面---现在是第一主界面	
	var csmStandard = new Ext.grid.CheckboxSelectionModel();
	var columnsStandard = [
		{
			header:'编号',dataIndex:'id',sortable:true,hidden:true
	    },{
	    	header:'企业名称',dataIndex:'enterprise.unitName'
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
	    	header:'发生日期',dataIndex:'createDate'
	    },
	    csmStandard
	];
	//sm要用在columns和grid两处地方
//	var csmStat = new Ext.grid.CheckboxSelectionModel();
	var columnsStat = [
		{
        	header:'企业名称',dataIndex:'enterprise.unitName'
        },{
        	header:'领取次数',dataIndex:'pay'
        },{
        	header:'合格证总数',dataIndex:'sum'
        },{
        	header:'剩余数量',dataIndex:'remain'
        }
//        ,{
//        	header:'领取日期',dataIndex:'createDate'
//        }
        ,sm
//        ,csmStat
	];
	var config = {
		view:new Ext.ux.GridViewHeGeZheng({forceFit:true}),
			advSearchField : [[]],	  
	        compSet: [
	             {url:0,reader:0,columns:0,bbarAction:0},
	             {url:1,reader:1,columns:1,bbarAction:1,tbarAction:0}
	        ],
			searchMenu : [
				 '合格证领发情况',
				 '统计和使用情况'
			],
			searchFields :[
			     searchField1,searchField2
	        ],
	        urls: ['standard/findStandardByQuery.action','history/findStandardSingle.action'],
	        readers : [RecordStandard,RecordStat],  
			columnsArray: [columnsStandard,columnsStat],			
	         bbarActions:[[         	
	         {
					text:'发放',
					iconCls:'out-icon',
					listeners:{
						click:function(){
							dipatchStandarWin.show();
							Ext.getCmp('standardInfo-form').getForm().reset();		
							emptyListCombo.store.reload({params:{start:0,limit:pageSize,queryString:'[state,0,=],[type,2,=]'}});
						}
					}
				},{
					text:'入库',
					iconCls:'in-icon',
					listeners:{
						click:function(){
							addStandardWin.show();
						}
					}
				},{
					text:'修改',
					iconCls:'edit-icon',
					handler:function() {
						if(sm.getCount()==1){ 
//							Ext.ComponentMgr.unregister(Ext.getCmp('enterpriseId'));
//							alert(Ext.getCmp('enterpriseId_')+','+Ext.getCmp('enterpriseId'));
							var record = sm.getSelected();
//							if(record.get('type')==0){//修改入库
								var theForm = Ext.getCmp("editEnterpriseWinID").getForm(); 
								theForm.reset();
//								theForm.loadRecord(record);
								theForm.findField('standard.numberStart').setValue(record.get('numberStart'));
								theForm.findField('standard.numberEnd').setValue(record.get('numberEnd'));
//								theForm.findField('sentId').setValue(record.get('enterprise.unitName'));
//								var v = theForm.findField('standard.enterpriseId');
//								v.setValue(record.get('enterpriseId'));
//								v.lastSelectText=record.get('enterprise.unitName')
//								v.superclass.setValue.call(v, record.get('enterprise.unitName'));
//								Ext.form.TextField.setValue.call(v, record.get('enterprise.unitName'));
								theForm.findField('standard.enterpriseId').store.reload({params:{},
						    		callback:function(r,options,sucess){
							    		if(sucess){
											theForm.findField('standard.enterpriseId').setValue(record.get('enterpriseId'));
							    		}
								    }
								});
								
								theForm.findField('standard.id').setValue(record.get('id'));
								theForm.findField('standard.type').setValue(record.get('type'));
								editStandarWin.show();
//							}
														 	
						}else{GOnlyOneSelectedFn(sm.getCount());}       
					}							
				},{
					text:'明细',
					iconCls:'info-icon',
					handler:showCheckFn							
				},'-',
				'提示 （<span class="red-row">红色：可用库存</span>','-','<span class="gray-row">灰色：用完的库存</span>）' 
	         ],[{
					text:'明细',
					iconCls:'info-icon',
					handler:showCheckFn							
				}]],
	         sm:sm,
		     listeners: {
//				render: xxFn
	         }
	    }; // eo config object 
		// apply config
		Ext.apply(this, Ext.apply(this.initialConfig, config)); 
		micrite.car.standardList.SearchPanel.superclass.initComponent.apply(this, arguments);
    } // eo function initComponent	
}); //eo extend

// 处理多语言
try {carfileListLocale();} catch (e) {}		
Ext.onReady(function() {
	Ext.QuickTips.init();	
	this.formPanel = new micrite.car.standardList.SearchPanel({id:'jjj'});	
	if (mainPanel) {
		mainPanel.getActiveTab().add(formPanel);
		mainPanel.getActiveTab().doLayout();
	} else {
		var vp = new Ext.Viewport({
			layout:'fit',
			items:[formPanel]
		});
	}
	//var this_=Ext.getCmp('jjj');
    //this_.startSearch();
	//formPanel.getStore().load({params:{start:0,limit:formPanel.pageSize,queryString:''}})//打开自动显示数据 
});
</script>
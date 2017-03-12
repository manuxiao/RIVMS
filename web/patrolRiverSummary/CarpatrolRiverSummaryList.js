
<script type="text/javascript">
Ext.namespace('micrite.patrolRiverSummary.patrolRiverSummaryList');
micrite.patrolRiverSummary.patrolRiverSummaryList.SearchPanel = Ext.extend(micrite.ComplexGrid, {	
	initComponent:function() {	
	//车辆类型下拉框
	var pageSize = parseInt(Ext.getDom('pageSize').value,10);
	
	var comboPaiSe = new Ext.form.ComboBox({
		valueField: "id",
		displayField: "name",
		store: storePaiSe,
		triggerAction :'all',
		lazyRender:false 
	});	

	var smcheck = new Ext.grid.CheckboxSelectionModel();
	
    var carPunishGridPanel = new Ext.grid.GridPanel({
        border:false,
        stripeRows:true,
        view:new Ext.grid.GridView({forceFit:true}),
        selModel:new Ext.grid.RowSelectionModel(),
       	cm:historyCM,
        store:historyStore
    });
    function checkTabPanelActivateFn(){
		    checkStore.load({params:{start:0,limit:pageSize,queryString:'[isPost,1,=],[carId,'+window.carId+',=],[testKind,2,=]'}});
    }
    function checkEvalTabPanelActivateFn(){
		    checkStore.load({params:{start:0,limit:pageSize,queryString:'[isPost,1,=],[carId,'+window.carId+',=],[testKind,1,=]'}});
    }
    function modifyTabPanelActivateFn(){
		    historyStore.load({params:{start:0,limit:pageSize,queryString:'[isPost,2,=],[status,'+window.carId+',=],[testKind,100,<]'}});
    }
    function punishTabPanelActivateFn(){
		    historyStore.load({params:{start:0,limit:pageSize,queryString:'[isPost,2,=],[status,'+window.carId+',=],[testKind,100,>]'}});
    }
    function getCarStatusStr(cmp){
    	storeCarStatus.findBy(function(record,id){
    		if(record.get('value')==cmp.getValue()){
    			cmp.setValue(record.get('name'));
    		}
    	});
    }
	var carInfoWindow = new Ext.Window({  
	   	modal:true,
        title:'查看车辆档案',
		closeAction:'hide',
		padding:'5px',
		width : 850,
		autoHeight : true,
		items : [{
		xtype:'form',
		labelAlign : 'right',
		labelWidth : 100,
		bodyStyle : 'padding:1px',
		id:'carfileList-form',
		baseCls:'x-plain',      
        items: [{ 
           		xtype: 'fieldset',
           		padding:'1px',
           		title:'车辆档案信息',       		
            	layout : 'column',        				
                collapsible: true,
                defaults: {width: 320},    // Default config options for child items
                height:300,
        items: [
        {
            labelWidth: 120,
            layout:'form',
            collapsible: true,
            defaults: {width: 230},    // Default config options for child items
            defaultType: 'textfield',
//        	height:230,   
            columnWidth : .5,
			baseCls : 'x-plain',
			
            items: [{
                fieldLabel: this.colModelOwnerLicense,
                itemId: 'ownerLicense',
                readOnly : true
            },{
                fieldLabel: this.colModelOwner,
                itemId: 'owner',
                readOnly : true
            },{
                fieldLabel: this.colModelTelephone,
                itemId: 'telephone',
                readOnly : true
            },{
                fieldLabel: this.colModelMobile,
                itemId: 'mobile',
                readOnly : true
            },{
                fieldLabel: this.colModelAddress,
                itemId: 'address',
                readOnly : true
            }, {
                fieldLabel: this.colModelLicenseNumber,
                itemId: 'licenseNumber',
                readOnly : true
            },{
				fieldLabel: this.colModelPaiSe,
				itemId: 'paiSe.name',
				readOnly : true
			},
			{
				fieldLabel: this.colModelCarType,
				itemId: 'licenseType.name',
				readOnly : true
			},
            {
                fieldLabel: this.colModelLoadTon,
                itemId: 'loadTon',
                readOnly : true
            }, {
                fieldLabel: this.colModelBrandType,
                itemId: 'brandType',
                readOnly : true
            }
            ]
        },
        {
            labelWidth: 130,
            layout:'form',
            collapsible: true,
            defaults: {width: 210},    // Default config options for child items
            defaultType: 'textfield',
//            height:230,  
        	columnWidth : .5,
			baseCls : 'x-plain',
		items : [
            {
				fieldLabel: this.colModelYingyunNo,
				itemId: 'yingyunNo',
				readOnly : true
			},{
				fieldLabel: this.colModelSkillRank,
				itemId: 'skillRank.name',
				readOnly : true
			},{
				fieldLabel: this.colModelEvaluateDate,
				itemId: 'evaluateDate',
				readOnly : true
			},{
				fieldLabel: this.colModelEvaluateCycle,
				itemId: 'evaluateCycle.name',
				readOnly : true
			},{
				fieldLabel: this.colModelMaintainDate,
				itemId: 'maintainDate',
				readOnly : true
			},{
				fieldLabel: this.colModelMaintainCycle,
				itemId: 'maintainCycle.name',
				readOnly : true
			},{
				fieldLabel: this.colModelMaintainDateEnd,
				itemId: 'maintainDateEnd',
				readOnly : true
			},{
				fieldLabel: this.colModelCarType,
				itemId: 'carType.name',
				readOnly : true
			},{
				fieldLabel: this.colModelFuelRank,
				itemId: 'fuelRank.name',
				readOnly : true
			},{ //id:'carStatus',
				fieldLabel: this.colModelCarStatus,
				itemId: 'carStatus',
				readOnly : true
			},{itemId: 'id',hidden:true}
            ]
        }]},           
		{
		xtype : 'tabpanel',
		itemId:'infoTabPanel',
		plain : true,
		bodyBorder : false,
		activeTab : 0,
		height : 160,
		defaults : {bodyStyle : 'padding:2px'},
		items : [
		{title : '车辆备注',layout : 'fit',items : {xtype : 'textarea',name : 'carRemark',maxLength : 300,readOnly : true}},
		{title : '二级维护记录',layout : 'fit',items :[checkWin],listeners:{activate:checkTabPanelActivateFn}},
		{title : '等级评定记录',layout : 'fit',items :[checkEvalWin],listeners:{activate:checkEvalTabPanelActivateFn}},
		{title : '车辆修改记录',layout : 'fit',items :[carEditHistoryGridPanel],listeners:{activate:modifyTabPanelActivateFn}},
		{title : '车辆异动记录',layout : 'fit',items :[carPunishGridPanel],listeners:{activate:punishTabPanelActivateFn}}
			],
		listeners:{
			activate:function(a){
				alert(a);
			}
		}
    	}],	
            buttonAlign:'center',
            buttons: [
				{
				text:'修改',
				scope:this,
				handler:function() {
					//打开编辑界面					
					carInfoWindow.hide();
					var record = this.getSelectionModel().getSelected();
					window.carId=record.get('id');
					if(window.carId==null||window.carId==''){
						alert('车辆档案编号不明，无法编辑');
						return;
					}
		        	GAddCarfileFn();
				}
    	},{
            text: '关闭',
            scope:this,
            handler: function(){
            	carInfoWindow.hide();
            }
        }]
    }]}); 	
  //通过checkbox选择或反选时触发选区轮换的一个制定选区模型
	var sm = new Ext.grid.CheckboxSelectionModel();
	this.addListener('rowdblclick', rowdblclickFn2);
	this.addListener('rowclick', decideButtonEnableFn);
	function rowdblclickFn(){ 
        if(sm.getCount()==1){ 
    	 	var record = this.getSelectionModel().getSelected();
			window.carId = record.get('id');
			var theForm=Ext.getCmp("carfileList-form").getForm()
			theForm.loadRecord(record);
			getCarStatusStr(theForm.findField('carStatus'));
		 	carInfoWindow.show();
		 	try{
		 		Ext.getCmp("carfileList-form").getComponent('infoTabPanel').getActiveTab().fireEvent('activate');
		 	}catch(e){}
 		}else{ GOnlyOneSelectedFn(sm.getCount());}
    };
    function rowdblclickFn2(){
    	if(this.getSelectionModel().getCount()==1){ 
    	 	var record = this.getSelectionModel().getSelected();
    		window.carId=record.get('id');
    	 	GShowCarfileDetailFn();
    	}else{ 
    		GOnlyOneSelectedFn(sm.getCount());
    	}
	};
    var carfileListDltBtn='carfileListDltBtn',
    carfileListMdfBtn='carfileListMdfBtn',
    carfileListInfoBtn='carfileListInfoBtn'

	var config = {
		    view:new Ext.ux.GridViewCar({forceFit:false}),
			advSearchField : [[		
//		          {name:this.colModelLicenseNumber,value:'licenseNumber',xtype:'textfield'},
//		          {name:this.colModelYingyunNo,value:'yingyunNo',xtype:'textfield'}				          
		      ]],	  
	        compSet: [
	             {url:0,reader:0,columns:0,bbarAction:0,downloadable:1},
	             {url:1,reader:0,columns:0,bbarAction:0,tbarAction:0,downloadable:1},
	             {url:2,reader:0,columns:0,bbarAction:0,tbarAction:0,downloadable:1}
	        ],
			searchMenu : [
				 '车牌号码',
				 '技术等级',
				 '超期查询'
			],
			searchFields :[
			     [
				 {advSearch:true},
	             this.colModelLicenseNumber,
	             {xtype:'textfield',
				  name:'licenseNumber',
	              expression:'like',
	              value:'浙B.',
	              width:120}	
	            ],[
	             '车辆类型',
				 {xtype:'combo',
	               name:'carTypeId',
		           valueField:'id',
		           hiddenName:'carTypeId',
		           displayField:'name',
		           emptyText:'全部',
		           editable:true,
		           allowBlank:true,
		           forceSelection:true,
		           selectOnFocus:true,
		           triggerAction:'all',
		           store:storeCarType          
		           },
	            ],['车主姓名',
				{	
        			xtype:'combo',
        			anchor:'90%',
        			triggerAction:'all',
					loadingText:'加载中……',
					name:'carownerId',
					hiddenName:'carownerId',
					width:160,
					editable:true,
					minChars:2,
					emptyText:'全部',
					allowBlank:true,
					forceSelection:true,
					allQuery:this.value,
        			queryParam:'py',
        			selectOnFocus:false,
//					typeAhead: false,
					store:	storeCarowners,				           
					valueField:'id',
					displayField:'name',
					mode:'remote'
        		},
				 {id:'text11',xtype:'tbtext',text:'起始日期'},
					{	
						name:'startDate',
						xtype:'datefield',
						format:'Y-m-d',
//						minValue:'2000-01-01',
						value:new Date()
					},
					{id:'text12',xtype:'tbtext',text:'截止日期'},
					{
						name:'endDate',
						xtype:'datefield',
						format:'Y-m-d',
						value:nextDate()
					}
	            ]
	            ],
//	            car/findCarfileByDateSpacing.action   
	        urls: ['car/findCarfile.action','car/findCarfileBySkill.action','car/getWillExpired.action'],
	        readers : [GCarFields],  
			columnsArray: [GCarColumns],
	        tbarActions : [[	
	         {
	        	 text:this.carTypePieChart,
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
	        	 handler:this.deleteCarfile,
	        	 id:carfileListDltBtn,
	        	 disabled:true
	         },
	         {
	        	 text:'新增车辆档案', 
	        	 iconCls :'add-icon',
	        	 scope:this, 
	        	 handler:function(){window.carId=null;GAddCarfileFn();}
	         },
	         {
	        	 text:'修改车辆信息',
	        	 iconCls :'edit-icon',
	        	 scope:this,
	        	 handler:function(){
	            	if(this.getSelectionModel().getCount()==1){ 
		        	 	var record = this.getSelectionModel().getSelected();
	            		window.carId=record.get('id');
		        	 	GAddCarfileFn();
	            	}else{ 
	            		Ext.MessageBox.alert('温馨提示', '请您选择一条记录！');
	            	}
            	},
            	id:carfileListMdfBtn,
	        	 disabled:true
	         },
	         {
	        	 text: mbLocale.infoButton, 
	        	 iconCls :'info-icon',
	        	 scope:this, 
	        	 handler:function(){
	            	if(this.getSelectionModel().getCount()==1){ 
		        	 	var record = this.getSelectionModel().getSelected();
	            		window.carId=record.get('id');
		        	 	GShowCarfileDetailFn();
	            	}else{ 
	            		Ext.MessageBox.alert('温馨提示', '请您选择一条记录！');
	            	}
            	},
	        	id:carfileListInfoBtn,
	        	disabled:true
	         },'-'
	         ]],
			sm:sm,
			listeners: {
				render: GCarRowRenderFn
			}
	    }; // eo config object 
		// apply config
		Ext.apply(this, Ext.apply(this.initialConfig, config)); 
		micrite.car.carfileList.SearchPanel.superclass.initComponent.apply(this, arguments);
//		disableAllBtn();
    }, // eo function initComponent	
    
	getPieChart : function() {
		var c1 = {id:'carfileList.piechar',
		          title:this.carTypePieChart};
        var c2 = {
            url: 'car/getCarTypePieChart.action',
            params:this.getAllField()
        };
        this.genChartWindow(c1,c2);
	}, //eof getPieChart
	
	deleteCarfile : function() {
		if(this.getSelectionModel().getCount()<1){ 
    		Ext.MessageBox.alert('温馨提示', '请您选择一条记录！');
    		return;
    	}
		var CarfileIds = this.selModel.selections.keys;
		var deleteRolesFun = function(buttonId, text, opt) {
			if (buttonId == 'yes') {
				micrite.util.ajaxRequest({
                    url: 'car/deleteCarfile.action',
                    params:{'CarfileIds':CarfileIds},
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
	} //eof deleteCarfile	
	
}); //eo extend

// 处理多语言
try {carfileListLocale();} catch (e) {}		
Ext.onReady(function() {
	Ext.QuickTips.init();	
	var formPanel = new micrite.car.carfileList.SearchPanel();

	if (mainPanel) {
		mainPanel.getActiveTab().add(formPanel);
		mainPanel.getActiveTab().doLayout();
	} else {
		var vp = new Ext.Viewport({
			layout:'fit',
			items:[formPanel]
		});
	}
	//formPanel.getStore().load({params:{start:0,limit:formPanel.pageSize,queryString:''}})//打开自动显示数据
});
</script>
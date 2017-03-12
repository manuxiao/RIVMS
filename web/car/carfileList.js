
<script type="text/javascript">
Ext.namespace('micrite.car.carfileList');
micrite.car.carfileList.SearchPanel = Ext.extend(micrite.ComplexGrid, {	
	initComponent:function() {	
	//车辆类型下拉框
	var pageSize = parseInt(Ext.getDom('pageSize').value,10);
	//只有车辆类型和技术等级组合查询时用到
	var comboSkillRank = new Ext.form.ComboBox({
		valueField: "id",
		displayField: "name",
		store: storeSkillRank,
		triggerAction :'all',
		lazyRender:false 
	});   
	var comboLicenseType = new Ext.form.ComboBox({
		valueField: "id",
		displayField: "name",
		store: storeLicenseType,
		triggerAction :'all',
		lazyRender:false 
	});
	var comboFuelRank = new Ext.form.ComboBox({
		valueField: "id",
		displayField: "name",
		store: storeFuelRank,
		triggerAction :'all',
		lazyRender:false 
	});   
	var comboEvCycle = new Ext.form.ComboBox({
		valueField: "id",
		displayField: "name",
		store: storeEvCycle,
		triggerAction :'all',
		lazyRender:false 
	});	   	
	var comboMtCycle = new Ext.form.ComboBox({
		valueField: "id",
		displayField: "name",
		store: storeMtCycle,
		triggerAction :'all',
		lazyRender:false 
	});
	var comboPaiSe = new Ext.form.ComboBox({
		valueField: "id",
		displayField: "name",
		store: storePaiSe,
		triggerAction :'all',
		lazyRender:false 
	});	

	//------非检测站传来的-车辆检测记录界面 的columnstore--------------		
	var checkStore = new Ext.data.JsonStore({
		id:'myStore',
		sortInfo : {field : 'jianTime',direction : 'desc'},
   		url:'check/findCheck.action',
		root:'data',
		fields:[
				{name: 'id'},
	            {name: 'jianTime'},
	            {name: 'endTime'},
	            {name: 'paiHao'},
	            {name: 'heGe'},
	            {name: 'printed'},
	            {name: 'cheDengji'},
            	{name: 'carId'},
            	{name: 'isPost'},
            	{name: 'status'},
	            {name: 'paiSe.id'},
	            {name: 'paiSe.name'},
	            {name: 'notepad'}				
			],
		totalProperty:'totalCount'
	});	
	//------车辆历史------------------------
	var historyStore = new Ext.data.JsonStore({
		sortInfo : {field : 'createDate',direction : 'desc'},
   		pruneModifiedRecords:true,
   		url:'check/findCheck.action',
		root:'data',
		fields:[
				{name: 'id'},
	            {name: 'createDate'},
	            {name: 'testKind'},
	            {name: 'createrId',mapping : 'createrId'},
	            {name: 'notepad'}				
			]
	});	
 
//Ext.ux.GridView=Ext.extend(   
//    Ext.grid.GridView,   
//    {   getRowClass:function(record,index)   
//        {      
//            if(record.get('printed') == 1)
//            {  win.getStore().on("load",function(){  win.getSelectionModel().selectRow(index,true);}); }
//        }   
//    });    
	
	var smcheck = new Ext.grid.CheckboxSelectionModel();
	var checkHisCM = new Ext.grid.ColumnModel({
		columns:[
//        	smcheck,
			new Ext.grid.RowNumberer(),
			{
				header:'编号',dataIndex:'id',sortable:true,hidden:true
        	},{
				header: '检测时间',
				width: 100, sortable: true, dataIndex: 'jianTime'
			},{
				header: '有效期止',
				width: 100, sortable: true, dataIndex: 'endTime'
			},{
				header: '合格证号',
				width: 100, sortable: true, dataIndex: 'heGe'
			},{
				header: '签证备注',
				width: 100, sortable: true, dataIndex: 'notepad'
			},{
				header: '车牌号码',
				width: 100, sortable: true, dataIndex: 'paiHao'
			},{
				header: '车牌颜色',
				width: 100, sortable: true, dataIndex: 'paiSe.name'
			},{
				header: '来自',
				width: 100, sortable: true, dataIndex: 'status',
				renderer:GRenderCheckSrc
			}
		]
	});
	var checkEvalHisCM = new Ext.grid.ColumnModel({
		columns:[
//        	smcheck,
			new Ext.grid.RowNumberer(),
			{
				header:'编号',dataIndex:'id',sortable:true,hidden:true
        	},{
				header: '评定时间',
				width: 100, sortable: true, dataIndex: 'jianTime'
			},{
				header: '有效期止',
				width: 100, sortable: true, dataIndex: 'endTime'
			},{
				header: '技术等级',
				width: 100, sortable: true, dataIndex: 'cheDengji'
			},{
				header: '合格证号',
				width: 100, sortable: true, dataIndex: 'heGe'
			},{
				header: '签证备注',
				width: 100, sortable: true, dataIndex: 'notepad'
			},{
				header: '车牌号码',
				width: 100, sortable: true, dataIndex: 'paiHao'
			},{
				header: '车牌颜色',
				width: 100, sortable: true, dataIndex: 'paiSe.name'
			},{
				header: '来自',
				width: 100, sortable: true, dataIndex: 'status',
				renderer:GRenderCheckSrc
			}
		]
	});
	
//	var smcheckAdd = new Ext.grid.CheckboxSelectionModel();
//	var cmAdd = new Ext.grid.ColumnModel({
//		columns:[	
//        	smcheckAdd,
//			new Ext.grid.RowNumberer(),{
//			header:'记录序号',dataIndex:'id',sortable:true,hidden:true
//        	},  
//  			{									
//				header: '检测时间',
//				width: 100, sortable: true, dataIndex: 'jianTime'
//			},
//			{
//		        header: '检测编号', 		        
//		        width: 100, sortable: true, dataIndex: 'testNo'
//		     }, 			
//			{
//				header: '车牌号码',
//				width: 100, sortable: true, dataIndex: 'paiHao'
//			},	           		            
//			{
//				header: '合格证号',
//				width: 100, sortable: true, dataIndex: 'heGe'
//			},
//			{
//				header: '车牌颜色',
//				width: 100, sortable: true, dataIndex: 'paiSe.id',
//				editor:comboPaiSe,renderer:myRenderer
//			}
//		]
//	});
	var historyCM = new Ext.grid.ColumnModel({
		columns:[	
			new Ext.grid.RowNumberer(),{
			header:'记录序号',dataIndex:'id',sortable:true,hidden:true
        	},  
  			{									
				header: '字段',
				width: 100, sortable: true, dataIndex: 'testKind',
				editor:storeCarColumns,renderer:myRendererFromStore2
			},	
			{
		        header: '内容',
				width: 100, sortable: true, dataIndex: 'notepad'
		     }, 
			{
				header: '操作人',
				width: 100, sortable: true, dataIndex: 'createrId',
				editor:storeCreaters,renderer:myRendererFromStore
			},	           		            
			{
				header: '操作日期',
				width: 100, sortable: true, dataIndex: 'createDate'
			}
		]
	});
	
	var checkWin = new Ext.grid.GridPanel({
        border:false,
        stripeRows:true,
        view:new Ext.grid.GridView({forceFit:true}),
        selModel:new Ext.grid.RowSelectionModel(),
       	cm:checkHisCM,
        store:checkStore,
//        sm:smcheck,
        listeners: {
//           beforerender: function(g){
//           	this.getStore().reload();
//                var view = g.getView();    // Capture the GridView.
//                var store = g.getStore();
//                var rowTip = new Ext.ToolTip({
//                    target: view.mainBody,    // The overall target element.
//                    delegate: '.x-grid3-row', // Each grid row causes its own seperate show and hide.
//                    trackMouse: true,         // Moving within the row should not hide the tip.
//                    renderTo: document.body,  // Render immediately so that tip.body can be
//                    listeners: {  // Change content dynamically depending on which element triggered the show.
//                        beforeshow: function updateTipBody(tip){
//                            var rowIndex = view.findRowIndex(tip.triggerElement);
//                            var record = store.getAt(rowIndex);
//                            if(record.get('printed') == 1){                           	
//                     			tip.body.dom.innerHTML = "<b>此检测记录已打印<br/>";
//                 			}else{
//                     				return false; //停止执行，从而禁止显示Tip
//                     				tip.destroy();
//                 			}
//            			}     
//        			}                    
//                });
//            }
        }
    });
	var checkEvalWin = new Ext.grid.GridPanel({
        border:false,
        stripeRows:true,
        view:new Ext.grid.GridView({forceFit:true}),
        selModel:new Ext.grid.RowSelectionModel(),
       	cm:checkEvalHisCM,
        store:checkStore,
        listeners: {
        }
    });     
    var carEditHistoryGridPanel = new Ext.grid.GridPanel({
        border:false,
        stripeRows:true,
        view:new Ext.grid.GridView({forceFit:true}),
        selModel:new Ext.grid.RowSelectionModel(),
       	cm:historyCM,
        store:historyStore//,
//        listeners: {
//           render: function(g){
//                var view = g.getView();    // Capture the GridView.
//                var store = g.getStore();
//                var rowTip = new Ext.ToolTip({
//                    target: view.mainBody,    // The overall target element.
//                    delegate: '.x-grid3-row', // Each grid row causes its own seperate show and hide.
//                    trackMouse: true,         // Moving within the row should not hide the tip.
//                    renderTo: document.body,  // Render immediately so that tip.body can be
//                    listeners: {  // Change content dynamically depending on which element triggered the show.
//                        beforeshow: function updateTipBody(tip){
//                            var rowIndex = view.findRowIndex(tip.triggerElement);
//                            var record = store.getAt(rowIndex);
//                            if(record.get('carId') == 0){                           	
//                     			tip.body.dom.innerHTML = "<b>没有找到匹配的车辆！<br/><br>车辆号码:"+record.get('paiHao')+"</br><br>车辆颜色:"+record.get('paiSe.id');
//                     			tpl: '<b>项目名称</b>:{ProjectName}<br/>责任人:{LiabilityMan}' 
//                 			}else{
//                     				return false; //停止执行，从而禁止显示Tip
//                     				tip.destroy();
//                 			}
//            			}     
//        			}                    
//                });
//            }
//        }
    });  
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
			},/*
             new Ext.form.ComboBox({//车牌颜色
                name:'paiSe.id',
                selectOnFocus:true,
                valueField:'id',
//                hiddenName:'paiSe.id',
                displayField:'name',
                fieldLabel:'车牌颜色',
                emptyText:'请选择车牌颜色',
                editable:false,
                readOnly : true,
                forceSelection:true,
                store:storePaiSe,
                typeAhead: true               
            }),*/
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
    carfileListInfoBtn='carfileListInfoBtn',
    carfileListGHBtn='carfileListGHBtn',
    carfileListZJBtn='carfileListZJBtn',
    carfileListBFBtn='carfileListBFBtn';
    function decideButtonEnableFn(grid,rowIndex,e){
//    	var record = grid.store.getAt(rowIndex);
    	if(this.getSelectionModel().getCount()==1){
    		Ext.getCmp(carfileListInfoBtn).enable();
    		Ext.getCmp(carfileListDltBtn).enable();
    		
    		var record = this.getSelectionModel().getSelected();
	    	if(record.get('carStatus')!=0){
	    		Ext.getCmp(carfileListMdfBtn).disable();
				Ext.getCmp(carfileListGHBtn).disable();
				Ext.getCmp(carfileListZJBtn).disable();
				Ext.getCmp(carfileListBFBtn).disable();
	    	}else{
	    		Ext.getCmp(carfileListMdfBtn).enable();
		    	if(record.get('expired')==2&&record.get('approval')==0){
					Ext.getCmp(carfileListGHBtn).disable();
					Ext.getCmp(carfileListZJBtn).disable();
					Ext.getCmp(carfileListBFBtn).disable();
		    	}else{
					Ext.getCmp(carfileListGHBtn).enable();
					Ext.getCmp(carfileListZJBtn).enable();
					Ext.getCmp(carfileListBFBtn).enable();
		    	}
	    	}
    	}else{
    		disableAllBtn();
    	}
    }
    function disableAllBtn(){
    	Ext.getCmp(carfileListDltBtn).disable();
		Ext.getCmp(carfileListMdfBtn).disable();
		Ext.getCmp(carfileListInfoBtn).disable();
		Ext.getCmp(carfileListGHBtn).disable();
		Ext.getCmp(carfileListZJBtn).disable();
		Ext.getCmp(carfileListBFBtn).disable();
    }

	function OwnerLicenseBlur(thisComponent){
		//alert(thisComponent.ownerCt.getComponent(''));
		var theForm=Ext.getCmp("carGHform").getForm();
		theForm.findField("carowner.name").setValue('');
    	var ownerLicense = theForm.findField("carowner.license").getValue();
        if(ownerLicense=='')return;
		Ext.Ajax.request({   
			url:'crm/getCarownerByLicense.action?carowner.license='+ownerLicense,
			success:function(response){
				obj = Ext.util.JSON.decode(response.responseText);
				if(!obj.success){	
//					showMsg('failure', obj.message);
					Ext.MessageBox.alert('温馨提示', '不存在该业户。是否输错？');
                	return false;
				}else{
//					showMsgSilently('success', '存在该业户');
					theForm.findField("ghee").setValue(obj.owner.id);
					theForm.findField("carowner.name").setValue(obj.owner.name);
				}
			},
			failure: function(response) {
				showMsgSilently('failure', response.responseText);
			}
		}); 
    }    
    // --------车辆过户 ----------------------
	var carGHWindow = new Ext.Window({
	   	modal:true,
        title:'车辆过户操作',
		closeAction:'hide',
		padding:'5px',
		width : 430,
		items : [{
        id: 'carGHform',
		labelAlign : 'right',
		labelWidth : 80,
		xtype:'form',
		bodyStyle : 'padding:1px',
		baseCls:'x-plain',
		items: [{ 
       		xtype: 'fieldset',
       		padding:'1px',
       		title:'车辆过户',       		
        	layout : 'column',        				
            collapsible: true,
            defaults: {width: 200},    // Default config options for child items
    items: [{
        labelWidth: 80,
        layout:'form',
        collapsible: true,
        defaults: {width: 200},    // Default config options for child items
        defaultType: 'textfield',        
        columnWidth : .8,
		baseCls : 'x-plain',		
        items: [
		{
			xtype:'hidden',
		    name: 'id',
		    allowBlank:false
		},
		{
			xtype:'hidden',
		    name: 'ghee',
		    allowBlank:false
		},
		{
		    fieldLabel: '过户人许可证号',
		    name: 'carowner.license',
		    allowBlank:false,
		    listeners:{
		     'blur': OwnerLicenseBlur  
		    }			
		},		
		{
		    fieldLabel: '过户人姓名',
		    name: 'carowner.name',
		    allowBlank:false,
		    readOnly:true		
		},
		{
			xtype:'textarea',
			name: 'notepad',
		    fieldLabel: '过户备注'
		},{
		    fieldLabel: '车牌号码',
		    name: 'licenseNumber',
		    allowBlank:false
		},{
		    fieldLabel: '车牌颜色',
		    name: 'paiSe.name',
		    allowBlank:false,
		    readOnly:true
		}]
    },
    {   
    	columnWidth : .2,
	items : [{
			xtype:'button',
//			id:'addNewCarOwnerbtn',
			text:'新增车主',
			width:70,
			iconCls :'add-icon',
			handler: this.addCarowner
		}]}]}],			
            buttonAlign:'center',
            buttons: [{
                text: mbLocale.submitButton,
                handler: function(){
                	if(!Ext.getCmp("carGHform").getForm().isValid())return;
                    Ext.getCmp("carGHform").getForm().submit({
                        url: 'car/updateGH.action',
                        method: 'POST',
                        disabled:true,
                        waitMsg: mbLocale.waitingMsg,                       
                        success: function(form, action) {
//                            obj = Ext.util.JSON.decode(action.response.responseText);
                            showMsg('success', action.result.message);
                            carGHWindow.hide();
                            micrite.security.framework.refleshActiveTabGridPanel();
                        },
                        failure: function(form, action) {
//                       		Ext.MessageBox.alert("请注意","请您输入红色提示的必填项！"); 
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
            handler: function(){carGHWindow.hide();}
        }]
        }]
    });    
    function showGHWindowFn(){
    	if(sm.getCount()==1){ 
			var record = sm.getSelected();//this.getSelectionModel()
			if(record.get('carStatus')!=0){
				Ext.MessageBox.alert('提示', '此车不能操作！');	
				return;
			}
			if(record.get('expired')==2&&record.get('approval')==0){
				Ext.MessageBox.alert('提示', '此车已超期、请先处理！');	
				return;
			}
    		var theForm=Ext.getCmp("carGHform").getForm();
    		theForm.reset();//不加不管点哪条记录都是相同内容
    	 	carGHWindow.show();
		 	theForm.loadRecord(record);		 	
    	}else{
    		GOnlyOneSelectedFn(sm.getCount());
    	}
	}
	function showZJWindowFn(){
		__zhuanjizhuxiaoFn('转籍','car/updateZJ.action?id=');
	}
	function showZXWindowFn(){
		__zhuanjizhuxiaoFn('报废','car/updateZX.action?id=');
	}
	function __zhuanjizhuxiaoFn(actionStr,urll){
		if(sm.getCount()==1){
			var record = sm.getSelected();
			if(record==null)return;
			if(record.get('carStatus')!=0){
				Ext.MessageBox.alert('提示', '此车不能操作！');	
				return;
			}
			if(record.get('expired')==2&&record.get('approval')==0){
				Ext.MessageBox.alert('提示', '此车已超期、请先处理！');	
				return;
			}
			Ext.Msg.prompt(''+record.get('licenseNumber')+actionStr, '简要说明：', function(id, msg){
		  	if(id=='ok'){
		  		Ext.Ajax.request({   
					url:urll+record.get('id'),
					success:function(response){
						obj = Ext.util.JSON.decode(response.responseText);
						if(!obj.success){
		                	showMsg('failure', obj.message);
						}else{
							showMsg('success', obj.message);
							micrite.security.framework.refleshActiveTabGridPanel();
						}
					}
				});
		  	}
		  }, this, true);

    	}else{ 
    		GOnlyOneSelectedFn(sm.getCount());
    	}
  	}

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
		           },'-',
	               '技术等级',
		           {xtype:'combo',
	               name:'skillRankId',
		           valueField:'id',
		           hiddenName:'skillRankId',
		           displayField:'name',
		           emptyText:'全部',
		           editable:true,
		           allowBlank:true,
		           forceSelection:true,
		           selectOnFocus:true,
		           triggerAction:'all',
		           store:storeSkillRank
		           }	
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
	         },'-',{
	        	 text:'过户',
	        	 iconCls :'edit-icon',
	        	 scope:this,
	        	 handler:showGHWindowFn,
	        	 id:carfileListGHBtn,
	        	 disabled:true
	         },{
	        	 text:'转籍',
	        	 iconCls :'edit-icon',
	        	 scope:this,
	        	 handler:showZJWindowFn,
	        	 id:carfileListZJBtn,
	        	 disabled:true
	         },{
	        	 text:'报废',
	        	 iconCls :'edit-icon',
	        	 scope:this,
	        	 handler:showZXWindowFn,
	        	 id:carfileListBFBtn,
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
	}, //eof deleteCarfile	
	addCarowner : function(a,b) {
		var theForm=Ext.getCmp("carGHform").getForm();
		window.ownerIdCmp = theForm.findField("ghee").getValue();
        window.ownerNameCmp = theForm.findField("carowner.name").getValue();
        window.ownerLicenseCmp = theForm.findField("carowner.license").getValue();
		var win = micrite.util.genWindow({
            id       : 'addCarownerWindowOfGuohu',
            title    : this.newCarowner,
            autoLoad : {url: 'crm/carownerDetail.js',scripts:true},
            width    : 500,
            height   : 360
        });
	}, //eof addCarowner
	deletecheck : function(){ 
		var obj = checkWin;
		var checkIds = new Array(obj.getSelectionModel().getSelections().length);
		for(var i=0;i<checkIds.length;i++) {
			checkIds[i] = obj.getSelectionModel().getSelections()[i].get('id');
		}
		var submitFun = function(buttonId, text, opt) {
			if (buttonId == 'yes') {
				micrite.util.ajaxRequest({
					url: 'check/deleteCheck.action',
					params:{'checkIds':checkIds},
					success:function(r,o){
						obj.getStore().reload();
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
	} //eof deletecheck
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
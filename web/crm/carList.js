<script type="text/javascript">
Ext.namespace('micrite.car.carList');
micrite.car.carList.SearchPanel = Ext.extend(micrite.ComplexGrid, {	
	initComponent:function() {	
	//车辆类型下拉框
	var RecordDef = Ext.data.Record.create([{name: 'id'},{name: 'name'}]);
	var RecordDef2 = Ext.data.Record.create([{name: 'value'},{name: 'name'}]);
	
	var pageSize = parseInt(Ext.getDom('pageSize').value,10);

	//------非检测站传来的-车辆检测记录界面 的columnstore--------------		
	var checkStore = new Ext.data.JsonStore({
		id:'myStore',
		sortInfo : {   
        field : 'jianTime',   
        direction : 'asc'  
   		},
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
	            {name: 'notepad'}				
			],
		totalProperty:'totalCount'
	});	
	//------车辆历史------------------------
	var historyStore = new Ext.data.JsonStore({
//		id:'postStore',
		sortInfo : {   
	        field : 'createDate',   
	        direction : 'desc'  
   		},
   		pruneModifiedRecords:true,
   		url:'check/findCheck.action',
		root:'data',
		fields:[
				{name: 'id'},
	            {name: 'createDate'},
	            {name: 'testKind'},
	            {name: 'createrId'},
	            {name: 'notepad'}				
		]//,
//		totalProperty:'totalCount'
	}); 
//	Ext.ux.GridView=Ext.extend(   
//    Ext.grid.GridView,   
//    {   getRowClass:function(record,index)   
//        {      
//            if(record.get('printed') == 1)
//            {  checkWin.getStore().on("load",function(){  checkWin.getSelectionModel().selectRow(index,true);}); }
//        }   
//    });	
	var smcheck = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns:[
        	smcheck,
			new Ext.grid.RowNumberer(),{
				header:'编号',dataIndex:'id',sortable:true,hidden:true
        	},
  			{
				header: '检测时间',
				width: 100, sortable: true, dataIndex: 'jianTime'
			},			                
			{
				header: '车牌号码',
				width: 100, sortable: true, dataIndex: 'paiHao'
			},	           		            
			{
				header: '合格证号',
				width: 100, sortable: true, dataIndex: 'heGe'
			},
			{
				header: '车牌颜色',
				width: 100, sortable: true, dataIndex: 'paiSe.name'			   
			}
		]
	});
	
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
//		id:'egp01',
        border:false,
        stripeRows:true,
//        view:new Ext.ux.GridView({forceFit:true}),
        selModel:new Ext.grid.RowSelectionModel(),
//        tbar:[[]],
       	cm:cm,
        store:checkStore,
        sm:smcheck,
        listeners: {
           render: function(g){
                var view = g.getView();    // Capture the GridView.
                var store = g.getStore();
                var rowTip = new Ext.ToolTip({
                    target: view.mainBody,    // The overall target element.
                    delegate: '.x-grid3-row', // Each grid row causes its own seperate show and hide.
                    trackMouse: true,         // Moving within the row should not hide the tip.
                    renderTo: document.body,  // Render immediately so that tip.body can be
                    listeners: {  // Change content dynamically depending on which element triggered the show.
                        beforeshow: function updateTipBody(tip){
                            var rowIndex = view.findRowIndex(tip.triggerElement);
                            var record = store.getAt(rowIndex);
                            if(record.get('printed') == 1){                           	
                     			tip.body.dom.innerHTML = "<b>此检测记录已打印<br/>";
                 			}else{
                     				return false; //停止执行，从而禁止显示Tip
                     				tip.destroy();
                 			}
            			}     
        			}                    
                });
            }
        }
    });
  
    
    var carEditHistoryGridPanel = new Ext.grid.GridPanel({
//		id:'egp02',
        border:false,
        stripeRows:true,
        view:new Ext.grid.GridView({forceFit:true}),
        selModel:new Ext.grid.RowSelectionModel(),
       	cm:historyCM,
        store:historyStore,
        listeners: {
           render: function(g){
                var view = g.getView();    // Capture the GridView.
                var store = g.getStore();
                var rowTip = new Ext.ToolTip({
                    target: view.mainBody,    // The overall target element.
                    delegate: '.x-grid3-row', // Each grid row causes its own seperate show and hide.
                    trackMouse: true,         // Moving within the row should not hide the tip.
                    renderTo: document.body,  // Render immediately so that tip.body can be
                    listeners: {  // Change content dynamically depending on which element triggered the show.
                        beforeshow: function updateTipBody(tip){
            			}     
        			}                    
                });
            }
        }
    });
	var carPunishGridPanel = new Ext.grid.GridPanel({
        border:false,
        stripeRows:true,
        view:new Ext.grid.GridView({forceFit:true}),
        selModel:new Ext.grid.RowSelectionModel(),
       	cm:historyCM,
        store:historyStore
    });
    
	var carInfoWindow = new Ext.Window({  
	   	modal:true,
        title:'查看车辆记录',
		closeAction:'hide',
		padding:'5px',
		width : 850,
		autoHeight : true,
		items : [{
		xtype:'form',
		labelAlign : 'right',
		labelWidth : 100,
		bodyStyle : 'padding:1px',
		id:'carList-form',
		baseCls:'x-plain',      
        items: [{ 
           		xtype: 'fieldset',
           		padding:'1px',
           		title:'车辆档案信息',       		
            	layout : 'column',        				
                collapsible: true,
                defaults: {width: 320},    // Default config options for child items
                autoHeight: true, 
        items: [
        {
            labelWidth: 120,
            layout:'form',
            collapsible: true,
            defaults: {width: 230},    // Default config options for child items
            defaultType: 'textfield',
        	height:300,   
            columnWidth : .5,
			baseCls : 'x-plain',
			
            items: [{
                fieldLabel: '经营许可证号',
                name: 'ownerLicense',
                readOnly : true
            },{
                fieldLabel: '车牌所属业户',
                name: 'owner',
                readOnly : true
            },{
                fieldLabel: '业户联系电话',
                name: 'telephone',
                vtype:'telephone',
                readOnly : true
            },{
                fieldLabel: this.colModelMobile,
                name: 'mobile',
                readOnly : true
            },{
                fieldLabel: '业户地址',
                name: 'address',
                readOnly : true
            }, {
                fieldLabel: '车牌号码',
                name: 'licenseNumber',
                readOnly : true
            },{
				fieldLabel: '车牌颜色',
				name: 'paiSe.name',
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
				fieldLabel: '车牌型号',
				name: 'licenseType.name',
				readOnly : true
			},
            {
                fieldLabel: this.colModelLoadTon,
                name: 'loadTon',
                readOnly : true
            }, {
                fieldLabel: '品牌型号',
                name: 'brandType',
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
		items : [{
				fieldLabel: this.colModelYingyunNo,
				name: 'yingyunNo',
				readOnly : true
			},{
				fieldLabel: '技术等级',
				name: 'skillRank.name',
				readOnly : true
			},{
				fieldLabel: '技术等级评定有效期止',
				name: 'evaluateDate',
				readOnly : true
			},{
				fieldLabel: '技术等级评定周期',
				name: 'evaluateCycle.name',
				readOnly : true
			},{
				fieldLabel: this.colModelMaintainDate,
				name: 'maintainDate',
				readOnly : true
			},{
				fieldLabel: this.colModelMaintainCycle,
				name: 'maintainCycle.name',
				readOnly : true
			},{
				fieldLabel: this.colModelMaintainDateEnd,
				name: 'maintainDateEnd',
				readOnly : true
			},{
				fieldLabel: this.colModelCarType,
				name: 'carType.name',
				readOnly : true
			},{
				fieldLabel: this.colModelFuelRank,
				name: 'fuelRank.name',
				readOnly : true
			},{name: 'id',hidden:true}
            ]
        }]},           
		{
		xtype : 'tabpanel',
//		id:'infoTabPanel',
		plain : true,
		bodyBorder : false,
		activeTab : 0,
		height : 200,
		defaults : {bodyStyle : 'padding:2px'},
		items : [
			{title : '车辆备注',layout : 'fit',items : {xtype : 'textarea',name : 'carRemark',maxLength : 300,readOnly : true}},
			{title : '车辆维护记录',layout : 'fit',items :[checkWin],listeners:{activate:checkTabPanelActivateFn}},
			{title : '车辆修改记录',layout : 'fit',items :[carEditHistoryGridPanel],listeners:{activate:modifyTabPanelActivateFn}},
			{title : '车辆违章记录',layout : 'fit',items :[carPunishGridPanel],listeners:{activate:punishTabPanelActivateFn}}
		]
			
//		items : [
//		{title : '车辆备注',layout : 'fit',items : {xtype : 'textarea',name : 'carRemark',maxLength : 300,readOnly : true}},
//		{title : '车辆维护记录',layout : 'fit',items :[checkWin],listeners:{activate:checkTabPanelActivateFn}},
//		{title : '车辆修改记录',layout : 'fit',items :[carEditHistoryGridPanel],listeners:{activate:modifyTabPanelActivateFn}},
//		{title : '车辆违章记录',layout : 'fit',items :[carPunishGridPanel],listeners:{activate:punishTabPanelActivateFn}}
//			]
    	}],	
            buttonAlign:'center',
            buttons: [
				{
//				text:'修改',
//				scope:this,
//				handler:function() {
//					//打开编辑界面					
//					carInfoWindow.hide();
//					var record = this.getSelectionModel().getSelected();
//					window.carId=record.get('id');
//					if(window.carId==null||window.carId==''){
//						alert('车辆档案编号不明，无法编辑');
//						return;
//					}
//		        	this.addCarfileFn();
//				}
//	    	},{
	            text: '关闭',
	            scope:this,
	            handler: function(){
	            	carInfoWindow.hide();
	            }
        }]
    }]});
    function checkTabPanelActivateFn(){
		    checkStore.load({params:{start:0,limit:pageSize,queryString:'[isPost,1,=],[carId,'+window.carId+',=]'}});
    }
    function modifyTabPanelActivateFn(){
		    historyStore.load({params:{start:0,limit:pageSize,queryString:'[isPost,2,=],[status,'+window.carId+',=],[testKind,100,<]'}});
    }
    function punishTabPanelActivateFn(){
		    historyStore.load({params:{start:0,limit:pageSize,queryString:'[isPost,2,=],[status,'+window.carId+',=],[testKind,100,>]'}});
    }
//    var carPunishGridPanel = new Ext.grid.GridPanel({
//        border:false,
//        stripeRows:true,
//        view:new Ext.grid.GridView({forceFit:true}),
//        selModel:new Ext.grid.RowSelectionModel(),
//       	cm:historyCM,
//        store:historyStore,
//        listeners: {
//        }
//    });
  //通过checkbox选择或反选时触发选区轮换的一个制定选区模型
	var sm = new Ext.grid.CheckboxSelectionModel();
	this.addListener('rowdblclick', rowdblclickFn);   
	function rowdblclickFn(){ 
        if(sm.getCount()==1)
        { 
    	 	var record = sm.getSelected();	
    	 	var record = this.getSelectionModel().getSelected();
			window.carId = record.get('id');
//		 	Ext.getCmp("carList-form").getForm().loadRecord(record);
//		 	carInfoWindow.show();
			GShowCarfileDetailFn();
			
 		}else{ GOnlyOneSelectedFn(sm.getCount());}
    };
    function onOwnerNameBlur(){//过户新车主查找
    	var ownerName = Ext.getCmp("newOwnerNameId").getValue();
        if(ownerName=='')return;
		Ext.Ajax.request({   
			url:'crm/getCarownerByName.action?carowner.name='+ownerName,
			success:function(response){
				obj = Ext.util.JSON.decode(response.responseText);
				if(!obj.success){
                	showMsg('failure', obj.message);
                	return false;
				}else{
					Ext.getCmp("gheeId").setValue(obj.owner.id);
				}
			},
			failure: function(response) {
				showMsg('failure', response.responseText);
			}
		}); 
    }

	var config = {
		    view:new Ext.ux.GridViewCar({forceFit:false}),
			advSearchField : [[]],	  
	        compSet: [
	             {url:0,reader:0,columns:0,bbarAction:0}
	        ],
			searchMenu : [
				 '车牌号码'
			],
			searchFields :[[
				 {advSearch:true},
	             this.colModelLicenseNumber,
	             {xtype:'textfield',
				  name:'licenseNumber',
	              expression:'like',
	              value:'浙B.',
	              width:100},	
			       {
		              xtype:'hidden',
					  name:'carowner.id',
			          expression:'=',
			          value:window.ownerId,
			          width:50}
	            ]         
	            ],
	        urls: ['car/findCarfile.action'],
	        readers : [GCarFields],  
			columnsArray: [GCarColumns],
	         bbarActions:[[]],
	         sm:sm,
	      listeners: {
           render: GCarRowRenderFn
        },	
		buttonAlign:'center',
	    buttons: [{
	        text: mbLocale.closeButton,
	        scope:this,
	        handler: function(){
	            Ext.WindowMgr.getActive().close();
	        }
	    }]
	    }; // eo config object 
		// apply config
		Ext.apply(this, Ext.apply(this.initialConfig, config)); 
		micrite.car.carList.SearchPanel.superclass.initComponent.apply(this, arguments);
    } // eo function initComponent	
}); //eo extend

// 处理多语言
try {carListLocale();} catch (e) {}
Ext.onReady(function() {
	 Ext.QuickTips.init();
     Ext.form.Field.prototype.msgTarget = 'side';
     var panel = new micrite.car.carList.SearchPanel();
     Ext.WindowMgr.getActive().add(panel);
     Ext.WindowMgr.getActive().doLayout();
     var ownerId = window.ownerId;
     if(ownerId==null){
		alert('不知道查哪个单位的车');
     }else{
		panel.getStore().load({params:{start:0,limit:panel.pageSize,queryString:'[carowner.id,'+ownerId+',=]'}});
		window.ownerId=null;
     }
});
</script>
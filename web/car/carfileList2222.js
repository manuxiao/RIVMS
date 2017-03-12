<script type="text/javascript">
Ext.namespace('micrite.car.carfileList');
micrite.car.carfileList.SearchPanel = Ext.extend(micrite.ComplexGrid, {	
	initComponent:function() {	
	//车辆类型下拉框
	var RecordDef = Ext.data.Record.create([{name: 'id'},{name: 'name'}]);
	var RecordDef2 = Ext.data.Record.create([{name: 'value'},{name: 'name'}]);
	
	//只有车辆类型和技术等级组合查询时用到
	var storeCarType = new Ext.data.Store({
		id: Ext.id(),    
		autoLoad:true,
		proxy : new Ext.data.HttpProxy({url: 'dic/getPartnerByAll.action?type=1'}),    
		reader : new Ext.data.JsonReader({    
			id:"id"
		}, RecordDef),
		remoteSort: true   
	});	
	var storeSkillRank = new Ext.data.Store({
		id: Ext.id(),    
		autoLoad:true,
		proxy : new Ext.data.HttpProxy({url: 'dic/getPartnerByAll.action?type=2'}),    
		reader : new Ext.data.JsonReader({    
			id:"id"
		}, RecordDef),
		remoteSort: true   
	});	

	var store = new Ext.data.Store({
		id: Ext.id(),    autoLoad:true,
		proxy : new Ext.data.HttpProxy({url: 'dic/getPartner.action?type=1'}), 
		reader : new Ext.data.JsonReader({    id:"id"}, RecordDef),remoteSort: true   });
	var combo = new Ext.form.ComboBox({valueField: "id",displayField: "name",store: store,triggerAction :'all',lazyRender:false });
	myRenderer = function(value){
		var idx = this.editor.getStore().find(this.editor.valueField, value);
	    var rec = this.editor.getStore().getAt(idx);
	    if (rec == null) {return value;}
	    else {
	        return rec.get(this.editor.displayField);
	    }
	};
	var pageSize = parseInt(Ext.getDom('pageSize').value,10);
//--------skillRank下拉框-----------------------			
	var store2 = new Ext.data.Store({
		id: Ext.id(),    
		autoLoad:true,
		proxy : new Ext.data.HttpProxy({url: 'dic/getPartner.action?type=2'}),    
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
//--------3下拉框-----------------------			
		var store3 = new Ext.data.Store({
		id: Ext.id(),    
		autoLoad:true,
		proxy : new Ext.data.HttpProxy({url: 'dic/getPartner.action?type=3'}),    
		reader : new Ext.data.JsonReader({    
			id:"id"
		}, RecordDef),
		remoteSort: true   
	});	   
	var combo3 = new Ext.form.ComboBox({
		valueField: "id",
		displayField: "name",
		store: store3,
		triggerAction :'all',
		lazyRender:false 
	});
	//--------4下拉框-----------------------			
		var store4 = new Ext.data.Store({
		id: Ext.id(),    
		autoLoad:true,
		proxy : new Ext.data.HttpProxy({url: 'dic/getPartner.action?type=4'}),    
		reader : new Ext.data.JsonReader({    
			id:"id"
		}, RecordDef),
		remoteSort: true   
	});	   	
	var combo4 = new Ext.form.ComboBox({
		valueField: "id",
		displayField: "name",
		store: store4,
		triggerAction :'all',
		lazyRender:false 
	});
	//--------5下拉框-----------------------			
		var store5 = new Ext.data.Store({
		id: Ext.id(),    
		autoLoad:true,
		proxy : new Ext.data.HttpProxy({url: 'dic/getPartner.action?type=5'}),    
		reader : new Ext.data.JsonReader({    
			id:"id"
		}, RecordDef),
		remoteSort: true   
	});	   
	var combo5 = new Ext.form.ComboBox({
		valueField: "id",
		displayField: "name",
		store: store5,
		triggerAction :'all',
		lazyRender:false 
	});
	//--------6下拉框-----------------------			
		var store6 = new Ext.data.Store({
		id: Ext.id(),    
		autoLoad:true,
		proxy : new Ext.data.HttpProxy({url: 'dic/getPartner.action?type=6'}),    
		reader : new Ext.data.JsonReader({    
			id:"id"
		}, RecordDef),
		remoteSort: true   
	});	   	
	var combo6 = new Ext.form.ComboBox({
		valueField: "id",
		displayField: "name",
		store: store6,
		triggerAction :'all',
		lazyRender:false 
	});
	//--------7下拉框-----------------------			
	var storePaiSe = new Ext.data.Store({
		id: Ext.id(),    
		autoLoad:true,
		proxy : new Ext.data.HttpProxy({url: 'dic/getPartner.action?type=7'}),    
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
		proxy : new Ext.data.HttpProxy({url: 'dic/getPartner.action?type=15'}),    
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
	
	var storeCarStatus = new Ext.data.Store({
		id: Ext.id(),    
		autoLoad:true,
		proxy : new Ext.data.HttpProxy({url: 'dic/getPartner.action?type=14'}),    
		reader : new Ext.data.JsonReader({    
			id:"value"
		}, RecordDef2),
		remoteSort: true   
	});	   	
	var comboCarStatus = new Ext.form.ComboBox({
    	name:'carStatus',
        hiddenName:'carStatus',
		valueField: "value",
		displayField: "name",
		fieldLabel:this.colModelCarStatus,
		store: storeCarStatus,
		triggerAction :'all',
		lazyRender:false 
	});
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
	            {name: 'jianTime',type : 'date',dateFormat : 'Y-m-dTH:i:s',mapping : 'jianTime'},
	            {name: 'paiHao'},
	            {name: 'heGe'},
	            {name: 'printed'},
            	{name: 'carId'},
            	{name: 'isPost'},
            	{name: 'status'},
	            {name: 'paiSe.id',mapping : 'paiSe.id'},
	            {name: 'notepad'}				
			],
		totalProperty:'totalCount'
	});	
	//------车辆历史------------------------
		var historyStore = new Ext.data.JsonStore({
		sortInfo : {   
        field : 'createDate',   
        direction : 'desc'  
   		},
   		pruneModifiedRecords:true,
   		url:'check/findCheck.action',
		root:'data',
		fields:[
				{name: 'id'},
	            {name: 'createDate',type : 'date',dateFormat : 'Y-m-dTH:i:s',mapping : 'createDate'},
	            {name: 'testKind'},
	            {name: 'createrId',mapping : 'createrId'},
	            {name: 'notepad'}				
			]
	});	
	
Ext.ux.GridViewCarId=Ext.extend(   
    Ext.grid.GridView,   
    {   getRowClass:function(record,index)   
        {      
//            if(record.get('carId') == 0){return 'red-row';}          
//            else  
//                return 'white-row'; 
        }   
    });  
Ext.ux.GridView=Ext.extend(   
    Ext.grid.GridView,   
    {   getRowClass:function(record,index)   
        {      
            if(record.get('printed') == 1)
            {  win.getStore().on("load",function(){  win.getSelectionModel().selectRow(index,true);}); }
        }   
    });	
	var smcheck = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns:[
        	smcheck,
			new Ext.grid.RowNumberer(),{
			header:'编号',dataIndex:'id',sortable:true,hidden:true
        	},
  			{
				header: '检测时间',
				width: 100, sortable: true, dataIndex: 'jianTime',
				renderer : Ext.util.Format.dateRenderer('Y-m-d')
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
				width: 100, sortable: true, dataIndex: 'paiSe.id',
				editor:comboPaiSe,renderer:myRenderer,readOnly:true				   
			}
		]
	});
	
	var smcheckAdd = new Ext.grid.CheckboxSelectionModel();
	var cmAdd = new Ext.grid.ColumnModel({
		columns:[	
        	smcheckAdd,
			new Ext.grid.RowNumberer(),{
			header:'记录序号',dataIndex:'id',sortable:true,hidden:true
        	},  
  			{									
				header: '检测时间',
				width: 100, sortable: true, dataIndex: 'jianTime',
				renderer : Ext.util.Format.dateRenderer('Y-m-d')
			},
			{
		        header: '检测编号', 		        
		        width: 100, sortable: true, dataIndex: 'testNo'
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
				width: 100, sortable: true, dataIndex: 'paiSe.id',
				editor:comboPaiSe,renderer:myRenderer
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
				editor:comboColumn,renderer:myRenderer
			},	
			{
		        header: '内容',
				width: 100, sortable: true, dataIndex: 'notepad'
		     }, 
			{
				header: '操作人',
				width: 100, sortable: true, dataIndex: 'createrId',
				editor:comboCreater,renderer:myRenderer
			},	           		            
			{
				header: '操作日期',
				width: 100, sortable: true, dataIndex: 'createDate',
				renderer : Ext.util.Format.dateRenderer('Y-m-d')
			}
		]
	});
	
	var win = new Ext.grid.GridPanel({
		id:'egp01',
        border:false,
        stripeRows:true,
        view:new Ext.ux.GridView({forceFit:true}),
        selModel:new Ext.grid.RowSelectionModel(),
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
		id:'egp02',
        border:false,
        stripeRows:true,
        view:new Ext.ux.GridViewCarId({forceFit:true}),
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
//                            var rowIndex = view.findRowIndex(tip.triggerElement);
//                            var record = store.getAt(rowIndex);
//                            if(record.get('carId') == 0){                           	
//                     			tip.body.dom.innerHTML = "<b>没有找到匹配的车辆！<br/><br>车辆号码:"+record.get('paiHao')+"</br><br>车辆颜色:"+record.get('paiSe.id');
//                     			tpl: '<b>项目名称</b>:{ProjectName}<br/>责任人:{LiabilityMan}' 
//                 			}else{
//                     				return false; //停止执行，从而禁止显示Tip
//                     				tip.destroy();
//                 			}
            			}     
        			}                    
                });
            }
        }
    });   
 //--------查看车辆页面只读界面----------------------	
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
		id:'carfilelook-form',
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
        	height:230,   
            columnWidth : .5,
			baseCls : 'x-plain',
			
            items: [{
                fieldLabel: '车牌所属业户',
                name: 'owner',
                readOnly : true
            },{
                fieldLabel: '业户联系电话',
                name: 'telephone',
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
                store:storePaiSe,
                typeAhead: true               
            }),
             new Ext.form.ComboBox({ //车牌型号
                name:'licenseType',
                selectOnFocus:true,
                valueField:'id',
                hiddenName:'licenseType.id',
                displayField:'name',
                fieldLabel:'车牌型号',
                emptyText:'',
                editable:false,
                forceSelection:true,
                readOnly:true,
                store:store3,
                typeAhead: true               
            }),
            {
                fieldLabel: '核载吨位',
                name: 'loadTon',
                readOnly : true
            }, {
                fieldLabel: '品牌型号',
                name: 'brandType',
                readOnly : true
            },
            {
				fieldLabel: '营运证号',
				name: 'yingyunNo',
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
            height:230,  
        	columnWidth : .5,
			baseCls : 'x-plain',
		items : [
            new Ext.form.ComboBox({ //技术等级
                name:'skillRank',
                readOnly : true,
                selectOnFocus:true,
                valueField:'id',
                hiddenName:'skillRank.id',
                displayField:'name',
                fieldLabel:'技术等级',
                emptyText:this.comboEmpty2Text,
                editable:false,
                forceSelection:true,
                store:store2,
                typeAhead: true               
            }),
            {           	
            	xtype:'uxspinnerdate',
                fieldLabel: '技术等级评定有效期止',
                name: 'evaluateDate',
                readOnly : true,                
	            value:new Date(),
	            format:'Y-m-d'	            
            }, 
            new Ext.form.ComboBox({//技术等级评定周期
                name:'evaluateCycle',
                selectOnFocus:true,
                valueField:'id',
                hiddenName:'evaluateCycle.id',
                displayField:'name',
                fieldLabel:'技术等级评定周期',
                emptyText:'请选择技术等级评定周期',
                editable:false,
                readOnly : true,
                forceSelection:true,
                store:store5,
                typeAhead: true               
            }),
            	{
            	xtype:'uxspinnerdate',
                fieldLabel: this.colModelMaintainDate,
                name: 'maintainDate',
                readOnly : true,                
                value:new Date(),
                format:'Y-m-d'
            },
            new Ext.form.ComboBox({//二维周期
                name:'maintainCycle',
                selectOnFocus:true,
                valueField:'id',
                hiddenName:'maintainCycle.id',
                displayField:'name',
                fieldLabel:this.colModelMaintainCycle,
                emptyText:'请选择二维周期',
                editable:false,
                readOnly : true,
                forceSelection:true,
                store:store6,
                typeAhead: true               
            }), 
            {
            	xtype:'uxspinnerdate',
                fieldLabel: this.colModelMaintainDateEnd,
                name: 'maintainDateEnd',
                readOnly : true,                
                value:new Date(),
                format:'Y-m-d'
            },           
            new Ext.form.ComboBox({
                name:'carType',
                selectOnFocus:true,
                valueField:'id',
                hiddenName:'carType.id',
                displayField:'name',
                fieldLabel:'车辆类型',
                emptyText:'请选择车辆类型',
                editable:false,
                readOnly : true,
                forceSelection:true,
                store:store,
                typeAhead: true }),  
             new Ext.form.ComboBox({//燃油类型
                name:'fuelRank',
                selectOnFocus:true,
                valueField:'id',
                hiddenName:'fuelRank.id',
                displayField:'name',
                fieldLabel:'燃油型号',
                emptyText:this.comboEmpty4Text,
                editable:false,
                forceSelection:true,
                store:store4,
                typeAhead: true               
            }),
				{name: 'id',hidden:true}
            ]
        }]},           
		{
		xtype : 'tabpanel',
		plain : true,
		bodyBorder : false,
		activeTab : 0,
		height : 200,
		defaults : {bodyStyle : 'padding:2px'},
		items : [  		
		{title : '车辆维护记录',layout : 'fit',items :[win]},
		{title : '车辆修改记录',layout : 'fit',items :[carEditHistoryGridPanel]},
		{title : '车辆备注',layout : 'fit',items : {xtype : 'textarea',name : 'carRemark',maxLength : 300,readOnly : true}}
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
				 	editCarWindow.show();
				 	Ext.getCmp("carfile2-form").getForm().loadRecord(record);	
				}
    	},{
            text: '关闭',
            scope:this,
            handler: function(){
            	carInfoWindow.hide();
            }
        }]
    }]}); 	
		  //--------编辑界面  ----------------------	
	   	var editCarWindow = new Ext.Window({
        title:'修改车辆记录',
		closeAction:'hide',
		padding:'5px',
		width : 850,
		height:520,
		items : [{
		xtype:'form',
		id:'carfile2-form',
		labelAlign : 'right',
		labelWidth : 100,
		bodyStyle : 'padding:1px',
		baseCls:'x-plain',	
        items: [{ 
           		xtype: 'fieldset',
           		padding:'1px',
           		title:'填写车辆档案信息',       		
            	layout : 'column',        				
                collapsible: true,
                defaults: {width: 300},    // Default config options for child items
                height:280,
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
                fieldLabel: '车牌所属业户',
                name: 'owner',
                allowBlank:false
            },{
                fieldLabel: '业户联系电话',
                name: 'telephone',
                allowBlank:false
            },{
                fieldLabel: this.colModelMobile,
                name: 'mobile',
                allowBlank:false
            },{
                fieldLabel: '业户地址',
                name: 'address',
                allowBlank:false
            }, {
                fieldLabel: '车牌号码',
                name: 'licenseNumber',
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
                allowBlank:false,
                forceSelection:true,
                triggerAction:'all',
                store:storePaiSe,
                typeAhead: true               
            }), 
             new Ext.form.ComboBox({ //车牌型号
                name:'licenseType',
                selectOnFocus:true,
                valueField:'id',
                hiddenName:'licenseType.id',
                displayField:'name',
                fieldLabel:'车牌型号',
                emptyText:'',
                editable:false,
                allowBlank:false,
                forceSelection:true,
                triggerAction:'all',
                store:store3,
                typeAhead: true               
            }),
            {
                fieldLabel: '核载吨数/载客',
                name: 'loadTon',
                allowBlank:true
            }, {
                fieldLabel: '品牌型号',
                name: 'brandType',
                allowBlank:true
            }
            ]
        },
        {
            labelWidth: 130,
            layout:'form',
            collapsible: true,
            defaults: {width: 210},    // Default config options for child items
            defaultType: 'textfield',
            height:300,          
        	columnWidth : .5,
			baseCls : 'x-plain',
		items : [
				{
				fieldLabel: '营运证号',
				name: 'yingyunNo',
				allowBlank:true
				}, 
            new Ext.form.ComboBox({ //技术等级
                name:'skillRank',
                selectOnFocus:true,
                valueField:'id',
                hiddenName:'skillRank.id',
                displayField:'name',
                fieldLabel:'技术等级',
                emptyText:this.comboEmpty2Text,
                editable:false,
                allowBlank:false,
                forceSelection:true,
                triggerAction:'all',
                store:store2,
                typeAhead: true               
            }),
            {           	
            	xtype:'uxspinnerdate',
                fieldLabel: '技术等级评定日期',
                name: 'evaluateDate',
                allowBlank:false,                
	            format:'Y-m-d'
	            
            }, 
            new Ext.form.ComboBox({//技术等级评定周期
                name:'evaluateCycle',
                selectOnFocus:true,
                valueField:'id',
                hiddenName:'evaluateCycle.id',
                displayField:'name',
                fieldLabel:'技术等级评定周期',
                emptyText:'请选择技术等级评定周期',
                editable:false,
                allowBlank:false,
                forceSelection:true,
                triggerAction:'all',
                store:store5,
                typeAhead: true               
            }),
            	{
            	xtype:'uxspinnerdate',
                fieldLabel: '二维操作日期',
                name: 'maintainDate',
                allowBlank:false,  
                format:'Y-m-d'
            },
            new Ext.form.ComboBox({//二维周期
                name:'maintainCycle',
                selectOnFocus:true,
                valueField:'id',
                hiddenName:'maintainCycle.id',
                displayField:'name',
                fieldLabel:'二维周期',
                emptyText:'请选择二维周期',
                editable:false,
                allowBlank:false,
                forceSelection:true,
                triggerAction:'all',
                store:store6,
                typeAhead: true               
            }),          
            new Ext.form.ComboBox({
                name:'carType',
                selectOnFocus:true,
                valueField:'id',
                hiddenName:'carType.id',
                displayField:'name',
                fieldLabel:'车辆类型',
                emptyText:'请选择车辆类型',
                editable:false,
                allowBlank:true,
                renderer:myRenderer,
                forceSelection:true,
                triggerAction:'all',
                store:store,
                typeAhead: true }),  
             new Ext.form.ComboBox({//燃油类型
                name:'fuelRank',
                selectOnFocus:true,
                valueField:'id',
                hiddenName:'fuelRank.id',
                displayField:'name',
                fieldLabel:'燃油型号',
                emptyText:this.comboEmpty4Text,
                editable:false,
                allowBlank:false,
                forceSelection:true,
                triggerAction:'all',
                store:store4,
                typeAhead: true               
            }),comboCarStatus,
				{name: 'id',hidden:true}
            ]
        }]},           
	{
		xtype : 'tabpanel',
		plain : true,
		bodyBorder : false,
		activeTab : 0,
		height : 100,
		defaults : {bodyStyle : 'padding:2px'},
		items : [
			{title : '车辆备注',layout : 'fit',items : {xtype : 'textarea',name : 'carRemark',maxLength : 300}}
			]
    	}],	
            buttonAlign:'center',
            buttons: [
			{
				text:'保存',
				handler:function() 
				{
				if (Ext.getCmp('carfile2-form').getForm().isValid()) {
					Ext.getCmp('carfile2-form').getForm().submit({
						scope:this,
						url: 'car/updateCarfile.action',
						method: 'POST',
						disabled:true,
						waitTitle:'请稍候',
						waitMsg:'正在修改数据，请等待...',
						success : function(form,action) {
							showMsg('温馨提示', '保存成功!'); 
							editCarWindow.hide();
							obj = Ext.util.JSON.decode(action.response.responseText);
							showMsg('success', '修改成功!');  							
						}, // eof success                   
                    failure: function(form, action) {
                        obj = Ext.util.JSON.decode(action.response.responseText);
                        showMsg('failure', obj.message);//比如服务器端单步调试到这里出错，不会有messaage的；
                    } //eof failure
				}); //eof submit
				}  //eof isValid
				}  //eof handler
    	},{
            text: '关闭',
            scope:this,
            handler: function(){
            	editCarWindow.hide();
            }
        }// ‘关闭’
        ]
    }]
    }); 
    
  //通过checkbox选择或反选时触发选区轮换的一个制定选区模型
	var sm = new Ext.grid.CheckboxSelectionModel();
	this.addListener('rowdblclick', rowdblclickFn);   
	function rowdblclickFn(){ 
        if(sm.getCount()==1)
        { 
    	 	var record = this.getSelectionModel().getSelected();	
		 	carInfoWindow.show();
		 	Ext.getCmp("carfilelook-form").getForm().loadRecord(record);					
			var licenseNumber = record.get('licenseNumber');
			var carpaiSe = record.get('paiSe.id');
			var carId = record.get('id');
		    checkStore.load({params:{start:0,limit:pageSize,queryString:'[isPost,1,=],[paiHao,'+licenseNumber+',=],[paiSe.id,'+carpaiSe+',=]'}});
		    historyStore.load({params:{start:0,limit:pageSize,queryString:'[isPost,2,=],[status,'+carId+',=]'}});
 		}else{ Ext.MessageBox.alert('提示', '请选择一条记录！');}
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
    // --------车辆过户 ----------------------
	var carGHWindow = new Ext.Window({
	   	modal:true,
        title:'车辆过户操作',
		closeAction:'hide',
		padding:'5px',
		width : 400,
		items : [{
        id: 'carGHform',
		labelAlign : 'right',
		labelWidth : 80,
		xtype:'form',
		bodyStyle : 'padding:1px',
		baseCls:'x-plain',	
        items: [
        {
            xtype: 'fieldset',           
            title:'车辆过户',
            layout:'form',
            collapsible: true,           
            labelWidth: 100,
            defaults: {width: 230},    // Default config options for child item										// items
            defaultType: 'textfield',
            items: [{
            	xtype:'hidden',
                name: 'id',
                allowBlank:false
            },{
            	xtype:'hidden',
            	id:'gheeId',
                name: 'ghee',
                allowBlank:false
            },{
                fieldLabel: '过户给...',
                id:'newOwnerNameId',
                name: 'newOwnerName',
                allowBlank:false,
                listeners:{
                 'blur': onOwnerNameBlur  
                }
            },{
            	xtype:'textarea',
                fieldLabel: '过户备注',
                name: 'notepad'
            },{
                fieldLabel: '车牌号码',
                name: 'licenseNumber',
                blankText : '',
                allowBlank:false,
                readOnly:true
            }, 	
              new Ext.form.ComboBox({// 车牌颜色
                name:'paiSe.id',
                selectOnFocus:true,
                valueField:'id',
                hiddenName:'paiSe.id',
                displayField:'name',
                fieldLabel:'车牌颜色',
                emptyText:'',
                editable:false,
                allowBlank:false,
                forceSelection:true,
                triggerAction:'all',
                store:storePaiSe,
                typeAhead: true,
                readOnly:true              
            })]               
        }],
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
                            obj = Ext.util.JSON.decode(action.response.responseText);
                            showMsg('success', obj.message);                                
                        },
                        failure: function(form, action) {
                       		Ext.MessageBox.alert("请注意","请您输入红色提示的必填项！"); 
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
    		var theForm=Ext.getCmp("carGHform").getForm();
			var record = this.getSelectionModel().getSelected();
    	 	carGHWindow.show();
		 	theForm.loadRecord(record);		 	
    	}else{ 
    		Ext.MessageBox.alert('温馨提示', '请您选择一条记录！');
    	}
	}
	var config = {
		    view:new Ext.ux.GridViewCar({forceFit:true}),
			advSearchField : [[]],	  
	        compSet: [
	             {url:0,reader:0,columns:0,bbarAction:0}
	        ],
			searchMenu : [
				 '多条件查询'
			],
			searchFields :[
			     [
	             this.colModelLicenseNumber,
	             {xtype:'textfield',
				  name:'licenseNumber',
	              value:'浙B.B7571',
	              width:120},'-',
	             '车辆类型',
				 {xtype:'combo',
	               name:'carTypeId',
		           selectOnFocus:true,
		           valueField:'id',
		           hiddenName:'carTypeId',
		           displayField:'name',
		           fieldLabel: '车辆类型',
		           emptyText:'请选择车辆类型',
		           editable:false,
		           allowBlank:false,
		           forceSelection:true,
		           triggerAction:'all',
		           store:storeCarType,
		           typeAhead: true,
					listeners:{
	                    render : function(combo) {  
			         			//设置默认选中第一个值  			                 			
	            				storeCarType.on('load', function() {  
			     			    combo.setValue(0);  
			     			});
                			}
						}		           
		           },'-',
	               '技术等级',
		           {xtype:'combo',
	               name:'skillRankId',
		           selectOnFocus:true,
		           valueField:'id',
		           hiddenName:'skillRankId',
		           displayField:'name',
		           fieldLabel: '技术等级',
		           emptyText:'请选择技术等级',
		           editable:false,
		           allowBlank:false,
		           forceSelection:true,
		           triggerAction:'all',
		           store:storeSkillRank,
		           typeAhead: true,
					listeners:{
	                    render : function(combo) {  
			         			//设置默认选中第一个值  			                 			
		        	   			storeSkillRank.on('load', function() {  
			     			    combo.setValue(0);  
			     			});
               			}
						}
		           }	
	            ]         
	            ],
//	            car/findCarfileByDateSpacing.action   'car/findCarfile.action',
	        urls: ['car/findCarfileBySkill.action'],
	        readers : [[
	        	 {name: 'id'},
			     {name: 'owner'},
			     {name: 'telephone'},
			     {name: 'mobile'},
			     {name: 'address'},
			     {name: 'licenseNumber'},			     
			     {name: 'licenseType.id',mapping : 'licenseType.id'},
			     {name: 'loadTon'},
			     {name: 'brandType'},			     
			     {name: 'fuelRank.id',mapping : 'fuelRank.id'},
			     {name: 'skillRank.id',mapping : 'skillRank.id'},
			     {name: 'skillRankId',mapping : 'skillRank.id'},
			     {name: 'evaluateDate',type : 'date',dateFormat : 'Y-m-d',mapping : 'evaluateDate'},
			     {name: 'evaluateCycle.id',mapping : 'evaluateCycle.id'},
			     {name: 'maintainDate',type : 'date',dateFormat : 'Y-m-d',mapping : 'maintainDate'},
			     {name: 'maintainDateEnd',type : 'date',dateFormat : 'Y-m-d',mapping : 'maintainDateEnd'},
			     {name: 'maintainCycle.id',mapping : 'maintainCycle.id'},
			     {name: 'yingyunNo'},
			     {name: 'paiSe.id',mapping : 'paiSe.id'},               
			     {name: 'carType.id',mapping : 'carType.id'},
			     {name: 'carTypeId',mapping : 'carType.id'},
			     {name: 'carRemark'},
			     {name: 'expired'},
			     {name: 'carStatus'}
	        ]],  
			columnsArray: [[
		          {
		          	header:this.colModelOwner,
		          	width:100, sortable: true,dataIndex: 'owner'
		          },
		          {
		          	header: this.colModelTelephone,
		          	width: 100, sortable: true, dataIndex: 'telephone'
		          },
		          {
		          	header: this.colModelMobile,
		          	width: 100, sortable: true, dataIndex: 'mobile'
		          },		          
		          {
			         header: this.colModelAddress,
			         width: 100, sortable: true, dataIndex: 'address'
			      },
			      {
				    header: this.colModelLicenseNumber,
				    width: 100, sortable: true, dataIndex: 'licenseNumber'
				  },
				  {
			          	header: this.colModelLicenseType,
			          	width: 100, sortable: true, dataIndex: 'licenseType.id',
						editor:combo3,renderer:myRenderer
			      },
				 {
					 header: this.colModelLoadTon,
					 width: 100, sortable: true, dataIndex: 'loadTon'
				  },
		          {
				         header: this.colModelBrandType,
				         width: 100, sortable: true, dataIndex: 'brandType'
				  },
				  {
			          	header: this.colModelFuelRank,
			          	width: 100, sortable: true, dataIndex: 'fuelRank.id',
			          	editor:combo4,renderer:myRenderer
			      }, 				  				  
				  {
			          	header: this.colModelSkillRank,
			          	width: 100, sortable: true, dataIndex: 'skillRank.id',
			          	editor:combo2,renderer:myRenderer
			      }, 			      	
		          {
			          	header: this.colModelEvaluateDate,
			          	width: 100, sortable: true, dataIndex: 'evaluateDate',
			          	renderer : Ext.util.Format.dateRenderer('Y-m-d')
			      },
			      {
			          	header: this.colModelEvaluateCycle,
			          	width: 100, sortable: true, dataIndex: 'evaluateCycle.id',
						editor:combo5,renderer:myRenderer
			      },
		          {
			          	header: this.colModelMaintainDate,
			          	width: 100, sortable: true, dataIndex: 'maintainDate',
			          	renderer : Ext.util.Format.dateRenderer('Y-m-d')
			      },
			      {
			          	header: this.colModelMaintainCycle,
			          	width: 100, sortable: true, dataIndex: 'maintainCycle.id',
						editor:combo6,renderer:myRenderer
			      },
		          {
			          	header: this.colModelMaintainDateEnd,
			          	width: 100, sortable: true, dataIndex: 'maintainDateEnd',
			          	renderer : Ext.util.Format.dateRenderer('Y-m-d')
			      },			      
				  {
			          	header: this.colModelPaiSe,
			          	width: 100, sortable: true, dataIndex: 'paiSe.id',
			          	editor:comboPaiSe,renderer:myRenderer
			      },
				  {
			          	header: this.colModelCarType,
			          	width: 100, sortable: true, dataIndex: 'carType.id',
			          	editor:combo,renderer:myRenderer
//			          	editor:combo,renderer:comboBoxRenderer(combo)
			      },
			      {
					    header: this.colModelCarRemark,
					    width: 100, sortable: true, dataIndex: 'carRemark'
				  },
				  {
					    header: this.colModelYingyunNo,
					    width: 100, sortable: true, dataIndex: 'yingyunNo'
				  },
		          sm
			 ]],
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
	        	 handler:this.deleteCarfile
	         },
	         {
	        	 text:'新增车辆档案', 
	        	 iconCls :'add-icon',
	        	 scope:this, 
	        	 handler:this.addCarfile
	         },
	         {
	        	 text:'修改车辆信息',
	        	 iconCls :'edit-icon',
	        	 scope:this,
	        	 handler:function(){								
            	if(sm.getCount()==1){ 
	        	 	var record = this.getSelectionModel().getSelected();	
	        	 	editCarWindow.show();
				 	Ext.getCmp("carfile2-form").getForm().loadRecord(record);
            	}else{ 
            		Ext.MessageBox.alert('温馨提示', '请您选择一条记录！');
            	}
            	}
	         },{
	        	 text:'车辆过户操作',
	        	 iconCls :'edit-icon',
	        	 scope:this,
	        	 handler:showGHWindowFn
	         }
	         ]],
	         sm:sm,
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
                            
                            if(record.get('expired')==2){                           	
                     			tip.body.dom.innerHTML = "<b>此车辆需要超期处罚！<br/><br>二维日期:"+record.get('maintainDate')+"</br><br>有效期止:"+record.get('maintainDateEnd');
                 			}else if(record.get('expired')==1){                           	
                     			tip.body.dom.innerHTML = "<b>此车辆二级维护已超期。<br/><br>二维日期:"+record.get('maintainDate')+"</br><br>有效期止:"+record.get('maintainDateEnd');
                 			}else{
                     				return false; //停止执行，从而禁止显示Tip
                     				tip.destroy();
                 			}
            			}     
        			}                    
                });
            }
        }
	    }; // eo config object 
		// apply config
		Ext.apply(this, Ext.apply(this.initialConfig, config)); 
		micrite.car.carfileList.SearchPanel.superclass.initComponent.apply(this, arguments);
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
	addCarfile : function() {
			var win = micrite.util.genWindow({
	            id       : 'addCarfileWindow',
                title    : this.newCarfile,
                autoLoad : {url: 'car/carfileDetail.js',scripts:true},
                width    : 710,
                resizable:false,
                height   : 480
            });
	}, //eof addCarfile
	
	deleteCarfile : function() {
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
	deletecheck : function(){ 
	        	 			var obj = Ext.getCmp('egp01');
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
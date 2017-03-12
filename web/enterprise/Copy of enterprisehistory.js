<script type="text/javascript">
Ext.namespace('micrite.enterprise.enterprisehistory');
micrite.enterprise.enterprisehistory.SearchPanel = Ext.extend(micrite.ComplexGrid, {	
	initComponent:function() {
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
	
	var storeCarStatus = new Ext.data.Store({
		id: Ext.id(),    
		autoLoad:true,
		proxy : new Ext.data.HttpProxy({url: 'car/getPartner.action?type=14'}),    
		reader : new Ext.data.JsonReader({    
			id:"value"
		}, RecordDef2),
		remoteSort: true   
	});	   	
	var comboCarStatus = new Ext.form.ComboBox({
		valueField: "value",
		displayField: "name",
		fieldLabel:this.colModelCarStatus,
		store: storeCarStatus,
		triggerAction :'all',
		lazyRender:false 
	});
	
	var sm = new Ext.grid.CheckboxSelectionModel();
	var config = {
			advSearchField : [[
						        {name:'字段',value:'testKind',xtype:'textfield'},
						        {name:'字段',value:'testKind',xtype:'textfield'},
						        {name:'字段',value:'testKind',xtype:'textfield'},
						        {name:'字段',value:'testKind',xtype:'textfield'}							  		
						      ]],	  
	        compSet: [
	             {url:0,reader:0,columns:0,bbarAction:0},
	             {url:1,reader:0,columns:0,bbarAction:0,tbarAction:-1}
	        ],
			searchMenu : [			     
				 this.searchCondition1,
				 '企业历史记录'
//				 this.searchCondition2
			],
			searchFields :[[ {advSearch:true},	             			 						 
			 				'车牌号',
			 				{xtype:'textfield',
			 				 name:'paiHao',
			 				 expression:'like',
			 				 width:120}				               
				               ],[	             			 						 
					               '操作人',
						           {xtype:'combo',
					               name:'check.createrId',
						           selectOnFocus:true,
						           valueField:'id',
						           hiddenName:'check.createrId',
						           displayField:'name',
						           fieldLabel: '操作人',
						           emptyText:'请选择操作人',
						           editable:false,
						           allowBlank:false,
						           forceSelection:true,
						           triggerAction:'all',
						           store:storeCreater,
						           typeAhead: true},
						           '-',
					               '字段',
						           {xtype:'combo',
					               name:'check.testKind',
						           selectOnFocus:true,
						           valueField:'value',
						           hiddenName:'check.testKind',
						           displayField:'name',
						           fieldLabel: '操作',
						           emptyText:'请选择操作',
						           editable:false,
						           allowBlank:false,
						           forceSelection:true,
						           triggerAction:'all',
						           store:storeColumn,
						           typeAhead: true}
				            ]	            
				            ],
	        urls: ['enterprise/findEnterprise.action','history/findHistoryEnterprise.action'],
	        readers : [[
	   	        	 {name: 'id'},
	   			     {name: 'paiHao'},			     
	   			     {name: 'paiSe.id',mapping : 'paiSe.id'},               
	   			     {name: 'id'},
	   		         {name: 'createDate',type : 'date',dateFormat : 'Y-m-dTH:i:s',mapping : 'createDate'},
	   		         {name: 'testKind'},		            
	   			     {name: 'notepad'},
	   			     {name: 'createrId'}			    
	   	        ]],  
	   			columnsArray: [[
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
	   			},
	   			sm
	   			 ]],	   	      
	         tbarActions : [[        	
	         {
	        	 text:this.enterpriseSourcePieChart,
	        	 iconCls :'pie-chart-icon',
	        	 scope:this,
	        	 handler:this.getPieChart
	         }
	         ]],
	         bbarActions:[[]],
	         sm:sm
	    }; // eo config object

		// apply config
		Ext.apply(this, Ext.apply(this.initialConfig, config)); 
		micrite.enterprise.enterprisehistory.SearchPanel.superclass.initComponent.apply(this, arguments);
    }, // eo function initComponent	
	getPieChart : function() {
		var c1 = {id:'enterprisehistory.piechar',
		          title:this.enterpriseSourcePieChart};
        var c2 = {
            url: 'enterprise/getEnterpriseSourcePieChart.action',
            params:this.getAllField()
        };
        this.genChartWindow(c1,c2);
	}//eof getPieChart

}); //eo extend
// 处理多语言
try {enterprisehistoryLocale();} catch (e) {}
Ext.onReady(function() {
	Ext.QuickTips.init();
	var formPanel = new micrite.enterprise.enterprisehistory.SearchPanel();
	if (mainPanel) {
		mainPanel.getActiveTab().add(formPanel);
		mainPanel.getActiveTab().doLayout();
	} else {
		var vp = new Ext.Viewport({
			layout:'fit',
			items:[formPanel]
		});
	}
//	formPanel.getStore().load({params:{start:0,limit:formPanel.pageSize,queryString:'[isPost,3,=]'}});//打开自动显示企业历史数据
});
</script>
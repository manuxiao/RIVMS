<script type="text/javascript">
Ext.namespace('micrite.car.carhistory');
micrite.car.carhistory.SearchPanel = Ext.extend(micrite.ComplexGrid, {	
	initComponent:function() {	

	var pageSize = parseInt(Ext.getDom('pageSize').value,10);			
	 
	var comboCarOptColumn = new Ext.form.ComboBox({
		valueField: "value",
		displayField: "name",
		store: storeCarColumns,
		triggerAction :'all',
		lazyRender:false 
	});
	var comboCarownerOptColumn = new Ext.form.ComboBox({
		valueField: "value",
		displayField: "name",
		store: storeCarownerColumns,
		triggerAction :'all',
		lazyRender:false 
	});
	var comboEnterpriseOptColumn = new Ext.form.ComboBox({
		valueField: "value",
		displayField: "name",
		store: storeEnterpriseColumns,
		triggerAction :'all',
		lazyRender:false 
	});
		 
	var comboCreater = new Ext.form.ComboBox({
		valueField: "id",
		displayField: "name",
		store: storeCreaters,
		triggerAction :'all',
		lazyRender:false 
	});

  //通过checkbox选择或反选时触发选区轮换的一个制定选区模型
	var commonSearchFields=[];
	var erweiSearchFields=[
	{advSearch:false},{xtype:'hidden',name: 'testKind',value:'2',expression:'='},
	'牌号',{xtype:'textfield',name:'paiHao',value:'浙B.',width:80},'-',
	'牌色',{xtype:'combo',
	name:'paiSeId',
	valueField:'id',
	displayField:'name',
	emptyText:'全选',
	editable:true,
	allowBlank:true,
	forceSelection:true,
	triggerAction:'all',
	store:storePaiSe,
	width:60},
	'操作人',{xtype:'combo',
	name:'createrId',
	valueField:'id',
	hiddenName:'createrId',
	displayField:'name',
	emptyText:'全选',
	editable:true,
	allowBlank:true,
	forceSelection:true,
	triggerAction:'all',
	store:storeCreaters,
	width:70},'-',
	'起',{name:'startDate',xtype:'datefield',format:'Y-m-d',value:preDate()},
	'止',{name:'endDate',xtype:'datefield',format:'Y-m-d',value:new Date()}
	];
	var dengjiSearchFields=[
	{advSearch:false},{xtype:'hidden',name: 'testKind',value:'1',expression:'='},
	'牌号',{xtype:'textfield',name:'paiHao',value:'浙B.',width:80},'-',
	'牌色',{xtype:'combo',
	name:'paiSeId',
	valueField:'id',
	displayField:'name',
	emptyText:'全选',
	editable:true,
	allowBlank:true,
	forceSelection:true,
	triggerAction:'all',
	store:storePaiSe,
	width:60},
	'操作人',{xtype:'combo',
	name:'createrId',
	valueField:'id',
	hiddenName:'createrId',
	displayField:'name',
	emptyText:'全选',
	editable:true,
	allowBlank:true,
	forceSelection:true,
	triggerAction:'all',
	store:storeCreaters,
	width:70},'-',
	'起',{name:'startDate',xtype:'datefield',format:'Y-m-d',value:preDate()},
	'止',{name:'endDate',xtype:'datefield',format:'Y-m-d',value:new Date()}
	];
	var carSearchFields=[
	{advSearch:false},
	'牌号',
	{xtype:'textfield',
	name:'paiHao',
	value:'浙B.',
	width:80	                       
	},'-','牌色',
	{xtype:'combo',
	name:'paiSeId',
	valueField:'id',
	displayField:'name',
	emptyText:'全选',
	editable:true,
	allowBlank:true,
	forceSelection:true,
	triggerAction:'all',
	store:storePaiSe,
	width:60 
	},'-','操作',
	{xtype:'combo',
	name:'testKind',
	valueField:'value',
	displayField:'name',
	emptyText:'全选',
	editable:true,
	allowBlank:true,
	forceSelection:true,
	triggerAction:'all',
	store:storeCarColumns,
	width:80
	},'-','操作人',
	{xtype:'combo',
	name:'createrId',
	valueField:'id',
	hiddenName:'createrId',
	displayField:'name',
	emptyText:'全选',
	editable:true,
	allowBlank:true,
	forceSelection:true,
	triggerAction:'all',
	store:storeCreaters,
	width:70
	},'-','起',
	{	
	name:'startDate',
	xtype:'datefield',
	format:'Y-m-d',
	value:preDate()
	},
	'止',
	{
	name:'endDate',
	xtype:'datefield',
	format:'Y-m-d',
	value:new Date()
	}
	];
	var carownerSearchFields=[
	{advSearch:false},
	'车主姓名',
	{xtype:'combo',
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
	store:	storeCarowners,				           
	valueField:'id',
	displayField:'name',
	mode:'remote'
	},'-','操作',
	{xtype:'combo',
	name:'testKind',
	valueField:'value',
	displayField:'name',
	emptyText:'全选',
	editable:true,
	allowBlank:true,
	forceSelection:true,
	triggerAction:'all',
	store:storeCarownerColumns,
	width:80
	},'-','操作人',
	{xtype:'combo',
	name:'createrId',
	valueField:'id',
	hiddenName:'createrId',
	displayField:'name',
	emptyText:'全选',
	editable:true,
	allowBlank:true,
	forceSelection:true,
	triggerAction:'all',
	store:storeCreaters,
	width:70
	},'-','起',
	{	
	name:'startDate',
	xtype:'datefield',
	format:'Y-m-d',
	value:preDate()
	},
	'止',
	{
	name:'endDate',
	xtype:'datefield',
	format:'Y-m-d',
	value:new Date()
	}
	];
	var enterpriseSearchFields=[
	'企业名称',
	{xtype:'combo',
	anchor:'90%',
	loadingText:'加载中……',
	emptyText:'全部',
	name:'enterpriseId',
	hiddenName:'enterpriseId',
	minChars:2,
	triggerAction:'all',
	editable:true,
	allowBlank:true,
	forceSelection:true,
	width:160,
	minChars:2,
	queryParam:'py',
	allQuery:this.value,
	store:	storeEnterprises,				           
	valueField:'id',
	displayField:'name',
	mode:'remote'
	},'-','操作',
	{xtype:'combo',
	name:'testKind',
	valueField:'value',
	displayField:'name',
	emptyText:'全选',
	editable:true,
	allowBlank:true,
	forceSelection:true,
	triggerAction:'all',
	store:storeCarownerColumns,
	width:80
	},'-','操作人',
	{xtype:'combo',
	name:'createrId',
	valueField:'id',
	hiddenName:'createrId',
	displayField:'name',
	emptyText:'全选',
	editable:true,
	allowBlank:true,
	forceSelection:true,
	triggerAction:'all',
	store:storeCreaters,
	width:70
	},'-','起',
	{	
	name:'startDate',
	xtype:'datefield',
	format:'Y-m-d',
	value:preDate()
	},
	'止',
	{
	name:'endDate',
	xtype:'datefield',
	format:'Y-m-d',
	value:new Date()
	}
	];
	var sysparamSearchFields=[{advSearch:true},{xtype:'hidden',name: 'isPost',value:'5',expression:'='}];
	var erweiRenders=[
    	{name: 'id'},
    	{name: 'cheZhu'},
    	{name: 'carId',mapping: 'carId'},
    	{name: 'paiSe.name'},
    	{name: 'paiHao'},
        {name: 'jianTime'},
        {name: 'endTime'},
        {name: 'createDate'},
	    {name: 'notepad'},
	    {name: 'createrId'}			    
	];
	var dengjiRenders=[
    	{name: 'id'},
    	{name: 'cheZhu'},
    	{name: 'carId',mapping: 'carId'},
    	{name: 'paiSe.name'},
    	{name: 'paiHao'},
        {name: 'jianTime'},
        {name: 'endTime'},
        {name: 'createDate'},
        {name: 'cheDengji'},
	    {name: 'notepad'},
	    {name: 'createrId'}			    
	];
	var carRenders=[
    	{name: 'id'},
    	{name: 'cheZhu'},
    	{name: 'carId',mapping: 'carId'},
    	{name: 'paiSe.name'},
    	{name: 'paiHao'},
        {name: 'createDate'},
        {name: 'testKind'},
	    {name: 'notepad'},
	    {name: 'createrId'}			    
	];
	var carownerRenders=[
    	{name: 'id'},
    	{name: 'cheZhu'},
    	{name: 'cheXiu'},
    	{name: 'carownerId',mapping : 'status'}, 
        {name: 'createDate'},
        {name: 'testKind'},
	    {name: 'notepad'},
	    {name: 'createrId'}			    
    ];
    var enterpriseRenders=[
		{name: 'id'},
		{name: 'enterpriseName',mapping :'cheZhu'},
		{name: 'enterpriseId',mapping : 'status'}, 
		{name: 'createDate'},
		{name: 'testKind'},		            
		{name: 'notepad'},
		{name: 'createrId'}
	];
	var sysparamRenders=[
		{name: 'id'},
		{name: 'cheZhu'},
		{name: 'createDate'},
		{name: 'notepad'},
		{name: 'createrId'}
	];
	var erweiColumnsArray=[ 
    	{
			header: '车主',
			width: 100, sortable: true, dataIndex: 'cheZhu'
		},{
			header: '牌号',
			width: 100, sortable: true, dataIndex: 'paiHao'
		},{
			header: '牌色',
			width: 100, sortable: true, dataIndex: 'paiSe.name'
		},{
	        header: '检测日期',
			width: 100, sortable: true, dataIndex: 'jianTime'
	    },{
	        header: '截止日期',
			width: 100, sortable: true, dataIndex: 'endTime'
	    },{
	        header: '备注',
			width: 100, sortable: true, dataIndex: 'notepad'
	    },{									
			header: '来自',
			width: 100, sortable: true, dataIndex: 'status',
			renderer:GRenderCheckSrc
		},{
			header: '操作人',
			width: 100, sortable: true, dataIndex: 'createrId',
			editor:comboCreater,renderer:myRenderer
		},{
			header: '操作日期',
			width: 100, sortable: true, dataIndex: 'createDate'
		}
	];
	var dengjiColumnsArray=[ 
    	{
			header: '车主',
			width: 100, sortable: true, dataIndex: 'cheZhu'
		},{
			header: '牌号',
			width: 100, sortable: true, dataIndex: 'paiHao'
		},{
			header: '牌色',
			width: 100, sortable: true, dataIndex: 'paiSe.name'
		},{									
			header: '等级',
			width: 100, sortable: true, dataIndex: 'cheDengji'
		},{
	        header: '检测日期',
			width: 100, sortable: true, dataIndex: 'jianTime'
	    },{
	        header: '截止日期',
			width: 100, sortable: true, dataIndex: 'endTime'
	    },{
	        header: '备注',
			width: 100, sortable: true, dataIndex: 'notepad'
	    },{									
			header: '来自',
			width: 100, sortable: true, dataIndex: 'status',
			renderer:GRenderCheckSrc
		},{
			header: '操作人',
			width: 100, sortable: true, dataIndex: 'createrId',
			editor:comboCreater,renderer:myRenderer
		},{
			header: '操作日期',
			width: 100, sortable: true, dataIndex: 'createDate'
		}
	];
	var carColumnsArray=[ 
    	{
			header: '车主',
			width: 100, sortable: true, dataIndex: 'cheZhu'
		},{
			header: '牌号',
			width: 100, sortable: true, dataIndex: 'paiHao'
		},{
			header: '牌色',
			width: 100, sortable: true, dataIndex: 'paiSe.name'
		},{									
			header: '操作',
			width: 100, sortable: true, dataIndex: 'testKind',
			editor:comboCarOptColumn,renderer:myRenderer
		},{
	        header: '备注',
			width: 100, sortable: true, dataIndex: 'notepad'
	     },{
			header: '操作人',
			width: 100, sortable: true, dataIndex: 'createrId',
			editor:comboCreater,renderer:myRenderer
		},{
			header: '操作日期',
			width: 100, sortable: true, dataIndex: 'createDate'
		}
	];
	var carownerColumnsArray=[
		{
			header: '车主',
			width: 100, sortable: true, dataIndex: 'cheZhu'
		},
		{
			header: '联系地址',
			width: 100, sortable: true, dataIndex: 'cheXiu'
		},
		{									
			header: '操作',
			width: 100, sortable: true, dataIndex: 'testKind',
			editor:comboCarownerOptColumn,renderer:myRenderer
		},{
	        header: '备注',
			width: 100, sortable: true, dataIndex: 'notepad'
	    },{
			header: '操作人',
			width: 100, sortable: true, dataIndex: 'createrId',
			editor:comboCreater,renderer:myRenderer
		},{
			header: '操作日期',
			width: 100, sortable: true, dataIndex: 'createDate'
		}
	];
	var enterpriseColumnsArray=[
		{
			header: '企业',
			width: 100, sortable: true, dataIndex: 'enterpriseName'
		},{									
			header: '操作',
			width: 100, sortable: true, dataIndex: 'testKind',
			editor:comboEnterpriseOptColumn,renderer:myRenderer
		},{
		    header: '内容',
			width: 100, sortable: true, dataIndex: 'notepad'
		},{
			header: '操作人',
			width: 100, sortable: true, dataIndex: 'createrId',
			editor:comboCreater,renderer:myRenderer
		},{
			header: '操作日期',
			width: 100, sortable: true, dataIndex: 'createDate'
		}
	];
	var sysparamColumnsArray=[
		{
			header: '参数名',
			width: 100, sortable: true, dataIndex: 'cheZhu'
		},{
		    header: '内容变化',
			width: 100, sortable: true, dataIndex: 'notepad'
		},{
			header: '操作人',
			width: 100, sortable: true, dataIndex: 'createrId',
			editor:comboCreater,renderer:myRenderer
		},{
			header: '操作日期',
			width: 100, sortable: true, dataIndex: 'createDate'
		}
	];
	var sm = new Ext.grid.CheckboxSelectionModel();
	var config = {
			advSearchField : [[	
//						          {name:'车牌号',value:'paiHao',xtype:'textfield'},
//						          {name:'时间',value:'createDate',xtype:'uxspinnerdate'}
			]],	  
	        compSet: [
	             {url:0,reader:0,columns:0,bbarAction:0},
	             {url:1,reader:1,columns:1,bbarAction:0},
	             {url:2,reader:2,columns:2,bbarAction:0},
	             {url:3,reader:3,columns:3,bbarAction:0},
	             {url:4,reader:4,columns:4,bbarAction:0},
	             {url:5,reader:5,columns:5,bbarAction:0}
	        ],
			searchMenu : ['二级维护日志','等级评定日志','车辆操作日志','车主操作日志','企业操作日志','系统参数日志'],
			searchFields :[erweiSearchFields,dengjiSearchFields,carSearchFields,carownerSearchFields,enterpriseSearchFields,sysparamSearchFields],
	        urls: ['history/findHistoryCheck.action','history/findHistoryCheck.action','history/findHistoryCarfile.action','history/findHistoryCarowner.action',
	        'history/findHistoryEnterprise.action','check/findCheck.action'],
	        readers : [erweiRenders,dengjiRenders,carRenders,carownerRenders,enterpriseRenders,sysparamRenders],  
			columnsArray: [erweiColumnsArray,dengjiColumnsArray,carColumnsArray,carownerColumnsArray,enterpriseColumnsArray,sysparamColumnsArray],
	         bbarActions:[[]],
	         sm:sm
	    }; // eo config object 
		// apply config
		Ext.apply(this, Ext.apply(this.initialConfig, config)); 
		micrite.car.carhistory.SearchPanel.superclass.initComponent.apply(this, arguments);
    }, // eo function initComponent	
   
	getPieChart : function() {
		var c1 = {id:'carhistory.piechar',
		          title:this.carTypePieChart};
        var c2 = {
            url: 'car/getCarTypePieChart.action',
            params:this.getAllField()
        };
        this.genChartWindow(c1,c2);
	} //eof getPieChart

}); //eo extend

// 处理多语言
try {carhistoryLocale();} catch (e) {}		
Ext.onReady(function() {
	Ext.QuickTips.init();	
	var formPanel = new micrite.car.carhistory.SearchPanel();	
	if (mainPanel) {
		mainPanel.getActiveTab().add(formPanel);
		mainPanel.getActiveTab().doLayout();
	} else {
		var vp = new Ext.Viewport({
			layout:'fit',
			items:[formPanel]
		});
		}
//	formPanel.getStore().load({params:{start:0,limit:formPanel.pageSize,queryString:'[isPost,2,=]'}})//打开自动显示车辆历史数据 
});
</script>
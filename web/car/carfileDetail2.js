<script type="text/javascript">
Ext.ns('micrite.car.carfileDetail');
micrite.car.carfileDetail.Panel = function(){
	var pageSize = parseInt(Ext.getDom('pageSize').value,10);
	var comboCarOptColumn = new Ext.form.ComboBox({
		valueField: "value",
		displayField: "name",
		store: storeCarColumns,
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
	
	var historyCM = new Ext.grid.ColumnModel({
		columns:[	
			new Ext.grid.RowNumberer(),{
			header:'记录序号',dataIndex:'id',sortable:true,hidden:true
        	},  
  			{									
				header: '字段',
				width: 100, sortable: true, dataIndex: 'testKind',
				editor:comboCarOptColumn,renderer:myRenderer
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
//		alert(storeCarColumns);    
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
    Ext.form.Field.prototype.msgTarget = 'side';    
    micrite.car.carfileDetail.Panel.superclass.constructor.call(this, {
        id: 'carfileDetail2-form',     
        frame: true,
        plain:true,
        labelAlign: 'right',
        style:'padding:1px',
        resizable:false,
        items: [
           		{ 
           		xtype: 'fieldset',
           		padding:'1px',
           		title:this.carfileDetailText,       		
            	layout : 'column',        				
                collapsible: true,
                defaults: {width: 260},
                autoHeight: true,
                fileUpload : true,  
        items: [
        {
            labelWidth: 90,
            layout:'form',
            collapsible: true,
            defaults: {width: 180},
            defaultType: 'textfield',
        	height:265,
            columnWidth : .5,
			baseCls : 'x-plain',
			
            items: [
            {
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
            defaults: {width: 150},    // Default config options for child items
            defaultType: 'textfield',
            height:265,           
        	columnWidth : .5,
			baseCls : 'x-plain',
			fileUpload : true,
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
				readOnly : true,
				renderer:function(v){
					var idx = storeCarStatus.find('value', value);
				    var rec = storeCarStatus.getAt(idx);
				    if (rec == null) {return value;}
				    else {
				        return rec.get('name');
				    }
				}
			},{itemId: 'id',hidden:true}
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
//			{title : this.carRemarkText,layout : 'fit',items : {xtype : 'textarea',itemId:'carRemark',name : 'carfile.carRemark',maxLength : 300}}
			{title : '车辆备注',layout : 'fit',items : {xtype : 'textarea',name : 'carRemark',maxLength : 300,readOnly : true}},
			{title : '二级维护记录',layout : 'fit',items :[checkWin],listeners:{activate:checkTabPanelActivateFn}},
			{title : '等级评定记录',layout : 'fit',items :[checkEvalWin],listeners:{activate:checkEvalTabPanelActivateFn}},
			{title : '车辆修改记录',layout : 'fit',items :[carEditHistoryGridPanel],listeners:{activate:modifyTabPanelActivateFn}},
			{title : '车辆异动记录',layout : 'fit',items :[carPunishGridPanel],listeners:{activate:punishTabPanelActivateFn}}
			]
    	}],	
            buttonAlign:'center',
            buttons: [{
                text: '修改',
				scope:this,
				handler:function() {
					//打开编辑界面					
//					carInfoWindow.hide();
					Ext.WindowMgr.getActive().close();
		        	GAddCarfileFn();
				}
        	},{
                text: mbLocale.closeButton,
                scope:this,
                handler: function(){
                	window.carId=null;
                	Ext.WindowMgr.getActive().close();
             	}
         }
    ]
    });    
};
Ext.extend(micrite.car.carfileDetail.Panel, Ext.FormPanel, {});
try{ carfileDetail2Locale(); } catch(e){alert(e);}

var ds_car = new Ext.data.Store({
	url : 'car/getCar.action',
	reader : new Ext.data.JsonReader(
		{id:'id'},//root : 'carfile',
	   GCarFields)
});

function init(carId){
ds_car.load({
		    	params:{id:carId},
		    	callback:function(r,options,sucess){
		    		if(sucess){
		    			var record = ds_car.getAt(0);
						if(record!=null){
							Ext.getCmp('carfileDetail2-form').getForm().loadRecord(record);
						}else{
							alert('未找到该记录');
							var w = Ext.WindowMgr.getActive();
							if(w!=null)
								w.close();
						}
		    		}
			    }
		    });
}

Ext.onReady(function(){
     Ext.QuickTips.init();
     Ext.form.Field.prototype.msgTarget = 'side';
     Ext.WindowMgr.getActive().add(new micrite.car.carfileDetail.Panel());
     Ext.WindowMgr.getActive().doLayout();
     if(window.carId){
     	init(window.carId);
     }
});
</script>
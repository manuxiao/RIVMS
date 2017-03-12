<script type="text/javascript">
Ext.namespace('micrite.car.expiredOpt');
micrite.car.expiredOpt.SearchPanel = Ext.extend(micrite.ComplexGrid, {	
	initComponent:function() {
	// 车辆类型下拉框
	var RecordDef = Ext.data.Record.create([{name: 'id'},{name: 'name'}]);
	var RecordDef2 = Ext.data.Record.create([{name: 'value'},{name: 'name'}]);
//--------------车辆类型--------------------	
	var store = new Ext.data.Store({
		id: Ext.id(),    autoLoad:true,
		// 设定读取的地址 
		proxy : new Ext.data.HttpProxy({url: 'dic/getPartner.action?type=1'}), 
		// 设定读取的格式
		reader : new Ext.data.JsonReader({id:"id"}, RecordDef),
		remoteSort: true   
	});
	var combo = new Ext.form.ComboBox({valueField: "id",displayField: "name",store: store,triggerAction :'all',lazyRender:false });
// -------通用renderer----------
	myRenderer = function(value){
		var idx = this.editor.getStore().find(this.editor.valueField, value);
	    var rec = this.editor.getStore().getAt(idx);
	    if (rec == null) {return value;}
	    else {return rec.get(this.editor.displayField);}
	};
	var pageSize = parseInt(Ext.getDom('pageSize').value,10);
// --------skillRank下拉框-----------------------
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
// --------3下拉框-----------------------
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
	// --------4下拉框-----------------------
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
	// --------5下拉框-----------------------
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
	// --------6下拉框-----------------------
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
	// --------车牌颜色下拉框-----------------------
	var store7 = new Ext.data.Store({
		id: Ext.id(),    
		autoLoad:true,
		proxy : new Ext.data.HttpProxy({url: 'dic/getPartner.action?type=7'}),    
		reader : new Ext.data.JsonReader({    
			id:"id"
		}, RecordDef),
		remoteSort: true   
	});	   	
	var combo7 = new Ext.form.ComboBox({
		valueField: "id",
		displayField: "name",
		store: store7,
		triggerAction :'all',
		lazyRender:false 
	});	

    // --------加注车辆状况 ----------------------
	var expiredBackWindow = new Ext.Window({
	   	modal:true,
        title:this.expiredOpt,
		closeAction:'hide',
		padding:'5px',
		width : 400,
//		height:400,
		items : [{
        id: 'expiredOpt-form',
		labelAlign : 'right',
		labelWidth : 80,
		xtype:'form',
		bodyStyle : 'padding:1px',
		baseCls:'x-plain',	
        items: [
        {
            xtype: 'fieldset',           
            title:this.expiredOpt,
            layout:'form',
            collapsible: true,           
            labelWidth: 100,
            defaults: {width: 230},    // Default config options for child item										// items
            defaultType: 'textfield',
//            height:350,
            items: [{
            	xtype:'hidden',
                name: 'id',
                allowBlank:false
            },{
            	xtype:'textarea',
                fieldLabel: '备注/回单',
                name: 'notepad',
                allowBlank:true
            },{
                fieldLabel: this.colModelLicenseNumber,
                name: 'licenseNumber',
                blankText : '',
                allowBlank:false
            }, 	
              new Ext.form.ComboBox({
                name:'paiSe.id',
                selectOnFocus:true,
                valueField:'id',
                hiddenName:'paiSe.id',
                displayField:'name',
                fieldLabel:this.colModelPaiSe,
                emptyText:'',
                editable:false,
                allowBlank:false,
                forceSelection:true,
                triggerAction:'all',
                store:store7,
                typeAhead: true               
            })]               
        }],
            buttonAlign:'center',
            buttons: [{
                text: mbLocale.submitButton,
                handler: function(){
                	if(!Ext.getCmp("expiredOpt-form").getForm().isValid())return;
                    Ext.getCmp("expiredOpt-form").getForm().submit({
                        url: 'car/ridofPunish.action',
                        method: 'POST',
                        disabled:true,
                        waitMsg: mbLocale.waitingMsg,
                        success: function(form, action) {
                            obj = Ext.util.JSON.decode(action.response.responseText);
                            showMsg('success', obj.message);                                
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
            text: mbLocale.cancelButton,
            scope:this,
            handler: function(){expiredBackWindow.hide();}
        }]
        }]
    });    

    
    
  // 通过checkbox选择或反选时触发选区轮换的一个制定选区模型
	var mainGridPanelSM = new Ext.grid.CheckboxSelectionModel();
	this.addListener('rowdblclick', showExpiredBackWindowFn);   
	
	function showExpiredBackWindowFn(){						
	     if(mainGridPanelSM.getCount()==1){
	     	var theForm=Ext.getCmp("expiredOpt-form").getForm();
			//var carId = this.selModel.selections.keys;
			var record = this.getSelectionModel().getSelected();	
    	 	expiredBackWindow.show();
		 	theForm.loadRecord(record);
		 	theForm.findField('notepad').setValue('');
		 }
	}
				
	var config = {
			/*
			 * keys: { key: [13], fn: this.startSearch, scope: this },
			 */
		    // view:new Ext.ux.GridView({forceFit:true,getRowClass:rowClass3}),
			view:new Ext.ux.GridViewCar({forceFit:true}),
			advSearchField : [[		 
						          {name:this.colModelLicenseNumber,value:'licenseNumber',xtype:'textfield'},
						          {name:'营运证号',value:'yingyunNo',xtype:'textfield'},
						          {name:'车牌所属业户',value:'owner',xtype:'textfield'},
						          {name:'业户拼音',value:'py',xtype:'textfield'}						          
						      ]],	  
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
	              keys: {
			        key: [13],
			        fn: function(){alert(12);}, 
			        scope: this
			      },listeners: {   
					'onclick': function(e) { alert(25);} 
				  },
	              width:120}
	            ]       
	            ],
	        urls: ['car/findCarfile.action'],
	        readers : [[
	        	 {name: 'id'},
			     {name: 'owner'},
			     {name: 'telephone'},			     
			     {name: 'address'},
			     {name: 'licenseNumber'},			     
			     {name: 'licenseType.id',mapping : 'licenseType.id'},
			     {name: 'loadTon'},
			     {name: 'brandType'},			     
			     {name: 'fuelRank.id',mapping : 'fuelRank.id'},
			     {name: 'skillRank.id',mapping : 'skillRank.id'},		     
//			     {name: 'evaluateDate',type : 'date',dateFormat : 'Y-m-d',mapping : 'evaluateDate'},
			     {name: 'evaluateDate'},
			     {name: 'evaluateCycle.id',mapping : 'evaluateCycle.id'},
//			     {name: 'maintainDate',type : 'date',dateFormat : 'Y-m-d',mapping : 'maintainDate'},
			     {name: 'maintainDate'},			     
//			     {name: 'maintainDateEnd',type : 'date',dateFormat : 'Y-m-d',mapping : 'maintainDateEnd'},
			     {name: 'maintainDateEnd'},
			     {name: 'maintainCycle.id',mapping : 'maintainCycle.id'},
			     {name: 'yingyunNo'},
			     {name: 'paiSe.id',mapping : 'paiSe.id'},               
			     {name: 'carType.id',mapping : 'carType.id'},
			     {name: 'carRemark'},
			     {name: 'expired'},
			     {name: 'carStatus'}
	        ]],  
			columnsArray: [[
		          mainGridPanelSM,
		          {
		          	header:this.colModelOwner,
		          	width:100, sortable: true,dataIndex: 'owner'
		          },
		          {
		          	header: this.colModelMobile,
		          	width: 100, sortable: true, dataIndex: 'telephone'
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
			          	editor:combo7,renderer:myRenderer
			      },
				  {
			          	header: this.colModelCarType,
			          	width: 100, sortable: true, dataIndex: 'carType.id',
			          	editor:combo,renderer:myRenderer
			      },
			      {
					    header: this.colModelCarRemark,
					    width: 100, sortable: true, dataIndex: 'carRemark'
				  },
				  {
					    header: this.colModelYingyunNo,
					    width: 100, sortable: true, dataIndex: 'yingyunNo'
				  }
			 ]],
	         bbarActions:[[
	         {
				text:this.expiredOpt,
				iconCls :'edit-icon',
				scope:this,
				handler:showExpiredBackWindowFn
			}
	         ]],
	         sm:mainGridPanelSM,
	      listeners: {
           render: GCarRowRenderFn
        }
	    }; // eo config object
		// apply config
		Ext.apply(this, Ext.apply(this.initialConfig, config)); 
		micrite.car.expiredOpt.SearchPanel.superclass.initComponent.apply(this, arguments);
    } // eo function initComponent
    
}); // eo extend

try {expiredOptLocale();} catch (e) {}
Ext.onReady(function() {
	Ext.QuickTips.init();	
	var formPanel = new micrite.car.expiredOpt.SearchPanel();	
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
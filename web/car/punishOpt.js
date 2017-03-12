<script type="text/javascript">
Ext.namespace('micrite.car.punishOpt');
micrite.car.punishOpt.SearchPanel = Ext.extend(micrite.ComplexGrid, {	
	initComponent:function() {
	var expiredBackWindow = new Ext.Window({
	   	modal:true,
        title:this.expiredOpt,
		closeAction:'hide',
		padding:'5px',
		width : 400,
//		height:400,
		items : [{
        id: 'punishOpt-form',
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
                allowBlank:false
            },{
                fieldLabel: this.colModelLicenseNumber,
                name: 'licenseNumber',
                readOnly : true
            },{
                fieldLabel: this.colModelPaiSe,
                name: 'paiSe.name',
                readOnly : true
            }]               
        }],
            buttonAlign:'center',
            buttons: [{
                text: mbLocale.submitButton,
                handler: function(){
                	if(!Ext.getCmp("punishOpt-form").getForm().isValid())return;
                    Ext.getCmp("punishOpt-form").getForm().submit({
                        url: 'car/ridofPunish.action',
                        method: 'POST',
                        disabled:true,
                        waitMsg: mbLocale.waitingMsg,
                        success: function(form, action) {
                            obj = Ext.util.JSON.decode(action.response.responseText);
                            showMsg('success', obj.message);
                            expiredBackWindow.hide();
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
	     	var record = this.getSelectionModel().getSelected();
	     	
	     	if(GExpiredApproval(record))return;
	     	
	     	var theForm=Ext.getCmp("punishOpt-form").getForm();
				
    	 	expiredBackWindow.show();
		 	theForm.loadRecord(record);
		 	theForm.findField('notepad').setValue('');
		 }else{
		 	GOnlyOneSelectedFn(mainGridPanelSM.getCount());
		 }
	}
				
	var config = {
			/*
			 * keys: { key: [13], fn: this.startSearch, scope: this },
			 */
		    // view:new Ext.ux.GridView({forceFit:true,getRowClass:rowClass3}),
			view:new Ext.ux.GridViewCar({forceFit:false}),
			advSearchField : [[		 
						          {name:this.colModelLicenseNumber,value:'licenseNumber',xtype:'textfield'},
						          {name:'营运证号',value:'yingyunNo',xtype:'textfield'},
						          {name:'车牌所属业户',value:'owner',xtype:'textfield'},
						          {name:'业户拼音',value:'py',xtype:'textfield'}						          
						      ]],	  
	        compSet: [
	             {url:0,reader:0,columns:0,bbarAction:0},
	             {url:1,reader:0,columns:0,bbarAction:0,tbarAction:-1}
	        ],
			searchMenu : [
				 '车牌号码'
			],
			searchFields :[[
				 {advSearch:true},
				 {
            		xtype:'hidden',
                	name: 'carStatus',
                	expression:'=',
                	value:'0'
            	 },
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
	            ],
	            []         
	            ],
	        urls: ['car/findCarfile.action','car/findCarfileBySkill.action'],
	        readers : [GCarFields],  
			columnsArray: [GCarColumns],
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
		micrite.car.punishOpt.SearchPanel.superclass.initComponent.apply(this, arguments);
    } // eo function initComponent
    
}); // eo extend

try {punishOptLocale();} catch (e) {}
Ext.onReady(function() {
	Ext.QuickTips.init();	
	var formPanel = new micrite.car.punishOpt.SearchPanel();	
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
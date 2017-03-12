<script type="text/javascript">
Ext.ns('micrite.check.checkDetail');
micrite.check.checkDetail.FormPanel = function() {  
		//--------7下拉框-----------------------	
	var RecordDef = Ext.data.Record.create([{name: 'id'},{name: 'name'},{name: 'type'}]);
		var store7 = new Ext.data.Store({
		id: Ext.id(),    
		autoLoad:true,
		//设定读取的地址
		proxy : new Ext.data.HttpProxy({url: 'dic/getPartner.action?type=7'}),    
		//设定读取的格式    
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
	
	
    Ext.form.Field.prototype.msgTarget = 'side';   
    micrite.check.checkDetail.FormPanel.superclass.constructor.call(this, {
        id: 'checkDetail-form',
        frame: true,
        plain:true,
        labelAlign: 'left',
        style:'padding:1px',
        items: [
        {
            xtype: 'fieldset',
            labelWidth: 110,
            title:this.checkDetailText,
            layout:'form',
            collapsible: true,
            defaults: {width: 150},    // Default config options for child items
            defaultType: 'textfield',
            autoHeight: true,
            items: [{                
            	xtype:'datefield',
                fieldLabel: this.jianTimeText,
                name: 'check.jianTime',
                allowBlank:false,                
                value:new Date(),
                format:'Y-m-d'         
            },
            {
                fieldLabel: this.testNoText,
                name: 'check.testNo',
                allowBlank:false
            }, 
            {
                fieldLabel: this.paiHaoText,
                name: 'check.paiHao',
                allowBlank:false
            },
            {
                fieldLabel: this.heGeText,
                name: 'check.heGe',
                allowBlank:false
            },
             new Ext.form.ComboBox({//车牌颜色
		                  name:'check.paiSe',
		                  selectOnFocus:true,
		                  valueField:'id',
		                  hiddenName:'check.paiSe.id',
		                  displayField:'name',
		                  fieldLabel:'车牌颜色',
		                  emptyText:'请选择车牌颜色',
		                  editable:false,
		                  forceSelection:true,
		                  triggerAction:'all',
		                  store:store7,
		                  typeAhead: true               
		            })
           ]
               
        },{
            buttonAlign:'center',
            buttons: [{
                text: '保存',
                handler: function(){
                	if(!Ext.getCmp("checkDetail-form").getForm().isValid())return;
                    Ext.getCmp("checkDetail-form").getForm().submit({
                        url: 'check/saveCheck.action',
                        method: 'POST',
                        disabled:true,
                        waitMsg: mbLocale.waitingMsg,
                        success: function(form, action) {
                                obj = Ext.util.JSON.decode(action.response.responseText);
                                showMsg('success', obj.message);                    
                        },
                        failure: function(form, action) {
                            obj = Ext.util.JSON.decode(action.response.responseText);
                            showMsg('failure', obj.message);
                        }
                    });
                }                    
            },
			{
        		text : '重置',
        		handler : function() { 
        		Ext.getCmp("checkDetail-form").getForm().reset();//重置form
        		}
        	}            
            ]
        }]
    });    
};

Ext.extend(micrite.check.checkDetail.FormPanel, Ext.FormPanel, { 
});
try{ checkDetailLocale(); } catch(e){}

Ext.onReady(function(){
    Ext.QuickTips.init();
     Ext.form.Field.prototype.msgTarget = 'side';
     Ext.WindowMgr.getActive().add(new micrite.check.checkDetail.FormPanel());
     Ext.WindowMgr.getActive().doLayout();
});
</script>

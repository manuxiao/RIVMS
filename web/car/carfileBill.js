<script type="text/javascript">
Ext.ns('micrite.car.carfileBill');
micrite.car.carfileBill.FormPanel = function(){
		var RecordDef = Ext.data.Record.create([    
            {name: 'id'},{name: 'name'},{name: 'type'}
    ]);    
     var store7 = new Ext.data.Store({ //读取车牌颜色   
        autoLoad:true,
        //设定读取的地址
        proxy : new Ext.data.HttpProxy(Ext.apply({
            url: 'dic/getPartner.action?type=7'
        },micrite.util.proxyLoad())),        
        //设定读取的格式    
        reader: new Ext.data.JsonReader({
            id:"id"            
        }, RecordDef),          
        remoteSort: true
    });
    Ext.form.Field.prototype.msgTarget = 'side';    
    micrite.car.carfileBill.FormPanel.superclass.constructor.call(this, {
        id: 'carfileBill-form',
        frame: true,
        plain:true,
        labelAlign: 'left',
        style:'padding:1px',
        store:'store1',
        items: [
        {
            xtype: 'fieldset',
            labelWidth: 100,
            title:'处罚回单',
            layout:'form',
            collapsible: true,
            defaults: {width: 130},    // Default config options for child items
            defaultType: 'textfield',
            autoHeight: true,
            items: [{
                fieldLabel: '备注/回单',
                name: 'check.notepad',
                allowBlank:false
            }
            , {
                fieldLabel: '车牌号码',
                name: 'licenseNumber',
                blankText : '请输入车牌号码',
                allowBlank:false
            }, 	
              new Ext.form.ComboBox({//车牌颜色
                name:'paiSe.id',
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
                store:store7,
                typeAhead: true               
            })
            ]
               
        },{
            buttonAlign:'center',
            buttons: [{
                text: mbLocale.submitButton,
                //提交按钮 之前校验不通过就不提交到后台
                handler: function(){
                	if(!Ext.getCmp("carfileBill-form").getForm().isValid())return;
                    Ext.getCmp("carfileBill-form").getForm().submit({
                        url: 'check/saveCheck.action',
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
        		text : '重置',
        		handler : function() { 
        		Ext.getCmp("carfileBill-form").getForm().reset();//重置form
        		
        		}
        	}            	
            ]
        }]
    }); 
};
Ext.extend(micrite.car.carfileBill.FormPanel, Ext.FormPanel, { 	            
});
try{ carfileBillLocale(); } catch(e){alert(e);}

Ext.onReady(function(){
     Ext.QuickTips.init();
     Ext.form.Field.prototype.msgTarget = 'side';
     Ext.WindowMgr.getActive().add(new micrite.car.carfileBill.FormPanel());
     Ext.WindowMgr.getActive().doLayout();
});
</script>
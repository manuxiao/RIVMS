<script type="text/javascript">
Ext.ns('micrite.crm.carownerDetail');
micrite.crm.carownerDetail.FormPanel = function() {
    var RecordDef = Ext.data.Record.create([    
            {name: 'id'},{name: 'name'}                   
    ]); 
    
    Ext.form.Field.prototype.msgTarget = 'side';
    function onOwnerLicenseBlur(){
    	var ownerLicense = Ext.getCmp("ownerLicense").getValue();
        if(ownerLicense=='')return;
		Ext.Ajax.request({   
			url:'crm/getCarownerByLicense.action?carowner.license='+ownerLicense,
			success:function(response){
				obj = Ext.util.JSON.decode(response.responseText);
				if(!obj.success){
                	
                	return false;
				}else{
					alert(obj.owner.license+'已经存在，业户名为'+obj.owner.name);
//					Ext.getCmp("carownerName").setValue(obj.owner.name);
				}
			},
			failure: function(response) {
				showMsgSilently('failure', response.responseText);
			}
		}); 
    }
    micrite.crm.carownerDetail.FormPanel.superclass.constructor.call(this, {
        id: 'carownerDetail-form',
        frame: true,
        plain:true,
        labelAlign: 'left',
        style:'padding:1px',
        items: [
        {
            xtype: 'fieldset',
            labelWidth: 150,
            title:this.carownerDetailText,
            layout:'form',
            collapsible: true,
            defaults: {width: 170},    // Default config options for child items
            defaultType: 'textfield',
            autoHeight: true,
            items: [{
                fieldLabel: this.licenseText,
                id:'ownerLicense',
                name: 'carowner.license',
                allowBlank:false,
                listeners:{
                 'blur': onOwnerLicenseBlur  
                }
            },{
            	id:'carownerName',
                fieldLabel: this.nameText,
                name: 'carowner.name',
                allowBlank:false
            },{
                fieldLabel: this.telephoneText,
                name: 'carowner.telephone',
                vtype:'telephone',
                allowBlank:false
            },                     
            {
                fieldLabel: this.mobileText,
                name: 'carowner.mobile',
                vtype:'mobile',
                allowBlank:true
            },                     
            {
                fieldLabel: this.addressText,
                name: 'carowner.address',
                allowBlank:false
            }]
               
        },{
            buttonAlign:'center',
            buttons: [{
                text: mbLocale.submitButton,
//                scope:this,
                handler: function(){
                		if(!Ext.getCmp("carownerDetail-form").getForm().isValid())return;
			            Ext.getCmp("carownerDetail-form").getForm().submit({
                        url: 'crm/saveCarowner.action',
                        method: 'POST',
                        disabled:true,
                        waitMsg: mbLocale.waitingMsg,
                        success: function(form, action) {
                        	obj = Ext.util.JSON.decode(action.response.responseText);
                        	var id=obj.id;
                            var ownerName = Ext.getCmp("carownerName").getValue();
                            var ownerLicense = Ext.getCmp("ownerLicense").getValue();
                            if(window.ownerIdCmp){
                            	window.ownerIdCmp.setValue(id);
                            	window.ownerIdCmp=null;
                            }
                            if(window.ownerNameCmp){
                            	window.ownerNameCmp.setValue(ownerName);
                            	window.ownerNameCmp=null;
                            }
                            if(window.ownerLicenseCmp){
                            	window.ownerLicenseCmp.setValue(ownerLicense);
                            	window.ownerLicenseCmp=null;
                            }
                            
                            
                            showMsg('success', obj.message);                           
                            Ext.WindowMgr.getActive().close();                            
                        },
                        failure: function(form, action) {
                        	try{
                            obj = Ext.util.JSON.decode(action.response.responseText);
                            showMsg('failure', obj.message);
                        	}catch(e){alert(e);}
                        }
                    });
                }                    
            },{
                text: mbLocale.closeButton,
                scope:this,
                handler: function(){
                    Ext.WindowMgr.getActive().close();
                }
            }]
        }]
    });
    
};

Ext.extend(micrite.crm.carownerDetail.FormPanel, Ext.FormPanel, {
    carownerDetailText:'Carowner Detail',
    idText:'ID',
    nameText:'Name',
    mobileText:'Mobile',
    sourceText:'Source',
    comboEmptyText:'Select a source...'
    
});
try{ carownerDetailLocale(); } catch(e){alert(e);}

Ext.onReady(function(){

     Ext.QuickTips.init();
     Ext.form.Field.prototype.msgTarget = 'side';
     Ext.WindowMgr.getActive().add(new micrite.crm.carownerDetail.FormPanel());
     Ext.WindowMgr.getActive().doLayout();
        

});
</script>

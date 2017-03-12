<script type="text/javascript">
Ext.ns('micrite.dic.dicDetail');
micrite.dic.dicDetail.FormPanel = function() {
    var RecordDef = Ext.data.Record.create([    
            {name: 'id'},{name: 'name'}                   
    ]); 
    
    Ext.form.Field.prototype.msgTarget = 'side';
    
    micrite.dic.dicDetail.FormPanel.superclass.constructor.call(this, {
        id: 'dicDetail-form',
        frame: true,
        plain:true,
        labelAlign: 'left',
        style:'padding:1px',
        items: [
        {
            xtype: 'fieldset',
            labelWidth: 150,
            title:this.dicDetailText,
            layout:'form',
            collapsible: true,
            defaults: {width: 170},    // Default config options for child items
            defaultType: 'textfield',
            autoHeight: true,
            items: [{
                fieldLabel: this.nameText,
                name: 'dic.name',
                allowBlank:false
            },{
                fieldLabel: this.telephoneText,
                name: 'dic.telephone',
                vtype:'telephone',
                allowBlank:false
            },                     
            {
                fieldLabel: this.mobileText,
                name: 'dic.mobile',
                vtype:'mobile',
                allowBlank:true
            },                     
            {
                fieldLabel: this.addressText,
                name: 'dic.address',
                allowBlank:false
            }]
               
        },{
            buttonAlign:'center',
            buttons: [{
                text: mbLocale.submitButton,
                handler: function(){
                		if(!Ext.getCmp("dicDetail-form").getForm().isValid())return;
			            Ext.getCmp("dicDetail-form").getForm().submit({
                        url: 'dic/saveCarowner.action',
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

Ext.extend(micrite.dic.dicDetail.FormPanel, Ext.FormPanel, {
    dicDetailText:'Carowner Detail',
    idText:'ID',
    nameText:'Name',
    mobileText:'Mobile',
    sourceText:'Source',
    comboEmptyText:'Select a source...'
    
});
try{ dicDetailLocale(); } catch(e){}

Ext.onReady(function(){

     Ext.QuickTips.init();
     Ext.form.Field.prototype.msgTarget = 'side';
     Ext.WindowMgr.getActive().add(new micrite.dic.dicDetail.FormPanel());
     Ext.WindowMgr.getActive().doLayout();
        

});
</script>

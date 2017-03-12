<script type="text/javascript">
Ext.ns('micrite.security.sysParam');
micrite.security.sysParam.FormPanel =  Ext.extend(Ext.form.FormPanel, {
	id:'micrite.security.sysParam.FormPanel',
	initComponent:function() {
	    var config = {
	    	labelWidth: 150,
	    	frame : true,
	    	monitorValid:true,
	    	style:'padding:1px',
	    	items: [{
	        	xtype:'fieldset',
	        	title: this.sysParamsFieldSet,
	        	collapsible: true,
		        autoHeight:true,
		        defaults: {width: 210},
		        defaultType: 'textfield',
		        items :[{
		                fieldLabel: this.erweiExpirePermissionDays,
		                name: 'days',
		                id:'days',
		                allowBlank:false
		            }
		        ]
		    },{
				buttonAlign:'center',
				buttons: [{
			        text: mbLocale.submitButton,
			        scope:this,
			        formBind:true,
			        handler:function() {
			        	if(!this.getForm().isValid())return;
				    	this.getForm().submit({
				            url: 'dic/ewEPDays.action',
				            method: 'POST',
				            waitMsg: mbLocale.waitingMsg,
				            success: function(form, action) {
				                showMsg('success', action.result.message);	 
				            },
				            failure: function(form, action) {
				                showMsg('failure', action.response.message);
				            }
				    	});
					} 
			    }]
		    }]
	    	}; // eof config object
		    // apply config
		    Ext.apply(this, Ext.apply(this.initialConfig, config));
		    // call parent
		    micrite.security.sysParam.FormPanel.superclass.initComponent.apply(this, arguments);
	
		},// eo funtion initComponent
		onRender:function(){
		    micrite.security.sysParam.FormPanel.superclass.onRender.apply(this, arguments);
			this.form.load({
		        url: 'dic/ewEPDays.action',
		        success:function(f,a){
		        	Ext.getCmp('days').setValue(a.result.data);
		        },
		        failure: function(f, a) {
		        	showMsg('failure', a.response.message);
	            }
		    });
		}

});

try{ sysParamLocale(); } catch(e){}

Ext.onReady(function() {
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';
    
    var formPanel = new micrite.security.sysParam.FormPanel();
    if (mainPanel) {
        mainPanel.getActiveTab().add(formPanel);
        mainPanel.getActiveTab().doLayout();
    } else {
        var vp = new Ext.Viewport({layout:'fit', items:[formPanel]});
    }
});

</script>
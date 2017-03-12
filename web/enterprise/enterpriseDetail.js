<script type="text/javascript">
Ext.ns('micrite.enterprise.enterpriseDetail');
micrite.enterprise.enterpriseDetail.FormPanel = function() {

function checkEnterpriseLicenseExist(cmp){
	if(cmp.getValue()=='')return;
	Ext.Ajax.request({   
		url:'enterprise/checkEnterpriseExists.action?enterprise.license='+cmp.getValue()+'&enterprise.id='+Ext.getCmp("id").getValue(),
		success:function(response){
			obj = Ext.util.JSON.decode(response.responseText);
			if(!obj.success){
//            	showMsg('success', obj.message);
				alert(obj.message);
            	//return false;
			}
		},
		failure: function(response) {
			showMsg('failure', response.responseText);
		}
	}); 
}

var comboWorkType=new Ext.form.ComboBox({
    itemId: 'workType.id',
    name:'enterprise.workType.id',
    hiddenName:'enterprise.workType.id',
    selectOnFocus:true,
    valueField:'id',
    displayField:'name',
    fieldLabel: this.workTypeText,
    emptyText:this.comboEmpty10Text,
    editable:false,
    allowBlank:false,
    forceSelection:true,
    triggerAction:'all',
    store:storeWorkType,
    typeAhead: true
})

    Ext.form.Field.prototype.msgTarget = 'side';    
    micrite.enterprise.enterpriseDetail.FormPanel.superclass.constructor.call(this, {
        id: 'enterpriseDetail-form',
        frame: true,
        plain:true,
        labelAlign: 'right',
        style:'padding:1px',
      items: [
       		{ 
       		xtype: 'fieldset',
       		padding:'1px',
       		title:this.enterpriseDetailText,       		
        	layout : 'column',        				
            collapsible: true,
            defaults: {width: 360},    // Default config options for child items
            autoHeight: true,
            fileUpload : true,        
         items: [
        	{
            labelWidth: 100,
            layout:'form',
            collapsible: true,
            defaults: {width: 180},    // Default config options for child items
            defaultType: 'textfield',
            autoHeight: true,
            
            columnWidth : .5,
			baseCls : 'x-plain',
			
            items: [	
            {name: 'enterprise.id',itemId: 'id',hidden:true},
            {
                fieldLabel: this.unitNameText,
                itemId:  'unitName',
                name: 'enterprise.unitName',
                allowBlank:false
            },{
                fieldLabel: this.addressText,
                itemId:  'address',
                name: 'enterprise.address',
                allowBlank:true
            },{
                fieldLabel: this.legalPersonText,
                itemId:  'legalPerson',
                name: 'enterprise.legalPerson',
                allowBlank:false
            },{
                fieldLabel: this.telephone1Text,
                itemId:  'telephone1',
                name: 'enterprise.telephone1',
                vtype:'telephone',
                allowBlank:true
            },{
                fieldLabel: this.handleManText,
                itemId:  'handleMan',
                name: 'enterprise.handleMan',
                allowBlank:true
            },{
                fieldLabel: this.telephone2Text,
                itemId:  'telephone2',
                name: 'enterprise.telephone2',
//                vtype:'telephone',
                allowBlank:true
            },{
                fieldLabel: this.telephone3Text,
                itemId:  'telephone3',
                name: 'enterprise.telephone3',
//                vtype:'telephone',
                allowBlank:true
            },{
                fieldLabel: this.commissionText,
                itemId:  'commission',
                name: 'enterprise.commission',
                allowBlank:true
            },{
                fieldLabel: this.telephone4Text,
                itemId:  'telephone4',
                name: 'enterprise.telephone4',
//                vtype:'telephone',
                allowBlank:true
            }]
        },
        {
            labelWidth: 100,
            layout:'form',
            collapsible: true,
            defaults: {width: 170},    // Default config options for child items
            defaultType: 'textfield',
            autoHeight: true,           
        	columnWidth : .5,
			baseCls : 'x-plain',
			fileUpload : true,
		items : [ 
            {
                fieldLabel: this.licenseText,
                itemId:  'license',
                name: 'enterprise.license',
                allowBlank:false,
                listeners:{
                 'blur': checkEnterpriseLicenseExist  
                }
            },
            new Ext.form.ComboBox({
		                itemId: 'kind.id',
		                name:'enterprise.kind.id',
		                hiddenName:'enterprise.kind.id',
		                selectOnFocus:true,
		                valueField:'id',
		                displayField:'name',
		                fieldLabel: this.kindText,
		                emptyText:this.comboEmpty9Text,
		                editable:false,
		                allowBlank:false,
		                forceSelection:true,
		                triggerAction:'all',
		                store:storeKind,
		                typeAhead: true
		        }),		         
		        {
            	xtype:'uxspinnerdate',
                fieldLabel: this.licenseDateText,
                itemId:  'licenseDate',
                name: 'enterprise.licenseDate',
//                allowBlank:true,
	            value:new Date(),
	            format:'Y-m-d'
//	            strategy: new Ext.ux.form.Spinner.TimeStrategy({defaultValue:'00:00'})                
            }, 
			{
            	xtype:'uxspinnerdate',
                fieldLabel: this.dateBeginText,
                itemId:  'dateBegin',
                name: 'enterprise.dateBegin',
                allowBlank:true,         
	            value:new Date(),
	            format:'Y-m-d'
//	            strategy: new Ext.ux.form.Spinner.TimeStrategy({defaultValue:'00:00'})                
            },
			{
            	xtype:'uxspinnerdate',
                fieldLabel: this.dateEndText,
                itemId:  'dateEnd',
                name: 'enterprise.dateEnd',
                allowBlank:true,         
	            value:new Date(),
	            format:'Y-m-d'
//	            strategy: new Ext.ux.form.Spinner.TimeStrategy({defaultValue:'00:00'})                
            },
			new Ext.form.ComboBox({
                itemId: 'qualification.id',
                name:'enterprise.qualification.id',
				hiddenName:'enterprise.qualification.id',
                selectOnFocus:true,
                valueField:'id',
                displayField:'name',
                fieldLabel: this.qualificationText,
                emptyText:this.comboEmpty8Text,
                editable:false,
                allowBlank:false,
                forceSelection:true,
                triggerAction:'all',
                store:storeQualification,
                typeAhead: true
            }),{
            	xtype: 'treecombo',
            	itemId: 'workArea',
            	hiddenName: 'enterprise.workArea',
            	fieldLabel: this.workAreaText,
            	editable:false,
			    url: 'check-nodes.json',
			    emptyText: '选择经营范围',
			    //width: 660,
			    listWidth: 180,
			    listeners:{
			    	expand:function(cmb){
			    		cmb.reset();
			    	}
			    }
		    },	
            new Ext.form.ComboBox({
            	itemId: 'station.id',
                name:'enterprise.station',
                hiddenName:'enterprise.station.id',
                selectOnFocus:true,
                valueField:'id',
                displayField: 'name',
                fieldLabel:this.stationText,
                emptyText:'',
                editable:false, 
                allowBlank:false,
                forceSelection:true,
                triggerAction:'all',
                store:storeStation,
                typeAhead: true }), 
			comboWorkType
                ]
            }]},           
	{
		xtype : 'tabpanel',
		plain : true,
		bodyBorder : false,
		activeTab : 0,
		height : 100,
		defaults : {bodyStyle : 'padding:2px'},
		items : [
			{title : this.workRemarkText,layout : 'fit',items : {xtype : 'textarea',name : 'enterprise.workRemark',maxLength : 500}}		]
	}],			
            buttonAlign:'center',
            buttons: [{
                text: mbLocale.submitButton,
                handler: function(){
                	if(!Ext.getCmp("enterpriseDetail-form").getForm().isValid())return;
                    Ext.getCmp("enterpriseDetail-form").getForm().submit({
                        url: 'enterprise/saveEnterprise.action',
                        method: 'POST',
                        disabled:true,
                        waitMsg: mbLocale.waitingMsg,
                        success: function(form, action) {
                    			Ext.WindowMgr.getActive().close();
                                obj = Ext.util.JSON.decode(action.response.responseText);
                                showMsg('success', obj.message);  
                                micrite.security.framework.refleshActiveTabGridPanel();
                        },
                        failure: function(form, action) {
                            obj = Ext.util.JSON.decode(action.response.responseText);
                            showMsg('failure', obj.message);
                        }
                    });
                }                    
//            },{
//        		text : '重置',
//        		handler : function() { 
//        			Ext.getCmp("enterpriseDetail-form").getForm().reset();//重置form
//        		}
        	},{
                text: mbLocale.closeButton,
                scope:this,
                handler: function(){
                Ext.WindowMgr.getActive().close();
             }
         }             
        	]
    });   
};

Ext.extend(micrite.enterprise.enterpriseDetail.FormPanel, Ext.FormPanel, { 
});
try{enterpriseDetailLocale();} catch(e){alert(e);}

var ds_car = new Ext.data.Store({
	url : 'enterprise/getEntity.action',
	reader : new Ext.data.JsonReader(
		{id:'id'},//root : 'carfile',
	   GEnterpriseFields)
});

function init(entId){
	ds_car.load({
		params:{id:entId},
		callback:function(r,options,sucess){
			if(sucess){
				var record = ds_car.getAt(0);
				if(record!=null){
					Ext.getCmp('enterpriseDetail-form').getForm().loadRecord(record);
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
     Ext.WindowMgr.getActive().add(new micrite.enterprise.enterpriseDetail.FormPanel());
     Ext.WindowMgr.getActive().doLayout();
     if(window.enterpriseId){
     	init(window.enterpriseId);
     }else{
//     	comboWorkType.hidden=true;?
     }
});
</script>

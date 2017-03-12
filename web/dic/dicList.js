<script type='text/javascript'>
Ext.ns('micrite.security.sysParam');
micrite.security.sysParam.FormPanel =  Ext.extend(Ext.Panel, {
	id:'micrite.security.sysParam.FormPanel',
	initComponent:function() {
		var recordDef1 = Ext.data.Record.create([{name: 'type'},{name: 'name'}]);
		var recordDef2 = Ext.data.Record.create([{name: 'id'},{name: 'name'},{name: 'type'},{name: 'value'},{name: 'show'}]);
	//----------------------------------	
		var storeTypes = new Ext.data.Store({
			id: Ext.id(),
			autoLoad:true,
			proxy : new Ext.data.HttpProxy({url: 'dic/getListItems.action'}), 
			reader : new Ext.data.JsonReader({id:'type'}, recordDef1),
			remoteSort: true   
		});
		var comboTypes = new Ext.form.ComboBox({
			fieldLabel:'分类',
			valueField: 'type',
			displayField: 'name',
			store: storeTypes,
			triggerAction :'all',
			lazyRender:false,
			editable:false,
			listeners:{   
		    select:function(me,record,index){   
//				var type=record.get('type');   
//				var combo=Ext.getCmp('ComboMerchandiseSortId');
//				combo.clearValue();
//				//combo.store.baseParams={'d.type': type};
//				combo.store.load({params:{'d.type':type}});
//				combo.disabled=true;
//				//combo.getEl().dom.disabled=false;
//		    	var type=record.get('type');//这个就依赖参数，fireEvent无法
		    	var type = comboTypes.getValue();//这样居然得不到值
		    	
				comboItems.clearValue();
				comboItems.getStore().baseParams={'d.type': type};
//				comboItems.getStore().load({params:{'d.type':type}});
				comboItems.getStore().load();
				if(comboItems.disabled){
					comboItems.enable();
				}
		    }
		} 
		});
		var storeItems = new Ext.data.Store({
			id: Ext.id(),
			autoLoad:false,
			proxy : new Ext.data.HttpProxy({url: 'dic/getListItems.action'}), 
			reader : new Ext.data.JsonReader({id:'id'}, recordDef2),
			remoteSort: true   
		});
		var comboItems = new Ext.form.ComboBox({
			fieldLabel:'分目',
			id:'ComboMerchandiseSortId',
			valueField: 'id',
			displayField: 'name',
			store: storeItems,
			triggerAction :'all',
			lazyRender:false,
			editable:false,
			disabled:true,
		 listeners:{   
		    select:function(me,record,index){
		  		  	Ext.getCmp('dname').setValue(record.get('name'));
		  		  	if(record.get('show')==0){
		  		  		Ext.getCmp('dshow').getEl().dom.checked=true;
		  		  		Ext.getCmp('dshow').checked=true;
//		  		  		alert('true='+Ext.getCmp('dshow').checked);
		  		  	}else{
		  		  		Ext.getCmp('dshow').getEl().dom.checked=false;
		  		  		Ext.getCmp('dshow').checked=false;
//		  		  		alert('false='+Ext.getCmp('dshow').checked);
		  		  	}
		    }
		 }
		});
		var btn_submit_permissiondays = new Ext.Button({
			text: mbLocale.submitButton,
			handler : function(){
				Ext.Ajax.request({
					url:'dic/ewEPDays.action',
					params:{days:Ext.getCmp('days').getValue()},
					success:function(response){
						obj = Ext.util.JSON.decode(response.responseText);
						if(!obj.success){
		                	showMsg('failure', obj.message);
		                	return false;
						}else{
							showMsg('success', obj.message);
							Ext.getCmp('days').setValue(obj.data);
						}
					},
					failure: function(response) {
						showMsg('failure', response.responseText);
					}
				});
			}
		});
		var btn_submit_editdictionaryitem = new Ext.Button({
			text: '修改本条',//mbLocale.submitButton,
			handler : function(){
				var id = comboItems.getValue();
				var isshow = ((Ext.getCmp('dshow').checked)?0:1);
				
//				alert(id+','+Ext.getCmp('dshow').checked);
				Ext.Ajax.request({   
					url:'dic/edit.action',
					params:{'d.id':id,'d.name':Ext.getCmp('dname').getValue(),'d.show':isshow},
					success:function(response){
						obj = Ext.util.JSON.decode(response.responseText);
						if(!obj.success){
		                	showMsg('failure', obj.message);
		                	return false;
						}else{
							showMsg('success', obj.message);
							refleshItems();
						}
					},
					failure: function(response) {
						showMsg('failure', response.responseText);
					}
				});
			}
		});
		function refleshItems(){
			comboTypes.fireEvent('select');//,comboTypes,comboTypes.getStore().getAt(comboTypes.));//不知道当前选中的是第几条；	
		}
		var btn_submit_adddictionaryitem = new Ext.Button({
			text: '新增本条',//mbLocale.addButton,
			handler : function(){
				if(comboTypes.getValue()=='')return;
				if(Ext.getCmp('dname').getValue()=='')return;
				var isshow = ((Ext.getCmp('dshow').checked)?0:1);
				Ext.Ajax.request({   
					url:'dic/add.action',
					params:{'d.name':Ext.getCmp('dname').getValue(),'d.show':isshow,'d.type':comboTypes.getValue()},
					success:function(response){
						obj = Ext.util.JSON.decode(response.responseText);
						if(!obj.success){
		                	showMsg('failure', obj.message);
		                	return false;
						}else{
							showMsg('success', obj.message);
							refleshItems();
						}
					},
					failure: function(response) {
						showMsg('failure', response.responseText);
					}
				});				
			}
		});
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
		            },btn_submit_permissiondays]
		    },{   
    xtype:'fieldset',  
    title: '下拉列表设置',   
    height:300,
//    defaultType: 'textfield',   
    items:[comboTypes,comboItems,{
    xtype:'fieldset',
    height:100,
    layout:'form',
    title: '修改',   
    defaultType: 'textfield',   
    items:[{id:'dname',fieldLabel:'名称'},{xtype:'checkbox',id:'dshow',fieldLabel:'显示'}]//,inputValue:1
    },btn_submit_editdictionaryitem,btn_submit_adddictionaryitem]   
}]
	    	}; // eof config object
		    // apply config
		    Ext.apply(this, Ext.apply(this.initialConfig, config));
		    // call parent
		    micrite.security.sysParam.FormPanel.superclass.initComponent.apply(this, arguments);
	
		},// eo funtion initComponent
		onRender:function(){
		    micrite.security.sysParam.FormPanel.superclass.onRender.apply(this, arguments);
		    Ext.Ajax.request({   
				url:'dic/ewEPDays.action',
				success:function(response){
					obj = Ext.util.JSON.decode(response.responseText);
					if(!obj.success){
	                	showMsg('failure', obj.message);
	                	return false;
					}else{
						Ext.getCmp('days').setValue(obj.data);
					}
				}
//				,
//				failure: function(response) {
//					showMsg('failure', response.responseText);
//				}
			});
		}

});

try{ sysParamLocale(); } catch(e){}

Ext.onReady(function() {
    Ext.QuickTips.init();
//    Ext.form.Field.prototype.msgTarget = 'side';
    
    var formPanel = new micrite.security.sysParam.FormPanel();
    if (mainPanel) {
        mainPanel.getActiveTab().add(formPanel);
        mainPanel.getActiveTab().doLayout();
    } else {
        var vp = new Ext.Viewport({layout:'fit', items:[formPanel]});
    }
});

</script>
<script type="text/javascript">
Ext.ns('micrite.car.carfileDetail');
micrite.car.carfileDetail.FormPanel = function(){
     
//    Ext.apply(Ext.form.VTypes, {
//    	reason:function(val, field) {
//        return /^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){10,10}$/; //首个是字母，用reason不行(可能是要用默认的)
//    },
//    reasonText: '第一个是字母!'
//	});

function checkCarExist(){
	var theForm=Ext.getCmp("carfileDetail-form").getForm();
	var licenseNumber = theForm.findField("licenseNumber").getValue();
	if(licenseNumber=='')return;
	var paiSeId = theForm.findField("paiSe.id").getValue();
	if(paiSeId=='')return;
	Ext.Ajax.request({   
		url:'car/checkCarExists.action?carfile.licenseNumber='+licenseNumber+'&carfile.paiSe.id='+paiSeId+'&carfile.id='+theForm.findField("id").getValue(),
		success:function(response){
			obj = Ext.util.JSON.decode(response.responseText);
			if(!obj.success){
            	//showMsg('success', obj.message);
				alert(obj.message);
            	//return false;
			}
		},
		failure: function(response) {
			showMsg('failure', response.responseText);
		}
	}); 
}
   
    function onOwnerLicenseBlur(){
    	var theForm=Ext.getCmp("carfileDetail-form").getForm();
//    	alert(theForm.findField('ownerLicense').getValue());//theForm.superclass()
//    	var TheFieldset=theForm.findByType('fieldset',false).indexOf(0); 
    	var ownerLicense = theForm.findField("ownerLicense").getValue();
        if(ownerLicense=='')return;
		Ext.Ajax.request({   
			url:'crm/getCarownerByLicense.action?carowner.license='+ownerLicense,
			success:function(response){
				obj = Ext.util.JSON.decode(response.responseText);
				if(!obj.success){
					theForm.findField("carowner.id").setValue('');
					//showMsgSilently('failure', obj.message);
                	return false;
				}else{
					theForm.findField("carowner.id").setValue(obj.owner.id);//老是报错 为空或不是对象
					theForm.findField("telephone").setValue(obj.owner.telephone);
					theForm.findField("mobile").setValue(obj.owner.mobile);
					theForm.findField("address").setValue(obj.owner.address);
					theForm.findField("owner").setValue(obj.owner.name);
				}
			},
			failure: function(response) {
				showMsgSilently('failure', response.responseText);
			}
		}); 
    }
    Ext.form.Field.prototype.msgTarget = 'side';    
    micrite.car.carfileDetail.FormPanel.superclass.constructor.call(this, {
        id: 'carfileDetail-form',     
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
                defaults: {width: 280},    // Default config options for child items
                autoHeight: true,
                fileUpload : true,  
        items: [
        {
            labelWidth: 90,
            layout:'form',
            collapsible: true,
            defaults: {width: 180},    // Default config options for child items
            defaultType: 'textfield',
        	height:250,         
            columnWidth : .5,
			baseCls : 'x-plain',
			
            items: [{xtype:'hidden',itemId:'carowner.id',name:'carfile.carowner.id'},
            	{xtype:'hidden',itemId:'id',name:'carfile.id'},{
                fieldLabel: this.ownerLicenseText,
                itemId: 'ownerLicense',
                name: 'carfile.ownerLicense',
                allowBlank:false,
                listeners:{
                 'blur': onOwnerLicenseBlur  
                }
            },{
                fieldLabel: this.ownerText,
                itemId: 'owner',
                name: 'carfile.owner',
                allowBlank:false
            },{
                fieldLabel: this.addressText,
                itemId: 'address',
                name: 'carfile.address',
                allowBlank:false
            },{
                fieldLabel: this.telephoneText,
                itemId: 'telephone',
                name: 'carfile.telephone',
                vtype:'telephone',
                allowBlank:false
            },{
                fieldLabel: this.mobileText,
                itemId: 'mobile',
                name: 'carfile.mobile',
                vtype:'mobile',
//                blankText :'必填、11位数字',
//                minLength :11,
//                minLengthText : "手机号码应为11位！",
//                maxLength : 11 ,
//                maxLengthText :"手机号码应为11位！",
//                regex : /^[0-9]{11,11}$/,
//                regexText : "只能输入数字",
                allowBlank:false
            }, {
                fieldLabel: this.licenseNumberText,
                itemId:'licenseNumber',
                name: 'carfile.licenseNumber',
	            value:'浙B.',
	            vtype:'paihao',
                allowBlank:false,
                listeners:{
                 'blur': checkCarExist  
                }
            }, 
            new Ext.form.ComboBox({
                itemId:'paiSe.id',
                name:'carfile.paiSe.id',
                selectOnFocus:true,
                valueField:'id',
                hiddenName:'carfile.paiSe.id',
                displayField:'name',
                fieldLabel:'车牌颜色',
                emptyText:'请选择车牌颜色',
                editable:false,
                allowBlank:false,
                forceSelection:true,
                triggerAction:'all',
                store:storePaiSe,
                typeAhead: true,
                listeners:{
                 'select': checkCarExist  
                }
            }), 
             new Ext.form.ComboBox({ //车牌型号
             	itemId:'licenseType.id',
                name:'carfile.licenseType.id',
                selectOnFocus:true,
                valueField:'id',
                hiddenName:'carfile.licenseType.id',
                displayField:'name',
                fieldLabel:this.source3Text,
                emptyText:this.comboEmpty3Text,
                editable:false,
                allowBlank:false,
                forceSelection:true,
                triggerAction:'all',
                store:storeLicenseType,
                typeAhead: true               
            }),
            {
          	  fieldLabel: this.loadTonText,
          	  itemId:'loadTon',
              name: 'carfile.loadTon',
              allowBlank:true
            }
            ]
        },
        {
            labelWidth: 130,
            layout:'form',
            collapsible: true,
            defaults: {width: 150},    // Default config options for child items
            defaultType: 'textfield',
            height:250,           
        	columnWidth : .5,
			baseCls : 'x-plain',
			fileUpload : true,
		items : [
				{
				fieldLabel: '营运证号',
				itemId: 'yingyunNo',
				name: 'carfile.yingyunNo',
				allowBlank:true
				}, 
            new Ext.form.ComboBox({ //技术等级
            	itemId:'skillRank.id',
                name:'carfile.skillRank.id',
                selectOnFocus:true,
                valueField:'id',
                hiddenName:'carfile.skillRank.id',
                displayField:'name',
                fieldLabel:this.source2Text,
                emptyText:this.comboEmpty2Text,
                editable:false,
                allowBlank:false,
                forceSelection:true,
                triggerAction:'all',
                store:storeSkillRank,
                typeAhead: true               
            }), 
            new Ext.form.ComboBox({//技术等级评定周期
                itemId:'evaluateCycle.id',
                name:'carfile.evaluateCycle.id',
                selectOnFocus:true,
                valueField:'id',
                hiddenName:'carfile.evaluateCycle.id',
                displayField:'name',
                fieldLabel:this.source5Text,
                emptyText:this.comboEmpty5Text,
                editable:false,
                allowBlank:false,
                forceSelection:true,
                triggerAction:'all',
                store:storeEvCycle,
                typeAhead: true               
            }),
            	{
            	xtype:'datefield',
                fieldLabel: this.maintainDateText,
                itemId: 'maintainDate',
                name: 'carfile.maintainDate',
                allowBlank:false,                
                value:new Date(),
                format:'Y-m-d'
            },
            new Ext.form.ComboBox({//二维周期
                itemId:'maintainCycle.id',
                name:'carfile.maintainCycle.id',
                selectOnFocus:true,
                valueField:'id',
                hiddenName:'carfile.maintainCycle.id',
                displayField:'name',
                fieldLabel:this.source6Text,
                emptyText:this.comboEmpty6Text,
                editable:false,
                allowBlank:false,
                forceSelection:true,
                triggerAction:'all',
                store:storeMtCycle,
                typeAhead: true               
            }), 
            new Ext.form.ComboBox({//燃油类型
               itemId:'fuelRank.id',
               name:'carfile.fuelRank.id',
               selectOnFocus:true,
               valueField:'id',
               hiddenName:'carfile.fuelRank.id',
               displayField:'name',
               fieldLabel:this.source4Text,
               emptyText:this.comboEmpty4Text,
               editable:false,
               allowBlank:false,
               forceSelection:true,
               triggerAction:'all',
               store:storeFuelRank,
               typeAhead: true               
           }),          
            new Ext.form.ComboBox({
                itemId:'carType.id',
                name:'carfile.carType.id',
                selectOnFocus:true,
                valueField:'id',
                hiddenName:'carfile.carType.id',
                displayField:'name',
                fieldLabel:this.sourceText,
                emptyText:this.comboEmptyText,
                editable:false,
                allowBlank:false,
                forceSelection:true,
                triggerAction:'all',
                store:storeCarType,
                typeAhead: true }),
            {
                fieldLabel: this.brandTypeText,
                itemId: 'brandType',
                name: 'carfile.brandType',
                allowBlank:true
            }
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
			{title : this.carRemarkText,layout : 'fit',items : {xtype : 'textarea',itemId:'carRemark',name : 'carfile.carRemark',maxLength : 300}}
			]
    	}],	
            buttonAlign:'center',
            buttons: [{
                text: mbLocale.submitButton,
                //提交按钮 之前校验不通过就不提交到后台
                handler: function(){
                	if(!Ext.getCmp("carfileDetail-form").getForm().isValid())return;
                    Ext.getCmp("carfileDetail-form").getForm().submit({
                        url: 'car/saveCarfile.action',
                        method: 'POST',
                        disabled:true,
                        waitMsg: mbLocale.waitingMsg,                       
                        success: function(form, action) {
                        	if(window.carId){
                        		window.carId=null;
                        		Ext.WindowMgr.getActive().close();
                        		micrite.patrolRiverSummary.framework.refleshActiveTabGridPanel();
                        		return;
                        	}
                        	Ext.WindowMgr.getActive().close();
//                            obj = Ext.util.JSON.decode(action.response.responseText);
							window.carId=action.result.carId;
							micrite.util.genWindow({
								modal:true,
								maximizable:false,
					            title    : this.printPreviewTxt, 
					            autoLoad : {url: 'car/erweiPrintPanel.js',scripts:true},  
								width : 660,
								height : 500
					        });
                        },
                        failure: function(form, action) {
//                       		Ext.MessageBox.alert("请注意","请您检查输入项！"); 
                            obj = Ext.util.JSON.decode(action.response.responseText);
                            var message=(obj.message=='undefined')?obj.actionErrors:obj.message;
                            showMsg('failure', message); 
                        }
                    });
                }                    
//            },{
//        		text : '重置',
//        		handler : function() { 
//        			Ext.getCmp("carfileDetail-form").getForm().reset();//重置form
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
Ext.extend(micrite.car.carfileDetail.FormPanel, Ext.FormPanel, {});
try{ carfileDetailLocale(); } catch(e){alert(e);}

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
							Ext.getCmp('carfileDetail-form').getForm().loadRecord(record);
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
     Ext.WindowMgr.getActive().add(new micrite.car.carfileDetail.FormPanel());
     Ext.WindowMgr.getActive().doLayout();
     if(window.carId){
     	init(window.carId);
     }
});
</script>
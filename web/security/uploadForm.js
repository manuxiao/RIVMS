<script type="text/javascript">
Ext.onReady(function() {
	var msg = function(title, msg){
        Ext.Msg.show({
            title: title,
            msg: msg,
            minWidth: 200,
            modal: true,
            icon: Ext.Msg.INFO,
            buttons: Ext.Msg.OK
        });
    }; 

    var fp1 = new Ext.FormPanel({
        //renderTo: 'fi-form',
    	flex:1,
        fileUpload: true,
        width: 500,
        frame: true,
        title: '维修企业数据导入',
        autoHeight: true,
        bodyStyle: 'padding: 10px 10px 0 10px;',
        labelWidth: 50,
        defaults: {
            anchor: '95%',
            allowBlank: false,
            msgTarget: 'side'
        },
        items: [{   
            xtype:'hidden',   
            name : 'type',   
            id:'type1',
            value:'enterprise'
        },{
            xtype: 'fileuploadfield',
            id: 'form-file1',
            emptyText: '维修企业数据文件',
            fieldLabel: '维修企业',
            name: 'file',
            buttonCfg: {
                text: '',
                iconCls: 'upload-icon'
            }
        }],
        buttons: [{
            text: '导入',
            handler: function(){
                if(fp1.getForm().isValid()){
	                fp1.getForm().submit({
	                    url: 'uploadEnterprise.action',
	                    waitMsg: '正在导入...',
	                    method: 'POST',
	                    disabled:true,
	                    success: function(fp1, o){
	                        msg('导入完成', '导入完成');
	                    },
	                    failure: function(fp1, action){   
                            msg('失败！', '文档文件导入失败！');   
                        } 
	                });
                }
            }
        },{
            text: '重置',
            handler: function(){
                fp1.getForm().reset();
            }
        }]
    });
    var fp2 = new Ext.FormPanel({
        //renderTo: 'fi-form',
    	flex:1,
        fileUpload: true,
        width: 500,
        frame: true,
        title: '车辆档案数据导入',
        autoHeight: true,
        bodyStyle: 'padding: 10px 10px 0 10px;',
        labelWidth: 50,
        defaults: {
            anchor: '95%',
            allowBlank: false,
            msgTarget: 'side'
        },
        items: [{
            xtype: 'fileuploadfield',
            emptyText: '车辆档案数据导入',
            fieldLabel: '车辆档案',
            name: 'file',
            buttonCfg: {
                text: '',
                iconCls: 'upload-icon'
            }
        }],
        buttons: [{
            text: '导入',
            handler: function(){
                if(!fp2.getForm().isValid()){return;}
                fp2.getForm().submit({
                    url: 'uploadCar.action',
                    waitMsg: '正在导入...',
                    method: 'POST',
                    disabled:true,
                    success: function(fp3, o){
                        msg('导入完成', '导入完成');
                    },
                    failure: function(fp3, action){   
                        msg('失败！', '文档文件导入失败！');   
                    } 
               });
            }
        },{
            text: '重置',
            handler: function(){
                fp2.getForm().reset();
            }
        }]
    });
    
    var fp3 = new Ext.FormPanel({
        //renderTo: 'fi-form',
    	flex:1,
        fileUpload: true,
        width: 500,
        frame: true,
        title: '检测数据导入',
        autoHeight: true,
        bodyStyle: 'padding: 10px 10px 0 10px;',
        labelWidth: 50,
        defaults: {
            anchor: '95%',
            allowBlank: false,
            msgTarget: 'side'
        },
        items: [{   
            xtype:'hidden',   
            name : 'type',   
            id:'type3',
            value:'dat'
        },{
            xtype: 'fileuploadfield',
            id: 'form-file3',
            emptyText: '检测数据导入',
            fieldLabel: '检测数据',
            name: 'file',
            buttonCfg: {
                text: '',
                iconCls: 'upload-icon'
            }
        }],
        buttons: [{
            text: '导入',
            handler: function(){
                if(fp3.getForm().isValid()){
	                fp3.getForm().submit({
	                    url: 'uploadDat.action',
	                    waitMsg: '正在导入...',
	                    method: 'POST',
	                    disabled:true,
	                    success: function(fp3, o){
	                        msg('导入完成', '导入完成');
	                    },
	                    failure: function(fp3, action){   
                            msg('失败！', '文档文件导入失败！');   
                        } 
	                });
                }
            }
        },{
            text: '重置',
            handler: function(){
                fp3.getForm().reset();
            }
        }]
    });
	var panel = new Ext.Panel({//Ext.Panel  
		layout:{
			type: 'vbox',
   			padding: '5',
    		align: 'stretch'
		},
		border:false,
		defaultType:'form',
		defaults:{margins:'0 0 5 0'},
		items:[fp1,fp2,fp3,{
			flex:1,
			border:false,
			title:'超时未检车辆导出',
			frame:true,
			layout:{type: 'vbox',
   			padding: '5',
   			pack:'center',
    		align: 'center'},
//			baseCls:'x-plain',
			items:[{
				xtype:'button',
				text:'导出',
				width:80,
				handler:function(bt) {
//					bt.ownerCt.getForm().submit({
//						url:'downloadExpiredCars.action',
//						method:'POST'
//					});
					Ext.Ajax.request({
						url:'downloadExpiredCars.action',
						method:'POST',
						form : Ext.DomHelper.append(Ext.getBody(), {   
                            tag : 'form',   
                            enctype : 'multipart/form-data'  
                        }),
                        success : function() {
                    		Ext.Msg.alert('提示', '文件下载成功');   
                		},   
                		failure : function() {   
                   	 		Ext.Msg.alert('提示', '文件下载失败');   
                		},   
                		error : function(form, action) {   
                    		Ext.Msg.alert('错误', '文件下载过程中出现了\n不可预知的错误.');   
                		}   

					});
//					window.open('downloadExpiredCars.action');
				}
			}]
		}]
    });
	mainPanel.getActiveTab().add(panel);
	mainPanel.getActiveTab().doLayout();
});
</script>
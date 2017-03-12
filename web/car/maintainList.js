<script type="text/javascript">
Ext.namespace('micrite.car.maintainList');
micrite.car.maintainList.SearchPanel = Ext.extend(micrite.ComplexGrid, {	
	initComponent:function() {
	var pageSize = parseInt(Ext.getDom('pageSize').value,10);

	// ------检测站传过来的车辆维护数据------------------------
	var postcheckstore = new Ext.data.JsonStore({
		id:'postStore',
		sortInfo : {field : 'jianTime',direction : 'desc'},
   		pruneModifiedRecords:true,
   		url:'check/findRecentPostCheck.action',
		root:'data',
		fields:[
				{name: 'id'},
	            {name: 'jianTime'},
	            {name: 'testNo'},
	            {name: 'paiHao'},
	            {name: 'heGe'},
	            {name: 'printed'},
	            {name: 'carId'},
	            {name: 'isPost'},
	            {name: 'status'},
	            {name: 'paiSe.id'},
	            {name: 'paiSe.name'},
	            {name: 'cheXiu'},
	            {name: 'testKind'},
	            {name: 'cheDengji'}
			],
		totalProperty:'totalCount'
	});

	/*
	 * var rowClass1 = function(record,index){ if(record.get('carId') ==
	 * 0){return 'red-row';}else{return 'white-row';} }; var rowClass2 =
	 * function(record,index){ if(record.get('printed') ==
	 * 1){win.getStore().on("load",function(){win.getSelectionModel().selectRow(index,true);}); } };
	 * var rowClass3 = function(record,index){ if(record.get('status') == '超期')
	 * {return 'red-row';}else{return 'white-row';} };
	 */
	Ext.ux.PostCheckGridView=Ext.extend(Ext.grid.GridView,{getRowClass:function(record,index){
			var ststr = ''; 
            if(record.get('carId') == 0){ststr= 'red-row';}else{ststr= 'white-row';}
            if(record.get('testKind') == 1){ststr+= '-dengji';}
            return ststr;
        }
    });  

	var postSMCheck = new Ext.grid.CheckboxSelectionModel();
	var postCM = new Ext.grid.ColumnModel({
		columns:[	
        	postSMCheck,
			new Ext.grid.RowNumberer(),{
				header:'记录序号',dataIndex:'id',sortable:true,hidden:true
        	},  
  			{									
				header: '检测日期',
				width: 100, sortable: true, dataIndex: 'jianTime'
			},
			{
		        header: '检测编号', 		        
		        width: 100, sortable: true, dataIndex: 'testNo'
		     }, 			
			{
				header: '车牌号码',
				width: 100, sortable: true, dataIndex: 'paiHao'
			},	           		            
			{
				header: '合格证号',
				width: 100, sortable: true, dataIndex: 'heGe'
			},
			{
				header: '车牌颜色',
				width: 100, sortable: true, dataIndex: 'paiSe.name'	   
			},
			{
				header: '维修企业',
				width: 100, sortable: true, dataIndex: 'cheXiu'			   
			}
		]
	});
	
  // ---------检测站数据----------------------
    var origCheckGridPanel = new Ext.grid.GridPanel({
		id:'egp01',
        border:false,
        stripeRows:true,
        // view:new Ext.ux.GridView({forceFit:true,getRowClass:rowClass1}),
        view:new Ext.ux.PostCheckGridView({forceFit:true}),
        selModel:new Ext.grid.RowSelectionModel(),
        /*
		 * tbar:[[{ text:'打印超时单', iconCls:'edit-icon', scope:this,
		 * handler:function(){ var id = this.selModel.selections.keys;
		 * micrite.util.genWindow({ id : 'addCheckWindow', title :
		 * this.printPreviewTxt, autoLoad : {url:
		 * 'printPaper.action?id='+id,scripts:true}, width : 420, height : 360
		 * }); }//eof handler },'-', { text:mbLocale.deleteButton, iconCls
		 * :'delete-icon', scope:this, handler:this.deletecheck } ]],
		 */
       	cm:postCM,
        store:postcheckstore,
        sm:postSMCheck,
        listeners: {
           /*render: function(g){
                var view = g.getView();    // Capture the GridView.
                var store = g.getStore();
                var rowTip = new Ext.ToolTip({
                    target: view.mainBody,    // The overall target element.
                    delegate: '.x-grid3-row', // Each grid row causes its own
												// seperate show and hide.
                    trackMouse: true,         // Moving within the row should
												// not hide the tip.
                    renderTo: document.body,  // Render immediately so that
												// tip.body can be
                    listeners: {  // Change content dynamically depending on
									// which element triggered the show.
                        beforeshow: function updateTipBody(tip){
                            var rowIndex = view.findRowIndex(tip.triggerElement);
                            var record = store.getAt(rowIndex);
                            if(record==null)return;
                            if(record.get('carId') == 0){                           	
                     			tip.body.dom.innerHTML = "<b>没有找到匹配的车辆！<br/><br>车辆号码:"+record.get('paiHao')+"</br><br>车辆颜色:"+record.get('paiSe.id');
                			}else{
                     				return false; // 停止执行，从而禁止显示Tip
                     				tip.destroy();
                 			}
            			}     
        			}                    
                });
            }*/
        }
    });
 
 // --------根据合格证找出是否在库是否已用掉----------------------
    function onHegeBlur(f){                 	
//        var heGe = Ext.getCmp("heGeId").getValue();
        var theForm = Ext.getCmp("addCheckFormID").getForm(); 
    	var heGe=theForm.findField('heGe').getValue();
        if(heGe=='')return;
		Ext.Ajax.request({   
			url:'check/findEnterpriseByHege.action?check.heGe='+heGe,
			success:function(response){
				obj = Ext.util.JSON.decode(response.responseText);
				if(!obj.success){
                	showMsg('failure', obj.message);
                	return false;
				}else{
//					Ext.getCmp("qiye").setValue(obj.message);
					theForm.findField('cheXiu').setValue(obj.message);
				}
			},
			failure: function(response) {
				showMsg('failure', response.responseText);
			}
		}); 
  	};
	var addCheckWindow = new Ext.Window({  
	   	modal:true,
	   	title:'新增维护记录',
		closeAction:'hide',
		padding:'5px',
		width : 660,
		autoHeight : true,
		items : [{
		xtype:'form',
		labelAlign : 'right',
		labelWidth : 100,
		bodyStyle : 'padding:1px',
		id:'addCheckFormID',
		baseCls:'x-plain',      
        items: [{ 
           		xtype: 'fieldset',
           		padding:'1px',
           		title:'维护记录信息',       		
            	layout : 'column',        				
                collapsible: true,
                defaults: {},    // Default config options for child items
                autoHeight: true, 
        items: [{//左边fieldset
            labelWidth: 80,
            layout:'form',
            collapsible: true,
            defaults: {width: 150},    // Default config options for child items
            defaultType: 'textfield',
        	height:165,   
            columnWidth : .5,
			baseCls : 'x-plain',		                          
            items: [
            	{
            	xtype:'hidden',
            	name:'carId',
            	allowBlank:false
            	},{
            	xtype:'hidden',
            	name:'forceSubmit',
            	allowBlank:false
            	},
            	{                
            	xtype:'datefield',
                fieldLabel: '检测时间',
                name: 'jianTime',
                allowBlank:false,
                editable:false,
                value:new Date(),
                format:'Y-m-d'         
            },{
                fieldLabel: '车牌号码',
                name: 'licenseNumber',
                allowBlank:false,
                editable:false,
                readOnly:true
            },{
              xtype:'combo',
	          name:'paiSe.id',
	          hiddenName:'paiSe.id',
			//selectOnFocus:true,
	          valueField:'id',
	          displayField:'name',
	          fieldLabel:'车牌颜色',
	          emptyText:'请选择车牌颜色',
	          editable:false,
			  readOnly:true,
			//forceSelection:true,
	          triggerAction:'all',
	          store:storePaiSe,
	          typeAhead: true               
		    },{
            	xtype:'textarea',
            	name: 'notepad',
//            	id:'checkNotpad',
                fieldLabel: '车辆状况备注',
                allowBlank:true
            }
            ]
        },
        {
            labelWidth: 80,
            layout:'form',
            collapsible: true,
            defaults: {width: 140},    // Default config options for child items
            defaultType: 'textfield',
            height:165,          
        	columnWidth : .4,
			baseCls : 'x-plain', 
			items: [{ 
//				id:'heGeId',
                fieldLabel: '合格证号',
                name: 'heGe',
//                allowBlank:false,
                listeners:{
                 'blur': onHegeBlur  
               }
            },{
            	name: 'cheXiu',
//            	id:'qiye',
                fieldLabel:'维修企业',
                value:'',             
                allowBlank:true
				//readOnly:true
            },{  
	            xtype:'combo',
//	            id:'testKindId',
	        	name:'testKind',
	            hiddenName:'testKind', // 这个设置着则提交这个名称作为变量名
	        	fieldLabel:'维护类型',
	            store:storeTestKind,
	            emptyText:'请选择',
				//typeAhead: true,
				editable : false,
				selectOnFocus:true,
				allowBlank : false,
	            triggerAction: 'all',
	            valueField:'value',  
	            displayField:'name',
	            listeners:{
	            	select:function(me,record,index){
//	            		var djCombo=Ext.getCmp('cheDengjiId');
	            		var theForm = Ext.getCmp("addCheckFormID").getForm(); 
    					var djCombo=theForm.findField('testKind');
	            		if(index==0){
	            			djCombo.enable();
	            			djCombo.allowBlank=false;
	            		}else{
	            			djCombo.disable();
	            			djCombo.allowBlank=true;
	            		}
	            	}
	            }
				//selectOnFocus:true,
				//frame:true
        	},
        	{
        	  xtype:'combo',
			  name:'cheDengji',
//        	  id:'cheDengjiId',
        	  fieldLabel:'技术等级',
	          hiddenName:'cheDengji',
	          store:storeSkillRank,
	          emptyText:'请选择',
	          editable:false,
        	  allowBlank : false,
	          triggerAction:'all',
	          valueField:'value',
	          displayField:'name'
			//typeAhead: true               
		    }]
        }]
        },{
		xtype : 'tabpanel',
		plain : true,
		bodyBorder : false,
		activeTab : 0,
		height : 300,
		width : 630,
		defaults : {bodyStyle : 'padding:2px'},
		items : [ 
			{title : '检测站数据',layout : 'fit',items :[origCheckGridPanel]}
//		    {title : '二级维护',layout : 'fit',items :[erweiGridPanel]},
//			{title : '等级评定',layout : 'fit',items :[dengjiGridPanel]}
			]
    	}],	
            buttonAlign:'center',
            buttons: [{  // 点击二级维护的保存，关闭界面，弹出非检测站数据的界面。要刷新新增一条
                text: '保存',
                handler:saveCheckFn					                                          
            },{
            text: '关闭',
            scope:this,
            handler: function(){
            	addCheckWindow.hide();
            }
        }]
    }]}); 	
    // --------加注车辆状况 ----------------------
	var expiredBackWindow = new Ext.Window({
	   	modal:true,
        title:'加注车辆状况',
		closeAction:'hide',
		padding:'5px',
		width : 400,
//		height:400,
		items : [{
        id: 'carfileNotepad-form',
		labelAlign : 'right',
		labelWidth : 80,
		xtype:'form',
		bodyStyle : 'padding:1px',
		baseCls:'x-plain',	
        items: [
        {
            xtype: 'fieldset',           
            title:'车辆状况备注',
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
                fieldLabel: '备注',
                name: 'notepad',
                allowBlank:true
            },{
                fieldLabel: '车牌号码',
                name: 'licenseNumber',
                blankText : '请输入车牌号码',
                allowBlank:false,
                readOnly:true
            }, 	
              new Ext.form.ComboBox({// 车牌颜色
                name:'paiSe.id',
//                selectOnFocus:true,
                valueField:'id',
                hiddenName:'paiSe.id',
                displayField:'name',
                fieldLabel:'车牌颜色',
                emptyText:'请选择车牌颜色',
                editable:false,
                allowBlank:false,
//                forceSelection:true,
                triggerAction:'all',
                store:storePaiSe,
                typeAhead: true,
                readOnly:true
            })]               
        }],
            buttonAlign:'center',
            buttons: [{
                text: mbLocale.submitButton,
                // 提交按钮 之前校验不通过就不提交到后台
                handler: function(){
                	if(!Ext.getCmp("carfileNotepad-form").getForm().isValid())return;
                    Ext.getCmp("carfileNotepad-form").getForm().submit({
                        url: 'car/saveNotepad.action',
                        method: 'POST',
                        disabled:true,
                        waitMsg: mbLocale.waitingMsg,                       
                        success: function(form, action) {
                            obj = Ext.util.JSON.decode(action.response.responseText);
                            showMsg('success', obj.message); 
                            expiredBackWindow.hide();//1-17小马新加
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
            text: '关闭',
            scope:this,
            handler: function(){expiredBackWindow.hide();}
        }]
        }]
    });    

	// 右键菜单
//	var popMenu = new Ext.menu.Menu({
//		id : 'popMenu0',
//		items : [{
//			text:'车辆签证',
//			iconCls :'edit-icon',
//			scope:this,
//			handler:showAddCheckFn
//		},{
//			text:'签证打印',
//			iconCls :'edit-icon',
//			scope:this,
//			handler:showPrintWindowFn
//		},{
//			text:'超期联系单打印',
//			iconCls:'edit-icon',
//			scope:this,
//			handler:showPrintExpiredFn
//		},{
//			text:'加注车辆状况',
//			iconCls :'edit-icon',
//			scope:this,
//			handler:showExpiredBackWindowFn       		
//		}]
//	});
//	function showPopMenuFn(grid,rowindex, e) {
//		this.getSelectionModel().selectRow(rowindex);//
//		e.preventDefault();
//		popMenu.showAt(e.getXY());
//	}  	
//	this.addListener('rowcontextmenu', showPopMenuFn);// 右键菜单代码关键部分
   
    // 双击二级维护column，自动填满check界面，若无匹配，提示，
 	origCheckGridPanel.addListener('rowdblclick', saveCheckDblClickFn);// 选入一条填入并且点击保存
 	origCheckGridPanel.addListener('rowclick', selectCheckFn);// 选中一条填入
	function saveCheckDblClickFn(){
    	if(postSMCheck.getCount()==1){ 
    	 	clickSet();
		}else{ 
			GOnlyOneSelectedFn(postSMCheck.getCount());
			return;
		}
    	saveCheckFn();
    }
 	function selectCheckFn(){ 
        if(postSMCheck.getCount()==1){ 
			clickSet();
		 	onHegeBlur();
		}
//		else{ 
//			GOnlyOneSelectedFn(postSMCheck.getCount());
//		}
    };
    function clickSet(){
	    var record = origCheckGridPanel.getSelectionModel().getSelected();
	 	var theForm = Ext.getCmp("addCheckFormID").getForm();
//		Ext.getCmp("addCheckFormID").getForm().loadRecord(record);
//	 	theForm.findField('carId').setValue(record.get('id'));
	 	theForm.findField('jianTime').setValue(record.get('jianTime'));
//	 	theForm.findField('licenseNumber').setValue(record.get('licenseNumber'));
//	 	theForm.findField('paiSe.id').setValue(record.get('paiSe.id'));
	 	theForm.findField('heGe').setValue(record.get('heGe'));
	 	theForm.findField('cheXiu').setValue(record.get('cheXiu'));
	 	theForm.findField('testKind').setValue(record.get('testKind'));
	 	if(record.get('testKind')==1){
	 		theForm.findField('cheDengji').setDisabled(false);
	 		theForm.findField('cheDengji').setValue(record.get('cheDengji'));
	 	}else{
	 		theForm.findField('cheDengji').setDisabled(true);
	 	}
    }
	    function saveCheckFn(){
    	var theForm = Ext.getCmp("addCheckFormID").getForm(); 
    	if(theForm.findField('testKind').getValue()==''){//||theForm.findField('heGeId').getValue()==''
			showMsg('提示', '维护类型不能为空!'); 
			return;
		}
		if(theForm.findField('testKind').getValue()==1&&theForm.findField('cheDengji').getValue()==''){
			Ext.MessageBox.alert('提示', '技术等级不能为空!'); 
			return;
		}
// var record = this.getSelectionModel().getSelected();
// var carId = record.get('id');
        theForm.submit({
        	url: 'check/addCheck.action',
            method: 'POST',
            scope:this,
            disabled:true,
            waitMsg: mbLocale.waitingMsg,
            success:function(form,action) {
            	showMsg('温馨提示', '保存成功!'); 
    			addCheckWindow.hide();
            	showPrintWindowFn();
            },       
            failure: function(form, action) {
                obj = Ext.util.JSON.decode(action.response.responseText);
                
                if(obj==null||obj.type==null)return;
                
                if(obj.type==100){
                	alert(obj.message);
                }else if(obj.type==0){
//                	alert('合格证号不能为空');
                	var msg = "合格证号为空，是否保存？";
				    ans = confirm(msg);
				    if (ans) {
				        theForm.findField('forceSubmit').setValue(1);
						saveCheckFn();
						return;
				    }
                }else if(obj.type==1){
                	
                	var msg = "此合格证已注销，是否保存？";
				    ans = confirm(msg);
				    if (ans) {
				        theForm.findField('forceSubmit').setValue(1);
						saveCheckFn();
						return;
				    }
//					Ext.MessageBox.confirm("此合格证已注销", "是否保存？", function(id){   
//						if(id=='yes'){
//							theForm.findField('forceSubmit').setValue(1);
//							saveCheckFn();
//							return;
//						}
//			  		});
                }else if(obj.type==2){
					var msg = "库存查无此合格证，是否保存？";
				    ans = confirm(msg);
				    if (ans) {
				        theForm.findField('forceSubmit').setValue(1);
						saveCheckFn();
						return;
				    }                	
//					Ext.Msg.confirm("无此合格证", "是否保存？", function(id){   
//						if(id=='yes'){
//							theForm.findField('forceSubmit').setValue(1);
//							saveCheckFn();
//							return;
//						}
//			  		});
                }
//                showMsg('failure', obj.message);//会弹出来
            }
        });
    };
  // 通过checkbox选择或反选时触发选区轮换的一个制定选区模型
	var mainGridPanelSM = new Ext.grid.CheckboxSelectionModel();
	this.addListener('rowdblclick', showAddCheckFn);
	this.addListener('rowclick', decideButtonEnableFn);
	function showAddCheckFn(){ 
        if(mainGridPanelSM.getCount()==1){ 
        	var theForm = Ext.getCmp("addCheckFormID").getForm(); 
    	 	var record = this.getSelectionModel().getSelected();	
		 	addCheckWindow.show();        	 			
		 	theForm.reset();
			var carId = record.get('id');
			var carpaiSe = record.get('paiSe.id');
			var licenseNumber = record.get('licenseNumber');
			theForm.findField('carId').setValue(carId);
			theForm.findField('paiSe.id').setValue(carpaiSe);
			theForm.findField('licenseNumber').setValue(licenseNumber);
			theForm.findField('forceSubmit').setValue(0);
//		 	theForm.loadRecord(record);
		    postcheckstore.load({
		    	params:{start:0,limit:pageSize,carId:carId},
		    	callback:function(r,options,sucess){
		    		var PHStr = licenseNumber.substring(3,licenseNumber.length);
		    		var PSId = carpaiSe;
				    if(sucess&&postcheckstore.getCount()>0){
				    	for(var i=0;i<postcheckstore.getCount();i++){
				    		var re = postcheckstore.getAt(i);
				    		var pH = re.get('paiHao');
				    		var pS = re.get('paiSe.id');
				    		try{
					    		if(PHStr==pH.substring(1,pH.length)&&pS==PSId){
					    			postSMCheck.selectRow(i);
					    			origCheckGridPanel.fireEvent('rowclick');
					    			break;
					    		}
				    		}catch(e){alert(e.message)}
				    	}
				    }	
			    }
		    });// [testKind,2,=],
//        	erweiGridPanel.getStore().load(
//        		{params:{start:0,limit:pageSize,queryString:'[isPost,1,=],[carId,'+carfileId+',=],[testKind,2,=]'}					    
//				    });// [testKind,2,=],        		
//        	dengjiGridPanel.getStore().load(
//        		{params:{start:0,limit:pageSize,queryString:'[isPost,1,=],[carId,'+carfileId+',=],[testKind,1,=]'}
//				    });// [testKind,1,=],	
		//获取车辆的notepad
			Ext.Ajax.request({   
				url:'car/getNotepad.action?id='+carId,
				success:function(response){
					
//					var record = origCheckGridPanel.getSelectionModel().getSelected();
	 				var theForm = Ext.getCmp("addCheckFormID").getForm();
				 	
//					var comp = Ext.getCmp('checkNotpad');
					obj = Ext.util.JSON.decode(response.responseText);
					if(!obj.success){
	                	showMsg('failure', obj.message);
	                	return false;
					}else{
//						comp.setValue(obj.message);
						theForm.findField('notepad').setValue(obj.message);
					}
				},
				failure: function(response) {
					showMsg('failure', response.responseText);
				}
			});
         }else{GOnlyOneSelectedFn(mainGridPanelSM.getCount());}
    };
    function decideButtonEnableFn(grid,rowIndex,e){
//    	var record = grid.store.getAt(rowIndex);
    	if(this.getSelectionModel().getCount()==1){
    		var record = this.getSelectionModel().getSelected(); 
	    	if(record.get('carStatus')!=0){
	    		Ext.getCmp('printExpiredBtnId').disable();
	    	}else{
		    	if(record.get('expired')==2&&record.get('approval')==0){
		    		Ext.getCmp('printExpiredBtnId').enable();
		    	}else{
		    		Ext.getCmp('printExpiredBtnId').disable();
		    	}
	    	}
    	}else{
    		Ext.getCmp('printExpiredBtnId').disable();
    	}
    }
    function showPrintWindowFn(){
    	if(mainGridPanelSM.getCount()==1){
//    		var record = this.getSelectionModel().getSelected();
    		var record = mainGridPanelSM.getSelected();
			var carId = record.get('id');
			window.carId=carId;
//        	var id = this.selModel.selections.keys;
			micrite.util.genWindow({
				modal:true,
				maximizable:false,
	            title    : this.printPreviewTxt, 
	            autoLoad : {url: 'car/erweiPrintPanel.js',scripts:true},  
				width : 660,
				height : 500
	        });
    	}else{
    		GOnlyOneSelectedFn(mainGridPanelSM.getCount());
    	}
	};
	function showPrintExpiredFn(){
		if(mainGridPanelSM.getCount()==1){
	 	var id = this.selModel.selections.keys;
		micrite.util.genWindow({
			modal:true,
			maximizable:false,
            id       : 'printExpiredWindowID',
            title    : this.printPreviewTxt, 
            autoLoad : {url: 'printPaper.action?id='+id,scripts:true},  
            width    : 650,
            height   : 420
        }); 
		}else{
			GOnlyOneSelectedFn(mainGridPanelSM.getCount());
		}
	};// eof handler
	function showExpiredBackWindowFn(){						
	     if(mainGridPanelSM.getCount()==1){
	     	var theForm=Ext.getCmp("carfileNotepad-form").getForm();
			//var carId = this.selModel.selections.keys;
			var record = this.getSelectionModel().getSelected();	
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
//						          {name:this.colModelLicenseNumber,value:'licenseNumber',xtype:'textfield'},
//						          {name:'营运证号',value:'yingyunNo',xtype:'textfield'},
//						          {name:'车牌所属业户',value:'owner',xtype:'textfield'},
//						          {name:'业户拼音',value:'py',xtype:'textfield'}						          
						      ]],	  
	        compSet: [
	             {url:0,reader:0,columns:0,bbarAction:0},
	             {url:1,reader:1,columns:1,bbarAction:0,tbarAction:-1}
	        ],
			searchMenu : [
				 '车牌号码',
				 '车主搜索'
			],
			searchFields :[[
				 {advSearch:true},
	             this.colModelLicenseNumber,
	             {xtype:'textfield',
				  name:'licenseNumber',
	              expression:'like',
	              value:'浙B.',
	              width:120
	              }
	            ],
	            [{advSearch:true},'车主姓名',
						{	
	            			xtype:'combo',
	            			anchor:'90%',
							name:'carowner.id',
							hiddenName:'carowner.id',
							width:160,
//							typeAheadDelay:500,
							allowBlank:true,
							forceSelection:true,
							typeAhead: false,
							editable:true,
//							pageSize:2,
	            			triggerAction:'all',
	            			allQuery:this.value,
	            			queryParam:'py',
	            			selectOnFocus:false,
							emptyText:'全部',
							minChars:2,
							store:	storeCarowners,
							loadingText:'加载中……',
							valueField:'id',
							displayField:'name',
							mode:'remote',
							listeners:{
//								'keyup':function(com2,e) {	
//									if(e.getKey() == Ext.EventObject.DOWN||e.getKey() == Ext.EventObject.UP) {
//										//e.stopEvent();
//									}else{
//										var key = com2.getRawValue();
//										if(key != ''&&key!=0){
//											var value = '[py,'+key+',like]';
//											com2.store.setBaseParam('queryString',value);
//										}else{
//											com2.store.setBaseParam('queryString','');
//										}
//										com2.store.reload({params:{start:0,limit:20}});
//									}
//									e.stopEvent();
//								}
							}
	            		}
				 
//	             '车主名称',
//	             {xtype:'textfield',
//	              name:'carowner.name',
//	              expression:'like',
//	              width:120}
	            ]         
	            ],
	        urls: ['car/findCarfile.action','car/findCarfile.action'],
	        readers : [GCarFields,GCarFields],  
			columnsArray: [GCarColumns,GCarColumns],
	         tbarActions : [[	
	         {
	        	 text:this.carTypePieChart,
	        	 iconCls :'pie-chart-icon',
	        	 scope:this,
	        	 handler:this.getPieChart
	         }
	         ]],
	         bbarActions:[[
	         {
				text:'车辆签证',
				iconCls :'edit-icon',
				scope:this,
				handler:showAddCheckFn
			},{
				text:'签证打印',
				iconCls :'edit-icon',
				scope:this,
				handler:showPrintWindowFn
			},{
				id:'printExpiredBtnId',disabled:true,
				text:'超期联系单打印',
				iconCls:'edit-icon',
				scope:this,
				handler:showPrintExpiredFn
			},{
				text:'车辆状况备注',
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
		micrite.car.maintainList.SearchPanel.superclass.initComponent.apply(this, arguments);
    }, // eo function initComponent
    
	getPieChart : function() {
		var c1 = {id:'maintainList.piechar',
		          title:this.carTypePieChart};
        var c2 = {
            url: 'car/getCarTypePieChart.action',
            params:this.getAllField()
        };
        this.genChartWindow(c1,c2);
	}// eof getPieChart

}); // eo extend

// 处理多语言
try {maintainListLocale();} catch (e) {alert(e);}
Ext.onReady(function() {
	Ext.QuickTips.init();
	var formPanel = new micrite.car.maintainList.SearchPanel();	
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
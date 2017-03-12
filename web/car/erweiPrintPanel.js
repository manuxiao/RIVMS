<script type="text/javascript">
Ext.ns('micrite.car.maintainList');
micrite.car.maintainList.PrintPanel = function(){
	
	var pageSize = parseInt(Ext.getDom('pageSize').value,10);
	var carId=window.carId;

	function setPrintIndex(type,comp,carfileId){
		Ext.Ajax.request({   
			url:'car/getPrintIndex.action?type='+type+'&carId='+carfileId,
			success:function(response){
				obj = Ext.util.JSON.decode(response.responseText);
				if(!obj.success){
                	showMsg('failure', obj.message);
                	return false;
				}else{
					comp.setValue(obj.message);
					if(type==1){
						if(obj.message==0){
							checkboxWithCarInfo.getEl().dom.checked=true;
							checkboxWithCarInfo.checked=true;
						}else{
							checkboxWithCarInfo.getEl().dom.checked=false;
							checkboxWithCarInfo.checked=false;
						}
					}
				}
			},
			failure: function(response) {
				showMsg('failure', response.responseText);
			}
		});
	};
	
			//二维打印index
	var comboPrintIndex2 = new Ext.form.ComboBox({
            	name:'printIndex2',
                store: new Ext.data.SimpleStore({
                    fields: ['key', 'value'],
                    data : [['0','1'],['1','2'],['2','3'],['3','4'],['4','5'],['5','6'],['6','7'],['7','8']]
                }),
                width:30,
                valueField:'key',
                displayField:'value',
                hiddenName:'printIndex2',
                fieldLabel:'打印位置',
                typeAhead: true,
                mode: 'local',
                triggerAction: 'all',
                emptyText:'请选择',
                selectOnFocus:true,
                allowBlank:false,
                forceSelection:true,
                editable:false
            });
        var checkboxWithCarInfo = new Ext.form.Checkbox({ 
//			id : "withCarInfoId", 
//			name : "withCarInfo", 
			autoScroll : false, 
			width : 120, 
			boxLabel : '含车信息', 
			inputValue : 1, 
//			anchor : "90%", 
			hideLabel : true 
		});	
        //技评等级index
        var comboPrintIndex1 = new Ext.form.ComboBox({
        	name:'printIndex1',
            store: new Ext.data.SimpleStore({
                fields: ['key', 'value'],
                data : [['0','1'],['1','2']]
            }),
            width:30,
            valueField:'key',
            displayField:'value',
            fieldLabel: '打印位置',
            hiddenName:'printIndex1',
            typeAhead: true,
            mode: 'local',
            triggerAction: 'all',
            emptyText:'请选择',
            selectOnFocus:true,
            allowBlank:false,
            forceSelection:true,
            editable:false,
            listeners:{
            	select:function(){
            		if(this.getValue()==0){
						checkboxWithCarInfo.getEl().dom.checked=true;
						checkboxWithCarInfo.checked=true;
					}else{
						checkboxWithCarInfo.getEl().dom.checked=false;
						checkboxWithCarInfo.checked=false;
					}
            	}
            }
          });

	var dengjiSMCheck = new Ext.grid.CheckboxSelectionModel();
	var dengjiCM = new Ext.grid.ColumnModel({
		columns:[	
        	dengjiSMCheck,
			new Ext.grid.RowNumberer(),
			{
				header:'序号',dataIndex:'id',sortable:true,hidden:true
        	},{									
				header: '有效期止',
				width: 100, sortable: true, dataIndex: 'endTime'
			},{
		        header: '技术等级',
		        width: 100, sortable: true, dataIndex: 'cheDengji'
//				editor:comboSkillRank,renderer:myRenderer
		     },{
				header: '合格证号',
				width: 100, sortable: true, dataIndex: 'heGe'
			},{
				header: '车牌号码',
				width: 100, sortable: true, dataIndex: 'paiHao'
			},{
				header: '车牌颜色',
				width: 80, sortable: true, dataIndex: 'paiSe.name'
			},{
				header: '维修企业',
				width: 120, sortable: true, dataIndex: 'cheXiu'			   
			}
		]
	});
	var erweiSMCheck = new Ext.grid.CheckboxSelectionModel(); 
	var erweiCM = new Ext.grid.ColumnModel({
		columns:[	
        	erweiSMCheck,
			new Ext.grid.RowNumberer(),{
				header:'编号',dataIndex:'id',sortable:true,hidden:true
        	},{									
				header: '检测日期',
				width: 100, sortable: true, dataIndex: 'jianTime'
			},{
				header: '有效期止',
				width: 100, sortable: true, dataIndex: 'endTime'
			},{
				header: '合格证号',
				width: 100, sortable: true, dataIndex: 'heGe'
			},{
				header: '车牌号码',
				width: 100, sortable: true, dataIndex: 'paiHao'
			},{
				header: '车牌颜色',
				width: 80, sortable: true, dataIndex: 'paiSe.name'
			},{
				header: '维修企业',
				width: 120, sortable: true, dataIndex: 'cheXiu'		   
			}
		]
	});
	var erweiStore = new Ext.data.JsonStore({
		id:'erweiStoreID',
		sortInfo : {field : 'jianTime',direction : 'desc'},
   		url:'check/findCheck.action',
   		baseParams:{start:0,limit:pageSize,queryString:'[isPost,1,=],[carId,'+carId+',=],[testKind,2,=]'},
		root:'data',
		fields:[
				{name: 'id'},
	            {name: 'jianTime'},
	            {name: 'endTime'},
	            {name: 'paiHao'},
	            {name: 'heGe'},
	            {name: 'printed'},
            	{name: 'carId'},
            	{name: 'cheXiu'},
            	{name: 'status'},
            	{name: 'createDate'},
	            {name: 'paiSe.id'},
	            {name: 'paiSe.name'},
	            {name: 'notepad'}
			],
		totalProperty:'totalCount'
	});	
	// ------大厅-等级------------------------
	var dengjiStore = new Ext.data.JsonStore({
		id:'dengjiStoreID',
		sortInfo : {field : 'endTime',direction : 'desc'},
   		pruneModifiedRecords:true,
   		url:'check/findCheck.action',
   		baseParams:{start:0,limit:pageSize,queryString:'[isPost,1,=],[carId,'+carId+',=],[testKind,1,=]'},
		root:'data',
		fields:[
				{name: 'id'},
	            {name: 'endTime'},
	            {name: 'cheDengji'},
	            {name: 'paiHao'},
	            {name: 'heGe'},
	            {name: 'printed'},
	            {name: 'carId'},
	            {name: 'isPost'},
	            {name: 'cheXiu'},	            
	            {name: 'testKind'},
	            {name: 'paiSe.id'},
	            {name: 'paiSe.name'},
	            {name: 'notepad'}				
			],
		totalProperty:'totalCount'
	});
	// /---------大厅-二级维护记录---tab3---///
	var erweiGridPanel = new Ext.grid.GridPanel({
		id:'egp03',
        border:false,
        stripeRows:true,
//        view:new Ext.ux.GridViewPostNot2({forceFit:true}),
        selModel:new Ext.grid.RowSelectionModel(),
        tbar:[['-',{
					text:'打印二维记录',
					iconCls:'door-out-icon',
					scope:this,
					handler:function() {
//						var id = this.selModel.selections.keys;
						if(!carId)return;
						var selections=erweiGridPanel.getSelectionModel().getSelections();
						var checkIds = new Array(selections.length);
						var checkIdsStr='';
						if(checkIds.length<=0){
							Ext.MessageBox.alert('提示', '请选择检测记录！');
							return; 
						}
						if(checkIds.length>8){
							Ext.MessageBox.alert('提示', '最多只能选8条记录！');
							return; 
						}
						for(var i=0;i<checkIds.length;i++) {
							checkIds[i] = selections[i].get('id');
							checkIdsStr+='checkIds='+checkIds[i];
							if(i<checkIds.length-1){
								checkIdsStr+='&';
							}
						}
//						alert(carId+','+checkIdsStr+',printIndex=');
						micrite.util.genWindow({
							modal:true,
							maximizable:false,
				            id       : 'printErweiWindowID',
			                title    : this.printPreviewTxt, 
			                autoLoad : {url: 'print2.action?id='+carId+'&'+checkIdsStr+'&printIndex='+comboPrintIndex2.getValue(),scripts:true},  
			                width    : 420,
			                height   : 360
			            });
            			        
					}  // eof handler
				},'-',
				['打印位置',comboPrintIndex2]	
        	]],
       	cm:erweiCM,
        store:erweiStore,
        sm:erweiSMCheck,
        listeners: {
        	beforeshow:function(){
        		setPrintIndex(2,comboPrintIndex2,carId);
			    erweiGridPanel.getStore().on("load",function(){
					setPrintedSelection(this);
				});
        	},
           render: function(g){
                var view = g.getView();    // Capture the GridView.
                var store = g.getStore();
                var rowTip = new Ext.ToolTip({
                    target: view.mainBody,    // The overall target element.
                    delegate: '.x-grid3-row', // Each grid row causes its own												// seperate show and hide.
                    trackMouse: true,         // Moving within the row should												// not hide the tip.
                    renderTo: document.body,  // Render immediately so that												// tip.body can be
                    listeners: {  // Change content dynamically depending on
									// which element triggered the show.
                        beforeshow: function updateTipBody(tip){
                            var rowIndex = view.findRowIndex(tip.triggerElement);
                            var record = store.getAt(rowIndex);
                            if(record.get('printed') == 1){                           	
                     			tip.body.dom.innerHTML = "<b>此检测记录已打印<br/>";
                 			}else{
                     				return false; // 停止执行，从而禁止显示Tip
                     				tip.destroy();
                 			}
            			}     
        			}                    
                });
            }
        }
    });

  // ---------大厅-等级评定记录-tab2----------------------
    var dengjiGridPanel = new Ext.grid.GridPanel({
		id:'egp02',
        border:false,
        stripeRows:true,
//        view:new Ext.ux.GridViewPostNot1({forceFit:true}),
        selModel:new Ext.grid.RowSelectionModel(),
        tbar:[['-',{
					text:'打印等评记录',
					iconCls:'edit-icon',
					scope:this,
					handler:function() {
//						var id = this.selModel.selections.keys;
						if(!carId)return;
						var selections=dengjiGridPanel.getSelectionModel().getSelections();
						var checkIds = new Array(selections.length);
						var checkIdsStr='';
						if(checkIds.length<=0){
							Ext.MessageBox.alert('提示', '请选择检测记录！');
							return; 
						}
						if(checkIds.length>2){
							Ext.MessageBox.alert('提示', '最多只能选2条记录！');
							return; 
						}
						for(var i=0;i<checkIds.length;i++) {
							checkIds[i] = selections[i].get('id');
							checkIdsStr+='checkIds='+checkIds[i];
							if(i<checkIds.length-1){
								checkIdsStr+='&';
							}
						}
						var withCarInfo = 0;
						if(checkboxWithCarInfo.checked)withCarInfo=1;
						micrite.util.genWindow({
							modal:true,
							maximizable:false,
				            id       : 'printDengjiWindowID',
			                title    : this.printPreviewTxt, 
			                autoLoad : {url: 'print1.action?id='+carId+'&printIndex='+comboPrintIndex1.getValue()+'&'+checkIdsStr+'&withCarInfo='+withCarInfo,scripts:true},  
			                width    : 420,
			                height   : 360
			            });
					}  // eof handler
				},'-',
				['打印位置',comboPrintIndex1],'-','-',
//				comboPrintIndex1,'-',
				checkboxWithCarInfo
        	]],
       	cm:dengjiCM,
        store:dengjiStore,
        sm:dengjiSMCheck,
        listeners: {
           render: function(g){
                var view = g.getView();    // Capture the GridView.
                var store = g.getStore();
                var rowTip = new Ext.ToolTip({
                    target: view.mainBody,    // The overall target element.
                    delegate: '.x-grid3-row', // Each grid row causes its own												// seperate show and hide.
                    trackMouse: true,         // Moving within the row should												// not hide the tip.
                    renderTo: document.body,  // Render immediately so that												// tip.body can be
                    listeners: {  // Change content dynamically depending on
									// which element triggered the show.
                        beforeshow: function updateTipBody(tip){
                            var rowIndex = view.findRowIndex(tip.triggerElement);
                            var record = store.getAt(rowIndex);
                            if(record.get('carId') == 0){                           	
                     			tip.body.dom.innerHTML = "<b>没有找到匹配的车辆！<br/><br>车辆号码:"+record.get('paiHao')+"</br><br>车辆颜色:"+record.get('paiSe.id');
                 			}else{
                 				return false; // 停止执行，从而禁止显示Tip
                 				tip.destroy();
                 			}
            			}     
        			}
                });
            }
        }
    });
    function setPrintedSelection(gridPanel){
//    	alert('this is setPrintedSelection fn.');
    	gridPanel.getSelectionModel().clearSelections();
    	var rowCount = gridPanel.getStore().getCount();
		for(var i = 0; i < rowCount; i++) {
			var record = gridPanel.getStore().getAt(i);
			
			if(record.get('printed') == 0) {
				gridPanel.getSelectionModel().selectRow(i,true);
			}
//			else{
//				gridPanel.getSelectionModel().selectRow(i,false);
//			}
		}
    }
    
	Ext.form.Field.prototype.msgTarget = 'side';    
    micrite.car.maintainList.PrintPanel.superclass.constructor.call(this, {
    	modal:true,
	   	title:'车辆维护记录打印界面',
		closeAction:'hide',
		padding:'5px',
		width : 660,
		height : 500,
        items: [{
			xtype : 'tabpanel',
			plain : true,
			deferredRender:false,
			bodyBorder : false,
			activeTab : 0,
			id:'checkreadWin-form',
			height : 420,
			width : 630,
			defaults : {bodyStyle : 'padding:2px'},
			items : [ 
				{title : '二级维护',layout : 'fit',items :[erweiGridPanel],listeners:{activate:erweiTabPanelActivateFn}},
				{title : '等级评定',layout : 'fit',items :[dengjiGridPanel],listeners:{activate:dengjiTabPanelActivateFn}}
			]
		}],
        buttonAlign:'center',
        buttons: [{text: '关闭',scope:this,handler: function(){Ext.WindowMgr.getActive().close();}}]
    });  
    function erweiTabPanelActivateFn(panel){
    	if(carId){
//    		alert('this is tabPanelActivateFn');
    		setPrintIndex(2,comboPrintIndex2,carId);
		    panel.get(0).getStore().on("load",function(){
				setPrintedSelection(panel.get(0));
			});
    		panel.get(0).getStore().load();
    	}
    }
    function dengjiTabPanelActivateFn(panel){
    	if(carId){
//    		alert('this is tabPanelActivateFn');
    		setPrintIndex(1,comboPrintIndex1,carId);
		    panel.get(0).getStore().on("load",function(){
				setPrintedSelection(panel.get(0));
			});
    		panel.get(0).getStore().load();
    	}
    }
    
	if(carId){
	
	}else{
		alert('unknow car');
	}
};
Ext.extend(micrite.car.maintainList.PrintPanel, Ext.Panel, {});
try{ printPanelLocale(); } catch(e){alert(''+e);}

Ext.onReady(function(){
     Ext.QuickTips.init();
     Ext.form.Field.prototype.msgTarget = 'side';
     Ext.WindowMgr.getActive().add(new micrite.car.maintainList.PrintPanel());
     Ext.WindowMgr.getActive().doLayout();
	
});
</script>
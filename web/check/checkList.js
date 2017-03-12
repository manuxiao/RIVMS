<script type="text/javascript">
Ext.namespace('micrite.check.checkList');
micrite.check.checkList.SearchPanel = Ext.extend(micrite.ComplexGrid, {	
	initComponent:function() {	
	Ext.ux.GridViewHeGeZheng2=Ext.extend(Ext.grid.GridView,{   
        getRowClass:function(record,index){      
            if(record.get('carId') == 0) {
            	return 'red-row';	
            }else{  
            	if(record.get('status') == 1)
            		return 'gray-row';
                return 'white-row';
            }
        }
    });
var searchField1 = [
				 {advSearch:true},
	             this.colModelHeGe,
	             {xtype:'textfield',
	              name:'heGe',
	              expression:'=',
	              width:80
	              },{
	              xtype:'hidden',name:'isPost',
	              expression:'=',value:'0'},
				 '检测日期',
				{
					name:'jianTime',
					xtype:'datefield',
					format:'Y-m-d',
					expression:'>=',
					value:preDate()
				},
				'--',
				{
					name:'jianTime',
					xtype:'datefield',
					format:'Y-m-d',
					expression:'<=',
					value:new Date()
				},
				'车牌号',
				{	xtype:'textfield',
        			name:'paiHao',
              		expression:'like',
              		width:50
				},
				'维修企业',
				{	xtype:'textfield',
        			name:'cheXiu',
              		expression:'like',
              		width:100
				}
	            ];
	var sm = new Ext.grid.CheckboxSelectionModel();
	var config = {
			view:new Ext.ux.GridViewHeGeZheng2({forceFit:true}),
			advSearchField : [[]],	  
	        compSet: [
	             {url:0,reader:0,columns:0,bbarAction:0}
//	             {url:1,reader:0,columns:0,bbarAction:0,tbarAction:-1},
//	             {url:2,reader:0,columns:0,bbarAction:0,tbarAction:0},	
//	             {url:3,reader:0,columns:0,bbarAction:0,tbarAction:1}	
//	             {url:0,reader:0,columns:0,bbarAction:-1,tbarAction:0,advField:0};
//	             浙B.T5002
	        ],
			searchMenu : [			 
//				 '二维操作记录',
				 '检测站记录'
//				 '车辆记录',
//				 '企业记录'
			],
			searchFields :[searchField1],
	            urls: ['check/findCheck.action','check/findCheck.action','car/findCarfile.action','enterprise/findEnterprise.action'],
	        readers : [[
	        	{name: 'id'},
			     {name: 'jianTime'},
			     {name: 'testNo'},
			     {name: 'cheZhu'},
			     {name: 'cheXiu'},
			     {name: 'paiHao'},
			     {name: 'cheLei'},
			     {name: 'heGe',mapping : 'heGe'},
			     {name: 'paiSe.id'},
			     {name: 'paiSe',mapping : 'paiSe.name'},
			     {name: 'testKind'},//1-dengji
			     {name: 'carId'},//0-not found car
			     {name: 'status'},
			     {name: 'createDate'}
	        ]    
	        ],
			columnsArray: [[
				  {
					header: this.colModelJianTime,
					width: 100, sortable: true, dataIndex: 'jianTime'
				  },			                
		          {
		          	header: this.colModelTestNo,
		          	width: 100, sortable: true, dataIndex: 'testNo',
		          	editable : false
		          },{
				    header: this.colModelHeGe,
				     width: 100, sortable: true, dataIndex: 'heGe',
				    editable : false
				 },{
					   header: this.colModelCheXiu,
					   width: 100, sortable: true, dataIndex: 'cheXiu',
					   editable : false
				 },{
			       header: this.colModelPaiHao,
			       width: 100, sortable: true, dataIndex: 'paiHao',
			       editable : false
			     },{
				   header: this.colModelPaiSe,
				   width: 100, sortable: true, dataIndex: 'paiSe',
					editable : false				   
				 },{
				    header: this.colModelCheLei,
				     width: 100, sortable: true, dataIndex: 'cheLei',
				    editable : false
				 },{
					   header: this.colModelCheZhu,
					   width: 100, sortable: true, dataIndex: 'cheZhu',
					   editable : false
				 },{
					   header: this.colModelCreateDate,
					   width: 100, sortable: true, dataIndex: 'createDate',
					   editable : false
				 },
		    	sm
			 ]
			 ],
	         bbarActions:[['提示 （<span class="red-row">红色：未匹配到车辆</span>','-','<span class="gray-row">灰色：被引用了的</span>','-','<span class="white-row">白色：未使用</span>）']],	       
//	         cm:cmcar,
	         sm:sm
	    }; // eo config object
	    
		// apply config
		Ext.apply(this, Ext.apply(this.initialConfig, config)); 
		micrite.check.checkList.SearchPanel.superclass.initComponent.apply(this, arguments);
    }, // eo function initComponent	
  
	addcheck : function() {
			var win = micrite.util.genWindow({
	            id       : 'addCheckWindow',
                title    : this.newCheck,
                autoLoad : {url: 'check/checkDetail.js',scripts:true},
                width    : 420,
                height   : 360
            });  
	}, //eof addcheck
	
	deletecheck : function() {
		var checkIds = this.selModel.selections.keys;
		var deleteRolesFun = function(buttonId, text, opt) {
			if (buttonId == 'yes') {
				micrite.util.ajaxRequest({
                    url: 'check/deleteCheck.action',
                    params:{'checkIds':checkIds},
                    success:function(r,o){
                        this.store.reload();
                    },
                    failure:Ext.emptyFn
                },this);
			}
		};
		Ext.Msg.show({
			title:mbLocale.infoMsg,
			msg: mbLocale.delConfirmMsg,
			buttons: Ext.Msg.YESNO,
			scope: this,
			fn: deleteRolesFun,
			icon: Ext.MessageBox.QUESTION
		});        
	}//eof deletecheck
}); //eo extend

try {checkListLocale();} catch (e) {}

Ext.onReady(function() {
	Ext.QuickTips.init();
	var formPanel = new micrite.check.checkList.SearchPanel();
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
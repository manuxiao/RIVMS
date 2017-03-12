<script type="text/javascript">
Ext.namespace('micrite.patrolRiverSummary.patrolRiverSummaryList');

micrite.patrolRiverSummary.patrolRiverSummaryList.SearchPanel = Ext.extend(micrite.ComplexEditorGrid, {
    initComponent:function() { 
    var sm = new Ext.grid.CheckboxSelectionModel();

        var config = {
        	 view:new Ext.grid.GridView({forceFit:false}),
            compSet:[{url:0,reader:0,columns:0,bbarAction:0}],
            searchFields :[[
                '填报人', {xtype:'textfield', name:'patrolRiverSummary.EditorId', width:130}
            ]],
            urls: ['findPatrolRiverSummary.action'],
            readers : [[
                {name: 'townRiverChief'},
                {name: 'villageRiverChief'},
                {name: 'RiverChiefUnit'},
                {name: 'RiverChief'},
                {name: 'publicSignDamage'},
                {name: 'FloatGarbage'},
                {name: 'ZaWuDuiFang'},
                {name: 'LuanDaJian'},
                {name: 'JianYiHanCe'},
                {name: 'PaiWuBZWeiQuan'},
                {name: 'GongYeZhiPai'},
                {name: 'NongYeZhiPai'},
                {name: 'QingLiLaJi'},
                {name: 'QingJieGongShiPai'},
                {name: 'QingLiLuanDuiFang'},
                {name: 'ShangMenXC'},
                {name: 'FaFangZiLiao'},
                {name: 'approveDemo'},
                {name: 'approveStatus'},
                {name: 'approveDate'},
                {name: 'approveId'},
                {name: 'createDate'},
                {name: 'createrId'},
                {name: 'editDate'},
                {name: 'editorId'},
                {name: 'Activity1'}
            ]],
            columnsArray: [[
                 sm,  

                {header: '填报人', width: 60, sortable: true, dataIndex: 'createrId'},
                {header: '填报时间', width: 80, sortable: true, dataIndex: 'createDate'},                
                {header: '审批时间', width: 80, sortable: true, dataIndex: 'approveDate'},
                {header: '审批人', width: 60, sortable: true, dataIndex: 'approveId',
                    editor: new Ext.form.TextField({allowBlank: true})},
                {header: '审批状态', width: 80, sortable: true, dataIndex: 'approveStatus',
                    editor: new Ext.form.TextField({allowBlank: true})},
                {header: '审批备注', width: 80, sortable: true, dataIndex: 'approveDemo'},                          
                {header: '镇级巡河', width: 60, sortable: true, dataIndex: 'townRiverChief'},
                {header: '村社区巡河', width: 80, sortable: true, dataIndex: 'villageRiverChief',
                    editor: new Ext.form.TextField({allowBlank: true})},
                {header: '巡河单位', width: 70, sortable: true, dataIndex: 'RiverChiefUnit'},
                {header: '河长单位巡河', width: 90, sortable: true, dataIndex: 'RiverChief'},
                {header: '公示牌污损', width: 70, sortable: true, dataIndex: 'publicSignDamage',
                    editor: new Ext.form.TextField({allowBlank: true})},
                {header: '漂浮垃圾', width: 60, sortable: true, dataIndex: 'FloatGarbage'},
                {header: '岸坡垃圾杂物', width: 90, sortable: true, dataIndex: 'ZaWuDuiFang'},
                {header: '沿河乱搭建', width: 80, sortable: true, dataIndex: 'LuanDaJian'},
                {header: '简易旱厕', width: 70, sortable: true, dataIndex: 'JianYiHanCe',
                    editor: new Ext.form.TextField({allowBlank: true})},
                {header: '入河排污标志未全', width: 110, sortable: true, dataIndex: 'PaiWuBZWeiQuan'},
                {header: '工业污水直排', width: 90, sortable: true, dataIndex: 'GongYeZhiPai'},
                {header: '农业污水直排', width: 90, sortable: true, dataIndex: 'NongYeZhiPai',
                    editor: new Ext.form.TextField({allowBlank: true})},
                {header: '清理垃圾（处，吨）', width: 120, sortable: true, dataIndex: 'QingLiLaJi'},
                {header: '清洁公示牌（块）', width: 110, sortable: true, dataIndex: 'QingJieGongShiPai'},
                {header: '清理乱堆放（处）', width: 110, sortable: true, dataIndex: 'QingLiLuanDuiFang',
                    editor: new Ext.form.TextField({allowBlank: true})},
                {header: '上门宣传（户）', width: 100, sortable: true, dataIndex: 'ShangMenXC'},
                {header: '发放宣传资料（份）', width: 120, sortable: true, dataIndex: 'FaFangZiLiao'},
                {header: '修改时间', width: 80, sortable: true, dataIndex: 'editDate'},
                {header: '修改人', width: 60, sortable: true, dataIndex: 'editId'},
                {header: '活动', width: 200, sortable: true, dataIndex: 'Activity1',
                    editor: new Ext.form.TextField({allowBlank: true})} 
             ]],
             tbarActions : [{
                 text:'新增',
                 iconCls :'add-icon',
                 scope:this,
                 handler:this.addpatrolRiverSummaryFun
             }],
             bbarActions:[[{
                 text:'行政审批',
                 iconCls :'bind-icon',
                 scope:this, 
                 handler:this.approveFun
             },{
                 text:mbLocale.deleteButton, 
                 iconCls :'delete-icon',
                 scope:this, 
                 handler:this.deletepatrolRiverSummaryFun
             },{
                 text:mbLocale.submitButton, 
                 iconCls :'save-icon',
                 scope:this, 
                 handler:this.savepatrolRiverSummaryFun
             }]],
             sm:sm
        }; // eo config object
    Ext.apply(this, Ext.apply(this.initialConfig, config)); 
    micrite.patrolRiverSummary.patrolRiverSummaryList.SearchPanel.superclass.initComponent.apply(this, arguments);
    },

    addpatrolRiverSummaryFun :function() {
        var win = micrite.util.genWindow({
            id: 'addpatrolRiverSummaryWindow',
            title    : this.addpatrolRiverSummary,
            autoLoad : {url: 'patrolRiverSummary/patrolRiverSummaryDetail.js',scripts:true},
            width    : 500,
            height   : 360
        });
    },

    approveFun:function() {
        //  选择的数据记录主键，形如“2, 4, 6, 10”
        var patrolRiverSummaryIds = this.selModel.selections.keys;
        if(patrolRiverSummaryIds.length!=1){
            Ext.MessageBox.alert(mbLocale.infoMsg,mbLocale.gridRowSelectMsg);
            return;
        }
        var authorities = this.selModel.getSelections();
        var win = micrite.util.genWindow({
            id: 'userSelectWindow',
            title    : this.bindRole+' -- '+authorities[0].get('name'),
            autoLoad : {url: 'patrolRiverSummary/roleSelect.jsp?patrolRiverSummaryId='+patrolRiverSummaryIds[0],scripts:true},
            width    : 600,
            height   : 400,
            border   : true
        });
    },

    deletepatrolRiverSummaryFun:function() {
        var patrolRiverSummaryIds = this.selModel.selections.keys;
        if (patrolRiverSummaryIds.length <= 0) {
            Ext.MessageBox.alert(mbLocale.infoMsg, mbLocale.gridMultRowSelectMsg);
            return;
        }             
        var deleteAuthorities = function(buttonId, text, opt) {
            if (buttonId == 'yes') {
                micrite.util.ajaxRequest({
                    url: 'deletepatrolRiverSummary.action',
                    params:{'authIds':patrolRiverSummaryIds},
                    success:function(r,o){
                        var res = Ext.decode(r.responseText);
                        if (res&&res.success){
                            this.store.reload();
                        }
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
            fn: deleteAuthorities,
            icon: Ext.MessageBox.QUESTION
        });        
    },
    
    savepatrolRiverSummaryFun:function() {
        var store = this.getStore();
        var patrolRiverSummary;
        if(store.getModifiedRecords().length!=1){
            Ext.MessageBox.alert(mbLocale.infoMsg,mbLocale.gridRowEditMsg);
            return;
        }
        patrolRiverSummary = store.getModifiedRecords()[0];
        var updateAuthorities = function(buttonId, text, opt) {
            if (buttonId == 'yes') {
                micrite.util.ajaxRequest({
                    url: 'updatepatrolRiverSummary.action',
                    params:{'patrolRiverSummary.id':patrolRiverSummary.id,
                        'patrolRiverSummary.value':patrolRiverSummary.get('value')},
                    success:function(r,o){
                        var res = Ext.decode(r.responseText);
                        if (res&&res.success){
                            this.store.commitChanges();
                        }
                    },
                    failure:Ext.emptyFn
                },this);
            }
        };
        Ext.Msg.show({
            title:mbLocale.infoMsg,
            msg: mbLocale.updateConfirmMsg,
            buttons: Ext.Msg.YESNO,
            scope: this,
            fn: updateAuthorities,
            icon: Ext.MessageBox.QUESTION
        });        
    }    
});

//  处理多语言
try {patrolRiverSummaryListLocale();} catch (e) {}

Ext.onReady(function() {
    Ext.QuickTips.init();
    var formPanel = new micrite.patrolRiverSummary.patrolRiverSummaryList.SearchPanel();
    
    if (mainPanel) {
        mainPanel.getActiveTab().add(formPanel);
        mainPanel.getActiveTab().doLayout();
    } else {
        var vp = new Ext.Viewport({
            layout:'fit',
            items:[formPanel]
        });
    }
});
</script>
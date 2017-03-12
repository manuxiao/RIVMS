function carownerListLocale(){
    if(micrite.crm.carownerList.SearchPanel) {
        Ext.override(micrite.crm.carownerList.SearchPanel, {
        	searchCondition1:'业户名称查询',
        	searchCondition2:'许可证号查询',
        	searchCondition3:'条件分组 3',  
        	searchName:'业户名称',        	
        	searchStartTime:'起始时间',
        	searchEndTime:'截至时间',	        	
            newCarowner:'新增车主',
            listCar:'车辆列表',
            carownerSourceBarChart:'来源分析(柱图)',
            carownerSourcePieChart:'来源分析(饼图)',
            colModelId:'ID',
            colModelLicense:'经营许可证号',
            colModelName:'业户名称',
            colModelMobile:'手机号码',
            colModelTelephone:'联系电话',
            colModelAddress:'联系地址',
            colCreation_ts:'创建时间',
            searchCarownerAddress:'业户地址',
            newButton:'新增',
            listCarButton:'车辆信息'
        });
    }	
}
function carownerDetailLocale(){
    if(micrite.crm.carownerDetail.FormPanel) {
        Ext.override(micrite.crm.carownerDetail.FormPanel, {
            carownerDetailText:'基本信息',
            idText:'ID',
            licenseText:'经营许可证号',
            nameText:'业户姓名',
            mobileText:'手机号码',
            telephoneText:'联系电话',
            addressText:'业户地址',
            comboEmptyText:'选择来源...'
        });
    }    
}

function carownerhistoryLocale(){	
    if(micrite.crm.carownerhistory.SearchPanel) {
        Ext.override(micrite.crm.carownerhistory.SearchPanel, {	        	
            newCarfile:'增加车辆记录',
            carTypePieChart:'车辆类型分析(饼图)',
            colModelOwner:'车牌所属业户',
            colModelTelephone:'业户联系电话',
            colModelMobile:'业户手机',
            colModelCarStatus:'营运状态',
            colModelCarType:'车辆类型',            
            colModelAddress:'业户地址',
            colModelLicenseNumber:'车牌号码',
            colModelLicenseType:'车牌类型',
            colModelLoadTon:'载重/载客',
            colModelBrandType:'品牌型号',
            colModelCarRemark:'车辆备注',
            colModelFuelRank:'燃油类型',
            colModelSkillRank:'技术等级',
            colModelEvaluateDate:'技术等级评定有效期止',
            colModelEvaluateCycle:'技术等级评定周期',
            colModelMaintainDate:'二维操作日期',
            colModelMaintainDateEnd:'二维操作有效期止',
            colModelMaintainCycle:'二维周期',
            colModelPaiSe:'车牌颜色',
            colModelYingyunNo:'营运证号'
        });
    }	
}
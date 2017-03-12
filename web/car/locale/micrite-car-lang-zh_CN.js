GCarText={	        	
    newCarfile:'增加车辆记录',
    updateCarfile:'修改车辆信息',
    showCarfile:'查看车辆信息',
    carTypePieChart:'车辆类型分析(饼图)',
    colModelOwner:'车牌所属业户',
    colModelOwnerLicense:'经营许可证号',
    colModelTelephone:'业户电话',
    colModelMobile:'业户手机',
    colModelCarStatus:'营运状态',
    colModelCarType:'车辆类型',
    colModelAddress:'业户地址',
    colModelLicenseNumber:'车牌号码',
    colModelLicenseType:'车牌类型',
    colModelLoadTon:'载重/载客',//核载吨位
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
};
function carfileListLocale(){	
    if(micrite.car.carfileList.SearchPanel) {
        Ext.override(micrite.car.carfileList.SearchPanel, GCarText);
        Ext.override(micrite.car.carfileList.SearchPanel, {
        	searchName:'业户名称'
        });        
    }	
}
function carfileDetail2Locale(){	
    if(micrite.car.carfileDetail.Panel) {
        Ext.override(micrite.car.carfileDetail.Panel, GCarText);
        Ext.override(micrite.car.carfileDetail.Panel, {
			carfileDetailText:'车辆档案信息',
            ownerLicenseText:'经营许可证号',
            ownerText:'车牌所属业户',
			addressText:'业户联系地址',
            telephoneText:'业户联系电话',
            mobileText:'业户手机号码',
            sourceText:'车辆类型',
            comboEmptyText:'选择车辆类型...',
            source2Text:'技术等级',
            comboEmpty2Text:'选择技术等级...',
            source3Text:'车牌类型',
            comboEmpty3Text:'选择车牌类型...',
            source4Text:'燃油类型',
            comboEmpty4Text:'请选择燃油类型...',
            source5Text:'技术等级评定周期',
            comboEmpty5Text:'请选择技术等级评定周期...',
            source6Text:'二维周期',
            comboEmpty6Text:'请选择二维周期...',
            source7Text:'车牌颜色',
            comboEmpty7Text:'请选择车牌颜色...',
            licenseNumberText:'车牌号码',
            licenseTypeText:'车牌类型',
            loadTonText:'载重/载客',
            brandTypeText:'品牌型号',
            carRemarkText:'车辆备注',
            fuelRankText:'燃油类型',
            skillRankText:'技术等级',
            evaluateDateText:'技术等级评定有效期止',
            evaluateCycleText:'技术等级评定周期',
            maintainDateText:'二维操作日期',
            maintainCycleText:'二维周期'
        });        
    }	
}
function printPanelLocale(){	
    if(micrite.car.maintainList.PrintPanel) {
        Ext.override(micrite.car.maintainList.PrintPanel, {	        	
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

function carhistoryLocale(){	
    if(micrite.car.carhistory.SearchPanel) {
        Ext.override(micrite.car.carhistory.SearchPanel, {	        	
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

function exemptOptLocale(){	
    if(micrite.car.exemptOpt.SearchPanel) {
        Ext.override(micrite.car.exemptOpt.SearchPanel, {	        	
            expiredOpt:'行政审批',
            colModelOwner:'车牌所属业户',
            colModelMobile:'业户联系电话',
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
function punishOptLocale(){	
    if(micrite.car.punishOpt.SearchPanel) {
        Ext.override(micrite.car.punishOpt.SearchPanel, {	        	
            expiredOpt:'超期处罚',
            colModelOwner:'车牌所属业户',
            colModelMobile:'业户联系电话',
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
function maintainListLocale(){	
    if(micrite.car.maintainList.SearchPanel) {
        Ext.override(micrite.car.maintainList.SearchPanel, {	        	
            newCarfile:'增加车辆记录',
            carTypePieChart:'车辆类型分析(饼图)',
            colModelOwner:'车牌所属业户',
            colModelMobile:'业户联系电话',
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
            colModelPaiSe:'车牌颜色'
        });
    }	
}

function carfileDetailLocale(){
    if(micrite.car.carfileDetail.FormPanel) {
        Ext.override(micrite.car.carfileDetail.FormPanel, {
            carfileDetailText:'车辆档案信息',
            ownerLicenseText:'经营许可证号',
            ownerText:'车牌所属业户',
			addressText:'业户联系地址',
            telephoneText:'业户联系电话',
            mobileText:'业户手机号码',
            sourceText:'车辆类型',
            comboEmptyText:'选择车辆类型...',            
            source2Text:'技术等级',
            comboEmpty2Text:'选择技术等级...',           
            source3Text:'车牌类型',
            comboEmpty3Text:'选择车牌类型...',            
            source4Text:'燃油类型',
            comboEmpty4Text:'请选择燃油类型...',            
            source5Text:'技术等级评定周期',
            comboEmpty5Text:'请选择技术等级评定周期...',            
            source6Text:'二维周期',
            comboEmpty6Text:'请选择二维周期...',           
            source7Text:'车牌颜色',
            comboEmpty7Text:'请选择车牌颜色...',            
            licenseNumberText:'车牌号码',
            licenseTypeText:'车牌类型',
            loadTonText:'载重/载客',
            brandTypeText:'品牌型号',
            carRemarkText:'车辆备注',
            fuelRankText:'燃油类型',
            skillRankText:'技术等级',
            evaluateDateText:'技术等级评定有效期止',
            evaluateCycleText:'技术等级评定周期',
            maintainDateText:'二维操作日期',
            maintainCycleText:'二维周期'
        });
    }    
}
//function carLocale(){
function carListLocale(){	
    if(micrite.car.carList.SearchPanel) {
        Ext.override(micrite.car.carList.SearchPanel, {	        	
            newCarfile:'增加车辆记录',
            carTypePieChart:'车辆类型分析(饼图)',
            colModelOwner:'车牌所属业户',
            colModelOwnerLicense:'经营许可证号',
            colModelTelephone:'业户联系电话',
            colModelMobile:'业户手机',
            colModelCarStatus:'营运状态',
            colModelCarType:'车辆类型',
            colModelAddress:'业户地址',
            colModelLicenseNumber:'车牌号码',
            colModelLicenseType:'车牌类型',
            colModelLoadTon:'载重/载客',//核载吨位
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
function carfileBillLocale(){
    if(micrite.car.carfileBill.FormPanel) {
        Ext.override(micrite.car.carfileBill.FormPanel, {
        });
    }    
}
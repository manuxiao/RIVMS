function checkListLocale(){
    if(micrite.check.checkList.SearchPanel) {
        Ext.override(micrite.check.checkList.SearchPanel, {
        	searchCondition1:'合格证号查询', 
        	searchCondition2:'检测时间查询', 
        	searchCellphone:'合格证编号',        	
        	searchStartTime:'起始时间',
        	searchEndTime:'截至时间',	        	
            newCheck:'增加检测记录',
            printPreviewTxt:'打印预览',     
            colModelJianTime:'检测时间',
            colModelTestNo:'检测编号',
            colModelCheZhu:'车主',
            colModelCheXiu:'维修企业',
            colModelPaiHao:'车牌号',
            colModelCheLei:'车辆类型',
            colModelHeGe:'合格证编号',
            colModelPaiSe:'车牌颜色',
            colModelCreateDate:'传输日期'
        });
    }	
}
function checkDetailLocale(){
    if(micrite.check.checkDetail.FormPanel) {
        Ext.override(micrite.check.checkDetail.FormPanel, {
            checkDetailText:'检测记录基本信息',          
            jianTimeText:'检测时间',
            testNoText:'检测编号',
            cheZhuText:'车主',
            cheXiuText:'维修企业',
            paiHaoText:'车牌号',
            cheLeiText:'车辆类型',
            heGeText:'合格证编号',
            paiSeText:'车牌颜色'
        });
    }    
}
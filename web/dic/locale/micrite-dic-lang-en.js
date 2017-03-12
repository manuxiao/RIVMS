function dicListLocale(){
    if(micrite.dic.dicList.SearchPanel) {
        Ext.override(micrite.dic.dicList.SearchPanel, {
        	searchCondition1:'Condition 1',
        	searchCondition2:'Condition 2',
        	searchCondition3:'Condition 3',
        	searchCellphone:'Cellphone',        	
        	searchStartTime:'StartTime',
        	searchEndTime:'EndTime',	        	
        	newCarowner:'New Customer',
        	listCar:'car list',
    		dicSourceBarChart:'Customer Source BarChart',
    		dicSourcePieChart:'Customer Source PieChart',
    		colModelId:'ID',
            colModelName:'Name',
            colModelMobile:'Cellphone',
            colModelSource:'Source',
            colCreation_ts:'Create Date',
            searchCustomerSourceType:'Customer Source Type',
            listCarButton:'car list'
        });
    }	
    if(micrite.dic.dicList.SearchResultGrid) {
        Ext.override(micrite.dic.dicList.SearchResultGrid, {
            colModelId:'ID',
            colModelName:'Name',
            colModelMobile:'Cellphone',
            colModelSource:'Source'
        });
        
    }
}
function dicDetailLocale(){
    if(micrite.dic.dicDetail.FormPanel) {
        Ext.override(micrite.dic.dicDetail.FormPanel, {
            dicDetailText:'Customer Detail',
            idText:'ID',
            nameText:'Name',
            mobileText:'Mobile',
            sourceText:'Source',
            comboEmptyText:'Select a source...'
        });
    }    
}
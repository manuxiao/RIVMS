function carownerListLocale(){
    if(micrite.crm.carownerList.SearchPanel) {
        Ext.override(micrite.crm.carownerList.SearchPanel, {
        	searchCondition1:'Condition 1',
        	searchCondition2:'Condition 2',
        	searchCondition3:'Condition 3',
        	searchCellphone:'Cellphone',        	
        	searchStartTime:'StartTime',
        	searchEndTime:'EndTime',	        	
        	newCarowner:'New Customer',
        	listCar:'car list',
    		carownerSourceBarChart:'Customer Source BarChart',
    		carownerSourcePieChart:'Customer Source PieChart',
    		colModelId:'ID',
            colModelName:'Name',
            colModelMobile:'Cellphone',
            colModelSource:'Source',
            colCreation_ts:'Create Date',
            searchCustomerSourceType:'Customer Source Type',
            listCarButton:'car list'
        });
    }	
    if(micrite.crm.carownerList.SearchResultGrid) {
        Ext.override(micrite.crm.carownerList.SearchResultGrid, {
            colModelId:'ID',
            colModelName:'Name',
            colModelMobile:'Cellphone',
            colModelSource:'Source'
        });
        
    }
}
function carownerDetailLocale(){
    if(micrite.crm.carownerDetail.FormPanel) {
        Ext.override(micrite.crm.carownerDetail.FormPanel, {
            carownerDetailText:'Customer Detail',
            idText:'ID',
            nameText:'Name',
            mobileText:'Mobile',
            sourceText:'Source',
            comboEmptyText:'Select a source...'
        });
    }    
}
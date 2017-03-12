preDate=function(){var d = new Date();if(d.getMonth()==0){d.setYear(d.getYear()-1);d.setMonth(11);}else{d.setMonth(d.getMonth()-1)} ;return d;};
nextDate=function(){var d = new Date();if(d.getMonth()==11){d.setYear(d.getYear()+1);d.setMonth(0);}else{d.setMonth(d.getMonth()+1)} ;return d;};

myRenderer = function(value){
	var idx = this.editor.getStore().find(this.editor.valueField, value);
    var rec = this.editor.getStore().getAt(idx);
    if (rec == null) {return value;}
    else {
        return rec.get(this.editor.displayField);
    }
};
myRendererFromStore = function(value){
	var idx = this.editor.find('id', value);
    var rec = this.editor.getAt(idx);
    if (rec == null) {return value;}
    else {
        return rec.get('name');
    }
};
myRendererFromStore2 = function(value){
	var idx = this.editor.find('value', value);
    var rec = this.editor.getAt(idx);
    if (rec == null) {return value;}
    else {
        return rec.get('name');
    }
};
RecordDef = Ext.data.Record.create([{name: 'id'},{name: 'name'},{name: 'type'},{name: 'value'}]);
RecordDef2 = Ext.data.Record.create([{name: 'id'},{name: 'name'}]);
RecordDef3 = Ext.data.Record.create([{name: 'value'},{name: 'name'}]);
JsonReaderValue = new Ext.data.JsonReader({id:"value"}, RecordDef);
JsonReaderId = new Ext.data.JsonReader({id:"id"}, RecordDef);
storeCarType = new Ext.data.Store({
	id: Ext.id(),    
	autoLoad:true,
	proxy : new Ext.data.HttpProxy({url: 'dic/getPartner.action?type=1'}),    
	reader : JsonReaderId,
	remoteSort: true
});
storeSkillRank = new Ext.data.Store({
	id: Ext.id(),    
	autoLoad:true,
	proxy : new Ext.data.HttpProxy({url: 'dic/getPartner.action?type=2'}),    
	reader : JsonReaderId,
	remoteSort: true
});
storeLicenseType = new Ext.data.Store({
	id: Ext.id(),    
	autoLoad:true,
	proxy : new Ext.data.HttpProxy({url: 'dic/getPartner.action?type=3'}),    
	reader : JsonReaderId,
	remoteSort: true
});
storeFuelRank = new Ext.data.Store({
	id: Ext.id(),    
	autoLoad:true,
	proxy : new Ext.data.HttpProxy({url: 'dic/getPartner.action?type=4'}),    
	reader : JsonReaderId,
	remoteSort: true
});
storeEvCycle = new Ext.data.Store({
	id: Ext.id(),    
	autoLoad:true,
	proxy : new Ext.data.HttpProxy({url: 'dic/getPartner.action?type=5'}),    
	reader : JsonReaderId,
	remoteSort: true
});
storeMtCycle = new Ext.data.Store({
	id: Ext.id(),
	autoLoad:true,
	proxy : new Ext.data.HttpProxy({url: 'dic/getPartner.action?type=6'}),    
	reader : JsonReaderId,
	remoteSort: true
});
storePaiSe = new Ext.data.Store({
	id: Ext.id(),    
	autoLoad:true,
	proxy : new Ext.data.HttpProxy({url: 'dic/getPartner.action?type=7'}),    
	reader : JsonReaderId,
	remoteSort: true
});
storeTestKind = new Ext.data.Store({
	id: Ext.id(),    
	autoLoad:true,
	proxy : new Ext.data.HttpProxy({url: 'dic/getPartner.action?type=13'}),    
	reader : JsonReaderId,
	remoteSort: true   
});
storeCarStatus = new Ext.data.Store({
	id: Ext.id(),    
	autoLoad:true,
	proxy : new Ext.data.HttpProxy({url: 'dic/getPartner.action?type=14'}),    
	reader : JsonReaderValue,
	remoteSort: true   
});	   	
comboCarStatus = new Ext.form.ComboBox({
	name:'carStatus',
    hiddenName:'carStatus',
	valueField: "value",
	displayField: "name",
	fieldLabel:this.colModelCarStatus,
	store: storeCarStatus,
	triggerAction :'all',
	lazyRender:false 
});
//--------enterprise
storeQualification = new Ext.data.Store({
	id: Ext.id(),    
	autoLoad:true,
	proxy : new Ext.data.HttpProxy({url: 'dic/getPartner.action?type=8'}),    
	reader : JsonReaderId,
	remoteSort: true   
});
storeKind = new Ext.data.Store({
	id: Ext.id(),    
	autoLoad:true,
	proxy : new Ext.data.HttpProxy({url: 'dic/getPartner.action?type=9'}),    
	reader : JsonReaderId,
	remoteSort: true   
});
storeWorkType = new Ext.data.Store({
	id: Ext.id(),    
	autoLoad:true,
	proxy : new Ext.data.HttpProxy({url: 'dic/getPartner.action?type=10'}),    
	reader : JsonReaderId,
	remoteSort: true   
});
storeStation = new Ext.data.Store({
	id: Ext.id(),    
	autoLoad:true,
	proxy : new Ext.data.HttpProxy({url: 'dic/getPartner.action?type=11'}),    
	reader : JsonReaderId,
	remoteSort: true   
});
storeCarColumns = new Ext.data.Store({
	id: Ext.id(),    
	autoLoad:true,
	proxy : new Ext.data.HttpProxy({url: 'dic/getPartner.action?type=15'}),    
	reader : JsonReaderId,
	remoteSort: true   
});
storeEnterpriseColumns = new Ext.data.Store({
	id: Ext.id(),    
	autoLoad:true,
	proxy : new Ext.data.HttpProxy({url: 'dic/getPartner.action?type=16'}),    
	reader : JsonReaderId,
	remoteSort: true   
});
storeCarownerColumns = new Ext.data.Store({
	id: Ext.id(),    
	autoLoad:true,
	proxy : new Ext.data.HttpProxy({url: 'dic/getPartner.action?type=17'}),    
	reader : JsonReaderId,
	remoteSort: true
});
storeCreaters = new Ext.data.Store({
	id: Ext.id(),    
	autoLoad:true,
	proxy : new Ext.data.HttpProxy({url: 'getUserIdName.action'}),    
	reader : new Ext.data.JsonReader({    
		id:"id"
	}, RecordDef),
	remoteSort: true   
});
storeCarowners = new Ext.data.JsonStore({
	url:'crm/findCarownerByPy.action',
	root:'data',
	totalProperty:'totalCount',
	fields:['id','name']
});	
storeEnterprises = new Ext.data.JsonStore({
	url:'enterprise/findEnterpriseByPy.action',
	root:'data',
	totalProperty:'totalCount',
	fields:['id',{name:'name',mapping:'name'}]
});	

GEnterpriseFields=[
	 {name: 'id'},	
     {name: 'unitName'},
     {name: 'telephone1'},		     
     {name: 'license'},
     {name: 'qualification.id',mapping : 'qualification.id'},
     {name: 'qualification.name',mapping : 'qualification.name'},
     {name: 'legalPerson'},				     
     {name: 'handleMan'},			     
     {name: 'telephone2'},
     {name: 'telephone3'},
     {name: 'commission'},
     {name: 'telephone4'},			     	
     {name: 'kind.id',mapping : 'kind.id'},
     {name: 'kind.name',mapping : 'kind.name'},
     {name: 'station.id',mapping : 'station.id'},
     {name: 'station.name',mapping : 'station.name'},
     {name: 'licenseDate'},			     
     {name: 'dateBegin'},
     {name: 'dateEnd'},
     {name: 'address'},		     
     {name: 'workArea'},
     {name: 'workRemark'},
     {name: 'py'},
     {name: 'workType.id',mapping : 'workType.id'},
     {name: 'workType.name',mapping : 'workType.name'}		     
];
GCarFields = [
	 {name: 'id'},
	 {name: 'carowner.id'},
     {name: 'owner'},
     {name: 'telephone'},
     {name: 'mobile'},
     {name: 'ownerLicense'},
     {name: 'address'},
     {name: 'licenseNumber'},
     {name: 'loadTon'},
     {name: 'brandType'},
     {name: 'yingyunNo'},
     {name: 'licenseType.id',mapping : 'licenseType.id'},
     {name: 'licenseType.name',mapping : 'licenseType.name'},
     {name: 'fuelRank.id',mapping : 'fuelRank.id'},
     {name: 'fuelRank.name',mapping : 'fuelRank.name'},
     {name: 'skillRank.id',mapping : 'skillRank.id'},
     {name: 'skillRank.name',mapping : 'skillRank.name'},
     {name: 'evaluateDate'},
     {name: 'evaluateCycle.id',mapping : 'evaluateCycle.id'},
     {name: 'evaluateCycle.name',mapping : 'evaluateCycle.name'},
     {name: 'maintainDate'},
     {name: 'maintainDateEnd'},
     {name: 'maintainCycle.id',mapping : 'maintainCycle.id'},
     {name: 'maintainCycle.name',mapping : 'maintainCycle.name'},
     {name: 'paiSe.id',mapping : 'paiSe.id'}, 
     {name: 'paiSe.name',mapping : 'paiSe.name'}, 
     {name: 'carType.id',mapping : 'carType.id'},
     {name: 'carType.name',mapping : 'carType.name'},
     {name: 'carRemark'},
     {name: 'expired'},
     {name: 'approval'},
     {name: 'daysToExpired'},
     {name: 'editDate',mapping : 'editDate'},
     {name: 'carStatus'}
];
GCarJsonReader = new Ext.data.JsonReader(
		{id:'id'},//root : 'carfile',
		GCarFields
	   );
GCarColumns=[new Ext.grid.CheckboxSelectionModel(),
      {
      	header:GCarText.colModelOwner,
      	width: 80, sortable: true,dataIndex: 'owner'
      },
      {
	    header:GCarText.colModelLicenseNumber,
	    width: 60, sortable: true, dataIndex: 'licenseNumber'
	  },
	  {
      	header:GCarText.colModelCarType,
      	width: 60, sortable: true, dataIndex: 'carType.name'
      },			      
	  {
      	header:GCarText.colModelPaiSe,
      	width: 60, sortable: true, dataIndex: 'paiSe.name'
      },
	  {
	    header:GCarText.colModelYingyunNo,
	    width: 80, sortable: true, dataIndex: 'yingyunNo'
	  },
      {
      	header:GCarText.colModelTelephone,
      	width: 80, sortable: true, dataIndex: 'telephone'
      },
      {
      	header:GCarText.colModelMobile,
      	width: 80, sortable: true, dataIndex: 'mobile'
      },
      {
         header:GCarText.colModelAddress,
         width: 100, sortable: true, dataIndex: 'address'
      },
      {
      	header:GCarText.colModelOwnerLicense,
      	width: 80, sortable: true, dataIndex: 'ownerLicense'
      },
	  {
      	header:GCarText.colModelLicenseType,
      	width: 80, sortable: true, dataIndex: 'licenseType.name'
      },
	 {
		 header:GCarText.colModelLoadTon,
		 width: 50, sortable: true, dataIndex: 'loadTon'
	  },
      {
         header:GCarText.colModelBrandType,
         width: 50, sortable: true, dataIndex: 'brandType'
	  },
	  {
      	header:GCarText.colModelFuelRank,
      	width: 35, sortable: true, dataIndex: 'fuelRank.name'
      }, 				  				  
	  {
      	header:GCarText.colModelSkillRank,
      	width: 35, sortable: true, dataIndex: 'skillRank.name'
      }, 			      	
      {
      	header:GCarText.colModelEvaluateDate,
      	width: 80, sortable: true, dataIndex: 'evaluateDate'
      },
      {
      	header:GCarText.colModelEvaluateCycle,
      	width: 40, sortable: true, dataIndex: 'evaluateCycle.name'
      },
      {
      	header:GCarText.colModelMaintainDate,
      	width: 80, sortable: true, dataIndex: 'maintainDate'
      },
      {
      	header:GCarText.colModelMaintainCycle,
      	width: 40, sortable: true, dataIndex: 'maintainCycle.name'
      },
      {
      	header:GCarText.colModelMaintainDateEnd,
      	width: 80, sortable: true, dataIndex: 'maintainDateEnd'
//		          	renderer : Ext.util.Format.dateRenderer('Y-m-d')
      },
      {
	    header:GCarText.colModelCarRemark,
	    width: 40, sortable: true, dataIndex: 'carRemark'
	  }
 ];
GRenderCheckSrc=function(v){if(v==0){return "大厅";}else{return "检测站";}}
GRenderCheckType=function(v){if(v==1){return "技术等级";}else if(v==2){return "二级维护";}else{return v;}}
GCarRowRenderFn=function(g){
    var view = g.getView();    // Capture the GridView.
//    var store = g.getStore();
    var rowTip = new Ext.ToolTip({
        target: view.mainBody,    // The overall target element.
        delegate: '.x-grid3-row', // Each grid row causes its own												// seperate show and hide.
        trackMouse: true,         // Moving within the row should												// not hide the tip.
        renderTo: document.body,  // Render immediately so that												// tip.body can be
        listeners: {  // Change content dynamically depending on
						// which element triggered the show.
            beforeshow: function updateTipBody(tip){
            	var store = g.getStore();
                var rowIndex = view.findRowIndex(tip.triggerElement);
                var record = store.getAt(rowIndex);
                if(record==null){
					return false;
				}
				var carStatus=record.get('carStatus');
				if(carStatus!=0){
					if(carStatus==1)
						tip.body.dom.innerHTML = "<b class='gray-row'>"+record.get('licenseNumber')+"已转籍！<br/><br>操作日期:"+record.get('editDate')+'</b>';
					else if(carStatus==2)
						tip.body.dom.innerHTML = "<b class='gray-row'>"+record.get('licenseNumber')+"已报废！<br/><br>操作日期:"+record.get('editDate')+'</b>';
					else 
						tip.body.dom.innerHTML = "<b class='gray-row'>"+record.get('licenseNumber')+"状态不明！<br/><br>最后操作日期:"+record.get('editDate')+'</b>';
					return true;	
				}
                if(record.get('expired')==2){
                	if(record.get('approval')==1){
                		tip.body.dom.innerHTML = "<b class='gray-row'>"+record.get('licenseNumber')+"已行政审批<br/><br>二维日期:"+record.get('maintainDate')+"</br><br>有效期止:"+record.get('maintainDateEnd');
                	}else if(record.get('approval')==2){
                		tip.body.dom.innerHTML = "<b class='gray-row'>"+record.get('licenseNumber')+"已处理<br/><br>二维日期:"+record.get('maintainDate')+"</br><br>有效期止:"+record.get('maintainDateEnd');
                	}else{
         				tip.body.dom.innerHTML = "<b class='red-row'>"+record.get('licenseNumber')+"已超期需处罚！<br/><br>二维日期:"+record.get('maintainDate')+"</br><br>有效期止:"+record.get('maintainDateEnd');
                	}
     			}else if(record.get('expired')==1){
         			tip.body.dom.innerHTML = "<b class='yellow-row'>"+record.get('licenseNumber')+"二级维护已超期。<br/><br>二维日期:"+record.get('maintainDate')+"</br><br>有效期止:"+record.get('maintainDateEnd');
     			}else{
//     				tip.body.dom.innerHTML='';
//     				showMsgSilently('failure','html='+tip.body.dom.innerHTML);
//     				tip.destroy();
     				return false; //停止执行，从而禁止显示Tip
     			}
			}     
		}
    });
};
GExpiredApproval=function(record){
	if(record.get('expired')==2){
		if(record.get('approval')==0){
			return false;
		}else{
			Ext.MessageBox.alert('提示', record.get('licenseNumber')+'违规已被处理');
			return true;
		}
	}else{
		Ext.MessageBox.alert('提示', record.get('licenseNumber')+'未违规无需处理');
		return true;
	}
}
Ext.ux.GridViewCar=Ext.extend(Ext.grid.GridView,{
	getRowClass:function(record,index){   
		var ret;
    	if(record.get('carStatus')!=0){
    		ret= 'gone-row';
    	}else{
    		if(record.get('expired')==2){
	        	if(record.get('approval')==1){
	        		ret='gray-row';
	        	}else if(record.get('approval')==2){
	        		ret='';
	        	}else{
	 				ret='red-row';
	        	}
 			}else if(record.get('expired')==1){                           	
     			ret='yellow-row';
 			}
    	}
//            alert('ret='+ret);
		return ret;            
    }   
});
function GOnlyOneSelectedFn(n){
	if(n==0){
		Ext.MessageBox.alert('提示', '请选择一条记录！');
	}else if(n>1){
		Ext.MessageBox.alert('提示', '只能选择一条记录！');
	}
}
Ext.apply(Ext.form.VTypes, {
	time: function(val, field) {
	    return /^(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])(\s[a|p]m)$/i.test(val);
	},
	timeText: '请输入正确格式的时间. 如 "12:34:00 am"',
	timeMask: /[\d\s:amp]/i,

	number: function(val, field) {
        return /^\d+$/.test(val);
    },
    numberText: '请输入数字！',
	// 固定电话及小灵通
	"telephone" : function(_v) {
		return /(^\d{3}\-\d{7,8}$)|(^\d{4}\-\d{7,8}$)|(^\d{3}\d{7,8}$)|(^\d{4}\d{7,8}$)|(^\d{7,8}$)/.test(_v);
	},
	"telephoneText" : "该输入项目必须是电话号码格式，例如：0513-89500414,051389500414,89500414",
	"telephoneMask" : /[0-9\-]/i,
	"mobile" : function(_v) {
		return /^1[358][0-9]\d{8}$/.test(_v);
	},
	"mobileText" : "该输入项目必须是手机号码格式，例如：13485135075",
	"mobileMask" : /[0-9]/i,
	"paihao": function(v,field){
		return v.length==8;
	},
	"paihaoText" : "牌号8个字符"
});
GAddCarfileFn = function() {
		var title = null;
		if(window.carId==null)title=mbLocale.newCarfile;
		else title=mbLocale.updateCarfile;
		var win = micrite.util.genWindow({
			modal:true,
			maximizable:false,
            title    : title,
            autoLoad : {url: 'car/carfileDetail.js',scripts:true},
            width    : 710,
            resizable:false,
            height   : 480
        });
	}; //eof addCarfile
	GShowCarfileDetailFn = function() {
		var win = micrite.util.genWindow({
			modal:true,
			maximizable:false,
            title    : mbLocale.showCarfile,
            autoLoad : {url: 'car/carfileDetail2.js',scripts:true},
            width    : 710,
            resizable:false,
            height   : 600
        });
	}; //eof addCarfile
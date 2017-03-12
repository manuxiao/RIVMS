Ext.ns('Ext.ux');
//自定义扩展一个带Tree的comboBox
Ext.ux.LovTreeCombo = Ext.extend(Ext.form.ComboBox, {
	store: new Ext.data.SimpleStore({
//                    fields: ['key', 'value'],
//                    data : [['0','1']]
                }),
	mode: 'local',
//zIndex:0,
initList: function() {
    this.list = new Ext.tree.TreePanel({
    	setZIndex:function(zIndx){this.zIndex=zIndx;},
        floating: true,
        autoHeight: false,
        autoExpand: true,
        height: 240,
        rootVisible: false,
        autoScroll:true,
        containerScroll: true,
        dataUrl: this.url,
        root: {
            nodeType: 'async',
            text: '维修',
            draggable: false,
            id: 'root'
        },
        listeners: {
            checkchange: this.onNodeCheckChange,
            scope: this
        },
        useArrows: true, 
        alignTo: function(el, pos) {
            this.setPagePosition(this.el.getAlignToXY(el, pos));
        }
    });
},
getZIndex:function(){return this.zIndex;},
expand: function() {
    if (!this.list.rendered) {
        this.list.render(document.body);
        this.list.setWidth(this.width);//"660px" 
        this.innerList = this.list.body;
        this.list.hide();
    }
    this.el.focus();
    
    Ext.ux.LovTreeCombo.superclass.expand.apply(this, arguments);
},

doQuery: function(q, forceAll) {
    this.expand();
},

collapseIf: function(e) {
    if (!e.within(this.wrap) && !e.within(this.list.el)) {
        this.collapse();
    }
},

valueList: [],
textList: [],

getvalueList: function() {
    return this.valueList;
},

onNodeCheckChange: function(node, e) {
    if (!node.leaf) {
        node.expand(true, false, function() {
            node.eachChild(function(child) {
                child.ui.toggleCheck(node.attributes.checked);
                child.attributes.checked = node.attributes.checked;
                child.fireEvent('checkchange', child, node.attributes.checked);
            });

        });
    }else {
    	if(node.parentNode.id!=this.list.getRootNode().id){
	    	if(node.attributes.checked){
	    		node.parentNode.getUI().checkbox.checked=true;
	    	}else{
	    		var noChildrenChecked = true;
	    		var treeNodes = node.parentNode.childNodes;
	    		for(var i=0;i<treeNodes.length;i++){
	    			if(treeNodes[i].getUI().checkbox.checked){
	    				noChildrenChecked=false;
	    				break;
	    			}
	    		}
	    		if(noChildrenChecked){
	    			node.parentNode.getUI().checkbox.checked=false;
	    		}
	    	}
    	}
//    	alert(node.attributes.checked);
//        var nodeValue = node.id;
//        var test = this.valueList.indexOf(nodeValue);
//			
//        if (test == -1 && node.attributes.checked) {
//            this.valueList.push(nodeValue)
//            this.textList.push(node.text);
//        }
//
//        if (test != -1 && !node.attributes.checked) {
//            this.valueList.remove(nodeValue);
//            this.textList.remove(node.text);
//        }

        //if(window.console){console.log(this.valueList.toString())}共选择了'+this.valueList.length.toString()+'菜单：'+
//        var str = this.textList.toString();
//        this.setRawValue(str);
		this.__fillwithString();

        if (this.hiddenField) {
            this.hiddenField.value = node.id;
        }
    }
    //this.collapse();
},
__fillwithString:function(){
	var node = this.list.getRootNode();
	var result = this.getCheckedNode(node);
	this.setRawValue(result);
},
getCheckedNode:function(node){   
    var result = "";   
    
    //判断根节点是否选中   
//    if (node.getUI().checkbox.checked) {   
//        if (node.id != "root") {   
//            result += node.id + ",";   
//        }   
//    }
    if(!node.leaf){
    	result=node.text+'(';
    }
    var treeNodes = node.childNodes;
    //遍历根节点下的所有子节点
//    alert(treeNodes.length);
    for (var i = 0; i < treeNodes.length; i++) {   
        if (treeNodes[i].getUI().checkbox.checked) {
        	if(!treeNodes[i].leaf&&(treeNodes[i].childNodes.length > 0)){
                var tmp = this.getCheckedNode(treeNodes[i]);
                result+=tmp;
        	}else{
        		//if(0!=i)result += ',';
        		if(result.charAt(result.length - 1)!='('){
        			result+=',';
        		}
            	result += treeNodes[i].text;
        	}
        }   
    }
    result += ')';
    return result;   
},
url: '',
reset: function() {
    this.valueList.length = 0;
    this.textList.length=0;
    this.applyEmptyText();
    if(this.list!=null)
    	this.list.getSelectionModel().clearSelections(false);
},
resetNode: function(node) {
    this.collapseNode(node);
    this.uncheckNode(node);
},
collapseNode: function(node) {
    if (node.isExpanded()) {
        node.collapse();
    }
},
uncheckNode: function(node) {

    if (node.getUI().isChecked()) {
        if (window.console) { console.log("未能选中此节点ID " + node.attributes.id) }

            node.getUI().toggleCheck(false);
        }
    }
});

//这边注册一下子
Ext.reg('treecombo', Ext.ux.LovTreeCombo);

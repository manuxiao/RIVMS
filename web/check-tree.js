<div id="tree-div"></div>
<script type="text/javascript">
Ext.onReady(function(){
    var tree = new Ext.tree.TreePanel({
        renderTo:'tree-div',
        title: '机动车维修',
        height: 400,
        width: 250,
        useArrows:true,
        autoScroll:true,
//        animate:true,
//        enableDD:true,
        containerScroll: true,
        rootVisible: false,
        frame: true,
        root: {
            nodeType: 'async'
        },
        
        // auto create TreeLoader
        dataUrl: 'check-nodes.json',
        
        listeners: {
            'checkchange': function(node, checked){
                if(checked){
                    node.getUI().addClass('complete');
                }else{
                    node.getUI().removeClass('complete');
                }
            }
        },
        
        buttons: [{
            text: '确定',
            handler: function(){
                var msg = '', selNodes = tree.getChecked();
                Ext.each(selNodes, function(node){
                    if(msg.length > 0){
                        msg += ',';
                    }
                    msg += node.text;
                });
                if(window.workArea){
                	window.workArea.setValue(msg);
                	window.worArea=null;
                }
                Ext.WindowMgr.getActive().close();
            }
        },{
            text: '关闭',
            handler: function(){
            	Ext.WindowMgr.getActive().close();
            }
        }]
    });

    //tree.getRootNode().expand(true);
});
</script>
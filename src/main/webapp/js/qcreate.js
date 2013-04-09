/*******************************************************************************
 * Add new instance panel/window
 ******************************************************************************/
var addInstanceWin;
addInstance = function() {
    if (!addInstanceWin) {
        var addInstanceHelp = new Ext.Panel({
            html : '<p class="help-box">Configure using the Host Name and JMX Port of the server ' +
                    'running your Quartz Instances. JMX must be enabled and accessible!</p>',
            region : 'north',
            border : false,
            autoHeight : true,
            width : '100%',
            frame : false
        });
        var addInstancePanel = new Ext.FormPanel({
            border : false,
            region : 'center',
            frame : false,
            plain : false,
            monitorValid : true,
            bodyStyle : 'padding:5px 5px 0',
            items : [new Ext.form.FieldSet({
                        border : false,
                        frame : false,
                        defaultType : 'textfield',
                        autoHeight : true,
                        width : '450px',
                        buttonAlign : 'center',
                        items : [{
                                    fieldLabel : 'Host',
                                    name : 'host',
                                    width : 120,
                                    allowBlank : false,
                                    required : true,
                                    maxLength : 180,
                                    blankText : 'Host cannot be left blank.'
                                }, {
                                    fieldLabel : 'Port',
                                    name : 'port',
                                    width : 50,
                                    vtype : 'port',
                                    allowBlank : false,
                                    required : true,
                                    // maxLength : 6,
                                    blankText : 'Port cannot be left blank.'
                                }, {
                                    fieldLabel : 'User Name',
                                    name : 'userName',
                                    width : 80,
                                    allowBlank : true,
                                    required : false,
                                    maxLength : 180
                                }, {
                                    fieldLabel : 'Password',
                                    name : 'password',
                                    width : 80,
                                    allowBlank : true,
                                    required : false,
                                    maxLength : 180
                                }]
                    })],
            buttons : [{
                formBind : true,
                text : 'Save New Instance',
                iconCls : 'icon-ok',
                handler : function() {
                    addInstancePanel.form.submit({
                                url : 'ui',
                                params : {
                                    action : 'create_instance',
                                    data : Ext.util.JSON
                                            .encode(addInstancePanel.form
                                                    .getValues())
                                },
                                waitMsg : 'Saving Settings...',
                                success : function(request, result) {
                                    var res = new Object();
                                    res = Ext.util.JSON
                                            .decode(result.response.responseText);
                                    if (res.success == true) {
                                        popAlert('Configuration Change', 'Settings save successful',
                                                'icon-n-info', 3000);
                                        addInstanceWin.hide();
                                        addInstancePanel.form.reset();
                                        doLoadInstance(res.data);
                                    } else if (res.success == false) {
                                        Ext.Msg.show({
                                                    title : 'Settings save failed!',
                                                    msg : res.message,
                                                    buttons : Ext.Msg.OK,
                                                    icon : Ext.MessageBox.ERROR
                                                });
                                        addInstanceWin.hide();
                                    }
                                },
                                failure : function(request, result) {
                                    var res = new Object();
                                    res = Ext.util.JSON
                                            .decode(result.response.responseText);
                                    if (res.success == false) {
                                        Ext.Msg.show({
                                                    title : 'Settings save failed!',
                                                    msg : res.message,
                                                    buttons : Ext.Msg.OK,
                                                    icon : Ext.MessageBox.ERROR
                                                });
                                        addInstanceWin.hide();
                                    }
                                }
                            });
                }
            }]
        });
        addInstanceWin = new Ext.Window({
                    id : 'addInstanceWin',
                    title : '<font class="panelTitle">Add Quartz Instance</font>',
                    layout : 'border',
                    width : 320,
                    frame : true,
                    height : 300,
                    closeAction : 'hide',
                    plain : false,
                    border : true,
                    items : [addInstanceHelp, addInstancePanel]
                });
    }
    addInstanceWin.show('addInstanceLink');
}
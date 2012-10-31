Ext.onReady(function()
{

    // load executing jobs
    var eventStore = new Ext.data.JsonStore({
        url : 'ui?action=monitor_jobs',
        root : 'data',
        totalProperty : 'totalCount',
        fields : ['calendarName', 'jobName', 'schedulerName',
            'triggerGroup', 'triggerName', 'fireTime',
            'nextFireTime', 'previousFireTime',
            'scheduledFireTime', 'recovering', 'jobRunTime',
            'refireCount', 'schedulerId', 'quartzInstanceId'],
        autoLoad : false
    });
    eventStore.setDefaultSort('fireTime', 'DESC');

    var jobTailPanel = new Ext.grid.GridPanel({
        iconCls : 'ico-proc',
        title : '<font style="font-size:14px">Job Monitor</font>',
        store : eventStore,
        closable : false,
        columns : [
            {
                id : 'jobName',
                header : "Job",
                width : 160,
                sortable : true,
                dataIndex : 'jobName',
                renderer : function(value, p, record)
                {
                    return '<div style="padding:2px;"><font style="font-size: 12px;color:#333333">'
                        + Ext.util.Format.htmlEncode(record.data.jobName)
                        + '</font></div>'
                }
            },
            {
                id : 'calendarName',
                header : "Calendar",
                width : 160,
                sortable : true,
                hidden : true,
                dataIndex : 'calendarName',
                renderer : function(value, p, record)
                {
                    return '<div style="padding:2px;"><font style="font-size: 12px;color:#333333">'
                        + Ext.util.Format.htmlEncode(record.data.calendarName)
                        + '</font></div>'
                }
            },
            {
                id : 'triggerName',
                header : "Trigger",
                width : 160,
                sortable : true,
                hidden : true,
                dataIndex : 'triggerName',
                renderer : function(value, p, record)
                {
                    return '<div style="padding:2px;"><font style="font-size: 12px;color:#333333">'
                        + Ext.util.Format.htmlEncode(record.data.triggerName)
                        + '</font></div>'
                }
            },
            {
                id : 'triggerGroup',
                header : "Trigger Group",
                width : 160,
                sortable : true,
                hidden : true,
                dataIndex : 'triggerGroup',
                renderer : function(value, p, record)
                {
                    return '<div style="padding:2px;"><font style="font-size: 12px;color:#333333">'
                        + Ext.util.Format.htmlEncode(record.data.triggerGroup)
                        + '</font></div>'
                }
            },
            {
                id : 'schedulerName',
                header : "Scheduler",
                width : 100,
                sortable : true,
                dataIndex : 'schedulerName',
                renderer : function(value, p, record)
                {
                    return '<div style="padding:2px;"><font style="font-size: 12px;color:#333333">'
                        + Ext.util.Format.htmlEncode(record.data.schedulerName)
                        + '</font></div>'
                }
            },
            {
                id : 'scheduledFireTime',
                header : "Scheduled",
                width : 130,
                sortable : true,
                dataIndex : 'scheduledFireTime',
                renderer : function(value, p, record)
                {
                    return '<div style="padding:2px;"><font style="font-size: 12px;color:#333333">'
                        + Ext.util.Format.date(record.data.scheduledFireTime,
                        'm/d/y H:i:s') + '</font></div>'
                }
            },
            {
                id : 'fireTime',
                header : "Fired",
                width : 130,
                sortable : true,
                dataIndex : 'fireTime',
                renderer : function(value, p, record)
                {
                    return '<div style="padding:2px;"><font style="font-size: 12px;color:#333333">'
                        + Ext.util.Format.date(record.data.fireTime,
                        'm/d/y H:i:s') + '</font></div>'
                }
            },
            {
                id : 'nextFireTime',
                header : "Next Fire",
                width : 130,
                sortable : true,
                dataIndex : 'nextFireTime',
                renderer : function(value, p, record)
                {
                    return '<div style="padding:2px;"><font style="font-weight:bold; font-size: 12px;color:#333333">'
                        + Ext.util.Format.date(record.data.nextFireTime,
                        'm/d/y H:i:s') + '</font></div>'
                }
            },
            {
                id : 'previousFireTime',
                header : "Previous Fire",
                width : 130,
                sortable : true,
                dataIndex : 'previousFireTime',
                renderer : function(value, p, record)
                {
                    return '<div style="padding:2px;"><font style="font-size: 12px;color:#333333">'
                        + Ext.util.Format.date(record.data.previousFireTime,
                        'm/d/y H:i:s') + '</font></div>'
                }
            },
            {
                id : 'jobRunTime',
                header : "Duration",
                width : 100,
                sortable : true,
                dataIndex : 'jobRunTime',
                renderer : function(value, p, record)
                {
                    return '<div style="padding:2px;"><font style="font-size: 12px;color:#333333">'
                        + record.data.jobRunTime + 'ms</font></div>'
                }
            },
            {
                id : 'refireCount',
                header : "Refire Count",
                width : 50,
                hidden : true,
                sortable : true,
                dataIndex : 'refireCount',
                renderer : function(value, p, record)
                {
                    return '<div style="padding:2px;"><font style="font-size: 12px;color:#333333">'
                        + record.data.refireCount + '</font></div>'
                }
            }
        ],
        autoHeight : false,
        autoWidth : true,
        height : 500,
        stripeRows : true,
        autoScroll : true,
        autoExpandColumn : 'jobName',
        floatable : false,
        border : false,
        frame : false,
        loadMask : true,
        region : 'center'
    });
    tabPanel = new Ext.TabPanel({
        region : 'center',
        autoScroll : false,
        enableTabScroll : true,
        border : true,
        frame : false,
        plain : false,
        activeTab : 0,
        // layoutOnTabChange : true, // IMPORTANT!
        items : [jobTailPanel]
    });
    new Ext.Viewport({
        layout : 'border',
        items : [
            {
                xtype : 'box',
                region : 'north',
                applyTo : 'headerDiv',
                height : 60
            },
            {
                layout : 'border',
                region : 'center',
                border : false,
                split : false,
                items : [tabPanel]
            }
        ],
        renderTo : Ext.getBody()
    });

    // start the job poll
    var jobMonitor = {
        run : function()
        {
            eventStore.reload();
        },
        interval : 20000
    }
    Ext.TaskMgr.start(jobMonitor);

    // load Quartz Instances
    var QIStore = new Ext.data.JsonStore({
        url : 'ui?action=get_all_instances',
        root : 'data',
        totalProperty : 'totalCount',
        fields : ['host', 'port', 'userName', 'password', 'uuid']
    });
    QIStore.load({
        callback : function(records, options, success)
        {
            // create tab
            for (
                var loop = 0; loop < QIStore.getTotalCount(); loop++
                )
            {
                if (QIStore.data.items[loop] != undefined)
                {
                    doLoadInstance(QIStore.data.items[loop].data);
                    popAlert('Quartz instance <b>'
                        + QIStore.data.items[loop].data.host + ':'
                        + QIStore.data.items[loop].data.port
                        + '</b> loaded successfully', 'Success',
                        'icon-n-info', 3000);
                }
            }

            if (QIStore.getTotalCount() <= 0)
            {
                popAlert(
                    'Quartz Instances Not Configured, click on the Add link to get started.',
                    'Nothing to see...', 'icon-n-warn', 8000);
            }
        }
    });

    var currentInstance;
    var jobStore;
    var triggerStore;
    var schedStore;
    doLoadInstance = function(instance)
    {
        currentInstance = instance;
        currentInstance.rendered = false;

        var html = '<div style="background-color:#DCF6D8;height: 42px; width: 100%;">'
            + '<table width="100%">'
            + '<tr>'
            + '<td style="text-align:left" width="200">'
            + '<div id="sched-select" style="margin-left:-100;"></div>'
            + '</td><td style="text-align:left">'
            + '<table><tr><td><div id="infoid"></div></td>'
            + '<td><div id="refreshid"></div></td></tr></table></td></tr></table></div>'
        jobStore = new Ext.data.JsonStore({
            url : 'ui',
            root : 'data',
            autoLoad : false,
            totalProperty : 'totalCount',
            fields : ['jobName', 'description', 'group', 'jobClass',
                'schedulerInstanceId', 'durability',
                'shouldRecover', 'quartzInstanceId',
                'nextFireTime', 'numTriggers']
        });
        jobStore.setDefaultSort('jobName', 'ASC');

        var jobGrid = new Ext.grid.GridPanel({
            // title : 'Jobs',
            store : jobStore,
            closable : false,
            columns : [
                {
                    id : 'jobName',
                    header : "Job",
                    width : 160,
                    sortable : true,
                    css : 'font-size: 13px;font-weight:bold;color:#333333;',
                    dataIndex : 'jobName',
                    renderer : function(value, p, record)
                    {
                        return Ext.util.Format
                            .htmlEncode(record.data.jobName);
                    }
                },
                {
                    id : 'nextFireTime',
                    header : "Next FireTime",
                    width : 170,
                    sortable : true,
                    css : 'font-size: 13px;font-weight:bold;color:#333333;',
                    dataIndex : 'nextFireTime'
                },
                {
                    id : 'numTriggers',
                    header : "Triggers",
                    width : 80,
                    sortable : true,
                    css : 'font-size: 13px;font-weight:bold;color:#333333;text-align:center;',
                    dataIndex : 'numTriggers',
                    renderer : function(value, p, record)
                    {
                        if (record.data.numTriggers < 1)
                        {
                            return '0';
                        }
                        else
                        {
                            return '<a ext:qtip="Click to view Job triggers." ext:qtitle="Show Triggers" href="javascript:void(0);" onClick="getTriggersForJob(\''
                                + record.data.quartzInstanceId
                                + '\',\''
                                + record.data.schedulerInstanceId
                                + '\',\''
                                + record.data.jobName
                                + '\',\''
                                + record.data.group
                                + '\')">'
                                + record.data.numTriggers + '</a>';
                        }
                    }
                },
                {
                    id : 'group',
                    header : "Group",
                    width : 100,
                    sortable : true,
                    css : 'font-size: 13px;font-weight:bold;color:#333333;',
                    dataIndex : 'group',
                    renderer : function(value, p, record)
                    {
                        return Ext.util.Format
                            .htmlEncode(record.data.group);
                    }
                },
                {
                    id : 'jobClass',
                    header : "Job Class",
                    width : 180,
                    sortable : true,
                    css : 'font-size: 13px;font-weight:bold;color:#333333;',
                    dataIndex : 'jobClass',
                    renderer : function(value, p, record)
                    {
                        return Ext.util.Format
                            .htmlEncode(record.data.jobClass);
                    }
                },
                {
                    id : 'durability',
                    header : "Durable",
                    width : 80,
                    sortable : true,
                    dataIndex : 'durability',
                    css : '',
                    renderer : function(value, p, record)
                    {
                        if (record.data.durability == true)
                        {
                            return '<div style="text-align:center;"><img src="images/ok.png" border="0"/></div>';
                        }
                    }
                },
                {
                    id : 'shouldRecover',
                    header : "Recoverable",
                    width : 80,
                    sortable : true,
                    dataIndex : 'shouldRecover',
                    css : 'padding:3px;',
                    renderer : function(value, p, record)
                    {
                        if (record.data.shouldRecover == true)
                        {
                            return '<div style="text-align:center;"><img src="images/ok.png" border="0"/></div>';
                        }
                    }
                }
            ],
            stripeRows : true,
            autoScroll : true,
            autoExpandColumn : 'jobName',
            floatable : false,
            autoHeight : false,
            border : false,
            layout : 'fit',
            frame : false,
            loadMask : true,
            region : 'center'
        });
        var schedPanel = new Ext.Panel({
            id : 'schedPanel' + instance.uuid,
            border : false,
            frame : false,
            html : html,
            layout : 'fit',
            region : 'north'
        });
        var innerPanel = new Ext.Panel({
            region : 'center',
            autoScroll : true,
            activeTab : 0,
            enableTabScroll : true,
            border : false,
            frame : false,
            plain : false,
            // autoHeight : false,
            // height : '100%',
            layout : 'border',
            layoutOnTabChange : true,// IMPORTANT!
            items : [jobGrid]
        });
        var sampleTab = new Ext.Panel({
            id : instance.uuid,
            iconCls : 'ico-clock',
            frame : false,
            autoScroll : false,
            border : false,
            plain : true,
            autoWidth : true,
            title : '<font style="font-size:14px">' + instance.userName
                + '@' + instance.host + ':' + instance.port
                + '</font>',
            height : 50,
            closable : true,
            layout : 'border',
            items : [schedPanel, innerPanel],
            listeners : {
                activate : function()
                {
                    handleInstanceTabActivate(instance);
                }
            }
        });
        innerPanel.doLayout();
        tabPanel.add(sampleTab);
        // tabPanel.setActiveTab(instance.uuid);

        schedStore = new Ext.data.JsonStore({
            url : 'ui?action=get_schedulers&uuid=' + instance.uuid,
            root : 'data',
            totalProperty : 'totalCount',
            fields : ['name', 'instanceId', 'uuidInstance'],
            autoLoad : false
        });
    }

    handleInstanceTabActivate = function(instance)
    {
        if (currentInstance.rendered == false)
        {
            // sched-select
            var comboTPL = new Ext.XTemplate(
                '<tpl for="."><div class="multiline-combo">',
                '<img src="images/schedule16x16.png" style="vertical-align:middle"/>&nbsp;<b>{name:htmlEncode}</b></div></tpl>');
            schedStore.load({
                callback : function(records, options, success)
                {
                    var schedbox = new Ext.form.ComboBox({
                        tpl : comboTPL,
                        itemSelector : 'div.multiline-combo',
                        mode : 'remote',
                        store : schedStore,
                        allowBlank : true,
                        emptyText : 'Select a Scheduler...',
                        selectOnFocus : true,
                        width : 180,
                        listWidth : 300,
                        editable : false,
                        name : 'schedName',
                        id : 'schedName',
                        displayField : 'name',
                        valueField : 'uuidInstance',
                        hiddenName : 'uuidInstance',
                        triggerAction : 'all',
                        listeners : {
                            'select' : function(combo, record)
                            {
                                getJobsForScheduler(record.data.uuidInstance);
                            }
                        }
                    });
                    new Ext.form.FormPanel({
                        baseCls : 'x-example-form',
                        style : {
                            margin : 0,
                            padding : 10
                        },
                        border : false,
                        renderTo : 'sched-select',
                        items : [schedbox]
                    });
                }
            });
        }
        currentInstance.rendered = true;
    }

    getJobsForScheduler = function(id)
    {
        jobStore.load({
            params : {
                action : 'get_jobs',
                'uuidInstance' : id
            }
        });
        var ii = document.getElementById('infoid');
        ii.innerHTML = '<a href="javascript:void(0);" onClick="getSchedulerInfo(\''
            + id
            + '\')"><img  ext:qtip="Click to view Schedule details." ext:qtitle="Show Schedule"  src="images/info.png" style="padding-bottom:5px;padding-left:7px;border:0;"/></a>';
        var ir = document.getElementById('refreshid');
        ir.innerHTML = '<a href="javascript:void(0);" onClick="getJobsForScheduler(\''
            + id
            + '\')"><img  ext:qtip="Click to refresh Jobs list." ext:qtitle="Refresh Jobs"  src="images/refresh.png" style="padding-bottom:5px;padding-left:7px;border:0;"/></a>';
    }

    getSchedulerInfo = function(id)
    {
        Ext.Ajax.request({
            url : 'ui',
            params : {
                action : 'get_scheduler_info',
                'uuidInstance' : id
            },
            waitMsg : 'Removing User Mapping...',
            success : function(response, request)
            {
                var res = new Object();
                res = Ext.util.JSON.decode(response.responseText);
                if (res.success == true)
                {
                    var schedulerPanel = new Ext.Panel({
                        title : '',
                        region : 'center',
                        autoHeight : true,
                        html : ''
                    });
                    schedulerWin = new Ext.Window({
                        id : 'addInstanceWin',
                        title : '<font class="panelTitle">Scheduler Details</font>',
                        layout : 'fit',
                        width : 620,
                        frame : true,
                        autoScroll : true,
                        height : 320,
                        closeAction : 'close',
                        plain : true,
                        border : true,
                        html : ''
                    });
                    schedulerWin.show(document.body);

                    var html = '<div style="padding:10px;"><table class="panelTbl" border="0" cellpadding="5" cellspacing="5">'
                        + '<tr><td class="label">Scheduler Name:</td><td>'
                        + res.name
                        + '</td></tr>'
                        + '<tr><td class="label">Job Store Class:</td><td>'
                        + res.jobStoreClassName
                        + '</td></tr>'
                        + '<tr><td class="label">Scheduler ID:</td><td>'
                        + res.instanceId
                        + '</td></tr>'
                        + '<tr><td class="label">Canonical Name:</td><td>'
                        + res.objectName.canonicalName
                        + '</td></tr>'
                        + '<tr><td class="label">Domain:</td><td>'
                        + res.objectName.domain
                        + '</td></tr>'
                        + '<tr><td class="label">Started:</td><td>'
                        + res.started
                        + '</td></tr>'
                        + '<tr><td class="label">Shutdown:</td><td>'
                        + res.shutdown
                        + '</td></tr>'
                        + '<tr><td class="label">Standby Mode:</td><td>'
                        + res.standByMode
                        + '</td></tr>'
                        + '<tr><td class="label">ThreadPool Class:</td><td>'
                        + res.threadPoolClassName
                        + '</td></tr>'
                        + '<tr><td class="label">ThreadPool Size:</td><td>'
                        + res.threadPoolSize
                        + '</td></tr>'
                        + '</table></div>'
                    schedulerWin.body.update(html);
                }
            }
        });
    }
});
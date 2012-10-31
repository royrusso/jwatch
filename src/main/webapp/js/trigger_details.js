getTriggersForJob = function(qid, sid, jname, gname) {

	var triggerStore = new Ext.data.JsonStore({
				url : 'ui',
				root : 'data',
				autoLoad : false,
				totalProperty : 'totalCount',
				fields : ['name', 'nextFireTime', 'previousFireTime',
						'startTime', 'STriggerState']
			});
	triggerStore.setDefaultSort('name', 'ASC');
	triggerStore.load({
		url : 'ui',
		params : {
			action : 'get_job_triggers',
			'uuid' : qid,
			'sid' : sid,
			'jobName' : jname,
			'groupName' : gname
		},
		waitMsg : 'Loading Triggers...',
		callback : function(records, options, success) {
			if (success == true) {
				var triggerGrid = new Ext.grid.GridPanel({
					title : '',
					store : triggerStore,
					closable : false,
					columns : [{
								id : 'name',
								header : "Trigger Name",
								width : 160,
								css : "font-size : 13px;padding:3px;",
								sortable : true,
								dataIndex : 'name'
							}, {
								id : 'previousFireTime',
								header : "Prev FireTime",
								width : 150,
								css : "font-size : 13px;padding:3px;",
								sortable : true,
								dataIndex : 'previousFireTime'
							}, {
								id : 'nextFireTime',
								header : "Next FireTime",
								width : 150,
								css : "font-size : 13px;padding:3px;color:green;",
								sortable : true,
								dataIndex : 'nextFireTime',
								renderer : function(value, p, record) {
									return '<font style="color:green;font-weight:bold;">'
											+ record.data.nextFireTime
											+ '</font>';
								}
							}, {
								id : 'startTime',
								header : "Started",
								width : 150,
								css : "font-size : 13px;padding:3px;",
								sortable : true,
								dataIndex : 'startTime'
							}, {
								id : 'STriggerState',
								header : "State",
								width : 150,
								css : "font-size : 13px;padding:3px; font-weight:bold;",
								sortable : true,
								dataIndex : 'STriggerState'
							}],
					stripeRows : true,
					region : 'center',
					autoScroll : true,
					autoExpandColumn : 'name',
					floatable : false,
					autoHeight : true,
					border : false,
					frame : false,
					loadMask : true
				});
				var triggerWin = new Ext.Window({
							id : 'triggerWin',
							title : '<font class="panelTitle">Job Triggers</font>',
							layout : 'border',
							width : 800,
							frame : true,
							height : 300,
							closeAction : 'close',
							plain : false,
							border : true,
							items : [triggerGrid]
						});
				triggerWin.show();
			} else {
				Ext.Msg.show({
							title : 'Trigger loading failed!',
							msg : res.message,
							buttons : Ext.Msg.OK,
							icon : Ext.MessageBox.ERROR
						});
			}
		}
	});
}
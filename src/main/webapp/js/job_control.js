/**
 * @author <a href="mailto:jkienitz@liveops.com">John Kienitz</a>
 *         Date: March 30, 2013 9:08:09 AM
 *
 * JWatch - Quartz Monitor: http://code.google.com/p/jwatch/
 * Copyright (C) 2013 John Kienitz, Liveops,  and the original author or authors.
 *
 * job control methods
 *
 */
deleteJob = function(jobStore, qid, sid, jname, gname) {
	jobControlRequest(qid, sid, jname, gname, 'delete_job', 'Delete');
//	jobStore.reload();
};

pauseJob = function(jobStore, qid, sid, jname, gname) {
	jobControlRequest(qid, sid, jname, gname, 'pause_job', 'Pause');
//	jobStore.reload();
};

resumeJob = function(jobStore, qid, sid, jname, gname) {
	jobControlRequest(qid, sid, jname, gname, 'resume_job', 'Resume');
//	jobStore.reload();
};

runJob = function(jobStore, qid, sid, jname, gname) {
	jobControlRequest(qid, sid, jname, gname, 'run_job', 'Run');
//	jobStore.reload();
};

jobControlRequest = function(qid, sid, jname, gname, requestCommand, requestName) {
	Ext.Ajax.request({
		   url: 'ui',
		   success: function(response, opts) {
			    var obj = Ext.decode(response.responseText);
			    if (obj.success == true) {
					Ext.Msg.show({
						title : requestName + ' successful',
						buttons : Ext.Msg.OK,
						icon : Ext.MessageBox.INFO
					});
			    } else {
					Ext.Msg.show({
						title : requestName + ' failed',
						msg : obj.message,
						buttons : Ext.Msg.OK,
						icon : Ext.MessageBox.ERROR
					});
			    }
	       },
	       failure: function(form, action) {
				Ext.Msg.show({
					title : 'Communication error - request failed!',
					buttons : Ext.Msg.OK,
					icon : Ext.MessageBox.ERROR
				});
	       },
			params : {
				action : requestCommand,
				'uuid' : qid,
				'sid' : sid,
				'jobName' : jname,
				'groupName' : gname
			}
		});
};


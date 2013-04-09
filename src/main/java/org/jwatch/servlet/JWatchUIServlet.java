/**
 * JWatch - Quartz Monitor: http://code.google.com/p/jwatch/
 * Copyright (C) 2011 Roy Russo and the original author or authors.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA 02110-1301 USA
 **/
package org.jwatch.servlet;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jwatch.handler.QuartzInstanceHandler;
import org.jwatch.util.GlobalConstants;
import org.jwatch.util.JSONUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Calls handler layer based on specific commans passed-in through GEt/POST that map to {@link org.jwatch.servlet.ActionConstants}.
 * Currently, all responses are in JSON format.
 * <p/>
 * TODO: This thing needs to be replaced by something like jpoxy, or maintenance will be costly.
 *
 * @author <a href="mailto:royrusso@gmail.com">Roy Russo</a>
 *         Date: Apr 4, 2011 9:29:14 AM
 */
public class JWatchUIServlet extends HttpServlet
{
   static Logger log = Logger.getLogger(JWatchUIServlet.class);

   public final void init(ServletConfig servletConfig) throws ServletException
   {
      log.debug("************ STARTED: JWatchUIServlet ******************");
   }

   public void doGet(HttpServletRequest req, HttpServletResponse res)
         throws ServletException
   {
      doPost(req, res);
   }

   public void doPost(HttpServletRequest req, HttpServletResponse res)
         throws ServletException
   {
      String subject = StringUtils.trimToNull(req.getParameter("action"));

      if (subject != null)
      {
         try
         {
            Object returnO = new Object();
            res.setContentType("application/json");
            PrintWriter out = res.getWriter();

            if (subject.equalsIgnoreCase(ActionConstants.LOAD_INSTANCES))
            {
               returnO = QuartzInstanceHandler.loadInstances();
            }
            else if (subject.equalsIgnoreCase(ActionConstants.CREATE_INSTANCE))
            {
               Map map = JSONUtil.convertRequestToMap(req);
               returnO = QuartzInstanceHandler.createInstance(map);
            }
            else if (subject.equalsIgnoreCase(ActionConstants.LOAD_SCHEDULERS))
            {
               Map map = JSONUtil.convertRequestToMap(req);
               returnO = QuartzInstanceHandler.getSchedulersForForQuartzInstance(map);
            }
            else if (subject.equalsIgnoreCase(ActionConstants.LOAD_JOBS))
            {
               Map map = JSONUtil.convertRequestToMap(req);
               returnO = QuartzInstanceHandler.getJobsForScheduler(map);
            }
            else if (subject.equalsIgnoreCase(ActionConstants.LOAD_SCHEDULERINFO))
            {
               Map map = JSONUtil.convertRequestToMap(req);
               returnO = QuartzInstanceHandler.getSchedulerInfo(map);
            }
            else if (subject.equalsIgnoreCase(ActionConstants.LOAD_TRIGGERS_FOR_JOB))
            {
               Map map = JSONUtil.convertRequestToMap(req);
               returnO = QuartzInstanceHandler.getTriggersForJob(map);
            }
            else if (subject.equalsIgnoreCase(ActionConstants.MONITOR_JOBS))
            {
               Map map = JSONUtil.convertRequestToMap(req);
               returnO = QuartzInstanceHandler.getJobsList(map);
            }
            //
            // johnk additions - add control capabilities
            //
            else if (subject.equalsIgnoreCase(ActionConstants.PAUSE_JOB)) {
               Map map = JSONUtil.convertRequestToMap(req);
               returnO = QuartzInstanceHandler.pauseJob(map);
            } else if (subject.equalsIgnoreCase(ActionConstants.RESUME_JOB)) {
               Map map = JSONUtil.convertRequestToMap(req);
               returnO = QuartzInstanceHandler.resumeJob(map);
            } else if (subject.equalsIgnoreCase(ActionConstants.DELETE_JOB)) {
               Map map = JSONUtil.convertRequestToMap(req);
               returnO = QuartzInstanceHandler.deleteJob(map);
            } else if (subject.equalsIgnoreCase(ActionConstants.RUN_JOB)) {
                Map map = JSONUtil.convertRequestToMap(req);
                returnO = QuartzInstanceHandler.runJob(map);
             }
            //
            // end johnk additions - add control capabilities
            //

            else {
            	JSONUtil.buildError(GlobalConstants.MESSAGE_ERR_ACTION_UNKNOWN);
            	log.error(GlobalConstants.MESSAGE_ERR_ACTION_UNKNOWN);
            }
            out.print(returnO);
            out.flush();
            out.close();

/*         HashMap<String, String> map =
      new HashMap<String, String>();
map.put("java.naming.factory.initial",
        RegistryContextFactory.class.getName());
JMXConnector connector = JMXConnectorFactory
      .connect(new JMXServiceURL("service:jmx:rmi:///jndi/rmi://:2911/jmxrmi"));
mbsc = connector.getMBeanServerConnection();

System.out.println("\nDomains:");
String domains[] = mbsc.getDomains();
Arrays.sort(domains);
for (String domain : domains)
{
   System.out.println("\tDomain = " + domain);
}

System.out.println("\nMBeanServer default domain = " + mbsc.getDefaultDomain());

System.out.println("\nMBean count = " + mbsc.getMBeanCount());
System.out.println("\nQuery MBeanServer MBeans:");
Set names = new TreeSet(mbsc.queryNames(null, null));

// quartz:type=QuartzScheduler,name=MyScheduler,instance=NON_CLUSTERED
ObjectName mbeanName = new ObjectName("quartz:instance=NON_CLUSTERED,name=MyScheduler,type=QuartzScheduler");
QuartzSchedulerMBean mbeanProxy = JMX.newMBeanProxy(mbsc, mbeanName, QuartzSchedulerMBean.class, true);
System.out.println(mbeanProxy.getSchedulerName());


List jobGroups = mbeanProxy.getJobGroupNames();
Map pmap = mbeanProxy.getPerformanceMetrics();
TabularData tdata = mbeanProxy.getCurrentlyExecutingJobs();
for (int i = 0; i < jobGroups.size(); i++)
{
   String groupName = (String) jobGroups.get(i);
   List<String> jobNames = mbeanProxy.getJobNames(groupName);
   for (int j = 0; j < jobNames.size(); j++)
   {
      String jobName = jobNames.get(j);
      CompositeData data = mbeanProxy.getJobDetail(jobName, groupName);

      System.out.println();
   }
   System.out.println();
}
connector.close();*/
         }
         catch (Exception e)
         {
            e.printStackTrace();
         }
      }
   }
}


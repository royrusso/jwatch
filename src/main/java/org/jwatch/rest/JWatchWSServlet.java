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
package org.jwatch.rest;

import javax.management.MBeanServerConnection;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="mailto:royrusso@gmail.com">Roy Russo</a>
 *         Date: Apr 4, 2011 3:42:08 PM
 */
public class JWatchWSServlet extends HttpServlet
{
   protected MBeanServerConnection mbsc = null;

   public final void init(ServletConfig servletConfig) throws ServletException
   {
      System.out.println("******************************");
   }

   public void doGet(HttpServletRequest req, HttpServletResponse res)
         throws ServletException
   {
      doPost(req, res);
   }

   public void doPost(HttpServletRequest req, HttpServletResponse res)
         throws ServletException
   {
      try
      {
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
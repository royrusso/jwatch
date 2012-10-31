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
package org.jwatch.domain.instance;

import org.jwatch.domain.adapter.QuartzJMXAdapter;
import org.jwatch.domain.quartz.Scheduler;
import org.jwatch.listener.notification.Listener;
import org.jwatch.listener.settings.QuartzConfig;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import java.util.HashMap;
import java.util.List;

/**
 * Represents one Quartz Instance. A server can have more than one QuartzInstance. One Quartz Instance can have many Schedulers.
 * This object is kept in mem
 * for easy fetching {@link org.jwatch.listener.settings.QuartzConfig}, thus avoiding having to instantiate a JMX
 * connection for every call.
 *
 * @author <a href="mailto:royrusso@gmail.com">Roy Russo</a>
 *         Date: Apr 6, 2011 4:57:14 PM
 */
public class QuartzInstance extends QuartzConfig
{
   private MBeanServerConnection mBeanServerConnection;

   private QuartzJMXAdapter jmxAdapter;
   private List<Scheduler> schedulerList;
   private static HashMap<String, Listener> listenersMap;

   /**
    * needed for shutdown. *
    */
   private JMXConnector jmxConnector;

   public QuartzInstance(String uuid, String host, int port, String userName, String password)
   {
      super(uuid, host, port, userName, password);
   }

   public QuartzInstance(QuartzConfig config)
   {
      super(config.getUuid(), config.getHost(), config.getPort(), config.getUserName(), config.getPassword());
   }

   public MBeanServerConnection getMBeanServerConnection()
   {
      return mBeanServerConnection;
   }

   public void setMBeanServerConnection(MBeanServerConnection mBeanServerConnection)
   {
      this.mBeanServerConnection = mBeanServerConnection;
   }

   public QuartzJMXAdapter getJmxAdapter()
   {
      return jmxAdapter;
   }

   public void setJmxAdapter(QuartzJMXAdapter jmxAdapter)
   {
      this.jmxAdapter = jmxAdapter;
   }

   public List<Scheduler> getSchedulerList()
   {
      return schedulerList;
   }

   public void setSchedulerList(List<Scheduler> schedulerList)
   {
      this.schedulerList = schedulerList;
   }

   public JMXConnector getJmxConnector()
   {
      return jmxConnector;
   }

   public void setJmxConnector(JMXConnector jmxConnector)
   {
      this.jmxConnector = jmxConnector;
   }

   public static HashMap<String, Listener> getListenersMap()
   {
      return listenersMap;
   }

   public static void setListenersMap(HashMap<String, Listener> _listenersMap)
   {
      listenersMap = _listenersMap;
   }

   public static void putListener(Listener listener)
   {
      if (listener != null)
      {
         if (listenersMap == null)
         {
            listenersMap = new HashMap();
         }
         listenersMap.put(listener.getUUID(), listener);
      }
   }

   @Override
   public String toString()
   {
      final StringBuilder sb = new StringBuilder();
      sb.append("QuartzInstance");
      sb.append("{mBeanServerConnection=").append(mBeanServerConnection);
      sb.append(", jmxAdapter=").append(jmxAdapter);
      sb.append(", schedulerList=").append(schedulerList);
      sb.append('}');
      return sb.toString();
   }

/*
MBeanAttributeInfo[description=Attribute exposed for management, name=Version, type=java.lang.String, read-only, descriptor={}]
MBeanAttributeInfo[description=Attribute exposed for management, name=Shutdown, type=boolean, read-only, isIs, descriptor={}]
MBeanAttributeInfo[description=Attribute exposed for management, name=Started, type=boolean, read-only, isIs, descriptor={}]
MBeanAttributeInfo[description=Attribute exposed for management, name=JobGroupNames, type=java.util.List, read-only, descriptor={}]
MBeanAttributeInfo[description=Attribute exposed for management, name=SchedulerName, type=java.lang.String, read-only, descriptor={}]
MBeanAttributeInfo[description=Attribute exposed for management, name=SchedulerInstanceId, type=java.lang.String, read-only, descriptor={}]
MBeanAttributeInfo[description=Attribute exposed for management, name=CurrentlyExecutingJobs, type=openmbean.TabularData, read-only, descriptor={}]
MBeanAttributeInfo[description=Attribute exposed for management, name=TriggerGroupNames, type=java.util.List, read-only, descriptor={}]
MBeanAttributeInfo[description=Attribute exposed for management, name=PausedTriggerGroups, type=java.util.Set, read-only, descriptor={}]
MBeanAttributeInfo[description=Attribute exposed for management, name=CalendarNames, type=java.util.List, read-only, descriptor={}]
MBeanAttributeInfo[description=Attribute exposed for management, name=ThreadPoolSize, type=int, read-only, descriptor={}]
MBeanAttributeInfo[description=Attribute exposed for management, name=SampledStatisticsEnabled, type=boolean, read/write, isIs, descriptor={}]
MBeanAttributeInfo[description=Attribute exposed for management, name=StandbyMode, type=boolean, read-only, isIs, descriptor={}]
MBeanAttributeInfo[description=Attribute exposed for management, name=JobStoreClassName, type=java.lang.String, read-only, descriptor={}]
MBeanAttributeInfo[description=Attribute exposed for management, name=ThreadPoolClassName, type=java.lang.String, read-only, descriptor={}]
MBeanAttributeInfo[description=Attribute exposed for management, name=JobsScheduledMostRecentSample, type=long, read-only, descriptor={}]
MBeanAttributeInfo[description=Attribute exposed for management, name=JobsExecutedMostRecentSample, type=long, read-only, descriptor={}]
MBeanAttributeInfo[description=Attribute exposed for management, name=JobsCompletedMostRecentSample, type=long, read-only, descriptor={}]
MBeanAttributeInfo[description=Attribute exposed for management, name=PerformanceMetrics, type=java.util.Map, read-only, descriptor={}]
MBeanAttributeInfo[description=Attribute exposed for management, name=AllJobDetails, type=openmbean.TabularData, read-only, descriptor={}]
MBeanAttributeInfo[description=Attribute exposed for management, name=AllTriggers, type=java.util.List, read-only, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=shutdown, returnType=void, signature=[], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=clear, returnType=void, signature=[], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=start, returnType=void, signature=[], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=scheduleJob, returnType=void, signature=[MBeanParameterInfo[description=, name=p1, type=java.lang.String, descriptor={}], MBeanParameterInfo[description=, name=p2, type=java.lang.String, descriptor={}], MBeanParameterInfo[description=, name=p3, type=java.util.Map, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=scheduleJob, returnType=void, signature=[MBeanParameterInfo[description=, name=p1, type=java.util.Map, descriptor={}], MBeanParameterInfo[description=, name=p2, type=java.util.Map, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=scheduleJob, returnType=java.util.Date, signature=[MBeanParameterInfo[description=, name=p1, type=java.lang.String, descriptor={}], MBeanParameterInfo[description=, name=p2, type=java.lang.String, descriptor={}], MBeanParameterInfo[description=, name=p3, type=java.lang.String, descriptor={}], MBeanParameterInfo[description=, name=p4, type=java.lang.String, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=getJobDetail, returnType=openmbean.CompositeData, signature=[MBeanParameterInfo[description=, name=p1, type=java.lang.String, descriptor={}], MBeanParameterInfo[description=, name=p2, type=java.lang.String, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=standby, returnType=void, signature=[], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=unscheduleJob, returnType=boolean, signature=[MBeanParameterInfo[description=, name=p1, type=java.lang.String, descriptor={}], MBeanParameterInfo[description=, name=p2, type=java.lang.String, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=addJob, returnType=void, signature=[MBeanParameterInfo[description=, name=p1, type=java.util.Map, descriptor={}], MBeanParameterInfo[description=, name=p2, type=boolean, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=addJob, returnType=void, signature=[MBeanParameterInfo[description=, name=p1, type=openmbean.CompositeData, descriptor={}], MBeanParameterInfo[description=, name=p2, type=boolean, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=deleteJob, returnType=boolean, signature=[MBeanParameterInfo[description=, name=p1, type=java.lang.String, descriptor={}], MBeanParameterInfo[description=, name=p2, type=java.lang.String, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=triggerJob, returnType=void, signature=[MBeanParameterInfo[description=, name=p1, type=java.lang.String, descriptor={}], MBeanParameterInfo[description=, name=p2, type=java.lang.String, descriptor={}], MBeanParameterInfo[description=, name=p3, type=java.util.Map, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=pauseJob, returnType=void, signature=[MBeanParameterInfo[description=, name=p1, type=java.lang.String, descriptor={}], MBeanParameterInfo[description=, name=p2, type=java.lang.String, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=pauseTrigger, returnType=void, signature=[MBeanParameterInfo[description=, name=p1, type=java.lang.String, descriptor={}], MBeanParameterInfo[description=, name=p2, type=java.lang.String, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=resumeJob, returnType=void, signature=[MBeanParameterInfo[description=, name=p1, type=java.lang.String, descriptor={}], MBeanParameterInfo[description=, name=p2, type=java.lang.String, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=resumeTrigger, returnType=void, signature=[MBeanParameterInfo[description=, name=p1, type=java.lang.String, descriptor={}], MBeanParameterInfo[description=, name=p2, type=java.lang.String, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=getTriggersOfJob, returnType=java.util.List, signature=[MBeanParameterInfo[description=, name=p1, type=java.lang.String, descriptor={}], MBeanParameterInfo[description=, name=p2, type=java.lang.String, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=getTrigger, returnType=openmbean.CompositeData, signature=[MBeanParameterInfo[description=, name=p1, type=java.lang.String, descriptor={}], MBeanParameterInfo[description=, name=p2, type=java.lang.String, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=getTriggerState, returnType=java.lang.String, signature=[MBeanParameterInfo[description=, name=p1, type=java.lang.String, descriptor={}], MBeanParameterInfo[description=, name=p2, type=java.lang.String, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=deleteCalendar, returnType=void, signature=[MBeanParameterInfo[description=, name=p1, type=java.lang.String, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=getJobNames, returnType=java.util.List, signature=[MBeanParameterInfo[description=, name=p1, type=java.lang.String, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=getTriggerNames, returnType=java.util.List, signature=[MBeanParameterInfo[description=, name=p1, type=java.lang.String, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=pauseTriggerGroup, returnType=void, signature=[MBeanParameterInfo[description=, name=p1, type=java.lang.String, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=resumeTriggerGroup, returnType=void, signature=[MBeanParameterInfo[description=, name=p1, type=java.lang.String, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=scheduleBasicJob, returnType=void, signature=[MBeanParameterInfo[description=, name=p1, type=java.util.Map, descriptor={}], MBeanParameterInfo[description=, name=p2, type=java.util.Map, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=interruptJob, returnType=boolean, signature=[MBeanParameterInfo[description=, name=p1, type=java.lang.String, descriptor={}], MBeanParameterInfo[description=, name=p2, type=java.lang.String, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=pauseJobGroup, returnType=void, signature=[MBeanParameterInfo[description=, name=p1, type=java.lang.String, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=pauseJobsStartingWith, returnType=void, signature=[MBeanParameterInfo[description=, name=p1, type=java.lang.String, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=pauseJobsEndingWith, returnType=void, signature=[MBeanParameterInfo[description=, name=p1, type=java.lang.String, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=pauseJobsContaining, returnType=void, signature=[MBeanParameterInfo[description=, name=p1, type=java.lang.String, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=resumeJobGroup, returnType=void, signature=[MBeanParameterInfo[description=, name=p1, type=java.lang.String, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=resumeJobsStartingWith, returnType=void, signature=[MBeanParameterInfo[description=, name=p1, type=java.lang.String, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=resumeJobsEndingWith, returnType=void, signature=[MBeanParameterInfo[description=, name=p1, type=java.lang.String, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=resumeJobsContaining, returnType=void, signature=[MBeanParameterInfo[description=, name=p1, type=java.lang.String, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=pauseAllTriggers, returnType=void, signature=[], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=resumeAllTriggers, returnType=void, signature=[], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=pauseTriggersStartingWith, returnType=void, signature=[MBeanParameterInfo[description=, name=p1, type=java.lang.String, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=pauseTriggersEndingWith, returnType=void, signature=[MBeanParameterInfo[description=, name=p1, type=java.lang.String, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=pauseTriggersContaining, returnType=void, signature=[MBeanParameterInfo[description=, name=p1, type=java.lang.String, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=resumeTriggersStartingWith, returnType=void, signature=[MBeanParameterInfo[description=, name=p1, type=java.lang.String, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=resumeTriggersEndingWith, returnType=void, signature=[MBeanParameterInfo[description=, name=p1, type=java.lang.String, descriptor={}]], impact=unknown, descriptor={}]
MBeanOperationInfo[description=Operation exposed for management, name=resumeTriggersContaining, returnType=void, signature=[MBeanParameterInfo[description=, name=p1, type=java.lang.String, descriptor={}]], impact=unknown, descriptor={}]
org.quartz.core.QuartzSchedulerMBeanImpl Desc: Information on the management interface of the MBean   
   */
}

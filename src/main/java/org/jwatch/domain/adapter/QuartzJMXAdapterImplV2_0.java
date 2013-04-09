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
package org.jwatch.domain.adapter;

import org.apache.log4j.Logger;
import org.jwatch.domain.connection.QuartzConnectUtil;
import org.jwatch.domain.instance.QuartzInstance;
import org.jwatch.domain.instance.QuartzInstanceUtil;
import org.jwatch.domain.quartz.Job;
import org.jwatch.domain.quartz.Scheduler;
import org.jwatch.domain.quartz.Trigger;
import org.jwatch.domain.quartz.TriggerUtil;
import org.jwatch.util.GlobalConstants;
import org.jwatch.util.JMXUtil;

import javax.management.*;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.TabularDataSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:royrusso@gmail.com">Roy Russo</a>
 *         Date: Apr 12, 2011 4:31:31 PM
 */
public class QuartzJMXAdapterImplV2_0 implements QuartzJMXAdapter
{
   static Logger log = Logger.getLogger(QuartzJMXAdapterImplV2_0.class);

   @Override
   public String getVersion(QuartzInstance quartzInstance, ObjectName objectName) throws Exception
   {
      MBeanServerConnection connection = quartzInstance.getMBeanServerConnection();
      String quartzVersion = (String) connection.getAttribute(objectName, "Version");
      if (!QuartzConnectUtil.isSupported(quartzVersion))
      {
         log.error(GlobalConstants.MESSAGE_WARN_VERSION + " Version:" + quartzVersion);
      }
      return quartzVersion;
   }

   @Override
   public void printAttributes(QuartzInstance quartzInstance, ObjectName objectName) throws Exception
   {
      MBeanInfo info = quartzInstance.getMBeanServerConnection().getMBeanInfo(objectName);
      MBeanAttributeInfo[] attributeInfos = info.getAttributes();
      for (int i = 0; i < attributeInfos.length; i++)
      {
         MBeanAttributeInfo attributeInfo = attributeInfos[i];
         System.out.println(attributeInfo.toString());
      }
   }

   public void printConstructors(QuartzInstance quartzInstance, ObjectName objectName) throws Exception
   {
      MBeanInfo info = quartzInstance.getMBeanServerConnection().getMBeanInfo(objectName);
      MBeanConstructorInfo[] arr = info.getConstructors();
      for (int i = 0; i < arr.length; i++)
      {
         MBeanConstructorInfo s = arr[i];
         System.out.println(s.toString());
      }
   }

   public void printOperations(QuartzInstance quartzInstance, ObjectName objectName) throws Exception
   {
      MBeanInfo info = quartzInstance.getMBeanServerConnection().getMBeanInfo(objectName);
      MBeanOperationInfo[] arr = info.getOperations();
      for (int i = 0; i < arr.length; i++)
      {
         MBeanOperationInfo s = arr[i];
         System.out.println(s.toString());
      }
   }

   public void printNotifications(QuartzInstance quartzInstance, ObjectName objectName) throws Exception
   {
      MBeanInfo info = quartzInstance.getMBeanServerConnection().getMBeanInfo(objectName);
      MBeanNotificationInfo[] arr = info.getNotifications();
      for (int i = 0; i < arr.length; i++)
      {
         MBeanNotificationInfo s = arr[i];
         System.out.println(s.toString());
      }
   }

   @Override
   public void printClassName(QuartzInstance quartzInstance, ObjectName objectName) throws Exception
   {
      MBeanInfo info = quartzInstance.getMBeanServerConnection().getMBeanInfo(objectName);
      System.out.println(info.getClassName() + " Desc: " + info.getDescription());
   }

   @Override
   public Scheduler populateScheduler(QuartzInstance quartzInstance, ObjectName objectName) throws Exception
   {
      Scheduler scheduler = new Scheduler();
      MBeanServerConnection connection = quartzInstance.getMBeanServerConnection();
      scheduler.setObjectName(objectName);
      scheduler.setName((String) connection.getAttribute(objectName, "SchedulerName"));
      scheduler.setInstanceId((String) connection.getAttribute(objectName, "SchedulerInstanceId"));
      scheduler.setJobStoreClassName((String) connection.getAttribute(objectName, "JobStoreClassName"));
      scheduler.setThreadPoolClassName((String) connection.getAttribute(objectName, "ThreadPoolClassName"));
      scheduler.setThreadPoolSize((Integer) connection.getAttribute(objectName, "ThreadPoolSize"));
      scheduler.setShutdown((Boolean) connection.getAttribute(objectName, "Shutdown"));
      scheduler.setStarted((Boolean) connection.getAttribute(objectName, "Started"));
      scheduler.setStandByMode((Boolean) connection.getAttribute(objectName, "StandbyMode"));
      scheduler.setQuartzInstanceUUID(quartzInstance.getUuid());
      scheduler.setVersion(this.getVersion(quartzInstance, objectName));
      return scheduler;
   }

   @Override
   public List getJobDetails(QuartzInstance quartzInstance, String scheduleID) throws Exception
   {
      List<Job> jobs = null;
      Scheduler scheduler = QuartzInstanceUtil.getSchedulerByInstanceId(quartzInstance, scheduleID);
      JMXInput jmxInput = new JMXInput(quartzInstance, new String[]{String.class.getName()}, "AllJobDetails", new Object[]{scheduleID}, scheduler.getObjectName());

      TabularDataSupport tdata = (TabularDataSupport) callJMXAttribute(jmxInput);
      if (tdata != null)
      {
         jobs = new ArrayList<Job>();

         // tdata contains a hashmap, so extract the values of the map, which are CompositeDataSupport type.
         for (Iterator it = tdata.values().iterator(); it.hasNext();)
         {
            // this is a mess, because we don't know what the data wtype will be...
            Object object = (Object) it.next();
            // only deal with javax.management.openmbean.CompositeDataSupport
            if (!(object instanceof CompositeDataSupport))
            {
               continue;
            }

            CompositeDataSupport compositeDataSupport = (CompositeDataSupport) object;
            Job job = new Job();
            job.setQuartzInstanceId(scheduler.getQuartzInstanceUUID());
            job.setSchedulerInstanceId(scheduler.getInstanceId());
            job.setJobName((String) JMXUtil.convertToType(compositeDataSupport, "name"));
            job.setDescription((String) JMXUtil.convertToType(compositeDataSupport, "description"));
            job.setDurability(((Boolean) JMXUtil.convertToType(compositeDataSupport, "durability")).booleanValue());
            job.setShouldRecover(((Boolean) JMXUtil.convertToType(compositeDataSupport, "shouldRecover")).booleanValue());
            job.setGroup((String) JMXUtil.convertToType(compositeDataSupport, "group"));
            job.setJobClass((String) JMXUtil.convertToType(compositeDataSupport, "jobClass"));

            // get Next Fire Time for job
			try {
				List<Trigger> triggers = this.getTriggersForJob(quartzInstance, scheduleID, job.getJobName(), job.getGroup());
				if (triggers != null && triggers.size() > 0) {
					Date nextFireTime = TriggerUtil.getNextFireTimeForJob(triggers);
					job.setNextFireTime(nextFireTime);
					job.setNumTriggers(triggers.size());
					job.setTriggerStatus(TriggerUtil.getTriggerStatusForJob(triggers));
				} else {
					job.setTriggerStatus("NONE");
				}
			} catch (Exception t) {
				log.error("Cannot retrieve triggers for job = " + job.getJobName());
				log.error(t.getMessage());
				job.setNextFireTime(null);
				job.setNumTriggers(0);
				job.setTriggerStatus("BAD TRIGGER");
			}
            log.debug("Loaded job: " + job);
            jobs.add(job);
         }
      }
      return jobs;

      /**
       * FYI
       * {description=Some rand job, durability=true, group=group1, jobClass=org.qtest.HelloJob, jobDataMap=javax.management.openmbean.TabularDataSupport(tabularType=javax.management.openmbean.TabularType(name=JobDataMap,rowType=javax.management.openmbean.CompositeType(name=JobDataMap,items=((itemName=key,itemType=javax.management.openmbean.SimpleType(name=java.lang.String)),(itemName=value,itemType=javax.management.openmbean.SimpleType(name=java.lang.String)))),indexNames=(key)),contents={}), name=myJob, shouldRecover=false}
       * javax.management.openmbean.CompositeType(name=JobDetail,items=((itemName=description,itemType=javax.management.openmbean.SimpleType(name=java.lang.String)),(itemName=durability,itemType=javax.management.openmbean.SimpleType(name=java.lang.Boolean)),(itemName=group,itemType=javax.management.openmbean.SimpleType(name=java.lang.String)),(itemName=jobClass,itemType=javax.management.openmbean.SimpleType(name=java.lang.String)),(itemName=jobDataMap,itemType=javax.management.openmbean.TabularType(name=JobDataMap,rowType=javax.management.openmbean.CompositeType(name=JobDataMap,items=((itemName=key,itemType=javax.management.openmbean.SimpleType(name=java.lang.String)),(itemName=value,itemType=javax.management.openmbean.SimpleType(name=java.lang.String)))),indexNames=(key))),(itemName=name,itemType=javax.management.openmbean.SimpleType(name=java.lang.String)),(itemName=shouldRecover,itemType=javax.management.openmbean.SimpleType(name=java.lang.Boolean))))
       */
   }

   @Override
   public Scheduler getScheduler(QuartzInstance quartzInstance, String scheduleID) throws Exception
   {
      return QuartzInstanceUtil.getSchedulerByInstanceId(quartzInstance, scheduleID);
   }

   @Override
   public List<Trigger> getTriggersForJob(QuartzInstance quartzInstance, String scheduleID, String jobName, String groupName) throws Exception
   {
      Scheduler scheduler = QuartzInstanceUtil.getSchedulerByInstanceId(quartzInstance, scheduleID);

      List<Trigger> triggers = null;

      JMXInput jmxInput = new JMXInput(quartzInstance, new String[]{String.class.getName(), String.class.getName()}, "getTriggersOfJob", new Object[]{jobName, groupName}, scheduler.getObjectName());
      List list = (List) callJMXOperation(jmxInput);
      if (list != null && list.size() > 0)
      {
         triggers = new ArrayList();
         for (int i = 0; i < list.size(); i++)
         {
            CompositeDataSupport compositeDataSupport = (CompositeDataSupport) list.get(i);
            Trigger trigger = new Trigger();
            trigger.setCalendarName((String) JMXUtil.convertToType(compositeDataSupport, "calendarName"));
            trigger.setDescription((String) JMXUtil.convertToType(compositeDataSupport, "description"));
            trigger.setEndTime((Date) JMXUtil.convertToType(compositeDataSupport, "endTime"));
            trigger.setFinalFireTime((Date) JMXUtil.convertToType(compositeDataSupport, "finalFireTime"));
            trigger.setFireInstanceId((String) JMXUtil.convertToType(compositeDataSupport, "fireInstanceId"));
            trigger.setGroup((String) JMXUtil.convertToType(compositeDataSupport, "group"));
            trigger.setJobGroup((String) JMXUtil.convertToType(compositeDataSupport, "jobGroup"));
            trigger.setJobName((String) JMXUtil.convertToType(compositeDataSupport, "jobName"));
            trigger.setMisfireInstruction(((Integer) JMXUtil.convertToType(compositeDataSupport, "misfireInstruction")).intValue());
            trigger.setName((String) JMXUtil.convertToType(compositeDataSupport, "name"));
            trigger.setNextFireTime((Date) JMXUtil.convertToType(compositeDataSupport, "nextFireTime"));
            trigger.setPreviousFireTime((Date) JMXUtil.convertToType(compositeDataSupport, "previousFireTime"));
            trigger.setPriority(((Integer) JMXUtil.convertToType(compositeDataSupport, "priority")).intValue());
            trigger.setStartTime((Date) JMXUtil.convertToType(compositeDataSupport, "startTime"));

            try // get current trigger state
            {
               JMXInput stateJmxInput = new JMXInput(quartzInstance, new String[]{String.class.getName(), String.class.getName()}, "getTriggerState", new Object[]{trigger.getName(), trigger.getGroup()}, scheduler.getObjectName());
               String state = (String) callJMXOperation(stateJmxInput);
               trigger.setSTriggerState(state);
            }
            catch (Throwable tt)
            {
               trigger.setSTriggerState(Trigger.STATE_GET_ERROR);
               log.error("Error getting triggers for job = " + jobName);
            }

            triggers.add(trigger);
         }
      }
      return triggers;

      /**
       * {calendarName=null, description=null, endTime=null, finalFireTime=null, fireInstanceId=1306173819858, group=groupx,
       * jobDataMap=javax.management.openmbean.TabularDataSupport(tabularType=javax.management.openmbean.TabularType
       * (name=JobDataMap,rowType=javax.management.openmbean.CompositeType(name=JobDataMap,items=
       * ((itemName=key,itemType=javax.management.openmbean.SimpleType(name=java.lang.String))
       * ,(itemName=value,itemType=javax.management.openmbean.SimpleType(name=java.lang.String)))),indexNames=(key)),contents={}),
       * jobGroup=groupx, jobName=myJobx, misfireInstruction=0, name=myTriggerx, nextFireTime=Mon May 23 14:12:19 EDT 2011,
       * previousFireTime=Mon May 23 14:11:39 EDT 2011, priority=5, startTime=Mon May 23 14:03:39 EDT 2011}

       javax.management.openmbean.CompositeType(name=Trigger,items=((itemName=calendarName,itemType=javax.management.openmbean.SimpleType(name=java.lang.String)),(itemName=description,itemType=javax.management.openmbean.SimpleType(name=java.lang.String)),(itemName=endTime,itemType=javax.management.openmbean.SimpleType(name=java.util.Date)),(itemName=finalFireTime,itemType=javax.management.openmbean.SimpleType(name=java.util.Date)),(itemName=fireInstanceId,itemType=javax.management.openmbean.SimpleType(name=java.lang.String)),(itemName=group,itemType=javax.management.openmbean.SimpleType(name=java.lang.String)),(itemName=jobDataMap,itemType=javax.management.openmbean.TabularType(name=JobDataMap,rowType=javax.management.openmbean.CompositeType(name=JobDataMap,items=((itemName=key,itemType=javax.management.openmbean.SimpleType(name=java.lang.String)),(itemName=value,itemType=javax.management.openmbean.SimpleType(name=java.lang.String)))),indexNames=(key))),(itemName=jobGroup,itemType=javax.management.openmbean.SimpleType(name=java.lang.String)),(itemName=jobName,itemType=javax.management.openmbean.SimpleType(name=java.lang.String)),(itemName=misfireInstruction,itemType=javax.management.openmbean.SimpleType(name=java.lang.Integer)),(itemName=name,itemType=javax.management.openmbean.SimpleType(name=java.lang.String)),(itemName=nextFireTime,itemType=javax.management.openmbean.SimpleType(name=java.util.Date)),(itemName=previousFireTime,itemType=javax.management.openmbean.SimpleType(name=java.util.Date)),(itemName=priority,itemType=javax.management.openmbean.SimpleType(name=java.lang.Integer)),(itemName=startTime,itemType=javax.management.openmbean.SimpleType(name=java.util.Date))))
       */
   }

   @Override
   public void attachListener(QuartzInstance quartzInstance, String scheduleID) throws Exception
   {
      //To change body of implemented methods use File | Settings | File Templates.
   }

   private Object callJMXAttribute(JMXInput jmxInput) throws Exception
   {
      QuartzInstance quartzInstance = jmxInput.getQuartzInstanceConnection();
      MBeanServerConnection connection = quartzInstance.getMBeanServerConnection();
      return (Object) connection.getAttribute(jmxInput.getObjectName(), jmxInput.getOperation());
   }

   private Object callJMXOperation(JMXInput jmxInput) throws Exception
   {
      QuartzInstance quartzInstance = jmxInput.getQuartzInstanceConnection();
      MBeanServerConnection connection = quartzInstance.getMBeanServerConnection();
      return connection.invoke(jmxInput.getObjectName(), jmxInput.getOperation(), jmxInput.getParameters(), jmxInput.getSignature());
   }

   // johnk additions and controls

   @Override
   public void pauseJob(QuartzInstance quartzInstance, String scheduleID, String jobName, String groupName) throws Exception
   {
      Scheduler scheduler = QuartzInstanceUtil.getSchedulerByInstanceId(quartzInstance, scheduleID);

      JMXInput jmxInput = new JMXInput(quartzInstance, new String[]{String.class.getName(), String.class.getName()}, "pauseJob", new Object[]{jobName, groupName}, scheduler.getObjectName());
      callJMXOperation(jmxInput);
   }

   @Override
   public void resumeJob(QuartzInstance quartzInstance, String scheduleID, String jobName, String groupName) throws Exception
   {
      Scheduler scheduler = QuartzInstanceUtil.getSchedulerByInstanceId(quartzInstance, scheduleID);

      JMXInput jmxInput = new JMXInput(quartzInstance, new String[]{String.class.getName(), String.class.getName()}, "resumeJob", new Object[]{jobName, groupName}, scheduler.getObjectName());
      callJMXOperation(jmxInput);
   }


   @Override
   public void deleteJob(QuartzInstance quartzInstance, String scheduleID, String jobName, String groupName) throws Exception
   {
      Scheduler scheduler = QuartzInstanceUtil.getSchedulerByInstanceId(quartzInstance, scheduleID);

      JMXInput jmxInput = new JMXInput(quartzInstance, new String[]{String.class.getName(), String.class.getName()}, "deleteJob", new Object[]{jobName, groupName}, scheduler.getObjectName());
      callJMXOperation(jmxInput);
   }


   @Override
//   public void triggerJobWithVolatileTrigger(QuartzInstance quartzInstance, String scheduleID, String jobName, String groupName, Map jobDataMap) throws Exception
   public void triggerJob(QuartzInstance quartzInstance, String scheduleID, String jobName, String groupName, Map jobDataMap) throws Exception
   {
      Scheduler scheduler = QuartzInstanceUtil.getSchedulerByInstanceId(quartzInstance, scheduleID);

      // get the jobdatamap from the job, and use it in the run request
      if (jobDataMap == null) {
    	  jobDataMap = new HashMap();
    	  JMXInput jmxInput = new JMXInput(quartzInstance, new String[]{String.class.getName(), String.class.getName()}, "getJobDetail", new Object[]{jobName, groupName}, scheduler.getObjectName());
    	  Object object =  callJMXOperation(jmxInput);
    	  CompositeDataSupport compositeDataSupport = (CompositeDataSupport) object;
    	  TabularDataSupport tdata = (TabularDataSupport) compositeDataSupport.get("jobDataMap");

          // tdata contains a hashmap, so extract the values of the map, which are CompositeDataSupport type.
          for (Iterator it = tdata.values().iterator(); it.hasNext();)
          {
              Object object2 = it.next();
              // only deal with javax.management.openmbean.CompositeDataSupport
              if (!(object2 instanceof CompositeDataSupport)) {
                 continue;
              }
              CompositeDataSupport compositeDataSupport2 = (CompositeDataSupport) object2;
              String key = (String) compositeDataSupport2.get("key");
              String value = (String) compositeDataSupport2.get("value");
              log.debug("key = " + key+" value ="+value);
              jobDataMap.put(key, value);
          }
      }
      JMXInput jmxInput = new JMXInput(quartzInstance, new String[]{String.class.getName(), String.class.getName(), Map.class.getName()}, "triggerJob", new Object[]{jobName, groupName, jobDataMap}, scheduler.getObjectName());
      log.debug("about to execute " + jmxInput.getOperation() + " for scheduleid = " + scheduleID + " job = " + jobName + " group = " + groupName);
      callJMXOperation(jmxInput);
   }





}

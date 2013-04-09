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

package org.jwatch.domain.quartz;

import java.util.Date;
import java.util.Map;

/**
 * @author <a href="mailto:royrusso@gmail.com">Roy Russo</a>
 *         Date: Apr 19, 2011 2:32:40 PM
 */
public class Job
{
   /**
    * JMX Reference: javax.management.openmbean.CompositeType(name=JobDetail,items=((itemName=description,itemType=javax.management.openmbean.SimpleType(name=java.lang.String)),(itemName=durability,itemType=javax.management.openmbean.SimpleType(name=java.lang.Boolean)),(itemName=group,itemType=javax.management.openmbean.SimpleType(name=java.lang.String)),(itemName=jobClass,itemType=javax.management.openmbean.SimpleType(name=java.lang.String)),(itemName=jobDataMap,itemType=javax.management.openmbean.TabularType(name=JobDataMap,rowType=javax.management.openmbean.CompositeType(name=JobDataMap,items=((itemName=key,itemType=javax.management.openmbean.SimpleType(name=java.lang.String)),(itemName=value,itemType=javax.management.openmbean.SimpleType(name=java.lang.String)))),indexNames=(key))),(itemName=name,itemType=javax.management.openmbean.SimpleType(name=java.lang.String)),(itemName=shouldRecover,itemType=javax.management.openmbean.SimpleType(name=java.lang.Boolean))))
    */

   private String quartzInstanceId;
   private String schedulerInstanceId;
   private String description;
   private boolean durability;
   private String group;
   private String jobClass;
   private String jobName;
   private boolean shouldRecover;
   private Map jobDataMap;
   private Date nextFireTime;
   private int numTriggers;
   private String triggerStatus;
   // johnk known trigger status values: WAITING, PAUSED, ACQUIRED, BLOCKED
   // we add NONE if a trigger is not found for a job.

   public String getQuartzInstanceId()
   {
      return quartzInstanceId;
   }

   public void setQuartzInstanceId(String quartzInstanceId)
   {
      this.quartzInstanceId = quartzInstanceId;
   }

   public String getSchedulerInstanceId()
   {
      return schedulerInstanceId;
   }

   public void setSchedulerInstanceId(String schedulerInstanceId)
   {
      this.schedulerInstanceId = schedulerInstanceId;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription(String description)
   {
      this.description = description;
   }

   public boolean isDurability()
   {
      return durability;
   }

   public void setDurability(boolean durability)
   {
      this.durability = durability;
   }

   public String getGroup()
   {
      return group;
   }

   public void setGroup(String group)
   {
      this.group = group;
   }

   public String getJobClass()
   {
      return jobClass;
   }

   public void setJobClass(String jobClass)
   {
      this.jobClass = jobClass;
   }

   public String getJobName()
   {
      return jobName;
   }

   public void setJobName(String jobName)
   {
      this.jobName = jobName;
   }

   public boolean isShouldRecover()
   {
      return shouldRecover;
   }

   public void setShouldRecover(boolean shouldRecover)
   {
      this.shouldRecover = shouldRecover;
   }

   public Map getJobDataMap()
   {
      return jobDataMap;
   }

   public void setJobDataMap(Map jobDataMap)
   {
      this.jobDataMap = jobDataMap;
   }

   public int getNumTriggers()
   {
      return numTriggers;
   }

   public void setNumTriggers(int numTriggers)
   {
      this.numTriggers = numTriggers;
   }

   public Date getNextFireTime()
   {
      return nextFireTime;
   }

   public void setNextFireTime(Date nextFireTime)
   {
      this.nextFireTime = nextFireTime;
   }

   public String getTriggerStatus()
   {
      return triggerStatus;
   }

   public void setTriggerStatus(String triggerStatus)
   {
      this.triggerStatus = triggerStatus;
   }

   @Override
   public String toString()
   {
      final StringBuilder sb = new StringBuilder();
      sb.append("Job");
      sb.append("{quartzInstanceId='").append(quartzInstanceId).append('\'');
      sb.append(", schedulerInstanceId='").append(schedulerInstanceId).append('\'');
      sb.append(", description='").append(description).append('\'');
      sb.append(", durability=").append(durability);
      sb.append(", group='").append(group).append('\'');
      sb.append(", jobClass='").append(jobClass).append('\'');
      sb.append(", jobName='").append(jobName).append('\'');
      sb.append(", shouldRecover=").append(shouldRecover);
      sb.append(", jobDataMap=").append(jobDataMap);
      sb.append(", nextFireTime=").append(nextFireTime);
      sb.append(", numTriggers=").append(numTriggers);
      sb.append(", triggerStatus=").append(triggerStatus);
      sb.append('}');
      return sb.toString();
   }
}

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

package org.jwatch.listener.notification;

import java.util.Date;

/**
 * @author <a href="mailto:royrusso@gmail.com">Roy Russo</a>
 *         Date: May 27, 2011 10:15:33 AM
 */
public class JobEvent
{
   private String calendarName;
   private String jobGroup;
   private String jobName;
   private String schedulerName;
   private String triggerGroup;
   private String triggerName;
   private Date fireTime;
   private Date nextFireTime;
   private Date previousFireTime;
   private Date scheduledFireTime;
   private boolean recovering;
   private long jobRunTime;
   private int refireCount;
   private String schedulerId;
   private String quartzInstanceId;

   public String getCalendarName()
   {
      return calendarName;
   }

   public void setCalendarName(String calendarName)
   {
      this.calendarName = calendarName;
   }

   public String getJobGroup()
   {
      return jobGroup;
   }

   public void setJobGroup(String jobGroup)
   {
      this.jobGroup = jobGroup;
   }

   public String getJobName()
   {
      return jobName;
   }

   public void setJobName(String jobName)
   {
      this.jobName = jobName;
   }

   public String getSchedulerName()
   {
      return schedulerName;
   }

   public void setSchedulerName(String schedulerName)
   {
      this.schedulerName = schedulerName;
   }

   public String getTriggerGroup()
   {
      return triggerGroup;
   }

   public void setTriggerGroup(String triggerGroup)
   {
      this.triggerGroup = triggerGroup;
   }

   public String getTriggerName()
   {
      return triggerName;
   }

   public void setTriggerName(String triggerName)
   {
      this.triggerName = triggerName;
   }

   public Date getFireTime()
   {
      return fireTime;
   }

   public void setFireTime(Date fireTime)
   {
      this.fireTime = fireTime;
   }

   public Date getNextFireTime()
   {
      return nextFireTime;
   }

   public void setNextFireTime(Date nextFireTime)
   {
      this.nextFireTime = nextFireTime;
   }

   public Date getPreviousFireTime()
   {
      return previousFireTime;
   }

   public void setPreviousFireTime(Date previousFireTime)
   {
      this.previousFireTime = previousFireTime;
   }

   public Date getScheduledFireTime()
   {
      return scheduledFireTime;
   }

   public void setScheduledFireTime(Date scheduledFireTime)
   {
      this.scheduledFireTime = scheduledFireTime;
   }

   public boolean isRecovering()
   {
      return recovering;
   }

   public void setRecovering(boolean recovering)
   {
      this.recovering = recovering;
   }

   public long getJobRunTime()
   {
      return jobRunTime;
   }

   public void setJobRunTime(long jobRunTime)
   {
      this.jobRunTime = jobRunTime;
   }

   public int getRefireCount()
   {
      return refireCount;
   }

   public void setRefireCount(int refireCount)
   {
      this.refireCount = refireCount;
   }

   public String getSchedulerId()
   {
      return schedulerId;
   }

   public void setSchedulerId(String schedulerId)
   {
      this.schedulerId = schedulerId;
   }

   public String getQuartzInstanceId()
   {
      return quartzInstanceId;
   }

   public void setQuartzInstanceId(String quartzInstanceId)
   {
      this.quartzInstanceId = quartzInstanceId;
   }

   @Override
   public String toString()
   {
      final StringBuilder sb = new StringBuilder();
      sb.append("JobEvent");
      sb.append("{calendarName='").append(calendarName).append('\'');
      sb.append(", jobGroup='").append(jobGroup).append('\'');
      sb.append(", jobName='").append(jobName).append('\'');
      sb.append(", schedulerName='").append(schedulerName).append('\'');
      sb.append(", triggerGroup='").append(triggerGroup).append('\'');
      sb.append(", triggerName='").append(triggerName).append('\'');
      sb.append(", fireTime=").append(fireTime);
      sb.append(", nextFireTime=").append(nextFireTime);
      sb.append(", previousFireTime=").append(previousFireTime);
      sb.append(", scheduledFireTime=").append(scheduledFireTime);
      sb.append(", recovering=").append(recovering);
      sb.append(", jobRunTime=").append(jobRunTime);
      sb.append(", refireCount=").append(refireCount);
      sb.append(", schedulerId='").append(schedulerId).append('\'');
      sb.append(", quartzInstanceId='").append(quartzInstanceId).append('\'');
      sb.append('}');
      return sb.toString();
   }
}


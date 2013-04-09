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

import org.jwatch.domain.instance.QuartzInstance;
import org.jwatch.domain.quartz.Scheduler;
import org.jwatch.domain.quartz.Trigger;

import javax.management.ObjectName;
import java.util.List;
import java.util.Map;

/**
 * TODO: implement this across other quartz versions.
 *
 * @author <a href="mailto:royrusso@gmail.com">Roy Russo</a>
 *         Date: Apr 12, 2011 4:31:07 PM
 */
public interface QuartzJMXAdapter
{
   public String getVersion(QuartzInstance quartzInstance, ObjectName objectName) throws Exception;

   //public Scheduler getScheduler

   public void printAttributes(QuartzInstance quartzInstance, ObjectName objectName) throws Exception;

   public void printConstructors(QuartzInstance quartzInstance, ObjectName objectName) throws Exception;

   public void printOperations(QuartzInstance quartzInstance, ObjectName objectName) throws Exception;

   public void printNotifications(QuartzInstance quartzInstance, ObjectName objectName) throws Exception;

   void printClassName(QuartzInstance quartzInstance, ObjectName objectName) throws Exception;

   Scheduler populateScheduler(QuartzInstance quartzInstance, ObjectName objectName) throws Exception;

   List getJobDetails(QuartzInstance quartzInstance, String scheduleID) throws Exception;

   Scheduler getScheduler(QuartzInstance quartzInstance, String scheduleID) throws Exception;

   List<Trigger> getTriggersForJob(QuartzInstance quartzInstance, String scheduleID, String jobName, String groupName) throws Exception;

   void attachListener(QuartzInstance quartzInstance, String scheduleID) throws Exception;

   // johnk additions
   void pauseJob(QuartzInstance quartzInstance, String scheduleID, String jobName, String groupName) throws Exception;

   void resumeJob(QuartzInstance quartzInstance, String scheduleID, String jobName, String groupName) throws Exception;

   // this really returns a boolean, but there is now structure to handle it
   void deleteJob(QuartzInstance quartzInstance, String scheduleID, String jobName, String groupName) throws Exception;

   // jobDataMap is really a JobDataMap, but we are always going to pass a null, so just use an Object
//   void triggerJobWithVolatileTrigger(QuartzInstance quartzInstance, String scheduleID, String jobName, String groupName, Map jobDataMap) throws Exception;
   void triggerJob(QuartzInstance quartzInstance, String scheduleID, String jobName, String groupName, Map jobDataMap) throws Exception;

}

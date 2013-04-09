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
import java.util.List;

/**
 * @author <a href="mailto:royrusso@gmail.com">Roy Russo</a>
 *         Date: May 26, 2011 12:06:49 PM
 */
public class TriggerUtil
{
   public static Date getNextFireTimeForJob(List<Trigger> triggers)
   {
      Date theNext = null;
      if (triggers != null && triggers.size() > 0)
      {

         for (int i = 0; i < triggers.size(); i++)
         {
            Trigger trigger = triggers.get(i);
            if (i == 0)  // avoid npe
            {
               theNext = trigger.getNextFireTime();
            }

            if (trigger.getNextFireTime().before(theNext))
            {
               theNext = trigger.getNextFireTime();
            }
         }
      }
      return theNext;
   }
   
   // johnk addtition
   
   public static String getTriggerStatusForJob(List<Trigger> triggers)
   {
      String triggerStatus = "NORMAL";
      if (triggers != null && triggers.size() > 0)
      {
         for (int i = 0; i < triggers.size(); i++)
         {
            Trigger trigger = triggers.get(i);
            if ("PAUSED".equalsIgnoreCase(trigger.getSTriggerState())) {
            	triggerStatus = "PAUSED";
            	break;
            } else if ("COMPLETED".equalsIgnoreCase(trigger.getSTriggerState())) {
                if ("NORMAL".equalsIgnoreCase(trigger.getSTriggerState())) {
                	triggerStatus = "COMPLETED";
                }
            	continue;
            }
            if (!triggerStatus.equalsIgnoreCase(trigger.getSTriggerState())) {
            	triggerStatus = trigger.getSTriggerState();
            }
         }
      }
      return triggerStatus;
   }

}

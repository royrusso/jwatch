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

import org.jwatch.domain.quartz.Scheduler;

import java.util.List;

/**
 * @author <a href="mailto:royrusso@gmail.com">Roy Russo</a>
 *         Date: Apr 28, 2011 5:01:26 PM
 */
public class QuartzInstanceUtil
{
   /**
    * @param quartzInstance
    * @param instanceId               scheduler-instance-id
    * @return
    */
   public static Scheduler getSchedulerByInstanceId(QuartzInstance quartzInstance, String instanceId)
   {
      List list = quartzInstance.getSchedulerList();
      if (list != null && list.size() > 0)
      {
         for (int i = 0; i < list.size(); i++)
         {
            Scheduler s = (Scheduler) list.get(i);
            if (s.getInstanceId().equals(instanceId))
            {
               return s;
            }
         }
      }
      return null;
   }
}

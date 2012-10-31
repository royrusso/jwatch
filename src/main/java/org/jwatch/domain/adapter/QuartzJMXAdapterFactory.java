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

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

/**
 * @author <a href="mailto:royrusso@gmail.com">Roy Russo</a>
 *         Date: Apr 20, 2011 4:41:37 PM
 */
public class QuartzJMXAdapterFactory
{
   /**
    * Currently creates the v2.0.0 adapter. In the future, we will need to have an adapter map that returns the correct
    * adapter object to use depending on version.
    *
    * @param objectName
    * @param connection
    * @return
    * @throws Exception
    */
   public static QuartzJMXAdapter initQuartzJMXAdapter(ObjectName objectName, MBeanServerConnection connection) throws Exception
   {
      QuartzJMXAdapter jmxAdapter = new QuartzJMXAdapterImplV2_0();
      return jmxAdapter;
   }
}

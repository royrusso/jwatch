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
package org.jwatch.domain.connection;

import org.jwatch.domain.instance.QuartzInstance;
import org.jwatch.listener.settings.QuartzConfig;

/**
 * All JMS connections to Quartz MBeans are handled here.
 * Because we cannot rely on the quartz-jar locally, we have to communicate/find mbean attribs using native JMX calls and
 * not use QuartzSchedulerMBean.
 * <p/>
 * Conection params are persisted in memory in {@link org.jwatch.domain.instance.QuartzInstanceService#quartzInstanceMap}
 *
 * @author <a href="mailto:royrusso@gmail.com">Roy Russo</a>
 *         Date: Apr 9, 2011 9:50:04 AM
 */
public interface QuartzConnectService
{
   /**
    * Initializes the connection to a quartzinstance.
    *
    * @param config
    * @return
    * @throws java.io.IOException
    * @throws javax.management.MalformedObjectNameException
    */
   public QuartzInstance initInstance(QuartzConfig config) throws Exception;
}

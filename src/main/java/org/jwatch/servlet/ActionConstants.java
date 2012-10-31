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
package org.jwatch.servlet;

/**
 * @author <a href="mailto:royrusso@gmail.com">Roy Russo</a>
 *         Date: Apr 8, 2011 4:11:45 PM
 */
public class ActionConstants
{
   public static final String LOAD_INSTANCES = "get_all_instances";
   public static final String LOAD_INSTANCE_DETAILS = "get_instance_details";
   public static final String CREATE_INSTANCE = "create_instance";
   public static final String LOAD_SCHEDULERS = "get_schedulers";
   public static final String LOAD_JOBS = "get_jobs";
   public static final String LOAD_SCHEDULERINFO = "get_scheduler_info";
   public static final String LOAD_TRIGGERS_FOR_JOB = "get_job_triggers";
   public static final String MONITOR_JOBS = "monitor_jobs";
}

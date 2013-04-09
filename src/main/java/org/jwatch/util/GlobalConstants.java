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
package org.jwatch.util;

/**
 * @author <a href="mailto:royrusso@gmail.com">Roy Russo</a>
 *         Date: Apr 7, 2011 2:20:52 PM
 */
public class GlobalConstants
{
   public static final String JSON_DATA_ROOT_KEY = "data";
   public static final String JSON_SUCCESS_KEY = "success";
   public static final String JSON_MESSAGE = "message";
   public static final String JSON_TOTAL_COUNT = "totalCount";

   public static final String MESSAGE_FAILED_CONNECT = "Connection Failed";
   public static final String MESSAGE_ERR_CHECK_LOG = "Error processing request! Check log for details.";
   public static final String MESSAGE_ERR_LOAD_JOBS = "Error Loading Jobs! Check log for details.";
   public static final String MESSAGE_WARN_VERSION = "Your version of Quartz may not be supported!";
   public static final String MESSAGE_ERR_CLOSE_CONFIG = "Unable to close config file handle.";
   public static final String MESSAGE_CONFIG_EMPTY = "Empty configuration details.";
   public static final String MESSAGE_ERR_LOAD_SCHEDULER = "Error loading Scheduler!";
   public static final String MESSAGE_ERR_LOAD_TRIGGERS = "Error loading Triggers for Job";
   public static final String MESSAGE_ERR_TAIL_JOBS = "Error monitoring jobs list.";
   public static final String MESSAGE_ERR_ACTION_UNKNOWN = "Error processing request! Unknown action.";

   // johnk additions
   public static final String MESSAGE_ERR_PAUSE_JOB = "Error pausing Job";
   public static final String MESSAGE_ERR_RESUME_JOB = "Error resuming Job";
   public static final String MESSAGE_ERR_DELETE_JOB = "Error deleting Job";
   public static final String MESSAGE_ERR_RUN_JOB = "Error running Job";

}

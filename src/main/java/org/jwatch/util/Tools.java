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

import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author <a href="mailto:royrusso@gmail.com">Roy Russo</a>
 *         Date: Apr 7, 2011 3:19:10 PM
 */
public class Tools
{
   public static final String DATE_FORMAT_DEFAULT = "MM/dd/yy HH:mm:ss z";

   public static String generateUUID()
   {
      UUID id = UUID.randomUUID();
      return id.toString();
   }

   public static String toStringFromDate(Date date, String format)
   {
      try
      {
         if (StringUtils.trimToNull(format) == null)
         {
            format = DATE_FORMAT_DEFAULT;
         }
         SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
         return simpleDateFormat.format(date);
      }
      catch (Exception e)
      {
         //
      }
      return null;
   }
}

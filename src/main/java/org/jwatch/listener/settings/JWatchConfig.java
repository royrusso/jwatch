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

package org.jwatch.listener.settings;

/**
 * @author <a href="mailto:royrusso@gmail.com">Roy Russo</a>
 *         Date: 7/12/11 9:16 AM
 */
public class JWatchConfig
{
    public static final String PROPS_FILE_NAME = "jwatch.properties";
    public static final String DB_FILE_PREFIX = "jwatchdb";
    public static final String QUARTZ_INSTANCE_FILE = "jwatch.json";
    // johnk - directory for all jwatch files
    public static final String QUARTZ_INSTANCE_DIR = "jwatch";
    private static boolean DBDebug = true;
    private static int maxShowEvents = 50;

    public static boolean isDBDebug()
    {
        return DBDebug;
    }

    public static void setDBDebug(boolean DBDebug)
    {
        JWatchConfig.DBDebug = DBDebug;
    }

    public static int getMaxShowEvents()
    {
        return maxShowEvents;
    }

    public static void setMaxShowEvents(int maxShowEvents)
    {
        JWatchConfig.maxShowEvents = maxShowEvents;
    }
}

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

import java.util.LinkedList;

/**
 * Keeps a linkedlist of the currently executed tasks. The size of the linkedlist
 *
 * @author <a href="mailto:royrusso@gmail.com">Roy Russo</a>
 *         Date: Jun 14, 2011 9:50:15 AM
 */
public class EventService
{
    public static final int DEFAULT_MAX_EVENT_LIST_SIZE = 1000;
    public static final int DEFAULT_MAX_SHOW_EVENT_LIST_SIZE = 100;

    private static LinkedList<JobEvent> eventList;
    private static int maxEventListSize;
    private static int maxShowEventListSize;

    public static LinkedList<JobEvent> getEventList()
    {
        return eventList;
    }

    public static int getMaxEventListSize()
    {
        return maxEventListSize;
    }

    public static int getMaxShowEventListSize()
    {
        return maxShowEventListSize;
    }

    public static void setMaxShowEventListSize(int maxShowEventListSize)
    {
        EventService.maxShowEventListSize = maxShowEventListSize;
    }

    public static void setMaxEventListSize(int maxEventListSize)
    {
        EventService.maxEventListSize = maxEventListSize;
    }

    public static void addEvent(JobEvent event)
    {
        if (eventList == null)
        {
            eventList = new LinkedList<JobEvent>();
        }

        // add event to the top of the list
        eventList.addFirst(event);

        // trim the list to size
        resizeEventList();
    }

    private static void resizeEventList()
    {
        if (eventList.size() > maxEventListSize)
        {
            eventList.removeLast();
            resizeEventList();
        }
        else
        {
            return;
        }
    }
}

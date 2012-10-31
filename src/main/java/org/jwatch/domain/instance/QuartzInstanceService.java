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

import org.apache.log4j.Logger;
import org.jwatch.domain.connection.QuartzConnectService;
import org.jwatch.domain.connection.QuartzConnectServiceImpl;
import org.jwatch.listener.settings.QuartzConfig;
import org.jwatch.util.SettingsUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Maps contain QuartzInstance objects loaded from the settings file and on-demand by the ui.
 *
 * @author <a href="mailto:royrusso@gmail.com">Roy Russo</a>
 *         Date: Apr 7, 2011 5:04:10 PM
 */
public class QuartzInstanceService
{
   static Logger log = Logger.getLogger(QuartzInstanceService.class);

   private static HashMap<String, QuartzInstance> quartzInstanceMap;

   public static HashMap<String, QuartzInstance> getQuartzInstanceMap()
   {
      return quartzInstanceMap;
   }

   public static void setQuartzInstanceMap(HashMap<String, QuartzInstance> quartzInstanceMap)
   {
      QuartzInstanceService.quartzInstanceMap = quartzInstanceMap;
   }

   public static void putQuartzInstance(QuartzInstance quartzInstance)
   {
      if (quartzInstance != null)
      {
         if (quartzInstanceMap == null)
         {
            quartzInstanceMap = new HashMap();
         }
         quartzInstanceMap.put(quartzInstance.getUuid(), quartzInstance);
      }
   }

   /**
    * loads configurations from the settings file and attempts connections on each config. It then populates in-memory
    * map with {@link QuartzInstance} objects.
    */
   public static void initQuartzInstanceMap()
   {
      if (quartzInstanceMap == null)
      {
         quartzInstanceMap = new HashMap();
      }

      List<QuartzConfig> configs = SettingsUtil.loadConfig();
      if (configs != null && configs.size() > 0)
      {
         log.info("Found " + configs.size() + " Quartz instances in settings file.");
         for (int i = 0; i < configs.size(); i++)
         {
            QuartzConfig config = configs.get(i);
            QuartzConnectService quartzConnectService = new QuartzConnectServiceImpl();
            QuartzInstance quartzInstance = null;
            try
            {
               quartzInstance = quartzConnectService.initInstance(config);
               config.setConnected(true);
            }
            catch (Throwable t)
            {
               log.error("Failed to connect! " + config.toString(), t);
            }

            if (quartzInstance != null)
            {
               QuartzInstanceService.putQuartzInstance(quartzInstance);
               log.debug(quartzInstance.toString());
            }
         }
      }
   }

   /**
    * fetch instance object from map.
    *
    * @param qiid
    * @return
    */
   public static QuartzInstance getQuartzInstanceByID(String qiid)
   {
      if (quartzInstanceMap != null)
      {
         return quartzInstanceMap.get(qiid);
      }
      return null;
   }

}

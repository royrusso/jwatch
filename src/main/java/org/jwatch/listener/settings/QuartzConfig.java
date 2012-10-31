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

import org.jwatch.domain.instance.QuartzInstance;

/**
 * Class contains connection settings and unique ID for a specific quartz instance.
 * This class gets persisted in the settings file (so don't add extra shit to it!)
 *
 * @author <a href="mailto:royrusso@gmail.com">Roy Russo</a>
 *         Date: Apr 13, 2011 11:56:31 AM
 */
public class QuartzConfig
{
   private String uname;
   private String uuid;
   private String host;
   private int port;
   private String userName;
   private String password;
   private boolean isConnected;

   public QuartzConfig()
   {
   }

   public QuartzConfig(String uuid, String host, int port, String userName, String password)
   {
      this.uuid = uuid;
      this.host = host;
      this.port = port;
      this.uname = this.getUname();
      this.userName = userName;
      this.password = password;
   }

   public QuartzConfig(QuartzInstance quartzInstance)
   {
      this.uuid = quartzInstance.getUuid();
      this.host = quartzInstance.getHost();
      this.port = quartzInstance.getPort();
      this.uname = this.getUname();
      this.userName = quartzInstance.getUserName();
      this.password = quartzInstance.getPassword();
   }

   public String getUname()
   {
      this.uname = getHost() + getPort();
      return uname;
   }

   public void setUname(String uname)
   {
      this.uname = uname;
   }

   public String getUuid()
   {
      return uuid;
   }

   public void setUuid(String uuid)
   {
      this.uuid = uuid;
   }

   public String getHost()
   {
      return host;
   }

   public void setHost(String host)
   {
      this.host = host;
   }

   public int getPort()
   {
      return port;
   }

   public void setPort(int port)
   {
      this.port = port;
   }

   public String getUserName()
   {
      return userName;
   }

   public void setUserName(String userName)
   {
      this.userName = userName;
   }

   public String getPassword()
   {
      return password;
   }

   public void setPassword(String password)
   {
      this.password = password;
   }

   public boolean isConnected()
   {
      return isConnected;
   }

   public void setConnected(boolean connected)
   {
      isConnected = connected;
   }

   @Override
   public String toString()
   {
      final StringBuilder sb = new StringBuilder();
      sb.append("QuartzConfig");
      sb.append("{uname='").append(uname).append('\'');
      sb.append(", uuid='").append(uuid).append('\'');
      sb.append(", host='").append(host).append('\'');
      sb.append(", port=").append(port);
      sb.append(", userName='").append(userName).append('\'');
      sb.append(", password='").append(password).append('\'');
      sb.append(", isConnected=").append(isConnected);
      sb.append('}');
      return sb.toString();
   }
}

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

import org.jwatch.domain.instance.QuartzInstance;

import javax.management.ObjectName;

/**
 * Generic class used to call JMX Operations from an adapter.
 *
 * @author <a href="mailto:royrusso@gmail.com">Roy Russo</a>
 *         Date: Apr 28, 2011 4:49:06 PM
 */
public class JMXInput
{
   private QuartzInstance quartzInstance;
   private String[] signature;
   private String operation;
   private Object[] parameters;
   private ObjectName objectName;

   public JMXInput()
   {
   }

   public JMXInput(QuartzInstance quartzInstance, String[] signature, String operation, Object[] parameters, ObjectName objectName)
   {
      this.quartzInstance = quartzInstance;
      this.signature = signature;
      this.operation = operation;
      this.parameters = parameters;
      this.objectName = objectName;
   }

   public QuartzInstance getQuartzInstanceConnection()
   {
      return quartzInstance;
   }

   public void setQuartzInstanceConnection(QuartzInstance quartzInstance)
   {
      this.quartzInstance = quartzInstance;
   }

   public String[] getSignature()
   {
      return signature;
   }

   public void setSignature(String[] signature)
   {
      this.signature = signature;
   }

   public String getOperation()
   {
      return operation;
   }

   public void setOperation(String operation)
   {
      this.operation = operation;
   }

   public Object[] getParameters()
   {
      return parameters;
   }

   public void setParameters(Object[] parameters)
   {
      this.parameters = parameters;
   }

   public ObjectName getObjectName()
   {
      return objectName;
   }

   public void setObjectName(ObjectName objectName)
   {
      this.objectName = objectName;
   }
}

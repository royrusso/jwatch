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

import javax.management.openmbean.CompositeDataSupport;
import java.util.Date;

/**
 * @author <a href="mailto:royrusso@gmail.com">Roy Russo</a>
 *         Date: May 4, 2011 11:31:59 AM
 */
public class JMXUtil
{
   /**
    * Return the value already cast to a type.
    * <p/>
    * -	BIGDECIMAL = new SimpleType("java.math.BigDecimal");
    * -	BIGINTEGER = new SimpleType("java.math.BigInteger");
    * -	BOOLEAN = new SimpleType("java.lang.Boolean");
    * -	BYTE = new SimpleType("java.lang.Byte");
    * -	CHARACTER = new SimpleType("java.lang.Character");
    * -	DATE = new SimpleType("java.util.Date");
    * -	DOUBLE = new SimpleType("java.lang.Double");
    * -	FLOAT = new SimpleType("java.lang.Float");
    * -	INTEGER = new SimpleType("java.lang.Integer");
    * -	LONG = new SimpleType("java.lang.Long");
    * -	OBJECTNAME = new SimpleType("javax.management.ObjectName");
    * -	SHORT = new SimpleType("java.lang.Short");
    * -	STRING = new SimpleType("java.lang.String");
    * -	VOID = new SimpleType("java.lang.Void");
    * +	BIGDECIMAL = new SimpleType<BigDecimal>("java.math.BigDecimal");
    * +	BIGINTEGER = new SimpleType<BigInteger>("java.math.BigInteger");
    * +	BOOLEAN = new SimpleType<Boolean>("java.lang.Boolean");
    * +	BYTE = new SimpleType<Byte>("java.lang.Byte");
    * +	CHARACTER = new SimpleType<Character>("java.lang.Character");
    * +	DATE = new SimpleType<Date>("java.util.Date");
    * +	DOUBLE = new SimpleType<Double>("java.lang.Double");
    * +	FLOAT = new SimpleType<Float>("java.lang.Float");
    * +	INTEGER = new SimpleType<Integer>("java.lang.Integer");
    * +	LONG = new SimpleType<Long>("java.lang.Long");
    * +	OBJECTNAME =
    * +	  new SimpleType<ObjectName>("javax.management.ObjectName");
    * +	SHORT = new SimpleType<Short>("java.lang.Short");
    * +	STRING = new SimpleType<String>("java.lang.String");
    * +	VOID = new SimpleType<Void>("java.lang.Void");
    */
   public static Object convertToType(CompositeDataSupport compositeDataSupport, String key)
   {
      if (compositeDataSupport.getCompositeType().getType(key).getClassName().equals("java.lang.String"))
      {
         return StringUtils.trimToEmpty((String) compositeDataSupport.get(key));
      }
      else if (compositeDataSupport.getCompositeType().getType(key).getClassName().equals("java.lang.Boolean"))
      {
         return compositeDataSupport.get(key);
      }
      else if (compositeDataSupport.getCompositeType().getType(key).getClassName().equals("java.util.Date"))
      {
         return (Date) compositeDataSupport.get(key);
      }
      else if (compositeDataSupport.getCompositeType().getType(key).getClassName().equals("java.lang.Integer"))
      {
         return (Integer) compositeDataSupport.get(key);
      }
      else if (compositeDataSupport.getCompositeType().getType(key).getClassName().equals("java.lang.Long"))
      {
         return (Long) compositeDataSupport.get(key);
      }
      return new Object();
   }
}

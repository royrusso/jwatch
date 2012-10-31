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
 */
package org.jwatch.util;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author <a href="mailto:royrusso@gmail.com">Roy Russo</a>
 *         Date: Apr 8, 2011 4:28:10 PM
 */
public class
      JSONUtil {
    public static Map<String, String> convertRequestToMap(HttpServletRequest request) {
        Map returnMap = new HashMap();
        if (request.getParameterMap() != null) {
            for (Iterator it = request.getParameterMap().entrySet().iterator(); it.hasNext(); ) {
                Map.Entry entry = (Map.Entry) it.next();
                String k = (String) entry.getKey();
                String v = request.getParameter(k);
                returnMap.put(k, v);
            }
        }
        return returnMap;
    }

    public static JSONObject buildError(String message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(GlobalConstants.JSON_SUCCESS_KEY, false);
        jsonObject.put(GlobalConstants.JSON_MESSAGE, message);
        return jsonObject;
    }

}

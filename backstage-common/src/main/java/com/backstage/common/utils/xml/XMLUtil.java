package com.backstage.common.utils.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class XMLUtil {

    /**
     * xml转换
     *
     * @param xmlStr
     * @return
     */
    public static List<Map<String, Object>> parserXmlStr(String xmlStr, String[] composeFields, String dateField) {
        List<Map<String, Object>> flightList = new ArrayList();
        try {
            Document document = DocumentHelper.parseText(xmlStr);
            Element employees = document.getRootElement();
            int fltIndex = 0;

            for (Iterator i = employees.elementIterator(); i.hasNext(); ++fltIndex) {
                Element employee = (Element) i.next();
                Map<String, Object> flight = new HashMap();
                //数组深拷贝
                String[] tmp = new String[composeFields.length];
                System.arraycopy(composeFields, 0, tmp, 0, composeFields.length);
                List<String> fltkeyList = Arrays.asList(tmp);

                Element node;
                String nodeName;
                for (Iterator j = employee.elementIterator(); j.hasNext(); flight.put(nodeName, checkVal(node.getData()))) {
                    node = (Element) j.next();
                    nodeName = node.getName();
                    if (fltkeyList.contains(nodeName)) {
                        if (!StringUtils.isEmpty(dateField) && nodeName.equals(dateField)) {
                            String planDate = node.getData().toString();
                            planDate = planDate.substring(0, 10);
                            fltkeyList.set(fltkeyList.indexOf(nodeName), planDate);
                        } else {
                            fltkeyList.set(fltkeyList.indexOf(nodeName), node.getData().toString());
                        }
                    }
                }

                String fltkey = "";

                String s;
                for (Iterator var17 = fltkeyList.iterator(); var17.hasNext(); fltkey = fltkey + s) {
                    s = (String) var17.next();
                }

                flight.put("fltkey", fltkey);
                flightList.add(flight);
            }

            boolean var14 = false;
        } catch (DocumentException var13) {
            System.out.println("parser flight Xml error::" + now());
        }

        return flightList;
    }

    public static String now() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(new Date());
    }

    private static Object checkVal(Object dateObject) {
        if (dateObject == null) {
            return null;
        } else {
            String dateStr = dateObject.toString();
            Calendar cal = Calendar.getInstance();
            int year = cal.get(1);
            Pattern pattern = Pattern.compile("^" + year + "-(0|1)[0-9]-[0-3][0-9] [0-2][0-9]:[0-6][0-9]:[0-6][0-9].[0-9]");
            boolean isDate = pattern.matcher(dateStr).matches();
            if (isDate) {
                dateStr = dateStr.substring(0, 19);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                try {
                    return sdf.parse(dateStr);
                } catch (ParseException var12) {
                    System.out.println("strToDate(Object dateObject) error::" + now());
                }
            }

            String dataStr = dateObject.toString();
            dataStr = dataStr.trim();
            Pattern doublePattern = Pattern.compile("^[0-9]*[.][0-9]*");
            boolean isDouble = doublePattern.matcher(dataStr).matches();
            if (isDouble) {
                return Double.parseDouble(dataStr);
            } else {
                Pattern intPattern = Pattern.compile("^[0-9]+");
                boolean isInt = intPattern.matcher(dataStr).matches();
                if (isInt) {
                    return Integer.parseInt(dataStr);
                } else if (dateObject instanceof String) {
                    String str = (String) dateObject;
                    str = str.trim();
                    return str.equals("") ? null : str;
                } else {
                    return dateObject;
                }
            }
        }
    }
}

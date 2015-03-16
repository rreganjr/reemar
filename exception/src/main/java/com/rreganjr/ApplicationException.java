/*
 * Copyright 2015 Ron Regan Jr. All Rights Reserved.
 * 
 * This file is part of Reemar - a collaborative system for Requirements 
 * Elicitation, Elaboration, Refinement and Management.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.rreganjr;

import java.util.Formatter;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Base exception with help for formatting.
 * 
 * This uses a pattern of static factory methods used to construct
 * instances of an exception. For example:
 * 
 * throw ApplicationException.notImplemented();
 * 
 * This facilitates testing of exception cases.
 * 
 * @author reemar.app@gmail.com
 */
public class ApplicationException extends RuntimeException {
	static final Log log = LogFactory.getLog(ApplicationException.class);
    static final ResourceBundle messages = ResourceBundle.getBundle(ApplicationException.class.getName());
    static final long serialVersionUID = 0;

    static final String NULL_ARG = "<null>";

    protected static interface MessageKey {
        public String name();
    }

    /**
     * These keys identify the message string in the ApplicationException*.properties files
     */
    static enum MessageKeys implements MessageKey {
        MSG_NOT_IMPLEMENTED,
        MSG_NOT_SUPPORTED,
        MSG_FAILED_TO_INITIALIZE_COMPONENT,
        MSG_UNSUPPORTED_DATE,
        MSG_PRE_GENERATED,
        MSG_MISSING_RESOURCE_BUNDLE,
        MSG_NO_RESOURCE_BUNDLE,
        MSG_INVALID_VALUE,
        MSG_MISSING_VALUE,
        MSG_INVALID_VALUES,
        MSG_MISSING_VALUES
    }

	public static ApplicationException notImplemented() {
		return new ApplicationException(MessageKeys.MSG_NOT_IMPLEMENTED);
	}

	public static ApplicationException notSupported(Object object) {
		return new ApplicationException(MessageKeys.MSG_NOT_SUPPORTED, object);
	}

	/**
	 * @param type -
	 *            the class that failed to initialize
	 * @param cause -
	 *            the reason the initialization failed
	 * @return
	 */
	public static ApplicationException failedToInitializeComponent(Class<?> type, Throwable cause) {
		return new ApplicationException(MessageKeys.MSG_FAILED_TO_INITIALIZE_COMPONENT, type.getName(), cause);
	}

	/**
	 * @param type -
	 *            the class that failed to initialize
	 * @param details -
	 *            a detailed message if an exception wasn't the cause of the
	 *            failure.
	 * @return
	 */
	public static ApplicationException failedToInitializeComponent(Class<?> type, String details) {
		return new ApplicationException(MessageKeys.MSG_FAILED_TO_INITIALIZE_COMPONENT, type.getName(), details);
	}

	/**
	 * @param dateString
	 * @return
	 */
	public static ApplicationException unsupportedDateString(String dateString) {
		return new ApplicationException(MessageKeys.MSG_UNSUPPORTED_DATE, dateString);
	}

	/**
	 * @param bundleName
	 * @param cause
	 * @return
	 */
	public static ApplicationException missingResourceBundle(String bundleName, Throwable cause) {
		return new ApplicationException(cause, MessageKeys.MSG_MISSING_RESOURCE_BUNDLE, bundleName, cause);
	}

	/**
	 * @return
	 */
	public static ApplicationException missingResourceBundle() {
		return new ApplicationException(MessageKeys.MSG_NO_RESOURCE_BUNDLE);
	}


	/**
	 * Create an exception with a message that the expected paramName
	 * contained the invalid value paramValue.
	 *  
	 * @param paramName
	 * @param paramValue
	 */
	public static ApplicationException invalidParameterValue(String paramName, String paramValue) {
		return new ApplicationException(MessageKeys.MSG_INVALID_VALUE, paramName, paramValue);
	}

	/**
	 * Create an exception with a message that the expected paramName
	 * contained the invalid value paramValue.
	 *  
	 * @param paramName
	 * @param paramValue
	 */
	public static ApplicationException invalidParameterValues(String paramName, String paramValue) {
		return new ApplicationException(MessageKeys.MSG_INVALID_VALUES, paramName, paramValue);
	}

	/**
	 * 
	 * @param paramName - the name of the parameter that had no value.
	 * @return ApplicationException
	 */
	public static ApplicationException missingParameterValue(String paramName) {
		return new ApplicationException(MessageKeys.MSG_MISSING_VALUE, paramName);
	}

	/**
	 * 
	 * @param paramName - the name of the parameters that have no value.
	 * @return ApplicationException
	 */
	public static ApplicationException missingParameterValues(String paramName) {
		return new ApplicationException(MessageKeys.MSG_MISSING_VALUES, paramName);
	}

	/**
	 * @param msgKey -
	 *            the resource key to a format string appropriate for java.util.Formatter
	 * @param args -
	 *            variable args list that map to the variables in the format
	 *            string
	 */
	protected ApplicationException(MessageKey msgKey, Object... args) {
		super(format(msgKey, prettyArray(args)));
		if (log.isDebugEnabled()) {
			log.debug(getMessage());
		}
	}

	/**
	 * @param cause -
	 *            a caught exception that resulted in this exception
     * @param msgKey -
     *            the resource key to a format string appropriate for java.util.Formatter
	 * @param args -
	 *            variable args list that map to the variables in the format
	 *            string
	 */
	protected ApplicationException(Throwable cause, MessageKey msgKey, Object... args) {
		super(format(msgKey, prettyArray(args)), cause);
		if (log.isDebugEnabled()) {
			log.debug(getMessage(), cause);
		}
	}

    /**
     * For testing only
     */
    ApplicationException(String message) {
        super(message);
    }

	/**
	 * Format the supplied format string with the supplied arguments and return the string.
	 * Cleanup the formatter.
	 *
     * @param msgKey -
     *            the resource key to a format string appropriate for java.util.Formatter
	 * @param args - arguments to fill into the format string
	 * @return a formatted string generated from the supplied format string and args.
	 * @see java.util.Formatter
	 */
	static String format(MessageKey msgKey, Object... args) {
		Formatter formatter = new Formatter();
		try {
            String format = messages.getString(msgKey.name());
			return formatter.format(format, prettyArray(args)).toString();
		} finally {
			formatter.close();
		}
	}

	static Object[] prettyArray(Object[] args) {
        if (args == null) {
            return new Object[0];
        }
		Object[] pretty = new Object[args.length];
		int i = 0;
		for (Object o : args) {
			if (o != null) {
				if (Object[].class.isAssignableFrom(o.getClass())) {
					StringBuilder b = new StringBuilder();
					Object[] inner = (Object[])args[i];
					if (inner.length > 0) {
						for (int x = 0; x < inner.length - 1; x++) {
							b.append(pretty(inner[x]));
							b.append(", ");
						}
						b.append(pretty(inner[inner.length -1]));
					}
					pretty[i] = b.toString();
				} else {
					pretty[i] = pretty(o);
				}
			} else {
				pretty[i] = NULL_ARG;
			}
			i++;
		}
		return pretty;
	}

	static String pretty(Object o) {
		if (o == null) {
			return NULL_ARG;
		} else if (o instanceof String) {
			return "\"" + o + "\"";
		} else {
			return o.toString();
		}
	}

    /**
     * @param paramNames an array of parameter names
     * @return a String of the supplied array delimited by ", "
     */
    protected static String asCommaDelimitedList(String[] paramNames) {
        StringBuilder sb = new StringBuilder();
        if (paramNames != null) {
            for (String paramName : paramNames) {
                sb.append(paramName);
                sb.append(", ");
            }
            if (sb.length() > 2) {
                sb.setLength(sb.length() - 2);
            }
        }
        return sb.toString();
    }

    protected static String asKeyValueCommaDelimitedList(String[] paramNames, Object[] values) {
        StringBuilder sb = new StringBuilder();
        if (paramNames != null) {
            int i = 0;
            for (i=0; i<paramNames.length; i++) {
                sb.append(paramNames[i]);
                sb.append(": ");
                sb.append(values[i]);
                sb.append(", ");
            }
            if (sb.length() > 2) {
                sb.setLength(sb.length() - 2);
            }
        }
        return sb.toString();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (getClass().hashCode());
		result = prime * result + ((getMessage() == null) ? 0 : getMessage().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApplicationException other = (ApplicationException) obj;
		if (getMessage() == null) {
			if (other.getMessage() != null) {
				return false;
			} else {
				return true;
			}
		}
		return getMessage().equals(other.getMessage());
	}
}

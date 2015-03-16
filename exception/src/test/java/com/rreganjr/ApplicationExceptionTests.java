package com.rreganjr;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ron on 3/15/15.
 */
public class ApplicationExceptionTests {

    @Test
    public void formatNoArgs() throws Exception {
        assertEquals(ApplicationException.messages.getString(ApplicationException.MessageKeys.MSG_NOT_IMPLEMENTED.name()),
                ApplicationException.format(ApplicationException.MessageKeys.MSG_NOT_IMPLEMENTED));
    }

    @Test
    public void notImplemented() throws Exception {
        assertTrue("The notImplemented() factory method returns ApplicationException with a not empty message",
                (ApplicationException.notImplemented().getMessage().length() > 0));

        assertEquals("The notImplemented() factory method returns ApplicationException with not implemented message",
                new ApplicationException(ApplicationException.MessageKeys.MSG_NOT_IMPLEMENTED), ApplicationException.notImplemented());
    }

    @Test
    public void notSupported() throws Exception {
        String testObject = "testObject";
        assertTrue("The notSupported() factory method returns ApplicationException with a not empty message",
                (ApplicationException.notSupported(testObject).getMessage().length() > 0));

        assertEquals("The notSupported() factory method returns ApplicationException with not supported message",
                new ApplicationException(ApplicationException.MessageKeys.MSG_NOT_SUPPORTED, testObject), ApplicationException.notSupported(testObject));
    }

    @Test
    public void failedToInitializeComponentWithException() throws Exception {
        String testObject = "testObject";
        Exception exception = new Exception();

        assertTrue("The failedToInitializeComponent() factory method returns ApplicationException with a not empty message",
                (ApplicationException.failedToInitializeComponent(testObject.getClass(), exception).getMessage().length() > 0));

        assertEquals("The failedToInitializeComponent() factory method returns ApplicationException with exception message",
                new ApplicationException(ApplicationException.MessageKeys.MSG_FAILED_TO_INITIALIZE_COMPONENT, testObject.getClass().getName(), exception),
                ApplicationException.failedToInitializeComponent(testObject.getClass(), exception));
    }

    @Test
    public void failedToInitializeComponent() throws Exception {
        String testObject = "testObject";
        String message = "a message.";
        assertTrue("The failedToInitializeComponent() factory method returns ApplicationException with a not empty message",
                (ApplicationException.failedToInitializeComponent(testObject.getClass(), message).getMessage().length() > 0));

        assertEquals("The failedToInitializeComponent() factory method returns ApplicationException with error message",
                new ApplicationException(ApplicationException.MessageKeys.MSG_FAILED_TO_INITIALIZE_COMPONENT, testObject.getClass().getName(), message),
                ApplicationException.failedToInitializeComponent(testObject.getClass(), message));
    }

    @Test
    public void unsupportedDateString() throws Exception {
        String unsupportedDateString = "1/1/1";
        assertTrue("The unsupportedDateString() factory method returns ApplicationException with a not empty message",
                (ApplicationException.unsupportedDateString(unsupportedDateString).getMessage().length() > 0));

        assertEquals("The unsupportedDateString() factory method returns ApplicationException with unsupported date message",
                new ApplicationException(ApplicationException.MessageKeys.MSG_UNSUPPORTED_DATE, unsupportedDateString),
                ApplicationException.unsupportedDateString(unsupportedDateString));
    }

    @Test
    public void missingResourceBundle() throws Exception {
        String bundleName = "bundleName";
        Exception exception = new Exception();
        assertTrue("The missingResourceBundle() factory method returns ApplicationException with a not empty message",
                (ApplicationException.missingResourceBundle(bundleName, exception).getMessage().length() > 0));

        assertEquals("The unsupportedDateString() factory method returns ApplicationException with unsupported date message",
                new ApplicationException(ApplicationException.MessageKeys.MSG_MISSING_RESOURCE_BUNDLE, bundleName, exception),
                ApplicationException.missingResourceBundle(bundleName, exception));
    }

    @Test
    public void missingResourceBundleNoBundle() throws Exception {
        assertTrue("The missingResourceBundle() factory method returns ApplicationException with a not empty message",
                (ApplicationException.missingResourceBundle().getMessage().length() > 0));

        assertEquals("The missingResourceBundle() factory method returns ApplicationException with unsupported date message",
                new ApplicationException(ApplicationException.MessageKeys.MSG_NO_RESOURCE_BUNDLE),
                ApplicationException.missingResourceBundle());
    }

    @Test
    public void invalidParameterValue() throws Exception {
        String paramName = "name";
        String paramValue = "value";
        assertTrue("The invalidParameterValue() factory method returns ApplicationException with a not empty message",
                (ApplicationException.invalidParameterValue(paramName, paramValue).getMessage().length() > 0));

        assertEquals("The invalidParameterValue() factory method returns ApplicationException with a invalid parameter value message",
                new ApplicationException(ApplicationException.MessageKeys.MSG_INVALID_VALUE, paramName, paramValue),
                ApplicationException.invalidParameterValue(paramName, paramValue));
    }

    @Test
    public void invalidParameterValues() throws Exception {
        String paramName = "name";
        String paramValues = "value, value";
        assertTrue("The invalidParameterValues() factory method returns ApplicationException with a not empty message",
                (ApplicationException.invalidParameterValues(paramName, paramValues).getMessage().length() > 0));

        assertEquals("The invalidParameterValues() factory method returns ApplicationException with a invalid parameter values message",
                new ApplicationException(ApplicationException.MessageKeys.MSG_INVALID_VALUES, paramName, paramValues),
                ApplicationException.invalidParameterValues(paramName, paramValues));
    }

    @Test
    public void missingParameterValue() throws Exception {
        String paramName = "name";
        assertTrue("The missingParameterValue() factory method returns ApplicationException with a not empty message",
                (ApplicationException.missingParameterValue(paramName).getMessage().length() > 0));

        assertEquals("The missingParameterValue() factory method returns ApplicationException with not missing parameter value message",
                new ApplicationException(ApplicationException.MessageKeys.MSG_MISSING_VALUE, paramName),
                ApplicationException.missingParameterValue(paramName));
    }

    @Test
    public void missingParameterValues() throws Exception {
        String paramName = "name";
        assertTrue("The missingParameterValues() factory method returns ApplicationException with a not empty message",
                (ApplicationException.missingParameterValues(paramName).getMessage().length() > 0));

        assertEquals("The missingParameterValues() factory method returns ApplicationException with missing parameter values message",
                new ApplicationException(ApplicationException.MessageKeys.MSG_MISSING_VALUES, paramName), ApplicationException.missingParameterValues(paramName));
    }

    @Test
    public void logDebuggingInConstructor() throws Exception {
        // TODO: how do we know logging is working? add an appender?
        Logger log = Logger.getLogger(ApplicationException.class);
        Level originalLevel = log.getLevel();
        log.setLevel(Level.DEBUG);
        new ApplicationException(ApplicationException.MessageKeys.MSG_NOT_IMPLEMENTED);
        log.setLevel(originalLevel);
    }

    @Test
    public void logDebuggingOffInConstructor() throws Exception {
        // TODO: how do we know logging is working? add an appender?
        Logger log = Logger.getLogger(ApplicationException.class);
        Level originalLevel = log.getLevel();
        log.setLevel(Level.ERROR);
        new ApplicationException(ApplicationException.MessageKeys.MSG_NOT_IMPLEMENTED);
        log.setLevel(originalLevel);
    }

    @Test
    public void logDebuggingConstructorWithException() throws Exception {
        // TODO: how do we know logging is working? add an appender?
        Logger log = Logger.getLogger(ApplicationException.class);
        Level myLoggerOriginalLevel = log.getLevel();
        log.setLevel(Level.DEBUG);
        new ApplicationException(new Exception(), ApplicationException.MessageKeys.MSG_NOT_IMPLEMENTED);
    }

    @Test
    public void logDebuggingOffConstructorWithException() throws Exception {
        // TODO: how do we know logging is working? add an appender?
        Logger log = Logger.getLogger(ApplicationException.class);
        Level originalLevel = log.getLevel();
        log.setLevel(Level.ERROR);
        new ApplicationException(new Exception(), ApplicationException.MessageKeys.MSG_NOT_IMPLEMENTED);
        log.setLevel(originalLevel);
    }

    @Test
    public void prettyWithNull() throws Exception {
        assertEquals("Pretty value for a null argument is " + ApplicationException.NULL_ARG, ApplicationException.NULL_ARG, ApplicationException.pretty(null));
    }

    @Test
    public void prettyArrayWithNull() throws Exception {
        assertTrue("prettyArray value for a null argument is an empty Array", ApplicationException.prettyArray(null).length == 0);
    }

    @Test
    public void prettyArrayWithArrayContainingNull() throws Exception {
        Object[] args = new Object[2];
        Object[] expected = new Object[]{ApplicationException.NULL_ARG, ApplicationException.NULL_ARG};
        assertArrayEquals("prettyArray value for array of nulls", expected, ApplicationException.prettyArray(args));
    }

    @Test
    public void prettyArrayWithVariousTypesOfArguments() throws Exception {
        Object[] args = new Object[]{new Integer(0),new Long(1), new Double(1.1), "string"};
        Object[] expected = new Object[]{"0", "1", "1.1", "\"string\""};
        assertArrayEquals("prettyArray value for array of values", expected, ApplicationException.prettyArray(args));
    }

    @Test
    public void prettyArrayWithArrayContainingArray() throws Exception {
        Object[] args = new Object[]{new Object[]{new Integer(0),new Long(1), new Double(1.1), "inner"}, new Object[]{}, "outer"};
        Object[] expected = new Object[]{"0, 1, 1.1, \"inner\"", "", "\"outer\""};
        assertArrayEquals("prettyArray value for array of values", expected, ApplicationException.prettyArray(args));
    }

    @Test
    public void prettyWithString() throws Exception {
        String string = "A string";
        assertEquals("Pretty value for a string argument is \"string\"", "\"" + string + "\"", ApplicationException.pretty(string));
    }

    @Test
    public void prettyWithObject() throws Exception {
        Object obj = new Long(1);
        assertEquals("Pretty value for a string argument is obj.toString", obj.toString(), ApplicationException.pretty(obj));
    }

    @Test
    public void asCommaDelimitedListNull() throws Exception {
        String[] params = null;
        String expected = "";
        assertEquals("asCommaDelimitedList with null arg returns empty string", expected, ApplicationException.asCommaDelimitedList(params));
    }

    @Test
    public void asCommaDelimitedListEmpty() throws Exception {
        String[] params = new String[]{};
        String expected = "";
        assertEquals("asCommaDelimitedList with empty array arg returns empty string ", expected, ApplicationException.asCommaDelimitedList(params));
    }

    @Test
    public void asCommaDelimitedList() throws Exception {
        String[] params = new String[]{"one", "two", "three"};
        String expected = "one, two, three";
        assertEquals("asCommaDelimitedList converts array to string of comma delimited values", expected, ApplicationException.asCommaDelimitedList(params));
    }

    @Test
    public void asKeyValueCommaDelimitedListNull() throws Exception {
        String[] params = null;
        String[] values = null;
        String expected = "";
        assertEquals("asKeyValueCommaDelimitedList with null parameters return empty string", expected, ApplicationException.asKeyValueCommaDelimitedList(params, values));
    }

    @Test
    public void asKeyValueCommaDelimitedListEmpty() throws Exception {
        String[] params = new String[]{};
        String[] values = new String[]{};
        String expected = "";
        assertEquals("asKeyValueCommaDelimitedList with empty parameters return empty string", expected, ApplicationException.asKeyValueCommaDelimitedList(params, values));
    }

    @Test
    public void asKeyValueCommaDelimitedList() throws Exception {
        String[] params = new String[]{"a", "b", "c"};
        String[] values = new String[]{"1", "2", "3"};
        String expected = "a: 1, b: 2, c: 3";
        assertEquals("asKeyValueCommaDelimitedList with parameters returns string of key = value comma delimited list",
                expected, ApplicationException.asKeyValueCommaDelimitedList(params, values));
    }

    @Test
    public void testEquals() throws Exception {
        ApplicationException exception1 = ApplicationException.notImplemented();
        ApplicationException exception2 = ApplicationException.notImplemented();
        ApplicationException exception3 = new ApplicationException(null);
        ApplicationException exception4 = new ApplicationException(null);
        assertTrue("An exception is equal to itself.", exception1.equals(exception1));
        assertTrue("An exception with a null message is equal to itself.", exception3.equals(exception3));
        assertTrue("Two exceptions with the same type and message are equal.", exception1.equals(exception2));
        assertTrue("Two exceptions with a null message are equal.", exception3.equals(exception4));
        assertFalse("An exception is not equal to null.", exception1.equals(null));
        assertFalse("An exception is not equal to another type of object.", exception1.equals(new Integer(1)));
        assertFalse("An exception is not equal to another exception with a null message.", exception1.equals(exception3));
        assertFalse("An exception is not equal to another exception with a null message.", exception3.equals(exception1));
    }

    @Test
    public void testHashCode() throws Exception {
        ApplicationException exception1 = ApplicationException.notImplemented();
        ApplicationException exception2 = ApplicationException.notImplemented();
        final Integer hashCode1 = new Integer(exception1.hashCode());
        final Integer hashCode2 = new Integer(exception2.hashCode());
        assertEquals(hashCode1, hashCode2);
        assertFalse("hashCode will not be zero.", hashCode1.equals(new Integer(0)));
    }

    @Test
    public void testHashCodeNullMessage() throws Exception {
        ApplicationException exception1 = new ApplicationException(null);
        ApplicationException exception2 = new ApplicationException(null);
        final Integer hashCode1 = new Integer(exception1.hashCode());
        final Integer hashCode2 = new Integer(exception2.hashCode());
        assertEquals(hashCode1, hashCode2);
        assertFalse("hashCode will not be zero.", hashCode1.equals(new Integer(0)));
    }

}

// LoadedSampleForm.java
package edu.umn.se.trap.test.generate;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import edu.umn.se.trap.util.FormDataLoader;

/**
 * A wrapper class to emulate a regular saved form map. This class is filled with form data by
 * loading a standard Java properties file.
 * 
 * @author planeman
 * 
 */
public class LoadedSampleForm implements Map<String, String>
{
    private final Properties formProperties;

    public LoadedSampleForm(String path)
    {
        formProperties = FormDataLoader.loadFormData(path);
    }

    @Override
    public void clear()
    {
        formProperties.clear();
    }

    @Override
    public Object clone()
    {
        return formProperties.clone();
    }

    public boolean contains(Object value)
    {
        return formProperties.contains(value);
    }

    @Override
    public boolean containsKey(Object key)
    {
        return formProperties.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value)
    {
        return formProperties.containsValue(value);
    }

    public Enumeration<Object> elements()
    {
        return formProperties.elements();
    }

    @Override
    public Set<java.util.Map.Entry<String, String>> entrySet()
    {
        Set<java.util.Map.Entry<String, String>> entries = new HashSet<java.util.Map.Entry<String, String>>();
        for (java.util.Map.Entry<Object, Object> entry : formProperties.entrySet())
        {
            FormEntry newEntry = new FormEntry((String) entry.getKey(), (String) entry.getValue());
            entries.add(newEntry);
        }
        return entries;
    }

    @Override
    public boolean equals(Object o)
    {
        return formProperties.equals(o);
    }

    @Override
    public String get(Object key)
    {
        return (String) formProperties.get(key);
    }

    public String getProperty(String key, String defaultValue)
    {
        return formProperties.getProperty(key, defaultValue);
    }

    public String getProperty(String key)
    {
        return formProperties.getProperty(key);
    }

    @Override
    public int hashCode()
    {
        return formProperties.hashCode();
    }

    @Override
    public boolean isEmpty()
    {
        return formProperties.isEmpty();
    }

    @Override
    public Set<String> keySet()
    {
        Set<String> keys = new HashSet<String>();
        for (Object key : formProperties.keySet())
        {
            keys.add((String) key);
        }

        return keys;
    }

    public Enumeration<Object> keys()
    {
        return formProperties.keys();
    }

    public void list(PrintStream out)
    {
        formProperties.list(out);
    }

    public void list(PrintWriter out)
    {
        formProperties.list(out);
    }

    public void load(InputStream inStream) throws IOException
    {
        formProperties.load(inStream);
    }

    public void load(Reader reader) throws IOException
    {
        formProperties.load(reader);
    }

    public void loadFromXML(InputStream in) throws IOException, InvalidPropertiesFormatException
    {
        formProperties.loadFromXML(in);
    }

    public Enumeration<?> propertyNames()
    {
        return formProperties.propertyNames();
    }

    @Override
    public String put(String key, String value)
    {
        return (String) formProperties.put(key, value);
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> t)
    {
        formProperties.putAll(t);
    }

    @Override
    public String remove(Object key)
    {
        return (String) formProperties.remove(key);
    }

    public void save(OutputStream out, String comments)
    {
        formProperties.save(out, comments);
    }

    public Object setProperty(String key, String value)
    {
        return formProperties.setProperty(key, value);
    }

    @Override
    public int size()
    {
        return formProperties.size();
    }

    public void store(OutputStream out, String comments) throws IOException
    {
        formProperties.store(out, comments);
    }

    public void store(Writer writer, String comments) throws IOException
    {
        formProperties.store(writer, comments);
    }

    public void storeToXML(OutputStream os, String comment, String encoding) throws IOException
    {
        formProperties.storeToXML(os, comment, encoding);
    }

    public void storeToXML(OutputStream os, String comment) throws IOException
    {
        formProperties.storeToXML(os, comment);
    }

    public Set<String> stringPropertyNames()
    {
        return formProperties.stringPropertyNames();
    }

    @Override
    public String toString()
    {
        return formProperties.toString();
    }

    @Override
    public Collection<String> values()
    {
        Collection<String> vals = new ArrayList<String>();
        for (Object val : formProperties.values())
        {
            vals.add((String) val);
        }
        return vals;
    }

    public class FormEntry implements java.util.Map.Entry<String, String>
    {

        private final String key;
        private final String value;

        public FormEntry(String key, String value)
        {
            this.key = key;
            this.value = value;
        }

        /*
         * (non-Javadoc)
         * @see java.util.Map.Entry#getKey()
         */
        @Override
        public String getKey()
        {
            // TODO Auto-generated method stub
            return null;
        }

        /*
         * (non-Javadoc)
         * @see java.util.Map.Entry#getValue()
         */
        @Override
        public String getValue()
        {
            // TODO Auto-generated method stub
            return null;
        }

        /*
         * (non-Javadoc)
         * @see java.util.Map.Entry#setValue(java.lang.Object)
         */
        @Override
        public String setValue(String value)
        {
            // TODO Auto-generated method stub
            return null;
        }

    }
}

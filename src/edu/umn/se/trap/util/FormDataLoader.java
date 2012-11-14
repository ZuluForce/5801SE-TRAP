package edu.umn.se.trap.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormDataLoader
{
    private static final Logger log = LoggerFactory.getLogger(FormDataLoader.class);

    public static Properties loadFormData(String filename)
    {
        return loadFormData(filename, false);
    }

    public static Properties loadFormData(String filename, boolean noException)
    {
        FileInputStream IIConfig;
        try
        {
            IIConfig = new FileInputStream(filename);
        }
        catch (FileNotFoundException fe)
        {
            log.error("Cannot find properties file: {}", filename);

            File directory = new File(".");
            log.debug("CWD: {}", directory.getAbsolutePath());

            if (noException)
            {
                return new Properties(); // empty config
            }
            else
            {
                throw new RuntimeException(fe);
            }
        }

        return loadFormData(IIConfig, noException);
    }

    public static Properties loadFormData(InputStream IIConfig)
    {
        return loadFormData(IIConfig, false);
    }

    /**
     * Load the property file <filename>. If the file isn't found or there is an error loading it,
     * if the noException flag is true an empty properties object will be returned. Otherwise it
     * will result in a RuntimeException.
     * 
     * @param filename
     *            Name of the properties file to load
     * @param noException
     *            If the property file cannot be loaded should we throw an exception or return an
     *            empty config?
     * @return
     */
    public static Properties loadFormData(InputStream IIConfig, boolean noException)
    {
        Properties settings = new Properties();

        try
        {
            settings.load(IIConfig);
        }
        catch (IOException ioe)
        {
            log.error("Failed to load config.properties: {}", ioe.getMessage());

            if (noException)
            {
                return settings;
            }
            else
            {
                throw new RuntimeException(ioe);
            }
        }

        return settings;
    }
}

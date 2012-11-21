/*****************************************************************************************
 * Copyright (c) 2012 Dylan Bettermann, Andrew Helgeson, Brian Maurer, Ethan Waytas
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ****************************************************************************************/
package edu.umn.se.trap.data;

/**
 * This object holds all information related to the conference attended. Not all fields presented in
 * this object are required for a valid form. All string values will be null if not set.
 * 
 * @author planeman
 * 
 */
public class ConferenceInfo
{
    /** Title of the attended conference */
    private String justificationConferenceTitle;

    /** Whether or not the user presented at the conference */
    private boolean justificationPresented;

    /** Title of the presentation if the user presented */
    private String justificationPresentationTitle;

    /** Abstract of the presentation if the user presented */
    private String justificationPresentationAbstract;

    /** The acknowledgement given if the user presented */
    private String justificationPresentationAcknowledge;

    /** Justification for using non-sponsored funds */
    private String justificationNonSponsored;

    /** Justification for using sponsored funds */
    private String justificationSponsored;

    /**
     * Get the presentation title.
     * 
     * @return - The presentation title. If the user didn't present this will be null.
     */
    public String getJustificationPresentationTitle()
    {
        return justificationPresentationTitle;
    }

    /**
     * Set the presentation title.
     * 
     * @param justificationPresentationTitle - The title of the presentation.
     */
    public void setJustificationPresentationTitle(String justificationPresentationTitle)
    {
        this.justificationPresentationTitle = justificationPresentationTitle;
    }

    /**
     * Get the title of the conference.
     * 
     * @return - Get the title of the conference. If the title isn't set this will be null.
     */
    public String getJustificationConferenceTitle()
    {
        return justificationConferenceTitle;
    }

    /**
     * Set the title of the conference
     * 
     * @param justificationConferenceTitle - The title of the conference.
     */
    public void setJustificationConferenceTitle(String justificationConferenceTitle)
    {
        this.justificationConferenceTitle = justificationConferenceTitle;
    }

    /**
     * Check if the user presented at the attended conference.
     * 
     * @return - boolean true if the user presented, otherwise false
     */
    public boolean isJustificationPresented()
    {
        return justificationPresented;
    }

    /**
     * Set the user presented flag
     * 
     * @param presented - String representation of the input form presentation value. If the value
     *            is equal to yes, y, or true, the presented flag is set to true. Otherwise it is
     *            false.
     */
    public void setJustificationPresented(String presented)
    {
        if (presented.compareTo("yes") == 0 || presented.compareTo("y") == 0
                || presented.compareTo("true") == 0)
        {
            justificationPresented = true;
        }
        else
        {
            justificationPresented = false;
        }
    }

    /**
     * Get the presentation abstract
     * 
     * @return - The presentation abstract. If the abstract was not set (ie the user didn't
     *         present), this value will be null.
     */
    public String getJustificationPresentationAbstract()
    {
        return justificationPresentationAbstract;
    }

    /**
     * Set the presentation abstract.
     * 
     * @param justificationPresentationAbstract - The abstract for the presentation.
     */
    public void setJustificationPresentationAbstract(String justificationPresentationAbstract)
    {
        this.justificationPresentationAbstract = justificationPresentationAbstract;
    }

    /**
     * Get the funding acknowledgment from the presentation.
     * 
     * @return - The presentation acknowledgment given by the user if he/she presented. This will be
     *         null if not set.
     */
    public String getJustificationPresentationAcknowledge()
    {
        return justificationPresentationAcknowledge;
    }

    /**
     * Set the presentation acknowledgment.
     * 
     * @param justificationPresentationAcknowledge - The acknowledgment for the presentation.
     */
    public void setJustificationPresentationAcknowledge(String justificationPresentationAcknowledge)
    {
        this.justificationPresentationAcknowledge = justificationPresentationAcknowledge;
    }

    /**
     * Get the justification for the use of non-sponsored funding.
     * 
     * @return - The justification for use of non-sponsored funding. This will be null if not set.
     */
    public String getJustificationNonSponsored()
    {
        return justificationNonSponsored;
    }

    /**
     * Set the justification for the use of non-sponsored funding.
     * 
     * @param justificationNonSponsored - The justification to use for non-sponsored funding.
     */
    public void setJustificationNonSponsored(String justificationNonSponsored)
    {
        this.justificationNonSponsored = justificationNonSponsored;
    }

    /**
     * Get the justification for the use of sponsored funding.
     * 
     * @return - the justification for the use of sponsored funding. This will be null if not set.
     */
    public String getJustificationSponsored()
    {
        return justificationSponsored;
    }

    /**
     * Set the justification for the use of sponsored funding.
     * 
     * @param justificationSponsored - The justification to use for sponsored funding.
     */
    public void setJustificationSponsored(String justificationSponsored)
    {
        this.justificationSponsored = justificationSponsored;
    }

    /**
     * Create a string representation of this ConferenceInfo object.
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Conference Information:\n");
        sb.append(String.format("\tTitle: %s\n", justificationConferenceTitle));
        sb.append(String.format("\tJustification Sponsored: %s\n", justificationSponsored));
        sb.append(String.format("\tJustification NonSponsored: %s\n", justificationNonSponsored));
        sb.append(String.format("\tPresented: %s\n", justificationPresented));
        if (justificationPresented)
        {
            sb.append(String.format("\tPresentation Title: %s\n", justificationPresentationTitle));
            sb.append(String.format("\tPresentation Abstract: %s\n",
                    justificationPresentationAbstract));
            sb.append(String.format("\tPresentation Acknowledgement: %s\n",
                    justificationPresentationAcknowledge));
        }

        return sb.toString();
    }

}

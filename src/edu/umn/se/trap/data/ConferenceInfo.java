/*****************************************************************************************
 * Copyright (c) 2012 Dylan Bettermann, Andrew Helgeson, Brian Maurer, Ethan Waytas
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 ****************************************************************************************/
package edu.umn.se.trap.data;

/**
 * @author planeman
 * 
 */
public class ConferenceInfo
{
    private String justificationConferenceTitle;
    private boolean justificationPresented;
    private String justificationPresentationTitle;
    private String justificationPresentationAbstract;
    private String justificationPresentationAcknowledge;
    private String justificationNonSponsored;
    private String justificationSponsored;

    public String getJustificationPresentationTitle()
    {
        return justificationPresentationTitle;
    }

    public void setJustificationPresentationTitle(String justificationPresentationTitle)
    {
        this.justificationPresentationTitle = justificationPresentationTitle;
    }

    public String getJustificationConferenceTitle()
    {
        return justificationConferenceTitle;
    }

    public void setJustificationConferenceTitle(String justificationConferenceTitle)
    {
        this.justificationConferenceTitle = justificationConferenceTitle;
    }

    public boolean isJustificationPresented()
    {
        return justificationPresented;
    }

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

    public String getJustificationPresentationAbstract()
    {
        return justificationPresentationAbstract;
    }

    public void setJustificationPresentationAbstract(String justificationPresentationAbstract)
    {
        this.justificationPresentationAbstract = justificationPresentationAbstract;
    }

    public String getJustificationPresentationAcknowledge()
    {
        return justificationPresentationAcknowledge;
    }

    public void setJustificationPresentationAcknowledge(String justificationPresentationAcknowledge)
    {
        this.justificationPresentationAcknowledge = justificationPresentationAcknowledge;
    }

    public String getJustificationNonSponsored()
    {
        return justificationNonSponsored;
    }

    public void setJustificationNonSponsored(String justificationNonSponsored)
    {
        this.justificationNonSponsored = justificationNonSponsored;
    }

    public String getJustificationSponsored()
    {
        return justificationSponsored;
    }

    public void setJustificationSponsored(String justificationSponsored)
    {
        this.justificationSponsored = justificationSponsored;
    }

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

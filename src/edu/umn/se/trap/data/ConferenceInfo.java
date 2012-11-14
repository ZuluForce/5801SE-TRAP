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

    private String justificationForTravel;
    private String justificationConferenceTitle;
    private boolean justificationPresented;
    private String justificationPresentationAbstract;
    private String justificationPresentationAcknowledge;
    private String justificationNonSponsored;
    private String justificationSponsored;

    public String getJustificationForTravel()
    {
        return justificationForTravel;
    }

    public void setJustificationForTravel(String justificationForTravel)
    {
        this.justificationForTravel = justificationForTravel;
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

    public void setJustificationPresented(boolean justificationPresented)
    {
        this.justificationPresented = justificationPresented;
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

}

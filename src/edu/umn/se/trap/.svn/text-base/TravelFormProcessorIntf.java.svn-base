//TravelFormProcessorIntf.java
/**
 * Copyright (c) 2012, Ian De Silva
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 * - Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * - Redistributions in binary form must reproduce the above copyright notice, 
 *   this list of conditions and the following disclaimer in the documentation 
 *   and/or other materials provided with the distribution.
 * - Neither the name of the University of Minnesota nor the names of its 
 *   contributors may be used to endorse or promote products derived from this 
 *   software without specific prior written permission.
 *   
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 */
package edu.umn.se.trap;

import java.util.Map;

/**
 * This defines the interface between the driver of the travel 
 * reimbursement system and the processing code for checking and generating the
 * travel reimbursement form.
 * 
 * NOTE:  When you implement this interface, it is good practice to create
 * your own exceptions for the project.  We expect this to be done for this 
 * project.
 * 
 * @author desilva
 */
public interface TravelFormProcessorIntf
{
    /**
     * An enumeration representing the form's status.
     */
    public static enum FORM_STATUS 
    {   
        /**
         * The form has not passed the submission checks.
         */
        DRAFT, 
        
        /**
         * The form has passed all checked required for submission of the form.
         */
        SUBMITTED
    };
    
    
    /**
     * Sets the user id (X500) of the user working on a form.
     * @param userId  the X500 id of the user working on a travel reimbursement
     *      form.
     * @throws Exception  if the user id is invalid.  SEE NOTE IN CLASS HEADER.
     */
    public void setUser(String userId) throws Exception;
    
    
    /**
     * Gets the user id of the user currently working on forms.
     * @return  the X500 user id of the user currently working on travel 
     *      reimbursement forms.
     */
    public String getUser();
    
    
    /**
     * Gets a list of currently saved form data for the set user.
     * @return  a map containing the integer identifier of for each form in the
     *      system belonging to the current user as the key and the travel 
     *      form's metadata.
     * @see {@link TravelFormMetadata} for more information.
     */
    public Map<Integer, TravelFormMetadata> getSavedForms() throws Exception;
    
    /**
     * Clears the entire collection of saved form data from the database in 
     * which they were stored.  This includes submitted and completed forms 
     * regardless of which user is set.
     * @throws Exception  if the collection of saved forms could not be cleared.
     */
    public void clearSavedForms() throws Exception;
    
    
    /**
     * Gets the saved form data for a given saved form.
     * @param formId  the identifier of the saved form data to get.
     * @return  the saved form data.
     * @throws Exception  if the form data could not be retrieved.  SEE NOTE IN 
     *      CLASS HEADER.
     */
    public Map<String, String> getSavedFormData(Integer formId) 
            throws Exception;
    
    
    /**
     * Saves a new set of form data for later.  This does not check the form 
     *      data, only that no invalid keys have been submitted.
     * @param formData  the data for the form
     * @param description  a description of the form data to save.
     * @return  the form data's identifier.
     * @throws Exception  if the form data could not be saved or the map 
     *      contained invalid keys.  SEE NOTE IN CLASS HEADER.
     */
    public Integer saveFormData(Map<String, String> formData,
                                String description) 
            throws Exception;
    
    
    /**
     * Saves a form into a specific form id, overwriting any information 
     *      already saved under that id.  This does not check the form 
     *      data, only that no invalid keys have been submitted.
     * @param formData  the form data to save
     * @param id  the identifier to save the form data under.  This must be in 
     *      the list of saved forms with an existing description.
     * @return  the form data's identifier
     * @throws Exception  if the form data could not be saved or the map 
     *      contained invalid keys.  SEE NOTE IN CLASS HEADER.
     */
    public Integer saveFormData(Map<String, String> formData, 
                                Integer id) 
            throws Exception;
    
    
    /**
     * Save the completed form data and check the form data for formatting 
     * errors and process the data in terms of the business rules.
     * @param formId  the id of the saved form data to submit.
     * @throws Exception  if there is an error in the data formatting or if the 
     *      data violates one of the business rules.  SEE NOTE IN CLASS HEADER.
     */
    public void submitFormData(Integer formId) throws Exception;
    
    
    /**
     * Gets a completed form.
     * @param formId  the id of a submitted (checked) form.
     * @return  the completed form as a mapping of string keys to string values.
     * @throws Exception  if the specified form has not been completed (passed 
     *      submission).  SEE NOTE IN CLASS HEADER.
     */
    public Map<String, String> getCompletedForm(Integer formId) throws Exception;
}

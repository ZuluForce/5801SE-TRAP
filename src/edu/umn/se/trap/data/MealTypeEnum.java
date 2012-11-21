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
 * An enumeration of the various meal types. The unset value is used to help when distinguishing
 * between an empty expense and a partially created one:
 * 
 * {@link MealExpense#isEmpty()}
 * 
 * @author andrewh
 * 
 */
public enum MealTypeEnum
{
    /** Meal of champions */
    BREAKFAST,

    /** aka luncheon */
    LUNCH,

    /** The one where I eat a lot and fall asleep */
    DINNER,

    /**
     * sentinel value meaning the enum isn't set...Or I'm a hobbit and one of my 6 meals isn't
     * listed here. Just checking to see if you are reading my comments of obvious things.
     */
    NOT_SET
}

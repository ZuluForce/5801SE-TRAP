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
 * An enumeration of all possible transportation types as they would show up in a form.
 * 
 * @author planeman
 * 
 */
public enum TransportationTypeEnum
{
    /** Air travel */
    AIR,

    /** A car... */
    CAR,

    /** Public transportation. Likely a bus but that doesn't matter. */
    PUBLIC_TRANSPORTATION,

    /** Gas to put in your hungry car */
    GAS,

    /** Parking to give your car a rest */
    PARKING,

    /** Luggage to haul your unnecessarily large amount of clothing */
    BAGGAGE,

    /** These things that ride on rails on the ground. Thats so old skool right?? */
    RAIL,

    /** Expensive, often smelly car driven by someone else to your destination */
    TAXI,

    /** Where you have to pay to use a road with the hopes that they actually maintain it */
    TOLL,

    /**
     * A sentinel value meaning the enum isn't set...Or I am using an unconventional means of
     * transport like my jet pack or soon to come teleportation device.
     */
    NOT_SET
}

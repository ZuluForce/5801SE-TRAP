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
package edu.umn.se.trap.util;

/**
 * 
 * @author andrewh
 * 
 * @param <L> Type of the left pair element
 * @param <R> Type of the right pair element
 */
public class Pair<L, R>
{
    private final L left;
    private final R right;

    /**
     * Construct a pair. This pair is immutable so this is the only time you can assign the left and
     * right elements.
     * 
     * @param left The element to occupy the left slot of the pair
     * @param right The element to occupy the right slot of the pair
     */
    public Pair(L left, R right)
    {
        this.left = left;
        this.right = right;
    }

    public L getLeft()
    {
        return left;
    }

    public R getRight()
    {
        return right;
    }

    @Override
    public int hashCode()
    {
        return left.hashCode() ^ right.hashCode();
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null)
            return false;
        if (!(o instanceof Pair))
            return false;
        Pair pairo = (Pair) o;
        return this.left.equals(pairo.getLeft()) && this.right.equals(pairo.getRight());
    }

    @Override
    public String toString()
    {
        return "(" + this.left + "," + this.right + ")";
    }
}

/**
 * LevenshteinAutomaton is a fast and comprehensive Java library capable
 * of performing automaton and non-automaton based Levenshtein distance
 * determination and neighbor calculations.
 * 
 *  Copyright (C) 2012 Kevin Lawson <Klawson88@gmail.com>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License (version 3) as 
 * published by the Free Software Foundation. Licensing for proprietary 
 * software is available at a cost, inquire for more details. 
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.BoxOfC.LevenshteinAutomatonTest;

import com.BoxOfC.LevenshteinAutomaton.AugBitSet;
import com.BoxOfC.LevenshteinAutomaton.Position;
import com.BoxOfC.LevenshteinAutomaton.State;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;



/**
 *
 * @author Kevin
 */
public class StateTest 
{
    @Test
    public void StateConstructorTest()
    {
        Position[] posArray1 = new Position[]{new Position(0,0)};
        State s1 = new State(posArray1);
        
        for(int i = 0; i < 1000; i++)
        {
            int memberCount = (int)(Math.random() * 100);
            ArrayList<Position> memberPositionArrayList = new ArrayList<Position>(memberCount);
            memberPositionArrayList.add(new Position(0, 0));
            
            for(int j = 0; j < memberCount - 1; j++)
            {
                int randomE = (int)(Math.random() * i) + 1;
                int randomI = (int)(Math.random() * randomE);
                
                memberPositionArrayList.add(new Position(randomI, randomE));
            }

            Collections.sort(memberPositionArrayList);
            State invalidState = new State(memberPositionArrayList.toArray(new Position[0]));
            
            State validState = new State(Arrays.asList(new State[]{invalidState}));
            
            assert validState.getMemberPositions().length == 1;
        }
        
        for(int i = 0; i < 1000; i++)
        {
            int nonBaseMemberCount = (int)(Math.random() * 100);
            ArrayList<Position> memberPositionArrayList = new ArrayList<Position>(nonBaseMemberCount + 1);
            memberPositionArrayList.add(new Position(0, 0));
            
            int iBase = 0;
            
            for(int j = 0; j < nonBaseMemberCount; j++)
            {
                iBase += (int)(Math.random() * 100) + 1;
                memberPositionArrayList.add(new Position(iBase, 0)) ;
            }
            
            Collections.sort(memberPositionArrayList);
            
            State invalidState = new State(memberPositionArrayList.toArray(new Position[0]));
            State validState = new State(Arrays.asList(new State[]{invalidState}));
            
            assert validState.getMemberPositions().length == nonBaseMemberCount + 1;
        }
    }
    
    
    @DataProvider(name = "getRelevantSubwordCharacteristicVectorArrayDP")
    public Object[][] getRelevantSubwordCharacterisitcVectorArrayTestDataProvider()
    {
        int testCount = 1000;
        
        Object[][] argArrayContainerArray = new Object[testCount][];
        int randomStrLength = (int)(Math.random() * 20);
        
        for(int i = 0; i < testCount; i++)
        {
            int randomN = (int)(Math.random() * 100);
            int randomE = (int)(Math.random() * randomN);
            int randomI = (int)(Math.random() * randomStrLength);
            
            char randomDesiredBinaryDigit = (char) (48 + (int)(Math.random() * 2));

            StringBuilder strBuilder = new StringBuilder();
            for(int j = 0; j < randomStrLength; j++) strBuilder.append((char)(((int)(Math.random() * 2)) + 48));
            
            argArrayContainerArray[i] = new Object[]{randomN, new Position(randomI, randomE), strBuilder.toString(), randomDesiredBinaryDigit};
        }
        
        return argArrayContainerArray;
    }
    
    
    
    @Test(dataProvider = "getRelevantSubwordCharacteristicVectorArrayDP")
    public void getRelevantSubwordCharacteristicVectorArrayTest(int maxEditDistance, Position p, String automatonString, char letter)
    {
        State s = new State(new Position[]{p});
        AugBitSet relevantSubwordCV = s.getRelevantSubwordCharacteristicVector(maxEditDistance, automatonString, letter);
        
        String unprocessedAutomatonSubstring = automatonString.substring(p.getI());
        
        for(int i = 0; i < relevantSubwordCV.getRelevantBitSetSize(); i++)
        {
            int currentBit = (relevantSubwordCV.get(i) ? 1 : 0);
            assert (currentBit == (unprocessedAutomatonSubstring.charAt(i) - 48)) == (letter == '0' ? false : true);
        }
    }
    
    
    @Test
    public void canBeStateTestDataProvider()
    {
        Position[] posArray1 = new Position[]{new Position(0,0)};
        State s1 = new State(posArray1);
        
        for(int i = 0; i < 1000; i++)
        {
            int randomE = (int)(Math.random() * i) + 1;
            int randomI = (int)(Math.random() * randomE);
            
            assert !State.canBeState(s1, new Position(randomI, randomE));
        }
        
        for(int i = 0; i < 1000; i++)
        {
            int randomI = (int)(Math.random() * i) + 1;
            int randomE = (int)(Math.random() * randomI);
            
            assert State.canBeState(s1, new Position(randomI, randomE));
        }
    }
    

}

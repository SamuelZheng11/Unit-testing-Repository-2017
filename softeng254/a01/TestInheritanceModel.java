package softeng254.a01; // DO NOT CHANGE THIS OR YOU WILL GET ZERO

import static org.junit.Assert.*;
import org.junit.Test;
import softeng254.a01.InheritanceModel;
import org.junit.Before;

/**
 * SOFTENG 254 2017 Assignment 1 submission
 *
 * Author: (Samuel Zheng, szhe560)
 **/


public class TestInheritanceModel { // DO NOT CHANGE THE CLASS NAME OR YOU WILL GET ZERO
	
	private InheritanceModel _model;
    
    @Before
    public void setup() {
        _model = new InheritanceModel();
        _model.addModule("Master", "class");
    }	
	
	@Test
	public void testContainsModuel(){
        boolean result = _model.containsModule("Master");
        assertTrue(result);	
	}	
    

    @Test
    public void testModuleCollision(){
		try{
			_model.addModule("Master", "class");
			fail();		
		}catch(Exception e){
			//Do nothing
		}
		    
    }
}

package softeng254.a01; // DO NOT CHANGE THIS OR YOU WILL GET ZERO

import static org.junit.Assert.*;
import org.junit.Test;
import softeng254.a01.InheritanceModel;
import org.junit.Before;
import java.util.Set;
import java.util.HashSet;

/**
 * SOFTENG 254 2017 Assignment 1 submission
 *
 * Author: (Samuel Zheng, szhe560)
 **/


public class TestInheritanceModel { // DO NOT CHANGE THE CLASS NAME OR YOU WILL GET ZERO
	
	private InheritanceModel _model;
    
    @Before
    public void setup() {
		//Generating two modules to test if they have been added to the code (preformed in tests later on)
        _model = new InheritanceModel();
        _model.addModule("MasterClass", "class");
		_model.addModule("MasterInterface", "interface");
		_model.addModule("java.lang.Object" , "class");

		//adding a child modules to use in tests can catch errors later on
		_model.addModule("ChildClass1", "class");	
		_model.addModule("ChildClass2", "class");	
		_model.addModule("ChildClass3", "class");
		_model.addModule("ChildClass4", "class");
		_model.addModule("ChildInterface", "interface");
    }	
	
	@Test
	public void testContainsModule(){
		//Testing to see if The model contains Modules specified in the @before code (to identify faults)
        assertTrue(_model.containsModule("MasterClass"));	
		assertTrue(_model.containsModule("MasterInterface"));
	}	
    
	@Test
	public void testAddInvalidKind(){
		try{
			_model.addModule("DummyClass", "InvalidKind");
			fail();	
		}catch(Exception e){
			//do nothing
		}
	}

    @Test
    public void testDuplicateModule(){
		try{
			_model.addModule("MasterClass", "class");
			_model.addModule("MasterInterface", "interface");
			fail();		
		}catch(Exception e){
			//Do nothing
		}
		    
    }

	@Test
	public void testIfModelContainsNullOrEmpty(){
		assertFalse(_model.containsModule(null));	
		assertFalse(_model.containsModule(null));	
	}
	
	@Test
	public void testAddingEmptyStringModule(){
		try{
			_model.addModule("", "interface");
			fail();
		}catch(Exception e){
			//do nothing
		}	
	}

	@Test
	public void testAddingNullAndEmptyModules(){
		try{
			_model.addModule(null, "class");
			fail();	
		}catch(Exception e){
			//do nothing
		}
	}


	@Test
	public void testAddingJavaLangObjectAsChild(){
		try{
			_model.addParent("java.lang.Object", "MasterClass");
			fail();
		}catch(Exception e){
		//do nothing
		}
	
	}

	@Test(timeout=5000)
	public void testLangObjectInfinateInheritance(){
		_model.addParent("java.lang.Object", "java.lang.Object");
		try{
			_model.getDescendants("java.lang.Object");
		}catch(Exception e){
			//do nothing
		}
	}


	@Test
	public void testNonExistingParentModuleRelationship(){

		try{
			_model.addParent("ChildClass1", "InvalidClass");
			_model.addParent("ChildClass1", "InvalidInterface");
			fail();
		}catch (Exception e){
			//do nothing	
		}	
	}

	@Test
	public void testExistingParentChildRelationship(){
		_model.addParent("ChildClass1", "MasterClass");
		_model.addParent("ChildClass1", "MasterInterface");
		try{
			_model.addParent("ChildClass1", "InvalidClass");
			_model.addParent("ChildClass1", "InvalidInterface");
			fail();
		}catch (Exception e){
			//do nothing	
		}	
	}

	@Test
	public void testMultipleValidParentChildRelationShip(){
		try{
			//should pass test successfully, because this is a simple adding parent to subclass
			_model.addParent("ChildClass1", "MasterClass");
			_model.addParent("ChildClass1", "MasterInterface");
			_model.addParent("ChildInterface", "MasterInterface");
		}catch(Exception e){
			fail();		
		}
	}

	@Test
	public void parentOfSelf(){
		try{
			_model.addParent("MasterClass", "MasterClass");
			fail();
		}catch(Exception e){
			//do nothing
		}	
	}

	@Test
	public void testChildHasMultipleParents(){
	_model.addParent("ChildClass2", "MasterClass");
	try{
		_model.addParent("ChildClass2" , "MasterInterfaceS");
		fail();
	}catch(Exception e){
		//do nothing	
	}	
	}

	@Test
	public void testClassIsParentOfInterface(){
		try{
			//should fail as an interface should not be able to inherit from a class
			_model.addParent("ChildInterface", "MasterClass");
			fail();
		}catch(Exception e){
			//do nothing
		}
	}

	@Test
	public void testCircularParentChildRelationship(){
		try{
			_model.addParent("ChildClass1", "MasterClass");
			_model.addParent("MasterClass", "ChildClass1");
			fail();
		}catch(Exception e){
			//do nothing
		}	
	}


	@Test
	public void testGetCorrectSetOfParents(){
		Set<String> actualParents = new HashSet<String>();
		Set<String> expectedParents = new HashSet<String>() {{add("MasterClass"); add("MasterInterface");}};
		try{
			_model.addParent("ChildClass1", "MasterClass");
			_model.addParent("ChildClass1", "MasterInterface");
			actualParents = _model.getParents("ChildClass1");
		}catch(Exception e){
			fail();
		}
		assertEquals(actualParents, expectedParents);
	}

	@Test
	public void testMultipleParentInterface(){
		_model.addParent("ChildClass1", "MasterInterface");
		_model.addParent("ChildClass1", "ChildInterface");
	}

	@Test
	public void testGetCorrectSetOfChildren(){
		Set<String> actualChildren = new HashSet<String>();
		Set<String> expectedChildren = new HashSet<String>() {{add("ChildClass1"); add("ChildClass2"); add("ChildClass3");}};
		try{
			_model.addParent("ChildClass1", "MasterClass");
			_model.addParent("ChildClass2", "MasterClass");
			_model.addParent("ChildClass3", "MasterClass");
			actualChildren = _model.getChildren("MasterClass");
		}catch(Exception e){
			fail();
		}
		assertEquals(actualChildren, expectedChildren);
	}


	@Test
	public void testIsAncestorOfSelf(){
		assertTrue(_model.isAncestor("MasterClass", "MasterClass"));
	}

	@Test
	public void testPartialAncestorRelationship(){
		boolean result = false;
		try{
			_model.addParent("ChildClass1", "MasterClass");
			_model.addParent("ChildClass2", "ChildClass1");
			_model.addParent("ChildClass3", "ChildClass2");
			_model.addParent("ChildClass4", "ChildClass3");
			result = _model.isAncestor("ChildClass1", "ChildClass3");
		}catch(Exception e){
			fail();
		}	

		assertTrue(result);	
	}
	
	@Test
	public void testGrandAncestorRelationship(){
		boolean result = false;
		try{
			_model.addParent("ChildClass1", "MasterClass");
			_model.addParent("ChildClass2", "ChildClass1");
			result = _model.isAncestor("MasterClass", "ChildClass2");
		}catch(Exception e){
			fail();
		}	

		assertTrue(result);	
	}

	@Test
	public void testIsDescendantOfSelf(){
		assertTrue(_model.isDescendant("MasterClass", "MasterClass"));
	}

	@Test
	public void testPartialDescendantRelationship(){
		boolean result = false;
		try{
			_model.addParent("ChildClass1", "MasterClass");
			_model.addParent("ChildClass2", "ChildClass1");
			_model.addParent("ChildClass3", "ChildClass2");
			_model.addParent("ChildClass4", "ChildClass3");
			result = _model.isDescendant("ChildClass3", "ChildClass1");
		}catch(Exception e){
			fail();
		}	

		assertTrue(result);	
	}

	@Test
	public void testGrandDescendantRelationship(){
		boolean result = false;
		try{
			_model.addParent("ChildClass1", "MasterClass");
			_model.addParent("ChildClass2", "ChildClass1");
			result = _model.isDescendant("ChildClass2", "MasterClass");
		}catch(Exception e){
			fail();
		}	

		assertTrue(result);
	}
	
	@Test
	public void testNonExistingAncestorOrDescendantRelationship(){
		boolean result1 = false;
		boolean result2 = false;
		try{
			result1 = _model.isAncestor("MasterClass", "DoesNotExist");
			result2 = _model.isDescendant("DoesNotExist", "MasterClass");
			fail();
		}catch(Exception e){
			//do nothing
		}
	}

	@Test
	public void testCircularAncestorDescendantRelationship(){
		try{
			_model.addParent("ChildClass1", "MasterClass");
			_model.addParent("ChildClass2", "ChildClass1");
			_model.addParent("MasterClass", "ChildClass2");
			fail();
		}catch(Exception e){
			//do nothing
		}
	}

	@Test
	public void testIsCorrectAncestorList(){
		_model.addParent("ChildClass1", "MasterClass");
		_model.addParent("ChildClass3", "ChildClass1");
		_model.addParent("ChildClass2", "MasterClass");
		Set<String> ActualAncestorList = new HashSet<String>();
		Set<String> expectedAncestorList = new HashSet<String>() {{add("ChildClass3"); add("ChildClass1"); add("MasterClass");}};
		ActualAncestorList = _model.getAncestors("ChildClass3");
		
		assertEquals(ActualAncestorList, expectedAncestorList);
	}

	@Test
	public void testInternalNodeAncestorList(){
		_model.addParent("ChildClass1", "MasterClass");
		_model.addParent("ChildClass3", "ChildClass1");
		_model.addParent("ChildClass2", "MasterClass");
		Set<String> ActualAncestorList = new HashSet<String>();
		Set<String> expectedAncestorList = new HashSet<String>() {{add("ChildClass1"); add("MasterClass");}};
		ActualAncestorList = _model.getAncestors("ChildClass1");
		
		assertEquals(ActualAncestorList, expectedAncestorList);
	}

	@Test
	public void testInternalNodeDecendantsList(){
		_model.addParent("ChildClass1", "MasterClass");
		_model.addParent("ChildClass3", "ChildClass1");
		_model.addParent("ChildClass2", "MasterClass");
		Set<String> ActualDescendantsList = new HashSet<String>();
		Set<String> expectedDescendantsList = new HashSet<String>() {{add("ChildClass1"); add("ChildClass3");}};
		ActualDescendantsList = _model.getDescendants("ChildClass1");
		
		assertEquals(ActualDescendantsList, expectedDescendantsList);
	}


	@Test
	public void testModuleExistsAfterAddParent(){
		_model.addParent("ChildClass1","MasterClass");
		assertTrue(_model.containsModule("ChildClass1"));

	}

	@Test
	public void testAddParentToInternalModule(){
		_model.addParent("ChildClass1", "MasterClass");
		_model.addParent("ChildClass2", "ChildClass1");
		try{
			_model.addParent("ChildClass1", "MasterInterface");
		}catch(Exception e){
			fail();		
		}

	}

	@Test
	public void testModuleWithSameNameDifferentKind(){
		try{
			_model.addModule("MasterClass", "interface");
			fail();
		}catch(Exception e){
			//do nothing		
		}
	}

	@Test
	public void testObjectAsParentOfInterface(){
		try{
			_model.addParent("MasterInterface", "java.lang.Object");
			fail();
		}catch(Exception e){
			//do nothing		
		}
	}
}

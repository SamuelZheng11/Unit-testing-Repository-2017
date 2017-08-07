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
			//testing to see if adding a class of an invalid kind will cause an exception to be thrown
			_model.addModule("DummyClass", "InvalidKind");
			fail();	
		}catch(Exception e){
			//do nothing
		}
	}

    @Test
    public void testAddDuplicateClass(){
		try{
			//adding module of kind "class" which has already been added in the @before method
			_model.addModule("MasterClass", "class");
			fail();		
		}catch(Exception e){
			//Do nothing
		}  
    }

    @Test
    public void testAddDuplicateInterface(){
		try{
			//adding module of kind "interface" both of which have already been added in the @before method
			_model.addModule("MasterInterface", "interface");
			fail();		
		}catch(Exception e){
			//Do nothing
		}  
    }

	@Test
	public void testIfModelContainsNullOrEmpty(){
		//testing if _model is null, this should never be true unless specified specifically
		assertFalse(_model.containsModule(null));
	}

	@Test
	public void testAddingEmptyStringModule(){
		try{
			//attempting to add a interface that has no name, this should not be allowed
			_model.addModule("", "interface");
			fail();
		}catch(Exception e){
			//do nothing
		}	
	}

	@Test
	public void testAddingNullAndEmptyModules(){
		try{
			//attempting to add a class that is null, this should not be allowed
			_model.addModule(null, "class");
			fail();	
		}catch(Exception e){
			//do nothing
		}
	}


	@Test
	public void testAddingJavaLangObjectAsChild(){
		try{
			//attempting to add java.lang.Object as a child to another class, this should never be allowed
			_model.addParent("java.lang.Object", "MasterClass");
			fail();
		}catch(Exception e){
		//do nothing
		}
	
	}

	@Test(timeout=5000)
	public void testLangObjectInfinateDescendants(){
		//setting up a possible infinate loop senario, with java.lang.Object being the parent of itself
		_model.addParent("java.lang.Object", "java.lang.Object");
		try{
		//attempting to get the descendants of java.lang.Objects' when it is the parent of itself
			_model.getDescendants("java.lang.Object");
		}catch(Exception e){
			//do nothing
		}
	}

	@Test(timeout=5000)
	public void testLangObjectInfinateAncestors(){
		//setting up a possible infinate loop senario, with java.lang.Object being the parent of itself
		_model.addParent("java.lang.Object", "java.lang.Object");
		//attempting to get the ancestors of java.lang.Objects' when it is the parent of itself
		_model.getAncestors("java.lang.Object");
	}


	@Test
	public void testNonExistingParentModuleRelationship(){
		try{
			//attempting to add modules that do not exist to a class in the model, this should not be allowed
			_model.addParent("ChildClass1", "InvalidClass");
			_model.addParent("ChildClass1", "InvalidInterface");
			fail();
		}catch (Exception e){
			//do nothing	
		}	
	}

	@Test
	public void testExistingParentChildRelationship(){
		//initialising a parent-child relationship for the try/catch statement
		_model.addParent("ChildClass1", "MasterClass");
		_model.addParent("ChildClass1", "MasterInterface");
		try{
			//attempting to define a relationship that already exists within the model
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
			//preforming a basic relationship definition, non of which should fail
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
			//attempting to add a module as the parent of itself, which unless is java.lang.Object should not be allowed
			_model.addParent("MasterClass", "MasterClass");
			fail();
		}catch(Exception e){
			//do nothing
		}	
	}

	@Test
	public void testChildHasMultipleParents(){
	//defining a relationship for the try/catch statement
	_model.addParent("ChildClass2", "MasterClass");
	try{
		//attempting to add a module as a parent to a child that already has a parent, this should not be allowed
		_model.addParent("ChildClass2" , "ChildClass1");
		fail();
	}catch(Exception e){
		//do nothing	
	}	
	}

	@Test
	public void testClassIsParentOfInterface(){
		try{
			//attempting to add a module of kind "class" as the parent of a module of kind "interface"
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
			//attempting to define a circular relationship where, the child is the parent of the parent
			//and the parent is the child of the child. This should never be allowed
			_model.addParent("ChildClass1", "MasterClass");
			_model.addParent("MasterClass", "ChildClass1");
			fail();
		}catch(Exception e){
			//do nothing
		}	
	}


	@Test
	public void testGetCorrectSetOfParents(){
		//setting up HashSets to use for asserts later on
		Set<String> actualParents = new HashSet<String>();
		Set<String> expectedParents = new HashSet<String>() {{add("MasterClass"); add("MasterInterface");}};
		try{
			//defining relationships
			_model.addParent("ChildClass1", "MasterClass");
			_model.addParent("ChildClass1", "MasterInterface");
			//saving results to compare with expected results is asserts later
			actualParents = _model.getParents("ChildClass1");
		}catch(Exception e){
			fail();
		}

		//asserting that the expected results and the actual results are the same for a successful test
		assertEquals(actualParents, expectedParents);
	}

	@Test
	public void testMultipleParentInterface(){
		//testing that a module can have both an interface and a class as a parent
		try{
		_model.addParent("ChildClass1", "MasterInterface");
		_model.addParent("ChildClass1", "ChildInterface");
		}catch(Exception e){
			fail();		
		}
	}

	@Test
	public void testGetCorrectSetOfChildren(){
		//setting up HashSets to use for asserts later on
		Set<String> actualChildren = new HashSet<String>();
		Set<String> expectedChildren = new HashSet<String>() {{add("ChildClass1"); add("ChildClass2"); add("ChildClass3");}};
		try{
			//defining relationships
			_model.addParent("ChildClass1", "MasterClass");
			_model.addParent("ChildClass2", "MasterClass");
			_model.addParent("ChildClass3", "MasterClass");
			//saving results to compare with expected results is asserts later
			actualChildren = _model.getChildren("MasterClass");
		}catch(Exception e){
			fail();
		}
		//asserting that the expected results and the actual results are the same for a successful test
		assertEquals(actualChildren, expectedChildren);
	}


	@Test
	public void testIsAncestorOfSelf(){
		//testing that by default a class is the ancestor of itself
		assertTrue(_model.isAncestor("MasterClass", "MasterClass"));
	}

	@Test
	public void testPartialAncestorRelationship(){
		//definting a failing variable in the assert later
		boolean result = false;
		try{
			//setting up relationship definitions
			_model.addParent("ChildClass1", "MasterClass");
			_model.addParent("ChildClass2", "ChildClass1");
			_model.addParent("ChildClass3", "ChildClass2");
			_model.addParent("ChildClass4", "ChildClass3");
			//testing if internal module inheritance is valid
			result = _model.isAncestor("ChildClass1", "ChildClass3");
		}catch(Exception e){
			fail();
		}	
		//asserting that the intial result has been changed
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
			//testing that an module with the same name as an existing module in the model can be added
			_model.addModule("MasterClass", "interface");
			fail();
		}catch(Exception e){
			//do nothing		
		}
	}

	@Test
	public void testJavaLangObjectAsParentOfInterface(){
		try{
			_model.addParent("MasterInterface", "java.lang.Object");
			fail();
		}catch(Exception e){
			//do nothing		
		}
	}
}

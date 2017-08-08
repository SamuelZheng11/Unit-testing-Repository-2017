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
    
	//the reason why this is here is to reduce the need to repeditivly add modules to the model when creating tests
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
	
	//testing if after modules are in the model after they were added in the @Before method 
	//this is important because we must know if the modules are present in the model after adding them
	@Test
	public void testContainsModule(){
		//Testing to see if The model contains Modules specified in the @before code (to identify faults)
        assertTrue(_model.containsModule("MasterClass"));	
		assertTrue(_model.containsModule("MasterInterface"));
	}	
    
	//Testing to see if adding adding an invalid kind module will cause the method to throw and exception
	//this is important because the kind defines what the module 'type' is, we should never have any invalid
	//kinds for a module
	@Test
	public void testAddInvalidKind(){
		try{
			//testing to see if adding a class of an invalid kind will cause an exception to be thrown
			_model.addModule("DummyClass", "InvalidKind");
			fail();	
		}catch(RuntimeException e){
			//do nothing
		}
	}

	//Testing to see if the model will accept a module being added dispite it already existing within the model
	//this is important because it tests for duplicates models existing inside the model
    @Test
    public void testAddDuplicateClass(){
		try{
			//adding module of kind "class" which has already been added in the @before method
			_model.addModule("MasterClass", "class");
			fail();		
		}catch(RuntimeException e){
			//Do nothing
		}  
    }

	//Testing to see if the model will accept a module being added dispite it already existing within the model
	//this is important because it tests for duplicates models existing inside the model
    @Test
    public void testAddDuplicateInterface(){
		try{
			//adding module of kind "interface" both of which have already been added in the @before method
			_model.addModule("MasterInterface", "interface");
			fail();		
		}catch(RuntimeException e){
			//Do nothing
		}  
    }

	//testing to see if the model will by default is null (should never happen as when we try to add
	//modules to it, problems will become immedialty apparent)
	@Test
	public void testIfModelContainsNullOrEmpty(){
		//testing if _model is null, this should never be true unless specified specifically
		assertFalse(_model.containsModule(null));
	}

	//testing to see if the module will accept an interface that is an empty string
	//this is important because we should not have nameless modules inside our model
	@Test
	public void testAddingEmptyStringModule(){
		try{
			//attempting to add a interface that has no name, this should not be allowed
			_model.addModule("", "interface");
			fail();
		}catch(RuntimeException e){
			//do nothing
		}	
	}

	//testing to see if the module will accept an interface that is null
	//this is important because we should not have null modules inside our model
	@Test
	public void testAddingNullModule(){
		try{
			//attempting to add a interface that has no name, this should not be allowed
			_model.addModule(null, "interface");
			fail();
		}catch(RuntimeException e){
			//do nothing
		}	
	}

	//testing to see if the module will accept an class that is an empty string
	//this is important because we should not have null modules inside our model
	@Test
	public void testAddingEmptyStringClass(){
		try{
			//attempting to add a class that is null, this should not be allowed
			_model.addModule("", "class");
			fail();	
		}catch(RuntimeException e){
			//do nothing
		}
	}

	//testing to see if the module will accept an class that is null
	//this is important because we should not have null modules inside our model
	@Test
	public void testAddingNullClass(){
		try{
			//attempting to add a class that is null, this should not be allowed
			_model.addModule(null, "class");
			fail();	
		}catch(RuntimeException e){
			//do nothing
		}
	}

	//testing to see if java.lang.Object can be added as a child to another module
	//it is important to test this because java.lang.Object should never be a child
	//as it is the grand ancestor of all objects
	@Test
	public void testAddingJavaLangObjectAsChild(){
		try{
			//attempting to add java.lang.Object as a child to another class, this should never be allowed
			_model.addParent("java.lang.Object", "MasterClass");
			fail();
		}catch(ModelException e){
		//do nothing
		}
	
	}
	
	//testing to see if an infinate loop occurs when executing isDescendants method
	@Test(timeout=5000)
	public void testLangObjectInfinateDescendants(){
		//setting up a possible infinate loop senario, with java.lang.Object being the parent of itself
		_model.addParent("java.lang.Object", "java.lang.Object");
		//attempting to get the descendants of java.lang.Objects' when it is the parent of itself
		//we expect this test to fail if it enters an infinate loop (if it doesn't fail it means that
		//it did not enter an infinte loop)
		_model.getDescendants("java.lang.Object");
	}

	@Test(timeout=5000)
	public void testLangObjectInfinateAncestors(){
		//setting up a possible infinate loop senario, with java.lang.Object being the parent of itself
		_model.addParent("java.lang.Object", "java.lang.Object");
		//attempting to get the ancestors of java.lang.Objects' when it is the parent of itself
		//we expect this test to fail if it enters an infinate loop (if it doesn't fail it means that
		//it did not enter an infinte loop)
		_model.getAncestors("java.lang.Object");
	}


	@Test
	public void testNonExistingParentModuleRelationship(){
		try{
			//attempting to add modules that do not exist to a class in the model, this should not be allowed
			_model.addParent("ChildClass1", "InvalidClass");
			_model.addParent("ChildClass1", "InvalidInterface");
			fail();
		}catch (ModelException e){
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
		}catch (ModelException e){
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
		}catch(ModelException e){
			fail();		
		}
	}

	@Test
	public void parentOfSelf(){
		try{
			//attempting to add a module as the parent of itself, which unless is java.lang.Object should not be allowed
			_model.addParent("MasterClass", "MasterClass");
			fail();
		}catch(ModelException e){
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
	}catch(ModelException e){
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
		}catch(ModelException e){
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
		}catch(ModelException e){
			//do nothing
		}	
	}


	@Test
	public void testGetCorrectSetOfParents(){
		//setting up HashSets to use for asserts later on
		Set<String> actualParents = new HashSet<String>();
		Set<String> expectedParents = new HashSet<String>() {{add("MasterClass"); add("MasterInterface");}};

		//defining relationships
		_model.addParent("ChildClass1", "MasterClass");
		_model.addParent("ChildClass1", "MasterInterface");
		//saving results to compare with expected results is asserts later
		actualParents = _model.getParents("ChildClass1");

		//asserting that the expected results and the actual results are the same for a successful test
		assertEquals(actualParents, expectedParents);
	}

	@Test
	public void testMultipleParentInterface(){
		//testing that a module can have both an interface and a class as a parent
		try{
		_model.addParent("ChildClass1", "MasterInterface");
		_model.addParent("ChildClass1", "ChildInterface");
		}catch(ModelException e){
			fail();		
		}
	}

	@Test
	public void testGetCorrectSetOfChildren(){
		//setting up HashSets to use for asserts later on
		Set<String> actualChildren = new HashSet<String>();
		Set<String> expectedChildren = new HashSet<String>() {{add("ChildClass1"); add("ChildClass2"); add("ChildClass3");}};
		//defining relationships
		_model.addParent("ChildClass1", "MasterClass");
		_model.addParent("ChildClass2", "MasterClass");
		_model.addParent("ChildClass3", "MasterClass");
		//saving results to compare with expected results is asserts later
		actualChildren = _model.getChildren("MasterClass");
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
		//setting up relationship definitions
		_model.addParent("ChildClass1", "MasterClass");
		_model.addParent("ChildClass2", "ChildClass1");
		_model.addParent("ChildClass3", "ChildClass2");
		_model.addParent("ChildClass4", "ChildClass3");
		//testing if the methord can correctly identify a internal ancestor-child relationship
		result = _model.isAncestor("ChildClass1", "ChildClass3");
		//asserting that the intial result has been changed
		assertTrue(result);	
	}
	
	@Test
	public void testGrandAncestorRelationship(){
		//definting a failing variable in the assert later
		boolean result = false;
		//setting up relationship definitions
		_model.addParent("ChildClass1", "MasterClass");
		_model.addParent("ChildClass2", "ChildClass1");
		//testing if the method can correctly identify a grand ancestor-child relationship
		result = _model.isAncestor("MasterClass", "ChildClass2");	
		//asserting that the intial result has been changed
		assertTrue(result);	
	}

	@Test
	public void testIsDescendantOfSelf(){
		//testing if the method can correctly identify that a class is the descendant of itself
		assertTrue(_model.isDescendant("MasterClass", "MasterClass"));
	}

	@Test
	public void testPartialDescendantRelationship(){
		//definting a failing variable in the assert later
		boolean result = false;
		//setting up relationship definitions
		_model.addParent("ChildClass1", "MasterClass");
		_model.addParent("ChildClass2", "ChildClass1");
		_model.addParent("ChildClass3", "ChildClass2");
		_model.addParent("ChildClass4", "ChildClass3");
		//testing if the method can correctly identify an internal parent-descendant relationship
		result = _model.isDescendant("ChildClass3", "ChildClass1");
		//asserting that the intial result has been changed
		assertTrue(result);	
	}

	@Test
	public void testGrandDescendantRelationship(){
		//definting a failing variable in the assert later
		boolean result = false;
			//setting up relationship definitions
			_model.addParent("ChildClass1", "MasterClass");
			_model.addParent("ChildClass2", "ChildClass1");
			//testing if the method can correctly identify an grand parent-descendant relationship
			result = _model.isDescendant("ChildClass2", "MasterClass");
		//asserting that the intial result has been changed
		assertTrue(result);
	}
	
	@Test
	public void testNonExistingAncestorOrDescendantRelationship(){
		//setting up variables to assert later
		boolean result1 = false;
		boolean result2 = false;
		try{
			//testing to see if the code can correctly identify that models do not exist when trying to 
			//find ancestors or descendants
			result1 = _model.isAncestor("MasterClass", "DoesNotExist");
			result2 = _model.isDescendant("DoesNotExist", "MasterClass");
			fail();
		}catch(ModelException e){
			//do nothing
		}
	}

	@Test
	public void testCircularAncestorDescendantRelationship(){
		try{
			//attempting to define a circular grand ancestor-child relationship
			_model.addParent("ChildClass1", "MasterClass");
			_model.addParent("ChildClass2", "ChildClass1");
			_model.addParent("MasterClass", "ChildClass2");
			//if the test completes without throwing an exception, it would instantly fail
			fail();
		}catch(ModelException e){
			//do nothing
		}
	}

	@Test
	public void testIsCorrectAncestorList(){
		//setting up a relationship
		_model.addParent("ChildClass1", "MasterClass");
		_model.addParent("ChildClass3", "ChildClass1");
		_model.addParent("ChildClass2", "MasterClass");
		//initialising variables to store the expected and actual outputs for the method
		Set<String> ActualAncestorList = new HashSet<String>();
		Set<String> expectedAncestorList = new HashSet<String>() {{add("ChildClass3"); add("ChildClass1"); add("MasterClass");}};
		ActualAncestorList = _model.getAncestors("ChildClass3");
		//asserting that the expected and actual outputs are the same
		assertEquals(ActualAncestorList, expectedAncestorList);
	}

	@Test
	public void testInternalNodeAncestorList(){
		//defining relationships
		_model.addParent("ChildClass1", "MasterClass");
		_model.addParent("ChildClass3", "ChildClass1");
		_model.addParent("ChildClass2", "MasterClass");
		//initialising variables to store the expected and actual outputs for the method
		Set<String> ActualAncestorList = new HashSet<String>();
		Set<String> expectedAncestorList = new HashSet<String>() {{add("ChildClass1"); add("MasterClass");}};
		ActualAncestorList = _model.getAncestors("ChildClass1");
		//asserting that the expected and actual outputs are the same
		assertEquals(ActualAncestorList, expectedAncestorList);
	}

	@Test
	public void testInternalNodeDecendantsList(){
		//defining relationships
		_model.addParent("ChildClass1", "MasterClass");
		_model.addParent("ChildClass3", "ChildClass1");
		_model.addParent("ChildClass2", "MasterClass");
		//initialising variables to store the expected and actual outputs for the method
		Set<String> ActualDescendantsList = new HashSet<String>();
		Set<String> expectedDescendantsList = new HashSet<String>() {{add("ChildClass1"); add("ChildClass3");}};
		ActualDescendantsList = _model.getDescendants("ChildClass1");
		//asserting that the expected and actual outputs are the same
		assertEquals(ActualDescendantsList, expectedDescendantsList);
	}


	@Test
	public void testModuleExistsAfterAddParent(){
		//expecting that the child class is not deleted from model after a relationship is defined
		_model.addParent("ChildClass1","MasterClass");
		assertTrue(_model.containsModule("ChildClass1"));

	}

	@Test
	public void testAddParentToInternalModule(){
		//defining relationship
		_model.addParent("ChildClass1", "MasterClass");
		_model.addParent("ChildClass2", "ChildClass1");
		//testing if an interface can be added as a parent to an internal module
		try{
			_model.addParent("ChildClass1", "MasterInterface");
		}catch(ModelException e){
			fail();		
		}

	}

		@Test
	public void testAddChildToInternalModule(){
		//defining relationship
		_model.addParent("ChildClass1", "MasterClass");
		_model.addParent("ChildClass2", "ChildClass1");
		_model.addParent("ChildClass3", "ChildClass2");
		//testing if a class can be added to an internal module
		try{
			_model.addParent("ChildClass4", "ChildClass1");
		}catch(ModelException e){
			fail();		
		}

	}

	//this test tries to identify two seperate/disjoint parent/child relationships 
	//inside of the model this is important because it tests if the model has the 
	//ability to contain two sets of inheritance relationships inside of the model 
	//as opposed to just storing all inheritance in one massive set
	@Test
	public void testGetChildrenOfTwoDisjointParentChildRelationships(){
			_model.addParent("ChildClass1", "MasterClass");
			_model.addParent("ChildClass2", "MasterClass");
			_model.addParent("ChildClass3", "MasterInterface");
			_model.addParent("ChildClass4", "MasterInterface");
			assertEquals(_model.getChildren("MasterClass"), new HashSet<String>() {{add("ChildClass1"); add("ChildClass2");}});
			assertEquals(_model.getChildren("MasterInterface"), new HashSet<String>() {{add("ChildClass3"); add("ChildClass4");}});
	}

	//this test tries to identify two seperate/disjoint parent/child relationships 
	//inside of the model this is important because it tests if the model has the 
	//ability to contain two sets of inheritance relationships inside of the model 
	//as opposed to just storing all inheritance in one massive set
	@Test
	public void testGetParentsOfTwoDisjointParentChildRelationships(){
			_model.addParent("ChildClass1", "MasterClass");
			_model.addParent("ChildClass1", "MasterInterface");
			_model.addParent("ChildClass3", "ChildInterface");
			_model.addParent("ChildClass3", "ChildClass2");
			assertEquals(_model.getParents("ChildClass1"), new HashSet<String>() {{add("MasterClass"); add("MasterInterface");}});
			assertEquals(_model.getParents("ChildClass3"), new HashSet<String>() {{add("ChildInterface"); add("ChildClass2");}});
	}

	//this test tries to identify an ancestor/descendant relationship of a model with 2 disjoint sets 
	//inside of the model this is important because it tests if the model has the 
	//ability to contain two sets of inheritance relationships inside of the model 
	//as opposed to just storing all inheritance in one massive set
	@Test
	public void testGetAncestorOfTwoBranchedDisjointParentChildRelationships(){
			_model.addModule("ChildClass5", "class");
			_model.addParent("ChildClass1", "MasterClass");
			_model.addParent("ChildClass2", "MasterClass");
			_model.addParent("ChildClass3", "ChildClass2");
			_model.addParent("ChildClass5", "ChildClass4");
			assertEquals(_model.getAncestors("ChildClass3"), new HashSet<String>() {{add("MasterClass"); add("ChildClass2"); add("ChildClass3");}});
	}

	//this test tries to identify an ancestor/descendant relationship of a model with 2 disjoint sets 
	//inside of the model this is important because it tests if the model has the 
	//ability to contain two sets of inheritance relationships inside of the model 
	//as opposed to just storing all inheritance in one massive set
	@Test
	public void testGetDescendantOfTwoDisjointParentChildRelationships(){
			_model.addModule("ChildClass5", "class");
			_model.addParent("ChildClass1", "MasterClass");
			_model.addParent("ChildClass2", "ChildClass1");
			_model.addParent("ChildClass5", "ChildClass4");
			assertEquals(_model.getDescendants("MasterClass"), new HashSet<String>() {{add("MasterClass"); add("ChildClass1"); add("ChildClass2");}});
	}

}

import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 * 
 * @author Put your name here
 * 
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     * 
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     * 
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     * 
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     * 
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * 
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     * 
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * Sample test cases.
     */

    @Test
    public final void testConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        m.add("green");
        assertEquals(mExpected, m);
    }

    // TODO - add test cases for add, changeToExtractionMode, removeFirst,
    // isInInsertionMode, order, and size
    
    @Test
    public final void testAddNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "Red", "Green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "Red", "Green", "Blue");
        m.add("Blue");
        assertEquals(mExpected, m);
    }
    
    @Test
    public final void testChangeToExtractionModeNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "Red", "Green", "Blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "Red", "Green", "Blue");
        
        m.changeToExtractionMode();
        mExpected.changeToExtractionMode();
        
        assertEquals(mExpected, m);
    }
    
    @Test
    public final void testChangeToExtractionModeEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        
        m.changeToExtractionMode();
        mExpected.changeToExtractionMode();
        
        assertEquals(mExpected, m);
    }
    
    @Test
    public final void testRemoveFirstNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "Red", "Green", "Blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true, "Red", "Green", "Blue");
        
        m.changeToExtractionMode();
        mExpected.changeToExtractionMode();
        
        String first = m.removeFirst();
        String expectedFirst = mExpected.removeFirst();
        
        assertEquals(expectedFirst, first);
        assertEquals(mExpected, m);
    }
    
    @Test
    public final void testIsInInsertionModeEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        
        boolean isInInsertionMode = m.isInInsertionMode();
        
        assertEquals(isInInsertionMode, true);
        assertEquals(mExpected, m);
    }
    
    @Test
    public final void testIsInInsertionModeNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "Red", "Green", "Blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true, "Red", "Green", "Blue");
        
        m.changeToExtractionMode();
        mExpected.changeToExtractionMode();
        
        boolean isInInsertionMode = m.isInInsertionMode();
        
        assertEquals(isInInsertionMode, false);
        assertEquals(mExpected, m);
    }
    
    @Test
    public final void testOrderEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        
      
        
        
        
        assertEquals(mExpected.order(), m.order());
        assertEquals(mExpected, m);
    }
    
    @Test
    public final void testOrderNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "Red", "Green", "Blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true, "Red", "Green", "Blue");
        
      
        
        
        
        assertEquals(m.order(), mExpected.order());
        assertEquals(mExpected, m);
    }
    
    @Test
    public final void testOrderNonEmptyExtractionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "Red", "Green", "Blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true, "Red", "Green", "Blue");
        
      
        m.changeToExtractionMode();
        mExpected.changeToExtractionMode();
        
        
        assertEquals(m.order(), mExpected.order());
        assertEquals(mExpected, m);
    }
    
    @Test
    public final void testSizeNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "Red", "Green", "Blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true, "Red", "Green", "Blue");
        
      
       int size = m.size();
        
        
        assertEquals(3, size);
        assertEquals(mExpected, m);
    }
    
    @Test
    public final void testSizeEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        
      
       int size = m.size();
        
        
        assertEquals(0, size);
        assertEquals(mExpected, m);
    }
}

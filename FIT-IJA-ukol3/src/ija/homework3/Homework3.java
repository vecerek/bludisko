
package ija.homework3;

import java.lang.reflect.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import ija.homework3.tape.*;
import ija.homework3.objects.*;

/**
 * Homework2: uloha c. 2 z IJA
 * Trida testujici implementaci zadani 2. ukolu.
 */
public class Homework2 {

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void testTapeObject01() {
        TapeObject to1 = TapeObject.create("w");
        TapeObject to2 = TapeObject.create("g");
        TapeObject to3 = TapeObject.create("p");

        assertEquals("Object to1 je instanci tridy Wall.", Wall.class, to1.getClass());
        assertEquals("Object to2 je instanci tridy Gate.", Gate.class, to2.getClass());
        assertNull("Object to3 nebyl vytvoren.", to3);

        assertFalse("Zed nelze otevrit", to1.canBeOpen());
        assertTrue("Zavrenou branu lze otevrit", to2.canBeOpen());
        to2.open();
        assertFalse("Otevrenou branu nelze otevrit", to2.canBeOpen());
    }

    @Test
    public void testTape01() {
        Tape t1 = new Tape(5, 0, "p p w p g");

        TapeField f = t1.fieldAt(0);
        assertTrue("Pozice 0: lze obsadit.", f.canSeize());
        assertEquals("Posun na pozici 1.", t1.fieldAt(1), f = f.rightField());
        assertTrue("Pozice 1: lze obsadit.", f.canSeize());
        assertEquals("Posun na pozici 2.", t1.fieldAt(2), f = f.rightField());
        assertFalse("Pozice 2: nelze obsadit.", f.canSeize());
        assertEquals("Posun na pozici 3.", t1.fieldAt(3), f = f.rightField());
        assertTrue("Pozice 3: lze obsadit.", f.canSeize());
        assertEquals("Posun na pozici 4.", t1.fieldAt(4), f = f.rightField());
        assertFalse("Pozice 4: nelze obsadit.", f.canSeize());
        assertNull("Posun na pozici 5 - neexistuje.", f.rightField());

        assertTrue("Pozice 4: zavrenou branu lze otevrit.", f.canBeOpen());
    }

    @Test
    public void testTape02() {
        Tape t1 = new Tape(5, 2, "w w p g w");
        TapeHead h1, h2;

        h1 = t1.createHead(1);
        assertNotNull("Hlava 1 vytvorena.", h1);
        h2 = t1.createHead(2);
        assertNull("Hlavu 2 nelze vytvorit.", h2);

        assertEquals("Hlava 1 je na pozici 2.", t1.fieldAt(2), h1.seizedField());
    }

    @Test
    public void testTape03() {
        Tape t1 = new Tape(9, 2, "w p g w p w p w g");
        TapeHead h1, h2;

        h1 = t1.createHead(1);
        assertNotNull("Hlava 1 vytvorena.", h1);
        h2 = t1.createHead(2);
        assertNotNull("Hlava 2 vytvorena.", h2);

        assertEquals("Hlava 1 je na pozici 1.", t1.fieldAt(1), h1.seizedField());
        assertEquals("Hlava 2 je na pozici 4.", t1.fieldAt(4), h2.seizedField());

        assertTrue("Hlava 1 se posune doprava", h1.moveRight());
        assertEquals("Hlava 1 je na pozici 6.", t1.fieldAt(6), h1.seizedField());

        assertFalse("Hlavu 2 nelze posunout doprava", h2.moveRight());
    }

    @Test
    public void testTape04() {
        Tape t1 = new Tape(9, 2, "w p g w p w p w g");
        TapeHead h1, h2;

        h1 = t1.createHead(1);
        h1.addKeys(2);
        assertNotNull("Hlava 1 vytvorena.", h1);
        h2 = t1.createHead(2);
        assertNotNull("Hlava 2 vytvorena.", h2);

        assertEquals("Hlava 1 je na pozici 1.", t1.fieldAt(1), h1.seizedField());
        assertEquals("Hlava 2 je na pozici 4.", t1.fieldAt(4), h2.seizedField());

        assertTrue("Hlava 1 se posune doprava", h1.moveRight());
        assertEquals("Hlava 1 je na pozici 2.", t1.fieldAt(2), h1.seizedField());

        assertTrue("Hlava 2 se posune doprava", h2.moveRight());
        assertEquals("Hlava 2 je na pozici 6.", t1.fieldAt(6), h2.seizedField());

        assertTrue("Hlava 1 se posune doprava", h1.moveRight());
        assertEquals("Hlava 1 je na pozici 4.", t1.fieldAt(4), h1.seizedField());

        assertFalse("Hlavu 2 nelze posunout doprava", h2.moveRight());

        assertTrue("Hlava 1 se posune doprava", h1.moveRight());
        assertEquals("Hlava 1 je na pozici 8.", t1.fieldAt(8), h1.seizedField());

        assertFalse("Hlavu 1 nelze posunout doprava", h1.moveRight());
        assertFalse("Hlavu 2 nelze posunout doprava", h2.moveRight());
    }

    @Test
    public void testTape05() {
        Tape t1 = new Tape(9, 2, "w p g w p w p w g");
        TapeHead h1, h2;

        h1 = t1.createHead(1);
        h1.addKeys(1);
        assertNotNull("Hlava 1 vytvorena.", h1);
        h2 = t1.createHead(2);
        assertNotNull("Hlava 2 vytvorena.", h2);

        assertEquals("Hlava 1 je na pozici 1.", t1.fieldAt(1), h1.seizedField());
        assertEquals("Hlava 2 je na pozici 4.", t1.fieldAt(4), h2.seizedField());

        assertTrue("Hlava 1 se posune doprava", h1.moveRight());
        assertEquals("Hlava 1 je na pozici 2.", t1.fieldAt(2), h1.seizedField());

        assertTrue("Hlava 2 se posune doprava", h2.moveRight());
        assertEquals("Hlava 2 je na pozici 6.", t1.fieldAt(6), h2.seizedField());

        assertTrue("Hlava 1 se posune doprava", h1.moveRight());
        assertEquals("Hlava 1 je na pozici 4.", t1.fieldAt(4), h1.seizedField());

        assertFalse("Hlavu 1 nelze posunout doprava", h1.moveRight());
        assertFalse("Hlavu 2 nelze posunout doprava", h2.moveRight());
    }

}
/*
 The MIT License

 Copyright (c) 2009-2011 Paul R. Holser, Jr.

 Permission is hereby granted, free of charge, to any person obtaining
 a copy of this software and associated documentation files (the
 "Software"), to deal in the Software without restriction, including
 without limitation the rights to use, copy, modify, merge, publish,
 distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to
 the following conditions:

 The above copyright notice and this permission notice shall be
 included in all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.pholser.util.properties;

import java.util.HashMap;
import java.util.Map;

import com.pholser.util.properties.boundtypes.IntPropertyHaver;

import org.junit.Test;

public class ErrorsThatOccurWhenBindingUntypedMapsToTypedInterfacesTest {
    @Test(expected = ClassCastException.class)
    public void returnTypeAndUnderlyingPropertyTypeDisagree() {
        Map<String, Object> items = new HashMap<String, Object>();
        items.put("i", new Object());
        PropertyBinder<IntPropertyHaver> binder = PropertyBinder.forType(IntPropertyHaver.class);
        IntPropertyHaver bound = binder.bind(items);

        bound.i();
    }

    @Test(expected = ClassCastException.class)
    public void primitiveWidening() {
        Map<String, Object> items = new HashMap<String, Object>();
        items.put("i", Short.valueOf("2"));
        PropertyBinder<IntPropertyHaver> binder = PropertyBinder.forType(IntPropertyHaver.class);
        IntPropertyHaver bound = binder.bind(items);

        bound.i();
    }

    @Test(expected = ClassCastException.class)
    public void resolvingASeparatorWithNonStringPropertyValue() {
        Map<String, Object> items = new HashMap<String, Object>();
        items.put("bar", "A,B,C");
        items.put("separator", new Object());
        PropertyBinder<WithValueOfSeparator> binder = PropertyBinder.forType(WithValueOfSeparator.class);

        binder.bind(items);
    }

    interface WithValueOfSeparator {
        @BoundProperty("bar")
        @ValuesSeparatedBy(valueOf = "[separator]")
        String[] bar();
    }

    @Test(expected = ClassCastException.class)
    public void resolvingADefaultValueWithNonStringPropertyValue() {
        Map<String, Object> items = new HashMap<String, Object>();
        items.put("bar", "ABC");
        items.put("other", new Object());
        PropertyBinder<WithValueOfDefault> binder = PropertyBinder.forType(WithValueOfDefault.class);

        binder.bind(items);
    }

    interface WithValueOfDefault {
        @BoundProperty("foo")
        @DefaultsTo(valueOf = "[other]")
        String foo();
    }
}

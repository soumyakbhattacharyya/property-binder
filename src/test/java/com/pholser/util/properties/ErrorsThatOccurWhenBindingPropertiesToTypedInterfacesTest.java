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

import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.ResourceBundle;

import com.pholser.util.properties.boundtypes.ArrayOfUnconvertibleTypeWithDefaultPropertyHaver;
import com.pholser.util.properties.boundtypes.ArrayOfUnconvertibleTypeWithSeparatorPropertyHaver;
import com.pholser.util.properties.boundtypes.BadDefaultValuePropertyHaver;
import com.pholser.util.properties.boundtypes.BadValueSeparatorPropertyHaver;
import com.pholser.util.properties.boundtypes.CharacterPropertyHaverWithTooLongDefault;
import com.pholser.util.properties.boundtypes.DatePropertyWithNonLenientValueHaver;
import com.pholser.util.properties.boundtypes.DefaultValueWithBothValueAndValueOf;
import com.pholser.util.properties.boundtypes.DefaultValueWithNeitherValueNorValueOf;
import com.pholser.util.properties.boundtypes.InterfaceWithSuperinterfaces;
import com.pholser.util.properties.boundtypes.ListOfArrayPropertyHaver;
import com.pholser.util.properties.boundtypes.ListOfUnconvertibleTypeWithDefaultPropertyHaver;
import com.pholser.util.properties.boundtypes.ListOfUnconvertibleTypeWithSeparatorPropertyHaver;
import com.pholser.util.properties.boundtypes.ListOfUnconvertibleTypeWithValueOfSeparatorPropertyHaver;
import com.pholser.util.properties.boundtypes.LowerBoundedListPropertyHaver;
import com.pholser.util.properties.boundtypes.MissingPrimitivePropertyHaver;
import com.pholser.util.properties.boundtypes.ParsedAsOnMethodOfImproperType;
import com.pholser.util.properties.boundtypes.ScalarPropertyHaver;
import com.pholser.util.properties.boundtypes.SeparatedPropertyHaverWithBothPatternAndValueOf;
import com.pholser.util.properties.boundtypes.SeparatorOnNonAggregateTypePropertyHaver;
import com.pholser.util.properties.boundtypes.TypeWithNonPublicValueOfPropertyHaver;
import com.pholser.util.properties.boundtypes.TypeWithNonStaticValueOfPropertyHaver;
import com.pholser.util.properties.boundtypes.TypeWithValueOfWithBadReturnTypePropertyHaver;
import com.pholser.util.properties.boundtypes.UpperBoundedListPropertyHaver;
import com.pholser.util.properties.internal.exceptions.AppliedSeparatorToNonAggregateTypeException;
import com.pholser.util.properties.internal.exceptions.BoundTypeNotAnInterfaceException;
import com.pholser.util.properties.internal.exceptions.InterfaceHasSuperinterfacesException;
import com.pholser.util.properties.internal.exceptions.MalformedDefaultValueException;
import com.pholser.util.properties.internal.exceptions.MalformedSeparatorException;
import com.pholser.util.properties.internal.exceptions.MultipleDefaultValueSpecificationException;
import com.pholser.util.properties.internal.exceptions.MultipleSeparatorSpecificationException;
import com.pholser.util.properties.internal.exceptions.NoDefaultValueSpecificationException;
import com.pholser.util.properties.internal.exceptions.UnsupportedParsedAsTypeException;
import com.pholser.util.properties.internal.exceptions.UnsupportedValueTypeException;
import com.pholser.util.properties.internal.exceptions.ValueConversionException;
import org.junit.Test;

public class ErrorsThatOccurWhenBindingPropertiesToTypedInterfacesTest extends StringBindingTestSupport {
    @Test(expected = BoundTypeNotAnInterfaceException.class)
    public void nonInterfaceClass() {
        PropertyBinder.forType(Object.class);
    }

    @Test(expected = InterfaceHasSuperinterfacesException.class)
    public void annotationClass() {
        PropertyBinder.forType(SuppressWarnings.class);
    }

    @Test(expected = NullPointerException.class)
    public void nullFile() throws Exception {
        PropertyBinder.forType(ScalarPropertyHaver.class).bind((File) null);
    }

    @Test(expected = NullPointerException.class)
    public void nullInputStream() throws Exception {
        PropertyBinder.forType(ScalarPropertyHaver.class).bind((InputStream) null);
    }

    @Test(expected = NullPointerException.class)
    public void nullMap() {
        PropertyBinder.forType(ScalarPropertyHaver.class).bind((Map<String, ?>) null);
    }

    @Test(expected = NullPointerException.class)
    public void nullResourceBundle() {
        PropertyBinder.forType(ScalarPropertyHaver.class).bind((ResourceBundle) null);
    }

    @Test(expected = NullPointerException.class)
    public void nullPropertySource() {
        PropertyBinder.forType(ScalarPropertyHaver.class).bind((PropertySource) null);
    }

    @Test(expected = NullPointerException.class)
    public void nullClass() {
        PropertyBinder.forType(null);
    }

    @Test(expected = MalformedDefaultValueException.class)
    public void nonSingleCharacterValueForCharacterProperty() {
        PropertyBinder.forType(CharacterPropertyHaverWithTooLongDefault.class);
    }

    @Test(expected = UnsupportedValueTypeException.class)
    public void upperBoundedListType() {
        PropertyBinder.forType(UpperBoundedListPropertyHaver.class);
    }

    @Test(expected = UnsupportedValueTypeException.class)
    public void lowerBoundedListType() {
        PropertyBinder.forType(LowerBoundedListPropertyHaver.class);
    }

    @Test(expected = UnsupportedValueTypeException.class)
    public void listOfArrayType() {
        PropertyBinder.forType(ListOfArrayPropertyHaver.class);
    }

    @Test(expected = UnsupportedValueTypeException.class)
    public void arrayOfUnconvertibleTypeWithDefault() {
        PropertyBinder.forType(ArrayOfUnconvertibleTypeWithDefaultPropertyHaver.class);
    }

    @Test(expected = UnsupportedValueTypeException.class)
    public void arrayOfUnconvertibleTypeWithSeparator() {
        PropertyBinder.forType(ArrayOfUnconvertibleTypeWithSeparatorPropertyHaver.class);
    }

    @Test(expected = UnsupportedValueTypeException.class)
    public void listOfUnconvertibleTypeWithDefault() {
        PropertyBinder.forType(ListOfUnconvertibleTypeWithDefaultPropertyHaver.class);
    }

    @Test(expected = UnsupportedValueTypeException.class)
    public void listOfUnconvertibleTypeWithSeparator() {
        PropertyBinder.forType(ListOfUnconvertibleTypeWithSeparatorPropertyHaver.class);
    }

    @Test(expected = UnsupportedValueTypeException.class)
    public void listOfUnconvertibleTypeWithValueOfSeparator() {
        PropertyBinder.forType(ListOfUnconvertibleTypeWithValueOfSeparatorPropertyHaver.class);
    }

    @Test(expected = UnsupportedValueTypeException.class)
    public void typeWithNonPublicValueOf() {
        PropertyBinder.forType(TypeWithNonPublicValueOfPropertyHaver.class);
    }

    @Test(expected = UnsupportedValueTypeException.class)
    public void typeWithNonStaticValueOf() {
        PropertyBinder.forType(TypeWithNonStaticValueOfPropertyHaver.class);
    }

    @Test(expected = UnsupportedValueTypeException.class)
    public void typeWithValueOfWithBadReturnType() {
        PropertyBinder.forType(TypeWithValueOfWithBadReturnTypePropertyHaver.class);
    }

    @Test(expected = NullPointerException.class)
    public void missingPrimitiveProperty() throws Exception {
        PropertyBinder<MissingPrimitivePropertyHaver> binder =
            PropertyBinder.forType(MissingPrimitivePropertyHaver.class);
        MissingPrimitivePropertyHaver bound = binder.bind(propertiesFile);

        bound.missingCharacterProperty();
    }

    @Test(expected = MalformedDefaultValueException.class)
    public void badDefaultValue() {
        PropertyBinder.forType(BadDefaultValuePropertyHaver.class);
    }

    @Test(expected = MalformedSeparatorException.class)
    public void badValueSeparator() {
        PropertyBinder.forType(BadValueSeparatorPropertyHaver.class);
    }

    @Test(expected = AppliedSeparatorToNonAggregateTypeException.class)
    public void applyingSeparatorToNonAggregateType() {
        PropertyBinder.forType(SeparatorOnNonAggregateTypePropertyHaver.class);
    }

    @Test(expected = MultipleSeparatorSpecificationException.class)
    public void separatorWithBothPatternAndValueOf() {
        PropertyBinder.forType(SeparatedPropertyHaverWithBothPatternAndValueOf.class);
    }

    @Test(expected = InterfaceHasSuperinterfacesException.class)
    public void typeWithSuperinterfaces() {
        PropertyBinder.forType(InterfaceWithSuperinterfaces.class);
    }

    @Test(expected = MultipleDefaultValueSpecificationException.class)
    public void defaultValueSpecWithBothValueAndValueOf() {
        PropertyBinder.forType(DefaultValueWithBothValueAndValueOf.class);
    }

    @Test(expected = NoDefaultValueSpecificationException.class)
    public void defaultValueSpecWithNeitherValueNorValueOf() {
        PropertyBinder.forType(DefaultValueWithNeitherValueNorValueOf.class);
    }

    @Test(expected = ValueConversionException.class)
    public void datePropertiesThatWouldNotPassNonLenientDateFormats() throws Exception {
        DatePropertyWithNonLenientValueHaver bound =
            PropertyBinder.forType(DatePropertyWithNonLenientValueHaver.class).bind(propertiesFile);

        bound.datePropertyWithNonLenientValue();
    }

    @Test(expected = UnsupportedParsedAsTypeException.class)
    public void parsedAsIfAppliedToTypeOtherThanDate() {
        PropertyBinder.forType(ParsedAsOnMethodOfImproperType.class);
    }
}

package com.sub.techsub.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringArrayConverterTest {

    private StringArrayConverter converter;

    @BeforeEach
    public void setUp() {
        converter = new StringArrayConverter();
    }

    @Test
    public void testConvertToDatabaseColumn_ValidArray() throws JsonProcessingException {
        String[] array = {"item1", "item2", "item3"};
        String jsonResult = converter.convertToDatabaseColumn(array);

        assertThat(jsonResult).isEqualTo("[\"item1\",\"item2\",\"item3\"]");
    }

    @Test
    public void testConvertToEntityAttribute_ValidJson() {
        String jsonData = "[\"item1\",\"item2\",\"item3\"]";
        String[] result = converter.convertToEntityAttribute(jsonData);

        assertThat(result).containsExactly("item1", "item2", "item3");
    }

}

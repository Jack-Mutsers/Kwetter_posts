package com.kwetter.posting_service.helpers.tools;

import java.util.List;

public class Helper {

    private Helper(){

    }

    public static boolean IsEmpty(String value){
        return value == null || value.isBlank();
    }

    public static <T> List<T> emptyIfNull(List<T> iterable) {
        return iterable == null ? List.of() : iterable;
    }
}

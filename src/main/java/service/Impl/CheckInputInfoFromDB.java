package service.Impl;

import java.util.HashSet;
import java.util.Set;

public class CheckInputInfoFromDB {
    static Set<String> checkUserInfoFromDB(String entity, boolean b, boolean b2, boolean b3, boolean b4) {
        Set<String> errors = new HashSet<>();
        if (b)
            errors.add(entity + " with national code already exists");
        if (b2)
            errors.add(entity + " with Mobile Number already exists");
        if (b3)
            errors.add("User with Email Address already exists");
        if (b4)
            // throw new FoundUserNameException("User with Username already exists");
            // System.err.println("User with Username already exists");
            errors.add("User with Username already exists");
        /*if (b || b2 || b3 || b4)
            throw new FoundException("correct the entries! ");*/

        return errors;
    }
}

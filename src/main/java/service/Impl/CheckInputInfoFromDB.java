package service.Impl;

import customeException.*;

public class CheckInputInfoFromDB {
    static void checkUserInfoFromDB(String entity, boolean b, boolean b2, boolean b3, boolean b4) {
        if (b)
            //throw new FoundNationalCodeException(entity+" with national code already exists");
            System.err.println(entity + " with national code already exists");
        if (b2)
            //  throw new FoundMobileNumberException(entity+"Expert with Mobile Number already exists");
            System.err.println(entity + " with Mobile Number already exists");
        if (b3)
            // throw new FoundEmailAddressException("User with Email Address already exists");
            System.err.println("User with Email Address already exists");
        if (b4)
            // throw new FoundUserNameException("User with Username already exists");
            System.err.println("User with Username already exists");
        if (b || b2 || b3 || b4)
            throw new FoundException("correct the entries! ");
    }
}

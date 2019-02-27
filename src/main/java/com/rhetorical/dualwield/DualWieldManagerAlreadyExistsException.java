package com.rhetorical.dualwield;

/**
 * Used to help in debugging when the DualWieldManager already exists.
 * */
class DualWieldManagerAlreadyExistsException extends Exception {
    DualWieldManagerAlreadyExistsException(String message) {
        super (message);
    }
}

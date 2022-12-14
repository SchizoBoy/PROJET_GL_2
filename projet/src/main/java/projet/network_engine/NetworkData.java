package projet.network_engine;

/***** IMPORTS *****/

import java.io.Serializable;    // To allow network transmission

/***** CLASS *****/

/**
 * Data exchanged between Client and Server
 */
public class NetworkData implements Serializable {
    /***** PARAMETERS *****/
    String message;

    /***** CONSTRUCTORS *****/

    /**
     * Default constructor
     */
    protected NetworkData() {}

    /**
     * Constructor with message
     * @param _message
     */
    NetworkData(String _message) {
        message = _message;
    }
}
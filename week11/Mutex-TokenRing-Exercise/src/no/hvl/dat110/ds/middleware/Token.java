/**
 *
 */
package no.hvl.dat110.ds.middleware;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author tdoy
 */
public record Token(int tokenId) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

}

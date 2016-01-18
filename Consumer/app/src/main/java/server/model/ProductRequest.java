package server.model;

/**
 * Created by lenovo on 17/01/2016.
 */

        import java.io.Serializable;

@SuppressWarnings("serial")
public class ProductRequest implements Serializable {
    private static final long serialVersionUID = 8805413983551141234L;
    private int id;

    public ProductRequest() {
    }

    public ProductRequest(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}

package server.model;

import java.io.Serializable;

/**
 * Created by lenovo on 17/01/2016.
 */
public class ListProduct implements Serializable

    {
        private static final long serialVersionUID = 1113799434508676095L;

        private String name;
        private String category;
        private double price;
        private String description;
        private byte[] thumbnail;
        private byte[] videoDemo;
        private double ownerLatitude;
        private double ownerLongitude;
}

package server.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Product implements Serializable {
  private static final long serialVersionUID = 1113799434508676095L;
  private int id;
  private String name;
  private String category;
  private double price;
  private String description;
  private byte[] thumbnail;
  private byte[] videoDemo;
  private double latitude;
  private double longitude;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }


  public Product(String name, String category, double price, String description, byte[] thumbnail, byte[] videoDemo, double latitude, double longitude) {
    this.name = name;
    this.category = category;
    this.price = price;
    this.description = description;
    this.thumbnail = thumbnail;
    this.videoDemo = videoDemo;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longtitude) {
    this.longitude = longtitude;
  }

  public Product() {}



  public byte[] getThumbnail() {
    return thumbnail;
  }

  public void setThumbnail(byte[] thumbnail) {
    this.thumbnail = thumbnail;
  }

  public byte[] getVideoDemo() {
    return videoDemo;
  }

  public void setVideoDemo(byte[] videoDemo) {
    this.videoDemo = videoDemo;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}

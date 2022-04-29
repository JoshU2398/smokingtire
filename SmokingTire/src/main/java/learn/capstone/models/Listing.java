package learn.capstone.models;

import learn.capstone.App;

import java.time.LocalDate;
import java.util.Objects;

public class Listing {
    Integer listingId;
    Integer price;
    Integer mileage;
    LocalDate postDate;
    String description;
    Integer viewCount;
    AppUser user;
    Car car;


    public Listing() {
    }


    public Listing(Integer listingId, Integer price, Integer mileage, LocalDate postDate, String description, Integer viewCount, AppUser user, Car car) {
        this.listingId = listingId;
        this.price = price;
        this.mileage = mileage;
        this.postDate = postDate;
        this.description = description;
        this.viewCount = viewCount;
        this.user = this.user;
        this.car = this.car;
    }


    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Integer getListingId() {
        return listingId;
    }

    public void setListingId(Integer listingId) {
        this.listingId = listingId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public LocalDate getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Listing listing = (Listing) o;
        return Objects.equals(listingId, listing.listingId) && Objects.equals(price, listing.price) && Objects.equals(mileage, listing.mileage) && Objects.equals(postDate, listing.postDate) && Objects.equals(description, listing.description) && Objects.equals(viewCount, listing.viewCount) && Objects.equals(user, listing.user) && Objects.equals(car, listing.car);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listingId, price, mileage, postDate, description, viewCount, user, car);
    }
}

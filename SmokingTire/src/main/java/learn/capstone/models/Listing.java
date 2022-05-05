package learn.capstone.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

public class Listing {
    Integer listingId;
    @NotNull(message = "Price is required.")
    @Min(value = 0, message = "Price cannot be less than zero.")
    Integer price;
    @NotNull(message = "Mileage is required")
    @Min(value = 0, message = "Mileage can't be less than zero.")
    Integer mileage;
    @NotNull(message = "Post date is required.")
    LocalDate postDate;
    @NotNull(message = "Description is required.")
    String description;
    Integer viewCount;
    @NotNull(message = "User is required.")
    AppUser listingUser;
    @NotNull(message = "Car is required.")
    Car car;
    Boolean isAvailable;


    public Listing() {
    }

    public Listing(Integer listingId, Integer price, Integer mileage, LocalDate postDate, String description, Integer viewCount, AppUser user, Car car, boolean isAvailable) {
        this.listingId = listingId;
        this.price = price;
        this.mileage = mileage;
        this.postDate = postDate;
        this.description = description;
        this.viewCount = viewCount;
        this.listingUser = user;
        this.car = car;
        this.isAvailable = isAvailable;
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

    public AppUser getListingUser() {
        return listingUser;
    }

    public void setListingUser(AppUser listingUser) {
        this.listingUser = listingUser;
    }

    public Boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Listing listing = (Listing) o;
        return isAvailable == listing.isAvailable && Objects.equals(listingId, listing.listingId) && Objects.equals(price, listing.price) && Objects.equals(mileage, listing.mileage) && Objects.equals(postDate, listing.postDate) && Objects.equals(description, listing.description) && Objects.equals(viewCount, listing.viewCount) && Objects.equals(listingUser, listing.listingUser) && Objects.equals(car, listing.car);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listingId, price, mileage, postDate, description, viewCount, listingUser, car, isAvailable);
    }
}

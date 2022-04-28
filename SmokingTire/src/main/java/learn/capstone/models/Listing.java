package learn.capstone.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Listing {
    Integer listingId;
    BigDecimal price;
    Integer mileage;
    LocalDate postDate;
    String description;
    Integer viewCount;
    AppUser owner;


    public Listing() {
    }

    public Listing(Integer listingId, BigDecimal price, Integer mileage, LocalDate postDate, String description, Integer viewCount, AppUser owner) {
        this.listingId = listingId;
        this.price = price;
        this.mileage = mileage;
        this.postDate = postDate;
        this.description = description;
        this.viewCount = viewCount;
        this.owner = owner;
    }

    public Integer getListingId() {
        return listingId;
    }

    public void setListingId(Integer listingId) {
        this.listingId = listingId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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

    public AppUser getOwner() {
        return owner;
    }

    public void setOwner(AppUser owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Listing listing = (Listing) o;
        return Objects.equals(listingId, listing.listingId) && Objects.equals(price, listing.price) && Objects.equals(mileage, listing.mileage) && Objects.equals(postDate, listing.postDate) && Objects.equals(description, listing.description) && Objects.equals(viewCount, listing.viewCount) && Objects.equals(owner, listing.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listingId, price, mileage, postDate, description, viewCount, owner);
    }
}

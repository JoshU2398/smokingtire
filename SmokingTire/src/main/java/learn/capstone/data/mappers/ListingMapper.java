package learn.capstone.data.mappers;

import learn.capstone.models.Listing;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ListingMapper implements RowMapper<Listing> {
    @Override
    public Listing mapRow(ResultSet rs, int rowNum) throws SQLException {
        Listing listing = new Listing();
        listing.setListingId(rs.getInt("listingId"));
        listing.setDescription(rs.getString("listingText"));
        listing.setPrice(rs.getInt("price"));
        listing.setMileage(rs.getInt("mileage"));

        listing.setViewCount(rs.getInt("views"));
        if(rs.getDate("createDate") != null){
            listing.setPostDate(rs.getDate("createDate").toLocalDate());
        }
        listing.setAvailable(rs.getBoolean("isAvailable"));
        listing.setImageUrl(rs.getString("imageUrl"));

        return listing;
    }
}

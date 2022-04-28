package learn.capstone.data.mappers;

import learn.capstone.models.AppUser;
import learn.capstone.models.Listing;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ListingMapper implements RowMapper<Listing> {
    @Override
    public Listing mapRow(ResultSet rs, int rowNum) throws SQLException {
        Listing listing = new Listing();
        listing.setListingId(rs.getInt("listingId"));
        listing.setPrice(rs.getBigDecimal("price"));
        listing.setMileage(rs.getInt("mileage"));
        listing.setDescription(rs.getString("listingText"));
        listing.setViewCount(rs.getInt("views"));
        listing.setUserId(rs.getInt("userId"));
        listing.setCarId(rs.getInt("carId"));
        if(rs.getDate("createDate") != null){
            listing.setPostDate(rs.getDate("createDate").toLocalDate());
        }

        return listing;
    }
}

package learn.capstone.data;

import learn.capstone.data.mappers.ListingMapper;
import learn.capstone.models.Listing;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ListingDbRepo implements ListingRepo {
    private JdbcTemplate template;

    public ListingDbRepo(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<Listing> findAllListings() {
        final String sql = "select listingId, listingText, userId, carId, createDate, views, mileage, price from listings;";
        return template.query(sql, new ListingMapper());
    }

    @Override
    @Transactional
    public List<Listing> findByMakeId(int makeId) {
        final String sql = "select listingId, listingText, userId, carId, createDate, views, mileage, price "
                + "inner join "
                + "inner join "
                + "from listings "
                + "where makeId = ?;";

        List<Listing> result = template.query(sql, new ListingMapper(), makeId).stream()
                ;
        if(result != null){

        }

        return result;
    }

    @Override
    @Transactional
    public List<Listing> findByModelId(int modelId) {
        final String sql = "select listingId, listingText, userId, carId, createDate, views, mileage, price "
                + "from listings "
                + "where modelId = ?;";

        List<Listing> result = template.query(sql, new ListingMapper(), modelId).stream()
                .findAny().orElse(null);
        if(result != null){

        }

        return result;
    }

    @Override
    public List<Listing> findByPriceRange(Integer min, Integer max) {
        return null;
    }

    @Override
    public Listing add(Listing toAdd) {
        return null;
    }

    @Override
    public boolean edit(Listing toEdit) {
        return false;
    }

    @Override
    public boolean deleteById(int listingId) {
        return false;
    }
}

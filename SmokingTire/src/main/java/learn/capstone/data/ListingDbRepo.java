package learn.capstone.data;

import learn.capstone.data.mappers.ListingMapper;
import learn.capstone.data.mappers.MakeMapper;
import learn.capstone.data.mappers.ModelMapper;
import learn.capstone.models.Car;
import learn.capstone.models.Listing;
import learn.capstone.models.Make;
import learn.capstone.models.Model;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ListingDbRepo implements ListingRepo {
    private JdbcTemplate template;

    public ListingDbRepo(JdbcTemplate template) {
        this.template = template;
    }


    @Override
    public List<Listing> findAllAvailableListings() {
        final String sql = "select listingId, listingText, createDate, views, mileage, price, isAvailable "
                + "from listings where isAvailable = true;";

        List<Listing> result = template.query(sql, new ListingMapper()).stream().collect(Collectors.toList());
    }

    @Override
    public List<Listing> findPurchasedListingsByUser(String username) {
        final String sql = "select l.listingId, l.listingText, l.createDate, l.views, l.mileage, l.price, l.isAvailable "
                + "from listings l "
                + "inner join users u on l.userId = u.userId "
                + "where l.isAvailable = true and where u.username = ?;";

        List<Listing> result = template.query(sql, new ListingMapper()).stream().collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<Listing> findByMakeId(int makeId) {
        final String sql = "select l.listingId, l.listingText, l.createDate, l.views, l.mileage, l.price "
                + "from listings l "
                + "inner join cars c on c.carId = l.carId "
                + "inner join makes m on m.makeId = c.makeId "
                + "where makeId = ?;";

        List<Listing> result = template.query(sql, new ListingMapper(), makeId).stream().collect(Collectors.toList());

        if(result != null){
            addMake(result);
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
                .collect(Collectors.toList());

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
        final String sql = "insert into listings (listingText, userId, carId, createDate, views, mileage, price)"
                + "values (?,?,?,?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, toAdd.getDescription());
            ps.setDate(2, Date.valueOf(toAdd.getPostDate()));
            ps.setInt(3, toAdd.getViewCount());
            ps.setInt(4, toAdd.getMileage());
            ps.setInt(5, toAdd.getPrice());
            return ps;
        }, keyHolder);

        if(rowsAffected <= 0){
            return null;
        }
        toAdd.setListingId(keyHolder.getKey().intValue());
        return toAdd;
    }

    @Override
    public boolean edit(Listing toEdit) {
        final String sql = "update listing set "
                + "listingText = ?, "
                + "createDate = ?, "
                + "views = ?, "
                + "mileage = ?, "
                + "price = ?, "
                + "where listingId = ?;";
        return template.update(sql,
                toEdit.getDescription(),
                toEdit.getPostDate(),
                toEdit.getViewCount(),
                toEdit.getMileage(),
                toEdit.getPrice(),
                toEdit.getListingId()
                ) > 0;
    }

    @Override
    public boolean deleteById(int listingId) {
        return template.update(
                "delete from listings where listingId = ?", listingId
        ) > 0;
    }

    private void addUser(List<Listing> listings {
        String 
        for (Listing l : listings) {

        }

    }

    private void addCar(List<Listing> listings) {
        for (Listing l : listings) {

        }

    }

    private void addMake(Car car){
        final String sql = "select m.makeId, m.makeName, m.modelId "
                + "from listings l "
                + "inner join cars c on c.carId = l.carId "
                + "inner join makes m on m.makeId = c.makeId "
                + "where c.carId = ?;";

        Make make = template.query(sql, new MakeMapper(), car.getCarId()).stream()
                .findFirst().orElse(null);

        addModel(make);
        car.setMake(make);
    }

    private void addModel(Make make){
        final String sql = "select m.makeId, m.makeName, m.modelId "
                + "from listings l "
                + "inner join cars c on c.carId = l.carId "
                + "inner join makes m on m.makeId = c.makeId "
                + "inner join models mo on mo.modelId = m.modelId "
                + "where m.makeId = ?;";

        Model model = template.query(sql, new ModelMapper(), make.getMakeId()).stream()
                .findFirst().orElse(null);
        make.setModel(model);
    }

}

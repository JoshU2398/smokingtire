package learn.capstone.data;

import learn.capstone.data.mappers.*;
import learn.capstone.models.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Collections;
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

        if(result.size() != 0){
            addCar(result);
            addUser(result);
        }
        return result;
    }

    @Override
    public List<Listing> findPurchasedListingsByUser(String username) {
        final String sql = "select l.listingId, l.listingText, l.createDate, l.views, l.mileage, l.price, l.isAvailable "
                + "from listings l "
                + "inner join users u on l.userId = u.userId "
                + "where l.isAvailable = false and u.username = ?;";

        List<Listing> result = template.query(sql, new ListingMapper(), username).stream().collect(Collectors.toList());

        if(result.size() != 0){
            addCar(result);
            addUser(result);
        }
        return result;
    }

    @Override
    @Transactional
    public List<Listing> findByMakeId(int makeId) {
        final String sql = "select l.listingId, l.listingText, l.createDate, l.views, l.mileage, l.price "
                + "from listings l "
                + "inner join cars c on c.carId = l.carId "
                + "inner join makes m on m.makeId = c.makeId "
                + "where m.makeId = ?;";

        List<Listing> result = template.query(sql, new ListingMapper(), makeId).stream().collect(Collectors.toList());

        if(result.size() != 0){
            addCar(result);
            addUser(result);
        }
        return result;
    }

    @Override
    @Transactional
    public List<Listing> findByModelId(int modelId) {
        final String sql = "select l.listingId, l.listingText, l.userId, l.carId, l.createDate, l.views, l.mileage, l.price "
                + "from listings l "
                + "inner join cars c on c.carId = l.carId "
                + "inner join makes m on m.makeId = c.makeId "
                + "inner join models mo on mo.modelId = m.modelId "
                + "where mo.modelId = ?;";

        List<Listing> result = template.query(sql, new ListingMapper(), modelId).stream()
                .collect(Collectors.toList());

        if(result.size() != 0){
            addCar(result);
            addUser(result);
        }
        return result;
    }

    @Override
    public List<Listing> findByPriceRange(Integer min, Integer max) {
        final String sql = "select listingId, listingText, userId, carId, createDate, views, mileage, price "
                + "from listings "
                + "where price between ? and ?;";

        List<Listing> result = template.query(sql, new ListingMapper(), min, max).stream()
                .collect(Collectors.toList());

        if(result.size() != 0){
            addCar(result);
            addUser(result);
        }
        return result;
    }

    @Override
    public Listing findByListingId(int listingId) {
        final String sql = "select listingId, listingText, userId, carId, createDate, views, mileage, price "
                + "from listings "
                + "where listingId = ?;";

        Listing result = template.query(sql, new ListingMapper(), listingId).stream()
                .findFirst().orElse(null);

        if(result != null){
            addCar(result);
            addUser(result);
        }
        return result;
    }

    @Override
    public Listing add(Listing toAdd) {
        final String sql = "insert into listings (listingText, userId, carId, createDate, views, mileage, price, isAvailable)"
                + "values (?,?,?,?,?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = template.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, toAdd.getDescription());
            ps.setInt(2, toAdd.getUser().getUserId());
            ps.setInt(3, toAdd.getCar().getCarId());
            ps.setDate(4, Date.valueOf(LocalDate.now()));
            ps.setInt(5, toAdd.getViewCount());
            ps.setInt(6, toAdd.getMileage());
            ps.setInt(7, toAdd.getPrice());
            ps.setBoolean(8, true);
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
        final String sql = "update listings set "
                + "listingText = ?, "
                + "createDate = ?, "
                + "mileage = ?, "
                + "price = ? "
                + "where listingId = ?;";

        return template.update(sql,
                toEdit.getDescription(),
                toEdit.getPostDate(),
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

    @Override
    public boolean increaseViewCount(Listing toUpdate) {
        final String sql = "update listings set "
                + "views = ? "
                + "where listingId = ?;";

        int views = toUpdate.getViewCount();
        views++;

        return template.update(sql, views, toUpdate.getListingId()) > 0;
    }

    @Override
    public boolean convertToSold(Listing toConvert, AppUser purchaser) {
        final String sql = "update listings set "
                + "userId = ?, "
                + "isAvailable = false "
                + "where listingId = ?;";

        toConvert.setUser(purchaser);

        return template.update(sql, toConvert.getUser().getUserId(), toConvert.getListingId()) > 0;
    }

    private void addUser(List<Listing> listings) {
        String sql = "select u.userId, u.username, u.password "
                + "from listings l "
                + "inner join users u on l.userId = u.userId "
                + "where l.listingId = ?;";

        for (Listing l : listings) {
            AppUser user = template.query(sql, new UserMapper(Collections.singleton("USER")), l.getListingId()).stream()
                    .findFirst().orElse(null);

            l.setUser(user);
        }

    }

    private void addUser(Listing listing) {
        String sql = "select u.userId, u.username, u.password "
                + "from listings l "
                + "inner join users u on l.userId = u.userId "
                + "where l.listingId = ?;";

        AppUser user = template.query(sql, new UserMapper(Collections.singleton("USER")), listing.getListingId()).stream()
                .findFirst().orElse(null);

        listing.setUser(user);
    }

    private void addCar(List<Listing> listings) {
        String sql = "select c.carId, c.horsepower, c.drivetrain, c.chassis, c.transmission "
                + "from listings l "
                + "inner join cars c on l.carId = c.carId "
                + "where l.listingId = ?;";

        for (Listing l : listings) {
            Car car = template.query(sql, new CarMapper(), l.getListingId()).stream()
                    .findFirst().orElse(null);

            addMake(car);
            l.setCar(car);
        }

    }

    private void addCar(Listing listing) {
        String sql = "select c.carId, c.horsepower, c.drivetrain, c.chassis, c.transmission "
                + "from listings l "
                + "inner join cars c on l.carId = c.carId "
                + "where l.listingId = ?;";

        Car car = template.query(sql, new CarMapper(), listing.getListingId()).stream()
                .findFirst().orElse(null);

        addMake(car);
        listing.setCar(car);
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
        final String sql = "select mo.modelId, mo.modelName, mo.modelYear "
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

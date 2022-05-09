package learn.capstone.data;

import learn.capstone.models.Image;
import java.util.List;

public interface ImageRepo {

    List<Image> findAll();
    Image findById(Integer id);
    List<Image> findImagesByListing(Integer listingId);
    Image add(Image toAdd);
    Boolean update(Image toUpdate);
    Boolean deleteById(Integer id);
}

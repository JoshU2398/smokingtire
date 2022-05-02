package learn.capstone.data;

import learn.capstone.models.Model;

import java.util.List;

public interface ModelRepo {

    List<Model> findAll();
}

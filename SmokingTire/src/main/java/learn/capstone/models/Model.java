package learn.capstone.models;

import java.time.LocalDate;
import java.util.Objects;

public class Model {

    Integer modelId;
    String modelName;
    LocalDate modelYear;

    public Model(){

    }

    public Model(Integer modelId, String modelName, LocalDate modelYear) {
        this.modelId = modelId;
        this.modelName = modelName;
        this.modelYear = modelYear;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public LocalDate getModelYear() {
        return modelYear;
    }

    public void setModelYear(LocalDate modelYear) {
        this.modelYear = modelYear;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model = (Model) o;
        return Objects.equals(modelId, model.modelId) && Objects.equals(modelName, model.modelName) && Objects.equals(modelYear, model.modelYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(modelId, modelName, modelYear);
    }
}

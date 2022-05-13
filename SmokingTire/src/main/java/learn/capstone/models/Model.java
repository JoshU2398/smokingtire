package learn.capstone.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class Model {
    Integer modelId;
    @NotNull(message = "Model name cannot be empty.")
    String modelName;
    @NotNull(message = "Model year cannot be empty.")
    @Min(value = 1950, message = "We are not accepting cars from prior to 1950.")
    Integer modelYear;
    Integer makeId;
    String makeName;

    public Model() {
    }

    public Model(Integer modelId, String modelName, Integer modelYear, Integer makeId, String makeName) {
        this.modelId = modelId;
        this.modelName = modelName;
        this.modelYear = modelYear;
        this.makeId = makeId;
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

    public Integer getModelYear() {
        return modelYear;
    }

    public void setModelYear(Integer modelYear) {
        this.modelYear = modelYear;
    }

    public Integer getMakeId() {
        return makeId;
    }

    public void setMakeId(Integer makeId) {
        this.makeId = makeId;
    }

    public String getMakeName() {
        return makeName;
    }

    public void setMakeName(String makeName) {
        this.makeName = makeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model = (Model) o;
        return Objects.equals(modelId, model.modelId) && Objects.equals(modelName, model.modelName) && Objects.equals(modelYear, model.modelYear) && Objects.equals(makeId, model.makeId) && Objects.equals(makeName, model.makeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(modelId, modelName, modelYear, makeId, makeName);
    }
}

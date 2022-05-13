package learn.capstone.models;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class Make {
    Integer makeId;
    @NotNull(message = "Name cannot be empty.")
    String makeName;


    public Make() {
    }

    public Make(Integer makeId, String makeName) {
        this.makeId = makeId;
        this.makeName = makeName;
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
        Make make = (Make) o;
        return Objects.equals(makeId, make.makeId) && Objects.equals(makeName, make.makeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(makeId, makeName);
    }
}

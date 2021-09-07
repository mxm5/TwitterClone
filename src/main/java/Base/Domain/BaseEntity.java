package Base.Domain;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity<ID extends Serializable> {
    @Id
    @GeneratedValue
    ID id;

    {
        setDateToNow();
    }

    public Date getNow() {
        return Date.valueOf(LocalDate.now());
    }

    public abstract void setDateToNow();

    public abstract void cleanAssociations();
}

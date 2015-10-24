package spring.sample.rest.domains;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(
        name = "room",
        indexes = {@Index(name = "idx_room_nm", columnList = "name")},
        uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Room extends AbstractPersistable<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "chk_date")
    private Date checkIn;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private List<SampleUser> sampleUsers = new LinkedList<>();

    public static Room create(String name) {
        Room r = new Room();
        r.setName(name);
        r.setCheckIn(new Date());
        return r;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                          .add("id", super.getId())
                          .add("name", this.name)
                          .add("checkIn", this.checkIn)
                          .toString();
    }
}

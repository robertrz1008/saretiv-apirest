package my.project.entities.transaction;

import jakarta.persistence.*;

@Entity
@Table(name = "support_activities")
public class SupportActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "supporttype_id", referencedColumnName = "id", nullable = false)
    private TypeSupport supportType;

    @ManyToOne
    @JoinColumn(name = "support_id", referencedColumnName = "id", nullable = false)
    private Support support;

    public SupportActivity(TypeSupport supportType, Support support) {
        this.supportType = supportType;
        this.support = support;
    }
    public SupportActivity(){}

    public TypeSupport getSupportType() {
        return supportType;
    }

    public void setSupportType(TypeSupport supportType) {
        this.supportType = supportType;
    }

    public Support getSupport() {
        return support;
    }

    public void setSupport(Support support) {
        this.support = support;
    }

    public long getId() {
        return id;
    }

}


@Entity
public class Diseases extends Model {
    @Id
    @Column(name = "disease_id")
    Long diseaseId;

    @Column(length = 50)
    String symptoms;

    @ManyToOne(optional = false)
    DiseasesTypes disType;

    @Column(length = 50, nullable = false, name = "disease_name")
    String disName;

    public Diseases(DiseasesTypes disType, String disName) {
        this.disType = disType;
        this.disName = disName;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public DiseasesTypes getDisType() {
        return disType;
    }

    public void setDisType(DiseasesTypes disType) {
        this.disType = disType;
    }

    public String getDisName() {
        return disName;
    }

    public void setDisName(String disName) {
        this.disName = disName;
    }
}

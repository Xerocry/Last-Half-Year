@Entity
public class Departments extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long depart_id;

    @Column(length=50, nullable = false)
    String depart_name;

    /*@OneToMany(mappedBy = "DOCTORS")
    List<Doctors> doctors;*/

    public Departments(String depart_name) {
        this.depart_name = depart_name;
    }

    public Long getDepart_id() {
        return depart_id;
    }

    public void setDepart_id(Long depart_id) {
        this.depart_id = depart_id;
    }

    public String getDepart_name() {
        return depart_name;
    }

    public void setDepart_name(String depart_name) {
        this.depart_name = depart_name;
    }

    /*public List<Doctors> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctors> doctors) {
        this.doctors = doctors;
    }*/
}

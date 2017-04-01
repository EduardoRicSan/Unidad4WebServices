package mx.edu.utng.wsmovie;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by SERGIO on 30/03/2017.
 */

public class Document implements KvmSerializable {
    private int id;
    private String description;
    private String active;
    private String university;
    private String date;

    public Document(int id, String description, String active, String university, String date) {
        this.id = id;
        this.description = description;
        this.active = active;
        this.university = university;
        this.date = date;
    }

    public Document() {
        this(0,"","","","");
    }

    @Override
    public Object getProperty(int i) {
        switch (i){
            case 0:
                return  id;
            case 1:
                return  description;
            case 2:
                return  active;
            case 3:
                return  university;
            case 4:
                return  date;

        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 5;
    }

    @Override
    public void setProperty(int i, Object o) {
        switch (i){
            case 0:
                id=Integer.parseInt(o.toString());
                break;
            case 1:
                description=o.toString();
                break;
            case 2:
                active=o.toString();
                break;
            case 3:
                university=o.toString();
                break;
            case 4:
                date=o.toString();
                break;
            default:
                break;
        }

    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        switch (i){
            case 0:
                propertyInfo.type=PropertyInfo.INTEGER_CLASS;
                propertyInfo.name="id";
                break;
            case 1:
                propertyInfo.type=PropertyInfo.STRING_CLASS;
                propertyInfo.name="description";
                break;
            case 2:
                propertyInfo.type=PropertyInfo.STRING_CLASS;
                propertyInfo.name="active";
                break;

            case 3:
                propertyInfo.type=PropertyInfo.STRING_CLASS;
                propertyInfo.name="university";
                break;
            case 4:
                propertyInfo.type=PropertyInfo.STRING_CLASS;
                propertyInfo.name="date";
                break;


            default:
                break;
        }

    }


}

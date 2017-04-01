package mx.edu.utng.wsmovie;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by SERGIO on 31/03/2017.
 */

public class Company implements KvmSerializable {
    private int id;
    private String name;
    private String owner;
    private String foundation;
    private String type;
    private String objetive;
    private String partner;

    public Company(int id, String name, String owner, String foundation, String type, String objetive, String partner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.foundation = foundation;
        this.type = type;
        this.objetive = objetive;
        this.partner = partner;
    }
    public Company() {
        this(0,"","","","","","");
    }

    @Override
    public Object getProperty(int i) {
        switch (i){
            case 0:
                return  id;
            case 1:
                return  name;
            case 2:
                return  owner;
            case 3:
                return  foundation;
            case 4:
                return  type;
            case 5:
                return  objetive;
            case 6:
                return  partner;

        }
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 7;
    }

    @Override
    public void setProperty(int i, Object o) {
        switch (i){
            case 0:
                id=Integer.parseInt(o.toString());
                break;
            case 1:
                name=o.toString();
                break;
            case 2:
                owner=o.toString();
                break;
            case 3:
                foundation=o.toString();
                break;
            case 4:
                type=o.toString();
                break;
            case 5:
                objetive=o.toString();
                break;
            case 6:
                partner=o.toString();
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
                propertyInfo.name="name";
                break;
            case 2:
                propertyInfo.type=PropertyInfo.STRING_CLASS;
                propertyInfo.name="owner";
                break;

            case 3:
                propertyInfo.type=PropertyInfo.STRING_CLASS;
                propertyInfo.name="foundation";
                break;
            case 4:
                propertyInfo.type=PropertyInfo.STRING_CLASS;
                propertyInfo.name="type";
                break;
            case 5:
                propertyInfo.type=PropertyInfo.STRING_CLASS;
                propertyInfo.name="objetive";
                break;
            case 6:
                propertyInfo.type=PropertyInfo.STRING_CLASS;
                propertyInfo.name="partner";
                break;


            default:
                break;
        }

    }

}

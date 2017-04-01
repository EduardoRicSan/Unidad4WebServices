package mx.edu.utng.wsmovie;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by SERGIO on 31/03/2017.
 */

public class Fruit implements KvmSerializable {
    private int id;
    private String name;
    private String flavor;
    private String colour;
    private float price;

    public Fruit(int id, String name, String flavor, String colour, float price) {
        this.id = id;
        this.name = name;
        this.flavor = flavor;
        this.colour = colour;
        this.price = price;
    }

    public Fruit(){
        this(0,"","","",0.0f);
    }
    @Override
    public Object getProperty(int i) {
        switch (i){
            case 0:
                return  id;
            case 1:
                return  name;
            case 2:
                return  flavor;
            case 3:
                return  colour;
            case 4:
                return  price;

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
                name=o.toString();
                break;
            case 2:
                flavor=o.toString();
                break;
            case 3:
                colour=o.toString();
                break;
            case 4:
                price=Float.parseFloat(o.toString());
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
                propertyInfo.name="flavor";
                break;
            case 3:
                propertyInfo.type=PropertyInfo.STRING_CLASS;
                propertyInfo.name="colour";
                break;
            case 4:
                propertyInfo.type=Float.class;
                propertyInfo.name="price";
                break;
            default:
                break;
        }

    }



}

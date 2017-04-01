package mx.edu.utng.wsmovie;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by SERGIO on 31/03/2017.
 */

public class Song  implements KvmSerializable {
    private int id;
    private String name;
    private String author;
    private String album;
    private int year;

    public Song(int id, String name, String author, String album, int year) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.album = album;
        this.year = year;
    }
    public Song(){
        this(0,"","","",0);
    }

    @Override
    public Object getProperty(int i) {
        switch (i){
            case 0:
                return  id;
            case 1:
                return  name;
            case 2:
                return  author;
            case 3:
                return  album;
            case 4:
                return  year;

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
                author=o.toString();
                break;
            case 3:
                album=o.toString();
                break;
            case 4:
                year=Integer.parseInt(o.toString());
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
                propertyInfo.name="author";
                break;

            case 3:
                propertyInfo.type=PropertyInfo.STRING_CLASS;
                propertyInfo.name="album";
                break;
            case 4:
                propertyInfo.type=PropertyInfo.INTEGER_CLASS;
                propertyInfo.name="year";
                break;


            default:
                break;
        }

    }


}


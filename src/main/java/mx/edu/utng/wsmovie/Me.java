package mx.edu.utng.wsmovie;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by SERGIO on 31/03/2017.
 */

public class Me implements KvmSerializable {
    private int id;
    private String fullname;
    private String nickname;
    private String age;
    private String hobby;

    public Me(int id, String fullname, String nickname, String age, String hobby) {
        this.id = id;
        this.fullname = fullname;
        this.nickname = nickname;
        this.age = age;
        this.hobby = hobby;
    }
    public Me() {
        this(0,"","","","");
    }


    @Override
    public Object getProperty(int i) {
        switch (i){
            case 0:
                return  id;
            case 1:
                return  fullname;
            case 2:
                return  nickname;
            case 3:
                return  age;
            case 4:
                return  hobby;
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
                fullname=o.toString();
                break;
            case 2:
                nickname=o.toString();
                break;
            case 3:
                age=o.toString();
                break;
            case 4:
                hobby=o.toString();
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
                propertyInfo.name="fullname";
                break;
            case 2:
                propertyInfo.type=PropertyInfo.STRING_CLASS;
                propertyInfo.name="nickname";
                break;

            case 3:
                propertyInfo.type=PropertyInfo.STRING_CLASS;
                propertyInfo.name="age";
                break;
            case 4:
                propertyInfo.type=PropertyInfo.STRING_CLASS;
                propertyInfo.name="hobby";
                break;

            default:
                break;
        }

    }
}

package com.hubin.android.p2pvoice.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作为列表中元素的最基本抽象类
 * 
 * @author hubin
 *
 */
public class BaseListItem implements Serializable, Cloneable
{
    private static final long serialVersionUID = 6076256592120368795L;

    private int id;

    protected String name;

//    private int number; // 编号
    // 位置
    protected int position = -1;

    protected String number;
    // 全拼
    private String nameLetters;
    // 全拼数组，支持多音字
    private List<String> nameLettersArray;
    // 头像资源ID
    private int iconRes;
    // 设置界面：用于删除时是否被选中标志
    private boolean isChecked = false;
    // 选择界面：是否是已被选中
    private boolean isSelected = false;
    // 业务界面：是否是已被选中
    private boolean isChoice = false;
    // 显示数据拼音的首字母
    private String sortLetters = "#";
    // 类型 
    protected int type = -1;
    // 子类型
    protected int subType = -1;
    //扩充字段
    private String data1 = null;
    private String data2 = null;
    
    private String mateNumber;// 最匹配号码

    public String getMateNumber()
    {
        return mateNumber;
    }

    public void setMateNumber(String mateNumber)
    {
        this.mateNumber = mateNumber;
    }

    public int getId()
    {
        return id;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public int getSubType()
    {
        return subType;
    }

    public void setSubType(int subType)
    {
        this.subType = subType;
    }

    public boolean isSelected()
    {
        return isSelected;
    }

    public void setSelected(boolean isSelected)
    {
        this.isSelected = isSelected;
    }

    public boolean isChoice()
    {
        return isChoice;
    }

    public void setChoice(boolean isChoice)
    {
        this.isChoice = isChoice;
    }

    public void triggerChoice()
    {
        isChoice = !isChoice;
    }

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getSortLetters()
    {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters)
    {
        this.sortLetters = sortLetters;
    }

//    public int getNumber()
//    {
//        return number;
//    }
//
//    public void setNumber(int number)
//    {
//        this.number = number;
//    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String content)
    {
        this.number = content;
    }

    public int getIconRes()
    {
        return iconRes;
    }

    public void setIconRes(int iconRes)
    {
        this.iconRes = iconRes;
    }

    public void triggerSelected()
    {
        isSelected = !isSelected;
    }

    public boolean isChecked()
    {
        return isChecked;
    }

    public void setChecked(boolean isChecked)
    {
        this.isChecked = isChecked;
    }

    public String getNameLetters()
    {
        return nameLetters;
    }

    public void setNameLetters(String nameLetters)
    {
        this.nameLetters = nameLetters;
    }

    public List<String> getNameLettersArray()
    {
        return nameLettersArray;
    }

    public void setNameLettersArray(List<String> nameLettersArray)
    {
        this.nameLettersArray = nameLettersArray;
    }

    @Override
    public BaseListItem clone()
    {
        try
        {
            return (BaseListItem) super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public String getData1()
    {
        return data1;
    }

    public void setData1(String data1)
    {
        this.data1 = data1;
    }

    public String getData2()
    {
        return data2;
    }

    public void setData2(String data2)
    {
        this.data2 = data2;
    }

//    @Override
//    public int describeContents()
//    {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags)
//    {
//
//        dest.writeInt(id);
//        dest.writeString(name);
//        dest.writeInt(number);
//        dest.writeInt(position);
//        dest.writeString(content);
//        dest.writeString(nameLetters);
//
//        if (nameLettersArray == null)
//        {
//            dest.writeString("");
//        }
//        else
//        {
//            dest.writeInt(nameLettersArray.length);
//            dest.writeStringArray(nameLettersArray);
//        }
//        dest.writeInt(iconRes);
//        dest.writeByte((byte) (isChecked ? 1 : 0));
//        dest.writeByte((byte) (isSelected ? 1 : 0));
//        dest.writeString(sortLetters);
//
//    }
//
//    public static final Parcelable.Creator<BaseListItem> CREATOR = new Creator<BaseListItem>()
//    {
//
//        @Override
//        public BaseListItem[] newArray(int size)
//        {
//            return new BaseListItem[size];
//        }
//
//        @Override
//        public BaseListItem createFromParcel(Parcel source)
//        {
//            BaseListItem baseListItem = new BaseListItem();
//            baseListItem.id = source.readInt();
//            baseListItem.name = source.readString();
//            baseListItem.number = source.readInt();
//            baseListItem.position = source.readInt();
//            baseListItem.content = source.readString();
//            baseListItem.nameLetters = source.readString();
//            int length = source.readInt();
//            String[] nameLettersArray = null;
//            if (length > 0)
//            {
//                nameLettersArray = new String[length];
//                source.readStringArray(nameLettersArray);
//            }
//            baseListItem.nameLettersArray = nameLettersArray;
//            baseListItem.iconRes = source.readInt();
//            baseListItem.isSelected = source.readByte() != 0;
//            baseListItem.isChecked = source.readByte() != 0;
//            baseListItem.sortLetters = source.readString();
//            
//            return baseListItem;
//        }
//    };

}

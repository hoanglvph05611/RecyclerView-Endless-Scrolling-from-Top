package com.androidfizz.androidwhatappchatusingrecyclerview;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Vishnu on 3/11/2018.
 */

public class ModelMessage {
    private int msgID,msgType;
    private String name,msg,dateCreated;

    public ModelMessage(int msgID, int msgType, String name, String msg, String dateCreated) {
        this.msgID = msgID;
        this.msgType = msgType;
        this.name = name;
        this.msg = msg;
        this.dateCreated = dateCreated;
    }

    public int getMsgID() {
        return msgID;
    }

    public int getMsgType() {
        return msgType;
    }

    public String getName() {
        return name;
    }

    public String getMsg() {
        return msg;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }
}

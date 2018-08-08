package com.example.jake.chance_chain;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "chance-mobilehub-653619147-ChattingTable")

public class ChattingTableDO {
    private String _chatid;
    private String _picture;
    private String _receiver;
    private String _sender;
    private String _time;

    @DynamoDBHashKey(attributeName = "Chatid")
    @DynamoDBAttribute(attributeName = "Chatid")
    public String getChatid() {
        return _chatid;
    }

    public void setChatid(final String _chatid) {
        this._chatid = _chatid;
    }
    @DynamoDBAttribute(attributeName = "Picture")
    public String getPicture() {
        return _picture;
    }

    public void setPicture(final String _picture) {
        this._picture = _picture;
    }
    @DynamoDBIndexHashKey(attributeName = "Receiver", globalSecondaryIndexName = "FindReceiver")
    public String getReceiver() {
        return _receiver;
    }

    public void setReceiver(final String _receiver) {
        this._receiver = _receiver;
    }
    @DynamoDBIndexHashKey(attributeName = "Sender", globalSecondaryIndexName = "FindSender")
    public String getSender() {
        return _sender;
    }

    public void setSender(final String _sender) {
        this._sender = _sender;
    }
    @DynamoDBAttribute(attributeName = "Time")
    public String getTime() {
        return _time;
    }

    public void setTime(final String _time) {
        this._time = _time;
    }

}

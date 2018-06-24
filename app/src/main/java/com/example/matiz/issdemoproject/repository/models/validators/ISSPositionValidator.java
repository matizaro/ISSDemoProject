package com.example.matiz.issdemoproject.repository.models.validators;

import android.text.TextUtils;

import com.example.matiz.issdemoproject.repository.models.content.ISSPosition;

public class ISSPositionValidator implements Validate<ISSPosition> {
    @Override
    public boolean isValid(ISSPosition object) {
        return !(object==null
                || TextUtils.isEmpty(object.getLatitude())
                || TextUtils.isEmpty(object.getLongitude()));
    }
}

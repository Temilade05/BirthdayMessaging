package com.demtem.birthday_messaging.services.contracts;

import com.demtem.birthday_messaging.models.responses.Response;
import org.bson.types.ObjectId;

public interface IService<T> {

    Response<T> createT(T t, ObjectId id);

    Response<T> readAllTs(ObjectId userId);

    Response<T> readTById(ObjectId id);

    Response<T> updateT(T t);

    Response<T> deleteT(ObjectId id);
}

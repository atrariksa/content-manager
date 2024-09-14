package com.timbailmu.content.handler;

import com.timbailmu.content.model.DigitalTasbihResponse;
import com.timbailmu.content.model.DigitalTasbihZikr;

import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;

public class DigitalTasbihHandler implements CrudHandler{

    public void create(Context ctx) {

    }

    public void getAll(Context ctx) {
        DigitalTasbihResponse<DigitalTasbihZikr> resp = new DigitalTasbihResponse<>();
        resp.setData(new DigitalTasbihZikr());
        resp.setCode("00");
        resp.setMessage("Success");
        ctx.json(resp);
    }

    public void getOne(Context ctx, String id) {

    }

    public void update(Context ctx, String id) {

    }

    public void delete(Context ctx, String id) {

    }
}

package cun.yun.card.admin.dal.service;

public interface RedisService {

    public void set(String key, Object value);

    public Object get(String key);

    public void set(String key, Object value, Long time);

    public void remove(String key);
}

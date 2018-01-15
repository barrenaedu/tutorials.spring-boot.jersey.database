package com.dao;

import com.domain.Message;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;

@Repository
public class MessageDaoImpl extends NamedParameterJdbcDaoSupport implements MessageDao {
    private static final String TABLE_NAME = "MESSAGES";

    private enum Column {
        ID,
        TEXT,
        SELECTED
    }

    private long id;

    @Autowired
    public MessageDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    public long createMessage(Message msg) {
        id++;
        msg.setId(id);
        String sql = "INSERT INTO " + TABLE_NAME + " " +
                "(" + Column.ID.toString() + ", " + Column.TEXT.toString() + ", " + Column.SELECTED.toString() + ") VALUES " +
                "(:" + Column.ID.toString() + ", :" + Column.TEXT.toString() + ", :" + Column.SELECTED.toString() + ")";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Column.ID.toString(), msg.getId());
        params.addValue(Column.TEXT.toString(), msg.getText());
        params.addValue(Column.SELECTED.toString(), BooleanUtils.toInteger(msg.isSelected()));
        getNamedParameterJdbcTemplate().update(sql, params);
        return msg.getId();
    }

    @Override
    public void updateMessage(Message msg) {
        String sql = "UPDATE " + TABLE_NAME + " SET " +
                Column.TEXT.toString() + "=:" + Column.TEXT.toString() + ", " +
                Column.SELECTED.toString() + "=:" + Column.SELECTED.toString();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Column.TEXT.toString(), msg.getText());
        params.addValue(Column.SELECTED.toString(), BooleanUtils.toInteger(msg.isSelected()));
        getNamedParameterJdbcTemplate().update(sql, params);
    }

    @Override
    public void deleteMessage(long id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + Column.ID.toString() + "=:" + Column.ID.toString();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Column.ID.toString(), id);
        getNamedParameterJdbcTemplate().update(sql, params);
    }

    @Override
    public Message getMessage(long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + Column.ID.toString() + "=:" + Column.ID.toString();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(Column.ID.toString(), id);
        try {
            return getNamedParameterJdbcTemplate().queryForObject(sql, params, (rs, rowNum) -> {
                Message msg = new Message();
                msg.setId(rs.getInt(Column.ID.toString()));
                msg.setText(rs.getString(Column.TEXT.toString()));
                msg.setSelected(BooleanUtils.toBoolean(rs.getInt(Column.SELECTED.toString())));
                return msg;
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Collection<Message> getMessages() {
        try {
            return getNamedParameterJdbcTemplate().query("SELECT * FROM " + TABLE_NAME, (rs, rowNum) -> {
                Message msg = new Message();
                msg.setId(rs.getInt(Column.ID.toString()));
                msg.setText(rs.getString(Column.TEXT.toString()));
                msg.setSelected(BooleanUtils.toBoolean(rs.getInt(Column.SELECTED.toString())));
                return msg;
            });
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

}

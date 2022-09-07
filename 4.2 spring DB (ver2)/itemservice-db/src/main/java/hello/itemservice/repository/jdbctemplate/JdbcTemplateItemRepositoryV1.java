package hello.itemservice.repository.jdbctemplate;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@Slf4j
public class JdbcTemplateItemRepositoryV1 implements ItemRepository {
    private final JdbcTemplate template;

    public JdbcTemplateItemRepositoryV1(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Item save(Item item) {
        String sql = "insert into item(item_name, price, quantity) values(?,?,?) ";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection ->{
            //자동 증가 키
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});    //id로 키를 자동 생성하기 위해
            ps.setString(1,item.getItemName());
            ps.setInt(2,item.getPrice());
            ps.setInt(3,item.getQuantity());
            return ps;
        },keyHolder);
        long key = keyHolder.getKey().longValue();
        item.setId(key);
        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        String sql = "update item set item_name=?,price=?,quantity=? where id=?";
        template.update(sql,updateParam.getItemName(),updateParam.getPrice(),updateParam.getQuantity(),itemId);
    }

    @Override
    public Optional<Item> findById(Long id) {
        String sql = "select id, item_name,price, quantity from item where id=?";
        try{
            Item item = template.queryForObject(sql, itemRowMapper(),id);
            return Optional.of(item);   //null인경우에 예외가 터지기 때문에
        }
        catch(EmptyResultDataAccessException e){    //데이터 결과가 없는 경우에
            return Optional.empty();
        }
        catch(IncorrectResultSizeDataAccessException e){
            return Optional.empty();    //예외가 둘 이상인경우.
        }


    }

    private RowMapper<Item> itemRowMapper() {
        return ((rs,rowNum)->{
            Item item = new Item();
            item.setId(rs.getLong("id"));
            item.setItemName(rs.getString("item_name"));
            item.setPrice(rs.getInt("price"));
            item.setQuantity(rs.getInt("quantity"));
            return item;
        });
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();
        String sql = "select * from item";
        List<Object> param = new ArrayList<>();
        //동적쿼리
        if(StringUtils.hasText(itemName)||maxPrice!=null){
            param.add(itemName);
            sql+=" where";
        }
        boolean andFlag=false;
        if(StringUtils.hasText(itemName)){
            sql+=" item_name like concat('%',?,'%')";
            param.add(itemName);
            andFlag  = true;
        }
        if(maxPrice!=null){
            if(andFlag){
                sql+=" and";
            }
            sql+=" price <= ?";
            param.add(maxPrice);
        }
        log.info("sql={}",sql);


        return  template.query(sql,itemRowMapper(),param.toArray());
    }
}

package hello.itemservice.repository.jdbctemplate;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * SimpleJdbcInsert
 * */


@Slf4j
@Repository
public class JdbcTemplateItemRepositoryV3 implements ItemRepository{
    private final NamedParameterJdbcTemplate template;  //파라미터를 순서기반이 아닌 이름기반으로 하게 된다.
    private final SimpleJdbcInsert jdbcInsert;
    public JdbcTemplateItemRepositoryV3(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("item")
                .usingGeneratedKeyColumns("id");
           //    .usingColumns("item_name","price","quantity") 생략 가능 SimpleJdbcInsert가 자동으로 데이터를 인지하기 때문
    }

    @Override
    public Item save(Item item) {
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(item);
        Number key = jdbcInsert.executeAndReturnKey(param);
        item.setId(key.longValue());

        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        String sql = "update item set item_name=:itemName,price=:price,quantity=:quantity where id=?:id";

        SqlParameterSource param = new MapSqlParameterSource().addValue("itemName", updateParam.getItemName()).addValue("price", updateParam.getPrice())
                .addValue("quantity", updateParam.getQuantity()).addValue("id", itemId);
        template.update(sql,param);

    }

    @Override
    public Optional<Item> findById(Long id) {
        String sql = "select id, item_name,price, quantity from item where id=:id";
        try{
            Map<String, Object> param = Map.of("id", id);
            Item item = template.queryForObject(sql,param, itemRowMapper());
            return Optional.of(item);   //null인경우에 예외가 터지기 때문에
        }
        catch(EmptyResultDataAccessException e){    //데이터 결과가 없는 경우에
            return Optional.empty();
        }
//        catch(IncorrectResultSizeDataAccessException e){
//            return Optional.empty();    //예외가 둘 이상인경우.
//        }


    }


    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();

        SqlParameterSource param = new BeanPropertySqlParameterSource(cond);
        //자바Bean프로퍼티 규약을 통해 자동으로 파라미터 객체 생성
        //getXxx() -> xxx ex ) getPrice -> price로 변환하여 꺼내와서 key,value로 꺼내옴
        String sql = "select id,item_name as itemName ,price,quantity from item";
        //db 컬럼이름과 객체이름이 다를때 컬럼이름 + as + 객체이름을 써서 별칭으로 만들어서 해결
        //sql에서는 snake 표기법이라고 해서 item_name 으로 컬럼을 표기하는 반면 자바 객체는 카멜 표기법을 사용함.
        //SqlParameterSource는 snake표기법을 카멜표기법으로 자동 변환시켜줌. 컬럼이름과 객체이름이 완전 다를때만 as 객체이름을 써서 별칭으로 붙여주면 된다.

        //동적쿼리
        if(StringUtils.hasText(itemName)||maxPrice!=null){
            sql+=" where";
        }
        boolean andFlag=false;
        if(StringUtils.hasText(itemName)){
            sql+=" item_name like concat('%',:itemName,'%')";
            andFlag  = true;
        }
        if(maxPrice!=null){
            if(andFlag){
                sql+=" and";
            }
            sql+=" price <= :maxPrice";
        }
        log.info("sql={}",sql);


        return  template.query(sql,param,itemRowMapper());
    }


    private RowMapper<Item> itemRowMapper() {
        return BeanPropertyRowMapper.newInstance(Item.class);   //resultSet 결과를 자바빈 규약에 맞추어 데이터 반환(camel 변환 지원)
    }

}

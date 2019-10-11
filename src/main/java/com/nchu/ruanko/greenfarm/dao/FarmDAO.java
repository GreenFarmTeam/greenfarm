package com.nchu.ruanko.greenfarm.dao;

import com.nchu.ruanko.greenfarm.pojo.entity.Farm;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface FarmDAO {

    @Insert("INSERT INTO gf_tb_farm(farm_uid,farm_price,farm_unit,farm_year,farm_area,farm_desc,farm_lng,farm_lat,farm_ctime,farm_type_uid,farm_business_uid)" +
            " VALUES(#{farm.farmUid},#{farm.farmPrice},#{farm.farmUnit},#{farm.farmYear},#{farm.farmArea},#{farm.farmDescription},#{farm.farmLongitude},#{farm.farmLatitude},#{farm.farmUpDate},#{typeUid},#{uid})")
    void insertFarm(@Param(value = "farm") Farm farm, @Param(value = "typeUid") String farmTypeUid, @Param(value = "uid") String businessUid);


    @Results(id = "farmMapper1", value = {
            @Result(property = "farmUid", column = "farm_uid"),
            @Result(property = "farmName", column = "farm_name"),
            @Result(property = "farmPrice", column = "farm_price"),
            @Result(property = "farmUnit", column = "farm_unit"),
            @Result(property = "farmYear", column = "farm_year"),
            @Result(property = "farmArea", column = "farm_area"),
            @Result(property = "farmDescription", column = "farm_desc"),
            @Result(property = "farmLongitude", column = "farm_lng"),
            @Result(property = "farmLatitude", column = "farm_lat"),
            @Result(property = "farmUpDate", column = "farm_ctime"),
            @Result(property = "farmState", column = "farm_state"),
            @Result(property = "business", column = "farm_business_uid", one = @One(select = "com.nchu.ruanko.greenfarm.dao.BusinessDAO.getBusinessByUID")),
            @Result(property = "type", column = "farm_type_uid", one = @One(select = "com.nchu.ruanko.greenfarm.dao.FarmTypeDAO.getFarmTypeByFarmTypeUID"))
    })
    @Select("SELECT *" +
            " FROM gf_tb_farm" +
            " WHERE farm_business_uid=#{uid}")
    List<Farm> listFarmsByBusinessUID(@Param(value = "uid") String businessUid);

    @ResultMap(value = "farmMapper1")
    @Select("SELECT *" +
            " FROM gf_tb_farm" +
            " WHERE farm_uid=#{uid}")
    Farm getFarmByFarmUID(@Param(value = "uid") String farmUid);

    @ResultMap(value = "farmMapper1")
    @Select("Select * from "+"" +
            "gf_tb_farm,gf_tb_farm_review " +
            "where gf_tb_farm_review.rev_farm_uid=gf_tb_farm.farm_uid and gf_tb_farm_review.rev_result=1"
          )
    List<Farm> listAllPassFarm();

    /**
     * 加载所有未审核的农场信息
     * @return
     */
    @ResultMap(value = "farmMapper1")
    @Select("Select * from "+"" +
            "gf_tb_farm,gf_tb_farm_review " +
            "where gf_tb_farm_review.rev_farm_uid=gf_tb_farm.farm_uid and gf_tb_farm.farm_state=0"
    )
    List<Farm> listAllNoCheckedFarm();

    /**
     * 加载所有管理员下架的农场信息
     * @return
     */
    @ResultMap(value = "farmMapper1")
    @Select("Select * from "+"" +
            "gf_tb_farm " +
            "where gf_tb_farm.farm_state=10"
    )
    List<Farm> listAllAdminDownFarm();
    /**
     * 加载所有正在上架的农场信息
     * @return
     */
    @ResultMap(value = "farmMapper1")
    @Select("Select * from "+"" +
            "gf_tb_farm " +
            "where gf_tb_farm.farm_state=11"
    )
    List<Farm> listAllUpFarm();
    /**
     * 加载所有农场主下架的农场信息
     * @return
     */
    @ResultMap(value = "farmMapper1")
    @Select("Select * from "+"" +
            "gf_tb_farm " +
            "where gf_tb_farm.farm_state=1"
    )
    List<Farm> listAllBusinessDownFarm();

    @Update("UPDATE gf_tb_farm" +
            " SET farm_ctime=#{date}" +
            " WHERE farm_uid=#{uid}")
    void updateFarmUpDateByFarmUID(@Param("date") Date date,@Param("uid") String farmUid);

    @Update("UPDATE gf_tb_farm" +
            " SET farm_state=#{state}" +
            " WHERE farm_uid=#{uid}")
    void updateFarmState(@Param("state")int up_state,@Param("uid") String farmUid);

    @ResultMap(value = "farmMapper1")
    @Select("SELECT *" +
            " FROM gf_tb_farm,gf_tb_farm_review" +
            " WHERE gf_tb_farm.farm_uid=gf_tb_farm_review.rev_farm_uid " +
            " AND gf_tb_farm_review.rev_rtime IS NOT NULL"
    )
    List<Farm> listAllCheckedFarm();

    @ResultMap(value = "farmMapper1")
    @Select("Select * from "+"" +
            "gf_tb_farm,gf_tb_farm_review,gf_tb_business " +
            "where gf_tb_farm.farm_uid=gf_tb_farm_review.rev_farm_uid and gf_tb_business.bus_uid=gf_tb_farm.farm_business_uid and gf_tb_business.bus_uid=#{uid} and gf_tb_farm_review.rev_rtime is not null"
    )
    List<Farm> listAllCheckedFarmByBusinessUid(@Param("uid") String businessUid);

    @ResultMap(value = "farmMapper1")
    @Select("Select * from "+"" +
            "gf_tb_farm,gf_tb_farm_review,gf_tb_business " +
            "where gf_tb_farm_review.rev_farm_uid=gf_tb_farm.farm_uid and gf_tb_business.bus_uid=gf_tb_farm.farm_business_uid and gf_tb_business.bus_uid=#{uid}  and gf_tb_farm.farm_state=0"
    )
    List<Farm> listAllNoCheckedFarmByBusinessUid(@Param("uid")String businessUid);

    @ResultMap(value = "farmMapper1")
    @Select("Select * from "+"" +
            "gf_tb_farm,gf_tb_business " +
            "where gf_tb_farm.farm_state=10 and gf_tb_business.bus_uid=gf_tb_farm.farm_business_uid and gf_tb_business.bus_uid=#{uid} "
    )
    List<Farm> listAllAdminDownFarmByBusinessUid(@Param("uid")String businessUid);

    @ResultMap(value = "farmMapper1")
    @Select("Select * from "+"" +
            "gf_tb_farm,gf_tb_business " +
            "where gf_tb_farm.farm_state=11 and gf_tb_business.bus_uid=gf_tb_farm.farm_business_uid and gf_tb_business.bus_uid=#{uid} "
    )
    List<Farm> listAllUpFarmByBusinessUid(@Param("uid")String businessUid);

    @ResultMap(value = "farmMapper1")
    @Select("Select * from "+"" +
            "gf_tb_farm,gf_tb_business " +
            "where gf_tb_farm.farm_state=1 and gf_tb_business.bus_uid=gf_tb_farm.farm_business_uid and gf_tb_business.bus_uid=#{uid} "
    )
    List<Farm> listAllBusinessDownFarmByBusinessUid(@Param("uid")String businessUid);
}